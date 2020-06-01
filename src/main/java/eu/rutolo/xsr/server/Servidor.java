package eu.rutolo.xsr.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.rutolo.xsr.Main;
import eu.rutolo.xsr.data.Log;
import eu.rutolo.xsr.data.Traductor;
import eu.rutolo.xsr.db.Cliente;
import eu.rutolo.xsr.db.Operacions;
import eu.rutolo.xsr.db.Pedido;
import eu.rutolo.xsr.db.Peza;
import eu.rutolo.xsr.db.Reparacion;

public class Servidor extends Thread {

	public static final String VERSION = "1.0";
	public static boolean logging = true;

	private Socket soc;
	private Operacions op;

	public Servidor(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		Log.i("Recibida petición de " + soc.getInetAddress().getHostName() + ":" + soc.getPort());

		// Conexion DB
		op = Main.db;

		try {
			procesar();
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void procesar() throws IOException {
		final int MAX_BUFFER = 1024;
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		byte[] messageBytes = new byte[MAX_BUFFER];
		String dataString = "";

		while (true) {
			int bytesRead = dis.read(messageBytes);
			dataString += new String(messageBytes, 0, bytesRead);

			if (bytesRead < MAX_BUFFER) {
				break;
			}
		}

		Peticion p = new Peticion(dataString);

		// Comprueba tipo
		switch (p.getTipo()) {
			case Peticion.GET:
				peticionGet(p);
				break;
			case Peticion.CREATE:
				peticionCreate(p);
				break;
			case Peticion.UPDATE:
				peticionUpdate(p);
				break;
			case Peticion.DELETE:
				peticionDelete(p);
				break;
			default:
				peticionError(p);
				break;
		}
	}

	private void peticionGet(Peticion p) throws IOException {
		JSONObject result = new JSONObject();
		try {
			JSONArray arr = new JSONArray();
			switch (p.getApartado()) {
				case Peticion.X_CLIENTES:
					for (Cliente cliente : op.listClientes()) {
						arr.put(new JSONObject(cliente));
					}
					break;
				case Peticion.X_PEZAS:
					for (Peza peza : op.listPezas()) {
						arr.put(new JSONObject(peza));
					}
					break;
				case Peticion.X_PEDIDOS:
					for (Pedido pedido : op.listPedidos()) {
						arr.put(new JSONObject(pedido));
					}
					break;
				case Peticion.X_REPARACIONS:
					for (Reparacion rep : op.listReparaciones()) {
						arr.put(new JSONObject(rep));
					}
					break;
				default:
					peticionError(p);
					return;
			}
			result.put("data", arr);
		} catch (JSONException e) {
			peticionError(p);
		}

		enviarRespuesta(Respuesta.getRespuesta(p, result));
	}

	private void peticionCreate(Peticion p) throws IOException {
		Respuesta respuesta = null;
		try {
			boolean exito = false;
			boolean flag404 = false;
			switch (p.getApartado()) {
				case Peticion.X_CLIENTES:
					// Crear cliente
					JSONObject clj = p.getDatos().getJSONObject("cliente");
					exito = op.addCliente(clj.getString("nome"), clj.getString("tlf"), clj.getString("email"),
							clj.getString("notas"));
					if (exito) {
						Log.i("Creado cliente " + clj.getString("nome"));
					} else {
						Log.e("No se pudo crear el cliente " + clj.getString("nome"));
					}
					break;

				case Peticion.X_PEZAS:
					JSONObject pezaJson = p.getDatos().getJSONObject("peza");
					exito = op.addPeza(pezaJson.getString("codigo"), pezaJson.getString("prov"),
							pezaJson.getString("nome"), pezaJson.getString("foto"), pezaJson.getBigDecimal("precio"),
							pezaJson.getInt("cantidade"), pezaJson.getString("notas"));
					if (exito) {
						Log.i("Creada peza " + pezaJson.getString("nome"));
					} else {
						Log.e("No se pudo crear la pieza " + pezaJson.getString("nome"));
					}
					break;

				case Peticion.X_PEDIDOS:
					JSONObject pedidoJson = p.getDatos().getJSONObject("pedido");
					int idCliente = pedidoJson.getInt("idCliente");
					int idPeza = pedidoJson.getInt("idPeza");

					if (op.getCliente(idCliente) == null) {
						JSONObject contenido404 = new JSONObject();
						contenido404.put("obj", "cliente");
						contenido404.put("id", idCliente);

						respuesta = Respuesta.getRespuesta404(contenido404);
						flag404 = true;
					} else if (op.getPeza(idPeza) == null) {
						JSONObject contenido404 = new JSONObject();
						contenido404.put("obj", "peza");
						contenido404.put("id", idCliente);

						respuesta = Respuesta.getRespuesta404(contenido404);
						flag404 = true;
					} else {
						exito = op.addPedido(idCliente, idPeza, pedidoJson.getString("pvp"),
								pedidoJson.getString("estado"));
					}
					break;

				case Peticion.X_REPARACIONS:
					JSONObject repJson = p.getDatos().getJSONObject("reparacion");
					int idClienteRep = repJson.getInt("id_cliente");

					if (op.getCliente(idClienteRep) == null) {
						JSONObject contenido404 = new JSONObject();
							contenido404.put("obj", "peza");
							contenido404.put("id", idClienteRep);
	
							respuesta = Respuesta.getRespuesta404(contenido404);
							flag404 = true;
					} else {

						Reparacion rep = new Reparacion(
							Traductor.string2date(repJson.getString("ini")),
							Traductor.string2date(repJson.getString("fin")),
							repJson.getInt("n_horas"),
							repJson.getBoolean("completa"),
							repJson.getString("causa"),
							repJson.getString("solucion"),
							repJson.getBigDecimal("pvp"),
							repJson.getString("notas"),
							idClienteRep
						);

						// Comprobar y añadir las piezas
						JSONArray idsPezasJson = repJson.getJSONArray("ids_pezas");
						for (int i = 0; i < idsPezasJson.length(); i++) {
							int id = Integer.parseInt((String) idsPezasJson.get(i));

							if (op.getPeza(id) == null) {
								JSONObject contenido404 = new JSONObject();
								contenido404.put("obj", "peza");
								contenido404.put("id", id);
		
								respuesta = Respuesta.getRespuesta404(contenido404);
								flag404 = true;
							} else {
								rep.addIdPeza(id);
							}
						}
						exito = op.addReparacion(rep);
					}
					break;

				default:
					peticionError(p);
					return;
			}

			if (!flag404) {
				respuesta = Respuesta.getRespuesta(p);
			}
		} catch (JSONException e) {
			peticionError(p);
		}

		enviarRespuesta(respuesta);
	}

	private void peticionUpdate(Peticion p) throws IOException {
		Respuesta respuesta = null;
		try {
			boolean exito = false;
			boolean flag404 = false;
			int id = 0;
			switch (p.getApartado()) {
				case Peticion.X_CLIENTES:
					id = p.getSelec().getInt("id");
					Cliente c = op.getCliente(id);
					// Modificar los datos nuevos en el objeto
					try {
						c.setNome(
							p.getDatos().getString("nome")
						);
					} catch (Exception e) {}

					try {
						c.setTlf(
							p.getDatos().getString("tlf")
						);
					} catch (Exception e) {}

					try {
						c.setEmail(
							p.getDatos().getString("email")
						);
					} catch (Exception e) {}

					try {
						c.setNotas(
							p.getDatos().getString("notas")
						);
					} catch (Exception e) {}

					exito = op.updateCliente(c);
					break;

				case Peticion.X_PEZAS:
					id = p.getSelec().getInt("id");
					Peza peza = op.getPeza(id);
					try {
						peza.setCodigo(
							p.getDatos().getString("codigo")
						);
					} catch (Exception e) {}
					try {
						peza.setProv(
							p.getDatos().getString("prov")
						);
					} catch (Exception e) {}
					try {
						peza.setNome(
							p.getDatos().getString("nome")
						);
					} catch (Exception e) {}
					try {
						peza.setFoto(
							p.getDatos().getString("foto")
						);
					} catch (Exception e) {}
					try {
						peza.setPrecio(
							p.getDatos().getString("precio")
						);
					} catch (Exception e) {}
					try {
						peza.setCantidade(
							p.getDatos().getInt("cantidade")
						);
					} catch (Exception e) {}
					try {
						peza.setNotas(
							p.getDatos().getString("notas")
						);
					} catch (Exception e) {}

					exito = op.updatePeza(peza);
					break;

				case Peticion.X_PEDIDOS:
					int idCliente = p.getSelec().getInt("idCliente");
					int idPeza = p.getSelec().getInt("idPeza");
					
					Pedido pedido = op.getPedido(idCliente, idPeza);
					try {
						pedido.setPvp(
							p.getDatos().getString("pvp")
						);
					} catch (Exception e) {}
					try {
						pedido.setEstado(
							p.getDatos().getString("estado")
						);
					} catch (Exception e) {}

					exito = op.updatePedido(pedido);
					break;

				case Peticion.X_REPARACIONS:
					id = p.getSelec().getInt("id");
					Reparacion rep = op.getReparacion(id);
					try {
						rep.setIni(
							Traductor.string2date(p.getDatos().getString("ini"))
						);
					} catch (Exception e) {}
					try {
						rep.setFin(
							Traductor.string2date(p.getDatos().getString("fin"))
						);
					} catch (Exception e) {}
					try {
						rep.setNhoras(
							p.getDatos().getInt("ini")
						);
					} catch (Exception e) {}
					try {
						rep.setCompleta(
							p.getDatos().getBoolean("completa")
						);
					} catch (Exception e) {}
					try {
						rep.setCausa(
							p.getDatos().getString("causa")
						);
					} catch (Exception e) {}
					try {
						rep.setSolucion(
							p.getDatos().getString("solucion")
						);
					} catch (Exception e) {}
					try {
						rep.setPvp(
							p.getDatos().getBigDecimal("pvp")
						);
					} catch (Exception e) {}
					try {
						rep.setNotas(
							p.getDatos().getString("notas")
						);
					} catch (Exception e) {}
					try {
						int idClienteRep = p.getDatos().getInt("id_cliente");
						if (op.getCliente(id) == null) {
							JSONObject contenido404 = new JSONObject();
							contenido404.put("obj", "peza");
							contenido404.put("id", id);
	
							respuesta = Respuesta.getRespuesta404(contenido404);
							flag404 = true;
							break;
						} else {
							rep.setIdCliente(
								idClienteRep
							);
						}
					} catch (Exception e) {}
					
					// Comprobar y añadir las piezas
					try {
						ArrayList<Integer> idsPiezasNuevas = new ArrayList<>();
						JSONArray idsPezasJson = p.getDatos().getJSONArray("ids_pezas");
						for (int i = 0; i < idsPezasJson.length(); i++) {
							int idPezaRep = (int) idsPezasJson.get(i);
	
							if (op.getCliente(idPezaRep) == null) {
								JSONObject contenido404 = new JSONObject();
								contenido404.put("obj", "peza");
								contenido404.put("id", id);
		
								respuesta = Respuesta.getRespuesta404(contenido404);
								flag404 = true;
								break;
							} else {
								idsPiezasNuevas.add(id);
							}
						}

						int[] arr = new int[idsPiezasNuevas.size()];
						for (int i = 0; i < idsPiezasNuevas.size(); i++) {
							arr[i] = idsPiezasNuevas.get(i);
						}
						rep.setIdsPezas(arr);
					} catch (Exception e) {}

					op.updateReparacion(rep);
					break;

				default:
					peticionError(p);
					return;
			}

			if (!flag404) {
				respuesta = Respuesta.getRespuesta(p);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			peticionError(p);
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			peticionError(p);
			return;
		}

		enviarRespuesta(respuesta);
	}

	private void peticionDelete(Peticion p) throws IOException {
		boolean exito = false;
		JSONObject datoBorrado = new JSONObject();
		try {
			int id = 0;
			switch (p.getApartado()) {
				case Peticion.X_CLIENTES:
					id = p.getSelec().getInt("id");
					datoBorrado = new JSONObject(op.getCliente(id));
					exito = op.deleteCliente(id);
					break;

				case Peticion.X_PEZAS:
					id = p.getSelec().getInt("id");
					datoBorrado = new JSONObject(op.getPeza(id));
					exito = op.deletePeza(id);
					break;

				case Peticion.X_PEDIDOS:
					int idCliente = p.getSelec().getInt("idCliente");
					int idPeza = p.getSelec().getInt("idPeza");
					exito = op.deletePedido(idCliente, idPeza);
					break;
				case Peticion.X_REPARACIONS:
					id = p.getSelec().getInt("id");
					exito = op.deleteReparacion(id);
					break;
				default:
					peticionError(p);
					return;
			}
		} catch (JSONException e) {
			peticionError(p);
		}

		enviarRespuesta(Respuesta.getRespuesta(p.getTipo(), exito, datoBorrado));
	}

	private void peticionError(Peticion p) throws IOException {
		Log.w("Petición errónea");
		enviarRespuesta(Respuesta.getRespuesta(p.getTipo(), false));
	}

	private void enviarRespuesta(Respuesta r) throws IOException {
		PrintWriter out = new PrintWriter(soc.getOutputStream());
		BufferedOutputStream dataOut = new BufferedOutputStream(soc.getOutputStream());

		for (String s : r.getHeaders()) {
			out.println(s);
		}
		out.flush();

		dataOut.write(r.getContent().getBytes(), 0, r.getContentLength());
		dataOut.flush();
	}
}

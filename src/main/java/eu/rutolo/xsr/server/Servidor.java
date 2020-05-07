package eu.rutolo.xsr.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.rutolo.xsr.data.Log;
import eu.rutolo.xsr.db.Cliente;
import eu.rutolo.xsr.db.Operacions;

public class Servidor extends Thread {

	public static final String VERSION = "1.0";
	public static boolean logging = true;

	private Socket soc;
	private Operacions op;
	private boolean exito = false;

	public Servidor(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		Log.i("Recibida petición de " + soc.getInetAddress().getHostName() + ":" + soc.getPort());

		// Conexion DB
		op = new Operacions();

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
		JSONArray arr = new JSONArray();
		switch (p.getApartado()) {
			case Peticion.X_CLIENTES:
				for (Cliente c : op.listClientes()) {
					arr.put(new JSONObject(c));
				}
				result.put("data", arr);
				exito = true;
				break;
			case Peticion.X_PEZAS:
				break;
			case Peticion.X_PEDIDOS:
				break;
			case Peticion.X_REPARACIONS:
				break;
			default:
				break;
		}

		// componer y enviar respuesta
		Respuesta r = Respuesta.getRespuesta(p, exito, result);
		
		PrintWriter out = new PrintWriter(soc.getOutputStream());
		BufferedOutputStream dataOut = new BufferedOutputStream(soc.getOutputStream());

		for (String s : r.getHeaders()) {
			Log.d("Escribiendo " + s);
			out.println(s);
		}
		out.flush();

		dataOut.write(r.getContent().getBytes(), 0, r.getContentLength());
		dataOut.flush();
	}

	private void peticionCreate(Peticion p) throws IOException {
		switch (p.getApartado()) {
			case Peticion.X_CLIENTES:
				// Crear cliente
				JSONObject clj = p.getDatos().getJSONObject("cliente");
				exito = op.addCliente(
					clj.getString("nome"),
					clj.getString("tlf"),
					clj.getString("email"),
					clj.getString("notas")
				);
				if (exito) {
					Log.i("Creado cliente " + clj.getString("nome"));
				} else {
					Log.e("No se pudo crear el cliente " + clj.getString("nome"));
				}
				break;

			case Peticion.X_PEZAS:
				break;
			case Peticion.X_PEDIDOS:
				break;
			case Peticion.X_REPARACIONS:
				break;
			default:
				break;
		}

		// componer y enviar respuesta
		Respuesta r = Respuesta.getRespuesta(p, exito);
		PrintWriter out = new PrintWriter(soc.getOutputStream());
		BufferedOutputStream dataOut = new BufferedOutputStream(soc.getOutputStream());

		for (String s : r.getHeaders()) {
			Log.d("Escribiendo " + s);
			out.println(s);
		}
		out.flush();

		dataOut.write(r.getContent().getBytes(), 0, r.getContentLength());
		dataOut.flush();
	}

	private void peticionUpdate(Peticion p) {
		switch (p.getApartado()) {
			case Peticion.X_CLIENTES:
				return;
			case Peticion.X_PEZAS:
				return;
			case Peticion.X_PEDIDOS:
				return;
			case Peticion.X_REPARACIONS:
				return;
			default:
				return;
		}
	}

	private void peticionDelete(Peticion p) {
		switch (p.getApartado()) {
			case Peticion.X_CLIENTES:
				return;
			case Peticion.X_PEZAS:
				return;
			case Peticion.X_PEDIDOS:
				return;
			case Peticion.X_REPARACIONS:
				return;
			default:
				return;
		}
	}

	private void peticionError(Peticion p) {

	}
}

package eu.rutolo.xsr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Collectors;

import org.json.JSONObject;

import eu.rutolo.xsr.data.Log;
import eu.rutolo.xsr.db.Operacions;

public class Servidor extends Thread {

	public static boolean logging = true;

	private Socket soc;
	private Operacions op;

	public Servidor(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		Log.i("Recibida petición de " + soc.getInetAddress().getHostName());

		// Conexion DB
		op = new Operacions();

		procesar();
		try {
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void procesar() {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String reqStr = br.lines().collect(Collectors.joining("\n"));
		Log.d(reqStr);
		Peticion p = new Peticion(reqStr);

		switch (p.getTipo()) {
			case Peticion.GET:
				peticionGet(p);
				return;
			case Peticion.CREATE:
				peticionCreate(p);
				return;
			case Peticion.UPDATE:
				peticionUpdate(p);
				return;
			case Peticion.DELETE:
				peticionDelete(p);
				return;
			default:
				peticionError(p);
				return;
		}
	}

	private void peticionGet(Peticion p) {
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

	private void peticionCreate(Peticion p) {
		switch (p.getApartado()) {
			case Peticion.X_CLIENTES:
				// Crear cliente
				JSONObject clj = p.getDatos().getJSONObject("cliente");
				boolean res = op.addCliente(
					clj.getString("nome"),
					clj.getString("tlf"),
					clj.getString("email"),
					clj.getString("notas")
				);
				if (res) {
					Log.i("Creado cliente " + clj.getString("nome"));
				} else {
					Log.e("No se pudo crear el cliente " + clj.getString("nome"));
				}
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

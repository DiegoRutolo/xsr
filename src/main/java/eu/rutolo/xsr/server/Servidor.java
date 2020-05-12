package eu.rutolo.xsr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Collectors;

import eu.rutolo.xsr.data.Log;

public class Servidor extends Thread {

	public static boolean logging = true;

	private Socket soc;

	public Servidor(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		Log.i("Recibida petición de " + soc.getInetAddress().getHostName());
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
	}
}

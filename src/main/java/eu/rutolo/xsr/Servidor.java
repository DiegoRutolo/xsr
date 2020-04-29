package eu.rutolo.xsr;

import java.io.IOException;
import java.net.Socket;

public class Servidor extends Thread {

	public static boolean logging = true;

	private Socket soc;

	public Servidor(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		log("Recibida petición de " + soc.getInetAddress().getHostName());
		procesar();
		try {
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void log(String msg) {
		if (logging) {
			System.out.println(msg);
		}
	}

	private void procesar() {
		
	}
}
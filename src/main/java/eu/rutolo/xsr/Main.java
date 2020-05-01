package eu.rutolo.xsr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import eu.rutolo.xsr.data.Config;

/**
 * Hello world!
 *
 */
public class Main {

	public static final int PORT = 10097;
	public static Config conf;

	public static void main(String[] args) {

		conf = new Config();

		ServerSocket ss;
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				Socket cliente = ss.accept();
				Servidor servidor = new Servidor(cliente);
				servidor.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

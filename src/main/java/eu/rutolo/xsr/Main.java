package eu.rutolo.xsr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import eu.rutolo.xsr.data.Config;
import eu.rutolo.xsr.db.Operacions;
import eu.rutolo.xsr.server.Servidor;

/**
 * Hello world!
 *
 */
public class Main {

	public static final int PORT = 10097;
	public static Config conf;
	public static Operacions db;

	public static void main(String[] args) {

		conf = new Config();
		db = new Operacions();

		System.out.println("Iniciado servidor con LogLevel " + conf.getLogLevel());

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

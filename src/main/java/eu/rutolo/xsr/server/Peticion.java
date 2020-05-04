package eu.rutolo.xsr.server;

import org.json.JSONObject;

public class Peticion {
	// Codigos de tipo de petición
	public static final int GET = 1;
	public static final int CREATE = 2;
	public static final int UPDATE = 3;
	public static final int DELETE = 4;

	// Codigos de Apartado
	public static final int X_CLIENTES = 5;

	private int tipo;
	private int apartado;
	private JSONObject content;		// https://stleary.github.io/JSON-java/index.html

	public Peticion(String reqStr) {
		
	}
}
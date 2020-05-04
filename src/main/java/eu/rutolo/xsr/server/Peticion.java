package eu.rutolo.xsr.server;

import java.io.BufferedReader;
import java.util.function.Consumer;

import org.json.JSONObject;

import eu.rutolo.xsr.data.Log;

public class Peticion {
	// Codigos de tipo de petición
	public static final int GET = 1;
	public static final int CREATE = 2;
	public static final int UPDATE = 3;
	public static final int DELETE = 4;

	// Codigos de Apartado
	public static final int X_CLIENTES = 5;
	public static final int X_PEZAS = 6;
	public static final int X_PEDIDOS = 7;
	public static final int X_REPARACIONS = 8;

	private String rawReq;
	private int tipo;
	private int apartado;
	private JSONObject content;
	private JSONObject datos;

	public Peticion(String rawReq) {
		this.rawReq = rawReq;

		this.content = new JSONObject(rawReq.substring(rawReq.indexOf("{")));
		
		// tipos
		switch (content.getJSONObject("operacion").getString("tipo")) {
			case "get":
				this.tipo = Peticion.GET;
				break;
			case "create":
				this.tipo = Peticion.CREATE;
				break;
			case "update":
				this.tipo = Peticion.UPDATE;
				break;
			case "delete":
				this.tipo = Peticion.DELETE;
				break;
			default:
				this.tipo = 400;
		}

		// apartado
		switch (content.getJSONObject("operacion").getString("apartado")) {
			case "x_clientes":
				this.apartado = Peticion.X_CLIENTES;
				break;
			case "x_pezas":
				this.apartado = Peticion.X_PEZAS;
				break;
			case "x_pedidos":
				this.apartado = Peticion.X_PEDIDOS;
				break;
			case "x_reparacions":
				this.apartado = Peticion.X_REPARACIONS;
				break;
			default:
				this.apartado = 400;
		}

		// datos
		this.datos = content.getJSONObject("operacion").getJSONObject("datos");

		Log.d("Creado objeto Petición");
		Log.d("Tipo: " + this.tipo + "; Apartado: " + this.apartado);
	}
	
	//#region Getters
	public String getRawReq() {
		return rawReq;
	}

	public int getTipo() {
		return tipo;
	}

	public int getApartado() {
		return apartado;
	}

	public JSONObject getContent() {
		return content;

		/*
		try {
			return (JSONObject) content.clone();
		} catch (CloneNotSupportedException e) {
			Log.e("No se puede clonar???");
			e.printStackTrace();
			throw new RuntimeException("Inclonable");
		}
		*/	
	}

	public JSONObject getDatos() {
		return datos;
	}
	//#endregion
}
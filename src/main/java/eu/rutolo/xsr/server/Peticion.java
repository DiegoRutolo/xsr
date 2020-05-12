package eu.rutolo.xsr.server;

import org.json.JSONObject;

import eu.rutolo.xsr.data.Log;

public class Peticion {
	// Codigos de tipo de petición
	public static final int GET = 1;
	public static final int CREATE = 2;
	public static final int UPDATE = 3;
	public static final int DELETE = 4;
	public static final int ERROR = 400;

	// Codigos de Apartado
	public static final int X_CLIENTES = 5;
	public static final int X_PEZAS = 6;
	public static final int X_PEDIDOS = 7;
	public static final int X_REPARACIONS = 8;

	private int tipo;
	private int apartado;
	private JSONObject content;

	public Peticion(String contentString) {
		Log.d("Recibido: ");
		Log.d(contentString);
		try {
			this.content = new JSONObject(contentString.substring(contentString.indexOf("{")));
		} catch (Exception e) {
			this.tipo = Peticion.ERROR;
			this.apartado = Peticion.ERROR;
			return;
		}
		
		// tipos
		String strTipo = "";
		try {
			strTipo = content.getJSONObject("operacion").getString("tipo");
		} catch (Exception e) {
			this.tipo = Peticion.ERROR;
			return;
		}

		switch (strTipo) {
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
				this.tipo = Peticion.ERROR;
				return;
		}

		// apartado
		String strApartado = "";
		try {
			strApartado = content.getJSONObject("operacion").getString("apartado");
		} catch (Exception e) {
			this.apartado = Peticion.ERROR;
			return;
		}
		switch (strApartado) {
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
				this.apartado = Peticion.ERROR;
				return;

		}
	}
	
	//#region Getters

	public int getTipo() {
		return tipo;
	}

	public int getApartado() {
		return apartado;
	}

	public JSONObject getContent() {
		return content;
	}

	public JSONObject getSelec() {
		try {
			return content.getJSONObject("operacion").getJSONObject("selec");
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	public JSONObject getDatos() {
		try {
			return content.getJSONObject("operacion").getJSONObject("datos");
		} catch (Exception e) {
			return new JSONObject();
		}
	}
	//#endregion
}
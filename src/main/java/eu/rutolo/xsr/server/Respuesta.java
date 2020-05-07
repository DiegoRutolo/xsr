package eu.rutolo.xsr.server;

import java.util.ArrayList;

import org.json.JSONObject;

public class Respuesta {

	private int code;
	private String codeStr;
	private int contentLength;
	private String content;
	
	private Respuesta() {}

	public static Respuesta getRespuesta(Peticion p, JSONObject content) {
		return getRespuesta(p.getTipo(), true, content);
	}

	public static Respuesta getRespuesta(Peticion p) {
		return getRespuesta(p.getTipo(), true);
	}
	public static Respuesta getRespuesta(int tipoPet, boolean exito) {
		return getRespuesta(tipoPet, exito, new JSONObject());
	}

	/**
	 * Método para crear objetos Respuesta.
	 * @param tipoPet	Tipo de petición
	 * @param exito		Si la petición se procesó correctamente
	 * @param content	Datos a devolver
	 * @return			Objeto Respuesta preparado para enviar al cliente.
	 */
	public static Respuesta getRespuesta(int tipoPet, boolean exito, JSONObject content) {
		Respuesta r = new Respuesta();

		if (exito) {
			switch (tipoPet) {
				case Peticion.GET:
				case Peticion.DELETE:
					r.code = 200;
					r.codeStr = "OK";
					r.content = content.toString();
					break;

				case Peticion.CREATE:
				case Peticion.UPDATE:
					r.code = 201;
					r.codeStr = "Created";
					r.content = "{}";
					break;

				default:
					break;
			}
		} else {
			r.code = 400;
			r.codeStr = "Bad Request";
			r.content = "";
		}

		r.content += "\n";
		r.contentLength = r.content.getBytes().length;
		return r;
	}

	public String[] getHeaders() {
		ArrayList<String> sb = new ArrayList<>();
		
		sb.add("HTTP/1.1 " + code + " " + codeStr);
		sb.add("Server: xsrd/"+Servidor.VERSION);
		sb.add("Content-Type: application/json");
		sb.add("Content-Length: " + contentLength);
		sb.add("");

		String[] t = new String[sb.size()];
		return sb.toArray(t);
	}

	public int getContentLength() {
		return contentLength;
	}

	public String getContent() {
		return content;
	}
}

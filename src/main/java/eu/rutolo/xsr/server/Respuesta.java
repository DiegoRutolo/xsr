package eu.rutolo.xsr.server;

import java.util.ArrayList;

import org.json.JSONObject;

public class Respuesta {

	private int code;
	private String codeStr;
	private int contentLength;
	private String content;
	
	private Respuesta() {}

	public static Respuesta getRespuesta(Peticion p, boolean exito) {
		return getRespuesta(p, exito, new JSONObject());
	}

	public static Respuesta getRespuesta(Peticion p, boolean exito, JSONObject content) {
		Respuesta r = new Respuesta();

		if (exito) {
			switch (p.getTipo()) {
				case Peticion.GET:
					r.code = 200;
					r.codeStr = "OK";
					r.content = content.toString();
					break;
				case Peticion.CREATE:
					r.code = 201;
					r.codeStr = "Created";
					r.content = "{}";
					break;
				case Peticion.UPDATE:
					
					break;
				case Peticion.DELETE:
					
					break;
				default:
					
					break;
			}
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
		sb.add("Connection: keep-alive");
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
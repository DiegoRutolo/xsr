package eu.rutolo.xsr.db;

public class Cliente {
	
	private int id;
	private String nome;
	private String tlf;
	private String email;
	private String notas;
	
	public Cliente(int id, String nome, String tlf, String email, String notas) {
		this.id = id;
		this.nome = nome;
		this.tlf = tlf;
		this.email = email;
		this.notas = notas;
	}

	//#region Getter y Setter
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTlf() {
		return this.tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
	//#endregion
}
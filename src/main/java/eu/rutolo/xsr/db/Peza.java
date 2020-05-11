package eu.rutolo.xsr.db;

import java.math.BigDecimal;

public class Peza {
	private int id;
	private String codigo;
	private String prov;
	private String nome;
	private String foto;
	private BigDecimal precio;
	private int cantidade;
	private String notas;

	public Peza(int id, String codigo, String prov, String nome, String foto, BigDecimal precio, int cantidade, String notas) {
		this.id = id;
		this.codigo = codigo;
		this.prov = prov;
		this.nome = nome;
		this.foto = foto;
		this.precio = precio;
		this.cantidade = cantidade;
		this.notas = notas;
	}

	//#region Getter y setter
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(String precio) {
		this.precio = new BigDecimal(precio);
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getCantidade() {
		return this.cantidade;
	}

	public void setCantidade(int cantidade) {
		this.cantidade = cantidade;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
	//#endregion
}
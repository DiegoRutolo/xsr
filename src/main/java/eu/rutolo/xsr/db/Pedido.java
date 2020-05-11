package eu.rutolo.xsr.db;

import java.math.BigDecimal;

public class Pedido {
	
	private int idCliente;
	private int idPeza;
	private BigDecimal pvp;
	private String estado;

	public Pedido(int idCliente, int idPeza, String pvp, String estado) {
		this(idCliente, idPeza, new BigDecimal(pvp), estado);
	}

	public Pedido(int idCliente, int idPeza, BigDecimal pvp, String estado) {
		this.idCliente = idCliente;
		this.idPeza = idPeza;
		this.pvp = pvp;
		this.estado = estado;
	}

	//#region Getters y Setters
	public int getIdCliente() {
		return this.idCliente;
	}

	public void setCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdPeza() {
		return this.idPeza;
	}

	public void setIdPeza(int idDPza) {
		this.idPeza = idDPza;
	}

	public BigDecimal getPvp() {
		return this.pvp;
	}

	public void setPvp(String pvp) {
		this.pvp = new BigDecimal(pvp);
	}

	public void setPvp(BigDecimal pvp) {
		this.pvp = pvp;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	//#endregion
}
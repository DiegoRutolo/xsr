package eu.rutolo.xsr.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Reparacion {
	private int id;
	private Date ini;
	private Date fin;
	private int nHoras;
	private boolean completa;
	private String causa;
	private String solucion;
	private BigDecimal pvp;
	private String notas;
	private int idCliente;
	private int[] idsPezas;

	public Reparacion(
		int id, Date ini, Date fin, int nHoras,
		boolean completa, String causa, String solucion, 
		BigDecimal pvp, String notas, int idCliente,
		int[] idsPezas
	){
		this.id = id;
		this.ini = ini;
		this.fin = fin;
		this.nHoras = nHoras;
		this.completa = completa;
		this.causa = causa;
		this.solucion = solucion;
		this.pvp = pvp;
		this.notas = notas;
		this.idCliente = idCliente;
		this.idsPezas = idsPezas;
	}

	//#region Getters y setters
	public int getId() {
		return id;
	}
	
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public int getNhoras() {
		return nHoras;
	}

	public void setNhoras(int nHoras) {
		this.nHoras = nHoras;
	}

	public boolean isCompleta() {
		return completa;
	}

	public void setCompleta(boolean completa) {
		this.completa = completa;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getSolucion() {
		return solucion;
	}

	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}

	public BigDecimal getPvp() {
		return pvp;
	}

	public void setPvp(BigDecimal pvp) {
		this.pvp = pvp;
	}

	public void setPvp(String pvp) {
		this.pvp = new BigDecimal(pvp);
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public void setCliente(Cliente c) {
		this.idCliente = c.getId();
	}

	public int[] getIdsPezas() {
		return idsPezas;
	}

	public void addIdPeza(int idPeza) {
		int[] oldIds = idsPezas;
		idsPezas = new int[oldIds.length+1];
		System.arraycopy(oldIds, 0, idsPezas, 0, oldIds.length);
		idsPezas[idsPezas.length-1] = idPeza;
	}

	public void setIdsPezas(int[] idsPezas) {
		this.idsPezas = idsPezas;
	}
	//#endregion
}

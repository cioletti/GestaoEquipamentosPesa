package com.gestaoequipamentos.bean;

public class GestorOSBean {
	
	private String numOS;
	private String modelo;
	private String cliente;
	private String serie;
	private String filial;
	private String dataNegociacao;
	private String dataAprovacao;
	private String codCliente;
	private String idCheckin;
	
	
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getFilial() {
		return filial;
	}
	public void setFilial(String filial) {
		this.filial = filial;
	}
	public String getDataNegociacao() {
		return dataNegociacao;
	}
	public void setDataNegociacao(String dataNegociacao) {
		this.dataNegociacao = dataNegociacao;
	}
	public String getDataAprovacao() {
		return dataAprovacao;
	}
	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}
	public String getNumOS() {
		return numOS;
	}
	public void setNumOS(String numOS) {
		this.numOS = numOS;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	public String getIdCheckin() {
		return idCheckin;
	}
	public void setIdCheckin(String idCheckin) {
		this.idCheckin = idCheckin;
	}
}

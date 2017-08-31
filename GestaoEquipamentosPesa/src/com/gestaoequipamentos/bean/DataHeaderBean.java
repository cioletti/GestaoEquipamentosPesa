package com.gestaoequipamentos.bean;

import java.util.Date;

public class DataHeaderBean {

//	private String _dateOpen;
//	private String _cliente;
//	private String _numOs;
//	private String _descricao;
//	private String _modelo;
//	private String _prazoPETS;
//	private String _filial;
	
//	private String _descricao;
	
	private String dateString;
	private String descricao;
	private Date data;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
}

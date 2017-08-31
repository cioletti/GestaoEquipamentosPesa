package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GePecas;

public class PecasDbsBean {
	private String codigo;
	private Long qtd;
	private String numPeca;
	private String nomePeca;
	private String sos;
	
	public PecasDbsBean() {
		
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Long getQtd() {
		return qtd;
	}
	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}
	public String getNumPeca() {
		return numPeca;
	}
	public void setNumPeca(String numPeca) {
		this.numPeca = numPeca;
	}
	public String getNomePeca() {
		return nomePeca;
	}
	public void setNomePeca(String nomePeca) {
		this.nomePeca = nomePeca;
	}
	public String getSos() {
		return sos;
	}
	public void setSos(String sos) {
		this.sos = sos;
	}
	public void fromBean (GePecas bean){
		setCodigo(bean.getSmcsCode());
		setQtd(bean.getQtd());
		setNumPeca(bean.getPartNo());
		setNomePeca(bean.getPartName());
		setSos("000");		
	}
}

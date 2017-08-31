package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeOperacao;


public class OperacaoBean {
	
	private String idSegmento;
	private Long id;
	private String numero;
	private String jbcd;
	private String jbcdStr;
	private String desricao;
	private String descricaoImportPecas;
	private String cptcd;
	private String idFuncionarioCriador;
	private String codErroDbs;
	private String msgErroDbs;
	
	
	public String getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getJbcd() {
		return jbcd;
	}
	public void setJbcd(String jbcd) {
		this.jbcd = jbcd;
	}
	public String getJbcdStr() {
		return jbcdStr;
	}
	public void setJbcdStr(String jbcdStr) {
		this.jbcdStr = jbcdStr;
	}
	public String getDesricao() {
		return desricao;
	}
	public void setDesricao(String desricao) {
		this.desricao = desricao;
	}
	public String getCptcd() {
		return cptcd;
	}
	public void setCptcd(String cptcd) {
		this.cptcd = cptcd;
	}
	public String getIdFuncionarioCriador() {
		return idFuncionarioCriador;
	}
	public void setIdFuncionarioCriador(String idFuncionarioCriador) {
		this.idFuncionarioCriador = idFuncionarioCriador;
	}
	public String getDescricaoImportPecas() {
		return descricaoImportPecas;
	}
	public void setDescricaoImportPecas(String descricaoImportPecas) {
		this.descricaoImportPecas = descricaoImportPecas;
	}
	public String getCodErroDbs() {
		return codErroDbs;
	}
	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}
	public String getMsgErroDbs() {
		return msgErroDbs;
	}
	public void setMsgErroDbs(String msgErroDbs) {
		this.msgErroDbs = msgErroDbs;
	}
	public void fromBean (GeOperacao operacao){
		setCptcd(operacao.getCptcd()+ " - " +operacao.getDescricaoCompCode());
		setDesricao(operacao.getDescricaoJbcd());
		setId(operacao.getId());
		setIdSegmento(String.valueOf(operacao.getIdSegmento().getId()));
		setJbcd(operacao.getJbcd());
		setNumero(operacao.getNumOperacao());
		setJbcdStr(operacao.getJbcd()+ " - " + operacao.getDescricaoJbcd().toUpperCase());
		setDescricaoImportPecas(operacao.getNumOperacao() + " - " + operacao.getDescricaoJbcd());
		setCodErroDbs(operacao.getCodErroDbs());
		setMsgErroDbs(operacao.getMsgDbs());
		
	}

}

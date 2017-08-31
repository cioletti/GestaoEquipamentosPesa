package com.gestaoequipamentos.bean;

import java.util.List;

public class DocSegOperBean {
		
	private String id;
	private String idSegmento;
	private String numOs;
	private String idOperacao;
	private String numDocto;
	private String codeErro;
	private String msgDbs;
	private String nomeFuncionario;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}
	public String getNumOs() {
		return numOs;
	}
	public void setNumOs(String numOs) {
		this.numOs = numOs;
	}
	public String getIdOperacao() {
		return idOperacao;
	}
	public void setIdOperacao(String idOperacao) {
		this.idOperacao = idOperacao;
	}
	public String getNumDocto() {
		return numDocto;
	}
	public void setNumDocto(String numDocto) {
		this.numDocto = numDocto;
	}
	public String getCodeErro() {
		return codeErro;
	}
	public void setCodeErro(String codeErro) {
		this.codeErro = codeErro;
	}
	public String getMsgDbs() {
		return msgDbs;
	}
	public void setMsgDbs(String msgDbs) {
		this.msgDbs = msgDbs;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

}

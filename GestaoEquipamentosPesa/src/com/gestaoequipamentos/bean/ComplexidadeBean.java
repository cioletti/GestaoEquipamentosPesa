package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeComplexidade;

public class ComplexidadeBean {
	
	private Long id;
	private String descricao;
	private String fator;
	private String sigla;
	private String descricaoSigla;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFator() {
		return fator;
	}
	public void setFator(String fator) {
		this.fator = fator;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricaoSigla() {
		return descricaoSigla;
	}
	public void setDescricaoSigla(String descricaoSigla) {
		this.descricaoSigla = descricaoSigla;
	}	
	public void fromBean (GeComplexidade complexidade){
		setId(complexidade.getId());
		setDescricao(complexidade.getDescricao());
		setSigla(complexidade.getSigla());
		setFator(String.valueOf(complexidade.getFator()));		
	}

}

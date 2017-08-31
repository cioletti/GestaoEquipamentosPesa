package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeLegendaProcessoOficina;


public class LegendaProcessoOficinaBean {
	
	private long id;
	private String descricao;
	private String sigla;
	private Long ordem;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Long getOrdem() {
		return ordem;
	}
	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}
	public void fromBean(GeLegendaProcessoOficina bean){
		setId(bean.getId());
		setDescricao(bean.getDescricao());
		setSigla(bean.getSigla());
		setOrdem(bean.getOrdem());
	}

}

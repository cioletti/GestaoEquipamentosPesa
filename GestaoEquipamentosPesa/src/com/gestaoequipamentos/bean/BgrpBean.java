package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeBgrp;


public class BgrpBean {
	
	private long id;
	private String descricao;
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
	
	public void fromBeanBgrp(GeBgrp bean){
		setId(bean.getId());
		setDescricao(bean.getDescricao());
		
	}

}

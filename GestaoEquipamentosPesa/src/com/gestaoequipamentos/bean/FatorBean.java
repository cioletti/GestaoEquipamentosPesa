package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeFator;

public class FatorBean {
	
	private Long id;
	private String valorInter;
	private String fatorUrgente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValorInter() {
		return valorInter;
	}
	public void setValorInter(String valorInter) {
		this.valorInter = valorInter;
	}
	public String getFatorUrgente() {
		return fatorUrgente;
	}
	public void setFatorUrgente(String fatorUrgente) {
		this.fatorUrgente = fatorUrgente;
	}
//	
//	public void fromBeanFator (GeFator fator){
//		setId(fator.getId());
//		setValorInter(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(fator.getValorInter())))));
//		setFatorUrgente(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(fator.getFatorUrgencia())))));
//		
//	}

}

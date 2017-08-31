package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeTipoFrete;

public class TipoFreteBean {

	private Long id;
	private String tipoFrete;
//	private String taxa;
//	private String freteMinimo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoFrete() {
		return tipoFrete;
	}
	public void setTipoFrete(String tipoFrete) {
		this.tipoFrete = tipoFrete;
	}
//	public String getTaxa() {
//		return taxa;
//	}
//	public void setTaxa(String taxa) {
//		this.taxa = taxa;
//	}
//	public String getFreteMinimo() {
//		return freteMinimo;
//	}
//	public void setFreteMinimo(String freteMinimo) {
//		this.freteMinimo = freteMinimo;
//	}
	public void fromBean(GeTipoFrete bean){
		setId(bean.getId());
		setTipoFrete(bean.getTipoFrete());
//		setTaxa(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,##0.00",bean.getTaxa().doubleValue()));
//		setFreteMinimo(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,##0.00",bean.getFreteMinimo().doubleValue()));		
	}

}

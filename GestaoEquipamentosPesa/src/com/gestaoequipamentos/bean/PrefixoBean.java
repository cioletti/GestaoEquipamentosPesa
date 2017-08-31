package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GePrefixo;

public class PrefixoBean {
	
	private Long id;
	private String descricao;
	private String valor;
	private String valorCusto;
	private Long idModelo;
	private String sigla;
	
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
	public Long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValorCusto() {
		return valorCusto;
	}
	public void setValorCusto(String valorCusto) {
		this.valorCusto = valorCusto;
	}
	public void fromBeanPrefixo(GePrefixo bean){
		setId(bean.getId());
		setDescricao(bean.getDescricao());	
		setValor(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,##0.00", bean.getValorDeVenda().doubleValue()));
		if(bean.getValorDeCusto() != null){
			setValorCusto(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,##0.00", bean.getValorDeCusto().doubleValue()));
		}
		setIdModelo(bean.getIdModelo().getIdArv());//mudara aqui
	}	
	public void toBeanPrefixo (GePrefixo entity){
		entity.setId(getId());
		entity.setDescricao(getDescricao());		
	}
}

package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.PmpClienteInter;


public class ClienteInterBean {

	private Long id;
	private Long fkFilial;
	private String customerNum;
	private String searchKey;
	private String descricao;
	private Long cod;
	private String filialDesc;	
	private String clienteGarantia;	
	
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
		
	public Long getFkFilial() {
		return fkFilial;
	}
	public void setFkFilial(Long fkFilial) {
		this.fkFilial = fkFilial;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public Long getCod() {
		return cod;
	}
	public void setCod(Long cod) {
		this.cod = cod;
	}
	public String getFilialDesc() {
		return filialDesc;
	}
	public void setFilialDesc(String filialDesc) {
		this.filialDesc = filialDesc;
	}
	public String getClienteGarantia() {
		return clienteGarantia;
	}
	public void setClienteGarantia(String clienteGarantia) {
		this.clienteGarantia = clienteGarantia;
	}
	public void formBean(PmpClienteInter clienteInter, String filial){
		setId(clienteInter.getId().longValue());
		setFkFilial(clienteInter.getFkFilial());
		setDescricao(clienteInter.getDescricao());
		setSearchKey(clienteInter.getSearchKey());
		//setCustomerNum((clienteInter.getCustomerNum() != null) ? clienteInter.getCustomerNum().replace("XX", filial) : clienteInter.getCustomerNum());
		setCustomerNum(clienteInter.getCustomerNum());
		setCod(clienteInter.getCod());
		setFilialDesc(clienteInter.getFilialDesc());
		if(clienteInter.getClienteGarantia() != null){
			setClienteGarantia((clienteInter.getClienteGarantia().equals("S"))? "SIM" : "NÃO");			
		}
	}
	
	public void toBean(PmpClienteInter entity){
		entity.setFkFilial(getFkFilial());
		entity.setDescricao(getDescricao().toUpperCase());
		entity.setSearchKey(getSearchKey());
		entity.setCustomerNum(getCustomerNum().toUpperCase());
		entity.setCod(getCod());
		entity.setFilialDesc(getFilialDesc());
		entity.setClienteGarantia(getClienteGarantia());
	}

}

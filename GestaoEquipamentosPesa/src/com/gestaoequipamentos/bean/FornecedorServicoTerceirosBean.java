package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GeFornecedorServTerceiros;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GePrefixo;

public class FornecedorServicoTerceirosBean {
	private Long id;
	private String descricao;
	private String endereco;
	private String telefone;
	private String email;
	private String cnpj;
	private String codigo;
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
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void fromBean (GeFornecedorServTerceiros fornecedor){
		
		setId(fornecedor.getId());
		setDescricao(fornecedor.getDescricao());
		setEndereco(fornecedor.getEndereco());
		setTelefone(fornecedor.getTelefone());
		setEmail(fornecedor.getEmail());
		setCnpj(fornecedor.getCnpj());
		setCodigo(fornecedor.getCodigo());
	}
	
	public void toBean (GeFornecedorServTerceiros fornecedor){
		fornecedor.setId(getId());
		fornecedor.setDescricao(getDescricao());	
		fornecedor.setEndereco(getEndereco());
		fornecedor.setEmail(getEmail());
		fornecedor.setTelefone(getTelefone());
		fornecedor.setCnpj(getCnpj());
		fornecedor.setCodigo(getCodigo());
	}

}

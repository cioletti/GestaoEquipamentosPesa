package com.gestaoequipamentos.bean;

public class GestorRentalBean {
	
	private Long id;
	private String nome;
	private String email;
	private Long filial;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getFilial() {
		return filial;
	}
	public void setFilial(Long filial) {
		this.filial = filial;
	}

}

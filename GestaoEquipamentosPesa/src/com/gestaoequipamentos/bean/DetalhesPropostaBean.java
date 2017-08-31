package com.gestaoequipamentos.bean;

import java.util.ArrayList;
import java.util.List;



public class DetalhesPropostaBean {
		private Long id;
		private List<SegmentoBean> segmentoList = new ArrayList<SegmentoBean>();
		private String formaEntregaProposta;
		private String aosCuidados;
		private String telefone;
		private String email;
		private String objetoProposta;
		private String maquina;
		private String modelo;
		private String serie;
		private String observacao;
		private String condicaoPagamento;
		private Long prazoEntrega;
		private String validadeProposta;
		private String aprovacaoPropostaServico;
		private String orcamentista;
		private String imprimirSubTributaria;
		private String isFindSubTributaria;
		private String fatorUrgencia;
		
		public List<SegmentoBean> getSegmentoList() {
			return segmentoList;
		}
		public void setSegmentoList(List<SegmentoBean> segmentoList) {
			this.segmentoList = segmentoList;
		}
		public String getAosCuidados() {
			return aosCuidados;
		}
		public void setAosCuidados(String aosCuidados) {
			this.aosCuidados = aosCuidados;
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
		public String getObjetoProposta() {
			return objetoProposta;
		}
		public void setObjetoProposta(String objetoProposta) {
			this.objetoProposta = objetoProposta;
		}
		public String getMaquina() {
			return maquina;
		}
		public void setMaquina(String maquina) {
			this.maquina = maquina;
		}
		public String getModelo() {
			return modelo;
		}
		public void setModelo(String modelo) {
			this.modelo = modelo;
		}
		public String getSerie() {
			return serie;
		}
		public void setSerie(String serie) {
			this.serie = serie;
		}
		
		public String getCondicaoPagamento() {
			return condicaoPagamento;
		}
		public void setCondicaoPagamento(String condicaoPagamento) {
			this.condicaoPagamento = condicaoPagamento;
		}
		public Long getPrazoEntrega() {
			return prazoEntrega;
		}
		public void setPrazoEntrega(Long prazoEntrega) {
			this.prazoEntrega = prazoEntrega;
		}
		public String getValidadeProposta() {
			return validadeProposta;
		}
		public void setValidadeProposta(String validadeProposta) {
			this.validadeProposta = validadeProposta;
		}
		public String getAprovacaoPropostaServico() {
			return aprovacaoPropostaServico;
		}
		public void setAprovacaoPropostaServico(String aprovacaoPropostaServico) {
			this.aprovacaoPropostaServico = aprovacaoPropostaServico;
		}
		public String getOrcamentista() {
			return orcamentista;
		}
		public void setOrcamentista(String orcamentista) {
			this.orcamentista = orcamentista;
		}
		public String getFormaEntregaProposta() {
			return formaEntregaProposta;
		}
		public void setFormaEntregaProposta(String formaEntregaProposta) {
			this.formaEntregaProposta = formaEntregaProposta;
		}
		public String getObservacao() {
			return observacao;
		}
		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getImprimirSubTributaria() {
			return imprimirSubTributaria;
		}
		public void setImprimirSubTributaria(String imprimirSubTributaria) {
			this.imprimirSubTributaria = imprimirSubTributaria;
		}
		public String getIsFindSubTributaria() {
			return isFindSubTributaria;
		}
		public void setIsFindSubTributaria(String isFindSubTributaria) {
			this.isFindSubTributaria = isFindSubTributaria;
		}
		public String getFatorUrgencia() {
			return fatorUrgencia;
		}
		public void setFatorUrgencia(String fatorUrgencia) {
			this.fatorUrgencia = fatorUrgencia;
		}
	
}

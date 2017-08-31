package com.gestaoequipamentos.bean;

import java.util.ArrayList;
import java.util.List;

public class ServicoTerceirosBean {
	private Long id;
	private Long idSegmento;
	private Long idTipoServicoTerceiros;
	private String numeroSegmento;
	private String jobControl;
	private String numeroOs;
	private String valor;
	private String descricao;
	private String qtdDiasProposta;
	private Long qtdServTerceiros;
	private String obs;
	private Long idStatusServTerceiros;
	private String statusServTerceiros;
	private String siglaStatusServTerceiros;
	private String descricaoFornecedor;
	private long idFornecedor;
	private List<FornecedorServicoTerceirosBean> fornecedorList;
	private Long idNaturezaOperacao;
	private List<ItemBean> itemList;
	
	private String codigoFornecedor;
	private String enviadoPor;
	private String dataAbertura;
	private String nomeFilial;
	private String cnpj;
	private String naturezaOperacao;
	private String arquivoDetalhe;
	private String localServico;
	private String descricaoLocalServico;
	private String modelo;
	private String serie;
	private String obsEnvioNotaFiscal;
	private String urlStatus;
	private String statusServico;
	private String garantia;
	private String statusNegociacao;
	private String statusNegociacaoConsultor;
	private String dataPrevisaoEntrega;
	
	
	
	public String getDataPrevisaoEntrega() {
		return dataPrevisaoEntrega;
	}

	public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) {
		this.dataPrevisaoEntrega = dataPrevisaoEntrega;
	}

	public ServicoTerceirosBean() {
		fornecedorList = new ArrayList<FornecedorServicoTerceirosBean>();
	}
	
	public String getStatusServTerceiros() {
		return statusServTerceiros;
	}
	public void setStatusServTerceiros(String statusServTerceiros) {
		this.statusServTerceiros = statusServTerceiros;
	}
	public String getSiglaStatusServTerceiros() {
		return siglaStatusServTerceiros;
	}
	public void setSiglaStatusServTerceiros(String siglaStatusServTerceiros) {
		this.siglaStatusServTerceiros = siglaStatusServTerceiros;
	}
	public String getDescricaoFornecedor() {
		return descricaoFornecedor;
	}
	public void setDescricaoFornecedor(String descricaoFornecedor) {
		this.descricaoFornecedor = descricaoFornecedor;
	}
	public long getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public Long getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(Long idSegmento) {
		this.idSegmento = idSegmento;
	}
	public Long getIdTipoServicoTerceiros() {
		return idTipoServicoTerceiros;
	}
	public void setIdTipoServicoTerceiros(Long idTipoServicoTerceiros) {
		this.idTipoServicoTerceiros = idTipoServicoTerceiros;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getQtdDiasProposta() {
		return qtdDiasProposta;
	}
	public void setQtdDiasProposta(String qtdDiasProposta) {
		this.qtdDiasProposta = qtdDiasProposta;
	}
	public Long getQtdServTerceiros() {
		return qtdServTerceiros;
	}
	public void setQtdServTerceiros(Long qtdServTerceiros) {
		this.qtdServTerceiros = qtdServTerceiros;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Long getIdStatusServTerceiros() {
		return idStatusServTerceiros;
	}
	public void setIdStatusServTerceiros(Long idStatusServTerceiros) {
		this.idStatusServTerceiros = idStatusServTerceiros;
	}
	public String getNumeroSegmento() {
		return numeroSegmento;
	}
	public void setNumeroSegmento(String numeroSegmento) {
		this.numeroSegmento = numeroSegmento;
	}
	public String getJobControl() {
		return jobControl;
	}
	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}
	public String getNumeroOs() {
		return numeroOs;
	}
	public void setNumeroOs(String numeroOs) {
		this.numeroOs = numeroOs;
	}
	public List<FornecedorServicoTerceirosBean> getFornecedorList() {
		return fornecedorList;
	}
	public void setFornecedorList(List<FornecedorServicoTerceirosBean> fornecedorList) {
		this.fornecedorList = fornecedorList;
	}

	public Long getIdNaturezaOperacao() {
		return idNaturezaOperacao;
	}

	public void setIdNaturezaOperacao(Long idNaturezaOperacao) {
		this.idNaturezaOperacao = idNaturezaOperacao;
	}

	public List<ItemBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemBean> itemList) {
		this.itemList = itemList;
	}

	public String getCodigoFornecedor() {
		return codigoFornecedor;
	}

	public void setCodigoFornecedor(String codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}

	public String getEnviadoPor() {
		return enviadoPor;
	}

	public void setEnviadoPor(String enviadoPor) {
		this.enviadoPor = enviadoPor;
	}

	public String getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public String getArquivoDetalhe() {
		return arquivoDetalhe;
	}

	public void setArquivoDetalhe(String arquivoDetalhe) {
		this.arquivoDetalhe = arquivoDetalhe;
	}

	public String getLocalServico() {
		return localServico;
	}

	public void setLocalServico(String localServico) {
		this.localServico = localServico;
	}

	public String getDescricaoLocalServico() {
		return descricaoLocalServico;
	}

	public void setDescricaoLocalServico(String descricaoLocalServico) {
		this.descricaoLocalServico = descricaoLocalServico;
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

	public String getObsEnvioNotaFiscal() {
		return obsEnvioNotaFiscal;
	}

	public void setObsEnvioNotaFiscal(String obsEnvioNotaFiscal) {
		this.obsEnvioNotaFiscal = obsEnvioNotaFiscal;
	}

	public String getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(String urlStatus) {
		this.urlStatus = urlStatus;
	}

	public String getStatusServico() {
		return statusServico;
	}

	public void setStatusServico(String statusServico) {
		this.statusServico = statusServico;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getStatusNegociacao() {
		return statusNegociacao;
	}

	public void setStatusNegociacao(String statusNegociacao) {
		this.statusNegociacao = statusNegociacao;
	}

	public String getStatusNegociacaoConsultor() {
		return statusNegociacaoConsultor;
	}

	public void setStatusNegociacaoConsultor(String statusNegociacaoConsultor) {
		this.statusNegociacaoConsultor = statusNegociacaoConsultor;
	}

}

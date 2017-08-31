package com.gestaoequipamentos.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.entity.TwFuncionario;

public class SituacaoOSBean {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private Long id;
	private String numeroOS;
	private String dataChegada;
	private String estimateByDataChegada;
	private String dataEntregaPedidos;
	private String estimateByDataEntregaPedidos;
	private String dataOrcamento;
	private String estimateByDataOrcamento;
	private String dataAprovacao;
	private String estimateByDataAprovacao;
	private String dataPrevisaoEntrega;
	private String estimateByDataPrevisaoEntrega;
	private String dataTerminoServico;
	private String estimateByDataTerminoServico;
	private String dataFaturamento;
	private String estimateByDataFaturamento;
	private Long filial;
	private Long idCheckin;
	private String isCheckout;
	private String tipoRejeicao;
	private String obs;
	private String statusOs;
	private BigDecimal valorTotalPecas;
	private BigDecimal valorMaoDeObra;
	private String cliente;
	private String jobControl;
	private String modelo;
	private String serie;
	private String tipoCliente;
	
	
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getJobControl() {
		return jobControl;
	}
	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumeroOS() {
		return numeroOS;
	}
	public void setNumeroOS(String numeroOS) {
		this.numeroOS = numeroOS;
	}
	public String getDataChegada() {
		return dataChegada;
	}
	public void setDataChegada(String dataChegada) {
		this.dataChegada = dataChegada;
	}
	public String getDataEntregaPedidos() {
		return dataEntregaPedidos;
	}
	public void setDataEntregaPedidos(String dataEntregaPedidos) {
		this.dataEntregaPedidos = dataEntregaPedidos;
	}
	public String getDataOrcamento() {
		return dataOrcamento;
	}
	public void setDataOrcamento(String dataOrcamento) {
		this.dataOrcamento = dataOrcamento;
	}
	public String getDataAprovacao() {
		return dataAprovacao;
	}
	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}
	public String getDataPrevisaoEntrega() {
		return dataPrevisaoEntrega;
	}
	public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) {
		this.dataPrevisaoEntrega = dataPrevisaoEntrega;
	}
	public String getDataTerminoServico() {
		return dataTerminoServico;
	}
	public void setDataTerminoServico(String dataTerminoServico) {
		this.dataTerminoServico = dataTerminoServico;
	}
	public String getDataFaturamento() {
		return dataFaturamento;
	}
	public void setDataFaturamento(String dataFaturamento) {
		this.dataFaturamento = dataFaturamento;
	}
	public Long getFilial() {
		return filial;
	}
	public void setFilial(Long filial) {
		this.filial = filial;
	}
	public Long getIdCheckin() {
		return idCheckin;
	}
	public void setIdCheckin(Long idCheckin) {
		this.idCheckin = idCheckin;
	}
	public String getIsCheckout() {
		return isCheckout;
	}
	public void setIsCheckout(String isCheckout) {
		this.isCheckout = isCheckout;
	}
	public String getTipoRejeicao() {
		return tipoRejeicao;
	}
	public void setTipoRejeicao(String tipoRejeicao) {
		this.tipoRejeicao = tipoRejeicao;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getStatusOs() {
		return statusOs;
	}
	public void setStatusOs(String statusOs) {
		this.statusOs = statusOs;
	}
	public String getEstimateByDataChegada() {
		return estimateByDataChegada;
	}
	public void setEstimateByDataChegada(String estimateByDataChegada) {
		this.estimateByDataChegada = estimateByDataChegada;
	}
	public String getEstimateByDataEntregaPedidos() {
		return estimateByDataEntregaPedidos;
	}
	public void setEstimateByDataEntregaPedidos(String estimateByDataEntregaPedidos) {
		this.estimateByDataEntregaPedidos = estimateByDataEntregaPedidos;
	}
	public String getEstimateByDataOrcamento() {
		return estimateByDataOrcamento;
	}
	public void setEstimateByDataOrcamento(String estimateByDataOrcamento) {
		this.estimateByDataOrcamento = estimateByDataOrcamento;
	}
	public String getEstimateByDataAprovacao() {
		return estimateByDataAprovacao;
	}
	public void setEstimateByDataAprovacao(String estimateByDataAprovacao) {
		this.estimateByDataAprovacao = estimateByDataAprovacao;
	}
	public String getEstimateByDataPrevisaoEntrega() {
		return estimateByDataPrevisaoEntrega;
	}
	public void setEstimateByDataPrevisaoEntrega(
			String estimateByDataPrevisaoEntrega) {
		this.estimateByDataPrevisaoEntrega = estimateByDataPrevisaoEntrega;
	}
	public String getEstimateByDataTerminoServico() {
		return estimateByDataTerminoServico;
	}
	public void setEstimateByDataTerminoServico(String estimateByDataTerminoServico) {
		this.estimateByDataTerminoServico = estimateByDataTerminoServico;
	}
	public String getEstimateByDataFaturamento() {
		return estimateByDataFaturamento;
	}
	public void setEstimateByDataFaturamento(String estimateByDataFaturamento) {
		this.estimateByDataFaturamento = estimateByDataFaturamento;
	}
	public BigDecimal getValorTotalPecas() {
		return valorTotalPecas;
	}
	public void setValorTotalPecas(BigDecimal valorTotalPecas) {
		this.valorTotalPecas = valorTotalPecas;
	}
	public BigDecimal getValorMaoDeObra() {
		return valorMaoDeObra;
	}
	public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
		this.valorMaoDeObra = valorMaoDeObra;
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
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public void fromBean(GeSituacaoOs situacaoOs, EntityManager manager) {
				
		setId(situacaoOs.getId());
		setNumeroOS(situacaoOs.getNumeroOs());
		setDataChegada((situacaoOs.getDataChegada() != null)? dateFormat.format(situacaoOs.getDataChegada()) : "");
		setDataEntregaPedidos((situacaoOs.getDataEntregaPedidos() != null)? dateFormat.format(situacaoOs.getDataEntregaPedidos()) : "");
		setDataOrcamento((situacaoOs.getDataOrcamento() != null) ? dateFormat.format(situacaoOs.getDataOrcamento()) : "");
		setDataAprovacao((situacaoOs.getDataAprovacao() != null) ? dateFormat.format(situacaoOs.getDataAprovacao()) : "");
		setDataPrevisaoEntrega((situacaoOs.getDataPrevisaoEntrega() != null) ? dateFormat.format(situacaoOs.getDataPrevisaoEntrega()) : "");
		setDataTerminoServico((situacaoOs.getDataTerminoServico() != null) ? dateFormat.format(situacaoOs.getDataTerminoServico()) : "");
		setDataFaturamento((situacaoOs.getDataFaturamento() != null) ? dateFormat.format(situacaoOs.getDataFaturamento()) : "");
		setFilial(situacaoOs.getFilial());
		setIdCheckin(situacaoOs.getIdCheckin().getId());
		setIsCheckout(situacaoOs.getIsCheckOut());
		setTipoRejeicao(situacaoOs.getTipoRejeicao());
		setObs(situacaoOs.getObs());
		
		if(situacaoOs.getIdFuncDataChegada() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataChegada());
			estimateByDataChegada = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataEntregaPedidos() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataEntregaPedidos());
			estimateByDataEntregaPedidos = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataOrcamento() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataOrcamento());
			estimateByDataOrcamento = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataAprovacao() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataAprovacao());
			estimateByDataAprovacao = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataPrevisaoEntrega() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataPrevisaoEntrega());
			estimateByDataPrevisaoEntrega = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataTerminoServico() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataTerminoServico());
			estimateByDataTerminoServico = funcionario.getEstimateBy();
		}
		if(situacaoOs.getIdFuncDataFaturamento() != null){
			TwFuncionario funcionario = manager.find(TwFuncionario.class, situacaoOs.getIdFuncDataFaturamento());
			estimateByDataFaturamento = funcionario.getEstimateBy();
		}		
	}
}

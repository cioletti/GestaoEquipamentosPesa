package com.gestaoequipamentos.bean;

import java.util.List;

import com.gestaoequipamentos.entity.GePreco;

public class PrecoBean {
	
	private Long id;
	private String descricaoCompCode;	
	private String jobCode;
	private String jobCodeDescricao;
	private String compCode;
	private Long idPrefixo;
	private String prefixoStr;
	private Long idModelo;
	private String modeloStr;
	private String qtdHoras;
	private Integer qtdItens;
	private Long idComplexidade;
	private String complexidadeStr;
	private String descricaoJobCode;
	private Long idJobCode;
	private String codCliente;
	private List<PrecoBean> orcamentoList;
	private String isUrgente;
	private String valor;
	private String descricaoServico;
	
	
	
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPrefixo() {
		return idPrefixo;
	}
	public void setIdPrefixo(Long idPrefixo) {
		this.idPrefixo = idPrefixo;
	}
	public Long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}
	public String getPrefixoStr() {
		return prefixoStr;
	}
	public void setPrefixoStr(String prefixoStr) {
		this.prefixoStr = prefixoStr;
	}
	public String getModeloStr() {
		return modeloStr;
	}
	public void setModeloStr(String modeloStr) {
		this.modeloStr = modeloStr;
	}
	public String getDescricaoCompCode() {
		return descricaoCompCode;
	}
	public void setDescricaoCompCode(String descricaoCompCode) {
		this.descricaoCompCode = descricaoCompCode;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getQtdHoras() {
		return qtdHoras;
	}
	public void setQtdHoras(String qtdHoras) {
		this.qtdHoras = qtdHoras;
	}
	public Long getIdComplexidade() {
		return idComplexidade;
	}
	public void setIdComplexidade(Long idComplexidade) {
		this.idComplexidade = idComplexidade;
	}
	public String getComplexidadeStr() {
		return complexidadeStr;
	}
	public void setComplexidadeStr(String complexidadeStr) {
		this.complexidadeStr = complexidadeStr;
	}
	public String getDescricaoJobCode() {
		return descricaoJobCode;
	}
	public void setDescricaoJobCode(String descricaoJobCode) {
		this.descricaoJobCode = descricaoJobCode;
	}
	public Long getIdJobCode() {
		return idJobCode;
	}
	public void setIdJobCode(Long idJobCode) {
		this.idJobCode = idJobCode;
	}
	public String getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	public String getJobCodeDescricao() {
		return jobCodeDescricao;
	}
	public void setJobCodeDescricao(String jobCodeDescricao) {
		this.jobCodeDescricao = jobCodeDescricao;
	}
	public List<PrecoBean> getOrcamentoList() {
		return orcamentoList;
	}
	public void setOrcamentoList(List<PrecoBean> orcamentoList) {
		this.orcamentoList = orcamentoList;
	}
	public String getIsUrgente() {
		return isUrgente;
	}
	public void setIsUrgente(String isUrgente) {
		this.isUrgente = isUrgente;
	}
	public Integer getQtdItens() {
		return qtdItens;
	}
	public void setQtdItens(Integer qtdItens) {
		this.qtdItens = qtdItens;
	}
	public String getDescricaoServico() {
		return descricaoServico;
	}
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}
	public void fromBean(GePreco bean){
		setId(bean.getId().longValue());
		setJobCode(bean.getJobCode());
		setCompCode(bean.getCompCode());
		setDescricaoCompCode(bean.getCompCode() + " - " + bean.getDescricaoCompCode());
		setQtdHoras(String.valueOf(bean.getQtdHoras()));
		setComplexidadeStr(bean.getIdComplexidade().getDescricao());
		setIdComplexidade(bean.getIdComplexidade().getId());
		setDescricaoJobCode(bean.getDescricaoJobCode());
		setDescricaoServico(bean.getDescricaoServico());
		//setComplexidadeStr(bean.getIdComplexidade().getDescricao());
		//setIdComplexidade(bean.getIdComplexidade().getId());
//		setHmVenda(String.valueOf(ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(bean.getHmVenda())))));
//		setHmCusto(String.valueOf(ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(bean.getHmCusto())))));	
		setIdModelo(bean.getIdModelo().getIdArv());
		setIdPrefixo(bean.getIdPrefixo().getId().longValue());
		setModeloStr(bean.getIdModelo().getDescricao());
		setPrefixoStr(bean.getIdPrefixo().getDescricao());
		setJobCodeDescricao(bean.getJobCode()+"-"+bean.getDescricaoJobCode());
		setQtdItens(1);
		
	}
	public void toBean(GePreco preco){
		preco.setCompCode (getCompCode());
		preco.setDescricaoCompCode(getDescricaoCompCode());
		//preco.setIdComplexidade(preco.getIdComplexidade());
		preco.setJobCode(getJobCode());
		preco.setQtdHoras (getQtdHoras());	
		preco.setDescricaoJobCode(getDescricaoJobCode());
		preco.setDescricaoServico(getDescricaoServico());
//		preco.setDescricao(getDescricao());
//		preco.setHmCusto(BigDecimal.valueOf(Double.valueOf(getHmCusto().replace(".", "").replace(",", "."))));
//		preco.setHmVenda(BigDecimal.valueOf(Double.valueOf(getHmVenda().replace(".", "").replace(",", "."))));		
		//preco.setIdModelo(preco.getIdModelo());
		//preco.setIdPrefixo(preco.getIdPrefixo());
	}

}

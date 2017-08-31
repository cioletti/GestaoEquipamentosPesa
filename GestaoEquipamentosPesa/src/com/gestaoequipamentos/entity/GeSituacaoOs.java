/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RDR
 */
@Entity
@Table(name = "GE_SITUACAO_OS")

public class GeSituacaoOs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)  
    private Long id;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "DATA_CHEGADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataChegada;
    @Column(name = "DATA_ORCAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOrcamento;
    @Column(name = "DATA_ENTREGA_PEDIDOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntregaPedidos;
    @Column(name = "DATA_APROVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAprovacao;
    @Column(name = "DATA_PREVISAO_ENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevisaoEntrega;
    @Column(name = "DATA_TERMINO_SERVICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTerminoServico;
    @Column(name = "DATA_FATURAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFaturamento;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "IS_CHECK_OUT")
    private String isCheckOut;
    @Column(name = "TIPO_REJEICAO")
    private String tipoRejeicao;
    @Column(name = "ID_FUNC_DATA_CHEGADA")
    private String idFuncDataChegada;
    @Column(name = "ID_FUNC_DATA_ENTREGA_PEDIDOS")
    private String idFuncDataEntregaPedidos;
    @Column(name = "ID_FUNC_DATA_ORCAMENTO")
    private String idFuncDataOrcamento;
    @Column(name = "ID_FUNC_DATA_APROVACAO")
    private String idFuncDataAprovacao;
    @Column(name = "ID_FUNC_DATA_PREVISAO_ENTREGA")
    private String idFuncDataPrevisaoEntrega;
    @Column(name = "ID_FUNC_DATA_TERMINO_SERVICO")
    private String idFuncDataTerminoServico;
    @Column(name = "ID_FUNC_DATA_FATURAMENTO")
    private String idFuncDataFaturamento;
    @Column(name = "OBS")
    private String obs;
    @Column(name = "ENCERRAR_AUTOMATICA")
    private String encerrarAutomatica;
    @JoinColumn(name = "ID_CHECKIN", referencedColumnName = "ID")
    @ManyToOne
    private GeCheckIn idCheckin;
    
    @Column(name = "MO_MISC_DESL")
    private BigDecimal moMiscDesl;
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Column(name = "VALOR_TOTAL")
    private BigDecimal valorTotal;
    @Column(name = "CONDICAO_PAGAMENTO")
    private String condicaoPagamento;
    @Column(name = "PROPOSTA_NUMERO")
    private String propostaNumero;
    @Column(name = "AUTORIZADO_POR")
    private String autorizadoPor;
    @Column(name = "ORDEM_DE_COMPRA")
    private String ordemDeCompra;
    @Column(name = "NOTES_PREENCHIDO_POR")
    private String notesPreenchidoPor;
    @Column(name = "OBS_NF")
    private String obsNf;
    @Column(name = "IS_SEND_DATA_FATURAMENTO")
    private String isSendDataFaturamento;
    
    @Column(name = "ORDEM_COMPRA_PECA")
    private String ordemCompraPeca;
    @Column(name = "ORDEM_COMPRA_SERVICO")
    private String ordemCompraServico;
    @Column(name = "ESTABELECIMENTO_CREDOR_PECAS")
    private Long estabelecimentoCredorPecas;
    @Column(name = "ESTABELECIMENTO_CREDOR_SERVICOS")
    private Long estabelecimentoCredorServicos;
    @Column(name = "CONDICAO_PAGAMENTO_PECAS")
    private String condicaoPagamentoPecas;
    @Column(name = "CONDICAO_PAGAMENTO_SERVICOS")
    private String condicaoPagamentoServicos;
    @Column(name = "DESCONTO_PORC_PECAS")
    private BigDecimal descontoPorcPecas;
    @Column(name = "DESCONTO_PORC_SERVICOS")
    private BigDecimal descontoPorcServicos;
    @Column(name = "DESCONTO_VALOR_PECAS")
    private BigDecimal descontoValorPecas;
    @Column(name = "DESCONTO_VALOR_SERVICOS")
    private BigDecimal descontoValorServicos;
    @Column(name = "AUTOMATICA_FATURADA")
    private String automaticaFaturada;
    @Column(name = "OBS_PECA")
    private String obsPeca;
    @Column(name = "OBS_SERVICO")
    private String obsServico;
    @Column(name = "CONDICAO_ESPECECIAL")
    private String condicaoEspececial;
    
    @Column(name = "OBSERVACAO_DESCONTO")
    private String observacaoDesconto;
    
    
    
    

    public GeSituacaoOs() {
    }

    public GeSituacaoOs(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Date getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(Date dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public Date getDataEntregaPedidos() {
        return dataEntregaPedidos;
    }

    public void setDataEntregaPedidos(Date dataEntregaPedidos) {
        this.dataEntregaPedidos = dataEntregaPedidos;
    }

    public Date getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(Date dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Date getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }

    public void setDataPrevisaoEntrega(Date dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    public Date getDataTerminoServico() {
        return dataTerminoServico;
    }

    public void setDataTerminoServico(Date dataTerminoServico) {
        this.dataTerminoServico = dataTerminoServico;
    }

    public Date getDataFaturamento() {
        return dataFaturamento;
    }

    public void setDataFaturamento(Date dataFaturamento) {
        this.dataFaturamento = dataFaturamento;
    }

    public Long getFilial() {
        return filial;
    }

    public void setFilial(Long filial) {
        this.filial = filial;
    }

    public String getIsCheckOut() {
        return isCheckOut;
    }

    public void setIsCheckOut(String isCheckOut) {
        this.isCheckOut = isCheckOut;
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

    public GeCheckIn getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(GeCheckIn idCheckin) {
        this.idCheckin = idCheckin;
    }

    public String getIdFuncDataChegada() {
		return idFuncDataChegada;
	}

	public void setIdFuncDataChegada(String idFuncDataChegada) {
		this.idFuncDataChegada = idFuncDataChegada;
	}

	public String getIdFuncDataEntregaPedidos() {
		return idFuncDataEntregaPedidos;
	}

	public void setIdFuncDataEntregaPedidos(String idFuncDataEntregaPedidos) {
		this.idFuncDataEntregaPedidos = idFuncDataEntregaPedidos;
	}

	public String getIdFuncDataOrcamento() {
		return idFuncDataOrcamento;
	}

	public void setIdFuncDataOrcamento(String idFuncDataOrcamento) {
		this.idFuncDataOrcamento = idFuncDataOrcamento;
	}

	public String getIdFuncDataAprovacao() {
		return idFuncDataAprovacao;
	}

	public void setIdFuncDataAprovacao(String idFuncDataAprovacao) {
		this.idFuncDataAprovacao = idFuncDataAprovacao;
	}

	public String getIdFuncDataPrevisaoEntrega() {
		return idFuncDataPrevisaoEntrega;
	}

	public void setIdFuncDataPrevisaoEntrega(String idFuncDataPrevisaoEntrega) {
		this.idFuncDataPrevisaoEntrega = idFuncDataPrevisaoEntrega;
	}

	public String getIdFuncDataTerminoServico() {
		return idFuncDataTerminoServico;
	}

	public void setIdFuncDataTerminoServico(String idFuncDataTerminoServico) {
		this.idFuncDataTerminoServico = idFuncDataTerminoServico;
	}

	public String getIdFuncDataFaturamento() {
		return idFuncDataFaturamento;
	}

	public void setIdFuncDataFaturamento(String idFuncDataFaturamento) {
		this.idFuncDataFaturamento = idFuncDataFaturamento;
	}

	public String getEncerrarAutomatica() {
		return encerrarAutomatica;
	}

	public void setEncerrarAutomatica(String encerrarAutomatica) {
		this.encerrarAutomatica = encerrarAutomatica;
	}

	public BigDecimal getMoMiscDesl() {
		return moMiscDesl;
	}

	public void setMoMiscDesl(BigDecimal moMiscDesl) {
		this.moMiscDesl = moMiscDesl;
	}

	public BigDecimal getValorPecas() {
		return valorPecas;
	}

	public void setValorPecas(BigDecimal valorPecas) {
		this.valorPecas = valorPecas;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(String condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public String getPropostaNumero() {
		return propostaNumero;
	}

	public void setPropostaNumero(String propostaNumero) {
		this.propostaNumero = propostaNumero;
	}

	public String getAutorizadoPor() {
		return autorizadoPor;
	}

	public void setAutorizadoPor(String autorizadoPor) {
		this.autorizadoPor = autorizadoPor;
	}

	public String getOrdemDeCompra() {
		return ordemDeCompra;
	}

	public void setOrdemDeCompra(String ordemDeCompra) {
		this.ordemDeCompra = ordemDeCompra;
	}

	public String getNotesPreenchidoPor() {
		return notesPreenchidoPor;
	}

	public void setNotesPreenchidoPor(String notesPreenchidoPor) {
		this.notesPreenchidoPor = notesPreenchidoPor;
	}

	public String getObsNf() {
		return obsNf;
	}

	public void setObsNf(String obsNf) {
		this.obsNf = obsNf;
	}

	public String getIsSendDataFaturamento() {
		return isSendDataFaturamento;
	}

	public void setIsSendDataFaturamento(String isSendDataFaturamento) {
		this.isSendDataFaturamento = isSendDataFaturamento;
	}

	public String getOrdemCompraPeca() {
		return ordemCompraPeca;
	}

	public void setOrdemCompraPeca(String ordemCompraPeca) {
		this.ordemCompraPeca = ordemCompraPeca;
	}

	public String getOrdemCompraServico() {
		return ordemCompraServico;
	}

	public void setOrdemCompraServico(String ordemCompraServico) {
		this.ordemCompraServico = ordemCompraServico;
	}

	public Long getEstabelecimentoCredorPecas() {
		return estabelecimentoCredorPecas;
	}

	public void setEstabelecimentoCredorPecas(Long estabelecimentoCredorPecas) {
		this.estabelecimentoCredorPecas = estabelecimentoCredorPecas;
	}

	public Long getEstabelecimentoCredorServicos() {
		return estabelecimentoCredorServicos;
	}

	public void setEstabelecimentoCredorServicos(Long estabelecimentoCredorServicos) {
		this.estabelecimentoCredorServicos = estabelecimentoCredorServicos;
	}

	public String getCondicaoPagamentoPecas() {
		return condicaoPagamentoPecas;
	}

	public void setCondicaoPagamentoPecas(String condicaoPagamentoPecas) {
		this.condicaoPagamentoPecas = condicaoPagamentoPecas;
	}

	public String getCondicaoPagamentoServicos() {
		return condicaoPagamentoServicos;
	}

	public void setCondicaoPagamentoServicos(String condicaoPagamentoServicos) {
		this.condicaoPagamentoServicos = condicaoPagamentoServicos;
	}

	public BigDecimal getDescontoPorcPecas() {
		return descontoPorcPecas;
	}

	public void setDescontoPorcPecas(BigDecimal descontoPorcPecas) {
		this.descontoPorcPecas = descontoPorcPecas;
	}

	public BigDecimal getDescontoPorcServicos() {
		return descontoPorcServicos;
	}

	public void setDescontoPorcServicos(BigDecimal descontoPorcServicos) {
		this.descontoPorcServicos = descontoPorcServicos;
	}

	public BigDecimal getDescontoValorPecas() {
		return descontoValorPecas;
	}

	public void setDescontoValorPecas(BigDecimal descontoValorPecas) {
		this.descontoValorPecas = descontoValorPecas;
	}

	public BigDecimal getDescontoValorServicos() {
		return descontoValorServicos;
	}

	public void setDescontoValorServicos(BigDecimal descontoValorServicos) {
		this.descontoValorServicos = descontoValorServicos;
	}

	public String getAutomaticaFaturada() {
		return automaticaFaturada;
	}

	public void setAutomaticaFaturada(String automaticaFaturada) {
		this.automaticaFaturada = automaticaFaturada;
	}

	public String getObsPeca() {
		return obsPeca;
	}

	public void setObsPeca(String obsPeca) {
		this.obsPeca = obsPeca;
	}

	public String getObsServico() {
		return obsServico;
	}

	public void setObsServico(String obsServico) {
		this.obsServico = obsServico;
	}

	public String getCondicaoEspececial() {
		return condicaoEspececial;
	}

	public void setCondicaoEspececial(String condicaoEspececial) {
		this.condicaoEspececial = condicaoEspececial;
	}

	public String getObservacaoDesconto() {
		return observacaoDesconto;
	}

	public void setObservacaoDesconto(String observacaoDesconto) {
		this.observacaoDesconto = observacaoDesconto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeSituacaoOs)) {
            return false;
        }
        GeSituacaoOs other = (GeSituacaoOs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geradorbean.GeSituacaoOs[ id=" + id + " ]";
    }
    
}

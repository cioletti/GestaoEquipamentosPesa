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
 * @author Douglas
 */
@Entity
@Table(name = "GE_CHECK_IN")

	public class GeCheckIn implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
	private Long id;
    @Column(name = "DATA_EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    @Column(name = "DATA_ABERTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "ID_SUPERVISOR")
    private String idSupervisor;
    @Column(name = "COD_CLIENTE")
    private String codCliente;
    @Column(name = "IS_SEARCH_DBS")
    private String isSearchDbs;
    @Column(name = "IS_CHECK_IN")
    private String isCheckIn;
    @Column(name = "IS_LIBERADO_P_DIGITADOR")
    private String isLiberadoPDigitador;
    @Column(name = "IS_LIBERADO_P_ORCAMENTISTA")
    private String isLiberadoPOrcamentista;
    @Column(name = "IS_LIBERADO_P_CONSULTOR")
    private String isLiberadoPConsultor;
    @Column(name = "STATUS_NEGOCIACAO_CONSULTOR")
    private String statusNegociacaoConsultor;
    @Column(name = "OBS_NEGOCIACAO_CONSULTOR")
    private String obsNegociacaoConsultor;
    @Column(name = "ID_FUNCIONARIO_CONSULTOR")
    private String idFuncionarioConsultor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "HAS_SEND_DBS")
    private String hasSendDbs;
    @Column(name = "HAS_SEND_DATA_ORCAMENTO")
    private String hasSendDataOrcamento;
    @Column(name = "FATOR_URGENCIA")
    private String fatorUrgencia;
    @Column(name = "FATOR_CLIENTE")
    private BigDecimal fatorCliente;
    @Column(name = "VALOR_MAO_DE_OBRA")
    private BigDecimal valorMaoDeObra;
    @Column(name = "VALOR_FRETE")
    private BigDecimal valorFrete;
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Column(name = "VALOR_SERVICOS_TERCEIROS")
    private BigDecimal valorServicosTerceiros;
    @Column(name = "VALOR_MAT_USO_TECNICO")
    private BigDecimal valorMatUsoTecnico;
    @Column(name = "VALOR_CLIENTE")
    private BigDecimal valorCliente;
    @Column(name = "VALOR_TERCEIROS")
    private BigDecimal valorTerceiros;
    @Column(name = "HAS_SEND_DATA_APROVACAO")
    private String hasSendDataAprovacao;
    @Column(name = "HAS_SEND_DATA_PREVISAO")
    private String hasSendDataPrevisao;
    @Column(name = "HAS_SEND_PECAS_DBS")
    private String hasSendPecasDbs;
    @Column(name = "TIPO_CLIENTE")
    private String tipoCliente;
    @Column(name = "CLIENTE_INTER")
    private String clienteInter;
    @Column(name = "CONTA_CONTABIL_SIGLA")
    private String contaContabilSigla;
    @Column(name = "CENTRO_CUSTO_SIGLA")
    private String centroCustoSigla;
    @Column(name = "SIGLA_INDICADOR_GARANTIA")
    private String siglaIndicadorGarantia;
    @Column(name = "VALIDADE_PROPOSTA")
    private Long validadeProposta;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "IS_CREATE_OS")
    private String isCreateOS;
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "ID_EQUIPAMENTO")
    private String idEquipamento;    
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;    
    @Column(name = "OBS_PROPOSTA")
    private String obsProposta;    
    @Column(name = "POSSUI_SUB_TRIBUTARIA")
    private String possuiSubTributaria;    
    @Column(name = "IS_FIND_SUB_TRIBUTARIA")
    private String isFindSubTributaria;    
    @Column(name = "HAS_SEND_CRM_DBS")
    private String hasSendCrmDbs;    
    @Column(name = "ID_CRM_PROPOSTA")
    private Long idCrmProposta;    
    @JoinColumn(name = "ID_TIPO_FRETE", referencedColumnName = "ID")
    @ManyToOne
    private GeTipoFrete idTipoFrete;
    @JoinColumn(name = "ID_OS_PALM", referencedColumnName = "IDOS_PALM")
    @ManyToOne
    private GeOsPalm idOsPalm;
    @Column(name = "ID_FUNCIONARIO_DATA_ORCAMENTO")
    private String idFuncionarioDataOrcamento;
    @Column(name = "IS_OPEN_OS")
    private String isOpenOs;    
    @Column(name = "IS_ENCERRADA")
    private String isEncerrada;    
    @Column(name = "OBS_JOB_CONTROL")
    private String obsJobControl;    
    @Column(name = "PIPNO")
    private String pipno;  
    @Column(name = "IS_APROVADO_OS")
    private String isAprovadoOs;
    @Column(name = "ID_OS_APROVADA")
    private String idOsAprovada;
    

    public String getIdFuncionarioDataOrcamento() {
		return idFuncionarioDataOrcamento;
	}

	public void setIdFuncionarioDataOrcamento(String idFuncionarioDataOrcamento) {
		this.idFuncionarioDataOrcamento = idFuncionarioDataOrcamento;
	}

	public GeCheckIn() {
    }

    public GeCheckIn(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getIsSearchDbs() {
        return isSearchDbs;
    }

    public void setIsSearchDbs(String isSearchDbs) {
        this.isSearchDbs = isSearchDbs;
    }
    
    public String getIsCheckIn() {
    	return isCheckIn;
    }
    
    public void setIsCheckIn(String isCheckIn) {
    	this.isCheckIn = isCheckIn;
    }

    public String getIsLiberadoPDigitador() {
		return isLiberadoPDigitador;
	}

	public void setIsLiberadoPDigitador(String isLiberadoPDigitador) {
		this.isLiberadoPDigitador = isLiberadoPDigitador;
	}

	public String getIsLiberadoPOrcamentista() {
		return isLiberadoPOrcamentista;
	}

	public void setIsLiberadoPOrcamentista(String isLiberadoPOrcamentista) {
		this.isLiberadoPOrcamentista = isLiberadoPOrcamentista;
	}

	public String getIsLiberadoPConsultor() {
		return isLiberadoPConsultor;
	}

	public void setIsLiberadoPConsultor(String isLiberadoPConsultor) {
		this.isLiberadoPConsultor = isLiberadoPConsultor;
	}

	public String getStatusNegociacaoConsultor() {
		return statusNegociacaoConsultor;
	}

	public void setStatusNegociacaoConsultor(String statusNegociacaoConsultor) {
		this.statusNegociacaoConsultor = statusNegociacaoConsultor;
	}

	public String getObsNegociacaoConsultor() {
		return obsNegociacaoConsultor;
	}

	public void setObsNegociacaoConsultor(String obsNegociacaoConsultor) {
		this.obsNegociacaoConsultor = obsNegociacaoConsultor;
	}

	public String getIdFuncionarioConsultor() {
		return idFuncionarioConsultor;
	}

	public void setIdFuncionarioConsultor(String idFuncionarioConsultor) {
		this.idFuncionarioConsultor = idFuncionarioConsultor;
	}

	public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMsgDbs() {
        return msgDbs;
    }

    public void setMsgDbs(String msgDbs) {
        this.msgDbs = msgDbs;
    }

    public String getHasSendDbs() {
        return hasSendDbs;
    }

    public void setHasSendDbs(String hasSendDbs) {
        this.hasSendDbs = hasSendDbs;
    }

    public String getHasSendDataOrcamento() {
        return hasSendDataOrcamento;
    }

    public void setHasSendDataOrcamento(String hasSendDataOrcamento) {
        this.hasSendDataOrcamento = hasSendDataOrcamento;
    }

    public String getFatorUrgencia() {
        return fatorUrgencia;
    }

    public void setFatorUrgencia(String fatorUrgencia) {
        this.fatorUrgencia = fatorUrgencia;
    }

    public BigDecimal getFatorCliente() {
        return fatorCliente;
    }

    public void setFatorCliente(BigDecimal fatorCliente) {
        this.fatorCliente = fatorCliente;
    }

    public BigDecimal getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public BigDecimal getValorServicosTerceiros() {
        return valorServicosTerceiros;
    }

    public void setValorServicosTerceiros(BigDecimal valorServicosTerceiros) {
        this.valorServicosTerceiros = valorServicosTerceiros;
    }

    public BigDecimal getValorMatUsoTecnico() {
        return valorMatUsoTecnico;
    }

    public void setValorMatUsoTecnico(BigDecimal valorMatUsoTecnico) {
        this.valorMatUsoTecnico = valorMatUsoTecnico;
    }

    public BigDecimal getValorCliente() {
        return valorCliente;
    }

    public void setValorCliente(BigDecimal valorCliente) {
        this.valorCliente = valorCliente;
    }

    public BigDecimal getValorTerceiros() {
        return valorTerceiros;
    }

    public void setValorTerceiros(BigDecimal valorTerceiros) {
        this.valorTerceiros = valorTerceiros;
    }

    public String getHasSendDataAprovacao() {
        return hasSendDataAprovacao;
    }

    public void setHasSendDataAprovacao(String hasSendDataAprovacao) {
        this.hasSendDataAprovacao = hasSendDataAprovacao;
    }

    public String getHasSendDataPrevisao() {
        return hasSendDataPrevisao;
    }

    public void setHasSendDataPrevisao(String hasSendDataPrevisao) {
        this.hasSendDataPrevisao = hasSendDataPrevisao;
    }

    public String getHasSendPecasDbs() {
        return hasSendPecasDbs;
    }

    public void setHasSendPecasDbs(String hasSendPecasDbs) {
        this.hasSendPecasDbs = hasSendPecasDbs;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getClienteInter() {
        return clienteInter;
    }

    public void setClienteInter(String clienteInter) {
        this.clienteInter = clienteInter;
    }

    public String getContaContabilSigla() {
        return contaContabilSigla;
    }

    public void setContaContabilSigla(String contaContabilSigla) {
        this.contaContabilSigla = contaContabilSigla;
    }

    public String getCentroCustoSigla() {
        return centroCustoSigla;
    }

    public void setCentroCustoSigla(String centroCustoSigla) {
        this.centroCustoSigla = centroCustoSigla;
    }

    public String getSiglaIndicadorGarantia() {
        return siglaIndicadorGarantia;
    }

    public void setSiglaIndicadorGarantia(String siglaIndicadorGarantia) {
        this.siglaIndicadorGarantia = siglaIndicadorGarantia;
    }

    public Long getValidadeProposta() {
        return validadeProposta;
    }

    public void setValidadeProposta(Long validadeProposta) {
        this.validadeProposta = validadeProposta;
    }

    public String getJobControl() {
		return jobControl;
	}

	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}

	public String getIsCreateOS() {
		return isCreateOS;
	}

	public void setIsCreateOS(String isCreateOS) {
		this.isCreateOS = isCreateOS;
	}

	public GeTipoFrete getIdTipoFrete() {
        return idTipoFrete;
    }

    public void setIdTipoFrete(GeTipoFrete idTipoFrete) {
        this.idTipoFrete = idTipoFrete;
    }

    public GeOsPalm getIdOsPalm() {
        return idOsPalm;
    }

    public void setIdOsPalm(GeOsPalm idOsPalm) {
        this.idOsPalm = idOsPalm;
    }

    public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public String getCodErroDbs() {
		return codErroDbs;
	}

	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}

	public String getObsProposta() {
		return obsProposta;
	}

	public void setObsProposta(String obsProposta) {
		this.obsProposta = obsProposta;
	}

	public String getPossuiSubTributaria() {
		return possuiSubTributaria;
	}

	public void setPossuiSubTributaria(String possuiSubTributaria) {
		this.possuiSubTributaria = possuiSubTributaria;
	}

	public String getIsFindSubTributaria() {
		return isFindSubTributaria;
	}

	public void setIsFindSubTributaria(String isFindSubTributaria) {
		this.isFindSubTributaria = isFindSubTributaria;
	}


	public String getIsOpenOs() {
		return isOpenOs;
	}

	public void setIsOpenOs(String isOpenOs) {
		this.isOpenOs = isOpenOs;
	}

	public String getHasSendCrmDbs() {
		return hasSendCrmDbs;
	}

	public void setHasSendCrmDbs(String hasSendCrmDbs) {
		this.hasSendCrmDbs = hasSendCrmDbs;
	}

	public Long getIdCrmProposta() {
		return idCrmProposta;
	}

	public void setIdCrmProposta(Long idCrmProposta) {
		this.idCrmProposta = idCrmProposta;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getIsEncerrada() {
		return isEncerrada;
	}

	public void setIsEncerrada(String isEncerrada) {
		this.isEncerrada = isEncerrada;
	}

	public String getObsJobControl() {
		return obsJobControl;
	}

	public void setObsJobControl(String obsJobControl) {
		this.obsJobControl = obsJobControl;
	}

	public String getPipno() {
		return pipno;
	}

	public void setPipno(String pipno) {
		this.pipno = pipno;
	}

	public String getIsAprovadoOs() {
		return isAprovadoOs;
	}

	public void setIsAprovadoOs(String isAprovadoOs) {
		this.isAprovadoOs = isAprovadoOs;
	}

	public String getIdOsAprovada() {
		return idOsAprovada;
	}

	public void setIdOsAprovada(String idOsAprovada) {
		this.idOsAprovada = idOsAprovada;
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
        if (!(object instanceof GeCheckIn)) {
            return false;
        }
        GeCheckIn other = (GeCheckIn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeCheckIn[ id=" + id + " ]";
    }
    
}

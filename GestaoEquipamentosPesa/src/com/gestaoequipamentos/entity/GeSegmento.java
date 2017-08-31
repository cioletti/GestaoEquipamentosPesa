/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_SEGMENTO")
public class GeSegmento implements Serializable {
    private static final long serialVersionUID = 1L; 
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
   private Long id;
    @Column(name = "NUMERO_SEGMENTO")
    private String numeroSegmento;
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Column(name = "DESCRICAO_JOB_CODE")
    private String descricaoJobCode;
    @Column(name = "DESCRICAO_COMP_CODE")
    private String descricaoCompCode;
    @Column(name = "COM_CODE")
    private String comCode;
    @Column(name = "HORAS_PREVISTA")
    private String horasPrevista;
    @Column(name = "HORAS_PREVISTA_SUBST")
    private String horasPrevistaSubst;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "JOB_CONTROL")
    private String jobControl;       
    @Column(name = "HAS_SEND_DBS")
    private String hasSendDbs;  
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;  
    @Column(name = "ID_FUNCIONARIO_PECAS")
    private String idFuncionarioPecas;  
    @Column(name = "DATA_LIBERACAO_PECAS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLiberacaoPecas;  
    @Column(name = "DATA_TERMINO_SERVICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTerminoServico;  
    @Column(name = "QTD_COMP")
    private Integer qtdComp;  
    @Column(name = "QTD_COMP_SUBST")
    private Integer qtdCompSubst;  
    @Column(name = "IS_CREATE_SEGMENTO")
    private String isCreateSegmento;  
    @Column(name = "ID_TW_FUNCIONARIO_SUBST")
    private String idTwFuncionarioSubst;  
    @Column(name = "DATA_SUBSTITUICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSubstituicao;
    @OneToMany(mappedBy = "idSegmento")
    private Collection<GeOperacao> geOperacaoCollection;
    @JoinColumn(name = "ID_CHECKIN", referencedColumnName = "ID")
    @ManyToOne
    private GeCheckIn idCheckin;
    @OneToMany(mappedBy = "idSegmento")
    @JoinColumn(name = "ID_FUNCIONARIO_CRIADOR", referencedColumnName = "EPIDNO")
    @ManyToOne
    private TwFuncionario idFuncionarioCriador;
    @OneToMany(mappedBy = "idSegmento")
    private Collection<GeDocSegOper> geDocSegOperCollection;
    @Column(name = "OBSERVACAO")
    private String observacao; 

    @Column(name = "TITULO_FOTOS")
    private String tituloFotos; 
    @Column(name = "DESCRICAO_FALHA_FOTOS")
    private String descricaoFalhaFotos; 
    @Column(name = "CONCLUSAO_FOTOS")
    private String conclusaofotos; 
    @Column(name = "FUNCIONARIO_FOTOS")
    private String funcionarioFotos; 
    @Column(name = "DESCRICAO_JOB_CONTROL")
    private String descricaoJobControl; 
    @Column(name = "NOME_SEGMENTO")
    private String nomeSegmento; 
    @Column(name = "ID_FUNCIONARIO_DATA_TERMINO_SERVICO")
    private String idFuncionarioDataTerminoServico; 
    @JoinColumn(name = "ID_LEGENDA_PROCESSO_OFICINA", referencedColumnName = "ID")
    @ManyToOne
    private GeLegendaProcessoOficina idLegendaProcessoOficina;
    @Column(name = "OBSERVACAO_FLUXO_OS")
    private String observacaoFluxoOs; 

    public String getTituloFotos() {
		return tituloFotos;
	}

	public void setTituloFotos(String tituloFotos) {
		this.tituloFotos = tituloFotos;
	}

	public String getDescricaoFalhaFotos() {
		return descricaoFalhaFotos;
	}

	public void setDescricaoFalhaFotos(String descricaoFalhaFotos) {
		this.descricaoFalhaFotos = descricaoFalhaFotos;
	}

	public String getConclusaofotos() {
		return conclusaofotos;
	}

	public void setConclusaofotos(String conclusaofotos) {
		this.conclusaofotos = conclusaofotos;
	}

	public String getFuncionarioFotos() {
		return funcionarioFotos;
	}

	public void setFuncionarioFotos(String funcionarioFotos) {
		this.funcionarioFotos = funcionarioFotos;
	}

	public GeSegmento() {
    }

    public GeSegmento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSegmento() {
        return numeroSegmento;
    }

    public void setNumeroSegmento(String numeroSegmento) {
        this.numeroSegmento = numeroSegmento;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getDescricaoJobCode() {
        return descricaoJobCode;
    }

    public void setDescricaoJobCode(String descricaoJobCode) {
        this.descricaoJobCode = descricaoJobCode;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getHorasPrevista() {
        return horasPrevista;
    }

    public void setHorasPrevista(String horasPrevista) {
        this.horasPrevista = horasPrevista;
    }

    public String getMsgDbs() {
        return msgDbs;
    }

    public void setMsgDbs(String msgDbs) {
        this.msgDbs = msgDbs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public Collection<GeOperacao> getGeOperacaoCollection() {
        return geOperacaoCollection;
    }

    public void setGeOperacaoCollection(Collection<GeOperacao> geOperacaoCollection) {
        this.geOperacaoCollection = geOperacaoCollection;
    }

    public GeCheckIn getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(GeCheckIn idCheckin) {
        this.idCheckin = idCheckin;
    }

    public Collection<GeDocSegOper> getGeDocSegOperCollection() {
        return geDocSegOperCollection;
    }

    public void setGeDocSegOperCollection(Collection<GeDocSegOper> geDocSegOperCollection) {
        this.geDocSegOperCollection = geDocSegOperCollection;
    }

    public String getJobControl() {
		return jobControl;
	}

	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}

	public String getDescricaoCompCode() {
		return descricaoCompCode;
	}

	public void setDescricaoCompCode(String descricaoCompCode) {
		this.descricaoCompCode = descricaoCompCode;
	}

	public String getHasSendDbs() {
		return hasSendDbs;
	}

	public void setHasSendDbs(String hasSendDbs) {
		this.hasSendDbs = hasSendDbs;
	}

	public Integer getQtdComp() {
		return qtdComp;
	}

	public void setQtdComp(Integer qtdComp) {
		this.qtdComp = qtdComp;
	}

	public TwFuncionario getIdFuncionarioCriador() {
		return idFuncionarioCriador;
	}

	public void setIdFuncionarioCriador(TwFuncionario idFuncionarioCriador) {
		this.idFuncionarioCriador = idFuncionarioCriador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public String getIsCreateSegmento() {
		return isCreateSegmento;
	}

	public void setIsCreateSegmento(String isCreateSegmento) {
		this.isCreateSegmento = isCreateSegmento;
	}

	public String getCodErroDbs() {
		return codErroDbs;
	}

	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}

	public String getIdFuncionarioPecas() {
		return idFuncionarioPecas;
	}

	public void setIdFuncionarioPecas(String idFuncionarioPecas) {
		this.idFuncionarioPecas = idFuncionarioPecas;
	}

	public Date getDataLiberacaoPecas() {
		return dataLiberacaoPecas;
	}

	public void setDataLiberacaoPecas(Date dataLiberacaoPecas) {
		this.dataLiberacaoPecas = dataLiberacaoPecas;
	}

	public Date getDataTerminoServico() {
		return dataTerminoServico;
	}

	public void setDataTerminoServico(Date dataTerminoServico) {
		this.dataTerminoServico = dataTerminoServico;
	}

	public String getHorasPrevistaSubst() {
		return horasPrevistaSubst;
	}

	public void setHorasPrevistaSubst(String horasPrevistaSubst) {
		this.horasPrevistaSubst = horasPrevistaSubst;
	}

	public Integer getQtdCompSubst() {
		return qtdCompSubst;
	}

	public void setQtdCompSubst(Integer qtdCompSubst) {
		this.qtdCompSubst = qtdCompSubst;
	}

	public Date getDataSubstituicao() {
		return dataSubstituicao;
	}

	public void setDataSubstituicao(Date dataSubstituicao) {
		this.dataSubstituicao = dataSubstituicao;
	}

	public String getIdTwFuncionarioSubst() {
		return idTwFuncionarioSubst;
	}

	public void setIdTwFuncionarioSubst(String idTwFuncionarioSubst) {
		this.idTwFuncionarioSubst = idTwFuncionarioSubst;
	}

	public String getDescricaoJobControl() {
		return descricaoJobControl;
	}

	public void setDescricaoJobControl(String descricaoJobControl) {
		this.descricaoJobControl = descricaoJobControl;
	}

	public GeLegendaProcessoOficina getIdLegendaProcessoOficina() {
		return idLegendaProcessoOficina;
	}

	public void setIdLegendaProcessoOficina(
			GeLegendaProcessoOficina idLegendaProcessoOficina) {
		this.idLegendaProcessoOficina = idLegendaProcessoOficina;
	}

	public String getNomeSegmento() {
		return nomeSegmento;
	}

	public void setNomeSegmento(String nomeSegmento) {
		this.nomeSegmento = nomeSegmento;
	}


	public String getObservacaoFluxoOs() {
		return observacaoFluxoOs;
	}

	public void setObservacaoFluxoOs(String observacaoFluxoOs) {
		this.observacaoFluxoOs = observacaoFluxoOs;
	}

	public String getIdFuncionarioDataTerminoServico() {
		return idFuncionarioDataTerminoServico;
	}

	public void setIdFuncionarioDataTerminoServico(
			String idFuncionarioDataTerminoServico) {
		this.idFuncionarioDataTerminoServico = idFuncionarioDataTerminoServico;
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
        if (!(object instanceof GeSegmento)) {
            return false;
        }
        GeSegmento other = (GeSegmento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSegmento[ id=" + id + " ]";
    }
    
}

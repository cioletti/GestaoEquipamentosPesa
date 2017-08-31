/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_SEGMENTO_LOG")

public class GeSegmentoLog implements Serializable {
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
    @Column(name = "COM_CODE")
    private String comCode;
    @Column(name = "ID_CHECKIN")
    private Long idCheckin;
    @Column(name = "HORAS_PREVISTA")
    private String horasPrevista;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "DESCRICAO_COMP_CODE")
    private String descricaoCompCode;
    @Column(name = "QTD_COMPONENTE")
    private Long qtdComponente;
    @Column(name = "HAS_SEND_DBS")
    private String hasSendDbs;
    @Column(name = "QTD_COMP")
    private Long qtdComp;
    @Column(name = "ID_FUNCIONARIO_CRIADOR")
    private String idFuncionarioCriador;
    @Lob
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "IS_CREATE_SEGMENTO")
    private String isCreateSegmento;
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;
    @Column(name = "ID_FUNCIONARIO_PECAS")
    private String idFuncionarioPecas;
    @Column(name = "DATA_LIBERACAO_PECAS")
    private String dataLiberacaoPecas;
    @Column(name = "DATA_TERMINO_SERVICO")
    private String dataTerminoServico;
    @Column(name = "QTD_COMP_SUBST")
    private Long qtdCompSubst;
    @Column(name = "HORAS_PREVISTA_SUBST")
    private String horasPrevistaSubst;
    @Column(name = "ID_TW_FUNCIONARIO_SUBST")
    private String idTwFuncionarioSubst;
    @Column(name = "DATA_SUBSTITUICAO")
    private String dataSubstituicao;
    @Lob
    @Column(name = "TITULO_FOTOS")
    private String tituloFotos;
    @Lob
    @Column(name = "DESCRICAO_FALHA_FOTOS")
    private String descricaoFalhaFotos;
    @Lob
    @Column(name = "CONCLUSAO_FOTOS")
    private String conclusaoFotos;
    @Column(name = "FUNCIONARIO_FOTOS")
    private String funcionarioFotos;
    @Column(name = "ID_FUNCIONARIO_REMOCAO")
    private String idFuncionarioRemocao;
    @Column(name = "ID_FUNCIONARIO_EDICAO")
    private String idFuncionarioEdicao;

    public GeSegmentoLog() {
    }

    public GeSegmentoLog(Long id) {
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

    public Long getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(Long idCheckin) {
        this.idCheckin = idCheckin;
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

    public Long getQtdComponente() {
        return qtdComponente;
    }

    public void setQtdComponente(Long qtdComponente) {
        this.qtdComponente = qtdComponente;
    }

    public String getHasSendDbs() {
        return hasSendDbs;
    }

    public void setHasSendDbs(String hasSendDbs) {
        this.hasSendDbs = hasSendDbs;
    }

    public Long getQtdComp() {
        return qtdComp;
    }

    public void setQtdComp(Long qtdComp) {
        this.qtdComp = qtdComp;
    }

    public String getIdFuncionarioCriador() {
        return idFuncionarioCriador;
    }

    public void setIdFuncionarioCriador(String idFuncionarioCriador) {
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

    public String getDataLiberacaoPecas() {
        return dataLiberacaoPecas;
    }

    public void setDataLiberacaoPecas(String dataLiberacaoPecas) {
        this.dataLiberacaoPecas = dataLiberacaoPecas;
    }

    public String getDataTerminoServico() {
        return dataTerminoServico;
    }

    public void setDataTerminoServico(String dataTerminoServico) {
        this.dataTerminoServico = dataTerminoServico;
    }

    public Long getQtdCompSubst() {
        return qtdCompSubst;
    }

    public void setQtdCompSubst(Long qtdCompSubst) {
        this.qtdCompSubst = qtdCompSubst;
    }

    public String getHorasPrevistaSubst() {
        return horasPrevistaSubst;
    }

    public void setHorasPrevistaSubst(String horasPrevistaSubst) {
        this.horasPrevistaSubst = horasPrevistaSubst;
    }

    public String getIdTwFuncionarioSubst() {
        return idTwFuncionarioSubst;
    }

    public void setIdTwFuncionarioSubst(String idTwFuncionarioSubst) {
        this.idTwFuncionarioSubst = idTwFuncionarioSubst;
    }

    public String getDataSubstituicao() {
        return dataSubstituicao;
    }

    public void setDataSubstituicao(String dataSubstituicao) {
        this.dataSubstituicao = dataSubstituicao;
    }

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

    public String getConclusaoFotos() {
        return conclusaoFotos;
    }

    public void setConclusaoFotos(String conclusaoFotos) {
        this.conclusaoFotos = conclusaoFotos;
    }

    public String getFuncionarioFotos() {
        return funcionarioFotos;
    }

    public void setFuncionarioFotos(String funcionarioFotos) {
        this.funcionarioFotos = funcionarioFotos;
    }

    public String getIdFuncionarioRemocao() {
		return idFuncionarioRemocao;
	}

	public void setIdFuncionarioRemocao(String idFuncionarioRemocao) {
		this.idFuncionarioRemocao = idFuncionarioRemocao;
	}

	public String getIdFuncionarioEdicao() {
		return idFuncionarioEdicao;
	}

	public void setIdFuncionarioEdicao(String idFuncionarioEdicao) {
		this.idFuncionarioEdicao = idFuncionarioEdicao;
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
        if (!(object instanceof GeSegmentoLog)) {
            return false;
        }
        GeSegmentoLog other = (GeSegmentoLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.GeSegmentoLog[ id=" + id + " ]";
    }
    
}

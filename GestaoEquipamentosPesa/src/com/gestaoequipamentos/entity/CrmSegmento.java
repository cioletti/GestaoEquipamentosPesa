/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_SEGMENTO")
@NamedQueries({
    @NamedQuery(name = "CrmSegmento.findAll", query = "SELECT c FROM CrmSegmento c")})
public class CrmSegmento implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_EMS_SEGMENTO")
    private long idEmsSegmento;
    @JoinColumn(name = "ID_PROPOSTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CrmProposta idProposta;
    @Column(name = "NUMERO_SEGMENTO")
    private String numeroSegmento;
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Column(name = "DESCRICAO_JOB_CODE")
    private String descricaoJobCode;
    @Column(name = "COM_CODE")
    private String comCode;
    @Column(name = "HORAS_PREVISTA")
    private String horasPrevista;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "MSG_DOC_DBS")
    private String msgDocDbs;
    @Column(name = "COD_ERRO_DOC_DBS")
    private String codErroDocDbs;
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
    @Column(name = "ID_FUNCIONARIO_PECAS")
    private String idFuncionarioPecas;
    @Basic(optional = false)
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
    @Column(name = "DATA_IMPORTACAO_PECAS")
    private Date dataImportacaoPecas;
    @Column(name = "QTD_COMP_SUBST")
    private Long qtdCompSubst;
    @Column(name = "HORAS_PREVISTA_SUBST")
    private String horasPrevistaSubst;
    @Column(name = "ID_TW_FUNCIONARIO_SUBST")
    private String idTwFuncionarioSubst;
    @Column(name = "DATA_SUBSTITUICAO")
    private String dataSubstituicao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Lob
    @Column(name = "OBS_REJEICAO")
    private String obsRejeicao;

    public CrmSegmento() {
    }
    public CrmSegmento(Long id) {
        this.id = id;
    }
    public CrmSegmento(Long id, long idEmsSegmento, Date dataCriacao) {
        this.id = id;
        this.idEmsSegmento = idEmsSegmento;
        this.dataCriacao = dataCriacao;
    }
    public String getObsRejeicao() {
        return obsRejeicao;
    }

    public void setObsRejeicao(String obsRejeicao) {
        this.obsRejeicao = obsRejeicao;
    }

    public CrmProposta getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(CrmProposta idProposta) {
        this.idProposta = idProposta;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(long idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
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

    public String getCodErroDbs() {
        return codErroDbs;
    }

    public void setCodErroDbs(String codErroDbs) {
        this.codErroDbs = codErroDbs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getMsgDocDbs() {
        return msgDocDbs;
    }

    public void setMsgDocDbs(String msgDocDbs) {
        this.msgDocDbs = msgDocDbs;
    }

    public String getCodErroDocDbs() {
        return codErroDocDbs;
    }

    public void setCodErroDocDbs(String codErroDocDbs) {
        this.codErroDocDbs = codErroDocDbs;
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

    public String getIdFuncionarioPecas() {
        return idFuncionarioPecas;
    }

    public void setIdFuncionarioPecas(String idFuncionarioPecas) {
        this.idFuncionarioPecas = idFuncionarioPecas;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataImportacaoPecas() {
        return dataImportacaoPecas;
    }

    public void setDataImportacaoPecas(Date dataImportacaoPecas) {
        this.dataImportacaoPecas = dataImportacaoPecas;
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

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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
        if (!(object instanceof CrmSegmento)) {
            return false;
        }
        CrmSegmento other = (CrmSegmento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmSegmento[ id=" + id + " ]";
    }
    
}

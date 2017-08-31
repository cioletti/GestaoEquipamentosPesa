/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_PRORROGAR_FASE_NEGOCIACAO")
public class CrmProrrogarFaseNegociacao implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DATA_REAL")
    private Date dataReal;
    @Column(name = "DATA_PRORROGADA")
    private Date dataProrrogada;
    @JoinColumn(name = "ID_SITUACAO_PROPOSTA", referencedColumnName = "ID")
    @ManyToOne
    private CrmSituacaoProposta idSituacaoProposta;
    @JoinColumn(name = "ID_FASE_NEGOCIACAO", referencedColumnName = "ID")
    @ManyToOne
    private CrmFaseNegociacao idFaseNegociacao;

    public CrmProrrogarFaseNegociacao() {
    }

    public CrmSituacaoProposta getIdSituacaoProposta() {
        return idSituacaoProposta;
    }

    public void setIdSituacaoProposta(CrmSituacaoProposta idSituacaoProposta) {
        this.idSituacaoProposta = idSituacaoProposta;
    }

    public CrmProrrogarFaseNegociacao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataReal() {
        return dataReal;
    }

    public void setDataReal(Date dataReal) {
        this.dataReal = dataReal;
    }

    public Date getDataProrrogada() {
        return dataProrrogada;
    }

    public void setDataProrrogada(Date dataProrrogada) {
        this.dataProrrogada = dataProrrogada;
    }

    public CrmFaseNegociacao getIdFaseNegociacao() {
        return idFaseNegociacao;
    }

    public void setIdFaseNegociacao(CrmFaseNegociacao idFaseNegociacao) {
        this.idFaseNegociacao = idFaseNegociacao;
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
        if (!(object instanceof CrmProrrogarFaseNegociacao)) {
            return false;
        }
        CrmProrrogarFaseNegociacao other = (CrmProrrogarFaseNegociacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.CrmProrrogarFaseNegociacao[ id=" + id + " ]";
    }
    
}

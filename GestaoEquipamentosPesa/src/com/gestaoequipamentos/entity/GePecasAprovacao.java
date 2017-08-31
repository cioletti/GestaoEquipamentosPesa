/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RODRIGO
 */
@Entity
@Table(name = "GE_PECAS_APROVACAO")
@XmlRootElement
public class GePecasAprovacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "QTD")
    private Long qtd;
    @Column(name = "PART_NUMBER")
    private String partNumber;
    @Column(name = "PART_NAME")
    private String partName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UNITE_RATE")
    private BigDecimal uniteRate;
    @Column(name = "D_N_PRICE")
    private BigDecimal dNPrice;
    @Column(name = "UNITE_C_L")
    private BigDecimal uniteCL;
    @Column(name = "C_L_PRICE")
    private BigDecimal cLPrice;
    @JoinColumn(name = "ID_APROVACAO_OS", referencedColumnName = "ID")
    @ManyToOne
    private GeFormularioAprovacaoOs idAprovacaoOs;

    public GePecasAprovacao() {
    }

    public GePecasAprovacao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getUniteRate() {
        return uniteRate;
    }

    public void setUniteRate(BigDecimal uniteRate) {
        this.uniteRate = uniteRate;
    }

    public BigDecimal getDNPrice() {
        return dNPrice;
    }

    public void setDNPrice(BigDecimal dNPrice) {
        this.dNPrice = dNPrice;
    }

    public BigDecimal getUniteCL() {
        return uniteCL;
    }

    public void setUniteCL(BigDecimal uniteCL) {
        this.uniteCL = uniteCL;
    }

    public BigDecimal getCLPrice() {
        return cLPrice;
    }

    public void setCLPrice(BigDecimal cLPrice) {
        this.cLPrice = cLPrice;
    }

    public GeFormularioAprovacaoOs getIdAprovacaoOs() {
        return idAprovacaoOs;
    }

    public void setIdAprovacaoOs(GeFormularioAprovacaoOs idAprovacaoOs) {
        this.idAprovacaoOs = idAprovacaoOs;
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
        if (!(object instanceof GePecasAprovacao)) {
            return false;
        }
        GePecasAprovacao other = (GePecasAprovacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.campo.entity.GePecasAprovacao[ id=" + id + " ]";
    }
    
}

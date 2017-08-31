/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author RDR
 */
@Entity
@Table(name = "GE_CABECALHO_PECAS")
@NamedQueries({
    @NamedQuery(name = "GeCabecalhoPecas.findAll", query = "SELECT g FROM GeCabecalhoPecas g")})
public class GeCabecalhoPecas implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "RFDCNO")
    private String rfdcno;
    @Column(name = "CUNO")
    private String cuno;
    @Column(name = "CUNM")
    private String cunm;
    @Column(name = "CUNM2")
    private String cunm2;
    @Column(name = "CUADD1")
    private String cuadd1;
    @Column(name = "CUADD2")
    private String cuadd2;
    @Column(name = "CUADD3")
    private String cuadd3;
    @Column(name = "CUCYST")
    private String cucyst;
    @Column(name = "ZIPCD9")
    private String zipcd9;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FRETE")
    private BigDecimal frete;
    @JoinColumn(name = "ID_DOC_SEG_OPER", referencedColumnName = "ID")
    @ManyToOne
    private GeDocSegOper idDocSegOper;

    public GeCabecalhoPecas() {
    }

    public GeCabecalhoPecas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfdcno() {
        return rfdcno;
    }

    public void setRfdcno(String rfdcno) {
        this.rfdcno = rfdcno;
    }

    public String getCuno() {
        return cuno;
    }

    public void setCuno(String cuno) {
        this.cuno = cuno;
    }

    public String getCunm() {
        return cunm;
    }

    public void setCunm(String cunm) {
        this.cunm = cunm;
    }

    public String getCunm2() {
        return cunm2;
    }

    public void setCunm2(String cunm2) {
        this.cunm2 = cunm2;
    }

    public String getCuadd1() {
        return cuadd1;
    }

    public void setCuadd1(String cuadd1) {
        this.cuadd1 = cuadd1;
    }

    public String getCuadd2() {
        return cuadd2;
    }

    public void setCuadd2(String cuadd2) {
        this.cuadd2 = cuadd2;
    }

    public String getCuadd3() {
        return cuadd3;
    }

    public void setCuadd3(String cuadd3) {
        this.cuadd3 = cuadd3;
    }

    public String getCucyst() {
        return cucyst;
    }

    public void setCucyst(String cucyst) {
        this.cucyst = cucyst;
    }

    public String getZipcd9() {
        return zipcd9;
    }

    public void setZipcd9(String zipcd9) {
        this.zipcd9 = zipcd9;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public GeDocSegOper getIdDocSegOper() {
        return idDocSegOper;
    }

    public void setIdDocSegOper(GeDocSegOper idDocSegOper) {
        this.idDocSegOper = idDocSegOper;
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
        if (!(object instanceof GeCabecalhoPecas)) {
            return false;
        }
        GeCabecalhoPecas other = (GeCabecalhoPecas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeCabecalhoPecas[ id=" + id + " ]";
    }
    
}

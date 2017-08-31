/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_MASTER_PECAS")
public class GeMasterPecas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "SOS1")
    private String sos1;
    @Column(name = "PANO20")
    private String pano20;
    @Column(name = "CLSFCL")
    private String clsfcl;
    @Column(name = "RPPANO")
    private String rppano;
    @Column(name = "QTY2")
    private String qty2;
    @Column(name = "RPLSOS")
    private String rplsos;
    @Column(name = "RLPACD")
    private String rlpacd;
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)   
    private Long id;

    public GeMasterPecas() {
    }

    public GeMasterPecas(Long id) {
        this.id = id;
    }

    public GeMasterPecas(Long id, String sos1, String pano20) {
        this.id = id;
        this.sos1 = sos1;
        this.pano20 = pano20;
    }

    public String getSos1() {
        return sos1;
    }

    public void setSos1(String sos1) {
        this.sos1 = sos1;
    }

    public String getPano20() {
        return pano20;
    }

    public void setPano20(String pano20) {
        this.pano20 = pano20;
    }

    public String getClsfcl() {
        return clsfcl;
    }

    public void setClsfcl(String clsfcl) {
        this.clsfcl = clsfcl;
    }

    public String getRppano() {
        return rppano;
    }

    public void setRppano(String rppano) {
        this.rppano = rppano;
    }

    public String getQty2() {
        return qty2;
    }

    public void setQty2(String qty2) {
        this.qty2 = qty2;
    }

    public String getRplsos() {
        return rplsos;
    }

    public void setRplsos(String rplsos) {
        this.rplsos = rplsos;
    }

    public String getRlpacd() {
        return rlpacd;
    }

    public void setRlpacd(String rlpacd) {
        this.rlpacd = rlpacd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof GeMasterPecas)) {
            return false;
        }
        GeMasterPecas other = (GeMasterPecas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmp.entity.GeMasterPecas[ id=" + id + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_FOTO_INSPECAO")
@NamedQueries({
    @NamedQuery(name = "GeFotoInspecao.findAll", query = "SELECT g FROM GeFotoInspecao g")})
public class GeFotoInspecao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FOTO_INSPECAO")
    private Long idFotoInspecao;
    @Lob
    @Column(name = "FOTO")
    private Serializable foto;
    @Column(name = "ID_OS_PALM")
    private Long idOsPalm;
    @Column(name = "ID_OS_PALM_DT")
    private Long idOsPalmDt;

    public GeFotoInspecao() {
    }

    public GeFotoInspecao(Long idFotoInspecao) {
        this.idFotoInspecao = idFotoInspecao;
    }

    public Long getIdFotoInspecao() {
        return idFotoInspecao;
    }

    public void setIdFotoInspecao(Long idFotoInspecao) {
        this.idFotoInspecao = idFotoInspecao;
    }

    public Serializable getFoto() {
        return foto;
    }

    public void setFoto(Serializable foto) {
        this.foto = foto;
    }

    public Long getIdOsPalm() {
        return idOsPalm;
    }

    public void setIdOsPalm(Long idOsPalm) {
        this.idOsPalm = idOsPalm;
    }

    public Long getIdOsPalmDt() {
        return idOsPalmDt;
    }

    public void setIdOsPalmDt(Long idOsPalmDt) {
        this.idOsPalmDt = idOsPalmDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFotoInspecao != null ? idFotoInspecao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeFotoInspecao)) {
            return false;
        }
        GeFotoInspecao other = (GeFotoInspecao) object;
        if ((this.idFotoInspecao == null && other.idFotoInspecao != null) || (this.idFotoInspecao != null && !this.idFotoInspecao.equals(other.idFotoInspecao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeFotoInspecao[ idFotoInspecao=" + idFotoInspecao + " ]";
    }
    
}

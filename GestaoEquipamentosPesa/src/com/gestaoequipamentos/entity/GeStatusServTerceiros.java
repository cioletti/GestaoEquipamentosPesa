/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_STATUS_SERV_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "GeStatusServTerceiros.findAll", query = "SELECT g FROM GeStatusServTerceiros g")})
public class GeStatusServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "SIGLA")
    private String sigla;
    @OneToMany(mappedBy = "idStatusServTerceiros")
    private Collection<GeSegmentoServTerceiros> geSegmentoServTerceirosCollection;

    public GeStatusServTerceiros() {
    }

    public GeStatusServTerceiros(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Collection<GeSegmentoServTerceiros> getGeSegmentoServTerceirosCollection() {
        return geSegmentoServTerceirosCollection;
    }

    public void setGeSegmentoServTerceirosCollection(Collection<GeSegmentoServTerceiros> geSegmentoServTerceirosCollection) {
        this.geSegmentoServTerceirosCollection = geSegmentoServTerceirosCollection;
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
        if (!(object instanceof GeStatusServTerceiros)) {
            return false;
        }
        GeStatusServTerceiros other = (GeStatusServTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeStatusServTerceiros[ id=" + id + " ]";
    }
    
}

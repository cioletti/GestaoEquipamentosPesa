/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_FORN_TIPO_SERV_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "GeFornTipoServTerceiros.findAll", query = "SELECT g FROM GeFornTipoServTerceiros g")})
public class GeFornTipoServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @JoinColumn(name = "ID_TIPO_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private GeTipoServicoTerceiros idTipoServTerceiros;
    @JoinColumn(name = "ID_FORN_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private GeFornecedorServTerceiros idFornServTerceiros;

    public GeFornTipoServTerceiros() {
    }

    public GeFornTipoServTerceiros(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeTipoServicoTerceiros getIdTipoServTerceiros() {
        return idTipoServTerceiros;
    }

    public void setIdTipoServTerceiros(GeTipoServicoTerceiros idTipoServTerceiros) {
        this.idTipoServTerceiros = idTipoServTerceiros;
    }

    public GeFornecedorServTerceiros getIdFornServTerceiros() {
        return idFornServTerceiros;
    }

    public void setIdFornServTerceiros(GeFornecedorServTerceiros idFornServTerceiros) {
        this.idFornServTerceiros = idFornServTerceiros;
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
        if (!(object instanceof GeFornTipoServTerceiros)) {
            return false;
        }
        GeFornTipoServTerceiros other = (GeFornTipoServTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeFornTipoServTerceiros[ id=" + id + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
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
@Table(name = "GE_TIPO_SERVICO_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "GeTipoServicoTerceiros.findAll", query = "SELECT g FROM GeTipoServicoTerceiros g")})
public class GeTipoServicoTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true) 
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoServTerceiros")
    private Collection<GeFornTipoServTerceiros> geFornTipoServTerceirosCollection;

    public GeTipoServicoTerceiros() {
    }

    public GeTipoServicoTerceiros(Long id) {
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

    public Collection<GeFornTipoServTerceiros> getGeFornTipoServTerceirosCollection() {
		return geFornTipoServTerceirosCollection;
	}

	public void setGeFornTipoServTerceirosCollection(
			Collection<GeFornTipoServTerceiros> geFornTipoServTerceirosCollection) {
		this.geFornTipoServTerceirosCollection = geFornTipoServTerceirosCollection;
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
        if (!(object instanceof GeTipoServicoTerceiros)) {
            return false;
        }
        GeTipoServicoTerceiros other = (GeTipoServicoTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeTipoServicoTerceiros[ id=" + id + " ]";
    }
    
}

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
@Table(name = "GE_SITUACAO_ITEM")
@NamedQueries({
    @NamedQuery(name = "GeSituacaoItem.findAll", query = "SELECT g FROM GeSituacaoItem g")})
public class GeSituacaoItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "QTD")
    private Long qtd;
    @JoinColumn(name = "ID_SITUACAO_SERV_TERC", referencedColumnName = "ID")
    @ManyToOne
    private GeSituacaoServTerceiros idSituacaoServTerc;
    @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID")
    @ManyToOne
    private GeItem idItem;

    public GeSituacaoItem() {
    }

    public GeSituacaoItem(Long id) {
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

    public GeSituacaoServTerceiros getIdSituacaoServTerc() {
        return idSituacaoServTerc;
    }

    public void setIdSituacaoServTerc(GeSituacaoServTerceiros idSituacaoServTerc) {
        this.idSituacaoServTerc = idSituacaoServTerc;
    }

    public GeItem getIdItem() {
        return idItem;
    }

    public void setIdItem(GeItem idItem) {
        this.idItem = idItem;
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
        if (!(object instanceof GeSituacaoItem)) {
            return false;
        }
        GeSituacaoItem other = (GeSituacaoItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSituacaoItem[ id=" + id + " ]";
    }
    
}

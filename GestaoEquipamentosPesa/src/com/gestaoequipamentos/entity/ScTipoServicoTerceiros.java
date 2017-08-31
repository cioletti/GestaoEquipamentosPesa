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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "SC_TIPO_SERVICO_TERCEIROS")

public class ScTipoServicoTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id 
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;

    public ScTipoServicoTerceiros() {
    }

    public ScTipoServicoTerceiros(Long id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScTipoServicoTerceiros)) {
            return false;
        }
        ScTipoServicoTerceiros other = (ScTipoServicoTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.campo.entity.ScTipoServicoTerceiros[ id=" + id + " ]";
    }
    
}

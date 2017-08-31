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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_FATOR")
@NamedQueries({
    @NamedQuery(name = "GeFator.findAll", query = "SELECT g FROM GeFator g")})
public class GeFator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_INTER")
    private Double valorInter;
    @Column(name = "VALOR_VENDA")
    private Double valorVenda;
    @Column(name = "FATOR_URGENCIA")
    private Double fatorUrgencia;

    public GeFator() {
    }

    public GeFator(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorInter() {
        return valorInter;
    }

    public void setValorInter(Double valorInter) {
        this.valorInter = valorInter;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Double getFatorUrgencia() {
        return fatorUrgencia;
    }

    public void setFatorUrgencia(Double fatorUrgencia) {
        this.fatorUrgencia = fatorUrgencia;
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
        if (!(object instanceof GeFator)) {
            return false;
        }
        GeFator other = (GeFator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeFator[ id=" + id + " ]";
    }
    
}

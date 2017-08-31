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

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_SEGMENTO_ASSOCIADO")

public class GeSegmentoAssociado implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "FATOR_CLIENTE")
    private BigDecimal fatorCliente;
    @Column(name = "ID_PRECO")
    private Long idPreco;
    @Column(name = "FATOR_URGENCIA")
    private BigDecimal fatorUrgencia;
    @JoinColumn(name = "ID_SEG_PAI", referencedColumnName = "ID")
    @ManyToOne
    private GeSegmento idSegPai;
    @JoinColumn(name = "ID_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private GeSegmento idSegmento;

    public GeSegmentoAssociado() {
    }

    public GeSegmentoAssociado(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFatorCliente() {
        return fatorCliente;
    }

    public void setFatorCliente(BigDecimal fatorCliente) {
        this.fatorCliente = fatorCliente;
    }

    public Long getIdPreco() {
        return idPreco;
    }

    public void setIdPreco(Long idPreco) {
        this.idPreco = idPreco;
    }

    public BigDecimal getFatorUrgencia() {
        return fatorUrgencia;
    }

    public void setFatorUrgencia(BigDecimal fatorUrgencia) {
        this.fatorUrgencia = fatorUrgencia;
    }

    public GeSegmento getIdSegPai() {
        return idSegPai;
    }

    public void setIdSegPai(GeSegmento idSegPai) {
        this.idSegPai = idSegPai;
    }

    public GeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(GeSegmento idSegmento) {
        this.idSegmento = idSegmento;
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
        if (!(object instanceof GeSegmentoAssociado)) {
            return false;
        }
        GeSegmentoAssociado other = (GeSegmentoAssociado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSegmentoAssociado[ id=" + id + " ]";
    }
    
}

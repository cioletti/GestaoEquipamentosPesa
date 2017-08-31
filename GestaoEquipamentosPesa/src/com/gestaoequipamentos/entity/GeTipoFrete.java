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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author RDR
 */
@Entity
@Table(name = "GE_TIPO_FRETE")
@NamedQueries({
    @NamedQuery(name = "GeTipoFrete.findAll", query = "SELECT g FROM GeTipoFrete g")})
public class GeTipoFrete implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)   
    private Long id;
    @Column(name = "TIPO_FRETE")
    private String tipoFrete;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "TAXA")
//    private BigDecimal taxa;
//    @Column(name = "FRETE_MINIMO")
//    private BigDecimal freteMinimo;

    public GeTipoFrete() {
    }

    public GeTipoFrete(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoFrete() {
        return tipoFrete;
    }

    public void setTipoFrete(String tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

//    public BigDecimal getTaxa() {
//        return taxa;
//    }
//
//    public void setTaxa(BigDecimal taxa) {
//        this.taxa = taxa;
//    }
//
//    public BigDecimal getFreteMinimo() {
//        return freteMinimo;
//    }
//
//    public void setFreteMinimo(BigDecimal freteMinimo) {
//        this.freteMinimo = freteMinimo;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeTipoFrete)) {
            return false;
        }
        GeTipoFrete other = (GeTipoFrete) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geradorbean.GeTipoFrete[ id=" + id + " ]";
    }
    
}

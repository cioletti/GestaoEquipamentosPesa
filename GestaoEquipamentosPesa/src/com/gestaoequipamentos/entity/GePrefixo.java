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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_PREFIXO")
public class GePrefixo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_DE_VENDA")
    private BigDecimal valorDeVenda;
    @Column(name = "VALOR_DE_CUSTO")
    private BigDecimal valorDeCusto;
    @JoinColumn(name = "ID_MODELO", referencedColumnName = "ID_ARV")
    @ManyToOne
    private GeArvInspecao idModelo;

    public GePrefixo() {
    }

    public GePrefixo(Long id) {
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

    public BigDecimal getValorDeVenda() {
        return valorDeVenda;
    }

    public void setValorDeVenda(BigDecimal valorDeVenda) {
        this.valorDeVenda = valorDeVenda;
    }

    public BigDecimal getValorDeCusto() {
		return valorDeCusto;
	}

	public void setValorDeCusto(BigDecimal valorDeCusto) {
		this.valorDeCusto = valorDeCusto;
	}

	public GeArvInspecao getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(GeArvInspecao idModelo) {
        this.idModelo = idModelo;
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
        if (!(object instanceof GePrefixo)) {
            return false;
        }
        GePrefixo other = (GePrefixo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GePrefixo[ id=" + id + " ]";
    }
    
}

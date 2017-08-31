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
 * @author Douglas
 */
@Entity
@Table(name = "GE_PRECO")

public class GePreco implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true) 
    private Long id;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "PREFIXO")
    private Long prefixo;
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Column(name = "COMP_CODE")
    private String compCode;
    @Column(name = "QTD_HORAS")
    private String qtdHoras;
    @Column(name = "DESCRICAO_COMP_CODE")
    private String descricaoCompCode;
    @Column(name = "DESCRICAO_SERVICO")
    private String descricaoServico;
    @Column(name = "DESCRICAO_JOB_CODE")
    private String descricaoJobCode;
    @JoinColumn(name = "ID_PREFIXO", referencedColumnName = "ID")
    @ManyToOne
    private GePrefixo idPrefixo;
    @JoinColumn(name = "ID_COMPLEXIDADE", referencedColumnName = "ID")
    @ManyToOne
    private GeComplexidade idComplexidade;
    @JoinColumn(name = "ID_MODELO", referencedColumnName = "ID_ARV")
    @ManyToOne
    private GeArvInspecao idModelo;

    public GePreco() {
    }

    public GePreco(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(Long prefixo) {
        this.prefixo = prefixo;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(String qtdHoras) {
        this.qtdHoras = qtdHoras;
    }

    public String getDescricaoCompCode() {
        return descricaoCompCode;
    }

    public void setDescricaoCompCode(String descricaoCompCode) {
        this.descricaoCompCode = descricaoCompCode;
    }

    public String getDescricaoServico() {
		return descricaoServico;
	}

	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}

	public String getDescricaoJobCode() {
        return descricaoJobCode;
    }

    public void setDescricaoJobCode(String descricaoJobCode) {
        this.descricaoJobCode = descricaoJobCode;
    }

    public GePrefixo getIdPrefixo() {
        return idPrefixo;
    }

    public void setIdPrefixo(GePrefixo idPrefixo) {
        this.idPrefixo = idPrefixo;
    }

    public GeComplexidade getIdComplexidade() {
        return idComplexidade;
    }

    public void setIdComplexidade(GeComplexidade idComplexidade) {
        this.idComplexidade = idComplexidade;
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
        if (!(object instanceof GePreco)) {
            return false;
        }
        GePreco other = (GePreco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GePreco[ id=" + id + " ]";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TW_FILIAL")
@NamedQueries({
    @NamedQuery(name = "TwFilial.findAll", query = "SELECT t FROM TwFilial t")})
public class TwFilial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STNO")
    private Long stno;
    @Column(name = "STNM")
    private String stnm;
    @Column(name = "REGIONAL")
    private BigInteger regional;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "APROVACAO_PROPOSTA_SERVICO")
    private String aprovacaoPropostaServico;
    @OneToMany(mappedBy = "filial")
    private Collection<GeGestorRental> geGestorRentalCollection;

    public TwFilial() {
    }

    public TwFilial(Long stno) {
        this.stno = stno;
    }

    public Long getStno() {
        return stno;
    }

    public void setStno(Long stno) {
        this.stno = stno;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public BigInteger getRegional() {
        return regional;
    }

    public void setRegional(BigInteger regional) {
        this.regional = regional;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Collection<GeGestorRental> getGeGestorRentalCollection() {
        return geGestorRentalCollection;
    }

    public void setGeGestorRentalCollection(Collection<GeGestorRental> geGestorRentalCollection) {
        this.geGestorRentalCollection = geGestorRentalCollection;
    }

    public String getAprovacaoPropostaServico() {
		return aprovacaoPropostaServico;
	}

	public void setAprovacaoPropostaServico(String aprovacaoPropostaServico) {
		this.aprovacaoPropostaServico = aprovacaoPropostaServico;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (stno != null ? stno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TwFilial)) {
            return false;
        }
        TwFilial other = (TwFilial) object;
        if ((this.stno == null && other.stno != null) || (this.stno != null && !this.stno.equals(other.stno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.TwFilial[ stno=" + stno + " ]";
    }
    
}

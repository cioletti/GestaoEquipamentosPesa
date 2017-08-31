/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
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
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_TIPO_PROPOSTA")
@NamedQueries({
    @NamedQuery(name = "CrmTipoProposta.findAll", query = "SELECT c FROM CrmTipoProposta c")})
public class CrmTipoProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "SIGLA")
    private String sigla;
    @OneToMany(mappedBy = "idTipoProposta")
    private Collection<CrmProposta> crmPropostaCollection;

    public CrmTipoProposta() {
    }

    public CrmTipoProposta(Long id) {
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Collection<CrmProposta> getCrmPropostaCollection() {
        return crmPropostaCollection;
    }

    public void setCrmPropostaCollection(Collection<CrmProposta> crmPropostaCollection) {
        this.crmPropostaCollection = crmPropostaCollection;
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
        if (!(object instanceof CrmTipoProposta)) {
            return false;
        }
        CrmTipoProposta other = (CrmTipoProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmTipoProposta[ id=" + id + " ]";
    }
    
}

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
@Table(name = "GE_OPERACAO")
@NamedQueries({
    @NamedQuery(name = "GeOperacao.findAll", query = "SELECT g FROM GeOperacao g")})
public class GeOperacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "NUM_OPERACAO")
    private String numOperacao;
    @Column(name = "JBCD")
    private String jbcd;
    @Column(name = "CPTCD")
    private String cptcd;
    @Column(name = "DESCRICAO_JBCD")
    private String descricaoJbcd;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "DESCRICAO_COMP_CODE")
    private String descricaoCompCode;
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs; 
    @Column(name = "MSG_DBS")
    private String msgDbs; 
    @Column(name = "IS_CREATE_OPERACAO")
    private String isCreateOperacao;
    @JoinColumn(name = "ID_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private GeSegmento idSegmento;
    @JoinColumn(name = "ID_FUNCIONARIO_CRIADOR", referencedColumnName = "EPIDNO")
    @ManyToOne
    private TwFuncionario idFuncionarioCriador;

    public GeOperacao() {
    }

    public GeOperacao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumOperacao() {
        return numOperacao;
    }

    public void setNumOperacao(String numOperacao) {
        this.numOperacao = numOperacao;
    }

    public String getJbcd() {
        return jbcd;
    }

    public void setJbcd(String jbcd) {
        this.jbcd = jbcd;
    }

    public String getCptcd() {
        return cptcd;
    }

    public void setCptcd(String cptcd) {
        this.cptcd = cptcd;
    }

    public String getDescricaoJbcd() {
        return descricaoJbcd;
    }

    public void setDescricaoJbcd(String descricaoJbcd) {
        this.descricaoJbcd = descricaoJbcd;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getDescricaoCompCode() {
        return descricaoCompCode;
    }

    public void setDescricaoCompCode(String descricaoCompCode) {
        this.descricaoCompCode = descricaoCompCode;
    }

    public GeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(GeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }

    public TwFuncionario getIdFuncionarioCriador() {
		return idFuncionarioCriador;
	}

	public void setIdFuncionarioCriador(TwFuncionario idFuncionarioCriador) {
		this.idFuncionarioCriador = idFuncionarioCriador;
	}


	public String getIsCreateOperacao() {
		return isCreateOperacao;
	}

	public void setIsCreateOperacao(String isCreateOperacao) {
		this.isCreateOperacao = isCreateOperacao;
	}

	public String getCodErroDbs() {
		return codErroDbs;
	}

	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}

	public String getMsgDbs() {
		return msgDbs;
	}

	public void setMsgDbs(String msgDbs) {
		this.msgDbs = msgDbs;
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
        if (!(object instanceof GeOperacao)) {
            return false;
        }
        GeOperacao other = (GeOperacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeOperacao[ id=" + id + " ]";
    }
    
}

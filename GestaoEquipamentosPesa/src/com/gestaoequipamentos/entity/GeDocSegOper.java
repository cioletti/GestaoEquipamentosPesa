/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_DOC_SEG_OPER")
@NamedQueries({
    @NamedQuery(name = "GeDocSegOper.findAll", query = "SELECT g FROM GeDocSegOper g")})
public class GeDocSegOper implements Serializable {
    private static final long serialVersionUID = 1L; 
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "DATA_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @OneToMany(mappedBy = "idDocSegOper")
    private Collection<GePecas> gePecasCollection;
    @JoinColumn(name = "ID_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private GeSegmento idSegmento;
    @JoinColumn(name = "ID_OPERACAO", referencedColumnName = "ID")
    @ManyToOne
    private GeOperacao idOperacao;
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;
    @Column(name = "MSG_DBS_REMOCAO")
    private String msgDbsRemocao;
    @Column(name = "ID_FUNCIONARIO_PECAS")
    private String idFuncionarioPecas;

    public GeDocSegOper() {
    }

    public GeDocSegOper(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getMsgDbs() {
        return msgDbs;
    }

    public void setMsgDbs(String msgDbs) {
        this.msgDbs = msgDbs;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Collection<GePecas> getGePecasCollection() {
        return gePecasCollection;
    }

    public void setGePecasCollection(Collection<GePecas> gePecasCollection) {
        this.gePecasCollection = gePecasCollection;
    }

    public GeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(GeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }

    public GeOperacao getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(GeOperacao idOperacao) {
        this.idOperacao = idOperacao;
    }

    public String getCodErroDbs() {
		return codErroDbs;
	}

	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}

	public String getMsgDbsRemocao() {
		return msgDbsRemocao;
	}

	public void setMsgDbsRemocao(String msgDbsRemocao) {
		this.msgDbsRemocao = msgDbsRemocao;
	}

	public String getIdFuncionarioPecas() {
		return idFuncionarioPecas;
	}

	public void setIdFuncionarioPecas(String idFuncionarioPecas) {
		this.idFuncionarioPecas = idFuncionarioPecas;
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
        if (!(object instanceof GeDocSegOper)) {
            return false;
        }
        GeDocSegOper other = (GeDocSegOper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeDocSegOper[ id=" + id + " ]";
    }
    
}

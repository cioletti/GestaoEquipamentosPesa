/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "GE_SITUACAO_CRM")

public class GeSituacaoCrm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true) 
    private Long id;
    @Column(name = "STATUS_OP")
    private String statusOp;
    @Column(name = "IS_FIND_CRM")
    private String isFindCrm;
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "COD_OPT")
    private Long codOpt;
    @JoinColumn(name = "ID_CHECKIN", referencedColumnName = "ID")
    @ManyToOne
    private GeCheckIn idCheckin;

    public GeSituacaoCrm() {
    }

    public GeSituacaoCrm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusOp() {
        return statusOp;
    }

    public void setStatusOp(String statusOp) {
        this.statusOp = statusOp;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getCodOpt() {
        return codOpt;
    }

    public void setCodOpt(Long codOpt) {
        this.codOpt = codOpt;
    }

    public GeCheckIn getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(GeCheckIn idCheckin) {
        this.idCheckin = idCheckin;
    }

    public String getIsFindCrm() {
		return isFindCrm;
	}

	public void setIsFindCrm(String isFindCrm) {
		this.isFindCrm = isFindCrm;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
        if (!(object instanceof GeSituacaoCrm)) {
            return false;
        }
        GeSituacaoCrm other = (GeSituacaoCrm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSituacaoCrm[ id=" + id + " ]";
    }
    
}

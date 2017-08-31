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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_CHECK_OUT")
@NamedQueries({
    @NamedQuery(name = "GeCheckOut.findAll", query = "SELECT g FROM GeCheckOut g")})
public class GeCheckOut implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "DATA_EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "ID_SUPERVISOR")
    private String idSupervisor;
    @Column(name = "COD_CLIENTE")
    private Long codCliente;
    @JoinColumn(name = "ID_CHECKIN", referencedColumnName = "ID")
    @ManyToOne
    private GeCheckIn idCheckin;

    public GeCheckOut() {
    }

    public GeCheckOut(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public Long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Long codCliente) {
        this.codCliente = codCliente;
    }

    public GeCheckIn getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(GeCheckIn idCheckin) {
        this.idCheckin = idCheckin;
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
        if (!(object instanceof GeCheckOut)) {
            return false;
        }
        GeCheckOut other = (GeCheckOut) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeCheckOut[ id=" + id + " ]";
    }
    
}

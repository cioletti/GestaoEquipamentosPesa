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
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_CONSULTOR_PROPOSTA")
public class GeConsultorProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @JoinColumn(name = "ID_EPIDNO", referencedColumnName = "EPIDNO")
    @ManyToOne(optional = false)
    private TwFuncionario idEpidno;
    @JoinColumn(name = "ID_CHECK_IN", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private GeCheckIn idCheckIn;

    public GeConsultorProposta() {
    }

    public GeConsultorProposta(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TwFuncionario getIdEpidno() {
        return idEpidno;
    }

    public void setIdEpidno(TwFuncionario idEpidno) {
        this.idEpidno = idEpidno;
    }

    public GeCheckIn getIdCheckIn() {
        return idCheckIn;
    }

    public void setIdCheckIn(GeCheckIn idCheckIn) {
        this.idCheckIn = idCheckIn;
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
        if (!(object instanceof GeConsultorProposta)) {
            return false;
        }
        GeConsultorProposta other = (GeConsultorProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GeConsultorProposta[ id=" + id + " ]";
    }
    
}

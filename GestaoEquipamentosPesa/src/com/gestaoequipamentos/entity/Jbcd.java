/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "JBCD")
public class Jbcd implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "JBCD")
    private String jbcd;
    @Column(name = "JBCDDS")
    private String jbcdds;

    public Jbcd() {
    }

    public Jbcd(Long id) {
        this.id = id;
    }

    public Jbcd(Long id, String jbcd) {
        this.id = id;
        this.jbcd = jbcd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJbcd() {
        return jbcd;
    }

    public void setJbcd(String jbcd) {
        this.jbcd = jbcd;
    }

    public String getJbcdds() {
        return jbcdds;
    }

    public void setJbcdds(String jbcdds) {
        this.jbcdds = jbcdds;
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
        if (!(object instanceof Jbcd)) {
            return false;
        }
        Jbcd other = (Jbcd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.Jbcd[ id=" + id + " ]";
    }
    
}

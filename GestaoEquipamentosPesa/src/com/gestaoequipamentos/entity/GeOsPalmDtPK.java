/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Douglas
 */
@Embeddable
public class GeOsPalmDtPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDOS_PALM_DT")
    private long idosPalmDt;
    @Basic(optional = false)
    @Column(name = "OS_PALM_IDOS_PALM")
    private long osPalmIdosPalm;

    public GeOsPalmDtPK() {
    }

    public GeOsPalmDtPK(long idosPalmDt, long osPalmIdosPalm) {
        this.idosPalmDt = idosPalmDt;
        this.osPalmIdosPalm = osPalmIdosPalm;
    }

    public long getIdosPalmDt() {
        return idosPalmDt;
    }

    public void setIdosPalmDt(long idosPalmDt) {
        this.idosPalmDt = idosPalmDt;
    }

    public long getOsPalmIdosPalm() {
        return osPalmIdosPalm;
    }

    public void setOsPalmIdosPalm(long osPalmIdosPalm) {
        this.osPalmIdosPalm = osPalmIdosPalm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idosPalmDt;
        hash += (int) osPalmIdosPalm;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeOsPalmDtPK)) {
            return false;
        }
        GeOsPalmDtPK other = (GeOsPalmDtPK) object;
        if (this.idosPalmDt != other.idosPalmDt) {
            return false;
        }
        if (this.osPalmIdosPalm != other.osPalmIdosPalm) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeOsPalmDtPK[ idosPalmDt=" + idosPalmDt + ", osPalmIdosPalm=" + osPalmIdosPalm + " ]";
    }
    
}

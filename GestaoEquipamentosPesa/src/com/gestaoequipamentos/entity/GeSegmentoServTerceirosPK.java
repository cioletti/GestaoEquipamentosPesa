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
 * @author Rodrigo
 */
@Embeddable
public class GeSegmentoServTerceirosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_SEGMENTO")
    private long idSegmento;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SERVICO_TERCEIROS")
    private long idTipoServicoTerceiros;

    public GeSegmentoServTerceirosPK() {
    }

    public GeSegmentoServTerceirosPK(long idSegmento, long idTipoServicoTerceiros) {
        this.idSegmento = idSegmento;
        this.idTipoServicoTerceiros = idTipoServicoTerceiros;
    }

    public long getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(long idSegmento) {
        this.idSegmento = idSegmento;
    }

    public long getIdTipoServicoTerceiros() {
        return idTipoServicoTerceiros;
    }

    public void setIdTipoServicoTerceiros(long idTipoServicoTerceiros) {
        this.idTipoServicoTerceiros = idTipoServicoTerceiros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSegmento;
        hash += (int) idTipoServicoTerceiros;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeSegmentoServTerceirosPK)) {
            return false;
        }
        GeSegmentoServTerceirosPK other = (GeSegmentoServTerceirosPK) object;
        if (this.idSegmento != other.idSegmento) {
            return false;
        }
        if (this.idTipoServicoTerceiros != other.idTipoServicoTerceiros) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSegmentoServTerceirosPK[ idSegmento=" + idSegmento + ", idTipoServicoTerceiros=" + idTipoServicoTerceiros + " ]";
    }
    
}

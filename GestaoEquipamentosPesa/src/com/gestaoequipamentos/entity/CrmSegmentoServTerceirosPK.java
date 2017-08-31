/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class CrmSegmentoServTerceirosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_CRM_SEGMENTO")
    private long idCrmSegmento;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SERVICO_TERCEIROS")
    private long idTipoServicoTerceiros;

    public CrmSegmentoServTerceirosPK() {
    }

    public CrmSegmentoServTerceirosPK(long idCrmSegmento, long idTipoServicoTerceiros) {
        this.idCrmSegmento = idCrmSegmento;
        this.idTipoServicoTerceiros = idTipoServicoTerceiros;
    }

    public long getIdCrmSegmento() {
        return idCrmSegmento;
    }

    public void setIdCrmSegmento(long idCrmSegmento) {
        this.idCrmSegmento = idCrmSegmento;
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
        hash += (int) idCrmSegmento;
        hash += (int) idTipoServicoTerceiros;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrmSegmentoServTerceirosPK)) {
            return false;
        }
        CrmSegmentoServTerceirosPK other = (CrmSegmentoServTerceirosPK) object;
        if (this.idCrmSegmento != other.idCrmSegmento) {
            return false;
        }
        if (this.idTipoServicoTerceiros != other.idTipoServicoTerceiros) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmSegmentoServTerceirosPK[ idCrmSegmento=" + idCrmSegmento + ", idTipoServicoTerceiros=" + idTipoServicoTerceiros + " ]";
    }
    
}

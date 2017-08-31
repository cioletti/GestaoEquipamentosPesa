/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_OS_PALM_DT")
@NamedQueries({
    @NamedQuery(name = "GeOsPalmDt.findAll", query = "SELECT g FROM GeOsPalmDt g")})
public class GeOsPalmDt implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeOsPalmDtPK geOsPalmDtPK;
    @Column(name = "ID_IDARV")
    private Long idIdarv;
    @Column(name = "SIMNAO")
    private String simnao;
    @Column(name = "GRUPO")
    private String grupo;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "TIPO_MANUTENCAO")
    private String tipoManutencao;
    @JoinColumn(name = "OS_PALM_IDOS_PALM", referencedColumnName = "IDOS_PALM", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GeOsPalm geOsPalm;

    public GeOsPalmDt() {
    }

    public GeOsPalmDt(GeOsPalmDtPK geOsPalmDtPK) {
        this.geOsPalmDtPK = geOsPalmDtPK;
    }

    public GeOsPalmDt(long idosPalmDt, long osPalmIdosPalm) {
        this.geOsPalmDtPK = new GeOsPalmDtPK(idosPalmDt, osPalmIdosPalm);
    }

    public GeOsPalmDtPK getGeOsPalmDtPK() {
        return geOsPalmDtPK;
    }

    public void setGeOsPalmDtPK(GeOsPalmDtPK geOsPalmDtPK) {
        this.geOsPalmDtPK = geOsPalmDtPK;
    }

    public Long getIdIdarv() {
        return idIdarv;
    }

    public void setIdIdarv(Long idIdarv) {
        this.idIdarv = idIdarv;
    }

    public String getSimnao() {
        return simnao;
    }

    public void setSimnao(String simnao) {
        this.simnao = simnao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public GeOsPalm getGeOsPalm() {
        return geOsPalm;
    }

    public void setGeOsPalm(GeOsPalm geOsPalm) {
        this.geOsPalm = geOsPalm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geOsPalmDtPK != null ? geOsPalmDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeOsPalmDt)) {
            return false;
        }
        GeOsPalmDt other = (GeOsPalmDt) object;
        if ((this.geOsPalmDtPK == null && other.geOsPalmDtPK != null) || (this.geOsPalmDtPK != null && !this.geOsPalmDtPK.equals(other.geOsPalmDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeOsPalmDt[ geOsPalmDtPK=" + geOsPalmDtPK + " ]";
    }
    
}

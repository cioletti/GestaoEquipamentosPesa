/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_SEGMENTO_SERV_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "CrmSegmentoServTerceiros.findAll", query = "SELECT c FROM CrmSegmentoServTerceiros c")})
public class CrmSegmentoServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CrmSegmentoServTerceirosPK crmSegmentoServTerceirosPK;
    @Column(name = "ID_EMS_SEGMENTO")
    private Long idEmsSegmento;
    @Column(name = "DATA")
    private String data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "QTD")
    private Long qtd;
    @Column(name = "ID_OPER")
    private Long idOper;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Lob
    @Column(name = "OBS_REJEICAO")
    private String obsRejeicao;
    @Column(name = "LOCAL_SERVICO")
    private String localServico;
    @JoinColumn(name = "ID_STATUS_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeStatusServTerceiros idStatusServTerceiros;
    @JoinColumn(name = "ID_FORN_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeFornecedorServTerceiros idFornServTerceiros;

    public CrmSegmentoServTerceiros() {
    }

    public CrmSegmentoServTerceiros(CrmSegmentoServTerceirosPK crmSegmentoServTerceirosPK) {
        this.crmSegmentoServTerceirosPK = crmSegmentoServTerceirosPK;
    }
    public String getObsRejeicao() {
        return obsRejeicao;
    }

    public void setObsRejeicao(String obsRejeicao) {
        this.obsRejeicao = obsRejeicao;
    }


    public CrmSegmentoServTerceiros(long idCrmSegmento, long idTipoServicoTerceiros) {
        this.crmSegmentoServTerceirosPK = new CrmSegmentoServTerceirosPK(idCrmSegmento, idTipoServicoTerceiros);
    }

    public CrmSegmentoServTerceirosPK getCrmSegmentoServTerceirosPK() {
        return crmSegmentoServTerceirosPK;
    }

    public void setCrmSegmentoServTerceirosPK(CrmSegmentoServTerceirosPK crmSegmentoServTerceirosPK) {
        this.crmSegmentoServTerceirosPK = crmSegmentoServTerceirosPK;
    }

    public Long getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(Long idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }

    public Long getIdOper() {
        return idOper;
    }

    public void setIdOper(Long idOper) {
        this.idOper = idOper;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getLocalServico() {
        return localServico;
    }

    public void setLocalServico(String localServico) {
        this.localServico = localServico;
    }

    public GeStatusServTerceiros getIdStatusServTerceiros() {
        return idStatusServTerceiros;
    }

    public void setIdStatusServTerceiros(GeStatusServTerceiros idStatusServTerceiros) {
        this.idStatusServTerceiros = idStatusServTerceiros;
    }

    public GeFornecedorServTerceiros getIdFornServTerceiros() {
        return idFornServTerceiros;
    }

    public void setIdFornServTerceiros(GeFornecedorServTerceiros idFornServTerceiros) {
        this.idFornServTerceiros = idFornServTerceiros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crmSegmentoServTerceirosPK != null ? crmSegmentoServTerceirosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrmSegmentoServTerceiros)) {
            return false;
        }
        CrmSegmentoServTerceiros other = (CrmSegmentoServTerceiros) object;
        if ((this.crmSegmentoServTerceirosPK == null && other.crmSegmentoServTerceirosPK != null) || (this.crmSegmentoServTerceirosPK != null && !this.crmSegmentoServTerceirosPK.equals(other.crmSegmentoServTerceirosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmSegmentoServTerceiros[ crmSegmentoServTerceirosPK=" + crmSegmentoServTerceirosPK + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_PECAS")
public class CrmPecas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_CRM_SEGMENTO")
    private long idCrmSegmento;
    @Basic(optional = false)
    @Column(name = "ID_EMS_SEGMENTO")
    private long idEmsSegmento;
    @Column(name = "PART_NO")
    private String partNo;
    @Column(name = "PART_NAME")
    private String partName;
    @Column(name = "QTD")
    private Long qtd;
    @Column(name = "GROUP_NUMBER")
    private String groupNumber;
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Column(name = "SMCS_CODE")
    private String smcsCode;
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Column(name = "USER_ID")
    private String userId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "VALOR_TOTAL")
    private BigDecimal valorTotal;
    @Column(name = "SOS1")
    private String sos1;
    @Column(name = "QTD_NAO_ATENDIDO")
    private Long qtdNaoAtendido;
    @Column(name = "IPI")
    private BigDecimal ipi;
    @Column(name = "ICMSUB")
    private BigDecimal icmsub;
    @Column(name = "TOTALTRIBUTOS")
    private BigDecimal totaltributos;
    @Column(name = "UNSEL")
    private BigDecimal unsel;

    public CrmPecas() {
    }

    public CrmPecas(Long id) {
        this.id = id;
    }

    public CrmPecas(Long id, long idCrmSegmento, long idEmsSegmento) {
        this.id = id;
        this.idCrmSegmento = idCrmSegmento;
        this.idEmsSegmento = idEmsSegmento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdCrmSegmento() {
        return idCrmSegmento;
    }

    public void setIdCrmSegmento(long idCrmSegmento) {
        this.idCrmSegmento = idCrmSegmento;
    }

    public long getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(long idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getSmcsCode() {
        return smcsCode;
    }

    public void setSmcsCode(String smcsCode) {
        this.smcsCode = smcsCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getSos1() {
        return sos1;
    }

    public void setSos1(String sos1) {
        this.sos1 = sos1;
    }

    public Long getQtdNaoAtendido() {
        return qtdNaoAtendido;
    }

    public void setQtdNaoAtendido(Long qtdNaoAtendido) {
        this.qtdNaoAtendido = qtdNaoAtendido;
    }

    public BigDecimal getIpi() {
        return ipi;
    }

    public void setIpi(BigDecimal ipi) {
        this.ipi = ipi;
    }

    public BigDecimal getIcmsub() {
        return icmsub;
    }

    public void setIcmsub(BigDecimal icmsub) {
        this.icmsub = icmsub;
    }

    public BigDecimal getTotaltributos() {
        return totaltributos;
    }

    public void setTotaltributos(BigDecimal totaltributos) {
        this.totaltributos = totaltributos;
    }

    public BigDecimal getUnsel() {
        return unsel;
    }

    public void setUnsel(BigDecimal unsel) {
        this.unsel = unsel;
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
        if (!(object instanceof CrmPecas)) {
            return false;
        }
        CrmPecas other = (CrmPecas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmPecas[ id=" + id + " ]";
    }
    
}

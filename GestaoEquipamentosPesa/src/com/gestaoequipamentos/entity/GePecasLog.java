/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_PECAS_LOG")

public class GePecasLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "PART_NO")
    private String partNo;
    @Column(name = "PART_NAME")
    private String partName;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
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
    @Column(name = "ID_DOC_SEG_OPER")
    private Long idDocSegOper;
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
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "NUM_SEGMENTO")
    private String numSegmento;
    @Column(name = "ID_PECA_ORIGINAL")
    private Long idPecaOriginal;

    public GePecasLog() {
    }

    public GePecasLog(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getIdDocSegOper() {
        return idDocSegOper;
    }

    public void setIdDocSegOper(Long idDocSegOper) {
        this.idDocSegOper = idDocSegOper;
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

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNumeroOs() {
		return numeroOs;
	}

	public void setNumeroOs(String numeroOs) {
		this.numeroOs = numeroOs;
	}

	public String getNumSegmento() {
		return numSegmento;
	}

	public void setNumSegmento(String numSegmento) {
		this.numSegmento = numSegmento;
	}

	public Long getIdPecaOriginal() {
		return idPecaOriginal;
	}

	public void setIdPecaOriginal(Long idPecaOriginal) {
		this.idPecaOriginal = idPecaOriginal;
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
        if (!(object instanceof GePecasLog)) {
            return false;
        }
        GePecasLog other = (GePecasLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GePecasLog[ id=" + id + " ]";
    }
    
}

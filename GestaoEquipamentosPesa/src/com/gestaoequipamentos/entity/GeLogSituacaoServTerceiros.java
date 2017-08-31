/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_LOG_SITUACAO_SERV_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "GeLogSituacaoServTerceiros.findAll", query = "SELECT g FROM GeLogSituacaoServTerceiros g")})
public class GeLogSituacaoServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "DATA_ENVIO_METROLOGIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioMetrologia;
    @Column(name = "ID_FUNC_ENVIO_METROLOGIA")
    private String idFuncEnvioMetrologia;
    @Lob
    @Column(name = "OBS_ENVIO_METROLOGIA")
    private String obsEnvioMetrologia;
    @Column(name = "DATA_ENVIO_RECEPCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioRecepcao;
    @Column(name = "ID_FUNC_ENVIO_RECEPCAO")
    private String idFuncEnvioRecepcao;
    @Lob
    @Column(name = "OBS_ENVIO_RECEPCAO")
    private String obsEnvioRecepcao;
    @Column(name = "DATA_ENVIO_FORNECEDOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioFornecedor;
    @Column(name = "ID_FUNC_ENVIO_FORNECEDOR")
    private String idFuncEnvioFornecedor;
    @Lob
    @Column(name = "OBS_ENVIO_FORNECEDOR")
    private String obsEnvioFornecedor;
    @Column(name = "DATA_FINALIZADO_FORNECEDOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFinalizadoFornecedor;
    @Column(name = "ID_FUNC_FINALIZADO_FORNECEDOR")
    private String idFuncFinalizadoFornecedor;
    @Lob
    @Column(name = "OBS_FINALIZADO_FORNECEDOR")
    private String obsFinalizadoFornecedor;
    @Column(name = "DATA_ENTRADA_ENVIO_METROLOGIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntradaEnvioMetrologia;
    @Column(name = "ID_FUNC_ENTRADA_ENVIO_METROLOGIA")
    private String idFuncEntradaEnvioMetrologia;
    @Lob
    @Column(name = "OBS_ENTRADA_ENVIO_METROLOGIA")
    private String obsEntradaEnvioMetrologia;
    @Column(name = "DATA_APROVACAO_METROLOGIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAprovacaoMetrologia;
    @Column(name = "ID_FUNC_APROVACAO_METROLOGIA")
    private String idFuncAprovacaoMetrologia;
    @Lob
    @Column(name = "OBS_APROVACAO_METROLOGIA")
    private String obsAprovacaoMetrologia;
    @Column(name = "DATA_REJEICAO_METROLOGIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRejeicaoMetrologia;
    @Column(name = "ID_FUNC_REJEICAO_METROLOGIA")
    private String idFuncRejeicaoMetrologia;
    @Lob
    @Column(name = "OBS_REJEICAO_METROLOGIA")
    private String obsRejeicaoMetrologia;
    
    @Column(name = "DATA_ENVIO_NOTA_FISCAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioNotaFiscal;
    @Column(name = "ID_FUNC_ENVIO_NOTA_FISCAL")
    private String idFuncEnvioNotaFiscal;
    @Lob
    @Column(name = "OBS_ENVIO_NOTA_FISCAL")
    private String obsEnvioNotaFiscal;
    
    
    @JoinColumn(name = "ID_SITUACAO_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeSituacaoServTerceiros idSituacaoServTerceiros;

    public GeLogSituacaoServTerceiros() {
    }

    public GeLogSituacaoServTerceiros(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFuncEnvioMetrologia() {
        return idFuncEnvioMetrologia;
    }

    public void setIdFuncEnvioMetrologia(String idFuncEnvioMetrologia) {
        this.idFuncEnvioMetrologia = idFuncEnvioMetrologia;
    }

    public String getObsEnvioMetrologia() {
        return obsEnvioMetrologia;
    }

    public void setObsEnvioMetrologia(String obsEnvioMetrologia) {
        this.obsEnvioMetrologia = obsEnvioMetrologia;
    }

    public String getIdFuncEnvioRecepcao() {
        return idFuncEnvioRecepcao;
    }

    public void setIdFuncEnvioRecepcao(String idFuncEnvioRecepcao) {
        this.idFuncEnvioRecepcao = idFuncEnvioRecepcao;
    }

    public String getObsEnvioRecepcao() {
        return obsEnvioRecepcao;
    }

    public void setObsEnvioRecepcao(String obsEnvioRecepcao) {
        this.obsEnvioRecepcao = obsEnvioRecepcao;
    }

    public String getIdFuncEnvioFornecedor() {
        return idFuncEnvioFornecedor;
    }

    public void setIdFuncEnvioFornecedor(String idFuncEnvioFornecedor) {
        this.idFuncEnvioFornecedor = idFuncEnvioFornecedor;
    }

    public String getObsEnvioFornecedor() {
        return obsEnvioFornecedor;
    }

    public void setObsEnvioFornecedor(String obsEnvioFornecedor) {
        this.obsEnvioFornecedor = obsEnvioFornecedor;
    }

    public String getIdFuncFinalizadoFornecedor() {
        return idFuncFinalizadoFornecedor;
    }

    public void setIdFuncFinalizadoFornecedor(String idFuncFinalizadoFornecedor) {
        this.idFuncFinalizadoFornecedor = idFuncFinalizadoFornecedor;
    }

    public String getObsFinalizadoFornecedor() {
        return obsFinalizadoFornecedor;
    }

    public void setObsFinalizadoFornecedor(String obsFinalizadoFornecedor) {
        this.obsFinalizadoFornecedor = obsFinalizadoFornecedor;
    }

    public String getIdFuncAprovacaoMetrologia() {
        return idFuncAprovacaoMetrologia;
    }

    public void setIdFuncAprovacaoMetrologia(String idFuncAprovacaoMetrologia) {
        this.idFuncAprovacaoMetrologia = idFuncAprovacaoMetrologia;
    }

    public String getObsAprovacaoMetrologia() {
        return obsAprovacaoMetrologia;
    }

    public void setObsAprovacaoMetrologia(String obsAprovacaoMetrologia) {
        this.obsAprovacaoMetrologia = obsAprovacaoMetrologia;
    }

    public String getIdFuncRejeicaoMetrologia() {
        return idFuncRejeicaoMetrologia;
    }

    public void setIdFuncRejeicaoMetrologia(String idFuncRejeicaoMetrologia) {
        this.idFuncRejeicaoMetrologia = idFuncRejeicaoMetrologia;
    }

    public String getObsRejeicaoMetrologia() {
        return obsRejeicaoMetrologia;
    }

    public void setObsRejeicaoMetrologia(String obsRejeicaoMetrologia) {
        this.obsRejeicaoMetrologia = obsRejeicaoMetrologia;
    }

    public GeSituacaoServTerceiros getIdSituacaoServTerceiros() {
        return idSituacaoServTerceiros;
    }

    public void setIdSituacaoServTerceiros(GeSituacaoServTerceiros idSituacaoServTerceiros) {
        this.idSituacaoServTerceiros = idSituacaoServTerceiros;
    }

    public Date getDataEnvioMetrologia() {
		return dataEnvioMetrologia;
	}

	public void setDataEnvioMetrologia(Date dataEnvioMetrologia) {
		this.dataEnvioMetrologia = dataEnvioMetrologia;
	}

	public Date getDataEnvioRecepcao() {
		return dataEnvioRecepcao;
	}

	public void setDataEnvioRecepcao(Date dataEnvioRecepcao) {
		this.dataEnvioRecepcao = dataEnvioRecepcao;
	}

	public Date getDataEnvioFornecedor() {
		return dataEnvioFornecedor;
	}

	public void setDataEnvioFornecedor(Date dataEnvioFornecedor) {
		this.dataEnvioFornecedor = dataEnvioFornecedor;
	}

	public Date getDataFinalizadoFornecedor() {
		return dataFinalizadoFornecedor;
	}

	public void setDataFinalizadoFornecedor(Date dataFinalizadoFornecedor) {
		this.dataFinalizadoFornecedor = dataFinalizadoFornecedor;
	}

	public Date getDataAprovacaoMetrologia() {
		return dataAprovacaoMetrologia;
	}

	public void setDataAprovacaoMetrologia(Date dataAprovacaoMetrologia) {
		this.dataAprovacaoMetrologia = dataAprovacaoMetrologia;
	}

	public Date getDataRejeicaoMetrologia() {
		return dataRejeicaoMetrologia;
	}

	public void setDataRejeicaoMetrologia(Date dataRejeicaoMetrologia) {
		this.dataRejeicaoMetrologia = dataRejeicaoMetrologia;
	}

	public Date getDataEntradaEnvioMetrologia() {
		return dataEntradaEnvioMetrologia;
	}

	public void setDataEntradaEnvioMetrologia(Date dataEntradaEnvioMetrologia) {
		this.dataEntradaEnvioMetrologia = dataEntradaEnvioMetrologia;
	}

	public String getIdFuncEntradaEnvioMetrologia() {
		return idFuncEntradaEnvioMetrologia;
	}

	public void setIdFuncEntradaEnvioMetrologia(String idFuncEntradaEnvioMetrologia) {
		this.idFuncEntradaEnvioMetrologia = idFuncEntradaEnvioMetrologia;
	}

	public String getObsEntradaEnvioMetrologia() {
		return obsEntradaEnvioMetrologia;
	}

	public void setObsEntradaEnvioMetrologia(String obsEntradaEnvioMetrologia) {
		this.obsEntradaEnvioMetrologia = obsEntradaEnvioMetrologia;
	}

	public Date getDataEnvioNotaFiscal() {
		return dataEnvioNotaFiscal;
	}

	public void setDataEnvioNotaFiscal(Date dataEnvioNotaFiscal) {
		this.dataEnvioNotaFiscal = dataEnvioNotaFiscal;
	}

	public String getIdFuncEnvioNotaFiscal() {
		return idFuncEnvioNotaFiscal;
	}

	public void setIdFuncEnvioNotaFiscal(String idFuncEnvioNotaFiscal) {
		this.idFuncEnvioNotaFiscal = idFuncEnvioNotaFiscal;
	}

	public String getObsEnvioNotaFiscal() {
		return obsEnvioNotaFiscal;
	}

	public void setObsEnvioNotaFiscal(String obsEnvioNotaFiscal) {
		this.obsEnvioNotaFiscal = obsEnvioNotaFiscal;
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
        if (!(object instanceof GeLogSituacaoServTerceiros)) {
            return false;
        }
        GeLogSituacaoServTerceiros other = (GeLogSituacaoServTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeLogSituacaoServTerceiros[ id=" + id + " ]";
    }
    
}

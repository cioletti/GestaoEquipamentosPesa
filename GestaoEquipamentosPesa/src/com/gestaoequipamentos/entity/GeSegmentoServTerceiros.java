/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_SEGMENTO_SERV_TERCEIROS")
public class GeSegmentoServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeSegmentoServTerceirosPK geSegmentoServTerceirosPK;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "QTD")
    private BigDecimal qtd;
    @Column(name = "OBS")
    private String obs;
    @JoinColumn(name = "ID_STATUS_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeStatusServTerceiros idStatusServTerceiros;
    @JoinColumn(name = "ID_FORN_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeFornecedorServTerceiros idFornServTerceiros;
    @OneToMany(mappedBy = "geSegmentoServTerceiros")
    private Collection<GeSituacaoServTerceiros> geSituacaoServTerceirosCollection;
    @Column(name = "LOCAL_SERVICO")
    private String localServico;
    @Column(name = "DATA_PREVISAO_ENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevisaoEntrega;
    @Column(name = "IS_CRIADO_DBS")
    private String isCriadoDbs;
    
    

    public GeSegmentoServTerceiros() {
    }

    public GeSegmentoServTerceiros(GeSegmentoServTerceirosPK geSegmentoServTerceirosPK) {
        this.geSegmentoServTerceirosPK = geSegmentoServTerceirosPK;
    }

    public GeSegmentoServTerceiros(long idSegmento, long idTipoServicoTerceiros) {
        this.geSegmentoServTerceirosPK = new GeSegmentoServTerceirosPK(idSegmento, idTipoServicoTerceiros);
    }

    public GeSegmentoServTerceirosPK getGeSegmentoServTerceirosPK() {
        return geSegmentoServTerceirosPK;
    }

    public void setGeSegmentoServTerceirosPK(GeSegmentoServTerceirosPK geSegmentoServTerceirosPK) {
        this.geSegmentoServTerceirosPK = geSegmentoServTerceirosPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getQtd() {
		return qtd;
	}

	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
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

	public Collection<GeSituacaoServTerceiros> getGeSituacaoServTerceirosCollection() {
		return geSituacaoServTerceirosCollection;
	}

	public void setGeSituacaoServTerceirosCollection(
			Collection<GeSituacaoServTerceiros> geSituacaoServTerceirosCollection) {
		this.geSituacaoServTerceirosCollection = geSituacaoServTerceirosCollection;
	}

	public String getLocalServico() {
		return localServico;
	}

	public void setLocalServico(String localServico) {
		this.localServico = localServico;
	}

	public Date getDataPrevisaoEntrega() {
		return dataPrevisaoEntrega;
	}

	public void setDataPrevisaoEntrega(Date dataPrevisaoEntrega) {
		this.dataPrevisaoEntrega = dataPrevisaoEntrega;
	}

	public String getIsCriadoDbs() {
		return isCriadoDbs;
	}

	public void setIsCriadoDbs(String isCriadoDbs) {
		this.isCriadoDbs = isCriadoDbs;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (geSegmentoServTerceirosPK != null ? geSegmentoServTerceirosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeSegmentoServTerceiros)) {
            return false;
        }
        GeSegmentoServTerceiros other = (GeSegmentoServTerceiros) object;
        if ((this.geSegmentoServTerceirosPK == null && other.geSegmentoServTerceirosPK != null) || (this.geSegmentoServTerceirosPK != null && !this.geSegmentoServTerceirosPK.equals(other.geSegmentoServTerceirosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeSegmentoServTerceiros[ geSegmentoServTerceirosPK=" + geSegmentoServTerceirosPK + " ]";
    }
    
}

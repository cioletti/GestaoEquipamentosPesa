/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_DATA_FLUXO_SEGMENTO")
public class GeDataFluxoSegmento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "ID_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private GeSegmento idSegmento;
    @JoinColumn(name = "ID_LEGENDA_PROCESSO_OFICINA", referencedColumnName = "ID")
    @ManyToOne
    private GeLegendaProcessoOficina idLegendaProcessoOficina;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "OBSERVACAO")
    private String observacao;

    public GeDataFluxoSegmento() {
    }

    public GeDataFluxoSegmento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public GeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(GeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }


    public GeLegendaProcessoOficina getIdLegendaProcessoOficina() {
		return idLegendaProcessoOficina;
	}

	public void setIdLegendaProcessoOficina(
			GeLegendaProcessoOficina idLegendaProcessoOficina) {
		this.idLegendaProcessoOficina = idLegendaProcessoOficina;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
        if (!(object instanceof GeDataFluxoSegmento)) {
            return false;
        }
        GeDataFluxoSegmento other = (GeDataFluxoSegmento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeDataFluxoSegmento[ id=" + id + " ]";
    }
    
}

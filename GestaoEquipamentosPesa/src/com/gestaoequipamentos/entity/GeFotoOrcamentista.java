/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_FOTO_ORCAMENTISTA")

public class GeFotoOrcamentista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "OBS")
    private String obs;
    @Column(name = "DATA")
    private String data;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "ID_CHECK_IN")
    private Long idCheckIn;
    @Column(name = "IMEI")
    private String imei;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;

    public GeFotoOrcamentista() {
    }

    public GeFotoOrcamentista(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Long getIdCheckIn() {
        return idCheckIn;
    }

    public void setIdCheckIn(Long idCheckIn) {
        this.idCheckIn = idCheckIn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getJobControl() {
        return jobControl;
    }

    public void setJobControl(String jobControl) {
        this.jobControl = jobControl;
    }

    public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
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
        if (!(object instanceof GeFotoOrcamentista)) {
            return false;
        }
        GeFotoOrcamentista other = (GeFotoOrcamentista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeFotoOrcamentista[ id=" + id + " ]";
    }
    
}

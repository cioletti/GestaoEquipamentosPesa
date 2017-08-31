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
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_REMOVER_PECA_LOG")

public class GeRemoverPecaLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PART_NUMBER")
    private String partNumber;
    @Column(name = "PART_NAME")
    private String partName;
    @Column(name = "ID_DOC_SEG_OPER")
    private Long idDocSegOper;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "DATA")
    private String data;
    @Column(name = "ID_SEGMENTO")
    private Long idSegmento;
    @Column(name = "NUMERO_SEGEMENTO")
    private String numeroSegemento;

    public GeRemoverPecaLog() {
    }

    public GeRemoverPecaLog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Long getIdDocSegOper() {
        return idDocSegOper;
    }

    public void setIdDocSegOper(Long idDocSegOper) {
        this.idDocSegOper = idDocSegOper;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(Long idSegmento) {
        this.idSegmento = idSegmento;
    }

    public String getNumeroSegemento() {
        return numeroSegemento;
    }

    public void setNumeroSegemento(String numeroSegemento) {
        this.numeroSegemento = numeroSegemento;
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
        if (!(object instanceof GeRemoverPecaLog)) {
            return false;
        }
        GeRemoverPecaLog other = (GeRemoverPecaLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeRemoverPecaLog[ id=" + id + " ]";
    }
    
}

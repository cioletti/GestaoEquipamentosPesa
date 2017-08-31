/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_ARV_INSPECAO")

public class GeArvInspecao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_ARV", nullable = false, unique = true)
    private Long idArv;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "SIM")
    private Character sim;
    @Column(name = "NAO")
    private Character nao;
    @Column(name = "ID_PAI_ROOT")
    private Long idPaiRoot;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "TIPO_MANUTENCAO")
    private String tipoManutencao;
    @Column(name = "SOS")
    private String sos;
    @Column(name = "SMCS")
    private String smcs;
    @OneToMany(mappedBy = "idModelo")
    private Collection<GePrefixo> gePrefixoCollection;
    @JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID")
    @ManyToOne
    private GeFamilia idFamilia;
    @OneToMany(mappedBy = "idPai")
    private Collection<GeArvInspecao> geArvInspecaoCollection;
    @JoinColumn(name = "ID_PAI", referencedColumnName = "ID_ARV")
    @ManyToOne
    private GeArvInspecao idPai;

    public GeArvInspecao() {
    }

    public GeArvInspecao(Long idArv) {
        this.idArv = idArv;
    }

    public Long getIdArv() {
        return idArv;
    }

    public void setIdArv(Long idArv) {
        this.idArv = idArv;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Character getSim() {
        return sim;
    }

    public void setSim(Character sim) {
        this.sim = sim;
    }

    public Character getNao() {
        return nao;
    }

    public void setNao(Character nao) {
        this.nao = nao;
    }

    public Long getIdPaiRoot() {
        return idPaiRoot;
    }

    public void setIdPaiRoot(Long idPaiRoot) {
        this.idPaiRoot = idPaiRoot;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getSmcs() {
        return smcs;
    }

    public void setSmcs(String smcs) {
        this.smcs = smcs;
    }


    public Collection<GePrefixo> getGePrefixoCollection() {
        return gePrefixoCollection;
    }

    public void setGePrefixoCollection(Collection<GePrefixo> gePrefixoCollection) {
        this.gePrefixoCollection = gePrefixoCollection;
    }

    public GeFamilia getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(GeFamilia idFamilia) {
        this.idFamilia = idFamilia;
    }


    public Collection<GeArvInspecao> getGeArvInspecaoCollection() {
        return geArvInspecaoCollection;
    }

    public void setGeArvInspecaoCollection(Collection<GeArvInspecao> geArvInspecaoCollection) {
        this.geArvInspecaoCollection = geArvInspecaoCollection;
    }

    public GeArvInspecao getIdPai() {
        return idPai;
    }

    public void setIdPai(GeArvInspecao idPai) {
        this.idPai = idPai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArv != null ? idArv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeArvInspecao)) {
            return false;
        }
        GeArvInspecao other = (GeArvInspecao) object;
        if ((this.idArv == null && other.idArv != null) || (this.idArv != null && !this.idArv.equals(other.idArv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeArvInspecao[ idArv=" + idArv + " ]";
    }
    
}

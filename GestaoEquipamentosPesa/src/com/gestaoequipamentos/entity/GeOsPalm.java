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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Douglas
 */
@Entity
@Table(name = "GE_OS_PALM")
@NamedQueries({
    @NamedQuery(name = "GeOsPalm.findAll", query = "SELECT g FROM GeOsPalm g")})
public class GeOsPalm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDOS_PALM", nullable = false, unique = true)
    private Long idosPalm;
    @Column(name = "TIPO_OPERACAO")
    private String tipoOperacao;
    @Column(name = "CLIENTE")
    private String cliente;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "SERIE")
    private String serie;
    @Column(name = "CODIGO_EQPTO")
    private String codigoEqpto;
    @Column(name = "SMU")
    private String smu;
    @Column(name = "UN_MEDIDA")
    private String unMedida;
    @Column(name = "RECEBEDOR")
    private String recebedor;
    @Column(name = "TRANSPORTADOR")
    private String transportador;
    @Column(name = "PLACA")
    private String placa;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Column(name = "TECNICO")
    private String tecnico;
    @Column(name = "FILIAL")
    private String filial;
    @Column(name = "IDCLIENTE")
    private String idcliente;
    @Column(name = "EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @Column(name = "HORIMETRO")
    private Long horimetro;
    @Column(name = "TIPOINSPECAO")
    private String tipoinspecao;
    @Column(name = "FAMILIA")
    private String familia;
    @Column(name = "MEDIDOR")
    private Long medidor;
    @Column(name = "TROCAR_PECAS")
    private String trocarPecas;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "CONTATO")
    private String contato;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "ID_AGENDAMENTO")
    private Long idAgendamento;
    @Column(name = "TIPO_MANUTENCAO")
    private String tipoManutencao;
    @Column(name = "EQUIPAMENTO")
    private String equipamento;
    @Column(name = "EMAIL_CONTATO")
    private String emailContato;
    @JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID")
    @ManyToOne
    private GeFamilia idFamilia;

    public GeOsPalm() {
    }

    public GeOsPalm(Long idosPalm) {
        this.idosPalm = idosPalm;
    }

    public Long getIdosPalm() {
        return idosPalm;
    }

    public void setIdosPalm(Long idosPalm) {
        this.idosPalm = idosPalm;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCodigoEqpto() {
        return codigoEqpto;
    }

    public void setCodigoEqpto(String codigoEqpto) {
        this.codigoEqpto = codigoEqpto;
    }

    public String getSmu() {
        return smu;
    }

    public void setSmu(String smu) {
        this.smu = smu;
    }

    public String getUnMedida() {
        return unMedida;
    }

    public void setUnMedida(String unMedida) {
        this.unMedida = unMedida;
    }

    public String getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(String recebedor) {
        this.recebedor = recebedor;
    }

    public String getTransportador() {
        return transportador;
    }

    public void setTransportador(String transportador) {
        this.transportador = transportador;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Long getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(Long horimetro) {
        this.horimetro = horimetro;
    }

    public String getTipoinspecao() {
        return tipoinspecao;
    }

    public void setTipoinspecao(String tipoinspecao) {
        this.tipoinspecao = tipoinspecao;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public Long getMedidor() {
        return medidor;
    }

    public void setMedidor(Long medidor) {
        this.medidor = medidor;
    }

    public String getTrocarPecas() {
        return trocarPecas;
    }

    public void setTrocarPecas(String trocarPecas) {
        this.trocarPecas = trocarPecas;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public GeFamilia getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(GeFamilia idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idosPalm != null ? idosPalm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeOsPalm)) {
            return false;
        }
        GeOsPalm other = (GeOsPalm) object;
        if ((this.idosPalm == null && other.idosPalm != null) || (this.idosPalm != null && !this.idosPalm.equals(other.idosPalm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeOsPalm[ idosPalm=" + idosPalm + " ]";
    }
    
}

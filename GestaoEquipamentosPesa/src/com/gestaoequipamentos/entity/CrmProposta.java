/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gestaoequipamentos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_PROPOSTA")
public class CrmProposta implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_EMS_PROPOSTA")
    private long idEmsProposta;
    @Column(name = "NUM_SERIE")
    private String numSerie;
    @Column(name = "ID_STATUS_OPT")
    private Long idStatusOpt;
    @Column(name = "DATA_OPT")
    private Date dataOpt;
    @Column(name = "ID_FUNCIONARIO_CRM")
    private String idFuncionarioCrm;
    @Column(name = "MATRICULA_FUNCIONARIO_DBS")
    private String matriculaFuncionarioDbs;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "TIPO_CLIENTE")
    private String tipoCliente;
    @Column(name = "COD_CLIENTE_EXT")
    private String codClienteExt;
    @Column(name = "COD_CLIENTE_INTER")
    private String codClienteInter;
    @Column(name = "COD_CLIENTE_GARANTIA")
    private String codClienteGarantia;
    @Column(name = "ID_EQUIPAMENTO")
    private String idEquipamento;
    @Column(name = "DATA_ENVIO")
    private Date dataEnvio;
    @Column(name = "DATA_ACEITE")
    private Date dataAceite;
    @Column(name = "DATA_FINALIZACAO")
    private Date dataFinalizacao;
    @Column(name = "DATA_REJEICAO")
    private Date dataRejeicao;
    @Column(name = "FATOR_CLIENTE")
    private Long fatorCliente;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "ID_FAMILIA_CAMPO")
    private BigDecimal idFamiliaCampo;
    @Column(name = "PREFIXO")
    private String prefixo;
    @Column(name = "ID_FAMILIA_OFICINA")
    private BigDecimal idFamiliaOficina;
    @Column(name = "POSSUI_SUB_TRIBUTARIA")
    private String possuiSubTributaria;
    @Column(name = "IS_FIND_SUB_TRIBUTARIA")
    private String isFindSubTributaria;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "TELEFONE")
    private String telefone;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Column(name = "FATOR_URGENCIA")
    private String fatorUrgencia;
    @Column(name = "OBS_MOTIVO_PERDA")
    private String obsMotivoPerda;
    @Column(name = "HORIMETRO")
    private BigDecimal horimetro;
    @Column(name = "IS_LOCK")
    private String isLock;
    @Column(name = "ID_FUNCIONARIO_LOCK")
    private String idFuncionarioLock;
    @Column(name = "ID_FUNCIONARIO_ADM")
    private String idFuncionarioAdm;
    @Column(name = "ESTIMATE_BY_FUNCIONARIO_LOCK")
    private String estimateByFuncionarioLock;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_MAO_DE_OBRA")
    private BigDecimal valorMaoDeObra;
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Column(name = "TOTAL_ORCAMENTO")
    private BigDecimal totalOrcamento;
    @Column(name = "VALOR_DESLOCAMENTO")
    private BigDecimal valorDeslocamento;
    @JoinColumn(name = "ID_TIPO_OPT", referencedColumnName = "ID")
    @ManyToOne
    private EmsTipoOportunidade idTipoOpt;
    @JoinColumn(name = "ID_TIPO_PROPOSTA", referencedColumnName = "ID")
    @ManyToOne
    private CrmTipoProposta idTipoProposta;
    @JoinColumn(name = "ID_MOTIVO_PERDA", referencedColumnName = "ID")
    @ManyToOne
    private CrmMotivoPerda idMotivoPerda;
    @JoinColumn(name = "ID_FASE_NEGOCIACAO", referencedColumnName = "ID")
    @ManyToOne
    private CrmFaseNegociacao idFaseNegociacao;
    @JoinColumn(name = "ID_CLASSIFICACAO", referencedColumnName = "ID")
    @ManyToOne
    private CrmClassificacao idClassificacao;
    @JoinColumn(name = "ID_PROPOSTA_PAI", referencedColumnName = "ID")
    @ManyToOne
    private CrmProposta idPropostaPai;

    public String getObsMotivoPerda() {
        return obsMotivoPerda;
    }

    public void setObsMotivoPerda(String obsMotivoPerda) {
        this.obsMotivoPerda = obsMotivoPerda;
    }

    public String getIdFuncionarioAdm() {
        return idFuncionarioAdm;
    }

    public void setIdFuncionarioAdm(String idFuncionarioAdm) {
        this.idFuncionarioAdm = idFuncionarioAdm;
    }

    public CrmProposta getIdPropostaPai() {
        return idPropostaPai;
    }

    public void setIdPropostaPai(CrmProposta idPropostaPai) {
        this.idPropostaPai = idPropostaPai;
    }

    public CrmProposta() {
    }

    public CrmProposta(Long id) {
        this.id = id;
    }

    public CrmProposta(Long id, long idEmsProposta) {
        this.id = id;
        this.idEmsProposta = idEmsProposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdEmsProposta() {
        return idEmsProposta;
    }

    public void setIdEmsProposta(long idEmsProposta) {
        this.idEmsProposta = idEmsProposta;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public Long getIdStatusOpt() {
        return idStatusOpt;
    }

    public void setIdStatusOpt(Long idStatusOpt) {
        this.idStatusOpt = idStatusOpt;
    }

    public Date getDataOpt() {
        return dataOpt;
    }

    public void setDataOpt(Date dataOpt) {
        this.dataOpt = dataOpt;
    }

    public String getIdFuncionarioCrm() {
        return idFuncionarioCrm;
    }

    public void setIdFuncionarioCrm(String idFuncionarioCrm) {
        this.idFuncionarioCrm = idFuncionarioCrm;
    }

    public String getMatriculaFuncionarioDbs() {
        return matriculaFuncionarioDbs;
    }

    public void setMatriculaFuncionarioDbs(String matriculaFuncionarioDbs) {
        this.matriculaFuncionarioDbs = matriculaFuncionarioDbs;
    }

    public String getJobControl() {
        return jobControl;
    }

    public void setJobControl(String jobControl) {
        this.jobControl = jobControl;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCodClienteExt() {
        return codClienteExt;
    }

    public void setCodClienteExt(String codClienteExt) {
        this.codClienteExt = codClienteExt;
    }

    public String getCodClienteInter() {
        return codClienteInter;
    }

    public void setCodClienteInter(String codClienteInter) {
        this.codClienteInter = codClienteInter;
    }

    public String getCodClienteGarantia() {
        return codClienteGarantia;
    }

    public void setCodClienteGarantia(String codClienteGarantia) {
        this.codClienteGarantia = codClienteGarantia;
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataAceite() {
        return dataAceite;
    }

    public void setDataAceite(Date dataAceite) {
        this.dataAceite = dataAceite;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public Date getDataRejeicao() {
        return dataRejeicao;
    }

    public void setDataRejeicao(Date dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }

    public Long getFatorCliente() {
        return fatorCliente;
    }

    public void setFatorCliente(Long fatorCliente) {
        this.fatorCliente = fatorCliente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getIdFamiliaCampo() {
        return idFamiliaCampo;
    }

    public void setIdFamiliaCampo(BigDecimal idFamiliaCampo) {
        this.idFamiliaCampo = idFamiliaCampo;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public BigDecimal getIdFamiliaOficina() {
        return idFamiliaOficina;
    }

    public void setIdFamiliaOficina(BigDecimal idFamiliaOficina) {
        this.idFamiliaOficina = idFamiliaOficina;
    }

    public String getPossuiSubTributaria() {
        return possuiSubTributaria;
    }

    public void setPossuiSubTributaria(String possuiSubTributaria) {
        this.possuiSubTributaria = possuiSubTributaria;
    }

    public String getIsFindSubTributaria() {
        return isFindSubTributaria;
    }

    public void setIsFindSubTributaria(String isFindSubTributaria) {
        this.isFindSubTributaria = isFindSubTributaria;
    }

    public Long getFilial() {
        return filial;
    }

    public void setFilial(Long filial) {
        this.filial = filial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getFatorUrgencia() {
        return fatorUrgencia;
    }

    public void setFatorUrgencia(String fatorUrgencia) {
        this.fatorUrgencia = fatorUrgencia;
    }

    public BigDecimal getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(BigDecimal horimetro) {
        this.horimetro = horimetro;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getIdFuncionarioLock() {
        return idFuncionarioLock;
    }

    public void setIdFuncionarioLock(String idFuncionarioLock) {
        this.idFuncionarioLock = idFuncionarioLock;
    }

    public String getEstimateByFuncionarioLock() {
        return estimateByFuncionarioLock;
    }

    public void setEstimateByFuncionarioLock(String estimateByFuncionarioLock) {
        this.estimateByFuncionarioLock = estimateByFuncionarioLock;
    }

    public BigDecimal getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public BigDecimal getTotalOrcamento() {
        return totalOrcamento;
    }

    public void setTotalOrcamento(BigDecimal totalOrcamento) {
        this.totalOrcamento = totalOrcamento;
    }

    public BigDecimal getValorDeslocamento() {
        return valorDeslocamento;
    }

    public void setValorDeslocamento(BigDecimal valorDeslocamento) {
        this.valorDeslocamento = valorDeslocamento;
    }

    public EmsTipoOportunidade getIdTipoOpt() {
        return idTipoOpt;
    }

    public void setIdTipoOpt(EmsTipoOportunidade idTipoOpt) {
        this.idTipoOpt = idTipoOpt;
    }

    public CrmTipoProposta getIdTipoProposta() {
        return idTipoProposta;
    }

    public void setIdTipoProposta(CrmTipoProposta idTipoProposta) {
        this.idTipoProposta = idTipoProposta;
    }

    public CrmMotivoPerda getIdMotivoPerda() {
        return idMotivoPerda;
    }

    public void setIdMotivoPerda(CrmMotivoPerda idMotivoPerda) {
        this.idMotivoPerda = idMotivoPerda;
    }

    public CrmFaseNegociacao getIdFaseNegociacao() {
        return idFaseNegociacao;
    }

    public void setIdFaseNegociacao(CrmFaseNegociacao idFaseNegociacao) {
        this.idFaseNegociacao = idFaseNegociacao;
    }

    public CrmClassificacao getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(CrmClassificacao idClassificacao) {
        this.idClassificacao = idClassificacao;
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
        if (!(object instanceof CrmProposta)) {
            return false;
        }
        CrmProposta other = (CrmProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.entity.CrmProposta[ id=" + id + " ]";
    }
    
}

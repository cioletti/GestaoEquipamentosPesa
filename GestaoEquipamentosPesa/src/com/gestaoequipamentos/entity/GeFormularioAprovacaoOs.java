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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RODRIGO
 */
@Entity
@Table(name = "GE_FORMULARIO_APROVACAO_OS")
public class GeFormularioAprovacaoOs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ARRANJO_MAQUINA")
    private String arranjoMaquina;
    @Column(name = "NOME_CONTATO")
    private String nomeContato;
    @Column(name = "TEL_CONCATO")
    private String telConcato;
    @Column(name = "HORIMETRO_PECA")
    private Long horimetroPeca;
    @Column(name = "ENTREGA_TECNICA")
    private String entregaTecnica;
    @Column(name = "MAQUINA_NOVA")
    private String maquinaNova;
    @Column(name = "PECA_VEND_BALCAO")
    private String pecaVendBalcao;
    @Column(name = "PECA_VEND_OS")
    private String pecaVendOs;
    @Column(name = "SERVICO_REFEITO")
    private String servicoRefeito;
    @Column(name = "NR_NF_VENDA")
    private String nrNfVenda;
    @Column(name = "NR_OS_ANTERIOR")
    private String nrOsAnterior;
    @Column(name = "MAQUINA_USADA")
    private String maquinaUsada;
    @Column(name = "PESA_L")
    private String pesaL;
    @Column(name = "DATA_VENDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVenda;
    @Column(name = "DATA_ENTREGA_TECNICA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntregaTecnica;
    @Column(name = "OS_CUSTOMIZACAO")
    private String osCustomizacao;
    @Lob
    @Column(name = "DESC_COMPONENTE")
    private String descComponente;
    @Lob
    @Column(name = "DESC_SINTOMAS_MAQUINA")
    private String descSintomasMaquina;
    @Lob
    @Column(name = "DESCRICAO_REPARO")
    private String descricaoReparo;
    @Column(name = "FOTO_COMPONENTE")
    private String fotoComponente;
    @Column(name = "AMOSTRA_OLEO_SOS")
    private String amostraOleoSos;
    @Column(name = "TESTE_LEAKOFF")
    private String testeLeakoff;
    @Column(name = "DOWNLOAD_SERVICE_REPORT_ET")
    private String downloadServiceReportEt;
    @Column(name = "INSPECAO_MATERIAL_RODANTE_CTPS")
    private String inspecaoMaterialRodanteCtps;
    @Column(name = "RELATORIO_CONSUMO_OELO_LUBRIFICANTE")
    private String relatorioConsumoOeloLubrificante;
    @Column(name = "FALTOU_FERRAMENTO")
    private String faltouFerramento;
    @Column(name = "CONSULTOU_MANUAL_SERVICO")
    private String consultouManualServico;
    @Column(name = "TEMPO_REPARO_SUFICIENTE")
    private String tempoReparoSuficiente;
    @Column(name = "COMPONENTE_PASSOU_TESTE")
    private String componentePassouTeste;
    @Column(name = "RECEBEU_TREINAMENTO")
    private String recebeuTreinamento;
    @Column(name = "RECEBEU_INFORMACAO_COMPLETA_REPARO")
    private String recebeuInformacaoCompletaReparo;
    @Column(name = "FALHA_GERADA_DIAGNOSTICO_INCORRETO")
    private String falhaGeradaDiagnosticoIncorreto;
    @Column(name = "CLIENTE_INFLUENCIOU_PROPOSTA")
    private String clienteInfluenciouProposta;
    @Column(name = "FOI_CONSULTADO_SERVICO_OS_ANTERIOR")
    private String foiConsultadoServicoOsAnterior;
    @Column(name = "FOI_CORTADO_PECAS_ORCAMENTO_ANTERIOR")
    private String foiCortadoPecasOrcamentoAnterior;
    @Column(name = "ID_CHECK_IN")
    private Long idCheckIn;
    @Column(name = "TIPO_SOLICITACAO")
    private String tipoSolicitacao;
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CODIGO_CLIENTE")
    private String codigoCliente;
    @Column(name = "CLIENTE")
    private String cliente;
    @Column(name = "SERIE")
    private String serie;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "IS_USADO")
    private String isUsado;
    
    
    @Column(name = "APLICACAO")
    private String aplicacao;
    @Column(name = "OF_CAT_EQUIP")
    private String OF_CAT_EQUIP;
    @Column(name = "OF_NON_CAT_EQUIP")
    private String OF_NON_CAT_EQUIP;
    @Column(name = "EQUIP_DELIVERY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date EQUIP_DELIVERY_DATE;
    @Column(name = "ANUAL_PARTS_SPEND")
    private BigDecimal ANUAL_PARTS_SPEND;
    @Column(name = "ANNUAL_SERVICE_SPEND")
    private BigDecimal ANNUAL_SERVICE_SPEND;
    @Column(name = "P_N_CAUSING_FAILURE")
    private String P_N_CAUSING_FAILURE;
    @Column(name = "GROUP_CAUSING_FAILURE")
    private String GROUP_CAUSING_FAILURE;
    @Column(name = "MACHINE_HOURS")
    private String MACHINE_HOURS;
    @Column(name = "DATE_OF_FAILURE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date DATE_OF_FAILURE;
    @Column(name = "PART_HOURS")
    private BigDecimal PART_HOURS;
    @Column(name = "COMPLAINT")
    private String COMPLAINT;
    @Column(name = "ROOT_CAUSE")
    private String ROOT_CAUSE;
    @Column(name = "REPAIR_COMPLICATIONS")
    private String REPAIR_COMPLICATIONS;
    @Column(name = "CORRECTIVE_ACTIONS")
    private String CORRECTIVE_ACTIONS;
    @Column(name = "TOTAL_CUSTOMER_LIST_PARTS")
    private BigDecimal TOTAL_CUSTOMER_LIST_PARTS;
    @Column(name = "TOTAL_CUSTOMER_LIST_LABOR")
    private BigDecimal TOTAL_CUSTOMER_LIST_LABOR;
    @Column(name = "TOTAL_CUSTOMER_LIST_MISC")
    private BigDecimal TOTAL_CUSTOMER_LIST_MISC;
    @Column(name = "TOTAL_DEALER_NET_PARTS")
    private BigDecimal TOTAL_DEALER_NET_PARTS;
    @Column(name = "TOTAL_DEALER_NET_LABOR")
    private BigDecimal TOTAL_DEALER_NET_LABOR;
    @Column(name = "TOTAL_DEALER_NET_MISC")
    private BigDecimal TOTAL_DEALER_NET_MISC;
    @Column(name = "NOTES")
    private String NOTES;
    @Column(name = "JUSTIFICATIVA_CONCESSAO")
    private String JUSTIFICATIVA_CONCESSAO;
    @Column(name = "EMAIL_CONTATO")
    private String emailContato;
    @Column(name = "MATERIAL")
    private String material;
    @Column(name = "ID_FUNCIONARIO_RESPONSAVEL")
    private String idFuncionarioResponsavel;
    @Column(name = "DATA_INCLUSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "TIPO_SISTEMA")
    private String tipoSistema;
    
    @Column(name = "NOME_FILIAL")
    private String nomeFilial;
    @Column(name = "CLIENTE_PESA")
    private String clientePesa;
    
    
    
    @Column(name = "OBS_REJEICAO")
    private String obsRejeicao;
    @Column(name = "OUTROS")
    private String outros;
    @Column(name = "DATA_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    
    
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    
    
    @Column(name = "MAQUINA_PAROU")
    private String maquinaParou;
    
    
    
    public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(String horimetro) {
		this.horimetro = horimetro;
	}

	@Column(name = "HORIMETRO")
    private String horimetro;
    
    
    public String getOF_CAT_EQUIP() {
		return OF_CAT_EQUIP;
	}

	public void setOF_CAT_EQUIP(String oF_CAT_EQUIP) {
		OF_CAT_EQUIP = oF_CAT_EQUIP;
	}

	public String getOF_NON_CAT_EQUIP() {
		return OF_NON_CAT_EQUIP;
	}

	public void setOF_NON_CAT_EQUIP(String oF_NON_CAT_EQUIP) {
		OF_NON_CAT_EQUIP = oF_NON_CAT_EQUIP;
	}

	public Date getEQUIP_DELIVERY_DATE() {
		return EQUIP_DELIVERY_DATE;
	}

	public void setEQUIP_DELIVERY_DATE(Date eQUIP_DELIVERY_DATE) {
		EQUIP_DELIVERY_DATE = eQUIP_DELIVERY_DATE;
	}

	public BigDecimal getANUAL_PARTS_SPEND() {
		return ANUAL_PARTS_SPEND;
	}

	public void setANUAL_PARTS_SPEND(BigDecimal aNUAL_PARTS_SPEND) {
		ANUAL_PARTS_SPEND = aNUAL_PARTS_SPEND;
	}

	public BigDecimal getANNUAL_SERVICE_SPEND() {
		return ANNUAL_SERVICE_SPEND;
	}

	public void setANNUAL_SERVICE_SPEND(BigDecimal aNNUAL_SERVICE_SPEND) {
		ANNUAL_SERVICE_SPEND = aNNUAL_SERVICE_SPEND;
	}

	public String getP_N_CAUSING_FAILURE() {
		return P_N_CAUSING_FAILURE;
	}

	public void setP_N_CAUSING_FAILURE(String p_N_CAUSING_FAILURE) {
		P_N_CAUSING_FAILURE = p_N_CAUSING_FAILURE;
	}

	public String getGROUP_CAUSING_FAILURE() {
		return GROUP_CAUSING_FAILURE;
	}

	public void setGROUP_CAUSING_FAILURE(String gROUP_CAUSING_FAILURE) {
		GROUP_CAUSING_FAILURE = gROUP_CAUSING_FAILURE;
	}

	public String getMACHINE_HOURS() {
		return MACHINE_HOURS;
	}

	public void setMACHINE_HOURS(String mACHINE_HOURS) {
		MACHINE_HOURS = mACHINE_HOURS;
	}

	public Date getDATE_OF_FAILURE() {
		return DATE_OF_FAILURE;
	}

	public void setDATE_OF_FAILURE(Date dATE_OF_FAILURE) {
		DATE_OF_FAILURE = dATE_OF_FAILURE;
	}

	public BigDecimal getPART_HOURS() {
		return PART_HOURS;
	}

	public void setPART_HOURS(BigDecimal pART_HOURS) {
		PART_HOURS = pART_HOURS;
	}

	public String getCOMPLAINT() {
		return COMPLAINT;
	}

	public void setCOMPLAINT(String cOMPLAINT) {
		COMPLAINT = cOMPLAINT;
	}

	public String getROOT_CAUSE() {
		return ROOT_CAUSE;
	}

	public void setROOT_CAUSE(String rOOT_CAUSE) {
		ROOT_CAUSE = rOOT_CAUSE;
	}

	public String getREPAIR_COMPLICATIONS() {
		return REPAIR_COMPLICATIONS;
	}

	public void setREPAIR_COMPLICATIONS(String rEPAIR_COMPLICATIONS) {
		REPAIR_COMPLICATIONS = rEPAIR_COMPLICATIONS;
	}

	public String getCORRECTIVE_ACTIONS() {
		return CORRECTIVE_ACTIONS;
	}

	public void setCORRECTIVE_ACTIONS(String cORRECTIVE_ACTIONS) {
		CORRECTIVE_ACTIONS = cORRECTIVE_ACTIONS;
	}

	public BigDecimal getTOTAL_CUSTOMER_LIST_PARTS() {
		return TOTAL_CUSTOMER_LIST_PARTS;
	}

	public void setTOTAL_CUSTOMER_LIST_PARTS(BigDecimal tOTAL_CUSTOMER_LIST_PARTS) {
		TOTAL_CUSTOMER_LIST_PARTS = tOTAL_CUSTOMER_LIST_PARTS;
	}

	public BigDecimal getTOTAL_CUSTOMER_LIST_LABOR() {
		return TOTAL_CUSTOMER_LIST_LABOR;
	}

	public void setTOTAL_CUSTOMER_LIST_LABOR(BigDecimal tOTAL_CUSTOMER_LIST_LABOR) {
		TOTAL_CUSTOMER_LIST_LABOR = tOTAL_CUSTOMER_LIST_LABOR;
	}

	public BigDecimal getTOTAL_CUSTOMER_LIST_MISC() {
		return TOTAL_CUSTOMER_LIST_MISC;
	}

	public void setTOTAL_CUSTOMER_LIST_MISC(BigDecimal tOTAL_CUSTOMER_LIST_MISC) {
		TOTAL_CUSTOMER_LIST_MISC = tOTAL_CUSTOMER_LIST_MISC;
	}

	public BigDecimal getTOTAL_DEALER_NET_PARTS() {
		return TOTAL_DEALER_NET_PARTS;
	}

	public void setTOTAL_DEALER_NET_PARTS(BigDecimal tOTAL_DEALER_NET_PARTS) {
		TOTAL_DEALER_NET_PARTS = tOTAL_DEALER_NET_PARTS;
	}

	public BigDecimal getTOTAL_DEALER_NET_LABOR() {
		return TOTAL_DEALER_NET_LABOR;
	}

	public void setTOTAL_DEALER_NET_LABOR(BigDecimal tOTAL_DEALER_NET_LABOR) {
		TOTAL_DEALER_NET_LABOR = tOTAL_DEALER_NET_LABOR;
	}

	public BigDecimal getTOTAL_DEALER_NET_MISC() {
		return TOTAL_DEALER_NET_MISC;
	}

	public void setTOTAL_DEALER_NET_MISC(BigDecimal tOTAL_DEALER_NET_MISC) {
		TOTAL_DEALER_NET_MISC = tOTAL_DEALER_NET_MISC;
	}

	public String getNOTES() {
		return NOTES;
	}

	public void setNOTES(String nOTES) {
		NOTES = nOTES;
	}

	public String getJUSTIFICATIVA_CONCESSAO() {
		return JUSTIFICATIVA_CONCESSAO;
	}

	public void setJUSTIFICATIVA_CONCESSAO(String jUSTIFICATIVA_CONCESSAO) {
		JUSTIFICATIVA_CONCESSAO = jUSTIFICATIVA_CONCESSAO;
	}

    public String getIsUsado() {
		return isUsado;
	}

	public void setIsUsado(String isUsado) {
		this.isUsado = isUsado;
	}

	public GeFormularioAprovacaoOs() {
    }

    public GeFormularioAprovacaoOs(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArranjoMaquina() {
        return arranjoMaquina;
    }

    public void setArranjoMaquina(String arranjoMaquina) {
        this.arranjoMaquina = arranjoMaquina;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelConcato() {
        return telConcato;
    }

    public void setTelConcato(String telConcato) {
        this.telConcato = telConcato;
    }

    public Long getHorimetroPeca() {
        return horimetroPeca;
    }

    public void setHorimetroPeca(Long horimetroPeca) {
        this.horimetroPeca = horimetroPeca;
    }

    public String getEntregaTecnica() {
        return entregaTecnica;
    }

    public void setEntregaTecnica(String entregaTecnica) {
        this.entregaTecnica = entregaTecnica;
    }

    public String getMaquinaNova() {
        return maquinaNova;
    }

    public void setMaquinaNova(String maquinaNova) {
        this.maquinaNova = maquinaNova;
    }

    public String getPecaVendBalcao() {
        return pecaVendBalcao;
    }

    public void setPecaVendBalcao(String pecaVendBalcao) {
        this.pecaVendBalcao = pecaVendBalcao;
    }

    public String getPecaVendOs() {
        return pecaVendOs;
    }

    public void setPecaVendOs(String pecaVendOs) {
        this.pecaVendOs = pecaVendOs;
    }

    public String getServicoRefeito() {
        return servicoRefeito;
    }

    public void setServicoRefeito(String servicoRefeito) {
        this.servicoRefeito = servicoRefeito;
    }

    public String getNrNfVenda() {
        return nrNfVenda;
    }

    public void setNrNfVenda(String nrNfVenda) {
        this.nrNfVenda = nrNfVenda;
    }

    public String getNrOsAnterior() {
        return nrOsAnterior;
    }

    public void setNrOsAnterior(String nrOsAnterior) {
        this.nrOsAnterior = nrOsAnterior;
    }

    public String getMaquinaUsada() {
        return maquinaUsada;
    }

    public void setMaquinaUsada(String maquinaUsada) {
        this.maquinaUsada = maquinaUsada;
    }

    public String getPesaL() {
        return pesaL;
    }

    public void setPesaL(String pesaL) {
        this.pesaL = pesaL;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataEntregaTecnica() {
        return dataEntregaTecnica;
    }

    public void setDataEntregaTecnica(Date dataEntregaTecnica) {
        this.dataEntregaTecnica = dataEntregaTecnica;
    }

    public String getOsCustomizacao() {
        return osCustomizacao;
    }

    public void setOsCustomizacao(String osCustomizacao) {
        this.osCustomizacao = osCustomizacao;
    }

    public String getDescComponente() {
        return descComponente;
    }

    public void setDescComponente(String descComponente) {
        this.descComponente = descComponente;
    }

    public String getDescSintomasMaquina() {
        return descSintomasMaquina;
    }

    public void setDescSintomasMaquina(String descSintomasMaquina) {
        this.descSintomasMaquina = descSintomasMaquina;
    }

    public String getDescricaoReparo() {
        return descricaoReparo;
    }

    public void setDescricaoReparo(String descricaoReparo) {
        this.descricaoReparo = descricaoReparo;
    }

    public String getFotoComponente() {
        return fotoComponente;
    }

    public void setFotoComponente(String fotoComponente) {
        this.fotoComponente = fotoComponente;
    }

    public String getAmostraOleoSos() {
        return amostraOleoSos;
    }

    public void setAmostraOleoSos(String amostraOleoSos) {
        this.amostraOleoSos = amostraOleoSos;
    }

    public String getTesteLeakoff() {
        return testeLeakoff;
    }

    public void setTesteLeakoff(String testeLeakoff) {
        this.testeLeakoff = testeLeakoff;
    }

    public String getDownloadServiceReportEt() {
        return downloadServiceReportEt;
    }

    public void setDownloadServiceReportEt(String downloadServiceReportEt) {
        this.downloadServiceReportEt = downloadServiceReportEt;
    }

    public String getInspecaoMaterialRodanteCtps() {
        return inspecaoMaterialRodanteCtps;
    }

    public void setInspecaoMaterialRodanteCtps(String inspecaoMaterialRodanteCtps) {
        this.inspecaoMaterialRodanteCtps = inspecaoMaterialRodanteCtps;
    }

    public String getRelatorioConsumoOeloLubrificante() {
        return relatorioConsumoOeloLubrificante;
    }

    public void setRelatorioConsumoOeloLubrificante(String relatorioConsumoOeloLubrificante) {
        this.relatorioConsumoOeloLubrificante = relatorioConsumoOeloLubrificante;
    }

    public String getFaltouFerramento() {
        return faltouFerramento;
    }

    public void setFaltouFerramento(String faltouFerramento) {
        this.faltouFerramento = faltouFerramento;
    }

    public String getConsultouManualServico() {
        return consultouManualServico;
    }

    public void setConsultouManualServico(String consultouManualServico) {
        this.consultouManualServico = consultouManualServico;
    }

    public String getTempoReparoSuficiente() {
        return tempoReparoSuficiente;
    }

    public void setTempoReparoSuficiente(String tempoReparoSuficiente) {
        this.tempoReparoSuficiente = tempoReparoSuficiente;
    }

    public String getComponentePassouTeste() {
        return componentePassouTeste;
    }

    public void setComponentePassouTeste(String componentePassouTeste) {
        this.componentePassouTeste = componentePassouTeste;
    }

    public String getRecebeuTreinamento() {
        return recebeuTreinamento;
    }

    public void setRecebeuTreinamento(String recebeuTreinamento) {
        this.recebeuTreinamento = recebeuTreinamento;
    }

    public String getRecebeuInformacaoCompletaReparo() {
        return recebeuInformacaoCompletaReparo;
    }

    public void setRecebeuInformacaoCompletaReparo(String recebeuInformacaoCompletaReparo) {
        this.recebeuInformacaoCompletaReparo = recebeuInformacaoCompletaReparo;
    }

    public String getFalhaGeradaDiagnosticoIncorreto() {
        return falhaGeradaDiagnosticoIncorreto;
    }

    public void setFalhaGeradaDiagnosticoIncorreto(String falhaGeradaDiagnosticoIncorreto) {
        this.falhaGeradaDiagnosticoIncorreto = falhaGeradaDiagnosticoIncorreto;
    }

    public String getClienteInfluenciouProposta() {
        return clienteInfluenciouProposta;
    }

    public void setClienteInfluenciouProposta(String clienteInfluenciouProposta) {
        this.clienteInfluenciouProposta = clienteInfluenciouProposta;
    }

    public String getFoiConsultadoServicoOsAnterior() {
        return foiConsultadoServicoOsAnterior;
    }

    public void setFoiConsultadoServicoOsAnterior(String foiConsultadoServicoOsAnterior) {
        this.foiConsultadoServicoOsAnterior = foiConsultadoServicoOsAnterior;
    }

    public String getFoiCortadoPecasOrcamentoAnterior() {
        return foiCortadoPecasOrcamentoAnterior;
    }

    public void setFoiCortadoPecasOrcamentoAnterior(String foiCortadoPecasOrcamentoAnterior) {
        this.foiCortadoPecasOrcamentoAnterior = foiCortadoPecasOrcamentoAnterior;
    }

    public Long getIdCheckIn() {
        return idCheckIn;
    }

    public void setIdCheckIn(Long idCheckIn) {
        this.idCheckIn = idCheckIn;
    }

    public String getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getIdFuncionarioResponsavel() {
		return idFuncionarioResponsavel;
	}

	public void setIdFuncionarioResponsavel(String idFuncionarioResponsavel) {
		this.idFuncionarioResponsavel = idFuncionarioResponsavel;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(String aplicacao) {
		this.aplicacao = aplicacao;
	}

	public String getObsRejeicao() {
		return obsRejeicao;
	}

	public void setObsRejeicao(String obsRejeicao) {
		this.obsRejeicao = obsRejeicao;
	}

	public Long getFilial() {
		return filial;
	}

	public void setFilial(Long filial) {
		this.filial = filial;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public String getTipoSistema() {
		return tipoSistema;
	}

	public void setTipoSistema(String tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public String getClientePesa() {
		return clientePesa;
	}

	public void setClientePesa(String clientePesa) {
		this.clientePesa = clientePesa;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMaquinaParou() {
		return maquinaParou;
	}

	public void setMaquinaParou(String maquinaParou) {
		this.maquinaParou = maquinaParou;
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
        if (!(object instanceof GeFormularioAprovacaoOs)) {
            return false;
        }
        GeFormularioAprovacaoOs other = (GeFormularioAprovacaoOs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.GeFormularioAprovacaoOs[ id=" + id + " ]";
    }
    
}

package com.gestaoequipamentos.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gestaoequipamentos.entity.GeFormularioAprovacaoOs;
import com.gestaoequipamentos.entity.GePecasAprovacao;
import com.gestaoequipamentos.util.ValorMonetarioHelper;


public class GeFormularioAprovacaoOsBean {
	 private static final long serialVersionUID = 1L;
	    private Long id;
	    private String arranjoMaquina;
	    private String nomeContato;
	    private String telConcato;
	    private Long horimetroPeca;
	    private String entregaTecnica;
	    private String maquinaNova;
	    private String pecaVendBalcao;
	    private String pecaVendOs;
	    private String servicoRefeito;
	    private String nrNfVenda;
	    private String nrOsAnterior;
	    private String maquinaUsada;
	    private String pesaL;
	    private String dataVenda;
	    private String dataEntregaTecnica;
	    private String osCustomizacao;
	    private String descComponente;
	    private String descSintomasMaquina;
	    private String descricaoReparo;
	    private String fotoComponente;
	    private String amostraOleoSos;
	    private String testeLeakoff;
	    private String downloadServiceReportEt;
	    private String inspecaoMaterialRodanteCtps;
	    private String relatorioConsumoOeloLubrificante;
	    private String faltouFerramento;
	    private String consultouManualServico;
	    private String tempoReparoSuficiente;
	    private String componentePassouTeste;
	    private String recebeuTreinamento;
	    private String recebeuInformacaoCompletaReparo;
	    private String falhaGeradaDiagnosticoIncorreto;
	    private String clienteInfluenciouProposta;
	    private String foiConsultadoServicoOsAnterior;
	    private String foiCortadoPecasOrcamentoAnterior;
	    private Long idCheckIn;
	    private String tipoSolicitacao;
	    private String status;
	    private String codigoCliente;
	    private String cliente;
	    private String serie;
	    private String modelo;
	    
	    private String aplication;
	    private String ofCatEquip;
	    private String ofNonCatEquip;
	    private String equipDeliveryDate;
	    private String anualPartsSpend;
	    private String anualServiceSpend;
	    private String pnCauseFailuret;
	    private String groupCausingFailure;
	    private String machineHours;
	    private String dateOfFailure;
	    private Long partHours;
	    private String complaint;
	    private String rootCauseTxt;
	    private String repairComplications;
	    private String correctiveActions;
	    private String partsCustomer;
	    private String laborCustomer;
	    private String miscCustomer;
	    private String partsDealer;
	    private String laborDealer;
	    private String miscDealer;
	    private String notes;
	    private String justificativaConcessao;
	    private List<PecasAprovacaoOSBean> listaPecasAprovacao;
	    
	    private String emailContato;
		private String material;
		private String horimetro;
	    private String obsRejeicao;
	    private String nomeFilial;
	    private String tipoSistema;
	    private String outros;
	    private String filialCriacaoOS;
	    private String clientePesa;
	    private String responsavel;
	    private String dataCriacao;
	    private String numOs;
	    private String funcAprovador;
	    private String maquinaParou;
		public SimpleDateFormat getFmtPtBr() {
			return fmtPtBr;
		}

		public void setFmtPtBr(SimpleDateFormat fmtPtBr) {
			this.fmtPtBr = fmtPtBr;
		}

	    public GeFormularioAprovacaoOsBean() {
	    	listaPecasAprovacao = new ArrayList<PecasAprovacaoOSBean>();
	    }

	    public GeFormularioAprovacaoOsBean(Long id) {
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

	    public String getDataVenda() {
	        return dataVenda;
	    }

	    public void setDataVenda(String dataVenda) {
	        this.dataVenda = dataVenda;
	    }

	    public String getDataEntregaTecnica() {
	        return dataEntregaTecnica;
	    }

	    public void setDataEntregaTecnica(String dataEntregaTecnica) {
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
			this.serie = serie.toUpperCase();
		}

		public String getModelo() {
			return modelo;
		}

		public void setModelo(String modelo) {
			this.modelo = modelo.toUpperCase();
		}
				public String getAplication() {
			return aplication;
		}

		public void setAplication(String aplication) {
			this.aplication = aplication;
		}

		public String getOfCatEquip() {
			return ofCatEquip;
		}

		public void setOfCatEquip(String ofCatEquip) {
			this.ofCatEquip = ofCatEquip;
		}

		public String getOfNonCatEquip() {
			return ofNonCatEquip;
		}

		public void setOfNonCatEquip(String ofNonCatEquip) {
			this.ofNonCatEquip = ofNonCatEquip;
		}

		public String getEquipDeliveryDate() {
			return equipDeliveryDate;
		}

		public void setEquipDeliveryDate(String equipDeliveryDate) {
			this.equipDeliveryDate = equipDeliveryDate;
		}

		public String getAnualPartsSpend() {
			return anualPartsSpend;
		}

		public void setAnualPartsSpend(String anualPartsSpend) {
			this.anualPartsSpend = anualPartsSpend;
		}

		public String getAnualServiceSpend() {
			return anualServiceSpend;
		}

		public void setAnualServiceSpend(String anualServiceSpend) {
			this.anualServiceSpend = anualServiceSpend;
		}

		public String getPnCauseFailuret() {
			return pnCauseFailuret;
		}

		public void setPnCauseFailuret(String pnCauseFailuret) {
			this.pnCauseFailuret = pnCauseFailuret;
		}

		public String getGroupCausingFailure() {
			return groupCausingFailure;
		}

		public void setGroupCausingFailure(String groupCausingFailure) {
			this.groupCausingFailure = groupCausingFailure;
		}

		public String getMachineHours() {
			return machineHours;
		}

		public void setMachineHours(String machineHours) {
			this.machineHours = machineHours;
		}

		public String getDateOfFailure() {
			return dateOfFailure;
		}

		public void setDateOfFailure(String dateOfFailure) {
			this.dateOfFailure = dateOfFailure;
		}

		public Long getPartHours() {
			return partHours;
		}

		public void setPartHours(Long partHours) {
			this.partHours = partHours;
		}

		public String getComplaint() {
			return complaint;
		}

		public void setComplaint(String complaint) {
			this.complaint = complaint;
		}

		public String getRootCauseTxt() {
			return rootCauseTxt;
		}

		public void setRootCauseTxt(String rootCauseTxt) {
			this.rootCauseTxt = rootCauseTxt;
		}

		public String getRepairComplications() {
			return repairComplications;
		}

		public void setRepairComplications(String repairComplications) {
			this.repairComplications = repairComplications;
		}

		public String getCorrectiveActions() {
			return correctiveActions;
		}

		public void setCorrectiveActions(String correctiveActions) {
			this.correctiveActions = correctiveActions;
		}

		public String getPartsCustomer() {
			return partsCustomer;
		}

		public void setPartsCustomer(String partsCustomer) {
			this.partsCustomer = partsCustomer;
		}

		public String getLaborCustomer() {
			return laborCustomer;
		}

		public void setLaborCustomer(String laborCustomer) {
			this.laborCustomer = laborCustomer;
		}

		public String getMiscCustomer() {
			return miscCustomer;
		}

		public void setMiscCustomer(String miscCustomer) {
			this.miscCustomer = miscCustomer;
		}

		public String getPartsDealer() {
			return partsDealer;
		}

		public void setPartsDealer(String partsDealer) {
			this.partsDealer = partsDealer;
		}

		public String getLaborDealer() {
			return laborDealer;
		}

		public void setLaborDealer(String laborDealer) {
			this.laborDealer = laborDealer;
		}

		public String getMiscDealer() {
			return miscDealer;
		}

		public void setMiscDealer(String miscDealer) {
			this.miscDealer = miscDealer;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public String getJustificativaConcessao() {
			return justificativaConcessao;
		}

		public void setJustificativaConcessao(String justificativaConcessao) {
			this.justificativaConcessao = justificativaConcessao;
		}
		public List<PecasAprovacaoOSBean> getListaPecasAprovacao() {
			return listaPecasAprovacao;
		}

		public void setListaPecasAprovacao(List<PecasAprovacaoOSBean> listaPecasAprovacao) {
			this.listaPecasAprovacao = listaPecasAprovacao;
		}
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
		public String getObsRejeicao() {
			return obsRejeicao;
		}

		public void setObsRejeicao(String obsRejeicao) {
			this.obsRejeicao = obsRejeicao;
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
		public String getFilialCriacaoOS() {
			return filialCriacaoOS;
		}

		public void setFilialCriacaoOS(String filialCriacaoOS) {
			this.filialCriacaoOS = filialCriacaoOS;
		}
		public String getClientePesa() {
			return clientePesa;
		}

		public void setClientePesa(String clientePesa) {
			this.clientePesa = clientePesa;
		}
		public String getResponsavel() {
			return responsavel;
		}

		public void setResponsavel(String responsavel) {
			this.responsavel = responsavel;
		}
		public String getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(String dataCriacao) {
			this.dataCriacao = dataCriacao;
		}
		public String getNumOs() {
			return numOs;
		}

		public void setNumOs(String numOs) {
			this.numOs = numOs;
		}
		public String getFuncAprovador() {
			return funcAprovador;
		}

		public void setFuncAprovador(String funcAprovador) {
			this.funcAprovador = funcAprovador;
		}
		public String getMaquinaParou() {
			return maquinaParou;
		}

		public void setMaquinaParou(String maquinaParou) {
			this.maquinaParou = maquinaParou;
		}
		private SimpleDateFormat fmtPtBr = new SimpleDateFormat("dd/MM/yyyy");
		private SimpleDateFormat fmtPtBrHHMM = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    public  void fromBean(GeFormularioAprovacaoOs aprovacaoOs) throws ParseException{
	    	aprovacaoOs.setArranjoMaquina(getArranjoMaquina());
			aprovacaoOs.setNomeContato(getNomeContato());
			aprovacaoOs.setTelConcato(getTelConcato());
			aprovacaoOs.setHorimetroPeca(getHorimetroPeca());
			aprovacaoOs.setEntregaTecnica(getEntregaTecnica());
			aprovacaoOs.setMaquinaNova(getMaquinaNova());
			aprovacaoOs.setPecaVendBalcao(getPecaVendBalcao());
			aprovacaoOs.setPecaVendOs(getPecaVendOs());
			aprovacaoOs.setServicoRefeito(getServicoRefeito());
			aprovacaoOs.setNrNfVenda(getNrNfVenda());
			aprovacaoOs.setNrOsAnterior(getNrOsAnterior());
			aprovacaoOs.setMaquinaUsada(getMaquinaUsada());
			aprovacaoOs.setPesaL(getPesaL());
			if(getDataVenda() != null){
				aprovacaoOs.setDataVenda(fmtPtBr.parse(getDataVenda()));
			}
			if(getDataEntregaTecnica() != null){
				aprovacaoOs.setDataEntregaTecnica(fmtPtBr.parse(getDataEntregaTecnica()));
			}
			aprovacaoOs.setOsCustomizacao(getOsCustomizacao());
			aprovacaoOs.setDescComponente(getDescComponente());
			aprovacaoOs.setDescSintomasMaquina(getDescSintomasMaquina());
			aprovacaoOs.setDescricaoReparo(getDescricaoReparo());
			aprovacaoOs.setFotoComponente(getFotoComponente());
			aprovacaoOs.setAmostraOleoSos(getAmostraOleoSos());
			aprovacaoOs.setTesteLeakoff(getTesteLeakoff());
			aprovacaoOs.setDownloadServiceReportEt(getDownloadServiceReportEt());
			aprovacaoOs.setInspecaoMaterialRodanteCtps(getInspecaoMaterialRodanteCtps());
			aprovacaoOs.setRelatorioConsumoOeloLubrificante(getRelatorioConsumoOeloLubrificante());
			aprovacaoOs.setFaltouFerramento(getFaltouFerramento());
			aprovacaoOs.setConsultouManualServico(getConsultouManualServico());
			aprovacaoOs.setTempoReparoSuficiente(getTempoReparoSuficiente());
			aprovacaoOs.setComponentePassouTeste(getComponentePassouTeste());
			aprovacaoOs.setRecebeuTreinamento(getRecebeuTreinamento());
			aprovacaoOs.setRecebeuInformacaoCompletaReparo(getRecebeuInformacaoCompletaReparo());
			aprovacaoOs.setFalhaGeradaDiagnosticoIncorreto(getFalhaGeradaDiagnosticoIncorreto());
			aprovacaoOs.setClienteInfluenciouProposta(getClienteInfluenciouProposta());
			aprovacaoOs.setFoiConsultadoServicoOsAnterior(getFoiConsultadoServicoOsAnterior());
			aprovacaoOs.setFoiCortadoPecasOrcamentoAnterior(getFoiCortadoPecasOrcamentoAnterior());
			//if(aprovacaoOs.getIdCheckIn() == 0){
			aprovacaoOs.setIdCheckIn(getIdCheckIn());
			//}
			aprovacaoOs.setTipoSolicitacao(getTipoSolicitacao());
			aprovacaoOs.setStatus(getStatus());
			aprovacaoOs.setCodigoCliente(getCodigoCliente());
			aprovacaoOs.setCliente(getCliente());
			aprovacaoOs.setSerie(getSerie());
			aprovacaoOs.setModelo(getModelo());
			
			aprovacaoOs.setAplicacao(getAplication());
			aprovacaoOs.setOF_CAT_EQUIP(getOfCatEquip());
			aprovacaoOs.setOF_NON_CAT_EQUIP(getOfNonCatEquip());
			if(getEquipDeliveryDate() != null){
				aprovacaoOs.setEQUIP_DELIVERY_DATE(fmtPtBr.parse(getEquipDeliveryDate()));
			}
			if(getAnualPartsSpend() != null){
				aprovacaoOs.setANUAL_PARTS_SPEND(BigDecimal.valueOf(Double.valueOf(getAnualPartsSpend().replace(".", "").replace(",", "."))));
			}
			if(getAnualServiceSpend() != null){
				aprovacaoOs.setANNUAL_SERVICE_SPEND(BigDecimal.valueOf(Double.valueOf(getAnualServiceSpend().replace(".", "").replace(",", "."))));
			}
			aprovacaoOs.setP_N_CAUSING_FAILURE(getPnCauseFailuret());
			aprovacaoOs.setGROUP_CAUSING_FAILURE(getGroupCausingFailure());
			aprovacaoOs.setMACHINE_HOURS(getMachineHours());
			if(getDateOfFailure() != null){
				aprovacaoOs.setDATE_OF_FAILURE(fmtPtBr.parse(getDateOfFailure()));
			}
			
			if(getPartHours() != null){
				aprovacaoOs.setPART_HOURS(BigDecimal.valueOf(getPartHours()));
			}
			aprovacaoOs.setCOMPLAINT(getComplaint());
			aprovacaoOs.setROOT_CAUSE(getRootCauseTxt());
			aprovacaoOs.setREPAIR_COMPLICATIONS(getRepairComplications());
			aprovacaoOs.setCORRECTIVE_ACTIONS(getCorrectiveActions());
			
			if(getPartsCustomer() != null){
				aprovacaoOs.setTOTAL_CUSTOMER_LIST_PARTS(BigDecimal.valueOf(Double.valueOf(getPartsCustomer().replace(".", "").replace(",", "."))));
			}
			if(getLaborCustomer() != null){
				aprovacaoOs.setTOTAL_CUSTOMER_LIST_LABOR(BigDecimal.valueOf(Double.valueOf(getLaborCustomer().replace(".", "").replace(",", "."))));
			}
			if(getMiscCustomer() != null){
				aprovacaoOs.setTOTAL_CUSTOMER_LIST_MISC(BigDecimal.valueOf(Double.valueOf(getMiscCustomer().replace(".", "").replace(",", "."))));
			}
			if(getPartsDealer() != null){
				aprovacaoOs.setTOTAL_DEALER_NET_PARTS(BigDecimal.valueOf(Double.valueOf(getPartsDealer().replace(".", "").replace(",", "."))));
			}
			if(getLaborDealer() != null){
				aprovacaoOs.setTOTAL_DEALER_NET_LABOR(BigDecimal.valueOf(Double.valueOf(getLaborDealer().replace(".", "").replace(",", "."))));
			}
			if(getMiscDealer() != null){
				aprovacaoOs.setTOTAL_DEALER_NET_MISC(BigDecimal.valueOf(Double.valueOf(getMiscDealer().replace(".", "").replace(",", "."))));
			}
			
			aprovacaoOs.setNOTES(getNotes());
			aprovacaoOs.setJUSTIFICATIVA_CONCESSAO(getJustificativaConcessao());
			
			aprovacaoOs.setEmailContato(getEmailContato());
			aprovacaoOs.setMaterial(getMaterial());
			aprovacaoOs.setHorimetro(getHorimetro());
			//if(aprovacaoOs.getObsRejeicao() == null){
			aprovacaoOs.setObsRejeicao(getObsRejeicao());
//			}else{
//				aprovacaoOs.setObsRejeicao(aprovacaoOs.getObsRejeicao()+"\n"+getObsRejeicao());
//			}
			aprovacaoOs.setTipoSistema(getTipoSistema());
			aprovacaoOs.setOutros(getOutros());
			aprovacaoOs.setClientePesa(getClientePesa());
			if(aprovacaoOs.getDataCriacao() == null){
				aprovacaoOs.setDataCriacao(new Date());
			}
			aprovacaoOs.setMaquinaParou(getMaquinaParou());
	    }
	    
	    public  void toBean(GeFormularioAprovacaoOs aprovacaoOs) throws ParseException{
	    	setId(aprovacaoOs.getId());
	    	setArranjoMaquina(aprovacaoOs.getArranjoMaquina());
			setNomeContato(aprovacaoOs.getNomeContato());
			setTelConcato(aprovacaoOs.getTelConcato());
			setHorimetroPeca(aprovacaoOs.getHorimetroPeca());
			setEntregaTecnica(aprovacaoOs.getEntregaTecnica());
			setMaquinaNova(aprovacaoOs.getMaquinaNova());
			setPecaVendBalcao(aprovacaoOs.getPecaVendBalcao());
			setPecaVendOs(aprovacaoOs.getPecaVendOs());
			setServicoRefeito(aprovacaoOs.getServicoRefeito());
			setNrNfVenda(aprovacaoOs.getNrNfVenda());
			setNrOsAnterior(aprovacaoOs.getNrOsAnterior());
			setMaquinaUsada(aprovacaoOs.getMaquinaUsada());
			setPesaL(aprovacaoOs.getPesaL());
			
			if(aprovacaoOs.getDataVenda() != null){
				setDataVenda(fmtPtBr.format(aprovacaoOs.getDataVenda()));
			}
			if(aprovacaoOs.getDataEntregaTecnica() != null){
				setDataEntregaTecnica(fmtPtBr.format(aprovacaoOs.getDataEntregaTecnica()));
			}
			setOsCustomizacao(aprovacaoOs.getOsCustomizacao());
			setDescComponente(aprovacaoOs.getDescComponente());
			setDescSintomasMaquina(aprovacaoOs.getDescSintomasMaquina());
			setDescricaoReparo(aprovacaoOs.getDescricaoReparo());
			setFotoComponente(aprovacaoOs.getFotoComponente());
			setAmostraOleoSos(aprovacaoOs.getAmostraOleoSos());
			setTesteLeakoff(aprovacaoOs.getTesteLeakoff());
			setDownloadServiceReportEt(aprovacaoOs.getDownloadServiceReportEt());
			setInspecaoMaterialRodanteCtps(aprovacaoOs.getInspecaoMaterialRodanteCtps());
			setRelatorioConsumoOeloLubrificante(aprovacaoOs.getRelatorioConsumoOeloLubrificante());
			setFaltouFerramento(aprovacaoOs.getFaltouFerramento());
			setConsultouManualServico(aprovacaoOs.getConsultouManualServico());
			setTempoReparoSuficiente(aprovacaoOs.getTempoReparoSuficiente());
			setComponentePassouTeste(aprovacaoOs.getComponentePassouTeste());
			setRecebeuTreinamento(aprovacaoOs.getRecebeuTreinamento());
			setRecebeuInformacaoCompletaReparo(aprovacaoOs.getRecebeuInformacaoCompletaReparo());
			setFalhaGeradaDiagnosticoIncorreto(aprovacaoOs.getFalhaGeradaDiagnosticoIncorreto());
			setClienteInfluenciouProposta(aprovacaoOs.getClienteInfluenciouProposta());
			setFoiConsultadoServicoOsAnterior(aprovacaoOs.getFoiConsultadoServicoOsAnterior());
			setFoiCortadoPecasOrcamentoAnterior(aprovacaoOs.getFoiCortadoPecasOrcamentoAnterior());
			setIdCheckIn(aprovacaoOs.getIdCheckIn());
			setTipoSolicitacao(aprovacaoOs.getTipoSolicitacao());
			if(aprovacaoOs.getStatus()  == null){ 
				setStatus("Pendente");
			} else	if ("A".equals(aprovacaoOs.getStatus())){
				setStatus("Aprovado");
			}else{
				setStatus("Rejeitado");
			}
			setCodigoCliente(aprovacaoOs.getCodigoCliente());
			setCliente(aprovacaoOs.getCliente());
			setSerie(aprovacaoOs.getSerie());
			setModelo(aprovacaoOs.getModelo());
			setAplication(aprovacaoOs.getAplicacao());
			setOfCatEquip(aprovacaoOs.getOF_CAT_EQUIP());
			setOfNonCatEquip(aprovacaoOs.getOF_NON_CAT_EQUIP());
			
			
			if(aprovacaoOs.getEQUIP_DELIVERY_DATE() != null){
				setEquipDeliveryDate(fmtPtBr.format(aprovacaoOs.getEQUIP_DELIVERY_DATE()));
			}
			if(aprovacaoOs.getANUAL_PARTS_SPEND() != null){
				setAnualPartsSpend(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getANUAL_PARTS_SPEND().doubleValue()));
			}
			if(aprovacaoOs.getANNUAL_SERVICE_SPEND() != null){
				setAnualServiceSpend(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getANNUAL_SERVICE_SPEND().doubleValue()));
			}
			setPnCauseFailuret(aprovacaoOs.getP_N_CAUSING_FAILURE());
			setGroupCausingFailure(aprovacaoOs.getGROUP_CAUSING_FAILURE());
			setMachineHours(aprovacaoOs.getMACHINE_HOURS());
			if(aprovacaoOs.getDATE_OF_FAILURE() != null){
				setDateOfFailure(fmtPtBr.format(aprovacaoOs.getDATE_OF_FAILURE()));
			}
			
			if(aprovacaoOs.getPART_HOURS() != null){
				setPartHours(aprovacaoOs.getPART_HOURS().longValue());
			}
			setComplaint(aprovacaoOs.getCOMPLAINT());
			setRootCauseTxt(aprovacaoOs.getROOT_CAUSE());
			setRepairComplications(aprovacaoOs.getREPAIR_COMPLICATIONS());
			setCorrectiveActions(aprovacaoOs.getCORRECTIVE_ACTIONS());
			
			if(aprovacaoOs.getTOTAL_CUSTOMER_LIST_PARTS() != null){
				setPartsCustomer(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getTOTAL_CUSTOMER_LIST_PARTS().doubleValue()));
			}
			if(aprovacaoOs.getTOTAL_CUSTOMER_LIST_LABOR() != null){
				setLaborCustomer(ValorMonetarioHelper.formata("###,##0.00",aprovacaoOs.getTOTAL_CUSTOMER_LIST_LABOR().doubleValue()));
			}
			if(aprovacaoOs.getTOTAL_CUSTOMER_LIST_MISC() != null){
				setMiscCustomer(ValorMonetarioHelper.formata("###,##0.00",aprovacaoOs.getTOTAL_CUSTOMER_LIST_MISC().doubleValue()));
			}
			if(aprovacaoOs.getTOTAL_DEALER_NET_PARTS() != null){
				setPartsDealer(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getTOTAL_DEALER_NET_PARTS().doubleValue()));
			}
			if(aprovacaoOs.getTOTAL_DEALER_NET_LABOR() != null){
				setLaborDealer(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getTOTAL_DEALER_NET_LABOR().doubleValue()));
			}
			if(aprovacaoOs.getTOTAL_DEALER_NET_MISC() != null){
				setMiscDealer(ValorMonetarioHelper.formata("###,##0.00", aprovacaoOs.getTOTAL_DEALER_NET_MISC().doubleValue()));
			}
			
			setNotes(aprovacaoOs.getNOTES());
			setJustificativaConcessao(aprovacaoOs.getJUSTIFICATIVA_CONCESSAO());
			
			setEmailContato(aprovacaoOs.getEmailContato());
			setMaterial(aprovacaoOs.getMaterial());
			setHorimetro(aprovacaoOs.getHorimetro());
			setObsRejeicao(aprovacaoOs.getObsRejeicao());
			setNomeFilial(aprovacaoOs.getNomeFilial());
			setTipoSistema(aprovacaoOs.getTipoSistema());
			setOutros(aprovacaoOs.getOutros());
			setClientePesa(aprovacaoOs.getClientePesa());
			if(aprovacaoOs.getDataCriacao() != null){
				setDataCriacao(fmtPtBrHHMM.format(aprovacaoOs.getDataCriacao()));
			}
			setMaquinaParou(aprovacaoOs.getMaquinaParou());
	    }
}

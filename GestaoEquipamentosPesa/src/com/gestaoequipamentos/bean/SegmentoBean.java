package com.gestaoequipamentos.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSegmentoLog;
import com.gestaoequipamentos.entity.TwFuncionario;

public class SegmentoBean {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private String numeroSegmento;
	private String descricao;
	private String cscc;
	private String jbcd;
	private String cptcd;
	private String cptcdStr;
	private String shopField;
	private Long id;
	private Long documento;
	private String jbcdStr;
	private String jbctStr;
	private String jbct;
	private Long idCheckin;
	private List<PecasBean> pecasList;
	private List<OperacaoBean> operacaoList;
	private String horas;
	private String horasSubst;
	private String hasSendPecasDbsSegmento;
	private Integer qtdComp;
	private Integer qtdCompSubst;
	private String msgDbs;
	private String observacao;
	private String idFuncionarioCriador;
	private String codErroDbs;
	private String havePecas;
	private String idFuncionarioPecas;
	private String nomeFuncionarioPecas;
	private String nomeFuncionarioRemocao;
	private String nomeFuncionarioEdicao;
	private String dataLiberacaoPecas;
	private String dataTerminoServico;
	private String numeroDoc;
	private String pedido;
	private String tituloFotos;
	private String descricaoFalhaFotos;
	private String conclusaoFotos;
	private Long idDocSegOper;
	private String idDocSegOperList;
	private JRBeanCollectionDataSource fotosList; 
	
	private List<SegmentoBean> segmentoList; 
	private String dateOpen;
	private String cliente;
	private String numOs;
	private String descricaojobControl;
	private String modelo;
	private String prazoPETS;
	private String filial;
	private String statusPrazo;
	private String siglaStatusLegenda;
	private String descricaoStatusLegenda;
	private String dataHeader;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String isMoreThirtyDay;
	private String numSerie;
	private String idEquipamento;
	private String nomeSegmento;
	private String observacaoStatusLegenda;
	
	public SegmentoBean() {
		segmentoList = new ArrayList<SegmentoBean>();
	}
	public String getNumeroSegmento() {
		return numeroSegmento;
	}
	public void setNumeroSegmento(String numeroSegmento) {
		this.numeroSegmento = numeroSegmento;
	}
	public String getCscc() {
		return cscc;
	}
	public void setCscc(String cscc) {
		this.cscc = cscc;
	}
	public String getShopField() {
		return shopField;
	}
	public void setShopField(String shopField) {
		this.shopField = shopField;
	}
	public List<PecasBean> getPecasList() {
		return pecasList;
	}
	public void setPecasList(List<PecasBean> pecasList) {
		this.pecasList = pecasList;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getJbcdStr() {
		return jbcdStr;
	}
	public void setJbcdStr(String jbcdStr) {
		this.jbcdStr = jbcdStr;
	}
	public Long getIdCheckin() {
		return idCheckin;
	}
	public void setIdCheckin(Long idCheckin) {
		this.idCheckin = idCheckin;
	}

	public List<OperacaoBean> getOperacaoList() {
		return operacaoList;
	}
	public void setOperacaoList(List<OperacaoBean> operacaoList) {
		this.operacaoList = operacaoList;
	}
	public String getJbcd() {
		return jbcd;
	}
	public void setJbcd(String jbcd) {
		this.jbcd = jbcd;
	}
	public String getCptcd() {
		return cptcd;
	}
	public void setCptcd(String cptcd) {
		this.cptcd = cptcd;
	}
	public String getJbctStr() {
		return jbctStr;
	}
	public void setJbctStr(String jbctStr) {
		this.jbctStr = jbctStr;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getCptcdStr() {
		return cptcdStr;
	}
	public void setCptcdStr(String cptcdStr) {
		this.cptcdStr = cptcdStr;
	}

	public String getHasSendPecasDbsSegmento() {
		return hasSendPecasDbsSegmento;
	}
	public void setHasSendPecasDbsSegmento(String hasSendPecasDbsSegmento) {
		this.hasSendPecasDbsSegmento = hasSendPecasDbsSegmento;
	}
	public Integer getQtdComp() {
		return qtdComp;
	}
	public void setQtdComp(Integer qtdComp) {
		this.qtdComp = qtdComp;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMsgDbs() {
		return msgDbs;
	}
	public void setMsgDbs(String msgDbs) {
		this.msgDbs = msgDbs;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getIdFuncionarioCriador() {
		return idFuncionarioCriador;
	}
	public void setIdFuncionarioCriador(String idFuncionarioCriador) {
		this.idFuncionarioCriador = idFuncionarioCriador;
	}
	public String getCodErroDbs() {
		return codErroDbs;
	}
	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}
	public String getHavePecas() {
		return havePecas;
	}
	public void setHavePecas(String havePecas) {
		this.havePecas = havePecas;
	}
	public String getIdFuncionarioPecas() {
		return idFuncionarioPecas;
	}
	public void setIdFuncionarioPecas(String idFuncionarioPecas) {
		this.idFuncionarioPecas = idFuncionarioPecas;
	}
	public String getDataLiberacaoPecas() {
		return dataLiberacaoPecas;
	}
	public void setDataLiberacaoPecas(String dataLiberacaoPecas) {
		this.dataLiberacaoPecas = dataLiberacaoPecas;
	}
	public String getDataTerminoServico() {
		return dataTerminoServico;
	}
	public void setDataTerminoServico(String dataTerminoServico) {
		this.dataTerminoServico = dataTerminoServico;
	}
	public String getNomeFuncionarioPecas() {
		return nomeFuncionarioPecas;
	}
	public void setNomeFuncionarioPecas(String nomeFuncionarioPecas) {
		this.nomeFuncionarioPecas = nomeFuncionarioPecas;
	}
	public String getNumeroDoc() {
		return numeroDoc;
	}
	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	public String getPedido() {
		return pedido;
	}
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	public String getHorasSubst() {
		return horasSubst;
	}
	public void setHorasSubst(String horasSubst) {
		this.horasSubst = horasSubst;
	}
	public Integer getQtdCompSubst() {
		return qtdCompSubst;
	}
	public void setQtdCompSubst(Integer qtdCompSubst) {
		this.qtdCompSubst = qtdCompSubst;
	}
	public String getTituloFotos() {
		return tituloFotos;
	}
	public void setTituloFotos(String tituloFotos) {
		this.tituloFotos = tituloFotos;
	}
	public String getDescricaoFalhaFotos() {
		return descricaoFalhaFotos;
	}
	public void setDescricaoFalhaFotos(String descricaoFalhaFotos) {
		this.descricaoFalhaFotos = descricaoFalhaFotos;
	}
	public String getConclusaoFotos() {
		return conclusaoFotos;
	}
	public void setConclusaoFotos(String conclusaoFotos) {
		this.conclusaoFotos = conclusaoFotos;
	}
	public Long getIdDocSegOper() {
		return idDocSegOper;
	}
	public void setIdDocSegOper(Long idDocSegOper) {
		this.idDocSegOper = idDocSegOper;
	}
	public String getNomeFuncionarioRemocao() {
		return nomeFuncionarioRemocao;
	}
	public void setNomeFuncionarioRemocao(String nomeFuncionarioRemocao) {
		this.nomeFuncionarioRemocao = nomeFuncionarioRemocao;
	}
	public String getNomeFuncionarioEdicao() {
		return nomeFuncionarioEdicao;
	}
	public void setNomeFuncionarioEdicao(String nomeFuncionarioEdicao) {
		this.nomeFuncionarioEdicao = nomeFuncionarioEdicao;
	}
	public String getJbct() {
		return jbct;
	}
	public void setJbct(String jbct) {
		this.jbct = jbct;
	}

	public JRBeanCollectionDataSource getFotosList() {
		return fotosList;
	}
	public void setFotosList(JRBeanCollectionDataSource fotosList) {
		this.fotosList = fotosList;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getDateOpen() {
		return dateOpen;
	}
	public void setDateOpen(String dateOpen) {
		this.dateOpen = dateOpen;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNumOs() {
		return numOs;
	}
	public void setNumOs(String numOs) {
		this.numOs = numOs;
	}
	public String getDescricaojobControl() {
		return descricaojobControl;
	}
	public void setDescricaojobControl(String descricaojobControl) {
		this.descricaojobControl = descricaojobControl;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPrazoPETS() {
		return prazoPETS;
	}
	public void setPrazoPETS(String prazoPETS) {
		this.prazoPETS = prazoPETS;
	}
	public String getFilial() {
		return filial;
	}
	public void setFilial(String filial) {
		this.filial = filial;
	}
	public String getStatusPrazo() {
		return statusPrazo;
	}
	public void setStatusPrazo(String statusPrazo) {
		this.statusPrazo = statusPrazo;
	}
	public String getSiglaStatusLegenda() {
		return siglaStatusLegenda;
	}
	public void setSiglaStatusLegenda(String siglaStatusLegenda) {
		this.siglaStatusLegenda = siglaStatusLegenda;
	}
	public List<SegmentoBean> getSegmentoList() {
		return segmentoList;
	}
	public void setSegmentoList(List<SegmentoBean> segmentoList) {
		this.segmentoList = segmentoList;
	}
	public String getDataHeader() {
		return dataHeader;
	}
	public void setDataHeader(String dataHeader) {
		this.dataHeader = dataHeader;
	}
	public String getDescricaoStatusLegenda() {
		return descricaoStatusLegenda;
	}
	public void setDescricaoStatusLegenda(String descricaoStatusLegenda) {
		this.descricaoStatusLegenda = descricaoStatusLegenda;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getIsMoreThirtyDay() {
		return isMoreThirtyDay;
	}
	public void setIsMoreThirtyDay(String isMoreThirtyDay) {
		this.isMoreThirtyDay = isMoreThirtyDay;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public String getIdEquipamento() {
		return idEquipamento;
	}
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
	public String getNomeSegmento() {
		return nomeSegmento;
	}
	public void setNomeSegmento(String nomeSegmento) {
		this.nomeSegmento = nomeSegmento;
	}
	public String getObservacaoStatusLegenda() {
		return observacaoStatusLegenda;
	}
	public void setObservacaoStatusLegenda(String observacaoStatusLegenda) {
		this.observacaoStatusLegenda = observacaoStatusLegenda;
	}
	public String getIdDocSegOperList() {
		return idDocSegOperList;
	}
	public void setIdDocSegOperList(String idDocSegOperList) {
		this.idDocSegOperList = idDocSegOperList;
	}
	public Long getDocumento() {
		return documento;
	}
	public void setDocumento(Long documento) {
		this.documento = documento;
	}
	public void toBean(GeSegmento entity, EntityManager manager){
		entity.setNumeroSegmento(getNumeroSegmento());
		entity.setComCode(getCptcd());
		entity.setJobCode(getJbcd());
		entity.setJobControl(getJbctStr());
		//entity.setIdCheckin(entity.getIdCheckin());
		//entity.setId(getId());
		entity.setDescricaoJobCode(getJbcdStr());
		entity.setJobControl(getJbctStr());
		entity.setHorasPrevista(getHoras());
		entity.setDescricaoCompCode(getCptcdStr());
		entity.setQtdComp(getQtdComp());
		entity.setIsCreateSegmento("N");
		entity.setCodErroDbs("100");
		entity.setMsgDbs("Processando segmento no DBS, aguarde o retorno!");
		TwFuncionario funcionario = manager.find(TwFuncionario.class, this.getIdFuncionarioCriador());
		entity.setIdFuncionarioCriador(funcionario);
		entity.setDescricaoJobControl(getDescricaojobControl());
	}

	
	public void fromBean (GeSegmento bean){
		setNumeroSegmento(bean.getNumeroSegmento());
		setJbcd(bean.getJobCode());
		setJbcdStr(bean.getDescricaoJobCode());
		setCptcd(bean.getComCode());
		setIdCheckin(bean.getIdCheckin().getId());	
		setId(bean.getId());
		setJbctStr(bean.getJobControl());
		setHoras(bean.getHorasPrevista());
		setCptcdStr(bean.getDescricaoCompCode());
		setHasSendPecasDbsSegmento((bean.getGeDocSegOperCollection().size() > 0)?"S":"N");
		for(GeDocSegOper oper : bean.getGeDocSegOperCollection()){
			if(oper.getIdOperacao() == null){
				setHavePecas("S");
			}
		}
		setQtdComp(bean.getQtdComp());
		setDescricao(bean.getNumeroSegmento() +" - "+bean.getDescricaoJobCode()+" - "+bean.getDescricaoCompCode());
		setMsgDbs(bean.getMsgDbs());
		setIdFuncionarioCriador((bean.getIdFuncionarioCriador().getEpidno()== null)? "" : bean.getIdFuncionarioCriador().getEpidno());
		setCodErroDbs(bean.getCodErroDbs());
		setIdFuncionarioPecas(bean.getIdFuncionarioPecas());
		if(bean.getDataLiberacaoPecas() != null){
			setDataLiberacaoPecas(dateFormat.format(bean.getDataLiberacaoPecas()));
		}
		if(bean.getDataTerminoServico() != null){
			setDataTerminoServico(dateFormat.format(bean.getDataTerminoServico()));
		}
		setDescricaojobControl(bean.getDescricaoJobControl());
		setObservacao(bean.getObservacao());
		setNomeSegmento(bean.getNomeSegmento());
	}
	public void fromBean (GeSegmentoLog bean){
		setNumeroSegmento(bean.getNumeroSegmento());
		setJbcd(bean.getJobCode());
		setJbcdStr(bean.getDescricaoJobCode());
		setCptcd(bean.getComCode());
		setIdCheckin(bean.getIdCheckin());	
		setId(bean.getId());
		setJbctStr(bean.getJobControl());
		setHoras(bean.getHorasPrevista());
		setCptcdStr(bean.getDescricaoCompCode());
//		setHasSendPecasDbsSegmento((bean.getGeDocSegOperCollection().size() > 0)?"S":"N");
//		for(GeDocSegOper oper : bean.getGeDocSegOperCollection()){
//			if(oper.getIdOperacao() == null){
//				setHavePecas("S");
//			}
//		}
		setQtdComp(bean.getQtdComp().intValue());
		setDescricao(bean.getNumeroSegmento() +" - "+bean.getDescricaoJobCode()+" - "+bean.getDescricaoCompCode());
		setMsgDbs(bean.getMsgDbs());
		//setIdFuncionarioCriador((bean.getIdFuncionarioCriador().getEpidno()== null)? "" : bean.getIdFuncionarioCriador().getEpidno());
		setCodErroDbs(bean.getCodErroDbs());
		setIdFuncionarioPecas(bean.getIdFuncionarioPecas());
		if(bean.getDataLiberacaoPecas() != null){
			setDataLiberacaoPecas(dateFormat.format(bean.getDataLiberacaoPecas()));
		}
		if(bean.getDataTerminoServico() != null){
			setDataTerminoServico(dateFormat.format(bean.getDataTerminoServico()));
		}
	}
	
}

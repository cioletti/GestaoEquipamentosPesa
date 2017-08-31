package com.gestaoequipamentos.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.CheckedInputStream;

import com.gestaoequipamentos.bean.ArquivosBean;
import com.gestaoequipamentos.bean.BgrpBean;
import com.gestaoequipamentos.bean.CadSegmentoBean;
import com.gestaoequipamentos.bean.CentroDeCustoBean;
import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.ClienteBean;
import com.gestaoequipamentos.bean.ClienteInterBean;
import com.gestaoequipamentos.bean.ComplexidadeBean;
import com.gestaoequipamentos.bean.ComponenteCodeBean;
import com.gestaoequipamentos.bean.CondicaoPagamentoBean;
import com.gestaoequipamentos.bean.ContaContabilBean;
import com.gestaoequipamentos.bean.ContatoBean;
import com.gestaoequipamentos.bean.DataHeaderBean;
import com.gestaoequipamentos.bean.DetalhesPropostaBean;
import com.gestaoequipamentos.bean.FamiliaBean;
import com.gestaoequipamentos.bean.FatorBean;
import com.gestaoequipamentos.bean.FatorReajusteBean;
import com.gestaoequipamentos.bean.FilialBean;
import com.gestaoequipamentos.bean.FornecedorServicoTerceirosBean;
import com.gestaoequipamentos.bean.FotosBean;
import com.gestaoequipamentos.bean.GeDocSegOperBean;
import com.gestaoequipamentos.bean.GeFormularioAprovacaoOsBean;
import com.gestaoequipamentos.bean.GestaoEquipamentosBean;
import com.gestaoequipamentos.bean.GestorOSBean;
import com.gestaoequipamentos.bean.GestorRentalBean;
import com.gestaoequipamentos.bean.IndicadorGarantiaBean;
import com.gestaoequipamentos.bean.InspecaoGeTreeBean;
import com.gestaoequipamentos.bean.ItemBean;
import com.gestaoequipamentos.bean.JobCodeBean;
import com.gestaoequipamentos.bean.JobControlBean;
import com.gestaoequipamentos.bean.LegendaProcessoOficinaBean;
import com.gestaoequipamentos.bean.ModeloBean;
import com.gestaoequipamentos.bean.NaturezaOperacaoBean;
import com.gestaoequipamentos.bean.OSInternaBean;
import com.gestaoequipamentos.bean.OperacaoBean;
import com.gestaoequipamentos.bean.PecasBean;
import com.gestaoequipamentos.bean.PecasBoTreeBean;
import com.gestaoequipamentos.bean.PrecoBean;
import com.gestaoequipamentos.bean.PrefixoBean;
import com.gestaoequipamentos.bean.RegraDeNegocioBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.ServicoTerceirosBean;
import com.gestaoequipamentos.bean.SistemaBean;
import com.gestaoequipamentos.bean.SituacaoOSBean;
import com.gestaoequipamentos.bean.SituacaoServTerceirosBean;
import com.gestaoequipamentos.bean.TipoFreteBean;
import com.gestaoequipamentos.bean.TipoServicoBean;
import com.gestaoequipamentos.bean.TreeBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.bean.ValidarCentroDeCustoContaContabilBean;
import com.gestaoequipamentos.business.ArquivosBusiness;
import com.gestaoequipamentos.business.BgrpBusiness;
import com.gestaoequipamentos.business.CadSegmentoBusiness;
import com.gestaoequipamentos.business.CentroDeCustoBusiness;
import com.gestaoequipamentos.business.CentroDeCustoContaContabilBusiness;
import com.gestaoequipamentos.business.ChekinBusiness;
import com.gestaoequipamentos.business.ClienteBusiness;
import com.gestaoequipamentos.business.ClienteInterBusiness;
import com.gestaoequipamentos.business.ComplexidadeBusiness;
import com.gestaoequipamentos.business.ConsultorCheckinBusiness;
import com.gestaoequipamentos.business.ContaContabilBusiness;
import com.gestaoequipamentos.business.ContratoBusiness;
import com.gestaoequipamentos.business.DigitadorCheckinBusiness;
import com.gestaoequipamentos.business.FamiliaBusiness;
import com.gestaoequipamentos.business.FatorBusiness;
import com.gestaoequipamentos.business.FatorReajusteBusiness;
import com.gestaoequipamentos.business.FotosBusiness;
import com.gestaoequipamentos.business.GestaoEquipamentosBusiness;
import com.gestaoequipamentos.business.GestorOsBusiness;
import com.gestaoequipamentos.business.GestorRentalBusiness;
import com.gestaoequipamentos.business.ImportXmlBusiness;
import com.gestaoequipamentos.business.InspencaoGeTreeBusiness;
import com.gestaoequipamentos.business.ModeloBusiness;
import com.gestaoequipamentos.business.OSInternaBusiness;
import com.gestaoequipamentos.business.PecasBusiness;
import com.gestaoequipamentos.business.PrecoBusiness;
import com.gestaoequipamentos.business.PrefixoBusiness;
import com.gestaoequipamentos.business.RegraDeNegocioBusiness;
import com.gestaoequipamentos.business.SegmentoBusiness;
import com.gestaoequipamentos.business.SituacaoServicoTerceirosBusiness;
import com.gestaoequipamentos.business.SupervisorCheckinBusiness;
import com.gestaoequipamentos.business.TipoFreteBusiness;
import com.gestaoequipamentos.business.TipoServicoBusiness;
import com.gestaoequipamentos.business.TreeBusiness;
import com.gestaoequipamentos.business.UsuarioBusiness;
import com.gestaoequipamentos.util.ExceptionLogin;

import flex.messaging.FlexContext;

public class ServiceController { 

	private UsuarioBean usuarioBean;

	public ServiceController() throws Exception {
		usuarioBean = (UsuarioBean) FlexContext.getFlexSession().getAttribute("usuario");
//		usuarioBean = new UsuarioBean();
//		usuarioBean.setFilial("00");
//		usuarioBean.setMatricula("RDRSISTEMAS");
	}

	public String getUrlLogintServer() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
				":").concat(
				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
				.concat("/ControlPanelPesa");
		 return url;
	}
	public String getUrlReport() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
   				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
   				":").concat(
   				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
   				.concat("/GestaoEquipamentosPesa");
                                           		
		return url;
	}
	public String getUrlReportApropriacaoHoras() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
   				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
   				":").concat(
   				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
   				.concat("/ApropriacaoHoras");
                                           		
		return url;
	}
	public String getUrlImg() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
                       .concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
                       ":").concat(
                    		   String.valueOf(FlexContext.getHttpRequest().getServerPort())).concat(FlexContext.getHttpRequest().getContextPath()).concat("/");
		return url;
	}
	
	public String alterPassword(String senhaAntiga, String senhaAtual) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.alterPassword(usuarioBean.getLogin(), senhaAntiga, senhaAtual);
	}

	private void validarUsuario() throws Exception {
		try {
			if (usuarioBean == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			ExceptionLogin exception = new ExceptionLogin("false");
			throw exception;
		}
	}
	
	public List<CentroDeCustoBean> findAllCentroDeCusto() throws Exception {
		validarUsuario();
		CentroDeCustoBusiness business = new CentroDeCustoBusiness();
		return business.findAllCentroDeCusto();
	}

	public List<ContaContabilBean> findAllContaContabil() throws Exception {
		validarUsuario();
		ContaContabilBusiness business = new ContaContabilBusiness();
		return business.findAllContaContabil();
	}



	public List<RegraDeNegocioBean> findAllRegraDeNegocio() throws Exception {
		validarUsuario();
		RegraDeNegocioBusiness business = new RegraDeNegocioBusiness();
		return business.findAllRegraDeNegocio();
	}

	public List<RegraDeNegocioBean> findRegraDeNegocioByFilial(String filial) throws Exception {
		validarUsuario();
		RegraDeNegocioBusiness business = new RegraDeNegocioBusiness();
		return business.findRegraDeNegocioByFilial(filial);
	}

	
	public List<ClienteInterBean> findAllClienteInter(String filial) throws Exception {
		validarUsuario();
		ClienteInterBusiness business = new ClienteInterBusiness();
		return business.findAllClienteInter(filial);
	}

	public List<ClienteInterBean> findAllClienteInter() throws Exception {
		validarUsuario();
		ClienteInterBusiness business = new ClienteInterBusiness();
		return business.findAllClienteInter(usuarioBean.getFilial());
	}

	public List<ClienteBean> findDataNomeCliente(String nomeCliente) throws Exception {
		validarUsuario();
		ClienteBusiness business = new ClienteBusiness();
		return business.findDataNomeCliente(nomeCliente);
	}

	public ClienteBean findDataCliente(String cuno) throws Exception {
		validarUsuario();
		ClienteBusiness business = new ClienteBusiness();
		return business.findDataCliente(cuno);
	}
	public Boolean verificarCodigoCliente(String codigoCliente) throws Exception {
		validarUsuario();
		ClienteBusiness business = new ClienteBusiness();
		return business.verificarCodigoCliente(codigoCliente);
	}
	

	public String getUrlReportServer() {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0].concat("://");
		return url.concat(FlexContext.getHttpRequest().getServerName()).concat(":")
		.concat(String.valueOf(FlexContext.getHttpRequest().getServerPort()))
		.concat(FlexContext.getHttpRequest().getContextPath()).concat("/ReportPdf");
	}
	
	public List<IndicadorGarantiaBean> findAllIndicadorGarantia() throws Exception{
		validarUsuario();
		CentroDeCustoContaContabilBusiness business = new CentroDeCustoContaContabilBusiness(usuarioBean);
		return business.findAllIndicadorGarantia();
	}
	public Boolean validarCentroDeCustoContaContabil(ValidarCentroDeCustoContaContabilBean bean, Long idOsPalm) throws Exception{
		validarUsuario();
		CentroDeCustoContaContabilBusiness business = new CentroDeCustoContaContabilBusiness(usuarioBean);
		return business.validarCentroDeCustoContaContabil(bean, idOsPalm);
	}
	
	public Collection<FilialBean> findAllFiliais() throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllFiliais();
	}
	public Collection<JobControlBean> findAllJobControl() throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllJobControl();
	}
	public Collection<JobCodeBean> findAllJobCode() throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllJobCode();
	}
	public Collection<JobCodeBean> findAllJobCode(String caracter) throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllJobCode(caracter);
	}
	public Collection<ComplexidadeBean> findAllComplexidade() throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllComplexidade();
	}
	
	public Collection<ComponenteCodeBean> findAllCompCode(String caracter) throws Exception {
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.findAllCompCode(caracter);
	}
	public String findEstimateBy() {
		String [] array = usuarioBean.getNome().split(" ");
		String estimateBy = "";
		for (String string : array) {
			if(string.length() > 2){
				estimateBy += string.charAt(0);
			}
		}
		return estimateBy;
	}

	
	public  UsuarioBean verificarLogin() throws Exception{
		validarUsuario();
		return usuarioBean;
	}
	public void invalidarSessao(){
		FlexContext.getFlexSession().invalidate();
	}
	
	public Boolean validarNumSerie(String numSerie, String cliente, int IdosPalm, String modeloMaquina) throws Exception{
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.validarNumSerie(numSerie, cliente, IdosPalm, modeloMaquina);
	}
	public List<ChekinBean> findAllChekin(String campoPesquisa) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekin(campoPesquisa, null);
	}
	public List<ChekinBean> findAllChekin(String campoPesquisa, String tipoCliente) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekin(campoPesquisa, null, tipoCliente);
	}
	public List<ChekinBean> findAllChekinOrcamentista(String campoPesquisa) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekinOrcamentista(campoPesquisa);
	}
	public List<ChekinBean> findAllChekinRental(String campoPesquisa) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekinRental(campoPesquisa);
	}
	public List<ChekinBean> findAllChekinGarantia(String campoPesquisa) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekinGarantia(campoPesquisa);
	}
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS, String col2findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOS(col1findSituacaoOS, col2findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOSSupervisor (String col1findSituacaoOS, String col2findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSSupervisor(col1findSituacaoOS, col2findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS, String col2findSituacaoOS, String tipoCliente)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOS(col1findSituacaoOS, col2findSituacaoOS, tipoCliente);
	}
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOS(col1findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOSSupervisor (String col1findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSSupervisor(col1findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS, ChekinBean bean)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSObj(col1findSituacaoOS, bean);
	}
	public List <ChekinBean> findSituacaoOSFaturamento (String col1findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSFaturamento(col1findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOSFaturamentoSupervisor (String col1findSituacaoOS)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSFaturamentoSupervisor(col1findSituacaoOS);
	}
	public List <ChekinBean> findSituacaoOSFaturamento (String col1findSituacaoOS, String tipoCliente)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSFaturamento(col1findSituacaoOS, tipoCliente);
	}
	
	public List<BgrpBean> findAllBgrp() throws Exception{
		validarUsuario();
		BgrpBusiness bgrp = new BgrpBusiness();
		return bgrp.findAllBgrp();
	}
	public List<PrecoBean> findPrecoModPre(Long idPrefixo, Long idModelo) throws Exception{
		validarUsuario();
		PrecoBusiness preco = new PrecoBusiness(this.usuarioBean);
		return preco.findPrecoModPre(idPrefixo,idModelo);		
	}
	public List<PrecoBean> findPrecoModPreClone(Long idPrefixo, Long idModelo) throws Exception{
		validarUsuario();
		PrecoBusiness preco = new PrecoBusiness(this.usuarioBean);
		return preco.findPrecoModPre(idPrefixo,idModelo);		
	}
	public List<PrecoBean> findAllPreco() throws Exception{
		validarUsuario();
		PrecoBusiness preco = new PrecoBusiness(this.usuarioBean);
		return preco.findAllPreco();		
	}
	public List<PrecoBean> clonarPreco(Long idModeloOrigem, Long idPrefixoOrigem, Long idModeloDestino, Long idPrefixoDestino) throws Exception{
		PrecoBusiness preco = new PrecoBusiness(this.usuarioBean);
		return preco.clonarPreco(idModeloOrigem, idPrefixoOrigem, idModeloDestino, idPrefixoDestino);		
		
	}
	public PrecoBean clonarPrecoIndividual(PrecoBean precoOrigem, Long idModeloDestino, Long idPrefixoDestino) throws Exception{
		validarUsuario();
		PrecoBusiness preco = new PrecoBusiness(this.usuarioBean);
		return preco.clonarPrecoIndividual(precoOrigem , idModeloDestino, idPrefixoDestino);		
		
	}
	
	public Collection<ModeloBean> findAllModelo() throws Exception{
		validarUsuario();
		ModeloBusiness modelo = new ModeloBusiness();
		return modelo.findAllModelo();
	}
	public Collection<ModeloBean> findAllModeloClone() throws Exception{
		validarUsuario();
		ModeloBusiness modelo = new ModeloBusiness();
		return modelo.findAllModelo();
	}
	
	public List<TipoFreteBean> findAllFrete() throws Exception{
		validarUsuario();
		TipoFreteBusiness frete = new TipoFreteBusiness();
		return frete.findAllFrete();
	}
	public Collection<ModeloBean> findAllModelos(String descricao) throws Exception{
		validarUsuario();
		ModeloBusiness modelo = new ModeloBusiness();
		return modelo.findAllModelo(descricao);
	}
	public ModeloBean salvarModelo(ModeloBean bean) throws Exception{
		validarUsuario();
		ModeloBusiness modelo = new ModeloBusiness();
		return modelo.salvarModelo(bean); 
	}
	public ComplexidadeBean salvarComplexidade(ComplexidadeBean bean) throws Exception{
		validarUsuario();
		ComplexidadeBusiness business = new ComplexidadeBusiness();
		return business.salvarComplexidade(bean);
	}
	public CadSegmentoBean salvarSegmento (CadSegmentoBean bean) throws Exception{
		validarUsuario();
		CadSegmentoBusiness business = new CadSegmentoBusiness();
		return business.salvarSegmento(bean);		
	}
	public FatorBean salvarFator (FatorBean bean) throws Exception{
		validarUsuario();
		FatorBusiness business = new FatorBusiness();
		return business.salvarFator(bean);
	}
	public Boolean removerModelo(ModeloBean bean) throws Exception{
		validarUsuario();
		ModeloBusiness modelo = new ModeloBusiness();
		return modelo.removerModelo(bean);
	}
	public Boolean removerComplexidade(ComplexidadeBean bean) throws Exception{
		validarUsuario();
		ComplexidadeBusiness business = new ComplexidadeBusiness();
		return business.removerComplexidade(bean);		
	}
	public Boolean removerCadSegmento (CadSegmentoBean bean) throws Exception{
		validarUsuario();
		CadSegmentoBusiness business = new CadSegmentoBusiness();
		return business.removerCadSegmento (bean);
	}
	public Boolean removerCadPreco (PrecoBean bean) throws Exception{
		validarUsuario();
		PrecoBusiness business = new PrecoBusiness(this.usuarioBean);
		return business.removerCadPreco (bean);
	}
	public FatorBean findAllFator () throws Exception{
		validarUsuario();
		FatorBusiness business = new FatorBusiness();
		return business.findAllFator();
	}

	public List<PrefixoBean> findAllPrefixo(Long idModelo) throws Exception{
		validarUsuario();
		PrefixoBusiness prefixo = new PrefixoBusiness();
		return prefixo.findAllPrefixo(idModelo);
	}
	public List<PrefixoBean> findAllPrefixoClone(Long idModelo) throws Exception{
		validarUsuario();
		PrefixoBusiness prefixo = new PrefixoBusiness();
		return prefixo.findAllPrefixo(idModelo);
	}
	public PrefixoBean salvarPrefixo(PrefixoBean pre) throws Exception{
		validarUsuario();
		PrefixoBusiness prefixo = new PrefixoBusiness();
		return prefixo.salvarPrefixo(pre);
	}
	public TipoFreteBean salvarFrete(TipoFreteBean bean) throws Exception{
		validarUsuario();
		TipoFreteBusiness frete = new TipoFreteBusiness();
		return frete.salvarFrete(bean);
	}
	public Boolean removerPrefixo(PrefixoBean pre) throws Exception{
		validarUsuario();
		PrefixoBusiness prefixo = new PrefixoBusiness();
		return prefixo.removerPrefixo(pre);
	}
	public Boolean removerFrete(TipoFreteBean bean) throws Exception{
		validarUsuario();
		TipoFreteBusiness frete = new TipoFreteBusiness();
		return frete.removerFrete(bean);
	}
	public String removerPeca(PecasBean bean, Long idDocSegOper, String ultimoItem) throws Exception{
		validarUsuario();
		ImportXmlBusiness xml = new ImportXmlBusiness(this.usuarioBean);
		return xml.removerPeca(bean, idDocSegOper, ultimoItem);
	}

	public Boolean saveOrUpdate(PrecoBean bean) throws Exception{
		validarUsuario();
		PrecoBusiness business = new PrecoBusiness(this.usuarioBean);
		return business.saveOrUpdate(bean);
	}
	public FamiliaBean saveOrUpdate(FamiliaBean bean) throws Exception{
		validarUsuario();
		FamiliaBusiness business = new FamiliaBusiness();
		return business.saveOrUpdate(bean);
	}
	public Boolean remover(FamiliaBean bean) throws Exception{
		validarUsuario();
		FamiliaBusiness business = new FamiliaBusiness();
		return business.remover(bean);
	}
	
	public boolean removerNodo(Long idArv) throws Exception{
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.removerNodo(idArv);
	}
	
	public GestaoEquipamentosBean newOsEstimada(GestaoEquipamentosBean bean){ //, SegmentoBean bean2
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.newOsEstimada(bean); //, bean2
	}
	public List<SegmentoBean> findAllSegmento(Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllSegmento(idCheckin);
	}
	public List<SegmentoBean> findAllSegmentoJobControl(Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllSegmentoJobControl(idCheckin);
	}
	public List<SegmentoBean> findAllSegmentoTelaOs(Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllSegmento(idCheckin);
	}
	public List<SegmentoBean> findAllSegmentoPedido(Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllSegmentoPedido(idCheckin);
	}
	public List<SegmentoBean> findAllSegmentoServTerc(Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllSegmentoServTerc(idCheckin);
	}
	public List<OperacaoBean> findAllOperacoes (Long idSegmento) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findAllOperacoes(idSegmento);
	}
	public Long fazerUpload(byte[] bytesArquivo, Long idSegmento, Long idOper, Long idDocSegOper, String idFuncionario) throws Exception {
		this.validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		synchronized (business) {
			return business.salvarXml(bytesArquivo, idSegmento, idOper,
					idDocSegOper, idFuncionario);
		}
	}
	public Boolean savePecasAvulsoDocSeg(PecasBean bean, Long idSegmento, Long idOper, String idFuncionarioPecas) throws Exception {
		this.validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		synchronized (business) {
			return business.savePecasAvulsoDocSeg(bean, idSegmento, idOper, idFuncionarioPecas);
		}
	}
	public Long savePecasAvulsoIdDoc(PecasBean bean, Long idSegmento, String idFuncionarioPecas) throws Exception {
		this.validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		synchronized (business) {
			return business.savePecasAvulsoIdDoc(bean, idSegmento, null, idFuncionarioPecas);
		}
	}
	
	public String savePecasAvulso (PecasBean bean, Long idDocSegOper, String idFuncionarioPecas)throws Exception {
		this.validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		return business.savePecasAvulso(bean, idDocSegOper,idFuncionarioPecas);
	}
	public Long fazerUploadCsv(byte[] bytesArquivo, Long idModelo, Long idPrefixo) throws Exception {
		this.validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.salvarCsv(bytesArquivo, idModelo, idPrefixo);
	}
	public List<PecasBean> findAllPecas(Long idDocSegOper){
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.findAllPecas(idDocSegOper);
	}
	public List<PecasBean> findAllPecas(Long idSegmento, Long idOperacao){
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.findAllPecas(idSegmento, idOperacao, null);
	}
	public List<GeDocSegOperBean> findAllNumDocSem00E(Long idSegmento){
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.findAllNumDocSem00E(idSegmento);
	}
	public List<PecasBean> findAllPecasSem00E(Long idSegmento, Long idOperacao){
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.findAllPecasSem00E(idSegmento, idOperacao);
	}
	
	
	public ChekinBean saveOrcamento(ChekinBean bean){
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.saveOrcamento(bean);
	}
	public GeDocSegOperBean saveSegundoOrcamento(GeDocSegOperBean bean){
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.saveSegundoOrcamento(bean);		
	}
	public String saveDataOrcamento (ChekinBean bean, String dataOrcamento, List<UsuarioBean> usuarioList) throws Exception {
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		String isliberada = business.saveDataOrcamento(bean, true, dataOrcamento, usuarioList);//quando ao requisição vem da tela tem que inserir a data de negociação por isso tem o parametro true
		
		if(isliberada.equals("Transação realizada com sucesso!")){
			List<SegmentoBean> segList = this.findAllSegmento(bean.getId());
			for (SegmentoBean segmentoBean : segList) {
				this.alterarFluxoProcessoOficina(segmentoBean.getIdCheckin(), "A");
			}
		}
		return isliberada;
	}
	public List<FamiliaBean> findAllFamilia() throws Exception {
		validarUsuario();
		FamiliaBusiness business = new FamiliaBusiness();
		return business.findAllFamilia();
	}
	public TreeBean save(TreeBean bean, Long idPai, Long paiRoot, Long idFamilia) throws Exception {
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.save(bean, idPai, paiRoot, idFamilia);
	}
	public TreeBean findAllTree(Integer idArv) throws Exception{
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.findAllTree(idArv);
	}
	public List<TreeBean> findAllTreePai(String tipo, Long idFamilia) throws Exception{
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.findAllTreePai(tipo, idFamilia);
	}
	public List<TreeBean> findAllTreePaiClone(String tipo, Long idFamilia) throws Exception{
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.findAllTreePai(tipo, idFamilia);
	}
	public TreeBean saveNodoClone(Long idArv, Long idFamilia, String descricao) throws Exception {
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.saveNodoClone(idArv, idFamilia, descricao);
	}
	public Boolean removerTree(Long idArv) throws Exception{
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.removerTree(idArv);
	}
	public TreeBean saveNodo(TreeBean bean, Long idPaiRoot, Long idFamilia) throws Exception {
		validarUsuario();
		TreeBusiness business = new TreeBusiness();
		return business.saveNodo(bean, idPaiRoot, idFamilia);
	}
	public List<InspecaoGeTreeBean> findAllInspencaoGeTree(Long idOsPalm) throws Exception{
		validarUsuario();
		InspencaoGeTreeBusiness business = new InspencaoGeTreeBusiness();
		return business.findAllInspencaoPmpTree(idOsPalm);		
	}
	public List<Integer> findAllFotos(Integer idOsPalmDt) throws Exception{
		validarUsuario();
		InspencaoGeTreeBusiness business = new InspencaoGeTreeBusiness();
		return business.findAllFotos(idOsPalmDt);
	}
	public List<GeDocSegOperBean> verificaPecas (Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.verificaPecas (idCheckin);		
	}
	public List<ComplexidadeBean> findAllComplexidades () throws Exception{
		validarUsuario();
		ComplexidadeBusiness business = new ComplexidadeBusiness();
		return business.findAllComplexidade();		
	}
	public List<CadSegmentoBean> findAllSegmento () throws Exception{
		validarUsuario();
		CadSegmentoBusiness business = new CadSegmentoBusiness();
		return business.findAllSegmento ();
	}
	public Boolean validarNumSerieSegmento(String numSerie) throws Exception{
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.validarNumSerieSegmento(numSerie);
	}
	public Boolean verificarModelo(String modelo, String numSerie) throws Exception{
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(usuarioBean);
		return business.verificarModelo(modelo, numSerie);
	}
	public String findHoras (String prefixo, String jobCode, String compCode, String modelo, long idFamilia) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findHoras(prefixo, jobCode, compCode, modelo,idFamilia);
	}
	public String saveDataNegociacao (ChekinBean bean, String codCliente) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveDataNegociacao(bean, codCliente);
	}
	public Boolean saveDataPrevisao(Long idCheckin, String dataPrevisao, TipoFreteBean freteBean) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveDataPrevisao(idCheckin, dataPrevisao, freteBean);
	} 
	
	public Boolean saveDataConclusao(Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveDataConclusao(idCheckin, new Date());
	} 
	public String findDataPrevisao (Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findDataPrevisao(idCheckin);
	}
	public String findDataConclusao (Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findDataConclusao(idCheckin);
	}
	public String findServicosTerceiros (Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findServicosTerceiros(idCheckin);
	}
	public Boolean saveValorServcoTerceiros(String valorTerceiros, Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveValorServcoTerceiros (valorTerceiros, idCheckin);
	}
	public List<PrecoBean>findAllOrcamento( PrecoBean bean) throws Exception{
		validarUsuario();
		PrecoBusiness business = new PrecoBusiness(this.usuarioBean);
		return business.findAllOrcamento(bean);
		
	}
	public Boolean gerarCalculo(PrecoBean bean) throws Exception{
		validarUsuario();
		PrecoBusiness business = new PrecoBusiness(this.usuarioBean);
		return business.gerarCalculo(bean);
	}
	
	public String gerarCalculoUnitario(PrecoBean bean) throws Exception{
		validarUsuario();
		PrecoBusiness business = new PrecoBusiness(this.usuarioBean);
		return business.gerarCalculoUnitario(bean);
	}
	public ChekinBean verificarSePossuiCheckOut(Long idCheCkin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.verificarSePossuiCheckOut(idCheCkin);
	}
	public FatorReajusteBean salvarFatorReajuste(FatorReajusteBean bean) throws Exception {
		validarUsuario();
		FatorReajusteBusiness business = new FatorReajusteBusiness(usuarioBean);
		return business.salvarFatorReajuste(bean);
	}
	public FatorReajusteBean findAllFatorReajuste () throws Exception{
		validarUsuario();
		FatorReajusteBusiness business = new FatorReajusteBusiness(usuarioBean);
		return business.findAllFatorReajuste();
	}
	public Boolean inserirSegmento (List<SegmentoBean> segmentoList, ChekinBean checkInObj) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		for (int i = 0; i < segmentoList.size(); i++) {
			SegmentoBean bean = segmentoList.get(i);
			if(bean.getId() != null && bean.getId() > 0){
				segmentoList.remove(i);
				i--;
			}
		}
		if(segmentoList.size() > 0){
			if(!business.sendSegmentoDbs(segmentoList, checkInObj.getId(), usuarioBean)){
				return false;
			}
//			Thread.sleep(15000);
//			if(!business.sendOperacaoDbs(checkInObj, segmentoList)){
//				return false;
//			}
		}else{
			return false;
		}
		return true;
	}
	public Boolean sendOperacaoDbs(ChekinBean checkInObj, List<OperacaoBean> operacaoList, String numeroSegmento) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		for (int i = 0; i < operacaoList.size(); i++) {
			OperacaoBean bean = operacaoList.get(i);
			if(bean.getId() != null && bean.getId() > 0){
				operacaoList.remove(i);
				i--;
			}
		}
		return business.sendOperacaoDbs(checkInObj, operacaoList, numeroSegmento);
	}
	
	public Boolean verificarGerarcaoProposta(Long idChechIn) throws Exception{
		validarUsuario();
//		ChekinBusiness business = new ChekinBusiness(usuarioBean);
//		return business.verificarGerarcaoProposta(idChechIn);
		return true;
	}
	public TipoServicoBean saveOrUpdate(TipoServicoBean bean) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.saveOrUpdate(bean);
	}
	public Boolean remover(Long idTipoServico) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.remover(idTipoServico);
	}
	
	public Boolean removerSemSubstituicao(Long id) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.removerSemSubstituicao(id);
	}
	
	public  List<TipoServicoBean> findAllServicoTerceiros() throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.findAllServicoTerceiros();
	}
	public List<ServicoTerceirosBean> saveServicoTerceiros(ServicoTerceirosBean bean, Long idCheckin, String observacao) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.saveServicoTerceiros(bean, idCheckin, observacao);
	}
	public Boolean saveDataProposta(ServicoTerceirosBean bean, Long idCheckin, Long idFrete, String observacao) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.saveDataProposta(bean.getQtdDiasProposta(), idCheckin, observacao);
		
	}	
	
	public List<ServicoTerceirosBean> findAllServicoTerceirosAssociado(Long idSegmento) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.findAllServicoTerceirosAssociado(idSegmento);
	}
	public Boolean remover(ServicoTerceirosBean bean) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.remover(bean);
	}
	public String verificarAprovacaoProposta(Long idCheckin) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.verificarAprovacaoProposta(idCheckin);
	}
	public ContatoBean findContato (Long idOsPalm)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findContato(idOsPalm);	
	}
	public Boolean saveOrUpdateContato (Long idOsPalm, String contato, String telefone)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveOrUpdateContato(idOsPalm, contato, telefone);	
	}
	public List<FilialBean> findAllFilial ()throws Exception{
		validarUsuario();
		GestorRentalBusiness business = new GestorRentalBusiness();
		return business.findAllFilial();	
	}
	public Boolean removerGestor (GestorRentalBean bean)throws Exception{
		validarUsuario();
		GestorRentalBusiness business = new GestorRentalBusiness();
		return business.removerGestor(bean);	
	}
	public Boolean salvarGestorRental(GestorRentalBean bean) throws Exception{
		validarUsuario();
		GestorRentalBusiness business = new GestorRentalBusiness();
		return business.salvarGestorRental(bean); 
	}
	public List<GestorRentalBean> findAllGestorRental () throws Exception{
		validarUsuario();
		GestorRentalBusiness business = new GestorRentalBusiness();
		return business.findAllGestorRental ();
	}
	public List<GestorOSBean> findAllOSGestor () throws Exception{
		validarUsuario();
		GestorOsBusiness business = new GestorOsBusiness(usuarioBean);
		return business.findAllOSGestor ();
	}
	public List<GestorOSBean> findAllOSGarantiaGestor () throws Exception{
		validarUsuario();
		GestorOsBusiness business = new GestorOsBusiness(usuarioBean);
		return business.findAllOSGarantiaGestor();
	}
	public Boolean liberarOS(String numOS)throws Exception{
		validarUsuario();
		GestorOsBusiness business = new GestorOsBusiness(usuarioBean);
		return business.liberarOS(numOS);
	}
	public Boolean rejeitarOS(String tipoRejeicao, String obs, String numOS )throws Exception{
		validarUsuario();
		GestorOsBusiness business = new GestorOsBusiness(usuarioBean);
		return business.rejeitarOS(tipoRejeicao, obs, numOS);
	}
	public List<ModeloBean> findAllModeloOS(String tipo, Long idFamilia)throws Exception{
		validarUsuario();
		OSInternaBusiness business = new OSInternaBusiness (usuarioBean);
		return business.findAllModeloOS(tipo, idFamilia);
	}
//	public MotivoRejeicaoOSBean findMotivoRejeicao(Long idCheckin)throws Exception{
//		validarUsuario();
//		ChekinBusiness business = new ChekinBusiness (usuarioBean);
//		return business.findMotivoRejeicao(idCheckin);
//	}
	public Boolean saveOSInterna(OSInternaBean bean, String tipoInspecao)throws Exception{
		validarUsuario();
		OSInternaBusiness business = new OSInternaBusiness (usuarioBean);
		return business.saveOSInterna(bean, tipoInspecao);
	}
	public Boolean removerPecas(Long idDocSegOper)throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		return business.removerPecas(idDocSegOper);
	}
	public Boolean removerPecasSegmento(Long idSegmento) throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		return business.removerPecasSegmento(idSegmento);
	}
	public BigDecimal findIdDocSegOper(Long idSegmento)throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findIdDocSegOper(idSegmento);
	}
	public String findNumDocDocSegOper(Long idSegmento)throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.findNumDocDocSegOper(idSegmento);
	}
	public String removerSegmentoDBS(Long idSegmento, String numOs, String numSegmento) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.removerSegmentoDBS(idSegmento, numOs, numSegmento);
	}
	
	public Boolean verificarPendenciasDePecas() throws Exception{
		validarUsuario();

		return true;
	}

	public List<PecasBoTreeBean> buscaPecasBO(Long idCheckin) throws Exception {
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		
		return business.buscaPecasBO(idCheckin);
	}	
	
	public void changeUser(Long idFilial){
		usuarioBean = (UsuarioBean) FlexContext.getFlexSession().getAttribute("usuario");
		usuarioBean.setFilial(idFilial.toString());
		FlexContext.getFlexSession().setAttribute("usuario", usuarioBean);
	}
	
	public DetalhesPropostaBean findDetalhesProposta(ChekinBean chekinBean) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findDetalhesProposta(chekinBean);
	}
	public DetalhesPropostaBean saveOrUpdateDetalhesProposta(Long idCheckIn, DetalhesPropostaBean detalhesPropostaBean) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveOrUpdateDetalhesProposta(idCheckIn, detalhesPropostaBean);
	}

	public List<ChekinBean> findAllChekinByJobControl(String campoPesquisa) throws Exception{ // Busca as OS para o campo de pesquisa e o JobControl do funcionario.
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekinSuper(campoPesquisa, this.findJobControlBySiglaSistema());
	}	
	// Busca as OS para o campo de pesquisa e o JobControl selecionado no combo da tela do SupervisorCheckin.
	public List<ChekinBean> findAllChekinByJobControlCbx(String campoPesquisa, String jobControl) throws Exception{ 
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findAllChekinSuper(campoPesquisa, jobControl);
	}	
	public String findJobControlBySiglaSistema() throws Exception{
		validarUsuario();	
		for(SistemaBean bean : usuarioBean.getSistemaList()){
			if(!bean.getSigla().equals(null) && bean.getSigla().equals("GE")){
				this.usuarioBean.setJobControl(bean.getJobControl());
				return bean.getJobControl();
			}
		}		
		return null;
	}
	public Collection<String> findAllArquivos(FotosBean fotos) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);
		return fotosBusiness.findAllArquivos(fotos.getId());
	}	
	public List<FotosBean> findAllArquivosOcamentista(String numeroOs, String jobControl) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);
		return fotosBusiness.findAllArquivosOcamentista(numeroOs,jobControl);
	}	
	public Collection<String> findAllArquivos(ArquivosBean arquivos) throws Exception {
		this.validarUsuario();
		ArquivosBusiness business = new ArquivosBusiness();
		return business.findAllArquivos(arquivos.getId());
	}	
	public Boolean fazerUploadEmDiretorio(FotosBean bib, byte[] bytesArquivo, String nomeArquivo) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);		
		return fotosBusiness.fazerUploadEmDiretorio(bib, bytesArquivo, nomeArquivo);
	}
	public Boolean fazerUploadFotoOrcamentista(FotosBean bib, byte[] bytesArquivo, String nomeArquivo) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);		
		return fotosBusiness.fazerUploadFotoOrcamentista(bib, bytesArquivo, nomeArquivo);
	}
	public Boolean fazerUploadEmDiretorio(ArquivosBean arquivos, byte[] bytesArquivo, String nomeArquivo) throws Exception {
		this.validarUsuario();
		ArquivosBusiness arquivosBusiness = new ArquivosBusiness();		
		return arquivosBusiness.fazerUploadEmDiretorio(arquivos, bytesArquivo, nomeArquivo);
	}
	public Boolean removerArquivoBibliografia(FotosBean fotos, String nomeArquivo) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);		
		return fotosBusiness.removerArquivoDaBibliografia(fotos.getId(), nomeArquivo);
	}
	public Boolean removerFotoOrcamentista(FotosBean fotos) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);		
		return fotosBusiness.removerFotoOrcamentista(fotos.getId());
	}
	public Boolean editarFotoOrcamentista(FotosBean fotos) throws Exception {
		this.validarUsuario();
		FotosBusiness fotosBusiness = new FotosBusiness(usuarioBean);		
		return fotosBusiness.editarFotoOrcamentista(fotos);
	}
	public Boolean removerArquivoBibliografia(ArquivosBean arquivos, String nomeArquivo) throws Exception {
		this.validarUsuario();
		ArquivosBusiness arquivosBusiness = new ArquivosBusiness();	
		return arquivosBusiness.removerArquivoDaBibliografia(arquivos.getId(), nomeArquivo);
	}
	public Boolean removerSegmentoComCodErro(Long idSegmento, String numOs, String numSegmento, Long idCheckin) throws Exception{
		this.validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.removerSegmentoComCodErro(idSegmento, numOs, numSegmento, idCheckin);
	}
	public Boolean removerOperacaoComCodErro (Long idOperacao, Long idSegmento) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.removerOperacaoComCodErro(idOperacao, idSegmento);
	}
	public List<ChekinBean> findAllChekinDigitador(String campoPesquisa) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);		
		return business.findAllChekin(campoPesquisa, null);
	}
	public List<ChekinBean> findAllChekinConsultor(String campoPesquisa) throws Exception{
		validarUsuario();
		ConsultorCheckinBusiness business = new ConsultorCheckinBusiness(usuarioBean);		
		return business.findAllChekinConsultor(campoPesquisa);
	}

	public Boolean liberarOSParaDigitador (ChekinBean bean, String jobControl) throws Exception{
		validarUsuario();
		SupervisorCheckinBusiness business = new SupervisorCheckinBusiness(usuarioBean);
		return business.liberarOSParaDigitador(bean, jobControl);		
	}
	public Boolean liberarOSParaOrcamentista (ChekinBean bean) throws Exception{
		validarUsuario();
		DigitadorCheckinBusiness business = new DigitadorCheckinBusiness(usuarioBean);
		Boolean isliberada = business.liberarOSParaOrcamentista(bean);
		if(isliberada == true){
			List<SegmentoBean> segList = this.findAllSegmento(bean.getId());
			for (SegmentoBean segmentoBean : segList) {
				this.alterarFluxoProcessoOficina(segmentoBean.getIdCheckin(), "AO");
			}
		}
		return isliberada;
	}
	public Boolean consultorLiberarOS(String statusNegociacaoOS, String dataAprovacao, String obs, ChekinBean bean)throws Exception{
		validarUsuario();
		ConsultorCheckinBusiness business = new ConsultorCheckinBusiness(usuarioBean);
		return business.consultorLiberarOS(statusNegociacaoOS, dataAprovacao, obs, bean);
	}
	public Boolean saveDataTerminoServicos(ChekinBean bean, String dataTerminoServico, String jobControl) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveDataTerminoServicos(bean, dataTerminoServico, jobControl);
	}
	public Boolean saveDataFaturamento(ChekinBean bean, String dataFaturamento, String encerrarAutomatica) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		Boolean isliberada = business.saveDataFaturamento(bean, dataFaturamento, encerrarAutomatica);
		if(isliberada == true){
			Boolean isInvoice = business.verificarOSInvoice(bean.getNumeroOs());
			List<SegmentoBean> segList = this.findAllSegmento(bean.getId());
			for (SegmentoBean segmentoBean : segList) {
				this.alterarFluxoProcessoOficina(segmentoBean.getIdCheckin(), ((isInvoice == true)?"E":"F"));
			}
		}
		return isliberada;
	}
	public Boolean saveObsOS(ChekinBean bean, String obsOS, String obsJobControl, String jobControl)throws Exception{// Salva observação para OS.
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveObsOS(bean, obsOS, obsJobControl, jobControl);
	}
	public Boolean saveObsJobControl(String obsJobControl, Long idCheckIn, String jobControl)throws Exception{// Salva observação do job control
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.saveObsJobControl(obsJobControl, idCheckIn, jobControl);
	}
	public SituacaoOSBean findDatasOS(ChekinBean bean)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findDatasOS(bean);
	}
	public Boolean removerCheckin(ChekinBean bean)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.removerCheckin(bean);
	}
	public Boolean verificaPendeciasPecas (Long idCheckin) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.verificaPendeciasPecas(idCheckin);
	}
	public List<ChekinBean> findAllChekinGestorAprovacao(String campoPesquisa) throws Exception{
		validarUsuario();
		ConsultorCheckinBusiness business = new ConsultorCheckinBusiness(usuarioBean);
		return business.findAllChekinGestorAprovacao(campoPesquisa);
	}
	
	public ClienteInterBean saveOrUpdate(ClienteInterBean bean) throws Exception{
		validarUsuario();
		ClienteInterBusiness business = new ClienteInterBusiness();
		return business.saveOrUpdate(bean);
	}
	public Boolean remover(ClienteInterBean bean) throws Exception{
		validarUsuario();
		ClienteInterBusiness business = new ClienteInterBusiness();
		return business.remover(bean);
	}
	public List<UsuarioBean> findAllConsultorUsuario(Long idCheckIn, String tipoCliente)throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllConsultorUsuario(idCheckIn, tipoCliente);
	}
	
	public List<ChekinBean> findAllConsultorGaosChekin(String campoPesquisa, String tipoCliente)throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findAllConsultorGaosChekin(campoPesquisa, tipoCliente);
	}
	
	public String removerOS (ChekinBean bean, String osTxt) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.removerOS(bean, osTxt);
	}
	
	public Boolean saveResponsavelPecas (SegmentoBean segmentoBean) throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.saveResponsavelPecas(segmentoBean);		
	}
	
	public Boolean removerFuncionarioPecas (SegmentoBean segmentoBean) throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.removerFuncionarioPecas(segmentoBean);		
	}
	
	public SegmentoBean findSegmentoBy(Long idSegmento) throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness();
		return business.findSegmentoBy(idSegmento);	
	}
	
	public List<UsuarioBean> findAllFuncionariosByCampoPesquisa(String campoPesquisa) throws Exception{
		validarUsuario();
		ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		return business.findAllFuncionariosByCampoPesquisa(campoPesquisa);	
	}
	
	public String findObsOs(Long idCheckIn) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findObsOs(idCheckIn);
	}
	public String alterHoursSegmentoDbs(SegmentoBean segmentoBean, Long idCheckIn) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.alterHoursSegmentoDbs(segmentoBean, idCheckIn, usuarioBean);
	}
	
	public Boolean editJobControlSegmento(SegmentoBean seg, ChekinBean ch, String observacaojobControl) throws Exception {
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.editarJobControl(seg, ch, observacaojobControl);
	}
	public String sincronizarJBCD(String codigo) throws Exception {
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.sincronizarJBCD(codigo);
	}
	public String sincronizarCPTCD(String codigo) throws Exception {
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.sincronizarCPTCD(codigo);
	}
	public String findSituacaoOSBO(String numeroOs) throws Exception {
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findSituacaoOSBO(numeroOs);
	}
	public Boolean removerCotacaoDBS(Long idDocSegOper,String numCotacao) throws Exception{
		validarUsuario();
		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		return business.removerCotacaoDBS(idDocSegOper, numCotacao);
	}
	public FornecedorServicoTerceirosBean saveOrUpdateFornecedorServTerceiros(FornecedorServicoTerceirosBean fornecedor) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.saveOrUpdateFornecedorServTerceiros(fornecedor);
	}
	
	public List<FornecedorServicoTerceirosBean> findAllFornecedorServTerceiro() throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.findAllFornecedorServTerceiro();
	}
	
	public Boolean removerFornecedorServTerceiro(FornecedorServicoTerceirosBean bean) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.removerFornecedorServTerceiro(bean);
	}
	public Boolean saveOrUpdateSituacaoServTerc(ServicoTerceirosBean bean, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.saveOrUpdateSituacaoServTerc(bean, obs);
	}
	public List<SituacaoServTerceirosBean> findHistoricoOs(String numeroOs, Long idSegmento, Long idTipoServTerceiros)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findHistoricoOs(numeroOs,idSegmento,idTipoServTerceiros);
	}
	public List<ServicoTerceirosBean> findServTercMetrologia()throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServTercMetrologia();
	}
	
	public List<TipoServicoBean> findServTerceirosAssociados(Long idFornecedor)throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.findAllServTerceirosAssociados(idFornecedor);
	}
	public List<TipoServicoBean> findServTerceirosNaoAssociados(Long idFornecedor)throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.findAllServTerceirosNaoAssociados(idFornecedor);
	}
	
	public Long associarServicoTerceiro(Long idFornecedor, Long idTipoServico)throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.associarServicoTerceiro(idFornecedor, idTipoServico);
	}
	public Boolean desassociarServicoTerceiro(TipoServicoBean bean)throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(this.usuarioBean); 
		return business.desassociarServicoTerceiro(bean);
	}
	public Boolean aprovarServTercMetrologia(ServicoTerceirosBean servicoTerceirosBean, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.aprovarServTercMetrologia(servicoTerceirosBean, idSegmento, obs);
	}
	
	public Boolean concluirServicoFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.concluirServicoFornecedor(idTipoServicoTerceiros, idSegmento, obs);
	}
	public List<FornecedorServicoTerceirosBean> findAllFornecedoresAssociados(Long idTipoServTerceiros) throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness();
		return business.findAllFornecedoresAssociados(idTipoServTerceiros);
	}	
	public List<ServicoTerceirosBean> findServConclusaoFornecedor(String campoPesquisa)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServConclusaoFornecedor(campoPesquisa);
	}
	public List<ServicoTerceirosBean> findServTercRecepcao()throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServTercRecepcao();
	}
	public Boolean enviarServTercFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.enviarServTercFornecedor(idTipoServicoTerceiros, idSegmento, obs);
	}
	public Boolean receberServTercFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.receberServTercFornecedor(idTipoServicoTerceiros, idSegmento, obs);
	}
	public Boolean rejeitarServTercOficina(Long idTipoServicoTerceiros, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.rejeitarServTercOficina(idTipoServicoTerceiros, idSegmento, obs);
	}
	public Boolean rejeitarServTercRecepcao(Long idTipoServicoTerceiros, Long idSegmento, String obs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.rejeitarServTercRecepcao(idTipoServicoTerceiros, idSegmento, obs);
	}
	public List<ServicoTerceirosBean> findServTercHistorico(String numeroOs)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServTercHistorico(numeroOs);
	}
	public List<NaturezaOperacaoBean> findAllNaturezaOperacao()throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findAllNaturezaOperacao();
	}
	public List<ItemBean> findAllItem()throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findAllItem();
	}
	public List<ServicoTerceirosBean> findServTercNotaFiscal(String campo)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServTercNotaFiscal(campo);
	}
	 public Boolean fazerUploadEmDiretorioNotaFiscal(ServicoTerceirosBean servicoTerceirosBean, byte[] bytes, String nomeArquivo) throws Exception {
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.fazerUploadEmDiretorioNotaFiscal(servicoTerceirosBean, bytes, nomeArquivo);
	 }
	 public Boolean fazerUploadEmDiretorioDetalhes(String dirDetalhes, byte[] bytes, String nomeArquivo) throws Exception {
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.fazerUploadEmDiretorioDetalhes(dirDetalhes, bytes, nomeArquivo);
	 }
	 public String recuperarNomeArquivo(ServicoTerceirosBean servicoTerceirosBean) throws Exception {
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.recuperarNomeArquivo(servicoTerceirosBean.getId());
	 }
	 public String recuperarNomeArquivoDetalhes(String nomeArquivo) throws Exception {
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.recuperarNomeArquivoDetalhes(nomeArquivo);
	 }
	 
	 public Boolean aprovarServTercRecepcao(ServicoTerceirosBean servicoTerceirosBean, Long idSegmento, String obs)throws Exception{
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.aprovarServTercRecepcao(servicoTerceirosBean, idSegmento, obs);
	 }
	 public List<ItemBean> recuperarItensServTerc(ServicoTerceirosBean servicoTerceirosBean)throws Exception{
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.recuperarItensServTerc(servicoTerceirosBean);
	 }
	 public Boolean removerItem(Long id)throws Exception{
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.removerItem(id);
	 }
	 public ItemBean saveOrUpdate(ItemBean itemBean)throws Exception{
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.saveOrUpdate(itemBean);
	 }
	 
	 public Boolean removerArquivoDetalhes(String dirDetalhe)throws Exception{
		 validarUsuario();
		 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		 return business.removerArquivoDetalhes(dirDetalhe);
	 }
     public Boolean verificarServicoTerceiros(SegmentoBean bean)throws Exception{
		 validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.verificarServicoTerceiros(bean);
	 }
     public Boolean transferirServicoTerceiros(SegmentoBean bean, String numOs)throws Exception{
    	 validarUsuario();
    	 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
    	 return business.transferirServicoTerceiros(bean, numOs);
     }
     
     public Boolean fazerUploadEmDiretorioServicoTerceiros(byte[] bytes, String dir, String nomeArquivo)throws Exception{
    	 validarUsuario();
    	 SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
    	 return business.fazerUploadEmDiretorioServicoTerceiros(bytes, dir, nomeArquivo);
     }
 	public Collection<String> findAllArquivos(ServicoTerceirosBean arquivo) throws Exception {
		this.validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findAllArquivos(arquivo.getIdSegmento()+"_"+arquivo.getIdTipoServicoTerceiros());
	}	
 	public List<String> findAllJobControlOrc(Long idChekin) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.findAllJobControlOrc(idChekin);
 	}
 	
 	public Boolean inicarServicoFornecedor(Long idTipoServicoTerceiros, Long idSegmento)throws Exception{
 		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.inicarServicoFornecedor(idTipoServicoTerceiros, idSegmento);
 	}
 	public Boolean salvarDetalhesFotos(ChekinBean checkinBean, String jobControl, String tituloFotos, String descricaoFalhaFotos, String conclusaoFotos) throws Exception{
 		this.validarUsuario();
 		FotosBusiness business = new FotosBusiness(this.usuarioBean);
 		return business.salvarDetalhesFotos(checkinBean, jobControl, tituloFotos, descricaoFalhaFotos, conclusaoFotos);
 	}

 	public SegmentoBean findDetalhesFotos(ChekinBean checkinBean, String jobControl) throws Exception{
 		this.validarUsuario();
 		FotosBusiness business = new FotosBusiness(this.usuarioBean);
 		return business.findDetalhesFotos(checkinBean, jobControl);
 	}
 	public List<SegmentoBean> findAllSegmentoOs(String numeroOs) throws Exception{
 		this.validarUsuario();
 		ChekinBusiness business = new ChekinBusiness(this.usuarioBean);
 		ChekinBean bean = business.findCheckin(numeroOs);
 		if(bean == null){
 			return new ArrayList<SegmentoBean>();
 		}
 		SegmentoBusiness segmentoBusiness = new SegmentoBusiness(this.usuarioBean);
 		return segmentoBusiness.findAllSegmento(bean.getId());
 	}
 	public List<PecasBean> findAllPecasLog(String idDocSegOper) throws Exception{
 		this.validarUsuario();
 		PecasBusiness business = new PecasBusiness();
 		return business.findAllPecasLog(idDocSegOper);
 	}
 	public List<PecasBean> findAllPecas00E(String idDocSegOper) throws Exception{
 		this.validarUsuario();
 		PecasBusiness business = new PecasBusiness();
 		return business.findAllPecas00E(idDocSegOper);
 	}
 	public List<PecasBean> findAllPecasRemovidas(String idDocSegOper) throws Exception{
 		this.validarUsuario();
 		PecasBusiness business = new PecasBusiness();
 		return business.findAllPecasRemovidas(idDocSegOper);
 	}
 	public List<SegmentoBean> findAllSegmento(String numeroOs) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.findAllSegmento(numeroOs);
 	}
 	public List<SegmentoBean> findAllSegmentoLog(String numeroOs) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.findAllSegmentoLog(numeroOs);
 	}
 	public Boolean alterarFluxoProcessoOficina(Long idCheckin, String siglaStatus) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.alterarFluxoProcessoOficina(idCheckin, siglaStatus, null);
 	}
 	public Boolean alterarFluxoProcessoOficina(Long idCheckin, String siglaStatus, String dataHeader) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.alterarFluxoProcessoOficina(idCheckin, siglaStatus, dataHeader);
 	}
 	public boolean alterarFluxoProcessoOficinaJobControl(Long idCheckIn, String siglaStatus, String dateHeader, String jobControl) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.alterarFluxoProcessoOficinaJobControl(idCheckIn, siglaStatus, dateHeader, jobControl, null);
 	}
 	public boolean alterarFluxoProcessoOficinaJobControlLegenda(Long idCheckIn, String siglaStatus, String dateHeader, String observacao, String jobControl) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.alterarFluxoProcessoOficinaJobControl(idCheckIn, siglaStatus, dateHeader, jobControl, observacao);
 	}
 	public boolean alterarFluxoProcessoOficinaJobControlVisualizarSegmento(Long idCheckIn, String siglaStatus, String dateHeader, String jobControl) throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.alterarFluxoProcessoOficinaJobControlVisualizarSegmento(idCheckIn, siglaStatus, dateHeader, jobControl, null);
 	}
 	
 	
 	
 	public List<LegendaProcessoOficinaBean> findAllLegendaProcessoOficina() throws Exception{
 		this.validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
 		return business.findAllLegendaProcessoOficina();
 	}
 	
 	public List<DataHeaderBean> findAllHeaderList(String data) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		return business.findAllHeaderList(data);
	}
 	public List<SegmentoBean> findAllStatusFluxoSegmentoOs(List<DataHeaderBean> dataHeaderList, String tipoCliente, String jobControl, String campoPesquisa, String beginDate, String endDate, String codigos) throws Exception{
 		validarUsuario();
 		ChekinBusiness business = new ChekinBusiness(usuarioBean);
 		return business.findAllStatusFluxoSegmentoOs(dataHeaderList, tipoCliente, jobControl, campoPesquisa, beginDate, endDate, codigos);
 	}
 	
 	public List<DataHeaderBean> findAllHeaderNext(String data) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(data));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return business.findAllHeaderList(format.format(calendar.getTime()));
	}
 	
 	public List<DataHeaderBean> findAllHeaderPrevious(String data) throws Exception{
		validarUsuario();
		ChekinBusiness business = new ChekinBusiness(usuarioBean);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(data));
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return business.findAllHeaderList(format.format(calendar.getTime()));
	}
 	public List<SegmentoBean> findAllSegmentoAssociados(Long idSegmento) throws Exception{
 		validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(usuarioBean);
 		return business.findAllSegmentoAssociados(idSegmento);
 	}
 	public List<SegmentoBean> findAllSegmentoDesassociados(Long idCheckIn, Long idSegmento) throws Exception{
 		validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(usuarioBean);
 		return business.findAllSegmentoDesassociados(idCheckIn, idSegmento);
 	}
 	public Boolean desassocicarSegmento(Long idSegmento) throws Exception{
 		validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(usuarioBean);
 		return business.desassocicarSegmento(idSegmento);
 	}
 	public Boolean associcarSegmento(Long idSegmento, Long idSegmentoPai) throws Exception{
 		validarUsuario();
 		SegmentoBusiness business = new SegmentoBusiness(usuarioBean);
 		return business.associcarSegmento(idSegmento, idSegmentoPai);
 	}
 	public List<ServicoTerceirosBean> findServTercHistoricoAdministrador(String campoPesquisa)throws Exception{
		validarUsuario();
		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
		return business.findServTercHistoricoAdministrador(campoPesquisa);
	}
 	public Boolean savePrevisaoDataTerminoServico(ServicoTerceirosBean bean, String data)throws Exception{
 		validarUsuario();
 		SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(usuarioBean);
 		return business.savePrevisaoDataTerminoServico(bean, data);
 	}
 	public List<UsuarioBean> findAllOrcamentistaUsuario()throws Exception{
 		validarUsuario();
 		UsuarioBusiness business = new UsuarioBusiness(usuarioBean);
 		return business.findAllOrcamentistaUsuario();
 	}
 	public String voltarStatusOs(String numOs)throws Exception{
 		validarUsuario();
 		ContratoBusiness business = new ContratoBusiness();
 		return business.voltarStatusOs(numOs);
 	}
 	public List<CondicaoPagamentoBean> findAllCondicaoPagamento()throws Exception{
		validarUsuario();
		ContratoBusiness business = new ContratoBusiness();
		return business.findAllCondicaoPagamento();
	}
	
	public List<CondicaoPagamentoBean> findAllCondicaoPagamento(String campo)throws Exception{
		validarUsuario();
		ContratoBusiness business = new ContratoBusiness();
		return business.findAllCondicaoPagamento(campo);
	}
	public boolean salvarFormularioAprovarOs(GeFormularioAprovacaoOsBean bean) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.salvarFormularioAprovarOs(bean);
	}
	public boolean aprovarOs(Boolean isAprovarOs, String motivoRejeicao, GeFormularioAprovacaoOsBean bean)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.aprovarOs(isAprovarOs, motivoRejeicao, bean);
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOs(String campo) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOs(campo);
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsGarantia(String campo) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsGarantia(campo);
	}
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsConcessaoRental(String campo) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsConcessaoRental(campo);
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsCustomizacao(String campo) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsCustomizacao(campo);
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsRental(String campo)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsRental(campo);
	}
	
	public GeFormularioAprovacaoOsBean findFormularioAprovarOsById(Long id) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsById(id);
	}
	public Boolean findCodigoLiberacao(Long codigoLiberacao, String serie, String codcliente)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findCodigoLiberacao(codigoLiberacao, serie, codcliente);
	}
	public Boolean verificaSolicitacaoCriacaoOS(String serie, String codcliente)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.verificaSolicitacaoCriacaoOS(serie, codcliente);
	}
	public String verificaSolicitacaoCriacaoOSCliente(String serie, String codcliente, Long idCheckIn, String tipoCliente)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.verificaSolicitacaoCriacaoOSCliente(serie, codcliente, idCheckIn, tipoCliente);
	}
	public Boolean validarNumSerieAprovarOs(String numSerie)throws Exception{
		validarUsuario();
		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(this.usuarioBean);
		return business.validarNumSerieAprovarOs(numSerie.toUpperCase());
	}
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarRecepcaoOs(String serie, String codigoCliente)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarRecepcaoOs(serie, codigoCliente);
	}
	public Boolean removerFormularioAprovarOsById(Long id) throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.removerFormularioAprovarOsById(id);
	}
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsGerador(String campo)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findFormularioAprovarOsGerador(campo);
	}
	
	public List<ChekinBean> findAllChekinRentalGarantia(String campo)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findAllChekinRentalGarantia(campo);
	}
	public List<ChekinBean> findAllChekinRentalGarantiaUsadas(String campo)throws Exception{
		validarUsuario();
		ChekinBusiness chekinBusiness = new ChekinBusiness(this.usuarioBean);
		return chekinBusiness.findAllChekinRentalGarantiaUsadas(campo);
	}
}

package com.gestaoequipamentos.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ItemBean;
import com.gestaoequipamentos.bean.NaturezaOperacaoBean;
import com.gestaoequipamentos.bean.ServicoTerceirosBean;
import com.gestaoequipamentos.bean.SituacaoServTerceirosBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeItem;
import com.gestaoequipamentos.entity.GeLogSituacaoServTerceiros;
import com.gestaoequipamentos.entity.GeNaturezaOperacao;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSegmentoServTerceiros;
import com.gestaoequipamentos.entity.GeSituacaoItem;
import com.gestaoequipamentos.entity.GeSituacaoServTerceiros;
import com.gestaoequipamentos.entity.GeStatusServTerceiros;
import com.gestaoequipamentos.entity.GeTipoServicoTerceiros;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.UtilGestaoEquipamentosPesa;
import com.gestaoequipamentos.util.ValorMonetarioHelper;

public class SituacaoServicoTerceirosBusiness {
	private UsuarioBean usuarioBean;
	
	public SituacaoServicoTerceirosBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	/**
	 * Envia ou reenvia o serviço de terceiros para a metrologia
	 * @param bean
	 * @param obs
	 * @return
	 */
	public Boolean saveOrUpdateSituacaoServTerc(ServicoTerceirosBean bean, String obs){
		EntityManager manager = null;
		try {
			String SQL = "";
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeSituacaoServTerceiros terceiros = new GeSituacaoServTerceiros();
			Query query = manager.createQuery("from GeSituacaoServTerceiros where geSegmentoServTerceiros.geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
					" and geSegmentoServTerceiros.geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", bean.getIdSegmento());
			query.setParameter("idTipoServicoTerceiros", bean.getIdTipoServicoTerceiros());
			if(query.getResultList().size() > 0){
				terceiros = (GeSituacaoServTerceiros)query.getSingleResult();
			}
			terceiros.setDataEnvioMetrologia(new Date());
			terceiros.setIdFuncEnvioMetrologia(this.usuarioBean.getMatricula());
			terceiros.setObsEnvioMetrologia(obs);
			

			query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
			" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", bean.getIdSegmento());
			query.setParameter("idTipoServicoTerceiros", bean.getIdTipoServicoTerceiros());
			
			GeSegmentoServTerceiros geSegmentoServTerceiros = (GeSegmentoServTerceiros)query.getSingleResult();
			terceiros.setGeSegmentoServTerceiros(geSegmentoServTerceiros);
			
			if(bean.getLocalServico().equals("I")){
				terceiros.setDataEnvioMetrologia(new Date());
				terceiros.setIdFuncEnvioMetrologia(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioMetrologia("Serviço Interno.");
				
				terceiros.setDataEnvioNotaFiscal(new Date());
				terceiros.setIdFuncEnvioNotaFiscal(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioNotaFiscal("Serviço Interno.");
				
				terceiros.setDataEnvioRecepcao(new Date());
				terceiros.setIdFuncEnvioRecepcao(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioRecepcao(obs);
				
				query = manager.createQuery("from GeStatusServTerceiros where sigla = 'APREC'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				
				geSegmentoServTerceiros.setIdStatusServTerceiros(servTerceiros);
			}
			
			terceiros.setDataRejeicaoMetrologia(null);
			terceiros.setIdFuncRejeicaoMetrologia(null);
			terceiros.setObsRejeicaoMetrologia(null);
			
			

			
			
			query = manager.createQuery("from GeStatusServTerceiros where sigla =:sigla");

			if(terceiros.getGeSegmentoServTerceiros().getIdFornServTerceiros().getSigla() != null && terceiros.getGeSegmentoServTerceiros().getIdFornServTerceiros().getSigla().equals("MASTER")){
				terceiros.setDataEnvioMetrologia(new Date());
				terceiros.setIdFuncEnvioMetrologia(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioMetrologia("Serviço PESA.");
				
				terceiros.setDataEnvioNotaFiscal(new Date());
				terceiros.setIdFuncEnvioNotaFiscal(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioNotaFiscal("Serviço PESA.");
				
				terceiros.setDataEnvioRecepcao(new Date());
				terceiros.setIdFuncEnvioRecepcao(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioRecepcao("Serviço PESA.");

				terceiros.setDataEnvioFornecedor(new Date());
				terceiros.setIdFuncEnvioFornecedor(this.usuarioBean.getMatricula());
				terceiros.setObsEnvioFornecedor("Serviço PESA.");
				
				terceiros.setDataFinalizadoFornecedor(new Date());
				terceiros.setIdFuncFinalizadoFornecedor(this.usuarioBean.getMatricula());
				terceiros.setObsFinalizadoFornecedor("Serviço PESA.");

				terceiros.setDataEntradaEnvioMetrologia(new Date());
				terceiros.setIdFuncEntradaEnvioMetrologia(this.usuarioBean.getMatricula());
				terceiros.setObsEntradaEnvioMetrologia("Serviço PESA.");

				terceiros.setDataAprovacaoMetrologia(new Date());
				terceiros.setIdFuncAprovacaoMetrologia(this.usuarioBean.getMatricula());
				terceiros.setObsAprovacaoMetrologia(obs);
				
				query.setParameter("sigla", "AP");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				geSegmentoServTerceiros.setIdStatusServTerceiros(servTerceiros);
				SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
				" where func.EPIDNO = psu.ID_TW_USUARIO"+
				" and psu.ID_PERFIL in (select ID from ADM_PERFIL where SIGLA in ('SUPER','ORC', 'MTR') and TIPO_SISTEMA = 'GE')"+
				" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			}else {
				if(bean.getLocalServico().equals("E")){
					if(terceiros.getDataEntradaEnvioMetrologia() == null){
						query.setParameter("sigla", "MTR");
						GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
						geSegmentoServTerceiros.setIdStatusServTerceiros(servTerceiros);
					}else if(terceiros.getDataEntradaEnvioMetrologia() != null){
						query.setParameter("sigla", "METAS");
						GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
						geSegmentoServTerceiros.setIdStatusServTerceiros(servTerceiros);
					}
					SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
					" where func.EPIDNO = psu.ID_TW_USUARIO"+
					" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'MTR' and TIPO_SISTEMA = 'GE')"+
					" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
				}else{
					query.setParameter("sigla", "APREC");
					GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
					geSegmentoServTerceiros.setIdStatusServTerceiros(servTerceiros);
					SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
					" where func.EPIDNO = psu.ID_TW_USUARIO"+
					" and psu.ID_PERFIL in (select ID from ADM_PERFIL where SIGLA in ('RECP', 'MTR') and TIPO_SISTEMA = 'GE')"+
					" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
				}
			}
			manager.merge(terceiros);
			manager.merge(geSegmentoServTerceiros);
			
			
			manager.getTransaction().commit();
			GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, bean.getIdSegmento());
			
//			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
//					" where func.EPIDNO = psu.ID_TW_USUARIO"+
//					" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'MTR' and TIPO_SISTEMA = 'GE')"+
//					" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+bean.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado.\nOBS: "+obs, "Liberação de Serviço de Terceiros  Sistema Oficina", (String)pair[1]);
			}
			
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	/**
	 * Recupera os históricos dos serviços de terceiros
	 * @param bean
	 * @param obs
	 * @return
	 */
	public List<SituacaoServTerceirosBean> findHistoricoOs(String numeroOs, Long idSegmento, Long idTipoServTerceiros){
		EntityManager manager = null;
		List<SituacaoServTerceirosBean> result = new ArrayList<SituacaoServTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select "+
								" convert(varchar(10),sst.DATA_ENVIO_METROLOGIA,103)+' '+convert(varchar(10),sst.DATA_ENVIO_METROLOGIA,108) DATA_ENVIO_METROLOGIA, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_ENVIO_METROLOGIA) ID_FUNC_ENVIO_METROLOGIA, convert(varchar(4000), sst.OBS_ENVIO_METROLOGIA) OBS_ENVIO_METROLOGIA,"+
								" convert(varchar(10),sst.DATA_ENVIO_RECEPCAO,103)+' '+convert(varchar(10),sst.DATA_ENVIO_RECEPCAO,108) DATA_ENVIO_RECEPCAO, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_ENVIO_RECEPCAO) ID_FUNC_ENVIO_RECEPCAO, convert(varchar(4000),sst.OBS_ENVIO_RECEPCAO) OBS_ENVIO_RECEPCAO,"+
								" convert(varchar(10),sst.DATA_ENVIO_FORNECEDOR,103)+' '+convert(varchar(10),sst.DATA_ENVIO_FORNECEDOR,108) DATA_ENVIO_FORNECEDOR, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_ENVIO_FORNECEDOR) ID_FUNC_ENVIO_FORNECEDOR, convert(varchar(4000),sst.OBS_ENVIO_FORNECEDOR) OBS_ENVIO_FORNECEDOR,"+
								" convert(varchar(10),sst.DATA_FINALIZADO_FORNECEDOR,103)+' '+convert(varchar(10),sst.DATA_FINALIZADO_FORNECEDOR,108) DATA_FINALIZADO_FORNECEDOR, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_FINALIZADO_FORNECEDOR) ID_FUNC_FINALIZADO_FORNECEDOR, convert(varchar(4000),sst.OBS_FINALIZADO_FORNECEDOR) OBS_FINALIZADO_FORNECEDOR,"+
								" convert(varchar(10),sst.DATA_ENTRADA_ENVIO_METROLOGIA,103)+' '+convert(varchar(10),sst.DATA_ENTRADA_ENVIO_METROLOGIA,108) DATA_ENTRADA_ENVIO_METROLOGIA, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_ENTRADA_ENVIO_METROLOGIA) ID_FUNC_ENTRADA_ENVIO_METROLOGIA, convert(varchar(4000),sst.OBS_ENTRADA_ENVIO_METROLOGIA) OBS_ENTRADA_ENVIO_METROLOGIA,"+
								" convert(varchar(10),sst.DATA_APROVACAO_METROLOGIA,103)+' '+convert(varchar(10),sst.DATA_APROVACAO_METROLOGIA,108) DATA_APROVACAO_METROLOGIA, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_APROVACAO_METROLOGIA) ID_FUNC_APROVACAO_METROLOGIA, convert(varchar(4000),sst.OBS_APROVACAO_METROLOGIA) OBS_APROVACAO_METROLOGIA,"+
								" convert(varchar(10),sst.DATA_REJEICAO_METROLOGIA,103)+' '+convert(varchar(10),sst.DATA_REJEICAO_METROLOGIA,108) DATA_REJEICAO_METROLOGIA, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_REJEICAO_METROLOGIA) ID_FUNC_REJEICAO_METROLOGIA, convert(varchar(4000),sst.OBS_REJEICAO_METROLOGIA) OBS_REJEICAO_METROLOGIA," +
								" sst.ID, tp.DESCRICAO,"+
								" convert(varchar(10),sst.DATA_ENVIO_NOTA_FISCAL,103)+' '+convert(varchar(10),sst.DATA_ENVIO_NOTA_FISCAL,108) DATA_ENVIO_NOTA_FISCAL, (select EPLSNM from TW_FUNCIONARIO where EPIDNO = sst.ID_FUNC_ENVIO_NOTA_FISCAL) ID_FUNC_ENVIO_NOTA_FISCAL, convert(varchar(4000),sst.OBS_ENVIO_NOTA_FISCAL) OBS_ENVIO_NOTA_FISCAL" +
								" from GE_SEGMENTO_SERV_TERCEIROS st, GE_SEGMENTO seg, GE_CHECK_IN ch, GE_SITUACAO_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tp"+
								" where st.ID_SEGMENTO = seg.ID"+
								" and seg.ID_CHECKIN = ch.ID"+
								" and ch.NUMERO_OS =:NUMERO_OS"+
								" and sst.ID_SEGMENTO = st.ID_SEGMENTO"+
								" and sst.ID_TIPO_SERVICO_TERCEIROS = st.ID_TIPO_SERVICO_TERCEIROS" +
								" and tp.ID = st.ID_TIPO_SERVICO_TERCEIROS" +
								" and st.ID_SEGMENTO =:ID_SEGMENTO" +
								" and st.ID_TIPO_SERVICO_TERCEIROS =:ID_TIPO_SERVICO_TERCEIROS");
			query.setParameter("NUMERO_OS", numeroOs);
			query.setParameter("ID_SEGMENTO", idSegmento);
			query.setParameter("ID_TIPO_SERVICO_TERCEIROS", idTipoServTerceiros);
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			for (Object[] pair : objects) {
				SituacaoServTerceirosBean bean = new SituacaoServTerceirosBean();
				bean.setDataEnvioMetrologia((String)pair[0]);
				bean.setIdFuncEnvioMetrologia((String)pair[1]);
				bean.setObsEnvioMetrologia((String)pair[2]);
				
				bean.setDataEnvioNotaFiscal((String)pair[23]);
				bean.setIdFuncEnvioNotaFiscal((String)pair[24]);
				bean.setObsEnvioNotaFiscal((String)pair[25]);

				bean.setDataEnvioRecepcao((String)pair[3]);
				bean.setIdFuncEnvioRecepcao((String)pair[4]);
				bean.setObsEnvioRecepcao((String)pair[5]);

				bean.setDataEnvioFornecedor((String)pair[6]);
				bean.setIdFuncEnvioFornecedor((String)pair[7]);
				bean.setObsEnvioFornecedor((String)pair[8]);

				bean.setDataFinalizadoFornecedor((String)pair[9]);
				bean.setIdFuncFinalizadoFornecedor((String)pair[10]);
				bean.setObsFinalizadoFornecedor((String)pair[11]);

				bean.setDataEntradaEnvioMetrologia((String)pair[12]);
				bean.setIdFuncEntradaEnvioMetrologia((String)pair[13]);
				bean.setObsEntradaEnvioMetrologia((String)pair[14]);

				bean.setDataAprovacaoMetrologia((String)pair[15]);
				bean.setIdFuncAprovacaoMetrologia((String)pair[16]);
				bean.setObsAprovacaoMetrologia((String)pair[17]);

				bean.setDataRejeicaoMetrologia((String)pair[18]);
				bean.setIdFuncRejeicaoMetrologia((String)pair[19]);
				bean.setObsRejeicaoMetrologia((String)pair[20]);

				bean.setId(((BigDecimal)pair[21]).longValue());
				bean.setDescricao((String)pair[22]);
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	/**
	 * Recupera os serviços de terceiros liberados para metrologia
	 * @param bean
	 * @param obs
	 * @return
	 */
	public List<ServicoTerceirosBean> findServTercMetrologia(){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
												"   sst.DESCRICAO STATUS, sst.SIGLA from"+ 
												"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
												"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm"+
												"	where st.ID_STATUS_SERV_TERCEIROS in (select ID from GE_STATUS_SERV_TERCEIROS where SIGLA in ('MTR','METAS'))"+
												"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
												"   and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
												"	and seg.ID = st.ID_SEGMENTO"+
												"	and ch.ID = seg.ID_CHECKIN"+
												"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
												"	and ch.ID_OS_PALM = palm.IDOS_PALM" +
												"   and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
												"	and palm.FILIAL =:FILIAL");
			query.setParameter("FILIAL", Integer.valueOf(this.usuarioBean.getFilial()));
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setSiglaStatusServTerceiros((String)pair[11]);
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	/**
	 * Recupera os serviços de terceiros liberados para emissão de nota fiscal
	 * @param bean
	 * @param obs
	 * @return
	 */
	public List<ServicoTerceirosBean> findServTercNotaFiscal(String campo){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
					"   sst.DESCRICAO STATUS, sst.SIGLA, forn.CODIGO,"+
					"   (select f.EPLSNM from TW_FUNCIONARIO f where f.EPIDNO = situacao.ID_FUNC_ENVIO_NOTA_FISCAL) enviadoPor,"+
					"   convert(varchar(10), situacao.DATA_ENVIO_NOTA_FISCAL, 103)+' '+convert(varchar(10),situacao.DATA_ENVIO_NOTA_FISCAL,108) data_envio, situacao.ID," +
					"   (select STNM from TW_FILIAL where STNO = situacao.FILIAL) filial, forn.CNPJ," +
					"   (select SIGLA +' - '+DESCRICAO from GE_NATUREZA_OPERACAO where ID = situacao.ID_NATUREZA_OPERACAO) natOper," +
					"    convert(varchar(4000),situacao.OBS_ENVIO_NOTA_FISCAL) OBS_ENVIO_NOTA_FISCAL from"+ 
					"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
					"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_SITUACAO_SERV_TERCEIROS situacao"+
					"	where st.ID_STATUS_SERV_TERCEIROS in (select ID from GE_STATUS_SERV_TERCEIROS where SIGLA in ('SNF'))"+
					"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
					"   and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
					"	and seg.ID = st.ID_SEGMENTO"+
					"	and ch.ID = seg.ID_CHECKIN"+
					"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
					//"	and ch.ID_OS_PALM = palm.IDOS_PALM" +
					"   and st.ID_STATUS_SERV_TERCEIROS = sst.ID" +
					"   and situacao.ID_SEGMENTO = seg.ID" +
					"   and situacao.ID_TIPO_SERVICO_TERCEIROS = tst.ID";
			
			if(!campo.trim().equals("")){
				SQL += "and (tst.DESCRICAO = '"+campo+"' or forn.CODIGO = '"+campo+"' or forn.DESCRICAO = '"+campo+"' or forn.CNPJ = '"+campo+"')";
			}
			Query query = manager.createNativeQuery(SQL);
			//"	and palm.FILIAL =:FILIAL");
			//query.setParameter("FILIAL", Integer.valueOf(this.usuarioBean.getFilial()));
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setSiglaStatusServTerceiros((String)pair[11]);
				bean.setCodigoFornecedor((String)pair[12]);
				bean.setEnviadoPor((String)pair[13]);
				bean.setDataAbertura((String)pair[14]);
				bean.setId(((BigDecimal)pair[15]).longValue());
				bean.setNomeFilial((String)pair[16]);
				bean.setCnpj((String)pair[17]);
				bean.setNaturezaOperacao((String)pair[18]);
				bean.setObsEnvioNotaFiscal((String)pair[19]);
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	/**
	 * Recupera os serviços de terceiros liberados pelo número da OS
	 * @param bean
	 * @param obs
	 * @return
	 */
	public List<ServicoTerceirosBean> findServTercHistorico(String numeroOs){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
					"   sst.DESCRICAO STATUS, sst.SIGLA, situacao.ID from"+ 
					"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
					"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm, GE_SITUACAO_SERV_TERCEIROS situacao"+
					"	where st.ID_STATUS_SERV_TERCEIROS is not null"+
					"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
					"   and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
					"	and seg.ID = st.ID_SEGMENTO"+
					"	and ch.ID = seg.ID_CHECKIN"+
					"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
					"	and ch.ID_OS_PALM = palm.IDOS_PALM" +
					"   and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
					"   and situacao.ID_SEGMENTO = seg.ID" +
					"   and situacao.ID_TIPO_SERVICO_TERCEIROS = tst.ID"+
			"	and ch.NUMERO_OS =:numeroOs";
			if(this.usuarioBean.getSiglaPerfil().equals("FORN")){
				SQL += " and ID_FORN_SERV_TERCEIROS = "+this.usuarioBean.getIdFornecedor();
			}
			Query query = manager.createNativeQuery(SQL);
			query.setParameter("numeroOs", numeroOs);
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoServTerc = new SituacaoServicoTerceirosBusiness(null); 
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setArquivoDetalhe(situacaoServTerc.recuperarNomeArquivoDetalhes(((BigDecimal)pair[0]).longValue()+"_"+ ((BigDecimal)pair[1]).longValue())); 
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setSiglaStatusServTerceiros((String)pair[11]);
				bean.setId(((BigDecimal)pair[12]).longValue());
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	/**
	 * Recupera os serviços de terceiros liberados pelo número da OS
	 * @param bean
	 * @param obs
	 * @return
	 */
	public List<ServicoTerceirosBean> findServTercHistoricoAdministrador(String campoPesquisa){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
			"   sst.DESCRICAO STATUS, sst.SIGLA, situacao.ID, convert(varchar(10), st.DATA_PREVISAO_ENTREGA, 103) DATA_PREVISAO_ENTREGA, st.LOCAL_SERVICO, ch.STATUS_NEGOCIACAO_CONSULTOR" +
			"   from"+ 
			"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
			"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm, GE_SITUACAO_SERV_TERCEIROS situacao"+
			"	where st.ID_STATUS_SERV_TERCEIROS is not null"+
			"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
			"   and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
			"	and seg.ID = st.ID_SEGMENTO"+
			"	and ch.ID = seg.ID_CHECKIN"+
			"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
			"	and ch.ID_OS_PALM = palm.IDOS_PALM" +
			"   and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
			"   and situacao.ID_SEGMENTO = seg.ID" +
			"   and situacao.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
			"   and sst.SIGLA not in ('AP', 'OFI', 'REJOFI')" +
			"   and palm.FILIAL = " + Long.valueOf(this.usuarioBean.getFilial())+
			//"   and ch.STATUS_NEGOCIACAO_CONSULTOR <> 'R'"+
			"	and (ch.NUMERO_OS like '%"+campoPesquisa+"%'or FORN.DESCRICAO like '%"+campoPesquisa+"%' or tst.DESCRICAO like '%"+campoPesquisa+"%' or sst.DESCRICAO like '%"+campoPesquisa+"%')";
			
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoServTerc = new SituacaoServicoTerceirosBusiness(null); 
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setArquivoDetalhe(situacaoServTerc.recuperarNomeArquivoDetalhes(((BigDecimal)pair[0]).longValue()+"_"+ ((BigDecimal)pair[1]).longValue())); 
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setSiglaStatusServTerceiros((String)pair[11]);
				bean.setId(((BigDecimal)pair[12]).longValue());
				bean.setDataPrevisaoEntrega((String)pair[13]);
				if(((String)pair[14]).equals("I")){
					bean.setDescricaoLocalServico("Interno");
				}else{
					bean.setDescricaoLocalServico("Externo");
				}

				if(pair[15] != null && ((String)pair[15]).equals("A")){
					//set status Aprovado
					bean.setStatusNegociacao("APROVADO");
					bean.setStatusNegociacaoConsultor("A");
				}else if(pair[15] != null && ((String)pair[15]).equals("R")){
					//set status Ag. Aprovação
					bean.setStatusNegociacao("REJEITADO");
				}else{
					//set status Ag. Aprovação
					bean.setStatusNegociacao("AG. APROVAÇÃO");
				}
				
				bean.setUrlStatus("img/TD.png");
				if(((String)pair[11]).equals("ENVFOR")){
					bean.setUrlStatus("img/CN.png");
				}else if(((String)pair[11]).equals("EXE")){
					bean.setUrlStatus("img/AM.png");
				}

				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	/**
	 * Aprovação do serviço de terceiros pela metrologia
	 * @param bean
	 * @param obs
	 * @return
	 */
	public Boolean aprovarServTercMetrologia(ServicoTerceirosBean servicoTerceirosBean, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
					" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", servicoTerceirosBean.getIdTipoServicoTerceiros());
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				String sigla = "AP";
				//serviço enviado da metrologia para recepção
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);
				if(terceiros.getIdStatusServTerceiros().getSigla().equals("MTR")){ 
//					sigla = "APREC";
					sigla = "SNF";
					query = manager.createQuery("from GeStatusServTerceiros where sigla = '"+sigla+"'");
					GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
					terceiros.setIdStatusServTerceiros(servTerceiros);
					
					GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(servicoTerceirosBean.getIdTipoServicoTerceiros(), idSegmento, manager);
//					situacaoTerceiros.setDataEnvioRecepcao(new Date());
//					situacaoTerceiros.setIdFuncEnvioRecepcao(this.usuarioBean.getMatricula());
//					situacaoTerceiros.setObsEnvioRecepcao(obs);
					
					situacaoTerceiros.setDataEnvioNotaFiscal(new Date());
					situacaoTerceiros.setIdFuncEnvioNotaFiscal(this.usuarioBean.getMatricula());
					situacaoTerceiros.setObsEnvioNotaFiscal(obs);
					situacaoTerceiros.setFilial(Long.valueOf(this.usuarioBean.getFilial()));
					
					situacaoTerceiros.setIdNaturezaOperacao(manager.find(GeNaturezaOperacao.class, servicoTerceirosBean.getIdNaturezaOperacao()));
					
					List<ItemBean> itemList = servicoTerceirosBean.getItemList();
					for (ItemBean itemBean : itemList) {
						GeSituacaoItem situacaoItem = new GeSituacaoItem();
						situacaoItem.setIdItem(manager.find(GeItem.class, itemBean.getId()));
						situacaoItem.setIdSituacaoServTerc(situacaoTerceiros);
						situacaoItem.setQtd(itemBean.getQtd());
						manager.persist(situacaoItem);
					}					

					situacaoTerceiros.setDataRejeicaoMetrologia(null);
					situacaoTerceiros.setIdFuncRejeicaoMetrologia(null);
					situacaoTerceiros.setObsRejeicaoMetrologia(null);
					manager.merge(situacaoTerceiros);
					
					manager.getTransaction().commit();
					//this.sendMailRecepcao(terceiros, geSegmento, manager, obs);
					this.sendMailEmissorNotaFiscal(terceiros, geSegmento, manager, obs);
					
					
					
				}else if(terceiros.getIdStatusServTerceiros().getSigla().equals("METAS")){
					sigla = "AP";
					
					query = manager.createQuery("from GeStatusServTerceiros where sigla = '"+sigla+"'");
					GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
					terceiros.setIdStatusServTerceiros(servTerceiros);
					
					GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(servicoTerceirosBean.getIdTipoServicoTerceiros(), idSegmento, manager);
					situacaoTerceiros.setDataAprovacaoMetrologia(new Date());
					situacaoTerceiros.setIdFuncAprovacaoMetrologia(this.usuarioBean.getMatricula());
					situacaoTerceiros.setObsAprovacaoMetrologia(obs);
					
					situacaoTerceiros.setDataRejeicaoMetrologia(null);
					situacaoTerceiros.setIdFuncRejeicaoMetrologia(null);
					situacaoTerceiros.setObsRejeicaoMetrologia(null);
					
					manager.merge(situacaoTerceiros);
					
					manager.getTransaction().commit();
					this.sendMailEncarregadoOrcamentista(terceiros, geSegmento, manager, obs);
				}else{
					manager.getTransaction().commit();
				}
				
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	private GeSituacaoServTerceiros getSituacaoServTerceiros(
			Long idTipoServicoTerceiros, Long idSegmento, EntityManager manager) {
		Query query;
		GeSituacaoServTerceiros situacaoTerceiros = new GeSituacaoServTerceiros();
		query = manager.createQuery("from GeSituacaoServTerceiros where geSegmentoServTerceiros.geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
				" and geSegmentoServTerceiros.geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
		query.setParameter("idSegmento",idSegmento);
		query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
		if(query.getResultList().size() > 0){
			situacaoTerceiros = (GeSituacaoServTerceiros)query.getSingleResult();
		}
		return situacaoTerceiros;
	}
	
	private Boolean sendMailRecepcao(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs, Long filial){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());

			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'RECP' and TIPO_SISTEMA = 'GE')"+
			" and func.STN1 = "+filial;
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado para enviar para o fornecedor "+terceiros.getIdFornServTerceiros().getDescricao()+".\nOBS: "+obs, "Liberação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Boolean sendMailEmissorNotaFiscal(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ENF' and TIPO_SISTEMA = 'GE')";
			//" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado para enviar para criar a nota fiscal para o fornecedor "+terceiros.getIdFornServTerceiros().getDescricao()+".\nOBS: "+obs, "Solicitação de Criação de NF Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Boolean sendMailEncarregadoOrcamentista(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'SUPER' and TIPO_SISTEMA = 'GE')" +
			" and psu.JOB_CONTROL = '"+geSegmento.getJobControl()+"'"+
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Aprovado.\nOBS: "+obs, "Aprovação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ORC' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Aprovado.\nOBS: "+obs, "Aprovação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	private Boolean sendMailRejeicaoEncarregadoOrcamentista(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'SUPER' and TIPO_SISTEMA = 'GE')" +
			" and psu.JOB_CONTROL = '"+geSegmento.getJobControl()+"'"+
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Rejeitado para oficina.\nOBS: "+obs, "Rejeição de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ORC' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Rejeitado para oficina.\nOBS: "+obs, "Rejeição de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	private Boolean sendMailRejeicaoEncarregadoOrcamentistaRecepcao(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'SUPER' and TIPO_SISTEMA = 'GE')" +
			" and psu.JOB_CONTROL = '"+geSegmento.getJobControl()+"'"+
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Rejeitado para devolver ao fornecedor.\nOBS: "+obs, "Rejeição de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ORC' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Rejeitado para devolver ao fornecedor.\nOBS: "+obs, "Rejeição de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}

			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'RECP' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi Rejeitado para devolver ao fornecedor.\nOBS: "+obs, "Rejeição de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Rejeita o serviço de terceiros e envia para oficina
	 * @param idTipoServicoTerceiros
	 * @param idSegmento
	 * @param obs
	 * @return
	 */
	public Boolean rejeitarServTercOficina(Long idTipoServicoTerceiros, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
					" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				query = manager.createQuery("from GeStatusServTerceiros where sigla = 'REJOFI'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);
				manager.merge(terceiros);
				
				for(GeSituacaoServTerceiros situacaoServTerceiros : terceiros.getGeSituacaoServTerceirosCollection()){
					situacaoServTerceiros.setDataRejeicaoMetrologia(new Date());
					situacaoServTerceiros.setIdFuncRejeicaoMetrologia(this.usuarioBean.getMatricula());
					situacaoServTerceiros.setObsRejeicaoMetrologia(obs);
					
					GeLogSituacaoServTerceiros bean = new GeLogSituacaoServTerceiros();
					bean.setDataEnvioMetrologia(situacaoServTerceiros.getDataEnvioMetrologia());
					bean.setIdFuncEnvioMetrologia(situacaoServTerceiros.getIdFuncEnvioMetrologia());
					bean.setObsEnvioMetrologia(situacaoServTerceiros.getObsEnvioMetrologia());

					bean.setDataEnvioNotaFiscal(situacaoServTerceiros.getDataEnvioNotaFiscal());
					bean.setIdFuncEnvioNotaFiscal(situacaoServTerceiros.getIdFuncEnvioNotaFiscal());
					bean.setObsEnvioNotaFiscal(situacaoServTerceiros.getObsEnvioNotaFiscal());

					bean.setDataEnvioRecepcao(situacaoServTerceiros.getDataEnvioRecepcao());
					bean.setIdFuncEnvioRecepcao(situacaoServTerceiros.getIdFuncEnvioRecepcao());
					bean.setObsEnvioRecepcao(situacaoServTerceiros.getObsEnvioRecepcao());

					bean.setDataEnvioFornecedor(situacaoServTerceiros.getDataEnvioFornecedor());
					bean.setIdFuncEnvioFornecedor(situacaoServTerceiros.getIdFuncEnvioFornecedor());
					bean.setObsEnvioFornecedor(situacaoServTerceiros.getObsEnvioFornecedor());

					bean.setDataFinalizadoFornecedor(situacaoServTerceiros.getDataFinalizadoFornecedor());
					bean.setIdFuncFinalizadoFornecedor(situacaoServTerceiros.getIdFuncFinalizadoFornecedor());
					bean.setObsFinalizadoFornecedor(situacaoServTerceiros.getObsFinalizadoFornecedor());

					bean.setDataEntradaEnvioMetrologia(situacaoServTerceiros.getDataEntradaEnvioMetrologia());
					bean.setIdFuncEntradaEnvioMetrologia(situacaoServTerceiros.getIdFuncEntradaEnvioMetrologia());
					bean.setObsEntradaEnvioMetrologia(situacaoServTerceiros.getObsEntradaEnvioMetrologia());

					bean.setDataAprovacaoMetrologia(situacaoServTerceiros.getDataAprovacaoMetrologia());
					bean.setIdFuncAprovacaoMetrologia(situacaoServTerceiros.getIdFuncAprovacaoMetrologia());
					bean.setObsAprovacaoMetrologia(situacaoServTerceiros.getObsAprovacaoMetrologia());

					bean.setDataRejeicaoMetrologia(situacaoServTerceiros.getDataRejeicaoMetrologia());
					bean.setIdFuncRejeicaoMetrologia(situacaoServTerceiros.getIdFuncRejeicaoMetrologia());
					bean.setObsRejeicaoMetrologia(situacaoServTerceiros.getObsRejeicaoMetrologia());

					bean.setIdSituacaoServTerceiros(situacaoServTerceiros);
					manager.persist(bean);
					
				}
				manager.getTransaction().commit();
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);
				this.sendMailRejeicaoEncarregadoOrcamentista(terceiros, geSegmento, manager, obs);
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean rejeitarServTercRecepcao(Long idTipoServicoTerceiros, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
			" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				query = manager.createQuery("from GeStatusServTerceiros where sigla = 'REJREC'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);
				manager.merge(terceiros);
				
				for(GeSituacaoServTerceiros situacaoServTerceiros : terceiros.getGeSituacaoServTerceirosCollection()){
					situacaoServTerceiros.setDataRejeicaoMetrologia(new Date());
					situacaoServTerceiros.setIdFuncRejeicaoMetrologia(this.usuarioBean.getMatricula());
					situacaoServTerceiros.setObsRejeicaoMetrologia(obs);
					
					GeLogSituacaoServTerceiros bean = new GeLogSituacaoServTerceiros();
					bean.setDataEnvioMetrologia(situacaoServTerceiros.getDataEnvioMetrologia());
					bean.setIdFuncEnvioMetrologia(situacaoServTerceiros.getIdFuncEnvioMetrologia());
					bean.setObsEnvioMetrologia(situacaoServTerceiros.getObsEnvioMetrologia());
					
					bean.setDataEnvioNotaFiscal(situacaoServTerceiros.getDataEnvioNotaFiscal());
					bean.setIdFuncEnvioNotaFiscal(situacaoServTerceiros.getIdFuncEnvioNotaFiscal());
					bean.setObsEnvioNotaFiscal(situacaoServTerceiros.getObsEnvioNotaFiscal());
					
					bean.setDataEnvioRecepcao(situacaoServTerceiros.getDataEnvioRecepcao());
					bean.setIdFuncEnvioRecepcao(situacaoServTerceiros.getIdFuncEnvioRecepcao());
					bean.setObsEnvioRecepcao(situacaoServTerceiros.getObsEnvioRecepcao());
					
					bean.setDataEnvioFornecedor(situacaoServTerceiros.getDataEnvioFornecedor());
					bean.setIdFuncEnvioFornecedor(situacaoServTerceiros.getIdFuncEnvioFornecedor());
					bean.setObsEnvioFornecedor(situacaoServTerceiros.getObsEnvioFornecedor());
					
					bean.setDataFinalizadoFornecedor(situacaoServTerceiros.getDataFinalizadoFornecedor());
					bean.setIdFuncFinalizadoFornecedor(situacaoServTerceiros.getIdFuncFinalizadoFornecedor());
					bean.setObsFinalizadoFornecedor(situacaoServTerceiros.getObsFinalizadoFornecedor());
					
					bean.setDataEntradaEnvioMetrologia(situacaoServTerceiros.getDataEntradaEnvioMetrologia());
					bean.setIdFuncEntradaEnvioMetrologia(situacaoServTerceiros.getIdFuncEntradaEnvioMetrologia());
					bean.setObsEntradaEnvioMetrologia(situacaoServTerceiros.getObsEntradaEnvioMetrologia());
					
					bean.setDataAprovacaoMetrologia(situacaoServTerceiros.getDataAprovacaoMetrologia());
					bean.setIdFuncAprovacaoMetrologia(situacaoServTerceiros.getIdFuncAprovacaoMetrologia());
					bean.setObsAprovacaoMetrologia(situacaoServTerceiros.getObsAprovacaoMetrologia());
					
					bean.setDataRejeicaoMetrologia(situacaoServTerceiros.getDataRejeicaoMetrologia());
					bean.setIdFuncRejeicaoMetrologia(situacaoServTerceiros.getIdFuncRejeicaoMetrologia());
					bean.setObsRejeicaoMetrologia(situacaoServTerceiros.getObsRejeicaoMetrologia());
					
					bean.setIdSituacaoServTerceiros(situacaoServTerceiros);
					manager.persist(bean);
					
				}
				manager.getTransaction().commit();
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);
				this.sendMailRejeicaoEncarregadoOrcamentistaRecepcao(terceiros, geSegmento, manager, obs);
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public List<ServicoTerceirosBean> findServConclusaoFornecedor(String campoPesquisa){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
			"   sst.DESCRICAO STATUS, palm.MODELO, palm.SERIE, sst.SIGLA, 'Não' garantia, ch.STATUS_NEGOCIACAO_CONSULTOR, convert(varchar(10), st.DATA_PREVISAO_ENTREGA, 103) DATA_PREVISAO_ENTREGA from"+ 
			"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
			"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm"+
			"	where st.ID_STATUS_SERV_TERCEIROS in (select ID from GE_STATUS_SERV_TERCEIROS where SIGLA in ('ENVFOR', 'EXE'))"+
			"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID"+
			"	and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
			"	and seg.ID = st.ID_SEGMENTO"+
			"	and ch.ID = seg.ID_CHECKIN"+
			"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
			"	and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
			"   and ID_FORN_SERV_TERCEIROS =:ID_FORNECEDOR" +
			"   and palm.IDOS_PALM = ch.ID_OS_PALM" +
			"  and SUBSTRING (ch.cod_Cliente, 5, 3)+ch.tipo_Cliente NOT IN ('243INT','247INT','249INT','023INT','241INT','463INT','468INT','267INT','268INT','288INT','998INT', '025INT')";
			if(campoPesquisa != null && !"".equals(campoPesquisa)){
				SQL += "   and (ch.NUMERO_OS like '%"+campoPesquisa+"%' or palm.MODELO like '%"+campoPesquisa+"%' or palm.SERIE like '%"+campoPesquisa+"%')"; 
			}
			SQL +=" union "+
			"select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
			"   sst.DESCRICAO STATUS, palm.MODELO, palm.SERIE, sst.SIGLA, 'Sim' garantia, ch.STATUS_NEGOCIACAO_CONSULTOR, convert(varchar(10), st.DATA_PREVISAO_ENTREGA, 103) DATA_PREVISAO_ENTREGA from"+ 
			"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
			"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm"+
			"	where st.ID_STATUS_SERV_TERCEIROS in (select ID from GE_STATUS_SERV_TERCEIROS where SIGLA in ('ENVFOR', 'EXE'))"+
			"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID"+
			"	and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
			"	and seg.ID = st.ID_SEGMENTO"+
			"	and ch.ID = seg.ID_CHECKIN"+
			"	and st.ID_FORN_SERV_TERCEIROS = forn.ID"+
			"	and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
			"   and ID_FORN_SERV_TERCEIROS =:ID_FORNECEDOR" +
			"   and palm.IDOS_PALM = ch.ID_OS_PALM" +
			"  and SUBSTRING (ch.cod_Cliente, 5, 3)+ch.tipo_Cliente IN ('243INT','247INT','249INT','023INT','241INT','463INT','468INT','267INT','268INT','288INT','998INT', '025INT')";
			if(campoPesquisa != null && !"".equals(campoPesquisa)){
				SQL += "   and (ch.NUMERO_OS like '%"+campoPesquisa+"%' or palm.MODELO like '%"+campoPesquisa+"%' or palm.SERIE like '%"+campoPesquisa+"%')"; 
			}
			Query query = manager.createNativeQuery(SQL);
			query.setParameter("ID_FORNECEDOR", this.usuarioBean.getIdFornecedor());
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setModelo((String)pair[11]);
				bean.setSerie((String)pair[12]);
				if(((String)pair[13]).equals("ENVFOR")){
					bean.setUrlStatus("img/CN.png");
				}else if(((String)pair[13]).equals("EXE")){
					bean.setUrlStatus("img/AM.png");
				}
				bean.setStatusServico((String)pair[13]);
				bean.setGarantia((String)pair[14]);
				if(pair[15] != null && ((String)pair[15]).equals("A")){
					//set status Aprovado
					bean.setStatusNegociacao("APROVADO");
					bean.setStatusNegociacaoConsultor("A");
				}else{
					//set status Ag. Aprovação
					bean.setStatusNegociacao("AG. APROVAÇÃO");
				}
				bean.setDataPrevisaoEntrega((String)pair[16]);
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	public List<ServicoTerceirosBean> findServTercRecepcao(){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select st.ID_SEGMENTO, st.ID_TIPO_SERVICO_TERCEIROS ,ch.NUMERO_OS, seg.NUMERO_SEGMENTO, seg.JOB_CONTROL, FORN.DESCRICAO FORNECEDOR, tst.DESCRICAO, st.QTD, st.VALOR, convert(varchar(4000),st.OBS) OBS, " +
					"   sst.DESCRICAO STATUS, sst.SIGLA, situacao.ID id_situacao_serv_terc, st.LOCAL_SERVICO from"+ 
					"	GE_SEGMENTO_SERV_TERCEIROS st, GE_STATUS_SERV_TERCEIROS sst, GE_TIPO_SERVICO_TERCEIROS tst, GE_SEGMENTO seg,"+
					"	GE_CHECK_IN ch, GE_FORNECEDOR_SERV_TERCEIROS forn, GE_OS_PALM palm, GE_SITUACAO_SERV_TERCEIROS situacao"+
					"	where st.ID_STATUS_SERV_TERCEIROS in (select ID from GE_STATUS_SERV_TERCEIROS where SIGLA in ('APREC','FINFOR','REJREC'))"+
					"	and st.ID_TIPO_SERVICO_TERCEIROS = tst.ID" +
					"	and tst.ID = st.ID_TIPO_SERVICO_TERCEIROS"+
					"	and seg.ID = st.ID_SEGMENTO"+
					"	and ch.ID = seg.ID_CHECKIN"+
					"	and st.ID_FORN_SERV_TERCEIROS = forn.ID" +
					"	and st.ID_STATUS_SERV_TERCEIROS = sst.ID"+
					"	and ch.ID_OS_PALM = palm.IDOS_PALM"+
					"	and situacao.ID_TIPO_SERVICO_TERCEIROS = st.ID_TIPO_SERVICO_TERCEIROS"+
					"	and situacao.ID_SEGMENTO = seg.ID"+
			"   and palm.FILIAL =:FILIAL");
			query.setParameter("FILIAL", Integer.valueOf(this.usuarioBean.getFilial()));
			List<Object[]> objects = (List<Object[]>)query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoServTerc = new SituacaoServicoTerceirosBusiness(null);
			for (Object[] pair : objects) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				bean.setIdSegmento(((BigDecimal)pair[0]).longValue());
				bean.setIdTipoServicoTerceiros(((BigDecimal)pair[1]).longValue());
				bean.setArquivoDetalhe(situacaoServTerc.recuperarNomeArquivoDetalhes(((BigDecimal)pair[0]).longValue()+"_"+ ((BigDecimal)pair[1]).longValue()));
				bean.setNumeroOs((String)pair[2]);
				
				bean.setNumeroSegmento((String)pair[3]);
				bean.setJobControl((String)pair[4]);
				bean.setDescricaoFornecedor((String)pair[5]);
				
				bean.setDescricao((String)pair[6]);
				bean.setQtdServTerceiros(((BigDecimal)pair[7]).longValue());
				
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[8]).longValue())));
				bean.setObs((String)pair[9]);
				bean.setStatusServTerceiros((String)pair[10]);
				bean.setSiglaStatusServTerceiros((String)pair[11]);
				bean.setId(((BigDecimal)pair[12]).longValue());
				if(((String)pair[13]).equals("I")){
					bean.setDescricaoLocalServico("Interno");
				}else{
					bean.setDescricaoLocalServico("Externo");
				}
				result.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public Boolean enviarServTercFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
 			manager.getTransaction().begin();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
			" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);

				String sigla = "ENVFOR";
				query = manager.createQuery("from GeStatusServTerceiros where sigla = '"+sigla+"'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);

				GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(idTipoServicoTerceiros, idSegmento, manager);
				situacaoTerceiros.setDataEnvioFornecedor(new Date());
				situacaoTerceiros.setIdFuncEnvioFornecedor(this.usuarioBean.getMatricula());
				situacaoTerceiros.setObsEnvioFornecedor(obs);
				manager.merge(situacaoTerceiros);

				manager.getTransaction().commit();
				if(terceiros.getLocalServico().equals("E")){
					this.sendMailFornecedor(terceiros, geSegmento, manager, obs);
				}

			}else{
				manager.getTransaction().commit();
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public Boolean sendMailFornecedor(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL, func.STN1 from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'FORN' and TIPO_SISTEMA = 'GE')" +
			" and func.ID_FORNECEDOR = "+terceiros.getIdFornServTerceiros().getId();
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				if(terceiros.getLocalServico().equals("E")){
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi enviado.\nOBS: "+obs, "Envio de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
				}else{
					TwFilial filial = manager.find(TwFilial.class, Long.valueOf((String)pair[2]));
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado para ser realizado na filial "+filial.getStnm()+".\nOBS: "+obs, "Solicitação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
					
				}
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean sendMailNotaFiscalFornecedor(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL, func.STN1 from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'FORN' and TIPO_SISTEMA = 'GE')" +
			" and func.ID_FORNECEDOR = "+terceiros.getIdFornServTerceiros().getId();
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				if(terceiros.getLocalServico().equals("E")){
					if(terceiros.getIdFornServTerceiros().getId() == 29){
						new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" está disponível para retirada na PESA e já possui nota fiscal para impressão.", "Serviço liberado para retirada Sistema Oficina", "rodrigo@rdrsistemas.com.br");
					}
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" está disponível para retirada na PESA e já possui nota fiscal para impressão.", "Serviço liberado para retirada Sistema Oficina", (String)pair[1]);
				}else{
					TwFilial filial = manager.find(TwFilial.class, Long.valueOf((String)pair[2]));
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado para ser realizado na filial "+filial.getStnm()+".", "Solicitação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
					
				}
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean sendMailAprovacaoFornecedor(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL, func.STN1 from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'FORN' and TIPO_SISTEMA = 'GE')" +
			" and func.ID_FORNECEDOR = "+terceiros.getIdFornServTerceiros().getId();
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				if(terceiros.getLocalServico().equals("E")){
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi aprovado para ser executado. "+obs, "Aprovação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
				}else{
					TwFilial filial = manager.find(TwFilial.class, Long.valueOf((String)pair[2]));
					new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi liberado para ser realizado na filial "+filial.getStnm()+". "+obs, "Solicitação de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
					
				}
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean receberServTercFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
			" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);

				String sigla = "METAS";
				query = manager.createQuery("from GeStatusServTerceiros where sigla = '"+sigla+"'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);

				GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(idTipoServicoTerceiros, idSegmento, manager);
				situacaoTerceiros.setDataEntradaEnvioMetrologia(new Date());
				situacaoTerceiros.setIdFuncEntradaEnvioMetrologia(this.usuarioBean.getMatricula());
				situacaoTerceiros.setObsEntradaEnvioMetrologia(obs);
				manager.merge(situacaoTerceiros);

				manager.getTransaction().commit();
				this.sendMailEntradaMetrologia(terceiros, geSegmento, manager, obs);

			}else{
				manager.getTransaction().commit();
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	private Boolean sendMailEntradaMetrologia(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'SUPER' and TIPO_SISTEMA = 'GE')" +
			" and psu.JOB_CONTROL = '"+geSegmento.getJobControl()+"'"+
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi entregue para a aprovação.\nOBS: "+obs, "Entrada de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ORC' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi entregue para a aprovação.\nOBS: "+obs, "Entrada de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}

			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'MTR' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(this.usuarioBean.getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi entregue para a aprovação.\nOBS: "+obs, "Entrada de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean concluirServicoFornecedor(Long idTipoServicoTerceiros, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
					" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				String sigla = "AP";
				//serviço enviado da metrologia para recepção
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);


				query = manager.createQuery("from GeStatusServTerceiros where sigla = 'FINFOR'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);

				GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(idTipoServicoTerceiros, idSegmento, manager);
				situacaoTerceiros.setDataFinalizadoFornecedor(new Date());
				situacaoTerceiros.setIdFuncFinalizadoFornecedor(this.usuarioBean.getMatricula());
				situacaoTerceiros.setObsFinalizadoFornecedor(obs);
				if(terceiros.getLocalServico().equals("I")){
										
					situacaoTerceiros.setDataEntradaEnvioMetrologia(new Date());
					situacaoTerceiros.setIdFuncEntradaEnvioMetrologia(this.usuarioBean.getMatricula());
					situacaoTerceiros.setObsEntradaEnvioMetrologia("Serviço Interno");
					
					situacaoTerceiros.setDataAprovacaoMetrologia(new Date());
					situacaoTerceiros.setIdFuncAprovacaoMetrologia(this.usuarioBean.getMatricula());
					situacaoTerceiros.setObsAprovacaoMetrologia("Serviço Interno");
					
					situacaoTerceiros.setDataRejeicaoMetrologia(null);
					situacaoTerceiros.setIdFuncRejeicaoMetrologia(null);
					situacaoTerceiros.setObsRejeicaoMetrologia(null);
					
					query = manager.createQuery("from GeStatusServTerceiros where sigla = 'AP'");
					servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
					terceiros.setIdStatusServTerceiros(servTerceiros);
					
				}
				manager.merge(situacaoTerceiros);

				manager.getTransaction().commit();
				this.sendMailConcluirServico(terceiros, geSegmento, manager, obs);

			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean inicarServicoFornecedor(Long idTipoServicoTerceiros, Long idSegmento){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
			" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", idTipoServicoTerceiros);
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				query = manager.createQuery("from GeStatusServTerceiros where sigla = 'EXE'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);
			
				manager.merge(terceiros);
				
				manager.getTransaction().commit();
				
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	private Boolean sendMailConcluirServico(GeSegmentoServTerceiros terceiros, GeSegmento geSegmento, EntityManager manager, String obs){
		try {
			GeTipoServicoTerceiros servicoTerceiros = manager.find(GeTipoServicoTerceiros.class, terceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
			
			String SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'SUPER' and TIPO_SISTEMA = 'GE')" +
			" and psu.JOB_CONTROL = '"+geSegmento.getJobControl()+"'"+
			" and func.STN1 = "+Integer.valueOf(geSegmento.getIdCheckin().getIdOsPalm().getFilial());
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi concluido.\nOBS: "+obs, "Conclusão de Serviço de Terceiros", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
			" where func.EPIDNO = psu.ID_TW_USUARIO"+
			" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'ORC' and TIPO_SISTEMA = 'GE')" +
			" and func.STN1 = "+Integer.valueOf(geSegmento.getIdCheckin().getIdOsPalm().getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi concluido.\nOBS: "+obs, "Conclusão de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
					" where func.EPIDNO = psu.ID_TW_USUARIO"+
					" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'MTR' and TIPO_SISTEMA = 'GE')" +
					" and func.STN1 = "+Integer.valueOf(geSegmento.getIdCheckin().getIdOsPalm().getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi concluido.\nOBS: "+obs, "Conclusão de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			SQL = "select EPLSNM, EMAIL from TW_FUNCIONARIO func, ADM_PERFIL_SISTEMA_USUARIO psu"+
					" where func.EPIDNO = psu.ID_TW_USUARIO"+
					" and psu.ID_PERFIL = (select ID from ADM_PERFIL where SIGLA = 'RECP' and TIPO_SISTEMA = 'GE')" +
					" and func.STN1 = "+Integer.valueOf(geSegmento.getIdCheckin().getIdOsPalm().getFilial());
			query = manager.createNativeQuery(SQL);
			objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail(((String)pair[0])+" o serviço de terceiros "+servicoTerceiros.getDescricao()+" da OS "+geSegmento.getIdCheckin().getNumeroOs()+" foi concluido.\nOBS: "+obs, "Conclusão de Serviço de Terceiros Sistema Oficina", (String)pair[1]);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
		System.out.println(dateFormat.format(new Date()));
	}
	
	public List<NaturezaOperacaoBean> findAllNaturezaOperacao(){
		List<NaturezaOperacaoBean> naturezaOperacaoList = new ArrayList<NaturezaOperacaoBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From GeNaturezaOperacao where sigla not in('Z28','Z29') order by descricao");
			List<GeNaturezaOperacao> result = query.getResultList();
			for(GeNaturezaOperacao nop : result){
				NaturezaOperacaoBean bean = new NaturezaOperacaoBean();
				bean.setId(nop.getId());
				bean.setDescricao(nop.getSigla()+" - "+nop.getDescricao());
				bean.setSigla(nop.getSigla());
				naturezaOperacaoList.add(bean);
			}

		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return naturezaOperacaoList;

	}
	public List<ItemBean> findAllItem(){
		List<ItemBean> itemList = new ArrayList<ItemBean>();
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("From GeItem order by descricao");
			List<GeItem> result = query.getResultList();
			for(GeItem item : result){
				ItemBean bean = new ItemBean();
				bean.setId(item.getId());
				bean.setDescricao(item.getDescricao());
				bean.setCodigo(item.getCodigo());
				bean.setValor((String.valueOf(ValorMonetarioHelper.formata("###,###,##0.00",
                		item.getValor().doubleValue()))));
				itemList.add(bean);
			}
			
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return itemList;
		
	}
	
	public Boolean fazerUploadEmDiretorioNotaFiscal(ServicoTerceirosBean servicoTerceirosBean, byte[] bytes, String nomeArquivo)  {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		//EntityManager manager = null;
		try {
			prop.load(in);
			String diretorioMinuta = prop.getProperty("dir.nota.fiscal") + servicoTerceirosBean.getId();
			deleteDir( new File(diretorioMinuta));
			
			
			if(!diretorioExiste(diretorioMinuta)) {
				criarDiretorioNF(servicoTerceirosBean.getId().intValue());
			}
			
			
			String caminhoCompletoArquivo = diretorioMinuta+ "/" + UtilGestaoEquipamentosPesa.replaceCaracterEspecial(nomeArquivo);
			
			File arquivo = new File(caminhoCompletoArquivo);
			FileOutputStream fos = new FileOutputStream(arquivo);
			
			fos.write(bytes);
			fos.flush();
			fos.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean fazerUploadEmDiretorioDetalhes(String dirDetalhes, byte[] bytes, String nomeArquivo)  {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		//EntityManager manager = null;
		try {
			prop.load(in);
			String diretorioMinuta = prop.getProperty("dir.detalhes.servterc") + dirDetalhes;
			deleteDir( new File(diretorioMinuta));
			
			
			if(!diretorioExiste(diretorioMinuta)) {
				criarDiretorioDetalhes(dirDetalhes);
			}
			
			String caminhoCompletoArquivo = diretorioMinuta+ "/" + UtilGestaoEquipamentosPesa.replaceCaracterEspecial(nomeArquivo);
			
			File arquivo = new File(caminhoCompletoArquivo);
			FileOutputStream fos = new FileOutputStream(arquivo);
			
			fos.write(bytes);
			fos.flush();
			fos.close();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
    public static boolean deleteDir(File dir) {  
        if (dir.isDirectory()) {  
            String[] children = dir.list();  
            for (int i=0; i<children.length; i++) {   
               boolean success = deleteDir(new File(dir, children[i]));  
                if (!success) {  
                    return false;  
                }  
            }  
        }  
      
        // Agora o diretório está vazio, restando apenas deletá-lo.  
        return dir.delete();  
    }  
	private Boolean diretorioExiste(String dir) {
		if ((new File(dir)).exists()) {
			return true;
		}
		return false;
	}
	
	private boolean criarDiretorioNF(Integer id) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		
		try {
			prop.load(in);
			String notaFiscalDir = prop.getProperty("dir.nota.fiscal");
			File dir = new File(notaFiscalDir + id);
			Boolean criou = dir.mkdirs(); 
			
			return criou;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean criarDiretorioDetalhes(String id) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		
		try {
			prop.load(in);
			String notaFiscalDir = prop.getProperty("dir.detalhes.servterc");
			File dir = new File(notaFiscalDir + id);
			Boolean criou = dir.mkdirs(); 
			
			return criou;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String recuperarNomeArquivo(Long id) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();		

		try {
			prop.load(in);
			String dirNF = prop.getProperty("dir.nota.fiscal");		
			File dir = new File(dirNF + id);

			if (dir.isDirectory()) {
				String[] children = dir.list();

				for (int i = 0; i < children.length; i++) { 
					return children[i];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
	public String recuperarNomeArquivoDetalhes(String nomeArquivo) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();		
		
		try {
			prop.load(in);
			String dirDetalhes = prop.getProperty("dir.detalhes.servterc");		
			File dir = new File(dirDetalhes + nomeArquivo);
			
			if (dir.isDirectory()) {
				String[] children = dir.list();
				
				for (int i = 0; i < children.length; i++) { 
					return children[i];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String findCaminhoArquivo(Long id){
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		try {
			prop.load(in);
			String caminho = prop.getProperty("dir.nota.fiscal");
			return caminho+=id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public String findCaminhoArquivoDetalhes(String nomeArquivo){
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		try {
			prop.load(in);
			String caminho = prop.getProperty("dir.detalhes.servterc");
			return caminho+=nomeArquivo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Aprovação do serviço de terceiros pela metrologia
	 * @param bean
	 * @param obs
	 * @return
	 */
	public Boolean aprovarServTercRecepcao(ServicoTerceirosBean servicoTerceirosBean, Long idSegmento, String obs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento " +
					" and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:idTipoServicoTerceiros");
			query.setParameter("idSegmento", idSegmento);
			query.setParameter("idTipoServicoTerceiros", servicoTerceirosBean.getIdTipoServicoTerceiros());
			if(query.getResultList().size() > 0){
				manager.getTransaction().begin();
				GeSegmentoServTerceiros terceiros = (GeSegmentoServTerceiros)query.getSingleResult();
				//serviço enviado da metrologia para recepção
				GeSegmento geSegmento = (GeSegmento)manager.find(GeSegmento.class, idSegmento);
				String sigla = "APREC";

				query = manager.createQuery("from GeStatusServTerceiros where sigla = '"+sigla+"'");
				GeStatusServTerceiros servTerceiros = (GeStatusServTerceiros)query.getSingleResult();
				terceiros.setIdStatusServTerceiros(servTerceiros);

				GeSituacaoServTerceiros situacaoTerceiros = getSituacaoServTerceiros(servicoTerceirosBean.getIdTipoServicoTerceiros(), idSegmento, manager);
				situacaoTerceiros.setDataEnvioRecepcao(new Date());
				situacaoTerceiros.setIdFuncEnvioRecepcao(this.usuarioBean.getMatricula());
				situacaoTerceiros.setObsEnvioRecepcao(obs);


				situacaoTerceiros.setDataRejeicaoMetrologia(null);
				situacaoTerceiros.setIdFuncRejeicaoMetrologia(null);
				situacaoTerceiros.setObsRejeicaoMetrologia(null);
				manager.merge(situacaoTerceiros);

				manager.getTransaction().commit();
				//this.sendMailRecepcao(terceiros, geSegmento, manager, obs);
				this.sendMailRecepcao(terceiros, geSegmento, manager, obs, situacaoTerceiros.getFilial());
				this.sendMailNotaFiscalFornecedor(terceiros, geSegmento, manager, "");
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public List<ItemBean> recuperarItensServTerc(ServicoTerceirosBean servicoTerceirosBean){
		EntityManager manager = null;
		List<ItemBean> itemBeans = new ArrayList<ItemBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSituacaoItem where idSituacaoServTerc.id =:idSituacaoServTerc ");
			query.setParameter("idSituacaoServTerc", servicoTerceirosBean.getId());
			List<GeSituacaoItem> items = query.getResultList();
			for (GeSituacaoItem geSituacaoItem : items) {
				ItemBean bean = new ItemBean();
				bean.setCodigo(geSituacaoItem.getIdItem().getCodigo());
				bean.setDescricao(geSituacaoItem.getIdItem().getDescricao());
				bean.setId(geSituacaoItem.getIdItem().getId());
				bean.setQtd(geSituacaoItem.getQtd());
				bean.setValor(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", geSituacaoItem.getIdItem().getValor().longValue())));
				itemBeans.add(bean);
			}
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return itemBeans;
	}
	
	public Boolean removerItem(Long id){
		List<ItemBean> itemList = new ArrayList<ItemBean>();
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeItem geItem = manager.find(GeItem.class, id);
			manager.remove(geItem);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {	
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
		
	}
	public ItemBean saveOrUpdate(ItemBean bean){
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeItem geItem = new GeItem();
			if(bean.getId() != null && bean.getId() > 0){
				geItem = manager.find(GeItem.class, bean.getId());
				geItem.setDescricao(bean.getDescricao().toUpperCase());
				geItem.setCodigo(bean.getCodigo().toUpperCase());
				geItem.setValor((BigDecimal.valueOf(Double.valueOf(bean.getValor().replace(".", "").replace(",", ".")))));
				manager.merge(geItem);
			}else{
				geItem.setDescricao(bean.getDescricao().toUpperCase());
				geItem.setCodigo(bean.getCodigo().toUpperCase());
				geItem.setValor((BigDecimal.valueOf(Double.valueOf(bean.getValor().replace(".", "").replace(",", ".")))));
				manager.persist(geItem);
			}
			bean.setId(geItem.getId());
			manager.getTransaction().commit();
			return bean;
		} catch (Exception e) {	
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
		
	}
	
	public Boolean removerArquivoDetalhes(String dirDetalhes){
		
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		//EntityManager manager = null;
		try {
			prop.load(in);
			String diretorioMinuta = prop.getProperty("dir.detalhes.servterc") + dirDetalhes;
			deleteDir( new File(diretorioMinuta));
			
		return true;

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Boolean fazerUploadEmDiretorioServicoTerceiros(byte[] bytes, String dir, String nomeArquivo) throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();

		try {
			prop.load(in);
			String diretorioServTerceiros = prop.getProperty("dir.anexos.servTerceiros") + dir;

			if(!diretorioExiste(diretorioServTerceiros)) {
				criarDiretorioServicoTerceiros(dir);
			}
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DATE, 1);
//			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyy");
			//String dataFormatada = format1.format(cal.getTime());
			Date date = new Date();
			//Long time = date.getTime();	
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
			String caminhoCompletoArquivo = prop.getProperty("dir.anexos.servTerceiros") + dir + "/" + UtilGestaoEquipamentosPesa.replaceCaracterEspecial(dateFormat.format(date)+"-"+nomeArquivo);

			File arquivo = new File(caminhoCompletoArquivo);
			FileOutputStream fos = new FileOutputStream(arquivo);

			fos.write(bytes);
			fos.flush();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean criarDiretorioServicoTerceiros(String id) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		
		try {
			prop.load(in);
			String notaFiscalDir = prop.getProperty("dir.anexos.servTerceiros");
			File dir = new File(notaFiscalDir + id);
			Boolean criou = dir.mkdirs(); 
			
			return criou;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Collection<String> findAllArquivos(String id) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();		

		Collection<String> listForm = new ArrayList<String>();

		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.anexos.servTerceiros");		
			File dir = new File(dirFotos + id+"/");

			if (dir.isDirectory()) {
				String[] children = dir.list();

				for (int i = 0; i < children.length; i++) { 
					listForm.add(children[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listForm;
	}
	
	public Boolean savePrevisaoDataTerminoServico(ServicoTerceirosBean bean, String data){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:ID_SEGMENTO and geSegmentoServTerceirosPK.idTipoServicoTerceiros =:ID_TIPO_SERVICO_TERCEIROS");
			query.setParameter("ID_SEGMENTO", bean.getIdSegmento());
			query.setParameter("ID_TIPO_SERVICO_TERCEIROS", bean.getIdTipoServicoTerceiros());
			GeSegmentoServTerceiros servTerceiros = (GeSegmentoServTerceiros)query.getSingleResult();
			servTerceiros.setDataPrevisaoEntrega(dateFormat.parse(data));
			manager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	
}

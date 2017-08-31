package com.gestaoequipamentos.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.ServicoTerceirosBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeOsPalm;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSegmentoServTerceiros;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.entity.GeTipoServicoTerceiros;
import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.SendEmailHelper;

public class ConsultorCheckinBusiness {
	
	private UsuarioBean bean;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat dateSerTerc = new SimpleDateFormat("dd/MM/yy");
	
	public ConsultorCheckinBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	/**
	 * Busca as OS pelo campo de pesquisa ou jobControl do funcionário. 
	 * @param campoPesquisa
	 * @param jobControl
	 * @return
	 */
//	public List<ChekinBean> findAllChekinConsultor(){
//		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
//		EntityManager manager = null;
//		
//		try {
//			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
//
//			String sqlCheckIn = "From " +
//					" GeCheckIn ch , GeOsPalm os	" +
//					"  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  " +
//					"  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
//					"  and os.idosPalm = ch.idOsPalm " +
//					"  and os.filial =:filial";
//					//"  and ch.jobControl =:jobControl AND (" ;
//			
////			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
////			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
////			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
////			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
//			sqlCheckIn += " ORDER BY ch.id ";
//						
//			sqlCheckIn += " order by os.emissao desc";;
//			Query query = manager.createQuery(sqlCheckIn );
//			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
//			//query.setParameter("jobControl", jobControl);
//			List<Object[]> list = (List<Object[]>) query.getResultList();
//			for (Object[] pair : list) {
//				ChekinBean bean = new ChekinBean();
//				GeCheckIn checkIn = (GeCheckIn)pair[0];
//				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
//				query.setParameter("idCheckin", checkIn.getId());
//				List<GeSituacaoOs> situacaoOsList = query.getResultList();
//				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
//				if(situacaoOsList.size() > 0){
//					geSituacaoOs = situacaoOsList.get(0);
//				}
//				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
//				bean.fromBean(checkIn, geSituacaoOs,geOsPalm);
//				bean.setIsRemoveSegmento("N");
//				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
//						bean.setIsRemoveSegmento("S");
//				}
//				resulList.add(bean);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		return resulList;
//	}
	
	public List<ChekinBean> findAllChekinConsultor(String campoPesquisa){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
					" GeCheckIn ch , GeOsPalm os, GeSituacaoOs st, GeConsultorProposta cp";					
					
					sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
					sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
					sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
					sqlCheckIn +=" and ch.isLiberadoPConsultor = 'S'";
					sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'P' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and st.idCheckin.id = ch.id";
					sqlCheckIn +=" and st.dataFaturamento is null";
					sqlCheckIn +=" and ch.tipoCliente = 'EXT'";
					sqlCheckIn +=" and cp.idCheckIn.id = ch.id";
					sqlCheckIn +=" and cp.idEpidno.epidno = '"+bean.getMatricula()+"'";

			//sqlCheckIn += " and os.filial =:filial AND (" ;
			sqlCheckIn += " AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			//query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List<ChekinBean> findAllChekinGestorAprovacao(String campoPesquisa){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;

		
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			
			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
			" GeCheckIn ch , GeOsPalm os, GeSituacaoOs st, GeConsultorProposta cp";					
			
			sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
			sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
			sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
			sqlCheckIn +=" and ch.isLiberadoPConsultor = 'S'";
			sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
			sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'P' or ch.statusNegociacaoConsultor is null)";
			sqlCheckIn +=" and st.idCheckin.id = ch.id";
			sqlCheckIn +=" and st.dataFaturamento is null";
			sqlCheckIn +=" and ch.tipoCliente = 'INT'";
			sqlCheckIn +=" and cp.idCheckIn.id = ch.id";
			sqlCheckIn +=" and cp.idEpidno.epidno = '"+bean.getMatricula()+"'";
			
			//sqlCheckIn += " and os.filial =:filial AND (" ;
			sqlCheckIn += " AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";;
			Query query = manager.createQuery(sqlCheckIn );
			//query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = new ChekinBusiness(this.bean).findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServico = new ChekinBusiness(this.bean).findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	/**
	 * Método que libera a os do supervisor para o digitador.
	 * @param bean
	 * @return true se a operação foi realizada normalmente.
	 */
//	public Boolean liberarOSParaDigitador(ChekinBean bean) {
//		EntityManager manager = null;
//		Connection con = null;
//		Statement prstmt = null;
//		ResultSet rs = null;
//		try {
//			manager = JpaUtil.getInstance();
//			manager.getTransaction().begin();
//			
//			Query query = manager.createQuery("FROM GeSituacaoOs WHERE idCheckin.id =:idCheckin");
//			query.setParameter("idCheckin", bean.getId());
//						
//			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();			
//			situacaoOs.setDataEntregaPedidos(new Date());			
//			manager.merge(situacaoOs);
//			
//			GeCheckIn geCheckIn = manager.find(GeCheckIn.class, bean.getId());
//			geCheckIn.setIsLiberadoPDigitador("S");
//			manager.merge(geCheckIn);
//			
//			//Grava data de entrega dos pedidos no DBS.
//			con = ConectionDbs.getConnecton();
//			prstmt = con.createStatement();
//			
//			String SQL = "select TRIM(W.NTDA) from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+geCheckIn.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '002' AND TRIM(W.WOSGNO) = ''";
//			rs = prstmt.executeQuery(SQL);
//			if(rs.next()){
//				String NTDA = rs.getString("NTDA");
//				String dataAux = NTDA.replace("../../....", dateFormat.format(new Date()));
//				SQL = "update LIBT99.WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+geCheckIn.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '002' AND TRIM(W.WOSGNO) = ''";
//				prstmt.executeUpdate(SQL);
//			}
//	
//			manager.getTransaction().commit();	
//			this.sendEmailDigitador(manager, bean);
//			
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			} 
//			e.printStackTrace();
//			return false;
//		}finally{
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.close();
//			}if(con != null){
//				try {
//					prstmt.close();
//					con.close();
//				} catch (SQLException e) {					
//					e.printStackTrace();
//				}
//			}
//		}	
//		return true;
//		
//	}	
	/**
	 * Método que envia e-mail para todos os funcionários envolvidos no processo da OS rejeitada.
	 * @param statusNegociacaoOS
	 * @param dataAprovacao
	 * @param obs
	 * @param bean
	 * @return
	 */	
	public Boolean consultorLiberarOS(String statusNegociacaoOS, String dataAprovacao, String obs, ChekinBean bean) {
		EntityManager manager = null;
		Connection conn = null;
		Statement prstmt = null;
		String jbCtrl = "";
		if(bean.getJobControl() != null){
			jbCtrl = "'"+bean.getJobControl()+"'";
		}
//		java.sql.PreparedStatement prstmt_ = null;
//		ResultSet rs = null;
		SegmentoBusiness businessSegmento = new SegmentoBusiness(this.bean);
		try {
			conn = ConectionDbs.getConnecton();
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			Query query = null;
			if(statusNegociacaoOS.equals("P")){
				GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getId());
				checkIn.setStatusNegociacaoConsultor("P");
				checkIn.setObsNegociacaoConsultor(obs);
				manager.merge(checkIn);
				
				this.sendEmailOS(manager, bean, obs, "foi REJEITADA PARCIALMENTE pelo motivo:", checkIn, "P");
				
				//this.sendEmailADM(manager, bean, obs, "foi REJEITADA PARCIALMENTE pelo motivo:", checkIn, "P");
				query = manager.createQuery("From GeSegmento where  idCheckin.id =:idCheckin and data_termino_servico is null");
				query.setParameter("idCheckin", bean.getId());
				List<GeSegmento> geSegmentos = (List<GeSegmento>)query.getResultList();
				for (GeSegmento geSegmento : geSegmentos) {
					geSegmento.setDataTerminoServico(new Date());
					businessSegmento.alterarFluxoProcessoOficina(geSegmento.getIdCheckin().getId(), "RP", null);
				}
				SendEmailHelper.sendEmailSupervisorJobControlAssociados(manager, jbCtrl, Integer.valueOf(checkIn.getIdOsPalm().getFilial()).toString(), bean.getNumeroOs());
				
			}else if(statusNegociacaoOS.equals("R")){
				GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getId());
				checkIn.setStatusNegociacaoConsultor("R");
				checkIn.setHasSendDataPrevisao("S");
				checkIn.setObsNegociacaoConsultor(obs);
				manager.merge(checkIn);
				
				
				query = manager.createQuery("FROM GeSituacaoOs WHERE idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", bean.getId());				
				GeSituacaoOs situacaoOs = (GeSituacaoOs) query.getSingleResult();
				
				situacaoOs.setDataAprovacao(new Date());
				situacaoOs.setIdFuncDataAprovacao(this.bean.getMatricula());
				situacaoOs.setDataPrevisaoEntrega(new Date());
				situacaoOs.setIdFuncDataPrevisaoEntrega(this.bean.getMatricula());
				situacaoOs.setDataTerminoServico(new Date());
				situacaoOs.setIdFuncDataTerminoServico(this.bean.getMatricula());
				situacaoOs.setDataFaturamento(new Date());
				situacaoOs.setIdFuncDataFaturamento(this.bean.getMatricula());
				situacaoOs.setIsCheckOut("S");
				
				query = manager.createQuery("From GeSegmento where  idCheckin.id =:idCheckin and data_termino_servico is null");
				query.setParameter("idCheckin", bean.getId());
				
				List<GeSegmento> geSegmentos = (List<GeSegmento>)query.getResultList();
				for (GeSegmento geSegmento : geSegmentos) {
					geSegmento.setDataTerminoServico(new Date());
					businessSegmento.alterarFluxoProcessoOficina(geSegmento.getIdCheckin().getId(), "R", null);
				}
				
				dataAprovacao = dateFormat.format(new Date());
				
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dataAprovacao, conn, bean.getNumeroOs(), "004");
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dataAprovacao, conn, bean.getNumeroOs(), "005");
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dataAprovacao, conn, bean.getNumeroOs(), "006");
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dataAprovacao, conn, bean.getNumeroOs(), "007");
				
				manager.merge(situacaoOs);
								
				this.sendEmailOS(manager, bean, obs,  "foi REJEITADA pelo motivo:", checkIn, "R");
				
				//this.sendEmailADM(manager, bean, obs,  "foi REJEITADA pelo motivo:", checkIn, "R");
				
				SendEmailHelper.sendEmailSupervisorJobControlAssociados(manager, jbCtrl, Integer.valueOf(checkIn.getIdOsPalm().getFilial()).toString(), bean.getNumeroOs());
				
			}else if(statusNegociacaoOS.equals("A")){
				GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getId());
				checkIn.setStatusNegociacaoConsultor("A");
				checkIn.setObsNegociacaoConsultor(obs);
				manager.merge(checkIn);
				
				
				query = manager.createQuery("FROM GeSituacaoOs WHERE idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", bean.getId());				
				GeSituacaoOs situacaoOs = (GeSituacaoOs) query.getSingleResult();
				
				situacaoOs.setDataAprovacao(new Date());
				situacaoOs.setIdFuncDataAprovacao(this.bean.getMatricula());
				
				if(situacaoOs.getIdCheckin().getTipoCliente().equals("EXT")){
					situacaoOs.setMoMiscDesl(BigDecimal.valueOf(Double.valueOf(bean.getMoMiscDesl().replace(".", "").replace(",", "."))));
					situacaoOs.setValorPecas(BigDecimal.valueOf(Double.valueOf(bean.getValorPecas().replace(".", "").replace(",", "."))));
					situacaoOs.setValorTotal(BigDecimal.valueOf(Double.valueOf(bean.getValorTotal().replace(".", "").replace(",", "."))));
					situacaoOs.setCondicaoPagamento(bean.getCondicaoPagamentoPecas()+" "+bean.getCondicaoPagamentoServicos());
					situacaoOs.setPropostaNumero(bean.getPropNumero());
					situacaoOs.setAutorizadoPor(bean.getAutPor());
					situacaoOs.setOrdemDeCompra(bean.getOrdemCompraPeca()+" "+bean.getOrdemCompraServico());
					situacaoOs.setNotesPreenchidoPor(bean.getPreenchidoPor());
					if((bean.getObsPeca()+" "+bean.getObsServico()).length() > 99){
						situacaoOs.setObsNf((bean.getObsPeca()+" "+bean.getObsServico()).substring(0,99));
					}
					situacaoOs.setOrdemCompraPeca(bean.getOrdemCompraPeca());
					situacaoOs.setOrdemCompraServico(bean.getOrdemCompraServico());
					situacaoOs.setEstabelecimentoCredorPecas(Long.valueOf(bean.getEstabelecimentoCredorPecas()));
					situacaoOs.setEstabelecimentoCredorServicos(Long.valueOf(bean.getEstabelecimentoCredorServicos()));
					situacaoOs.setCondicaoPagamentoPecas(bean.getCondicaoPagamentoPecas());
					situacaoOs.setCondicaoPagamentoServicos(bean.getCondicaoPagamentoServicos());
					situacaoOs.setDescontoPorcPecas(BigDecimal.valueOf(Double.valueOf(bean.getDescPorcPecas().replace(".", "").replace(",", "."))));
					situacaoOs.setDescontoPorcServicos(BigDecimal.valueOf(Double.valueOf(bean.getDescPorcServicos().replace(".", "").replace(",", "."))));
					situacaoOs.setDescontoValorPecas(BigDecimal.valueOf(Double.valueOf(bean.getDescValorPecas().replace(".", "").replace(",", "."))));
					situacaoOs.setDescontoValorServicos(BigDecimal.valueOf(Double.valueOf(bean.getDescValorServicos().replace(".", "").replace(",", "."))));
					situacaoOs.setAutomaticaFaturada(bean.getAutomaticaFaturada());
					situacaoOs.setObsPeca(bean.getObsPeca());
					situacaoOs.setObsServico(bean.getObsServico());
					situacaoOs.setCondicaoEspececial(bean.getCondEspecial());
					situacaoOs.setEncerrarAutomatica(bean.getEncerrarAutomatica());
					situacaoOs.setObservacaoDesconto(bean.getObservacaoDesconto());
				}
				
				manager.merge(situacaoOs);
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dataAprovacao, conn, bean.getNumeroOs(), "004"); 							
				
				//Verifica se a OS está OPEN
//				String SQL = "select TRIM(w.ACTI) ACTI from "+IConstantAccess.LIB_DBS+".WOPHDRS0 w  where TRIM(w.WONO) = '"+bean.getNumeroOs()+"'"; 
//				prstmt_ = conn.prepareStatement(SQL);
//				rs = prstmt_.executeQuery();
//				if(rs.next()){
//					String ACTI = rs.getString("ACTI");
//					
//					if(ACTI.equals("E")){
//						if(!new ChekinBusiness(null).openOs(bean.getId(), bean.getNumeroOs())){
//							//return "Não foi possível abrir OS!-true";
//						}
//					}
//				}
				
				
				this.sendEmailOS(manager, bean, obs, "foi APROVADA. Obs:", checkIn, "A");
				
				SendEmailHelper.sendEmailSupervisorJobControlAssociados(manager, jbCtrl, Integer.valueOf(checkIn.getIdOsPalm().getFilial()).toString(), bean.getNumeroOs());
				
		
				String email= "Gostariamos de informar que a OS numero "+checkIn.getNumeroOs()+"foi aprovada e tem o seguintes serviços de terceiros:\n";
				//Busca dados do segmento e serviço de terceiros para inserir no DBS.				
				query = manager.createQuery("From GeSegmento seg, GeSegmentoServTerceiros ter"+
						" WHERE seg.id = ter.geSegmentoServTerceirosPK.idSegmento"+
				" AND seg.idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", bean.getId());

				List<Object[]> list = (List<Object[]>) query.getResultList();

				//Envia data de aprovação para o DBS.
				
				prstmt = conn.createStatement();				
				
				SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(this.bean);
				int seq = 1;
				BigDecimal valorServicoTerceiros = BigDecimal.ZERO;
				for (Object[] obj : list) {
					
					GeSegmentoServTerceiros geSegmentoServTerceiros = (GeSegmentoServTerceiros)obj[1];
					if(geSegmentoServTerceiros.getIsCriadoDbs() != null && geSegmentoServTerceiros.getIsCriadoDbs().equals("S")){
						continue;
					}
					query = manager.createNativeQuery("select DESCRICAO from GE_TIPO_SERVICO_TERCEIROS where ID = "+geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
					String descricaoServTerc = (String)query.getSingleResult();
		
					//String descricaoServTerc = geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros();
					if(descricaoServTerc != null && descricaoServTerc.length() > 0){
						if(descricaoServTerc.length() > 16){
							descricaoServTerc = descricaoServTerc.substring(0,16);
						}
					}else{
						descricaoServTerc = "";
					}
					GeSegmento geSegmento = (GeSegmento) obj[0];					
					String valorSerTerc = "";
					String valorServTercNota = valorServTercFormatada(geSegmentoServTerceiros.getValor());
					if(bean.getTipoCliente().equals("INT")){
						valorSerTerc = valorServTercFormatada(geSegmentoServTerceiros.getValor());
					}else{
						BigDecimal valor = geSegmentoServTerceiros.getValor().multiply(BigDecimal.valueOf(1.5));
						valorServicoTerceiros = valorServicoTerceiros.add(valor);
						valorSerTerc = valorServTercFormatada(valor.setScale(2, RoundingMode.HALF_UP));
					}
					try {
						//if(geSegmentoServTerceiros.getIsCriadoDbs() == null){
							String pair = "'"+bean.getNumeroOs()+"','"+geSegmento.getNumeroSegmento()+"','"+seq+"','"+"MOT"+"','"+dateSerTerc.format(new Date()).replace("/", "")+"','"+qtdServTercFormatada(geSegmentoServTerceiros.getQtd())+"','"+valorServTercNota+"','"+valorSerTerc+"','"+descricaoServTerc+"'";
							//String pair = "'"+bean.getNumeroOs()+"','"+geSegmento.getNumeroSegmento()+"','"+seq+"','"+"MOT"+"','"+dateSerTerc.format(new Date()).replace("/", "")+"','"+qtdServTercFormatada(geSegmentoServTerceiros.getQtd())+"','"+valorServTercNota+"','"+valorSerTerc+"','"+descricaoServTerc+"'";
							String SQL = "INSERT INTO "+IConstantAccess.AMBIENTE_DBS+".USPMSSM0 (WONO, WOSGNO, SEQ, CHGCD, DOCDAT, QTY, UNCS, UNSL, DESC) VALUES ("+pair+")";
							prstmt.executeUpdate(SQL);
							geSegmentoServTerceiros.setIsCriadoDbs("S");
						//}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					seq++;
					email=email+descricaoServTerc+"\n";
					if(geSegmentoServTerceiros.getIdFornServTerceiros() != null){
						ServicoTerceirosBean servicoTerceirosBean = new ServicoTerceirosBean();
						servicoTerceirosBean.setIdSegmento(geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdSegmento());
						servicoTerceirosBean.setIdTipoServicoTerceiros(geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
						servicoTerceirosBean.setLocalServico(geSegmentoServTerceiros.getLocalServico());
						GeTipoServicoTerceiros tipoServicoTerceiros = manager.find(GeTipoServicoTerceiros.class, geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());

						servicoTerceirosBean.setDescricao(tipoServicoTerceiros.getDescricao());
						if("OFI".equals(geSegmentoServTerceiros.getIdStatusServTerceiros().getSigla())){
							business.saveOrUpdateSituacaoServTerc(servicoTerceirosBean, "Serviço de terceiros aprovado pelo cliente!");
						}
						business.sendMailAprovacaoFornecedor(geSegmentoServTerceiros, geSegmento, manager, "");
					}
				}
				String SQL = "";
				try {
					if(bean.getTipoCliente().equals("EXT") && valorServicoTerceiros.doubleValue() > 0){
							
							query = manager.createQuery("from GeSegmento where idCheckin.id = "+checkIn.getId()+" and numeroSegmento = '46'");
							
							
							if(query.getResultList().size() > 0){
								String watpcd = "    ";
								String totalMaoObra = valorServTercFormatada(valorServicoTerceiros.setScale(2, RoundingMode.HALF_UP));
								GeSegmento segmentoBean = (GeSegmento)query.getSingleResult();
								String pair = "'"+checkIn.getId()+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJobControl()+"','"+segmentoBean.getJobCode()+"','"+segmentoBean.getComCode()+"','E','00000','F','"+totalMaoObra+"', '00001', '"+watpcd+"','       '";
								SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, lbrate, lbamt, qty, watpcd, pipno) values("+pair+")";
								
								prstmt.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where WONOSM = '"+checkIn.getId()+"-O' and wono = '"+checkIn.getNumeroOs()+"' and wosgno = '"+segmentoBean.getNumeroSegmento()+"'");
								
								prstmt.executeUpdate(SQL);
								

								//SQL = "update "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0	set LBAMT = '"+totalMaoObra+"', lbrate = 'F' where wono = '"+bean.getNumeroOs()+"' and wosgno = '46'";
								//prstmt.executeUpdate(SQL);
							}
					}
				} catch (Exception e) {
					e.printStackTrace();
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					new EmailHelper().sendSimpleMail(errors.toString()+"\n "+SQL, "ERRO AO FIRMAR VALOR DE SERVIÇO DE TERCEIROS  Sistema Oficina", "rodrigo@rdrsistemas.com.br");
				}
				
				List<SegmentoBean> segList = businessSegmento.findAllSegmento(bean.getId());
				for (SegmentoBean segmentoBean : segList) {
					businessSegmento.alterarFluxoProcessoOficina(segmentoBean.getIdCheckin(), "M", null);
				}
				
				this.sendEmailOSSuprimentos(manager, bean, email, checkIn);
			}
			manager.getTransaction().commit();	
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			} 
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(conn != null){
				try {
					conn.close();
					if(prstmt != null){
						prstmt.close();
					}
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}
		return true;		
	}
	
	public static void main(String[] args) {
		BigDecimal vt = BigDecimal.valueOf(33.95);
		System.out.println(vt.toString().replace(".", ""));
	}
	

	public String valorServTercFormatada(BigDecimal valor) {
		String value = valor.toString().replace(".", "");
		for(int i = value.length(); i < 13; i++){
			value = "0"+value;
		}		
		return value;
	}
	public String qtdServTercFormatada(BigDecimal qtd) {
		String qty = qtd.toString();
		for(int i = qtd.toString().length(); i < 5; i++){
			qty = "0"+qty;
		}		
		return qty+"00";
	}
	/**
	 * Envia e-mail para o supervisor. 
	 * @param manager
	 * @param bean
	 */
//	private void sendEmailOSAprovada(EntityManager manager, ChekinBean bean) {
//		try {
//			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
//					 " WHERE tw.epidno = perfil.id_tw_usuario"+
//					 " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'SUPER' and tipo_sistema = 'GE')"+
//					 " AND tw.STN1 =:filial"+
//					 " AND perfil.JOB_CONTROL =:jobControl");
//			
//			query.setParameter("filial", Integer.valueOf(this.bean.getFilial()).toString());
//			query.setParameter("jobControl", bean.getJobControl());
//			
//			if(query.getResultList().size() > 0){
//				List<Object []> list = query.getResultList();
//				for(Object [] pair : list){
//					EmailHelper helper = new EmailHelper();
//					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" foi aprovada" ,"Nova Ordem de Serviço", (String)pair[0]);		
//				}				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	/**
	 * Envia e-mail para o administrador do sistema relatando a situação da OS.
	 */
//	private void sendEmailADM(EntityManager manager, ChekinBean bean, String obs, String statusOSEmail, GeCheckIn checkin, String status){
//		try {
//			//Envia e-mail para todos os envolvidos no processo de OS.
//			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
//					  " WHERE tw.epidno = perfil.id_tw_usuario"+
//					  " AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
//					  " WHERE SIGLA in ('ADM')"+
//					  " AND TIPO_SISTEMA = 'GE'"+
//					  " AND tw.STN1 =:filial)"); 
//			
//			query.setParameter("filial", Integer.valueOf(checkin.getIdOsPalm().getFilial()).toString());		
//			
//				if(query.getResultList().size() > 0){
//					List<Object []> list = query.getResultList();
//					for(Object [] pair : list){
//						EmailHelper helper = new EmailHelper();
//						helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" "+statusOSEmail+" "+obs,"Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);
//					}
//				}
//			
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * Envia e-mail para todos informado que a OS foi rejeitada parcial, totalmente ou aprovada.
	 * @param manager
	 */	
	private void sendEmailOS(EntityManager manager, ChekinBean bean, String obs, String statusOSEmail, GeCheckIn checkin, String status){
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					  //" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					  //" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					  //" WHERE SIGLA in ('ORC', 'DIG','RECP')"+
					  " WHERE SIGLA in ('ORC','RECP','ADM', 'GERENT', 'GEGAR')"+
					  " AND TIPO_SISTEMA = 'GE'"+
					  " AND tw.STN1 =:filial)"); 
			
			query.setParameter("filial", Integer.valueOf(checkin.getIdOsPalm().getFilial()).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					String siglaPerfil = (String)pair[2];
//					if("A".equals(status)){
//						if(!"DIG".equals(siglaPerfil)){
							//helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" foi APROVADA: "+obs,"Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);
//						}else{
//							continue;
//						}
					//}else if(!"DIG".equals(siglaPerfil)){
					if(!"RECP".equals(siglaPerfil)){
						helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" "+statusOSEmail+" "+obs," Sistema Oficina Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);
					}else if("RECP".equals(siglaPerfil) && "R".equals(status)) {
						helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" "+statusOSEmail+" "+obs," Sistema Oficina Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);
					}
					//}
				}				
			}
			//Envia e-mail para todos o supervisor do JobControl da OS.
			query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					 " WHERE tw.epidno = perfil.id_tw_usuario"+
					 " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'SUPER' and tipo_sistema = 'GE')"+
					 " AND tw.STN1 =:filial"+
					 " AND perfil.JOB_CONTROL =:jobControl");
			
			query.setParameter("filial", Integer.valueOf(checkin.getIdOsPalm().getFilial()).toString());
			query.setParameter("jobControl", bean.getJobControl());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+bean.getNumeroOs()+" "+statusOSEmail+" "+obs,"Nova Ordem de Serviço  Sistema Oficina", (String)pair[0]);		
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void sendEmailOSSuprimentos(EntityManager manager, ChekinBean bean, String email, GeCheckIn checkin){
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					" WHERE tw.epidno = perfil.id_tw_usuario"+
					" AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					//" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					//" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					//" WHERE SIGLA in ('ORC', 'DIG','RECP')"+
					" WHERE SIGLA = 'SUPRI'"+
					" AND TIPO_SISTEMA = 'GE'"+
					" AND tw.STN1 =:filial)"); 
			
			query.setParameter("filial", Integer.valueOf(checkin.getIdOsPalm().getFilial()).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+email," Sistema Oficina Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);
					//}
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

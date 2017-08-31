package com.gestaoequipamentos.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.GenericServlet;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.GeDocSegOperBean;
import com.gestaoequipamentos.bean.LegendaProcessoOficinaBean;
import com.gestaoequipamentos.bean.OperacaoBean;
import com.gestaoequipamentos.bean.PecasBoTreeBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeDataFluxoSegmento;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeFator;
import com.gestaoequipamentos.entity.GeLegendaProcessoOficina;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSegmentoAssociado;
import com.gestaoequipamentos.entity.GeSegmentoLog;
import com.gestaoequipamentos.entity.GeSegmentoServTerceiros;
import com.gestaoequipamentos.entity.GeSegmentoServTerceirosPK;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

public class SegmentoBusiness {
	private UsuarioBean usuarioBean;
	public SegmentoBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	public void removerSegmento(Long idChechIn){ // REMOVER PELO ID 
		//abrir transação
		//delete from ge_segmento where id_checkin = 1
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_segmento where id_checkin = "+idChechIn);
			query.executeUpdate();
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
	}

	public Boolean inserirSegmento(List<SegmentoBean> segmentoList, Long idChechIn){
		//CHAMAR O REMOVER PEÇAS
		this.removerSegmento(idChechIn);
		EntityManager manager = null;
		//abrir transação
		try {

			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idChechIn);
			for (SegmentoBean segmentoBean : segmentoList) {
				GeSegmento geSegmento = new GeSegmento();
				geSegmento.setIdCheckin(checkIn);
				segmentoBean.toBean(geSegmento, manager);
				
				manager.persist(geSegmento);
				List<OperacaoBean> operacaoList = segmentoBean.getOperacaoList();
				if(operacaoList != null){
					for (int i = 0; i < operacaoList.size(); i++){
						OperacaoBean operacaoBean = operacaoList.get(i);
						GeOperacao geOperacao = new GeOperacao();
						String[] cptcd = operacaoBean.getCptcd().split(" - ");
						geOperacao.setCptcd(cptcd[0]);
						geOperacao.setNumOperacao(operacaoBean.getNumero());
						geOperacao.setDescricaoCompCode(cptcd[1]);
						//geOperacao.setCptcd(operacaoBean.getCptcd());
						geOperacao.setJbcd(operacaoBean.getJbcd());
						geOperacao.setDescricaoJbcd(operacaoBean.getDesricao());
						geOperacao.setIdSegmento(geSegmento);
						geOperacao.setIsCreateOperacao("N");
						geOperacao.setCodErroDbs("100");
						geOperacao.setMsgDbs("Operação enviada, aguarde o retorno do DBS!");
						TwFuncionario funcionario = manager.find(TwFuncionario.class, operacaoBean.getIdFuncionarioCriador());
						geOperacao.setIdFuncionarioCriador(funcionario);
						manager.persist(geOperacao);
					}
				}
			}
			manager.getTransaction().commit();			
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean inserirSegmentoNovo(List<SegmentoBean> segmentoList, Long idChechIn){
		//CHAMAR O REMOVER PEÇAS
		EntityManager manager = null;
		//abrir transação
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idChechIn);
			for (SegmentoBean segmentoBean : segmentoList) {

				String SQL = "select * from GE_SEGMENTO where ID_CHECKIN = "+idChechIn+" and NUMERO_SEGMENTO = '"+segmentoBean.getNumeroSegmento()+"'";
				Query query = manager.createNativeQuery(SQL);
				if(query.getResultList().size() > 0){
					continue;
				}
				GeSegmento geSegmento = new GeSegmento();
				geSegmento.setIdCheckin(checkIn);
				segmentoBean.toBean(geSegmento, manager);
				
				manager.persist(geSegmento);
				List<OperacaoBean> operacaoList = segmentoBean.getOperacaoList();
				if(operacaoList != null){
					for (int i = 0; i < operacaoList.size(); i++){
						OperacaoBean operacaoBean = operacaoList.get(i);
						GeOperacao geOperacao = new GeOperacao();
						String[] cptcd = operacaoBean.getCptcd().split(" - ");
						geOperacao.setCptcd(cptcd[0]);
						geOperacao.setNumOperacao(operacaoBean.getNumero());
						geOperacao.setDescricaoCompCode(cptcd[1]);
						//geOperacao.setCptcd(operacaoBean.getCptcd());
						geOperacao.setJbcd(operacaoBean.getJbcd());
						geOperacao.setDescricaoJbcd(operacaoBean.getDesricao());
						geOperacao.setIdSegmento(geSegmento);
						geOperacao.setCodErroDbs("100");
						geOperacao.setMsgDbs("Aguardando o retorno do DBS!");
						TwFuncionario funcionario = manager.find(TwFuncionario.class, operacaoBean.getIdFuncionarioCriador());
						geOperacao.setIdFuncionarioCriador(funcionario);
						manager.persist(geOperacao);
					}
				}
			}
			manager.getTransaction().commit();	
			return true;
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public synchronized Boolean sendSegmentoDbs(List<SegmentoBean> segmentoList, Long idCheckIn, UsuarioBean usuarioBean){
		Connection con = null;
		Statement statement = null;
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		EntityManager manager = null;
		String lbrate = "F"; // Caractere que será enviado junto com o total para o DBS para enviar o total para o DBS
		String ind = "E";
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idCheckIn);
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			con.setAutoCommit(true);
			statement = con.createStatement();

			
			//prstmt_ = con.prepareStatement(SQL);
			//rs = prstmt_.executeQuery();

			//SegmentoBusiness business = new SegmentoBusiness();
			
			this.inserirSegmentoNovo(segmentoList, idCheckIn);
			for (SegmentoBean segmentoBean :segmentoList) {
				try {
					String horas = segmentoBean.getHoras().replace(".", "");
//					horas = new Integer(Integer.valueOf(horas) * segmentoBean.getQtdComp()).toString();
					if(horas.length() == 3){
						horas = "00"+horas;
					}else if(horas.length() == 4){
						horas = "0"+horas;
					}else if(horas.length() == 1){
						horas = "0000"+horas;
					}else if(horas.length() == 2){
						horas = "000"+horas;
					}
//					if(checkIn.getTipoCliente().equals("INT")){
//						horas = "";
//					}
//					String fatorCliente = "";
//					if (checkIn.getTipoCliente() == null || (checkIn.getTipoCliente() != "" && checkIn.getTipoCliente().equals("INT"))){
//						fatorCliente = "1,00";
//					}else{
//						fatorCliente = "1,35";
//					}
					String pair = "";
					String SQL = "";
					String qty = segmentoBean.getQtdComp().toString();
					for(int i = segmentoBean.getQtdComp().toString().length(); i < 5; i++){
						qty = "0"+qty;
					}
					String watpcd = "    ";
					if(checkIn.getSiglaIndicadorGarantia() != null && !("").equals(checkIn.getSiglaIndicadorGarantia())){
						watpcd = checkIn.getSiglaIndicadorGarantia();
					}
					Map<String, String> map = new ChekinBusiness(usuarioBean).findValorMaoDeObra(checkIn.getId(), checkIn.getIdOsPalm().getModelo(), segmentoBean.getNumeroSegmento(), checkIn.getTipoCliente(), checkIn.getIdOsPalm().getIdFamilia().getId());					
					if(map != null && map.size() > 0){
						pair = "'"+idCheckIn+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"','"+lbrate+"','"+map.get(segmentoBean.getNumeroSegmento())+"', '"+qty+"', '"+watpcd+"','       '";
						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, lbrate, lbamt, qty, watpcd, pipno) values("+pair+")";	
					}else{
						pair = "'"+idCheckIn+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"', '"+qty+"', '"+watpcd+"','       '";
						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, qty, watpcd, pipno) values("+pair+")";
					}
					
//				pair = "'"+idCheckIn+"-OF','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','E','"+horas+"'";	
//				SQL = "insert into "+AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr) values("+pair+")";			
					statement.executeUpdate(SQL);
				} catch (Exception e) {
					Query query = manager.createQuery("From GeSegmento where idCheckin.id =:id and numeroSegmento =:numeroSegmento");
					query.setParameter("id", checkIn.getId());
					query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
					GeSegmento seg = (GeSegmento)query.getSingleResult();
					seg.setMsgDbs("Erro ao enviar segmento para o DBS!");
					seg.setCodErroDbs("99");
					manager.merge(seg);
					manager.getTransaction().commit();
					return false;
				}
			}
			manager.getTransaction().commit();
			
//			Thread.sleep(5000);
//			
//			ChekinBusiness chekinBusiness = new ChekinBusiness(null);
//			if(checkIn.getTipoCliente() != null && !checkIn.getTipoCliente().equals("")){
//				if(checkIn.getTipoCliente().equals("INT")){
//					if(!chekinBusiness.updateIntoClienteInterOrExcecaoGarantiaDbsAvulso(checkIn.getNumeroOs(),checkIn.getClienteInter(), checkIn.getContaContabilSigla(), checkIn.getCentroCustoSigla(), null, checkIn.getId().toString(), segmentoList)){
//						return false;
//					}
//				}else if(checkIn.getTipoCliente().equals("EXT")){
//					if(!chekinBusiness.updateGarantiaClienteExternoDbs(checkIn.getNumeroOs(), checkIn.getContaContabilSigla(), checkIn.getCentroCustoSigla(),segmentoList.get(0).getNumeroSegmento(),checkIn.getId().toString())){
//						return false;
//					}
//				}
//			}else{
//				//significa que é garantica com a regra de excessão
//				if(!("").equals(checkIn.getClienteInter())){
//					if(!chekinBusiness.updateIntoClienteInterOrExcecaoGarantiaDbsAvulso(checkIn.getNumeroOs(),checkIn.getClienteInter(), checkIn.getContaContabilSigla(), checkIn.getCentroCustoSigla(), null, checkIn.getId().toString(), segmentoList)){
//						return false;
//					}
//				}else{//grantia sem regra de excessão
//					if(!chekinBusiness.updateGarantiaClienteExternoDbs(checkIn.getNumeroOs(), checkIn.getContaContabilSigla(), checkIn.getCentroCustoSigla(),segmentoList.get(0).getNumeroSegmento(),checkIn.getId().toString())){
//						return false;
//					}
//				}
//			}
			
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
			try {
				if(statement != null){
					statement.close();
				}
				if(con != null){
					con.close();
				}
			} catch (SQLException e) {
				//manager.getTransaction().rollback();
				e.printStackTrace();
			}
		}
			
		return false;
		
		
	}
	/**
	 * Altera as horas e a quantidade de itens de um segmento
	 * @param segmentoBean
	 * @param idCheckIn
	 * @param usuarioBean
	 * @return
	 */
	public String alterHoursSegmentoDbs(SegmentoBean segmentoBean, Long idCheckIn, UsuarioBean usuarioBean){
		Connection con = null;
		Statement statement = null;
		EntityManager manager = null;
		String lbrate = "F"; // Caractere que será enviado junto com o total para o DBS para enviar o total para o DBS
		String ind = "E";
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idCheckIn);
			
//			Query query = manager.createNativeQuery("select case  when sum(DATEDIFF(SS, ha.data_ini, ha.data_fim)) is null then 0 ELSE"+
//														" sum(DATEDIFF(SS, ha.data_ini, ha.data_fim)) END "+
//														" from OF_AGENDAMENTO ag, OF_SEGMENTO seg, OF_HORAS_AGENDAMENTO ha"+
//														" where ag.ID_SEGMENTO = seg.id"+
//														" and ag.NUM_OS =:NUM_OS"+
//														" and seg.NUM_SEGMENTO =:NUM_SEGMENTO"+
//														" and ha.ID_AGENDAMENTO = ag.ID");
//			query.setParameter("NUM_OS", checkIn.getNumeroOs());
//			query.setParameter("NUM_SEGMENTO", segmentoBean.getNumeroSegmento());
//			Integer segundosTrab = (Integer)query.getSingleResult();
//			Double horasTrab =  segundosTrab.doubleValue() / 3600;
//			if(horasTrab > Double.valueOf(segmentoBean.getHorasSubst())){
//				DecimalFormat df = new DecimalFormat("0.##");
//				return "A alteração não é permitida, pois as horas que já foram trabalhadas na OS,\nsão maiores que as horas a substituir!\n" +
//						"Horas Trabalhadas = "+df.format(horasTrab)+"\n" +
//						"Horas a substituir = "+segmentoBean.getHorasSubst();
//			}
			
			
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			con.setAutoCommit(true);
			statement = con.createStatement();
			
			Query query = manager.createQuery("From GeSegmento where idCheckin.id =:id and numeroSegmento =:numeroSegmento");
			query.setParameter("id", checkIn.getId());
			query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
			GeSegmento seg = (GeSegmento)query.getSingleResult();
			seg.setHorasPrevistaSubst(segmentoBean.getHorasSubst());
			seg.setQtdCompSubst(segmentoBean.getQtdCompSubst());
			seg.setIdTwFuncionarioSubst(usuarioBean.getMatricula());
			seg.setDataSubstituicao(new Date());
			manager.getTransaction().commit();
			manager.getTransaction().begin();
			
				try {
					String horas = segmentoBean.getHorasSubst().replace(".", "");
//					horas = new Integer(Integer.valueOf(horas) * segmentoBean.getQtdComp()).toString();
					if(horas.length() == 3){
						horas = "00"+horas;
					}else if(horas.length() == 4){
						horas = "0"+horas;
					}else if(horas.length() == 1){
						horas = "0000"+horas;
					}else if(horas.length() == 2){
						horas = "000"+horas;
					}
//					String fatorCliente = "";
//					if (checkIn.getTipoCliente() == null || (checkIn.getTipoCliente() != "" && checkIn.getTipoCliente().equals("INT"))){
//						fatorCliente = "1,00";
//					}else{
//						fatorCliente = "1,35";
//					}
					String pair = "";
					String SQL = "";
					String qty = segmentoBean.getQtdCompSubst().toString();
					for(int i = segmentoBean.getQtdCompSubst().toString().length(); i < 5; i++){
						qty = "0"+qty;
					}
					String watpcd = "    ";
					if(checkIn.getSiglaIndicadorGarantia() != null && !("").equals(checkIn.getSiglaIndicadorGarantia())){
						watpcd = checkIn.getSiglaIndicadorGarantia();
					}
					Map<String, String> map = new ChekinBusiness(usuarioBean).findValorMaoDeObraSubst(checkIn.getId(), checkIn.getIdOsPalm().getModelo(), segmentoBean.getNumeroSegmento(), checkIn.getTipoCliente(), checkIn.getIdOsPalm().getIdFamilia().getId());					
					if(map != null && map.size() > 0){
						pair = "'"+idCheckIn+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"','"+lbrate+"','"+map.get(segmentoBean.getNumeroSegmento())+"', '"+qty+"', '"+watpcd+"','       '";
						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, lbrate, lbamt, qty, watpcd, pipno) values("+pair+")";	
					}else{
						pair = "'"+idCheckIn+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"', '"+qty+"', '"+watpcd+"','       '";
						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, qty, watpcd, pipno) values("+pair+")";
					}
					
//				pair = "'"+idCheckIn+"-OF','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','E','"+horas+"'";	
//				SQL = "insert into "+AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr) values("+pair+")";
					
					
					
					
					
					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where WONOSM = '"+idCheckIn+"-O' and wono = '"+seg.getIdCheckin().getNumeroOs()+"' and wosgno = '"+seg.getNumeroSegmento()+"'");
					
					statement.executeUpdate(SQL);
					
					
					
					
					seg.setIsCreateSegmento("N");
					seg.setCodErroDbs("100");
					seg.setMsgDbs("Processando segmento no DBS, aguarde o retorno!");
				} catch (Exception e) {
					query = manager.createQuery("From GeSegmento where idCheckin.id =:id and numeroSegmento =:numeroSegmento");
					query.setParameter("id", checkIn.getId());
					query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
					seg = (GeSegmento)query.getSingleResult();
					seg.setMsgDbs("Erro ao alterar quantidade e horas do Segmento!");
					seg.setCodErroDbs("99");
					manager.merge(seg);
				}
			//}
			manager.getTransaction().commit();
			
			logEditarSegmento(manager, seg.getId());
			
			return "Solicitação de alteração de quantidade e horas executada com sucesso!\nAguarde o retorno do DBS.";
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(statement != null){
					statement.close();
				}
				if(con != null){
					con.close();
				}
			} catch (SQLException e) {
				//manager.getTransaction().rollback();
				e.printStackTrace();
			}
		}
		
		return "Não foi possível realizar solicitação de alteração de quantidade e horas executada com sucesso!\nAcione o administrador do sistema.";
		
		
	}
	public void logEditarSegmento(EntityManager manager, Long idSegmento) {
		try {
			Query query;
			
			String SQl = "insert GE_SEGMENTO_LOG (NUMERO_SEGMENTO "+
							    "  ,JOB_CODE "+
							    "  ,DESCRICAO_JOB_CODE "+
							    "  ,COM_CODE "+
							    "  ,ID_CHECKIN "+
							    "  ,HORAS_PREVISTA "+
							    "  ,MSG_DBS "+
							    "  ,NUM_DOC "+
							    "  ,JOB_CONTROL "+
							    "  ,DESCRICAO_COMP_CODE "+
							    "  ,QTD_COMPONENTE "+
							    "  ,HAS_SEND_DBS "+
							    "  ,QTD_COMP "+
							    "  ,ID_FUNCIONARIO_CRIADOR "+
							    "  ,OBSERVACAO "+
							    "  ,IS_CREATE_SEGMENTO "+
							    "  ,COD_ERRO_DBS "+
							    "  ,ID_FUNCIONARIO_PECAS "+
							    "  ,DATA_LIBERACAO_PECAS "+
							    "  ,DATA_TERMINO_SERVICO "+
							    "  ,QTD_COMP_SUBST "+
							    "  ,HORAS_PREVISTA_SUBST "+
							    "  ,ID_TW_FUNCIONARIO_SUBST "+
							    "  ,DATA_SUBSTITUICAO "+
							    "  ,TITULO_FOTOS "+
							    "  ,DESCRICAO_FALHA_FOTOS "+
							    "  ,CONCLUSAO_FOTOS "+
							    "  ,FUNCIONARIO_FOTOS "+
							    "  , ID_FUNCIONARIO_EDICAO) "+
							" SELECT   "+
							    "  NUMERO_SEGMENTO "+
							    "  ,JOB_CODE "+
							    "  ,DESCRICAO_JOB_CODE "+
							    "  ,COM_CODE "+
							    "  ,ID_CHECKIN "+
							    "  ,HORAS_PREVISTA "+
							    "  ,MSG_DBS "+
							    "  ,NUM_DOC "+
							    "  ,JOB_CONTROL "+
							    "  ,DESCRICAO_COMP_CODE "+
							    "  ,QTD_COMPONENTE "+
							    "  ,HAS_SEND_DBS "+
							    "  ,QTD_COMP "+
							    "  ,ID_FUNCIONARIO_CRIADOR "+
							    "  ,OBSERVACAO "+
							    "  ,IS_CREATE_SEGMENTO "+
							    "  ,COD_ERRO_DBS "+
							    "  ,ID_FUNCIONARIO_PECAS "+
							    "  ,DATA_LIBERACAO_PECAS "+
							    "  ,DATA_TERMINO_SERVICO "+
							    "  ,QTD_COMP_SUBST "+
							    "  ,HORAS_PREVISTA_SUBST "+
							    "  ,ID_TW_FUNCIONARIO_SUBST "+
							    "  ,DATA_SUBSTITUICAO "+
							    "  ,TITULO_FOTOS "+
							    "  ,DESCRICAO_FALHA_FOTOS "+
							    "  ,CONCLUSAO_FOTOS "+
							    "  ,FUNCIONARIO_FOTOS "+
							    "  , '"+this.usuarioBean.getMatricula()+"' ID_FUNCIONARIO_EDICAO "+
							  " FROM GE_SEGMENTO where id = "+idSegmento;
			manager.getTransaction().begin();
			query = manager.createNativeQuery(SQl);
			query.executeUpdate();
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			new EmailHelper().sendSimpleMail("erro log de editar segmento", "Erro Log editar segmento  Sistema Oficina", "rodrigo@rdrsistemas.com.br");
			e.printStackTrace();
		} 
	}
	
//	private String sendCentroCustoContatabilDbs(Long idCheckIn){
//		EntityManager manager = null;
//		manager = JpaUtil.getInstance();
//		GeCheckIn checkIn = manager.find(GeCheckIn.class, idCheckIn);
//		
//		if(checkIn.getTipoCliente() != null){
//			if(checkIn.getTipoCliente().equals("INT")){
//				if(!this.updateIntoClienteInterOrExcecaoGarantiaDbs(checkIn.getNumeroOs(),checkIn.getClienteInter(), checkIn.getContaContabilSigla(), checkIn.getCentroCustoSigla(),segmentoList, idCheckIn.toString())){
//					bean.setMsg("O segmento foi criado, mas não foi possível cadastrar centro de custo e conta contábil!");
//				}
//			}else if(bean.getVcc().getTipoCliente().equals("EXT")){
//				if(!this.updateGarantiaClienteExternoDbs(wono, bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),bean.getSegmento(),checkInObj.getId().toString())){
//					bean.setMsg("O segmento foi criado, mas não foi possível cadastrar centro de custo e conta contábil!");
//				}
//			}
//		}else{
//			//significa que é garantica com a regra de excessão
//			if(!("").equals(bean.getVcc().getClienteInter())){
//				if(!this.updateIntoClienteInterOrExcecaoGarantiaDbs(wono,bean.getVcc().getClienteInter(), bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),segmentoList,checkInObj.getId().toString())){
//					bean.setMsg("O segmento foi criado, mas não foi possível cadastrar centro de custo e conta contábil!");
//				}
//			}else{//grantia sem regra de excessão
//				if(!this.updateGarantiaClienteExternoDbs(wono, bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),bean.getSegmento(),checkInObj.getId().toString())){
//					bean.setMsg("O segmento foi criado, mas não foi possível cadastrar centro de custo e conta contábil!");
//				}
//			}
//		}
//	}
	
	public Boolean sendOperacaoDbs(ChekinBean checkInObj, List<SegmentoBean> segmentoList){
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();

			Connection con = null;
			Statement statement = null;
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
			try {
//				prop.load(in);
//				String url = prop.getProperty("dbs.url");
//				String user = prop.getProperty("dbs.user");
//				String password =prop.getProperty("dbs.password");
//				Class.forName(prop.getProperty("dbs.driver")).newInstance();
				con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
				statement = con.createStatement();
				manager.getTransaction().begin();
				
				String SQL = "delete from LIBU15FTP.USPOPSM0 where wonosm = '"+checkInObj.getId()+"-O' and coderr = '99'";
				statement.executeUpdate(SQL);
				
				for (SegmentoBean segmentoBean : segmentoList) {
					for (OperacaoBean operacaoBean : segmentoBean.getOperacaoList()) {
						String pair = "'"+checkInObj.getId()+"-O','"+checkInObj.getNumeroOs()+"','"+segmentoBean.getNumeroSegmento()+"','"+operacaoBean.getNumero()+"','"+operacaoBean.getJbcd()+"','"+operacaoBean.getCptcd().split(" - ")[0]+"'";
						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 (wonosm, wono, wosgno, woopno, jbcd, cptcd) values("+pair+")"; //
						statement.executeUpdate(SQL);
					}

					Query query =  manager.createQuery(" from GeSegmento s where s.numeroSegmento =:numeroSegmento and s.idCheckin.id =:id");
					query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
					query.setParameter("id", checkInObj.getId());
					GeSegmento seg = (GeSegmento)query.getSingleResult();
					seg.setHasSendDbs("S");
				}
				
				
				manager.getTransaction().commit();
				return true;
			}catch (Exception e) {
				if(manager != null && manager.getTransaction().isActive()){
	        		manager.getTransaction().rollback();
	        	}
				e.printStackTrace();
				return false;
			}finally{
				if(manager != null && manager.isOpen()){
					manager.close();
	        	}
				try {
					if(statement != null){
						statement.close();
					}
					if(con != null){
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}


	}
	public Boolean sendOperacaoDbs(ChekinBean checkInObj, List<OperacaoBean> operacaoList, String numeroSegmento){
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			Connection con = null;
			Statement statement = null;
			try {
				con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
				statement = con.createStatement();
				manager.getTransaction().begin();
				
												    
//				String SQL = "delete from LIBU15FTP.USPOPSM0 where wonosm = '"+checkInObj.getId()+"-O' and coderr = '99'";
//				statement.executeUpdate(SQL);
				
				for (OperacaoBean operacaoBean : operacaoList) {
					GeSegmento seg = null;
					try {
						


						Query query =  manager.createQuery(" from GeSegmento s where s.numeroSegmento =:numeroSegmento and s.idCheckin.id =:id");
						query.setParameter("numeroSegmento", numeroSegmento);
						query.setParameter("id", checkInObj.getId());
						seg = (GeSegmento)query.getSingleResult();
						seg.setHasSendDbs("S");
						
						GeOperacao geOperacao = new GeOperacao();
						String[] cptcd = operacaoBean.getCptcd().split(" - ");
						geOperacao.setCptcd(cptcd[0]);
						geOperacao.setNumOperacao(operacaoBean.getNumero());
						geOperacao.setDescricaoCompCode(cptcd[1]);
						//geOperacao.setCptcd(operacaoBean.getCptcd());
						geOperacao.setJbcd(operacaoBean.getJbcd());
						geOperacao.setDescricaoJbcd(operacaoBean.getDesricao());
						geOperacao.setIdSegmento(seg);
						geOperacao.setCodErroDbs("100");
						geOperacao.setMsgDbs("Operação enviada, aguarde o retorno do DBS!");
						manager.persist(geOperacao);
						
						String pair = "'"+geOperacao.getId()+"-O','"+checkInObj.getNumeroOs()+"','"+numeroSegmento+"','"+operacaoBean.getNumero()+"','"+operacaoBean.getJbcd()+"','"+operacaoBean.getCptcd().split(" - ")[0]+"'";
						String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 (wonosm, wono, wosgno, woopno, jbcd, cptcd) values("+pair+")"; //
						statement.executeUpdate(SQL);
					} catch (Exception e) {
						e.printStackTrace();
						GeOperacao geOperacao = new GeOperacao();
						String[] cptcd = operacaoBean.getCptcd().split(" - ");
						geOperacao.setCptcd(cptcd[0]);
						geOperacao.setNumOperacao(operacaoBean.getNumero());
						geOperacao.setDescricaoCompCode(cptcd[1]);
						//geOperacao.setCptcd(operacaoBean.getCptcd());
						geOperacao.setJbcd(operacaoBean.getJbcd());
						geOperacao.setDescricaoJbcd(operacaoBean.getDesricao());
						geOperacao.setIdSegmento(seg);
						geOperacao.setCodErroDbs("99");
						geOperacao.setMsgDbs("Erro ao enviar operação para o DBS!");
					}
					
				}
				
				manager.getTransaction().commit();
				return true;
			}catch (Exception e) {
				if(manager != null && manager.getTransaction().isActive()){
	        		manager.getTransaction().rollback();
	        	}
				e.printStackTrace();
				return false;
			}finally{
				try {
					if(manager != null && manager.isOpen()){
	        			manager.close();
	        		}
					if(statement != null){
						statement.close();
					}
					if(con != null){
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}


	public List<SegmentoBean> findAllSegmento(Long idCheckin){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		//Connection conn = null;
		//Statement st = null;
		//ResultSet rs = null;
		try{
			manager = JpaUtil.getInstance();
			//conn = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Query query = manager.createQuery("From GeSegmento where idCheckin.id = :idCheckin order by numeroSegmento");
			query.setParameter("idCheckin", idCheckin);
			List<GeSegmento> result = query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoSerTerc = new SituacaoServicoTerceirosBusiness(null);
			for(GeSegmento segmentoObj : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(segmentoObj);
//				if(bean.getIdFuncionarioPecas() != null){
//					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
//					
//					String nomeFuncionarioPecas = (String) query.getSingleResult();
//					bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
//				}	
				
				query = manager.createNativeQuery("select distinct f.ESTIMATEBY from  Ge_Doc_Seg_Oper op, tw_funcionario f where op.id in (select oper.id from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento) and f.EPIDNO = op.ID_FUNCIONARIO_PECAS");
				query.setParameter("id_Segmento", segmentoObj.getId());
				List<String> siglafunc = query.getResultList();
				String estimateBy = "";
				for (String esby : siglafunc) {
					estimateBy += esby;
				}
				if(estimateBy.length() > 0){
					estimateBy = estimateBy.substring(0, estimateBy.length());
				}
				bean.setNomeFuncionarioPecas(estimateBy);
				query = manager.createNativeQuery("select num_doc, id from  Ge_Doc_Seg_Oper where id in (select oper.id from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento and num_doc is not null)");
				query.setParameter("id_Segmento", segmentoObj.getId());
				List<Object[]> listNumDoc = query.getResultList();
				String numDoc = "";
				String idDocSegOper = "";
				for (Object[] pair : listNumDoc) {
					
				
				//if(query.getResultList().size() > 0){
					//Object[]pair = (Object[])query.getResultList().get(0);
					numDoc += (String)pair[0]+",";
					idDocSegOper +=  Integer.valueOf( ((BigDecimal)pair[1]).toString())+",";
					//bean.setNumeroDoc((String)pair[0]);
					//bean.setIdDocSegOper(((BigDecimal)pair[1]).longValue());
//					st = conn.createStatement();
//					rs = st.executeQuery("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segmentoObj.getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segmentoObj.getNumeroSegmento()+"'");
//					//bean.setPedido("Não");
//					if(rs.next()){
//						bean.setPedido("Sim");
//					}
				}
				if(numDoc.length() > 0){
					numDoc = numDoc.substring(0,numDoc.length() - 1);
				}
				if(idDocSegOper.length() > 0){
					idDocSegOper = idDocSegOper.substring(0,idDocSegOper.length() - 1);
				}
				bean.setIdDocSegOperList(idDocSegOper);
				bean.setNumeroDoc(numDoc);
				//bean.setIdDocSegOper(((BigDecimal)pair[1]).longValue());
				segmento.add(bean);				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			if(conn != null){
//				try {
//					//st.close();
//					//rs.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmento(String numeroOs){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		//Connection conn = null;
		//Statement st = null;
		//ResultSet rs = null;
		try{
			manager = JpaUtil.getInstance();
			//conn = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Query query = manager.createQuery("From GeSegmento seg, GeCheckIn ch where seg.idCheckin.id = ch.id and ch.numeroOs =:numeroOs order by seg.numeroSegmento");
			query.setParameter("numeroOs", numeroOs);
			List<Object[]> result = query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoSerTerc = new SituacaoServicoTerceirosBusiness(null);
			for(Object[] pair : result){
				GeSegmento segmentoObj = (GeSegmento)pair[0];
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(segmentoObj);
				if(bean.getIdFuncionarioPecas() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
					
					String nomeFuncionarioPecas = (String) query.getSingleResult();
					bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
				}	
				query = manager.createNativeQuery("select num_doc, id from  Ge_Doc_Seg_Oper where id = (select max(oper.id) from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento)");
				query.setParameter("id_Segmento", segmentoObj.getId());
				if(query.getResultList().size() > 0){
					Object[]pair1 = (Object[])query.getResultList().get(0);
					bean.setNumeroDoc((String)pair1[0]);
					bean.setIdDocSegOper(((BigDecimal)pair1[1]).longValue());
//					st = conn.createStatement();
//					rs = st.executeQuery("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segmentoObj.getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segmentoObj.getNumeroSegmento()+"'");
//					//bean.setPedido("Não");
//					if(rs.next()){
//						bean.setPedido("Sim");
//					}
				}
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			if(conn != null){
//				try {
//					//st.close();
//					//rs.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoLog(String numeroOs){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		//Connection conn = null;
		//Statement st = null;
		//ResultSet rs = null;
		try{
			manager = JpaUtil.getInstance();
			//conn = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Query query = manager.createQuery("From GeSegmentoLog seg, GeCheckIn ch where seg.idCheckin = ch.id and ch.numeroOs =:numeroOs order by seg.numeroSegmento");
			query.setParameter("numeroOs", numeroOs);
			List<Object[]> result = query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoSerTerc = new SituacaoServicoTerceirosBusiness(null);
			for(Object[] pair : result){
				GeSegmentoLog segmentoObj = (GeSegmentoLog)pair[0];
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(segmentoObj);
				if(bean.getIdFuncionarioPecas() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
					
					String nomeFuncionarioPecas = (String) query.getSingleResult();
					bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
				}
				if(segmentoObj.getIdFuncionarioRemocao() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+segmentoObj.getIdFuncionarioRemocao()+"'");
					
					String nomeFuncionarioRemocao = (String) query.getSingleResult();
					bean.setNomeFuncionarioRemocao(nomeFuncionarioRemocao);	
				}
				if(segmentoObj.getIdFuncionarioEdicao() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+segmentoObj.getIdFuncionarioEdicao()+"'");
					
					String nomeFuncionarioEdicao = (String) query.getSingleResult();
					bean.setNomeFuncionarioEdicao(nomeFuncionarioEdicao);	
				}
				query = manager.createNativeQuery("select num_doc, id from  Ge_Doc_Seg_Oper where id = (select max(oper.id) from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento)");
				query.setParameter("id_Segmento", segmentoObj.getId());
				if(query.getResultList().size() > 0){
					Object[]pair1 = (Object[])query.getResultList().get(0);
					bean.setNumeroDoc((String)pair1[0]);
					bean.setIdDocSegOper(((BigDecimal)pair1[1]).longValue());
//					st = conn.createStatement();
//					rs = st.executeQuery("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segmentoObj.getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segmentoObj.getNumeroSegmento()+"'");
//					//bean.setPedido("Não");
//					if(rs.next()){
//						bean.setPedido("Sim");
//					}
				}
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			if(conn != null){
//				try {
//					//st.close();
//					//rs.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoPedido(Long idCheckin){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			manager = JpaUtil.getInstance();
			conn = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Query query = manager.createQuery("From GeSegmento s, GeDocSegOper oper where s.idCheckin.id = :idCheckin and oper.idSegmento.id = s.id  order by s.numeroSegmento");
			query.setParameter("idCheckin", idCheckin);
			List<Object[]> result = query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoSerTerc = new SituacaoServicoTerceirosBusiness(null);
			for(Object[] pair : result){
				SegmentoBean bean = new SegmentoBean();
				GeSegmento segmentoObj = (GeSegmento)pair[0];
				GeDocSegOper docSegOper = (GeDocSegOper)pair[1];
				bean.fromBean((GeSegmento)pair[0]);
				if(bean.getIdFuncionarioPecas() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+docSegOper.getIdFuncionarioPecas()+"'");
					
					String nomeFuncionarioPecas = (String) query.getSingleResult();
					bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
				}	
//				query = manager.createNativeQuery("select num_doc from  Ge_Doc_Seg_Oper where id in (select oper.id from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento)");
//				query.setParameter("id_Segmento", segmentoObj.getId());
				if(docSegOper.getNumDoc() != null){
					bean.setNumeroDoc(docSegOper.getNumDoc());
					st = conn.createStatement();
					rs = st.executeQuery("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segmentoObj.getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segmentoObj.getNumeroSegmento()+"'");
					//bean.setPedido("Não");
					if(rs.next()){
						bean.setPedido("Sim");
					}
				}
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(conn != null){
				try {
					conn.close();
					st.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoServTerc(Long idCheckin){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		//Connection conn = null;
		//Statement st = null;
		//ResultSet rs = null;
		try{
			manager = JpaUtil.getInstance();
			//conn = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Query query = manager.createQuery("From GeSegmento where idCheckin.id = :idCheckin order by numeroSegmento");
			query.setParameter("idCheckin", idCheckin);
			List<GeSegmento> result = query.getResultList();
			SituacaoServicoTerceirosBusiness situacaoSerTerc = new SituacaoServicoTerceirosBusiness(null);
			for(GeSegmento segmentoObj : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(segmentoObj);
				if(bean.getIdFuncionarioPecas() != null){
					query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
					
					String nomeFuncionarioPecas = (String) query.getSingleResult();
					bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
				}	
				query = manager.createNativeQuery("select num_doc from  Ge_Doc_Seg_Oper where id = (select max(oper.id) from Ge_Doc_Seg_Oper oper where oper.id_Segmento =:id_Segmento)");
				query.setParameter("id_Segmento", segmentoObj.getId());
				if(query.getResultList().size() > 0){
					bean.setNumeroDoc((String)query.getResultList().get(0));
//					st = conn.createStatement();
//					rs = st.executeQuery("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segmentoObj.getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segmentoObj.getNumeroSegmento()+"'");
//					//bean.setPedido("Não");
//					if(rs.next()){
//						bean.setPedido("Sim");
//					}
				}
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			if(conn != null){
//				try {
//					//st.close();
//					//rs.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return segmento;
	}
	public List<OperacaoBean> findAllOperacoes (Long idSegmento){
		List <OperacaoBean> operacao = new ArrayList<OperacaoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeOperacao where idSegmento.id =:idSegmento order by numOperacao");
			query.setParameter("idSegmento", idSegmento);
			List<GeOperacao> result = query.getResultList();
			for (GeOperacao geOperacao : result){
				OperacaoBean bean = new OperacaoBean();
				bean.fromBean (geOperacao);
				operacao.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return operacao;

	}
	public List<GeDocSegOperBean> verificaPecas (Long idCheckin){
		List <GeDocSegOperBean> listDocSeg = new ArrayList<GeDocSegOperBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("Select seg.id segmento, oper.id operacao from ge_segmento seg"+ 
					" left join  ge_operacao oper on seg.id = oper.id_segmento"+
					" where id_checkin =" + idCheckin);			
			List<Object[]> result = (List<Object[]>) query.getResultList();
			//			int i = 0;
			for (Object[] obj : result){
				if((BigDecimal)obj[1] == null){
					query = manager.createQuery("From GeDocSegOper where id in (select oper.id from GeDocSegOper oper where oper.idSegmento.id = "+(BigDecimal)obj[0]+") ");
				}else{
					query = manager.createQuery("From GeDocSegOper where id in (select oper.id from GeDocSegOper oper where oper.idSegmento.id = "+(BigDecimal)obj[0]+" and idOperacao.id = "+(BigDecimal)obj[1]+") ");
				}
				List<GeDocSegOper> list2 = query.getResultList();
				for(GeDocSegOper oper : list2){    //((String)obj[2] == null)?"":(String)obj[2];  == null ?"" : oper.getIdOperacao().getId())
					GeDocSegOperBean bean = new GeDocSegOperBean();
					bean.setId(oper.getId());					
					bean.setDescOperacao(oper.getIdOperacao() != null ? oper.getIdOperacao().getNumOperacao() + " - " +oper.getIdOperacao().getJbcd()+" - "+ oper.getIdOperacao().getDescricaoJbcd()+"/"+oper.getIdOperacao().getCptcd()+" - "+ oper.getIdOperacao().getDescricaoCompCode(): null );					
					bean.setDescSegmento(oper.getIdSegmento().getNumeroSegmento() + " - " + oper.getIdSegmento().getDescricaoJobCode()+"/"+ oper.getIdSegmento().getDescricaoCompCode());
					bean.setIdOperacao(oper.getIdOperacao()!= null ? oper.getIdOperacao().getId(): null); 
					bean.setIdSegmento(oper.getIdSegmento().getId());
					bean.setMsgDbs(oper.getMsgDbs());
					bean.setNumDoc(oper.getNumDoc());
					if(oper.getNumDoc() != null){
						bean.setMsgDbsRemocao(oper.getMsgDbsRemocao());
					}else{
						bean.setMsgDbsRemocao("O documento não foi criado!");
					}
					bean.setCodErroDbs(oper.getCodErroDbs());
					if (bean.getMsgDbs() == null && bean.getNumDoc() != null){
						bean.setStatus("ok");
						bean.setStatusUrlImage("img/CE.png");						
					}else{
						bean.setStatus("pendente");
						bean.setStatusUrlImage("img/CN.png");
					}
					bean.setDataCriacao(String.valueOf(oper.getDataCriacao()));
					listDocSeg.add(bean);
				}
			}		

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listDocSeg;
	}
	
	
	public Boolean verificaPendeciasPecas (Long idCheckin){
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("Select seg.id segmento, oper.id operacao from ge_segmento seg"+ 
					" left join  ge_operacao oper on seg.id = oper.id_segmento"+
					" where id_checkin =" + idCheckin);			
			List<Object[]> result = (List<Object[]>) query.getResultList();
			//			int i = 0;
			for (Object[] obj : result){
				if((BigDecimal)obj[1] == null){
					query = manager.createQuery("From GeDocSegOper where id = (select max(oper.id) from GeDocSegOper oper where oper.idSegmento.id = "+(BigDecimal)obj[0]+") ");
				}else{
					query = manager.createQuery("From GeDocSegOper where id = (select max(oper.id) from GeDocSegOper oper where oper.idSegmento.id = "+(BigDecimal)obj[0]+" and idOperacao.id = "+(BigDecimal)obj[1]+") ");
				}
				List<GeDocSegOper> listOper = query.getResultList();
				for(GeDocSegOper oper : listOper){  
					if (oper.getNumDoc() == null){
						return false;						
					}
				}
			}		

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}

	public String findHoras (String prefixo, String jobCode, String compCode, String modelo, Long idFamilia){
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select pre.qtd_horas from ge_prefixo p, ge_arv_inspecao m, ge_preco pre " +
					" where p.id_modelo = m.id_arv " +
					" and p.descricao = '"+prefixo.toUpperCase()+"'" +
					"  and m.DESCRICAO = '"+modelo+"' " +
					" and pre.id_modelo = m.id_arv " +
					" and p.id = pre.id_prefixo" +
					" and pre.job_code = '"+jobCode+"'" +
					" and m.id_familia = '"+idFamilia+"'"+
					" and pre.comp_code = '"+compCode+"'" +
					" order by  pre.id desc");
			List<String> result = query.getResultList();
			for (String horas : result){
				return horas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;

	}
	public BigDecimal findIdDocSegOper (Long idSegmento){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select max(oper.id) from ge_doc_seg_oper oper where oper.id_segmento = "+idSegmento);
			BigDecimal idDocSegOper = (BigDecimal)query.getSingleResult();
			return idDocSegOper;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
		
	}
	public String findNumDocDocSegOper (Long idSegmento){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select ope.num_doc from ge_doc_seg_oper ope where ope.id = (select max(oper.id) from ge_doc_seg_oper oper where oper.id_segmento = "+idSegmento+")");
			if(query.getResultList().size() > 0){
				String numDoc = (String)query.getSingleResult();
				return numDoc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
		
	}
	
	public String removerSegmentoDBS(Long idSegmento, String numOs, String numSegmento){
		Connection con = null;
		//ResultSet rs = null;
		Statement prstmtDelete = null;
		EntityManager manager = null;
		String pair = "'"+idSegmento+"-O','"+numOs+"','"+numSegmento+"'";
		String SQl = "";
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
						"	where seg.ID_AGENDAMENTO = ag.ID"+
						"	and ag.NUM_OS = '"+numOs+"'"+
						"	and seg.NUM_SEGMENTO = '"+numSegmento+"'"+
						"	and ag.ID_STATUS_ATIVIDADE = (select ID from OF_STATUS_ATIVIDADE where SIGLA = 'EXE')");
			if(query.getResultList().size() > 0){
				return "Segmento sendo executado na oficina!";
			}

			query = manager.createNativeQuery("select * from GE_SEGMENTO_SERV_TERCEIROS where ID_SEGMENTO = "+idSegmento);
			if(query.getResultList().size() > 0){
				return "O segmento possui serviço de terceiro!";
			}
			
			con = ConectionDbs.getConnecton(); 
			prstmtDelete = con.createStatement();
			//con.setAutoCommit(false);
			prstmtDelete.executeUpdate("insert into "+IConstantAccess.AMBIENTE_DBS+".USPDSSM0 (WONOSM, WONO, WOSGNO) values("+pair+")");
			con.commit();
			
			manager.getTransaction().begin();
			GeSegmento geSegmento = manager.find(GeSegmento.class, idSegmento);			
			geSegmento.setCodErroDbs("100");
			geSegmento.setMsgDbs("Aguarde resposta do DBS!");
			
			manager.merge(geSegmento);
			manager.getTransaction().commit();
			
			try {
				SQl = "insert GE_SEGMENTO_LOG (NUMERO_SEGMENTO "+
								    "  ,JOB_CODE "+
								    "  ,DESCRICAO_JOB_CODE "+
								    "  ,COM_CODE "+
								    "  ,ID_CHECKIN "+
								    "  ,HORAS_PREVISTA "+
								    "  ,MSG_DBS "+
								    "  ,NUM_DOC "+
								    "  ,JOB_CONTROL "+
								    "  ,DESCRICAO_COMP_CODE "+
								    "  ,QTD_COMPONENTE "+
								    "  ,HAS_SEND_DBS "+
								    "  ,QTD_COMP "+
								    "  ,ID_FUNCIONARIO_CRIADOR "+
								    "  ,OBSERVACAO "+
								    "  ,IS_CREATE_SEGMENTO "+
								    "  ,COD_ERRO_DBS "+
								    "  ,ID_FUNCIONARIO_PECAS "+
								    "  ,DATA_LIBERACAO_PECAS "+
								    "  ,DATA_TERMINO_SERVICO "+
								    "  ,QTD_COMP_SUBST "+
								    "  ,HORAS_PREVISTA_SUBST "+
								    "  ,ID_TW_FUNCIONARIO_SUBST "+
								    "  ,DATA_SUBSTITUICAO "+
								    "  ,TITULO_FOTOS "+
								    "  ,DESCRICAO_FALHA_FOTOS "+
								    "  ,CONCLUSAO_FOTOS "+
								    "  ,FUNCIONARIO_FOTOS "+
								    "  , ID_FUNCIONARIO_REMOCAO) "+
								" SELECT   "+
								    "  NUMERO_SEGMENTO "+
								    "  ,JOB_CODE "+
								    "  ,DESCRICAO_JOB_CODE "+
								    "  ,COM_CODE "+
								    "  ,ID_CHECKIN "+
								    "  ,HORAS_PREVISTA "+
								    "  ,MSG_DBS "+
								    "  ,NUM_DOC "+
								    "  ,JOB_CONTROL "+
								    "  ,DESCRICAO_COMP_CODE "+
								    "  ,QTD_COMPONENTE "+
								    "  ,HAS_SEND_DBS "+
								    "  ,QTD_COMP "+
								    "  ,ID_FUNCIONARIO_CRIADOR "+
								    "  ,OBSERVACAO "+
								    "  ,IS_CREATE_SEGMENTO "+
								    "  ,COD_ERRO_DBS "+
								    "  ,ID_FUNCIONARIO_PECAS "+
								    "  ,DATA_LIBERACAO_PECAS "+
								    "  ,DATA_TERMINO_SERVICO "+
								    "  ,QTD_COMP_SUBST "+
								    "  ,HORAS_PREVISTA_SUBST "+
								    "  ,ID_TW_FUNCIONARIO_SUBST "+
								    "  ,DATA_SUBSTITUICAO "+
								    "  ,TITULO_FOTOS "+
								    "  ,DESCRICAO_FALHA_FOTOS "+
								    "  ,CONCLUSAO_FOTOS "+
								    "  ,FUNCIONARIO_FOTOS "+
								    "  , '"+this.usuarioBean.getMatricula()+"' ID_FUNCIONARIO_REMOCAO "+
								  " FROM GE_SEGMENTO where id = "+idSegmento;
				manager.getTransaction().begin();
				query = manager.createNativeQuery(SQl);
				query.executeUpdate();
				manager.getTransaction().commit();
			} catch (Exception e) {
				StringWriter erro = new StringWriter();
				e.printStackTrace(new PrintWriter(erro));
				new EmailHelper().sendSimpleMail("erro log de remover segmento "+SQl+" "+erro.toString(), "Erro Log remover segmento  Sistema Oficina", "rodrigo@rdrsistemas.com.br");
				if(manager != null && manager.getTransaction().isActive()){
					manager.getTransaction().rollback();
				}
			}
			
			
			return "Comando de remover segmento no DBS executado com sucesso!";
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					if(prstmtDelete != null){
						prstmtDelete.close();
					}
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Erro ao tentar remover o segmento";
	}
	public Boolean transferirServicoTerceiros(SegmentoBean bean, String numOs){
		EntityManager manager = null;
		try {

			manager = JpaUtil.getInstance();
			String SQL = "select seg.ID from GE_CHECK_IN c, GE_SEGMENTO seg where c.NUMERO_OS = '"+numOs+"' and c.ID = seg.ID_CHECKIN and seg.NUMERO_SEGMENTO = '46'";
			Query query = manager.createNativeQuery(SQL);
			if(query.getResultList().size() == 0){
				return false;
			}
			BigDecimal idSegmento = (BigDecimal)query.getSingleResult();
			manager.getTransaction().begin();
			query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idSegmento");
			query.setParameter("idSegmento", bean.getId());
			List<GeSegmentoServTerceiros> geSegmentoServTerceirosList = query.getResultList();
			for (GeSegmentoServTerceiros geSegmentoServTerceiros : geSegmentoServTerceirosList) {
				
				GeSegmentoServTerceiros servTerceiros = new GeSegmentoServTerceiros();
				servTerceiros.setGeSegmentoServTerceirosPK(new GeSegmentoServTerceirosPK(idSegmento.longValue(), geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros()));
				servTerceiros.setData(geSegmentoServTerceiros.getData());
				servTerceiros.setValor(geSegmentoServTerceiros.getValor());
				servTerceiros.setQtd(geSegmentoServTerceiros.getQtd());
				servTerceiros.setObs(geSegmentoServTerceiros.getObs());
				servTerceiros.setIdStatusServTerceiros(geSegmentoServTerceiros.getIdStatusServTerceiros());
				servTerceiros.setIdFornServTerceiros(geSegmentoServTerceiros.getIdFornServTerceiros());
				servTerceiros.setLocalServico(geSegmentoServTerceiros.getLocalServico());
				
				manager.persist(geSegmentoServTerceiros);
			}	
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
	public Boolean verificarServicoTerceiros(SegmentoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from GE_SEGMENTO_SERV_TERCEIROS where ID_SEGMENTO =:ID_SEGMENTO ");
			query.setParameter("ID_SEGMENTO", bean.getId());
			if(query.getResultList().size() > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public Boolean removerCotacaoDBS(Long idDocSegOper, String numCotacao){
		Connection con = null;
		//ResultSet rs = null;
		Statement prstmtDelete = null;
		EntityManager manager = null;
		try {
			con = ConectionDbs.getConnecton(); 
			prstmtDelete = con.createStatement();
			//con.setAutoCommit(false);
			prstmtDelete.executeUpdate("insert into "+IConstantAccess.AMBIENTE_DBS+".USPDDSM0 (DOCDBS) values('"+numCotacao+"')");
			con.commit();
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeDocSegOper geDocSegOper = manager.find(GeDocSegOper.class, idDocSegOper);			
			geDocSegOper.setCodErroDbs("100");
			geDocSegOper.setMsgDbsRemocao("Aguarde resposta do DBS!");
			
			manager.merge(geDocSegOper);
			manager.getTransaction().commit();
			
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					if(prstmtDelete != null){
						prstmtDelete.close();
					}
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public List<PecasBoTreeBean> buscaPecasBO(Long idCheckin) {
		PreparedStatement prstmt = null;
		Connection con = null;
		ResultSet rs = null;

		PreparedStatement prstmtLocal = null;
		Connection conLocal = null;
		ResultSet rsLocal = null;

		List<PecasBoTreeBean> pecasBoTreeBeans = new ArrayList<PecasBoTreeBean>();

		try {
			con = com.gestaoequipamentos.util.Connection.getConnection();
//			while (con == null){
//				con = com.gestaoequipamentos.util.Connection.getConnection();
//			}
			prstmt = con.prepareStatement("select oper.num_doc, ch.numero_os, seg.numero_segmento, opera.num_operacao from ge_segmento seg, ge_check_in ch, ge_doc_seg_oper oper left join ge_operacao opera on opera.id  = oper.id_operacao"+
												"	where ch.id = seg.id_checkin"+
												"	and ch.id ="+idCheckin+
												"	and oper.id_segmento = seg.id"+
												"	and oper.id = ("+
												"	select max(r.id) from ge_doc_seg_oper r"+
												"	where r.id_segmento = seg.id)");
			rs = prstmt.executeQuery();
			
			conLocal = com.gestaoequipamentos.util.Connection.getConnection();
//			while (conLocal == null){
//				conLocal = com.gestaoequipamentos.util.Connection.getConnection();
//			}
			
			while (rs.next()) {
				
				PecasBoTreeBean objPai = new PecasBoTreeBean();

				List<PecasBoTreeBean> pecasBoTreeBeansFilhos = new ArrayList<PecasBoTreeBean>();

				String num_doc = rs.getString("num_doc");	//Número do documento
				String numero_os = rs.getString("numero_os");		//Número da OS
				String numero_segmento = rs.getString("numero_segmento");	//Número do segmento
				String num_operacao = rs.getString("num_operacao");	//Número da operação

				objPai.setRfdcno(num_doc);
				objPai.setWono(numero_os);
				objPai.setWosgno(numero_segmento);
				objPai.setWoopno(num_operacao);

				String SQL = "SELECT mod.pano20 pano20, mod.ds18 ds18, mod.qybo qybo, mod.qyor qyor, TO_DATE( mod.ackdt8, 'YYYY/MM/DD') AS data, mod.status_real status_real " +
						"FROM MME.VW_MMEITG_OEPTRC_DS_V2 mod " +
						"WHERE trim(mod.WONO) = '" + numero_os + "' AND WOSGNO = '"+numero_segmento+"'";
				if(num_operacao != null){
					SQL += " AND WOOPNO = '"+num_operacao+"'";
				}
				prstmtLocal = conLocal.prepareStatement(SQL);
				rsLocal = prstmtLocal.executeQuery();

				
				while (rsLocal.next()) {
					PecasBoTreeBean objFilho = new PecasBoTreeBean();

					String pano20 = rsLocal.getString("pano20");			//numero da peça
					String ds18 = rsLocal.getString("ds18");				//Descrição
					String qybo = rsLocal.getString("qybo");				//Quantidade BO(Peças que não estão no estoque)
					String qyor = rsLocal.getString("qyor");				//Quantidade atendida
					String data = rsLocal.getString("data");				//Data
					String statusReal = rsLocal.getString("status_real");	//

					objFilho.setPano20(pano20);
					objFilho.setDs18(ds18);
					objFilho.setQybo(qybo);
					objFilho.setQyor(qyor);
					objFilho.setData(data);
					objFilho.setStatusReal(statusReal);
					
					pecasBoTreeBeansFilhos.add(objFilho);
				}
				
				objPai.setChildren(pecasBoTreeBeansFilhos);
				
				pecasBoTreeBeans.add(objPai);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
					prstmt.close();
				}
				if (conLocal != null) {
					conLocal.close();
					prstmtLocal.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return pecasBoTreeBeans;
	}
	/**
	 * Remove segmento com código de erro 99 (erro no segmento) local e DBS.
	 * @param idSegmento
	 * @param numOs
	 * @param numSegmento
	 * @return
	 */
	public Boolean removerSegmentoComCodErro(Long idSegmento, String numOs,
			String numSegmento, Long idCheckin) {
		EntityManager manager = null;
		Statement prstmt = null;
		Connection con = null;	
		
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from GE_SEGMENTO where ID_CHECKIN = "+idCheckin+" and ID = " +idSegmento);
			query.executeUpdate();
			
			con = ConectionDbs.getConnecton(); 
			prstmt = con.createStatement();
			//con.setAutoCommit(false);
			prstmt.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 s where s.WONOSM = '"+idCheckin+"-O' and s.WOSGNO = '"+numSegmento+"'");

			con.commit();
			manager.getTransaction().commit();
			
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
			try {
				if(con != null){
					con.close();
					prstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Remove a operação com código de erro 99 (erro no segmento) local e DBS.
	 * @param idOperacao
	 * @param numOs
	 * @param numOperacao
	 * @param idCheckin
	 * @return
	 */
	public Boolean removerOperacaoComCodErro(Long idOperacao, Long idSegmento) {
		EntityManager manager = null;
		Statement prstmt = null;
		Connection con = null;	
		
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from GE_OPERACAO where ID = " +idOperacao);
			query.executeUpdate();

			con = ConectionDbs.getConnecton(); 
			prstmt = con.createStatement();
			//con.setAutoCommit(false);
			prstmt.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 s where s.WONOSM = '"+idOperacao+"-O'");

			con.commit();
			manager.getTransaction().commit();

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
			try {
				if(con != null){
					con.close();
					prstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Recupera todos os Job Controls de uma OS 
	 * @param idChekin
	 * @return
	 */
	public List<String> findAllJobControlOrc(Long idChekin){
		EntityManager manager = null;
		List<String> jobControls = new ArrayList<String>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select distinct JOB_CONTROL from GE_SEGMENTO where ID_CHECKIN =:idCheckin order by JOB_CONTROL");
			query.setParameter("idCheckin", idChekin);
			jobControls = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return jobControls;
	}

	public boolean alterarFluxoProcessoOficina(Long idCheckin, String siglaStatus, String dateHeader){
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", idCheckin);
			List<GeSegmento> geSegmentos = query.getResultList();
			for (GeSegmento geSegmento : geSegmentos) {
				manager.getTransaction().begin();


				if(dateHeader == null){
					query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO = "+geSegmento.getId()+" and CONVERT(varchar(10),DATA,112) = CONVERT(varchar(10), getdate(), 112)");
					query.executeUpdate();
				}else{
					query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO = "+geSegmento.getId()+" and CONVERT(varchar(10),DATA,103) = '"+dateHeader+"'");
					query.executeUpdate();
				}

				query = manager.createQuery("from GeLegendaProcessoOficina where sigla = '"+siglaStatus+"'");
				GeLegendaProcessoOficina geLegendaProcessoOficina = (GeLegendaProcessoOficina)query.getSingleResult();



				geSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
				GeDataFluxoSegmento dataFluxoSegmento = new GeDataFluxoSegmento();
				if(dateHeader == null){
					dataFluxoSegmento.setData(new Date());
				} else{
					dataFluxoSegmento.setData(dateFormat.parse(dateHeader));
				}
				if(this.usuarioBean != null){
					dataFluxoSegmento.setIdFuncionario(this.usuarioBean.getMatricula());
				}
				//dataFluxoSegmento.setIdFuncionario(geSegmento.getIdCheckin().getIdSupervisor());
				dataFluxoSegmento.setIdSegmento(geSegmento);
				dataFluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
				manager.persist(dataFluxoSegmento);

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

	
	public boolean alterarFluxoProcessoOficinaJobControl(Long idCheckIn, String siglaStatus, String dateHeader, String jobControl, String observacao){
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
//			if(isDigitador == false && usuarioBean.getJobControl() != null && jobControl != null  && !jobControl.equals(usuarioBean.getJobControl())){
//				return true;
//			}
			manager = JpaUtil.getInstance();
			Query query = null;
			query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
        	query.setParameter("idCheckin", idCheckIn);
        	query.setParameter("jobControl", jobControl);
        	List<GeSegmento> geSegmentos = query.getResultList();
        	if(geSegmentos.size() > 0){
        		GeSegmento geSegmento = geSegmentos.get(0);
        		if(geSegmento.getIdLegendaProcessoOficina() != null && !geSegmento.getIdLegendaProcessoOficina().getSigla().equals("NV")
        				&& siglaStatus.equals("I")){
        			return true;
        		}
        	}
			
			
			
        	manager.getTransaction().begin();
			if(dateHeader == null){
				query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO in (select id from GE_SEGMENTO where ID_CHECKIN = "+idCheckIn+" and JOB_CONTROL = '"+jobControl+"') and CONVERT(varchar(10),DATA,112) = CONVERT(varchar(10), getdate(), 112)");
				query.executeUpdate();
			}else{
				query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO in (select id from GE_SEGMENTO where ID_CHECKIN = "+idCheckIn+" and JOB_CONTROL = '"+jobControl+"') and CONVERT(varchar(10),DATA,103) = '"+dateHeader+"'");
				query.executeUpdate();
			}
			
			query = manager.createQuery("from GeLegendaProcessoOficina where sigla = '"+siglaStatus+"'");
        	GeLegendaProcessoOficina geLegendaProcessoOficina = (GeLegendaProcessoOficina)query.getSingleResult();
			
        	query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
        	query.setParameter("idCheckin", idCheckIn);
        	query.setParameter("jobControl", jobControl);
        	geSegmentos = query.getResultList();
        	
			//GeSegmento geSegmento = manager.find(GeSegmento.class, idSegmento);
        	if(query.getResultList().size() > 0){
        		for (GeSegmento geSegmento: geSegmentos) {
        			geSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
        			if(observacao != null){
        				geSegmento.setObservacaoFluxoOs(observacao);
        			}
        			GeDataFluxoSegmento dataFluxoSegmento = new GeDataFluxoSegmento();
        			if(dateHeader == null){
        				dataFluxoSegmento.setData(new Date());
        			} else{
        				dataFluxoSegmento.setData(dateFormat.parse(dateHeader));
        			}
        			if(this.usuarioBean != null){
        				dataFluxoSegmento.setIdFuncionario(this.usuarioBean.getMatricula());
        			}
        			//dataFluxoSegmento.setIdFuncionario(geSegmento.getIdCheckin().getIdSupervisor());
        			dataFluxoSegmento.setIdSegmento(geSegmento);
        			dataFluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
        			manager.persist(dataFluxoSegmento);
        		}
        	}
			manager.getTransaction().commit();

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
	public boolean alterarFluxoProcessoOficinaJobControlVisualizarSegmento(Long idCheckIn, String siglaStatus, String dateHeader, String jobControl, String observacao){
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(usuarioBean.getJobControl() != null && jobControl != null  && !jobControl.equals(usuarioBean.getJobControl())){
				return true;
			}
			manager = JpaUtil.getInstance();
			Query query = null;
			query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", idCheckIn);
			query.setParameter("jobControl", jobControl);
			List<GeSegmento> geSegmentos = query.getResultList();
			if(geSegmentos.size() > 0){
				GeSegmento geSegmento = geSegmentos.get(0);
				if(geSegmento.getIdLegendaProcessoOficina() != null && !geSegmento.getIdLegendaProcessoOficina().getSigla().equals("NV")
						&& siglaStatus.equals("I")){
					return true;
				}
			}
			
			
			
			manager.getTransaction().begin();
			if(dateHeader == null){
				query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO in (select id from GE_SEGMENTO where ID_CHECKIN = "+idCheckIn+" and JOB_CONTROL = '"+jobControl+"') and CONVERT(varchar(10),DATA,112) = CONVERT(varchar(10), getdate(), 112)");
				query.executeUpdate();
			}else{
				query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO in (select id from GE_SEGMENTO where ID_CHECKIN = "+idCheckIn+" and JOB_CONTROL = '"+jobControl+"') and CONVERT(varchar(10),DATA,103) = '"+dateHeader+"'");
				query.executeUpdate();
			}
			
			query = manager.createQuery("from GeLegendaProcessoOficina where sigla = '"+siglaStatus+"'");
			GeLegendaProcessoOficina geLegendaProcessoOficina = (GeLegendaProcessoOficina)query.getSingleResult();
			
			query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", idCheckIn);
			query.setParameter("jobControl", jobControl);
			geSegmentos = query.getResultList();
			
			//GeSegmento geSegmento = manager.find(GeSegmento.class, idSegmento);
			if(query.getResultList().size() > 0){
				for (GeSegmento geSegmento: geSegmentos) {
					geSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
					if(observacao != null){
						geSegmento.setObservacaoFluxoOs(observacao);
					}
					GeDataFluxoSegmento dataFluxoSegmento = new GeDataFluxoSegmento();
					if(dateHeader == null){
						dataFluxoSegmento.setData(new Date());
					} else{
						dataFluxoSegmento.setData(dateFormat.parse(dateHeader));
					}
					if(this.usuarioBean != null){
						dataFluxoSegmento.setIdFuncionario(this.usuarioBean.getMatricula());
					}
					//dataFluxoSegmento.setIdFuncionario(geSegmento.getIdCheckin().getIdSupervisor());
					dataFluxoSegmento.setIdSegmento(geSegmento);
					dataFluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
					manager.persist(dataFluxoSegmento);
				}
			}
			manager.getTransaction().commit();
			
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
	
	
	
	public List<LegendaProcessoOficinaBean> findAllLegendaProcessoOficina(){
		EntityManager manager = null;
		List<LegendaProcessoOficinaBean> legendas = new ArrayList<LegendaProcessoOficinaBean>();
		try {
			manager = JpaUtil.getInstance();
			//Query query = manager.createQuery("from GeLegendaProcessoOficina where sigla not in ('NV', 'I', 'AO', 'A', 'OK', 'F', 'E', 'R', 'RP','ADI') order by ordem");
			Query query = manager.createQuery("from GeLegendaProcessoOficina order by descricao");
			List<GeLegendaProcessoOficina> legendaList = query.getResultList();
			for (GeLegendaProcessoOficina geLegendaProcessoOficina : legendaList) {
				LegendaProcessoOficinaBean bean = new LegendaProcessoOficinaBean();
				bean.fromBean(geLegendaProcessoOficina);
				legendas.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return legendas;
	}
	
	public List<SegmentoBean> findAllSegmentoJobControl(Long idCheckin){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select distinct s.job_Control, convert(varchar(4000),s.descricao_Job_Control) descricao_Job_Control From Ge_Segmento s where s.id_Checkin = :idCheckin ");
			query.setParameter("idCheckin", idCheckin);
			List<Object[]> result = query.getResultList();
			for(Object[] pair : result){
				SegmentoBean bean = new SegmentoBean();
				bean.setJbctStr((String)pair[0]);
				bean.setDescricaojobControl((String)pair[1]);
				segmento.add(bean);				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoAssociados(Long idSegmento){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery(" select seg from GeSegmento seg, GeSegmentoAssociado sa"+
											  "	where seg.id = sa.idSegmento.id"+
											  "	and sa.idSegPai.id =:idSegmento " +
											  " order by seg.numeroSegmento");
			query.setParameter("idSegmento", idSegmento);
			List<GeSegmento> result = query.getResultList();
			for(GeSegmento geSegmento : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(geSegmento);
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoDesassociados(Long idCheckIn, Long idSegmento){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery(" select seg from GeSegmento seg"+
					"	where seg.id not in (select sa.idSegmento.id from GeSegmentoAssociado sa where sa.idSegmento.id = seg.id)"+
					" and seg.id not in (select sa.idSegPai.id from GeSegmentoAssociado sa where sa.idSegPai.id = seg.id)" +
					" and seg.id not in ("+idSegmento+")" +
			"	and seg.idCheckin.id =:idCheckin " +
			"   order by seg.numeroSegmento");
			query.setParameter("idCheckin", idCheckIn);
			List<GeSegmento> result = query.getResultList();
			for(GeSegmento geSegmento : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(geSegmento);
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return segmento;
	}
	public List<SegmentoBean> findAllSegmentoDesassociadosProposta(Long idCheckIn){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery(" select seg from GeSegmento seg"+
					"	where seg.id not in (select sa.idSegmento.id from GeSegmentoAssociado sa where sa.idSegmento.id = seg.id)"+
					"	and seg.idCheckin.id =:idCheckin " +
			"   order by seg.numeroSegmento");
			query.setParameter("idCheckin", idCheckIn);
			List<GeSegmento> result = query.getResultList();
			for(GeSegmento geSegmento : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(geSegmento);
				segmento.add(bean);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return segmento;
	}
	
	public Boolean desassocicarSegmento(Long idSegmento){  
		//abrir transação
		//delete from ge_segmento where id_checkin = 1
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from GeSegmentoAssociado where idSegmento.id =:idSegmento ");
			query.setParameter("idSegmento", idSegmento);
			GeSegmentoAssociado associado = (GeSegmentoAssociado)query.getSingleResult();
			manager.remove(associado);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean associcarSegmento(Long idSegmento, Long idSegmentoPai){ // REMOVER PELO ID 
		//abrir transação
		//delete from ge_segmento where id_checkin = 1
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeSegmento segmentoPai = manager.find(GeSegmento.class, idSegmentoPai);
			GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);
			
			GeSegmentoAssociado geSegmentoAssociado = new GeSegmentoAssociado();
			geSegmentoAssociado.setFatorCliente(segmento.getIdCheckin().getFatorCliente());
			
			Query query = manager.createQuery("from GeFator ");
			GeFator geFator = (GeFator)query.getSingleResult();
			
			geSegmentoAssociado.setFatorUrgencia(BigDecimal.valueOf(geFator.getFatorUrgencia()));
			String SQL = "select p.id from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = '"+segmento.getIdCheckin().getIdOsPalm().getModelo()+"' and a.id_familia = "+segmento.getIdCheckin().getIdOsPalm().getIdFamilia().getId()+")"+
								" and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring('"+segmento.getIdCheckin().getIdOsPalm().getSerie()+"',0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = '"+segmento.getIdCheckin().getIdOsPalm().getModelo()+"' and a.id_familia = "+segmento.getIdCheckin().getIdOsPalm().getIdFamilia().getId()+"))"+
								" and p.comp_code = '"+segmento.getComCode()+"'"+
								" and p.job_code = '"+segmento.getJobCode()+"'";
			query = manager.createNativeQuery(SQL);
			BigDecimal idPreco = (BigDecimal)query.getSingleResult();
			geSegmentoAssociado.setIdPreco(idPreco.longValue());
			geSegmentoAssociado.setIdSegmento(segmento);
			geSegmentoAssociado.setIdSegPai(segmentoPai);
			manager.persist(geSegmentoAssociado);
			
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
}

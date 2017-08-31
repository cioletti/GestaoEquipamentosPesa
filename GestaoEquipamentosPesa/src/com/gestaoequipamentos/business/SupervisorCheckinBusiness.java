package com.gestaoequipamentos.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.SendEmailHelper;

public class SupervisorCheckinBusiness {
	
	private UsuarioBean bean;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public SupervisorCheckinBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	/**
	 * Busca as OS pelo campo de pesquisa ou jobControl do funcionário. 
	 * @param campoPesquisa
	 * @param jobControl
	 * @return
	 */
//	public List<ChekinBean> findAllChekinByJobControl(String campoPesquisa, String jobControl){
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
//					"  and os.filial =:filial" +
//					"  and ch.jobControl =:jobControl AND (" ;
//			
//			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
//			sqlCheckIn += " ORDER BY ch.id )";
//						
//			sqlCheckIn += " order by os.emissao desc";;
//			Query query = manager.createQuery(sqlCheckIn );
//			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
//			query.setParameter("jobControl", jobControl);
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
	/**
	 * Método que libera a os do supervisor para o digitador.
	 * @param bean
	 * @return true se a operação foi realizada normalmente.
	 */
	public Boolean liberarOSParaDigitador(ChekinBean bean, String jobControl) {
		EntityManager manager = null;
		//Connection con = null;
		//Statement prstmt = null;
		Boolean liberarParaOrcamentista = false;

		//ResultSet rs = null;
		try {
			manager = JpaUtil.getInstance();
			GeCheckIn geCheckIn = manager.find(GeCheckIn.class, bean.getId());
			SegmentoBusiness business = new SegmentoBusiness(this.bean);
			business.alterarFluxoProcessoOficinaJobControl(geCheckIn.getId(), "ADI", null, jobControl, null);
			manager.getTransaction().begin();
			
			Query query = manager.createQuery("FROM GeSituacaoOs WHERE idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", bean.getId());
						
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();
			geCheckIn.setIsLiberadoPDigitador("S");
			manager.merge(geCheckIn);
			if(jobControl.equals(situacaoOs.getIdCheckin().getJobControl())){
				situacaoOs.setDataEntregaPedidos(new Date());			
				situacaoOs.setIdFuncDataEntregaPedidos(this.bean.getMatricula());
				manager.merge(situacaoOs);
				//con = ConectionDbs.getConnecton();
				//prstmt = con.createStatement();
				//prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(dateFormat.format(new Date()), con, geCheckIn.getNumeroOs(), "002"); 
			}
			
			query = manager.createQuery("From GeSegmento where  idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", bean.getId());
			query.setParameter("jobControl", jobControl);
			
			List<GeSegmento> geSegmentos = (List<GeSegmento>)query.getResultList();
			
			//libera os segmentos para o digitador
			for (GeSegmento geSegmento : geSegmentos) {
				geSegmento.setDataLiberacaoPecas(new Date());
			}
			
			
			//Grava data de entrega dos pedidos no DBS.
//			String SQL = "select TRIM(W.NTDA) NTDA from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+geCheckIn.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '002' AND TRIM(W.WOSGNO) = ''";
//			rs = prstmt.executeQuery(SQL);
//			if(rs.next()){
//				String NTDA = rs.getString("NTDA");
//				String dataAux = NTDA.replace("../../....", dateFormat.format(new Date()));
//				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+geCheckIn.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '002' AND TRIM(W.WOSGNO) = ''";
//				prstmt.executeUpdate(SQL);
//			}
			manager.getTransaction().commit();	
			//manager.getTransaction().begin();	
			// Verifica se existe algum segmento a liberar diferente do que vai comitar. 
			query = manager.createNativeQuery("select COUNT(*) from ge_SEGMENTO seg"+ 
								" where seg.ID_CHECKIN = "+bean.getId()+
								" and seg.DATA_LIBERACAO_PECAS is null");
								//" and seg.JOB_CONTROL <> '"+jobControl+"'");
			
			if(((Integer)query.getSingleResult()) == 0){
				// Se não existem segmentos a liberar, verifica se há peças em algum segmento.
				query = manager.createNativeQuery("select COUNT(*) from ge_SEGMENTO seg, GE_DOC_SEG_OPER oper"+ 
								" where seg.ID_CHECKIN = "+bean.getId()+
								" and oper.ID_SEGMENTO = seg.id");
				//para liberar para o orçamentista o job control tem que ser dele
//				if(((Integer)query.getSingleResult()) == 0 && jobControl.equals(situacaoOs.getIdCheckin().getJobControl())){
				if(((Integer)query.getSingleResult()) == 0){
					geCheckIn.setIsLiberadoPOrcamentista("S");
					liberarParaOrcamentista = true;
					business.alterarFluxoProcessoOficina(geCheckIn.getId(), "AO", null);
				}
			}			
			manager.getTransaction().begin();	
		    manager.merge(geCheckIn);
			manager.getTransaction().commit();	
			//só pode enviar e-mail depois de comitar todas as transações o e-mail vai para o orçamentista ou para digitador
			
			if(liberarParaOrcamentista){
				SendEmailHelper.sendEmailOrcamentista(manager, bean.getNumeroOs(), Integer.valueOf(this.bean.getFilial()).toString());
			}else{
				this.sendEmailDigitador(manager, bean.getNumeroOs(),this.bean.getFilial(), jobControl);				
			}
			
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
//			if(con != null){
//				try {
//					//rs.close();
//					prstmt.close();
//					con.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}	
		return true;
		
	}	
	/**
	 * Envia e-mail para o digitador quando supervisor liberar a OS.
	 * @param manager
	 */	
	private void sendEmailDigitador(EntityManager manager, String numOS, String filial, String jobControl){
		try {
			
			if(jobControl == null){
				jobControl = "";
			}
			
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND tw.STN1 = '"+Integer.valueOf(filial).toString()+"'"+
					  " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'DIG' and tipo_sistema = 'GE')"); 
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+", gostaríamos de informar que o(s) segmento(s) da sessão "+jobControl+" da OS "+numOS+" foram liberados para orçamento de peças!", "Ordem de Serviço Sistema Oficina", (String)pair[0]);		
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

package com.gestaoequipamentos.business;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.SendEmailHelper;

public class DigitadorCheckinBusiness {
	
	private UsuarioBean bean;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public DigitadorCheckinBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	/**
	 * Busca as OS pelo campo de pesquisa ou jobControl do funcionário. 
	 * @param campoPesquisa
	 * @param jobControl
	 * @return
	 */
	/*public List<ChekinBean> findAllChekinDigitador(String campoPesquisa){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			String sqlCheckIn = "From " +
					" GeCheckIn ch , GeOsPalm os	" +
					"  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  " +
					"  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					"  and os.idosPalm = ch.idOsPalm " +
					"  and os.filial =:filial" +
					"  and ch.isLiberadoPDigitador = 'S' and (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
						
			sqlCheckIn += " order by os.emissao desc";;
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm);
				bean.setIsRemoveSegmento("N");
				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
						bean.setIsRemoveSegmento("S");
				}
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
	}	*/
	/**
	 * Método que libera a os do supervisor para o digitador.
	 * @param bean
	 * @return true se a operação foi realizada normalmente.
	 */
	public Boolean liberarOSParaOrcamentista(ChekinBean bean) {
		EntityManager manager = null;
//		Connection con = null;
//		Statement prstmt = null;
//		ResultSet rs = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * From " +
									  "	Ge_Segmento where "  +
									  "	id_Checkin =:id_Checkin " +
									  "	and DATA_LIBERACAO_PECAS is null");
			query.setParameter("id_Checkin", bean.getId());
//			if(query.getResultList().size() > 0){
//				return false;
//			}			
			
			manager.getTransaction().begin();
			GeCheckIn geCheckIn = manager.find(GeCheckIn.class, bean.getId());
			if(query.getResultList().size() == 0){
				geCheckIn.setIsLiberadoPOrcamentista("S");
			}else{
				return false;
			}
			manager.merge(geCheckIn);
			
			//Grava data de entrega dos pedidos no DBS.
//			con = ConectionDbs.getConnecton();
//			prstmt = con.createStatement();
			
//			String SQL = "select W.NTDA from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where W.WONO = '"+geCheckIn.getNumeroOs()+"' AND W.NTLNO1 = '002' AND W.WOSGNO = ''";
//			rs = prstmt.executeQuery(SQL);
//			if(rs.next()){
//				String NTDA = rs.getString("NTDA");
//				String dataAux = NTDA.replace("../../....", dateFormat.format(new Date()));
//				SQL = "update LIBT99.WOPNOTE0 W set W.NTDA = '"+dataAux+"' where W.WONO = '"+geCheckIn.getNumeroOs()+"' AND W.NTLNO1 = '002' AND W.WOSGNO = ''";
//				prstmt.executeUpdate(SQL);
//			}
	
			manager.getTransaction().commit();
			
			SendEmailHelper.sendEmailOrcamentista(manager, bean.getNumeroOs(), "'"+Integer.valueOf(this.bean.getFilial()).toString()+"'");
			
			//this.sendEmailOrcamentista(manager, bean.getNumeroOs());
			
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
//	/**
//	 * Envia e-mail para o orçamentista quando digitador liberar a OS. (Rotina foi movida para a classe SendEmailHelper)
//	 * @param manager
//	 */	
//	private void sendEmailOrcamentista(EntityManager manager, String numOS){
//		try {
//			Query query = manager.createNativeQuery("select * from GE_CHECK_IN c, GE_SEGMENTO seg, GE_SEGMENTO_SERV_TERCEIROS ter "+
//					"	where c.ID = seg.ID_CHECKIN "+
//					"	and seg.ID = ter.ID_SEGMENTO "+
//					"	and c.NUMERO_OS = '"+numOS+"'"); 
//			String msgComplemento = "";
//			if(query.getResultList().size() > 0){
//				msgComplemento = "E possui serviços de terceiros.";
//			}
//			 query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
//					  " WHERE tw.epidno = perfil.id_tw_usuario"+
//					  " AND tw.STN1 = '"+Integer.valueOf(this.bean.getFilial()).toString()+"'"+
//					  " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'ORC' and tipo_sistema = 'GE')"); 
//			
//			if(query.getResultList().size() > 0){
//				List<Object []> list = query.getResultList();
//				
//				for(Object [] pair : list){
//					EmailHelper helper = new EmailHelper();
//					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi liberada para orçamento! "+msgComplemento, "Ordem de Serviço", (String)pair[0]);		
//				}				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}

}

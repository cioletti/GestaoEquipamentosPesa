package com.gestaoequipamentos.business;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.ComplexidadeBean;
import com.gestaoequipamentos.bean.ComponenteCodeBean;
import com.gestaoequipamentos.bean.FilialBean;
import com.gestaoequipamentos.bean.GeDocSegOperBean;
import com.gestaoequipamentos.bean.JobCodeBean;
import com.gestaoequipamentos.bean.JobControlBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeComplexidade;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeOsPalm;
import com.gestaoequipamentos.entity.GePecas;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

public class GestaoEquipamentosBusiness {

	private String FILIAL ;
	private static String HQL_FIND_ALL_FILIAIS = "FROM TwFilial ORDER BY stnm";	

	public GestaoEquipamentosBusiness(UsuarioBean bean) {
		FILIAL = bean.getFilial();
	}

	public Collection<FilialBean> findAllFiliais() {
		Collection<FilialBean> listForm = new ArrayList<FilialBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery(HQL_FIND_ALL_FILIAIS);
			List<TwFilial> list = (List<TwFilial>) query.getResultList();
			for (TwFilial twFil : list) {
				FilialBean filialBean = new FilialBean();
				filialBean.fromBean(twFil);
				listForm.add(filialBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	public Collection<JobControlBean> findAllJobControl() {
		Collection<JobControlBean> listForm = new ArrayList<JobControlBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select respar from jobcontrol where respar not in ('SC','MR','OP') order by respar");
			List<String> list = (List<String>) query.getResultList();
			for (String jbctr : list) {
				JobControlBean bean = new JobControlBean();
				bean.setDescricao(jbctr);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	public Collection<JobCodeBean> findAllJobCode(String caracter) {
		Collection<JobCodeBean> listForm = new ArrayList<JobCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select distinct jbcd, jbcdds from jbcd where jbcd not in('923',"+
					"'874',"+
					"'840',"+
					"'825',"+
					"'817',"+
					"'774',"+
					"'770',"+
					"'742',"+
					"'740',"+
					"'738',"+
					"'737',"+
					"'732',"+
					"'730',"+
					"'725',"+
					"'717',"+
					"'571',"+
					"'551',"+
					"'535',"+
					"'527',"+
					"'524',"+
					"'074',"+
					"'074',"+
					"'070',"+
					"'053',"+
					"'037',"+
					"'036',"+
					"'035','023') " +
					" and jbcdds like '"+caracter.toUpperCase()+"%' or jbcd = '"+caracter+"'" +
					" order by jbcd");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				JobCodeBean bean = new JobCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	/**
	 * Recupera um job code específico
	 * @return
	 */
	public Collection<JobCodeBean> findAllJobCode() {
		Collection<JobCodeBean> listForm = new ArrayList<JobCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select distinct jbcd, jbcdds from jbcd where jbcd not in('923',"+
					"'874',"+
					"'840',"+
					"'825',"+
					"'817',"+
					"'774',"+
					"'770',"+
					"'742',"+
					"'740',"+
					"'738',"+
					"'737',"+
					"'732',"+
					"'730',"+
					"'725',"+
					"'717',"+
					"'571',"+
					"'551',"+
					"'535',"+
					"'527',"+
					"'524',"+
					"'074',"+
					"'074',"+
					"'070',"+
					"'053',"+
					"'037',"+
					"'036',"+
			"'035','023') order by jbcd");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				JobCodeBean bean = new JobCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	public Collection<ComplexidadeBean> findAllComplexidade() {
		Collection<ComplexidadeBean> listForm = new ArrayList<ComplexidadeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();;

			Query query = manager.createQuery("FROM GeComplexidade");
			List<GeComplexidade> result = query.getResultList();
			for (GeComplexidade list  : result) {
				ComplexidadeBean bean = new ComplexidadeBean();
				bean.setDescricao(list.getDescricao());
				bean.setFator(String.valueOf(list.getFator()));
				bean.setSigla(list.getSigla());
				bean.setId(list.getId());
				bean.setDescricaoSigla(list.getSigla() + "-" + list.getDescricao());
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}

	public Collection<ComponenteCodeBean> findAllCompCode(String caracter) {
		Collection<ComponenteCodeBean> listForm = new ArrayList<ComponenteCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select cptcd, cptcdd from cptcd where cptcdd like '"+caracter.toUpperCase()+"%' or cptcd = '"+caracter+"'");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				ComponenteCodeBean bean = new ComponenteCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}

	private Boolean saveNomeClienteNovoSerieModelo(String cliente, int IdosPalm, String numeroSerie, String modeloMaquina){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeOsPalm where idOs_Palm =:IdosPalm");
			query.setParameter("IdosPalm", IdosPalm);
			GeOsPalm palm = (GeOsPalm)query.getSingleResult();	
			query = manager.createQuery("From GeArvInspecao where descricao =:descricao");
			query.setParameter("descricao", modeloMaquina);
			GeArvInspecao arvInspecao = (GeArvInspecao)query.getSingleResult();
			palm.setCliente(cliente.toUpperCase());	
			palm.setSerie(numeroSerie);
			palm.setModelo(modeloMaquina);
			palm.setEquipamento(modeloMaquina);
			palm.setIdFamilia(arvInspecao.getIdFamilia());
			manager.merge(palm);
			manager.getTransaction().commit();			

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;

	}


	public Boolean validarNumSerie(String numSerie, String cliente,  int IdosPalm, String modeloMaquina){

		ResultSet rs = null;
		PreparedStatement prstmt_ = null;

		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();

		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  
			String SQL = "select * from "+IConstantAccess.LIB_DBS+".EMPATCH0 f where trim(f.ATSLN1) = '"+numSerie+"'";
			
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				saveNomeClienteNovoSerieModelo(cliente, IdosPalm, numSerie, modeloMaquina);
				return true;
			}			
			
			SQL = "select * from "+IConstantAccess.LIB_DBS+".empeqpd0 f where (trim(f.eqmfs2) = '"+numSerie+"' or trim(f.RDMSR1) = '"+numSerie+"')";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				saveNomeClienteNovoSerieModelo(cliente, IdosPalm, numSerie, modeloMaquina);
				return true;
			}
			SQL = "select * from "+IConstantAccess.LIB_DBS+".EMPORDH0 where eqmfs2 = '"+numSerie+"'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				saveNomeClienteNovoSerieModelo(cliente, IdosPalm, numSerie, modeloMaquina);
				return true;
			}
			return false;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(con != null){
					con.close();
					rs.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public Boolean validarNumSerieAprovarOs(String numSerie){

		ResultSet rs = null;
		PreparedStatement prstmt_ = null;

		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();

		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  
			String SQL = "select * from "+IConstantAccess.LIB_DBS+".EMPATCH0 f where trim(f.ATSLN1) = '"+numSerie+"'";
			
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				return true;
			}			
			
			SQL = "select * from "+IConstantAccess.LIB_DBS+".empeqpd0 f where (trim(f.eqmfs2) = '"+numSerie+"' or trim(f.RDMSR1) = '"+numSerie+"')";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				return true;
			}
			SQL = "select * from "+IConstantAccess.LIB_DBS+".EMPORDH0 where eqmfs2 = '"+numSerie+"'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				return true;
			}
			return false;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(con != null){
					con.close();
					rs.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public Boolean validarNumSerieSegmento(String numSerie){ //Método não usado

		//ResultSet rs = null;
		//PreparedStatement prstmt_ = null;

		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();

		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			
			//TODO: Descomentar Código após concluir telas da entrada da OS.
			//TODO: Inicio
//			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  
//			String SQL = "select * from "+IConstantAccess.LIB_DBS+".empeqpd0 f where trim(f.eqmfs2) = '"+numSerie.trim()+"'";
//			prstmt_ = con.prepareStatement(SQL);
//			rs = prstmt_.executeQuery();
//			if(rs.next()){
//				return true;
//			}
//			return false;
			//TODO: Fim
			
			return true; // RETIRAR RETORNO APÓS CONCLUSÃO DAS TELAS
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(con != null && con.isClosed() == false){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean verificarModelo(String modelo, String numSerie){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "";
			if(numSerie.equals("OSINTER")){
				SQL = "select count(*) from ge_prefixo t, ge_arv_inspecao arv"+
				" where t.id_modelo = arv.id_arv"+
				" and arv.descricao = '"+modelo+"'"+
				" and t.descricao = '"+numSerie+"'";
			}else{
				SQL = "select count(*) from ge_prefixo t, ge_arv_inspecao arv"+
				" where t.id_modelo = arv.id_arv"+
				" and arv.descricao = '"+modelo+"'"+
				" and t.descricao = SUBSTRING('"+numSerie+"', 1,4)";
			}
			Query query = manager.createNativeQuery(SQL);			
			Integer result = (Integer)query.getSingleResult();
			if(result > 0){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}

	public ChekinBean saveOrcamento(ChekinBean bean){
		EntityManager manager = null;
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		Connection con = null;
		Statement prstmt = null;
		try {
			manager = JpaUtil.getInstance();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			prstmt = con.createStatement();
//			Query query = manager.createNativeQuery ("select t.id_doc_seg_oper, seg.numero_segmento, op.num_operacao, ch.numero_os, oper.num_doc, oper.msg_dbs from ge_doc_seg_oper oper,ge_segmento seg, ge_check_in ch,ge_operacao op right join"+
//					"(select dso.id_segmento, dso.id_operacao , max(dso.id) id_doc_seg_oper"+
//					" from ge_doc_seg_oper dso"+
//					" where dso.id_segmento in(select seg.id from ge_segmento seg where seg.id_checkin =:id)"+
//					" group by dso.id_segmento, dso.id_operacao) t  on  t.id_operacao = op.id"+
//					" where t.id_segmento = seg.id"+
//			" and seg.id_checkin = ch.id" +
//			" and oper.id = t.id_doc_seg_oper"); 		
			Query query = manager.createNativeQuery ("select oper.id, seg.numero_segmento, ch.numero_os, oper.num_doc, oper.msg_dbs"+ 
								" from ge_doc_seg_oper oper,ge_segmento seg, ge_check_in ch"+
								" where seg.id_checkin = ch.id"+
								" and seg.id_checkin =:id"+
								" and oper.ID_SEGMENTO = seg.ID"); 		

			query.setParameter("id", bean.getId());
			List<Object[]> result = query.getResultList();
			if(result.isEmpty()){
				bean.setHasSendDbs("N");
				return bean;
			}
			for (Object[] obj : result){
				if(obj[3] == null && obj[4] == null){
					String id_doc_seg_oper = ((BigDecimal)obj[0]).toString();
					manager.getTransaction().begin();
					GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, Long.valueOf(id_doc_seg_oper));
					String idFuncionario = docSegOper.getIdFuncionarioPecas();
					String nmec = "";
					if(idFuncionario != null && idFuncionario.length() > 0){
						TwFuncionario funcionario = manager.find(TwFuncionario.class, idFuncionario);
						if(funcionario.getLogin().length() <= 4){
							nmec = funcionario.getLogin().toUpperCase();
						}
					}
					
					docSegOper.setMsgDbs("Peças enviadas para o DBS, aguarde 2 minutos para o retorno do documento!");
					manager.getTransaction().commit();					
					String numero_segmento = (String)obj[1];
					//String num_operacao = ((String)obj[2] == null)?"":(String)obj[2];
					String num_os = (String)obj[2];
					query = manager.createNativeQuery("select PART_NO, SOS1, SUM(qtd) from Ge_Pecas where ID_DOC_SEG_OPER =:id_doc_seg_oper group by PART_NO, SOS1");
					query.setParameter("id_doc_seg_oper", Long.valueOf(id_doc_seg_oper));
					List<Object[]> list = query.getResultList();
					prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
					try {
						for (Object[] pecas : list) {
							String partNumber = ((String)pecas[0]).replace("'", "");
							if(!((String)pecas[1]).equals("221")){
								partNumber = ((String)pecas[0]).replace("-", "").replace("'", "");	
							}
							String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('O-"+id_doc_seg_oper+"', '"+(((String)pecas[1] == null)?"000":(String)pecas[1])+"', '"+partNumber+"', '"+(BigDecimal)pecas[2]+"')";
							prstmt.executeUpdate(SQL);
						}
						String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 (PEDSM, WONOSM, SGNOSM, OPERSM, NMEC) values('O-"+id_doc_seg_oper+"', '"+num_os+"', '"+numero_segmento+"', '','"+nmec+"')";
						prstmt.executeUpdate(SQL);
						manager.getTransaction().begin();
						query = manager.createNativeQuery("delete from GE_PECAS_00E where ID_DOC_SEG_OPER ="+id_doc_seg_oper);
						query.executeUpdate();
						query = manager.createNativeQuery("insert into GE_PECAS_00E select * from GE_PECAS where ID_DOC_SEG_OPER ="+id_doc_seg_oper);
						query.executeUpdate();
						manager.getTransaction().commit();
						
					} catch (Exception e) {
						prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
						prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
						e.printStackTrace();
						bean.setHasSendDbs("N");
						return bean;
					}
				}
			}	
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getId());
			checkIn.setHasSendDbs("S");
			checkIn.setHasSendPecasDbs("S");
			manager.getTransaction().commit();
			bean.setHasSendDbs("S");
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			bean.setHasSendDbs("N");
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
		//return false;				
//		bean.setHasSendDbs("S");
//		Query q = manager.createQuery("FROM GeCheckIn where numeroOs =:numeroOs"); 
//		q.setParameter("numeroOs", bean.getNumeroOs());
//		try {
//			manager.getTransaction().begin();	
//			GeCheckIn checkIn = (GeCheckIn) q.getSingleResult();
//			checkIn.setHasSendDbs("S");
//			manager.merge(checkIn);
//			manager.getTransaction().commit();	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return bean;
	}
	public GeDocSegOperBean saveSegundoOrcamento(GeDocSegOperBean bean){

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		Connection con = null;
		Statement prstmt = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
//			con = DriverManager.getConnection(url, user, password);
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			prstmt = con.createStatement();
			con.setAutoCommit(true);
			//				
			Query query = manager.createNativeQuery ("select doc.id, seg.numero_segmento, op.num_operacao, ch.numero_os"+
					" from ge_segmento seg,ge_operacao op right join  ge_doc_seg_oper doc on doc.id_operacao = op.id , ge_check_in ch "+
					" where doc.id_segmento = seg.id"+
					//" and doc.id_operacao = op.id"+
					" and ch.id = seg.id_checkin"+
			" and doc.id =:id");
			query.setParameter("id", bean.getId());
			//query.setParameter("id", bean.getId());
			List<Object[]> result = query.getResultList();
			for (Object[] obj : result){
				String id_doc_seg_oper = ((BigDecimal)obj[0]).toString();
				manager.getTransaction().begin();
				GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, Long.valueOf(id_doc_seg_oper));
				String idFuncionario = docSegOper.getIdFuncionarioPecas();
				String nmec = "";
				if(idFuncionario != null && idFuncionario.length() > 0){
					TwFuncionario funcionario = manager.find(TwFuncionario.class, idFuncionario);
					if(funcionario.getLogin().length() <= 4){
						nmec = funcionario.getLogin().toUpperCase();
					}
				}
				docSegOper.setMsgDbs("Peças enviadas para o DBS, aguarde 2 minutos para o retorno do documento!");
				docSegOper.setCodErroDbs(null);
				manager.getTransaction().commit();
				String numero_segmento = (String)obj[1];
				String num_operacao = ((String)obj[2] == null)?"":(String)obj[2];
				String num_os = (String)obj[3];
				//query = manager.createQuery("from GePecas p where p.idDocSegOper.id =:id ");
				query = manager.createNativeQuery("select PART_NO, SOS1, SUM(qtd) from Ge_Pecas where ID_DOC_SEG_OPER =:id_doc_seg_oper group by PART_NO, SOS1");
				query.setParameter("id_doc_seg_oper", Long.valueOf(id_doc_seg_oper));
				List<Object[]> list = query.getResultList();
				prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
				try {
					for (Object[] pecas : list) {
						String partNumber = ((String)pecas[0]).replace("'", "");
						if(!((String)pecas[1]).equals("221")){
							partNumber = ((String)pecas[0]).replace("-", "").replace("'", "");	
						}
						String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('O-"+id_doc_seg_oper+"', '"+(((String)pecas[1] == null)?"000":(String)pecas[1])+"', '"+partNumber+"', '"+(BigDecimal)pecas[2]+"')";
						prstmt.executeUpdate(SQL);
					}
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 (PEDSM, WONOSM, SGNOSM, OPERSM, NMEC) values('O-"+id_doc_seg_oper+"', '"+num_os+"', '"+numero_segmento+"', '"+num_operacao+"','"+nmec+"')";
					prstmt.executeUpdate(SQL);
					manager.getTransaction().begin();
					query = manager.createNativeQuery("delete from GE_PECAS_00E where ID_DOC_SEG_OPER ="+id_doc_seg_oper);
					query.executeUpdate();
					query = manager.createNativeQuery("insert into GE_PECAS_00E select * from GE_PECAS where ID_DOC_SEG_OPER ="+id_doc_seg_oper);
					query.executeUpdate();
					manager.getTransaction().commit();
				} catch (Exception e) {
					if(manager.getTransaction().isActive()){
						manager.getTransaction().rollback();
					}
					prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
					prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where PEDSM = 'O-"+id_doc_seg_oper+"'");
					manager.getTransaction().begin();
					docSegOper.setMsgDbs("Erro ao tentar enviar peças para o DBS!");
					docSegOper.setCodErroDbs("99");
					manager.getTransaction().commit();
					e.printStackTrace();
					return null;
				}
				
			}	
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
			//bean.setHasSendDbs("N");
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
		return bean;
	}

//	public void sendPecasDbs(List<PecasDbsBean> list, ChekinBean bean){
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
//		Connection con = null;
//		Statement prstmt = null;
//		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
//
//
//			//con = DriverManager.getConnection(url, user, password);
//			con = DriverManager.getConnection(url, user, password);
//			prstmt = con.createStatement();
//			//inserir detales
//			for (PecasDbsBean pecasDbsBean : list) {
//				String SQL = "insert into LIBU15FTP.USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('GE-"+bean.getId()+"', '"+pecasDbsBean.getSos()+"', '"+pecasDbsBean.getNumPeca()+"', '"+pecasDbsBean.getQtd()+"')";
//				prstmt.executeUpdate(SQL);
//			}
//			//inserir cabeçalho
//			String SQL = "insert into LIBU15FTP.USPPHSM0 (PEDSM, STNSM, CUNOSM) values('GE-"+bean.getId()+"', '"+bean.getFilial()+"', '"+bean.getCodCliente()+"')";
//			prstmt.executeUpdate(SQL);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				prstmt.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}



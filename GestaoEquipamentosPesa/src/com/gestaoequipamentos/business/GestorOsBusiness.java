package com.gestaoequipamentos.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.GestorOSBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

public class GestorOsBusiness {
	private UsuarioBean bean;
	private SimpleDateFormat fmt = new SimpleDateFormat("dMMyyyy");

	public GestorOsBusiness(UsuarioBean bean){
		this.bean = bean;
	}

	public List<GestorOSBean> findAllOSGestor (){
		List<GestorOSBean> list = new ArrayList<GestorOSBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select palm.cliente,palm.modelo, palm.serie,chin.numero_os,t.stnm,chin.cod_cliente,chin.id  "+
					" from ge_check_in chin, ge_os_palm palm, ge_situacao_os so, tw_filial t"+
					" where chin.centro_custo_sigla = (select cc.sigla from adm_perfil_sistema_usuario t, pmp_centro_de_custo cc"+
					" where cc.id = t.id_centro_custo "+
					" and t.id_tw_usuario = '"+bean.getMatricula()+"'"+ ")"+
					" and substr(chin.cliente_inter, 2,2) = '"+bean.getFilial()+"'" +
					" and palm.idos_palm = chin.id_os_palm"+
					" and so.numero_os = chin.numero_os"+
					" and so.data_negociacao is not null"+								
					" and so.data_aprovacao is null"+
					" and chin.tipo_cliente = 'INT'"+ 
			" and t.stno = palm.filial");
			List<Object[]> result = query.getResultList();
			for(Object[] objects : result){
				GestorOSBean bean = new GestorOSBean();
				bean.setCliente(objects[0].toString());
				bean.setModelo(objects[1].toString());
				bean.setSerie(objects[2].toString());
				bean.setNumOS(objects[3].toString());				
				bean.setFilial(objects[4].toString());
				bean.setCodCliente(objects[5].toString());
				bean.setIdCheckin(objects[6].toString());
				list.add(bean);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return list;		
	}
	public List<GestorOSBean> findAllOSGarantiaGestor(){
		List<GestorOSBean> list = new ArrayList<GestorOSBean>();
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select palm.cliente,palm.modelo, palm.serie,chin.numero_os,t.stnm,chin.cod_cliente,chin.id"+  
					 " from ge_check_in chin, ge_os_palm palm, ge_situacao_os so, tw_filial t"+
					" where  palm.filial = "+Integer.valueOf(bean.getFilial())+
					"  and palm.idos_palm = chin.id_os_palm"+
					"  and so.numero_os = chin.numero_os"+
					"  and so.data_negociacao is not null"+
					"  and so.data_aprovacao is null"+
					"  and chin.sigla_indicador_garantia is not null"+ 
					" and t.stno = palm.filial");
			List<Object[]> result = query.getResultList();
			for(Object[] objects : result){
				GestorOSBean bean = new GestorOSBean();
				bean.setCliente(objects[0].toString());
				bean.setModelo(objects[1].toString());
				bean.setSerie(objects[2].toString());
				bean.setNumOS(objects[3].toString());				
				bean.setFilial(objects[4].toString());
				bean.setCodCliente(objects[5].toString());
				bean.setIdCheckin(objects[6].toString());
				list.add(bean);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return list;		
	}
	public Boolean liberarOS(String numOS){
		PreparedStatement prstmt_ = null;
		Connection con = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeSituacaoOs where numeroOs =:numOS");
			query.setParameter("numOS", numOS);
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();			
			situacaoOs.setDataAprovacao(new Date());
			query = manager.createQuery("From GeCheckIn where numeroOs =:numOS");
			query.setParameter("numOS", numOS);
			GeCheckIn checkIn = (GeCheckIn) query.getSingleResult();
			checkIn.setHasSendDataAprovacao("S");
			manager.merge(checkIn);
			//Envia datas de negociação e aprovação para o DBS
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  
			//recupera o fator do segmento o cliente do DBS
			String SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"' where WONO ='"+numOS+"'";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.execute();			
			manager.merge(situacaoOs);						
			manager.getTransaction().commit();
			//this.saveDataOrcamento (bean, false);//manda false para não alterar a data de orçamento e recupera as peças do dbs para enviar para o crm
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
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public Boolean rejeitarOS(String tipoRejeicao, String obs, String numOS){
		PreparedStatement prstmt_ = null;
		Connection con = null;
		EntityManager manager = null;
		String SQL = "";
		try {
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeSituacaoOs where numeroOs =:numOS");
			query.setParameter("numOS", numOS);
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();
			//Envia todas as datas de para o DBS
			if(tipoRejeicao.equals("TOTAL")){
//				InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//				Properties prop = new Properties();
//				prop.load(in);
//				String url = prop.getProperty("dbs.url");
//				String user = prop.getProperty("dbs.user");
//				String password =prop.getProperty("dbs.password");
//				Class.forName(prop.getProperty("dbs.driver")).newInstance(); 
				con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
				situacaoOs.setDataOrcamento(new Date());
				situacaoOs.setDataEntregaPedidos(new Date());
				situacaoOs.setDataAprovacao(new Date());
				situacaoOs.setDataPrevisaoEntrega(new Date());
				situacaoOs.setDataTerminoServico(new Date());
				situacaoOs.setDataFaturamento(new Date());		
				situacaoOs.setTipoRejeicao(tipoRejeicao);
				situacaoOs.setObs(obs);
				SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTOC = '"+fmt.format(situacaoOs.getDataOrcamento())+"',DTNG = '"+fmt.format(situacaoOs.getDataEntregaPedidos())+"', DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"',DTEX = '"+fmt.format(situacaoOs.getDataPrevisaoEntrega())+"',DTCO = '"+fmt.format(situacaoOs.getDataPrevisaoEntrega())+"',DTFC = '"+fmt.format(situacaoOs.getDataFaturamento())+"' where WONO ='"+numOS+"'";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.execute();
			}else{
				situacaoOs.setDataEntregaPedidos(null);
				situacaoOs.setTipoRejeicao(tipoRejeicao);
				situacaoOs.setObs(obs);
			}			
			manager.merge(situacaoOs);						
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
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}



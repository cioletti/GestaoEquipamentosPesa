package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeSituacaoOs;

public class JobGestorOS  implements Job{
	private SimpleDateFormat fmt = new SimpleDateFormat("dMMyyyy");

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PreparedStatement prstmt_ = null;
		Connection con = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("select (sysdate - to_date(to_char(so.data_negociacao, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS'))*24 horas, so.numero_os "+
					" from GE_SITUACAO_OS so, GE_CHECK_IN ch "+
					" where so.id_checkin = ch.id" + 
					" and ch.tipo_cliente = 'INT'"+
					" and so.data_aprovacao is null"+
					" and so.data_negociacao is not null"+
					" and so.data_faturamento is null");
			List<Object[]> result = query.getResultList();
			for (Object[] object : result ){
				if(Double.valueOf(object[0].toString()) > 48){
					query = manager.createQuery("From GeSituacaoOs where numeroOs =:numOS");
					query.setParameter("numOS", object[1].toString());
					GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();			
					situacaoOs.setDataAprovacao(new Date());
					query = manager.createQuery("From GeCheckIn where numeroOs =:numOS");
					query.setParameter("numOS", object[1].toString());
					GeCheckIn checkIn = (GeCheckIn) query.getSingleResult();
					checkIn.setHasSendDataAprovacao("S");
					manager.merge(checkIn);
//					InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//					Properties prop = new Properties();
//					prop.load(in);
//					String url = prop.getProperty("dbs.url");
//					String user = prop.getProperty("dbs.user");
//					String password =prop.getProperty("dbs.password");
//					Class.forName(prop.getProperty("dbs.driver")).newInstance();
					con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
					//recupera o fator do segmento o cliente do DBS
					String SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"' where WONO ='"+object[1].toString()+"'";
					prstmt_ = con.prepareStatement(SQL);
					prstmt_.execute();			
					manager.merge(situacaoOs);						
					manager.getTransaction().commit();
				}
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
			try {
				if(con != null){
					con.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}


}

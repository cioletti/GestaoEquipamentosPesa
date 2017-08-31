package com.gestaoequipamentos.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobVerificaSegmentoDBS implements Job {
	//Varre o DBS a cada 30 min. e compara com a nossa base e retira os segmentos diferentes(da nossa base)
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PreparedStatement prstmt_ = null;
		Connection con = null;
		ResultSet rs = null;
		EntityManager manager = null;
//		String WONO = "";
		try {
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			manager = JpaUtil.getInstance();	
			//manager.getTransaction().begin();
			Query query = manager.createNativeQuery("SELECT ch.numero_os FROM ge_check_in ch"+
					" WHERE ch.id IN(SELECT os.Id_Checkin  FROM ge_situacao_os os"+
					" WHERE os.data_aprovacao IS NULL)"+
					" AND CH.NUMERO_OS IS NOT NULL");
			List<String> list = query.getResultList();
			for(String os: list){
				//List<String > WOSGNO = new ArrayList<String>();
				String WONO = "";
				String WOSGNO = "";
				String SQL = ("SELECT distinct WOPSEGS0.WONO, WOPSEGS0.WOSGNO, WOPSEGS0.JBCD,WOPSEGS0.CPTCD, WOPSEGS0.FRSTHR, WOPSEGS0.CSCC, WOPSEGS0.QTY4"+
						" FROM B108F034."+IConstantAccess.LIB_DBS+".WOPHDRS0 WOPHDRS0,B108F034."+IConstantAccess.LIB_DBS+".WOPSEGS0 WOPSEGS0"+
						" WHERE  WOPSEGS0.WONO = '"+os+"'");
				prstmt_ = con.prepareStatement(SQL);
				rs = prstmt_.executeQuery();
				while(rs.next()){					
					WONO = rs.getString("WONO").trim();
					WOSGNO = WOSGNO+"'"+rs.getString("WOSGNO")+"',";
				}
				if(WOSGNO.length() > 0){
					manager.getTransaction().begin();	
					query = manager.createNativeQuery("DELETE FROM ge_segmento seg"+
							" WHERE seg.id_checkin = (SELECT ch.id FROM ge_check_in ch"+
							" WHERE ch.numero_os = '"+WONO+"')"+
							" AND seg.numero_segmento NOT IN ("+WOSGNO.substring(0, (WOSGNO.length()-1))+")");
					query.executeUpdate();
					manager.getTransaction().commit();
				}
				con.commit();
			}

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
					con.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

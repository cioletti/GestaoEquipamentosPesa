package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.business.SegmentoBusiness;
import com.gestaoequipamentos.entity.GeCheckIn;

public class JobOsOpenDbs implements Job {//Recuperar as peças do DBS
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		
        Connection con = null;
        Statement prstmt = null;
        EntityManager manager = null;
        ResultSet rs = null;
        try {
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	manager = JpaUtil.getInstance();
//        	Query query = manager.createQuery("from GeCheckIn where isOpenOs = 'N' and numeroOs is not null");
        	Query query = manager.createQuery("from GeCheckIn where (dataAbertura is null or isEncerrada is null) and numeroOs is not null");
        	List<GeCheckIn> result = query.getResultList();
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        	for (GeCheckIn checkIn : result) {
        		//Verifica se a OS está OPEN
        		String SQL = "select TRIM(w.ACTI) ACTI, TRIM(w.OPNDT8) OPNDT8 from "+IConstantAccess.LIB_DBS+".WOPHDRS0 w  where TRIM(w.WONO) = '"+checkIn.getNumeroOs()+"'"; 
        		rs = prstmt.executeQuery(SQL);
        		if(rs.next()){
        			String ACTI = rs.getString("ACTI");
        			String OPNDT8 = rs.getString("OPNDT8");
        			if(!ACTI.equals("E")){
        				manager.getTransaction().begin();
        				checkIn.setIsOpenOs("S");
        				if(OPNDT8 != null && !OPNDT8.equals("")){
        					checkIn.setDataAbertura(dateFormat.parse(OPNDT8));
        				}
        				if(ACTI.equals("I")){
        					checkIn.setIsEncerrada("S");
        					SegmentoBusiness business = new SegmentoBusiness(null);
        					List<SegmentoBean> segList = business.findAllSegmento(checkIn.getId());
        					for (SegmentoBean segmentoBean : segList) {
        						business.alterarFluxoProcessoOficina(segmentoBean.getIdCheckin(), "E", null);
        					}
        				}
        				manager.merge(checkIn);
        				manager.getTransaction().commit();
        			}
        		}
        	}
        } catch (Exception e) {
        	new EmailHelper().sendSimpleMail("ERRO OPEN OS GE", "ERRO OPEN OS GE", "rodrigo@rdrsistemas.com.br");
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
        			rs.close();
        			prstmt.close();
        		}
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}if(manager != null && manager.isOpen()){
        		manager.close();
        	}        	
        }    
		
	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}



package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.business.ChekinBusiness;
import com.gestaoequipamentos.controller.PropostaManutencao;
import com.gestaoequipamentos.entity.GeCheckIn;

public class JobObsNotesDbs implements Job {//Recuperar as peças do DBS
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		
        Connection conDbs = null;
        Statement prstmt = null;
        EntityManager manager = null;
      //  ResultSet rs = null;
        try {
        	conDbs = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = conDbs.createStatement();
        	manager = JpaUtil.getInstance();

        	Query query = manager.createQuery("from GeCheckIn where isEncerrada is null and obsProposta is not null and codErroDbs = '00'");
        	List<GeCheckIn> list = query.getResultList();
        	for (GeCheckIn checkIn : list) {

        		prstmt = new PropostaManutencao().sendNotesObsOsDbs(null, conDbs, prstmt, checkIn);
        	}
        } catch (Exception e) {
        	new EmailHelper().sendSimpleMail("ERRO NOTES OS GE", "ERRO NOTES OS GE", "rodrigo@rdrsistemas.com.br");
        	if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
        	e.printStackTrace();
        	
        }finally{
        	try {
        		if(manager != null && manager.isOpen()){
        			manager.close();
        		}  
        		if(conDbs != null){
        			conDbs.close();
        			//rs.close();
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



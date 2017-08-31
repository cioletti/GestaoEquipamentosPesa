package com.gestaoequipamentos.util;

import java.math.BigDecimal;
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

import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GeSegmento;

public class JobOperacaoDbs implements Job {
	
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		
        Connection con = null;
        Statement prstmt = null;
       
        EntityManager manager = null;
        ResultSet rs = null;
        
        try {
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	
        	manager = JpaUtil.getInstance();
        	Query query = manager.createNativeQuery(" select ID from GE_OPERACAO o where (o.IS_CREATE_OPERACAO = 'N' OR o.IS_CREATE_OPERACAO is null) and (o.Cod_Erro_Dbs <> '99' or o.Cod_Erro_Dbs is null)");
        	List<BigDecimal> idList = (List<BigDecimal>)query.getResultList();
        	String pair = "";
        	for (BigDecimal bigDecimal : idList) {
				pair += "'"+bigDecimal+"-O',";
			}
        	pair = (pair.length() > 0)?pair.substring(0, pair.length()-1):"''";
        	String SQL = "select TRIM(DESCERR) DESCERR, TRIM(CODERR) CODERR, TRIM(WONOSM) WONOSM from "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 where LENGTH(TRIM(CODERR)) > 0 and TRIM(WONOSM) in("+pair+")";
        	
        	
        	rs = prstmt.executeQuery(SQL);
        	while (rs.next()){		            	 
        		String CODERR = rs.getString("CODERR").trim();				
				
        		String DESCERR = rs.getString("DESCERR").trim();
        		String [] aux = rs.getString("WONOSM").trim().split("-");
        		if(aux.length > 1){
        			String PEDSM = aux[0];
        			manager.getTransaction().begin();
        			GeOperacao geOperacao = manager.find(GeOperacao.class, Long.valueOf(PEDSM));
        			if(geOperacao != null){
        				if(CODERR.equals("00")){
        					geOperacao.setMsgDbs("Operação Criada com sucesso!");
        					geOperacao.setIsCreateOperacao("S");
        					geOperacao.setCodErroDbs("00");
        				}else{
        					geOperacao.setIsCreateOperacao("N");
        					geOperacao.setCodErroDbs(CODERR);
        					geOperacao.setMsgDbs(DESCERR);
        				}
        			} else {
        				continue;
        			}
        			
       				manager.getTransaction().commit();
        		}
        	}
        	
        } catch (Exception e) {
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
			}
        	
        }
		
	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}



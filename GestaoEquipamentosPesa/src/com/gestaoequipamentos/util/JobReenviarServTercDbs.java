package com.gestaoequipamentos.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobReenviarServTercDbs implements Job {//Recuperar as peças do DBS
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		
        Connection conDbs = null;
        Statement prstmt = null; 
        Statement stmtInsert = null; 
        Statement stmtDelete = null; 
        ResultSet rs = null;
        try {
        	conDbs = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = conDbs.createStatement();
        	stmtInsert = conDbs.createStatement();
        	stmtDelete = conDbs.createStatement();
        	rs = prstmt.executeQuery("select st.WONO, st.WOSGNO, st.SEQ, st.CHGCD, st.DOCDAT, st.QTY, st.UNCS, st.UNSL, st.DESC from LIBU17FTP.USPMSSM0 st where descerr = 'ERRO NA FUNCAO MNTWOMSC                           '");
        	while(rs.next()){
        			String pair = "'"+rs.getString("WONO").trim()+"','"+rs.getString("WOSGNO").trim()+"','"+rs.getString("SEQ").trim()+"','"+rs.getString("CHGCD")+"','"+rs.getString("DOCDAT")+"','"+rs.getString("QTY")+"','"+rs.getString("UNCS")+"','"+rs.getString("UNSL")+"','"+rs.getString("DESC")+"'";
        			String insert = "insert into LIBU17FTP.USPMSSM0 (wono, wosgno, seq, chgcd, docdat, qty, uncs, unsl, desc) values("+pair+")";	
        			String delete = "delete from LIBU17FTP.USPMSSM0 where WONO = '"+rs.getString("WONO").trim()+"' and wosgno = '"+rs.getString("WOSGNO")+"' and SEQ = '"+rs.getString("SEQ").trim()+"'";
        			stmtDelete.executeUpdate(delete);
        			stmtInsert.executeUpdate(insert);
        	}
        	
        } catch (Exception e) {
        	StringWriter sw = new StringWriter();
        	PrintWriter pw = new PrintWriter(sw);
        	e.printStackTrace(pw);
        	new EmailHelper().sendSimpleMail("ERRO SERVIÇO DE TERCEIROS "+pw.toString(), "ERRO NOTES OS GE", "rodrigo@rdrsistemas.com.br");
        	e.printStackTrace();
        	
        }finally{
        	try {
        		if(conDbs != null){
        			conDbs.close();
        			if(prstmt != null){
        				prstmt.close();
        			}
        			if(stmtInsert != null){
        				stmtInsert.close();
        			}
        			if(stmtDelete != null){
        				stmtDelete.close();
        			}
        		}
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}       	
        }    
		
	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}



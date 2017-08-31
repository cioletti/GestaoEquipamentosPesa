package com.gestaoequipamentos.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SendEmailHelper {
	/**
	 * Envia e-mail para o supervisor que possui JobControl associado relatando que a OS foi criada!
	 * @param manager
	 * @param jobcontrol
	 * @param filial
	 * @param numOS
	 */
	public static void sendEmailSupervisorJobControlAssociados(EntityManager manager, String jobcontrol, String filial, String numOS){ 
		try {
			
			if(jobcontrol == null){
				jobcontrol = "''";
			}
			
			Query query = manager.createNativeQuery("select distinct f2.EMAIL,f2.EPLSNM from ADM_PERFIL_SISTEMA_USUARIO psu2, TW_FUNCIONARIO f2, ADM_JOB_CONTROL jc2,"+
							"(select f.EPIDNO, psu.JOB_CONTROL from"+ 
							" TW_FUNCIONARIO f,"+ 
							" ADM_PERFIL_SISTEMA_USUARIO psu,"+
							" ADM_JOB_CONTROL jc"+
							" where f.EPIDNO = psu.ID_TW_USUARIO"+
							" and psu.ID = jc.ID_ADM_PERFIL_SISTEMA_USUARIO"+
							" and psu.JOB_CONTROL in ("+jobcontrol+")) as func"+
							" where jc2.ID_JOB_CONTROL <> func.JOB_CONTROL"+
							" and f2.EPIDNO = psu2.ID_TW_USUARIO"+
							" and psu2.ID = jc2.ID_ADM_PERFIL_SISTEMA_USUARIO"+
							" and func.EPIDNO = psu2.ID_TW_USUARIO"+
							" and f2.STN1 = '"+Integer.valueOf(filial).toString()+"'");
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi liberada!", "Ordem de Serviço Sistema Oficina", (String)pair[0]);		
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia e-mail para o orçamentista quando digitador liberar a OS.
	 * @param manager
	 */	
	public static void sendEmailOrcamentista(EntityManager manager, String numOS, String filial){
		try {
			Query query = manager.createNativeQuery("select * from GE_CHECK_IN c, GE_SEGMENTO seg, GE_SEGMENTO_SERV_TERCEIROS ter "+
					"	where c.ID = seg.ID_CHECKIN "+
					"	and seg.ID = ter.ID_SEGMENTO "+
					"	and c.NUMERO_OS = '"+numOS+"'"); 
			String msgComplemento = "";
			if(query.getResultList().size() > 0){
				msgComplemento = "E possui serviços de terceiros.";
			}
			 query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND tw.STN1 = "+filial+
					  " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'ORC' and tipo_sistema = 'GE')"); 
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi liberada para orçamento! "+msgComplemento, "Ordem de Serviço Sistema Oficina", (String)pair[0]);		
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
	
}

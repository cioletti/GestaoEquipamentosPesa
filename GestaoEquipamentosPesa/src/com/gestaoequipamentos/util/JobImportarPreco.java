package com.gestaoequipamentos.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeComplexidade;
import com.gestaoequipamentos.entity.GePreco;
import com.gestaoequipamentos.entity.GePrefixo;

public class JobImportarPreco implements Job {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GePrefixo");
			List<GePrefixo> lisPre = query.getResultList();
			for (GePrefixo gePrefixo : lisPre) {
				query = manager.createNativeQuery("select * from GE_PRECO where ID_MODELO = "+gePrefixo.getIdModelo().getIdArv()+" and ID_PREFIXO = "+gePrefixo.getId()+" and JOB_CODE = '029' and COMP_CODE = '9042'");
				if(query.getResultList().size() == 0){
					manager.getTransaction().begin();
					GePreco preco = new GePreco();
					preco.setIdComplexidade(manager.find(GeComplexidade.class, 2L));
					preco.setIdModelo(gePrefixo.getIdModelo());
					preco.setIdPrefixo(gePrefixo);
					preco.setJobCode("029");
					preco.setCompCode("9042");
					preco.setQtdHoras("0.00");
					preco.setDescricaoCompCode("DIVERSOS");
					preco.setDescricaoJobCode("MISCELANEOUS");
					preco.setDescricaoServico("- SERVIÇO DE TERCEIROS");
					manager.persist(preco);
					manager.getTransaction().commit();
				}
			}
			
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	
	/**
	 * Envia e-mail para supervisor do JobContol da OS ao ser criado uma nova Ordem de Serviço.
	 * @param manager
	 */	
	private void sendEmailSupervisor(EntityManager manager, String jobcontrol, String filial, String numOS){
		try {
			
			if(jobcontrol == null){
				jobcontrol = "''";
			}
			
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					 " WHERE tw.epidno = perfil.id_tw_usuario"+
					 " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'SUPER' and tipo_sistema = 'GE')"+
					 " AND tw.STN1 = '"+Integer.valueOf(filial).toString()+"'"+
					 " AND perfil.JOB_CONTROL in ("+jobcontrol+")");

			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi liberada!", "Ordem de Serviço", (String)pair[0]);		
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		System.out.println("00XX246".substring(4, "00XX246".length()).equals("246"));
	}
	
//	public static void main(String[] args) {
//		String data = "../../.... ...... (SAIDA MATERIAL E N.FISCAL)     ";
//		  
//
//		
//		String dataAux = data.replace("../../....", dateFormat.format(new Date()));
//		
//		System.out.println(dataAux);
//	}
		
	}



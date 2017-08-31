package com.gestaoequipamentos.util;

import java.text.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Application Lifecycle Listener implementation class BusinessGroupListener
 *
 */
public class BusinessGroupListener implements ServletContextListener {
	 //private static Scheduler sched;
	 private static Scheduler schedJobObsNotesDbs;
	 private static Scheduler schedRecuperarSegmentoDbs;
	 //private static Scheduler schedRecuperarSegmentoCrmDbs;
	 private static Scheduler schedRecuperarOsDbs;
	 private static Scheduler schedRecuperarOsCrmDbs;
	 //private static Scheduler schedRecuperarOperacaoDbs;
	 private static Scheduler schedJobVerificaDBS;
	 private static Scheduler schedJobSubTributariaDBS;
	// private static Scheduler  schedJobFaturamentoDBS;
	// private static Scheduler  schedJobCpcdJbcdDBS;
	// private static Scheduler  schedJobGestorOS;
	// private static Scheduler  schedJobVerificaSegmentoDBS;//Varre o DBS a cada 30 min. e compara com a nossa base e retira os segmentos diferentes(da nossa base)
	 private static Scheduler  schedJobRemoverSegmento;
	 private static Scheduler  schedJobRemoverCotacao;
	 private static Scheduler  schedJobImportarPecasDBS;
	 private static Scheduler  schedJobOsOpenDBS;
	 private static Scheduler  schedJobDataSituacaoOsDbs;
	 private static Scheduler  schedJobReenviarServTercDbs;
	 //private static Scheduler  schedJobAtualizarLegendaOsDbs;
	 //private static Scheduler  removerReservaFerramenta;
	// private static Scheduler  osPerdida;
    /**
     * Default constructor. 
     */
    public BusinessGroupListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	SchedulerFactory sf=new StdSchedulerFactory();

    	try {
//    		sched = sf.getScheduler();
//    		JobDetail jd=new JobDetail("jobBgrp","bgrp",JobBusinessGroup.class);
//    		CronTrigger ct=new CronTrigger("cronTriggerBgrp","bgrp","0 0 4 * * ?");
//    		sched.scheduleJob(jd,ct);
//    		sched.start();
    		
    		schedJobObsNotesDbs = sf.getScheduler();
    		JobDetail verSchedJobObsNotesDbs=new JobDetail("jobschedJobObsNotesDbs","schedschedJobObsNotesDbs",JobObsNotesDbs.class);
    		CronTrigger vschedJobObsNotesDbs=new CronTrigger("cronTriggerschedschedJobObsNotesDbs","DbsschedschedJobObsNotesDbs","0 0 3,21 * * ?");
    		schedJobObsNotesDbs.scheduleJob(verSchedJobObsNotesDbs,vschedJobObsNotesDbs);
    		schedJobObsNotesDbs.start();

    		schedRecuperarSegmentoDbs = sf.getScheduler();
    		JobDetail verSchedRecuperarSegmentoDbs=new JobDetail("jobSchedRecuperarSegmentoDbsDbs","schedRecuperarSegmentoDbsDbs",JobAtualizacaoLegendaProcessoOficina.class);
    		CronTrigger vSchedRecuperarSegmentoDbsDBS=new CronTrigger("cronTriggerschedRecuperarSegmentoDbsDbs","DbsschedRecuperarSegmentoDbs","0 0 3 * * ?");
    		schedRecuperarSegmentoDbs.scheduleJob(verSchedRecuperarSegmentoDbs,vSchedRecuperarSegmentoDbsDBS);
    		schedRecuperarSegmentoDbs.start();

    		//Recuperar as peças do DBS
    		schedJobVerificaDBS = sf.getScheduler();
    		JobDetail verDbs=new JobDetail("jobVerificaDbs","verificarDbs",JobVerificaDbs.class);
    		CronTrigger vDBS=new CronTrigger("cronTriggerDbs","Dbs1","5 0/1 * * * ?");
    		schedJobVerificaDBS.scheduleJob(verDbs,vDBS);
    		schedJobVerificaDBS.start();
    		
    		
    		//Recuperar OS do DBS
    		schedRecuperarOsDbs = sf.getScheduler();
    		JobDetail osDbs=new JobDetail("jobOsDbs","vOsDbs",JobOsDbs.class);
    		CronTrigger osCDBS=new CronTrigger("cronTriggerOsDbs","OsDbs1","10 0/1 * * * ?");
    		schedRecuperarOsDbs.scheduleJob(osDbs,osCDBS);
    		schedRecuperarOsDbs.start();

    		//Recuperar Segmento do DBS
    		schedRecuperarSegmentoDbs = sf.getScheduler();
    		JobDetail segmentoDbs=new JobDetail("jobSegmentoDbs","vSegmentoDbs",JobSegmentoDbs.class);
    		CronTrigger segmentoCDBS=new CronTrigger("cronTriggerSegmentoDbs","SegmentoDbs1","14 0/1 * * * ?");
    		schedRecuperarSegmentoDbs.scheduleJob(segmentoDbs,segmentoCDBS);
    		schedRecuperarSegmentoDbs.start();
    		
    		//Recuperar Operação do DBS
//    		schedRecuperarOperacaoDbs = sf.getScheduler();
//    		JobDetail operacaoDbs=new JobDetail("jobOperacaoDbs","vOperacaoDbs",JobOperacaoDbs.class);
//    		CronTrigger operacaoCDBS=new CronTrigger("cronTriggerOperacaoDbs","OperacaoDbs1","0 0/1 * * * ?");
//    		schedRecuperarOperacaoDbs.scheduleJob(operacaoDbs,operacaoCDBS);
//    		schedRecuperarOperacaoDbs.start();

    		//Verifica se a substiyuição tributária já retornou do DBS DBS
    		schedJobSubTributariaDBS = sf.getScheduler();
    		JobDetail subTribuDbs=new JobDetail("jobsubTribuDbs","vsubTribuDbs",JobSubTributariaDbs.class);
    		CronTrigger subTribuCDBS=new CronTrigger("cronTriggersubTribuDbs","subTribuDbs","8 0/1 * * * ?");
    		schedJobSubTributariaDBS.scheduleJob(subTribuDbs,subTribuCDBS);
    		schedJobSubTributariaDBS.start();
//
//    		schedJobFaturamentoDBS = sf.getScheduler();
//    		JobDetail fatDbs=new JobDetail("jobFaturamentoDbs","faturamentoDbs",JobFaturamento.class);
//    		CronTrigger fDBS=new CronTrigger("cronTriggerFat","Dbs2","0 0 4 * * ?");
//    		schedJobFaturamentoDBS.scheduleJob(fatDbs,fDBS);
//    		schedJobFaturamentoDBS.start();
//
//    		schedJobCpcdJbcdDBS = sf.getScheduler();
//    		JobDetail cpcdDbs=new JobDetail("jobCpcdJbcdDbs","cpcdJbcdDbs",JobCpcdJbcd.class);
//    		CronTrigger cpcd=new CronTrigger("cronTriggerCpcdJbcd","Dbs3","0 0 5 * * ?");
//    		schedJobCpcdJbcdDBS.scheduleJob(cpcdDbs,cpcd);
//    		schedJobCpcdJbcdDBS.start();
//    		
//    		schedJobGestorOS = sf.getScheduler(); // Cron para verificar se a os não foi aprovada pelo GestorOS 
//    		JobDetail gestorOs=new JobDetail("JobGestorOS","gestorOS",JobGestorOS.class);
//    		CronTrigger gestor=new CronTrigger("cronTriggerGestorOS","gestor","0 0 20 ? * MON-FRI");
//    		schedJobGestorOS.scheduleJob(gestorOs,gestor);
//    		schedJobGestorOS.start();
//    		
//    		schedJobVerificaSegmentoDBS = sf.getScheduler(); //Cron para varrer o DBS a cada dia min. e compara com a nossa base e retira os segmentos diferentes(da nossa base) 
//    		JobDetail segmentoDBS=new JobDetail("jobVerificaSegmentoDBS","verificaSegmentoDBS",JobVerificaSegmentoDBS.class);
//    		CronTrigger segmento=new CronTrigger("cronTriggerGestorOS","segDBS","0 0 4 * * ?");
//    		schedJobVerificaSegmentoDBS.scheduleJob(segmentoDBS,segmento);
//    		schedJobVerificaSegmentoDBS.start();
//
    		schedJobRemoverSegmento = sf.getScheduler(); //verifica se o segmento foi removido do DBS
    		JobDetail removerSegmentoDBS=new JobDetail("jobRemoverSegmento","removerSegmentoDBS",JobRemoverSegmento.class);
    		CronTrigger removerSeg=new CronTrigger("cronTriggerSchedJobRemoverSegmento","removerSegDBS","18 0/1 * * * ?");
    		schedJobRemoverSegmento.scheduleJob(removerSegmentoDBS,removerSeg);
    		schedJobRemoverSegmento.start();
    		
    		schedJobImportarPecasDBS = sf.getScheduler(); 
    		JobDetail importarPecasDBS = new JobDetail("jobImportarPecasDBS", "importarPecasDBS", JobImportaPecasDBS.class);
    		CronTrigger cronImoportarPecasDBS = new CronTrigger("cronTriggerSchedJobImportarPecasDBS", "importPecasDBS", "0 0 7,8,9,10,11,12,13,14,15,16,17,18 * * ?");
    		schedJobImportarPecasDBS.scheduleJob(importarPecasDBS, cronImoportarPecasDBS);
    		schedJobImportarPecasDBS.start();
    		
    		
    		schedJobOsOpenDBS = sf.getScheduler(); 
    		JobDetail jobOsOpenDBS = new JobDetail("jobOsOpenDBS", "osOpenDBS", JobOsOpenDbs.class);
    		CronTrigger cronJobOsOpenDBSDBS = new CronTrigger("cronTriggerSchedOsOpenDBS", "ordemServicoOpenDBS", "40 0 0/2 * * ?");
    		schedJobOsOpenDBS.scheduleJob(jobOsOpenDBS, cronJobOsOpenDBSDBS);
    		schedJobOsOpenDBS.start();
//    		
//    		removerReservaFerramenta = sf.getScheduler(); 
//    		JobDetail reservaFerramenta = new JobDetail("jobReservaFerramenta", "reservaFerramenta", RemoverReservaFerramenta.class);
//    		CronTrigger cronReservarFerramanta = new CronTrigger("cronTriggerSchedJobReservaFerramenta", "importPecasDBS", "0 0 20 * * ?");
//    		removerReservaFerramenta.scheduleJob(reservaFerramenta, cronReservarFerramanta);
//    		removerReservaFerramenta.start();

//    		osPerdida = sf.getScheduler(); 
//    		JobDetail osPer =new JobDetail("JobOsPerdida","osPerdida",JobOSPerdida.class);
//    		CronTrigger per=new CronTrigger("cronOsPerdida","osP","0 0 17 * * ?");
//    		osPerdida.scheduleJob(osPer,per);
//    		osPerdida.start();
    		
    		schedJobRemoverCotacao = sf.getScheduler(); //verifica se a Cotacao foi removida do DBS
    		JobDetail removerCotacaoDBS=new JobDetail("jobRemoverCotacao","removerCotacaoDBS",JobRemoverCotacao.class);
    		CronTrigger removerCot=new CronTrigger("cronTriggerSchedJobRemoverCotacao","removerCotDBS","25 0/1 * * * ?");
    		schedJobRemoverCotacao.scheduleJob(removerCotacaoDBS,removerCot);
    		schedJobRemoverCotacao.start();

    		schedJobDataSituacaoOsDbs = sf.getScheduler(); //Envia as datas do fluxo da DBS
    		JobDetail jobDataSituacaoOsDbs=new JobDetail("jobDataSituacaoOsDbs","dataSituacaoOsDbs",JobDataSituacaoOsDbs.class);
    		CronTrigger cronDataSituacaoOs=new CronTrigger("cronTriggerJobDataSituacaoOsDbs","dataSitOsDbs","0 30 8-23 * * ?");
    		schedJobDataSituacaoOsDbs.scheduleJob(jobDataSituacaoOsDbs,cronDataSituacaoOs);
    		schedJobDataSituacaoOsDbs.start();
    		
    		//Recuperar OS CRM do DBS
    		schedRecuperarOsCrmDbs = sf.getScheduler();
    		JobDetail osCrmDbs=new JobDetail("jobOsCrmDbs","vOsCrmDbs",JobOsCrmDbs.class);
    		CronTrigger osCrmDBS=new CronTrigger("cronTriggerOsCrmDbs","OsCrmDbs1","35 0/1 * * * ?");
    		//CronTrigger osCrmDBS=new CronTrigger("cronTriggerOsCrmDbs","OsCrmDbs1","0 34 8 * * ?");
    		schedRecuperarOsCrmDbs.scheduleJob(osCrmDbs,osCrmDBS);
    		schedRecuperarOsCrmDbs.start();

    		schedJobReenviarServTercDbs = sf.getScheduler();
    		JobDetail osJobReenviarServTercDbs=new JobDetail("JobReenviarServTercDbs","vJobReenviarServTercDbs",JobReenviarServTercDbs.class);
    		CronTrigger JobReenviarServTercDbs=new CronTrigger("cronTriggerJobReenviarServTercDbs","OsJobReenviarServTercDbs","0 0/20 * * * ?");
    		//CronTrigger osCrmDBS=new CronTrigger("cronTriggerOsCrmDbs","OsCrmDbs1","0 34 8 * * ?");
    		schedJobReenviarServTercDbs.scheduleJob(osJobReenviarServTercDbs,JobReenviarServTercDbs);
    		schedJobReenviarServTercDbs.start();
//    		
//    		//Recuperar Segmento CRM do DBS
//    		schedRecuperarSegmentoCrmDbs = sf.getScheduler();
//    		JobDetail segmentoCrmDbs=new JobDetail("jobSegmentoCrmDbs","vSegmentoCrmDbs",JobSegmentoCrmDbs.class);
//    		//CronTrigger segmentoCrmCDBS=new CronTrigger("cronTriggerSegmentoCrmDbs","SegmentoCrmDbs1","45 0/1 * * * ?");
//    		CronTrigger segmentoCrmCDBS=new CronTrigger("cronTriggerSegmentoCrmDbs","SegmentoCrmDbs1","0 36 9 * * ?");
//    		schedRecuperarSegmentoCrmDbs.scheduleJob(segmentoCrmDbs,segmentoCrmCDBS);
//    		schedRecuperarSegmentoCrmDbs.start();
    		
    	} catch (SchedulerException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	  try {
  			//sched.shutdown();
    		//schedJobAtualizarLegendaOsDbs.shutdown();
    		schedJobObsNotesDbs.shutdown();
  			schedRecuperarOsDbs.shutdown();
  			schedRecuperarOsCrmDbs.shutdown();
  			//schedRecuperarOperacaoDbs.shutdown();
  			schedRecuperarSegmentoDbs.shutdown();
  			//schedRecuperarSegmentoCrmDbs.shutdown();
  			schedJobRemoverSegmento.shutdown();
  			schedJobRemoverCotacao.shutdown();
  			schedJobSubTributariaDBS.shutdown();
  			schedJobVerificaDBS.shutdown();
  			//schedJobFaturamentoDBS.shutdown();
  			//schedJobCpcdJbcdDBS.shutdown();
  			//schedJobGestorOS.shutdown();
  			//schedJobVerificaSegmentoDBS.shutdown();
  			//schedJobRemoverSegmento.shutdown();
  			schedJobImportarPecasDBS.shutdown();
  			schedJobOsOpenDBS.shutdown();
  			schedJobDataSituacaoOsDbs.shutdown();
  			schedJobReenviarServTercDbs.shutdown();
  			//removerReservaFerramenta.shutdown();
  			//osPerdida.shutdown();
  		} catch (SchedulerException e) {
  			e.printStackTrace();
  		}
    }
	
}

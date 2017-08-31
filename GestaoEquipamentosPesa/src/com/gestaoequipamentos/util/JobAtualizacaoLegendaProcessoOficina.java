package com.gestaoequipamentos.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeDataFluxoSegmento;
import com.gestaoequipamentos.entity.GeLegendaProcessoOficina;
import com.gestaoequipamentos.entity.GeSegmento;

public class JobAtualizacaoLegendaProcessoOficina implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if(DateHelper.verificarDiaSemana() == 1 || DateHelper.verificarDiaSemana() == 7){
			return;
		}
		String SQL= "select ID_SEGMENTO, ID_LEGENDA_PROCESSO_OFICINA, ID_FUNCIONARIO from GE_DATA_FLUXO_SEGMENTO d"+
					"	where convert(varchar(10),d.data,103) = convert(varchar(10),DATEADD(DAY, -1 , getdate()) ,103)"+
					"	and d.ID_SEGMENTO not in ("+
					"	select f.ID_SEGMENTO from GE_DATA_FLUXO_SEGMENTO f"+
					"	where convert(varchar(10),f.data,103) = convert(varchar(10),GETDATE() ,103))"+
					"	and d.ID_LEGENDA_PROCESSO_OFICINA not in(select ID from GE_LEGENDA_PROCESSO_OFICINA where SIGLA = 'E')";
		if(DateHelper.verificarDiaSemana() == 2){
			SQL= "select ID_SEGMENTO, ID_LEGENDA_PROCESSO_OFICINA, ID_FUNCIONARIO from GE_DATA_FLUXO_SEGMENTO d"+
			"	where (convert(varchar(10),d.data,103) = convert(varchar(10),DATEADD(DAY, -3 , getdate()) ,103)"+
				" or"+
				" convert(varchar(10),d.data,103) = convert(varchar(10),DATEADD(DAY, -2 , getdate()) ,103)"+
				" or"+
				" convert(varchar(10),d.data,103) = convert(varchar(10),DATEADD(DAY, -1 , getdate()) ,103)"+
				" )"+
			"	and d.ID_SEGMENTO not in ("+
			"	select f.ID_SEGMENTO from GE_DATA_FLUXO_SEGMENTO f"+
			"	where convert(varchar(10),f.data,103) = convert(varchar(10),GETDATE() ,103))"+
			"	and d.ID_LEGENDA_PROCESSO_OFICINA not in(select ID from GE_LEGENDA_PROCESSO_OFICINA where SIGLA = 'E')";
		}
		
//		SQL = "select ID_SEGMENTO, MAX(d.id) id from GE_DATA_FLUXO_SEGMENTO d"+
//						
//				"		where d.ID_SEGMENTO not in ("+
//				"		select f.ID_SEGMENTO from GE_DATA_FLUXO_SEGMENTO f"+
//				"		where convert(varchar(10),f.data,103) = convert(varchar(10),GETDATE() ,103))"+
//				"		and convert(varchar(10),d.data,103) <> convert(varchar(10),GETDATE() ,103)"+
//				"		group by ID_SEGMENTO";
		EntityManager manager = null;
			try {
//					try {
//						manager = JpaUtil.getInstance();
//						Query query = manager.createNativeQuery(SQL);
//						List<Object[]> pair = (List<Object[]>)query.getResultList();
//						for (Object[] objects : pair) {
//							GeDataFluxoSegmento fluxoSegmento = manager.find(GeDataFluxoSegmento.class, ((BigDecimal)objects[1]).longValue());
//							if(fluxoSegmento.getIdLegendaProcessoOficina().getSigla().equals("E")){
//								continue;
//							}
//							manager.getTransaction().begin();
//							fluxoSegmento.setData(new Date());
//							fluxoSegmento.setIdFuncionario(fluxoSegmento.getIdFuncionario());
//							GeLegendaProcessoOficina geLegendaProcessoOficina = fluxoSegmento.getIdLegendaProcessoOficina();
//							fluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
//							GeSegmento geSegmento = fluxoSegmento.getIdSegmento();
//							fluxoSegmento.setIdSegmento(geSegmento);
//							manager.persist(fluxoSegmento);
//							manager.getTransaction().commit();
//						}
//					} catch (Exception e1) {
//						e1.printStackTrace();
//						EmailHelper emailHelper = new EmailHelper();
//						emailHelper.sendSimpleMail("Erro ao executar serviço de sincronização de legenda", "ERRO Legenda Fluxo OS", "rodrigo@rdrsistemas.com.br");
//					}
					try {
						manager = JpaUtil.getInstance();
						Query query = manager.createNativeQuery(SQL);
						List<Object[]> pair = (List<Object[]>)query.getResultList();
						for (Object[] objects : pair) {
							manager.getTransaction().begin();
							GeDataFluxoSegmento fluxoSegmento = new GeDataFluxoSegmento();
							fluxoSegmento.setData(new Date());
							fluxoSegmento.setIdFuncionario((String)objects[2]);
							GeLegendaProcessoOficina geLegendaProcessoOficina = manager.find(GeLegendaProcessoOficina.class, ((BigDecimal)objects[1]).longValue());
							fluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
							GeSegmento geSegmento = manager.find(GeSegmento.class, ((BigDecimal)objects[0]).longValue());
							fluxoSegmento.setIdSegmento(geSegmento);
							manager.persist(fluxoSegmento);
							manager.getTransaction().commit();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Erro ao executar serviço de sincronização de legenda", "ERRO Legenda Fluxo OS", "rodrigo@rdrsistemas.com.br");
					}
			}catch (Exception e) {
				e.printStackTrace();
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar serviço de sincronização de legenda", "ERRO Legenda Fluxo OS", "rodrigo@rdrsistemas.com.br");
			}finally{
				try {
					if(manager != null && manager.isOpen()){
						manager.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}

}

package com.gestaoequipamentos.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RemoverReservaFerramenta implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update fe_ferramenta_estoque set id_status_ferramenta = (select id from fe_status_ferramenta where sigla = 'DP')"+
					" where id in(select r.id_ferramenta_estoque from fe_reserva_ferramenta r)");
			query.executeUpdate();
			query = manager.createNativeQuery("delete from fe_reserva_ferramenta");
			query.executeUpdate();
			manager.getTransaction().commit();
			
			String SQL = " select df.descricao, func.eplsnm, func.email, to_char(emp.data_prevista_devolucao, 'DD/MM/YYYY') data_devolucao"+
						"	from fe_emprestimo_ferramenta emp, fe_ferramenta_estoque est, fe_ferramenta f, fe_descricao_ferramenta df, tw_funcionario func"+
						"	where emp.id_ferramenta_estoque = est.id"+
						"	and to_char(emp.data_prevista_devolucao, 'DD/MM/YYYY') < to_char(sysdate, 'DD/MM/YYYY')"+
						"	and emp.data_real_devolucao is null"+
						"	and f.id = est.id_ferramenta"+
						"	and f.id_descricao_ferramenta = df.id"+
						"	and func.epidno = emp.id_funcionario_receptor";
			query = manager.createNativeQuery(SQL);
			List<Object[]> objects = query.getResultList();
			for (Object[] pair : objects) {
				new EmailHelper().sendSimpleMail("Gostariamos de informar que o tecnico "+pair[1]+" nao entregou a ferramenta "+pair[0]+",no dia "+pair[3]+".", "", (String)pair[2]);
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

}

package com.gestaoequipamentos.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeSituacaoCrm;
import com.gestaoequipamentos.entity.GeSituacaoOs;

public class JobVerificaOportunidadeCRM implements Job {
	private SimpleDateFormat fmt = new SimpleDateFormat("dMMyyyy");
	//Job para verificar se gerou código da oportunidade e qual o status da mesma.
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PreparedStatement prstmt = null;
		Connection con = null;
		EntityManager manager = null;

		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
			String SQL = "select ds.cust_servcgepk, ds.cuno, ds.status, ds.notes, ds.ofic_maodeobra, ds.ofic_mut, ds.ofic_terceiros, ds.ofic_pecas, ds.ofic_fretes from mme.vw_crmstatusoportun_ds ds, ge_situacao_crm crm"+
						" where ds.cust_servcgepk = to_char(crm.id)"+
						" and crm.is_find_crm = 'N'"+
						" and ds.status <> 'Aberta'"; 
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> list = query.getResultList();
			for(Object[] sitCrm: list){
				
				GeSituacaoCrm crm = manager.find(GeSituacaoCrm.class, Long.valueOf((String)sitCrm[0]));
				crm.setStatusOp((String)sitCrm[2]);
				crm.setObservacao((String)sitCrm[3]);
				crm.getIdCheckin().setValorMaoDeObra((BigDecimal)sitCrm[4]);
				crm.getIdCheckin().setValorMatUsoTecnico((BigDecimal)sitCrm[5]);
				crm.getIdCheckin().setValorServicosTerceiros((BigDecimal)sitCrm[6]);
				crm.getIdCheckin().setValorPecas((BigDecimal)sitCrm[7]);
				crm.getIdCheckin().setValorFrete((BigDecimal)sitCrm[8]);
				crm.setIsFindCrm("S");
				crm.getIdCheckin().setHasSendDataAprovacao("S");
				query = manager.createQuery("from GeSituacaoOs os where os.idCheckin.id =:id order by os.id desc");
				query.setParameter("id", crm.getIdCheckin().getId());
				GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getResultList().get(0);
				if(((String)sitCrm[2]).equals("Fechada - Ganha")){
					situacaoOs.setDataAprovacao(new Date());
					SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"' where WONO ='"+situacaoOs.getNumeroOs()+"'";
					prstmt = con.prepareStatement(SQL);
					prstmt.executeUpdate();
				}else if (((String)sitCrm[2]).equals("Fechada - Perdida")){
					situacaoOs.setDataAprovacao(new Date());
					situacaoOs.setDataPrevisaoEntrega(new Date());
					situacaoOs.setDataTerminoServico(new Date());
					situacaoOs.setDataFaturamento(new Date());
					crm.getIdCheckin().setHasSendDataPrevisao("S");
					SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"', set DTEX = '"+fmt.format(situacaoOs.getDataPrevisaoEntrega())+"', set DTCO = '"+fmt.format(situacaoOs.getDataTerminoServico())+"', set DTFC = '"+fmt.format(situacaoOs.getDataFaturamento())+"' where WONO ='"+situacaoOs.getNumeroOs()+"'";
					prstmt = con.prepareStatement(SQL);
					prstmt.executeUpdate();
				}
				
			}
			manager.getTransaction().commit();

		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					con.close();
					prstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

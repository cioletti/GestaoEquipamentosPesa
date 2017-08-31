package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.CondicaoPagamentoBean;
import com.gestaoequipamentos.entity.CondicaoPagamento;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.util.JpaUtil;

public class ContratoBusiness {
	
	
	public BigDecimal calculoTotalPecas(String idCheckin){
		EntityManager manager = null;
		BigDecimal totalPecas = BigDecimal.ZERO;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select sum(p.valor_total) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
					" where seg.id_checkin ="+Long.valueOf(idCheckin)+
					" and seg.id = oper.id_segmento"+
					" group by oper.num_doc)  aux"+
			" where p.id_doc_seg_oper = aux.id_oper"); 
			totalPecas = (BigDecimal)query.getSingleResult();
			if(totalPecas == null){
				return BigDecimal.ZERO;
			}
			return totalPecas;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
//	public BigDecimal calculoTotalFrete (String idCheckin){
//		EntityManager manager = null;
//		try {
//			manager = JpaUtil.getInstance();
//			GeCheckIn checkIn = manager.find(GeCheckIn.class, Long.valueOf(idCheckin));			
//			if(checkIn.getIdTipoFrete() != null){
//				Query query = manager.createNativeQuery("select sum(((p.qtd_nao_atendido * p.valor) * "+ checkIn.getIdTipoFrete().getTaxa()+") / 100) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
//						" where seg.id_checkin ="+Long.valueOf(idCheckin)+
//						" and seg.id = oper.id_segmento"+
//						" group by oper.num_doc)  aux"+
//						" where p.id_doc_seg_oper = aux.id_oper"); 
//				BigDecimal totalFrete = (BigDecimal)query.getSingleResult();
//				if(totalFrete == null || totalFrete.intValue() == 0){
//					return BigDecimal.ZERO;
//				}
//				if(checkIn.getIdTipoFrete() != null && totalFrete.longValue() < checkIn.getIdTipoFrete().getFreteMinimo().longValue()){
//					return checkIn.getIdTipoFrete().getFreteMinimo();
//				}else{
//					return totalFrete;
//				}			
//			}else{
//				return BigDecimal.ZERO;
//			}			
//			
//		} catch (Exception e) {
//			e.printStackTrace();			
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		return BigDecimal.ZERO;
//	}
	
	public BigDecimal calculoTotalMaoDeObra (String idChechIn){
		EntityManager manager = null;
		BigDecimal total = BigDecimal.ZERO;
		try {
			manager = JpaUtil.getInstance();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, Long.valueOf(idChechIn));
			String SQL = "";
			if(checkIn.getTipoCliente() != null && !checkIn.getTipoCliente().equals("INT")){
				SQL = "select distinct ((pre.valor_de_venda * ch.fator_cliente)* "+
						"	(select c.fator from ge_complexidade c "+
						"	where c.id =  "+
						"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+")"+ 
						"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substr(palm.serie,0,4) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+") )"+
						"	 and p.comp_code = seg.com_code"+
						"	 and p.job_code = seg.job_code )"+
						"	) * decode(ch.fator_urgencia,'S', (select fator_urgencia from ge_fator), 1)) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+

						"	(select c.fator from ge_complexidade c"+ 
						"	where c.id =  "+
						"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+")"+ 
						"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substr(palm.serie,0,4) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+") )"+
						"	 and p.comp_code = seg.com_code"+
						"	 and p.job_code = seg.job_code )"+
						"	) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, ch.valor_servicos_terceiros, seg.numero_segmento"+

						"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
						"	where ch.id = "+idChechIn+
						"	and ch.id = seg.id_checkin"+
						"	and ch.id_os_palm = palm.idos_palm"+
						"	and pre.descricao = substr(palm.serie,0,4)" +
						"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
			}else{
				SQL = " select ((select f.valor_inter from ge_fator f) * seg.qtd_comp), seg.horas_prevista"+
						"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
						"	where ch.id = "+idChechIn+
						"	and ch.id = seg.id_checkin"+
						"	and ch.id_os_palm = palm.idos_palm"+
						"	and pre.descricao = substr(palm.serie,0,4)" +
						"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
			}
			Query query = manager.createNativeQuery(SQL);
			query = manager.createNativeQuery(SQL);
			List<Object[]> list = query.getResultList();
			BigDecimal valorMaoDeObra = BigDecimal.ZERO;			
			for (Object[] objects : list) {
				valorMaoDeObra = (BigDecimal)objects[0];
				if(valorMaoDeObra == null){
					valorMaoDeObra = BigDecimal.ZERO;
				}
				String horas = (String)objects[1];
				total = total.add(valorMaoDeObra.multiply(BigDecimal.valueOf(Double.valueOf(horas))));
			}
			return total;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return total;		
	}
	
	public BigDecimal calculoServicosTerceiros(String idCheckin){
		EntityManager manager = null;
		BigDecimal servico_terceiros = BigDecimal.ZERO;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select decode(sum(st.valor), null,0 , sum(st.valor))  from ge_segmento seg, ge_segmento_serv_terceiros st"+
			  "	where seg.id = st.id_segmento"+
			  "	and seg.id_checkin = "+Long.valueOf(idCheckin); 
		Query query = manager.createNativeQuery(SQL);
		BigDecimal st = (BigDecimal)query.getSingleResult();
		if(st.longValue() > 0){
			return servico_terceiros = st;
		}else{
			return servico_terceiros = BigDecimal.ZERO;
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return servico_terceiros;
	}
	public String voltarStatusOs(String numOs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select * from GE_SITUACAO_OS where NUMERO_OS = '"+numOs+"' and DATA_FATURAMENTO is not null";
			Query query = manager.createNativeQuery(SQL);
			if(query.getResultList().size() > 0){
				return "Não é possível voltar OS com status de faturada!";
			}
			
			manager.getTransaction().begin();
			SQL = "update GE_SITUACAO_OS "+
					" set DATA_APROVACAO = null, ID_FUNC_DATA_APROVACAO = null,"+
					" DATA_ORCAMENTO = null, ID_FUNC_DATA_ORCAMENTO = null,"+
					" DATA_PREVISAO_ENTREGA = null, ID_FUNC_DATA_PREVISAO_ENTREGA = null,"+
					" DATA_TERMINO_SERVICO = null, ID_FUNC_DATA_TERMINO_SERVICO = null"+
					" where NUMERO_OS = '"+numOs+"'";
			query = manager.createNativeQuery(SQL);
			query.executeUpdate();
			SQL = "update GE_CHECK_IN "+
					" set STATUS_NEGOCIACAO_CONSULTOR = 'N', IS_LIBERADO_P_CONSULTOR = 'N', "+
					" IS_LIBERADO_P_ORCAMENTISTA = 'S', IS_LIBERADO_P_DIGITADOR = 'S' "+
					" where NUMERO_OS = '"+numOs+"'";
			query = manager.createNativeQuery(SQL);
			query.executeUpdate();
			
			manager.getTransaction().commit();
			return "Status de OS foi alterado com sucesso!";
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return "Erro ao tentar voltar Status de OS!";
	}
	
	public List<CondicaoPagamentoBean> findAllCondicaoPagamento(){
		List<CondicaoPagamentoBean> listForm = new ArrayList<CondicaoPagamentoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("from CondicaoPagamento");
			List<CondicaoPagamento> list = (List<CondicaoPagamento>) query.getResultList();
			for (CondicaoPagamento pagamento : list) {
				CondicaoPagamentoBean bean = new CondicaoPagamentoBean();
				bean.setId(pagamento.getId());
				bean.setDescricao(pagamento.getDescricao()+" - "+pagamento.getSigla());
				bean.setSigla(pagamento.getSigla());
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
	
	public List<CondicaoPagamentoBean> findAllCondicaoPagamento(String campo){
		List<CondicaoPagamentoBean> listForm = new ArrayList<CondicaoPagamentoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("from CondicaoPagamento where (descricao  like '"+campo+"%' or sigla like '"+campo+"%')");
			List<CondicaoPagamento> list = (List<CondicaoPagamento>) query.getResultList();
			for (CondicaoPagamento pagamento : list) {
				CondicaoPagamentoBean bean = new CondicaoPagamentoBean();
				bean.setId(pagamento.getId());
				bean.setDescricao(pagamento.getDescricao()+" - "+pagamento.getSigla());
				bean.setSigla(pagamento.getSigla());
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}

}

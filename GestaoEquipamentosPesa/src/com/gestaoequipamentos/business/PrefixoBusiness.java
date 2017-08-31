package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.PrefixoBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GePrefixo;
import com.gestaoequipamentos.util.JpaUtil;

public class PrefixoBusiness {
	public List<PrefixoBean> findAllPrefixo(Long idModelo){
		List<PrefixoBean> prefixo = new ArrayList<PrefixoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GePrefixo where idModelo.id =:idModelo order by descricao");
			query.setParameter("idModelo", idModelo);
			List<GePrefixo> result = query.getResultList();
			for (GePrefixo prefixoObj : result) { 
				PrefixoBean bean = new PrefixoBean();
				bean.fromBeanPrefixo(prefixoObj);
				prefixo.add(bean);
			}
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return prefixo;
	}
	public PrefixoBean salvarPrefixo(PrefixoBean pre) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GePrefixo prefixo = new GePrefixo();

			if (pre.getId() == 0) {
				GeArvInspecao inspecao = manager.find(GeArvInspecao.class, pre.getIdModelo());
				
				Query query = manager.createNativeQuery("select * from GE_PREFIXO p, GE_ARV_INSPECAO a WHERE p.DESCRICAO = '"+pre.getDescricao()+"'" +
						" and p.id_modelo = a.id_arv and a.id_familia = "+inspecao.getIdFamilia().getId() +" and a.DESCRICAO = '"+inspecao.getDescricao()+"'");
				
				if(query.getResultList().size() > 0){
					return null;
				}				
				
				prefixo.setDescricao(pre.getDescricao());
				prefixo.setIdModelo(inspecao);//mudara aqui
				prefixo.setValorDeVenda(BigDecimal.valueOf(Double.valueOf(pre.getValor().replace(".", "").replace(",", "."))));
				//prefixo.setValorDeCusto(BigDecimal.valueOf(Double.valueOf(pre.getValorCusto().replace(".", "").replace(",", "."))));
				manager.persist(prefixo);
			} else {
				prefixo = manager.find(GePrefixo.class, pre.getId().longValue());
				prefixo.setDescricao(pre.getDescricao());
				prefixo.setIdModelo(manager.find(GeArvInspecao.class, pre.getIdModelo()));//mudara aqui
				prefixo.setValorDeVenda(BigDecimal.valueOf(Double.valueOf(pre.getValor().replace(".", "").replace(",", "."))));
				//prefixo.setValorDeCusto(BigDecimal.valueOf(Double.valueOf(pre.getValorCusto().replace(".", "").replace(",", "."))));
				manager.merge(prefixo);
			}
			manager.getTransaction().commit();
			pre.setId(prefixo.getId());
			return pre;

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
		return null;
	}

	public Boolean removerPrefixo(PrefixoBean pre) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GePrefixo trCtg = manager.find(GePrefixo.class, pre.getId());
			manager.remove(trCtg);
			manager.getTransaction().commit();

			return true;
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
		return false;
	}


}

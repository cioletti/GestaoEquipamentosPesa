package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.TipoFreteBean;
import com.gestaoequipamentos.entity.GeTipoFrete;
import com.gestaoequipamentos.util.JpaUtil;

public class TipoFreteBusiness {

	public List<TipoFreteBean> findAllFrete(){
		List<TipoFreteBean> modelo = new ArrayList<TipoFreteBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From GeTipoFrete");
			List<GeTipoFrete> result = query.getResultList();
			for(GeTipoFrete frete : result){
				TipoFreteBean bean = new TipoFreteBean();
				bean.fromBean(frete);
				modelo.add(bean);
			}

		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return modelo;

	}
	public TipoFreteBean salvarFrete(TipoFreteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeTipoFrete frete = new GeTipoFrete();

			if (bean.getId() == 0) {
				frete.setTipoFrete(bean.getTipoFrete());
//				frete.setTaxa(BigDecimal.valueOf(Double.valueOf(bean.getTaxa().replace(".", "").replace(",", "."))));
//				frete.setFreteMinimo(BigDecimal.valueOf(Double.valueOf(bean.getFreteMinimo().replace(".", "").replace(",", "."))));
				manager.persist(frete);
			}else{
				frete = manager.find(GeTipoFrete.class, bean.getId());
				frete.setTipoFrete(bean.getTipoFrete());
//				frete.setTaxa(BigDecimal.valueOf(Double.valueOf(bean.getTaxa().replace(".", "").replace(",", "."))));
//				frete.setFreteMinimo(BigDecimal.valueOf(Double.valueOf(bean.getFreteMinimo().replace(".", "").replace(",", "."))));
				manager.merge(frete);
			}
			manager.getTransaction().commit();
			bean.setId(frete.getId());
			return bean;
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
	public Boolean removerFrete(TipoFreteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeTipoFrete frete = manager.find(GeTipoFrete.class, bean.getId());
			manager.remove(frete);
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

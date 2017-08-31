package com.gestaoequipamentos.business;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.FatorBean;
import com.gestaoequipamentos.entity.GeFator;
import com.gestaoequipamentos.util.JpaUtil;

public class FatorBusiness {

	public FatorBean salvarFator(FatorBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFator fator = new GeFator();
			if (bean.getId() == 0){
				fator.setFatorUrgencia (Double.valueOf(bean.getFatorUrgente()));
				fator.setValorInter (Double.valueOf(bean.getValorInter().replace(".", "").replace(",", ".")));

				manager.merge(fator);
				manager.getTransaction().commit();
				bean.setId(fator.getId());
				return bean;
			}else {			
				Query query = manager.createQuery("From GeFator where id = :idFator");
				query.setParameter("idFator", bean.getId());
				fator = (GeFator)query.getSingleResult();
				fator.setFatorUrgencia(Double.valueOf(bean.getFatorUrgente()));
				fator.setValorInter(Double.valueOf(bean.getValorInter().replace(".", "").replace(",", ".")));
				manager.merge(fator);
				manager.getTransaction().commit();
				bean.setId (fator.getId());
				return bean;
			}
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
	public FatorBean findAllFator (){
		FatorBean bean = new FatorBean();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeFator");
			GeFator result = (GeFator)query.getSingleResult();
			bean.setId(result.getId());
			bean.setFatorUrgente(String.valueOf(result.getFatorUrgencia()));
			bean.setValorInter(String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(result.getValorInter())))));


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;

	}
}

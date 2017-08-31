package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.FamiliaBean;
import com.gestaoequipamentos.entity.GeFamilia;
import com.gestaoequipamentos.util.JpaUtil;

public class FamiliaBusiness {

	public List<FamiliaBean> findAllFamilia() {
		List<FamiliaBean> familiaList = new ArrayList<FamiliaBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From GeFamilia order by descricao");
			List<GeFamilia> result = query.getResultList();
			for (GeFamilia familia : result) {
				FamiliaBean bean = new FamiliaBean();
				bean.setId(familia.getId());
				bean.setDescricao(familia.getDescricao());
				familiaList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return familiaList;
	}



	public FamiliaBean saveOrUpdate(FamiliaBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFamilia familia = null;
			if (bean.getId() == null || bean.getId() == 0) {
				familia = new GeFamilia();
				familia.setDescricao(bean.getDescricao().toUpperCase());
				manager.persist(familia);
			} else {
				familia = manager.find(GeFamilia.class, bean.getId());
				familia.setDescricao(bean.getDescricao().toUpperCase());
				manager.merge(familia);
			}
			manager.getTransaction().commit();
			bean.setId(familia.getId());
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

	public Boolean remover(FamiliaBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(GeFamilia.class, bean.getId()));
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

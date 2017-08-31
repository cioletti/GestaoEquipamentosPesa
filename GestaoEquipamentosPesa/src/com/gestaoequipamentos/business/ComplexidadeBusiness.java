package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ComplexidadeBean;
import com.gestaoequipamentos.entity.GeComplexidade;
import com.gestaoequipamentos.util.JpaUtil;

public class ComplexidadeBusiness {

	public List<ComplexidadeBean> findAllComplexidade (){
		List<ComplexidadeBean> list = new ArrayList<ComplexidadeBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("FROM GeComplexidade");
			List<GeComplexidade> result = query.getResultList();
			for (GeComplexidade complexidade : result){
				ComplexidadeBean bean = new ComplexidadeBean();
				bean.fromBean(complexidade);
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return list;
	}
	public Boolean removerComplexidade(ComplexidadeBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_complexidade where id = "+bean.getId());
			query.executeUpdate();
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}
	public ComplexidadeBean salvarComplexidade(ComplexidadeBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeComplexidade complexidade = new GeComplexidade();
			if (bean.getId() == 0){
				complexidade.setDescricao(bean.getDescricao());
				complexidade.setFator(BigDecimal.valueOf(Double.valueOf(bean.getFator())));
				complexidade.setSigla(bean.getSigla());
				manager.merge(complexidade);
			}else{
				complexidade = manager.find(GeComplexidade.class, bean.getId());
				complexidade.setDescricao(bean.getDescricao());
				complexidade.setFator(BigDecimal.valueOf(Double.valueOf(bean.getFator())));
				complexidade.setSigla(bean.getSigla());
				manager.merge(complexidade);
			}
			manager.getTransaction().commit();
			bean.setId(complexidade.getId());
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
}

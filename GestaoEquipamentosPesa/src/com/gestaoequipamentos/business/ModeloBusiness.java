package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ModeloBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeModelo;
import com.gestaoequipamentos.util.JpaUtil;

public class ModeloBusiness {
	public Collection<ModeloBean> findAllModelo(){
		Collection<ModeloBean> modelo = new ArrayList<ModeloBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GeArvInspecao where idPai is null order by descricao");   
			List<GeArvInspecao> result = query.getResultList();
			for (GeArvInspecao modeloObj : result) { 
				ModeloBean bean = new ModeloBean();
				bean.fromBeanModelo(modeloObj);
				modelo.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return modelo;
	}
	public Collection<ModeloBean> findAllModelo(String descricao){
		Collection<ModeloBean> modelo = new ArrayList<ModeloBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From GeArvInspecao where descricao like '"+descricao+"%' and idPai is null order by descricao");
			List<GeArvInspecao> result = query.getResultList();
			for (GeArvInspecao modeloObj : result) { 
				ModeloBean bean = new ModeloBean();
				bean.fromBeanModelo(modeloObj);
				modelo.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return modelo;
	}

	//não modificar
	public ModeloBean salvarModelo(ModeloBean ctg) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeModelo trCtg = new GeModelo();

			if (ctg.getId() == 0) {
				trCtg.setDescricao(ctg.getDescricao().toUpperCase());
				manager.persist(trCtg);
			} else {
				trCtg = manager.find(GeModelo.class, ctg.getId().longValue());
				trCtg.setDescricao(ctg.getDescricao().toUpperCase());
				manager.merge(trCtg);
			}
			manager.getTransaction().commit();
			ctg.setId(trCtg.getId());
			return ctg;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	public Boolean removerModelo(ModeloBean ctg) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeModelo trCtg = manager.find(GeModelo.class, ctg.getId());
			manager.remove(trCtg);
			manager.getTransaction().commit();

			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}

}

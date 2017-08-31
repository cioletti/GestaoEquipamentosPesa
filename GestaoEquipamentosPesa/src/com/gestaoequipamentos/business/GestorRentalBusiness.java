package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.FilialBean;
import com.gestaoequipamentos.bean.GestorRentalBean;
import com.gestaoequipamentos.entity.GeGestorRental;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.util.JpaUtil;

public class GestorRentalBusiness {
	
	public List<FilialBean> findAllFilial (){
		List<FilialBean> list = new ArrayList<FilialBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From TwFilial order by stnm");
			List<TwFilial> result = query.getResultList();
			for (TwFilial filial : result){
				FilialBean bean = new FilialBean();
				bean.fromBean(filial);
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
	public Boolean removerGestor(GestorRentalBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeGestorRental rental = manager.find(GeGestorRental.class, bean.getFilial());
			manager.remove(rental);
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
	public Boolean salvarGestorRental(GestorRentalBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeGestorRental rental = new GeGestorRental();

			if (bean.getId() == 0) {
				rental.setEmail(bean.getEmail());
				//rental.setFilial(bean.getFilial());//mudara aqui
				rental.setNome(bean.getNome());
				manager.persist(rental);
			} else {
				rental = manager.find(GeGestorRental.class, bean.getId().longValue());
				rental.setEmail(bean.getEmail());
				//rental.setFilial(bean.getFilial());//mudara aqui
				rental.setNome(bean.getNome());
				manager.merge(rental);
			}
			manager.getTransaction().commit();
			rental.setId(rental.getId());
			

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
	public List<GestorRentalBean> findAllGestorRental (){
		List<GestorRentalBean> list = new ArrayList<GestorRentalBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("FROM GeGestorRental");
			List<GeGestorRental> result = query.getResultList();
			for(GeGestorRental gestor : result){
				GestorRentalBean bean = new GestorRentalBean();
				bean.setEmail(gestor.getEmail());
				bean.setFilial(gestor.getFilial().getStno());
				bean.setId(gestor.getId());
				bean.setNome(gestor.getNome());
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
}

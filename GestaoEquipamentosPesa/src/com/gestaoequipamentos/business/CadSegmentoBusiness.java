package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.CadSegmentoBean;
import com.gestaoequipamentos.entity.GeSegmentoCliente;
import com.gestaoequipamentos.util.JpaUtil;

public class CadSegmentoBusiness {

	public List<CadSegmentoBean> findAllSegmento (){
		List<CadSegmentoBean> list = new ArrayList<CadSegmentoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 
			Query query = manager.createQuery("FROM GeSegmentoCliente");
			List<GeSegmentoCliente> result = query.getResultList();
			for(GeSegmentoCliente segmento : result){
				CadSegmentoBean bean = new CadSegmentoBean();
				bean.setCodigo(segmento.getCodigo());
				bean.setDescricao(segmento.getDescricao());
				bean.setFator(String.valueOf(segmento.getFator()));
				bean.setId(segmento.getId());
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
	public CadSegmentoBean salvarSegmento (CadSegmentoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 
			manager.getTransaction().begin();
			GeSegmentoCliente segmento = new GeSegmentoCliente();
			if (bean.getId() == 0){
				segmento.setCodigo(bean.getCodigo());
				segmento.setDescricao(bean.getDescricao());
				segmento.setFator((Double.valueOf(bean.getFator())));
				manager.merge(segmento);					
			}else{
				segmento = manager.find(GeSegmentoCliente.class, bean.getId());
				segmento.setCodigo(bean.getCodigo());
				segmento.setDescricao(bean.getDescricao());
				segmento.setFator(Double.valueOf(bean.getFator()));
				manager.merge(segmento);			
			}
			manager.getTransaction().commit();
			bean.setId(segmento.getId());
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
	public Boolean removerCadSegmento (CadSegmentoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_segmento_cliente where id = "+bean.getId());
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
}

package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.FatorReajusteBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeFatorReajuste;
import com.gestaoequipamentos.entity.GePrefixo;
import com.gestaoequipamentos.util.JpaUtil;

public class FatorReajusteBusiness {
	private UsuarioBean bean;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public FatorReajusteBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	public FatorReajusteBean salvarFatorReajuste(FatorReajusteBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFatorReajuste fator = new GeFatorReajuste();
			if (bean.getId() == 0){
				fator.setFatorReajuste (Double.valueOf(bean.getFatorAjuste()));
				fator.setData(new Date());
				fator.setIdUsuario(this.bean.getMatricula());
				manager.persist(fator);
				bean.setId(fator.getId());
			}else {			
				Query query = manager.createQuery("From GeFatorReajuste where id = :idFator");
				query.setParameter("idFator", bean.getId());
				fator = (GeFatorReajuste)query.getSingleResult();
				fator.setFatorReajuste (Double.valueOf(bean.getFatorAjuste()));
				fator.setData(new Date());
				fator.setIdUsuario(this.bean.getMatricula());
				manager.merge(fator);
				bean.setId (fator.getId());
			}
			Query query = manager.createQuery("From GePrefixo");
			List<GePrefixo> resultList = query.getResultList();
			for (GePrefixo gePrefixo : resultList) {
				gePrefixo.setValorDeVenda(BigDecimal.valueOf(gePrefixo.getValorDeVenda().doubleValue() * Double.valueOf(bean.getFatorAjuste())));
			}
			manager.getTransaction().commit();
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
	public FatorReajusteBean findAllFatorReajuste (){
		FatorReajusteBean bean = new FatorReajusteBean();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeFatorReajuste");
			GeFatorReajuste result = (GeFatorReajuste)query.getSingleResult();
			bean.setId(result.getId());
			bean.setFatorAjuste(String.valueOf(result.getFatorReajuste()));
			bean.setData(format.format(result.getData()));


		} catch (Exception e) {
			bean.setData(format.format(new Date()));
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;

	}
}

package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.PecasBean;
import com.gestaoequipamentos.entity.GePecas;
import com.gestaoequipamentos.entity.GePecas00E;
import com.gestaoequipamentos.entity.GePecasLog;
import com.gestaoequipamentos.entity.GeRemoverPecaLog;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.JpaUtil;

public class PecasBusiness {
	
	public void removerPecas(Long idChechIn){ // REMOVER PELO ID 
		//abrir transação
	}
	
	public void inserirPecas(List<PecasBean> pecasList, Long idChechIn){
		//CHAMAR O REMOVER PEÇAS
		
		//abrir transação
		for (PecasBean pecasBean : pecasList) {
			
		}
	}
	
	public List<PecasBean> findAllPecasLog(String idDocSegOper){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GePecasLog p, TwFuncionario f where p.idDocSegOper in ("+idDocSegOper+") and f.epidno = p.idFuncionario");
			//query.setParameter("idSegmento", idSegmento);
			List<Object[]> result = query.getResultList();
			for(Object[] pair : result){
				GePecasLog pecasObj = (GePecasLog)pair[0];
				TwFuncionario funcObj = (TwFuncionario)pair[1];
				PecasBean bean = new PecasBean();
				bean.setNomeFuncionario(funcObj.getEplsnm());
				bean.fromBean(pecasObj);
				pecas.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
	public List<PecasBean> findAllPecas00E(String idDocSegOper){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GePecas00E where idDocSegOper.id in ("+idDocSegOper+")");
			//query.setParameter("idSegmento", idSegmento);
			List<GePecas00E> result = query.getResultList();
			for(GePecas00E pecasObj : result){
				PecasBean bean = new PecasBean();
				bean.fromBean(pecasObj);
				pecas.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
	public List<PecasBean> findAllPecasRemovidas(String idDocSegOper){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeRemoverPecaLog p, TwFuncionario f where p.idDocSegOper in ("+idDocSegOper+") and f.epidno = p.idFuncionario");
			//query.setParameter("idSegmento", idSegmento);
			List<Object[]> result = query.getResultList();
			for(Object[] pair : result){
				GeRemoverPecaLog pecasObj = (GeRemoverPecaLog)pair[0];
				TwFuncionario funcObj = (TwFuncionario)pair[1];
				PecasBean bean = new PecasBean();
				bean.setNomeFuncionario(funcObj.getEplsnm());
				bean.fromBean(pecasObj);
				pecas.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	

}

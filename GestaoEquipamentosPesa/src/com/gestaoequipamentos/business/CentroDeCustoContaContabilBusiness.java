package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ClienteInterBean;
import com.gestaoequipamentos.bean.IndicadorGarantiaBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.bean.ValidarCentroDeCustoContaContabilBean;
import com.gestaoequipamentos.entity.GeOsPalm;
import com.gestaoequipamentos.entity.PmpIndicadorGarantia;
import com.gestaoequipamentos.entity.PmpRegraDeNegocio;
import com.gestaoequipamentos.util.JpaUtil;

public class CentroDeCustoContaContabilBusiness {
	private String FILIAL;

	public CentroDeCustoContaContabilBusiness(UsuarioBean bean) {
		FILIAL = bean.getFilial();
	}
	public List<IndicadorGarantiaBean> findAllIndicadorGarantia(){
		List<IndicadorGarantiaBean> ig = new ArrayList<IndicadorGarantiaBean>();
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From PmpIndicadorGarantia");
			List<PmpIndicadorGarantia> result = query.getResultList();
			for (PmpIndicadorGarantia garantia : result) {
				IndicadorGarantiaBean bean = new IndicadorGarantiaBean();
				bean.setId(garantia.getId());
				bean.setDescricao(garantia.getDescricao());
				bean.setInd(garantia.getInd());
				bean.setTipo(garantia.getTipo());
				ig.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return ig;
	}

	public Boolean validarCentroDeCustoContaContabil(ValidarCentroDeCustoContaContabilBean bean,Long idOsPalm){
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			String par = "";
			if(bean.getTipoCliente() != null && !bean.getTipoCliente().equals("")){
				if(bean.getTipoCliente().equals("INT")){
					//par = bean.getClienteInter()+bean.getCentroDeCusto()+bean.getContaContabil(); // Não existe mais centro de custo, conta contábil
					par = bean.getClienteInter();
				}else if(bean.getTipoCliente().equals("EXT")){
					//par = bean.getCentroDeCusto()+bean.getContaContabil(); // Não existe mais centro de custo, conta contábil
					par = bean.getCentroDeCusto()+bean.getContaContabil();
				}
			}
//			else{ // Não existe mais centro de custo, conta contábil
//				if(bean.getContaContabilSigla().equals("13")){
//					par = "7999900"+bean.getClienteInter()+bean.getCentroDeCusto()+bean.getContaContabil();
//				}if(bean.getIndGarantia().equals("REFO") && bean.getContaContabilSigla().equals("20")){
//					par = bean.getClienteInter()+bean.getCentroDeCusto()+bean.getContaContabil();
//				}
//			}
			Query query = manager.createQuery("From PmpRegraDeNegocio where descricao =:descicao");
			query.setParameter("descicao", par.trim());
			List<PmpRegraDeNegocio> result = query.getResultList();
			if(result.size() > 0){
				this.updateModeloMaquina(bean, idOsPalm);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public void updateModeloMaquina (ValidarCentroDeCustoContaContabilBean bean,Long idOsPalm){// Atualiza o modelo da máquina na tabela OSPalm
		GeOsPalm palm = new GeOsPalm();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			palm = manager.find(GeOsPalm.class, idOsPalm);
			palm.setModelo(bean.getModeloMaquina());
			manager.merge(palm);
			manager.getTransaction().commit();			
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
		bean.setModeloMaquina(palm.getModelo());// Atualiza o valor do Bean para o valor salvo na tabela.
	}

	public List<ClienteInterBean> findAllClienteInterCC(){
		List<ClienteInterBean> list = new ArrayList<ClienteInterBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select id, fk_filial, customer_num, search_key, descricao, cod, filial_desc from pmp_cliente_inter ci where to_number(substr(ci.customer_num,2,2)) = to_number('"+FILIAL+"')");

			List<Object[]> result = query.getResultList();
			for (Object[] objects : result) {
				ClienteInterBean bean = new ClienteInterBean();
				bean.setId(((BigDecimal)objects[0]).longValue());
				bean.setFkFilial((Long)objects[1]);
				bean.setCustomerNum((String)objects[2]);
				bean.setSearchKey((String)objects[3]);
				bean.setDescricao((String)objects[4]);
				bean.setCod((objects[5] != null)?((BigDecimal)objects[5]).longValue():null);
				bean.setFilialDesc((String)objects[6]);
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

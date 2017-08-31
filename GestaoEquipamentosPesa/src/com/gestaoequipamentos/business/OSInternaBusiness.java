package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ModeloBean;
import com.gestaoequipamentos.bean.OSInternaBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeFamilia;
import com.gestaoequipamentos.entity.GeOsPalm;
import com.gestaoequipamentos.util.JpaUtil;

public class OSInternaBusiness {

	private UsuarioBean bean;

	public OSInternaBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	public List<ModeloBean> findAllModeloOS(String tipo, Long idFamilia){
		List<ModeloBean> modelo = new ArrayList<ModeloBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeArvInspecao where tipo = :tipo and idPai.idArv is null and idFamilia.id =:idFamilia order by descricao");
			query.setParameter("tipo", tipo);
			query.setParameter("idFamilia", idFamilia);
			List<GeArvInspecao> list = query.getResultList();
			for(GeArvInspecao modeloObj : list){
				ModeloBean bean = new ModeloBean();
				bean.fromBeanModelo(modeloObj);
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

	public Boolean saveOSInterna(OSInternaBean bean, String tipoInspecao){
		Date dataEmissao = new Date();

		EntityManager manager = null;
		
		// Salvar na tabela GeOsPalm
			try {
				manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				GeOsPalm palm = new GeOsPalm();

				palm.setCliente(bean.getNomeCliente().toUpperCase());
				palm.setModelo(bean.getDescricaoModelo());
				palm.setSerie(bean.getSerie().toUpperCase());
				palm.setSmu(bean.getHorimetro());
				palm.setUnMedida("Hora");
				palm.setTecnico(this.bean.getNome().toUpperCase());
				palm.setFilial(this.bean.getFilial().equals("00") ? "0" : this.bean.getFilial());
				palm.setEmissao(dataEmissao);
				palm.setHorimetro((Long.valueOf(bean.getHorimetro())));
				palm.setTipoOperacao("OSINTERNA");
				palm.setIdcliente("-1");
				palm.setTipoinspecao(tipoInspecao.toUpperCase());
				palm.setMedidor(Long.valueOf("0"));
				palm.setTrocarPecas("N");
				palm.setIdFuncionario(this.bean.getMatricula().toUpperCase());
				palm.setContato(bean.getContato().toUpperCase());
				palm.setEmailContato(bean.getEmailContato());
				palm.setTelefone(bean.getTelefone());
				palm.setIdFamilia(manager.find(GeFamilia.class, bean.getIdfamilia()));
				palm.setTipoManutencao(tipoInspecao.toUpperCase());
				palm.setEquipamento(bean.getDescricaoModelo().toUpperCase());
				manager.persist(palm);					
				
				GeCheckIn geCheckIn = new GeCheckIn();				
				geCheckIn.setDataEmissao(dataEmissao);
				geCheckIn.setIdOsPalm(manager.find(GeOsPalm.class, palm.getIdosPalm()));
				geCheckIn.setIdSupervisor(this.bean.getMatricula());
				geCheckIn.setCodCliente("");
				geCheckIn.setIsSearchDbs("N");
				geCheckIn.setHasSendDbs("N");
				geCheckIn.setHasSendDataOrcamento("N");
				geCheckIn.setFatorUrgencia("N");
				geCheckIn.setHasSendDataAprovacao("N");
				geCheckIn.setHasSendDataPrevisao("N");
				geCheckIn.setHasSendPecasDbs("N");
				geCheckIn.setIsCheckIn("N");
				geCheckIn.setStatusNegociacaoConsultor("N");
				
				manager.persist(geCheckIn);
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

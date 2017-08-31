package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.TreeBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeFamilia;
import com.gestaoequipamentos.util.JpaUtil;

public class TreeBusiness {

	private static final String TIPO_SISTEMA = "GE";


	public Boolean removerTree(Long idArv){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_arv_inspecao where id_pai_root = "+idArv);
			query.executeUpdate();
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
	public TreeBean findAllTree(Integer idArv){

		TreeBean bean = new TreeBean();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			GeArvInspecao tree = manager.find(GeArvInspecao.class, idArv.longValue());
			bean.fromBean(tree);
			Query query = manager.createQuery("From GeArvInspecao where idPaiRoot = :idpairoot and idPai.idArv is not null and tipo =:tipo order by descricao");
			query.setParameter("idpairoot", tree.getIdArv());
			query.setParameter("tipo", TIPO_SISTEMA);
			List<GeArvInspecao> result = query.getResultList();

			this.mountTree(result, bean);
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;
	}

	private TreeBean mountTree(List<GeArvInspecao> list, TreeBean tree){
		List<TreeBean> treeList = new ArrayList<TreeBean>();
		for (GeArvInspecao arvInspecao : list) {
			TreeBean children = new TreeBean();
			if(tree.getIdArv().equals(arvInspecao.getIdPai().getIdArv())){
				children.fromBean(arvInspecao);
				treeList.add(children);
				this.mountTree(list, children);
			}
		}
		tree.setChildren(treeList);
		return tree;
	}
	public List<TreeBean> findAllTreePai(String tipo, Long idFamilia){
		List<TreeBean> treeList = new ArrayList<TreeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeArvInspecao where tipo = :tipo and idPai.idArv is null and idFamilia.id =:idFamilia order by descricao");
			query.setParameter("tipo", tipo);
			query.setParameter("idFamilia", idFamilia);
			List<GeArvInspecao> list = query.getResultList();
			TreeBean bean = new TreeBean();
			bean.setIdArv(-1l);
			bean.setDescricao("Selecione");
			treeList.add(bean);
			for (GeArvInspecao arvInspecao : list) {
				bean = new TreeBean();
				bean.fromBean(arvInspecao);
				treeList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return treeList;
	}


	public TreeBean saveNodo(TreeBean bean, Long idPaiRoot, Long idFamilia) {

		try {

			if(bean.getIdArv() == null || bean.getIdArv() == 0){
				bean = this.save(bean, null, null, idFamilia);
			}else{
				bean = this.save(bean, null, bean.getIdArv(), idFamilia);
			}
			if(bean.getChildren() != null && bean.getChildren().size() > 0){
				this.saveRecursive(bean.getChildren(), bean.getIdArv(), bean.getIdArv(), idFamilia);
			}
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public TreeBean saveNodoClone(Long idArv, Long idFamilia, String descricao) {

		try {
			TreeBean bean = this.findAllTree(Integer.valueOf(idArv.toString()));
			bean.setDescricao(descricao);

			bean = this.saveClone(bean, null, idArv, idFamilia);

			if(bean.getChildren() != null && bean.getChildren().size() > 0){
				this.saveRecursiveClone(bean.getChildren(), bean.getIdArv(), bean.getIdArv(), idFamilia);
			}
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean removerNodo(Long idArv){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			Query query = manager.createNativeQuery("delete from ge_arv_inspecao where id_arv = "+idArv);
			query.executeUpdate();
			
			query = manager.createNativeQuery("delete from ge_arv_inspecao where id_pai = "+idArv);
			query.executeUpdate();
			
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
	private void saveRecursive(List<TreeBean> children, Long idPai, Long paiRoot, Long idFamilia){
		for (TreeBean bean : children) {
			//bean.setIdArv(null);
			bean = this.save(bean, idPai, paiRoot, idFamilia);
			if(bean.getChildren() != null && bean.getChildren().size() > 0){
				this.saveRecursive(bean.getChildren(), bean.getIdArv(), paiRoot, idFamilia);
			}
		}
	}
	private void saveRecursiveClone(List<TreeBean> children, Long idPai, Long paiRoot, Long idFamilia){
		for (TreeBean bean : children) {	
			bean = this.saveClone(bean, idPai, paiRoot, idFamilia);
			if(bean.getChildren() != null && bean.getChildren().size() > 0){
				this.saveRecursiveClone(bean.getChildren(), bean.getIdArv(), paiRoot, idFamilia);
			}
		}
	}
	public TreeBean save(TreeBean bean, Long idPai, Long paiRoot, Long idFamilia) {

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeArvInspecao pai = null;
			if(idPai != null){
				pai = manager.find(GeArvInspecao.class, idPai);
			}
			GeFamilia familia = manager.find(GeFamilia.class, idFamilia);
			GeArvInspecao inspecao = new GeArvInspecao();
			if(bean.getIdArv() != null && bean.getIdArv() > 0){
				inspecao = manager.find(GeArvInspecao.class, bean.getIdArv());
				inspecao.setTipo(TIPO_SISTEMA);
				inspecao.setDescricao(bean.getDescricao());
				inspecao.setIdPai(pai);
				inspecao.setIdFamilia(familia);
				inspecao.setTipoManutencao(bean.getTipoManutencao());
				inspecao.setSos(bean.getSos());
				inspecao.setSmcs(bean.getSmcs());
				manager.merge(inspecao);
			}else{
				inspecao.setTipo(TIPO_SISTEMA);
				inspecao.setDescricao(bean.getDescricao());
				inspecao.setIdPai(pai);
				inspecao.setIdFamilia(familia);
				inspecao.setTipoManutencao(bean.getTipoManutencao());
				inspecao.setSos(bean.getSos());
				inspecao.setSmcs(bean.getSmcs());
				manager.persist(inspecao);
			}
			if(pai == null){
				inspecao.setIdPaiRoot(inspecao.getIdArv());
			}else{
				inspecao.setIdPaiRoot(paiRoot);

			}
			manager.merge(inspecao);
			manager.getTransaction().commit();
			bean.fromBean(inspecao);
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
	public TreeBean saveClone(TreeBean bean, Long idPai, Long paiRoot, Long idFamilia) {

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeArvInspecao pai = null;
			if(idPai != null){
				pai = manager.find(GeArvInspecao.class, idPai);
			}
			GeFamilia familia = manager.find(GeFamilia.class, idFamilia);
			GeArvInspecao inspecao = new GeArvInspecao();

			inspecao.setTipo(TIPO_SISTEMA);
			inspecao.setDescricao(bean.getDescricao());
			inspecao.setIdPai(pai);
			inspecao.setIdFamilia(familia);
			inspecao.setTipoManutencao(bean.getTipoManutencao());
			inspecao.setSos(bean.getSos());
			inspecao.setSmcs(bean.getSmcs());
			manager.persist(inspecao);

			if(pai == null){
				inspecao.setIdPaiRoot(inspecao.getIdArv());
			}else{
				inspecao.setIdPaiRoot(paiRoot);

			}
			manager.merge(inspecao);
			manager.getTransaction().commit();
			bean.fromBean(inspecao);
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

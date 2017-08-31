package com.gestaoequipamentos.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.BgrpBean;
import com.gestaoequipamentos.entity.GeBgrp;
import com.gestaoequipamentos.util.JpaUtil;

public class BgrpBusiness {

	public List<BgrpBean> findAllBgrp(){
		List<BgrpBean> bgrp = new ArrayList<BgrpBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GeBgrp order by descricao");   
			List<GeBgrp> result = query.getResultList();
			for (GeBgrp bgrpObj : result) { 
				BgrpBean bean = new BgrpBean();
				bean.fromBeanBgrp(bgrpObj);
				bgrp.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bgrp;
	}

}

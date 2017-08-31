package com.gestaoequipamentos.bean;

import java.io.Serializable;


public class ArquivosBean implements Serializable {

		private static final long serialVersionUID = 8204818137243563157L;
		private Long id;
		private String descricao;
		private Serializable referencia;

		public ArquivosBean() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao.toUpperCase();
		}

		public Serializable getReferencia() {
			return referencia;
		}

		public void setReferencia(Serializable referencia) {
			this.referencia = referencia;
		}

//		public void fromBean(TrBibliografia entity) {
//			setId(entity.getId());
//			setDescricao(entity.getDescricao());
//		}
//
//		public TrBibliografia toBean() {
//			TrBibliografia bib = new TrBibliografia();
//			bib.setDescricao(getDescricao());
//			return bib;
//		}

}

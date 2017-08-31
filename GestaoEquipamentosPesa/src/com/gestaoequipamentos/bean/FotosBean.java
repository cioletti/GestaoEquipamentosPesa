package com.gestaoequipamentos.bean;

import java.io.File;
import java.io.Serializable;


public class FotosBean  {

		private static final long serialVersionUID = 8204818137243563157L;
		private Long id;
		private String numeroOs;
		private String observacao;
		private Long idCheckin;
		private String jobControl;
		private String nomeArquivo;
		private File imagem;
		private String tituloFotos;
		private String descricaoFalhaFotos;
		private String conclusaoFotos;
		

		public FotosBean() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNumeroOs() {
			return numeroOs;
		}

		public void setNumeroOs(String numeroOs) {
			this.numeroOs = numeroOs;
		}

		public String getObservacao() {
			return observacao;
		}

		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}

		public Long getIdCheckin() {
			return idCheckin;
		}

		public void setIdCheckin(Long idCheckin) {
			this.idCheckin = idCheckin;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getJobControl() {
			return jobControl;
		}

		public void setJobControl(String jobControl) {
			this.jobControl = jobControl;
		}

		public String getNomeArquivo() {
			return nomeArquivo;
		}

		public void setNomeArquivo(String nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
		}

		public File getImagem() {
			return imagem;
		}

		public void setImagem(File imagem) {
			this.imagem = imagem;
		}

		public String getTituloFotos() {
			return tituloFotos;
		}

		public void setTituloFotos(String tituloFotos) {
			this.tituloFotos = tituloFotos;
		}

		public String getDescricaoFalhaFotos() {
			return descricaoFalhaFotos;
		}

		public void setDescricaoFalhaFotos(String descricaoFalhaFotos) {
			this.descricaoFalhaFotos = descricaoFalhaFotos;
		}

		public String getConclusaoFotos() {
			return conclusaoFotos;
		}

		public void setConclusaoFotos(String conclusaoFotos) {
			this.conclusaoFotos = conclusaoFotos;
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

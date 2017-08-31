package com.gestaoequipamentos.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaoequipamentos.business.SituacaoServicoTerceirosBusiness;

/**
 * Servlet implementation class VisualizarNF
 */
public class VisualizarNF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizarNF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idSituacaoServTerc = request.getParameter("idSituacaoServTerc");


		try {
			SituacaoServicoTerceirosBusiness business = new SituacaoServicoTerceirosBusiness(null);
			String caminho = business.findCaminhoArquivo(Long.valueOf(idSituacaoServTerc));

			File arquivo = null;
			String nomeFile = business.recuperarNomeArquivo(Long.valueOf(idSituacaoServTerc));
			arquivo = new File(caminho+"/"+nomeFile);

			FileInputStream fis = new FileInputStream(arquivo);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum); //no doubt here is 0
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			byte[] bytes = bos.toByteArray();
			//arquivo.delete();
			bos.flush();
			fis.close();
			bos.close();
			ServletOutputStream servletOutputStream = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename=" + nomeFile);

			servletOutputStream.write(bytes);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

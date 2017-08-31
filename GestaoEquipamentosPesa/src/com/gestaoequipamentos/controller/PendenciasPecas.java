package com.gestaoequipamentos.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gestaoequipamentos.util.Connection;

/**
 * Servlet implementation class PendenciasPecas
 */
public class PendenciasPecas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PendenciasPecas() {
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
		String idCheckin = request.getParameter("idCheckin");

		JasperReport jasperReport = null;
		java.sql.Connection conn = null;

		// Obtem o caminho do .jasper
		ServletContext servletContext = super.getServletContext();
		String caminhoJasper = servletContext.getRealPath("WEB-INF/pecas-discrepantes/pecas-discrepantes.jasper");
		String pathSubreport = servletContext.getRealPath("WEB-INF/pecas-discrepantes/") + "/";

		Map parametros = new HashMap();
		
		parametros.put("SUBREPORT_DIR", pathSubreport);
		parametros.put("ID_CHECHIN", BigDecimal.valueOf(Double.valueOf(idCheckin)));

		try {
			jasperReport = (JasperReport) JRLoader.loadObject(caminhoJasper);
		} catch (Exception jre) {
			jre.printStackTrace();
		}

		try {

			byte[] pdfInspecao = null;
			// Gera o pdf para exibicao..
			try {
				conn = Connection.getConnection();
				pdfInspecao = JasperRunManager.runReportToPdf(jasperReport, parametros, conn);
			} catch (Exception jre) {
				jre.printStackTrace();
			}

			// Parametros para nao fazer cache e o que será exibido..
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=pecas-discrepantes.pdf");

			// Envia para o navegador o pdf..
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(pdfInspecao);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn != null){				
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/*EntityManager manager = JpaUtil.getInstance();
	query = manager.createNativeQuery("SELECT s.id, s.numero_segmento FROM ge_segmento s WHERE s.id_checkin = " + idCheckin);

	List<Object[]> listSeg = (List<Object[]>) query.getResultList();

	List<PecasBean> listPecasDescontinuadas = new ArrayList<PecasBean>();
	List<PecasBean> listPecasSubstituidas = new ArrayList<PecasBean>();
	List<PecasBean> listPecasDesmembradasdas = new ArrayList<PecasBean>();
	List<PecasBean> listPecasSemClassificacaoFiscal = new ArrayList<PecasBean>();
	
	//Peças substituída, desmembrada ou descontinuada
	for (Object[] objSeg : listSeg) {
		query = manager.createNativeQuery("SELECT mp.sos1, mp.pano20, mp.clsfcl, mp.rppano, mp.qty2, mp.rplsos, mp.rlpacd, mp.id FROM ge_pecas p " +
				"INNER JOIN ge_master_pecas mp ON mp.pano20 = p.part_no " +
				"WHERE p.id_doc_seg_oper = " +
				"	(" +
				"		SELECT MAX(dso.id) FROM ge_doc_seg_oper dso " +
				"		WHERE dso.id_segmento = " + (BigDecimal) objSeg[0] + ") AND mp.rlpacd = 4");

		List<Object[]> listPecasGeral = (List<Object[]>) query.getResultList();
		
		for (Object[] objPeca : listPecasGeral) {
			if (objPeca[3] == null || objPeca[3].equals("")) { //campo "rppano"
				//Peça descontinuada
			} else {
				query = manager.createNativeQuery("SELECT mp.sos1, mp.pano20, mp.clsfcl, mp.rppano, mp.qty2, mp.rplsos, mp.rlpacd, mp.id FROM ge_master_pecas mp WHERE mp.pano20 = " + (String) objSeg[3] + " AND mp.rppano IS NOT NULL AND mp.rppano <> ''");
				
				List<Object[]> listPecas = (List<Object[]>) query.getResultList();
				
				if(listPecas.size() == 1) {
					//Peça substituída
				} else {
					//Peça desmembrada
				}
			}
		}
	}
	
	//Peça que não possue classificação fiscal
	for (Object[] objSeg : listSeg) {
		query = manager.createNativeQuery("SELECT mp.sos1, mp.pano20, mp.clsfcl, mp.rppano, mp.qty2, mp.rplsos, mp.rlpacd, mp.id FROM ge_pecas p " +
				"INNER JOIN ge_master_pecas mp ON mp.pano20 = p.part_no " +
				"WHERE p.id_doc_seg_oper = (SELECT MAX(dso.id) FROM ge_doc_seg_oper dso WHERE dso.id_segmento = " + (BigDecimal) objSeg[0] + ") AND " +
				"(mp.clsfcl = '' OR mp.clsfcl IS NULL OR mp.clsfcl = '00000000' OR mp.clsfcl = '99999999')");
		
		List<Object[]> listPecas = (List<Object[]>) query.getResultList();
		
		for (Object[] objPeca : listPecas) {
			
		}
	}*/	
	
	
}

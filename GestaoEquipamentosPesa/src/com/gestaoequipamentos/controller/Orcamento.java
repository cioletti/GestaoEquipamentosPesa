package com.gestaoequipamentos.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import antlr.collections.List;

import com.gestaoequipamentos.bean.PrecoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GePreco;
import com.gestaoequipamentos.util.Connection;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.ValorMonetarioHelper;

import flex.messaging.FlexContext;

/**
 * Servlet implementation class PropostaManutencao
 */
public class Orcamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orcamento() {
        super();
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
		//String idPreco  = request.getParameter("idPreco");
		//String qtdComp = request.getParameter("qtdComp");
		//String[] quantidadeComp = qtdComp.split(",");
		String nomeCliente  = request.getParameter("nomeCliente");
		String end  = request.getParameter("end");
		//String resultado  = request.getParameter("resultado");
		//String valorUnitario = request.getParameter("valorUnitario");
		//String[] vUnitario = valorUnitario.split(";");
		String modelo = request.getParameter("modelo");
		String prefixo = request.getParameter("prefixo");
		
		Map<Long, Long> mapQtdItens = new HashMap<Long, Long>();
//		String [] precoItem = idPreco.split("##");
//		String idPrecoTratato = "";
//		for (String preco : precoItem) {
//			String aux [] = preco.split(",");
//			idPrecoTratato += aux[0]+",";
//			mapQtdItens.put(Long.valueOf(aux[0]), Long.valueOf(aux[1]));
//		}
		
		UsuarioBean usuarioBean = (UsuarioBean)request.getSession().getAttribute("usuario");
		int filial = Integer.valueOf(usuarioBean.getFilial());
		String telefone = usuarioBean.getTelefone();
		
		JasperReport jasperReport = null;  
		byte[] pdfInspecao = null;    

		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/orcamento/orcamento.jasper"); 
		String pathSubreport = servletContext.getRealPath("WEB-INF/orcamento/")+"/"; 

		//Carrega o arquivo com o jasperReport  
		try {  
			jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
		} catch (Exception jre) {  
			jre.printStackTrace();  
		}  
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select STNM from tw_filial t where t.STNO ="+ filial);//
//			
			Map parametros = new HashMap(); 			
						
			parametros.put("SUBREPORT_DIR", pathSubreport);  
			Object object = query.getSingleResult();
			parametros.put("FILIAL", object.toString().trim());
			parametros.put("TELEFONE", telefone);
			//parametros.put("ID_PECAS",   Arrays.asList(idPreco.split(","))) ; 
			parametros.put("NOME_CLIENTE", nomeCliente); 
			parametros.put("ENDERECO", end); 
			
			parametros.put("MODELO", modelo); 
			parametros.put("PREFIXO", prefixo); 
			
			query = manager.createNativeQuery("SELECT GE_PRECO.job_code,GE_PRECO.descricao_job_code,GE_PRECO.comp_code,GE_PRECO.descricao_comp_code,GE_PRECO.ID"+									
									", orc.QTD, ORC.VALOR_UNITARIO FROM GE_PRECO GE_PRECO, GE_ORCAMENTO orc"+
									" WHERE GE_PRECO.ID = orc.ID_PRECO" +
									" and orc.ID_FUNCIONARIO =:ID_FUNCIONARIO");
			query.setParameter("ID_FUNCIONARIO", usuarioBean.getMatricula());
			
			java.util.List<Object[]> result = query.getResultList();
			java.util.Collection<PrecoBean> collection = new ArrayList<PrecoBean>();
			int i = 0;
			for(Object[] pair : result){
				PrecoBean precoBean = new PrecoBean();
				precoBean.setJobCode(pair[0].toString());
				precoBean.setCompCode(pair[2].toString());
				precoBean.setDescricaoJobCode(pair[1].toString());
				precoBean.setDescricaoCompCode(pair[3].toString());
				precoBean.setQtdItens(((BigDecimal)pair[5]).intValue());
				precoBean.setValor("R$ "+ValorMonetarioHelper.formata("###,###,##0.00", ((BigDecimal)pair[6]).doubleValue()));
				collection.add(precoBean);
				i++;
				
			}
			
			query = manager.createNativeQuery("SELECT  sum(ORC.VALOR_UNITARIO) FROM GE_PRECO GE_PRECO, GE_ORCAMENTO orc"+
					" WHERE GE_PRECO.ID = orc.ID_PRECO" +
					" and orc.ID_FUNCIONARIO =:ID_FUNCIONARIO");
			query.setParameter("ID_FUNCIONARIO", usuarioBean.getMatricula());
			BigDecimal totalContrato = (BigDecimal)query.getSingleResult();

			parametros.put("TOTAL_ORCAMENTO", "R$ "+ValorMonetarioHelper.formata("###,###,##0.00", totalContrato.doubleValue())); 
			
			
			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("logo.jpg");
			File img = File.createTempFile("img", "gif",new File("."));
			OutputStream out=new FileOutputStream(img);
			byte buf[]=new byte[1024];
			int len;
			while((len=inputStream.read(buf))>0)
				out.write(buf,0,len);
			out.close();
			inputStream.close();
			
			parametros.put("logo", img);			
			parametros.put("orcamento", new JRBeanCollectionDataSource(collection));			
			
			//Gera o pdf para exibicao..  
			try {  
				pdfInspecao = JasperRunManager.runReportToPdf( jasperReport, parametros, new JREmptyDataSource());  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  

			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/pdf" );  
			response.setHeader("Content-disposition",
			"attachment; filename=orcamento.pdf" ); 

			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( pdfInspecao );  
			servletOutputStream.flush();  
			servletOutputStream.close();
			img.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}

}

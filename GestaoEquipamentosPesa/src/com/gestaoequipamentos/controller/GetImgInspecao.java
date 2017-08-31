package com.gestaoequipamentos.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

/**
 * Servlet implementation class GetImgInspecao
 */
public class GetImgInspecao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImgInspecao() {
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
		String idFotoInspecao = request.getParameter("idFotoInspecao");
		Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		try {

			con = com.gestaoequipamentos.util.Connection.getConnection();
			String sql = "select foto from ge_foto_inspecao where id_foto_inspecao ="+idFotoInspecao;
			pstmt= con.prepareStatement(sql);
			java.sql.ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next())return ;
			byte[] fileBytes = rs.getBytes(1);
			OutputStream os = response.getOutputStream();
		
			os.write(fileBytes);
			os.flush();
			os.close();
			
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

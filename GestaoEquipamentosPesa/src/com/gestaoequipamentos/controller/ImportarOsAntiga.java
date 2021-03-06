package com.gestaoequipamentos.controller;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.Connection;
import com.gestaoequipamentos.util.IConstantAccess;

public class ImportarOsAntiga {
	private static Integer FILIAL = 0;
	private static String idFuncionario = "F003";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.sql.Connection conOracle = Connection.getConnection();
		java.sql.Connection conDbs = ConectionDbs.getConnecton();
		String SQL_OS_PALM = "SELECT WOPHDRS0.WONO, WOPHDRS0.CUNM, WOPHDRS0.EQMFCD, WOPHDRS0.EQMFMD, WOPHDRS0.EQMFSN, WOPHDRS0.OPNDT8, WOPHDRS0.SVMTHR"+
							 " FROM B108F034."+IConstantAccess.LIB_DBS+".WOPHDRS0 WOPHDRS0, B108F034."+IConstantAccess.LIB_DBS+".WOPSEGS0 WOPSEGS0"+
							 " WHERE WOPHDRS0.WONO = WOPSEGS0.WONO AND ((WOPHDRS0.ACTI='O') AND (WOPHDRS0.STNO="+FILIAL+") AND (WOPHDRS0.RESPAR Not Like 'R%') AND (WOPSEGS0.SHPFLD='S') AND (WOPSEGS0.BGRP not in ('CONS', 'PM')) AND (WOPSEGS0.WOSGNO='01')) order by WOPHDRS0.WONO";

		String SQL_CHECK_IN = "SELECT USPWOHD0.WONO, USPWOHD0.CUSTNO, USPWOHD0.STATUS, USPWOHD0.CONTA, WOPSEGS0.LBCUNO, WOPSEGS0.WARCLI, WOPSEGS0.WATPCD,USPWOHD0.DTAI,"+
							  "	USPWOHD0.DTOC, USPWOHD0.DTNG, USPWOHD0.DTAP, USPWOHD0.DTEX, USPWOHD0.DTCO,USPWOHD0.DTFC"+ 
							  "	FROM  B108F034."+IConstantAccess.LIB_DBS+".WOPSEGS0 WOPSEGS0 LEFT OUTER JOIN B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 USPWOHD0 ON WOPSEGS0.WONO = USPWOHD0.WONO"+
							  "	WHERE USPWOHD0.STORE="+FILIAL+" AND WOPSEGS0.SHPFLD='S' AND WOPSEGS0.ACTI='O' AND WOPSEGS0.WOSGNO='01' AND WOPSEGS0.BGRP not in ('CONS', 'PM') " +
							  " AND USPWOHD0.WONO = ";
		String SQL_SEGMENTO = "SELECT WOPSEGS0.WONO, WOPSEGS0.WOSGNO, WOPSEGS0.JBCD, WOPSEGS0.CPTCD, WOPSEGS0.FRSTHR, WOPSEGS0.CSCC, WOPSEGS0.QTY4"+
							  "	FROM B108F034."+IConstantAccess.LIB_DBS+".WOPHDRS0 WOPHDRS0, B108F034."+IConstantAccess.LIB_DBS+".WOPSEGS0 WOPSEGS0"+
							  "	WHERE WOPHDRS0.WONO = WOPSEGS0.WONO AND WOPSEGS0.SHPFLD='S' AND WOPSEGS0.ACTI='O' AND WOPHDRS0.RESPAR Not Like 'R%' AND WOPHDRS0.BGRP not in ('CONS', 'PM') AND WOPHDRS0.STNO="+FILIAL+
							  "	AND WOPSEGS0.WONO =";
		String SQL_OPERACAO = "SELECT WOPOPER0.WONO, WOPOPER0.WOSGNO, WOPOPER0.WOOPNO, WOPOPER0.JBCD, WOPOPER0.CPTCD"+
							  "	FROM B108F034."+IConstantAccess.LIB_DBS+".WOPHDRS0 WOPHDRS0, B108F034."+IConstantAccess.LIB_DBS+".WOPOPER0 WOPOPER0"+
							  "	WHERE WOPHDRS0.EQMFCD = WOPOPER0.EQMFCD AND WOPHDRS0.WONO = WOPOPER0.WONO AND ((WOPOPER0.ACTI='O') AND (WOPHDRS0.STNO=0))"+
							  "	AND WOPOPER0.WONO =";	
		String SQL_DOC_SEG_OPER = "SELECT PCPCOHD0.WONO, PCPCOHD0.WOSGNO, PCPCOHD0.WOOPNO, PCPCOHD0.RFDCNO"+
								  "	FROM B108F034."+IConstantAccess.LIB_DBS+".PCPCOHD0 PCPCOHD0"+
								  "	WHERE PCPCOHD0.STNO=0"+
								  "	AND SUBSTR(PCPCOHD0.RFDCNO,1,3) = 'FFE'" +
								  " AND PCPCOHD0.WONO = ";
		String SQL_PECAS = "SELECT PCPCOPD0.ORQY, PCPCOPD0.BOIMCT, PCPCOPD0.SOS1, PCPCOPD0.PANO20, PCPCOPD0.DS18, PCPCOPD0.UNSEL, (unsel*orqy) TOTAL"+
							" FROM B108F034."+IConstantAccess.LIB_DBS+".PCPCOPD0 PCPCOPD0"+
							" WHERE PCPCOPD0.RFDCNO=";
		
		String SQL_FATOR_CLIENTE = "select crm.cust_codigosegmento from MME.vw_crmsegmentoscontas_ds crm where  crm.cuno = ";
		Statement stOracle = null;
		ResultSet rsOracle = null;
		Statement stOracle01 = null;
		ResultSet rsOracle01 = null;
		Statement stDbs = null;
		ResultSet rsDbs = null;
		Statement stDbs01 = null;
		ResultSet rsDbs01 = null;
		Statement stDbs02 = null;
		ResultSet rsDbs02 = null;
		Statement stDbs03 = null;
		ResultSet rsDbs03 = null;
		Statement stDbs04 = null;
		ResultSet rsDbs04 = null;
		Statement stDbs05 = null;
		ResultSet rsDbs05 = null;
		try {
			
			conOracle.setAutoCommit(false);
			//rsOracle = stOracle.executeQuery(SQL_OS_PALM);
			stDbs = conDbs.createStatement();
			rsDbs = stDbs.executeQuery(SQL_OS_PALM);
			//SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			String pair = "";
			//inclusão na tabela ge_os_palm
			stOracle = conOracle.createStatement();
			stOracle01 = conOracle.createStatement();
			stDbs01 = conDbs.createStatement();
			stDbs02 = conDbs.createStatement();
			stDbs03 = conDbs.createStatement();
			stDbs04 = conDbs.createStatement();
			stDbs05 = conDbs.createStatement();
			while(rsDbs.next()){
				String modelo = rsDbs.getString("EQMFMD").trim();
				if(modelo.equals("320C-00")){
					modelo = modelo.replaceAll("-00", "");
				}
				modelo = modelo.replaceAll("-", "");
				rsOracle = stOracle.executeQuery("select GE_OS_PALM_SEQ.Nextval seq from dual");
				rsOracle.next();
				Long idGeOsPalm = rsOracle.getLong("seq");
				
				rsOracle = stOracle.executeQuery(" select arv.id_familia from ge_arv_inspecao arv"+
								" where arv.descricao = '"+modelo+"' and arv.id_pai is null");
				System.out.println(modelo+" - "+rsDbs.getString("WONO").trim());
				rsOracle.next();
				Long idFamilia = rsOracle.getLong("id_familia");
				
				pair = idGeOsPalm+", 'CHECKIN','"+rsDbs.getString("CUNM").trim()+"','"+modelo+"','"+rsDbs.getString("EQMFSN").trim()+"','"+rsDbs.getString("SVMTHR").trim()+"'," +
						"'Hora','AONTONIO EVANILDO OLIVEIRA DA SILVA','"+FILIAL+"',to_date('"+rsDbs.getString("OPNDT8").trim()+"','yyyyMMdd'),'"+rsDbs.getString("SVMTHR").trim()+"','GE','N','"+idFuncionario+"',"+idFamilia+",'GE','"+modelo+"'";
				stOracle.executeUpdate("insert into ge_os_palm (idos_palm, tipo_operacao, cliente, modelo, serie, smu, un_medida, tecnico, filial, emissao, horimetro, tipoinspecao, trocar_pecas, id_funcionario, id_familia,"+
										" tipo_manutencao, equipamento) values ("+pair+")");
				
				
				
				rsOracle01 = stOracle01.executeQuery("select GE_CHECK_IN_SEQ.Nextval seq from dual");
				rsOracle01.next();
				Long idCheckIn = rsOracle01.getLong("seq");
				
				
				rsDbs01 = stDbs01.executeQuery(SQL_CHECK_IN+"'"+rsDbs.getString("WONO").trim()+"'");
				//inclusão na tabela ge_check_in
				
				while(rsDbs01.next()){
					
					rsOracle01 =  stOracle01.executeQuery(SQL_FATOR_CLIENTE+"'"+rsDbs01.getString("CUSTNO").trim()+"'");
					rsOracle01.next();
					String segmentoCliente = rsOracle01.getString("cust_codigosegmento");
					
					rsOracle01 =  stOracle01.executeQuery("select seg.fator from ge_segmento_cliente seg where seg.codigo = "+"'"+segmentoCliente+"'");
					rsOracle01.next();
					
					BigDecimal fatorCliente = rsOracle01.getBigDecimal("fator");
					
					
					rsOracle01 = stOracle01.executeQuery("select GE_SEGMENTO_SEQ.NEXTVAL seq from dual");
					rsOracle01.next();						
					
					
					//BigDecimal fatorCliente = BigDecimal.valueOf(1.5D);
					
					String hasSendDbs = rsDbs01.getString("DTAP").trim().length() > 1?"S":"N";
					String hasSendDataOrcamento = rsDbs01.getString("DTOC").trim().length() > 1?"S":"N";
					String hasSendDataAprovacao = rsDbs01.getString("DTAP").trim().length() > 1?"S":"N";
					String hasSendDataPrevisao = rsDbs01.getString("DTEX").trim().length() > 1?"S":"N";
					pair = idCheckIn+","+idGeOsPalm+",'"+rsDbs.getString("WONO").trim()+"','1050040','"+rsDbs01.getString("CUSTNO").trim()+"'," +
							"'N','"+hasSendDbs+"','"+hasSendDataOrcamento+"','N',"+fatorCliente+",'"+hasSendDataAprovacao+"','"+hasSendDataPrevisao+"','"+hasSendDbs+"',"+((rsDbs01.getString("LBCUNO") != null && rsDbs01.getString("LBCUNO").trim().length() > 0)?"'INT'":"'EXT'")+"," +
									((rsDbs01.getString("LBCUNO") != null && rsDbs01.getString("LBCUNO").trim().length() > 0)?"'"+rsDbs01.getString("LBCUNO")+"'":null)+","
									+((rsDbs01.getString("CONTA") != null && rsDbs01.getString("CONTA").trim().length() > 0)?"'"+rsDbs01.getString("CONTA").trim().split("          ")[0]+"'":null)+","
									+((rsDbs01.getString("CONTA") != null && rsDbs01.getString("CONTA").trim().length() > 0)?"'"+rsDbs01.getString("CONTA").trim().split("          ")[1]+"'":null)+","
									+((rsDbs01.getString("WATPCD") != null && rsDbs01.getString("WATPCD").trim().length() > 0)?"'"+rsDbs01.getString("WATPCD").trim()+"'":null)+",30,22,to_date('"+rsDbs.getString("OPNDT8").trim()+"','yyyyMMdd')";
					
					
					stOracle01.executeUpdate("insert into ge_check_in (id, id_os_palm, numero_os, id_supervisor, cod_cliente, is_search_dbs, has_send_dbs, has_send_data_orcamento, fator_urgencia, fator_cliente, has_send_data_aprovacao, has_send_data_previsao,"+
											" has_send_pecas_dbs, tipo_cliente, cliente_inter,  conta_contabil_sigla, centro_custo_sigla, sigla_indicador_garantia, validade_proposta, id_tipo_frete, data_emissao)"+
											" values ("+pair+")");
					//inclusão na tabela ge_situacao_os
					String data_inicio = (rsDbs01.getString("DTAI").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTAI").trim().length() == 8)?rsDbs01.getString("DTAI").trim():"0"+rsDbs01.getString("DTAI").trim())+"','ddMMyyyy')":null;
					String data_orcamento = (rsDbs01.getString("DTOC").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTOC").trim().length() == 8)?rsDbs01.getString("DTOC").trim():"0"+rsDbs01.getString("DTOC").trim())+"','ddMMyyyy')":null;
					String data_negociacao = (rsDbs01.getString("DTNG").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTNG").trim().length() == 8)?rsDbs01.getString("DTNG").trim():"0"+rsDbs01.getString("DTNG").trim())+"','ddMMyyyy')":null;
					String data_aprovacao = (rsDbs01.getString("DTAP").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTAP").trim().length() == 8)?rsDbs01.getString("DTAP").trim():"0"+rsDbs01.getString("DTAP").trim())+"','ddMMyyyy')":null;
					String data_previsao_entrega = (rsDbs01.getString("DTEX").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTEX").trim().length() == 8)?rsDbs01.getString("DTEX").trim():"0"+rsDbs01.getString("DTEX").trim())+"','ddMMyyyy')":null;
					String data_conclusao_servico = (rsDbs01.getString("DTCO").length() > 1)?"to_date('"+((rsDbs01.getString("DTCO").trim().length() == 8)?rsDbs01.getString("DTCO").trim():"0"+rsDbs01.getString("DTCO").trim())+"','ddMMyyyy')":null;
					String data_faturamento = (rsDbs01.getString("DTFC").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTFC").trim().length() == 8)?rsDbs01.getString("DTFC").trim():"0"+rsDbs01.getString("DTFC").trim())+"','ddMMyyyy')":null;
					pair = "ge_situacao_os_seq.nextval, '"+rsDbs.getString("WONO").trim()+"',"+data_inicio+","+data_orcamento+","+data_negociacao+","+data_aprovacao+","+data_previsao_entrega+","+data_conclusao_servico+","+data_faturamento+","+FILIAL+","+idCheckIn;
					stOracle01.executeUpdate("insert into ge_situacao_os (id , numero_os, data_inicio, data_orcamento, data_negociacao, data_aprovacao, data_previsao_entrega, data_conclusao_servico, data_faturamento, filial, id_checkin)"+
											" values("+pair+")");
					//Inserção dos segmentos da os
					rsDbs02 = stDbs02.executeQuery(SQL_SEGMENTO+"'"+rsDbs.getString("WONO").trim()+"' ORDER BY WOPSEGS0.WOSGNO");
					//String hasSendPecasDbs = "N";
					while(rsDbs02.next()){
						String jbcd = rsDbs02.getString("JBCD").trim();
						String cpcd = rsDbs02.getString("CPTCD").trim();
						System.out.println("Segmento - "+rsDbs02.getString("WOSGNO").trim()+" jbcd "+jbcd+" cptcd "+cpcd);
						rsOracle01 = stOracle01.executeQuery("select jbcdds from jbcd where jbcd = '"+jbcd+"'");
						rsOracle01.next();
						String jbcdds= rsOracle01.getString("jbcdds");

						rsOracle01 = stOracle01.executeQuery("select cptcdd from cptcd where cptcd = '"+cpcd+"'");
						rsOracle01.next();						
						String cpcdds= rsOracle01.getString("cptcdd");
						
						Double horasPrevista = rsDbs02.getDouble("FRSTHR");
						
						
						rsOracle01 = stOracle01.executeQuery("select GE_SEGMENTO_SEQ.NEXTVAL seq from dual");
						rsOracle01.next();						
						Long idSegmento = rsOracle01.getLong("seq");
						
						pair = idSegmento+",'"+jbcd+"','"+jbcd+" - "+jbcdds+"','"+cpcd+"','"+cpcd+" - "+cpcdds+"',"+idCheckIn+","+horasPrevista+",'"+rsDbs02.getString("CSCC").trim()+"','S',"+rsDbs02.getInt("QTY4")+",'"+rsDbs02.getString("WOSGNO").trim()+"'";
						stOracle01.executeUpdate("insert into ge_segmento (id, job_code, descricao_job_code, com_code, descricao_comp_code, id_checkin, horas_prevista, job_control, has_send_dbs, qtd_comp, numero_segmento)"+
												" values("+pair+")");
						
						//Inserção das operações do segmento
						rsDbs03 = stDbs03.executeQuery(SQL_OPERACAO+"'"+rsDbs.getString("WONO").trim()+"' AND WOPOPER0.WOSGNO = '"+rsDbs02.getString("WOSGNO").trim()+"' ORDER BY WOPOPER0.WOOPNO");
						while(rsDbs03.next()){
							jbcd = rsDbs03.getString("JBCD").trim();
							cpcd = rsDbs03.getString("CPTCD").trim();
							
							System.out.println("Operação - "+rsDbs03.getString("WOOPNO").trim()+" jbcd "+jbcd+" cptcd "+cpcd);
							
							rsOracle01 = stOracle01.executeQuery("select jbcdds from jbcd where jbcd = '"+jbcd+"'");
							rsOracle01.next();
							jbcdds= rsOracle01.getString("jbcdds");

							rsOracle01 = stOracle01.executeQuery("select cptcdd from cptcd where cptcd = '"+cpcd+"'");
							rsOracle01.next();						
							cpcdds= rsOracle01.getString("cptcdd");
							
							rsOracle01 = stOracle01.executeQuery("select ge_operacao_seq.nextval seq from dual");
							rsOracle01.next();						
							Long idOperacao= rsOracle01.getLong("seq");
							
							pair = idOperacao+",'"+jbcd+"','"+jbcd+" - "+jbcdds+"','"+cpcd+"','"+cpcd+" - "+cpcdds+"','"+rsDbs03.getString("WOOPNO").trim()+"',"+idSegmento;
							stOracle01.executeUpdate("insert into ge_operacao (id, jbcd, descricao_jbcd, cptcd, descricao_comp_code, num_operacao, id_segmento)"+
													" values("+pair+")");
							//Inserção da ge_doc_seg_oper com segmento e operação
							rsDbs04 = stDbs04.executeQuery(SQL_DOC_SEG_OPER+"'"+rsDbs.getString("WONO").trim()+"' AND PCPCOHD0.WOSGNO = '"+rsDbs03.getString("WOSGNO").trim()+
									"' AND PCPCOHD0.WOOPNO = '"+rsDbs03.getString("WOOPNO").trim()+"' order by PCPCOHD0.WONO, PCPCOHD0.RFDCNO");
							while(rsDbs04.next()){
								rsOracle01 = stOracle01.executeQuery("select ge_doc_seg_oper_seq.nextval seq from dual");
								rsOracle01.next();						
								Long idDocSegOper= rsOracle01.getLong("seq");
								pair = idDocSegOper+","+idSegmento+","+idOperacao+",'"+rsDbs04.getString("RFDCNO").trim()+"'";
								stOracle01.executeUpdate("insert into ge_doc_seg_oper (id, id_segmento, id_operacao, num_doc)"+
														" values("+pair+")");
								//importação das peças para cada operação
								rsDbs05 = stDbs05.executeQuery(SQL_PECAS+"'"+rsDbs04.getString("RFDCNO").trim()+"'");
								while(rsDbs05.next()){
									System.out.println("Pecas - "+rsDbs05.getString("PANO20").trim()+" RFDCNO "+rsDbs04.getString("RFDCNO").trim());
									pair = "ge_pecas_seq.nextval,'"+rsDbs05.getString("PANO20").trim()+"','"+rsDbs05.getString("DS18").trim()+"',"+rsDbs05.getInt("ORQY")+","+idDocSegOper+","+rsDbs05.getDouble("UNSEL")+","+rsDbs05.getDouble("TOTAL")+",'"+rsDbs05.getString("SOS1")+"',"+rsDbs05.getInt("BOIMCT");
									stOracle01.executeUpdate("insert into ge_pecas (id, part_no, part_name, qtd, id_doc_seg_oper, valor, valor_total,sos1, qtd_nao_atendido)"+
															" values("+pair+")");
									//hasSendPecasDbs = "S";
								}
							}
						}
						
						//Inserção da ge_doc_seg_oper com segmento
						rsDbs04 = stDbs04.executeQuery(SQL_DOC_SEG_OPER+"'"+rsDbs.getString("WONO").trim()+"' AND PCPCOHD0.WOSGNO = '"+rsDbs02.getString("WOSGNO").trim()+
								"' AND PCPCOHD0.WOOPNO = '' order by PCPCOHD0.WONO, PCPCOHD0.RFDCNO");
						while(rsDbs04.next()){		
							rsOracle01 = stOracle01.executeQuery("select ge_doc_seg_oper_seq.nextval seq from dual");
							rsOracle01.next();						
							Long idDocSegOper= rsOracle01.getLong("seq");
							pair = idDocSegOper+","+idSegmento+",'"+rsDbs04.getString("RFDCNO").trim()+"'";
							stOracle01.executeUpdate("insert into ge_doc_seg_oper (id, id_segmento, num_doc)"+
													" values("+pair+")");
							//importação das peças para cada segmento
							rsDbs05 = stDbs05.executeQuery(SQL_PECAS+"'"+rsDbs04.getString("RFDCNO").trim()+"'");
							while(rsDbs05.next()){
								System.out.println("Pecas - "+rsDbs05.getString("PANO20").trim()+" RFDCNO "+rsDbs04.getString("RFDCNO").trim());
								pair = "ge_pecas_seq.nextval,'"+rsDbs05.getString("PANO20").trim()+"','"+rsDbs05.getString("DS18").trim()+"',"+rsDbs05.getInt("ORQY")+","+idDocSegOper+","+rsDbs05.getDouble("UNSEL")+","+rsDbs05.getDouble("TOTAL")+",'"+rsDbs05.getString("SOS1")+"',"+rsDbs05.getInt("BOIMCT");
								stOracle01.executeUpdate("insert into ge_pecas (id, part_no, part_name, qtd, id_doc_seg_oper, valor, valor_total,sos1, qtd_nao_atendido)"+
														" values("+pair+")");
								//hasSendPecasDbs = "S";
							}
						}
						
					}
//					if(hasSendDbs.equals("S")){
//						stOracle01.executeUpdate("update ge_check_in c set c.has_send_pecas_dbs = 'S' where c.id = "+idCheckIn);
//					}
					//break;
				}
				
				
			}
			conOracle.commit();
			System.out.println("Importação realizada com sucesso!");
			
		} catch (Exception e) {
			try {
				conOracle.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				conOracle.close();
				conDbs.close();
				stOracle.close();
				rsOracle.close();
				stOracle01.close();
				rsOracle01.close();
				stDbs.close();
				rsDbs.close();
				rsDbs01.close();
				stDbs01.close();
				rsDbs02.close();
				stDbs02.close();
				rsDbs03.close();
				stDbs03.close();
				rsDbs04.close();
				stDbs04.close();
				rsDbs05.close();
				stDbs05.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
//	public static void main(String[] args) {
//		
//			java.sql.Connection conOracle = com.gestaoequipamentos.util.Connection.getConnection();
//			java.sql.Connection conDbs = ConectionDbs.getConnecton();
//			String SQL_OS_PALM = "SELECT WOPHDRS0.WONO, WOPHDRS0.CUNM, WOPHDRS0.EQMFCD, WOPHDRS0.EQMFMD, WOPHDRS0.EQMFSN, WOPHDRS0.OPNDT8, WOPHDRS0.SVMTHR"+
//								 " FROM B108F034.LIBU15.WOPHDRS0 WOPHDRS0, B108F034.LIBU15.WOPSEGS0 WOPSEGS0"+
//								 " WHERE WOPHDRS0.WONO = WOPSEGS0.WONO AND ((WOPHDRS0.ACTI='O') AND (WOPHDRS0.STNO="+FILIAL+") AND (WOPHDRS0.RESPAR Not Like 'R%') AND (WOPSEGS0.SHPFLD='S') AND (WOPSEGS0.BGRP not in ('CONS', 'PM')) AND (WOPSEGS0.WOSGNO='01')) " +
//								 		" order by WOPHDRS0.WONO";
//
//			String SQL_CHECK_IN = "SELECT USPWOHD0.WONO, USPWOHD0.CUSTNO, USPWOHD0.STATUS, USPWOHD0.CONTA, WOPSEGS0.LBCUNO, WOPSEGS0.WARCLI, WOPSEGS0.WATPCD,USPWOHD0.DTAI,"+
//								  "	USPWOHD0.DTOC, USPWOHD0.DTNG, USPWOHD0.DTAP, USPWOHD0.DTEX, USPWOHD0.DTCO,USPWOHD0.DTFC"+ 
//								  "	FROM  B108F034.LIBU15.WOPSEGS0 WOPSEGS0 LEFT OUTER JOIN B108F034.LIBU15.USPWOHD0 USPWOHD0 ON WOPSEGS0.WONO = USPWOHD0.WONO"+
//								  "	WHERE USPWOHD0.STORE="+FILIAL+" AND WOPSEGS0.SHPFLD='S' AND WOPSEGS0.ACTI='O' AND WOPSEGS0.WOSGNO='01' AND WOPSEGS0.BGRP not in ('CONS', 'PM') " +
//								  " AND USPWOHD0.WONO = ";
//			String SQL_SEGMENTO = "SELECT WOPSEGS0.WONO, WOPSEGS0.WOSGNO, WOPSEGS0.JBCD, WOPSEGS0.CPTCD, WOPSEGS0.SJFRHR, WOPSEGS0.CSCC, WOPSEGS0.QTY4"+
//								  "	FROM B108F034.LIBU15.WOPHDRS0 WOPHDRS0, B108F034.LIBU15.WOPSEGS0 WOPSEGS0"+
//								  "	WHERE WOPHDRS0.WONO = WOPSEGS0.WONO AND WOPSEGS0.SHPFLD='S' AND WOPSEGS0.ACTI='O' AND WOPHDRS0.RESPAR Not Like 'R%' AND WOPHDRS0.BGRP not in ('CONS', 'PM') AND WOPHDRS0.STNO="+FILIAL+
//								  "	AND WOPSEGS0.WONO =";
//			String SQL_OPERACAO = "SELECT WOPOPER0.WONO, WOPOPER0.WOSGNO, WOPOPER0.WOOPNO, WOPOPER0.JBCD, WOPOPER0.CPTCD"+
//								  "	FROM B108F034.LIBU15.WOPHDRS0 WOPHDRS0, B108F034.LIBU15.WOPOPER0 WOPOPER0"+
//								  "	WHERE WOPHDRS0.EQMFCD = WOPOPER0.EQMFCD AND WOPHDRS0.WONO = WOPOPER0.WONO AND ((WOPOPER0.ACTI='O') AND (WOPHDRS0.STNO=0))"+
//								  "	AND WOPOPER0.WONO =";	
//			String SQL_DOC_SEG_OPER = "SELECT PCPCOHD0.WONO, PCPCOHD0.WOSGNO, PCPCOHD0.WOOPNO, PCPCOHD0.RFDCNO"+
//									  "	FROM B108F034.LIBU15.PCPCOHD0 PCPCOHD0"+
//									  "	WHERE PCPCOHD0.STNO=0"+
//									  "	AND SUBSTR(PCPCOHD0.RFDCNO,1,3) = 'FFE'" +
//									  " AND PCPCOHD0.WONO = ";
//			String SQL_PECAS = "SELECT PCPCOPD0.ORQY, PCPCOPD0.BOIMCT, PCPCOPD0.SOS1, PCPCOPD0.PANO20, PCPCOPD0.DS18, PCPCOPD0.UNSEL, (unsel*orqy) TOTAL"+
//								" FROM B108F034.LIBU15.PCPCOPD0 PCPCOPD0"+
//								" WHERE PCPCOPD0.RFDCNO=";
//			
//			String SQL_FATOR_CLIENTE = "select crm.cust_codigosegmento from MME.vw_crmsegmentoscontas_ds crm where  crm.cuno = ";
//			Statement stOracle = null;
//			ResultSet rsOracle = null;
//			Statement stOracle01 = null;
//			ResultSet rsOracle01 = null;
//			Statement stDbs = null;
//			ResultSet rsDbs = null;
//			Statement stDbs01 = null;
//			ResultSet rsDbs01 = null;
//			Statement stDbs02 = null;
//			ResultSet rsDbs02 = null;
//			Statement stDbs03 = null;
//			ResultSet rsDbs03 = null;
//			Statement stDbs04 = null;
//			ResultSet rsDbs04 = null;
//			Statement stDbs05 = null;
//			ResultSet rsDbs05 = null;
//			try {
//				
//				conOracle.setAutoCommit(false);
//				//rsOracle = stOracle.executeQuery(SQL_OS_PALM);
//				stDbs = conDbs.createStatement();
//				rsDbs = stDbs.executeQuery(SQL_OS_PALM);
//				//SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//				String pair = "";
//				//inclusão na tabela ge_os_palm
//				stOracle = conOracle.createStatement();
//				stOracle01 = conOracle.createStatement();
//				stDbs01 = conDbs.createStatement();
//				stDbs02 = conDbs.createStatement();
//				stDbs03 = conDbs.createStatement();
//				stDbs04 = conDbs.createStatement();
//				stDbs05 = conDbs.createStatement();
//				while(rsDbs.next()){
////					String modelo = rsDbs.getString("EQMFMD").trim();
////					if(modelo.equals("320C-00")){
////						modelo = modelo.replaceAll("-00", "");
////					}
////					modelo = modelo.replaceAll("-", "");
////					rsOracle = stOracle.executeQuery("select GE_OS_PALM_SEQ.Nextval seq from dual");
////					rsOracle.next();
////					Long idGeOsPalm = rsOracle.getLong("seq");
////					
////					rsOracle = stOracle.executeQuery(" select arv.id_familia from ge_arv_inspecao arv"+
////									" where arv.descricao = '"+modelo+"' and arv.id_pai is null");
//					System.out.println(rsDbs.getString("WONO").trim());
////					rsOracle.next();
////					Long idFamilia = rsOracle.getLong("id_familia");
////					
////					pair = idGeOsPalm+", 'CHECKIN','"+rsDbs.getString("CUNM").trim()+"','"+modelo+"','"+rsDbs.getString("EQMFSN").trim()+"','"+rsDbs.getString("SVMTHR").trim()+"'," +
////							"'Hora','AONTONIO EVANILDO OLIVEIRA DA SILVA','"+FILIAL+"',to_date('"+rsDbs.getString("OPNDT8").trim()+"','yyyyMMdd'),'"+rsDbs.getString("SVMTHR").trim()+"','GE','N','"+idFuncionario+"',"+idFamilia+",'GE','"+modelo+"'";
////					stOracle.executeUpdate("insert into ge_os_palm (idos_palm, tipo_operacao, cliente, modelo, serie, smu, un_medida, tecnico, filial, emissao, horimetro, tipoinspecao, trocar_pecas, id_funcionario, id_familia,"+
////											" tipo_manutencao, equipamento) values ("+pair+")");
////					
////					
////					
////					rsOracle01 = stOracle01.executeQuery("select GE_CHECK_IN_SEQ.Nextval seq from dual");
////					rsOracle01.next();
////					Long idCheckIn = rsOracle01.getLong("seq");
//					
//					
//					rsDbs01 = stDbs01.executeQuery(SQL_CHECK_IN+"'"+rsDbs.getString("WONO").trim()+"'");
//					//inclusão na tabela ge_check_in
//					
//					if(!rsDbs01.next()){
//							EmailHelper helper = new EmailHelper();
//							//helper.sendSimpleMail("Os "+rsDbs.getString("WONO").trim(), "Os Sistema Check In", "salomao.freitas@marcosa.com.br");
//							helper.sendSimpleMail("Os "+rsDbs.getString("WONO").trim(), "Os Sistema Check In", "iury.andrade@marcosa.com.br");
//					}
//					
///*					while(rsDbs01.next()){
//						
////						rsOracle01 =  stOracle01.executeQuery(SQL_FATOR_CLIENTE+"'"+rsDbs01.getString("CUSTNO").trim()+"'");
////						rsOracle01.next();
////						BigDecimal fatorCliente = rsOracle01.getBigDecimal("cust_codigosegmento");
//						
////						BigDecimal fatorCliente = BigDecimal.valueOf(1.5D);
////						
////						String hasSendDbs = rsDbs01.getString("DTAP").trim().length() > 1?"S":"N";
////						String hasSendDataOrcamento = rsDbs01.getString("DTOC").trim().length() > 1?"S":"N";
////						String hasSendDataAprovacao = rsDbs01.getString("DTAP").trim().length() > 1?"S":"N";
////						String hasSendDataPrevisao = rsDbs01.getString("DTEX").trim().length() > 1?"S":"N";
////						pair = idCheckIn+","+idGeOsPalm+",'"+rsDbs.getString("WONO").trim()+"','1050040','"+rsDbs01.getString("CUSTNO").trim()+"'," +
////								"'N','"+hasSendDbs+"','"+hasSendDataOrcamento+"','N',"+fatorCliente+",'"+hasSendDataAprovacao+"','"+hasSendDataPrevisao+"','"+hasSendDbs+"',"+((rsDbs01.getString("LBCUNO") != null && rsDbs01.getString("LBCUNO").trim().length() > 0)?"'INT'":"'EXT'")+"," +
////										((rsDbs01.getString("LBCUNO") != null && rsDbs01.getString("LBCUNO").trim().length() > 0)?"'"+rsDbs01.getString("LBCUNO")+"'":null)+","
////										+((rsDbs01.getString("CONTA") != null && rsDbs01.getString("CONTA").trim().length() > 0)?"'"+rsDbs01.getString("CONTA").trim().split("          ")[0]+"'":null)+","
////										+((rsDbs01.getString("CONTA") != null && rsDbs01.getString("CONTA").trim().length() > 0)?"'"+rsDbs01.getString("CONTA").trim().split("          ")[1]+"'":null)+","
////										+((rsDbs01.getString("WATPCD") != null && rsDbs01.getString("WATPCD").trim().length() > 0)?"'"+rsDbs01.getString("WATPCD").trim()+"'":null)+",30,22";
////						
////						
////						stOracle01.executeUpdate("insert into ge_check_in (id, id_os_palm, numero_os, id_supervisor, cod_cliente, is_search_dbs, has_send_dbs, has_send_data_orcamento, fator_urgencia, fator_cliente, has_send_data_aprovacao, has_send_data_previsao,"+
////												" has_send_pecas_dbs, tipo_cliente, cliente_inter,  conta_contabil_sigla, centro_custo_sigla, sigla_indicador_garantia, validade_proposta, id_tipo_frete)"+
////												" values ("+pair+")");
////						//inclusão na tabela ge_situacao_os
////						String data_inicio = (rsDbs01.getString("DTAI").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTAI").trim().length() == 8)?rsDbs01.getString("DTAI").trim():"0"+rsDbs01.getString("DTAI").trim())+"','ddMMyyyy')":null;
////						String data_orcamento = (rsDbs01.getString("DTOC").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTOC").trim().length() == 8)?rsDbs01.getString("DTOC").trim():"0"+rsDbs01.getString("DTOC").trim())+"','ddMMyyyy')":null;
////						String data_negociacao = (rsDbs01.getString("DTNG").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTNG").trim().length() == 8)?rsDbs01.getString("DTNG").trim():"0"+rsDbs01.getString("DTNG").trim())+"','ddMMyyyy')":null;
////						String data_aprovacao = (rsDbs01.getString("DTAP").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTAP").trim().length() == 8)?rsDbs01.getString("DTAP").trim():"0"+rsDbs01.getString("DTAP").trim())+"','ddMMyyyy')":null;
////						String data_previsao_entrega = (rsDbs01.getString("DTEX").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTEX").trim().length() == 8)?rsDbs01.getString("DTEX").trim():"0"+rsDbs01.getString("DTEX").trim())+"','ddMMyyyy')":null;
////						String data_conclusao_servico = (rsDbs01.getString("DTCO").length() > 1)?"to_date('"+((rsDbs01.getString("DTCO").trim().length() == 8)?rsDbs01.getString("DTCO").trim():"0"+rsDbs01.getString("DTCO").trim())+"','ddMMyyyy')":null;
////						String data_faturamento = (rsDbs01.getString("DTFC").trim().length() > 1)?"to_date('"+((rsDbs01.getString("DTFC").trim().length() == 8)?rsDbs01.getString("DTFC").trim():"0"+rsDbs01.getString("DTFC").trim())+"','ddMMyyyy')":null;
////						pair = "ge_situacao_os_seq.nextval, '"+rsDbs.getString("WONO").trim()+"',"+data_inicio+","+data_orcamento+","+data_negociacao+","+data_aprovacao+","+data_previsao_entrega+","+data_conclusao_servico+","+data_faturamento+","+FILIAL+","+idCheckIn;
////						stOracle01.executeUpdate("insert into ge_situacao_os (id , numero_os, data_inicio, data_orcamento, data_negociacao, data_aprovacao, data_previsao_entrega, data_conclusao_servico, data_faturamento, filial, id_checkin)"+
////												" values("+pair+")");
//						//Inserção dos segmentos da os
//						rsDbs02 = stDbs02.executeQuery(SQL_SEGMENTO+"'"+rsDbs.getString("WONO").trim()+"' ORDER BY WOPSEGS0.WOSGNO");
////						if(!rsDbs02.next()){
////							EmailHelper helper = new EmailHelper();
////							helper.sendSimpleMail("Os "+rsDbs.getString("WONO").trim(), "Os Sistema Check In", "salomao.freitas@marcosa.com.br");
////						}
//						while(rsDbs02.next()){
//							String jbcd = rsDbs02.getString("JBCD").trim();
//							String cpcd = rsDbs02.getString("CPTCD").trim();
//							System.out.println("Segmento - "+rsDbs02.getString("WOSGNO").trim()+" jbcd "+jbcd+" cptcd "+cpcd);
//							
//							
//						}
//					}*/
//					
//					
//				}
//				//conOracle.commit();
//				
//			} catch (Exception e) {
//				try {
//					conOracle.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				e.printStackTrace();
//			}finally{
//				try {
//					stOracle.close();
//					//rsOracle.close();
//					stOracle01.close();
//					//rsOracle01.close();
//					stDbs.close();
//					rsDbs.close();
//					rsDbs01.close();
//					stDbs01.close();
//					//rsDbs02.close();
//					stDbs02.close();
//					//rsDbs03.close();
//					stDbs03.close();
//					stDbs04.close();
//					stDbs05.close();
//					conOracle.close();
//					conDbs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//
//		
//	}

}

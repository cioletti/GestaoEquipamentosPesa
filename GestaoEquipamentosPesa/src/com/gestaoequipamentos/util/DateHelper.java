package com.gestaoequipamentos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	public static boolean verificarDatasVencimento(Date data) throws ParseException {  
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");        
		Date dataAtual = format.parse(format.format(new Date()));  
		if(data == null)  
			return false;  
		if(data.compareTo(dataAtual) == -1){
			return false;
		}
		return true;
	}
	
	
//	public static int diferencaEmDias(Date beginDate, Date endDate){
//		Long diff = endDate.getTime() - beginDate.getTime();
//		
//		// Quantidade de milissegundos em um dia
//		int tempoDia = 1000 * 60 * 60 * 24;
//		 
//		int diasDiferenca = diff.intValue() / tempoDia;
//		
//		return diasDiferenca;
//	}
	
    public static double diferencaEmDia(Date dataInicial, Date dataFinal){  
        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
      
        return diferencaEmDias;  
    } 	
    
    public static int verificarDiaSemana(){
    	Calendar cal = Calendar.getInstance();  
    	return cal.get(Calendar.DAY_OF_WEEK); 
    }

}
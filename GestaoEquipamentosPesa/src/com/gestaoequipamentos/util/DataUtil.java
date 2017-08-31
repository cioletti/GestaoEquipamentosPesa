package com.gestaoequipamentos.util;

public class DataUtil {
	public static Boolean verificarClienteGarantia(String cliente){
		String [] garantia = {"243","247","249","023","241","463","468","267","268","288","998", "025"};
		for (String fim : garantia) {
			if(cliente.endsWith(fim)){
				return true;
			}
		}
		return false;
	}
}

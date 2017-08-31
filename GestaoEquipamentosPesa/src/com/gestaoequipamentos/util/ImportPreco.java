package com.gestaoequipamentos.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeComplexidade;
import com.gestaoequipamentos.entity.GePreco;
import com.gestaoequipamentos.entity.GePrefixo;

public class ImportPreco {
	public static void main(String[] args) {
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println(smt.format(new Date()));
			
		
	}
}

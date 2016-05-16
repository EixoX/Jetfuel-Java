package com.eixox.data.cvm;

import java.sql.Timestamp;

import com.eixox.adapters.DateYmdAdapter;
import com.eixox.adapters.IntegerAdapter;

public class CONFIG_001 {

	public int cvm_codigo;

	public Timestamp exercicio_data;

	public int status_id;

	public String exigencia_num;

	public int moeda_id;

	public int moeda_escala_id;

	public double correcao_fator;

	public String contato_nome;

	public int contato_ddd;

	public int cotato_telefone;

	public String versao_tela;

	public String versao_programa;

	public String contato_email;
	
	public final void read(String line){
		this.cvm_codigo = IntegerAdapter.parseInt(line.substring(0,  6));
		this.exercicio_data = DateYmdAdapter.parseTimestamp(line.substring(6, 14));
		//this.status_id = IntegerAdapter.parseInt(input)
	}

}

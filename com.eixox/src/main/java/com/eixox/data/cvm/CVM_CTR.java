package com.eixox.data.cvm;

import java.sql.Timestamp;

import com.eixox.adapters.DateYmdAdapter;
import com.eixox.adapters.IntegerAdapter;
import com.eixox.adapters.LongAdapter;

public class CVM_CTR {

	public int codigoCvm;

	public Timestamp dataExercicio;

	public int tipoDocumento;

	public long cnpj;

	public String razaoSocial;

	public Timestamp dataEmissao;

	public String algoritmoControle;

	public void read(String line) {
		this.codigoCvm = IntegerAdapter.parseInt(line.substring(0, 6));
		this.dataExercicio = DateYmdAdapter.parseTimestamp(line.substring(6, 14));
		this.tipoDocumento = IntegerAdapter.parseInt(line.substring(14, 15));
		this.cnpj = LongAdapter.parseLong(line.substring(15, 29));
		this.razaoSocial = line.substring(29, 69).trim();
		this.dataEmissao = DateYmdAdapter.parseTimestamp(line.substring(69, 85));
		this.algoritmoControle = line.substring(85, 100).trim();
	}
}

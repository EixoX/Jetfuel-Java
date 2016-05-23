package com.eixox.data.bovespa;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.Iterator;

import com.eixox.adapters.DateYmdAdapter;
import com.eixox.adapters.DoubleAdapter;
import com.eixox.adapters.IntegerAdapter;
import com.eixox.adapters.LongAdapter;

public class SerieHistoricaFile implements Closeable, Iterable<SerieHistoricaFile>, Iterator<SerieHistoricaFile> {

	private final BufferedReader reader;

	// Numero da linha do arquivo
	public int lineNumber;
	/*
	 * TIPO DE REGISTRO
	 */
	public int tipoRegistro;
	/*
	 * NOME DO ARQUIVO
	 */
	public String nomeArquivo;
	/*
	 * C�DIGO DA ORIGEM
	 */
	public String codigoOrigem;
	/*
	 * DATA DA GERA��O DO ARQUIVO
	 */
	public Timestamp dataGeracaoArquivo;
	/*
	 * TOTAL DE REGISTROS - INCLUIR TAMB�M OS REGISTROS HEADER E TRAILER.
	 */
	public long totalRegistros;

	/*
	 * DATA DO PREG�O
	 */
	public Timestamp dataPregao;

	/*
	 * CODBDI - C�DIGO BDI - UTILIZADO PARA CLASSIFICAR OS PAP�IS NA EMISS�O DO
	 * BOLETIM DI�RIO DE INFORMA��ES
	 */
	public int codigoBDI;

	/*
	 * CODNEG - C�DIGO DE NEGOCIA��O DO PAPEL
	 */
	public String codigoNegociacao;

	/*
	 * TPMERC - TIPO DE MERCADO - C�D. DO MERCADO EM QUE O PAPEL EST� CADASTRADO
	 */
	public int tipoMercado;

	/*
	 * NOMRES - NOME RESUMIDO DA EMPRESA EMISSORA DO PAPEL
	 */
	public String nomeResumido;

	/*
	 * ESPECI - ESPECIFICA��O DO PAPEL
	 */
	public String especificacao;

	/*
	 * PRAZOT - PRAZO EM DIAS DO MERCADO A TERMO
	 */
	public String prazoDias;

	/*
	 * MODREF - MOEDA DE REFER�NCIA
	 */
	public String moeda;

	/*
	 * PREABE - PRE�O DE ABERTURA DO PAPEL-MERCADO NO PREG�O
	 */
	public double precoAbertura;

	/*
	 * PREMAX - PRE�O M�XIMO DO PAPEL-MERCADO NO PREG�O
	 */
	public double precoMaximo;

	/*
	 * PREMIN - PRE�O M�NIMO DO PAPEL- MERCADO NO PREG�O
	 */
	public double precoMinimo;

	/*
	 * PREMED - PRE�O M�DIO DO PAPELMERCADO NO PREG�O
	 */
	public double precoMedio;

	/*
	 * PREULT - PRE�O DO �LTIMO NEG�CIO DO PAPEL-MERCADO NO PREG�O
	 */
	public double precoFechamento;

	/*
	 * PREOFC - PRE�O DA MELHOR OFERTA DE COMPRA DO PAPEL-MERCADO
	 */
	public double precoMelhorOfertaCompra;

	/*
	 * PREOFV - PRE�O DA MELHOR OFERTA DE VENDA DO PAPEL-MERCADO
	 */
	public double precoMelhorOfertaVenda;

	/*
	 * TOTNEG - NEG. - N�MERO DE NEG�CIOS EFETUADOS COM O PAPELMERCADO NO PREG�O
	 */
	public int quantidadeNegocios;

	/*
	 * QUATOT - QUANTIDADE TOTAL DE T�TULOS NEGOCIADOS NESTE PAPELMERCADO
	 */
	public long quantidadeTitulosNegociados;

	/*
	 * VOLTOT - VOLUME TOTAL DE T�TULOS NEGOCIADOS NESTE PAPELMERCADO
	 */
	public double volumeTitulosNegociados;

	/*
	 * PREEXE - PRE�O DE EXERC�CIO PARA O MERCADO DE OP��ES OU VALOR DO CONTRATO
	 * PARA O MERCADO DE TERMO SECUND�RIO
	 */
	public double precoExercicio;

	/*
	 * INDOPC - INDICADOR DE CORRE��O DE PRE�OS DE EXERC�CIOS OU VALORES DE
	 * CONTRATO PARA OS MERCADOS DE OP��ES OU TERMO SECUND�RIO
	 */
	public int indicadorCorrecao;

	/*
	 * DATVEN - DATA DO VENCIMENTO PARA OS MERCADOS DE OP��ES OU TERMO
	 * SECUND�RIO
	 */
	public Timestamp dataVencimento;

	/*
	 * FATCOT - FATOR DE COTA��O DO PAPEL
	 */
	public int fatorCotacao;

	/*
	 * PTOEXE - PRE�O DE EXERC�CIO EM PONTOS PARA OP��ES REFERENCIADAS EM D�LAR
	 * OU VALOR DE CONTRATO EM PONTOS PARA TERMO SECUND�RIO PARA OS
	 * REFERENCIADOS EM D�LAR, CADA PONTO EQUIVALE AO VALOR, NA MOEDA CORRENTE,
	 * DE UM CENT�SIMO DA TAXA M�DIA DO D�LAR COMERCIAL INTERBANC�RIO DE
	 * FECHAMENTO DO DIA ANTERIOR, OU SEJA, 1 PONTO = 1/100 US$
	 */
	public double pontosExercicio;

	/*
	 * CODISI - C�DIGO DO PAPEL NO SISTEMA ISIN OU C�DIGO INTERNO DO PAPEL
	 */
	public String isin;

	/*
	 * DISMES - N�MERO DE DISTRIBUI��O DO PAPEL
	 */
	public int distribuicao;

	public SerieHistoricaFile(Reader in) {
		this.reader = new BufferedReader(in);
	}

	public SerieHistoricaFile(File file) throws FileNotFoundException {
		this(new FileReader(file));
	}

	public SerieHistoricaFile(String fileName) throws FileNotFoundException {
		this(new File(fileName));
	}

	private void readHeader(String line) {
		this.nomeArquivo = line.substring(2, 15).trim();
		this.codigoOrigem = line.substring(15, 23).trim();
		this.dataGeracaoArquivo = DateYmdAdapter.parseTimestamp(line.substring(23, 31));
	}

	private void readTrailer(String line) {
		this.nomeArquivo = line.substring(2, 15).trim();
		this.codigoOrigem = line.substring(15, 23).trim();
		this.dataGeracaoArquivo = DateYmdAdapter.parseTimestamp(line.substring(23, 31));
		this.totalRegistros = LongAdapter.ENGLISH.parseLong(line.substring(31, 42));
	}

	private void readCotacao(String line) {

		this.dataPregao = DateYmdAdapter.parseTimestamp(line.substring(2, 10));
		this.codigoBDI = IntegerAdapter.ENGLISH.parseInteger(line.substring(10, 12));
		this.codigoNegociacao = line.substring(12, 24).trim();
		this.tipoMercado = IntegerAdapter.ENGLISH.parseInteger(line.substring(24, 27));
		this.nomeResumido = line.substring(27, 39).trim();
		this.especificacao = line.substring(39, 49).trim();
		this.prazoDias = line.substring(49, 52).trim();
		this.moeda = line.substring(52, 56).trim();
		this.precoAbertura = DoubleAdapter.ENGLISH.parseDouble(line.substring(56, 69)) / 100.0;
		this.precoMaximo = DoubleAdapter.ENGLISH.parseDouble(line.substring(69, 82)) / 100.0;
		this.precoMinimo = DoubleAdapter.ENGLISH.parseDouble(line.substring(82, 95)) / 100.0;
		this.precoMedio = DoubleAdapter.ENGLISH.parseDouble(line.substring(95, 108)) / 100.0;
		this.precoFechamento = DoubleAdapter.ENGLISH.parseDouble(line.substring(108, 121)) / 100.0;
		this.precoMelhorOfertaCompra = DoubleAdapter.ENGLISH.parseDouble(line.substring(121, 134)) / 100.0;
		this.precoMelhorOfertaVenda = DoubleAdapter.ENGLISH.parseDouble(line.substring(134, 147)) / 100.0;
		this.quantidadeNegocios = IntegerAdapter.ENGLISH.parseInteger(line.substring(147, 152));
		this.quantidadeTitulosNegociados = LongAdapter.ENGLISH.parseLong(line.substring(152, 170));
		this.volumeTitulosNegociados = DoubleAdapter.ENGLISH.parseDouble(line.substring(170, 188)) / 100.0;
		this.precoExercicio = DoubleAdapter.ENGLISH.parseDouble(line.substring(188, 201)) / 100.0;
		this.indicadorCorrecao = IntegerAdapter.ENGLISH.parseInteger(line.substring(201, 202));
		this.dataVencimento = DateYmdAdapter.parseTimestamp(line.substring(202, 210));
		this.fatorCotacao = IntegerAdapter.ENGLISH.parseInteger(line.substring(210, 217));
		this.pontosExercicio = DoubleAdapter.ENGLISH.parseDouble(line.substring(217, 230)) / 1000000.0;
		this.isin = line.substring(230, 242).trim();
		this.distribuicao = IntegerAdapter.ENGLISH.parseInteger(line.substring(242, 245));

	}

	public void close() throws IOException {
		this.reader.close();
	}

	public boolean read() throws IOException {
		String line = reader.readLine();
		if (line == null)
			return false;
		this.lineNumber++;
		this.tipoRegistro = Integer.parseInt(line.substring(0, 2));
		switch (this.tipoRegistro) {
		case 0:
			readHeader(line);
			return true;
		case 1:
			readCotacao(line);
			return true;
		case 99:
			readTrailer(line);
			return true;
		default:
			return read();
		}
	}

	public boolean hasNext() {
		boolean r = false;
		try {
			r = read();
		} catch (Exception e) {
			e.printStackTrace();
			r = false;
		}
		if (r)
			return true;
		else {
			try {
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public SerieHistoricaFile next() {
		return this;
	}

	public Iterator<SerieHistoricaFile> iterator() {
		return this;
	}

}

package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vacina {

	public static final int ESTAGIO_INICIAL = 1;
	public static final int ESTAGIO_TESTES = 2;
	public static final int ESTAGIO_APLICACAO_EM_MASSA = 3;
	
	private String paisOrigem;
	private int estagioPesquisa;
	private LocalDate dataInicioPesquisa;
	private Pesquisador pesquisadorResponsavel;
	private List<Nota> notas;
	
	public Vacina() {
	}
	
	public Vacina(String paisOrigem, int estagioPesquisa, LocalDate dataInicioPesquisa,
			Pesquisador pesquisadorResponsavel) {
		super();
		this.paisOrigem = paisOrigem;
		this.estagioPesquisa = estagioPesquisa;
		this.dataInicioPesquisa = dataInicioPesquisa;
		this.pesquisadorResponsavel = pesquisadorResponsavel;
		this.notas = new ArrayList<Nota>();
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public int getEstagioPesquisa() {
		return estagioPesquisa;
	}

	public void setEstagioPesquisa(int estagioPesquisa) {
		this.estagioPesquisa = estagioPesquisa;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public Pesquisador getPesquisadorResponsavel() {
		return pesquisadorResponsavel;
	}

	public void setPesquisadorResponsavel(Pesquisador pesquisadorResponsavel) {
		this.pesquisadorResponsavel = pesquisadorResponsavel;
	}

	
	public List<Nota> getNotas() {
		return notas;
	}
	
	public boolean addNotas(List<Nota> notas) {
		return this.notas.addAll(notas);
	}
	
	public void addNota(Nota nota) {
		this.notas.add(nota);
	}
	
}

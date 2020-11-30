package br.sc.senac.controller;

import java.time.LocalDate;
import java.util.List;

import br.sc.senac.model.bo.VacinaBO;
import br.sc.senac.model.exception.DataInicioPesquisaInvalidaException;
import br.sc.senac.model.exception.NomeInvalidoException;
import br.sc.senac.model.exception.PesquisadorInvalidoException;
import br.sc.senac.model.seletor.PessoaSeletor;
import br.sc.senac.model.seletor.VacinaSeletor;
import br.sc.senac.model.vo.Pessoa;
import br.sc.senac.model.vo.Vacina;

public class ControllerVacina {
	/*2- Vacinas
		* pa�s de origem, est�gio da pesquisa (1-inicial, 2-testes, 3-aplica��o em massa), data de in�cio da pesquisa, nome do pesquisador respons�vel
		* nome do pa�s e do pesquisador deve possuir no m�nimo 3 letras
		* converter data no formado "dd/MM/YYYY" para LocalDate
		* todos os campos s�o obrigat�rios
	* */
	
	public static final String TIPO_RELATORIO_XLS = "xls"; 
	
	VacinaBO vacinaBO = new VacinaBO();
	
	public String salvar(Vacina novaVacina) {
		String mensagem = "";
		
		try {
			this.validarVacina(novaVacina);
			novaVacina = vacinaBO.salvar(novaVacina);
			mensagem = "Vacina salva com sucesso! Id gerado: " + novaVacina.getId();
		} catch(NomeInvalidoException
				| PesquisadorInvalidoException
				| DataInicioPesquisaInvalidaException e) {
			mensagem = e.getMessage();
		}
		return mensagem;
	}

	private void validarVacina(Vacina novaVacina)
			throws NomeInvalidoException, PesquisadorInvalidoException,
			DataInicioPesquisaInvalidaException{
		this.validarNome(novaVacina.getNome());
		this.validarPesquisador(novaVacina.getPesquisadorResponsavel());
		this.validarDataInicio(novaVacina.getDataInicioPesquisa());
	}

	private void validarDataInicio(LocalDate dataInicioPesquisa) throws DataInicioPesquisaInvalidaException {
		if(dataInicioPesquisa == null) {
			throw new DataInicioPesquisaInvalidaException("Data inicio pesquisa precisa ser preenchida");
		}
	}

	private void validarPesquisador(Pessoa pesquisadorResponsavel) throws PesquisadorInvalidoException {
		if(pesquisadorResponsavel == null) {
			throw new PesquisadorInvalidoException("O campo pesquisador respons�vel � obrigat�rio, � necess�rio haver um pesquisador cadastrado.");
		}
	}

	private void validarNome(String nome) throws NomeInvalidoException {
		if(nome == null || nome.isEmpty()) {
			throw new NomeInvalidoException("O campo nome � obrigat�rio, ele deve ser preenchido.");
		}
		
		if(nome.length() < 3) {
			throw new NomeInvalidoException("O campo nome deve ter pelo menos 3 letras.");
		}
	}

	public List<Vacina> coletarTodasVacinas() {
		return vacinaBO.pegarListaDeVacinas();
	}
	
	public List<Vacina> listarVacinas(VacinaSeletor seletor) {
		return vacinaBO.listarVacinas(seletor);
	}
	
	public void gerarRelatorioTotalVacinaPorPesquisador(List<Vacina> vacinas, String caminhoEscolhido, String tipoRelatorio) {
		if(tipoRelatorio.equals(TIPO_RELATORIO_XLS)){
			vacinaBO.gerarPlanilhaVacinaTotalPorPesquisador(vacinas, caminhoEscolhido);
		}
	}

}

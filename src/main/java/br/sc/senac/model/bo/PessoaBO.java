package br.sc.senac.model.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.dao.InstituicaoDAO;
import br.sc.senac.model.dao.PessoaDAO;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.exception.DataNascimentoInvalidaException;
import br.sc.senac.model.seletor.PessoaSeletor;
import br.sc.senac.model.utilidades.Utils;
import br.sc.senac.model.vo.Instituicao;
import br.sc.senac.model.vo.Pessoa;


public class PessoaBO {

	private PessoaDAO pessoaDAO = new PessoaDAO();
	private InstituicaoDAO instituicaoDAO = new InstituicaoDAO();
	
	public Pessoa salvar(Pessoa novaPessoa) throws CpfJaCadastradoException, DataNascimentoInvalidaException{
		Instituicao instiutuicaoDoBanco = null;
		if(novaPessoa.getInstituicao() != null) {
			if(instituicaoDAO.jaExisteNome(novaPessoa.getInstituicao())) {
				instiutuicaoDoBanco = instituicaoDAO.pesquisarPeloNome(novaPessoa.getInstituicao().getNome());
			} else {
				instiutuicaoDoBanco = instituicaoDAO.inserir(novaPessoa.getInstituicao());
			}
			novaPessoa.setInstituicao(instiutuicaoDoBanco);
		}
		
		if(this.pessoaDAO.cpfJaCadastrado(novaPessoa)) {
			throw new CpfJaCadastradoException("O CPF informado (" + Utils.formatarCpf(novaPessoa.getCpf()) 
			+ ") j� foi cadastrado.");
		}
		
		if(novaPessoa.getDataNascimento().isAfter(LocalDate.now())) {
			throw new DataNascimentoInvalidaException("Data nascimento deve ser antes da data atual.");
		}
		
		this.pessoaDAO.inserir(novaPessoa);
		
		return novaPessoa;
	}
	
	public boolean atualizar(Pessoa pessoaAtualizada) throws CpfJaCadastradoException{
		Pessoa pessoaAntiga = pessoaDAO.pesquisarPorId(pessoaAtualizada.getId());
		Instituicao instiutuicaoDoBanco = null;
		
		if(!pessoaAntiga.getCpf().equals(pessoaAtualizada.getCpf())) {
			if(this.pessoaDAO.cpfJaCadastrado(pessoaAtualizada)) {
				throw new CpfJaCadastradoException("O CPF informado (" + pessoaAtualizada.getCpf() 
				+ ") j� foi cadastrado.");
			}
		}
		
		if(pessoaAtualizada.getInstituicao() != null) {
			if(instituicaoDAO.jaExisteNome(pessoaAtualizada.getInstituicao())) {
				instiutuicaoDoBanco = instituicaoDAO.pesquisarPeloNome(pessoaAtualizada.getInstituicao().getNome());
			} else {
				instiutuicaoDoBanco = instituicaoDAO.inserir(pessoaAtualizada.getInstituicao());
			}
			pessoaAtualizada.setInstituicao(instiutuicaoDoBanco);
		}
		
		return this.pessoaDAO.alterar(pessoaAtualizada);
	}
	
	public Pessoa consultarPorCPF(String cpf) {
		return this.consultarPorCPF(cpf);
	}
	
	public boolean excluirPessoa(Pessoa pessoaQueSeraExcluida) {
		boolean excluiu = pessoaDAO.excluir(pessoaQueSeraExcluida.getId());
		
		return excluiu;
	}

	public List<Pessoa> pegarListaDePesquisadores() {
		return pessoaDAO.pesquisarTodosPesquisadores();
	}

	
	public List<Pessoa> listarPessoas(PessoaSeletor seletor) {
		return this.pessoaDAO.listarComSeletor(seletor);
	}

	public List<Pessoa> pegarListaDePessoas() {
		return this.pessoaDAO.pesquisarTodos();
	}

	public String excluirPessoas(List<Integer> idsASeremExcluidos) {
		StringBuffer mensagem = new StringBuffer();
		StringBuffer msgPessoasExcluidasComSucesso = new StringBuffer();
		StringBuffer msgPessoasExcluidasSemSucesso = new StringBuffer();
		List<Pessoa> pessoasExcluidas = new ArrayList<Pessoa>();
		List<Boolean> statusPessoasExcluidas = new ArrayList<Boolean>();
		boolean nenhumaPessoaExcluida = true;
		
		for(Integer idASerExcluido : idsASeremExcluidos) {
			Pessoa pessoaExcluida = pessoaDAO.pesquisarPorId(idASerExcluido);
			if (pessoaExcluida != null) {
				statusPessoasExcluidas.add(pessoaDAO.excluir(idASerExcluido));
				pessoasExcluidas.add(pessoaExcluida);
			}
		}
		
		for(int i = 0; i < statusPessoasExcluidas.size(); i++) {
			Boolean foiExcluida = statusPessoasExcluidas.get(i);
			Pessoa pessoaExcluida = pessoasExcluidas.get(i);
			if(foiExcluida) {
				if(msgPessoasExcluidasComSucesso.length() > 0) {
					msgPessoasExcluidasComSucesso.append(", ");
				}
				msgPessoasExcluidasComSucesso.append(
						"(" + pessoaExcluida.getId() + " - " + pessoaExcluida.toString() + ")"
						);
				nenhumaPessoaExcluida = false;
			} else {
				if(msgPessoasExcluidasSemSucesso.length() > 0) {
					msgPessoasExcluidasSemSucesso.append(", ");
				}
				msgPessoasExcluidasSemSucesso.append(
						"(" + pessoaExcluida.getId() + " - " + pessoaExcluida.toString() + ")"
						);
			}
		}
		
		if(nenhumaPessoaExcluida) {
			mensagem.append("Nenhuma pessoa exclu�da.");
		} else {
			mensagem.append("Pessoa(s) ");
			mensagem.append(msgPessoasExcluidasComSucesso);
			mensagem.append(" exclu�da(s) com sucesso!\n");
			if(msgPessoasExcluidasSemSucesso.length() > 0) {
				mensagem.append("N�o foi poss�vel excluir a(s) pessoa(s) ");
				mensagem.append(msgPessoasExcluidasSemSucesso);
				mensagem.append('!');
			}
		}
		
		return mensagem.toString();
	}
	
}

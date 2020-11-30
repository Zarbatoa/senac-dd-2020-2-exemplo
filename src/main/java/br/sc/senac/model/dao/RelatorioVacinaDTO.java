package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.sc.senac.model.seletor.RelatorioVacinaSeletor;
import br.sc.senac.model.vo.Vacina;

public class RelatorioVacinaDTO {
	
	private Vacina construirDoResultSet(ResultSet result) {
		// TODO Auto-generated method stub
		return null;
	} // ser� que consigo fazer somente desse m�todo???
	
	
	//aqui iniciam os m�todos de filtros
	public ArrayList<Vacina> listarComSeletor(RelatorioVacinaSeletor seletor) {
		String sql = " SELECT * FROM VACINA v ";

		if (seletor.temFiltro()) {
			sql = criarFiltros(seletor, sql);
		}

		if (seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset();
		}
		Connection conexao = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conexao, sql);
		ArrayList<Vacina> vacinas = new ArrayList<Vacina>();
		try {
			ResultSet result = prepStmt.executeQuery();

			while (result.next()) {
				Vacina v = construirDoResultSet(result); 
				vacinas.add(v);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pessoas com filtros.\nCausa: " + e.getMessage());
		}
		return vacinas;
	}
	
	private String criarFiltros(RelatorioVacinaSeletor seletor, String sql) {

		// Tem pelo menos UM filtro
		sql += " WHERE ";
		boolean primeiro = true;

		if ((seletor.getDataInicio() != null) && (seletor.getDataFim()!= null)) {
			// Regra composta, olha as 3 op��es de preenchimento do per�odo

			// Todo o per�odo preenchido (in�cio e fim)
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "v.dataCadastro BETWEEN" + seletor.getDataInicio() + " AND " + seletor.getDataFim(); //arrumar vari�vel para ficar igual ao sql
			primeiro = false;
		} else if (seletor.getDataInicio() != null) {
			// s� in�cio
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "v.dataCadastro >= " + seletor.getDataInicio(); //arrumar vari�vel para ficar igual ao sql
			primeiro = false;
		} else if (seletor.getDataFim() != null) {
			// s� o fim
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "v.dataCadastro <= " + seletor.getDataFim(); //arrumar vari�vel para ficar igual ao sql
			primeiro = false;
		}
		return sql;
	}


}

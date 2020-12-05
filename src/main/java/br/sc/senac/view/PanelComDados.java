package br.sc.senac.view;

import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class PanelComDados extends JPanel {
	
	// acho que este atributo vai ser util, somentente p relatorios
	protected boolean dadosPreenchidos;
	
	public boolean getDadosPreenchidos() {
		return dadosPreenchidos;
	}

	public void setDadosPreenchidos(boolean dadosPreenchidos) {
		this.dadosPreenchidos = dadosPreenchidos;
	}

	/**
	 * @return retorna se este Panel j� preencheu os dados alguma vez
	 *   (no nosso caso seria se as JTables est�o preenchidas e n�o foram limpas)
	 * */
	public boolean hasDados() {
		return dadosPreenchidos;
	}
	
	/**
	 * @return retorna um array de nomes da tabela de dados
	 * */
	public abstract String[] getNomesColunas();
	
	/**
	 * @return retorna os dados, que est�o mostrando na JTable
	 *  (incluindo a primeira linha com as descri��es), ou seja,
	 *  os dados com pagina��o
	 * */
	public abstract List<String[]> getDadosVisiveis();
	
	/**
	 * @return retorna todos os dados, sem os limites da pagina��o
	 *  (incluindo a primeira linha com as descri��es)
	 * */
	public abstract List<String[]> getDadosCompletos();
}

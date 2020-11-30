package br.sc.senac.model.bo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.sc.senac.model.vo.Vacina;

/**
 * Classe utilit�ria para gerar planilhas Excel atrav�s da API Apache POI.
 * 
 * @author Vilmar C�sar Pereira J�nior
 *
 **/

public class GeradorPlanilhaVacina {
	
	// essa classe GeradorPlanilhaVacina precisa tamb�m ser referenciada em VacinaBO. 
	// isso serve para a cria��o de qualquer planilha relacionada a vacina
	
	public void gerarPlanilhaVacinaTotalPorPesquisador(List<Vacina> vacinas, String caminhoEscolhido) {
		String[] columns = { "Nome Vacina","Pa�s de Origem", "Pesquisador Respons�vel", "Institui��o" }; //teria que fazer um m�todo no  VO vacina? 

		HSSFWorkbook planilha = new HSSFWorkbook();

		// 1) Cria uma aba na planilha (nome � um par�metro opcional)
		HSSFSheet abaPlanilha = planilha.createSheet("Vacinas");

		Row headerRow = abaPlanilha.createRow(0);

		// 2) Cria o cabe�alho a partir de um array columns
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
		}

		// 3) Cria as linhas com os produtos da lista
		int rowNum = 1;
		for (Vacina vac : vacinas) {
			Row novaLinha = abaPlanilha.createRow(rowNum++);

			novaLinha.createCell(0).setCellValue(vac.getNome());
			novaLinha.createCell(3).setCellValue(vac.getPaisOrigem());
			novaLinha.createCell(1).setCellValue(vac.getPesquisadorResponsavel().getNomeCompleto());
			novaLinha.createCell(2).setCellValue(vac.getPesquisadorResponsavel().getInstituicao().getNome());
			// fazer o total de pesquisador por vacina. provavemente ter� de ser um DTO --> DAO ???
		}

		// 4) Ajusta o tamanho de todas as colunas conforme a largura do
		// conte�do
		for (int i = 0; i < columns.length; i++) {
			abaPlanilha.autoSizeColumn(i);
		}

		//5) Escreve o arquivo em disco, no caminho informado
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(caminhoEscolhido + ".xls");
			planilha.write(fileOut);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fileOut != null){
				try {
					fileOut.close();
					planilha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

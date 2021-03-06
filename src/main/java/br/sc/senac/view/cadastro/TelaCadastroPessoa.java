package br.sc.senac.view.cadastro;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import br.sc.senac.controller.ControllerPessoa;
import br.sc.senac.model.utilidades.Constantes;
import br.sc.senac.model.utilidades.Utils;
import br.sc.senac.model.vo.Instituicao;
import br.sc.senac.model.vo.Pessoa;
import br.sc.senac.model.vo.TipoPessoa;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class TelaCadastroPessoa extends JPanel {

	private JTextField tfNome;
	private JTextField tfInstituicao;
	
	private JComboBox cbCategoria;
	private JComboBox cbSexo;
	private JTextField tfSobrenome;
	private DatePicker dpDataInicioPesquisa;
	private JFormattedTextField ftfCpf;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroPessoa frame = new TelaCadastroPessoa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public TelaCadastroPessoa() throws ParseException {
		setBounds(100, 100, 650, 370);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new MigLayout("", "[][][grow][][][grow][][]", "[][][][][][][][][][][]"));
		
		JLabel lblCadastroDePessoa = new JLabel("Cadastro de Pessoa");
		lblCadastroDePessoa.setFont(new Font("Tahoma", Font.BOLD, 11));
		this.add(lblCadastroDePessoa, "cell 3 0 2 1");
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblNome, "cell 0 2,alignx trailing");
		
		tfNome = new JTextField();
		tfNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(tfNome, "cell 1 2 2 1,growx");
		tfNome.setColumns(10);
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		
		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblSobrenome, "cell 4 2,alignx trailing");
		
		tfSobrenome = new JTextField();
		tfSobrenome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(tfSobrenome, "cell 5 2 2 1,growx");
		tfSobrenome.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblCpf, "cell 0 4,alignx trailing");
		ftfCpf = new JFormattedTextField(mascaraCpf);
		ftfCpf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(ftfCpf, "cell 1 4 2 1,growx");
		
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblSexo, "cell 4 4,alignx trailing");
		cbSexo = new JComboBox(Constantes.OPCOES_SEXO_EDICAO_CADASTRO);
		cbSexo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(cbSexo, "cell 5 4 2 1,growx");
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblCategoria, "cell 0 6,alignx trailing");
		
		cbCategoria = new JComboBox(Constantes.OPCOES_TIPO_PESSOA_EDICAO_CADASTRO);
		cbCategoria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoPessoa opcaoSelecionada = (TipoPessoa)cbCategoria.getSelectedItem();
				
				if(opcaoSelecionada == Constantes.TIPO_PESQUISADOR) {
					tfInstituicao.setEnabled(true);
				} else {
					tfInstituicao.setEnabled(false);
				}
			}
		});
		this.add(cbCategoria, "cell 1 6 2 1,growx");
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento:");
		lblDataDeNascimento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblDataDeNascimento, "cell 4 6,alignx trailing");
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		dpDataInicioPesquisa = new DatePicker(dateSettings);
		this.add(dpDataInicioPesquisa,"cell 5 6 2 1,growx");
		
		JLabel lblInstituicao = new JLabel("Institui\u00E7\u00E3o:");
		lblInstituicao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(lblInstituicao, "cell 0 8,alignx trailing");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// Preencher o Objeto com os dados da tela
				Pessoa novaPessoa = new Pessoa();
				novaPessoa.setTipo((TipoPessoa)cbCategoria.getSelectedItem());
				novaPessoa.setInstituicao(new Instituicao(-1,tfInstituicao.getText()));
				novaPessoa.setNome(tfNome.getText());
				novaPessoa.setSobrenome(tfSobrenome.getText());
				LocalDate novaDataNascimento = dpDataInicioPesquisa.getDate();
				novaPessoa.setDataNascimento(novaDataNascimento);
				novaPessoa.setSexo(((String)cbSexo.getSelectedItem()).charAt(0));
				novaPessoa.setCpf(Utils.desformatarCpf(ftfCpf.getText()));
				
				// Instanciar um controller adequado
				ControllerPessoa controller = new ControllerPessoa();
				
				// Chamar o m�todo salvar no controller e pegar a mensagem retornada
				String mensagem = controller.salvar(novaPessoa);
				
				// Mostrar a mensagem devolvida pelo controller
				JOptionPane.showMessageDialog(null, mensagem);
			}
		});
		
		tfInstituicao = new JTextField();
		tfInstituicao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(tfInstituicao, "cell 1 8 2 1,growx");
		tfInstituicao.setColumns(10);
		this.add(btnSalvar, "cell 2 10");
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 11));

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetarTodosOsCampos();
			}
		});
		this.add(btnLimpar, "cell 5 10");
	}
	
	protected void resetarTodosOsCampos() {
		tfNome.setText("");
		tfSobrenome.setText("");
		ftfCpf.setText("");
		cbSexo.setSelectedIndex(0);
		cbCategoria.setSelectedIndex(0);
		dpDataInicioPesquisa.clear();
		tfInstituicao.setText("");
	}

}

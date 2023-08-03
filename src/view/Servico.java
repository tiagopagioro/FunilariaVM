package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

public class Servico extends JDialog {
	private JTextField txtValor;
	private JTextField txtOS;
	private JTextField txtCliente;
	private JTextField txtID;
	private JTextField txtServico;
	private JTextField txtPlaca;
	private JDateChooser txtDataOS;
	private JDateChooser txtDataSaida;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JComboBox cboStatus;

	// Variável global que será usada para setar o nome do usuário pelo path
	// path(caminho): login -> tela principal -> serviços
	public String usuario;
	private JTextField txtUser;
	private JButton btnLimpar;
	private JScrollPane scrollPane;
	private JList listaUsuarios;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtCor;
	private JTextField txtAno;
	private JTextField txtObs;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JTextField txtMec;
	private JScrollPane scrollPane_1;
	private JList listaMec;
	private JTextField txtIDMec;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servico dialog = new Servico();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Servico() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// quando a janela for ativada, a JText txtUser ira reproduzir o nome do usuário
				// logado
				txtUser.setText(usuario);
			}
		});
		setTitle("Serviços");
		setBounds(100, 100, 886, 551);
		getContentPane().setLayout(null);

		txtValor = new JTextField();
		txtValor.setDocument(new Validador(10));
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação de caracteres permitidos (evento key typed)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(125, 167, 186, 62);
		getContentPane().add(scrollPane_1);

		listaMec = new JList();
		listaMec.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarMecLista();
			}
		});
		listaMec.setBorder(null);
		scrollPane_1.setViewportView(listaMec);
		txtValor.setText("0");
		txtValor.setBounds(142, 392, 123, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JLabel lblNewLabel = new JLabel("Valor");
		lblNewLabel.setBounds(86, 395, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Data OS");
		lblNewLabel_1.setBounds(53, 102, 46, 20);
		getContentPane().add(lblNewLabel_1);

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Servico.class.getResource("/img/create.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarServico();
			}
		});
		btnAdicionar.setBounds(142, 439, 89, 62);
		getContentPane().add(btnAdicionar);

		btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Servico.class.getResource("/img/pesquisar.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscar.setBounds(205, 11, 65, 62);
		getContentPane().add(btnBuscar);

		JLabel lblNewLabel_2 = new JLabel("OS");
		lblNewLabel_2.setBounds(69, 37, 30, 14);
		getContentPane().add(lblNewLabel_2);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(109, 34, 86, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(574, 34, 286, 124);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(20, 50, 188, 76);
		panel.add(scrollPane);

		listaUsuarios = new JList();
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		scrollPane.setViewportView(listaUsuarios);

		txtCliente = new JTextField();
		txtCliente.setDocument(new Validador(50));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtCliente.setBounds(20, 30, 188, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Servico.class.getResource("/img/search.png")));
		lblNewLabel_4.setBounds(218, 22, 32, 32);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("ID");
		lblNewLabel_5.setBounds(20, 77, 46, 14);
		panel.add(lblNewLabel_5);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(47, 73, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		txtID.setDocument(new Validador(10));

		JLabel lblNewLabel_3 = new JLabel("Status");
		lblNewLabel_3.setBounds(445, 338, 46, 14);
		getContentPane().add(lblNewLabel_3);

		cboStatus = new JComboBox();
		cboStatus.setModel(new DefaultComboBoxModel(new String[] { "", "Aguardando orçamento", "Em andamento",
				"Aguardando peças", "Aguardando aprovação", "Orçamento reprovado", "Concluído" }));
		cboStatus.setBounds(514, 334, 232, 22);
		getContentPane().add(cboStatus);

		txtServico = new JTextField();
		txtServico.setBounds(514, 238, 232, 20);
		getContentPane().add(txtServico);
		txtServico.setColumns(10);
		txtServico.setDocument(new Validador(100));

		JLabel lblNewLabel_7 = new JLabel("Serviço");
		lblNewLabel_7.setBounds(445, 241, 59, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Placa");
		lblNewLabel_8.setBounds(69, 292, 55, 14);
		getContentPane().add(lblNewLabel_8);

		txtPlaca = new JTextField();
		txtPlaca.setBounds(142, 289, 232, 20);
		getContentPane().add(txtPlaca);
		txtPlaca.setColumns(10);
		txtPlaca.setDocument(new Validador(10));

		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Servico.class.getResource("/img/update.png")));
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarServico();
			}
		});
		btnEditar.setBounds(288, 439, 89, 62);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Servico.class.getResource("/img/delete.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setBounds(430, 439, 89, 62);
		getContentPane().add(btnExcluir);

		txtDataOS = new JDateChooser();
		txtDataOS.setEnabled(false);
		txtDataOS.setBounds(109, 102, 187, 20);
		getContentPane().add(txtDataOS);

		JLabel lblNewLabel_9 = new JLabel("Data de Saída");
		lblNewLabel_9.setBounds(324, 395, 97, 14);
		getContentPane().add(lblNewLabel_9);

		txtDataSaida = new JDateChooser();
		txtDataSaida.setBounds(413, 395, 159, 20);
		getContentPane().add(txtDataSaida);

		txtUser = new JTextField();
		txtUser.setEditable(false);
		txtUser.setBounds(399, 34, 123, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("Usuário");
		lblNewLabel_10.setBounds(347, 37, 46, 14);
		getContentPane().add(lblNewLabel_10);

		btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setIcon(new ImageIcon(Servico.class.getResource("/img/clear.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(574, 439, 89, 62);
		getContentPane().add(btnLimpar);

		JLabel lblNewLabel_11 = new JLabel("Marca");
		lblNewLabel_11.setBounds(69, 193, 46, 14);
		getContentPane().add(lblNewLabel_11);

		txtMarca = new JTextField();
		txtMarca.setBounds(142, 190, 231, 20);
		getContentPane().add(txtMarca);
		txtMarca.setColumns(10);
		txtMarca.setDocument(new Validador(40));

		JLabel lblNewLabel_12 = new JLabel("Modelo");
		lblNewLabel_12.setBounds(69, 241, 46, 14);
		getContentPane().add(lblNewLabel_12);

		txtModelo = new JTextField();
		txtModelo.setBounds(142, 238, 231, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);
		txtModelo.setDocument(new Validador(40));

		JLabel lblNewLabel_13 = new JLabel("Cor");
		lblNewLabel_13.setBounds(445, 193, 46, 14);
		getContentPane().add(lblNewLabel_13);

		txtCor = new JTextField();
		txtCor.setBounds(514, 190, 231, 20);
		getContentPane().add(txtCor);
		txtCor.setColumns(10);
		txtCor.setDocument(new Validador(20));

		JLabel lblNewLabel_6 = new JLabel("Ano");
		lblNewLabel_6.setBounds(69, 338, 46, 14);
		getContentPane().add(lblNewLabel_6);

		txtAno = new JTextField();
		txtAno.setDocument(new Validador(5));
		txtAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtAno.setBounds(142, 335, 232, 20);
		getContentPane().add(txtAno);
		txtAno.setColumns(10);

		txtObs = new JTextField();
		txtObs.setBounds(514, 289, 232, 20);
		getContentPane().add(txtObs);
		txtObs.setColumns(10);
		txtObs.setDocument(new Validador(100));

		JLabel lblNewLabel_14 = new JLabel("Observações");
		lblNewLabel_14.setBounds(430, 292, 77, 14);
		getContentPane().add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Funileiro");
		lblNewLabel_15.setBounds(53, 150, 63, 14);
		getContentPane().add(lblNewLabel_15);

		txtMec = new JTextField();
		txtMec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarMec();
			}
		});
		txtMec.setBounds(127, 147, 184, 20);
		getContentPane().add(txtMec);
		txtMec.setColumns(10);

		JLabel lblNewLabel_16 = new JLabel("ID");
		lblNewLabel_16.setBounds(328, 150, 46, 14);
		getContentPane().add(lblNewLabel_16);

		txtIDMec = new JTextField();
		txtIDMec.setEditable(false);
		txtIDMec.setBounds(361, 147, 86, 20);
		getContentPane().add(txtIDMec);
		txtIDMec.setColumns(10);

		btnOS = new JButton("");
		btnOS.setBorderPainted(false);
		btnOS.setFocusable(false);
		btnOS.setFocusPainted(false);
		btnOS.setContentAreaFilled(false);
		btnOS.setIcon(new ImageIcon(Servico.class.getResource("/img/4243338_basic_app_print_ux_icon.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(747, 392, 89, 73);
		getContentPane().add(btnOS);

	}// fim do construtor

	private void adicionarServico() {
		if (txtMarca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Marca do veículo");
			txtMarca.requestFocus();
		} else if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Modelo do veículo");
			txtModelo.requestFocus();
		} else if (txtPlaca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Placa do veículo");
			txtPlaca.requestFocus();
		} else if (txtAno.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Ano do veículo");
			txtAno.requestFocus();
		} else if (txtCor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cor do veículo");
			txtCor.requestFocus();
		} else if (txtServico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Serviço a ser realizado");
			txtServico.requestFocus();
		} else if (((String) cboStatus.getSelectedItem()).isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Status da OS");
			cboStatus.requestFocus();
		} else if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o Cliente atendido");
			txtCliente.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("Concluído") && txtValor.getText().equals("0.00")){
			JOptionPane.showMessageDialog(null, "Preencha o Valor do serviço");
			txtValor.requestFocus();
		} else {
			// lógica principal
			// a linha abaixo cria uma variável de nome create que recebe o código sql
			String create = "insert into servicos(marca,modelo,placa,ano,cor,servico,observacoes,statusOS,valor,dataSaida,id,idmec,usuario) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtMarca.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtPlaca.getText());
				pst.setString(4, txtAno.getText());
				pst.setString(5, txtCor.getText());
				pst.setString(6, txtServico.getText());
				pst.setString(7, txtObs.getText());
				pst.setString(8, cboStatus.getSelectedItem().toString());
				pst.setString(9, txtValor.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (txtDataSaida.getDate() == null) {
					pst.setString(10, null);
				} else {
					String dataFormatada2 = formatador.format(txtDataSaida.getDate());
					pst.setString(10, dataFormatada2);
					
				}
				
				pst.setString(11, txtID.getText());
				pst.setString(12, txtIDMec.getText());
				pst.setString(13, txtUser.getText());
				if (cboStatus.getSelectedItem().equals("Concluído") && txtDataSaida.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data de saída");
					txtDataSaida.requestFocus();
				} else {
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Servico salvo com sucesso");
					limparCampos();
				}
				con.close();		
			} catch (Exception e) {
				System.out.println(e);
			}
		} // fim do método novoUsuario
	}

	private void buscarOS() {
		// Pesquisar o número da OS sem capturar da caixa de texto
		String numOS = JOptionPane.showInputDialog("Número da OS");
		// CRUD Read
		String read = "select * from servicos where os = ?";
		try {
			// abrir conexão
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				// formatação da data no componente JDateChooser
				String setarData = rs.getString(11);
				// apoio ao entendimento da lógica
				// System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				txtDataOS.setDate(dataFormatada);
				// formatação da data no componente JDateChooser
				String setarDataSaida = rs.getString(12);
				// correção de BUG para valores nulos de data
				if (setarDataSaida == null) {
					txtDataSaida.setDate(null);
				} else {
					// apoio ao entendimento da lógica
					// System.out.println(setarDataSaida);
					Date dataSaidaFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataSaida);
					txtDataSaida.setDate(dataSaidaFormatada);
				}
				txtMarca.setText(rs.getString(2));
				txtModelo.setText(rs.getString(3));
				txtPlaca.setText(rs.getString(4));
				txtAno.setText(rs.getString(5));
				txtCor.setText(rs.getString(6));
				txtServico.setText(rs.getString(7));
				txtObs.setText(rs.getString(8));
				cboStatus.setSelectedItem(rs.getString(9));
				txtValor.setText(rs.getString(10));
				txtID.setText(rs.getString(13));
				txtIDMec.setText(rs.getString(14));
				txtUser.setText(rs.getString(15));
				txtDataOS.setDate(dataFormatada);
				txtID.setEditable(false);
				txtCliente.setEditable(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);
				txtIDMec.setEditable(false);

			} else {
				JOptionPane.showMessageDialog(null, "Número de OS inexistente.");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// fim do método buscarOS

	/**
	 * Método para editar os dados do usuário exceto a senha
	 */
	private void editarServico() {
		// System.out.println("teste do botão editar");
		String update = "update servicos set marca=?,modelo=?,placa=?,ano=?,cor=?,servico=?,observacoes=?,statusOS=?,valor=?,dataSaida=?,idmec=? where os=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtMarca.getText());
			pst.setString(2, txtModelo.getText());
			pst.setString(3, txtPlaca.getText());
			pst.setString(4, txtAno.getText());
			pst.setString(5, txtCor.getText());
			pst.setString(6, txtServico.getText());
			pst.setString(7, txtObs.getText());
			pst.setString(8, cboStatus.getSelectedItem().toString());
			pst.setString(9, txtValor.getText());
			SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
			if (txtDataSaida.getDate() == null) {
				pst.setString(10, null);
			} else {
				String dataFormatada2 = formatador.format(txtDataSaida.getDate());
				pst.setString(10, dataFormatada2);
			}
			pst.setString(12, txtOS.getText());
			pst.setString(11, txtIDMec.getText());
			if (cboStatus.getSelectedItem().equals("Concluído") && txtDataSaida.getDate() == null) {
				JOptionPane.showMessageDialog(null, "Preencha a data de saída");
				txtDataSaida.requestFocus(); }
			else if (cboStatus.getSelectedItem().equals("Concluído") && txtValor.getText().equals("0.00")){
				JOptionPane.showMessageDialog(null, "Preencha o Valor do serviço");
				txtValor.requestFocus(); 
			} else {
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Servico salvo com sucesso");
				limparCampos();
			}
			con.close();		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
// fim do método editarUsuario

	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirServico() {
		// System.out.println("teste do botão excluir");
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Serviço excluído");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método excluirUsuario

	/**
	 * Método para listar os usuários (pesquisa avançada)
	 */
	private void listarUsuarios() {
		// System.out.println("teste");
		// criar um objeto -> lista (vetor dinâmico) para exibir a
		// lista de usuários do banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		// CRUD Read
		String readLista = "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			// enquanto existir usuários que começam com a(s) letra(s)
			while (rs.next()) {
				// exibir a lista
				scrollPane.setVisible(true);
				// exibir os usuários na lista na lista
				modelo.addElement(rs.getString(2));
				// Dica Luan (UX - esconder lista - semelhante google)
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para buscar um usuário da lista
	 */
	private void buscarUsuarioLista() {
		// selecionar a linha da lista ("indice do vetor")
		int linha = listaUsuarios.getSelectedIndex();
		// se a linha(índice) da lista for selecionada
		if (linha >= 0) {
			System.out.println(linha);
			// limit - instrução sql que limita o número de registros selecionados da tabela
			// (índice,numéro de registros) Ex (1,1)
			String read2 = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
				} else {
					System.out.println("usuário não cadastrado");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir um índice(usuário) na lista
			scrollPane.setVisible(false);
		}
	}// fim do método buscarUsuario

	/**
	 * Método para listar os usuários (pesquisa avançada)
	 */
	private void listarMec() {
		// System.out.println("teste");
		// criar um objeto -> lista (vetor dinâmico) para exibir a
		// lista de usuários do banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaMec.setModel(modelo);
		// CRUD Read
		String readLista = "select * from mecanicos where nome like '" + txtMec.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			// enquanto existir usuários que começam com a(s) letra(s)
			while (rs.next()) {
				// exibir a lista
				scrollPane_1.setVisible(true);
				// exibir os usuários na lista na lista
				modelo.addElement(rs.getString(2));
				// Dica Luan (UX - esconder lista - semelhante google)
				if (txtMec.getText().isEmpty()) {
					scrollPane_1.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para buscar um usuário da lista
	 */
	private void buscarMecLista() {
		// selecionar a linha da lista ("indice do vetor")
		int linha = listaMec.getSelectedIndex();
		// se a linha(índice) da lista for selecionada
		if (linha >= 0) {
			System.out.println(linha);
			// limit - instrução sql que limita o número de registros selecionados da tabela
			// (índice,numéro de registros) Ex (1,1)
			String read2 = "select * from mecanicos where nome like '" + txtMec.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane_1.setVisible(false);
					txtIDMec.setText(rs.getString(1));
					txtMec.setText(rs.getString(2));
				} else {
					System.out.println("Mecânico não cadastrado");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir um índice(usuário) na lista
			scrollPane_1.setVisible(false);
		}
	}// fim do método buscarUsuario

	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		if (txtServico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Adicione a OS antes de imprimir");
			txtCliente.requestFocus();
		} else {
		Document document = new Document();
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "select servicos.os,date_format(dataOS,'%d/%m/%Y - %H:%i') as data_entrada,servicos.usuario as usuário,servicos.marca,servicos.modelo,servicos.servico,servicos.statusOS as status_OS,mecanicos.nome as mecânico,servicos.valor,date_format(servicos.dataSaida, '%d/%m/%Y') as data_saida,clientes.nome as cliente, clientes.numero from servicos inner join clientes on servicos.id = clientes.id inner join mecanicos on servicos.idmec = mecanicos.idmec where OS = ?;";
			try {
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				rs = pst.executeQuery();
				// se existir a OS
				
				if (rs.next()) {
					// document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);

					Paragraph usuario = new Paragraph("Usuário: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_RIGHT);
					document.add(usuario);
					
					Paragraph data = new Paragraph(rs.getString(2));
					data.setAlignment(Element.ALIGN_RIGHT);
					document.add(data);
					
					Paragraph cliente = new Paragraph("Funileiro:  " + rs.getString(8));
					cliente.setAlignment(Element.ALIGN_RIGHT);
					document.add(cliente);

					Image imagem = Image.getInstance(Servico.class.getResource("/img/logo2.png"));
					imagem.scaleToFit(200, 200);
					imagem.setAbsolutePosition(35, 650);
					document.add(imagem);
					
					Paragraph linha = new Paragraph(" ");
					linha.setAlignment(Element.ALIGN_CENTER);
					document.add(linha);
					
					Paragraph linha1 = new Paragraph(" ");
					linha1.setAlignment(Element.ALIGN_CENTER);
					document.add(linha1);
					
					Paragraph linha2 = new Paragraph(" ");
					linha2.setAlignment(Element.ALIGN_CENTER);
					document.add(linha2);
					
					Paragraph linha3 = new Paragraph(" ");
					linha3.setAlignment(Element.ALIGN_CENTER);
					document.add(linha3);
					
					Paragraph linha4 = new Paragraph(" ");
					linha4.setAlignment(Element.ALIGN_CENTER);
					document.add(linha4);
					
					Paragraph texto1 = new Paragraph("Ordem de Serviço");
					texto1.setAlignment(Element.ALIGN_CENTER);
					texto1.getFont();
					document.add(texto1);
					
					Paragraph linha5 = new Paragraph(" ");
					linha5.setAlignment(Element.ALIGN_CENTER);
					document.add(linha5);
					
					Paragraph texto2 = new Paragraph("    Marque os locais onde serão realizados os serviços");
					texto2.setAlignment(Element.ALIGN_LEFT);
					document.add(texto2);
					
					Image carro = Image.getInstance(Servico.class.getResource("/img/carro.jpg"));
					carro.setAbsolutePosition(35, 422);
					document.add(carro);
					
					Image vertical = Image.getInstance(Servico.class.getResource("/img/Vertical-Line.png"));
					vertical.scaleToFit(200, 200);
					vertical.setAbsolutePosition(310, 416);
					document.add(vertical);
					
					Paragraph marca = new Paragraph("Marca do carro:  " + rs.getString(4));
					marca.setAlignment(Element.ALIGN_RIGHT);
					document.add(marca);
					
					Paragraph modelo = new Paragraph("Modelo do carro:  " + rs.getString(5));
					modelo.setAlignment(Element.ALIGN_RIGHT);
					document.add(modelo);
					
					Paragraph servico = new Paragraph("Serviço:  " + rs.getString(6));
					servico.setAlignment(Element.ALIGN_RIGHT);
					document.add(servico);
					
					Paragraph status = new Paragraph("Status do serviço:  " + rs.getString(7));
					status.setAlignment(Element.ALIGN_RIGHT);
					document.add(status);
					
					Paragraph valor = new Paragraph("Valor do serviço:  " + rs.getString(9));
					valor.setAlignment(Element.ALIGN_RIGHT);
					document.add(valor);
					
					Paragraph datasaida = new Paragraph("Data de Saída:  " + rs.getString(10));
					datasaida.setAlignment(Element.ALIGN_RIGHT);
					document.add(datasaida);
					
					Paragraph cliente1 = new Paragraph("Cliente:  " + rs.getString(11));
					cliente1.setAlignment(Element.ALIGN_RIGHT);
					document.add(cliente1);
					
					Paragraph cliente2 = new Paragraph("Número do Cliente:  " + rs.getString(12));
					cliente2.setAlignment(Element.ALIGN_RIGHT);
					document.add(cliente2);
					
					Paragraph linha6 = new Paragraph(" ");
					linha6.setAlignment(Element.ALIGN_CENTER);
					document.add(linha6);
					
					Paragraph linha7 = new Paragraph(" ");
					linha6.setAlignment(Element.ALIGN_CENTER);
					document.add(linha7);
					
					Paragraph texto3 = new Paragraph("Observações");
					texto3.setAlignment(Element.ALIGN_CENTER);
					document.add(texto3);
					
					Image horizontal = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal.scaleToFit(500, 40);
					horizontal.setAbsolutePosition(50, 350);
					document.add(horizontal);
					
					Image horizontal1 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal1.scaleToFit(500, 40);
					horizontal1.setAbsolutePosition(50, 330);
					document.add(horizontal1);
					
					Image horizontal12 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal12.scaleToFit(500, 40);
					horizontal12.setAbsolutePosition(50, 310);
					document.add(horizontal12);
					
					Image horizontal13 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal13.scaleToFit(500, 40);
					horizontal13.setAbsolutePosition(50, 290);
					document.add(horizontal13);
					
					Image horizontal14 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal14.scaleToFit(500, 40);
					horizontal14.setAbsolutePosition(50, 270);
					document.add(horizontal14);
					
					Image horizontal15 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal15.scaleToFit(500, 40);
					horizontal15.setAbsolutePosition(50, 250);
					document.add(horizontal15);
					
					Image horizontal16 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal16.scaleToFit(500, 40);
					horizontal16.setAbsolutePosition(50, 230);
					document.add(horizontal16);
					
					Image horizontal17 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal17.scaleToFit(500, 40);
					horizontal17.setAbsolutePosition(50, 210);
					document.add(horizontal17);
					
					Image horizontal18 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal18.scaleToFit(500, 40);
					horizontal18.setAbsolutePosition(50, 190);
					document.add(horizontal18);
					
					Image horizontal19 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal19.scaleToFit(500, 40);
					horizontal19.setAbsolutePosition(50, 170);
					document.add(horizontal19);
					
					Image horizontal10 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal10.scaleToFit(500, 40);
					horizontal10.setAbsolutePosition(50, 150);
					document.add(horizontal10);
					
					Paragraph linha8 = new Paragraph(" ");
					linha8.setAlignment(Element.ALIGN_CENTER);
					document.add(linha8);
					
					Paragraph linha67 = new Paragraph(" ");
					linha67.setAlignment(Element.ALIGN_CENTER);
					document.add(linha67);
					
					Paragraph linha68 = new Paragraph(" ");
					linha68.setAlignment(Element.ALIGN_CENTER);
					document.add(linha68);
					
					Paragraph linha69 = new Paragraph(" ");
					linha69.setAlignment(Element.ALIGN_CENTER);
					document.add(linha69);
					
					Paragraph linha61 = new Paragraph(" ");
					linha61.setAlignment(Element.ALIGN_CENTER);
					document.add(linha61);
					
					Paragraph linha62 = new Paragraph(" ");
					linha62.setAlignment(Element.ALIGN_CENTER);
					document.add(linha62);
					
					Paragraph linha623 = new Paragraph(" ");
					linha623.setAlignment(Element.ALIGN_CENTER);
					document.add(linha623);

					Paragraph linha624 = new Paragraph(" ");
					linha624.setAlignment(Element.ALIGN_CENTER);
					document.add(linha624);

					Paragraph linha611 = new Paragraph(" ");
					linha611.setAlignment(Element.ALIGN_CENTER);
					document.add(linha611);
					
					Paragraph linha622 = new Paragraph(" ");
					linha622.setAlignment(Element.ALIGN_CENTER);
					document.add(linha622);


					Paragraph linha6223 = new Paragraph(" ");
					linha6223.setAlignment(Element.ALIGN_CENTER);
					document.add(linha6223);
					
					Paragraph linha633 = new Paragraph(" ");
					linha633.setAlignment(Element.ALIGN_CENTER);
					document.add(linha633);
					
					Paragraph linha644 = new Paragraph(" ");
					linha644.setAlignment(Element.ALIGN_CENTER);
					document.add(linha644);
					
					Paragraph linha655 = new Paragraph(" ");
					linha655.setAlignment(Element.ALIGN_CENTER);
					document.add(linha655);
					
					Paragraph linha66 = new Paragraph(" ");
					linha66.setAlignment(Element.ALIGN_CENTER);
					document.add(linha66);
					
					Paragraph texto5 = new Paragraph("            Assinatura do Cliente                                             Assinatura do responsável pela OS");
					texto5.setAlignment(Element.ALIGN_LEFT);
					document.add(texto5);
					
					Image horizontal101 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal101.scaleToFit(120, 40);
					horizontal101.setAbsolutePosition(75, 60);
					document.add(horizontal101);
					
					Image horizontal1012 = Image.getInstance(Servico.class.getResource("/img/horizontal.jpg"));
					horizontal1012.scaleToFit(150, 40);
					horizontal1012.setAbsolutePosition(360, 60);
					document.add(horizontal1012);
					
					
				}
				// fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacionar e usar o leitor padrao de pdf para
		// exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	}

	private void limparCampos() {
		txtID.setText(null);
		txtMarca.setText(null);
		txtOS.setText(null);
		txtCliente.setText(null);
		txtAno.setText(null);
		txtCor.setText(null);
		txtObs.setText(null);
		txtModelo.setText(null);
		txtServico.setText(null);
		txtDataOS.setDate(null);
		txtPlaca.setText(null);
		txtMec.setText(null);
		txtIDMec.setText(null);
		cboStatus.setSelectedItem("");
		txtValor.setText("0");
		txtDataSaida.setDate(null);
		btnBuscar.setEnabled(true);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		txtCliente.setEditable(true);
		txtMec.setEditable(true);
	}
}

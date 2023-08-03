package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import util.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Clientes extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JTextField txtNome;
	private JTextField txtNumero;
	private JTextField txtEmail;
	private JButton btnLimpar;
	private JButton btnBuscar;
	private JTextField txtNum;
	private JTextField txtComplemento;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnCriar;
	private JTextField txtID;
	private JScrollPane scrollPane;
	private JList<String> listaUsuarios;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Funilaria.png")));
		setTitle("Cliente");
		setBounds(100, 100, 423, 582);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(87, 107, 270, 89);
		getContentPane().add(scrollPane);
		
		listaUsuarios = new JList();
		scrollPane.setViewportView(listaUsuarios);
		listaUsuarios.setBorder(null);
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		
		
		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setBounds(33, 214, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtCep = new JTextField();
		txtCep.setBounds(117, 211, 119, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);
		txtCep.setDocument(new Validador(20));
		
		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(33, 252, 70, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(117, 249, 240, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		txtEndereco.setDocument(new Validador(80));
		
		JLabel lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setBounds(33, 295, 70, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(117, 292, 240, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);
		txtBairro.setDocument(new Validador(80));
		
		JLabel lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setBounds(33, 333, 70, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(117, 330, 240, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);
		txtCidade.setDocument(new Validador(40));
		
		JLabel lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setBounds(33, 370, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(117, 366, 68, 22);
		getContentPane().add(cboUf);
		
		JButton btnBuscarCep = new JButton("Buscar");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(268, 210, 89, 23);
		getContentPane().add(btnBuscarCep);
		
		JLabel lblNewLabel_5 = new JLabel("Dados do Cliente");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_5.setBounds(87, 11, 230, 47);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Nome");
		lblNewLabel_6.setBounds(33, 89, 46, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Celular");
		lblNewLabel_7.setBounds(33, 131, 46, 14);
		getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("E-mail");
		lblNewLabel_8.setBounds(33, 173, 46, 14);
		getContentPane().add(lblNewLabel_8);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.setBounds(87, 86, 270, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));
		
		txtNumero = new JTextField();
		txtNumero.setBounds(87, 128, 149, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);
		txtNumero.setDocument(new Validador(20));
		
		txtEmail = new JTextField();
		txtEmail.setBounds(87, 170, 180, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(50));
		
		btnCriar = new JButton("");
		btnCriar.setContentAreaFilled(false);
		btnCriar.setBorderPainted(false);
		btnCriar.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCriar.setBounds(33, 475, 89, 58);
		getContentPane().add(btnCriar);
		
		btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/clear.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(296, 475, 89, 58);
		getContentPane().add(btnLimpar);
		
		btnBuscar = new JButton("");
		btnBuscar.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		
		JLabel lblnum = new JLabel("Número");
		lblnum.setBounds(33, 412, 70, 14);
		getContentPane().add(lblnum);
		
		JLabel lblcomp = new JLabel("Complemento");
		lblcomp.setBounds(33, 449, 95, 14);
		getContentPane().add(lblcomp);
		
		txtNum = new JTextField();
		txtNum.setBounds(117, 409, 86, 20);
		getContentPane().add(txtNum);
		txtNum.setColumns(10);
		txtNum.setDocument(new Validador(8));
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(117, 446, 240, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		txtComplemento.setDocument(new Validador(50));
		
		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUsuario();
			}
		});
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnUpdate.setBounds(117, 475, 89, 58);
		getContentPane().add(btnUpdate);
		
		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete.png")));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(202, 475, 89, 58);
		getContentPane().add(btnDelete);
		
		txtID = new JTextField();
		txtID.setVisible(false);
		txtID.setBounds(36, 58, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

	}// fim do construtor
	
	/**
	 * Método que busca o Endereço automaticamente em WebService
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = txtCep.getText();
		String resultado = null;
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml"); //importado do java.net
		SAXReader xml = new SAXReader();//importado do orgdom4j
		Document documento = xml.read(url); //importado do orgdom4j
		Element root = documento.getRootElement(); //importado do orgdom4j
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) { //importado do java.util
			Element element = it.next();
			if (element.getQualifiedName().equals("cidade")) {
				txtCidade.setText(element.getText());
			}
			if (element.getQualifiedName().equals("bairro")) {
				txtBairro.setText(element.getText());
			}
			if (element.getQualifiedName().equals("uf")) {
				cboUf.setSelectedItem(element.getText());
			}
			if (element.getQualifiedName().equals("tipo_logradouro")) {
				tipoLogradouro = element.getText();
			}
			if (element.getQualifiedName().equals("logradouro")) {
				logradouro = element.getText();
			}
			if (element.getQualifiedName().equals("resultado")) {
				resultado = element.getText();
				if (resultado.equals("0")) {
					JOptionPane.showMessageDialog(null, "CEP inválido");
				}
			}
			
		}
	txtEndereco.setText(tipoLogradouro + " " + logradouro);
	} catch (Exception e) {
		System.out.println(e);
	}
	}
	
	private void adicionarUsuario() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do cliente");
			txtNumero.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do cliente");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do cliente");
			txtCidade.requestFocus();
		} else if (((String) cboUf.getSelectedItem()).isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o UF do cliente");
			cboUf.requestFocus();
		} else if (txtNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do cliente");
			txtNum.requestFocus();
		} else {
			// lógica principal
			// a linha abaixo cria uma variável de nome create que recebe o código sql
			String create = "insert into clientes(nome,numero,email,cep,endereco,bairro,cidade,uf,num,complemento) values (?,?,?,?,?,?,?,?,?,?)";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(create);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtNumero.getText());
			pst.setString(3, txtEmail.getText());
			pst.setString(4, txtCep.getText());
			pst.setString(5, txtEndereco.getText());
			pst.setString(6, txtBairro.getText());
			pst.setString(7, txtCidade.getText());
			pst.setString(8, cboUf.getSelectedItem().toString());
			pst.setString(9, txtNum.getText());
			pst.setString(10, txtComplemento.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cliente salvo com sucesso");
			con.close();
			limparCampos();
			btnCriar.setEnabled(false);
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuário não atualizado.\nEste número já está em uso");
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método novoUsuario
	}
	
	private void buscarUsuario() {
		// System.out.println("teste do botão buscar");
		String read = "select * from clientes where nome = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtNome.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtNumero.setText(rs.getString(3));
				txtEmail.setText(rs.getString(4));
				txtCep.setText(rs.getString(5));
				txtEndereco.setText(rs.getString(6));
				txtBairro.setText(rs.getString(7));
				txtCidade.setText(rs.getString(8));
				cboUf.setSelectedItem(rs.getString(9));
				txtNum.setText(rs.getString(10));
				txtComplemento.setText(rs.getString(11));
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Cliente inexistente");
				txtNome.requestFocus();
				btnCriar.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método buscarUsuario
	
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
		String readLista = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
				if (txtNome.getText().isEmpty()) {
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
			String read2 = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtNumero.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4));
					txtCep.setText(rs.getString(5));
					txtEndereco.setText(rs.getString(6));
					txtBairro.setText(rs.getString(7));
					txtCidade.setText(rs.getString(8));
					cboUf.setSelectedItem(rs.getString(9));
					txtNum.setText(rs.getString(10));
					txtComplemento.setText(rs.getString(11));
					//desativar a caixa de senha
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnCriar.setEnabled(false);
				} else {
					System.out.println("usuário não cadastrado");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			//se não existir um índice(usuário) na lista
			scrollPane.setVisible(false);
		}
	}// fim do método buscarUsuario
	
	/**
	 * Método responsável pela edição dos dados de um contato
	 */
	private void editarUsuario() {
		//System.out.println("teste do botão editar");
		//validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do cliente");
			txtNumero.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do cliente");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do cliente");
			txtCidade.requestFocus();
		} else if (((String) cboUf.getSelectedItem()).isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o UF do cliente");
			cboUf.requestFocus();
		} else if (txtNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do cliente");
			txtNum.requestFocus();
		} else {
			// lógica principal
			// a linha abaixo cria uma variável de nome create que recebe o código sql
			String update = "update clientes set nome=?,numero=?,email=?,cep=?,endereco=?,bairro=?,cidade=?,uf=?,num=?,complemento=? where id = ?";
			//tratamento de exceções
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query(substituir ????)
				pst = con.prepareStatement(update);
				pst.setString(11, txtID.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtNumero.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, cboUf.getSelectedItem().toString());
				pst.setString(9, txtNum.getText());
				pst.setString(10, txtComplemento.getText());
				//executar o update no banco
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do contato alterados com sucesso");
				//NUNCA esquecer de fechar a conexão
				con.close();
				//limpar os campos
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não atualizado.\nEste número já está em uso");
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	}
	
	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirUsuario() {
		//System.out.println("teste do botão excluir");
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where nome=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtNome.getText());
				pst.executeUpdate();				
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				con.close();
				limparCampos();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método excluirUsuario
	
	private void limparCampos() {
		txtNome.setText(null);
		txtNumero.setText(null);
		txtEmail.setText(null);
		cboUf.setSelectedItem(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtCidade.setText(null);
		txtBairro.setText(null);
		txtNum.setText(null);
		txtComplemento.setText(null);
		btnCriar.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
		scrollPane.setVisible(false);
	}
}//fim do codigo

package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.DAO;

public class Mecanicos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTextField txtNome;
	private JTextField txtID;
	private JButton btnLimpar;
	private JScrollPane scrollPane;
	private JList listarMecanicos;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mecanicos dialog = new Mecanicos();
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
	public Mecanicos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Mecanicos.class.getResource("/img/Funilaria.png")));
		setTitle("Mecanicos");
		setBounds(100, 100, 424, 260);
		getContentPane().setLayout(null);
		
		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarMecanico();
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(95, 117, 269, 80);
		getContentPane().add(scrollPane);
		
		listarMecanicos = new JList();
		listarMecanicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarMecanicoLista();
			}
		});
		scrollPane.setViewportView(listarMecanicos);
		btnAdicionar.setIcon(new ImageIcon(Mecanicos.class.getResource("/img/create.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(42, 130, 89, 67);
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirMec();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(new ImageIcon(Mecanicos.class.getResource("/img/delete.png")));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(275, 130, 89, 67);
		getContentPane().add(btnExcluir);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarMecanico();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setIcon(new ImageIcon(Mecanicos.class.getResource("/img/update.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(160, 130, 89, 67);
		getContentPane().add(btnEditar);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarMecanicos();
			}
		});
		txtNome.setBounds(98, 96, 267, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(42, 99, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBounds(99, 51, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(42, 54, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Mecanicos.class.getResource("/img/clear.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(288, 11, 89, 57);
		getContentPane().add(btnLimpar);
		


	}// fim do construtor
	
	
	private void adicionarMecanico() {
		// validação do combobox
		// if(cboPerfil.getSelectedItem().equal("") )
		// System.out.println("teste do botão adicionar");
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do mecânico");
		} else {
		String create = "insert into mecanicos (nome) values (?)";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(create);
			pst.setString(1, txtNome.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Funileiro adicionado com sucesso");
			limparCampos();
			con.close();
			// tratamento de exceção em caso de duplicação do login
		} catch (Exception e1) {
			System.out.println(e1);
		}
		}
	}// fim do método novoUsuario
	
	/**
	 * Método para listar os usuários (pesquisa avançada)
	 */
	private void listarMecanicos() {
		// System.out.println("teste");
		// criar um objeto -> lista (vetor dinâmico) para exibir a
		// lista de usuários do banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listarMecanicos.setModel(modelo);
		// CRUD Read
		String readLista = "select * from mecanicos where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
	private void buscarMecanicoLista() {
		// selecionar a linha da lista ("indice do vetor")
		int linha = listarMecanicos.getSelectedIndex();
		// se a linha(índice) da lista for selecionada
		if (linha >= 0) {
			System.out.println(linha);
			// limit - instrução sql que limita o número de registros selecionados da tabela
			// (índice,numéro de registros) Ex (1,1)
			String read2 = "select * from mecanicos where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					System.out.println("Funileiro não cadastrado");
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
	private void editarMecanico() {
		//validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do mecânico");
			txtNome.requestFocus();
		} else {
			// lógica principal
			// a linha abaixo cria uma variável de nome create que recebe o código sql
			String update = "update mecanicos set nome=? where idmec = ?";
			//tratamento de exceções
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(2, txtID.getText());
				pst.setString(1, txtNome.getText());
				//executar o update no banco
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do funileiro alterados com sucesso");
				//NUNCA esquecer de fechar a conexão
				con.close();
				//limpar os campos
				limparCampos();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	}
	
	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirMec() {
		//System.out.println("teste do botão excluir");
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste mecânico?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from mecanicos where nome=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtNome.getText());
				pst.executeUpdate();				
				JOptionPane.showMessageDialog(null, "Mecânico excluído");
				con.close();
				limparCampos();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método excluirUsuario
	
	private void limparCampos() {
		txtNome.setText(null);
		txtID.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnAdicionar.setEnabled(true);
	}
}//fim do codigo

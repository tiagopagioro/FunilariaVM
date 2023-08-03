package view;

import java.awt.Color;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import model.DAO;
import util.Validador;

public class Usuarios extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnLimpar;
	private JButton btnUpdate;
	private JButton btnCriar;
	private JButton btnDeletar;
	private JButton btnSearch;
	private JComboBox cboPerfil;
	private JCheckBox chkSenha;
	private JList<String> listaUsuarios;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/Funilaria.png")));
		setTitle("Usuários");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 596, 341);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(81, 133, 420, 89);
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

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(36, 38, 46, 14);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBounds(81, 35, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(36, 120, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(36, 80, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(36, 166, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtNome = new JTextField();
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBorder(null);
		txtNome.setBounds(81, 117, 420, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		btnCriar = new JButton("");
		btnCriar.setEnabled(false);
		btnCriar.setBorderPainted(false);
		btnCriar.setContentAreaFilled(false);
		btnCriar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/create.png")));
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCriar.setBounds(36, 220, 89, 74);
		getContentPane().add(btnCriar);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 163, 197, 20);
		getContentPane().add(txtSenha);

		btnSearch = new JButton("");
		btnSearch.setBorderPainted(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnSearch.setBounds(258, 69, 65, 37);
		getContentPane().add(btnSearch);
		
		getRootPane().setDefaultButton(btnSearch);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSenha.isSelected()) {
					editarUsuarioSenha();
				} else {
					editarUsuario();
				}
			}
		});
		btnUpdate.setBounds(135, 220, 89, 74);
		getContentPane().add(btnUpdate);

		btnDeletar = new JButton("");
		btnDeletar.setEnabled(false);
		btnDeletar.setBorderPainted(false);
		btnDeletar.setContentAreaFilled(false);
		btnDeletar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete.png")));
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnDeletar.setBounds(234, 220, 89, 74);
		getContentPane().add(btnDeletar);
		
		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setBounds(338, 166, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		btnLimpar = new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/clear.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(412, 220, 89, 74);
		getContentPane().add(btnLimpar);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil.setBounds(379, 162, 127, 22);
		getContentPane().add(cboPerfil);
		
		chkSenha = new JCheckBox("Alterar senha");
		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSenha.setEditable(true);
				txtSenha.setText(null);
				txtSenha.requestFocus();
				txtSenha.setBackground(Color.YELLOW);
			}
		});
		chkSenha.setVisible(false);
		chkSenha.setBounds(91, 190, 133, 23);
		getContentPane().add(chkSenha);
		txtSenha.setDocument(new Validador(250));
		
				txtLogin = new JTextField();
				txtLogin.setBounds(79, 77, 176, 20);
				getContentPane().add(txtLogin);
				txtLogin.setColumns(10);
				txtLogin.setDocument(new Validador(20));

	}// fim do construtor

	/**
	 * Método para adicionar um novo usuário
	 */
	private void adicionarUsuario() {
		// System.out.println("teste do botão adicionar");
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuário");
			txtSenha.requestFocus();
		} else if (((String) cboPerfil.getSelectedItem()).isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de perfil do usuário");
			txtSenha.requestFocus();		
		} else {
		String create = "insert into usuarios(nome,login,senha,perfil) values(?,?,md5(?),?)";
		String capturaSenha = new String(txtSenha.getPassword());
		try {
			con = dao.conectar();
			pst = con.prepareStatement(create);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, capturaSenha);
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
			limparCampos();
			con.close();
			//tratamento de exceção em caso de login duplicado
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está em uso");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e2) {
			System.out.println(e2);
		}
		}
	}// fim do método novoUsuario

	/**
	 * Método para buscar um usuário
	 */
	private void buscarUsuario() {
		// System.out.println("teste do botão buscar");
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				//mostrar o checkbox da troca de senha
				chkSenha.setVisible(true);
				//desativar a caixa de senha
				txtSenha.setEditable(false);
				btnUpdate.setEnabled(true);
				btnDeletar.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				// ativar o botão adicionar
				btnCriar.setEnabled(true);
				// desativar o botão buscar
				btnSearch.setEnabled(false);
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
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
	 * Método para editar os dados do usuário e senha
	 */
	
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
			String read2 = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					// mostrar o checkbox (troca de senha)
					chkSenha.setVisible(true);
					// desabilitar a caixa de senha
					txtSenha.setEditable(false);
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

	
	private void editarUsuarioSenha() {
		// System.out.println("teste do botão editar");
		String capturaSenha = new String(txtSenha.getPassword());
		String update = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where iduser=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, capturaSenha);
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.setString(5, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuário não atualizado.\nEste login já está em uso");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuario
	
	/**
	 * Método para editar os dados do usuario exceto a senha
	 */
	private void editarUsuario() {
		// System.out.println("teste do botão editar");
		String update = "update usuarios set nome=?,login=?,perfil=? where iduser=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, cboPerfil.getSelectedItem().toString());
			pst.setString(4, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
			limparCampos();
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuário não atualizado.\nEste login já está em uso");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuario

	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirUsuario() {
		//System.out.println("teste do botão excluir");
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();				
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método excluirUsuario
	
	private void limparCampos() {
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		txtID.setText(null);
		btnCriar.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDeletar.setEnabled(false);
		btnSearch.setEnabled(true);
		scrollPane.setVisible(false);
		chkSenha.setVisible(false);
		txtSenha.setBackground(Color.WHITE);
	}
}// fim do código

package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Relatorios extends JDialog {
	private JButton btnClientes;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnClientes_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setTitle("Relatórios");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Relatórios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(179, 11, 91, 30);
		getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 57, 199, 66);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Clientes");
		lblNewLabel_1.setBounds(77, 4, 50, 19);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		{
			btnClientes = new JButton("");
			btnClientes.setBounds(74, 30, 56, 32);
			btnClientes.setFocusPainted(false);
			panel.add(btnClientes);
			btnClientes.setContentAreaFilled(false);
			btnClientes.setBorderPainted(false);
			btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4243338_basic_app_print_ux_icon (1).png")));
			
			JPanel panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_1.setBounds(225, 57, 199, 66);
			getContentPane().add(panel_1);
			
			JLabel lblNewLabel_1_1 = new JLabel("Aguardando Peças");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1_1.setBounds(38, 4, 134, 19);
			panel_1.add(lblNewLabel_1_1);
			
			btnClientes_1 = new JButton("");
			btnClientes_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					relatorioPecas();
				}
			});
			btnClientes_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4243338_basic_app_print_ux_icon (1).png")));
			btnClientes_1.setFocusPainted(false);
			btnClientes_1.setContentAreaFilled(false);
			btnClientes_1.setBorderPainted(false);
			btnClientes_1.setBounds(74, 30, 56, 32);
			panel_1.add(btnClientes_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_2.setBounds(10, 162, 199, 66);
			getContentPane().add(panel_2);
			
			JLabel lblNewLabel_1_2 = new JLabel("Aguardando Orçamento");
			lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1_2.setBounds(22, 5, 163, 19);
			panel_2.add(lblNewLabel_1_2);
			
			JButton btnClientes_2 = new JButton("");
			btnClientes_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					relatorioFunileiros();
				}
			});
			btnClientes_2.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4243338_basic_app_print_ux_icon (1).png")));
			btnClientes_2.setFocusPainted(false);
			btnClientes_2.setContentAreaFilled(false);
			btnClientes_2.setBorderPainted(false);
			btnClientes_2.setBounds(74, 30, 56, 32);
			panel_2.add(btnClientes_2);
			
			JPanel panel_2_1 = new JPanel();
			panel_2_1.setLayout(null);
			panel_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_2_1.setBounds(225, 162, 199, 66);
			getContentPane().add(panel_2_1);
			
			JLabel lblNewLabel_1_2_1 = new JLabel("Aguardando Aprovação Clientes");
			lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabel_1_2_1.setBounds(8, 0, 189, 32);
			panel_2_1.add(lblNewLabel_1_2_1);
			
			JButton btnClientes_2_1 = new JButton("");
			btnClientes_2_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					relatorioAgrClientes();
				}
			});
			btnClientes_2_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4243338_basic_app_print_ux_icon (1).png")));
			btnClientes_2_1.setFocusPainted(false);
			btnClientes_2_1.setContentAreaFilled(false);
			btnClientes_2_1.setBorderPainted(false);
			btnClientes_2_1.setBounds(74, 30, 56, 32);
			panel_2_1.add(btnClientes_2_1);
			btnClientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					relatorioClientes();
				}
			});
		}
	}
	
	private void relatorioClientes() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um paragrafo
			Paragraph os = new Paragraph("Clientes");
			os.setAlignment(Element.ALIGN_CENTER);
			document.add(os);
			document.add(new Paragraph(" "));
			//------------------------------------------------------------------------------------------
			//query (instrução sql para gerar o relatorio de clientes)
			String readClientes = "select nome,numero from clientes order by nome";
			try {
				con = dao.conectar();
				//preparar a query (executar instrução sql)
				pst = con.prepareStatement(readClientes);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				//criar uma tabela de duas colunas usando o framework (itextPDF)
				PdfPTable tabela = new PdfPTable(2); // (2) é o numero de colunas
				// criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Fone"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
				}
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacionar e usar o leitor padrao de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}
	
	private void relatorioPecas() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome peças.pdf
			PdfWriter.getInstance(document, new FileOutputStream("peças.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um paragrafo
			Paragraph os = new Paragraph("Aguardando Peças");
			os.setAlignment(Element.ALIGN_CENTER);
			document.add(os);
			document.add(new Paragraph(" "));
			//------------------------------------------------------------------------------------------
			//query (instrução sql para gerar o relatorio de clientes)
			String readPecas = "select os, marca,modelo,servico, nome, numero, usuario, date_format(dataOS,'%d/%m/%Y') as data_entrada, servicos.usuario as usuário, clientes.nome as cliente, clientes.numero from servicos inner join clientes on servicos.id = clientes.id where statusOS = 'Aguardando peças'";
			try {
				con = dao.conectar();
				
				//preparar a query (executar instrução sql)
				pst = con.prepareStatement(readPecas);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				//criar uma tabela de duas colunas usando o framework (itextPDF)
				PdfPTable tabela = new PdfPTable(8); // (2) é o numero de colunas
				// criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell (new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col3 = new PdfPCell (new Paragraph("Celular"));
				PdfPCell col4 = new PdfPCell (new Paragraph("Marca"));
				PdfPCell col5 = new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col6 = new PdfPCell (new Paragraph("Serviço"));
				PdfPCell col7 = new PdfPCell (new Paragraph("Data da OS"));
				PdfPCell col8 = new PdfPCell (new Paragraph("Cadatrada por:"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(7));
				}
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacionar e usar o leitor padrao de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("peças.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}
	
	private void relatorioFunileiros() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome peças.pdf
			PdfWriter.getInstance(document, new FileOutputStream("aguardando_orçamento.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um paragrafo
			Paragraph os = new Paragraph("Aguardando Orçamento");
			os.setAlignment(Element.ALIGN_CENTER);
			document.add(os);
			document.add(new Paragraph(" "));
			//------------------------------------------------------------------------------------------
			//query (instrução sql para gerar o relatorio de clientes)
			String readPecas = "select os, marca,modelo,servico, nome, numero, usuario, date_format(dataOS,'%d/%m/%Y') as data_entrada, servicos.usuario as usuário, clientes.nome as cliente, clientes.numero from servicos inner join clientes on servicos.id = clientes.id where statusOS = 'Aguardando orçamento'";
			try {
				con = dao.conectar();
				
				//preparar a query (executar instrução sql)
				pst = con.prepareStatement(readPecas);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				//criar uma tabela de duas colunas usando o framework (itextPDF)
				PdfPTable tabela = new PdfPTable(8); // (2) é o numero de colunas
				// criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell (new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col3 = new PdfPCell (new Paragraph("Celular"));
				PdfPCell col4 = new PdfPCell (new Paragraph("Marca"));
				PdfPCell col5 = new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col6 = new PdfPCell (new Paragraph("Serviço"));
				PdfPCell col7 = new PdfPCell (new Paragraph("Data da OS"));
				PdfPCell col8 = new PdfPCell (new Paragraph("Cadatrada por:"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(7));
				}
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacionar e usar o leitor padrao de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("peças.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}
	
	private void relatorioAgrClientes() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome peças.pdf
			PdfWriter.getInstance(document, new FileOutputStream("aguardando_peças.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um paragrafo
			Paragraph os = new Paragraph("Aguardando Peças");
			os.setAlignment(Element.ALIGN_CENTER);
			document.add(os);
			document.add(new Paragraph(" "));
			//------------------------------------------------------------------------------------------
			//query (instrução sql para gerar o relatorio de clientes)
			String readPecas = "select os, marca,modelo,servico, nome, numero, usuario, date_format(dataOS,'%d/%m/%Y') as data_entrada, servicos.usuario as usuário, clientes.nome as cliente, clientes.numero from servicos inner join clientes on servicos.id = clientes.id where statusOS = 'Aguardando aprovação'";
			try {
				con = dao.conectar();
				
				//preparar a query (executar instrução sql)
				pst = con.prepareStatement(readPecas);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				//criar uma tabela de duas colunas usando o framework (itextPDF)
				PdfPTable tabela = new PdfPTable(8); // (2) é o numero de colunas
				// criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell (new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col3 = new PdfPCell (new Paragraph("Celular"));
				PdfPCell col4 = new PdfPCell (new Paragraph("Marca"));
				PdfPCell col5 = new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col6 = new PdfPCell (new Paragraph("Serviço"));
				PdfPCell col7 = new PdfPCell (new Paragraph("Data da OS"));
				PdfPCell col8 = new PdfPCell (new Paragraph("Cadatrada por:"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(7));
				}
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacionar e usar o leitor padrao de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("peças.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}
}

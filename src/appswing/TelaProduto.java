package appswing;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import regras_negocio.Fachada;
import modelo.Produto;

public class TelaProduto extends JDialog {

	private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, descricaoField;
    private JButton atualizarButton, inserirButton, removerButton;
    private JScrollPane scrollPane;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelaProduto frame = new TelaProduto();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
    public TelaProduto(JFrame parent) {
        super(parent, "Gerenciar Produtos", true);
        setBounds(100, 100, 600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Painel da tabela (central)
        tableModel = new DefaultTableModel(new Object[]{"ID", "Descrição"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para inputs e botões (inferior)
        JPanel panelBottom = new JPanel(new BorderLayout());
        
        // Painel de inputs com FlowLayout
        JPanel panelInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInputs.add(new JLabel("Descrição:"));
        descricaoField = new JTextField(20); // Ajuste o tamanho do campo conforme necessário
        panelInputs.add(descricaoField);
        
        panelBottom.add(panelInputs, BorderLayout.NORTH); // Inputs no topo do painel inferior
        
        // Painel de botões
        JPanel panelButtons = new JPanel();
        atualizarButton = new JButton("Atualizar Lista");
        inserirButton = new JButton("Inserir Produto");
        removerButton = new JButton("Remover Produto");

        panelButtons.add(atualizarButton);
        panelButtons.add(inserirButton);
        panelButtons.add(removerButton);
        
        panelBottom.add(panelButtons, BorderLayout.SOUTH); // Botões na parte inferior do painel inferior
        
        // Adicionar o painel inferior à parte inferior da janela principal
        add(panelBottom, BorderLayout.SOUTH);

        // Carregar a lista de produtos ao abrir a tela
        carregarListaProdutos();
        
        // ActionListener para atualizar a lista de produtos
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarListaProdutos();
            }
        });
        
        // ActionListener para remover produto
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        }); 
        
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inserirProduto();
            }
        });   
    }
	
	 private void carregarListaProdutos() {
		 try {
		        // Chama o método da Fachada para listar os produtos
		        List<Produto> produtos = Fachada.listarProdutos();

		        // Verifica se a lista não é nula e tem produtos
		        if (produtos == null || produtos.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado.");
		            return;  // Sai do método se não houver produtos
		        }

		        // Limpa a tabela antes de adicionar novos produtos
		        tableModel.setRowCount(0);

		        // Itera sobre os produtos e os adiciona à tabela
		        for (Produto p : produtos) {
		            if (p != null) {
		                tableModel.addRow(new Object[]{p.getId(), p.getDescricao()});
		            }
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
		        e.printStackTrace();
		    }
	    }
	
	   private void inserirProduto() {
	        try {
	            String descricao = descricaoField.getText();
	            if (descricao.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "A descrição não pode estar vazia.");
	                return;
	            }
	            Fachada.cadastraProduto(descricao);
	            JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
	            descricaoField.setText("");
	            carregarListaProdutos();
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Erro ao inserir produto: " + e.getMessage());
	        }
	    }
	   
	   private void removerProduto() {
	        int selectedRow = table.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
	            return;
	        }
	        try {
	            int produtoId = (int) tableModel.getValueAt(selectedRow, 0);
	            Fachada.removerProduto(produtoId);
	            JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
	            carregarListaProdutos();
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + e.getMessage());
	        }
	    }
	   
	   public static void main(String[] args) {
	        JFrame framePrincipal = new JFrame();
	        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        framePrincipal.setSize(300, 200);
	        framePrincipal.setVisible(true);

	        JButton abrirTelaProdutoButton = new JButton("Abrir Tela de Produtos");
	        abrirTelaProdutoButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                TelaProduto telaProduto = new TelaProduto(framePrincipal);
	                telaProduto.setVisible(true);
	            }
	        });
	        framePrincipal.add(abrirTelaProdutoButton);
	    }
	   

}

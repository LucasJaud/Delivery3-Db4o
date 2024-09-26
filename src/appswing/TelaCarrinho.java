package appswing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import regras_negocio.Fachada;
import modelo.Produto;

public class TelaCarrinho extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable produtosTable, carrinhoTable;
	private JButton inserirButton, removerButton,atualizarButton ,entregaButton;
	private JScrollPane produtosScrollPane, carrinhoScrollPane;
	private JTextField descricaoField, idField;
    private DefaultTableModel produtosTableModel, carrinhoTableModel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			TelaCarrinho dialog = new TelaCarrinho();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
    public TelaCarrinho(JFrame parent) {
		super(parent, "Carrinho de Compras", true);
		setSize(800,600);
//		setBounds(100, 100, 450, 300);
		 getContentPane().setLayout(new BorderLayout());
	     contentPanel.setLayout(new BorderLayout());
	     contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	     getContentPane().add(contentPanel, BorderLayout.CENTER);
		
	  
	        produtosTableModel = new DefaultTableModel(new Object[]{"ID", "Descrição"}, 0);
	        produtosTable = new JTable(produtosTableModel);
	        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        produtosScrollPane = new JScrollPane(produtosTable);
		
	     
	        carrinhoTableModel = new DefaultTableModel(new Object[]{"ID", "Descrição"}, 0);
	        carrinhoTable = new JTable(carrinhoTableModel);
	        carrinhoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        carrinhoScrollPane = new JScrollPane(carrinhoTable);
        
	    
	        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, produtosScrollPane, carrinhoScrollPane);
	        splitPane.setDividerLocation(250);  
	        contentPanel.add(splitPane, BorderLayout.CENTER);

	        
	        JPanel panelInputsButtons = new JPanel(new GridLayout(2, 1, 10, 10));
	        
	        
	        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        inputPanel.add(new JLabel("ID do Produto:"));
	        idField = new JTextField(10);
	        inputPanel.add(idField);
	        panelInputsButtons.add(inputPanel);
	
	     
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        inserirButton = new JButton("Inserir no Carrinho");
	        removerButton = new JButton("Remover do Carrinho");
	        entregaButton = new JButton("Finalizar");
	        atualizarButton = new JButton("Atualizar");
	        buttonPanel.add(inserirButton);
	        buttonPanel.add(removerButton);
	        buttonPanel.add(atualizarButton);
	        buttonPanel.add(entregaButton);
	        panelInputsButtons.add(buttonPanel);

	        getContentPane().add(panelInputsButtons, BorderLayout.SOUTH);

	        
	        carregarListaProdutos();
	        
	     
	        inserirButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                inserirProduto();
	            }
	        });

	        
	        removerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                removerProduto();
	            }
	        });
	        
	        atualizarButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	carregarCarrinhoProduto();
	            }
	        });
	        
	        entregaButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                fazerEntrega();
	            }
	        });
	        
		
	}
	
	private void carregarListaProdutos() {
		 try {
		        List<Produto> produtos = Fachada.listarProdutos();

		        
		        if (produtos == null || produtos.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado.");
		            return;  
		        }

		       
		        produtosTableModel.setRowCount(0);

		       
		        for (Produto p : produtos) {
		            if (p != null) {
		            	produtosTableModel.addRow(new Object[]{p.getId(), p.getDescricao()});
		            }
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
		        e.printStackTrace();
		    }
	    }
	
	private void carregarCarrinhoProduto() {
		try {
			
			List<Produto> carrinho =Fachada.getListaProdutosTemporaria();
//			if (carrinho == null || carrinho.isEmpty())
//	            return;
			
			carrinhoTableModel.setRowCount(0);
			for (Produto p : carrinho) {
	            if (p != null) {
	            	carrinhoTableModel.addRow(new Object[]{p.getId(), p.getDescricao()});
	            }
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	
	private void inserirProduto() {
        try {
            String id = idField.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "o id não pode ser vazio");
                return;
            }
            Fachada.adicionarProdutoTemporario(Integer.parseInt(id));
            JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
            idField.setText("");
            carregarCarrinhoProduto();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir produto: " + e.getMessage());
        }
    }
	
	private void removerProduto() {
        int selectedRow = carrinhoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
            return;
        }
        try {
            int produtoId = (int) carrinhoTableModel.getValueAt(selectedRow, 0);
            Fachada.removerProdutoTemporario(produtoId);
            JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
            carregarCarrinhoProduto();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + e.getMessage());
        }
    }
	
	private void fazerEntrega() {
		TelaFinalizarCompra telaFinalizar = new TelaFinalizarCompra(this);
        telaFinalizar.setVisible(true);
	} 
}

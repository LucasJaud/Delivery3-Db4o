package appswing;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import modelo.Entregador;
import modelo.Produto;
import modelo.Entrega;
import regras_negocio.Fachada;

public class TelaEntrega extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
    private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			TelaEntrega dialog = new TelaEntrega();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
    public TelaEntrega(JFrame parent) {
        super(parent, "Consulta de Entregas", true); 
        setBounds(100, 100, 600, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Data", "Endereço", "Entregador", "Ver Produtos"});

        
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 3, 10, 10)); 
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Campo de busca por endereço
        JLabel lblEndereco = new JLabel("Buscar por Endereço:");
        bottomPanel.add(lblEndereco);
        
        JTextField enderecoField = new JTextField();
        bottomPanel.add(enderecoField);

        JButton btnBuscarEndereco = new JButton("Buscar");
        btnBuscarEndereco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String endereco = enderecoField.getText();
                if (!endereco.isEmpty()) {
                    buscarPorEndereco(endereco);
                    enderecoField.setText("");
                } else {
                    JOptionPane.showMessageDialog(TelaEntrega.this, "Digite um endereço.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(btnBuscarEndereco);

        // Campo de busca por produto (ID)
        JLabel lblProduto = new JLabel("Buscar por ID do Produto:");
        bottomPanel.add(lblProduto);

        JTextField produtoField = new JTextField();
        bottomPanel.add(produtoField);

        JButton btnBuscarProduto = new JButton("Buscar");
        btnBuscarProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int produtoId = Integer.parseInt(produtoField.getText());
                    buscarPorProduto(produtoId);
                    produtoField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaEntrega.this, "Digite um ID de produto válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(btnBuscarProduto);

        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarListaEntregas();
            }
        });
        bottomPanel.add(new JLabel()); 
        bottomPanel.add(btnAtualizar);

                atualizarListaEntregas();

        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                
                if (column == 4) {
                    int entregaId = (int) tableModel.getValueAt(row, 0);
                    mostrarProdutosEntrega(entregaId);
                }
            }
        });
    }
    
    private void buscarPorEndereco(String endereco) {
        try {
            List<Entrega> entregas = Fachada.entregasEndereco(endereco);
            atualizarTabela(entregas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar por endereço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPorProduto(int produtoId) {
        try {
            List<Entrega> entregas = Fachada.entregasProduto(produtoId);
            atualizarTabela(entregas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar por ID do produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTabela(List<Entrega> entregas) {
        tableModel.setRowCount(0);
        
        for (Entrega entrega : entregas) {
            tableModel.addRow(new Object[]{entrega.getId(), entrega.getData(), entrega.getEndereco(), entrega.getEntregador().getNome(), "Ver Produtos"});
        }
    }
    
	
    private void atualizarListaEntregas() {
        try {
            
            tableModel.setRowCount(0);

            
            List<Entrega> entregas = Fachada.listarEntregas();

            
            for (Entrega entrega : entregas) {
                tableModel.addRow(new Object[]{
                    entrega.getId(),
                    entrega.getData(),
                    entrega.getEndereco(),
                    entrega.getEntregador().getNome(),
                    "Ver Produtos" 
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar entregas: " + e.getMessage());
        }
    }
    
    
    private void mostrarProdutosEntrega(int entregaId) {
        try {
            Entrega entrega = Fachada.entregaPorId(entregaId);
            List<Produto> produtos = entrega.getProdutos();

            
            JDialog produtosDialog = new JDialog(this, "Produtos da Entrega " + entregaId, true);
            produtosDialog.setSize(400, 300);
            produtosDialog.setLayout(new BorderLayout());

            
            DefaultTableModel produtosTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Descrição"}
            );

            JTable produtosTable = new JTable(produtosTableModel);
            JScrollPane produtosScrollPane = new JScrollPane(produtosTable);
            produtosDialog.add(produtosScrollPane, BorderLayout.CENTER);

            
            for (Produto produto : produtos) {
                produtosTableModel.addRow(new Object[]{
                    produto.getId(),
                    produto.getDescricao()
                });
            }

            produtosDialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos da entrega: " + e.getMessage());
        }
    }
	
	}



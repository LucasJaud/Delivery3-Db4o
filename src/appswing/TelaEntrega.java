package appswing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		super(parent, "Consulta de Entregas", true); // 'true' indica que é modal
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        
        tableModel = new DefaultTableModel(new Object[][]{},new String[]{"ID", "Data", "Endereço", "Entregador","Ver Produtos"});
        
     // Tabela
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior com botão de atualizar
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarListaEntregas();
            }
        });
        panel.add(btnAtualizar);
        
        // Carrega a lista de entregas ao abrir a tela
        atualizarListaEntregas();

        // Adiciona um listener para clicar e ver os produtos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                // Verifica se o botão de "Ver Produtos" foi clicado
                if (column == 4) {
                    int entregaId = (int) tableModel.getValueAt(row, 0);
                    mostrarProdutosEntrega(entregaId);
                }
            }
        });
        
		}
	
	// Método para carregar a lista de entregas no JTable
    private void atualizarListaEntregas() {
        try {
            // Limpa a tabela atual
            tableModel.setRowCount(0);

            // Busca as entregas no banco
            List<Entrega> entregas = Fachada.listarEntregas();

            // Adiciona as entregas na tabela
            for (Entrega entrega : entregas) {
                tableModel.addRow(new Object[]{
                    entrega.getId(),
                    entrega.getData(),
                    entrega.getEndereco(),
                    entrega.getEntregador().getNome(),
                    "Ver Produtos" // Botão para ver produtos
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar entregas: " + e.getMessage());
        }
    }
    
    // Método para exibir os produtos de uma entrega em um novo JDialog
    private void mostrarProdutosEntrega(int entregaId) {
        try {
            Entrega entrega = Fachada.entregaPorId(entregaId);
            List<Produto> produtos = entrega.getProdutos();

            // Criar um novo JDialog para mostrar os produtos
            JDialog produtosDialog = new JDialog(this, "Produtos da Entrega " + entregaId, true);
            produtosDialog.setSize(400, 300);
            produtosDialog.setLayout(new BorderLayout());

            // Modelo da tabela de produtos
            DefaultTableModel produtosTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Descrição"}
            );

            JTable produtosTable = new JTable(produtosTableModel);
            JScrollPane produtosScrollPane = new JScrollPane(produtosTable);
            produtosDialog.add(produtosScrollPane, BorderLayout.CENTER);

            // Adiciona os produtos na tabela
            for (Produto produto : produtos) {
                produtosTableModel.addRow(new Object[]{
                    produto.getId(),
                    produto.getDesc()
                });
            }

            produtosDialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos da entrega: " + e.getMessage());
        }
    }
	
	}



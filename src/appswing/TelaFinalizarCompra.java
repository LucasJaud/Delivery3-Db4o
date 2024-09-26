package appswing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import regras_negocio.Fachada;

public class TelaFinalizarCompra extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton finalizarButton;
	private JTable carrinhoTable;
    private JTextField entregadorField, enderecoField, dataField;
    private DefaultTableModel carrinhoTableModel;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelaFinalizarCompra dialog = new TelaFinalizarCompra();
//					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//					dialog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the dialog.
	 */
    public TelaFinalizarCompra(Window parent) {
		super(parent, "Finalizar Compra", ModalityType.APPLICATION_MODAL);
        setSize(600, 400);
        setLayout(new BorderLayout());
        
     // Painel de informações de entrega
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
     // Campos de informações de entrega
        infoPanel.add(new JLabel("Entregador:"));
        entregadorField = new JTextField();
        infoPanel.add(entregadorField);
        
        infoPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        infoPanel.add(enderecoField);

        infoPanel.add(new JLabel("Data:"));
        dataField = new JTextField();
        infoPanel.add(dataField);
        
        add(infoPanel, BorderLayout.NORTH);
        
//     // Tabela para exibir os produtos no carrinho
//        carrinhoTableModel = new DefaultTableModel(new Object[]{"ID", "Descrição"}, 0);
//        carrinhoTable = new JTable(carrinhoTableModel);
//        JScrollPane scrollPane = new JScrollPane(carrinhoTable);
//        add(scrollPane, BorderLayout.CENTER);
        
        // Botão para confirmar finalização
        JButton finalizarButton = new JButton("Finalizar Compra");
        finalizarButton.addActionListener(e -> finalizarCompra());
        add(finalizarButton, BorderLayout.SOUTH);

	}
	
	private void finalizarCompra() {
	try {
        String nome = entregadorField.getText();
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do entregador não pode estar vazio.");
        }

        String endereco = enderecoField.getText();
        if (endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode estar vazio.");
        }

        String data = dataField.getText();
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data não pode estar vazio.");
        }

        // Se tudo estiver correto, mostrar sucesso
        Fachada.FazerEntrega(nome, data, endereco);
        JOptionPane.showMessageDialog(this, "Compra finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Fecha a tela de finalização

	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e1) {
	        JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }

	}
	
}

package appswing;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Entregador;
import regras_negocio.Fachada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaEntregador extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	 private JPanel contentPane;
	    private JTable table;
	    private JTextField textFieldId;
	    private JTextField textFieldNome;
	    private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			TelaEntregador dialog = new TelaEntregador();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	
//	 public TelaEntregador() {
//	        this(null); // Chama o construtor que aceita Frame, passando null
//	    }
	/**
	 * Create the dialog.
	 */
	    public TelaEntregador(Frame parent) {
			super(parent, "Entregadores", true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        setBounds(100, 100, 600, 400);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(new BorderLayout(10,10));
	        contentPane.setLayout(new GridLayout(0, 1));

	        // Tabela
	        tableModel = new DefaultTableModel(new Object[]{"Nome","Entregas"}, 0);
	        table = new JTable(tableModel);
	        JScrollPane scrollPane = new JScrollPane(table);
	        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
	        contentPane.add(scrollPane, BorderLayout.CENTER);

	        // Painel para inputs e botões
	        JPanel panel = new JPanel();
	        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        contentPane.add(panel, BorderLayout.SOUTH);
	      

	        JLabel lblNome = new JLabel("Nome:");
	        panel.add(lblNome);
	        textFieldNome = new JTextField(20);
	        panel.add(textFieldNome);

	        JButton btnAdicionar = new JButton("Adicionar");
	        btnAdicionar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                adicionarEntregador();
	            }
	        });
	        

	        JButton btnRemover = new JButton("Remover");
	        btnRemover.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                removerEntregador();
	            }
	        });
	        
	        

	        JButton btnAtualizar = new JButton("Atualizar");
	        btnAtualizar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                carregarListaEntregadores();
	            }
	        });
	        
	        panel.add(btnAtualizar);
	        panel.add(btnRemover);
	        panel.add(btnAdicionar);

	        JLabel lblNumEntregas = new JLabel("Buscar por Numero de entregas:");
	        panel.add(lblNumEntregas);
	        JTextField textFieldNumEntregas = new JTextField(15);
	        panel.add(textFieldNumEntregas);

	        JButton btnBuscar = new JButton("Buscar");
	        btnBuscar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int numEntregas = Integer.parseInt(textFieldNumEntregas.getText());
	                buscarEntregadoresPorNumEntregas(numEntregas);
	            }
	        });
	        panel.add(btnBuscar);

	        
	        carregarListaEntregadores();
	        
		}
		
		private void carregarListaEntregadores() {
			try {
	            List<Entregador> entregadores = Fachada.listarEntregadores(); // Método para listar entregadores
	            tableModel.setRowCount(0); // Limpa a tabela

	            for (Entregador entregador : entregadores) {
	                tableModel.addRow(new Object[]{entregador.getNome(),entregador.getEntregas().size()});
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Erro ao carregar entregadores: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    }
		
		private void buscarEntregadoresPorNumEntregas(int numEntregas) {
		    try {
		        List<Entregador> entregadores = Fachada.entregadoresNumEntregas(numEntregas);
		        tableModel.setRowCount(0); 
		        for (Entregador entregador : entregadores) {
		            tableModel.addRow(new Object[]{entregador.getNome(), entregador.getEntregas().size()});
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Erro ao buscar entregadores: " + e.getMessage());
		    }
		}

	    private void adicionarEntregador() {
	    	 String nome = textFieldNome.getText();
	         if (!nome.isEmpty()) {
	             try {
	                 Entregador novoEntregador = new Entregador(); // Presumindo que você tenha uma classe Entregador
	                 novoEntregador.setNome(nome);
	                 Fachada.cadastraEntregador(novoEntregador.getNome()); // Método para adicionar entregador
	                 carregarListaEntregadores(); // Atualiza a tabela
	                 textFieldNome.setText(""); // Limpa o campo de entrada
	             } catch (Exception ex) {
	                 JOptionPane.showMessageDialog(this, "Erro ao adicionar entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	             }
	         } else {
	             JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
	         }
	    }

	    private void removerEntregador() {
	    	 int selectedRow = table.getSelectedRow();
	         if (selectedRow != -1) {
	             String nome = (String) tableModel.getValueAt(selectedRow, 0); // Obtém o nome do entregador selecionado
	             try {
	                 Fachada.removerEntregador(nome); // Método para remover entregador
	                 carregarListaEntregadores(); // Atualiza a tabela
	             } catch (Exception ex) {
	                 JOptionPane.showMessageDialog(this, "Erro ao remover entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	             }
	         } else {
	             JOptionPane.showMessageDialog(this, "Selecione um entregador para remover.");
	         }
	    }
    
}

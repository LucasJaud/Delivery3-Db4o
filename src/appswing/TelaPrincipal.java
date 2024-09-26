package appswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import regras_negocio.Fachada;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Produto;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fachada.inicializar();
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
					
					Runtime.getRuntime().addShutdownHook(new Thread() {
			            public void run() {
			                Fachada.finalizar();
			            }
					});	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
        setTitle("Delivery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centraliza a janela
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // Grid com 4 linhas para os botões
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margens no painel
        
       
        JButton btnProdutos = new JButton("Gerenciar Produtos");
        btnProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaProduto telaProduto = new TelaProduto(TelaPrincipal.this);
                telaProduto.setVisible(true);
            }
        });
        
     
        JButton btnEntregadores = new JButton("Gerenciar Entregadores");
        btnEntregadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaEntregador telaEntregador = new TelaEntregador(TelaPrincipal.this);
                telaEntregador.setVisible(true);
            }
        });
        
     
        JButton btnCarrinho = new JButton("Carrinho de Compras");
        btnCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCarrinho telaCarrinho = new TelaCarrinho(TelaPrincipal.this);
                telaCarrinho.setVisible(true);
            }
        });
        
        
        JButton btnEntregas = new JButton("Gerenciar Entregas");
        btnEntregas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaEntrega telaEntrega = new TelaEntrega(TelaPrincipal.this);
                telaEntrega.setVisible(true);
            }
        });
        
     
        panel.add(btnProdutos);
        panel.add(btnEntregadores);
        panel.add(btnCarrinho);
        panel.add(btnEntregas);
        
        
        add(panel);
        
	}
	


}

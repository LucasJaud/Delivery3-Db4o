package appconsole;

import regras_negocio.Fachada;
import modelo.Produto;
import modelo.Entrega;
import modelo.Entregador;

public class Listar {
	
	public Listar() {
		
		try {
			Fachada.inicializar();
			System.out.println("\nLista de Produtos");
			for(Produto c: Fachada.listarProdutos())
				System.out.println(c);
			
			System.out.println("\nLista de Entregadores");
			for(Entregador t: Fachada.listarEntregadores())
				System.out.println(t);
			
			System.out.println("\nLista de Entregas");
			for(Entrega g: Fachada.listarEntregas())
				System.out.println(g);
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("\nfim do programa !");
		
	}

	public static void main(String[] args) {
		new Listar();

	}

}

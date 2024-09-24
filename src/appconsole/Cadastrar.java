package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {
	
	public Cadastrar() {
		try {
			Fachada.inicializar();
			System.out.println("cadastrando Produtos");
			Fachada.cadastraProduto("hamburguer");
			Fachada.cadastraProduto("batatas");
			Fachada.cadastraProduto("coca-cola");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			
			System.out.println("cadastrando Entregadores");
			Fachada.cadastraEntregador("Lucas");
			Fachada.cadastraEntregador("João");
			Fachada.cadastraEntregador("Paulo");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			
			System.out.println("cadastrando Entregas");
			Fachada.adicionarProdutoTemporario(1);
			Fachada.adicionarProdutoTemporario(2);
			Fachada.adicionarProdutoTemporario(3);
			Fachada.FazerEntrega("Lucas","19/07/2024", "123");
			Fachada.adicionarProdutoTemporario(3);
			Fachada.adicionarProdutoTemporario(2);
			Fachada.adicionarProdutoTemporario(1);
			Fachada.FazerEntrega("Lucas","19/07/2024", "321");
			Fachada.adicionarProdutoTemporario(2);
			Fachada.adicionarProdutoTemporario(1);
			Fachada.adicionarProdutoTemporario(3);
			Fachada.FazerEntrega("Lucas","19/07/2024", "213");
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		Fachada.finalizar();
		System.out.println("\nfim do programa !");
		
	}
	
	public static void main(String[] args) {
		new Cadastrar();

	}

}

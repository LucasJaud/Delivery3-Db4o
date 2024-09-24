package regras_negocio;

import java.util.ArrayList;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOEntrega;
import daodb4o.DAOEntregador;
import daodb4o.DAOProduto;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Produto;

public class Fachada {
	
	private static List<Produto> listaProdutosTemporaria = new ArrayList<>();
	private Fachada() {}
	
	private static DAOEntrega daoentrega = new DAOEntrega(); 
	private static DAOEntregador daoentregador = new DAOEntregador(); 
	private static DAOProduto daoproduto = new DAOProduto(); 
	
	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}
	
	public static Produto cadastraProduto(String desc) {
		DAO.begin();
		Produto produto;
		produto = new Produto(desc);
		daoproduto.create(produto);
		DAO.commit();
		return produto;
	}
	
	public static void removerProduto(int codigo) throws Exception{
		DAO.begin();
		Produto produto = daoproduto.read(codigo);
		if(produto == null)
			throw new Exception("Produto não existe");
		
		 if (listaProdutosTemporaria.contains(produto)) {
			 listaProdutosTemporaria.remove(produto);
		    }
		 
		 List<Entrega> entregas = daoentrega.readAll();
		 for (Entrega entrega : entregas) {
		        if (entrega.getProdutos().contains(produto)) {
		            entrega.remover(produto);  
		            daoentrega.update(entrega); 
		        }
		    }
		 daoproduto.delete(produto);
		 DAO.commit(); 
	}
	
	
	public static Entregador cadastraEntregador(String nome) throws Exception{
		DAO.begin();
		Entregador entregador = daoentregador.read(nome);
			if(entregador != null)
				throw new Exception("Ja existe este entregador!!");
			
			entregador = new Entregador(nome);
			daoentregador.create(entregador);
			DAO.commit();
			return entregador;
	}
	
	public static void removerEntregador(String nome) throws Exception{
		DAO.begin();
		Entregador entregador = daoentregador.read(nome);
		if(entregador == null)
			throw new Exception("Não existe esse entregador");
		List<Entrega> entregas = daoentrega.readAll();
		for(Entrega entrega : entregas) {
			if(entrega.getEntregador() == entregador)
				entrega.setEntregador(null);
		}
		daoentregador.delete(entregador);
		DAO.commit();
		
	}
	
	public static Entrega FazerEntrega(String nome,String data , String endereco) throws Exception{
		DAO.begin();
		Entregador entregador = daoentregador.read(nome);
		
		if(entregador == null)
			throw new Exception("entregador não existe!!");
		
		if(listaProdutosTemporaria.isEmpty())
			throw new Exception("Entrega sem produtos");
		
		long entregasNoDia = entregador.getEntregas().stream()
                .filter(entrega -> entrega.getData().equals(data))
                .count();

// Verifica se o entregador já atingiu o limite de 4 entregas no dia
		if (entregasNoDia >= 4) {
		throw new Exception("O entregador já realizou 4 entregas hoje!");
		}
		
		Entrega entrega = new Entrega(data,endereco);
		
		for (Produto produto : listaProdutosTemporaria) {
            entrega.adicionar(produto);
        }
		entrega.setEntregador(entregador);
		entregador.adicionar(entrega);
		
		daoentrega.create(entrega);
		daoentregador.update(entregador);
		DAO.commit();
		listaProdutosTemporaria.clear();
		return entrega;
	
	}
	
	public static void removerEntrega(int id) throws Exception{
		DAO.begin();
		Entrega entrega = daoentrega.read(id);
		if(entrega == null)
			throw new Exception("entrega invalida");
		Entregador entregador = daoentregador.read(entrega.getEntregador().getNome());
		entregador.remover(entrega);
		daoentrega.delete(entrega);
		DAO.commit();
	}
	
	// metodo para remover entregas invalidas
	public static void removerEntregasInvalidas(){
		DAO.begin();
		List<Entrega> entregas = daoentrega.readAll();
		for(Entrega entrega: entregas) {
			if(entrega.getProdutos().isEmpty() || entrega.getEntregador() == null)
				daoentrega.delete(entrega);
		}
		DAO.commit();
	}
	
	//metodo auxiliar para adicionar produtos a entrega
	public static void adicionarProdutoTemporario(int id) throws Exception {
        Produto produto = daoproduto.read(id);
        if (produto == null) 
            throw new Exception("Produto inválido!!");
        
        if (listaProdutosTemporaria.contains(produto)) 
                throw new Exception("O produto já foi adicionado à lista!");
            
        listaProdutosTemporaria.add(produto);
    }
	
	//metodo auxiliar para remover produtos da lista temporaria
	public static void removerProdutoTemporario(int codigo) throws Exception{
		Produto produto =daoproduto.read(codigo);
		
		if (!listaProdutosTemporaria.contains(produto)) {
			 throw new Exception("Produto não esta na lista");
		    }
		listaProdutosTemporaria.remove(produto);
	}
	
	
	//metodos para listar
	public static List<Produto> listarProdutos(){
		List<Produto> lista = daoproduto.readAll();
		return lista;
	}
	
	public static Entrega entregaPorId(int id) {
		 return daoentrega.read(id);
	}
	
	public static List<Entregador> listarEntregadores(){
		List<Entregador> lista = daoentregador.readAll();
		return lista;
	}
	
	public static List<Entrega> listarEntregas(){
		List<Entrega> lista = daoentrega.readAll();
		return lista;
	}
	
	public static List<Entrega> entregasEndereco(String endereco){
		List<Entrega> lista = daoentrega.EntregaEndereco(endereco);
		return lista;
	}
	
	public static List<Entrega> entregasProduto(int id){
		List<Entrega> lista = daoentrega.EntregaProduto(id);
		return lista;
	}
	
	
	public static List<Produto> getListaProdutosTemporaria() {
		return listaProdutosTemporaria;
	}
	public static void setListaProdutosTemporaria(List<Produto> listaProdutosTemporaria) {
		Fachada.listaProdutosTemporaria = listaProdutosTemporaria;
	}
	

}

package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Entrega;



public class DAOEntrega extends DAO<Entrega>{


	public void create(Entrega obj) {
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}

	@Override
	public Entrega read(Object chave) {
		int id = (int) chave;
		Query q = manager.query();
		q.constrain(Entrega.class);
		q.descend("id").constrain(id);
		List<Entrega> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;		
	}
	
	public List<Entrega> entregaEntregador (String nome) {
		Query q;
		q = manager.query();
		q.constrain(Entrega.class);
		q.descend("Entregador").descend("nome").constrain(nome);
		return q.execute();
	}
	
	public List<Entrega> EntregaEndereco (String endereco){
		String end =(String) endereco;
		Query q;
		q =manager.query();
		q.constrain(Entrega.class);
		q.descend("endereco").constrain(end);
		return q.execute();
		
	}
	
	public List<Entrega> EntregaProduto(int chave){
		Query q;
		q = manager.query();
		q.constrain(Entrega.class);
		q.descend("produtos").descend("id").constrain(chave);
		return q.execute();
	}
	
//	public List<Entrega> EntregaProduto(String chave){
//		String desc = (String) chave;
//		Query q;
//		q = manager.query();
//		q.constrain(Entrega.class);
//		q.descend("produto").descend("desc").constrain();
//		return q.execute();
//	}
	
	
	
	
	
	
}

package daodb4o;

import java.util.List;

import com.db4o.query.Query;


import modelo.Produto;

public class DAOProduto extends DAO<Produto>{
	


	@Override
	public Produto read(Object chave) {
		int id = (int) chave;
		Query q = manager.query();
		q.constrain(Produto.class);
		q.descend("id").constrain(id);
		List<Produto> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	
	public void create(Produto obj) {
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setCodigo(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	
	}



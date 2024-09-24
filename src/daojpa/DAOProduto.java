package daojpa;

import java.util.List;

import com.db4o.query.Query;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Produto;

public class DAOProduto extends DAO<Produto>{
	


	@Override
	public Produto read(Object chave) {
		try {
		int id = (int) chave;
		TypedQuery<Produto> q = manager.createQuery("select p from Produto p where p.id=:id",Produto.class);
		q.setParameter("id", id);
		return q.getSingleResult();
		
		}catch(NoResultException e){
			return null;
		}
			
	}
	
	
//	public void create(Produto obj) {
//		int novoid = super.gerarId();  	//gerar novo id da classe
//		obj.setCodigo(novoid);				//atualizar id do objeto antes de grava-lo no banco
//		manager.store( obj );
//	}
	
	
	}



package daojpa;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entregador;

public class DAOEntregador extends DAO<Entregador>{
	
	@Override
	public Entregador read (Object chave) {
		try {
		String nome = (String) chave;
		TypedQuery<Entregador> q = manager.createQuery("select t from Entregador t where t.nome=:n",Entregador.class);
		q.setParameter("n", nome);
		return q.getSingleResult();
		} 
		catch(NoResultException e) {
			return null;
			}
		}
	
	public List<Entregador> entregadorNumEntrega(int n){
		TypedQuery<Entregador> q = manager.createQuery("",Entregador.class);
		
		
		List<Entregador> entregadores = q.getResultList();
		
		List<Entregador> res = new ArrayList<>();
		for(Entregador e : entregadores) {
			if(e.getEntregas().size() >= n)
				res.add(e);
		}
		
		return res;	
	}
	
		
}

	


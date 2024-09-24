package daojpa;

import java.util.List;

import com.db4o.query.Query;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;



public class DAOEntrega extends DAO<Entrega>{


	

	@Override
	public Entrega read(Object chave) {
		try {
		int id = (int) chave;
		TypedQuery<Entrega> q = manager.createQuery("select e from Entrega e where id=:i",Entrega.class);
		q.setParameter("i", id);
			return q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}	
	}
	
	public List<Entrega> entregaEntregador (String nome) {
		try {
		TypedQuery<Entrega> q;
		q = manager.createQuery("SELECT e FROM Entrega e JOIN e.entregador ent WHERE ent.nome=:n",Entrega.class);
		q.setParameter("n", nome);
		return q.getResultList();
		}catch(NoResultException e) {
			return null;
		}
	}
	
	public List<Entrega> EntregaEndereco (String endereco){
		try {
		String end =(String) endereco;
		TypedQuery<Entrega> q;
		q =manager.createQuery("Select e from Entrega e where e.endereco=:end",Entrega.class);
		q.setParameter("end", end);
		return q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		
	}
	
	public List<Entrega> EntregaProduto(int chave){
		try {
		TypedQuery<Entrega> q;
		q = manager.createQuery("SELECT e FROM Entrega e JOIN e.produtos p WHERE p.id = :chave",Entrega.class);
		q.setParameter("chave", chave);
		return q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
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

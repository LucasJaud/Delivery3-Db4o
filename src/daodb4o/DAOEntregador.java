package daodb4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Entregador;

public class DAOEntregador extends DAO<Entregador>{
	
	@Override
	public Entregador read (Object chave) {
		String nome = (String) chave;
		Query q = manager.query();
		q.constrain(Entregador.class);
		q.descend("nome").constrain(nome);
		List<Entregador> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public List<Entregador> entregadorNumEntrega(int n){
		manager.ext().purge();
		Query q = manager.query();
		q.constrain(Entregador.class);
		
		List<Entregador> entregadores = q.execute();
		
		List<Entregador> res = new ArrayList<>();
		for(Entregador e : entregadores) {
			if(e.getEntregas().size() >= n)
				res.add(e);
		}
		
		return res;
		
	}
		
}

	


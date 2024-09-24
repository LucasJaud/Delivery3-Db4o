package modelo;

import java.util.ArrayList;

public class Entregador {
private String nome;
ArrayList <Entrega> entregas = new ArrayList<>();

public Entregador() {}

public Entregador(String nome){
this.nome = nome;	
}

@Override
public String toString() {
	 String texto ="Entregador: nome=" + nome;
	 
	 for(Entrega entrega: entregas) {
		 texto += "\n entrega:"+ entrega.getId();
	 }
	 
	 return texto;
	
}

public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public ArrayList<Entrega> getEntregas() {
	return entregas;
}

public void setEntregas(ArrayList<Entrega> entregas) {
	this.entregas = entregas;
}

public void adicionar(Entrega e) {
	this.entregas.add(e);
}

public void remover(Entrega e) {
	this.entregas.remove(e);
}


}

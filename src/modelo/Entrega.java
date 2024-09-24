package modelo;

import java.util.ArrayList;
public class Entrega {
private int id;
private String data;
private String endereco;
private Entregador entregador = new Entregador();
ArrayList <Produto> produtos =new ArrayList<>();

public Entrega() {}

public Entrega(String data,String endereco){
	this.data = data;
	this.endereco = endereco;
}


@Override
public String toString() {
	String texto = "Entrega: id=" + id + ", data=" + data + ", endereco=" + endereco + ", entregador=" + entregador.getNome();
	for(Produto produto: produtos){
		texto += "\n produtos:" +produto.getDesc();
	}
	
	return texto;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public String getEndereco() {
	return endereco;
}
public void setEndereco(String endereco) {
	this.endereco = endereco;
}
public Entregador getEntregador() {
	return entregador;
}
public void setEntregador(Entregador entregador) {
	this.entregador = entregador;
}

public ArrayList<Produto> getProdutos() {
	return produtos;
}
public void setProdutos(ArrayList<Produto> produtos) {
	this.produtos = produtos;
}

public void adicionar(Produto p){
	this.produtos.add(p);
}
public void remover(Produto p){
	this.produtos.remove(p);
}

	
}

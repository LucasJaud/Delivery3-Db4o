package modelo;

public class Produto {

private int id;
private String desc;

public Produto() {}

public Produto(String desc) {
	this.desc =desc;
}

public int getId() {
	return id;
}
public void setCodigo(int id) {
	this.id = id;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}

@Override
public String toString() {
	return "Produto: id=" + id + ", desc=" + desc ;
}

}
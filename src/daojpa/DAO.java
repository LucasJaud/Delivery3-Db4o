/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package daojpa;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;


public abstract class DAO<T> implements DAOInterface<T> {
	protected static EntityManager manager;

	public static void open(){	
		manager = Util.conectarBanco();				//banco local
		//manager = Util.conectarBancoRemoto();		//banco remoto
	}

	public static void close(){
		Util.fecharBanco();
	}

	//----------CRUD-----------------------

	public void create(T obj){
		manager.persist( obj );
	}

	public abstract T read(Object chave);	//sobrescrito nas subclasses

	public T update(T obj){
		return manager.merge(obj);
	}

	public void delete(T obj) {
		manager.remove(obj);
	}
	
    // Método genérico deleteAll
    @SuppressWarnings("unchecked")
	public void deleteAll() {
        try {
            // Iniciar transação
            manager.getTransaction().begin();

            // Obter o nome da classe T (entidade)
            Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            // Executar query genérica para deletar todos os registros da entidade T
            int deletedCount = manager.createQuery("DELETE FROM " + type.getSimpleName()).executeUpdate();

            // Confirmar a transação
            manager.getTransaction().commit();

            System.out.println(deletedCount + " registros de " + type.getSimpleName() + " deletados com sucesso.");
        } catch (Exception e) {
            // Fazer rollback caso ocorra algum erro
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

	@SuppressWarnings("unchecked")
	public List<T> readAll(){
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		TypedQuery<T> query = manager.createQuery("select x from " + type.getSimpleName() + " x",type);
		return  query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> readAllPagination(int firstResult, int maxResults) {
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		return manager.createQuery("select x from " + type.getSimpleName() + " x",type)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.getResultList();
	}

//	@SuppressWarnings("unchecked")
//	//deletar todos objetos de um tipo (e subtipo)
//	public void deleteAll(){
//		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
//				.getGenericSuperclass()).getActualTypeArguments()[0];
//
//		Query q = manager.query();
//		q.constrain(type);
//		for (Object t : q.execute()) {
//			manager.delete(t);
//		}
//	}

	//--------transa��o---------------
	public static void begin(){	
		if(!manager.getTransaction().isActive())
			manager.getTransaction().begin();
	}		

	public static void commit(){
		if(manager.getTransaction().isActive()){
			manager.getTransaction().commit();
		}
	}
	public static void rollback(){
		if(manager.getTransaction().isActive())
			manager.getTransaction().rollback();
	}
	
	public void lock(T obj) {
		//usado somente no controle de concorrencia persimista
		manager.lock(obj, LockModeType.PESSIMISTIC_WRITE); 
	}
	
	// acesso direto a classe de conex�o jdbc
		public static Connection getConnectionJdbc() {
			try {
				EntityManagerFactory factory = manager.getEntityManagerFactory();
				String driver = (String) factory.getProperties().get("jakarta.persistence.jdbc.driver");
				String url = (String)	factory.getProperties().get("jakarta.persistence.jdbc.url");
				String user = (String)	factory.getProperties().get("jakarta.persistence.jdbc.user");
				String pass = (String)	factory.getProperties().get("jakarta.persistence.jdbc.password");
				Class.forName(driver);
				return DriverManager.getConnection(url, user, pass);
			} 
			catch (Exception ex) {
				return null;
			}
		}

	//	gerar novo id para uma classe T
	//  consulta o maior valor armazenado no atributo "id" 

//	public int gerarId() {
//		@SuppressWarnings("unchecked")
//		Class<T> type =(Class<T>) ((ParameterizedType) this.getClass()
//				.getGenericSuperclass()).getActualTypeArguments()[0];
//
//		//verificar se o banco esta vazio 
//		if(manager.query(type).size()==0) {
//			return 1;			//primeiro id gerado
//		}
//		else {
//			//obter o maior valor de id para o tipo
//			Query q = manager.query();
//			q.constrain(type);
//			q.descend("id").orderDescending();
//			List<T> resultados =  q.execute();
//			if(resultados.isEmpty()) 
//				return 1; 		//nenhum resultado, retorna primeiro id 
//			else 
//				try {
//					//obter objeto localizado
//					T objeto =  resultados.get(0);
//					Field atributo = type.getDeclaredField("id") ;
//					atributo.setAccessible(true);
//					//obter atributo id do objeto localizado e incrementa-lo
//					int maxid = (Integer) atributo.get(objeto);  //valor do id
//					return maxid+1;
//
//				} catch(NoSuchFieldException e) {
//					throw new RuntimeException("classe "+type+" - nao tem atributo id");
//				} catch (IllegalAccessException e) {
//					throw new RuntimeException("classe "+type+" - atributo id inacessivel");
//				}
//		}
//	}

}


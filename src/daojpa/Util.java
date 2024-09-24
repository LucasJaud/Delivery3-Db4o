/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daojpa;

import org.apache.log4j.Logger;

import com.db4o.ObjectContainer;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Produto;
import modelo.Entrega;
import modelo.Entregador;


public class Util {
	private static EntityManager manager;
	private static EntityManagerFactory factory;
	
	private static final Logger logger = Logger.getLogger(Util.class);

	
	public static EntityManager conectarBanco(){
		if(manager == null) {
			factory = Persistence.createEntityManagerFactory("hibernate-postgresql");
			manager = factory.createEntityManager();
			logger.debug("-------- conectou banco");
		}
		return manager;
	}
	
//	public static ObjectContainer conectarBancoRemoto(){		
//		if (manager != null)
//			return manager;		//ja tem uma conexao
//
//		//---------------------------------------
//		//configurar e conectar/criar banco remoto
//		//---------------------------------------
//
//		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
//		config.common().messageLevel(0);  // 0,1,2,3...
//
//		// habilitar cascata na altera��o, remo��o e leitura
//	
//		//Conex�o remota 
//		//****************
//		String ipservidor="";
//		//ipservidor = "10.0.4.189";		// computador do professor (lab3)
//		ipservidor = "54.163.92.174";		// AWS
//		manager = Db4oClientServer.openClient(config, ipservidor, 34000,"usuario1","senha1");
//		return manager;
//	}

	public static void fecharBanco() {
		if(manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager=null;
			logger.debug("-------- desconectou banco");
		}
	}
}

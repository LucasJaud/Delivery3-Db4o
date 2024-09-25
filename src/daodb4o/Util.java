/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daodb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import modelo.Produto;
import modelo.Entrega;
import modelo.Entregador;


public class Util {
	private static ObjectContainer manager=null;

	
	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao

		//---------------------------------------------------------------
		//configurar, criar e abrir banco local (pasta do projeto)
		//---------------------------------------------------------------
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na altera��o, remo��o e leitura
		config.common().objectClass(Produto.class).cascadeOnDelete(false);;
		config.common().objectClass(Produto.class).cascadeOnUpdate(true);;
		config.common().objectClass(Produto.class).cascadeOnActivate(true);
		config.common().objectClass(Entrega.class).cascadeOnDelete(false);;
		config.common().objectClass(Entrega.class).cascadeOnUpdate(true);;
		config.common().objectClass(Entrega.class).cascadeOnActivate(true);
		config.common().objectClass(Entregador.class).cascadeOnDelete(false);;
		config.common().objectClass(Entregador.class).cascadeOnUpdate(true);;
		config.common().objectClass(Entregador.class).cascadeOnActivate(true);
		
		config.common().objectClass(Produto.class).objectField("codigo").indexed(true);
		config.common().objectClass(Entregador.class).objectField("nome").indexed(true);
		config.common().objectClass(Entrega.class).objectField("id").indexed(true);

		//conexao local
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}
	
	public static ObjectContainer conectarBancoRemoto(){		
		if (manager != null)
			return manager;		//ja tem uma conexao

		//---------------------------------------
		//configurar e conectar/criar banco remoto
		//---------------------------------------

		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
		config.common().messageLevel(0);  // 0,1,2,3...

		// habilitar cascata na altera��o, remo��o e leitura
	
		//Conex�o remota 
		//****************
		String ipservidor="";
		//ipservidor = "10.0.4.189";		// computador do professor (lab3)
		ipservidor = "54.163.92.174";		// AWS
		manager = Db4oClientServer.openClient(config, ipservidor, 34000,"usuario1","senha1");
		return manager;
	}

	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
}

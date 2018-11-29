package examples.prep_exam_4;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class emisor_basico extends Agent{

	protected void setup(){
		//En el setup solo tenemos que añadir el comportamiento.
		addBehaviour(new EmisorComportamiento());
	}

	class EmisorComportamiento extends Behaviour{

		boolean fin = false;

		public void action(){

			//Parece que getLocalName es un método de la clase Agente. Te debe dar el nombre que tiene en jade o algo.
			System.out.println(getLocalName() + ": Preparandose para enviar un msn al receptor");
			
			//Esto parece que es el Agent ID. Tiene que coincidir con como lo llames en el gui de Jade
			AID id = new AID();
			id.setLocalName("receptor");

			//Creamos el objeto ACLMessage
			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST); //El request es la performativa

			//Rellenamos los campos necesarios del mensaje
			mensaje.setSender(getAID());
			mensaje.setLanguage("Español");
			mensaje.addReceiver(id);
			mensaje.setContent("Wussup my man? My man = receptor");

			//Enviamos el mensaje
			send(mensaje);

			System.out.println(getLocalName() + ": Enviando el mensaje al receptor");
			System.out.println(mensaje.toString());
			fin = true; //Una vez mandamos el mensaje acabaremos el behaviour
		}

		public boolean done(){
			return fin;
		}
	}
}

package examples.prep_exam_4;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class receptor_basico extends Agent{

	protected void setup(){
		//Solo necesitamos añadir el comportamiento del receptor
		addBehaviour(new ReceptorComportamiento());
	}

	class ReceptorComportamiento extends Behaviour{

		private boolean fin = false;

		public void action(){

			System.out.println("Preparandose para recibir mensajes");

			//Recuerda que los mensajes están en una cola, así que obtenemos el primero de la cola
			//Aquí es donde pondrías el blockingReceive(), el receive(), etc. 
			ACLMessage mensaje = blockingReceive();

			//Comprueba que hay mensaje siempre al principio
			if(mensaje != null){
				System.out.println(getLocalName() + " acaba de recibir el siguiente mensaje:\n"+mensaje.toString());
				fin = true;
			}
		}

		public boolean done(){
			return fin;
		}
	}
}
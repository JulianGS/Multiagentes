/*
Utilizando el ejercicio "EjercicioExamen2"  implementa un FSMB con 4 estados:

Primero: Se ejecuta 1 vez e imprime 1."FSMB"
Segundo: Se ejecuta 20 veces e imprime la iteración; AL ACABAR pone "2.FSMB".
Tercero: Envía un mensaje al receptor en el que pone "BIBA PROLOJ" con una AID "receptor". AL ACABAR pone "3. FSMB"
Cuarto: Imprime "4. FSMB" y en otra línea "FIN SFMB".

Este FSMB que hemos definido se ejecuta en paralelo con otro comportamiento compuesto. Definido a continuación:

Comportamiento paralelo que ejecute 2 comportamientos:

Primero: Cada 5s imprime "Me corro vivo"
Segundo: Espera la recepción del mensaje del comportamiento 3. Cuando lo reciba muestra el contenido del mensaje y 
el lenguaje en el que está escrito

*/

package examples.prep_exam2;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.lang.acl.*;
import jade.*;

public class ejercicio_medi extends Agent{

	SequentialBehaviour fsmb_principal;

	protected void setup(){
		fsmb_principal = new SequentialBehaviour(this){
			public int onEnd(){
				myAgent.doDelete();
				return super.onEnd();
			}
		};

		//Una vez hemos declarado el comportamiento secuencial que es el hilo principal del programa, empezamos a declarar las cosas que pasamos
		//Como mensajes, nº aleatorios, y cosas así.
		
		//Añado los subcomportamientos
		fsmb_principal.addSubBehaviour(new Primero());
		fsmb_principal.addSubBehaviour(new Comp2());
		fsmb_principal.addSubBehaviour(new Comp3());
		fsmb_principal.addSubBehaviour(new Cuarto());

		addBehaviour(fsmb_principal);
	}

	protected void takeDown(){
		System.out.println("Agente Finalizado. Liberando recursos");
	}

	//Comportamiento 1
	private class Primero extends OneShotBehaviour{
		public void action(){
			System.out.println("1. FSMB");
		}
	}

	//Comportamiento 4
	private class Cuarto extends OneShotBehaviour{
		public void action(){
			System.out.println("4. FSMB\n FIN");
		}
	}

	//Comportamiento 2
	private class Comp2 extends Behaviour{
		private int n_ejecuciones;

		public void onStart(){
			n_ejecuciones = 0;
		}

		public void action(){
			System.out.println("Soy el comportamiento 2 y estoy en la iteración: "+n_ejecuciones);
			n_ejecuciones++;
		}

		public boolean done(){
			return(n_ejecuciones==20);
		}

		public int onEnd(){
			System.out.println("2. FSMB");
			return 1;
		}
	}

	//
	private class Comp3 extends Behaviour{
		private String receptor_name;

		public void onStart(){
			receptor_name = "receptor";
		}
		public void action(){
			//Busca en la gui de jade el id que le pongas, por eso cuando creemos el agente en jade es IMPORTANTE llamarlo "receptor"
			AID id = new AID();
			id.setLocalName(receptor_name);

			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
			
			mensaje.setSender(getAID());
			mensaje.setLanguage("Spanish");
			mensaje.addReceiver(id);
			mensaje.setContent("BIBA PROLOJ");

			send(mensaje);
		}

		public boolean done(){
			return true;
		}
		public int onEnd(){
			System.out.println("3. FSMB");
			return 0;
		}
	}
	//
}

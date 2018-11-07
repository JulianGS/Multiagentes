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

public class ejercicio_medi2 extends Agent{
	
	SequentialBehaviour fsmb_principal;
	protected void setup(){
		fsmb_principal = new SequentialBehaviour(this){
			public int onEnd(){
				myAgent.doDelete();
				return super.onEnd();
			}
		};
		fsmb_principal.addSubBehaviour(new Comp1());
		fsmb_principal.addSubBehaviour(new Comp2());
		addBehaviour(fsmb_principal);
	}
	protected void takeDown(){
		System.out.println("Agente finalizado. Liberando recursos. JARILLO D5");
	}

	private class Comp1 extends CyclicBehaviour{
		public void action(){
			System.out.println("Me corro vivo");
			block(5000);
		}
	}

	private class Comp2 extends OneShotBehaviour{
		public void action(){
			System.out.println("RECEPTOR: Preparando para recibir");
			ACLMessage mensaje = blockingReceive();
			System.out.println(mensaje.toString());
		}
	}
}
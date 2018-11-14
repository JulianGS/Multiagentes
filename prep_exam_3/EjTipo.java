package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;

/*
Diseñe un agente que implemente un comportamiento paralelo compuesto por 3 subcomportamientos, cada uno ejecutado en una hebra independiente.

El proble consiste en simular el comportamiento paralelo como tal. NO puede haber ejecución concurrente, sino que se irá alternando la ejecución
de los métodos action() tal y como lo haría el planificador.

El comp. paralelo finalizará cuando 2 de los subcomp terminen; y a su vez finalizará el agente. 

Cada subcomp se ejecuta un nº de veces aleatorio (1-100), pasado como argumento. 

Cada subcomp imprime su nombre y las ejecuciones que le quedan
*/

public class EjTipo extends Agent{

	//Como cada uno se ejecuta en una hebra distinta, hay que declararlos aquí
	Comp1 sb1, sb2, sb3;
	ParallelBehaviour principal;
	ThreadedBehaviourFactory hebras;

	int finalizado = 0;

	protected void setup(){
		principal = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);
		
		//Crear los subcomportamientos
		sb1 = new Comp1((int)((Math.random()*100)+1),"1",1,true);
		sb2 = new Comp1((int)((Math.random()*100)+1),"2",2,false);
		sb3 = new Comp1((int)((Math.random()*100)+1),"3",3,false);

		hebras = new ThreadedBehaviourFactory();

		principal.addSubBehaviour(hebras.wrap(sb1));
		principal.addSubBehaviour(hebras.wrap(sb2));
		principal.addSubBehaviour(hebras.wrap(sb3));

		addBehaviour(principal);
	}

	protected void takeDown(){
		System.out.println("Agente Finalisao");
	}

	private class Comp1 extends Behaviour{
		private int ejecuciones;
		private String nombre;
		//A parte de las ejecuciones y el nombre SIEMPRE necesitamos:
		private int ID;
		private boolean inicial;

		private Comp1 hermano;

		public Comp1(int ejecuciones, String nombre, int ID, boolean inicial){
			this.ejecuciones = ejecuciones;
			this.nombre = nombre;
			this.ID=ID;
			this.inicial=inicial;
		}
		//Antes de hacer el action, compruebo si soy el inicial. Si NO lo soy, me suspendo
		public void onStart(){
			
			//Esto es LA SECCIÓN CRÍTICA

			if(this.ID==1){ //Inicial
				this.hermano=sb2; //Activo el 2
			}else if(this.ID == 2){ //Después del 2
				this.hermano = sb3; //Activo el 3
			}else if(this.ID == 1){ //Después del 3
				this.hermano = sb1; //Vuelvo a activar el 1
			}

			//ESTO SIEMPRE
			if(!this.inicial){ //Si No soy el inicial, me bloqueo
				this.block();
			}
		}

		public void action(){
			
			ejecuciones--;
			System.out.println("Soy el comprtamiento 1."+nombre+" y me quedan "+ejecuciones+" ejecuciones");

			//Una vez he hecho mi action, vuelvo a activar al siguiente action, que es mi hermano.
			this.hermano.restart();
			//Y me bloqueo para que el acceda a la sección crítica exclusivamente
			this.block();
		}

		public boolean done(){
			return (ejecuciones<=0);
		}

		public int onEnd(){
			finalizado++;
			if(finalizado==2){
				myAgent.doDelete();
			}
			return 1;
		}
	}

}
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

	//Declarar los behaviour
	ParallelBehaviour principal;
	SubComp sb1, sb2, sb3;
	ThreadedBehaviourFactory tfb;

	protected void setup(){

		principal = new ParallelBehaviour(this, 2){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};
		tfb = new ThreadedBehaviourFactory();

		sb1 = new SubComp(1,(int)((Math.random()*100)+1),true);
		sb2 = new SubComp(2,(int)((Math.random()*100)+1),false);
		sb3 = new SubComp(3,(int)((Math.random()*100)+1),false);

		principal.addSubBehaviour(tfb.wrap(sb1));
		principal.addSubBehaviour(tfb.wrap(sb2));
		principal.addSubBehaviour(tfb.wrap(sb3));

		addBehaviour(principal);
	}

	protected void takeDown(){
		System.out.println("Hasta los huevillos de las hebras. Agente Finalizado");
		tfb.interrupt();
	}

	private class SubComp extends Behaviour{

		private int nombre;
		private int ejecuciones;
		private int llevo;

		//ESTO SIEMPRE EN THREADS
		private boolean inicial;
		private SubComp hermano;

		//Constructor
		public SubComp(int nombre, int ejecuciones, boolean inicial){
			this.nombre = nombre;
			this.ejecuciones = ejecuciones;
			this.inicial = inicial;
			this.llevo = 0;
		}

		public void onStart(){
			//En el onStart es donde voy a dar el orden a la sección crítica, es decir, antes de ejecutar el action
			
			//Lo primero es ver que comportamiento somos, para asignar quien entra después de nosotros:

			if(this.nombre == 1){
				this.hermano = sb2;
			}else if(this.nombre == 2){
				this.hermano = sb3;
			}else if(this.nombre == 3){
				this.hermano = sb1;
			}else{
				System.out.println("Error delimitando hermano");
				myAgent.doDelete();
			}

			//Después, si somos el comportamiento 1 o 2, nos bloqueamos.
			if(!this.inicial){
				tfb.suspend(this);
			}
		}

		public void action(){
			//Si he llegado al action es porque estoy dentro de la sección crítica así que hago mi función
			llevo++;
			System.out.println("Soy el subcomportamiento "+this.nombre+" y me quedan "+(ejecuciones-llevo)+" ejecuciones");

			//Y aviso a mi hermano de que he terminado para que entre él
			tfb.resume(this.hermano);
			//Y yo me bloqueo y así salgo de la sección crítica.
			tfb.suspend(this);
		}

		public boolean done(){
			return (llevo>=ejecuciones);
		}

		public int onEnd(){
			//Cuando salgo de la sección crítica veo que comportamiento se va a ejecutar el tercero: Yo -> Mi hermano -> 3er
			SubComp tercero = hermano.getHermano();
			//Y me añado como su hermano después de él
			tercero.setHermano(this.hermano);
			return 1;
		}

		public SubComp getHermano(){
			return this.hermano;
		}

		public void setHermano(SubComp hermano){
			this.hermano = hermano;
		}
	}
}
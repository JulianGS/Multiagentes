/*
Añadir 3 subcomp paralelos a 1 comp paralelo que finalizará cuando finalicen todos sus hijos.

	1- Se ejecuta 3 veces, mostrando "Soy el subcomp1 y estoy en mi i ejecución"
	2- Se ejecuta 1 vez mostrando "Soy el subcomp2"
	3- Se activa a las 5s de iniciarse el paralelo principal, muestra "Soy subcomp3 y acabo de despertar"
*/

package examples.prep_exam_2;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Paralelo extends Agent{

	ParallelBehaviour pb;

	protected void setup(){

		pb = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ALL){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};

		pb.addSubBehaviour(new P1());
		pb.addSubBehaviour(new P2());
		pb.addSubBehaviour(new P3(this,5000));

		addBehaviour(pb);
	}

	protected void takeDown(){
		System.out.println("Agente Caido");
	}

	private class P1 extends Behaviour{
		int i;

		public void onStart(){
			i=0;
		}

		public void action(){
			System.out.println("Soy el subcomp1 y estoy en mi "+i+" ejecución");
			i++;
		}

		public boolean done(){
			return(i>=3);
		}

	}//P1

	private class P2 extends OneShotBehaviour{
		public void action(){
			System.out.println("Soy el subcomp2");
		}
	}//P2

	private class P3 extends WakerBehaviour{
		private long timeout;
		public P3(Agent a, long timeout){
			super(a,timeout);
			this.timeout=timeout;
		}
		protected void onWake(){
			System.out.println("Soy subcomp3 y he despertado");
		}
	}//P3
}
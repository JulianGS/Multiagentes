/*
Diseñe un agente que con un comportamiento secuencial compuesto por 2 subcomportamientos:

	1- Un comportamiento paralelo que ejecute 3 subcomportamientos y finaliza cuando alguno termine. Cada subcomp se ejecuta
	un random(1,1000) de veces e imprime un identificativo. El random se pasa por argumento.

	2- Un FSM con 2 estados. El primero genera un random (0,100) y ve si es par. 
		Si es par->Transita al estado 2
		Si NO es par->Vuelve a repetirse

*/
package examples.prep_exam_2;

import jade.core.Agent;
import jade.core.behaviours.*;

public class ParaleloFSM extends Agent{
	
	SequentialBehaviour principal;
	ParallelBehaviour subcomp1;
	FSMBehaviour fsm;

	protected void setup(){
		//
		principal = new SequentialBehaviour(){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};
		//
		fsm = new FSMBehaviour(this) {
			public int onEnd() {
				System.out.println("FSM behaviour completed.");
				return 1;
			}
		};

		fsm.registerFirstState(new Fsm_1(),"Estado 1");
		fsm.registerLastState(new Fsm_2(),"Estado 2");

		fsm.registerTransition("Estado 1","Estado 2",1);
		fsm.registerTransition("Estado 1","Estado 1",0);


		//
		subcomp1 = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ANY);
		
		subcomp1.addSubBehaviour(new Sb1((int)((Math.random()*100)+1),1));
		subcomp1.addSubBehaviour(new Sb1((int)((Math.random()*100)+1),2));
		subcomp1.addSubBehaviour(new Sb1((int)((Math.random()*100)+1),3));
		//

		//
		principal.addSubBehaviour(subcomp1);
		principal.addSubBehaviour(fsm);

		addBehaviour(principal);

	}
	
	private class Sb1 extends Behaviour{
		int n;
		int id;
		int llevo;
		public Sb1(int n, int id){
			this.n=n;
			this.id=id;
			this.llevo=0;
		}
		public void action(){
			System.out.println("Soy el comportamiento 1."+this.id+" y me quedan "+(n-llevo)+" ejecuciones");
			llevo++;
		}
		public boolean done(){
			return (llevo>n);
		}

	}//Sb1

	private class Fsm_1 extends OneShotBehaviour{
		int aleatorio;
		int salida;
		public void onStart(){
			aleatorio = (int)((Math.random()*1000)+1);
			salida = 0;
		}

		public void action(){
			if(aleatorio%2==0){
				salida = 1;
			}
		}

		public int onEnd(){
			if(salida==1){
				return 1;
			}else{
				return 0;
			} 
		}
	}//Fsm_1

	private class Fsm_2 extends OneShotBehaviour{
		public void action(){
			System.out.println("Soy el estado final del FSM");
		}
	}//Fsm_2
	protected void takeDown(){
		System.out.println("Liberando Recursos");
	}
}//Agent
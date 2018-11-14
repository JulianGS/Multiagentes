package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;

/*
SIN USAR HEBRAS

Diseñe un agente que con una variable entera compartida y en el que su comportamiento principal será un comp partalelo con 3 subcomp asociados:

	1 - Un wakeup que borra el agente en 12 segundos
	2 - Dos comportamientos cíclicos
		2.1 - Incrementan el valor de la var compartida, esperan n segundos e imprimen el valor de la variable. 
			  Para realizar una pausa usa Thread.sleep(tiempo en ms). Los 2 valores de n se pasarán al constructor y serán de 2000 ms 
			  para Comp1 y 3000 ms para comp2
*/

public class AgenteHebraFalso extends Agent{

	int var_compartida;
	ParallelBehaviour comp_principal; 

	protected void setup(){

		comp_principal = new ParallelBehaviour();

		comp_principal.addSubBehaviour(new SubComp1(this,12000));
		comp_principal.addSubBehaviour(new SubComp2(this,2000));
		comp_principal.addSubBehaviour(new SubComp2(this,3000));

		addBehaviour(comp_principal);
	}

	protected void takeDown(){
		System.out.println("Agente finalizado. Liberando recursos");
	}

	private class SubComp1 extends WakerBehaviour{
		
		private Agent a;
		private long timeout;

		public SubComp1(Agent a, long timeout){
			super(a,timeout);
		}

		protected void onWake(){
			System.out.println("Han pasado 12s.... Borrando Agente......");
			myAgent.doDelete();
		}
	}

	private class SubComp2 extends Behaviour{
		
		private Agent a;
		private int n;

		public SubComp2(Agent a, int n){
			this.a = a;
			this.n = n;
		}

		public void action(){
			System.out.println("Antes:"+var_compartida);
			var_compartida++;
			try {
				Thread.sleep(n);
			} catch (InterruptedException e) {
				
			}
			System.out.println("5 secs más tarde: "+var_compartida);
		}

		public boolean done(){
			return false;
		}
	}

}
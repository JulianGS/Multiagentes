package examples.prep_exam_1;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Temperatura extends Agent{
	private Behaviour comp1;
	private int tem_actual;	
	protected void setup(){
		comp1 = new MiComportamiento1();
		addBehaviour(comp1);
	}//setup

	private class MiComportamiento1 extends CyclicBehaviour{
		int contador;
		public void onStart(){
			tem_actual = (int)(Math.random() * 33+24);
			//tem_actual = 25;
			contador = 0;	
			System.out.println("Pasa");		
		}		
		public void action(){
			System.out.println("Actualmente en comp1 la temperatura es de "+tem_actual);			
			if(tem_actual>28){
				Behaviour refrigerar = new Refrigerar();
				myAgent.addBehaviour(refrigerar);
				comp1.block();
			}else if(tem_actual<28){
				Behaviour calentar = new Calentar();
				myAgent.addBehaviour(calentar);
				comp1.block();
			}
			contador++;	
		}//action

		public boolean done(){
			return (contador == 10);	
		}

	}//MiComp1

	private class Refrigerar extends OneShotBehaviour{
		
		public void action(){
			tem_actual--;
			System.out.println("Enfrio y queda "+tem_actual);	
		}//action
		public int onEnd(){
			//comp1.reset();
			//myAgent.addBehaviour(comp1);
			comp1.restart();
			return 0;
		}

	}//Enfriar

	private class Calentar extends OneShotBehaviour{
		
		public void action(){
			tem_actual++;
			System.out.println("Caliento y queda "+tem_actual);	
		}//action
		public int onEnd(){
			//comp1.reset();
			//myAgent.addBehaviour(comp1);
			comp1.restart();			
			return 0;
		}
	}//Calentar
}//Mi agente

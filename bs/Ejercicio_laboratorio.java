package examples;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Ejercicio_laboratorio extends Agent{
	private Behaviour comp;
	protected void setup(){
		//Codigo
		comp = new MiComportamiento1();
		addBehaviour(comp);
	}
	// Finalización del agente
	protected void takeDown(){
        	System.out.println("****Agente finalizado****");
    	}

	//Comportamiento 1
	private class MiComportamiento1 extends Behaviour{
		private int x;		
		public void onStart(){
			int x = 0;		
		}		
		public void action(){
			System.out.println("Soy el comportamiento 1");
			System.out.println("Ahora x = " + x);
			x++;
			if(x==5){
				myAgent.addBehaviour(new MiComportamiento2());
				comp.block();
			}
		}

		public boolean done(){
			//Codigo
			return (x == 8);
		}
		public int onEnd(){
			System.out.println("Liberando recursos");			
			myAgent.doDelete();
			return 0;
		}
	}

	//Comportamiento 2
	private class MiComportamiento2 extends Behaviour{
		private int y;		
		public void onStart(){
			y = 5;		
		}
		public void action(){
			System.out.println("Soy el comportamiento 2");
			System.out.println("Ahora y = "+y);
			y--;
		}
		public boolean done(){
			//Codigo
			return (y == 0);
		}
		public int onEnd(){
			comp1.restart();
			return 0;
		}	
	}
}

//Version que acaba si x=8
/*
package examples;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Ejercicio_laboratorio extends Agent{
	private Behaviour comp;
	protected void setup(){
		//Codigo
		comp = new MiComportamiento1();
		addBehaviour(comp);
	}

	//Comportamiento
	private class MiComportamiento1 extends Behaviour{
		private int x = 0;		
		public void action(){
			System.out.println("Mi nombre es: " + getName());
			System.out.println("Soy el comportamiento del agente");
			System.out.println("Ahora x = " + x);
			x++;
		}

		public boolean done(){
			//Codigo
			return (x == 8);
		}
	}
}*/

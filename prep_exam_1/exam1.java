package examples.prep_exam_1;

import jade.core.Agent;
import jade.core.behaviours.*;

public class exam1 extends Agent {
	private Comp1 refrigeracion;
	private Comp2 calefaccion;
	private int temperatura;
	protected void setup(){
		refrigeracion = new Comp1();
		calefaccion = new Comp2();
		temperatura = (int)(Math.random()*33+24); // Numero aleatorio entre 24 y 32 ºC
		
		if (temperatura > 28)
			addBehaviour(refrigeracion);
		else if (temperatura < 28)
			addBehaviour(calefaccion);
		else	System.out.println("La temperatura es 28ºC agente");
	}
	private class Comp1 extends Behaviour {
		public void action() {
			System.out.println("La temperatura es "+temperatura+" ºC, refrigerando...");
			temperatura--;
		}
		public boolean done() {
			return (temperatura == 28);
		}
		public int onEnd() {
			System.out.println("La temperatura es 28ºC\nNueva Lectura");
			temperatura = (int)(Math.random()*8)+24; // Numero aleatorio entre 24 y 32 ºC
		
			if (temperatura > 28)
				myAgent.addBehaviour(refrigeracion);
			else if (temperatura < 28)
				myAgent.addBehaviour(calefaccion);
			else	System.out.println("La temperatura es 28ºC");
			return 0;
		}
	}
	private class Comp2 extends Behaviour {
		public void action() {
			System.out.println("La temperatura es "+temperatura+" ºC, calentando...");
			temperatura++;
		}
		public boolean done() {
			return (temperatura == 28);
		}
		public int onEnd() {
			System.out.println("La temperatura es 28ºC\nNueva Lectura");
			temperatura = (int)(Math.random()*33+24); // Numero aleatorio entre 24 y 32 ºC
		
			if (temperatura > 28)
				myAgent.addBehaviour(refrigeracion);
			else if (temperatura < 28)
				myAgent.addBehaviour(calefaccion);
			else	System.out.println("La temperatura es 28ºC");
			return 0;
		}
	}
}

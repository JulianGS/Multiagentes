package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;

/*
USANDO HEBRAS

Diseñe un agente que con una variable entera compartida y en el que su comportamiento principal será un comp partalelo con 3 subcomp asociados:

	1 - Un wakeup que borra el agente en 12 segundos
	2 - Dos comportamientos cíclicos
		2.1 - Incrementan el valor de la var compartida, esperan n segundos e imprimen el valor de la variable. 
			  Para realizar una pausa usa Thread.sleep(tiempo en ms). Los 2 valores de n se pasarán al constructor y serán de 2000 ms 
			  para Comp1 y 3000 ms para comp2. Este comp se ejecuta en una hebra dedicada
*/

public class AgenteHebraVerdadero extends Agent{

	int var_compartida = 0; //Siempre inicializar
	ParallelBehaviour comp_principal;
	ThreadedBehaviourFactory CompHebras; //NUEVO
	SubComp2 con; //Para hebras, importante declarar el comportamiento que la use como global

	protected void setup(){

		comp_principal = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		CompHebras = new ThreadedBehaviourFactory(); //Instancio 

		//El waker
		comp_principal.addSubBehaviour(new SubComp1(this,12000));

		//Creamos uno con threads, y otro sin
		SubComp2 sin = new SubComp2(this, 2000,"sin"); //SIN
		con = new SubComp2(this,20000,"con"); //CON

		comp_principal.addSubBehaviour(sin);
		comp_principal.addSubBehaviour(CompHebras.wrap(con));
		

		addBehaviour(comp_principal);
	}

	protected void takeDown(){
		System.out.println("Agente finalizado. Liberando recursos");
		//Esto siempre igual cuando uses threads
		Thread td = CompHebras.getThread(con);
		td.interrupt();
		super.takeDown();
	}

	private class SubComp1 extends WakerBehaviour{
		
		private Agent a;
		private long timeout;

		public SubComp1(Agent a, long timeout){
			super(a,timeout);
		}

		protected void onWake(){
			System.out.println("Han pasado 12s.... Borrando Agente......");
		}

		//El doDelete se hace en el onEnd, no en el action!
		public int onEnd(){
			myAgent.doDelete(); 
			return 1;
		}
	}

	private class SubComp2 extends Behaviour{
		
		private Agent a;
		private int n;
		private String nombre;
		private boolean done = false;

		public SubComp2(Agent a, int n, String nombre){
			this.a = a;
			this.n = n;
			this.nombre = nombre;
		}

		public void action(){
			var_compartida++;
			System.out.println(this.nombre+" incrementó a "+var_compartida);
			try {
				Thread.sleep(n);
			} catch (InterruptedException e) {
				done = true;
			}
			System.out.println("Pasados los "+this.n+" segundos");
			System.out.println(this.nombre+" incrementó a "+var_compartida
				+ " , Leyó " + var_compartida);
			
		}

		public boolean done(){
			return done;
		}
	}

}
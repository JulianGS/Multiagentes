package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;


public class ConcurrentAgent extends Agent {
	//Variable global incrementada en Cyclic
	public int value = 0;
	
	//Dos comportamientos
	ThreadedBehaviourFactory tbf;
	MyCycle cyc2;
	
	@Override
	protected void setup() {
		//Tercer comportamiento
		ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		
		MyWaker wk = new MyWaker(this, 12000); //A los 12 segundos, borra el agente
		pb.addSubBehaviour(wk); //Añado el comportamiento waker al paralelo
		
		tbf = new ThreadedBehaviourFactory(); //Instancio el comportamiento de hebra

		/*Los comportamientos cycle lo que hacen es:
			1º Value ++ (que es la variable global iniciada a 0)
			
			2º CREA un String al que le contatena: nombre (pasado por atributo), "Written", y el valor de value
			
			3º Hace un try-catch donde intenta dormir durante el tiempo que le pasamos por argumento al THREAD;
			si no lo consigue pone done = true.

			4º IMPRIME el string del paso 2 + "Read:" y el valor de value
			*/

		MyCycle cyc1 = new MyCycle("Normal", 2000); //Comportamiento al que le paso (nombre,tiempo)

		cyc2 = new MyCycle("Threaded", 20000); //Comportamiento al que le paso (nombre,tiempo)

		//Añado al comportamiento paralelo los dos comportamientos Cycle
		pb.addSubBehaviour(cyc1);
		pb.addSubBehaviour(cyc2);
		//pb.addSubBehaviour(cyc2);
		
		//Añado el comportamiento paralelo al agente
		addBehaviour(pb);
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Taking down");
		Thread td = tbf.getThread(cyc2);
		td.interrupt();
		super.takeDown();
	}
	
	private class MyWaker extends WakerBehaviour {
		
		public MyWaker(Agent a, long timeout) {
			super(a, timeout);
		}

		@Override
		protected void onWake() {
			System.out.println("12 seconds are passed!");
		}
		
		@Override
		public int onEnd() {
			myAgent.doDelete();
			return 0;
		}
	}
	
	private class MyCycle extends Behaviour {
		private long time;
		private String name;
		private boolean done;
		
		public MyCycle(String name, long time) {
			this.time = time;
			this.name = name;
			done = false;
		}
		@Override
		public void action() {
			value++;
			String toPrint = "";
			toPrint += name + ": Written " + value;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				done = true;
			}
			System.out.println(toPrint + ", Read " + value);
			
			
			
		}
		@Override
		public boolean done() {
			return done;
		}
		
	}
}


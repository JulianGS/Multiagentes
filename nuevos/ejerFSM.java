package examples.nuevos;

import jade.core.Agent;
import jade.core.behaviours.*;
import java.util.Date;

public class ejerFSM extends Agent{
	protected void setup(){
		Date d = new Date();
		ParallelBehaviour pb = new ParallelBehaviour(this,0){
			public int onEnd(){
				myAgent.doDelete();
				return 0;
			}
		};//PARALLEL BEHAVIOUR

		pb.addSubBehaviour(new comp1());		
		pb.addSubBehaviour(new OneShotBehaviour(this){
			public void action(){
				System.out.println("Soy el 2º sub comportamiento y soy OneShot");
			}
		});//ADD SUBBEHAVIOUR
		addBehaviour(pb);
		d.setSeconds(d.getSeconds()+5);
		pb.addSubBehaviour(new comp3(this,d));	
	}//setup

	private class comp1 extends Behaviour{
		private int ejec;
		public void onStart(){
			ejec = 1;
		}
		public void action(){
			System.out.println("Soy el 1er sub comportamiento y estoy en mi ejecución nº "+ejec);
			ejec++;
		}
		public boolean done(){
			return (ejec > 3);
		}
	}//comp1

	private class comp3 extends WakerBehaviour{
		public comp3(Agent a, Date wakeupDate){
			super(a, wakeupDate);
		}

		protected void onWake(){
			System.out.println("Soy el 3er subcomportamiento y acabo de despertar. Waker Behaviour");
		}
	}//comp3
}//Agent

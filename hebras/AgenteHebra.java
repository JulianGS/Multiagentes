package examples.hebras;

import jade.core.Agent;
import jade.core.behaviours.*;

public class AgenteHebra extends Agent{
	private Behaviour comp1;
	private Behaviour comp2;
	protected int var_compartida;

	protected void setup(){
		long segundos = 43200;
		this.addBehaviour(new wakeup(this, segundos));
	}

	private class wakeup extends WakerBehaviour{
		protected void onWake() {
			System.out.println("Han pasado 12 segundos");
			myAgent.doDelete();
		}		
	}//Waker

	private class MiComportamiento1 extends CyclicBehaviour{
		public void action{
			
		}
	}//comp1
	private class MiComportamiento2 extends CyclicBehaviour{}//comp2
}

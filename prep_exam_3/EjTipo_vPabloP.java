//Tiene race conditions, se arregla con sleeps

package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;

public class EjTipo_vPabloP extends Agent {
	private ParallelBehaviour main;
	private MiComp sub1,sub2,sub3;
	protected ThreadedBehaviourFactory tbf;

	protected void setup() {
		tbf = new ThreadedBehaviourFactory();
 		main = new ParallelBehaviour(this, 2) {
			public int onEnd(){
				doDelete();				
				return 0;				
			}};
		sub1 = new MiComp("1", (int)(Math.random()*100+1));
		sub2 = new MiComp("2", (int)(Math.random()*100+1));
		sub3 = new MiComp("3", (int)(Math.random()*100+1));
		
 		main.addSubBehaviour(tbf.wrap(sub1));
		main.addSubBehaviour(tbf.wrap(sub2));
		main.addSubBehaviour(tbf.wrap(sub3));
		
		addBehaviour(main);
	}
	protected void takeDown() {
		System.out.println("Liberando recursos...");
		tbf.interrupt();
		super.takeDown();
		System.out.println("*** Agente destruido ***");
	}
	private class MiComp extends Behaviour {
		private String identify;
		private int exec;
		private MiComp brother1, brother2;
		
		public MiComp (String identify, int exec) {
			this.identify = identify;
			this.exec = exec;

		}	
		public void onStart() {
			switch (Integer.parseInt(this.identify)) {
				case 1:
					this.brother1 = sub2;
					this.brother2 = sub3;
					break;
				case 2:
					this.brother1 = sub1;
					this.brother2 = sub3;
					break;
				default: // case 3
					this.brother1 = sub1;
					this.brother2 = sub2;
					break;
			}
		}
		public void action() {
		
			this.brother1.block();
			this.brother2.block();
		
			System.out.println("I'm subBehaviour "+this.identify+" and "+(this.exec--)+" executions left.");

			this.brother1.restart();
			this.brother2.restart();
		}
		public boolean done() {
			return (exec <= 0);
		}
		public int onEnd() {
			System.out.println("I'm "+this.identify+" and I have been finish!");
			return 0;
		}
	}
}

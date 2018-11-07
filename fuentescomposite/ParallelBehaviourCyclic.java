package examples.fuentescomposite;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.Behaviour;

public class ParallelBehaviourCyclic extends Agent {
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		ParallelBehaviour pb = new ParallelBehaviour(this,4);//el 2 es WHEN_ANY, un 0 seria WHEN_ALL. Puedes usar "ParallelBehaviour.WHEN_ALL
		pb.addSubBehaviour(new CyclicBehaviour(this) {
			public void action(){
				System.out.println("one");
			}
		});
		pb.addSubBehaviour(new CyclicBehaviour(this) { //Si pongo Behaviour (sin cyclic) habría que añadir done()
			public void action(){
				System.out.println("two");
			}
		});
		pb.addSubBehaviour(new CyclicBehaviour(this) {
			public void action(){
				System.out.println("three");
			}
		});
		pb.addSubBehaviour(new CyclicBehaviour(this) {
			public void action(){
				System.out.println("four");
			}
		});	
		addBehaviour(pb);
			}

	
}

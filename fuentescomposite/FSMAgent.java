package examples.fuentescomposite;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.FSMBehaviour;

/**
   This example shows the usage of the <code>FSMBehavior</code> that allows
   composing behaviours according to a Finite State Machine.<br>
   @author Giovanni Caire - TILAB
 */
public class FSMAgent extends Agent {
	// Primero se definen los identificadores de los estados
	private static final String STATE_A = "A";
	private static final String STATE_B = "B";
	private static final String STATE_C = "C";
	private static final String STATE_D = "D";
	private static final String STATE_E = "E";
	private static final String STATE_F = "F";
	
	protected void setup() {
		FSMBehaviour fsm = new FSMBehaviour(this) {
			public int onEnd() {
				System.out.println("FSM behaviour completed.");
				myAgent.doDelete();
				return super.onEnd();
			}
		};
		
		// Register state A (first state)
		fsm.registerFirstState(new NamePrinter(), STATE_A);
		
		// Register state B
		fsm.registerState(new NamePrinter(), STATE_B);
		
		// Register state C
		fsm.registerState(new RandomGenerator(3), STATE_C); //A randomgenerator le paso un entero y me genera valores entre 0 y ese valor-1.
		
		// Register state D
		fsm.registerState(new NamePrinter(), STATE_D);
		
		// Register state E
		fsm.registerState(new RandomGenerator(4), STATE_E);
		
		// Register state F (final state)
		fsm.registerLastState(new NamePrinter(), STATE_F);

		// Register the transitions
		fsm.registerDefaultTransition(STATE_A, STATE_B);
		fsm.registerDefaultTransition(STATE_B, STATE_C);
		fsm.registerTransition(STATE_C, STATE_C, 0);
		fsm.registerTransition(STATE_C, STATE_D, 1);
		fsm.registerTransition(STATE_C, STATE_A, 2);
		fsm.registerDefaultTransition(STATE_D, STATE_E);
		//Tengo 2 transiciones del estado E. Cuando hay 2 transiciones, prioriza la que tiene etiqueta antes que la default		
		fsm.registerTransition(STATE_E, STATE_F, 3); 
		fsm.registerDefaultTransition(STATE_E, STATE_B);
		
		addBehaviour(fsm);
	}
	
	/**
	   Inner class NamePrinter.
	   This behaviour just prints its name
	 */
	private class NamePrinter extends OneShotBehaviour {
		public void action() {
			System.out.println("Executing behaviour "+getBehaviourName());
		}
	}
	 
	/**
	   Inner class RandomGenerator.
	   This behaviour prints its name and exits with a random value
	   between 0 and a given integer value
	 */
	private class RandomGenerator extends NamePrinter {
		private int maxExitValue;
		private int exitValue;
		
		private RandomGenerator(int max) {
			super();
			maxExitValue = max;
		}
		
		public void action() {
			System.out.println("Executing behaviour "+getBehaviourName());
			exitValue = (int) (Math.random() * maxExitValue);
			System.out.println("Exit value is "+exitValue);
		}
		
		public int onEnd() {
			return exitValue;
		}
	}
}
/*
El autómata sería:

>A->B->C->(A con 2)
       C->(C con 0)
       C->(D con 1) D->E->([F] con 3)
		       E->B (por defecto, con 0,1,2 por así decirlo)
*/

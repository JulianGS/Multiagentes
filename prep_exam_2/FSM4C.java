package examples.prep_exam_2;

/*
Un fsm con 4 estados, en los cuales se muestra un mensaje con su nombre.:

	INICIO -> OneShot
	ESTADO_1 -> OneShot
	ESTADO_2 -> Behaviour
	FIN -> OneShot

	Trans(Inicio, E1)
	Trans(E1, E1)
	Trans(E1, E2)
	Trans(E2, FIN)
	*/

import jade.core.Agent;
import jade.core.beaviours.*;

public class FSM4C extends Agent{

	FSMBehaviour fsm;

	protected void setup(){
		fsm = new FSMBehaviour(){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};

		fsm.registerFirstState(new OneShotBehaviour(){
			public void action(){
				System.out.println("Soy INICIO");
			}
		},"INICIO");

		fsm.registerState(new OneShotBehaviour(){
			public void action(){
				System.out.println("Soy ESTADO_1");
			}
		},"ESTADO_1");
		
		fsm.registerState(new OneShotBehaviour(){
			public void action(){
				System.out.println("Soy FIN");
			}
		},"FIN");

		fsm.registerLastState(new Behaviour(){
			public void action(){
				System.out.println("Soy FIN");
			}
			public boolean done(){
				return true;
			}
		},"FIN");		

	}
	protected void takeDown(){
		System.out.println("Agente finalizado");
	}
}
/*
ENUNCIADO:

-Añadir los 2 comportamientos en la inicializacion del agente
-Ambos comportamientos llevan una var entera "nEjec" random entre 0-100
-Este numero se redondea al numero par mayor sino lo es*/

package examples.prep_exam_1;

import jade.core.Agent;
import jade.core.Behaviours.*;

public class MiAgenteClase extends Agent{
	private Behaviour comp1;
	private Behaviour comp2;	
	protected void setup(){
		comp1 = new MiComportamiento1();
		comp2 = new MiComportamiento2();
		addBehaviour(comp1);
		addBehaviour(comp2);
	}//setup

	protected void takeDown(){
		System.out.println("Hundido");
	}

	private class MiComportamiento1 extends Behaviour{
		private int NumeroEjecuciones;
		private int actuales;
		public void onStart(){
			NumeroEjecuciones = ((int)(Math.random()*100));
			while(NumeroEjecuciones % 2 != 0){
				NumeroEjecuciones = Math.round(NumeroEjecuciones);
			}
			actuales = 0;
		}			
		public void action(){
			if(actuales == (NumeroEjecuciones/2)){
				comp2.block();			
			}	
		}//action
	
		public boolean done(){
			return (actuales == 5);
		}//done

		public int onEnd(){
			myAgent.doDelete();			
			return 0;
		}
	}//MiComp1

	private class MiComportamiento2 extends Behaviour{
		private int NumeroEjecuciones;
		private int actuales;
		public void onStart(){
			NumeroEjecuciones = ((int)(Math.random()*100));
			while(NumeroEjecuciones % 2 != 0){
				NumeroEjecuciones = Math.round(NumeroEjecuciones);
			}
			actuales = 0;
		}			
		public void action(){
			if(actuales == (NumeroEjecuciones/2)){
				comp1.block();			
			}	
		}//action
	
		public boolean done(){
			return (actuales == 5);
		}//done
		public int onEnd(){
			myAgent.doDelete();			
			return 0;
		}
	}//MiComp2
}//Mi agente

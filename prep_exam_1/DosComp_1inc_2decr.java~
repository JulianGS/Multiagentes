/*
ENUNCIADO:

- En la inicialización del agente se añade sólo un comportamiento del tipo 1
- El comport1: hace que se incremente una variable entera x inicializada a 0 en cada ejecución del comportamiento y la muestra por pantalla.
  Cuando x=5, se añade el comport2 y se bloquea el comport1. Comp1 finaliza si x = 8
- El comport2: hace que se decremente una variable entera y = 5. Cuando y=0 se desbloque comp1 y comp2 finaliza su ejecución.
- Añade un método de finalización que imprima en pantalla "acabao".

*/

package examples.prep_exam_1;

import jade.core.Agent;
import jade.core.Behaviours.*;

public class DosComp_1inc_2decr extends Agent{
	private Behaviour comp1; //Como voy a tener que bloquearlo, necesito una variable si o si.	
	protected void setup(){
		comp1 = new MiComportamiento1(); //cumplo la regla 1	
		addBehaviour(comp1);
	}//setup

	protected void takeDown(){
		System.out.println("Agente finalizado");	
	}

	private class MiComportamiento1 extends Behaviour{
		private int x;
		//para cumplir que x=0 al principio de la ejecución, tengo que añadir onStart()
		public void onStart(){
			  x = 0;
		}//onStart		
		public void action(){
			System.out.println("Ahora mismo x = "+x);
			if(x==5){
				MyAgent.addBehaviour(new MiComportamiento2());
				comp1.block();
			}
			x++;	
			
			
		}//action
	
		public boolean done(){
			return (x==8);
		}//done
		public int onEnd(){
			MyAgent.doDelete();
			return 0;
		}
	}//MiComp1
	
	private class MiComportamiento2 extends Behaviour{
		private int y;
		public void onStart(){
			 y = 5;		
		}
		public void action(){
			System.out.println("Ahora y = "+y);
			y--;
		}
		public boolean done(){
			return (y == 0);		
		}
		public int onEnd(){
			restart(comp1);
			return 0;		
		}
	}//MiComp2
}//Mi agente


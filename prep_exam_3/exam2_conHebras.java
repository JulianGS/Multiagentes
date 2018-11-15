/*
ESTA SHIT PERO CON HEBRAS

Diseñe un agente que simule un comportamiento FSMBehaviour sin utilizar la clase para este composite behaviour concreto.

* El autómata tendrá 3 estados

* Cada comportamiento asociado al estado recibirá un texto identificativo del comportamiento (p.e "comp 1") y un entero entre 1-1000
  generado de forma aleatoria, que será el nº de veces que se ejecutará el comportamiento al iniciarse.

* En cada ejecución se mostrará el texto "Soy el comportamiento X y estoy en mi ejecución Y"

* Cada vez que termina un estado se genera un aleatorio entre 0-2, donde 0 = volver al estado anterior si es posible
  1 = permanecer en el mismo estado y 2 = avanzar al siguiente estado si es posible / finalizar el agente si es el estado final.

* Se añadirá el método para la liberación de recursos imprimiendo "Liberando recursos"

*/

package examples.prep_exam3;

import jade.core.Agent;
import jade.core.behaviours.*;

public class exam2_conHebras extends Agent{

	ParallelBehaviour principal;
	SubComp estado1, estado2, estado3;
	ThreadedBehaviourFactory tbf;

	protected void setup(){

		principal = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ALL){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};

		tbf = new ThreadedBehaviourFactory();
		
		estado1 = new SubComp(1,(int)((Math.random()*100)+1),true);
		estado2 = new SubComp(2,(int)((Math.random()*100)+1),false);
		estado3 = new SubComp(3,(int)((Math.random()*100)+1),false);

		principal.addSubBehaviour(tbf.wrap(estado1));
		principal.addSubBehaviour(tbf.wrap(estado2));
		principal.addSubBehaviour(tbf.wrap(estado3));

		addBehaviour(principal);
	}

	protected void takeDown(){
		System.out.println("Finalizando Agente");
	}

	private class SubComp extends Behaviour{

		private int nombre, ejecuciones,llevo;
		private boolean primero;
		private SubComp irmao;

		public SubComp(int nombre, int ejecuciones, boolean primero){
			this.nombre=nombre;
			this.ejecuciones=ejecuciones;
			this.primero=primero;
			this.llevo=0;
		}

		public void onStart(){

		}

		public void action(){
			llevo++;
			System.out.println("Soy el estado "+nombre+" y me quedan "+(ejecuciones-llevo)+" ejecuciones");
		}

		public boolean done(){
			return (llevo>=ejecuciones);
		}

		public int onEnd(){
			int aleatorio = (int)(Math.random()*3);
			System.out.println("aleatorio = "+aleatorio);
			switch (aleatorio){
				case 1:
				if(this.nombre==1){
					System.out.println("1 pero estoy en estado 1, me quedo aquí");
					principal.addSubBehaviour(tbf.wrap(new SubComp(1,ejecuciones,true)));
				}else{
					System.out.println("1 - Voy al estado anterior");
					principal.addSubBehaviour(tbf.wrap(new SubComp((this.nombre-1),ejecuciones,false)));
				}
				break;

				case 2:
				System.out.println("2 - Repito estado");
				principal.addSubBehaviour(tbf.wrap(new SubComp(this.nombre,ejecuciones,false)));
				break;

				case 3:
				if(this.nombre==3){
					System.out.println("3 - Y estaba en estado 3, fin del programa");
				}else{
					System.out.println("3 - Estado siguiente");
					principal.addSubBehaviour(tbf.wrap(new SubComp((this.nombre+1),ejecuciones,false)));
				}
				
			}//Switch
			return 1;
		}
	}
}
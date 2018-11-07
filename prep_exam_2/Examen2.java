/*
Diseñe un agente que simule un comportamiento FSMBehaviour sin utilizar la clase para este composite behaviour concreto.

* El autómata tendrá 3 estados

* Cada comportamiento asociado al estado recibirá un texto identificativo del comportamiento (p.e "comp 1") y un entero entre 1-1000
  generado de forma aleatoria, que será el nº de veces que se ejecutará el comportamiento al iniciarse.

* En cada ejecución se mostrará el texto "Soy el comportamiento X y estoy en mi ejecución Y"

* Cada vez que termina un estado se genera un aleatorio entre 0-2, donde 0 = volver al estado anterior si es posible
  1 = permanecer en el mismo estado y 2 = avanzar al siguiente estado si es posible / finalizar el agente si es el estado final.

* Se añadirá el método para la liberación de recursos imprimiendo "Liberando recursos"

*/

package examples.prep_exam2;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Examen2 extends Agent{
	
	SequentialBehaviour sb;
	int n_ejec;

	protected void setup(){
		
		sb = new SequentialBehaviour(this){
			public int onEnd(){
				myAgent.doDelete();
				return 1;
			}
		};
		n_ejec = (int)((Math.random()*100)+1);
		sb.addSubBehaviour(new Estado(0,n_ejec));
		addBehaviour(sb);

	}//setup

	protected void takeDown(){
		System.out.println("Agente finalizado. Liberando recursos");
	}

	private class Estado extends Behaviour{
		int llevo;
		int n_ej;
		int nombre;

		public Estado(int nombre, int n_ej){
			this.nombre=nombre;
			this.n_ej=n_ej;
			this.llevo=0;
		}

		public void action(){
			System.out.println("Soy el comportamiento "+nombre+". LLevo "+llevo+" ejecuciones");
			System.out.println("Tengo que hacer "+n_ej+" ejecuciones");
			llevo++;
		}

		public boolean done(){
			return (llevo>n_ej);
		}

		public int onEnd(){
			int aleatorio = (int)(Math.random()*3);
			System.out.println("aleatorio = "+aleatorio);
			switch (aleatorio){
				case 0:
				if(this.nombre==0){
					System.out.println("0 pero estoy en estado 0, me quedo aquí");
					sb.addSubBehaviour(new Estado(0,n_ejec));
				}else{
					System.out.println("0 - Voy al estado anterior");
					sb.addSubBehaviour(new Estado((this.nombre-1),n_ejec));
				}
				break;

				case 1:
				System.out.println("1 - Repito estado");
				sb.addSubBehaviour(new Estado(this.nombre,n_ejec));
				break;

				case 2:
				if(this.nombre==2){
					System.out.println("2 - Y estaba en estado 2, fin del programa");
				}else{
					System.out.println("2 - Estado siguiente");
					sb.addSubBehaviour(new Estado((this.nombre+1),n_ejec));
				}
				
			}//Switch
			return 1;
		}//OnEnd

	}//Estado
}//Agente

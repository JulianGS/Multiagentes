package examples;

import jade.core.Agent;
import jade.core.behaviours.*;

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

public class EjercioExamen2 extends Agent{

	SequentialBehaviour fsmb;

	protected void setup(){
		fsmb = new SequentialBehaviour(this){
			public int onEnd(){
				reset();
				return super.onEnd();
			}
		};
		int al = (int)((Math.random()*1000)+1);
		fsmb.addSubBehaviour(new Estado(this, 1, al));
		addBehaviour(fsmb);
		
	}
	private class Estado extends Behaviour{

		private int nombre;
		private int n;
		private int i;
		private Agent a;

		public Estado(Agent a, int nombre, int n){
			super(a);
			this.a = a;
			this.n = n;
			this.nombre = nombre;
		}
		public void onStart(){
			this.i = 0;
		}
		public void action(){
			System.out.println("Soy el estado comp" + this.nombre + " y estoy en mi ejecucion " + this.i);
			this.i++;
		}
		public boolean done(){
			return i > this.n;
		}
		public int onEnd(){
			if(this.nombre<3){
				int trans = (int)(Math.random()*3);
				System.out.println(trans);
				int al = (int)((Math.random()*1000)+1);
				switch(trans){
					case 0:
						if(this.nombre == 1){
							System.out.println("0 Pero soy el estado inicial, asi que vuelvo a mi mismo");
							fsmb.addSubBehaviour(new Estado(this.a, 1, al));
						}else{
							System.out.println("Vuelve al estado anterior");
							fsmb.addSubBehaviour(new Estado(this.a, this.nombre-1, al));
						}
						break;
					case 1:
						System.out.println("1 asi que vuelvo a mi mismo");
						fsmb.addSubBehaviour(new Estado(this.a, this.nombre, al));
						break;
					case 2:
						System.out.println("2 Avanza de estado"+al);
						fsmb.addSubBehaviour(new Estado(this.a, this.nombre+1, al));
						break;
					default:
						System.out.println("Ha ocurrido un error");
						break;
				}
			}else{
				System.out.println("Fin");
			}
			return 0;
		}
	}
}


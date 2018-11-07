package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class Ejemplo2 extends Agent{
 
    // Inicialización del agente
    protected void setup(){
        // Añade un comportamiento
        addBehaviour(new MiComportamiento());
    }
 
    // Esto se ejecuta después de setup. Igual que el done de abajo después de action.
    protected void takeDown(){
        System.out.println("He llegado a takedown");
    }
 
    // Definición de un comportamiento
    private class MiComportamiento extends Behaviour{
        private int estado = 0;
 
        // Función que realiza MiComportamiento
        public void action(){
            switch(estado){
                case 0: System.out.println("Do"); break;
                case 1: System.out.println("Re"); break;
                case 2: System.out.println("Mi"); break;
                case 3: System.out.println("Fa"); break;
                case 4: System.out.println("Sol");break;
                case 5: System.out.println("La"); break;
                case 6: System.out.println("Si"); break;
                case 7:{
                        System.out.println("Do");
                        myAgent.doDelete(); //Si no pones doDelete, el agente aunque ya no ejecute action, se queda en espera, no muere.
			//Y por tanto no muere MiComportamiento (clase). Y no se ejecuta takedown.
			//DO DELETE MATA A MICOMPORTAMIENTO ; NO AL AGENTE; POR TANTO tras setup() se ejecuta takedown()
                        break;
                }
            }
            estado++;
        }
 
        // Comprueba si el comportamiento ha finalizado.
        public boolean done(){
		System.out.println("LLego a done");            
		return (estado > 7);
        }
    }
}

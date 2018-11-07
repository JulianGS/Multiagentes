package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class MiAgente2 extends Agent{
 
    protected void setup(){
        //Aqui es donde se añade el comportamiento.
        addBehaviour(new MiComportamiento1());
    }
    //Este es el comportamiento.
    private class MiComportamiento1 extends Behaviour{
        public void action(){
            System.out.println("Mi nombre es: "+getName() );
            System.out.println("Soy el primer comportamiento");
 
        myAgent.addBehaviour(new MiComportamiento2());
        }
        public boolean done(){
            return true;
        }
    }
    //Este es el otro comportamiento
    private class MiComportamiento2 extends Behaviour{
        public void action(){
            System.out.println("Soy el segundo comportamiento");
    }
        public boolean done(){
            return true;
        }
   }
}

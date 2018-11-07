package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class Cyclic extends Agent{
 
     public void setup() {
         MyCyclicBehaviour c = new MyCyclicBehaviour();
        addBehaviour(c);
    }
 
    protected void takeDown(){
        System.out.println("Ejecucion finalizada");
    }
 
     private class MyCyclicBehaviour extends CyclicBehaviour {
          public void action() {
             System.out.println("Ejecutamos la accion ciclicamente");
          }
     }
}

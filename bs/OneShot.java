package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class OneShot extends Agent{
 
    public void setup() {
        MyOneShotBehaviour c = new MyOneShotBehaviour();
        addBehaviour(c);
    }
 
    protected void takeDown(){
        System.out.println("Ejecucion finalizada");
    }
 
    private class MyOneShotBehaviour extends OneShotBehaviour {
        public void action() {
           System.out.println("Ejecutamos la accion una sola vez");
           myAgent.doDelete();
         }
    }
}


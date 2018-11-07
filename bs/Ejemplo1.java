package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class Ejemplo1 extends Agent{
    //
    private Behaviour comp;
 
    // Inicialización del agente
    protected void setup()
    {
     //Creamos un comportamiento: un objeto de la clase MiComportamiento1
     comp = new MiComportamiento1();
     //Aqui es donde se añade el comportamiento. Es decir, lo añadimos a la cola de comportamiento activo. 
     addBehaviour(comp);
    }
 
    //Definición de un comportamiento
    private class MiComportamiento1 extends Behaviour{
 
    // define la acción a ser ejecutada cuando se ejecute el comportamiento.
    public void action(){
       System.out.println("Mi nombre es: "+getName() );
       System.out.println("Soy el primer comportamiento");
       // Añade un comportamiento desde otro comportamiento.
       myAgent.addBehaviour(new MiComportamiento2());
        }
 
        // Determina si el comportamiento ha sido completado o no.
        // Si el comportamiento ha finalizado, éste se elimina
      // de la cola de comportamientos activos.
        public boolean done(){
            return true;
        }
    }
 
    //Definición de un segundo comportamiento
    private class MiComportamiento2 extends Behaviour{
        public void action(){
            System.out.println("Soy el segundo comportamiento");
            myAgent.removeBehaviour(comp);
	  //Borramos el primer comportamiento;
        }
        public boolean done(){
            return true;
        }
    }
 
}

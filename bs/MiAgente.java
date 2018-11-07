package examples;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class MiAgente extends Agent{
    /*Lo que se inicia cuando creo el agente*/
    protected void setup(){
    //Aqui es donde se añade el comportamiento.
        addBehaviour(new MiComportamiento1()); //Creamos un objeto de la clase MiComportamiento, que será el comportamiento del agente
	//addBehaviour(new MiComportamiento2()); crearía otro comportamiento más, que podría estar en otra clase debajo "MiComportamiento2"
    }
    //Este es el comportamiento.
    private class MiComportamiento1 extends Behaviour{
	//Lo que se hace cada vez que se ejecuta el agente        
	public void action(){
        System.out.println("Mi nombre es: "+getName() );
            System.out.println("Soy el comportamiento del agente");
 
    	}
	//Done devuelve cuando tiene que dejar de ejecutar action. True = objetivo completo ; no ejecutes más
        public boolean done(){
		// Si inficamos esta variable flase, el metodo action se repite de forma cíclica, acabando en error xq solicita el método done            
		return true;
        }
    }
} //class

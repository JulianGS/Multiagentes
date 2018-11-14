package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.ThreadedBehaviourFactory;


public class Ejemplo3EjConcurrent extends Agent{

        int numeroEjecuciones = (int) (Math.random() * 100);	
	ThreadedBehaviourFactory tbf;
	MiComportamiento Comp1, Comp2;
 
    // Inicialización del agente
    protected void setup()
    {

	ComportamientoControl Comp3;
	tbf = new ThreadedBehaviourFactory();

	ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);

	Comp1= new MiComportamiento("Comp. Incremento","Suma");
	Comp2= new MiComportamiento("Comp. Decremento", "Resta");
	Comp3= new ComportamientoControl("Comp, Control ", 50);
	pb.addSubBehaviour(tbf.wrap(Comp1));
	pb.addSubBehaviour(tbf.wrap(Comp2));
	pb.addSubBehaviour(Comp3);

	addBehaviour(pb);

    }
 
    // Finalización del agente
    protected void takeDown()
    {
    
	System.out.println("Taking down");
	Thread td1 = tbf.getThread(Comp1);
	td1.interrupt();
	Thread td2 = tbf.getThread(Comp2);
	td2.interrupt();
	super.takeDown();

        System.out.println("****Agente finalizado****");
    }
 
    // Definición de un comportamiento
    private class MiComportamiento extends Behaviour
    {
        
 	private String name, operacion;
	public MiComportamiento(String name, String operacion){
	
		this.name=name;
		this.operacion=operacion;

	}

        // // Función que realiza MiComportamiento
        public void action()
        {
            System.out.println(name + ": Esta es la ejecucion "+numeroEjecuciones);


	
	    if (operacion=="Suma")
	            numeroEjecuciones++;
	    else
		    numeroEjecuciones--;		
	

		
}
 
        // Comprueba si el comportamiento ha finalizado
        public boolean done(){
            return false;
        } 
    }


    private class ComportamientoControl extends Behaviour
    {

	private int ValorFinal;
	private String name;

	public ComportamientoControl(String name, int ValorFinal){
		
		this.name=name;	
		this.ValorFinal=ValorFinal;

	}

        public void action()
        {
 
		block(2000);
	   Comp1.block(1000);
	   Comp2.block();
           System.out.println(name + ": Esta es la ejecucion "+numeroEjecuciones + " y el valor buscado es " + ValorFinal);       


		removeBehaviour(Comp1);
        }
 
        // Comprueba si el comportamiento ha finalizado
        public boolean done(){
            return (numeroEjecuciones==ValorFinal);
            
        }

	public int OnEnd(){
		myAgent.doDelete();
		return 0;
	}

    }


}

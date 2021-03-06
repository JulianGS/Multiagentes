/*setup() -> ha habido doDelete()? -> NO -> onStart() -> action() -> done() -> onEnd() ->  ha habido ... 
				   -> SI -> takeDown()*/

import jade.core.Agent;
import jade.core.behaviours.*;

public class MiAgente extends Agent{
	//Antes del setup, es recomendable que siempre declaremos una variable comportamiento.
	private Behaviour comportamiento;	
	
	/*setup se hace LO PRIMERO. Se usa para añadir un comportamiento*/	
	protected void setup(){
		//si hemos creado una variable de comportamiento la iniciamos
		comportamiento = new MiComportamiento();
		addBehaviour(comportamiento);

		//Si no hemos creado variable
		addBehaviour(new MiComportamiento());
	}//setup
	
	//Lo que se incluya en este método se ejecuta después de setup() y por tanto de action (y done) <=> hay doDelete(). 
	//Sino, llega a done() y ahí se queda muerto. 
	protected void takeDown(){
		System.out.println("El agente ha terminado");
	}//takeDown

	// ----- FIN SECCIÓN ----- //

	//Ahora iría el propio comportamiento del agente. Este se incluye en una clase.
	//OneShotBehaviour -> no necesita done xq siempre devuelve true
	//CyclicBehaviour -> no necesita done xq siempre devuelve false
	//TickerBehaviour -> es un cíclico que cada cierto tiempo (definido en el constructor) ejecuta la accion (definida en onTick())
	//WakerBehaviour -> es un oneShot que se ejecuta (en onWake()) cuando se ha cumplido un tiempo

	private class MiComportamiento extends Behaviour{
		//Aqui pueden declararse cosas

		//ANTES de action() y DESPUES de setup puede aparecer este método
		public void onStart(){
			System.out.println("Esto se hace cada vez que se inicia el comportamiento");
        	}//onStart

		//este es el comportamiento que se ejecuta DESPUÉS de setup().
		public void action(){
			System.out.println("Holi soy el agente "+getName());
			MyAgent.addBehaviour(new MiComportamiento2()); //también puedo añadir otros comportamientos aquí.
			MyAgent.doDelete(); //Es como hacer ctrl + C. Mata a MiComportamiento y permite que se ejecute takeDown().
			
		}//action

		/*Esto se ejecuta DESPUÉS de action(), siempre. Puedes devolver una condicion directamente. Si es true, quita el 
		comportamiento de la cola de activos, pero NO lo elimina de la GUI*/
		public boolean done(){
			return true;
			//return (i<3);
		}//done

		// Se ejecuta después de done si done = True
        	public int onEnd(){
            		// Hace que el comportamiento se reinicie al finalizar.
            		reset(); //Devuelve el comportamiento a su estado inicial. Tendrías que añadirlo de nuevo para hacer otra ejecucion.
            		myAgent.addBehaviour(this);
             		return 0;
        	}
	}//MiComportamiento

	//Dentro de la clase total podemos añadir más comportamientos
	private class MiComportamiento2 extends Behaviour{
		public void action(){
			MyAgent.removeBehaviour(comp); //Por supuesto, también podemos quitar comportamientos
		}//action
		public boolean done(){
			return true;
		}//done
	}//MiComportamiento2
}//class

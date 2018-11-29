package examples.prep_exam_4.EjTipo;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceptorTipo extends Agent{

	boolean fin = false;
	
	AID emisor1 = new AID();
	emisor1.setLocalName("emisor1");
	AID emisor2 = new AID();
	emisor2.setLocalName("emisor2");

	protected void setup(){
		addBehaviour(new Pares1());
		addBehaviour(new Impares1());
		addBehaviour(new Pares2());
		addBehaviour(new Impares2());
	}

	private class Pares1() extends Behaviour(){
		int count = 0;

		public void action(){
			ACLMessage mensaje = blockingReceive();
			if(mensaje != null && mensaje.)
		}
	}
}
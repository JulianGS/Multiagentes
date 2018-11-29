package examples.prep_exam_4.EjTipo;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class EmisorTipo1 extends Agent{

	protected void setup(){
		addBehaviour(comp);
	}

	private class comp extends Behaviour{
		boolean fin = false;

		public void action(){

			AID id = new AID();
			id.setLocalName("receptor");

			ACLMessage msn = new ACLMessage(ACLMessage.REQUEST);
			msn.setSender(getAID()); //Como sender, nos ponemos nosotros
			msn.addReceiver(id); //Como recibidor hemos puesto el id del agente receptor en la gui de jade
			msn.setContent(str((int)Math.random()*100)); //Enviamos el random
			block(500); //Me bloqueo medio segundo
		}

		public boolean done(){
			return fin;
		}
	}
}
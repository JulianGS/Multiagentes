package examples.examen2016;

import jade.core.*;
import jade.lang.*;

public class EmisorYulius extends Agent{

	private class Sender1 extends Behaviour{

		private boolean fin = false;
		ACLMessage msj = new ACLMessage(ACLMessage.INFORM);
		int n = (int)(Math.random()*10000);

		System.out.println(myAgent.getName()" envía: "+n);

		msj.addReceiver(new AID("agente2",AID.ISLOCALNAME));
		msj.setSender(myAgent.getAID());
	}
}
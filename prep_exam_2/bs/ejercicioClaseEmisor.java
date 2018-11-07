package examples.comunicacion;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.lang.acl.*;
import jade.*;


public class ejercicioClaseEmisor extends Agent{

	protected void setup(){
		addBehaviour(new Emisor());
	}
	protected void takeDown(){
		System.out.println("EMISOR: Liberando recursos");
	}
	private class Emisor extends CyclicBehaviour{

		private boolean fin;
		String receptorname;
		int n;

		public void onStart(){
			fin = false;
			receptorname = "receptor";
			n = 1000;
		}		
		public void action(){
			System.out.println("EMISOR: " + getLocalName() +": Envia mensaje a " + receptorname);
			AID id = new AID();
			id.setLocalName(receptorname);

			MessageTemplate par = new MessageTemplate(MessageTemplate.MatchOntology("par"));
			MessageTemplate impar = new MessageTemplate(MessageTemplate.MatchOntology("impar"));


			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

			mensaje.setSender(getAID());
			mensaje.setLanguage("Spanish");
			mensaje.addReceiver(id);
			int numero = (int)(Math.random()*n);
			mensaje.setContent(Integer.toString(numero));

			send(mensaje);

			System.out.println("EMISOR: A la espera de recibir");
			ACLMessage mensaje_r = blockingReceive();
			System.out.println("EMISOR: Mensaje recibido: " + mensaje_r.toString()); 
			n = Integer.parseInt(mensaje_r.getContent());
		}
	}
}


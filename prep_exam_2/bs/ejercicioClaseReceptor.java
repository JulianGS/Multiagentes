package examples.comunicacion;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ejercicioClaseReceptor extends Agent{
	protected void setup(){
		addBehaviour(new Receptor());
	}
	protected void takeDown(){
		System.out.println("Liberando recursos");
	}
	private class Receptor extends CyclicBehaviour{
		private boolean fin;
		private String receptorname;
		private int n_recibido;
		public void onStart(){
			fin = false;
			receptorname = "emisor";
		}
		public void action(){
			System.out.println("RECEPTOR: Preparandose para recibir");
			ACLMessage mensaje = blockingReceive();
			if(mensaje != null){
				System.out.println("RECEPTOR: " + getLocalName() + ": recibe: " + mensaje.toString());
				n_recibido = Integer.parseInt(mensaje.getContent());
			}

			AID id = new AID();
			id.setLocalName(receptorname);
			
			ACLMessage mensaje_e = new ACLMessage(ACLMessage.REQUEST);

			mensaje_e.setSender(getAID());
			mensaje_e.setLanguage("Spanish");
			mensaje_e.addReceiver(id);
			int numero = (int)(Math.random()*n_recibido);
			mensaje_e.setContent(Integer.toString(numero));

			send(mensaje_e);
		}
	}
}


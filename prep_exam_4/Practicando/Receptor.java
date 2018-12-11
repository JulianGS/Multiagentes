package examples.prep_exam_4.Practicando;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.*;

public class Receptor extends Agent{

	//ThreadedBehaviourFactory tbf;
	ArrayList <Comportamiento> comps = new ArrayList <Comportamiento>();

	protected void setup(){
		
		MessageTemplate pares1 = and(MessageTemplate.MatchSender(new AID("emisor1",AID.ISLOCALNAME)),MessageTemplate.MatchLanguage("pares"));
		MessageTemplate impares1 = and(MessageTemplate.MatchSender(new AID("emisor1",AID.ISLOCALNAME)),MessageTemplate.MatchLanguage("impares"));
		MessageTemplate pares2 = and(MessageTemplate.MatchSender(new AID("emisor2",AID.ISLOCALNAME)),MessageTemplate.MatchLanguage("pares"));
		MessageTemplate impares2 = and(MessageTemplate.MatchSender(new AID("emisor2",AID.ISLOCALNAME)),MessageTemplate.MatchLanguage("impares"));

		comps.add(new Comportamiento("recb1",pares1));
		comps.add(new Comportamiento("recb2",impares1));
		comps.add(new Comportamiento("recb3",pares2));
		comps.add(new Comportamiento("recb4",impares2));

		for(int i = 0; i<comps.size(); i++){
			addBehaviour(comps.get(i));
		}

	}

	protected void takeDown(){
		for(int j = 0; j<comps.size(); j++){
			ACLMessage msn = new ACLMessage(ACLMessage.CANCEL);
			msn.addReceiver(new AID(comps.get(i).getName(),AID.ISLOCALNAME));
			send(msn);
		}
	}

	private class Comportamiento extends Behaviour{

		private String nombre;
		private MessageTemplate mt;
		private boolean fin = false;

		ArrayList <Integer> recb = new ArrayList <Integer>();
		int contador=0;

		public Comportamiento(String nombre, MessageTemplate mt){
			this.nombre = nombre;
			this.mt = mt;
		}

		public void action(){
			ACLMessage msj = myAgent.receive(mt);
			if(msj != null){
				int contenido = Integer.parseInt(msj.getContent());
			 	System.out.println("<Receptor>"+nombre+", recibido:"+contenido);
        		recb.add(contenido);

        		if(contenido >= 60){
        			contador++;
        		}
			}
			if(contador == 3){
				fin = true;
			}
		}

		public String getName(){
			return this.nombre;
		}
		public boolean done(){
			return fin;
		}

		public int onEnd(){
			myAgent.doDelete();
			return 1;
		}
	}
}
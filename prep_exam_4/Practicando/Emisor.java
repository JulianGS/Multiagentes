package examples.prep_exam_4.Practicando;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.*;

public class Emisor extends Agent{

	protected void setup(){
		addBehaviour(new Comportamiento());
	}

	protected void onEnd(){
		System.out.prinln("Acabado");
	}

	private class Comportamiento extends Behaviour{

		private boolean fin = false;

		public void action(){
			ACLMessage msj = new ACLMessage(ACLMessage.REQUEST);
			int n = (int)(Math.random()*100);
			System.out.println(myAgent.getName()+" enviará"+ n);

			//Partes del mensaje ACL
			msj.addReceiver(new AID("receptor", AID.ISLOCALNAME));
			msj.setSender(myAgent.getAID());
			msj.setContent(str(n));

			if(n % 2 == 0){
				msj.setLanguage("par");
			}else{
				msj.setLanguage("impar");
			}

			//Enviarlo
			myAgent.send(msj);

			if(msj != null){
				fin = true;
			}
		}

		public boolean done(){
			return fin;
		}

	}
}
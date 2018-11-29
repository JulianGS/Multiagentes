package comunicacion.ejemploex;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class Emisor extends Agent {
    Random rand = new Random();
    Send send;
    Receive receive;

    protected void setup() {
        send = new Send();
        receive = new Receive();

        addBehaviour(send);
        //addBehaviour(receive);
    }

    private class Send extends Behaviour {
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            int val = rand.nextInt(101);
            msg.setLanguage(val % 2 == 0 ? "even" : "odd");
            msg.setSender(getAID());

            AID aid = new AID();
            aid.setLocalName("Receptor");
            msg.addReceiver(aid);
            msg.setContent(Integer.toString(val));

            send(msg);
            System.out.format("Soy el %s y he enviado %d\n", myAgent.getName(), val);
            block(500);

            ACLMessage msgdown = myAgent.receive();

            if (msgdown != null && msgdown.getContent().equalsIgnoreCase("takedown"))
                myAgent.doDelete();
        }

        public boolean done() {
            return false;
        }
    }

    private class Receive extends Behaviour {
        public void action() {
            ACLMessage msg = myAgent.blockingReceive();

            if (msg.getContent().equalsIgnoreCase("takedown"))
                myAgent.doDelete();
        }

        public boolean done() {
            return false;
        }
    }
}

package comunicacion.ejemploex;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import jade.tools.sniffer.Message;

import java.util.HashSet;
import java.util.Set;

public class Receptor extends Agent {
    private MessageTemplate templateAg1;
    private MessageTemplate templateAg2;
    private MessageTemplate templateOdd;
    private MessageTemplate templateEven;

    public HashSet<jade.core.AID> agents = new HashSet<>();

    private Comprobar evenAg1;
    private Comprobar oddAg1;
    private Comprobar evenAg2;
    private Comprobar oddAg2;


    protected void setup() {
        templateAg1 = MessageTemplate.and(MessageTemplate.MatchSender(new AID("Emisor1", AID.ISLOCALNAME)),
            MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        templateAg2 = MessageTemplate.and(MessageTemplate.MatchSender(new AID("Emisor2", AID.ISLOCALNAME)),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        templateEven = MessageTemplate.and(MessageTemplate.MatchLanguage("even"),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        templateOdd = MessageTemplate.and(MessageTemplate.MatchLanguage("odd"),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));

        evenAg1 = new Comprobar(MessageTemplate.and(templateAg1, templateEven));
        evenAg1.setBehaviourName("evenAg1");

        oddAg1 = new Comprobar(MessageTemplate.and(templateAg1, templateOdd));
        oddAg1.setBehaviourName("oddAg1");

        evenAg2 = new Comprobar(MessageTemplate.and(templateAg2, templateEven));
        evenAg2.setBehaviourName("evenAg2");

        oddAg2 = new Comprobar(MessageTemplate.and(templateAg2, templateOdd));
        oddAg2.setBehaviourName("oddAg2");

        addBehaviour(evenAg1);
        addBehaviour(oddAg1);
        addBehaviour(evenAg2);
        addBehaviour(oddAg2);
    }

    private class Comprobar extends Behaviour {
        MessageTemplate template;
        int consecutive = 0;
        public Comprobar(MessageTemplate mt) {
            template = mt;
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.blockingReceive(template);
            System.out.format("Soy el Receptor en el comportamiento %s y he recibido %s con idioma %s\n", getBehaviourName(), msg.getContent(), msg.getLanguage());
            int num = Integer.parseInt(msg.getContent());
            if (num <= 60) {
                consecutive = 0;
            } else {
                consecutive++;
            }
            agents.add(msg.getSender());
        }

        public boolean done() {
            if (consecutive >= 3) {
                myAgent.doDelete();
            }
            return false;
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("takedown");

        for (jade.core.AID ag : agents) {
            msg.addReceiver(ag);
        }

        send(msg);
    }


}

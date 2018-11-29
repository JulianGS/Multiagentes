package comuni;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class AgentSend extends Agent {
    private Behaviour sender;
    private Recibir receiver;
    private ReceiveTakedown recTak;

    @Override
    protected void setup() {
        Object[] args = getArguments();

        if (args.length != 0) {
            String destStr = (String) args[0];

            AID dest = new AID(destStr, AID.ISLOCALNAME);

            receiver = new Recibir(dest);

            if (args.length > 1) {
                sender = new SendBeh(dest);
                addBehaviour(sender);
            }
        }

        recTak = new ReceiveTakedown();
        addBehaviour(receiver);
        addBehaviour(recTak);
    }

    private class SendBeh extends OneShotBehaviour {
        private AID dest;
        private Random rand = new Random();

        public SendBeh(AID destination) {
            this.dest = destination;
        }

        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            int val = rand.nextInt(100000);
            msg.setContent(Integer.toString(val));
            msg.addReceiver(this.dest);

            myAgent.send(msg);
            System.out.format("Empiezo enviando a %s el numero %d desde %s\n", dest.getLocalName(), val, myAgent.getLocalName());
        }
    }

    private class ReceiveTakedown extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);

            ACLMessage msg = receive(template);

            if (msg != null && msg.getContent().equals("takedown")) {
                System.out.format("Soy el %s y voy a salir\n", getLocalName());
                doDelete();
            }
            block();
        }
    }

    @Override
    protected void takeDown() {
        ACLMessage takedownmsg = new ACLMessage(ACLMessage.CANCEL);
        takedownmsg.addReceiver(receiver.dest);
        takedownmsg.addReceiver(receiver.agReceived);
        takedownmsg.setContent("takedown");

        send(takedownmsg);

        System.out.format("--------------SALIENDO %s--------------\n", getLocalName());
    }
}

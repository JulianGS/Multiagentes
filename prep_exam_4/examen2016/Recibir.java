package examples.examen2016;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Recibir extends Behaviour {
    public AID dest;
    public AID agReceived;
    public int newNumber = 1000000000;
    public MessageTemplate template;

    public Recibir(AID destination) {
        this.dest = destination;
        template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }

    public void action() {
        System.out.format("Soy el %s y estoy esperando a recibir\n", myAgent.getLocalName());
        ACLMessage msg = myAgent.receive(template);
        if (msg == null) {
            block();
        } else {

            int number = Integer.parseInt(msg.getContent());

            if (agReceived == null)
                agReceived = msg.getSender();

            newNumber = number / 2;

            if (number > 0) {
                ACLMessage msgSend = new ACLMessage(ACLMessage.INFORM);
                msgSend.addReceiver(dest);
                msgSend.setContent(Integer.toString(newNumber));
                myAgent.send(msgSend);
                System.out.format("Soy %s y he enviado a %s el numero %d\n", myAgent.getLocalName(), dest.getLocalName(), newNumber);
            } else {
                System.out.format("Soy %s y he recibido el numero %d y voy a salir\n", myAgent.getLocalName(), number);
            }
        }
    }

    public boolean done() {
        if (newNumber <= 0)
            System.out.println(myAgent.getLocalName() + " saliendo");
        return newNumber <= 0;
    }

    public int onEnd() {
        myAgent.doDelete();
        return 0;
    }
}

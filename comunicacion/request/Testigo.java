package examples.practica5;
 
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;
 
 
public class Testigo extends Agent {
 
    protected void setup()
    {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            System.out.println("He visto un accidente! Solicitando ayuda a varios hospitales...");
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            for (int i = 0; i < args.length; ++i)
                msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            msg.setContent("accidente a 5 kms");
 
            addBehaviour(new ManejadorInitiator(this,msg));
 
        }
        else System.out.println("Debe de esribir el nombre de un hospital como minimo (pasado como parametro).");
    }
 
    class ManejadorInitiator extends AchieveREInitiator
    {
        public ManejadorInitiator(Agent a,ACLMessage msg) {
            super(a,msg);
        }
 
        protected void handleAgree(ACLMessage agree)
        {
            System.out.println("Hospital " + agree.getSender().getName()
                    + " informa que han salido a atender el accidente.");
        }
 
        protected void handleRefuse(ACLMessage refuse)
        {
            System.out.println("Hospital " + refuse.getSender().getName()
                    + " responde que el accidente esta fuera de su radio de accion. No llegaremos a tiempo!!!");
        }
 
        protected void handleNotUnderstood(ACLMessage notUnderstood)
        {
            System.out.println("Hospital " + notUnderstood.getSender().getName()
                    + " no puede entender el mensaje.");
        }
 
        protected void handleInform(ACLMessage inform)
        {
            System.out.println("Hospital " + inform.getSender().getName()
                    + " informa que han atendido el accidente.");
        }
 
        protected void handleFailure(ACLMessage fallo)
        {
            if (fallo.getSender().equals(myAgent.getAMS())) {
                System.out.println("Alguna de los hospitales no existe");
            }
            else
            {
                System.out.println("Fallo en el hospital " + fallo.getSender().getName()
                        + ": " + fallo.getContent().substring(1, fallo.getContent().length()-1));
            }
        }
    }
 
}

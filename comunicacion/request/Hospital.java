package examples.practica5;
 
import java.util.StringTokenizer;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
 
 
public class Hospital extends Agent {
 
    public double DISTANCIA_MAX;
 
    protected void setup()
    {
        DISTANCIA_MAX=(Math.random()*10);
        System.out.println("Hospital "+getLocalName()+": Esperando avisos...");
        MessageTemplate protocolo = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        MessageTemplate performativa = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        MessageTemplate plantilla = MessageTemplate.and(protocolo, performativa);
 
        addBehaviour(new ManejadorResponder(this, plantilla));
    }
 
    class ManejadorResponder extends AchieveREResponder
    {
        public ManejadorResponder(Agent a,MessageTemplate mt) {
            super(a,mt);
        }
 
        protected ACLMessage handleRequest(ACLMessage request)throws NotUnderstoodException, RefuseException
        {
            System.out.println("Hospital "+getLocalName()+": Hemos recibido una llamada de " + request.getSender().getName() + " diciendo que ha visto un accidente.");
            StringTokenizer st=new StringTokenizer(request.getContent());
            String contenido=st.nextToken();
            if(contenido.equalsIgnoreCase("accidente"))
            {
                st.nextToken();
                int distancia=Integer.parseInt(st.nextToken());
                if (distancia<DISTANCIA_MAX)
                {
                    System.out.println("Hospital "+getLocalName()+": Vamos echando chispas!!!");
                    ACLMessage agree = request.createReply();
                    agree.setPerformative(ACLMessage.AGREE);
                    return agree;
                }
                else
                {
                    System.out.println("Hospital "+getLocalName()+": Accidente fuera de nuestro radio de accion. No llegaremos a tiempo!!!");
                    throw new RefuseException("Accidente demasiado lejos");
                }
            }
            else throw new NotUnderstoodException("Hospital manda un mensaje que no puedo entender.");
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request,ACLMessage response) throws FailureException
        {
            if (Math.random() > 0.2) {
                System.out.println("Hospital "+getLocalName()+": Han vuelto de atender el accidente.");
                ACLMessage inform = request.createReply();
                inform.setPerformative(ACLMessage.INFORM);
                return inform;
            }
            else
            {
                System.out.println("Hospital "+getLocalName()+": Han hecho todo lo posible, lo sentimos.");
                throw new FailureException("Han hecho todo lo posible");
            }
        }
    }
}

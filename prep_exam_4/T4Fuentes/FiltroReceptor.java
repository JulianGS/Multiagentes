
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
 
public class FiltroReceptor extends Agent
{
      class Comportamiento extends SimpleBehaviour
      {
          boolean fin = false;
          MessageTemplate plantilla = null;
          public Comportamiento ()
          {
            AID emisor = new AID();
            emisor.setLocalName("emisor");
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con un slot :sender dado.
            MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con una perfomativa dada.
            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
 
          //Devuelve una plantilla de mensaje que conicida con algún mensaje con una slot :language dado
            MessageTemplate filtroIdioma = MessageTemplate.MatchLanguage("Español");
 
          //Operación lógica AND entre dos objetos de esta clase.
            plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
            plantilla = MessageTemplate.and(plantilla,filtroIdioma);
          }
 
          public void action()
          {
            ACLMessage mensaje = receive(plantilla);
 
            if (mensaje!= null)
            {
                System.out.println(getLocalName() + ": ha recibido el siguiente mensaje: ");
                System.out.println(mensaje.toString());
                fin = true;
            }else
            {
                System.out.println(getLocalName() + ":Esperando mensajes...");
                block();
            }
        }
 
        public boolean done()
        {
            return fin;
        }
    }
 
    protected void setup()
    {
        addBehaviour(new Comportamiento());
    }
}

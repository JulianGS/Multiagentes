package examples.comunicacion.DF;
 
import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
 
public class Ofrece extends Agent
{
    protected void setup()
    {
    // Descripción del agente
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());
        descripcion.addLanguages("Castellano");
 
    // Descripcion de un servicio se proporciona
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Tipo del servicio 1");
        servicio.setName("Nombre del servicio 1");
 
    // Añade dicho servicio a la lista de servicios de la descripción del agente
        descripcion.addServices(servicio);
 
    // Descripcion de otro servicio se proporciona
        servicio = new ServiceDescription();
        servicio.setType("Tipo del servicio 2");
        servicio.setName("Nombre del servicio 2");
 
    // Añade dicho servicio a la lista de servicios de la descripción del agente
        descripcion.addServices(servicio);
 
        try
        {
            DFService.register(this, descripcion);
        }
        catch (FIPAException e)
        {
            e.printStackTrace();
        }
    }
 
    protected void takeDown()
    {
        try
        {
            DFService.deregister(this);
        }
        catch (FIPAException fe)
        {
            fe.printStackTrace();
        }
        System.out.println("El agente "+getAID().getName()+" ya no ofrece sus servicios.");
    }
}

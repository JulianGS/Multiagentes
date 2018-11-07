package examples.comunicacion.DF;
 
import jade.core.Agent;
import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
 
public class PaginasBlancas extends Agent
{
    protected void setup()
    {
        AMSAgentDescription [] agentes = null;
        System.out.println("El agente " + getAID().getName() + " se ha iniciado.");
 
        try
        {
            SearchConstraints restricciones = new SearchConstraints();
            restricciones.setMaxResults ( new Long(-1) ); /// Todos
            agentes = AMSService.search( this, new AMSAgentDescription (), restricciones );
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
 
        AID miId = getAID();
 
        System.out.println();
        System.out.println("Lista de agentes activos:");
        for (int i=0; i < agentes.length; i++)
        {
            AID id = agentes[i].getName();
            System.out.println(( id.equals( miId ) ? "--> " : "    " ) + "Agente" + i + ": " + id.getName() );
        }
    }
}

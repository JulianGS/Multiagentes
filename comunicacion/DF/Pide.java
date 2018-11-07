package examples.comunicacion.DF;
 
import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import java.util.Iterator;
 
public class Pide extends Agent
{
    protected void setup()
    {
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Tipo del servicio 1");
 
    // Plantilla de descripción que busca el agente
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.addLanguages("Castellano");
 
    // Servicio que busca el agente
        descripcion.addServices(servicio);
        try
        {
        // Todas las descripciones que encajan con la plantilla proporcionada en el DF
            DFAgentDescription[] resultados = DFService.search(this,descripcion); //Busca una descripción y la mete en resultado
 
            if (resultados.length == 0)
                System.out.println("Ningun agente ofrece el servicio deseado");
 
            for (int i = 0; i < resultados.length; ++i)
            {
                System.out.println("El agente "+resultados[i].getName()+" ofrece los siguientes servicios:");
                Iterator servicios = resultados[i].getAllServices();
                int j = 1;
                while(servicios.hasNext())
                {
                    servicio = (ServiceDescription)servicios.next();
                    System.out.println(j+"- "+servicio.getName());
                    System.out.println();
                    j++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

package c;

import jade.core.*;
import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

public class Receptor extends Agent{
  private ArrayList<CompRecibir> comps = new ArrayList<CompRecibir>();
  private String [] emisores={"Emisor1","Emisor2"};

  private class CompRecibir extends Behaviour{
    private ArrayList<Integer> lista = new ArrayList<Integer>();
    private int n=0;
    private MessageTemplate mt;
    private String nombre;
    private boolean done=false;

    public CompRecibir(String nombre, MessageTemplate mt){
      this.nombre=nombre;
      this.mt=mt;
    }
    public void action(){
      ACLMessage msg = myAgent.receive(mt);
      if(msg!=null){

        int cont=Integer.parseInt(msg.getContent());
        System.out.println("<Receptor>"+nombre+", recibido:"+cont);
        lista.add(cont);
        if(cont>60){
          n++;
        }else{
          n=0;
        }

        if(n>=3){
          done=true;
        }
        block();
      }else{
        block();
      }
    }
    public boolean done(){

      return done;
    }
    public int onEnd(){
      myAgent.doDelete();
      return 0;
    }
    public ArrayList<Integer> getLista(){
      return lista;
    }
    public String getNombre(){
      return nombre;
    }
  }
  private ThreadedBehaviourFactory t;
  protected void setup(){
    MessageTemplate p1 = MessageTemplate.and(MessageTemplate.MatchLanguage("p"),MessageTemplate.MatchSender(new AID(emisores[0],AID.ISLOCALNAME)));
    MessageTemplate i1 = MessageTemplate.and(MessageTemplate.MatchLanguage("i"),MessageTemplate.MatchSender(new AID(emisores[0],AID.ISLOCALNAME)));
    MessageTemplate p2 = MessageTemplate.and(MessageTemplate.MatchLanguage("p"),MessageTemplate.MatchSender(new AID(emisores[1],AID.ISLOCALNAME)));
    MessageTemplate i2 = MessageTemplate.and(MessageTemplate.MatchLanguage("i"),MessageTemplate.MatchSender(new AID(emisores[1],AID.ISLOCALNAME)));

    comps.add(new CompRecibir("pares1",p1));
    comps.add(new CompRecibir("impares1",i1));
    comps.add(new CompRecibir("pares2",p2));
    comps.add(new CompRecibir("impares2",i2));

    //No es necesario usar threads pero los uso para practicar
    t=new ThreadedBehaviourFactory();

    for(CompRecibir c:comps){
      addBehaviour(t.wrap(c));
    }
  }
  protected void takeDown(){
    t.interrupt();
    for(CompRecibir c:comps){
      System.out.println(c.getNombre()+": "+c.getLista());
    }
    //Mata los emisores
    for(String r:emisores){
      //He usado la performativa cancel pero podria haber usado algun String
      //para el lenguaje o algo asi
      ACLMessage msg=new ACLMessage(ACLMessage.CANCEL);
      msg.addReceiver(new AID(r,AID.ISLOCALNAME));
      send(msg);
    }
  }
}

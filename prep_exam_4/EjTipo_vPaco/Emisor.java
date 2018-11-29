package c;

import jade.core.*;
import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

public class Emisor extends Agent{
  private class Enviar extends CyclicBehaviour{
    public void action(){
      ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
      int numero=(int)(Math.random()*100);
      System.out.println(myAgent.getName()+", enviando: "+numero);
      msg.addReceiver(new AID("Receptor",AID.ISLOCALNAME));
      msg.setSender(myAgent.getAID());
      if(numero%2==0){
        msg.setLanguage("p");
      }else{
        msg.setLanguage("i");
      }
      msg.setContent(numero+"");
      myAgent.send(msg);
      block(500);
    }
  }

  private class Acabar extends Behaviour{
    private boolean acabar=false;
    public void action(){
      ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
      if(msg!=null){
        acabar=true;
      }else{
        block();
      }
    }
    public boolean done(){
      return acabar;
    }
  }
  protected void setup(){
    ParallelBehaviour p=new ParallelBehaviour(this,1){
      public int onEnd(){
        myAgent.doDelete();
        return 0;
      }
    };
    p.addSubBehaviour(new Enviar());
    p.addSubBehaviour(new Acabar());
    addBehaviour(p);
  }
  protected void takeDown(){
    System.out.println(getName()+", liberando recursos");
  }
}

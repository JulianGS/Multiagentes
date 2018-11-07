package examples.fuentescomposite;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.Behaviour;

/**
 * This is an example of recursive aggregation of composite agent behaviours.
 * A composite behaviour is created, composed of some Sequential Behaviours,
 * and OneShot behaviours.
 * @author Giovanni Rimassa - Universit� di Parma
 * @version  $Date: 2003/12/03 16:46:36 $ $Revision: 2.3 $
 */
public class ComplexBehaviourAgent extends Agent {
  class SingleStepBehaviour extends OneShotBehaviour {
    private String myStep;

    public SingleStepBehaviour(Agent a, String step) {
      super(a);
      myStep = step;
    }

    public void action() {
      System.out.println("Agent "+getName()+": Step "+myStep);
    } 

  }

  protected void setup() {
    SequentialBehaviour myBehaviour1 = new SequentialBehaviour(this) {
      public int onEnd() {
        reset();
        return super.onEnd();
      } 

    };
    SequentialBehaviour myBehaviour2 = new SequentialBehaviour(this);
    SequentialBehaviour myBehaviour2_1 = new SequentialBehaviour(this);
    SequentialBehaviour myBehaviour2_2 = new SequentialBehaviour(this);

    myBehaviour2_1.addSubBehaviour(new SingleStepBehaviour(this, "2.1.1"));
    myBehaviour2_1.addSubBehaviour(new SingleStepBehaviour(this, "2.1.2"));
    myBehaviour2_1.addSubBehaviour(new SingleStepBehaviour(this, "2.1.3"));
    myBehaviour2_2.addSubBehaviour(new SingleStepBehaviour(this, "2.2.1"));
    myBehaviour2_2.addSubBehaviour(new SingleStepBehaviour(this, "2.2.2"));

    Behaviour b = new SingleStepBehaviour(this, "2.2.3");

    myBehaviour2_2.addSubBehaviour(b);
    myBehaviour1.addSubBehaviour(new SingleStepBehaviour(this, "1.1")); 
    myBehaviour1.addSubBehaviour(new SingleStepBehaviour(this, "1.2")); 
    myBehaviour1.addSubBehaviour(new SingleStepBehaviour(this, "1.3"));
    myBehaviour2.addSubBehaviour(myBehaviour2_1);
    myBehaviour2.addSubBehaviour(myBehaviour2_2);
    myBehaviour2.addSubBehaviour(new SingleStepBehaviour(this, "2.3"));
    myBehaviour2.addSubBehaviour(new SingleStepBehaviour(this, "2.4"));
    myBehaviour2.addSubBehaviour(new SingleStepBehaviour(this, "2.5"));
    //Es decir yo he creado los comportamientos arriba, y ahora lo que hago es ejecutar el action del 1, y luego el action del 2
    //Por eso salen los resultados intercalados.
    addBehaviour(myBehaviour1);
    addBehaviour(myBehaviour2);
  } 
}

/*
MyBehaviour1 y MyBehaviour2 son secuenciales. Estos son sus hilos de ejecución.

Mb1: 1.1 -> 1.2 -> 1.3
Mb2: Mb2_1 -> Mb2_2 -> 2.3 -> 2.4 -> 2.5

¿Que es Mb2_1 y Mb2_2? Subrutinas que hacen:

Mb2_1: 2.1.1 -> 2.1.2 -> 2.1.3
Mb2_2: 2.2.1 -> 2.2.2 -> 2.2.3
*/

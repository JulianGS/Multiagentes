package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.util.concurrent.Semaphore;

public class EjTipo_vJuanjo extends Agent {
    ThreadedBehaviourFactory tbf;
    Semaphore m;

    class SimpleBehaviour extends Behaviour{
        int executions;
        Semaphore m;

        public SimpleBehaviour(int n, Semaphore m){
            this.executions = n;
            this.m = m;
        }

        public void action(){
            try {
                m.acquire();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("Soy " + getBehaviourName() + " y me quedan " + executions-- + " ejecuciones");
        }

        public boolean done(){
            m.release();
            return executions < 0;
        }
    }


    public void setup(){
        tbf = new ThreadedBehaviourFactory();
        m = new Semaphore(1);

        ParallelBehaviour b = new ParallelBehaviour(this, 2){
            public int onEnd(){
                myAgent.doDelete();
                return 0;
            }
        };

        ParallelBehaviour b1 = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ANY);
        SimpleBehaviour b1_1 = new SimpleBehaviour((int) (Math.random() * 101), m);
        SimpleBehaviour b1_2 = new SimpleBehaviour((int) (Math.random() * 101), m);
        SimpleBehaviour b1_3 = new SimpleBehaviour((int) (Math.random() * 101), m);

        b1_1.setBehaviourName("B1.1");
        b1_2.setBehaviourName("B1.2");
        b1_3.setBehaviourName("B1.3");

        b.addSubBehaviour(tbf.wrap(b1_1));
        b.addSubBehaviour(tbf.wrap(b1_2));
        b.addSubBehaviour(tbf.wrap(b1_3));


        addBehaviour(b);
    }

    public void takeDown(){
        System.out.println("Freeing Resources");
        tbf.interrupt();
    }
}
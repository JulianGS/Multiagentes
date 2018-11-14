package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;

import java.util.Random;

public class EjTipo_vJavier extends Agent {
    private ThreadedBehaviourFactory tbf;
    private Random rand;
    private ParallelBehaviour par;

    private class RandomDecrement extends Behaviour {
        private int val;

        public RandomDecrement(int val) {
            this.val = val;
        }

        synchronized public void action() {
            try {
				val--;
				System.out.format("Soy el comportamiento %s y mi valor es %d\n", getBehaviourName(), val);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public boolean done() {
            return val == 0;
        }
    }

    protected void setup() {
        tbf = new ThreadedBehaviourFactory();
        rand = new Random();
        par = new ParallelBehaviour(2) {
            public int onEnd() {
                myAgent.doDelete();
                return 1;
            }
        };
        for (int i = 0; i < 3; i++) {
            RandomDecrement beh = new RandomDecrement(rand.nextInt(101));
            beh.setBehaviourName(String.format("Decremento%d", i + 1));
            par.addSubBehaviour(tbf.wrap(beh));
        }

        addBehaviour(par);
    }

    public void takeDown() {
        tbf.interrupt();
        System.out.println("Agente cerrandose");
    }


}
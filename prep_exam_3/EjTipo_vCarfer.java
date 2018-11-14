package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.core.behaviours.WakerBehaviour;

public class EjTipo_vCarfer extends Agent {

	ThreadedBehaviourFactory tbf;
  MyCycle cyc1, cyc2, cyc3;

  protected void setup() {
    ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
    tbf = new ThreadedBehaviourFactory();
    cyc1 = new MyCycle("Comp 1",1,true);
    cyc2 = new MyCycle("Comp 2",2,false);
    cyc3 = new MyCycle("Comp 3",3,false);

    pb.addSubBehaviour(tbf.wrap(cyc1));
    pb.addSubBehaviour(tbf.wrap(cyc2));
    pb.addSubBehaviour(tbf.wrap(cyc3));
    addBehaviour(pb);
  }

  private class MyCycle extends Behaviour {
		private int ID;
		private String name;
		private boolean inicial;

		private int value;

		public MyCycle(String name, int ID,boolean inicial) {
			this.ID = ID;
			this.name = name;
			this.inicial = inicial;
			this.value=0;
		}

    public void onStart(){
      if(!this.inicial){
        tbf.suspend(this);
      }
    }

		public void action() {
			value++;
			System.out.println(name + ": Ejecución: " + value);
      switch(this.ID){
        case 1:
          tbf.resume(cyc2);
          break;
        case 2:
          tbf.resume(cyc3);
          break;
        case 3:
          tbf.resume(cyc1);
          break;
        default:
          System.out.println("Error en: "+this.name);
      }
      tbf.suspend(this);
		}

		public boolean done() {
			return value>20;
		}

	}

}

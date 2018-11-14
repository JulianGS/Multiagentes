package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.core.behaviours.WakerBehaviour;

public class EjTipo_vSuspendResume extends Agent {

	ThreadedBehaviourFactory tbf;
  MyCycle cyc1, cyc2, cyc3;

  protected void setup() {
    ParallelBehaviour pb = new ParallelBehaviour(2);
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
    private int maxValue;
		private int value;

    private MyCycle brother1;

		public MyCycle(String name, int ID,boolean inicial) {
			this.ID = ID;
			this.name = name;
			this.inicial = inicial;
			this.value=0;
      this.maxValue = (int)(Math.random()*100);
		}

    public void onStart(){
      switch(this.ID){
        case 1:
          this.brother1 = cyc2;
          break;
        case 2:
          this.brother1 = cyc3;
          break;
        case 3:
          this.brother1 = cyc1;
          break;
        default:
          System.out.println("Error en: "+this.name);
      }
      if(!this.inicial){
        tbf.suspend(this);
      }
    }

		public void action() {
			value++;
			System.out.println(name + ": Ejecución: " + value + " : El Valor max es: "+maxValue);
      tbf.resume(this.brother1);

      tbf.suspend(this);
		}

		public boolean done() {
			return value>maxValue;
		}

    public int onEnd(){
      brother1.getBrother().setBrother(this.brother1);
      return super.onEnd();
    }

    public void setBrother(MyCycle b1){
      this.brother1 = b1;
    }

    public MyCycle getBrother(){
      return this.brother1;
    }

	}

}

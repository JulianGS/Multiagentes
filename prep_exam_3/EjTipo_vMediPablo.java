package examples.prep_exam_3;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.NotFoundException;

public class EjTipo_vMediPablo extends Agent{
	private Behaviour comp1, comp2, comp3;
	private ThreadedBehaviourFactory tbf;
	private ParallelBehaviour pb;

	protected void setup(){
		tbf = new ThreadedBehaviourFactory();
		pb = new ParallelBehaviour(this, 2){
			public int onEnd(){
				tbf.interrupt();
				doDelete();
				return super.onEnd();
			}
		};

		comp1 = new Comp(1,((int)(Math.random()*100)));
		comp2 = new Comp(2,((int)(Math.random()*100)));
		comp3 = new Comp(3,((int)(Math.random()*100)));
			
		pb.addSubBehaviour(tbf.wrap(comp1));
		pb.addSubBehaviour(tbf.wrap(comp2));
		pb.addSubBehaviour(tbf.wrap(comp3));

		addBehaviour(pb);	
	}
	protected void takeDown(){

		super.takeDown();
		System.out.println("Liberando recursos...");
	}
	private class Comp extends Behaviour{
		private int id;
		private int it;
		private Behaviour c1, c2;
		private boolean fin;
		
		public Comp(int id, int it){
			this.id = id;
			this.it = it;
		}		

		public void onStart(){
			fin = false;
			if(this.id == 1){
				c1 = comp2;
				c2 = comp3;
			}else if(this.id == 2){
				c1 = comp1;
				c2 = comp3;
			}else{
				c1 = comp2;
				c2 = comp1;
			}
		
		}
		public void action(){
			try{
				this.c1.block();
				this.c2.block();
				System.out.println("Soy Comp" + this.id + " Y estoy en " + this.it--);
				this.c1.restart();
				this.c2.restart();
			}catch(Exception e){
				fin = true;
			}		
		}
		public boolean done(){
			return it < 0 || fin;
		}
	}
}

import java.util.Date;

import jade.core.*;
import jade.core.behaviours.WakerBehaviour;


public class WakerBehaviourAgent extends Agent {
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		Date d = new Date();
		d.setSeconds(d.getSeconds() + 10);
		this.addBehaviour(new WakerBehaviourImpl(this, d) );
	}
	
	private class WakerBehaviourImpl extends WakerBehaviour {
		private static final long serialVersionUID = 1L;

		public WakerBehaviourImpl(Agent a, Date wakeupDate) {
			super(a, wakeupDate);
		}
		
		protected void onWake() {
			System.out.println("WOW I'm late");
		}
		
	} }

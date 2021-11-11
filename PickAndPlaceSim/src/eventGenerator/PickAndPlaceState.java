package eventGenerator;

import org.w3c.dom.events.Event;

import eventGenerator.PickAndPlaceEvents.PickAndPlaceEvent;

public class PickAndPlaceState
{
	
	public enum CellActivity {
		IDLEA,
		IDLEB,
		ATOB,
		BTOA
	}
	
	public enum FixtureState {
		HAS_PART,
		EMPTY
	}
	
	public enum RobotState {
		MOVING,
		NOT_MOVING
	}
		
	
	public CellActivity thisCellActivity = CellActivity.IDLEA;
	public FixtureState thisFixtureAState = FixtureState.HAS_PART;
	public FixtureState thisFixtureBState = FixtureState.EMPTY;
	public RobotState thisRobotState = RobotState.MOVING;
	
	public PickAndPlaceState() {
		
	}
	
	public void consumeEvent (PickAndPlaceEvent event) {
		
		switch (event) {
		
			case START:
				consumeStartEvent();
				break;
			case STOP:
				consumeStopEvent();
				break;
			case CONT:
				consumeContEvent();
				break;
			default:
				System.out.println("Invalid event consumed!");
				break;
		}		
	}
	
	private void consumeStartEvent() {
		
		switch (thisCellActivity) {
		
			case ATOB:
				//Invalid event. Do nothing
				break;
			case BTOA:
				//invalid event. Do nothing.
				break;
			case IDLEA:
				this.thisCellActivity = CellActivity.ATOB;
				this.thisFixtureAState = FixtureState.EMPTY;
				this.thisFixtureBState = FixtureState.EMPTY;
				this.thisRobotState = RobotState.MOVING;
				System.out.println("The part is being moved to fixture B!");
				break;
			case IDLEB:
				this.thisCellActivity = CellActivity.BTOA;
				this.thisFixtureAState = FixtureState.EMPTY;
				this.thisFixtureBState = FixtureState.EMPTY;
				this.thisRobotState = RobotState.MOVING;
				System.out.println("The part is being moved to fixture A!");
				break;
			default:
				System.out.println("Invalid state consuming START event");		
				break;
		}		
	}
	
	private void consumeStopEvent() {
		
		switch (thisCellActivity) {
		
			case ATOB:				
				this.thisCellActivity = CellActivity.IDLEA;	
				this.thisFixtureAState = FixtureState.EMPTY;
				this.thisFixtureBState = FixtureState.HAS_PART;
				this.thisRobotState = RobotState.NOT_MOVING;
				System.out.println("The part is in fixture B. Going Idle.");
				break;
			case BTOA:
				this.thisCellActivity = CellActivity.IDLEB;	
				this.thisFixtureAState = FixtureState.HAS_PART;
				this.thisFixtureBState = FixtureState.EMPTY;
				this.thisRobotState = RobotState.NOT_MOVING;
				System.out.println("The part is in fixture A. Going Idle.");
				break;
			case IDLEA:
				//Invalid event. Do nothing
				break;
			case IDLEB:
				//Invalid event. Do nothing
				break;
			default:
				System.out.println("Invalid state consuming STOP event");	
				break;		
		}		
	}
	
	private void consumeContEvent() {
		
		switch (thisCellActivity) {
			case ATOB:
				this.thisCellActivity = CellActivity.BTOA;
				this.thisFixtureAState = FixtureState.EMPTY;
				this.thisFixtureBState = FixtureState.EMPTY;
				this.thisRobotState = RobotState.MOVING;
				System.out.println("Immediately placing part back in fixture A.");
				break;
			case BTOA:
				this.thisCellActivity = CellActivity.ATOB;
				this.thisFixtureAState = FixtureState.EMPTY;
				this.thisFixtureBState = FixtureState.EMPTY;
				this.thisRobotState = RobotState.MOVING;
				System.out.println("Immediately placing part back in fixture B.");
				break;
			case IDLEA:
				//Invalid event. Do nothing.
				break;
			case IDLEB:
				//Invalid event. Do nothing
				break;
			default:
				System.out.println("Invalid state consuming CONT event");
				break;		
		}
		
		
	}

}

package Elastic.PickAndPlaceSimulator.EventGenerator;

import Elastic.PickAndPlaceSimulator.EventGenerator.PickAndPlaceEvents.PickAndPlaceEvent;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class PickAndPlaceState
{
	
	private static final String CELL_PROGRAM_VAR = "ExecutingProgram";
	private static final String ROBOT_MOVE_SPEED_VAR = "RobotMoveSpeed";
	private static final String CELL_STATUS_VAR = "CellStatus";
	

	public enum CellProgram {
		IDLEA {
			@Override
			public String getProgramName() {
				return "No_Program";
			}
		},
		
		IDLEB {
			@Override
			public String getProgramName() {
				return "No_Program";
			}
		},
		
		ATOB {
			@Override
			public String getProgramName() {
				return "Move_Part_to_B";
			}
		},
		
		BTOA {
			@Override
			public String getProgramName() {
				return "Move_Part_to_A";
			}
		};
		
		public abstract String getProgramName();
	}
	
	public enum FixtureState {
		HAS_PART,
		EMPTY
	}
	
	public enum RobotState {
		MOVING {
			@Override
			public String getRobotSpeed() {
				return "15.5";
			}
		},
		
		NOT_MOVING {
			@Override
			public String getRobotSpeed() {
				return "0";
			}
		};	
		
		public abstract String getRobotSpeed();
	}	
		
	public enum CellStatus {
		ACTIVE,
		IDLE
	}
	
	
	public CellProgram thisCellProgram = CellProgram.IDLEA;
	public FixtureState thisFixtureAState = FixtureState.HAS_PART;
	public FixtureState thisFixtureBState = FixtureState.EMPTY;
	public RobotState thisRobotState = RobotState.MOVING;	
	
	public String getLoc = "";
	
	
	public PickAndPlaceState(String getLocIn) {
		this.getLoc = getLocIn;
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
		
		switch (thisCellProgram) {
		
			case ATOB:
				//Invalid event. Do nothing
				break;
			case BTOA:
				//invalid event. Do nothing.
				break;
			case IDLEA:
				this.thisCellProgram = CellProgram.ATOB;		
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.ACTIVE.toString());
				
				this.thisFixtureAState = FixtureState.EMPTY;
				//TODO
				
				this.thisFixtureBState = FixtureState.EMPTY;
				//TODO
				
				this.thisRobotState = RobotState.MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
				System.out.println("The part is being moved to fixture B!");
				break;
				
			case IDLEB:
				this.thisCellProgram = CellProgram.BTOA;
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.ACTIVE.toString());
				
				this.thisFixtureAState = FixtureState.EMPTY;
				//TODO
				
				this.thisFixtureBState = FixtureState.EMPTY;
				//TODO
				
				this.thisRobotState = RobotState.MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
				System.out.println("The part is being moved to fixture A!");
				break;
				
			default:
				System.out.println("Invalid state consuming START event");		
				break;
		}		
	}
	
	private void consumeStopEvent() {
		
		switch (thisCellProgram) {
		
			case ATOB:				
				this.thisCellProgram = CellProgram.IDLEA;	
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.IDLE.toString());
				
				this.thisFixtureAState = FixtureState.EMPTY;
				//TODO
				
				this.thisFixtureBState = FixtureState.HAS_PART;
				//TODO
				
				this.thisRobotState = RobotState.NOT_MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
				System.out.println("The part is in fixture B. Going Idle.");
				break;
				
			case BTOA:
				this.thisCellProgram = CellProgram.IDLEB;	
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.IDLE.toString());
				
				this.thisFixtureAState = FixtureState.HAS_PART;
				//TODO
				
				this.thisFixtureBState = FixtureState.EMPTY;
				//TODO
				
				this.thisRobotState = RobotState.NOT_MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
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
		
		switch (thisCellProgram) {
			case ATOB:
				this.thisCellProgram = CellProgram.BTOA;
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.ACTIVE.toString());
				
				this.thisFixtureAState = FixtureState.EMPTY;
				//TODO
				
				this.thisFixtureBState = FixtureState.EMPTY;
				//TODO
				
				this.thisRobotState = RobotState.MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
				System.out.println("Immediately placing part back in fixture A.");
				break;
				
			case BTOA:
				this.thisCellProgram = CellProgram.ATOB;
				putCellUpdate(getLoc, CELL_PROGRAM_VAR, thisCellProgram.getProgramName());
				putCellUpdate(getLoc, CELL_STATUS_VAR, CellStatus.ACTIVE.toString());
				
				this.thisFixtureAState = FixtureState.EMPTY;
				//TODO
				
				this.thisFixtureBState = FixtureState.EMPTY;
				//TODO
				
				this.thisRobotState = RobotState.MOVING;
				putCellUpdate(getLoc, ROBOT_MOVE_SPEED_VAR, thisRobotState.getRobotSpeed());
				
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
	
	private void putCellUpdate(String getLoc, String varName, String newValue) {
		
		//Get Current Cell Status
		HttpRequest request = Unirest.get(getLoc + varName);
    	HttpResponse<JsonNode> jsonResponse = request.asJson();
    	JSONObject myObj = jsonResponse.getBody().getObject();
    	   	
    	//Edit required element
    	JSONObject cellStatusObj = myObj.getJSONObject("elem");
    	cellStatusObj.put("value", newValue);    	    	
    	String JSONString = cellStatusObj.toString();   
    	    	
    	//Put to sub model
    	jsonResponse = Unirest.put(getLoc)
    		.header("Content-Type", "application/json")
    		.body(JSONString)
    		.asJson();
    	
    	System.out.println(jsonResponse.getStatusText());		
		
	}

}

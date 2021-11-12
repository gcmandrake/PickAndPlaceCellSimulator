package Elastic.PickAndPlaceSimulator.EventGenerator;

public class PickAndPlaceEvents {
	
	//Must total 1!
	public final static double START_PROBABILITY = 0.1;
	public final static double STOP_PROBABILITY = 0.4;
	public final static double CONT_PROBABILTIY = 0.5;
	
	public enum PickAndPlaceEvent {
		START,
		STOP,
		CONT
	}
	
		
	public static PickAndPlaceEvent generateEvent() {
		
		double randomNumber = Math.random();
		
		if (randomNumber >= 0 && randomNumber <= START_PROBABILITY ) {
			return PickAndPlaceEvent.START;
		} else if (randomNumber > START_PROBABILITY && randomNumber <= (STOP_PROBABILITY + START_PROBABILITY)) {
			return PickAndPlaceEvent.STOP;
		} else {
			return PickAndPlaceEvent.CONT;
		}
		
		
	}

}

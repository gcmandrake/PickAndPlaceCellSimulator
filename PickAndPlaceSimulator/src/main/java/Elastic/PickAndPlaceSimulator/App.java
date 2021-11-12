package Elastic.PickAndPlaceSimulator;

import Elastic.PickAndPlaceSimulator.EventGenerator.PickAndPlaceEventGenerator;


public class App 
{
	
	private final static String BaseURL = "http://localhost:51310";
	private final static String locationURL = BaseURL + "/aas/FANUC_ER4iA_inc_Controller_1/submodels/operationalinformation/elements/";	
   
	public static void main( String[] args )
    {        
    	PickAndPlaceEventGenerator generator = new PickAndPlaceEventGenerator(locationURL);   
    	        
    }
}

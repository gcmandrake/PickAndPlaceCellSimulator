package eventGenerator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import eventGenerator.PickAndPlaceEvents.PickAndPlaceEvent;

public class PickAndPlaceEventGenerator {
	
	public PickAndPlaceEventGenerator() {
		
		BlockingQueue<PickAndPlaceEvent> eventQueue = new LinkedBlockingDeque<>(2);
		ExecutorService executor = Executors.newFixedThreadPool(2);		
		
		PickAndPlaceState systemState = new PickAndPlaceState();		
		
		//A thread that produces events		
		Runnable producerTask = () -> {
			
			try {
				
				//Always start with a START
				eventQueue.put(PickAndPlaceEvent.START);
				
				while (true) {						
						
					PickAndPlaceEvent event = PickAndPlaceEvents.generateEvent();
					eventQueue.put(event);
					//System.out.println("Added an event: " + event.toString() + ".");
											
					Thread.sleep(5000);
												
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		//A thread that consumes events
		Runnable consumerTask = () -> {
			
			try {
				while (true) {
					PickAndPlaceEvent event = eventQueue.take();					
					systemState.consumeEvent(event);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
					
				e.printStackTrace();			
			}
			
		};
		
		executor.execute(producerTask);
		executor.execute(consumerTask);
		executor.shutdown();
		
	}
	
	

}

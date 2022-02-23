package aed.airport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import es.upm.aedlib.positionlist.*;


public class Tests {
	
	@Test
	public void testChangeTime() {
		IncomingFlightsRegistry airport = new IncomingFlightsRegistry();
		airport.arrivesAt("avion",1050);
		airport.arrivesAt("avion",1200);
		
		assertEquals(1200, airport.arrivalTime("avion"));
		
	}
	@Test
	public void testLista() {
		IncomingFlightsRegistry airport = new IncomingFlightsRegistry();
		airport.arrivesAt("avion1",20);
		airport.arrivesAt("avion2",10);
		PositionList<FlightArrival> res = new NodePositionList<>();
		FlightArrival avion1 = new FlightArrival("avion1",20);
		FlightArrival avion2 = new FlightArrival("avion2",10);
		res.addLast(avion2);
		res.addLast(avion1);
		
		assertEquals(res, airport.arriving(0));

	}
}


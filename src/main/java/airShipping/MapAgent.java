package airShipping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.Instant;

import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.annotation.Service;
import jadex.commons.Boolean3;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.micro.annotation.Agent;

@Agent(autoprovide = Boolean3.TRUE)
@Service
public class MapAgent {

	@Agent
	private IInternalAccess agent;

	private Plane plane;
	private LocalAirport departAirport;
	private LocalAirport arrivalAirport;

	List<Plane> planeList = new ArrayList<>();
	HashMap<String, LocalAirport> airportList = new HashMap<String, LocalAirport>();

//	private long reversetimestamp;
//	private long timestamp;

	private IComponentStep<Void> simulationstep;

	@OnStart
	public IFuture<Void> agentRun() {
		departAirport = new LocalAirport((new Vector2Double(1.7, 7.9)), "A");
		arrivalAirport = new LocalAirport((new Vector2Double(13.8, 2.5)), "B");
		plane = new Plane("01", departAirport, arrivalAirport);

		planeList.add(plane);
		airportList.put("airport_A", departAirport);
		airportList.put("airport_B", arrivalAirport);

		System.out.println("Agent is running, a plane is created. \n");
		simulationstep = new IComponentStep<Void>() {
			public IFuture<Void> execute(IInternalAccess ia) {
//				long currentTimestamp = System.currentTimeMillis();
//				System.out.println("timestamp:" + timestamp );
//				System.out.println("reversetimestamp:" + reversetimestamp );
//				System.out.println("current time:" + currentTimestamp );
//				long reverseDelta = currentTimestamp - reversetimestamp;
//				System.out.println("reverseDelta:" + reverseDelta );
//				if(reverseDelta > 5000) {
//					car.reverse();
//					reversetimestamp = currentTimestamp;
//				}
//				double deltaseconds = (currentTimestamp - timestamp) / 1000.0; 

//				IVector2 startPos = plane.getCurrentPosition();
//				IVector2 newPos = plane.getNewPosition(startPos);
				Instant instant = Instant.now();

				boolean arrived = plane.getCurrentPosition().equals(arrivalAirport.getAirportCoordinate());
				if (!(plane == null) && !arrived) {
					System.out.println("Current UTC time: " + instant);
					if (!arrived) {
						plane.updatePos(1);
					}
				} else {
					return null;
				}
//				timestamp = currentTimestamp;
				ia.scheduleStep(simulationstep);
				return IFuture.DONE;
			}
		};
//		reversetimestamp = System.currentTimeMillis();
//		timestamp = System.currentTimeMillis();
		agent.scheduleStep(simulationstep);

		return new Future<Void>();
	}
}

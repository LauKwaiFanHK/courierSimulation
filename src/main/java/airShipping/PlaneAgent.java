package airShipping;

import jadex.bridge.IInternalAccess;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.bridge.service.ServiceScope;
import jadex.bridge.service.annotation.OnInit;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.search.ServiceQuery;
import jadex.micro.annotation.Agent;

@Agent
public class PlaneAgent {
	@Agent
	private IInternalAccess agent;

	private IMapService mapService;

//	Warehouse warehouseB = new Warehouse("Warehouse B", 20, 1);
//	Warehouse warehouseC = new Warehouse("Warehouse C", 20, 1);

//	private LocalAirport airportA = new LocalAirport();
//	private LocalAirport airportB = new LocalAirport();
//	private LocalAirport airportC = new LocalAirport();

	@OnInit
	public IFuture<Void> agentInit() {

//		airportA.setAirportCoordinate(new Vector2Double(1.7, 7.9));
//		airportB.setAirportCoordinate(new Vector2Double(13.8, 2.5));
//		airportC.setAirportCoordinate(new Vector2Double(5.2, 11.7));
//		airportA.setAirportName("A");
//		airportB.setAirportName("B");
//		airportC.setAirportName("C");

		ServiceQuery<IMapService> query = new ServiceQuery<IMapService>(IMapService.class);
		query.setScope(ServiceScope.PLATFORM);

		mapService = agent.getLocalService(query);

//		IFuture<Void> done = mapService.createPlane();

		System.out.println("Plane Agent found: " + mapService);

		return IFuture.DONE;
	}

	@OnStart
	public IFuture<Void> agentStart() {

//		IFuture<Void> arrived = mapService.setPlaneTarget(airportC);

//		arrived.then((done) -> {
//			System.out.println("Plane has arrived!");
//		});

		return new Future<Void>();
	}
}

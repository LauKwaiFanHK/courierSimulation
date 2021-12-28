package airShipping2;

import java.util.ArrayList;

import jadex.bridge.IInternalAccess;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.bridge.service.ServiceScope;
import jadex.bridge.service.annotation.OnInit;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.search.ServiceQuery;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentArgument;
import jadex.commons.Boolean3;

@Agent(autoprovide = Boolean3.TRUE)
@Service
public class PlaneAgent implements IPlaneService {
	@Agent
	private IInternalAccess agent;

	private IMapService mapService;

	private Airports airports = new Airports();

	private ArrayList<IVector2> normalRoute = new ArrayList<>();

	private ArrayList<IVector2> expressRoute = new ArrayList<>();

	@OnInit
	public IFuture<Void> agentInit() {
		// route B-C-D-A-B
		normalRoute.add(airports.getAirportC());
		normalRoute.add(airports.getAirportD());
		normalRoute.add(airports.getAirportA());
		normalRoute.add(airports.getAirportB());

		// route B-A-B
		expressRoute.add(airports.getAirportA());
		expressRoute.add(airports.getAirportB());

		ServiceQuery<IMapService> query = new ServiceQuery<IMapService>(IMapService.class);
		query.setScope(ServiceScope.PLATFORM);

		mapService = agent.getLocalService(query);

		IFuture<Void> done = mapService.createPlane("ABC", airports.getAirportB(), "XYZ", airports.getAirportB());

		System.out.println("Plane Agent found: " + mapService);

		return done;
	}

	// Good news, only one @OnStart method allowed in a package :)
	@OnStart
	public IFuture<Void> agentStart() {

		IResultListener<Void> rl = new IResultListener<Void>() {
			int i = 0;

			public void resultAvailable(Void done) {
				i = (i + 1) % normalRoute.size();
				IFuture<Void> arrived = mapService.setPlaneTarget("ABC", normalRoute.get(i));
				arrived.addResultListener(this);
				System.out.println("Plane has arrived at " + normalRoute.get(i - 1));
			}

			public void exceptionOccurred(Exception e) {
			}
		};

		IResultListener<Void> rl2 = new IResultListener<Void>() {
			int i = 0;

			public void resultAvailable(Void done) {
				i = (i + 1) % expressRoute.size();
				IFuture<Void> arrived = mapService.setPlaneTarget("XYZ", expressRoute.get(i));
				arrived.addResultListener(this);
				System.out.println("Plane has arrived at " + expressRoute.get(i - 1));
			}

			public void exceptionOccurred(Exception e) {
			}
		};

		IFuture<Void> arrived = mapService.setPlaneTarget("ABC", normalRoute.get(0));
		arrived.addResultListener(rl);

		IFuture<Void> arrived2 = mapService.setPlaneTarget("XYZ", expressRoute.get(0));
		arrived2.addResultListener(rl2);

		return new Future<Void>();
	}

	public IFuture<Void> setPlaneID(String planeID) {
//		this.planeID = planeID;
		return IFuture.DONE;
	};
}

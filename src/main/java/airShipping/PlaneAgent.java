package airShipping;

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

	@AgentArgument
	private String planeID = "000";
	@AgentArgument
	private IVector2 planeStartPosition = new Vector2Double(0.0, 0.0);
	@AgentArgument
	private int capacity = 0;
	@AgentArgument
	private ArrayList<IVector2> route = new ArrayList<>();

	@OnInit
	public IFuture<Void> agentInit() {

		ServiceQuery<IMapService> query = new ServiceQuery<IMapService>(IMapService.class);
		query.setScope(ServiceScope.PLATFORM);

		mapService = agent.getLocalService(query);

//		IFuture<Void> done = mapService.createPlane("ABC", airports.getAirportB(), "XYZ", airports.getAirportB());

		IFuture<Void> done = mapService.createPlane2(planeID, planeStartPosition, capacity);

		System.out.println("Plane Agent found: " + mapService);

		return done;
	}

	// Good news, only one @OnStart method allowed in a package :)
	@OnStart
	public IFuture<Void> agentStart() {

		IResultListener<Void> rl = new IResultListener<Void>() {
			int i = 0;

			public void resultAvailable(Void done) {
				i = (i + 1) % route.size();
				IFuture<Void> arrived = mapService.setPlaneTarget(/* "ABC" */ planeID, route.get(i));
				arrived.addResultListener(this);
				System.out.println("Plane has arrived at " + route.get(i - 1));
			}

			public void exceptionOccurred(Exception e) {
			}
		};

		// test
//		IResultListener<Void> rl2 = new IResultListener<Void>() {
//			int i = 0;
//
//			public void resultAvailable(Void done) {
//				i = (i + 1) % expressRoute.size();
//				IFuture<Void> arrived = mapService.setPlaneTarget("XYZ", expressRoute.get(i));
//				arrived.addResultListener(this);
//				System.out.println("Plane has arrived at " + expressRoute.get(i - 1));
//			}
//
//			public void exceptionOccurred(Exception e) {
//			}
//		};

		IFuture<Void> arrived = mapService.setPlaneTarget(/* "ABC" */planeID, route.get(0));
		arrived.addResultListener(rl);

		// test
//		IFuture<Void> arrived2 = mapService.setPlaneTarget("XYZ", expressRoute.get(0));
//		arrived2.addResultListener(rl2);

		return new Future<Void>();
	}

	public IFuture<Void> setPlaneID(String planeID) {
//		this.planeID = planeID;
		return IFuture.DONE;
	};
}

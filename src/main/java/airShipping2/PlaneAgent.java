package airShipping2;

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

	private final IVector2 airportA = new Vector2Double(1.7, 7.9);
	private final IVector2 airportB = new Vector2Double(13.8, 2.5);
	private final IVector2 airportC = new Vector2Double(8.2, 11.7);
	private final IVector2 airportD = new Vector2Double(4.9, 1.1);

	@OnInit
	public IFuture<Void> agentInit() {

		ServiceQuery<IMapService> query = new ServiceQuery<IMapService>(IMapService.class);
		query.setScope(ServiceScope.PLATFORM);

		mapService = agent.getLocalService(query);

		IFuture<Void> done = mapService.createPlane("Plane-01");

		System.out.println("Plane Agent found: " + mapService);

		return done;
	}

	@OnStart
	public IFuture<Void> agentStart() {
		IResultListener<Void> rl = new IResultListener<Void>() {
			public void resultAvailable(Void done) {

				System.out.println("Plane has arrived!");
				IFuture<Void> arrived = mapService.setPlaneTarget(airportA);
				arrived.addResultListener(this);
			}

			public void exceptionOccurred(Exception e) {
			}
		};

		IFuture<Void> arrived = mapService.setPlaneTarget(airportC);
		arrived.addResultListener(rl);

		return new Future<Void>();
	}
}

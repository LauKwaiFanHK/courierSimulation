package airShipping;

import java.util.ArrayList;

import jadex.base.IPlatformConfiguration;
import jadex.base.PlatformConfigurationHandler;
import jadex.base.Starter;
import jadex.bridge.IComponentStep;
import jadex.bridge.IExternalAccess;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.ServiceScope;
import jadex.bridge.service.search.ServiceQuery;
import jadex.bridge.service.types.cms.CreationInfo;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.micro.annotation.Agent;

@Agent
public class Main {

	public static void main(String[] args) {
		final Airports airports = new Airports();

		final Routes route = new Routes();
		route.setExpressRouteBA();
		ArrayList<IVector2> expressRoute = route.getExpressRouteBA();

		final Routes route2 = new Routes();
		route2.setNormalRouteBCDAB();
		ArrayList<IVector2> normalRoute = route2.getNormalRouteBCDAB();

		final Routes route3 = new Routes();
		route3.setNormalRouteEFGHE();
		ArrayList<IVector2> normalRoute2 = route3.getNormalRouteEFGHE();

		final Routes route4 = new Routes();
		route4.setExpressRouteEH();
		ArrayList<IVector2> expressRoute2 = route4.getExpressRouteEH();

		System.out.println("Starting...");
		IPlatformConfiguration config = PlatformConfigurationHandler.getMinimal();
		config.setGui(false);
		IFuture<IExternalAccess> platformfuture = Starter.createPlatform(config);

		System.out.println("Calling get()");
		IExternalAccess platform = platformfuture.get();
		System.out.println("Got platform using get() " + platform);

		CreationInfo ci = new CreationInfo();
		ci.setFilenameClass(MapAgent.class);
		IExternalAccess envagent = platform.createComponent(ci).get();
		System.out.println("Got MapAgent using get() " + envagent);

		// plane agent for ABC
		ci = new CreationInfo();
		ci.setFilenameClass(PlaneAgent.class);
		ci.addArgument("planeID", "ABC");
		ci.addArgument("planeStartPosition", airports.getAirportB());
		ci.addArgument("route", expressRoute);
		ci.addArgument("capacity", 100);
		platform.createComponent(ci).get();

		// plane agent for XYZ
		ci = new CreationInfo();
		ci.setFilenameClass(PlaneAgent.class);
		ci.addArgument("planeID", "XYZ");
		ci.addArgument("planeStartPosition", airports.getAirportB());
		ci.addArgument("route", normalRoute);
		ci.addArgument("capacity", 5000);
		platform.createComponent(ci).get();

		// plane agent for LMN
		ci = new CreationInfo();
		ci.setFilenameClass(PlaneAgent.class);
		ci.addArgument("planeID", "LMN");
		ci.addArgument("planeStartPosition", airports.getAirportE());
		ci.addArgument("route", normalRoute2);
		ci.addArgument("capacity", 5000);
		platform.createComponent(ci).get();

		// plane agent for OPQ
		ci = new CreationInfo();
		ci.setFilenameClass(PlaneAgent.class);
		ci.addArgument("planeID", "OPQ");
		ci.addArgument("planeStartPosition", airports.getAirportE());
		ci.addArgument("route", expressRoute2);
		ci.addArgument("capacity", 100);
		platform.createComponent(ci).get();

		platform.scheduleStep(new IComponentStep<Void>() {

			@Override
			public IFuture<Void> execute(IInternalAccess ia) {
				System.out.println("Platform step is starting...");
				ServiceQuery<IMapService> query = new ServiceQuery<IMapService>(IMapService.class);
				query.setScope(ServiceScope.PLATFORM);
				IMapService is = null;
				System.out.println("Query Map Service: " + query);
				try {
					is = ia.getLocalService(query);
					System.out.println("Map Service founded: " + is);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Map service search result: " + is);

				return IFuture.DONE;
			}
		});
	}
}

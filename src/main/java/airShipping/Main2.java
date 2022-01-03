package airShipping;

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
import jadex.micro.annotation.Agent;

@Agent
public class Main2 {

	public static void main(String[] args) {

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

		ci = new CreationInfo();
		ci.setFilenameClass(PlaneAgent.class);
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

				ServiceQuery<IPlaneService> query2 = new ServiceQuery<IPlaneService>(IPlaneService.class);
				query2.setScope(ServiceScope.PLATFORM);
				IPlaneService is2 = null;
				System.out.println("Query Plane Service: " + query2);

				try {
					is2 = ia.getLocalService(query2);
					System.out.println("Plane Service founded: " + is2);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Plane service search result: " + is2);

				return IFuture.DONE;
			}
		});
	}
}

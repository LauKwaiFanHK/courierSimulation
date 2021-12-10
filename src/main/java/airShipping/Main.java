package airShipping;

import jadex.base.IPlatformConfiguration;
import jadex.base.PlatformConfigurationHandler;
import jadex.base.Starter;
import jadex.bridge.IComponentStep;
import jadex.bridge.IExternalAccess;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.ServiceScope;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.search.ServiceQuery;
import jadex.bridge.service.types.cms.CreationInfo;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;

@Agent
public class Main {

	public static void main(String[] args) {
		System.out.println("Starting...");
		IPlatformConfiguration config = PlatformConfigurationHandler.getMinimal();
		config.setGui(true);
		IFuture<IExternalAccess> platformfuture = Starter.createPlatform(config);

		System.out.println("Calling get()");
		IExternalAccess platform = platformfuture.get();
		System.out.println("Got platform using get() " + platform);
		CreationInfo ci = new CreationInfo();
		ci.setFilenameClass(MapAgent.class);
		IExternalAccess envagent = platform.createComponent(ci).get();
		System.out.println("Got MapAgent using get() " + envagent);

		platform.scheduleStep(new IComponentStep<Void>() {

			@Override
			public IFuture<Void> execute(IInternalAccess ia) {
				System.out.println("Platform step is starting...");
				return IFuture.DONE;
			}
		});
	}
}

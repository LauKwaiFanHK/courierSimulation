package airShipping2;

import java.util.ArrayList;

import jadex.bridge.service.annotation.Service;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.IVector2;

@Service
public interface IMapService {
	public IFuture<Void> createPlane(String id);

	public IFuture<Void> setPlaneTarget(IVector2 target);
}

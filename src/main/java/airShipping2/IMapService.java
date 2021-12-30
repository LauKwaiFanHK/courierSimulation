package airShipping2;

import jadex.bridge.service.annotation.Service;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.IVector2;

@Service
public interface IMapService {
	public IFuture<Void> createPlane(String id, IVector2 startPosition, String id2, IVector2 startPosition2);

	public IFuture<Void> setPlaneTarget(String id, IVector2 target);
}
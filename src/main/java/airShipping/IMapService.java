package airShipping;

import java.util.ArrayList;

import jadex.bridge.service.annotation.Service;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.IVector2;

/**
 * Service used to interact with the map.
 *
 */
@Service
public interface IMapService {
	/**
	 * Creates a new plane on the map.
	 * 
	 * @param name Name of the plane (identifier).
	 * @return Returns null, when plane has been created.
	 */
//	public IFuture<Void> createPlane();

//	public IFuture<IVector2> getArrivalAirportPosition(String planeID);

	/**
	 * Sets a new target for the plane.
	 * 
	 * @param name   Name of the plane.
	 * @param target Target for the plane.
	 * @return Returns, when plane has arrived.
	 */
	public IFuture<Void> setPlaneTarget(LocalAirport nextAirport);

}

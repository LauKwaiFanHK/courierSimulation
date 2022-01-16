package airShipping;

import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

public class Airports {
	private final IVector2 airportA = new Vector2Double(1.7, 7.9);
	private final IVector2 airportB = new Vector2Double(13.8, 2.5);
	private final IVector2 airportC = new Vector2Double(8.2, 11.7);
	private final IVector2 airportD = new Vector2Double(4.9, 1.1);

	public IVector2 getAirportA() {
		return airportA;
	}

	public IVector2 getAirportB() {
		return airportB;
	}

	public IVector2 getAirportC() {
		return airportC;
	}

	public IVector2 getAirportD() {
		return airportD;
	}
}

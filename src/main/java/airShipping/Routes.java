package airShipping;

import java.util.ArrayList;

import jadex.extension.envsupport.math.IVector2;

public class Routes {

	final Airports airports = new Airports();

	ArrayList<IVector2> expressRoute_BA = new ArrayList<>();

	ArrayList<IVector2> normalRoute_BCDAB = new ArrayList<>();

	ArrayList<IVector2> expressRoute_EH = new ArrayList<>();

	ArrayList<IVector2> normalRoute_EFGHE = new ArrayList<>();

	public ArrayList<IVector2> getExpressRouteBA() {
		return expressRoute_BA;
	}

	public void setExpressRouteBA() {
		// route StartPosition-A-B
		expressRoute_BA.add(airports.getAirportA());
		expressRoute_BA.add(airports.getAirportB());

	}

	public ArrayList<IVector2> getNormalRouteBCDAB() {
		return normalRoute_BCDAB;
	}

	public void setNormalRouteBCDAB() {
		// route StartPosition-C-D-A-B
		normalRoute_BCDAB.add(airports.getAirportC());
		normalRoute_BCDAB.add(airports.getAirportD());
		normalRoute_BCDAB.add(airports.getAirportA());
		normalRoute_BCDAB.add(airports.getAirportB());
	}

	public ArrayList<IVector2> getExpressRouteEH() {
		return expressRoute_EH;
	}

	public void setExpressRouteEH() {
		// route StartPosition-H-E
		expressRoute_EH.add(airports.getAirportH());
		expressRoute_EH.add(airports.getAirportE());

	}

	public ArrayList<IVector2> getNormalRouteEFGHE() {
		return normalRoute_EFGHE;
	}

	public void setNormalRouteEFGHE() {
		// route StartPosition-F-G-H-E
		normalRoute_EFGHE.add(airports.getAirportF());
		normalRoute_EFGHE.add(airports.getAirportG());
		normalRoute_EFGHE.add(airports.getAirportH());
		normalRoute_EFGHE.add(airports.getAirportE());
	}

}

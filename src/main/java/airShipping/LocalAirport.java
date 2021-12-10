package airShipping;

import java.util.ArrayList;
import java.util.List;

import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

public class LocalAirport {
	private IVector2 airportCoordinate;
	private String airportName;
	// warehouse of the airport
	private Warehouse warehouse;

	List<Warehouse> airport_warehouse = new ArrayList<>();

	// constructor for LocalAirport
	public LocalAirport(IVector2 airportCoordinate, String airportName, Warehouse warehouse) {
		this.airportCoordinate = airportCoordinate;
		this.airportName = airportName;
		this.warehouse = warehouse;
	}

	public void createNewWarehouse(String id, int capacity, int fillUpRate) {
		Warehouse warehouse = new Warehouse(id, capacity, fillUpRate);
		airport_warehouse.add(warehouse);
	}

	public double getAirportXcoordinate() {
		double x = airportCoordinate.getXAsDouble();
		return x;
	}

	public double getAirportYcoordinate() {
		double y = airportCoordinate.getYAsDouble();
		return y;
	}

	public IVector2 getAirportCoordinate() {
		return airportCoordinate;
	}

	public void setAirportCoordinate(int x, int y) {
		this.airportCoordinate = new Vector2Double(x, y);
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}

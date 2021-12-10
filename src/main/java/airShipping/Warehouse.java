package airShipping;

public class Warehouse {

	String id;
	int capacity;
	int fillUpRate; // per second
	private State state;
	int numberOfParcels;
	double occupancy;

	enum State {
		empty, halfFull, full
	}

	public Warehouse(String id, int capacity, int fillUpRate) {
		this.id = id;
		this.capacity = capacity;
		this.fillUpRate = fillUpRate;
		this.state = State.empty;
		this.numberOfParcels = 0;
		this.occupancy = 0.0;
	}

	public int getNumberOfParcels() {
		return numberOfParcels;
	}

	public void setNumberOfParcels(int numberOfParcels) {
		this.numberOfParcels = numberOfParcels;
	}

	public double getParcelFillUpRate() {
		return fillUpRate;
	}

	public void setParcelFillUpRate(int fillUpRate) {
		this.fillUpRate = fillUpRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

//	public int getTotalParcel(int fillUpRate, int second) {
//		numberOfParcels = fillUpRate * second;
//		return numberOfParcels;
//	}

//	public double getOccupacy(int fillUpRate, int second, int capacity) {
//		double occupacy = 0.0;
//		occupacy = getTotalParcel(fillUpRate, second) / capacity;
//		return occupacy;
//	}

	public String updateState(double occupacy) {
		State currentState = State.empty;
		try {
			if (occupacy == 0.0) {
				currentState = State.empty;
				return currentState.name();
			} else if (occupacy == 0.5) {
				currentState = State.halfFull;
				return currentState.name();
			} else if (occupacy == 1.0) {
				currentState = State.full;
				return currentState.name();
			} else if (occupacy > 1.0) {
				System.out.println("Overloaded!");
			} else {
				return occupacy + " % full.";
			}
		} catch (Exception e) {
			System.out.println("Invalid State.");
		}
		return currentState.name();
	}

	// methods for loading and unloading parcels

	// load parcels into the warehouse
	public void loadParcels(int seconds) {
		// System.out.println("Loading parcels into warehouse...");
		this.numberOfParcels += (fillUpRate * seconds);
		this.setOccupancy();
		// System.out.println("Parcels in " + this.getId() + ": " +
		// this.getNumberOfParcels());

	}

	// set the current occupancy of the warehouse
	public void setOccupancy() {
		this.occupancy = (double) numberOfParcels / capacity;
	}

	// get the current occupancy of the warehouse
	public double getOccupancy() {
		return occupancy;
	}

}

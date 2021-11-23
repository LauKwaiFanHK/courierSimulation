package airShipping;

public class Warehouse {

	String id;
//	String location;
	double capacity;
	double fillUpRate;
	private State state;
	int numberOfParcels;

	enum State {
		empty, halfFull, full
	}

	public Warehouse(String id, double capacity, double fillUpRate) {
		this.id = id;
//		this.location = location;
		this.capacity = capacity;
		this.fillUpRate = fillUpRate;
		this.state = State.empty;
	}

	public int getNumberOfParcels() {
		return numberOfParcels;
	}

	public void setNumberOfParcels(int numberOfParcels) {
		this.numberOfParcels = numberOfParcels;
	}

	public int getTotalParcel(int fillUpRate, int second) {
		numberOfParcels = fillUpRate * second;
		return numberOfParcels;
	}

	public double getOccupacy(int fillUpRate, int second, int capacity) {
		double occupacy = 0.0;
		occupacy = getTotalParcel(fillUpRate, second) / capacity;
		return occupacy;
	}

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

	public double getParcelFillUpRate() {
		return fillUpRate;
	}

	public void setParcelFillUpRate(double fillUpRate) {
		this.fillUpRate = fillUpRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getLocation() {
//		return location;
//	}

//	public void setLocation(String location) {
//		this.location = location;
//	}

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
}

package airShipping;

import jadex.commons.future.Future;
import jadex.extension.envsupport.math.IVector2;

public class Plane {

	private String id;
	private IVector2 currentPosition;
	private LocalAirport departAirport;
	private LocalAirport arrivalAirport;

	enum PlaneDirection {
		NONE, RIGHTDOWN, RIGHTUP, LEFTUP, LEFTDOWN
	}

	private PlaneDirection myDirection;
	private IVector2 target;
	private Future<Void> targetArrived;

	boolean fullyLoaded;
	double loadDuration;
	double unloadDuration;
	// plane storage
//	private int capacity;
//	int numOfParcels;
//	private double occupancy;

	public Plane(String id, LocalAirport departAirport, LocalAirport arrivalAirport) {
		this.id = id;
		this.currentPosition = departAirport.getAirportCoordinate();
		this.departAirport = departAirport;
		this.arrivalAirport = arrivalAirport;
		this.target = arrivalAirport.getAirportCoordinate();
		this.myDirection = this.getPlaneDirection();
		this.loadDuration = 6.0;
		this.unloadDuration = 4.0;
		this.fullyLoaded = false;
//		this.capacity = 100;
//		this.numOfParcels = 0;
//		this.occupancy = 0.0;
	}

	public IVector2 getCurrentPosition() {
		return currentPosition;
	}

	public IVector2 setCurrentPosition(IVector2 currentPosition) {
		this.currentPosition = currentPosition;
		return currentPosition;
	}

	public PlaneDirection getMyDirection() {
		return myDirection;
	}

	public void setMyDirection(PlaneDirection myDirection) {
		this.myDirection = myDirection;
	}

	public IVector2 getTarget() {
		return target;
	}

	public void setTarget(IVector2 target) {
		this.target = target;
	}

	public Future<Void> getTargetArrived() {
		return targetArrived;
	}

	public void setTargetArrived(Future<Void> targetArrived) {
		this.targetArrived = targetArrived;
	}

	public boolean isFullyLoaded() {
		return fullyLoaded;
	}

	public void setFullyLoaded(boolean fullyLoaded) {
		this.fullyLoaded = fullyLoaded;
	}

	public double getUnloadDuration() {
		return unloadDuration;
	}

	public void setUnloadDuration(double unloadDuration) {
		this.unloadDuration = unloadDuration;
	}

	public double getLoadingTime() {
		return loadDuration;
	}

	public void setLoadingTime(double loadingTime) {
		this.loadDuration = loadingTime;
	}

	public double getCurrentPositionX(IVector2 currentPosition) {
		double posX = this.currentPosition.getXAsDouble();
		return posX;
	}

	public double getCurrentPositionY(IVector2 currentPosition) {
		double posY = this.currentPosition.getYAsDouble();
		return posY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalAirport getDepartAirport() {
		return departAirport;
	}

	public void setDepartAirport(LocalAirport departAirport) {
		this.departAirport = departAirport;
	}

	public LocalAirport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(LocalAirport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	private double speedIfStop = 0.0;

	private double speed = 0.0;

	public void loadParcels() {
		if (!fullyLoaded) {
			System.out.println("Plane locates at airport: " + this.getDepartAirport().getAirportName());
			System.out.println("Plane's current location: " + this.currentPosition);
			System.out.println("Parcels are now being loaded to the plane...");
			if (this.speed == 0.0 && !(loadDuration == 0.0)) {
				System.out.println("Still " + loadDuration + " seconds before departure...\n---");
				loadDuration = loadDuration - 1;
			} else {
				if (loadDuration == 0.0) {
					System.out.println("Plane is fully loaded. Ready to depart.\n---");
					this.setFullyLoaded(true);
				}
			}
		}
	}

	public void unloadParcels() {
		if (fullyLoaded) {
			System.out.println("Plane locates at airport: " + this.getArrivalAirport().getAirportName());
			System.out.println("Plane's current location: " + this.currentPosition);
			System.out.println("Parcels are now being unloaded from the plane...");
			if (this.speed == 0.0 && !(unloadDuration == 0.0)) {
				System.out.println("Still " + unloadDuration + " seconds to finish unloading...\n---");
				unloadDuration = unloadDuration - 1;
			} else {
				if (unloadDuration == 0.0) {
					System.out.println("All parcels is unloaded at airport: " + this.arrivalAirport.getAirportName()
							+ ". The plane is ready to fill up parcels again!\n---");
					this.setFullyLoaded(false);
				}
			}
		}
	}

	public void updatePos(double deltaseconds) {
//		IVector2 direction = vel.getDirection(vel)
//		IVector2 posDelta = newPos.copy().multiply(deltaseconds);
//		currentPosition = currentPosition.add(posDelta);

		boolean stopped = this.checkStopped(getSpeed());
		double currentPosX = this.getCurrentPosition().getXAsDouble();
		double currentPosY = this.getCurrentPosition().getYAsDouble();
		double arrivalAirportPosX = arrivalAirport.getAirportXcoordinate();
		double arrivalAirportPosY = arrivalAirport.getAirportYcoordinate();
		IVector2 newVector = arrivalAirport.getAirportCoordinate().copy().subtract(currentPosition);
		IVector2 newUnitVector = newVector.normalize();

		switch (myDirection) {
		case RIGHTUP:
			if ((currentPosX >= arrivalAirportPosX) && (currentPosY >= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case RIGHTDOWN:
			if ((currentPosX >= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTUP:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY >= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTDOWN:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		default:
			break;
		}
	}

	public PlaneDirection getPlaneDirection() {
		IVector2 newVector = arrivalAirport.getAirportCoordinate().copy().subtract(currentPosition);
		double newVectorX = newVector.getXAsDouble();
		double newVectorY = newVector.getYAsDouble();
		PlaneDirection myDirection = PlaneDirection.NONE;
		if (newVectorX >= 0 && newVectorY >= 0) {
			myDirection = PlaneDirection.RIGHTUP;
		}
		if (newVectorX >= 0 && newVectorY <= 0) {
			myDirection = PlaneDirection.RIGHTDOWN;
		}
		if (newVectorX <= 0 && newVectorY >= 0) {
			myDirection = PlaneDirection.LEFTUP;
		}
		if (newVectorX <= 0 && newVectorY <= 0) {
			myDirection = PlaneDirection.LEFTDOWN;
		}
		return myDirection;
	}

	public void stopPlane() {
		this.speed = 0.0;
		this.setCurrentPosition(arrivalAirport.getAirportCoordinate());
		System.out.println("Arrived at position: " + this.getCurrentPosition());
		System.out.println("Speed: " + this.getSpeed());
	}

	public void movePlane(IVector2 newUnitVector) {
		this.setSpeed(0.03);
		System.out.println("Plane's current Position: " + currentPosition);
		System.out.println("Speed: " + this.getSpeed() + "\n----");
		IVector2 positionDelta = newUnitVector.multiply(speed);
		currentPosition = currentPosition.add(positionDelta);
	}

	public void reverse() {
		speed = -speed;
	}

	public String toString() {
		return "Current plane position: " + currentPosition;
	}

	public boolean checkStopped(double currentSpeed) {
		if (currentSpeed == speedIfStop) {
			return true;
		} else {
			return false;
		}
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	};

	public double getSpeed() {
		return speed;
	};

	public boolean hasArrived() {
		return currentPosition.equals(this.getArrivalAirport().getAirportCoordinate());
	}

	// methods for plane storage

//	public void setCapacity(int capacity) {
//		this.capacity = capacity;
//	}
//
//	public int getCapacity() {
//		return capacity;
//	}
//
//	public int getNumOfParcels() {
//		return numOfParcels;
//	}
//
//	// set the current occupancy of the plane
//	public void setOccupancy() {
//		this.occupancy = (double) numOfParcels / capacity;
//	}
//
//	// get the current occupancy of the plane
//	public double getOccupancy() {
//		return occupancy;
//	}
//
//	// load parcels into plane*
//	public void loadParcelsToPlane(int fillUp, int seconds) {
//		this.numOfParcels += (fillUp * seconds);
//		this.setOccupancy();
//		// System.out.println("Parcels in plane: " + this.numOfParcels);
//	}
//
//	// unload parcels from plane*
//	public void unloadParcelsFromPlane() {
//		this.numOfParcels = 0;
//		this.setOccupancy();
//	}
//
//	// show the status of plane and warehouses**
//	public void showStatus() {
//		Warehouse departWarehouse = this.getDepartAirport().getWarehouse();
//		Warehouse arrivalWarehouse = this.getArrivalAirport().getWarehouse();
//		System.out.println("Plane occupancy: " + (this.occupancy * 100) + "%" + "\nDepart warehouse occupancy: "
//				+ (departWarehouse.getOccupancy() * 100) + "%" + "\nArrival warehouse occupancy: "
//				+ (arrivalWarehouse.getOccupancy() * 100) + "%");
//	}
}

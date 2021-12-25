package airShipping2;

import jadex.commons.future.Future;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

public class Plane {

	private final String id;
	private IVector2 currentPosition = new Vector2Double(13.8, 2.5);
	private IVector2 target = new Vector2Double(0.0, 0.0);
	private double speed;
	private boolean fullyLoaded;

	enum PlaneDirection {
		NONE, RIGHTDOWN, RIGHTUP, LEFTUP, LEFTDOWN
	}

	private PlaneDirection myDirection;
	private Future<Void> targetArrived;

	public Plane(String id) {
		this.id = id;
//		this.currentPosition = new Vector2Double(1.7, 7.9);
		this.speed = 0.0;
		this.myDirection = PlaneDirection.NONE;
		this.fullyLoaded = true;
	}

	public double getCurrentPositionX(IVector2 currentPosition) {
		double posX = this.currentPosition.getXAsDouble();
		return posX;
	}

	public double getCurrentPositionY(IVector2 currentPosition) {
		double posY = this.currentPosition.getYAsDouble();
		return posY;
	}

	public void updatePos(double deltaseconds) {
		boolean stopped = this.checkStopped(getSpeed());
		double currentPosX = this.getCurrentPosition().getXAsDouble();
		double currentPosY = this.getCurrentPosition().getYAsDouble();
		double arrivalAirportPosX = target.getXAsDouble();
		double arrivalAirportPosY = target.getYAsDouble();
		IVector2 newVector = target.copy().subtract(currentPosition);
		IVector2 newUnitVector = newVector.normalize();

		switch (myDirection) {
		case RIGHTUP:
			if ((currentPosX >= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case RIGHTDOWN:
			if ((currentPosX >= arrivalAirportPosX) && (currentPosY >= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTUP:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else if (fullyLoaded) {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTDOWN:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY >= arrivalAirportPosY)) {
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
		IVector2 newVector = target.copy().subtract(currentPosition);
		double newVectorX = newVector.getXAsDouble();
		double newVectorY = newVector.getYAsDouble();
		PlaneDirection myDirection = PlaneDirection.NONE;
		if (newVectorX >= 0 && newVectorY <= 0) {
			myDirection = PlaneDirection.RIGHTUP;
		}
		if (newVectorX >= 0 && newVectorY >= 0) {
			myDirection = PlaneDirection.RIGHTDOWN;
		}
		if (newVectorX <= 0 && newVectorY <= 0) {
			myDirection = PlaneDirection.LEFTUP;
		}
		if (newVectorX <= 0 && newVectorY >= 0) {
			myDirection = PlaneDirection.LEFTDOWN;
		}
		return myDirection;
	}

	public void stopPlane() {
		this.speed = 0.0;
		this.setCurrentPosition(target);
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

	public boolean checkStopped(double currentSpeed) {
		if (currentSpeed == 0.0) {
			return true;
		} else {
			return false;
		}
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
		this.setMyDirection(this.getPlaneDirection());
	}

	public Future<Void> getTargetArrived() {
		return targetArrived;
	}

	public void setTargetArrived(Future<Void> targetArrived) {
		this.targetArrived = targetArrived;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	};

	public double getSpeed() {
		return speed;
	};

	public boolean hasArrived() {
		return currentPosition.equals(this.target);
	}

	public boolean isFullyLoaded() {
		return fullyLoaded;
	}

	public String getId() {
		return id;
	}

}

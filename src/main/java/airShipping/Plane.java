package airShipping;

import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import java.awt.Point;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Plane {

	private String id;
	private IVector2 currentPosition;
	private LocalAirport departAirport;
	private LocalAirport arrivalAirport;

	enum PlaneDirection {
		NONE, RIGHTDOWN, RIGHTUP, LEFTUP, LEFTDOWN
	}

	private PlaneDirection myDirection;
	private double x0;
	private double x1;
	private double y0;
	private double y1;

	public Plane(String id, LocalAirport departAirport, LocalAirport arrivalAirport) {
		this.id = id;
		this.currentPosition = departAirport.getAirportCoordinate();
		this.departAirport = departAirport;
		this.arrivalAirport = arrivalAirport;
		this.myDirection = this.getPlaneDirection();
		this.x0 = departAirport.getAirportXcoordinate();
		this.x1 = arrivalAirport.getAirportXcoordinate();
		this.y0 = departAirport.getAirportYcoordinate();
		this.y1 = arrivalAirport.getAirportYcoordinate();
	}

	public IVector2 getCurrentPosition() {
		return currentPosition;
	}

	public IVector2 setCurrentPosition(IVector2 currentPosition) {
		this.currentPosition = currentPosition;
		return currentPosition;
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

	private double speed = 1;

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
			} else {
				this.movePlane(newUnitVector);
				break;
			}
		case RIGHTDOWN:
			if ((currentPosX >= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTUP:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY >= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else {
				this.movePlane(newUnitVector);
				break;
			}
		case LEFTDOWN:
			if ((currentPosX <= arrivalAirportPosX) && (currentPosY <= arrivalAirportPosY)) {
				if (!stopped) {
					this.stopPlane();
					break;
				}
			} else {
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

}

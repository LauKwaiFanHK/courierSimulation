package airShipping;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.time.Instant;

import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnInit;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.annotation.Service;
import jadex.commons.Boolean3;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.micro.annotation.Agent;

@Agent(autoprovide = Boolean3.TRUE)
@Service
public class MapAgent implements IMapService {

	@Agent
	private IInternalAccess agent;

	private JFrame frame;

	private Plane plane;
	private LocalAirport airportA;
	private LocalAirport airportB;
	private LocalAirport airportC;

	List<Plane> planeList = new ArrayList<>();
	HashMap<String, LocalAirport> airportList = new HashMap<String, LocalAirport>();

//	private long reversetimestamp;
//	private long timestamp;

	private IComponentStep<Void> simulationstep;

	@OnInit
	public IFuture<Void> agentInit() {
		Future<Void> done = new Future<Void>();

		SwingUtilities.invokeLater(() -> {
			frame = new JFrame("Environment Simulation v1.0");
			frame.setSize(600, 600);
			frame.setVisible(true);

			agent.getExternalAccess().scheduleStep(new IComponentStep<Void>() {
				public IFuture<Void> execute(IInternalAccess ia) {
					done.setResult(null);
					return IFuture.DONE;
				}
			});
		});

		return done;
	}

	@OnStart
	public IFuture<Void> agentRun() {
		Warehouse warehouseA = new Warehouse("Warehouse A", 20, 1);
		Warehouse warehouseB = new Warehouse("Warehouse B", 20, 1);
		Warehouse warehouseC = new Warehouse("Warehouse C", 20, 1);
		airportA = new LocalAirport((new Vector2Double(1.7, 7.9)), "A", warehouseA);
		airportB = new LocalAirport((new Vector2Double(13.8, 2.5)), "B", warehouseB);
		airportC = new LocalAirport((new Vector2Double(5.2, 11.7)), "C", warehouseC);
		plane = new Plane("Plane-01", airportA, airportB);

		planeList.add(plane);
		airportList.put("airport_A", airportA);
		airportList.put("airport_B", airportB);
		airportList.put("airport_C", airportC);

		System.out.println("Agent is running, a plane is created. \n");
		System.out.println("Loading parcels into warehouse... \n");

		Timer swingtimer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (MapAgent.this) {
					Container pane = frame.getContentPane();

					// uncomment 2 next line, then comment the 3rd next line, then go to line 165
					BufferedImage img = new BufferedImage(pane.getWidth(), pane.getHeight(),
							BufferedImage.TYPE_4BYTE_ABGR_PRE);
					Graphics2D g = (Graphics2D) img.getGraphics();
					// Graphics2D g = (Graphics2D) frame.getContentPane().getGraphics();

					// grid
					g.setColor(Color.pink);
					g.fillRect(0, 0, pane.getWidth(), pane.getHeight());

					// departAirport (Airport A)
					double w1 = 75;
					double h1 = 50;
					double x1 = 1.7 * 30;
					x1 = x1 - (w1 / 2);
					double y1 = 7.9 * 30;
					y1 = y1 - (h1 / 2);
					Rectangle2D dAirportRec = new Rectangle2D.Double(x1, y1, w1, h1);
					g.setColor(Color.darkGray);
					g.fill(dAirportRec);
					// Depart airport text
					Font f1 = new Font("Arial", Font.PLAIN, 12);
					g.setColor(Color.black);
					g.setFont(f1);
					String s = "Airport A";
					g.drawString(s, (int) x1, (int) (y1 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap1 = "Capacity: " + (plane.getDepartAirport().getWarehouse().getOccupancy() * 100) + "%";
					g.drawString(cap1, (int) x1, (int) (y1 + 70));

					// arrivalAirport (Airport B)
					double w2 = 75;
					double h2 = 50;
					double x2 = airportB.getAirportCoordinate().getXAsDouble() * 30;
					x2 = x2 - (w2 / 2);
					double y2 = airportB.getAirportCoordinate().getYAsDouble() * 30;
					y2 = y2 - (h2 / 2);
					Rectangle2D planeRecc = new Rectangle2D.Double(x2, y2, w2, h2);
					g.setColor(Color.yellow);
					g.fill(planeRecc);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s2 = "Airport B";
					g.drawString(s2, (int) x2, (int) (y2 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap2 = "Capacity: " + (plane.getArrivalAirport().getWarehouse().getOccupancy() * 100) + "%";
					g.drawString(cap2, (int) x2, (int) (y2 + 70));

					// arrivalAirport (Airport C)
					double w3 = 75;
					double h3 = 50;
					double x3 = airportC.getAirportCoordinate().getXAsDouble() * 30;
					x3 = x3 - (w3 / 2);
					double y3 = airportC.getAirportCoordinate().getYAsDouble() * 30;
					y2 = y3 - (h2 / 2);
					Rectangle2D airportCRecc = new Rectangle2D.Double(x3, y3, w3, h3);
					g.setColor(Color.blue);
					g.fill(airportCRecc);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s3 = "Airport C";
					g.drawString(s3, (int) x3, (int) (y3 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap3 = "Capacity: " + (plane.getArrivalAirport().getWarehouse().getOccupancy() * 100) + "%";
					g.drawString(cap3, (int) x3, (int) (y3 + 70));

					// Plane
					double w = 15;
					double h = 15;
					double x = plane.getCurrentPosition().getXAsDouble() * 30;
					x = x - (w / 2);
					double y = plane.getCurrentPosition().getYAsDouble() * 30;
					y = y - (h / 2);
					// double x = 13.8 -(w/2);
					// double y = 2.5 - (h/2);
					Ellipse2D planeRec = new Ellipse2D.Double(x, y, w, h);
					g.setColor(Color.red);
					g.fill(planeRec);
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String capPlane = "Plane's is fully loaded: " + (plane.isFullyLoaded());
					g.drawString(capPlane, (int) x, (int) (y + 70));

					// uncomment 2 next line
					Graphics2D gScreen = (Graphics2D) pane.getGraphics();
					gScreen.drawImage(img, 0, 0, null);
				}
			}
		});
		swingtimer.start();

		simulationstep = new IComponentStep<Void>() {
			public IFuture<Void> execute(IInternalAccess ia) {
				synchronized (MapAgent.this) {

//				long currentTimestamp = System.currentTimeMillis();
//				System.out.println("timestamp:" + timestamp );
//				System.out.println("reversetimestamp:" + reversetimestamp );
//				System.out.println("current time:" + currentTimestamp );
//				long reverseDelta = currentTimestamp - reversetimestamp;
//				System.out.println("reverseDelta:" + reverseDelta );
//				if(reverseDelta > 5000) {
//					car.reverse();
//					reversetimestamp = currentTimestamp;
//				}
//				double deltaseconds = (currentTimestamp - timestamp) / 1000.0; 

//				IVector2 startPos = plane.getCurrentPosition();
//				IVector2 newPos = plane.getNewPosition(startPos);
					Instant instant = Instant.now();
					// warehouses
//					Warehouse departWarehouse = plane.getDepartAirport().getWarehouse();
//					Warehouse arrivalWarehouse = plane.getArrivalAirport().getWarehouse();
//					int planeFillUp = departWarehouse.fillUpRate;
//
//					// System.out.println("Current UTC time: " + instant);
//					if (departWarehouse.getOccupancy() != 1.0) {
//						departWarehouse.loadParcels(1);
//						System.out.println("Parcels in warehouse: " + departWarehouse.getNumberOfParcels()
//								+ ". Occupancy: " + (departWarehouse.getOccupancy() * 100) + "%");
//						if (departWarehouse.getOccupancy() == 1.0) {
//							System.out.println("Warehouse capacity is reached. Loading parcels into the plane.\n");
//						}
//						/*
//						 * while (departWarehouse.getOccupancy() != 1.0) {
//						 * 
//						 * }
//						 */
//					} else if (departWarehouse.getOccupancy() == 1.0) { // no. of parcels in warehouse reduces with time
//						while (plane.getOccupancy() != 1.0) {
//							plane.loadParcelsToPlane(planeFillUp, 1);
//							System.out.println("Parcels in plane: " + +plane.getNumOfParcels() + ". Occupancy: "
//									+ (plane.getOccupancy() * 100) + "%");
//							if (plane.getOccupancy() == 1.0) {
//								System.out.println("Plane capacity is reached. Plane is ready to take off.\n");
//							}
//						}
//					}

//					if (plane.getOccupancy() == 1.0) {
					// departWarehouse.unloadParcels(1);
//					boolean arrived = plane.getCurrentPosition()
//							.equals(plane.getArrivalAirport().getAirportCoordinate());
					if (!(plane == null)) {
//						System.out.println("Current UTC time: " + instant);
//							plane.showStatus();
						if (!plane.hasArrived()) {
							plane.loadParcels();
							plane.updatePos(1);
						} else {
							plane.unloadParcels();
							if (!plane.fullyLoaded) {
//								plane.setDepartAirport(airportB);
//								plane.setArrivalAirport(airportC);
//								plane.setMyDirection(plane.getPlaneDirection());
//								plane.setLoadingTime(7);
//								System.out
//										.println("Next Destination is: " + plane.getArrivalAirport().getAirportName());
							}
						}
					} else {
						return null;
					}
//					}

					ia.scheduleStep(simulationstep);
					return IFuture.DONE;
				}
			}
		};
//		reversetimestamp = System.currentTimeMillis();
//		timestamp = System.currentTimeMillis();
		agent.scheduleStep(simulationstep);

		return new Future<Void>();
	}

	// use hashmap or arraylist if more planes will be created in later phase
//	public IFuture<Void> createPlane() {
//
//		synchronized (this) {
//			plane = new Plane("Plane-01", airportA, airportB);
//		}
//
//		return IFuture.DONE;
//	};

	public IFuture<Void> setPlaneTarget(LocalAirport nextAirport) {
		Future<Void> ret = new Future<>();
		synchronized (this) {
//			Plane plane = cars.get(name); // if planeList exists, add arg String planeIdentifier
			plane.setTarget(nextAirport.getAirportCoordinate());
			plane.setTargetArrived(ret);
		}

		return ret;
	}

}

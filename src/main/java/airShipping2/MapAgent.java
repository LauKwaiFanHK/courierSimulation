package airShipping2;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.jhlabs.vecmath.Color4f;

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
import jadex.micro.annotation.AgentArgument;

@Agent(autoprovide = Boolean3.TRUE)
@Service
public class MapAgent implements IMapService {

	@Agent
	private IInternalAccess agent;

	private JFrame frame;

	private Plane plane;
	private Plane plane2;
	private Map<String, Plane> planeList = new HashMap<>();

	private IComponentStep<Void> simulationstep;

	private Airports airports = new Airports();

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

		System.out.println("Map Agent is running.");

		IVector2 airportA = airports.getAirportA();
		IVector2 airportB = airports.getAirportB();
		IVector2 airportC = airports.getAirportC();
		IVector2 airportD = airports.getAirportD();

		Timer swingtimer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (MapAgent.this) {
//					System.out.println("Plane: " + plane);
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
					String cap1 = "This is: " + (s);
					g.drawString(cap1, (int) x1, (int) (y1 + 70));

					// arrivalAirport (Airport B)
					double w2 = 75;
					double h2 = 50;
					double x2 = airportB.getXAsDouble() * 30;
					x2 = x2 - (w2 / 2);
					double y2 = airportB.getYAsDouble() * 30;
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
					String cap2 = "This is: " + (s2);
					g.drawString(cap2, (int) x2, (int) (y2 + 70));

					// arrivalAirport (Airport C)
					double w3 = 75;
					double h3 = 50;
					double x3 = airportC.getXAsDouble() * 30;
					x3 = x3 - (w3 / 2);
					double y3 = airportC.getYAsDouble() * 30;
					y3 = y3 - (h2 / 2);
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
					String cap3 = "This is: " + (s3);
					g.drawString(cap3, (int) x3, (int) (y3 + 70));

					// arrivalAirport (Airport D)
					double w4 = 75;
					double h4 = 50;
					double x4 = airportD.getXAsDouble() * 30;
					x4 = x4 - (w4 / 2);
					double y4 = airportD.getYAsDouble() * 30;
					y4 = y4 - (h2 / 2);
					Rectangle2D airportDRecc = new Rectangle2D.Double(x4, y4, w4, h4);
					g.setColor(Color.green);
					g.fill(airportDRecc);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s4 = "Airport D";
					g.drawString(s4, (int) x4, (int) (y4 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap4 = "This is: " + (s4);
					g.drawString(cap4, (int) x4, (int) (y4 + 70));

					// Plane
					if (plane != null && plane2 != null) {
						for (Plane plane : planeList.values()) {
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
							String capPlane = "Number of parcels in plane: " + (plane.getNumberOfParcelsLoaded());
							g.drawString(capPlane, (int) x, (int) (y + 70));
						}
					}
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
					if (plane != null) {
						for (Plane plane : planeList.values()) {
							if (!plane.hasArrived()) {
								if (plane.getNumberOfParcelsLoaded() < plane.getCapacity()) {
									plane.loadParcel();
								}
								plane.updatePos(1);
							} else {
								if (plane.getTargetArrived() != null && !plane.getTargetArrived().isDone()) {
									plane.getTargetArrived().setResult(null);
									plane.setTargetArrived(null);
									plane.unloadParcel();
								}
							}
						}
					}
					ia.scheduleStep(simulationstep);
				}
				return IFuture.DONE;
			}
		};
		agent.scheduleStep(simulationstep);

		return new Future<Void>();

	}

	public IFuture<Void> createPlane(String id, IVector2 startPosition, String id2, IVector2 startPosition2) {

		synchronized (this) {
			plane = new Plane(id, startPosition);
			System.out.println("A plane is created: " + id);

			plane2 = new Plane(id2, startPosition2);
			System.out.println("A second plane is created: " + id2);

			planeList.put(id, plane);
			planeList.put(id2, plane2);
		}

		return IFuture.DONE;
	};

	public IFuture<Void> setPlaneTarget(String id, IVector2 target) {
		Future<Void> ret = new Future<>();
		synchronized (this) {
			Plane plane = planeList.get(id);
			plane.setTarget(target);
			plane.setTargetArrived(ret);
		}
		return ret;
	}

}

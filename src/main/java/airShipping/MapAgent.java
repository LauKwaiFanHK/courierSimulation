package airShipping;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.jhlabs.vecmath.Color4f;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
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
	//
	@Agent
	private IInternalAccess agent;

	static JFrame frame;
	static JPanel panel;
	private Plane plane;
	private Plane plane2;
	private Map<String, Plane> planeList = new HashMap<>();
	private Map<String, JProgressBar> barList = new HashMap<>();

	private IComponentStep<Void> simulationstep;

	private Airports airports = new Airports();

	private long timestamp;

	@OnInit
	public IFuture<Void> agentInit() {
		Future<Void> done = new Future<Void>();

		SwingUtilities.invokeLater(() -> {
			frame = new JFrame("Environment Simulation v1.0");
			frame.setSize(1000, 1000);
			frame.setVisible(true);
			System.out.println("Frame's layout: " + frame.getLayout());

			Container pane = frame.getContentPane();
			pane.setLayout(null);
			panel = new JPanel();
			pane.add(panel);
			panel.setBounds(0, 610, 1000, 110);
			panel.setBackground(Color.gray);

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
		IVector2 airportE = airports.getAirportE();
		IVector2 airportF = airports.getAirportF();
		IVector2 airportG = airports.getAirportG();
		IVector2 airportH = airports.getAirportH();

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
//					g.setColor(Color.pink);
//					g.fillRect(0, 0, pane.getWidth(), (pane.getHeight() - 100));
					Image bg = Toolkit.getDefaultToolkit().getImage("map.jpg");
					g.drawImage(bg, 0, 0, pane.getWidth(), (pane.getHeight() - 100), null);

					// departAirport (Airport A)
					double w1 = 84;
					double h1 = 51;
					double x1 = 1.7 * pane.getWidth() * 0.04;
					x1 = x1 - (w1 / 2);
					double y1 = 7.9 * pane.getHeight() * 0.04;
					y1 = y1 - (h1 / 2);
					Image airp1 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp1, (int) x1, (int) y1, (int) w1, (int) h1, null);

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
					double w2 = 84;
					double h2 = 51;
					double x2 = airportB.getXAsDouble() * pane.getWidth() * 0.04;
					x2 = x2 - (w2 / 2);
					double y2 = airportB.getYAsDouble() * pane.getHeight() * 0.04;
					y2 = y2 - (h2 / 2);
					Image airp2 = Toolkit.getDefaultToolkit().getImage("Airport2.png");
					g.drawImage(airp2, (int) x2, (int) y2, (int) w2, (int) h2, null);

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
					double w3 = 84;
					double h3 = 51;
					double x3 = airportC.getXAsDouble() * pane.getWidth() * 0.04;
					x3 = x3 - (w3 / 2);
					double y3 = airportC.getYAsDouble() * pane.getHeight() * 0.04;
					y3 = y3 - (h2 / 2);
					Image airp3 = Toolkit.getDefaultToolkit().getImage("Airport3.png");
					g.drawImage(airp3, (int) x3, (int) y3, (int) w3, (int) h3, null);

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
					double w4 = 84;
					double h4 = 51;
					double x4 = airportD.getXAsDouble() * pane.getWidth() * 0.04;
					x4 = x4 - (w4 / 2);
					double y4 = airportD.getYAsDouble() * pane.getHeight() * 0.04;
					y4 = y4 - (h2 / 2);
					Image airp4 = Toolkit.getDefaultToolkit().getImage("Airport4.png");
					g.drawImage(airp4, (int) x4, (int) y4, (int) w4, (int) h4, null);

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

					// departAirport (Airport E)
					double w5 = 75;
					double h5 = 50;
					double x5 = airportE.getXAsDouble() * pane.getWidth() * 0.04;
					x5 = x5 - (w5 / 2);
					double y5 = airportE.getYAsDouble() * pane.getHeight() * 0.04;
					y5 = y5 - (h5 / 2);
					Image airp5 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp5, (int) x5, (int) y5, (int) w5, (int) h5, null);
					// Depart airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s5 = "Airport E";
					g.drawString(s5, (int) x5, (int) (y5 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap5 = "This is: " + (s5);
					g.drawString(cap5, (int) x5, (int) (y5 + 70));

					// arrivalAirport (Airport F)
					double w6 = 75;
					double h6 = 50;
					double x6 = airportF.getXAsDouble() * pane.getWidth() * 0.04;
					x6 = x6 - (w6 / 2);
					double y6 = airportF.getYAsDouble() * pane.getHeight() * 0.04;
					y6 = y6 - (h6 / 2);
					Image airp6 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp6, (int) x6, (int) y6, (int) w6, (int) h6, null);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s6 = "Airport F";
					g.drawString(s6, (int) x6, (int) (y6 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap6 = "This is: " + (s6);
					g.drawString(cap6, (int) x6, (int) (y6 + 70));

					// arrivalAirport (Airport G)
					double w7 = 75;
					double h7 = 50;
					double x7 = airportG.getXAsDouble() * pane.getWidth() * 0.04;
					x7 = x7 - (w7 / 2);
					double y7 = airportG.getYAsDouble() * pane.getHeight() * 0.04;
					y7 = y7 - (h7 / 2);
					Image airp7 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp7, (int) x7, (int) y7, (int) w7, (int) h7, null);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s7 = "Airport G";
					g.drawString(s7, (int) x7, (int) (y7 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap7 = "This is: " + (s7);
					g.drawString(cap7, (int) x7, (int) (y7 + 70));

					// arrivalAirport (Airport H)
					double w8 = 75;
					double h8 = 50;
					double x8 = airportH.getXAsDouble() * pane.getWidth() * 0.04;
					x8 = x8 - (w8 / 2);
					double y8 = airportH.getYAsDouble() * pane.getHeight() * 0.04;
					y8 = y8 - (h8 / 2);
					Image airp8 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp8, (int) x8, (int) y8, (int) w8, (int) h8, null);
					// Arrival airport text
					g.setColor(Color.black);
					g.setFont(f1);
					String s8 = "Airport H";
					g.drawString(s4, (int) x4, (int) (y4 - 10));
					// capacity text
					g.setColor(Color.black);
					g.setFont(f1);
					String cap8 = "This is: " + (s8);
					g.drawString(cap8, (int) x8, (int) (y8 + 70));

					// Plane
					if (plane != null /* && plane2 != null */) {
						for (Plane plane : planeList.values()) {
							double w = 73;
							double h = 23;
							double x = plane.getCurrentPosition().getXAsDouble() * pane.getWidth() * 0.04;
							x = x - (w / 2);
							double y = plane.getCurrentPosition().getYAsDouble() * pane.getHeight() * 0.04;
							y = y - (h / 2);
							// double x = 13.8 -(w/2);
							// double y = 2.5 - (h/2);
							Image airplane = Toolkit.getDefaultToolkit().getImage("plane.png");
							g.drawImage(airplane, (int) x, (int) y, (int) w, (int) h, null);
							// capacity text
							g.setColor(Color.black);
							g.setFont(f1);
							String capPlane = plane.getNumberOfParcelsLoaded() + " parcels in plane";
							g.drawString(capPlane, (int) x, (int) (y + 70));

							JProgressBar bar = barList.get(plane.getId());
							if (bar != null) {
								bar.setValue(plane.getOccupacyRate());
							}
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
					// test
					long currentTimestamp = System.currentTimeMillis();
					double deltaseconds = (currentTimestamp - timestamp) / 1000.0;
					System.out.println("Time: " + deltaseconds);

					if (plane != null) {
						for (Plane plane : planeList.values()) {
							if (!plane.hasArrived()) {
								if (plane.getNumberOfParcelsLoaded() == 0) {
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
					timestamp = currentTimestamp;
					ia.scheduleStep(simulationstep);
				}
				return IFuture.DONE;
			}

		};
		agent.scheduleStep(simulationstep);

		return new Future<Void>();

	}

	// test
	public IFuture<Void> createPlane2(String id, IVector2 startPosition, int capacity) {

		synchronized (this) {

			plane = new Plane(id, startPosition, capacity);
			System.out.println("A plane is created: " + id);
			System.out.println("Plane's start position is: " + startPosition);
			plane.loadParcel();

			planeList.put(id, plane);

			JLabel text_plane = new JLabel();
			JProgressBar bar_plane = new JProgressBar();
			panel.add(text_plane);
			panel.add(bar_plane);
			text_plane.setText(plane.getId() + "'s current occupacy: ");
			bar_plane.setValue(plane.getOccupacyRate());
			bar_plane.setStringPainted(true);
			barList.put(plane.getId(), bar_plane);
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

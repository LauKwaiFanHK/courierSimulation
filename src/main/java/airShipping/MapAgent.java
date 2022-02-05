package airShipping;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
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

	@Agent
	private IInternalAccess agent;

	static JFrame frame;

	static JPanel panel;

	private Plane plane;

	private Map<String, Plane> planeList = new HashMap<>();

	private Map<String, JProgressBar> barList = new HashMap<>();

	private Map<String, JLabel> profitList = new HashMap<>();

	private IComponentStep<Void> simulationstep;

	private Airports airports = new Airports();

	HashMap<IVector2, Image> airportsWeather = new HashMap<>();

	private long timestamp;

	private double counter;

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
			panel.setBounds(0, 610, 1000, 150);
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
		AirportsWeather weathers = new AirportsWeather();
		airportsWeather = weathers.updateAirportsWeather();

		Timer swingtimer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (MapAgent.this) {
					Container pane = frame.getContentPane();

					BufferedImage img = new BufferedImage(pane.getWidth(), pane.getHeight(),
							BufferedImage.TYPE_4BYTE_ABGR_PRE);

					Graphics2D g = (Graphics2D) img.getGraphics();

					// grid
					Image bg = Toolkit.getDefaultToolkit().getImage("map.jpg");
					g.drawImage(bg, 0, 0, pane.getWidth(), (pane.getHeight() - 100), null);

					// Airport A
					double w1 = 84;
					double h1 = 51;
					double x1 = 1.7 * pane.getWidth() * 0.04;
					x1 = x1 - (w1 / 2);
					double y1 = 7.9 * pane.getHeight() * 0.04;
					y1 = y1 - (h1 / 2);
					Image airp1 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp1, (int) x1, (int) y1, (int) w1, (int) h1, null);

					// Airport A text
					Font f1 = new Font("Arial", Font.PLAIN, 12);
					g.setColor(Color.black);
					g.setFont(f1);
					String s = "Airport A";
					g.drawString(s, (int) x1, (int) (y1 - 10));

					// Airport A weather
					Image airp1_weather = airportsWeather.get(airportA);
					g.drawImage(airp1_weather, (int) (x1 + 20), (int) (y1 + 50), 50, 50, null);

					// Airport B
					double w2 = 84;
					double h2 = 51;
					double x2 = airportB.getXAsDouble() * pane.getWidth() * 0.04;
					x2 = x2 - (w2 / 2);
					double y2 = airportB.getYAsDouble() * pane.getHeight() * 0.04;
					y2 = y2 - (h2 / 2);
					Image airp2 = Toolkit.getDefaultToolkit().getImage("Airport2.png");
					g.drawImage(airp2, (int) x2, (int) y2, (int) w2, (int) h2, null);

					// airport B text
					g.setColor(Color.black);
					g.setFont(f1);
					String s2 = "Airport B";
					g.drawString(s2, (int) x2, (int) (y2 - 10));

					// airport B weather
					Image airp2_weather = airportsWeather.get(airportB);
					g.drawImage(airp2_weather, (int) (x2 + 20), (int) (y2 + 50), 50, 50, null);

					// airport C
					double w3 = 84;
					double h3 = 51;
					double x3 = airportC.getXAsDouble() * pane.getWidth() * 0.04;
					x3 = x3 - (w3 / 2);
					double y3 = airportC.getYAsDouble() * pane.getHeight() * 0.04;
					y3 = y3 - (h2 / 2);
					Image airp3 = Toolkit.getDefaultToolkit().getImage("Airport3.png");
					g.drawImage(airp3, (int) x3, (int) y3, (int) w3, (int) h3, null);

					// airport C text
					g.setColor(Color.black);
					g.setFont(f1);
					String s3 = "Airport C";
					g.drawString(s3, (int) x3, (int) (y3 - 10));

					// airport C weather
					Image cap3_weather = airportsWeather.get(airportC);
					g.drawImage(cap3_weather, (int) (x3 + 20), (int) (y3 + 50), 50, 50, null);

					// airport D
					double w4 = 84;
					double h4 = 51;
					double x4 = airportD.getXAsDouble() * pane.getWidth() * 0.04;
					x4 = x4 - (w4 / 2);
					double y4 = airportD.getYAsDouble() * pane.getHeight() * 0.04;
					y4 = y4 - (h2 / 2);
					Image airp4 = Toolkit.getDefaultToolkit().getImage("Airport4.png");
					g.drawImage(airp4, (int) x4, (int) y4, (int) w4, (int) h4, null);

					// airport D text
					g.setColor(Color.black);
					g.setFont(f1);
					String s4 = "Airport D";
					g.drawString(s4, (int) x4, (int) (y4 - 10));

					// airport D weather
					Image cap4_weather = airportsWeather.get(airportD);
					g.drawImage(cap4_weather, (int) (x4 + 20), (int) (y4 + 50), 50, 50, null);

					// airport E
					double w5 = 75;
					double h5 = 50;
					double x5 = airportE.getXAsDouble() * pane.getWidth() * 0.04;
					x5 = x5 - (w5 / 2);
					double y5 = airportE.getYAsDouble() * pane.getHeight() * 0.04;
					y5 = y5 - (h5 / 2);
					Image airp5 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp5, (int) x5, (int) y5, (int) w5, (int) h5, null);

					// airport E text
					g.setColor(Color.black);
					g.setFont(f1);
					String s5 = "Airport E";
					g.drawString(s5, (int) x5, (int) (y5 - 10));

					// airport E weather
					Image cap5_weather = airportsWeather.get(airportE);
					g.drawImage(cap5_weather, (int) (x5 + 20), (int) (y5 + 50), 50, 50, null);

					// airport F
					double w6 = 75;
					double h6 = 50;
					double x6 = airportF.getXAsDouble() * pane.getWidth() * 0.04;
					x6 = x6 - (w6 / 2);
					double y6 = airportF.getYAsDouble() * pane.getHeight() * 0.04;
					y6 = y6 - (h6 / 2);
					Image airp6 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp6, (int) x6, (int) y6, (int) w6, (int) h6, null);

					// airport F text
					g.setColor(Color.black);
					g.setFont(f1);
					String s6 = "Airport F";
					g.drawString(s6, (int) x6, (int) (y6 - 10));

					// airport F weather
					Image cap6_weather = airportsWeather.get(airportF);
					g.drawImage(cap6_weather, (int) (x6 + 20), (int) (y6 + 50), 50, 50, null);

					// Airport G
					double w7 = 75;
					double h7 = 50;
					double x7 = airportG.getXAsDouble() * pane.getWidth() * 0.04;
					x7 = x7 - (w7 / 2);
					double y7 = airportG.getYAsDouble() * pane.getHeight() * 0.04;
					y7 = y7 - (h7 / 2);
					Image airp7 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp7, (int) x7, (int) y7, (int) w7, (int) h7, null);

					// airport G text
					g.setColor(Color.black);
					g.setFont(f1);
					String s7 = "Airport G";
					g.drawString(s7, (int) x7, (int) (y7 - 10));

					// airport G weather
					Image cap7_weather = airportsWeather.get(airportG);
					g.drawImage(cap7_weather, (int) (x7 + 20), (int) (y7 + 50), 50, 50, null);

					// airport H
					double w8 = 75;
					double h8 = 50;
					double x8 = airportH.getXAsDouble() * pane.getWidth() * 0.04;
					x8 = x8 - (w8 / 2);
					double y8 = airportH.getYAsDouble() * pane.getHeight() * 0.04;
					y8 = y8 - (h8 / 2);
					Image airp8 = Toolkit.getDefaultToolkit().getImage("Airport1.png");
					g.drawImage(airp8, (int) x8, (int) y8, (int) w8, (int) h8, null);

					// airport H text
					g.setColor(Color.black);
					g.setFont(f1);
					String s8 = "Airport H";
					g.drawString(s8, (int) x8, (int) (y8 - 10));

					// airport H weather
					Image cap8_weather = airportsWeather.get(airportH);
					g.drawImage(cap8_weather, (int) (x8 + 20), (int) (y8 + 50), 50, 50, null);

					// Plane
					if (plane != null) {
						for (Plane plane : planeList.values()) {
							double w = 73;
							double h = 23;
							double x = plane.getCurrentPosition().getXAsDouble() * pane.getWidth() * 0.04;
							x = x - (w / 2);
							double y = plane.getCurrentPosition().getYAsDouble() * pane.getHeight() * 0.04;
							y = y - (h / 2);

							Image airplane = Toolkit.getDefaultToolkit().getImage("plane.png");
							g.drawImage(airplane, (int) x, (int) y, (int) w, (int) h, null);

							// plane text, shows number of parcels in plane
							g.setColor(Color.black);
							g.setFont(f1);
							String capPlane = plane.getNumberOfParcelsLoaded() + " parcels in plane: " + plane.getId();
							g.drawString(capPlane, (int) x, (int) (y + 70));

							JProgressBar bar = barList.get(plane.getId());
							if (bar != null) {
								bar.setValue(plane.getOccupacyRate());
							}

							JLabel label = profitList.get(plane.getId());
							if (label != null) {
								double accumulatedProfit = plane.getAccumulatedProfit();
								DecimalFormat df = new DecimalFormat("#.#");
								String str_aProfit = df.format(accumulatedProfit);
								str_aProfit = str_aProfit + " €";
								label.setText(str_aProfit);
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
					long currentTimestamp = System.currentTimeMillis();
					double deltaseconds = (currentTimestamp - timestamp) / 1000.0;
					counter += deltaseconds;

					if (counter > 8) {
						airportsWeather = weathers.updateAirportsWeather();
						counter = 0;
					}

					if (plane != null) {
						for (Plane plane : planeList.values()) {
							IVector2 startPos = plane.getCurrentPosition();
							Image startAirportWeather = airportsWeather.get(startPos);
							if (!(startAirportWeather == weathers.getWeather().get(5)) && (!plane.hasArrived())) {
								if (plane.getNumberOfParcelsLoaded() == 0) {
									plane.loadParcel();
								}
								plane.updatePos(1);
							}
							if (plane.hasArrived()) {
								if (plane.getTargetArrived() != null && !plane.getTargetArrived().isDone()) {
									plane.getTargetArrived().setResult(null);
									plane.setTargetArrived(null);
									plane.unloadParcel();
									plane.calculateAccumulatedProfit();
									plane.setStartAirport(plane.getCurrentPosition().copy());
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
		timestamp = System.currentTimeMillis();
		agent.scheduleStep(simulationstep);

		return new Future<Void>();

	}

	public IFuture<Void> createPlane(String id, IVector2 startPosition, int capacity) {

		synchronized (this) {

			plane = new Plane(id, startPosition, capacity);
			System.out.println("A plane is created: " + id);
			System.out.println("Plane's start position is: " + startPosition);
			plane.loadParcel();

			planeList.put(id, plane);

			JLabel plane_occupacy_text = new JLabel();
			JLabel plane_accumulated_profit_text = new JLabel();
			JProgressBar plane_bar = new JProgressBar();
			JLabel plane_profit = new JLabel();

			plane_occupacy_text.setPreferredSize(new Dimension(280, 20));
			plane_accumulated_profit_text.setPreferredSize(new Dimension(160, 20));
			plane_profit.setPreferredSize(new Dimension(150, 20));
			plane_bar.setPreferredSize(new Dimension(250, 20));
			panel.add(plane_occupacy_text);
			panel.add(plane_bar);
			panel.add(plane_accumulated_profit_text);
			panel.add(plane_profit);

			plane_occupacy_text.setText(plane.getId() + " -  Current occupacy: ");
			plane_bar.setValue(plane.getOccupacyRate());
			plane_bar.setStringPainted(true);
			barList.put(plane.getId(), plane_bar);
			plane_accumulated_profit_text.setText("   Accumulated profit: ");
			profitList.put(plane.getId(), plane_profit);

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

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}

package airShipping;

import java.util.HashMap;
import java.util.Map;
import java.awt.Image;
import java.awt.Toolkit;

import jadex.extension.envsupport.math.IVector2;

public class AirportsWeather {

	private Map<Integer, Image> weathers = new HashMap<>();
	Image sunnyIcon = Toolkit.getDefaultToolkit().getImage("sun.png");
	Image sunshineIcon = Toolkit.getDefaultToolkit().getImage("sunshine.png");
	Image snowIcon = Toolkit.getDefaultToolkit().getImage("snowflake.png");
	Image rainyIcon = Toolkit.getDefaultToolkit().getImage("rainy.png");
	Image cloudyIcon = Toolkit.getDefaultToolkit().getImage("clouds.png");
	Image stormIcon = Toolkit.getDefaultToolkit().getImage("storm.png");

	public AirportsWeather() {
		weathers.put(0, sunnyIcon);
		weathers.put(1, sunshineIcon);
		weathers.put(2, snowIcon);
		weathers.put(3, rainyIcon);
		weathers.put(4, cloudyIcon);
		weathers.put(5, stormIcon);
	}

	public Map<Integer, Image> getWeather() {
		return weathers;
	}

	public HashMap<IVector2, Image> updateAirportsWeather() {
		Airports airports = new Airports();
		HashMap<IVector2, Image> airportsWeather = new HashMap<>();
		airportsWeather.put(airports.getAirportA(), generateRandomWeather());
		airportsWeather.put(airports.getAirportB(), generateRandomWeather());
		airportsWeather.put(airports.getAirportC(), generateRandomWeather());
		airportsWeather.put(airports.getAirportD(), generateRandomWeather());
		airportsWeather.put(airports.getAirportE(), generateRandomWeather());
		airportsWeather.put(airports.getAirportF(), generateRandomWeather());
		airportsWeather.put(airports.getAirportG(), generateRandomWeather());
		airportsWeather.put(airports.getAirportH(), generateRandomWeather());
		return airportsWeather;
	}

	public Image generateRandomWeather() {
		int weatherID = (int) (Math.random() * weathers.size());
		Image currentWeather = weathers.get(weatherID);
		return currentWeather;
	}
}

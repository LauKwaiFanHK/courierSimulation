package airShipping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jadex.extension.envsupport.math.IVector2;

public class AirportsWeather {

	private Map<Integer, String> weathers = new HashMap<>();

	public AirportsWeather() {
		weathers.put(0, "sunny");
		weathers.put(1, "cloudy");
		weathers.put(2, "storm");
	}

	public Map<Integer, String> getWeather() {
		return weathers;
	}

	public HashMap<IVector2, String> updateAirportsWeather() {
		Airports airports = new Airports();
		HashMap<IVector2, String> airportsWeather = new HashMap<>();
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

	public String generateRandomWeather() {
		int weatherID = (int) (Math.random() * weathers.size());
		String currentWeather = weathers.get(weatherID);
		return currentWeather;
	}
}

package io.tubar.distance;

import io.tubar.data.Bar;
import io.tubar.distance.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class DistanceCalculatorTest {

	@Test
	public void testgetDistanceSingleSingle() throws ClientProtocolException, IOException {
		Bar origin = new Bar(1, "Olivarius", 49.033964, 2.067468);

		Bar destination = new Bar(2, "Cafe de la Presse", 48.850312, 2.368967);
		
		DistanceCalculator calculator = new DistanceCalculator();
		
		Long distance = calculator.getDistance(origin, origin, "json");
		
		assert distance == 0;
		
		distance = calculator.getDistance(origin, destination, "json");
		
		assert distance != 0;
	}
	
	@Test
	public void testgetDistanceSingleMutliple() throws ClientProtocolException, IOException {
		Bar origin = new Bar(1, "Olivarius", 49.033964, 2.067468);

		Bar destination1 = new Bar(2, "Cafe de la Presse", 48.850312, 2.368967);
		Bar destination2 = new Bar(3, "Le SISISI", 45.777466, 3.086772);
		
		ArrayList<Bar> destinations = new ArrayList<Bar>();
		
		destinations.add(destination1);
		destinations.add(destination2);
		
		DistanceCalculator calculator = new DistanceCalculator();
		
		ArrayList<Long> distance = calculator.getDistance(origin, destinations, "json");
		
		assert distance.size() == 2;
	}

}

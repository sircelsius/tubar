package io.tubar.distance;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import io.tubar.data.Bar;

public interface DistanceCalculatorInterface {
	/**
	 * Get the distance between two bars using the Google Maps API
	 * @param origin the origin Bar
	 * @param destination the destination Bar
	 * @param output the output format (either json or xml)
	 * @return the distance between the two bars, in meters
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Long getDistance(Bar origin, Bar destination, String output) throws ClientProtocolException, IOException;
	
	/**
	 * Get the distances between an origin Bar and a List of destination bars
	 * @param origin the origin Bar
	 * @param destinations the List of destination Bars
	 * @param output the output format (either json or xml)
	 * @return the List of distances to the destination bars
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public ArrayList<Long> getDistance(Bar origin, ArrayList<Bar> destinations, String output) throws ClientProtocolException, IOException;
}

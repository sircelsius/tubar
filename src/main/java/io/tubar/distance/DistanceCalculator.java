package io.tubar.distance;

import io.tubar.data.Bar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DistanceCalculator implements DistanceCalculatorInterface {

	private String base = "https://maps.googleapis.com/maps/api/distancematrix/";
	
	private String key = null;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public Long getDistance(Bar origin, Bar destination, String output) throws ClientProtocolException, IOException {
		String url = requestBuilder(origin, destination, "json");
		String requestResult = executeRequest(url);
		
		// JSON parsing for a single distance
		JSONObject jsonObj = parseRequestResult(requestResult);
		
		JSONArray rows = (JSONArray) jsonObj.get("rows");
		
		JSONObject elementsObj = (JSONObject) rows.get(0);
		
		JSONArray elements = (JSONArray) elementsObj.get("elements");
		
		JSONObject distanceObj = (JSONObject) elements.get(0);
		
		JSONObject distance = (JSONObject) distanceObj.get("distance");
		
		Long distanceValue = (Long) distance.get("value");
		
		return distanceValue;
	}

	public ArrayList<Long> getDistance(Bar origin,
			ArrayList<Bar> destinations, String output) throws ClientProtocolException, IOException {
		
		ArrayList<Long> res = new ArrayList<Long>();
		String url = requestBuilder(origin, destinations, "json");
		String requestResult = executeRequest(url);
		
		// JSON parsing for a single distance
		JSONObject jsonObj = parseRequestResult(requestResult);
		
		if( (String) jsonObj.get("status").toString() != "OK"){
			System.out.println("ERROR - Querying Google Maps Distance Matrix API failed.");
			System.out.println("Error code: " + jsonObj.get("status"));
		}
		
		JSONArray rows = (JSONArray) jsonObj.get("rows");
		
		JSONObject elementsObj = (JSONObject) rows.get(0);
		
		JSONArray elements = (JSONArray) elementsObj.get("elements");
		
		JSONObject distanceObj = null;
		
		JSONObject distance = null;
		
		Long distanceValue = null;
		
		for (int i = 0; i < elements.size(); i++) {
			distanceObj = (JSONObject) elements.get(i);
			
			distance = (JSONObject) distanceObj.get("distance");
			
			distanceValue = (Long) distance.get("value");
			
			res.add(distanceValue);
		}
		
		return res;
	}
	
	private String requestBuilder(Bar origin, Bar destination, String output){
		String res = base + output + "?";
		
		String origins = "origins=" + origin.getLat().toString() + "," + origin.getLon().toString();
		
		String destinations = "destinations=" + destination.getLat().toString() + "," + destination.getLon().toString();
		
		String mode = "mode=walking";
		
		String delimiter = "&";
		
		res += origins + delimiter + destinations + delimiter + mode;
		
		getAPIKey();
		
		if(key != null){
			res+= delimiter + key;
		}
		else{
			System.out.println("WARNING - the env variable GOOGLE_DISTANCE_MATRIX_KEY is not set. Set this variable to avoid OVER_QUERY_LIMIT status codes when querying the Google Maps Distance Matrix API");
		}
		
		return res;
	}
	
	private String requestBuilder(Bar origin, ArrayList<Bar> destinations, String output){
		String res = base + output + "?";
		
		String origins = "origins=" + origin.getLat().toString() + "," + origin.getLon().toString();
		
		String destinationsString = "destinations=";
		
		for(Bar destination : destinations){
			// we need to add an encoded pipe at the end of each destination
			destinationsString += destination.getLat().toString() + "," + destination.getLon().toString() + "%7C";
		}
		
		// remove the trailing pipe from destinationsString
		destinationsString = destinationsString.substring(0, destinationsString.length() - 3);
		
		String mode = "mode=walking";
		
		String delimiter = "&";
		
		res += origins + delimiter + destinationsString + delimiter + mode;
		
		getAPIKey();
		
		if(key != null){
			res+= delimiter + key;
		}
		else{
			System.out.println("WARNING - the env variable GOOGLE_DISTANCE_MATRIX_KEY is not set. Set this variable to avoid OVER_QUERY_LIMIT status codes when querying the Google Maps Distance Matrix API");
		}
		
		return res;
	}
	
	private String executeRequest(String url) throws ClientProtocolException, IOException{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
	 
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));
	 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return result.toString();
	}
	
	private JSONObject parseRequestResult(String requestResult){
		JSONObject jsonObj = null;
		
		JSONParser parser = new JSONParser();
		try {
			jsonObj = (JSONObject) parser.parse(requestResult);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObj;
	}
	
	private void getAPIKey(){
		this.key = System.getenv("GOOGLE_DISTANCE_MATRIX_KEY");
	}

}

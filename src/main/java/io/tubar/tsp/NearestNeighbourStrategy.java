package io.tubar.tsp;

import io.tubar.data.Bar;
import io.tubar.distance.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NearestNeighbourStrategy implements TSPInitiationStrategy{
	
	DistanceCalculator distanceCalculator = new DistanceCalculator();
	
	private long executionTime = 0;
	
	public ArrayList<Bar> execute(ArrayList<Bar> bars){
		long startTime = System.nanoTime(); 
		
		ArrayList<Bar> result = new ArrayList<Bar>();
		
		ArrayList<Bar> tmp = bars;
		
		int size = bars.size();
		
		if(size == 0){
			return result;
		}
		else{
			int start = getRandom(0, bars.size()-1);
			
			int position = start;
			
			Bar current = bars.get(start);
			
			result.add(current);
			
			tmp.remove(position);
			
			ArrayList<Long> distances;
			
			// iterate over the temporary ArrayList<Bar> to get
			// the next closest Bar and add it to the result
			while(!tmp.isEmpty()){
				try {
					distances = distanceCalculator.getDistance(current, tmp, "json");
				} catch (IOException e) {
					e.printStackTrace();
					return new ArrayList<Bar>();
				}
				
				position = getMinimumIndex(distances);
				
				current = tmp.get(position);
				
				result.add(current);
				
				tmp.remove(position);
			}
			
			long endTime = System.nanoTime();
			
			executionTime = endTime - startTime;
			
			return result;
		}
	}
	
	/**
	 * Get the minimum value in a ArrayList<Integer>
	 * @param distances the list of which we want the minimum
	 * @return the minimum value of the list parameter
	 */
	private int getMinimumIndex(ArrayList<Long> distances){
		int result = 0;
		
		Long current = distances.get(0);
		
		for(int i = 0; i < distances.size(); i++){
			if(distances.get(i) < current){
				current = distances.get(i);
				result = i;
			}
		}
		
		return result;
	}
	
	public long getExecutionTime(){
		return executionTime;
	}
	
	private int getRandom(int min, int max){
		Random tmpRand = new Random();
		
		int res = tmpRand.nextInt((max-min) + 1) + min;
		
		return res;
	}
}

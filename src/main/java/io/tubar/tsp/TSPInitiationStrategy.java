package io.tubar.tsp;

import io.tubar.data.Bar;
import java.util.ArrayList;

public interface TSPInitiationStrategy {
	/**
	 * Calculate an initial solution
	 * @param bars the list of bars to calculate the TSP on
	 * @return the ArrayList<Bar> initial solution
	 */
	public ArrayList<Bar> execute(ArrayList<Bar> bars);
	
	/**
	 * Get the execution time of the execute method
	 * @return the execution time
	 */
	public long getExecutionTime();
}
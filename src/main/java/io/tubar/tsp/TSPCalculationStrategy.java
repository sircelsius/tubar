package io.tubar.tsp;

import io.tubar.data.Bar;

import java.util.ArrayList;

/**
 * Strategy Design Pattern for TSP tour generation
 * @author Marc du Boucheron
 *
 */
public interface TSPCalculationStrategy {
	/**
	 * Given an initialSolution, optimize that solution.
	 * @param initialSolution
	 * @return The optimized solution
	 */
	public ArrayList<Bar> execute(ArrayList<Bar> initialSolution);
	
	/**
	 * When a solution is calculated, get the executionTime.
	 * Used for performance calculations. Returns 0 if the algorithm
	 * wasn't run.
	 * @return the execution time
	 */
	public long getExecutionTime();
}

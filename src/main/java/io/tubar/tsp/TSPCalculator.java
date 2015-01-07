package io.tubar.tsp;

import io.tubar.data.Bar;

import java.util.ArrayList;

/**
 * Exposes the different strategies available for calculating a tour.
 * @author Marc du Boucheron
 *
 */
public class TSPCalculator {
	/**
	 * The Calculation Strategy (transforms an initial solution into an
	 * optimized solution).
	 * Example of calculation strategy: Simulated Annealing
	 */
	private TSPCalculationStrategy calculationStrategy;
	
	/**
	 * The Initiation strategy (from a randomly ordered list of Bars, get
	 * an "acceptable" tour before optimization)
	 * Example of initiation strategy: Nearest Neighbours
	 */
	private TSPInitiationStrategy initiationStrategy;
	
	/**
	 * Default constructor for the TSPCalculator Class
	 * @param calculationStrategy a io.tubar.tsp.TSPCalculationStrategy Object
	 * @param initiationStrategy a io.tubar.tsp.TSPInitiationStrategy Object
	 */
	public TSPCalculator(TSPCalculationStrategy calculationStrategy, TSPInitiationStrategy initiationStrategy) {
		super();
		this.calculationStrategy = calculationStrategy;
		this.initiationStrategy = initiationStrategy;
	}
	
	/**
	 * Change the calculationStrategy of the TSPCalculator
	 * @param strategy a TSPCalculationStrategy Object
	 */
	public void changeCalculationStrategy(TSPCalculationStrategy strategy){
		this.calculationStrategy = strategy;
	}
	
	/**
	 * Change the initiationStrategy of the TSPCalculator.
	 * @param strategy a TSPInitiationStrategy Object
	 */
	public void changeInitiationStrategy(TSPInitiationStrategy strategy){
		this.initiationStrategy = strategy;
	}
	
	/**
	 * Execute the initiationStrategy's main method
	 * @param bars
	 * @return an initial solution
	 */
	public ArrayList<Bar> initiateTSP(ArrayList<Bar> bars){
		return initiationStrategy.execute(bars);
	}
	
	/**
	 * Execute the calculationStrategy's main method
	 * @param initial
	 * @return an optimized solution
	 */
	public ArrayList<Bar> calculateTSP(ArrayList<Bar> initial){
		return calculationStrategy.execute(initial);
	}
	
	/**
	 * Get the optimization execution time
	 * @return the optimization time
	 */
	public long getCalculationTime(){
		return calculationStrategy.getExecutionTime();
	}
	
	/**
	 * Get the initiation execution time
	 * @return the initiation time
	 */
	public long getInitiationTime(){
		return initiationStrategy.getExecutionTime();
	}

	/**
	 * @return the calculationStrategy
	 */
	public TSPCalculationStrategy getCalculationStrategy() {
		return calculationStrategy;
	}

	/**
	 * @return the initiationStrategy
	 */
	public TSPInitiationStrategy getInitiationStrategy() {
		return initiationStrategy;
	}
	
	
}

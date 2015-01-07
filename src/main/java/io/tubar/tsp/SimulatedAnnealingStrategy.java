package io.tubar.tsp;

import io.tubar.data.Bar;
import io.tubar.data.BarService;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealingStrategy implements TSPCalculationStrategy {
	
	private BarService barService = new BarService();
	
	private Double temperature = (double) 99;
	
	private Double minTemperature = (double) 50;
	
	private Double coolingFactor = 0.9;
	
	private int numberOfIterations = 0;
	
	private int numberOfSuccessfulIterations = 0;
	
	private int numberOfSavedIterations = 0;
	
	private long executionTime = 0;

	@Override
	public ArrayList<Bar> execute(ArrayList<Bar> initialSolution) {
		resetVars();
		
		long startTime = System.nanoTime();
		
		Long initialLength = barService.getTotalLength(initialSolution);
		
		Long currentLength = initialLength;
		
		Long newLength = currentLength;
		
		ArrayList<Bar> currentSolution = initialSolution;
		
		ArrayList<Bar> newSolution = currentSolution;
		
		while(temperature > minTemperature){
			numberOfIterations++;
			
			newSolution= randomSwap(currentSolution);
			
			newLength = barService.getTotalLength(newSolution);
			
			if(newLength < currentLength){
				// if the new solution is better than the current,
				// set it as the current, lower the temperature and start again
				
				numberOfSuccessfulIterations++;
				
				currentSolution = newSolution;
				currentLength = newLength;
				
				temperature = temperature*coolingFactor;
			}
			else{
				if(solutionSaver(temperature)){
					// if the new solution is saved, set it as the current,
					// lower the temperature and start again
					numberOfSavedIterations++;
					
					currentSolution = newSolution;
					currentLength = newLength;
					
					temperature = temperature*coolingFactor;
				}
			}
		}
		
		long endTime = System.nanoTime();
		
		executionTime = endTime - startTime;
		
		if(currentLength > initialLength){
			return initialSolution;
		}
		else{
			return currentSolution;
		}
	}
	
	private ArrayList<Bar> randomSwap(ArrayList<Bar> initial){
		ArrayList<Bar> res = initial;
		
		int positionOne = getRandom(0, initial.size() -1);
		
		int positionTwo = getRandom(0, initial.size() -1);
		
		// make sure that we get two different indices
		while(positionOne == positionTwo){
			positionTwo = getRandom(0, initial.size()-1);
		}
		
		// swap the bars
		Bar tmpOne = initial.get(positionOne);
		
		Bar tmpTwo = initial.get(positionTwo);
		
		res.set(positionOne, tmpTwo);
		
		res.set(positionTwo, tmpOne);
		
		return res;
	}
	
	private int getRandom(int min, int max){
		Random tmpRand = new Random();
		
		int res = tmpRand.nextInt((max-min) + 1) + min;
		
		return res;
	}
	
	private boolean solutionSaver(Double t){
		Double p = Math.random();
		
		if(t == 0){
			return false;
		}
		
		Double criteria = (Math.exp(1 - (t/minTemperature))) / (Math.exp(0));
		
		return p > criteria;
	}
	
	private void resetVars(){
		temperature = (double) 99;
		
		minTemperature = (double) 50;
		
		coolingFactor = 0.9;
		
		numberOfIterations = 0;
		
		numberOfSuccessfulIterations = 0;
		
		numberOfSavedIterations = 0;
		
		executionTime = 0;
	}
	
	public long getExecutionTime(){
		return executionTime;
	}
	
	public int getNumberOfIterations(){
		return numberOfIterations;
	}
	
	public int getNumberOfSuccessfullIterations(){
		return numberOfSuccessfulIterations;
	}
	
	public int getNumberOfSavedIterations(){
		return numberOfSavedIterations;
	}
}

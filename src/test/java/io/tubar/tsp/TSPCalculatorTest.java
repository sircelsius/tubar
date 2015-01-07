package io.tubar.tsp;

import io.tubar.data.Bar;
import io.tubar.data.BarService;
import io.tubar.tsp.NearestNeighbourStrategy;
import io.tubar.tsp.SimulatedAnnealingStrategy;
import io.tubar.tsp.TSPCalculationStrategy;
import io.tubar.tsp.TSPCalculator;
import io.tubar.tsp.TSPInitiationStrategy;

import java.util.ArrayList;

import org.junit.Test;

public class TSPCalculatorTest {

	@Test
	public void test() {
		TSPCalculationStrategy calcStrategy = new SimulatedAnnealingStrategy();
		
		TSPInitiationStrategy initStrategy = new NearestNeighbourStrategy();
		
		BarService barService = new BarService();
		
		TSPCalculator calculator = new TSPCalculator(calcStrategy, initStrategy);
		
		Bar bar1 = new Bar(1, "Olivarius", 49.033964, 2.067468);
		Bar bar2 = new Bar(2, "Cafe de la Presse", 48.850312, 2.368967);
		Bar bar3 = new Bar(3, "Le SISISI", 45.777466, 3.086772);
		Bar bar4 = new Bar(4, "Pépinière 27", 48.85861, 2.373063);
		Bar bar5 = new Bar(5, "Café de la Place", 48.892248, 2.345054);
		
		ArrayList<Bar> orig = new ArrayList<Bar>();
		
		orig.add(bar1);
		orig.add(bar2);
		orig.add(bar3);
		orig.add(bar4);
		orig.add(bar5);
		
		System.out.println("TSPCalculator");
		
		long origLength = barService.getTotalLength(orig);
		
		System.out.println("Original length (m): " + origLength);
		
		System.out.println("Initiating...");
		ArrayList<Bar> init = calculator.initiateTSP(orig);
		
		System.out.println("Initiation phase finished.");
		for (int i = 0; i < init.size(); i++) {
			System.out.println("Bar " + i + ": " + init.get(i).getName());
		}
		
		long initTime = calculator.getInitiationTime() / 1000000;
		
		long initLength = barService.getTotalLength(init);
		
		System.out.println("Initial length (m): " + initLength);
		
		System.out.println("Initiation duration (ms): " + initTime);
		
		System.out.println("Optimizing...");
		
		ArrayList<Bar> res = calculator.calculateTSP(init);
		
		for (int i = 0; i < res.size(); i++) {
			System.out.println("Bar " + i + ": " + res.get(i).getName());
		}
		
		long calcTime = calculator.getCalculationTime() / 1000000;
		
		System.out.println("Optimization time (ms): " + calcTime);
		
		long resLength = barService.getTotalLength(res);
		
		System.out.println("Optimal distance: " + resLength);
	}

}

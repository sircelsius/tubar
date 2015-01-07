package io.tubar.tsp;

import io.tubar.data.Bar;
import io.tubar.data.BarService;
import io.tubar.tsp.SimulatedAnnealingStrategy;

import java.util.ArrayList;

import org.junit.Test;

public class SimulatedAnnealingStrategyTest {

	@Test
	public void testExecute() {
		
		BarService barService = new BarService();
		
		SimulatedAnnealingStrategy strategy = new SimulatedAnnealingStrategy();
		
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
		
		Long initialLength = barService.getTotalLength(orig);
		
		ArrayList<Bar> res = strategy.execute(orig);
		
		System.out.println("Distance initiale (m): " + initialLength);
		
		Long finalLength = barService.getTotalLength(res);
		
		System.out.println("Distance finale (m): " + finalLength);
		
		Long executionTime = strategy.getExecutionTime();
		executionTime = executionTime / 1000000;
		
		System.out.println("Temps d'optimisation (ms): " + executionTime);
		
		Long optimizationDistance = initialLength - finalLength;
		float optimizationPercentage = ( (float) optimizationDistance) / ( (float) initialLength);
		
		System.out.println("Distance d'optimisation: " + optimizationDistance + " ( " + optimizationPercentage*100 + "% )");
		
		int iterations = strategy.getNumberOfIterations();
		int successfull = strategy.getNumberOfSuccessfullIterations();
		int saved = strategy.getNumberOfSavedIterations();
		
		float percentageSuccessfull = 100 - (((float) (iterations - successfull)) / ((float) iterations)) * 100;
		
		float percentageSaved = 100 - (((float) ((iterations-successfull) - saved)) / ((float) (iterations - successfull))) * 100;
		
		System.out.println("Nombre d'itérations réussies: " + successfull + " / " + iterations + " ( " + percentageSuccessfull + "% )");
		
		System.out.println("Nombre d'itérations sauvées: " + saved + " / " + (iterations - successfull) + " ( " + percentageSaved + "% )");
		
		for (int i = 0; i < res.size(); i++) {
			System.out.println("Bar " + i + ": " + res.get(i).getName());
		}
	}

}

package io.tubar.tsp;

import io.tubar.data.Bar;
import io.tubar.data.BarService;

import java.util.ArrayList;

import org.junit.Test;

public class NearestNeighbourStrategyTest {

	@Test
	public void test() {
		NearestNeighbourStrategy strategy = new NearestNeighbourStrategy();
		
		BarService barService = new BarService();
		
		Bar bar1 = new Bar(1, "Olivarius", 49.033964, 2.067468);
		Bar bar2 = new Bar(2, "Cafe de la Presse", 48.850312, 2.368967);
		Bar bar3 = new Bar(3, "Le SISISI", 45.777466, 3.086772);
		Bar bar4 = new Bar(4, "Pépinière 27", 48.85861, 2.373063);
		
		ArrayList<Bar> orig = new ArrayList<Bar>();
		
		orig.add(bar1);
		orig.add(bar2);
		orig.add(bar3);
		orig.add(bar4);
		
		Long origLength = barService.getTotalLength(orig);
		
		ArrayList<Bar> res = strategy.execute(orig);
		
		Long finalLength = barService.getTotalLength(res);
		
		System.out.println("Original length - Initial length = " + (origLength - finalLength));
		
		Long executionTime = strategy.getExecutionTime();
		executionTime = executionTime / 1000000;
		
		System.out.println("Temps d'optimisation (ms): " + executionTime);
		
		for (int i = 0; i < res.size(); i++) {
			System.out.println("Bar " + i + ": " + res.get(i).getName());
		}
	}

}

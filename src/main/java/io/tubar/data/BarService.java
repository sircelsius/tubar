package io.tubar.data;

import io.tubar.distance.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;

@Api(name = "bar",
		version = "v1",
		description = "API to manage Bar objects",
		namespace = @ApiNamespace(ownerDomain = "tubar.io",
									ownerName = "tubar.io",
									packagePath = ""))
public class BarService {
	public static List<Bar> bars = new ArrayList<Bar>();
	
	@ApiMethod(name="add")
	public Bar addBar(@Named("id") Integer id, @Named("name") String name, @Named("lat") Double lat, @Named("lon") Double lon) throws NotFoundException{
		// check if the id is already taken
		int index = bars.indexOf(new Bar(id));
		if(index != -1){
			throw new NotFoundException("Bar already exists");
		}
		
		Bar b =  new Bar(id, name, lat, lon);
		bars.add(b);
		
		return b;
	}
	
	@ApiMethod(name="update")
	public Bar updateBar(Bar b) throws NotFoundException{
		int index = bars.indexOf(b);
		if(index == -1){
			throw new NotFoundException("Bar does not exist");
		}
		
		Bar currentBar = bars.get(index);
		
		currentBar.setName(b.getName());
		currentBar.setLat(b.getLat());
		currentBar.setLon(b.getLon());
		
		return b;
	}
	
	@ApiMethod(name="remove")
	public void removeBar(@Named("id") Integer id) throws NotFoundException{
		int index = bars.indexOf(new Bar(id));
		
		if(index == -1){
			throw new NotFoundException("Bar does not exist");
		}
		
		bars.remove(index);
	}
	
	@ApiMethod(name="list")
	public List<Bar> getBars(){
		return bars;
	}
	
	@ApiMethod(name="getByName", path="getByName")
	public Bar getBarByName(@Named("name") String name) throws NotFoundException{
		Bar result = null;
		
		for(Bar bar : bars){
			if(bar.getName().equals(name)){
				result = bar;
			}
		}
		
		if(result == null){
			throw new NotFoundException("Bar does not exist");
		}
		
		return result;
	}
	
	@ApiMethod(name="getBar")
	public Bar getBar(@Named("id") Integer id) throws NotFoundException{
		int index = bars.indexOf(new Bar(id));
		
		if(index == -1){
			throw new NotFoundException("Bar does not exist");
		}
		
		return bars.get(index);
	}
	
	public Long getTotalLength(ArrayList<Bar> solution){
		DistanceCalculator distanceCalculator = new DistanceCalculator();
		
		Long res = (long) 0;
		
		if(solution.size() == 0){
			return res;
		}
		for (int i = 0; i < solution.size() - 1; i++) {
			try {
				res += distanceCalculator.getDistance(solution.get(i), solution.get(i+1), "json");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return res;
	}
}

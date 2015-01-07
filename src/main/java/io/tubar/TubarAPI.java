package io.tubar;

/**
  * Add your first API methods in this class, or you may create another class. In that case, please
  * update your web.xml accordingly.
 **/
import io.tubar.data.Bar;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

/** An endpoint class we are exposing */
@Api(name = "tubar",
     version = "v1",
     namespace = @ApiNamespace(ownerDomain = "tubar.io",
                                ownerName = "tubar.io",
                                packagePath=""))
public class TubarAPI {

    /** Create a Bar Object.
     * @param Integer id The id of the object to create  
     */
    @ApiMethod(name = "newBar")
    public Bar newBar(@Named("id") Integer id) {
        Bar response = new Bar(id);
        return response;
    }
    
    @ApiMethod(name = "newBarFull")
    public Bar newBarFull(@Named("id") Integer id, @Named("name") String name, @Named("lat") Double lat, @Named("lon") Double lon){
    	Bar response = new Bar(id, name, lat, lon);
    	return response;
    }

}
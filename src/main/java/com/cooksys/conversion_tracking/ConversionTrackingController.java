package com.cooksys.conversion_tracking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.Location;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.tx.TXLocation;
import com.cooksys.conversion_tracking.tx.TXLong;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXShort;

@RestController
//@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.PUT})
@CrossOrigin(methods = RequestMethod.POST)
public class ConversionTrackingController {
	@Autowired
	ConversionTrackingService cts;

	@RequestMapping("/location/{pk}")
	public Location getLocation(@PathVariable Long pk) {
		return cts.readLocation(pk);
	}

	@RequestMapping("/area/{pk}")
	public Area getArea(@PathVariable Long pk) {
		return cts.readArea(pk);
	}
	
	@RequestMapping("/user/{pk}")
	public User getUser(@PathVariable Long pk) {
		return cts.readUser(pk);
	}
	
	@RequestMapping("/locations")
	public List<Location> getLocations() {
		return cts.readLocations();
	}

	@RequestMapping("/areas")
	public List<Area> getAreas() {
		return cts.readAreas();
	}
	
	@RequestMapping("/users")
	public List<User> getUsers() {
		return cts.readUsers();
	}
	
	@RequestMapping("/users/area/{pk}")
	public List<User> getUsersByArea(@PathVariable Long pk) {
		return cts.readUsersByArea(pk);
	}

	@RequestMapping("/users/area/num/{num}")
	public List<User> getUsersByAreaNum(@PathVariable Integer num) {
		return cts.readUsersByArea(num);
	}
	
	@RequestMapping("/area/{pk}/anonymous")
	public TXResponse<Long> getAnonymousVisitsByArea(@PathVariable Long pk) {
		return cts.readAnonymousVisitsByArea(pk);
	}

	@RequestMapping("/area/num/{num}/anonymous")
	public TXResponse<Long> getAnonymousVisitsByAreaNum(@PathVariable Integer num) {
		return cts.readAnonymousVisitsByArea(num);
	}
	
	@RequestMapping("/area/{pk}/rate")
	public TXResponse<Double> processConversionRateByArea(@PathVariable Long pk) {
		return cts.processConversionRateByArea(pk);
	}
	
	@RequestMapping("/area/num/{num}/rate")
	public TXResponse<Double> processConversionRateByAreaNum(@PathVariable Integer num) {
		return cts.processConversionRateByArea(num);
	}
	
	@RequestMapping("/area/num/{num}/hits")
	public TXResponse<Long> getHitsByAreaNum(@PathVariable Integer num) {
		return cts.processHitsByArea(num);
	}

	@RequestMapping(value="/location", method=RequestMethod.POST)
	public Location postLocation(@RequestBody TXLocation location_tx) {
		return cts.createLocation(location_tx);
	}
	
	@RequestMapping(value="/area", method=RequestMethod.POST)
	public Area postArea(@RequestBody Area area) {
		return cts.createArea(area);
	}

	@RequestMapping(value="/anonymous", method=RequestMethod.POST)	
	public void processAnonymousVisit(@RequestBody TXShort short_tx) {
		cts.processAnonymousVisit(short_tx);
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public TXResponse<Boolean> processLogin(@RequestBody TXLong long_tx) {
		return cts.processLogin(long_tx);
	}

	@RequestMapping(value="/user", method=RequestMethod.POST)
	public User postUser(@RequestBody TXLong long_tx) {
		return cts.postUser(long_tx);
	}
	
}

package com.cooksys.conversion_tracking.controller;

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
import com.cooksys.conversion_tracking.model.URL;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.service.AdministrativeService;
import com.cooksys.conversion_tracking.tx.TXLocation;
import com.cooksys.conversion_tracking.tx.TXLong;
import com.cooksys.conversion_tracking.tx.TXURLlong;

@RestController
@CrossOrigin(methods = RequestMethod.POST)
public class AdministrativeController {
	@Autowired
	AdministrativeService as;

	@RequestMapping("/url/{pk}")
	public URL getURL(@PathVariable Long pk) {
		return as.readURL(pk);
	}
	
	@RequestMapping("/location/{pk}")
	public Location getLocation(@PathVariable Long pk) {
		return as.readLocation(pk);
	}

	@RequestMapping("/area/{pk}")
	public Area getArea(@PathVariable Long pk) {
		return as.readArea(pk);
	}
	
	@RequestMapping("/user/{pk}")
	public User getUser(@PathVariable Long pk) {
		return as.readUser(pk);
	}
	
	@RequestMapping("/urls")
	public List<URL> getURLs() {
		return as.readURLs();
	}
	
	@RequestMapping("/locations")
	public List<Location> getLocations() {
		return as.readLocations();
	}

	@RequestMapping("/areas")
	public List<Area> getAreas() {
		return as.readAreas();
	}
	
	@RequestMapping("/users")
	public List<User> getUsers() {
		return as.readUsers();
	}

	@RequestMapping(value="/url", method=RequestMethod.POST)
	public URL postURL(@RequestBody TXURLlong  url_tx) {
		return as.createURL(url_tx);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public User postUser(@RequestBody TXLong long_tx) {
		return as.createUser(long_tx);
	}
	
	@RequestMapping(value="/location", method=RequestMethod.POST)
	public Location postLocation(@RequestBody TXLocation location_tx) {
		return as.createLocation(location_tx);
	}
	
	@RequestMapping(value="/area", method=RequestMethod.POST)
	public Area postArea(@RequestBody Area area) {
		return as.createArea(area);
	}
	
}

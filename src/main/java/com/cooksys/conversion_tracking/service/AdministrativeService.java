package com.cooksys.conversion_tracking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.Location;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.repository.AreaRepository;
import com.cooksys.conversion_tracking.repository.LocationRepository;
import com.cooksys.conversion_tracking.repository.UserRepository;
import com.cooksys.conversion_tracking.tx.TXLocation;
import com.cooksys.conversion_tracking.tx.TXLong;

@Service
public class AdministrativeService {
	@Autowired
	UserRepository ur;

	@Autowired
	LocationRepository lr;
	
	@Autowired
	AreaRepository ar;
	
	public Location readLocation(Long pk) {
		return lr.findOne(pk);
	}
	public Area readArea(Long pk) {
		return ar.findOne(pk);
	}
	public User readUser(Long pk) {
		return ur.findOne(pk);
	}
	
	public List<Location> readLocations() {
		return lr.findAll();
	}
	public List<Area> readAreas() {
		return ar.findAll();
	}
	public List<User> readUsers() {
		return ur.findAll();
	}

	public Location createLocation(TXLocation location_tx) {
		Area a  = ar.findOneByNum(location_tx.getNum());
		if(a == null){
			return null;
		}
		
		Location l = new Location();
		l.setTitle(location_tx.getTitle());
		l.setArea(a);
		return  lr.save(l);
	}
	
	public User createUser(TXLong long_tx) {
		Area a = ar.findOneByNum(long_tx.getNum());
		User u = null;
		
		u = new User();
		u.setName(long_tx.getUsername());
		u.setPassword(long_tx.getPassword());
		u.setArea(a);
		ur.save(u);

		return u;	
	}

	public Area createArea(Area area) {
		return ar.save(area);
	}
	
}

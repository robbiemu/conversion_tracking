package com.cooksys.conversion_tracking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.Location;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.repository.AreaRepository;
import com.cooksys.conversion_tracking.repository.LocationRepository;
import com.cooksys.conversion_tracking.repository.UserRepository;
import com.cooksys.conversion_tracking.tx.TXLocation;
import com.cooksys.conversion_tracking.tx.TXLong;

import static com.cooksys.conversion_tracking.Defs.*;

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
			throw new DataIntegrityViolationException("Area num must exist!");
		}
		
		Location l = new Location();
		l.setVersion(LOCATION_TABLE_VERSION);
		l.setTitle(location_tx.getTitle());
		l.setArea(a);
		return  lr.save(l);
	}
	
	public User createUser(TXLong long_tx) {
		Area a = ar.findOneByNum(long_tx.getNum());
		
		User u = ur.findOneByName(long_tx.getUsername()); // ensure we aren't going to create a record with a null username
		if (long_tx.getUsername() == null) {
			throw new DataIntegrityViolationException("Username cannot be null!");
		}
		if (u != null){
			throw new DataIntegrityViolationException("User with username already exists!");
		}
		
		u = new User();
		u.setVersion(USER_TABLE_VERSION);
		u.setName(long_tx.getUsername());
		u.setPassword(long_tx.getPassword());
		u.setArea(a);
		u.setAdminRights(long_tx.getAdmin());
		ur.save(u);

		return u;	
	}

	public Area createArea(Area area) {
		if(area.getNum() == null) {
			throw new DataIntegrityViolationException("Area num cannot be null!");
		}
		area.setVersion(AREA_TABLE_VERSION);
		return ar.save(area);
	}
	
}

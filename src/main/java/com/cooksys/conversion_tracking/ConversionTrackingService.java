package com.cooksys.conversion_tracking;

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
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXShort;

import java.util.List;

@Service
public class ConversionTrackingService {
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

	public <T> List<User> readUsersByArea(T var) {
		Area a = (var instanceof Long)? ar.findOne((Long) var): ar.findOneByNum((Integer) var);
		return ur.findByArea(a);
	}

	public <T> TXResponse<Long> readAnonymousVisitsByArea(T var) {
		Area a = (var instanceof Long)? ar.findOne((Long) var): ar.findOneByNum((Integer) var);
		
		TXResponse<Long> txr = new TXResponse<>("AnonymousVisitsByArea");
		txr.setField(a.getAnonymousCount());
		return txr;
	}
	
	public <T> TXResponse<Double> processConversionRateByArea(T var) {
		Area a = (var instanceof Long)? ar.findOne((Long) var): ar.findOneByNum((Integer) var);

		Integer converts = ur.countByArea(a);
		
		TXResponse<Double> txr = new TXResponse<>("ConversionRateByArea");
		txr.setField(converts/(a.getAnonymousCount() * 1.0));
		
		return txr;
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

	public void processAnonymousVisit(TXShort short_tx) {
		Area a = ar.findOneByNum(short_tx.getNum());
		a.increment();
		ar.save(a);
	}

	public TXResponse<Boolean> processLogin(TXLong long_tx) {
		Area a = ar.findOneByNum(long_tx.getNum());
		
		TXResponse<Boolean> txr = new TXResponse<>("Login");
		txr.setField(ur.findByNameAndPassword(long_tx.getUsername(), long_tx.getPassword()) != null? true: false);

		if(a != null) {
			a.decrement();
			ar.save(a);
		}

		return txr;
	}

	public User postUser(TXLong long_tx) {
		Area a = ar.findOneByNum(long_tx.getNum());
		User u = null;
		
		if(a != null) {
			u = new User();
			u.setName(long_tx.getUsername());
			u.setPassword(long_tx.getPassword());
			u.setArea(a);
			ur.save(u);
		}
		return u;	
	}

	public Area createArea(Area area) {
		return ar.save(area);
	}

}

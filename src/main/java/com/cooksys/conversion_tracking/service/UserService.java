package com.cooksys.conversion_tracking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.repository.AreaRepository;
import com.cooksys.conversion_tracking.repository.UserRepository;
import com.cooksys.conversion_tracking.tx.TXLong;
import com.cooksys.conversion_tracking.tx.TXResponse;

@Service
public class UserService {
	@Autowired
	UserRepository ur;
	
	@Autowired
	AreaRepository ar;
	
	public <T> List<User> readUsersByArea(T var) {
		Area a = (var instanceof Long)? ar.findOne((Long) var): ar.findOneByNum((Integer) var);
		return ur.findByArea(a);
	}

	public TXResponse<Map<String, Object>> processLogin(TXLong long_tx) {
		TXResponse<Map<String, Object>> txr = new TXResponse<>("Login");
		Map<String,Object> res = new HashMap<>();
		
		User u = ur.findByNameAndPassword(long_tx.getUsername(), long_tx.getPassword());
		if(u == null) {
			res.put("Logged In", false);
		} else {
			res.put("Logged In", true);
			res.put("Admin account", u.getAdminRights());
		}
		txr.setField(res);

		Area a = ar.findOneByNum(long_tx.getNum());
		if(a != null) {
			a.decrement();
			a.incrementUserLogin();
			
			ar.save(a);
		}

		return txr;
	}
	
	/* see administrativeService for new user creation */
	
}

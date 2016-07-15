package com.cooksys.conversion_tracking.service;

import java.util.List;

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

	public TXResponse<Boolean> processLogin(TXLong long_tx) {
		TXResponse<Boolean> txr = new TXResponse<>("Login");
		txr.setField(ur.findByNameAndPassword(long_tx.getUsername(), long_tx.getPassword()) != null? true: false);

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

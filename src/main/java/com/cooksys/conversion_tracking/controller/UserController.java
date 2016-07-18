package com.cooksys.conversion_tracking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.service.UserService;
import com.cooksys.conversion_tracking.tx.TXLong;
import com.cooksys.conversion_tracking.tx.TXResponse;

@RestController
@CrossOrigin(methods = RequestMethod.POST)
public class UserController {
	@Autowired
	UserService us;

	@RequestMapping("/users/area/{pk}")
	public List<User> getUsersByArea(@PathVariable Long pk) {
		return us.readUsersByArea(pk);
	}

	@RequestMapping("/users/area/num/{num}")
	public List<User> getUsersByAreaNum(@PathVariable Integer num) {
		return us.readUsersByArea(num);
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public TXResponse<Map<String, Object>> processLogin(@RequestBody TXLong long_tx) {
		return us.processLogin(long_tx);
	}
	
	/* for register new user see AdministrativeController */
}

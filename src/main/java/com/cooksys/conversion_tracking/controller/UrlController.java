package com.cooksys.conversion_tracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.conversion_tracking.model.URL;
import com.cooksys.conversion_tracking.service.UrlService;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXURLbyURL;
import com.cooksys.conversion_tracking.tx.TXURLshort;

@RestController
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.DELETE})
public class UrlController {
	@Autowired
	UrlService us;
	
	@RequestMapping(value="/url/increment", method=RequestMethod.POST)
	public TXResponse<Boolean> increment(@RequestBody TXURLshort short_tx) {
		return us.increment(short_tx);
	}

	@RequestMapping(value="/url/decrement", method=RequestMethod.POST)
	public TXResponse<Boolean> decrement(@RequestBody TXURLshort short_tx) {
		return us.decrement(short_tx);
	}

	@RequestMapping(value="/url/find", method=RequestMethod.POST)
	public URL findByURL(@RequestBody TXURLbyURL tx) {
		return us.findByURL(tx);
	}

	
/*	@RequestMapping(value="/url/label", method=RequestMethod.DELETE)
	public TXResponse<Boolean> deleteByLabel(@RequestBody TXURLshort short_tx) {
		return us.deleteByLabel(short_tx);
	} */

	@RequestMapping(value="/url/label/{label}", method=RequestMethod.GET)
	public TXResponse<Boolean> deleteByLabel(@PathVariable String label) {
		return us.deleteByLabel(label);
	}
	
}

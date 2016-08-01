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

import com.cooksys.conversion_tracking.Tuple;
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

	@RequestMapping("/url/{label}/tracking/{proratum}")
	public TXResponse<List<List<Long>>> getURLTrackingProRatum(@PathVariable String label, @PathVariable String proratum) {
		return us.readURLTrackingProRatum(label, proratum);
	}	
	
	@RequestMapping("/urls/and/tracking")
	public TXResponse<List<Tuple<List<Long>, URL>>> getURLsWithTracking() {
		return us.readURLsWithTracking();
	}	

	@RequestMapping("/urls/and/tracking/{proratum}")
	public TXResponse<List<Tuple<List<Long>, URL>>> getURLsWithTrackingProRatum(@PathVariable String proratum) {
		return us.readURLsWithTrackingProRatum(proratum);
	}		
	
	@RequestMapping("/urls/and/hits")
	public TXResponse<List<Tuple<Long, URL>>> getURLsWithHits() {
		return us.readURLsWithHits();
	}	

	@RequestMapping("/urls/and/hits/{proratum}")
	public TXResponse<List<Tuple<Long, URL>>> getURLsWithHitsProRatum(@PathVariable String proratum) {
		return us.readURLsWithHitsProRatum(proratum);
	}	
	
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

	@RequestMapping(value="/url/label/{label}/hits", method=RequestMethod.GET)
	public TXResponse<Long> hitCountByLabel(@PathVariable String label) {
		return us.hitCountByLabel(label);
	}
	
	@RequestMapping(value="/url/label/{label}/hits/{proratum}", method=RequestMethod.GET)
	public TXResponse<Long> hitCountByLabelAndProRatum(@PathVariable String label, @PathVariable String proratum) {
		return us.hitCountByLabelAndProRatum(label, proratum);
	}
	
}

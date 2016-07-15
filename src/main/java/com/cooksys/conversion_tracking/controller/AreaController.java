package com.cooksys.conversion_tracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.conversion_tracking.service.AreaService;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXShort;

@RestController
@CrossOrigin(methods = RequestMethod.POST)
public class AreaController {
	@Autowired
	AreaService as;
	
	@RequestMapping("/area/{pk}/anonymous")
	public TXResponse<Long> getAnonymousVisitsByArea(@PathVariable Long pk) {
		return as.readAnonymousVisitsByArea(pk);
	}

	@RequestMapping("/area/num/{num}/anonymous")
	public TXResponse<Long> getAnonymousVisitsByAreaNum(@PathVariable Integer num) {
		return as.readAnonymousVisitsByArea(num);
	}
	
	@RequestMapping("/area/{pk}/rate")
	public TXResponse<Double> processConversionRateByArea(@PathVariable Long pk) {
		return as.processConversionRateByArea(pk);
	}
	
	@RequestMapping("/area/num/{num}/rate")
	public TXResponse<Double> processConversionRateByAreaNum(@PathVariable Integer num) {
		return as.processConversionRateByArea(num);
	}
	
	@RequestMapping("/area/num/{num}/hits")
	public TXResponse<Long> getHitsByAreaNum(@PathVariable Integer num) {
		return as.processHitsByArea(num);
	}
	
	@RequestMapping(value="/anonymous", method=RequestMethod.POST)	
	public void processAnonymousVisit(@RequestBody TXShort short_tx) {
		as.processAnonymousVisit(short_tx);
	}
}

package com.cooksys.conversion_tracking.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.model.Hit;
import com.cooksys.conversion_tracking.model.URL;
import com.cooksys.conversion_tracking.repository.UrlRepository;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXURLbyURL;
import com.cooksys.conversion_tracking.tx.TXURLshort;

@Service
public class UrlService {
	@Autowired
	UrlRepository ur;
	
	public TXResponse<Boolean> increment(TXURLshort short_tx) {
		TXResponse<Boolean> txr = new TXResponse<>("URL tracking for url:label '"+short_tx.getLabel()+"' incrementation");

		URL u = ur.findOneByLabel(short_tx.getLabel());
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		
		for(Hit h: u.getHits()) {
			if(h.getDayOfYear() == doy) {
				
			}
		}

		u.increment();
		ur.save(u);
		
		txr.setField(true);
		
		return txr;
	}

	public TXResponse<Boolean> decrement(TXURLshort short_tx) {
		TXResponse<Boolean> txr = new TXResponse<>("URL tracking for url:label '"+short_tx.getLabel()+"' decrementation");

		URL u = ur.findOneByLabel(short_tx.getLabel());
		u.decrement();
		ur.save(u);
		
		txr.setField(true);
		
		return txr;
	}

	public TXResponse<Boolean> deleteByLabel(String label) {
		TXResponse<Boolean> txr = new TXResponse<>("URL deletion for label '"+label);

		URL u = ur.findOneByLabel(label);
		ur.delete(u);
		
		txr.setField(true);
		
		return txr;	
	}

	public URL findByURL(TXURLbyURL tx) {

		return ur.findOneByBaseURLAndExtensionURL(tx.getBaseURL(), tx.getExtensionURL());
	}

}

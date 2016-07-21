package com.cooksys.conversion_tracking.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.Tuple;
import com.cooksys.conversion_tracking.model.Hit;
import com.cooksys.conversion_tracking.model.URL;
import com.cooksys.conversion_tracking.model.User;
import com.cooksys.conversion_tracking.repository.HitRepository;
import com.cooksys.conversion_tracking.repository.UrlRepository;
import com.cooksys.conversion_tracking.repository.UserRepository;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXURLbyURL;
import com.cooksys.conversion_tracking.tx.TXURLshort;

import static com.cooksys.conversion_tracking.Defs.*;

@Service
public class UrlService {
	@Autowired
	UrlRepository ur;
	
	@Autowired 
	HitRepository hr;
	
	@Autowired
	UserRepository userr;
		
	public TXResponse<Boolean> increment(TXURLshort short_tx) {
		TXResponse<Boolean> txr = new TXResponse<>("URL tracking for url:label '"+short_tx.getLabel()+"' incrementation");

		URL u = ur.findOneByLabel(short_tx.getLabel());
		if(u == null) {
			throw new InvalidDataAccessApiUsageException("Url with label must exist in database!");
		}
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		Integer year = cal.get(Calendar.YEAR);
		
		for(Hit h: hr.findByUrl(u)) {
			if((h.getDayOfYear().equals(doy)) && (h.getYear().equals(year))) {
				h.increment();
				hr.save(h);
				txr.setField(true);
				break;
			}
		}

		if(txr.getField() == null) {
			Hit h = new Hit();
			h.setAnonymousCount(1L);
			h.setUrl(u);
			hr.save(h);
		}
		txr.setField(true);
		
		return txr;
	}

	public TXResponse<Boolean> decrement(TXURLshort short_tx) {
		TXResponse<Boolean> txr = new TXResponse<>("URL tracking for url:label '"+short_tx.getLabel()+"' decrementation");

		URL u = ur.findOneByLabel(short_tx.getLabel());
		if(u == null) {
			throw new InvalidDataAccessApiUsageException("Url with label must exist in database!");
		}

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		Integer year = cal.get(Calendar.YEAR);
		
		for(Hit h: hr.findByUrl(u)) {
			if((h.getDayOfYear().equals(doy)) && (h.getYear().equals(year))) {
				h.decrement();
				hr.save(h);
				txr.setField(true);
			}
		}
		
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

	public TXResponse<Long> hitCountByLabel(String label) {
		URL u = ur.findOneByLabel(label);
		if(u == null) {
			throw new InvalidDataAccessApiUsageException("Url with label must exist in database!");
		}
		
		TXResponse<Long> txr = new TXResponse<>("Hit Count By Label");
	
		Long total = 0L;
//		System.out.println(u.getHits().size() + " hit records to investigate for label: " + u.getLabel());
		for(Hit h: hr.findByUrl(u)){
			total += h.getAnonymousCount();
		}
		txr.setField(total);
		return txr;
	}

	public TXResponse<Long> hitCountByLabelAndProRatum(String label, String proratum) {
		URL u = ur.findOneByLabel(label);
		if(u == null) {
			throw new InvalidDataAccessApiUsageException("Url with label must exist in database!");
		}

		TXResponse<Long> txr = new TXResponse<>("Pro-rated Hit Count By Label");
	
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		Integer year = cal.get(Calendar.YEAR);
		
		switch (proratum) {
			case PRORATUM_WEEKLY:
				if(doy < 7) {
					year -= 1;
					doy += 365;
				}
				doy -= 7;
				break;
			case PRORATUM_MONTHLY:
				if(doy < 30) {
					year -= 1;
					doy += 365;
				}
				doy -= 30;
				break;
			case PRORATUM_YEARLY:
				year -= 1;
				break;
			default:
				throw new InvalidDataAccessApiUsageException("Illegal pro-ratum for search time.");
		}
		
		Long total = 0L;
		if(year < cal.get(Calendar.YEAR)){
			for(Hit h: hr.findByUrlAndYear(u, cal.get(Calendar.YEAR))){
				total += h.getAnonymousCount();
			}
			for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
				total += h.getAnonymousCount();
			}		
		} else {
			for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
				total += h.getAnonymousCount();
			}			
		}
		txr.setField(total);
		return txr;
	}

	public TXResponse<List<Tuple<Long, URL>>> readURLsWithHits() {
		TXResponse<List<Tuple<Long, URL>>> txr = new TXResponse<>("URLs with Hits");
		List<Tuple<Long, URL>> l = new ArrayList<>();
		
		for(URL u: ur.findAll()) {
			Long total = 0L;
			for(Hit h: hr.findByUrl(u)){
				total += h.getAnonymousCount();
			}
			l.add(new Tuple<Long, URL>(total, u));
		}
		txr.setField(l);
		return txr;
	}

	public TXResponse<List<Tuple<Long, URL>>> readURLsWithHitsProRatum(String proratum) {
		TXResponse<List<Tuple<Long, URL>>> txr = new TXResponse<>("URLs with Hits");
		List<Tuple<Long, URL>> l = new ArrayList<>();

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		Integer year = cal.get(Calendar.YEAR);
		
		switch (proratum) {
			case PRORATUM_WEEKLY:
				if(doy < 7) {
					year -= 1;
					doy += 365;
				}
				doy -= 7;
				break;
			case PRORATUM_MONTHLY:
				if(doy < 30) {
					year -= 1;
					doy += 365;
				}
				doy -= 30;
				break;
			case PRORATUM_YEARLY:
				year -= 1;
				break;
			default:
				throw new InvalidDataAccessApiUsageException("Illegal pro-ratum for search time.");
		}
		
		for(URL u: ur.findAll()) {
			Long total = 0L;
			if(year < cal.get(Calendar.YEAR)){
				for(Hit h: hr.findByUrlAndYear(u, cal.get(Calendar.YEAR))){
					total += h.getAnonymousCount();
				}
				for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
					total += h.getAnonymousCount();
				}		
			} else {
				for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
					total += h.getAnonymousCount();
				}			
			}
			l.add(new Tuple<Long, URL>(total, u));
		}
		txr.setField(l);
		return txr;
	}

	public TXResponse<List<Tuple<List<Long>, URL>>> readURLsWithTracking() {
		TXResponse<List<Tuple<List<Long>, URL>>> txr = new TXResponse<>("URLs with Tracking");
		List<Tuple<List<Long>, URL>> l = new ArrayList<>();
		
		for(URL u: ur.findAll()) {
			Long anonymousTotal = 0L;
			Long registeredTotal = 0L;
			for(Hit h: hr.findByUrl(u)){
				anonymousTotal += h.getAnonymousCount();
				registeredTotal += h.getRegisteredCount();
			}
			Long total = anonymousTotal + registeredTotal;
			Long conversions = userr.countByUrl(u);
			Long conversionRate = null;
			if(total == 0) {
				conversionRate = 0L;
			} else {
				Double d = 100 * (conversions/(anonymousTotal*1D));
				conversionRate = d.longValue();
			}
			
			List<Long> trackingDetails = new ArrayList<>();
			trackingDetails.add(anonymousTotal);
			trackingDetails.add(conversions);
			trackingDetails.add(conversionRate);
			l.add(new Tuple<List<Long>, URL>(trackingDetails, u));
		}
		txr.setField(l);
		return txr;
	}

	public TXResponse<List<Tuple<List<Long>, URL>>> readURLsWithTrackingProRatum(String proratum) {
		TXResponse<List<Tuple<List<Long>, URL>>> txr = new TXResponse<>("URLs with Tracking");
		List<Tuple<List<Long>, URL>> l = new ArrayList<>();

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date
		Integer doy = cal.get(Calendar.DAY_OF_YEAR);
		Integer year = cal.get(Calendar.YEAR);
		
		switch (proratum) {
			case PRORATUM_WEEKLY:
				if(doy < 7) {
					year -= 1;
					doy += 365;
				}
				doy -= 7;
				break;
			case PRORATUM_MONTHLY:
				if(doy < 30) {
					year -= 1;
					doy += 365;
				}
				doy -= 30;
				break;
			case PRORATUM_YEARLY:
				year -= 1;
				break;
			default:
				throw new InvalidDataAccessApiUsageException("Illegal pro-ratum for search time.");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, doy);
		calendar.set(Calendar.YEAR, year);
		Date fromDate = new Date(calendar.getTimeInMillis());

		
		for(URL u: ur.findAll()) {
			Long anonymousTotal = 0L;
			Long registeredTotal = 0L;
			if(year < cal.get(Calendar.YEAR)){
				for(Hit h: hr.findByUrlAndYear(u, cal.get(Calendar.YEAR))){
					anonymousTotal += h.getAnonymousCount();
					registeredTotal += h.getRegisteredCount();
				}
				for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
					anonymousTotal += h.getAnonymousCount();
					registeredTotal += h.getRegisteredCount();
				}		
			} else {
				for(Hit h: hr.findByUrlAndYearAndDayOfYearGreaterThanEqual(u, year, doy)){
					anonymousTotal += h.getAnonymousCount();
					registeredTotal += h.getRegisteredCount();
				}			
			}
			Long total = anonymousTotal + registeredTotal;
			Long conversions = userr.countByUrlAndLastUpdatedBetween(u, fromDate, new Date());
			Long conversionRate = null;
			if(total == 0) {
				conversionRate = 0L;
			} else {
				Double d = 100 * (conversions/(anonymousTotal*1D));
				conversionRate = d.longValue();
			}
			
			List<Long> trackingDetails = new ArrayList<>();
			trackingDetails.add(anonymousTotal);
			trackingDetails.add(conversions);
			trackingDetails.add(conversionRate);
			l.add(new Tuple<List<Long>, URL>(trackingDetails, u));
		}
		txr.setField(l);
		return txr;
	}
	
	
}

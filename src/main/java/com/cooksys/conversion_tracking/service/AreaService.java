package com.cooksys.conversion_tracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.repository.AreaRepository;
import com.cooksys.conversion_tracking.repository.UserRepository;
import com.cooksys.conversion_tracking.tx.TXResponse;
import com.cooksys.conversion_tracking.tx.TXShort;

@Service
public class AreaService {
	@Autowired
	UserRepository ur;
	
	@Autowired
	AreaRepository ar;
	
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
		txr.setField(converts/(a.getAnonymousCount()+a.getUserLoginCount() * 1.0));
		
		return txr;
	}


	public void processAnonymousVisit(TXShort short_tx) {
		Area a = ar.findOneByNum(short_tx.getNum());
		a.increment();
		ar.save(a);
	}


	public TXResponse<Long> processHitsByArea(Integer num) {
		Area a = ar.findOneByNum(num);
		
		Long l = a.getAnonymousCount() + a.getUserLoginCount();
		TXResponse<Long> txr = new TXResponse<>("HitsByArea");
		txr.setField(l);
		
		return txr;
	}
}

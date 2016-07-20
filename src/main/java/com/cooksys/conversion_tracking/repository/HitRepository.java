package com.cooksys.conversion_tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.Hit;
import com.cooksys.conversion_tracking.model.URL;

public interface HitRepository extends JpaRepository<Hit, Long> {
	List<Hit> findByUrl(URL url);
	List<Hit> findByUrlAndYear(URL url, Integer year);	
	List<Hit> findByUrlAndYearAndDayOfYearGreaterThanEqual(URL url, Integer year, Integer dayOfYear);	
}

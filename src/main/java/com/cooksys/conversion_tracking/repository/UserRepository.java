package com.cooksys.conversion_tracking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.URL;
import com.cooksys.conversion_tracking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByNameAndPassword(String name, String password);
	List<User> findByArea(Area area);
	List<User> findByUrl(URL url);
	List<User> findByUrlAndLastUpdatedBetween(URL url, Date start, Date end);
	Long countByUrl(URL url);
	Long countByUrlAndLastUpdatedBetween(URL url, Date start, Date end);
	Integer countByArea(Area area);
	User findOneByName(String username);
}
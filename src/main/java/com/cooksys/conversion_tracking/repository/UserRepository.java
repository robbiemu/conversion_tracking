package com.cooksys.conversion_tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.Area;
import com.cooksys.conversion_tracking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByNameAndPassword(String name, String password);
	List<User> findByArea(Area area);
	Integer countByArea(Area area);
}
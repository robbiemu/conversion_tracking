package com.cooksys.conversion_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
	Area findOneByNum(Integer num);
}
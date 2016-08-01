package com.cooksys.conversion_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
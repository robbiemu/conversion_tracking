package com.cooksys.conversion_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.conversion_tracking.model.URL;

public interface UrlRepository extends JpaRepository<URL, Long> {
	URL findOneByLabel(String label);
	URL findOneByBaseURLAndExtensionURL(String baseURL, Integer extensionURL);
}

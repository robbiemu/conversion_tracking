package com.cooksys.conversion_tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "urls", uniqueConstraints = {@UniqueConstraint(columnNames = {"base_url","extension_url"})})
@Data
@EqualsAndHashCode(callSuper=false)
public class URL extends Model {
	@Column(unique=true, nullable=false) String label;
	@Column String description;
	@Column(name="base_url", nullable=false) String baseURL;
	@Column(name="extension_url", nullable=false) Integer extensionURL;
}

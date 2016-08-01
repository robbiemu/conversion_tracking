package com.cooksys.conversion_tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "locations")
@Data
@EqualsAndHashCode(callSuper=false)
public class Location extends Model {
	@Column(unique=true, nullable=false) String title;
	@ManyToOne Area area;
}

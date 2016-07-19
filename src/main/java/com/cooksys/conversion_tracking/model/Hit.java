package com.cooksys.conversion_tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="hits")
@Data
@EqualsAndHashCode(callSuper=false)
public class Hit extends Model {
	@Column(name="day_of_year") Integer dayOfYear;
	@Column Integer year;
	@Column Long anonymousCount;
	@JsonIgnore @ManyToOne URL url;
	
	public Long increment() {
		return ++anonymousCount;
	}

	public Long decrement() {
		return --anonymousCount;
	}
}

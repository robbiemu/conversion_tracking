package com.cooksys.conversion_tracking.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	@Column Long anonymousCount=0L;
	@Column Long registeredCount=0L;
	@JsonIgnore @ManyToOne URL url;
	
	public Hit(){
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date()); // Give your own date

		this.dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		this.year = cal.get(Calendar.YEAR);
	}
	
	public Long increment() {
		return ++anonymousCount;
	}

	public Long decrement() {
		++registeredCount;
		return --anonymousCount;
	}
}

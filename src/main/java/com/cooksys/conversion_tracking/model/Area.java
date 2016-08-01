package com.cooksys.conversion_tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="areas")
@Data
@EqualsAndHashCode(callSuper=false)
public class Area extends Model {
	@Column(unique=true) String handle;
	@Column(unique=true, nullable=false) Integer num;
	@Column(name="anonymous_count") Long anonymousCount=0L;
	@Column(name="user_login_count") Long userLoginCount=0L;
	
	public Long increment () {
		return ++anonymousCount;
	}
	public Long decrement () {
		return --anonymousCount;
	}
	
	public Long  incrementUserLogin() {
		return ++userLoginCount;
	}

}

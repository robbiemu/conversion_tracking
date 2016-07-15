package com.cooksys.conversion_tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Model {
	@Column(unique=true) String name;
	@Column String password;
	@ManyToOne Area area;
}

package com.cooksys.conversion_tracking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Model {
	@Column(unique=true, nullable=false) String name;
	@Column String password;
	@Column(name="admin_rights") Boolean adminRights = false;
	@ManyToOne Area area;
	@ManyToOne URL url;
}

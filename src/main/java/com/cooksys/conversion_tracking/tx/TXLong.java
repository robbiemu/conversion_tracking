package com.cooksys.conversion_tracking.tx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TXLong {
	String username;
	String password;
	Integer num;
	String label;
	Boolean admin;
	
}

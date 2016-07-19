package com.cooksys.conversion_tracking.tx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TXURLlong {
	String label;
	String description;
	String baseURL;
	Integer extensionURL;
}

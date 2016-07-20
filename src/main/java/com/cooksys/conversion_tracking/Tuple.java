package com.cooksys.conversion_tracking;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Tuple<L, R> {
	private L left;
	private R right;
	
	public Tuple(L l, R r) {
		left = l;
		right = r;
	}
}

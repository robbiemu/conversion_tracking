package com.cooksys.conversion_tracking.tx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TXResponse<T> {
	String fieldType;
	T field;
	
	public TXResponse() {
	}
	public TXResponse(String transactionType) {
		this.fieldType = transactionType;
	}	
	public TXResponse(String transactionType, T field) {
		this.fieldType = transactionType;
		this.field = field;
	}
	
}

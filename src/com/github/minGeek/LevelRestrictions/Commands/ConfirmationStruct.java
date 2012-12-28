package com.github.minGeek.LevelRestrictions.Commands;

public class ConfirmationStruct {

	private Long created;
	public String value;
	
	public ConfirmationStruct( String value ) {
		this.created = System.currentTimeMillis();
		this.value = value;
	}
	
	public Boolean expired() {

		return ( System.currentTimeMillis() - this.created ) < 10000;
	}
	
	
}

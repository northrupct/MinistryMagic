package com.blackops.model;

import java.io.Serializable;

public class Family implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -446741769640915115L;
	String name;
	
	public Family() {
		super();
		this.name = "";
	}

	public Family(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return name;
	}
	
	

}

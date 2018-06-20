package com.blackops.model;

import java.io.Serializable;

public class Family implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -446741769640915115L;
	String name;
	boolean changed;
	
	public Family() {
		super();
		this.name = "";
		changed = false;
	}

	public Family(String name) {
		super();
		this.name = name;
		changed = false;
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

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	

}

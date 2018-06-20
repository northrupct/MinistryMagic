package com.blackops.model;

import java.io.Serializable;

public class Minister implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3407839215992830838L;
	String name;
	boolean changed;
	
	public Minister() {
		super();
		this.name = "";
		changed = false;
	}

	public Minister(String name) {
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

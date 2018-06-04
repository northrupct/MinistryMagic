package com.blackops.model;

import java.io.Serializable;

import javax.swing.DefaultListModel;

public class Assignment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9190865265797098960L;
	DefaultListModel<String> ministers;
	DefaultListModel<String> families;
	private District parent;
	
	private boolean changed;
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public Assignment() {
		ministers = new DefaultListModel<String>();
		families = new DefaultListModel<String>();
		changed = false;
	}

	public Assignment(District parent) {
		this.parent = parent;
		ministers = new DefaultListModel<String>();
		families = new DefaultListModel<String>();
		changed = false;
	}
	
	Assignment(District parent, DefaultListModel<String> ministers, DefaultListModel<String> families) {
		this.parent = parent;
		this.ministers = ministers;
		this.families = families;
	}

	public DefaultListModel<String> getMinisters() {
		return ministers;
	}

	public void setMinisters(DefaultListModel<String> ministers) {
		this.ministers = ministers;
	}

	public DefaultListModel<String> getFamilies() {
		return families;
	}

	public void setFamilies(DefaultListModel<String> families) {
		this.families = families;
	}

	public District getParent() {
		return parent;
	}

	public void setParent(District parent) {
		this.parent = parent;
	}
	
	

}

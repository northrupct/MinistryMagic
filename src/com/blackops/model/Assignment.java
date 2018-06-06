package com.blackops.model;

import java.io.Serializable;

import javax.swing.DefaultListModel;

public class Assignment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9190865265797098960L;
	DefaultListModel<Minister> ministers;
	DefaultListModel<Family> families;
	private District parent;
	
	private boolean changed;
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public Assignment() {
		ministers = new DefaultListModel<Minister>();
		families = new DefaultListModel<Family>();
		changed = false;
	}

	public Assignment(District parent) {
		this.parent = parent;
		ministers = new DefaultListModel<Minister>();
		families = new DefaultListModel<Family>();
		changed = false;
	}
	
	Assignment(District parent, DefaultListModel<Minister> ministers, DefaultListModel<Family> families) {
		this.parent = parent;
		this.ministers = ministers;
		this.families = families;
	}

	public DefaultListModel<Minister> getMinisters() {
		return ministers;
	}

	public void setMinisters(DefaultListModel<Minister> ministers) {
		this.ministers = ministers;
	}

	public DefaultListModel<Family> getFamilies() {
		return families;
	}

	public void setFamilies(DefaultListModel<Family> families) {
		this.families = families;
	}

	public District getParent() {
		return parent;
	}

	public void setParent(District parent) {
		this.parent = parent;
	}
	
	

}

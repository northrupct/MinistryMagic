package com.blackops.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class MinisteringModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -757611163752882220L;

	ArrayList<District> districts;
	
	DefaultListModel<String> unassignedFamilies;
	DefaultListModel<String> unassignedMinisters;
	
	public MinisteringModel() {
		districts = new ArrayList<District>();
		unassignedFamilies = new DefaultListModel<String>();
		unassignedMinisters = new DefaultListModel<String>();

//		unassignedFamilies.addElement("Bartons");
//		unassignedFamilies.addElement("Rodgers");
//		unassignedFamilies.addElement("Romanov");
//		unassignedFamilies.addElement("Stark");
//		unassignedFamilies.addElement("Parker");
//		
//		unassignedMinisters.addElement("Batman");
//		unassignedMinisters.addElement("Robin");
//		unassignedMinisters.addElement("Hulk");
//		unassignedMinisters.addElement("Superman");
//		unassignedMinisters.addElement("Starfire");
		
	}
	
	public void addDistrict(District d) {
		districts.add(d);
	}

	public ArrayList<District> getDistricts() {
		return districts;
	}

	public void setDistricts(ArrayList<District> districts) {
		this.districts = districts;
	}

	public DefaultListModel<String> getUnassignedFamilies() {
		return unassignedFamilies;
	}

	public void setUnassignedFamilies(DefaultListModel<String> unassignedFamilies) {
		this.unassignedFamilies = unassignedFamilies;
	}

	public DefaultListModel<String> getUnassignedMinisters() {
		return unassignedMinisters;
	}

	public void setUnassignedMinisters(DefaultListModel<String> unassignedMinisters) {
		this.unassignedMinisters = unassignedMinisters;
	}
	
	

}

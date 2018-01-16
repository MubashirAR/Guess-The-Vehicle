package com.xmlscanner.dao;

import java.util.List;

import com.xmlscanner.model.Wheel;

public interface WheelDAO {

public Wheel get(String id);
	
	public Wheel save(Wheel wheel);
	
	
	public List<Wheel> getAll();
}

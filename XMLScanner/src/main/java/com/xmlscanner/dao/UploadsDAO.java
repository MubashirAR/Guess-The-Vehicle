package com.xmlscanner.dao;

import java.util.List;

import com.xmlscanner.model.Uploads;

public interface UploadsDAO {

public Uploads get(String id);
	
	public Uploads save(Uploads uploads);
	
	public List<Uploads> getAll();
}

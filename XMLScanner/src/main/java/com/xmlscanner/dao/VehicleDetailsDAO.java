package com.xmlscanner.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xmlscanner.model.VehicleDetails;

public interface VehicleDetailsDAO {
	
	public VehicleDetails get(String id);
	
	public VehicleDetails save(VehicleDetails vehicleDetails);
	
	public Long getMaxXMLString();
	
	public List<VehicleDetails> getAll();

	public List<VehicleDetails> getVehiclesByFileId(long id);
}

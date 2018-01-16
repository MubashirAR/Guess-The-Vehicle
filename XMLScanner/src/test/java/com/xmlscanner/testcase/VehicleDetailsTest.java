package com.xmlscanner.testcase;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xmlscanner.dao.VehicleDetailsDAO;
import com.xmlscanner.model.VehicleDetails;

public class VehicleDetailsTest {
	
	@Autowired
	private static VehicleDetails vehicleDetails;
	
	@Autowired
	private static VehicleDetailsDAO vehicleDetailsDAO;
	
	@BeforeClass
	public static void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.xmlscanner");
		context.refresh();
	
		 vehicleDetails = (VehicleDetails) context.getBean("vehicleDetails");

		vehicleDetailsDAO = (VehicleDetailsDAO) context.getBean("vehicleDetailsDAO");
		
		
	}
	
	//@Test
	public void saveTestCase() {
		vehicleDetails.setId(1);
		vehicleDetails.setPowerTrain("Human");
		vehicleDetails.setFrame("metal");
		vehicleDetailsDAO.getMaxXMLString();
		vehicleDetails.setXMLString(2L);
		vehicleDetails.setXMLFile(new char[] {'a','b','c'});
		boolean flag = vehicleDetailsDAO.save(vehicleDetails)==null?false:true;
		assertEquals("Save XML", true, flag);
	}
	
	@Test
	public void getVehiclesByIdTestCase() {
		boolean flag = vehicleDetailsDAO.getVehiclesByFileId(101L)==null?false:true;
		assertEquals("Save XML", true, flag);
	}

}

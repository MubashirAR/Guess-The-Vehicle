package com.xmlscanner.testcase;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xmlscanner.dao.WheelDAO;
import com.xmlscanner.model.Wheel;

public class WheelTest {


	@Autowired
	private static Wheel wheel;
	
	@Autowired
	private static WheelDAO wheelDAO;
	
	@BeforeClass
	public static void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.xmlscanner");
		context.refresh();
	
		 wheel = (Wheel) context.getBean("wheel");

		wheelDAO = (WheelDAO) context.getBean("wheelDAO");
		
		
	}
	
	@Test
	public void saveTestCase() {
		
		wheel.setMaterial("plastic");
		wheel.setPosition("front");
		wheel.setVehicle_id("101");
		
		
		Wheel wheel2 = wheelDAO.save(wheel);
		boolean flag = wheel2==null?false:true;
		assertEquals("Save Wheel", true, flag);
	}
	
	
}

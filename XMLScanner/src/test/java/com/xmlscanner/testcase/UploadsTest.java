package com.xmlscanner.testcase;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xmlscanner.dao.UploadsDAO;
import com.xmlscanner.model.Uploads;

import oracle.sql.DATE;

public class UploadsTest {

	@Autowired
	private static Uploads uploads;
	
	@Autowired
	private static UploadsDAO uploadsDAO;
	
	@BeforeClass
	public static void init() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.xmlscanner");
		context.refresh();
	
		 uploads = (Uploads) context.getBean("uploads");

		uploadsDAO = (UploadsDAO) context.getBean("uploadsDAO");
		
		
	}
	
	@Test
	public void saveTestCase() {
		uploads.setId(1);
		
		uploads.setTimestamp(LocalDateTime.now());
		uploads.setXmlfile(new char[] {'a','b','c'});
		uploads=uploadsDAO.save(uploads);
		boolean flag = uploads==null?false:true;
		assertEquals("Save XML", true, flag);
	}
	
}

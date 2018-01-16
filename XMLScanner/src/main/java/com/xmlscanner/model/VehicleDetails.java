package com.xmlscanner.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class VehicleDetails {
	
	@Id
	private long id;
	
	@Column
	private String Type;
	
	@Column 
	private String Frame;
	
	@Column 
	private String PowerTrain;
	
	@Column 
	private String NumberOfWheels;
	
	@Column
	private Long XMLString;
	
	@Column
	private LocalDateTime timestamp;
	
	@Column(name="xmlfile")
	private char[] xmlfile;	
	
	/*@Column
	private long wheel_id;*/
	
	@Column
	private long fileID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getFrame() {
		return Frame;
	}

	public void setFrame(String frame) {
		Frame = frame;
	}

	public String getPowerTrain() {
		return PowerTrain;
	}

	public void setPowerTrain(String powerTrain) {
		PowerTrain = powerTrain;
	}

	public String getNumberOfWheels() {
		return NumberOfWheels;
	}

	public void setNumberOfWheels(String numberOfWheels) {
		NumberOfWheels = numberOfWheels;
	}

	public Long getXMLString() {
		return XMLString;
	}

	public void setXMLString(Long xMLString) {
		XMLString = xMLString;
	}

	@org.hibernate.annotations.Type(type="org.hibernate.type.PrimitiveCharacterArrayClobType")
    @Basic(fetch=FetchType.LAZY)
    @Column(name="xmlfile",columnDefinition="CLOB")
	public char[] getXMLFile() {
		return xmlfile;
	}

	public void setXMLFile(char[] file) {
		this.xmlfile = file;
	}

	/*public long getWheel_id() {
		return wheel_id;
	}

	public void setWheel_id(long wheel_id) {
		this.wheel_id = wheel_id;
	}*/

	public long getFileID() {
		return fileID;
	}

	public void setFileID(long fileID) {
		this.fileID = fileID;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	

}

package com.xmlscanner.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

import oracle.sql.DATE;

@Entity
@Component
public class Uploads {

	@Id
	private long id;
	
	@Column
	private char[] xmlfile;
	
	@Column
	private LocalDateTime timestamp;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public char[] getXmlfile() {
		return xmlfile;
	}

	public void setXmlfile(char[] xmlfile) {
		this.xmlfile = xmlfile;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
		
	
}

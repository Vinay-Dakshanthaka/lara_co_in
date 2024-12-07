package com.lara.c2c.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author riteshk
 * @since 0.1.0
 */

@Getter
@Setter
@ToString
public class ExceptionReport {
	
	private static final SimpleDateFormat SDT = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss:SSS");

	private final Date creationDate;
	private final Exception exception;

	public ExceptionReport(Exception exception) {
		this.creationDate = new Date(); //FIXME: must be synchronized with timestamp created by AspectJ!
		this.exception = exception;
	}
	
	public String getCreationDateFormatted() {
		return SDT.format(creationDate);
	}

}
package com.lara.c2c.utility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServiceUtils {
	
	public static String generateActivationNumber() {
    	int n = 30;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+ "0123456789"+ "abcdefghijklmnopqrstuvxyz";   
        StringBuilder sb = new StringBuilder(n);   
        for (int i = 0; i < n; i++) { 
            int index = (int)(AlphaNumericString.length() * Math.random());   
            sb.append(AlphaNumericString .charAt(index)); 
        }   
        return sb.toString();
	}
	
	public static String generateOtp() {
		    Random rnd = new Random();
		    int number = rnd.nextInt(999999);
		    // this will convert any number sequence into 6 character.
		    return String.format("%06d", number);
	}
	
	public static Timestamp getCurrTimeStamp() {				
		return new Timestamp(new Date().getTime());
	}
	
	public static Date getCurrentDateTime() {
		java.util.Date date = new java.util.Date();
		long t = date.getTime();
		//java.sql.Date sqlDate = new java.sql.Date(t);
		//java.sql.Time sqlTime = new java.sql.Time(t);
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
		return sqlTimestamp;
	}
	
	public static <T, U> List<U> convertStringListToIntList(List<T> listOfString, Function<T, U> function) { 
        return listOfString.stream() 
            .map(function) 
            .collect(Collectors.toList()); 
    }
}

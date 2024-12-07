package com.lara.c2c.utility;

public class Utils {

	public static ApiStatus errorStatus(String message) {
		return new ApiStatus(Constants.ERROR, message);
	}
	
	public static ApiStatus successStatus(String message) {
		return new ApiStatus(Constants.SUCCESS, message);
	}
	
	// Return logic from the program conditions method name must be at the bottom
	static public String getLogic(String program) {
		String function = program.substring(program.lastIndexOf(Constants.METHOD_NAME),program.lastIndexOf("}"));
		String code = function.substring(function.indexOf("{")+1, function.lastIndexOf("}"));
		return code;
	}

}

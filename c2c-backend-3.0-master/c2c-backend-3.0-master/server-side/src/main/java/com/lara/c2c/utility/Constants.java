package com.lara.c2c.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.ExceptionReport;

import lombok.AccessLevel;

/**
 * @author riteshk
 * @since 0.1.0
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
	
	public static final String TEMP_FILE_UPLOAD_FOLDER = ".\\src\\main\\resources\\temp\\";
	
	public static final String MSG_INTERNAL_SERVER_ERROR = "Oops, it looks like something went wrong. Please try again later.";
	public static final String MSG_NOT_FOUND = "Sorry, the page you are looking for cannot be found.";
	public static final String MSG_UNAUTHORIZED = "Sorry, you are not authorised to view this page.";
	public static final String MSG_GONE = "Sorry, the resource you are looking for cannot be found.";
	private static final String MSG_ASSET_LOCKED = "Sorry, %s currently active editing the asset that you're trying to access. Please try accessing after sometime.";
	private static final String MSG_MISSING_PARAMETER = "Missing parameter from the request: ";
	private static final String MSG_INVALID_PARAMETER = "Invalid parameter in the request: ";
	public static final String OHS_REQUEST_HEADER = "OAM_REMOTE_USER";

	
	/*
	 * online compiler purpose
	 */
	public static final String METHOD_NAME= "test";
	public static final String SUCCESS= "Success";
	public static final String ERROR= "Error";
	public static final String 	SAVE_SUCCESS= "Program Saved";
	public static final String 	SAVE_ERROR= "Program Not Saved";
	public static final String RECORD_NOT_FOUND = "Record Not Found";
	public static final String UPDATE_SUCCESS = "Program Updated";
	public static final String INVALID_ENTRY = "Invalid Entry";
	public static final String QUESTION_ADDED = "Question Added";	
	
	
	
	
	/**
	 * Error message for a missing parameter
	 *
	 * @param param
	 * The name of the parameter
	 * @return Error message for a missing parameter
	 */
	public static String msgMissingParameter(String param) {
		return MSG_MISSING_PARAMETER.concat(param);
	}

	/**
	 * Error message for an invalid parameter
	 *
	 * @param param
	 * The name of the parameter
	 * @return Error message for an invalid parameter
	 */
	public static String msgInvalidParameter(String param) {
		return MSG_INVALID_PARAMETER.concat(param);
	}

	/**
	 * Error message for asset locked
	 *
	 * The lock owner
	 * @param userId
	 * @return Error message for asset locked
	 */
	public static String msgAssetLocked(String userId) {
		return String.format(MSG_ASSET_LOCKED, userId);
	}

	/**
	 * Checking Response object status.</br>
	 * If the Request process failed, the Response object must contain an existing {@link ExceptionReport} object with
	 * all the necessary data about the occurred {@link Exception}.
	 * 
	 * @param response
	 * response object
	 * @return <code>true</code> if the Request process failed, <code>false</code> otherwise.
	 */
	public static final boolean isRequestFailed(BaseResponse response) {
		return (response == null || response.getExceptionReport() != null);
	}

	/**
	 * Assemble a custom response error message sent to the front-end.
	 * 
	 * @param response
	 * response object contains the thrown Exception.
	 * @return assembled response error message
	 */
	public static final String buildResponseErrorMessage(BaseResponse response) {
		// Safety check
		if (response == null) {
			return MSG_INTERNAL_SERVER_ERROR; // Default message
		}

		final ExceptionReport report = response.getExceptionReport();
		final StringBuilder errorMsg = new StringBuilder();
		errorMsg.append("Error occurred on ") //
				.append("[") //
				.append(report.getCreationDateFormatted()) //
				.append("]: ") //
				.append(report.getException().getMessage());

		return errorMsg.toString();
	}

}
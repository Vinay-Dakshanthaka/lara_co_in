package com.lara.c2c.dto;



import com.lara.c2c.model.ExceptionReport;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base class for every response DTO
 *
 * @author riteshk
 * @since 0.1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseResponse extends BaseRequestResponse {

	private ExceptionReport exceptionReport;

}

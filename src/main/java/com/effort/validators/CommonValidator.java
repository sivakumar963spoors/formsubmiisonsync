package com.effort.validators;

import org.springframework.validation.ObjectError;

public class CommonValidator {
	
	public ObjectError rejectValue(String objectName, String defaultMessage){
		return new ObjectError(objectName, defaultMessage);
	}

}

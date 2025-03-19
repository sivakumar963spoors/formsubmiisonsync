package com.effort.validators;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.validation.ObjectError;

import com.effort.util.Api;

public class CommonValidator {
	
	private static Map<String, String> countryMap;
	public ObjectError rejectValue(String objectName, String defaultMessage){
		return new ObjectError(objectName, defaultMessage);
	}
	
	public static boolean isDecimal(String number){
		try {
			Double.parseDouble(number);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isBoolean(String str){
		try {
			/*Boolean.parseBoolean(str);*/
			if(str != null && (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false"))){
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean emailValidator(String emailId) {
		return Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", emailId);
	}
	
	public static boolean timeValidator(String time) {
		return Pattern.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", time);
	}
	
	public static String isValidCountry(String country){
		if(!Api.isEmptyString(country)){
			return countryMap.get(country.trim().toLowerCase());
		} else {
			return null;
		}
	}

}

package com.effort.util;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import com.effort.entity.EntityField;
import com.effort.entity.EntitySectionField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.settings.Constants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class SecurityUtils {
	public static final int INPUT_TYPE_TEXT = 7;
	public static final int INPUT_TYPE_NAME = 1;
	public static final int INPUT_TYPE_ADDRESS = 2;
	public static final int INPUT_TYPE_EMAIL = 3;
	public static final int INPUT_TYPE_COMPANY_NAME = 4;
	public static final int INPUT_TYPE_PHONE = 5;
	public static final int INPUT_TYPE_COMPANY_LABEL = 6;
	public static final int INPUT_TYPE_FORM_OR_ENTITY_FIELD_LABEL = 8;
	public static final int INPUT_TYPE_FORM_OR_ENTITY_TITLE = 9;
	public static final int INPUT_TYPE_OTHER_TITLE = 10;
	public static final int INPUT_TYPE_COMPANY_LEVEL_ID = 11;
	public static final int INPUT_TYPE_CAPTCHA = 12;
	public static final int INPUT_TYPE_NUMBER = 13;
	public static final int INPUT_TYPE_NUMBER_CSV = 14;
	public static final int INPUT_TYPE_ALPAHBET = 15;
	public static final int INPUT_TYPE_ALPAHBET_WITH_UNDERSCORE = 16;
	public static final int INPUT_TYPE_ALPAHBET_WITH_HYPHEN = 17;
	public static final int INPUT_TYPE_ALPAHBET_WITH_HYPHEN_AND_UNDERSCORE = 18;
	public static final int INPUT_TYPE_DATE_TIME = 19;
	public static final int INPUT_TYPE_JSON = 20;
	public static final int INPUT_TYPE_BOOLEAN = 21;
	public static final int INPUT_TYPE_LOCATION = 22;
	public static final int INPUT_TYPE_SQL_ORDER = 23;
	public static final int INPUT_TYPE_URL = 24;
	public static final int INPUT_TYPE_ALPHA_NUMERIC = 25;
	public static final int INPUT_TYPE_EXPRESSION = 26;
	public static final int INPUT_TYPE_POSITIVE_NUMBER_CSV = 27;
	public static final int INPUT_TYPE_DURATION = 28;
	public static final int INPUT_TYPE_PRINT_TEMPLATE = 29;
	
	
	//Getting from xml file                                                                                                                    *Resource: Deva*/
		public static String exprForTypeName;
		public static String exprForTypeAddress; 
		public static String exprForTypeEmail;
		public static String exprForTypeCompanyName; 
		public static String exprForTypePhone;
		public static String exprForTypeCompanyLabel;
		public static String exprForTypeText;
		public static String exprForTypeFormOrEntityFieldlabel;
		public static String exprForTypeFormOrEntityTitle;
		public static String exprForTypeOtherTitle;
		public static String exprForTypeCompanyLevelId;
		public static String exprForTypeCaptcha;
		public static String exprForTypeNumber; 
		public static String exprForTypeNumberCsv; 
		public static String exprForTypeAlphabet; 
		public static String exprForTypeAlpahbetWithUnderscore; 
		public static String exprForTypeAlpahbetWithHyphen; 
		public static String exprForTypeAlpahbetWithHyphenAndUnderscore; 
		public static String exprForTypeDatetime; 
		public static String exprForTypeJson;
		public static String exprForTypeBoolean; 
		public static String exprForTypeLocation; 
		public static String exprForTypeSqlOrder; 
		public static String exprForSkipNewLine;
		public static String exprForTypeUrl;
		public static String exprForTypeAlphaNumeric;
		public static String exprForTypeExpression;
		public static String exprForTypePositiveNumberCsv; 
		public static String exprForTypeDuration;
		
		
	public static void stripInvalidCharacters(Object object, int userInputType, String... propertyNames) 
	{
		if(object != null)
		{
    		for (String propertyName : propertyNames)
    		{
    			Object value;
    			try 
    			{
    				
    				value = PropertyUtils.getProperty(object, propertyName);
    				
    				if(value!=null && !Api.isEmptyString(value.toString()))
    				{
    					Log.debug(Api.class, "stripInvalidCharacters() // INFO : PropertyName : "+propertyName+". PropertyValue : "+value.toString());
    					
    					
    					if(propertyName.equals("formula") && (object instanceof FormFieldSpec  || object  instanceof FormSectionFieldSpec ) ){
        					Object fieldType = PropertyUtils.getProperty(object, "fieldType");
        					if(fieldType != null && fieldType.toString().equals("13")){
        						continue;
        					}
        				}
	    					
	    				if((propertyName.equals("fieldValue") || propertyName.equals("fieldDisplayValue")) && 
	    						(object instanceof FormField || object  instanceof FormSectionField)) {
    						Object fieldType = PropertyUtils.getProperty(object, "fieldType");
    						if(fieldType != null && fieldType.toString().equals("10")) {
    							value = stripInvalidCharacters(value.toString(), getUserInputTypeForField(Integer.parseInt(fieldType.toString())));
    							PropertyUtils.setProperty(object, propertyName, value);
    							continue;
    						}
    					}
    					value = stripInvalidCharacters(value.toString(), userInputType);
    					PropertyUtils.setProperty(object, propertyName, value);
    				}
    			} 
    			catch (Exception e)
    			{
    				Log.debug(Api.class, "stripInvalidCharacters() // ERROR : ExceptionOccured "+e.getMessage());
    			}
    					 
    		}
		 }
	}
	
	public static void stripInvalidCharacters(List<?> objects, int userInputType,String... propertyNames)
	{
		if(objects!=null && !objects.isEmpty())
		{
			Long currentTime = System.currentTimeMillis();
			for (Object object : objects) 
			{
				if(object instanceof FormField) {
					FormField formField = (FormField)object;
					if((formField != null) && (formField.getFieldType() != null)) {
						if(Constants.FORM_FIELD_TYPE_RICH_TEXT == formField.getFieldType()) {
							continue;
						}
					}
				}
				stripInvalidCharacters(object, userInputType, propertyNames);
			}
			Log.debug(Api.class, "stripInvalidCharacters() // INFO : Time Taken in list Method : "+(System.currentTimeMillis() - currentTime));
		}
	}
	
	
	private static String getRegexPattern(int userInputType) {
	    switch (userInputType) {
	        case INPUT_TYPE_NAME: return exprForTypeName;
	        case INPUT_TYPE_ADDRESS: return exprForTypeAddress;
	        case INPUT_TYPE_EMAIL: return exprForTypeEmail;
	        case INPUT_TYPE_COMPANY_NAME: return exprForTypeCompanyName;
	        case INPUT_TYPE_PHONE: return exprForTypePhone;
	        case INPUT_TYPE_COMPANY_LABEL: return exprForTypeCompanyLabel;
	        case INPUT_TYPE_TEXT: return exprForTypeText;
	        case INPUT_TYPE_FORM_OR_ENTITY_FIELD_LABEL: return exprForTypeFormOrEntityFieldlabel;
	        case INPUT_TYPE_FORM_OR_ENTITY_TITLE: return exprForTypeFormOrEntityTitle;
	        case INPUT_TYPE_OTHER_TITLE: return exprForTypeOtherTitle;
	        case INPUT_TYPE_COMPANY_LEVEL_ID: return exprForTypeCompanyLevelId;
	        case INPUT_TYPE_CAPTCHA: return exprForTypeCaptcha;
	        case INPUT_TYPE_NUMBER: return exprForTypeNumber;
	        case INPUT_TYPE_NUMBER_CSV: return exprForTypeNumberCsv;
	        case INPUT_TYPE_ALPAHBET: return exprForTypeAlphabet;
	        case INPUT_TYPE_ALPAHBET_WITH_UNDERSCORE: return exprForTypeAlpahbetWithUnderscore;
	        case INPUT_TYPE_ALPAHBET_WITH_HYPHEN: return exprForTypeAlpahbetWithHyphen;
	        case INPUT_TYPE_ALPAHBET_WITH_HYPHEN_AND_UNDERSCORE: return exprForTypeAlpahbetWithHyphenAndUnderscore;
	        case INPUT_TYPE_DATE_TIME: return exprForTypeDatetime;
	        case INPUT_TYPE_JSON: return exprForTypeJson;
	        case INPUT_TYPE_BOOLEAN: return exprForTypeBoolean;
	        case INPUT_TYPE_LOCATION: return exprForTypeLocation;
	        case INPUT_TYPE_SQL_ORDER: return exprForTypeSqlOrder;
	        case INPUT_TYPE_URL: return exprForTypeUrl;
	        case INPUT_TYPE_ALPHA_NUMERIC: return exprForTypeAlphaNumeric;
	        case INPUT_TYPE_EXPRESSION: return exprForTypeExpression;
	        default: return null;
	    }
	}
	public static String stripInvalidCharacters(String value, int userInputType) {
	    if (value == null) {
	        return null;
	    }

	    value = value.trim(); 

	    String regexPattern = getRegexPattern(userInputType);
	    if (regexPattern != null) {
	        value = value.replaceAll(regexPattern, "");
	    }

	    // Special case for SQL Order: If invalid, default to "ASC"
	    if (userInputType == INPUT_TYPE_SQL_ORDER && !"ASC".equalsIgnoreCase(value) && !"DESC".equalsIgnoreCase(value)) {
	        value = "ASC";
	    }

	    return value;
	}
	/***** This method strip XSS and SQL codes by removing malicious code*/
	public static String stripXSSAndSqlForPrintTemplate(String value, boolean noQuotes) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
         // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("alert\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            /*Date: 2016-05-09
			*Method Purpose: hadling confirm and prompt 
			*Resource: Deva*/
            // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("confirm\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("prompt\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
         // Avoid onclick= expressions
            scriptPattern = Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            
            String ipPingPattern  = "ping(.*)(?:[0-9]{1,3}\\.){3}[0-9]{1,3}"; //OScommand injection
            scriptPattern = Pattern.compile(ipPingPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            value = StringUtils.replaceEach(value, new String[]{"startstarspoorsLessthanstartstar", "startstarspoorsGreaterthanstartstar"}, new String[]{"<", ">"});
        }
        return value;
    }
	public static void stripInvalidCharactersInCustomerAutoFilteringCritiria(List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias) {
		if(customerAutoFilteringCritirias != null && customerAutoFilteringCritirias.size() > 0)
		{
			SecurityUtils.stripInvalidCharacters(customerAutoFilteringCritirias, SecurityUtils.INPUT_TYPE_TEXT, "referenceFieldExpressionId", "value");
		}
	}
	
	public static void stripInvalidCharactersInCustomEntitiesViewConfigurationInCustomersBean(CustomEntityViewConfigurationInCustomer customEntityViewConfigurationInCustomer) 
	{
		if(customEntityViewConfigurationInCustomer != null)
		{
			SecurityUtils.stripInvalidCharacters(customEntityViewConfigurationInCustomer, SecurityUtils.INPUT_TYPE_DATE_TIME, "createdTime","modifiedTime");
			SecurityUtils.stripInvalidCharacters(customEntityViewConfigurationInCustomer, SecurityUtils.INPUT_TYPE_FORM_OR_ENTITY_TITLE, "entityTitle");
			SecurityUtils.stripInvalidCharacters(customEntityViewConfigurationInCustomer, SecurityUtils.INPUT_TYPE_TEXT, "externalId");
			
		}
	}
	
	/***** This method strip XSS and SQL codes by removing malicious code*/
	public static String stripXSSAndSql(String value, boolean noQuotes) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
         // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("alert\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            /*Date: 2016-05-09
			*Method Purpose: hadling confirm and prompt 
			*Resource: Deva*/
            // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("confirm\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid anything in a alert() functions type of expression
            scriptPattern = Pattern.compile("prompt\\((.*)\\)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
         // Avoid onclick= expressions
            scriptPattern = Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            
            String ipPingPattern  = "ping(.*)(?:[0-9]{1,3}\\.){3}[0-9]{1,3}"; //OScommand injection
            scriptPattern = Pattern.compile(ipPingPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            if(noQuotes)
            {
	            value = value.replace("\"", "**spoorsDoubleQuote**");
	            value = value.replace("&", "**spoorsAmp**");
	            
	            value = value.replace("’", "**rightSingleQuote**");
	            value = value.replace("”", "**rightDoubleQuote**");
            }
			/* value = stringUtilEscapeHtml(value); */
            //value = escapeHtml(value);
            value = escapeSql(value);
            value = value.replaceAll("&#39;", "'");
            /*value = value.replaceAll("&auml;", "ä");
            value = value.replaceAll("&ouml;", "ö");
            value = value.replaceAll("&uuml;", "ü");
            value = value.replaceAll("&eacute;", "é");
            value = value.replaceAll("&egrave;", "è");
            value = value.replaceAll("&ccedil;", "ç");
            value = value.replaceAll("&ntilde;", "ñ");
            
            value = value.replaceAll("&Auml;", "Ä");
            value = value.replaceAll("&Ouml;", "Ö");
            value = value.replaceAll("&Uuml;", "Ü");
            value = value.replaceAll("&igrave;", "ì");
            value = value.replaceAll("&ecirc;", "ê");
            value = value.replaceAll("&Agrave;", "À");
            
            value = value.replaceAll("&Atilde;", "Ã");
            
            value = value.replaceAll("&Egrave;", "È");
            
            value = value.replaceAll("&Ecirc;", "Ê");
            
            value = value.replaceAll("&Igrave;", "Ì");
            
            value = value.replaceAll("&Iacute;", "Í");
            
            
            value = value.replaceAll("&Ograve;", "Ò");
            value = value.replaceAll("&Otilde;", "Õ");
            value = value.replaceAll("&Ugrave;", "Ù");
            
            value = value.replaceAll("&Uacute;", "Ú");
            value = value.replaceAll("&Yacute;", "Ý");
            
            
            value = value.replaceAll("&agrave;", "à");
            value = value.replaceAll("&atilde;", "ã");
            
            value = value.replaceAll("&egrave;", "è");            
            value = value.replaceAll("&ecirc;", "ê");
            value = value.replaceAll("&igrave;", "ì");          
            value = value.replaceAll("&iacute;", "í");                        
            value = value.replaceAll("&ograve;", "ò");
            value = value.replaceAll("&otilde;", "õ");
            value = value.replaceAll("&ugrave;", "ù");            
            value = value.replaceAll("&uacute;", "ú");
            value = value.replaceAll("&yacute;", "ý");
            value = value.replaceAll("&ocirc;", "ô");
            value = value.replaceAll("&aacute;", "á");
            value = value.replaceAll("&acirc;", "â");*/

            if(noQuotes)
            {
	            value = value.replace("**spoorsDoubleQuote**", "\"");
	            value = value.replace("**spoorsAmp**", "&");
	            
	            value = value.replace("**rightSingleQuote**","’");
	            value = value.replace("**rightDoubleQuote**","”");
            }
            //value = StringUtils.replaceEach(value, new String[]{"&lt;", "&gt;"}, new String[]{"<", ">"});
            value = StringUtils.replaceEach(value, new String[]{"startstarspoorsLessthanstartstar", "startstarspoorsGreaterthanstartstar"}, new String[]{"<", ">"});
        }
        return value;
    }
	
	
	public static void stripInvalidCharactersFromField(Object field) 
	{
		Integer fieldType = -1;
		
		if(field != null)
		{
			if(field instanceof FormField)
			{	
				FormField formField = (FormField)field;
				 fieldType = formField.getFieldType();
			}
			else if(field instanceof FormSectionField)
			{	
				FormSectionField formSectionField = (FormSectionField)field;
				fieldType = formSectionField.getFieldType();
			}
			else if(field instanceof EntityField)
			{	
				EntityField entityField = (EntityField)field;
				fieldType = entityField.getFieldType();
			}
			else if(field instanceof EntitySectionField)
			{
				EntitySectionField entitySectionField = (EntitySectionField)field;
				fieldType = entitySectionField.getFieldType();
			}
		}
		
		if(fieldType != null && fieldType != -1)
		{
			switch(fieldType)
			{
				case Constants.FORM_FIELD_TYPE_TEXT :
					stripInvalidCharacters(field, INPUT_TYPE_TEXT, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_NUMBER :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_DATE :
					stripInvalidCharacters(field, INPUT_TYPE_DATE_TIME, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_YES_NO :
					stripInvalidCharacters(field, INPUT_TYPE_BOOLEAN, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_SINGLE_SELECT_LIST :
					stripInvalidCharacters(field, INPUT_TYPE_TEXT, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_MULTI_SELECT_LIST :
					stripInvalidCharacters(field, INPUT_TYPE_TEXT, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_CUSTOMER :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_PHONE :
					stripInvalidCharacters(field, INPUT_TYPE_PHONE, "fieldValue");
					break;
				/*case Constants.FORM_FIELD_TYPE_URL :
					stripInvalidCharacters(field, INPUT_TYPE_TEXT, "fieldValue");
					break;*/
				case Constants.FORM_FIELD_TYPE_TIME :
					stripInvalidCharacters(field, INPUT_TYPE_DATE_TIME, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_IMAGE :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_SIGNATURE :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_LIST :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_EMPLOYEE :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER_CSV, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_CURRENCY :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_MULTIPICK_LIST :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER_CSV, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_LOCATION :
					stripInvalidCharacters(field, INPUT_TYPE_LOCATION, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_DATE_TIME :
					stripInvalidCharacters(field, INPUT_TYPE_DATE_TIME, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_COUNTRY :
					stripInvalidCharacters(field, INPUT_TYPE_TEXT, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_FINGER_PRINT :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_NUMBER_TO_WORD :
					stripInvalidCharacters(field, INPUT_TYPE_ALPAHBET, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_CUSTOMER_TYPE :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_FORM :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_TIMESPAN :
					stripInvalidCharacters(field, INPUT_TYPE_DATE_TIME, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_VIDEO :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				case Constants.FORM_FIELD_TYPE_MULTI_MEDIA :
					stripInvalidCharacters(field, INPUT_TYPE_NUMBER, "fieldValue");
					break;
				default:
					break;
			}
		}
	}

}

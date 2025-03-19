package com.effort.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.effort.context.AppContext;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.Log;

public class BulkActionPerformManager {

	
	private SchedularManager getSchedularManager(){
		SchedularManager schedularManager = AppContext.getApplicationContext().getBean("schedularManager",SchedularManager.class);
		return schedularManager;
	}
	
	public List<FormField> populateNumberToWordFieldsNew(long formId, String formSpecId,
			List<FormFieldSpec> actionFormFieldSpecs, List<FormField> newFormFields) {

		String logText = "populateNumberToWordFields() // formId = " + formId + " formSpecId = " + formSpecId;
		for (FormFieldSpec spec : actionFormFieldSpecs) {
			for (FormField field : newFormFields) {
				if (field.getFieldSpecId() == spec.getFieldSpecId()) {
					field.setExpression(spec.getExpression());
					break;
				}
			}
		}
		Map<String, FormField> expressionAndFormFieldMap = (Map) Api.getMapFromList(newFormFields, "expression");
		FormField referenceFormField = null;
		List<FormField> newNumberToWordFormFields = new ArrayList<FormField>();
		FormField newFormField = null;
		for (FormFieldSpec actionFormFieldSpec : actionFormFieldSpecs) {

			if (actionFormFieldSpec.getFieldType() != Constants.FORM_FIELD_TYPE_NUMBER_TO_WORD) {
				continue;
			}
			referenceFormField = expressionAndFormFieldMap.get(actionFormFieldSpec.getFormula());
			if (referenceFormField == null) {
				continue;
			}
			if (!Api.isNumber(referenceFormField.getFieldValue())) {
				Log.info(getClass(), logText + " not a number FieldValue = " + referenceFormField.getFieldValue());
				continue;
			}
			String numberToWordCurrencyType = actionFormFieldSpec.getNumberToWordCurrencyType();
			String numberToText = number2Text(Double.parseDouble(referenceFormField.getFieldValue()),
					numberToWordCurrencyType);
			newFormField = getSchedularManager().getFormField(0, actionFormFieldSpec, numberToText, numberToText);
			newNumberToWordFormFields.add(newFormField);
			Log.info(getClass(), logText + " newNumberToWordFormFields size = " + newNumberToWordFormFields.size());
		}
		return newNumberToWordFormFields;
	}
	
	public String convert_number(double number)
    {
        if ((number < 0) || (number > 999999999))
        {
            return "NUMBER OUT OF RANGE!";
        }
        double Gn = Math.floor(number / 10000000);  /* Crore */
        number -= Gn * 10000000;
        double kn = Math.floor(number / 100000);     /* lakhs */
        number -= kn * 100000;
        double Hn = Math.floor(number / 1000);      /* thousand */
        number -= Hn * 1000;
        double Dn = Math.floor(number / 100);       /* Tens (deca) */
        number = number % 100;               /* Ones */
        double tn= Math.floor(number / 10);
        double one=Math.floor(number % 10);
        String res = "";

        if (Gn>0)
        {
            res += (convert_number(Gn) + " Crore");
        }
        if (kn>0)
        {
                res += (((res=="") ? "" : " ") +
                convert_number(kn) + " Lakh");
        }
        if (Hn>0)
        {
            res += (((res=="") ? "" : " ") +
                convert_number(Hn) + " Thousand");
        }

        if (Dn>0)
        {
            res += (((res=="") ? "" : " ") +
                convert_number(Dn) + " Hundred");
        }


        String[] ones = new String[]{"", "One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen","Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen","Nineteen"};
        String[] tens = new String[]{"", "", "Twenty", "Thirty", "Fourty", "Fifty", "Sixty","Seventy", "Eighty", "Ninety"};

        if (tn>0 || one>0)
        {
            if (!(res==""))
            {
                res += " AND ";
            }
            if (tn < 2)
            {
                res += ones[(int)tn * 10 + (int)one];
            }
            else
            {

                res += tens[(int)tn];
                if (one>0)
                {
                    res += ("-" + ones[(int)one]);
                }
            }
        }

        if (res=="")
        {
            res = "zero";
        }
        return res;
    }
	
	  public double frac(double f) {
	        return f % 1;
	    }
	  
	  
public String number2Text(double value,String numberToWordCurrencyType) {
        
		double fraction = Math.round(frac(value)*100);
        String f_text  = "";

        if(numberToWordCurrencyType.equals("Rupees")){
        	if(fraction > 0) {
                f_text = " And "+convert_number(fraction)+" Paise";
            }
        }
        //alert(convert_number(value)+" RUPEE "+f_text+" ONLY");
        if ((value < 0) || (value > 999999999))
        {
            return "NUMBER OUT OF RANGE!";
        }
        String val = convert_number(value)+" "+numberToWordCurrencyType+f_text+" Only";
        return val;
    }
}

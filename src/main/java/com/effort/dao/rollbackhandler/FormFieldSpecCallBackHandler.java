package com.effort.dao.rollbackhandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.effort.entity.FormFieldSpec;


public class FormFieldSpecCallBackHandler implements RowCallbackHandler{
	
	private Map<Long, FormFieldSpec> formFieldSpecMap = null;
	
	public FormFieldSpecCallBackHandler(){
		formFieldSpecMap = new HashMap<Long, FormFieldSpec>();
	}
	/*Date: 2016-06-01
	*Method Purpose: getting skeletonFormFieldSpecId
	*Resource: Deva*/
	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Long id = rs.getLong("fieldSpecId");
		FormFieldSpec formFieldSpec = new FormFieldSpec();
		formFieldSpec.setDisplayOrder(rs.getInt("displayOrder"));
		formFieldSpec.setFieldLabel(rs.getString("fieldLabel"));
		formFieldSpec.setFieldSpecId(id);
		formFieldSpec.setFieldType(rs.getInt("fieldType"));
		formFieldSpec.setIsRequired(rs.getBoolean("isRequired"));
		formFieldSpec.setFormSpecId(rs.getLong("formSpecId"));
		formFieldSpec.setExpression(rs.getString("expression"));
		formFieldSpec.setUniqueId(rs.getString("uniqueId"));
		formFieldSpec.setComputedField(rs.getBoolean("computedField"));
		formFieldSpec.setFormula(rs.getString("formula"));
		formFieldSpec.setFieldTypeExtra(rs.getString("fieldTypeExtra"));
		formFieldSpec.setValidationExpr(rs.getString("validationExpr"));
		formFieldSpec.setValidationErrorMsg(rs.getString("validationErrorMsg"));
		formFieldSpec.setSkeletonFormFieldSpecId(rs.getLong("skeletonFormFieldSpecId"));
		formFieldSpec.setDecimalValueLimit(rs.getInt("decimalValueLimit"));
		formFieldSpec.setReadDataFrom(rs.getInt("readDataFrom"));
		
		try{
			formFieldSpec.setMin(rs.getString("min"));
			formFieldSpec.setMax(rs.getString("max"));
			formFieldSpec.setRestrictDataFromMobile(rs.getBoolean("restrictDataFromMobile"));
		}catch(Exception ex){
			
		}
		
		formFieldSpecMap.put(id, formFieldSpec);
	}

	public Map<Long, FormFieldSpec> getFormFieldSpecMap() {
		return formFieldSpecMap;
	}

}

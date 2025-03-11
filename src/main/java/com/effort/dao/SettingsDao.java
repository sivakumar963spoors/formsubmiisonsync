package com.effort.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.effort.entity.CompanyLabel;
import com.effort.entity.EmployeeAccessSettings;
import com.effort.entity.LabelValue;
import com.effort.sqls.Sqls;
import com.effort.util.Api;

@Repository
public class SettingsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public String getCompanySetting(long companyId, String key){
		
//		String value = null;
//		try{
		String	value = jdbcTemplate.queryForObject(Sqls.SELECT_COMPANY_SETTINGS, new Object[] { key, companyId }, String.class);
//		}catch(Exception e){
			
//		}
		return value;
	}
	public String getGlobalSettings(String key){
		String smsc = jdbcTemplate.queryForObject(Sqls.SELECT_GLOBAL_SETTINGS, new Object[] {key}, String.class);
		return smsc;
	}
	public EmployeeAccessSettings getEmployeeAccessSettings(long empId){
		EmployeeAccessSettings employeeAccessSettings = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_ACCESS_SETTINGS, new Object[]{empId}, new BeanPropertyRowMapper<EmployeeAccessSettings>(EmployeeAccessSettings.class));
		return employeeAccessSettings;
	}
	
	public Map<String, String> getCompanyLabelsMap(int companyId,Long appId) {
		//Map<String,String>clientKeysMap=Api.getCompanyLabelClientKeysMap();
		List<LabelValue> labelValues =getLableValuesForCompany(companyId,appId);
		Map<String ,String>clienLabelMap=new LinkedHashMap<String,String>();
		if(labelValues!=null){
		for (LabelValue labelValue : labelValues) {
			clienLabelMap.put(""+labelValue.getLabelId(), labelValue.getLabelValue());
		}
		}
		return clienLabelMap;
	}
	
	public String getDefinedLableValue(String companyId,String labelId, String appId){
		try{
		return 	jdbcTemplate.queryForObject(Sqls.GET_LABEL_FOR_GIVEN_TYPE_AND_COMPANY, new Object[]{companyId,labelId,appId}, String.class);
			
		}catch(Exception ex){
		    return	null;
		}
		
	}
	public List<LabelValue> getLableValuesForCompany(long companyId,Long appId) {
		List<LabelValue> labelValues = jdbcTemplate.query(Sqls.GET_ALL_LABLE_FOR_COMPANY, new Object[] { companyId ,companyId,appId}, new BeanPropertyRowMapper<LabelValue>(LabelValue.class));
		if (labelValues.isEmpty()) {
			labelValues = getAllDefaultLableForCompany(companyId);
		}
		
		if(appId == 0){
			
			String jobDefinedLabel = getDefinedLableValue(companyId+"", CompanyLabel.JOB_LABEL,appId+"");
			if(Api.isEmptyString(jobDefinedLabel)){
		
				for(LabelValue lv : labelValues){
					// for Job , Jobs
					if(lv.getLabelId() == 11 || lv.getLabelId() == 12){
						lv.setVisibility(false);
					}
					
				}//for
			}
		}//if(appId == 0)

		return labelValues;
	}
	private List<LabelValue> getAllDefaultLableForCompany(long companyId) {
		List<LabelValue> labelValues = jdbcTemplate.query(Sqls.GET_ALL_DEFAULT_VALUES, new Object[] { companyId }, new BeanPropertyRowMapper<LabelValue>(LabelValue.class));
		return labelValues;
	}

	public String getEmployeeSetting(long empId, String key){
		String value = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_SETTINGS, new Object[] { key, empId }, String.class);
		return value;
	}
}

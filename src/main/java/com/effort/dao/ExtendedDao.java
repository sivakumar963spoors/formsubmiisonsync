package com.effort.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


import com.effort.context.AppContext;
import com.effort.entity.CustomEntityOnDemandMapping;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.CustomerOnDemandMapping;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.EmployeeOnDemandMapping;
import com.effort.entity.EmployeeRequisitionForm;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.FormCustomEntities;
import com.effort.entity.FormCustomers;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.MobileNumberVerification;
import com.effort.entity.ProcessJmsMessageStatus;
import com.effort.entity.RemainderFieldsMap;
import com.effort.entity.ReportField;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.effort.entity.StartWorkStopWorkLocations;
import com.effort.entity.WebUser;
import com.effort.entity.Work;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkOnDemandMapping;
import com.effort.entity.WorkRequisitionJmsMessageStatus;
import com.effort.entity.WorkflowStage;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;

@Repository
@ComponentScan("com.effort.dao")
public class ExtendedDao {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private Constants constants;
	private Integer placeHoldersGroupLimit;
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		
		return extraSupportAdditionalSupportDao;
	}
	
private ExtraSupportDao getExtraSupportDao(){
		
		ExtraSupportDao extraSupportDao = AppContext.getApplicationContext().getBean("extraSupportDao",ExtraSupportDao.class);
		
		return extraSupportDao;
	}
	
public WorkflowStage getWorkFlowNextStage(String workflowId, Long workFlowStageId) {
	WorkflowStage workflowStage = null;
	try {
		String sq1 = Sqls.SELECT_WORK_FLOW_NEXT_STAGES.replace(":workFlowIds",
				workflowId);
		workflowStage = jdbcTemplate.queryForObject(
				sq1, new Object[] { workFlowStageId },
				new BeanPropertyRowMapper<WorkflowStage>(
						WorkflowStage.class));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return workflowStage;
}

public void updateWorkFlowFormStatusInvalidateHistory(Long formId, String stageId) {
	String sql = Sqls.UPDATE_WORKFLOW_FORMSTATUS_INVALIDATE_HISTORY_BY_FORMID;
	String extraCondition = ""; 
	if(!Api.isEmptyString(stageId)) {
		extraCondition = " AND `id` NOT IN ( SELECT `id` FROM `WorkFlowFormStatusHistory` WHERE `workFlowStageId` IN ("+stageId+") AND `status` = 1 )";;
	}
	sql = sql.replace(":extraCondition",extraCondition);
	jdbcTemplate.update(sql, new Object[] {formId});
}

public List<Long> getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(Long formId, Long workFlowStageId,
		String empIds, int status, WorkFlowFormStatus workflowFormStatus) {
	List<Long> employeeIds = new ArrayList<Long>();
	String sql = Sqls.SELECT_GROUP_BASED_EMPLOYEES_WHO_HAS_ALREADY_APPROVED_OR_REJECTED.replaceAll(":empIds", empIds);
	try {
		employeeIds = jdbcTemplate.queryForList(sql,new Object[] {formId,workFlowStageId,status},Long.class);
		
	} catch (Exception e) {
		
	}
	return employeeIds;
}

private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
	
	ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
	
	return extraSupportAdditionalDao;
}

	public ReportField getReportFormSectionFields(String fieldValue,
			Long fieldSpecId, Integer fieldType) {
		ReportField reportField = null;
		try {
			String idsCsv = "";
			if(!Api.isEmptyString(fieldValue)) {
				String fieldValueCsv = SecurityUtils.stripInvalidCharacters(fieldValue, SecurityUtils.INPUT_TYPE_POSITIVE_NUMBER_CSV);
				fieldValueCsv = fieldValueCsv.replaceAll("\n", "").replaceAll("\r", "");
				String[] fieldArr = fieldValueCsv.split(",");
				for(int i=0; i <fieldArr.length; i++){
					if(!Api.isEmptyString(fieldArr[i])){
						if(Api.isEmptyString(idsCsv)){
							idsCsv = fieldArr[i];
						}else{
							idsCsv += ", "+fieldArr[i];
						}
					}
				}
			}

			if(Api.isEmptyString(idsCsv)) {
				idsCsv = "-1";
			}
			
			String subQuery = "";
			if(fieldType == 5 || fieldType == 6) 
			{
				subQuery = "select group_concat(`FormSectionFieldSpecValidValues`.`value`) from `FormSectionFieldSpecValidValues` where `FormSectionFieldSpecValidValues`.`valueId` IN (:idsCsv)";
			}
			else if(fieldType == 7) {
				subQuery = "select `Customers`.`customerName` from `Customers` where (`Customers`.`customerId` = '"+fieldValue+"')";
			}
			else if(fieldType == 15) {
				subQuery = "select concat(`Employees`.`empFirstName`,' ',`Employees`.`empLastName`) from `Employees` where (`Employees`.`empId` = '"+fieldValue+"')";
			}
			else if(fieldType == 14) {
				subQuery = "SELECT GROUP_CONCAT(e.`displayValue`) FROM `EntityFields` e WHERE e.`identifier`=1 and e.`entityId` = '"+fieldValue+"' limit 1";
			}
			else if(fieldType == 17) {
				subQuery = "SELECT group_concat(e.`displayValue`) FROM `EntityFields` e WHERE e.`identifier`=1 and e.`entityId` IN (:idsCsv)";
			}
			else if(fieldType == 4) {
				subQuery = "if('true'= '"+fieldValue+"','yes','no')";
			}
			else if(fieldType == 24) {
				subQuery = "select customerTypeName from CustomerTypes where customerTypeId= '"+fieldValue+"' limit 1";
			}
			else if(fieldType == 30) {
				subQuery = "SELECT group_concat(`Customers`.`customerName`) FROM `Customers` where `Customers`.`customerId` IN (:idsCsv)";
			}
			else if(fieldType == 31) {
				subQuery = "select Territories.`territoryName` FROM `Territories` where (Territories.`territoryId` = '"+fieldValue+"')";
			}
			else if(fieldType == 32) {
				subQuery = "select CustomEntities.`customEntityName` FROM `CustomEntities` where (CustomEntities.`customEntityId` = '"+fieldValue+"')";
			}
			else if(fieldType == 25) {
				subQuery = "SELECT IFNULL(concat(f.`formId`,'-',group_concat(fi.`identifier`)),f.formId) FROM  Forms f LEFT JOIN (select `FormFields`.`formId` AS `formId`,group_concat(`FormFields`.`displayValue` separator ',') AS `identifier` from `FormFields` where (`FormFields`.`identifier` = 1) and formId = '"+fieldValue+"' group by formId) AS fi ON f.formId=fi.formId WHERE f.`formId`= '"+fieldValue+"'";
			}
			else if(fieldType == 49) {
			    subQuery = "SELECT group_concat(CustomEntities.`customEntityName`) FROM `CustomEntities` where `CustomEntities`.`customEntityId` IN (:idsCsv)";
			}

			/*
			 * else { subQuery = "'"+fieldValue+"'"; }
			 */
			
			String sql = "";
			if(Api.isEmptyString(subQuery)) 
			{
				sql = Sqls.SELECT_REPORT_FORM_SECTION_FIELDS_SYNC_IDEPENDENT_FIELD;
				sql = sql.replace(":idsCsv", idsCsv);
				reportField = jdbcTemplate.queryForObject(sql,
						new Object[] { fieldValue,fieldSpecId },
						new BeanPropertyRowMapper<ReportField>(ReportField.class));
			}else {
				sql = Sqls.SELECT_REPORT_FORM_SECTION_FIELDS_SYNC.replace(":subQuery", subQuery);
				sql = sql.replace(":idsCsv", idsCsv);
				reportField = jdbcTemplate.queryForObject(sql,
						new Object[] { fieldSpecId },
						new BeanPropertyRowMapper<ReportField>(ReportField.class));
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportField;
	}
	
	public void insertFormSectionFieldsForSync(List<FormSectionField> sectionFields,boolean restrictDataFromMobile,boolean isSync, Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap, boolean isMasterForm) {
		Long currentInMillies = System.currentTimeMillis();
		
		Set<Long> formIds = new HashSet<Long>();
		List<FormCustomers> formCustomers = new ArrayList<FormCustomers>();
		List<FormCustomEntities> formCustomEntities = new ArrayList<FormCustomEntities>();
		Set<Long> formIdsForCustomEntities = new HashSet<Long>();
		
		for (FormSectionField field : sectionFields) {
			
			
			if(!field.isAdditionalFieldsInfo()) 
			{
				ReportField reportField = getReportFormSectionFields(
						field.getFieldValue(), field.getSectionFieldSpecId(),formSectionFieldSpecMap.get(field.getSectionFieldSpecId()).getFieldType());
			
				if (reportField != null) {
					field.setFieldLabel(reportField.getFieldLabel());
					field.setFieldType(reportField.getFieldType());
					field.setDisplayOrder(reportField.getDisplayOrder());
					field.setIdentifier(reportField.getIdentifier());
					field.setDisplayValue(reportField.getDisplayValue());
					field.setUniqueId(reportField.getUniqueId());
					field.setInitialFormSectionFieldSpecId(reportField
							.getInitialFieldSpecId());
					field.setSkeletonFormSectionFieldSpecId(reportField
							.getSkeletonFieldSpecId());

				}
			}
			
			
			if(field.getFieldType() == 7)
			{
				if(!Api.isEmptyString(field.getFieldValue()))
				{
					FormCustomers formCustomer = new FormCustomers();
					formCustomer.setFormId(field.getFormId());
					formCustomer.setCustomerId(Long.parseLong(field.getFieldValue()));
					formCustomers.add(formCustomer);
					formIds.add(field.getFormId());
				}
			}else if(field.getFieldType() == 32)
			{
				if(!Api.isEmptyString(field.getFieldValue()))
				{
					FormCustomEntities formCustomEntity = new FormCustomEntities();
					formCustomEntity.setFormId(field.getFormId());
					formCustomEntity.setCustomEntityId(Long.parseLong(field.getFieldValue()));
					formCustomEntities.add(formCustomEntity);
					formIdsForCustomEntities.add(field.getFormId());
				}
			}
			if(isSync) {
				if(field.getFieldType() == Constants.FORM_FIELD_TYPE_RICH_TEXT) {
					if(!Api.isEmptyString(field.getFieldValue()))
					{
						RichTextFormSectionField rtfFormSectionFields = new RichTextFormSectionField();
						rtfFormSectionFields.setFormId(field.getFormId());
						rtfFormSectionFields.setFormSpecId(field.getFormSpecId());
						rtfFormSectionFields.setValue(field.getFieldValue());
						rtfFormSectionFields.setSectionSpecId(field.getSectionSpecId());
						rtfFormSectionFields.setSectionFieldSpecId(field.getSectionFieldSpecId());
						rtfFormSectionFields.setInstanceId(field.getInstanceId());
						long id = getExtraSupportAdditionalSupportDao().insertRichTextFormSectionField(rtfFormSectionFields);
						//field.setFieldValue(id+"");
						//field.setDisplayValue(id+"");
					}
				}
			}

		}
		
		
		String tableName = "FormSectionFields";
		
		if(isMasterForm) {
			tableName = "MasterSectionFormFields";
		}
		
		if(restrictDataFromMobile) {
			tableName = "RestrictDataFromMobileSectionFormFields";
		}
		
		List<FormSectionField> fieldsSubList = new ArrayList<FormSectionField>();
		String sql = new String("INSERT INTO "+tableName+" (`formId`, `formSpecId`, `sectionSpecId`, `sectionFieldSpecId`, `instanceId`, `fieldValue`, `createdTime`, `modifiedTime`,`fieldLabel`,`fieldType`,`displayOrder`,`identifier`,`displayValue`,`uniqueId`,`initialFormSectionSpecId`,`skeletonFormSectionSpecId`) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		for(int i =0; i < sectionFields.size(); i++)
		{
			if(first)
			{
				placeHolderString.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				first = false;
			}
			else
			{
				placeHolderString.append(", (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			fieldsSubList.add(sectionFields.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (sectionFields.size()-1))
			{
				extraDao.insertFormSectionFields(sql + placeHolderString, fieldsSubList);
				placeHolderString = new StringBuffer();
				first = true;
				fieldsSubList = new ArrayList<FormSectionField>();
			}
		}

		if(formCustomers.size() > 0 && !restrictDataFromMobile)
		{
			Long currentTime = System.currentTimeMillis();
			getExtraSupportDao().insertFormCustomers(formCustomers);
			Log.info(this.getClass(),"FormIds :"+formIds+" Time Taken to process FormCustomersInsertionFor FormFileds :"+(System.currentTimeMillis() - currentTime)+" ms");
			
		}
		if(formCustomEntities.size() > 0 && !restrictDataFromMobile){
			Long currentTime = System.currentTimeMillis();
			getExtraSupportAdditionalDao().insertFormCustomEntities(formCustomEntities);
			Log.info(this.getClass(),"FormIds :"+formIdsForCustomEntities+" Time Taken to process formCustomEntitiesInsertionFor FormFileds :"+(System.currentTimeMillis() - currentTime)+" ms");
		}
		
		if(constants.isPrintLogs()){
			Log.info(getClass(), "insertFormSectionFields() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		}
		
	}
	
	public void updateWorkModifiedTime(String modifiedTime, long workId) {
		String sql = Sqls.UPDATE_WORK_MODIFIED_TIME_FOR_MULTI_CAST;
	    jdbcTemplate.update(sql, new Object[]{modifiedTime,workId});
	}
	
	
	public ReportField getReportFormFields(String fieldValue, Long fieldSpecId, Integer fieldType) {
		ReportField reportField = null;

		// if (fieldValue != null && fieldValue.contains("'")) {
		// fieldValue = fieldValue.replaceAll("'", "''");
		// }
		try {
			String idsCsv = "";
			if(!Api.isEmptyString(fieldValue)) {
				String fieldValueCsv = SecurityUtils.stripInvalidCharacters(fieldValue, SecurityUtils.INPUT_TYPE_POSITIVE_NUMBER_CSV);
				fieldValueCsv = fieldValueCsv.replaceAll("\n", "").replaceAll("\r", "");
				String[] fieldArr = fieldValueCsv.split(",");
				for(int i=0; i <fieldArr.length; i++){
					if(!Api.isEmptyString(fieldArr[i])){
						if(Api.isEmptyString(idsCsv)){
							idsCsv = fieldArr[i];
						}else{
							idsCsv += ", "+fieldArr[i];
						}
					}
				}
			}

			if(Api.isEmptyString(idsCsv)) {
				idsCsv = "-1";
			}
			
			String subQuery = "";
			if(fieldType == 5 || fieldType == 6) 
			{
				subQuery = "select group_concat(`FormFieldSpecValidValues`.`value`) from `FormFieldSpecValidValues` where `FormFieldSpecValidValues`.`valueId` IN (:idsCsv)";
			}
			else if(fieldType == 7) {
				subQuery = "select `Customers`.`customerName` from `Customers` where (`Customers`.`customerId` = '"+fieldValue+"')";
			}
			else if(fieldType == 15) {
				subQuery = "select concat(`Employees`.`empFirstName`,' ',`Employees`.`empLastName`) from `Employees` where (`Employees`.`empId` = '"+fieldValue+"')";
			}
			else if(fieldType == 14) {
				subQuery = "SELECT GROUP_CONCAT(e.`displayValue`) as displayValue FROM `EntityFields` e WHERE e.`identifier`=1 and e.`entityId`= '"+fieldValue+"' limit 1";
			}
			else if(fieldType == 17) {
				subQuery = "SELECT group_concat(e.`displayValue`) FROM `EntityFields` e WHERE e.`identifier`=1 and e.`entityId` IN (:idsCsv)";
			}
			else if(fieldType == 4) {
				subQuery = "if('true'= '"+fieldValue+"','yes','no')";
			}
			else if(fieldType == 24) {
				subQuery = "select customerTypeName from CustomerTypes where customerTypeId= '"+fieldValue+"' limit 1";
			}
			else if(fieldType == 30) {
				subQuery = "SELECT group_concat(`Customers`.`customerName`) FROM `Customers` where `Customers`.`customerId` IN (:idsCsv)";
			}
			else if(fieldType == 31) {
				subQuery = "select Territories.`territoryName` FROM `Territories` where (Territories.`territoryId` = '"+fieldValue+"')";
			}
			else if(fieldType == 32) {
				subQuery = "select CustomEntities.`customEntityName` FROM `CustomEntities` where (CustomEntities.`customEntityId` = '"+fieldValue+"')";
			}
			else if(fieldType == 25) {
				subQuery = "SELECT IFNULL(concat(f.`formId`,'-',group_concat(fi.`identifier`)),f.formId) FROM  Forms f LEFT JOIN (select `FormFields`.`formId` AS `formId`,group_concat(`FormFields`.`displayValue` separator ',') AS `identifier` from `FormFields` where (`FormFields`.`identifier` = 1) and formId = '"+fieldValue+"' group by formId) AS fi ON f.formId=fi.formId WHERE f.`formId`= '"+fieldValue+"'";
			}
			else if(fieldType == 49) {
				subQuery = "SELECT group_concat(CustomEntities.`customEntityName`) FROM `CustomEntities` where `CustomEntities`.`customEntityId` IN (:idsCsv)";
			}
			/*
			 * else { subQuery = "\""+fieldValue+"\""; }
			 */
			String sql = "";
			if(Api.isEmptyString(subQuery)) 
			{
				sql = Sqls.SELECT_REPORT_FORM_FIELDS_SYNC_IDEPENDENT_FIELD;
				sql = sql.replace(":idsCsv",idsCsv);
				reportField = jdbcTemplate.queryForObject(sql, new Object[] {fieldValue,fieldSpecId},
						new BeanPropertyRowMapper<ReportField>(ReportField.class));
			}else {
				sql = Sqls.SELECT_REPORT_FORM_FIELDS_SYNC.replace(":subQuery", subQuery);
				sql = sql.replace(":idsCsv",idsCsv);
				reportField = jdbcTemplate.queryForObject(sql, new Object[] {fieldSpecId},
						new BeanPropertyRowMapper<ReportField>(ReportField.class));
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportField;
	}
	
	public void insertFormFieldsForSync(List<FormField> fields, boolean isMasterForm,boolean restrictDataFromMobile,boolean isSync, Map<Long, FormFieldSpec> formFieldSpecMap) {
		Long currentInMillies = System.currentTimeMillis();
		Set<Long> formIds = new HashSet<Long>();
		List<FormCustomers> formCustomers = new ArrayList<FormCustomers>();
		List<FormCustomEntities> formCustomEntities = new ArrayList<FormCustomEntities>();
		Set<Long> formIdsForCustomEntities = new HashSet<Long>();
		for (FormField field : fields) {
			
			if(!field.isAdditionalFieldsInfo()) 
			{
				ReportField reportField = getReportFormFields(
						field.getFieldValue(), field.getFieldSpecId(),formFieldSpecMap.get(field.getFieldSpecId()).getFieldType());
				if (reportField != null) {
					field.setFieldLabel(reportField.getFieldLabel());
					field.setFieldType(reportField.getFieldType());
					field.setDisplayOrder(reportField.getDisplayOrder());
					field.setIdentifier(reportField.getIdentifier());
					field.setDisplayValue(reportField.getDisplayValue());
					field.setUniqueId(reportField.getUniqueId());
					field.setInitialFormFieldSpecId(reportField
							.getInitialFieldSpecId());
					field.setSkeletonFormFieldSpecId(reportField
							.getSkeletonFieldSpecId());
					field.setIsVisible(reportField.getIsVisible());
				}
			}
			if(field.getFieldType() == 7)
			{
				if(!Api.isEmptyString(field.getFieldValue()))
				{
					FormCustomers formCustomer = new FormCustomers();
					formCustomer.setFormId(field.getFormId());
					formCustomer.setCustomerId(Long.parseLong(field.getFieldValue()));
					formCustomer.setFormSpecId(field.getFormSpecId());
					formCustomers.add(formCustomer);
					formIds.add(field.getFormId());
				}
			}
			else if(field.getFieldType() == 32){
				if(!Api.isEmptyString(field.getFieldValue()))
				{
					FormCustomEntities formCustomEntity = new FormCustomEntities();
					
					formCustomEntity.setFormId(field.getFormId());
					formCustomEntity.setCustomEntityId(Long.parseLong(field.getFieldValue()));
					formCustomEntities.add(formCustomEntity);
					formIdsForCustomEntities.add(field.getFormId());
				}
			}
			if(isSync) {
				if(field.getFieldType() == Constants.FORM_FIELD_TYPE_RICH_TEXT) {
					if(!Api.isEmptyString(field.getFieldValue()))
					{
						RichTextFormField rtfFormFields = new RichTextFormField();
						rtfFormFields.setFormId(field.getFormId());
						rtfFormFields.setFormSpecId(field.getFormSpecId());
						rtfFormFields.setValue(field.getFieldValue());
						rtfFormFields.setFieldSpecId(field.getFieldSpecId());
						long id = getExtraSupportAdditionalSupportDao().insertRichTextFormField(rtfFormFields);
						//field.setFieldValue(id+"");
						//field.setDisplayValue(id+"");
					}
				}
			}

		}
		
		List<FormField> fieldsSubList = new ArrayList<FormField>();
		String tableName = "FormFields";
		if(isMasterForm) {
			tableName = "MasterFormFields";
		}
		if(restrictDataFromMobile) {
			tableName = "RestrictDataFromMobileFormFields";
		}
		//String sql = new String(Sqls.INSERT_FORM_FIELD_NEW);
		//String sql = new String("INSERT INTO `FormFields` (`formId`, `formSpecId`, `fieldSpecId`, `fieldValue`, `createdTime`, `modifiedTime`,`fieldLabel`,`fieldType`,`displayOrder`,`identifier`,`displayValue`,`uniqueId`,`initialFormFieldSpecId`,`skeletonFormFieldSpecId`,`isVisible`) VALUES");
		String sql = new String("INSERT INTO "+tableName+" (`formId`, `formSpecId`, `fieldSpecId`, `fieldValue`, `createdTime`, `modifiedTime`,`fieldLabel`,`fieldType`,`displayOrder`,`identifier`,`displayValue`,`uniqueId`,`initialFormFieldSpecId`,`skeletonFormFieldSpecId`,`isVisible`,groupExpression) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		for(int i =0; i < fields.size(); i++)
		{
			if(first)
			{
				placeHolderString.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				first = false;
			}
			else
			{
				placeHolderString.append(", (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			fieldsSubList.add(fields.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (fields.size()-1))
			{
				extraDao.insertFormFields(sql + placeHolderString, fieldsSubList);
				placeHolderString = new StringBuffer();
				first = true;
				fieldsSubList = new ArrayList<FormField>();
			}
		}

		if(formCustomers.size() > 0 && !restrictDataFromMobile)
		{
			Long currentTime = System.currentTimeMillis();
			getExtraSupportDao().insertFormCustomers(formCustomers);
			Log.info(this.getClass(),"FormIds :"+formIds+" Time Taken to process FormCustomersInsertionFor FormFileds :"+(System.currentTimeMillis() - currentTime)+" ms");
		}
		if(formCustomEntities.size() > 0 && !restrictDataFromMobile)
		{
			Long currentTime = System.currentTimeMillis();
			getExtraSupportAdditionalDao().insertFormCustomEntities(formCustomEntities);
			Log.info(this.getClass(),"FormIds :"+formIdsForCustomEntities+" Time Taken to process formCustomEntitiesInsertionFor FormFileds :"+(System.currentTimeMillis() - currentTime)+" ms");
		}
		
		if(constants.isPrintLogs()){
			Log.info(getClass(), "insertFormFields() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		}
		
	}
	
	public long insertEmployeeRequisitionForm(final EmployeeRequisitionForm employeeRequisitionForm)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_EMPLOYEE_REQUISTIONS_FORMS,
						Statement.RETURN_GENERATED_KEYS);
				
				ps.setInt(1, employeeRequisitionForm.getCompanyId());
				ps.setLong(2, employeeRequisitionForm.getConfigurationId());
				ps.setInt(3, employeeRequisitionForm.getTriggerEvent());
				if(employeeRequisitionForm.getWorkFlowFormStatusId() == null){
					ps.setNull(4, Types.BIGINT);
				}else{
					ps.setLong(4, employeeRequisitionForm.getWorkFlowFormStatusId());
				}
				
				ps.setLong(5, employeeRequisitionForm.getFormId());
				ps.setInt(6, employeeRequisitionForm.getProcessingStatus());
				ps.setString(7, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
		        ps.setString(8, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				
				return ps;
			}
		}, keyHolder);

		long id1 = keyHolder.getKey().longValue();
		return id1;
	}
	
	public List<EmployeeOnDemandMapping> getEmployeesOnDemandMappingByUniqueId(String formSpecUniqueId, Integer companyId,Integer triggerEvent) {
		List<EmployeeOnDemandMapping> employeeOnDemandMappingList = new ArrayList<EmployeeOnDemandMapping>();
		
		try
		{
			String sql = Sqls.SELECT_EMPLOYEE_ON_DEMAND_MAPPING_BY_UNIQUEID.replace(":formSpecUniqueId", formSpecUniqueId)
					.replace(":companyId", companyId+"");
			if(triggerEvent == null){
				sql = sql.replace(":triggerEvent", "");
			}else{
				sql = sql.replace(":triggerEvent", "AND triggerEvent="+triggerEvent);
			}
			employeeOnDemandMappingList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<EmployeeOnDemandMapping>(EmployeeOnDemandMapping.class));
			
		}
		catch(Exception e)
		{
			Log.info(getClass(), "getEmployeesOnDemandMappingByUniqueId() got exception  ",e);
		}
		
		return employeeOnDemandMappingList;
	}
	public List<RemainderFieldsMap> getRemainderFieldsMapForFormSpecs(
			String formSpecIds) {
		List<RemainderFieldsMap> remainderFieldsMap = new ArrayList<RemainderFieldsMap>();
		if (!Api.isEmptyString(formSpecIds)) {
			String sql = Sqls.SELECT_REMAINDER_FIELDS_MAP_FOR_FORMSPECS
					.replace(":formSpecIds", formSpecIds);
			remainderFieldsMap = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<RemainderFieldsMap>(
							RemainderFieldsMap.class));
		}
		return remainderFieldsMap;
	}
	
	public CustomerOnDemandMapping getCustomerOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
		CustomerOnDemandMapping customerOnDemandMapping = null;
		try {
			customerOnDemandMapping = jdbcTemplate.queryForObject(Sqls.SELECT_CUSTOMER_ON_DEMAPND_MAPPING_BY_UNIQUE_ID,
														new Object[] {uniqueId,companyId},
														new BeanPropertyRowMapper<CustomerOnDemandMapping>(CustomerOnDemandMapping.class));
		}catch(Exception e) {
			Log.info(this.getClass(), "Got Exception while getCustomerOnDemandMappingForGivenFormSpec() : "+e);
		}
		return customerOnDemandMapping;
	}
	public List<CustomerAutoFilteringCritiria> getCustomerAutoFilteringCritiriasForFormSpecs(
			String formSpecIds) {
		List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias = new ArrayList<CustomerAutoFilteringCritiria>();
		if (!Api.isEmptyString(formSpecIds)) {
			String sql = Sqls.SELECT_CUSTOMER_AUTO_FILTERING_FOR_FORMSPECS
					.replace(":formSpecIds", formSpecIds);
			customerAutoFilteringCritirias = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<CustomerAutoFilteringCritiria>(
							CustomerAutoFilteringCritiria.class));
		}
		return customerAutoFilteringCritirias;
	}
	
	public EmployeeOnDemandMapping getEmployeeOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
		EmployeeOnDemandMapping employeeOnDemandMapping = null;
		try {
			employeeOnDemandMapping = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_ON_DEMAPND_MAPPING_BY_UNIQUE_ID,
														new Object[] {uniqueId,companyId},
														new BeanPropertyRowMapper<EmployeeOnDemandMapping>(EmployeeOnDemandMapping.class));
		}catch(Exception e) {
			Log.info(this.getClass(), "Got Exception while getEmployeeOnDemandMappingForGivenFormSpec() : "+e);
		}
		return employeeOnDemandMapping;
	}
	
	public Long insertIntoCustomEntityRequisitionJmsMessageStatus(CustomEntityRequisitionJmsMessageStatus requisitionJmsMessageStatus, int status) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_CUSTOM_ENTITY_REQUISITION_JMS_MESSAGE_STATUS,
							Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, requisitionJmsMessageStatus.getType());
					ps.setLong(2, requisitionJmsMessageStatus.getId());
					ps.setLong(3, requisitionJmsMessageStatus.getCompanyId());
					ps.setLong(4, requisitionJmsMessageStatus.getEmpId());
					ps.setInt(5, requisitionJmsMessageStatus.getChangeType());
					ps.setLong(6, requisitionJmsMessageStatus.getEntityId());
					ps.setBoolean(7, requisitionJmsMessageStatus.getByClient());
					ps.setInt(8, status);
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setLong(11, requisitionJmsMessageStatus.getFormId());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
		} catch (Exception e) {
			return 0l;
		}

	}
	
	public Long insertIntoEmployeeRequisitionJmsMessageStatus(EmployeeRequisitionJmsMessageStatus requisitionJmsMessageStatus, int status) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_EMPLOYEE_REQUISITION_JMS_MESSAGE_STATUS,
							Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, requisitionJmsMessageStatus.getType());
					ps.setLong(2, requisitionJmsMessageStatus.getId());
					ps.setLong(3, requisitionJmsMessageStatus.getCompanyId());
					ps.setLong(4, requisitionJmsMessageStatus.getEmpId());
					ps.setInt(5, requisitionJmsMessageStatus.getChangeType());
					ps.setLong(6, requisitionJmsMessageStatus.getEntityId());
					ps.setBoolean(7, requisitionJmsMessageStatus.getByClient());
					ps.setInt(8, status);
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setLong(11, requisitionJmsMessageStatus.getFormId());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
		} catch (Exception e) {
			return 0l;
		}

	}
	
	
	public Long insertIntoWorkRequisitionJmsMessageStatus(WorkRequisitionJmsMessageStatus requisitionJmsMessageStatus, int status) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_WORK_REQUISITION_JMS_MESSAGE_STATUS,
							Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, requisitionJmsMessageStatus.getType());
					ps.setLong(2, requisitionJmsMessageStatus.getId());
					ps.setLong(3, requisitionJmsMessageStatus.getCompanyId());
					ps.setLong(4, requisitionJmsMessageStatus.getEmpId());
					ps.setInt(5, requisitionJmsMessageStatus.getChangeType());
					ps.setLong(6, requisitionJmsMessageStatus.getEntityId());
					ps.setBoolean(7, requisitionJmsMessageStatus.getByClient());
					ps.setInt(8, status);
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setLong(11, requisitionJmsMessageStatus.getFormId());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
		} catch (Exception e) {
			return 0l;
		}

	}
	
	public Long insertIntoCustomerRequisitionJmsMessageStatus(CustomerRequisitionJmsMessageStatus requisitionJmsMessageStatus, int status) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_CUSTOMER_REQUISITION_JMS_MESSAGE_STATUS,
							Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, requisitionJmsMessageStatus.getType());
					ps.setLong(2, requisitionJmsMessageStatus.getId());
					ps.setLong(3, requisitionJmsMessageStatus.getCompanyId());
					ps.setLong(4, requisitionJmsMessageStatus.getEmpId());
					ps.setInt(5, requisitionJmsMessageStatus.getChangeType());
					ps.setLong(6, requisitionJmsMessageStatus.getEntityId());
					ps.setBoolean(7, requisitionJmsMessageStatus.getByClient());
					ps.setInt(8, status);
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setLong(11, requisitionJmsMessageStatus.getFormId());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
		} catch (Exception e) {
			return 0l;
		}

	}
	
	public Long insertIntoProcessJmsMessageStatus(ProcessJmsMessageStatus processJmsMessageStatus, int status) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_PROCESS_JMS_MESSAGE_STATUS,
							Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, processJmsMessageStatus.getType());
					ps.setLong(2, processJmsMessageStatus.getId());
					ps.setLong(3, processJmsMessageStatus.getCompanyId());
					ps.setLong(4, processJmsMessageStatus.getEmpId());
					ps.setInt(5, processJmsMessageStatus.getChangeType());
					ps.setLong(6, processJmsMessageStatus.getEntityId());
					ps.setBoolean(7, processJmsMessageStatus.getByClient());
					ps.setInt(8, status);
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setBoolean(11, processJmsMessageStatus.isMarutiCompany());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
		} catch (Exception e) {
			return 0l;
		}

	}
	
	public Long verifyMobileNumberAndOtp(
			MobileNumberVerification mobileNumberVerification) {
		String sql = Sqls.VERIFY_MOBILE_NUMBER_AND_OTP;
		
		String isValidOtp = "";
		try{
		isValidOtp =  jdbcTemplate.queryForObject(
				sql, new Object[] {
						mobileNumberVerification.getId(),
						mobileNumberVerification.getPhoneNo(),
						mobileNumberVerification.getOtp()
				},String.class);
		}catch(Exception e){
			return (long) MobileNumberVerification.INVALID_OTP;
		}
		if(isValidOtp.equals("0"))
			return (long) MobileNumberVerification.OTP_EXPIRED;
		else
			return (long) MobileNumberVerification.OTP_VERIFIED;
	}
	

public WorkOnDemandMapping getWorkOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
	
	try{
	return jdbcTemplate
			.queryForObject(Sqls.SELECT_WORK_ON_DEMAND_MAPPING_BY_UNIQUEID_FOR_LIST.replace(":formSpecUniqueId", formSpecUniqueId)
					.replace(":companyId", companyId+""),
					new Object[] {},
					new BeanPropertyRowMapper<WorkOnDemandMapping>(
							WorkOnDemandMapping.class));
	}
		catch(Exception e){
			return null;
		}
	}

public int insertWebLocations(long locationId, String browserInfo,String ipAddress) {
	return jdbcTemplate.update(
			Sqls.INSERT_WEB_LOCATON,
			new Object[] {
					locationId,
					browserInfo,
					ipAddress});
}


public void modifyWorkAssignee(Work work, Long modifiedBy,WebUser webUser) {
	try {
		if (work.getAssignTo() != null) {
			jdbcTemplate.update(Sqls.UPDATE_WORK_ASSIGNMENT_DATA, new Object[] { work.getWorkId(),
					work.getAssignTo(), Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),modifiedBy });
		}
	} catch (Exception e) {

	}
	Api.populateLogText(webUser,null);
	int updatedCount = getAuditDao().auditWorkAssignmentDataAuditLogs(work.getWorkId(), webUser,work.getSourceOfModification());
	Log.info(getClass(), "modifyWorkAssignee() // workId = "+work.getWorkId()+" updatedCount = "+updatedCount);
}

public void updateStopWorkLocationForStartWorkLocation(StartWorkStopWorkLocations startWorkStopWorkLocation, long id) {
	try
	{
		String sql = Sqls.UPDATE_STOPWORK_LOCATION_FOR_STARTWORK_LOCATION_THROUGH_WEBLITE;
		jdbcTemplate.update(sql, new Object[]{
				startWorkStopWorkLocation.getStopWorkLocationId(),
				startWorkStopWorkLocation.getStopWorkLocationTime(),
				startWorkStopWorkLocation.getSignOutSource(),
				startWorkStopWorkLocation.getSignOutFormId(),
				startWorkStopWorkLocation.getAutoStopWork(),
				Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
				id });
	}
	catch(Exception e )
	{
		Log.info(this.getClass(), "error in updateAutoSignedOut() >> "+e.toString());
		e.printStackTrace();
	}
}
public long insertStartWorkStopWorkLocations(final StartWorkStopWorkLocations startWorkStopWorkLocation, 
		final long empId,final int companyId) {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_START_WORK_STOP_WORK_LOCATIONS_FOR_SYNC,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, startWorkStopWorkLocation.getStartWorkLocationId());
					ps.setString(2, startWorkStopWorkLocation.getStartWorkLocationTime());
					if(!Api.isEmptyString(startWorkStopWorkLocation.getStartWorkReasonId()) && !"null".equalsIgnoreCase(startWorkStopWorkLocation.getStartWorkReasonId()))
					{
						ps.setString(3, startWorkStopWorkLocation.getStartWorkReasonId());
					}else{
						ps.setNull(3, Types.BIGINT);
					}
					ps.setString(4, startWorkStopWorkLocation.getStartWorkReason());
					ps.setString(5, startWorkStopWorkLocation.getStopWorkLocationId());
					ps.setString(6, startWorkStopWorkLocation.getStopWorkLocationTime());
					ps.setString(7, startWorkStopWorkLocation.getClientCreatedTime());
					ps.setString(8, startWorkStopWorkLocation.getClientModifiedTime());
					ps.setString(9, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(10, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(11, startWorkStopWorkLocation.getClientSideId());
					ps.setString(12, startWorkStopWorkLocation.getCustomerId());
					ps.setString(13,startWorkStopWorkLocation.getClientCode());
					ps.setString(14,companyId+"");
					ps.setString(15, empId+"");
					ps.setInt(16, startWorkStopWorkLocation.getAutoStartWork() == null ? 0 : startWorkStopWorkLocation.getAutoStartWork());
					ps.setInt(17, startWorkStopWorkLocation.getAutoStopWork() == null ? 0 : startWorkStopWorkLocation.getAutoStopWork());
					if(startWorkStopWorkLocation.getStartWorkMediaId() != null)
						ps.setLong(18, startWorkStopWorkLocation.getStartWorkMediaId());
					else
						ps.setNull(18, Types.BIGINT);
					if(startWorkStopWorkLocation.getSignInFormId() != null)
					{
						ps.setLong(19,startWorkStopWorkLocation.getSignInFormId());
					}
					else
					{
						ps.setNull(19, Types.BIGINT);
					}
					if(startWorkStopWorkLocation.getSignOutFormId() != null)
					{
						ps.setLong(20,startWorkStopWorkLocation.getSignOutFormId());
					}
					else
					{
						ps.setNull(20, Types.BIGINT);
					}
					ps.setInt(21,startWorkStopWorkLocation.getSignInSource());
					if(startWorkStopWorkLocation.getCheckInId() != null)
					{
						ps.setLong(22,startWorkStopWorkLocation.getCheckInId());
					}
					else
					{
						ps.setNull(22, Types.BIGINT);
					}
					if(startWorkStopWorkLocation.getCheckOutId() != null)
					{
						ps.setLong(23,startWorkStopWorkLocation.getCheckOutId());
					}
					else
					{
						ps.setNull(23, Types.BIGINT);
					}
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			startWorkStopWorkLocation.setId(id);

			return id;
		}

public EmployeeOnDemandMapping getEmployeeOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
	
	try{
	return jdbcTemplate
			.queryForObject(Sqls.SELECT_EMPLOYEE_ON_DEMAND_MAPPING_BY_UNIQUEID_FOR_LIST.replace(":formSpecUniqueId", formSpecUniqueId)
					.replace(":companyId", companyId+""),
					new Object[] {},
					new BeanPropertyRowMapper<EmployeeOnDemandMapping>(
							EmployeeOnDemandMapping.class));
	}
		catch(Exception e){
			return null;
		}
	}
public CustomEntityOnDemandMapping getCustomEntityOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
	
	try{
	return jdbcTemplate
			.queryForObject(Sqls.SELECT_CUSTOMENTITY_ON_DEMAND_MAPPING_BY_UNIQUEID_FOR_LIST.replace(":formSpecUniqueId", formSpecUniqueId)
					.replace(":companyId", companyId+""),
					new Object[] {},
					new BeanPropertyRowMapper<CustomEntityOnDemandMapping>(
							CustomEntityOnDemandMapping.class));
	}
		catch(Exception e){
			return null;
		}
	}

public CustomerOnDemandMapping getCustomerOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
	
	try{
	return jdbcTemplate
			.queryForObject(Sqls.SELECT_CUSTOMER_ON_DEMAND_MAPPING_BY_UNIQUEID_FOR_LIST.replace(":formSpecUniqueId", formSpecUniqueId)
					.replace(":companyId", companyId+""),
					new Object[] {},
					new BeanPropertyRowMapper<CustomerOnDemandMapping>(
							CustomerOnDemandMapping.class));
	}
		catch(Exception e){
			return null;
		}
	}
}

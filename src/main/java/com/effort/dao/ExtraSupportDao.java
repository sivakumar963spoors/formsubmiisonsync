package com.effort.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.beans.http.request.location.Location;
import com.effort.context.AppContext;
import com.effort.entity.CustomEntity;
import com.effort.entity.CustomEntitySpec;
import com.effort.entity.CustomerEvent;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntitySectionField;
import com.effort.entity.EntitySectionFieldSpec;
import com.effort.entity.FieldRestrictCritria;
import com.effort.entity.FieldSpecFilter;
import com.effort.entity.FieldSpecRestrictionGroup;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.FormCleanUpRule;
import com.effort.entity.FormCustomers;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldGroupSpec;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldsColorDependencyCriterias;
import com.effort.entity.FormFilteringCritiria;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.MobileNumberVerification;
import com.effort.entity.Settings;
import com.effort.entity.StockFormConfiguration;
import com.effort.entity.WorkSpec;
import com.effort.entity.WorkSpecSchedulingConfig;
import com.effort.entity.Workflow;
import com.effort.manager.WebExtensionManager;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Log;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

@Repository
public class ExtraSupportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Constants constants;
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	public List<Long> getEmpWorkReassignmentEmpGroups(String empId) {
		String sql = Sqls.SELECT_WORK_REASSIGNMENT_EMP_GROUPS_FOR_EMP.replace(":empIds", empId+"");
		List<Long> empGroupIds = null;
		try {
			empGroupIds = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);
		} catch (Exception e) {
			Log.info(this.getClass(), "getEmpWorkReassignmentEmpGroups()", e);
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmpWorkReassignmentEmpGroups method",e.toString(),stackTrace,0);
		}
		return empGroupIds;
	}
	
public List<CustomEntity> getCustomEntitiesByCustomEntityIds(String customEntityIdCsv) {
		
		List<CustomEntity> customEntities = new ArrayList<CustomEntity>();
		try
		{
			if(!Api.isEmptyString(customEntityIdCsv))
			{
				String sql = Sqls.SELECT_CUSTOM_ENTITIES_BY_CUSTOM_ENTITY_IDS.replace(":customEntityIds", customEntityIdCsv);
				customEntities = jdbcTemplate.query(sql, new Object[] {},
						new BeanPropertyRowMapper<CustomEntity>(CustomEntity.class));
			}
		}
		catch(Exception e)
		{
			Log.info(getClass(), "getCustomEntitiesByCustomEntityIds() // Exception occured ",e);
		}
		
		return customEntities;
	}
	
	public void updateWorkFlowFormStatusModifiedTime(Long id) 
	{
		if(id != null)
		{
			String sql = Sqls.UPDATE_WORK_FLOW_STATUS_MODIFIED_TIME;
			jdbcTemplate.update(sql, new Object[]{Api.getDateTimeInUTC(new Date(System
					.currentTimeMillis())), id});
		}
	}
	
	public void updateWorkStatusModifiedTime(String workId) {
		if(!Api.isEmptyString(workId)) 
		{
			try 
			{
				Long workIdLongValue = Long.parseLong(workId);
				jdbcTemplate.update(Sqls.UPDATE_WORK_STATUS_MODIFIED_TIME,  new Object[]{Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())),workIdLongValue} );
			}catch(Exception e) {
				
			}
		}
	}
	
	public void insertFormCustomers(List<FormCustomers> formCustomers)
	{
		Long currentInMillies = System.currentTimeMillis();
		List<FormCustomers> formCustomersSubList = new ArrayList<FormCustomers>();
		String sql = new String("INSERT IGNORE INTO `FormCustomers`(`formId`, `customerId`) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		
		for(int i =0; i < formCustomers.size(); i++)
		{
			if(first)
			{
				placeHolderString.append(" (?,?)");
				first = false;
			}
			else
			{
				placeHolderString.append(", (?,?)");
			}
			formCustomersSubList.add(formCustomers.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (formCustomers.size()-1))
			{
				insertFormCustomers(sql + placeHolderString, formCustomersSubList);
				placeHolderString = new StringBuffer();
				first = true;
				formCustomersSubList = new ArrayList<FormCustomers>();
			}
		}
		
		Log.info(getClass(), "insertFormCustomers() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		
	}

	public void insertFormCustomers(final String sql,
			final List<FormCustomers> formCustomers) 
	{
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < formCustomers.size(); i++)
				{
					FormCustomers formCustomer = formCustomers.get(i);
					ps.setLong(placeHolder++, formCustomer.getFormId());
					ps.setLong(placeHolder++, formCustomer.getCustomerId());
				}
				return ps;
			}
		});
	}
	
	public Employee getAssignedEmployeeForGivenWork(String workId) {
		try {
			return jdbcTemplate.queryForObject(
					Sqls.SELECT_ASSIGNED_EMPLOYEE_FOR_GIVEN_WORK,
					new Object[] { workId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception ex) {
			Log.info(getClass(), "Exception while retriving employee", ex);
		}
		return null;
	}

	public Settings getSettingValueByKeyAndCompanyId(
			String enableActionableWorksResolvingKey, int companyId) {
		Settings actionableSetting = null;
		try{
			actionableSetting = jdbcTemplate.queryForObject(Sqls.SELECT_SETTING_VALUE_BY_KEY_AND_COMPANY_ID, new Object[]{companyId,enableActionableWorksResolvingKey}, 
					new BeanPropertyRowMapper<Settings>(Settings.class));
		}catch(Exception e){
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getSettingValueByKeyAndCompanyId method",e.toString(),stackTrace,companyId);
			Log.info(this.getClass(), "Got Exception While getSettingValueByKeyAndCompanyId : "+e);
		}
		return actionableSetting;
	}
public List<EntitySectionField> getEntitySectionFieldsByEntityIn(List<Entity> entities) {
		
		String ids = Api.toCSV(entities, "entityId", CsvOptions.NONE);

		return getEntitySectionFieldsByEntityIn(ids);
	}

public List<EntitySectionField> getEntitySectionFieldsByEntityIn(String entityIds) {
	
	if(!Api.isEmptyString(entityIds))
	{
		try {
			String sql = Sqls.SELECT_ENTITY_SECTION_FIELD_BY_ENTITY_IN.replace(":ids",
					entityIds);
			List<EntitySectionField> entitySectionFields = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<EntitySectionField>(
							EntitySectionField.class));
			return entitySectionFields;
		} catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<EntitySectionField>();
		}
	}
	else
	{
		return new ArrayList<EntitySectionField>();
	}
	
}
public Map<Long, EntitySectionFieldSpec> getEntitySectionFieldSpecMapForFields(
		List<EntitySectionField> entitySectionFields) {
	if (entitySectionFields != null && entitySectionFields.size() > 0) {
		String ids = "";
		for (EntitySectionField entitySectionField : entitySectionFields) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}

			ids += "'" + entitySectionField.getSectionFieldSpecId() + "'";
		}

		return getEntitySectionFieldSpecMapForFields(ids);
	} else {
		return new HashMap<Long, EntitySectionFieldSpec>();
	}
}

public Map<Long, EntitySectionFieldSpec> getEntitySectionFieldSpecMapForFields(
		String sectionFieldSpecIds) {
	Map<Long, EntitySectionFieldSpec> map = new HashMap<Long, EntitySectionFieldSpec>();

	if (!Api.isEmptyString(sectionFieldSpecIds)) {
		List<EntitySectionFieldSpec> entitySectionFieldSpec = jdbcTemplate
				.query(Sqls.SELECT_ENTITY_SECTION_FIELD_SPECS_IN.replace(
						":ids", sectionFieldSpecIds), new Object[] {},
						new BeanPropertyRowMapper<EntitySectionFieldSpec>(
								EntitySectionFieldSpec.class));

		for (EntitySectionFieldSpec entitySectionFieldSpecTemp : entitySectionFieldSpec) {
			map.put(entitySectionFieldSpecTemp.getSectionFieldSpecId(),
					entitySectionFieldSpecTemp);
		}

	}

	return map;
}

public List<FormFieldsColorDependencyCriterias> getFormFieldsColorDependencyCriteriasByFormSpecId(
		String formSpecId) {
	String sql = Sqls.SELECT_FORM_FIELDS_COLOR_DEPENDENCY_CRITERIAS.replace(":formSpecIds", formSpecId);
	List<FormFieldsColorDependencyCriterias> colorDependencies = jdbcTemplate.query(sql,
			new BeanPropertyRowMapper<FormFieldsColorDependencyCriterias>(FormFieldsColorDependencyCriterias.class));
	return colorDependencies;
}

public List<FormFieldGroupSpec> getFormFieldGroupSpecForIn(
		List<FormSpec> formSpecs) {
	if (formSpecs != null && formSpecs.size() > 0) {
		String ids = "";
		for (FormSpec formSpec : formSpecs) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}
			ids += formSpec.getFormSpecId();
		}

		List<FormFieldGroupSpec> formFieldGroupSpecs = jdbcTemplate.query(
				Sqls.SELECT_FORM_FIELD_GROUP_SPECS_IN.replace(":ids", ids),
				new Object[] {},
				new BeanPropertyRowMapper<FormFieldGroupSpec>(FormFieldGroupSpec.class));

		return formFieldGroupSpecs;
	}

	return new ArrayList<FormFieldGroupSpec>();
}
public List<FormCleanUpRule> getFormDataCleanUpRulesForFormSpecId(
		String formSpecIds) 
{
	String sql = Sqls.SELECT_FORM_CLEAN_UP_RULES_BY_FORM_SPEC_ID.replace(":formSpecIds", formSpecIds);;
	return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<FormCleanUpRule>(FormCleanUpRule.class));
}
public List<FormFilteringCritiria> getFormFilteringCritiriasForFormSpecs(
		String formSpecIds) {
	List<FormFilteringCritiria> formFilteringCritiria = new ArrayList<FormFilteringCritiria>();
	if (!Api.isEmptyString(formSpecIds)) {
		String sql = Sqls.SELECT_FORM_FILTERING_CRITIRIA_FOR_FORMSPECS
				.replace(":formSpecIds", formSpecIds);
		formFilteringCritiria = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<FormFilteringCritiria>(
						FormFilteringCritiria.class));
	}
	return formFilteringCritiria;
}


public List<FieldValidationCritiria> getFieldValidationCritirias(
		String formSpecIds) {
	List<FieldValidationCritiria> fieldValidationCritirias = new ArrayList<FieldValidationCritiria>();
	if(!Api.isEmptyString(formSpecIds))
	{
		String sql = Sqls.SELECT_FIELD_VALIDATION_CRITIRIA.replace(":formSpecIds", formSpecIds);
		fieldValidationCritirias = jdbcTemplate.query(
				sql, new Object[] {},
				new BeanPropertyRowMapper<FieldValidationCritiria>(
						FieldValidationCritiria.class));
	}
	return fieldValidationCritirias;
	
}

public List<StockFormConfiguration> getStockFieldConfigurationsForSync(
		String formSpecIds) 
{
	List<StockFormConfiguration> stockFormConfigurations = new ArrayList<StockFormConfiguration>();
	
	if(!Api.isEmptyString(formSpecIds))
	{
		String sql = Sqls.SELECT_STOCK_FORM_CONFIGURATIONS_FOR_FORMSPEC_IDS.replace(":formSpecIds", formSpecIds);
		return jdbcTemplate.query(sql, new Object[]{},
				new BeanPropertyRowMapper<StockFormConfiguration>(StockFormConfiguration.class));
	}
	return stockFormConfigurations;
}
public List<FieldSpecFilter> getFieldSpecFiltersFormFormSpecIds(String formSpecIds,
		int formfieldType) 
{
	String sql = "";
	if (formfieldType == FieldSpecFilter.FIELD_IS_FORMFIELD) {
		sql = Sqls.SELECT_FORM_FIELD_SPEC_FILTERS_FORMSPECIDS.replace(":formSpecIds", formSpecIds);
	} else {
		sql = Sqls.SELECT_FORM_SECTION_FIELD_SPEC_FILTERS_FORMSPECIDS.replace(":formSpecIds", formSpecIds);
	}
	List<FieldSpecFilter> fieldSpecFilters = jdbcTemplate
			.query(sql, new Object[] {},
					new BeanPropertyRowMapper<FieldSpecFilter>(
							FieldSpecFilter.class));
	return fieldSpecFilters;
}

public List<EmployeeGroup> getEmployeesOfEmployeeGroups(String empGroupIds)
{
	List<EmployeeGroup> empGroups = new ArrayList<EmployeeGroup>();
	if(!Api.isEmptyString(empGroupIds))
	{
		String sql = Sqls.SELECT_ACTIVE_EMPS_IN_EMP_GROUPS.replace(":empGroupIds", empGroupIds);
		empGroups = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<EmployeeGroup>(EmployeeGroup.class));
	}
	return empGroups;
}

public List<CustomerEvent> getCutomerEventsByCustomerId(String customerIds) {
	
	List<CustomerEvent> customerEvents = new ArrayList<CustomerEvent>();
	try
	{
		String sql = Sqls.SELECT_CUSTOMER_EVENTS_BY_CUSTOMER_ID.replace(":customerIds", customerIds);
		customerEvents = jdbcTemplate.query(sql,new BeanPropertyRowMapper<CustomerEvent>(CustomerEvent.class));
	}
	catch(Exception e)
	{
		Log.info(this.getClass(), "got Exception while getCutomerEventsByCustomerId : "+e);
	}
	return customerEvents;
}
public void saveLocationExtra(final Location location) 
{
	jdbcTemplate.update(new PreparedStatementCreator() {
	@Override
	public PreparedStatement createPreparedStatement(
				Connection connection) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(
					Sqls.INSERT_LOCATION_EXTRA, Statement.RETURN_GENERATED_KEYS);
	ps.setLong(1,location.getLocationId());
	if (location.getAirplaneMode() == null) {
		ps.setNull(2, Types.INTEGER);
	} else {
		ps.setInt(2, location.getAirplaneMode());
	}
	
	if (location.getEmpLocationProvider() == null) {
		ps.setNull(3, Types.INTEGER);
	} else {
		ps.setInt(3, location.getEmpLocationProvider());
	}
	
	return ps;
		}
	});
	
	
}
public void saveMockLocation(final Location location) 
{
	jdbcTemplate.update(new PreparedStatementCreator() {
	@Override
	public PreparedStatement createPreparedStatement(
				Connection connection) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(
					Sqls.INSERT_MOCK_LOCATION, Statement.RETURN_GENERATED_KEYS);
	ps.setLong(1,location.getLocationId());
	ps.setBoolean(2, location.isMockLocation());
	return ps;
		}
	});
}
public List<CustomEntitySpec> getCustomEntitySpecs(Integer companyId) 
{
	List<CustomEntitySpec> customEntitySpecs = new ArrayList<CustomEntitySpec>();
	 try{
		  String sql=Sqls.SELECT_CUSTOM_ENTITY_SPECS;
		  customEntitySpecs = jdbcTemplate
					.query(sql,new Object[] {companyId},
							new BeanPropertyRowMapper<CustomEntitySpec>(CustomEntitySpec.class));
	 }catch(Exception e){
		 Log.info(this.getClass(), "getCustomEntitySpecs : "+e);
		// StackTraceElement[] stackTrace = e.getStackTrace();
		// getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getCustomEntitySpecs method",e.toString(),stackTrace,companyId);
	 }
	return customEntitySpecs;
}



public FormSectionFieldSpec getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(
		String formSpecId, String sectionFieldSpecId)
{
	FormSectionFieldSpec sectionFieldSpec = null;
	String sql = Sqls.SELECT_FORM_SECTION_FIELD_BY_FORM_SPEC_ID_AND_SECTION_FIELD_SPEC_ID;
	try
	{
		sectionFieldSpec = jdbcTemplate.queryForObject(sql, new Object[] {formSpecId,sectionFieldSpecId},
				new BeanPropertyRowMapper<FormSectionFieldSpec>(FormSectionFieldSpec.class));
		return sectionFieldSpec;
	}
	catch(Exception e)
	{
		Log.info(this.getClass(), "got exception getFormSectionFieldByFormSpecIdAndSectionFieldSpecId : "+e);
	}
	return sectionFieldSpec;
}
public void insertActivityStreamWebNotification(final List<Long> empIds,final long activityStreamId)
{
	jdbcTemplate.batchUpdate(
			Sqls.INSERT_INTO_ACTIVITY_STREAM_WEB_NOTIFICATION,
			new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					Long empId = empIds.get(i);
					ps.setLong(1,empId);
					ps.setLong(2,activityStreamId);
				}

				@Override
				public int getBatchSize() {
					return empIds.size();
				}
			});
}
public CustomEntity getCustomEntityByCustomEntityId(String customEntityId) {
	CustomEntity customEntity = new CustomEntity();
	try{
		String sql = Sqls.SELECT_CUSTOM_ENTITY_BY_CUSTOM_ENTITY_ID;
		
		customEntity= jdbcTemplate.queryForObject(sql,new Object[] {customEntityId},
				new BeanPropertyRowMapper<CustomEntity>(CustomEntity.class));
	}
	catch(Exception e){
		Log.info(getClass(), "getCustomEntityByCustomEntityId() // Exception occured ",e);
	}
	return customEntity;
}

public FormFieldSpec getFormFieldSpecByFormSpecIdAndFieldSpecId(
		String formSpecId, String fieldSpecId) {
	FormFieldSpec formFieldSpec = new FormFieldSpec();
	try
	{
		String sqls = Sqls.SELECT_FORM_FIELD_SPEC_BY_FIELD_SPEC_ID;
		formFieldSpec = jdbcTemplate.queryForObject(sqls, new Object[] {fieldSpecId,formSpecId},new BeanPropertyRowMapper<FormFieldSpec>(
				FormFieldSpec.class));
	}
	catch(Exception e)
	{
		Log.info(getClass(), "getFormFieldSpecByFormSpecIdAndFieldSpecId() // got exception"+e);
	}
	return formFieldSpec;
}
public List<FormFieldSpec> getFormFieldSpecByFormSpecIdAndFieldType(String formSpecId,String fieldType) {
	 
	 String sql = Sqls.SELECT_FIELD_SPEC_BY_SPEC_ID_AND_FIELD_TYPE.replaceAll(":formSpecId", formSpecId)
			 .replaceAll(":fieldType", fieldType);
		return jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<FormFieldSpec>(FormFieldSpec.class));
	}


public void updateOtpGenerationData(MobileNumberVerification mobileNumberVerification, int status) {
	String sql = Sqls.UPDATE_MOBILE_NUMBER_VERIFICATION;
	jdbcTemplate.update(sql, new Object[] {Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),status,
			mobileNumberVerification.getId()});
	
}

@SuppressWarnings("deprecation")
public String getExistedValueAgainstFormSpecAndSectionFieldSpec(FormSectionField formField) {
	String sql = Sqls.GET_EXISTED_VALUE_AGAINST_FORMID_AND_SECTIONFIELDSPECID;
	String fieldValue = "";
	try{
		fieldValue =  jdbcTemplate.queryForObject(
				sql, new Object[] {
						formField.getFormId(),
						formField.getFormSpecId(),
						formField.getSectionFieldSpecId(),
						formField.getInstanceId()
				},String.class);
	}catch(Exception e){
		e.printStackTrace();
	}
	return fieldValue;
}

public WorkSpec getWorkSpecUsingFormSpecUniqueId(String uniqueId) {

	try{
		return jdbcTemplate
				.queryForObject(Sqls.GET_WORKSPEC_FROM_FORMSPEC_UNIQUEID,
						new Object[] { uniqueId },
						new BeanPropertyRowMapper<WorkSpec>(
								WorkSpec.class));
		}
		catch(Exception e){
			Log.info(getClass(),"Error in getWorkActionSpec "+e.getMessage());
			return null;
		}
	
}	

public EntityField getEntityField(Long entityId,Long entitySpecId,Long entityFieldSpecId) {
			EntityField entityField = null;
			try{
				entityField = jdbcTemplate.queryForObject(
						Sqls.SELETCTT_ENTITY_FIELD, new Object[] {entityId,entitySpecId,entityFieldSpecId},
						new BeanPropertyRowMapper<EntityField>(EntityField.class));
			}catch(EmptyResultDataAccessException e){
				Log.info(getClass(), "no EntityField found for entityId:"+entityId+" entitySpecId: "+entitySpecId+" entityFieldSpecId:"+entityFieldSpecId);
			}

			return entityField;
		}
	  

public WorkSpecSchedulingConfig getWorkSpecSchedulingConfig(Long workSpecId) {
	WorkSpecSchedulingConfig workSpecSchedulingConfig = null;
	try {
		workSpecSchedulingConfig = jdbcTemplate.queryForObject(Sqls.SELECT_WORKSPEC_SCHEDULING_CONFIG,
				new Object[]{workSpecId}, new BeanPropertyRowMapper<WorkSpecSchedulingConfig>(WorkSpecSchedulingConfig.class));
	} catch (IncorrectResultSizeDataAccessException ie) {
		Log.ignore(this.getClass(), ie);
	} catch (Exception e) {
		Log.info(this.getClass(), "Exception" , e);
	}
	
	return workSpecSchedulingConfig;
}

public List<FormFieldGroupSpec> getFormFieldGroupSpecForIn(String formSpecIds) {

	String sql = Sqls.SELECT_FORM_FIELD_GROUP_SPECS_IN.replace(":ids", formSpecIds);
		List<FormFieldGroupSpec> formFieldGroupSpecs = jdbcTemplate.query(sql,
				new Object[] {},
				new BeanPropertyRowMapper<FormFieldGroupSpec>(FormFieldGroupSpec.class));

		return formFieldGroupSpecs;
}
public CustomEntitySpec getCustomEntitySpecByFormSpecUniqueId(String uniqueId, Integer companyId) {
	CustomEntitySpec customEntitySpec = new CustomEntitySpec();
	try{
		String sql = Sqls.SELECT_CUSTOM_ENTITY_SPEC_BY_FORM_SPEC_UNIQUE_ID;
		
		customEntitySpec = jdbcTemplate.queryForObject(sql,new Object[] {uniqueId,companyId},
				new BeanPropertyRowMapper<CustomEntitySpec>(CustomEntitySpec.class));
	}
	catch(Exception e){
		Log.info(getClass(), "getCustomEntitySpec() // Exception occured ",e);
	}
	return customEntitySpec;
}


 
public int modifyCustomEntity(CustomEntity customEntity) {

	return jdbcTemplate.update(
			Sqls.UPDATE_CUSTOM_ENTITY,
			new Object[] {
					customEntity.getCustomEntityNo(),
					customEntity.getCustomEntityName(),
					customEntity.getCustomEntityLocation(),
					customEntity.getClientSideId(),
					customEntity.getClientCode(),
					customEntity.getParentCustomEntityId(),
					customEntity.getModifiedBy(),
					Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
					customEntity.getAssignTo(),
					customEntity.getLastActivityName(),
					customEntity.getLastActivityTime(),
					customEntity.getCustomEntityFieldsUniqueKey(),
					customEntity.getFormId(),
					customEntity.getCustomEntityId() 
				});
}

public List<FormFilteringCritiria> getFormFilteringCritirias(
		String formSpecId) {
	String sql = Sqls.SELECT_FORM_FILTERING_CRITIRIA;
	List<FormFilteringCritiria> formFilteringCritiria = jdbcTemplate.query(
			sql, new Object[] { formSpecId },
			new BeanPropertyRowMapper<FormFilteringCritiria>(
					FormFilteringCritiria.class));
	return formFilteringCritiria;
}

public Workflow getWorkFlowMappedFormEntity(int companyId, String entityId,
		short entityType) {
	String sql = Sqls.SELECT_WORKFLOW_FOR_FORMSPEC_UNIQUEID;
	Workflow workflow = null;
	try {
		workflow = jdbcTemplate.queryForObject(sql, new Object[]{companyId, entityType, entityId} , new BeanPropertyRowMapper<Workflow>(Workflow.class));
	} catch (Exception e) {
		Log.ignore(this.getClass(), entityId);
	}
	return workflow;
}


public String getExistedValueAgainstFormSpecAndFieldSpec(FormField formField) {
	String sql = Sqls.GET_EXISTED_VALUE_AGAINST_FORMID_AND_FORMSPECID;

	String fieldValue = "";
	try{
		fieldValue =  jdbcTemplate.queryForObject(
				sql, new Object[] {
						formField.getFormId(),
						formField.getFormSpecId(),
						formField.getFieldSpecId()
				},String.class);
	}catch(Exception e){
		e.printStackTrace();
	}
	return fieldValue;
}

public List<FieldRestrictCritria> getFieldSpecRestrictedCritiriaOfNotMine(
		String formSpecId, int type, String groupIds) {
	if (!Api.isEmptyString(groupIds)) {
		String sql = "";
		if (type == FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD) {
			sql = Sqls.SELECT_FIELDS_WITH_RESTRICTIONS_OF_NOT_MINE.replace(":groupIds",
					groupIds);
		} else {
			sql = Sqls.SELECT_SECTION_FIELLD_RESTRICTIONS_OF_NOT_MINE.replace(
					":groupIds", groupIds);
		}

		sql = sql.replace(":formSpecIds", formSpecId);
		List<FieldRestrictCritria> fieldRestrictCritrias = jdbcTemplate
				.query(sql, new Object[] {},
						new BeanPropertyRowMapper<FieldRestrictCritria>(
								FieldRestrictCritria.class));
		return fieldRestrictCritrias;
	}

	return new ArrayList<FieldRestrictCritria>();

}

}

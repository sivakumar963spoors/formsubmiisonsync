package com.effort.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.effort.dao.rollbackhandler.ClientIdCallBackHandler;
import com.effort.entity.CompanyFont;
import com.effort.entity.CompanyRestApis;
import com.effort.entity.CustomEntityOnDemandMapping;
import com.effort.entity.EmployeeFilteringCritiria;
import com.effort.entity.FlatDataTableConfiguration;
import com.effort.entity.FlatTableDataStatus;
import com.effort.entity.Form;
import com.effort.entity.FormCustomEntities;
import com.effort.entity.FormField;
import com.effort.entity.FormListUpdate;
import com.effort.entity.FormRejectionStagesConfiguration;
import com.effort.entity.FormSpec;
import com.effort.entity.FormSpecConfigSaveOnOtpVerify;
import com.effort.entity.OfflineListUpdateConfiguration;
import com.effort.entity.Settings;
import com.effort.entity.WebAppTool;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
@Repository
public class ExtraSupportAdditionalDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Constants constants;
	
	
	public List<Form> getMasterFormsIn(String formIds) {
		if(!Api.isEmptyString(formIds)) {
			return jdbcTemplate.query(Sqls.SELECT_MASTER_FORMS_BY_IDS.replace(":ids",formIds), 
					new BeanPropertyRowMapper<Form>(Form.class));
		}
		return new ArrayList<Form>();
	}
	public long insertOrUpdateFormListUpdates(final FormListUpdate formListUpdate) {
		  
		  KeyHolder keyHolder = new GeneratedKeyHolder();
			 jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
			  
			   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_OR_UPDATE_FORM_LIST_UPDATES, 
						Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, formListUpdate.getFormId());
						ps.setInt(2, formListUpdate.getListUpdateStatus());
						ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
						ps.setString(4, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
						ps.setString(5, formListUpdate.getRemarks());
						ps.setLong(6, formListUpdate.getCompanyId());
						return ps;
				}
			}, keyHolder);

			return formListUpdate.getFormId();
	  }
	
public void insertWorkFlowForCdsgStockUpdate(Long entityId , long companyId, int type, int changeType, int processingStatus) {
		
		jdbcTemplate.update(
				Sqls.INSERT_WORK_FLOW_FOR_CDSG_STOCK_UPDATE,
				new Object[] { entityId, companyId, type, changeType, processingStatus,
						Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
						Api.getDateTimeInUTC(new Date(System.currentTimeMillis()))
				});
	}

	
	
	public void deleteFormCustomEntitiesForForm(long formId) {
		String sql = Sqls.DELETE_FORM_CUSTOM_ENTITIES_FOR_FORM_ID;
		jdbcTemplate.update(sql, new Object[]{formId});
	}
	
	
	public List<FormField> getMasterFormFieldsByFormId(long formId) {
		return jdbcTemplate.query(
				Sqls.SELECT_MASTER_FORM_FIELD_BY_FORM_ID,
				new Object[] { formId }, new BeanPropertyRowMapper<FormField>(
						FormField.class));
	}

	public Form getMasterForm(Long formId) {
		Form form = null;
		try {
			form = jdbcTemplate.queryForObject(Sqls.SELECT_MASTER_FORM,
					new Object[] { formId },new BeanPropertyRowMapper<Form>(Form.class));
		}catch(Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getMasterForm method",e.toString(),stackTrace,0);
		}
		return form;
	}
	public List<WebAppTool> getWebAppToolsByFormSpecUniqueId(String formSpecUniqueId) {
		return jdbcTemplate.query(Sqls.SELECT_WEB_APP_TOOLS_BY_FORM_SPEC_UNIQUE_ID, new Object[]{formSpecUniqueId},
				new BeanPropertyRowMapper<WebAppTool>(WebAppTool.class));
	}
	public List<OfflineListUpdateConfiguration> getFormSpecOfflineListUpdateConfiguration(
			FormSpec formSpec) 
	{
		return jdbcTemplate.query(
				Sqls.SELECT_OFFLINE_LIST_UPDATE_FORM_CONFIGURATION, new Object[] {formSpec.getUniqueId()},
				new BeanPropertyRowMapper<OfflineListUpdateConfiguration>(OfflineListUpdateConfiguration.class));
	}
	
	public Map<String, Long> getMasterFormClientSideIds(
			List<Form> addedMasterForms, String code) 
	{
		ClientIdCallBackHandler clientIdCallBackHandler = new ClientIdCallBackHandler();
		code = "'" + code + "'";

		if (!Api.isEmptyString(code)) {
			String clientSideIds = "";
			for (Form form : addedMasterForms) {
				if (!Api.isEmptyString(form.getClientFormId())) {
					if (!Api.isEmptyString(clientSideIds)) {
						clientSideIds += ",";
					}

					clientSideIds += "'" + form.getClientFormId() + "'";
				}
			}

			if (!Api.isEmptyString(clientSideIds)) {
				jdbcTemplate.query(
						Sqls.SELECT_CLIENT_IDS_FOR_MASTER_FORMS.replace(":clientSideIds",
								clientSideIds).replace(":clientCode", code),
						clientIdCallBackHandler);
			}
		}

		return clientIdCallBackHandler.getClientIdMap();
	}
	
	
	public void insertFormCustomEntities(
			List<FormCustomEntities> formCustomEntities) {
		Long currentInMillies = System.currentTimeMillis();
		List<FormCustomEntities> formCustomEntitySubList = new ArrayList<FormCustomEntities>();
		String sql = new String("INSERT IGNORE INTO `FormCustomEntities`(`formId`, `customEntityId`) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		
		for(int i =0; i < formCustomEntities.size(); i++)
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
			formCustomEntitySubList.add(formCustomEntities.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (formCustomEntities.size()-1))
			{
				insertFormCustomEntity(sql + placeHolderString, formCustomEntitySubList);
				placeHolderString = new StringBuffer();
				first = true;
				formCustomEntitySubList = new ArrayList<FormCustomEntities>();
			}
		}
		
		Log.info(getClass(), "insertFormCustomEntities() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		
	}
	
	private void insertFormCustomEntity(final String sql,
			final List<FormCustomEntities> formCustomEntities)
	{

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < formCustomEntities.size(); i++)
				{
					FormCustomEntities formCustomEntity = formCustomEntities.get(i);
					ps.setLong(placeHolder++, formCustomEntity.getFormId());
					ps.setLong(placeHolder++, formCustomEntity.getCustomEntityId());
				}
				return ps;
			}
		});
	
	}
	public void insertIntoFlatDataTables(long formId, Integer companyId, String formSpecUniqueId,boolean isWorkActionForm) {
	    jdbcTemplate.update(
	            Sqls.INSERT_BY_SELECT_INTO_FLAT_DATA_TABLES_STATUS,
	            new Object[] {formId, FlatTableDataStatus.STATUS_UN_PROCESSED, FlatTableDataStatus.TYPE_CREATE, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
	                    Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), formSpecUniqueId, companyId,isWorkActionForm});
	}
	
	public List<FlatDataTableConfiguration> getFlatDataTableConfigurationFromColumnId(String columnId, int companyId) {
	    try
	    {  
	        String sql = Sqls.SELECT_FLAT_DATA_TABLE_CONFIGURATION_FROM_COLUMN_ID;
	        
	        Object[] objArray = new Object[] {columnId, companyId};
	        Log.info(getClass(), "getFlatDataTableConfiguration() query = "+Api.displayQuery(sql,objArray));
	        return jdbcTemplate.query(sql, objArray,
	                new BeanPropertyRowMapper<FlatDataTableConfiguration>(
	                        FlatDataTableConfiguration.class));
	    }
	    catch(Exception e)
	    {
	        Log.info(getClass(), "getFlatDataTableConfigurationFromColumnId()// got exception "+e.toString());
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public FormRejectionStagesConfiguration getFormRejectionStagesConfigurationByStageId(int companyId,long workFlowId,long stageId) 
	{
		try {
			String sql = Sqls.SELECT_FORM_REJECTION_STAGES_CONFIGURATION_BY_STAGEID;
			Object[] objArray = new Object[] {companyId,workFlowId,stageId};
			Log.info(getClass(), "getFormRejectionStagesConfiguration() // displayQuery = "+Api.displayQuery(sql,objArray));
			return  jdbcTemplate.queryForObject(sql, objArray,
					new BeanPropertyRowMapper<FormRejectionStagesConfiguration>(FormRejectionStagesConfiguration.class));
		} catch (Exception ex) {
			Log.info(getClass(), "getFormRejectionStagesConfiguration() Got Exception :- "+ex.toString());
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
	public List<FormField> getMasterFormFieldsForSync(String formIds) {
		if (!Api.isEmptyString(formIds)) {
			return jdbcTemplate.query(
					Sqls.SELECT_MASTER_FORM_FIELD_SYNC.replace(":ids", formIds),
					new Object[] {}, new BeanPropertyRowMapper<FormField>(
							FormField.class));
		}

		return new ArrayList<FormField>();
	}
	

	public List<EmployeeFilteringCritiria> getListOfEmployeeFilterCriterias(
			String formSpecIds) {
		List<EmployeeFilteringCritiria> employeeFilteringCritiria = new ArrayList<EmployeeFilteringCritiria>();
		if(!Api.isEmptyString(formSpecIds))
		{
			String sql = Sqls.SELECT_EMPLOYEE_FILTER_CRITERIA.replace(":formSpecIds", formSpecIds);
			employeeFilteringCritiria = jdbcTemplate.query(
					sql, new Object[] {},
					new BeanPropertyRowMapper<EmployeeFilteringCritiria>(
							EmployeeFilteringCritiria.class));
		}
		return employeeFilteringCritiria;
		
	}
	public List<FormSpecConfigSaveOnOtpVerify> getFormSpecConfigSaveOnOtpVerifyList(String uniqueIdsCsv) {
		String sql = Sqls.SELECT_FORMSPEC_CONFIG_SAVE_ON_OTP_VERIFY.replace(":formSpecUniqueIds", uniqueIdsCsv);
		List<FormSpecConfigSaveOnOtpVerify> formSpecConfigSaveOnOtpVerifyList = new ArrayList<>();
		if (!Api.isEmpty(uniqueIdsCsv)) {
			formSpecConfigSaveOnOtpVerifyList = jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper<FormSpecConfigSaveOnOtpVerify>(FormSpecConfigSaveOnOtpVerify.class));
		} 
		return formSpecConfigSaveOnOtpVerifyList;
	}
	public void insertIntoFlatDataTablesForLocation(long locatonId) {
	    jdbcTemplate.update(
	            Sqls.INSERT_FLAT_TABLE_DATA_STATUS_LOCATIONS,
	            new Object[] {locatonId, FlatTableDataStatus.STATUS_UN_PROCESSED, FlatTableDataStatus.TYPE_CREATE, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
	                    Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), FlatDataTableConfiguration.LOCATION_TYPE});
	}
	public List<OfflineListUpdateConfiguration> getOfflineListUpdateConfigurationsForSync(
			String formSpecIds) 
	{
		List<OfflineListUpdateConfiguration> offlineListUpdateConfiguration = new ArrayList<OfflineListUpdateConfiguration>();
		
		if(!Api.isEmptyString(formSpecIds))
		{
			String sql = Sqls.SELECT_OFFLINE_LIST_UPDATE_FORM_CONFIGURATIONS_FOR_FORMSPEC_IDS.replace(":formSpecIds", formSpecIds);
			return jdbcTemplate.query(sql, new Object[]{},
					new BeanPropertyRowMapper<OfflineListUpdateConfiguration>(OfflineListUpdateConfiguration.class));
		}
		return offlineListUpdateConfiguration;
	}
	public CustomEntityOnDemandMapping getCustomEntityOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
		CustomEntityOnDemandMapping customEntityOnDemandMapping = null;
		try {
			customEntityOnDemandMapping = jdbcTemplate.queryForObject(Sqls.SELECT_CUSTOM_ENTITY_ON_DEMAPND_MAPPING_BY_UNIQUE_ID,
														new Object[] {uniqueId,companyId},
														new BeanPropertyRowMapper<CustomEntityOnDemandMapping>(CustomEntityOnDemandMapping.class));
		}catch(Exception e) {
			Log.info(this.getClass(), "Got Exception while getCustomEntityOnDemandMappingForGivenFormSpec() : "+e);
		}
		return customEntityOnDemandMapping;
	}
	
	public List<CompanyRestApis> getCompanyRestApiDataWithCompanyId(int type, String companyId)  {
		String sql = Sqls.SELECT_COMPANY_REST_API_DATA_WITH_COMPANYID;
		return jdbcTemplate.query(sql, new Object[] {type,companyId},new BeanPropertyRowMapper<CompanyRestApis>(CompanyRestApis.class));
	}
	public List<FormField> getMasterFormFieldsByForm(long formId) {
		List<FormField> masterFormFields = jdbcTemplate.query(
				Sqls.SELECT_MASTER_FORM_FIELD_BY_FORM_AND_FIELD,
				new Object[] { formId }, new BeanPropertyRowMapper<FormField>(
						FormField.class));
		return masterFormFields;
	}
	public FormSpec getFormSpecByFormSpecId(long formSpecId) {
		String sql = Sqls.SELECT_FORMSPEC;
		return jdbcTemplate.queryForObject(sql, new Object[] {
				formSpecId},new BeanPropertyRowMapper<FormSpec>(FormSpec.class));
	}
	
	public CompanyFont getCompanyFont(long fontId) {
		CompanyFont companyFont = jdbcTemplate.queryForObject(
				Sqls.SELECT_COMPANY_FONT, new Object[] { fontId },
				new BeanPropertyRowMapper<CompanyFont>(CompanyFont.class));
		return companyFont;
	}
}

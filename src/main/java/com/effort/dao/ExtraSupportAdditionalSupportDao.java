package com.effort.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.effort.context.AppContext;
import com.effort.entity.AutoGenereteSequenceSpecConfiguarationField;
import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.CustomEntitySpecStockConfiguration;
import com.effort.entity.FlatDataTableConfiguration;
import com.effort.entity.FlatTableDataStatus;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpecsExtra;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionFieldSpecsExtra;
import com.effort.entity.FormSpec;
import com.effort.entity.OfflineListUpdateConfiguration;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.effort.entity.WorkOnDemandMapping;
import com.effort.manager.WebExtensionManager;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Log;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
@Repository
public class ExtraSupportAdditionalSupportDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Constants constants;
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	public List<FormSectionFieldSpec> getFormSectionFieldSpecsByFormSpec(long formSpecId) {
		List<FormSectionFieldSpec> formSectionFieldSpecs = jdbcTemplate.query(
				Sqls.SELECT_FORM_SECTION_FIELD_SPECS, new Object[] { formSpecId },
				new BeanPropertyRowMapper<FormSectionFieldSpec>(FormSectionFieldSpec.class));
		return formSectionFieldSpecs;
	}
	
	
	public long insertRichTextFormField(final RichTextFormField richTextFormField) {
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
		  
		   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_RICHTEXT_FORMFIELDS, 
					Statement.RETURN_GENERATED_KEYS);
				    ps.setLong(1, richTextFormField.getFormSpecId());
					ps.setLong(2, richTextFormField.getFieldSpecId());
					ps.setLong(3, richTextFormField.getFormId());
					ps.setString(4, richTextFormField.getValue());
					return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		richTextFormField.setRichTextValueId(id);
		return id;
	}
	
	public long insertRichTextFormSectionField(final RichTextFormSectionField richTextFormSectionField) {
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
		  
		   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_RICHTEXT_FORM_SECTION_FIELDS, 
					Statement.RETURN_GENERATED_KEYS);
				    ps.setLong(1, richTextFormSectionField.getFormSpecId());
					ps.setLong(2, richTextFormSectionField.getSectionSpecId());
					ps.setLong(3, richTextFormSectionField.getSectionFieldSpecId());
					ps.setLong(4, richTextFormSectionField.getFormId());
					ps.setString(5, richTextFormSectionField.getValue());
					ps.setInt(6, richTextFormSectionField.getInstanceId());
					return ps;
			}
		}, keyHolder);

		long richTextSectionFieldId = keyHolder.getKey().longValue();
		richTextFormSectionField.setRichTextSectionFieldId(richTextSectionFieldId);
		return richTextSectionFieldId;
	}
	
	public int deleteRichTextFormFields(long formId, String ignorefieldSpecIds, boolean isMasterForm) {
		String sql = null;
		if (!Api.isEmptyString(ignorefieldSpecIds)) {
			sql = Sqls.DELETE_RICH_TEXT_FIELDS_IN_SYNC.replace(":fieldSpecIds",ignorefieldSpecIds); 
		} else {
			sql = Sqls.DELETE_RICH_TEXT_FORM_FIELD;
		}
		return jdbcTemplate.update(sql, new Object[] { formId });
	}
	public int deleteRichTextFormSectionFields(long formId,
			String ignoreSectionFieldSpecIds) {
		String sql = null;
		if (!Api.isEmptyString(ignoreSectionFieldSpecIds)) {
			sql = Sqls.DELETE_RICH_TEXT_FORM_SECTION_FIELD_IN_SYNC.replace(
					":sectionFieldSpecIds", ignoreSectionFieldSpecIds);
		} else {
			sql = Sqls.DELETE_RICH_TEXT_FORM_SECTION_FIELD;
		}
		return jdbcTemplate.update(sql, new Object[] { formId });
	}
	
	
	 public void insertIntoCustomEntityCreationOnStockUpdation(long formId,
				CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_INTO_CUSTOM_ENTITY_CREATION_ON_STOCK_UPDATION,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1,customEntitySpecStockConfiguration.getCustomEntitySpecStockConfigurationId() );
					ps.setLong(2,Long.parseLong(customEntitySpecStockConfiguration.getCustomEntitySpecId()));
					ps.setLong(3,formId);
					ps.setInt(4,0);
					ps.setString(5, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(6, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));

					return ps;
				}
			}, keyHolder);
			
		}
	
	public CustomEntitySpecStockConfiguration getCustomEntitySpecStockConfigurationByFormSpecUniqueId(String uniqueId, int approval) {
		try
		{
			return jdbcTemplate.queryForObject(Sqls.SELECT_CUSTOM_ENTITY_SPEC_STOCK_CONFIGURATION_BY_FORMSPEC_UNIQUEID,
				new Object[] {uniqueId,approval}, new BeanPropertyRowMapper<CustomEntitySpecStockConfiguration>(
						CustomEntitySpecStockConfiguration.class));
		}
		catch(Exception e)
		{
			Log.info(getClass(), "getCustomEntitySpecStockConfigurationByFormSpecUniqueId() // Exception : "+e.getMessage(), e);
			return null;
		}
	}
	public void insertIntoFormExtra(Form form) 
	{
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_OR_UPDATE_FORM_EXTRA,
							Statement.RETURN_GENERATED_KEYS);
					
					ps.setLong(1, form.getFormId());
					if(form.getListUpdatedFromMobile() !=null && form.getListUpdatedFromMobile() == true) {
						ps.setBoolean(2, true);
					}else {
						ps.setBoolean(2, false);
					}
					return ps;
				}
			}, keyHolder);
			
		}catch(Exception e) {
			Log.info(getClass(), "Got Exception in insertIntoFormExtra "+e);
			e.printStackTrace();
		}

	}
	
	public List<FormFieldSpecsExtra> getFormFieldSpecsExtraForIn(
			List<FormSpec> formSpecs)
	{
		String ids = "";
		if(formSpecs!= null && formSpecs.size()>0){
			ids = Api.toCSV(formSpecs, "formSpecId", CsvOptions.FILTER_NULL_OR_EMPTY);
		}
		return getFormFieldSpecsExtraForIn(ids);
	}
	
	public List<FormFieldSpecsExtra> getFormFieldSpecsExtraForIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormFieldSpecsExtra> formFieldSpecsExtra = jdbcTemplate.query(
					Sqls.SELECT_FORM_FIELD_SPECS_EXTRA_IN.replace(":ids", ids),
					new Object[] {}, new BeanPropertyRowMapper<FormFieldSpecsExtra>(
							FormFieldSpecsExtra.class));
			return formFieldSpecsExtra;
		}
		return new ArrayList<FormFieldSpecsExtra>();
	}
	
	public void insertOrUpdateFlatTableDataStatus(long id,long flatDataTableId, int companyId) {
	    jdbcTemplate.update(
	            Sqls.INSERT_OR_UPDATE_FLAT_DATA_TABLES_STATUS,
	            new Object[] {id,companyId,FlatDataTableConfiguration.FORM_SPEC_TYPE,flatDataTableId,FlatTableDataStatus.STATUS_UN_PROCESSED, FlatTableDataStatus.TYPE_CREATE, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), Api.getDateTimeInUTC(new Date(System.currentTimeMillis()))});
	}
	
	 public void updateMasterFormFieldValueAndDisplayValue(long fieldSpecId, String fieldValue,
				long formId, String displayValue) 
		{
			String sql = Sqls.UPDATE_MASTER_FORM_FIELD_VALUE_AND_DISPLAY_VALUE;
			jdbcTemplate.update(sql, new Object[] { fieldValue, displayValue, fieldSpecId, formId });
			
		}
	 public void updateMasterFormFieldFormSpecIdAndFieldSpecId(long fieldSpecId, long formSpecId,
				long formId,String uniqueId) 
		{
			String sql = Sqls.UPDATE_MASTER_FORM_FIELD_FORM_SPEC_ID_AND_FIELD_SPEC_ID;
			jdbcTemplate.update(sql, new Object[] {formSpecId,fieldSpecId,formId,uniqueId});
			
		}

	 public List<FormSectionFieldSpecsExtra> getFormSectionFieldSpecsForIn(
				List<FormSpec> formSpecs)
		{
			String ids = "";
			if(formSpecs!= null && formSpecs.size()>0){
				ids = Api.toCSV(formSpecs, "formSpecId", CsvOptions.FILTER_NULL_OR_EMPTY);
			}
			return getFormSectionFieldSpecsForIn(ids);
		}
	 public List<FormSectionFieldSpecsExtra> getFormSectionFieldSpecsForIn(
				String ids)
		{
			if (!Api.isEmptyString(ids)) {
				List<FormSectionFieldSpecsExtra> formSectionFieldSpecsExtra = jdbcTemplate.query(
						Sqls.SELECT_FORM_SECTION_FIELD_SPECS_EXTRA_IN.replace(":ids", ids),
						new Object[] {}, new BeanPropertyRowMapper<FormSectionFieldSpecsExtra>(
								FormSectionFieldSpecsExtra.class));
				return formSectionFieldSpecsExtra;
			}
			return new ArrayList<FormSectionFieldSpecsExtra>();
		}
	 public List<RichTextFormField> getRichTextFormFieldForIn(String ids) {
			if (!Api.isEmptyString(ids)) {
				List<RichTextFormField> richTextFormField = jdbcTemplate.query(
						Sqls.SELECT_RICH_TEXT_FORM_FIELD_IN.replace(":ids", ids),
						new Object[] {}, new BeanPropertyRowMapper<RichTextFormField>(
								RichTextFormField.class));
				return richTextFormField;
			}
			return new ArrayList<RichTextFormField>();
		}

	 public List<CustomEntityFilteringCritiria> getListOfCustomEntityFilteringCritiriaForFormSpecs(String formSpecIds){
			List<CustomEntityFilteringCritiria> customEntityFilteringCritiria = new ArrayList<CustomEntityFilteringCritiria>();
			if(!Api.isEmptyString(formSpecIds)) {
				String sql = Sqls.SELECT_CUSTOM_ENTITY_FILTERING_CRITIRIA_FOR_FORMSPECS.replace(":formSpecIds", formSpecIds);
				customEntityFilteringCritiria = jdbcTemplate.query(sql,
						new BeanPropertyRowMapper<CustomEntityFilteringCritiria>(CustomEntityFilteringCritiria.class));
			}
			return customEntityFilteringCritiria;
		}
	 
	 public List<RichTextFormSectionField> getRichTextFormSectionFieldForIn(String ids)
		{
			if (!Api.isEmptyString(ids)) {
				List<RichTextFormSectionField> richTextFormSectionField = jdbcTemplate.query(
						Sqls.SELECT_RICH_TEXT_FORM_SECTION_FIELD_IN.replace(":ids", ids),
						new Object[] {}, new BeanPropertyRowMapper<RichTextFormSectionField>(
								RichTextFormSectionField.class));
				return richTextFormSectionField;
			}
			return new ArrayList<RichTextFormSectionField>();
		}
	 public List<RichTextFormField> getRichTextFormFieldForFieldSpecsIn(
				List<FormField> formfields)
		{
			String ids = "";
			if(formfields!= null && formfields.size()>0){
				ids = Api.toCSV(formfields, "fieldSpecId", CsvOptions.FILTER_NULL_OR_EMPTY);
			}
			return getRichTextFormFieldForFieldSpecsIn(ids);
		}
		public List<RichTextFormField> getRichTextFormFieldForFieldSpecsIn(String ids) {
			if (!Api.isEmptyString(ids)) {
				List<RichTextFormField> richTextFormField = jdbcTemplate.query(
						Sqls.SELECT_RICH_TEXT_FORM_FIELD_SPEC_ID_IN.replace(":ids", ids),
						new Object[] {}, new BeanPropertyRowMapper<RichTextFormField>(
								RichTextFormField.class));
				return richTextFormField;
			}
			return new ArrayList<RichTextFormField>();
		}
		public List<RichTextFormSectionField> getRichTextFormSectionFieldForFieldSpecsIn(List<FormSectionField> formSectionFields)
		{
			String ids = "";
			if(formSectionFields!= null && formSectionFields.size()>0){
				ids = Api.toCSV(formSectionFields, "sectionFieldSpecId", CsvOptions.FILTER_NULL_OR_EMPTY);
			}
			return getRichTextFormSectionFieldForFieldSpecsIn(ids);
		}
		public List<RichTextFormSectionField> getRichTextFormSectionFieldForFieldSpecsIn(String ids)
		{
			if (!Api.isEmptyString(ids)) {
				List<RichTextFormSectionField> richTextFormSectionField = jdbcTemplate.query(
						Sqls.SELECT_RICH_TEXT_FORM_SECTION_FIELD_SPEC_ID_IN.replace(":ids", ids),
						new Object[] {}, new BeanPropertyRowMapper<RichTextFormSectionField>(
								RichTextFormSectionField.class));
				return richTextFormSectionField;
			}
			return new ArrayList<RichTextFormSectionField>();
		}
		public List<RichTextFormField> getRichTextFormFieldForIn(
				List<Form> forms)
		{
			String ids = "";
			if(forms!= null && forms.size()>0){
				ids = Api.toCSV(forms, "formId", CsvOptions.FILTER_NULL_OR_EMPTY);
			}
			return getRichTextFormFieldForIn(ids);
		}
		public List<RichTextFormSectionField> getRichTextFormSectionFieldForIn(List<Form> forms)
		{
			String ids = "";
			if(forms!= null && forms.size()>0){
				ids = Api.toCSV(forms, "formId", CsvOptions.FILTER_NULL_OR_EMPTY);
			}
			return getRichTextFormSectionFieldForIn(ids);
		}
		public FormSpec getLatestEmployeeFormspec(Integer companyId,
				int purpouseEmployeeForm)
		{
			FormSpec employeeFormSpec = null;
			try
			{
				String sql = Sqls.SELECT_LATEST_EMPLOYEE_FORM_SPEC;
				employeeFormSpec = jdbcTemplate.queryForObject(
						sql, new Object[] {companyId,purpouseEmployeeForm},new BeanPropertyRowMapper<FormSpec>(FormSpec.class));
			}
			catch(Exception e)
			{
				Log.info(this.getClass(), "got exception While getLatestEmployeeFormspec : ");
				StackTraceElement[] stackTrace = e.getStackTrace();
				getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in Employee Bulk Uploads",e.toString(),stackTrace,companyId);
			}
			return employeeFormSpec;
		}
		
		
		public WorkOnDemandMapping getWorkOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
			WorkOnDemandMapping workOnDemandMapping = null;
			try {
				workOnDemandMapping = jdbcTemplate.queryForObject(Sqls.SELECT_Work_ENTITY_ON_DEMAPND_MAPPING_BY_UNIQUE_ID,
															new Object[] {uniqueId,companyId},
															new BeanPropertyRowMapper<WorkOnDemandMapping>(WorkOnDemandMapping.class));
			}catch(Exception e) {
				Log.info(this.getClass(), "Got Exception while getWorkOnDemandMappingForGivenFormSpec() : "+e);
			}
			return workOnDemandMapping;
		}
		
		public List<AutoGenereteSequenceSpecConfiguarationField> getAutoGenereteSequenceSpecConfiguarationByFormSpecId(
				String uniqueId) {
			
			List<AutoGenereteSequenceSpecConfiguarationField> autoGenereteSequenceSpecConfiguarationFields =
					new ArrayList<AutoGenereteSequenceSpecConfiguarationField>();
			if(!Api.isEmptyString(uniqueId)){
				String uniqueIdsCsv = Api.processStringValuesList1(Api.csvToList(uniqueId));
				String sql = Sqls.SELECT_AUTO_GENERATED_SEQUENCE_SPEC_CONFIGURATION_FIELDS_BY_FORMSPEC_UNIQUE_IDS.replace(":formSpecUniqueId", uniqueIdsCsv);
				autoGenereteSequenceSpecConfiguarationFields = jdbcTemplate.query(sql, new Object[] {},
								new BeanPropertyRowMapper<AutoGenereteSequenceSpecConfiguarationField>(AutoGenereteSequenceSpecConfiguarationField.class));
			}
			return autoGenereteSequenceSpecConfiguarationFields;
			
		}
}

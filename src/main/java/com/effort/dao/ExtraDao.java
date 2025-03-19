package com.effort.dao;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.core.appender.routing.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import com.effort.beans.http.request.location.CellInfo;
import com.effort.beans.http.request.location.Location;
import com.effort.context.AppContext;
import com.effort.dao.rollbackhandler.ClientIdCallBackHandler;
import com.effort.dao.rollbackhandler.FormFieldSpecCallBackHandler;
import com.effort.dao.rollbackhandler.PermissionSetsEmployeeGroupCallBackHandler;
import com.effort.entity.ActivityStream;
import com.effort.entity.AddressBean;
import com.effort.entity.Company;
import com.effort.entity.Customer;
import com.effort.entity.CustomerField;
import com.effort.entity.CustomerFilteringCritiria;
import com.effort.entity.CustomerTag;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeEntityMap;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.FieldRestrictCritria;
import com.effort.entity.FieldSpecRestrictionGroup;
import com.effort.entity.FizikemDashboardBean;
import com.effort.entity.Form;
import com.effort.entity.FormCustomEntities;
import com.effort.entity.FormCustomers;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormPageSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionFieldSpecValidValue;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JobCreateOrModifiNotify;
import com.effort.entity.JobFormMapBean;
import com.effort.entity.ListFilteringCritiria;
import com.effort.entity.Payment;
import com.effort.entity.PermissionSet;
import com.effort.entity.Plugin;
import com.effort.entity.ReportField;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.effort.entity.StartWorkStopWorkLocations;
import com.effort.entity.Subscripton;
import com.effort.entity.Tag;
import com.effort.entity.VisibilityDependencyCriteria;
import com.effort.entity.Visit;
import com.effort.entity.VisitState;
import com.effort.entity.Work;
import com.effort.entity.WorkFormMap;
import com.effort.entity.WorkSpec;
import com.effort.mail.Mail;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;

@Repository
@ComponentScan("com.effort.dao")

public class ExtraDao {

	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
	@Autowired
	private Constants constants;
	
	public static final int ELEMENT_VISIT = 1;
	public static final int ELEMENT_FORM = 2;
	public static final int ELEMENT_ROUTE = 3;
	public static final int ELEMENT_WORK = 4;

	
	
	public Long getFormSectionSpecIdFromSkeleton(
			long skeletonFormSectionSpecId, long formSpecId) {
		try {

			return jdbcTemplate
					.queryForObject(Sqls.GET_FORMSECTION_SPEC_ID_FROM_SKELETON,
							new Object[] { skeletonFormSectionSpecId,
									formSpecId }, Long.class);

		} catch (Exception ex) {
//			ex.printStackTrace();
		}

		return null;
	}

	public int deleteAssignAway(int type, long elementId, long empId) {
		return jdbcTemplate.update(Sqls.DELETE_ASSIGN_AWAY, new Object[] {
				type, elementId, empId });
	}
	
	public List<FormSectionField> getFormSectionFieldsForForm(long formId) {
		List<FormSectionField> formSectionFields = jdbcTemplate.query(
				Sqls.SELECT_FORM_SECTION_FIELD_FOR_FORM,
				new Object[] { formId },
				new BeanPropertyRowMapper<FormSectionField>(
						FormSectionField.class));
		return formSectionFields;
	}
	public List<FormFieldSpec> getFormFieldSpecForIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormFieldSpec> formFieldSpecs = jdbcTemplate.query(
					Sqls.SELECT_FORM_FIELD_SPECS_IN.replace(":ids", ids),
					new Object[] {}, new BeanPropertyRowMapper<FormFieldSpec>(
							FormFieldSpec.class));
			return formFieldSpecs;
		}

		return new ArrayList<FormFieldSpec>();
	}
	
	public Work getWork(String workId) {
		if (Api.isEmptyString(workId)) {
			workId = "0";
		}
		return jdbcTemplate.queryForObject(Sqls.SELECT_WORK,
				new Object[] { workId }, new BeanPropertyRowMapper<Work>(
						Work.class));
		
	}
	
	public int[] insertVisitEntitySkill(final List<String> enitities,
			final long visitId) {
		return jdbcTemplate.batchUpdate(Sqls.INSERT_EMP_VISIT_SKILL_MAPPING,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						String entityId = enitities.get(i);
						ps.setLong(1,visitId);
						ps.setString(2,entityId);
					}

					@Override
					public int getBatchSize() {
						return enitities.size();
					}
				});
	}
	
	public int insertVisitEvent(long visitId, int type) {
		return jdbcTemplate.update(
				Sqls.INSERT_VISIT_EVENT,
				new Object[] {
						visitId,
						type,
						Api.getDateTimeInUTC(new Date(System
								.currentTimeMillis())) });
	}

	public long addWork(final Work work, final Long empId,
			final Integer companyId, final String code, String clientVersion) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_WORK, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, work.getWorkSpecId());
				if(work.isFromMigrationFields()) {
				ps.setLong(2, work.getMigrationFilledBy());
				}else {
				ps.setLong(2, empId);
				}
				if(work.isFromMigrationFields()) {
				ps.setLong(3, work.getMigrationModifiedBy());					
				}else {
				ps.setLong(3, empId);
				}
				ps.setLong(4, work.getFormId());
				ps.setInt(5, companyId);
				if(work.isFromMigrationFields()) {
				ps.setString(6, work.getMigrationCreatedTime());		
				}else {
				ps.setString(6, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				}
				if(work.isFromMigrationFields()) {
				ps.setString(7, work.getMigrationModifiedTime());		
				}else {
				ps.setString(7, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				}
				if (work.getParentWorkId() == null) {
					ps.setNull(8, Types.BIGINT);
				} else {
					ps.setLong(8, work.getParentWorkId());
				}
				if (work.getParentWorkActionSpecId() == null) {
					ps.setNull(9, Types.BIGINT);
				} else {
					ps.setLong(9, work.getParentWorkActionSpecId());
				}

				ps.setString(10, work.getClientSideId());
				ps.setString(11, code);
				if (work.getAssignTo() == null) {
					ps.setNull(12, Types.NULL);
				} else {
					ps.setLong(12, work.getAssignTo());
				}

				ps.setBoolean(13, work.isTemplate());
				if (work.getRecurringParentId() == null) {
					ps.setNull(14, Types.NULL);
				} else {
					ps.setLong(14, work.getRecurringParentId());
				}

				ps.setBoolean(15, work.isRejected());
				ps.setString(
						16,
						work.getRejectedOn() == null ? null : Api
								.getDateTimeInUTC(DatatypeConverter
										.parseDateTime(work.getRejectedOn())));
				ps.setString(17, work.getExternalId());
				ps.setString(18, work.getApiUserId());
				ps.setBoolean(19, work.isInvitation());
				ps.setInt(20, work.getInvitationState());
				if (work.getLastActionSpecId() == null) {
					ps.setNull(21, Types.BIGINT);
				} else {
					ps.setLong(21, work.getLastActionSpecId());
				}
				if (work.getNextActionSpecId() == null) {
					ps.setNull(22, Types.VARCHAR);
				} else {
					ps.setString(22, work.getNextActionSpecId());
				}
				if(work.getRejectedBy() == null)
				{
					ps.setNull(23, Types.BIGINT);
				}
				else
				{
					ps.setLong(23, work.getRejectedBy());
				}
				if(work.getRejectionFormId() == null)
				{
					ps.setNull(24, Types.BIGINT);
				}
				else
				{
					ps.setLong(24, work.getRejectionFormId());
				}
				ps.setBoolean(25, work.isWorkSharing());
				ps.setBoolean(26, work.isSmartScheduling());
				ps.setBoolean(27, work.isCanRejectWorkInvitation());
				ps.setBoolean(28, work.isSubTaskWork());
				if(work.getSubTaskParentWorkId() == null){
					ps.setNull(29, Types.BIGINT);
				}
				else{
					ps.setLong(29, work.getSubTaskParentWorkId());
				}
				if(work.getWorkProcessSubTaskSpecId() == null){
					ps.setNull(30, Types.BIGINT);
				}
				else{
					ps.setLong(30, work.getWorkProcessSubTaskSpecId());
				}
				if (Api.isEmptyString(work.getSubTaskParentClientSideWorkId())) {
					ps.setNull(31, Types.VARCHAR);
				} else {
					ps.setString(31, work.getSubTaskParentClientSideWorkId());
				}
				if (work.getWorkFieldsUniqueKey() == null) {
					ps.setNull(32, Types.VARCHAR);
				} else {
					ps.setString(32, work.getWorkFieldsUniqueKey());
				}
				if(Api.isEmptyString(work.getStartTime()))
				{
					ps.setNull(33, Types.VARCHAR);
				}else{
					ps.setString(33, work.getStartTime());
				}
				if(Api.isEmptyString(work.getEndTime()))
				{
					ps.setNull(34, Types.VARCHAR);
				}else{
					ps.setString(34, work.getEndTime());
				}
				if(Api.isEmptyString(work.getPhoneNumber()))
				{
					ps.setNull(35, Types.VARCHAR);
				}else{
					ps.setString(35, work.getPhoneNumber());
				}
				
				if(Api.isEmptyString(work.getWorkName()))
				{
					ps.setNull(36, Types.VARCHAR);
				}else{
					ps.setString(36, work.getWorkName());
				}
				if(Api.isEmptyString(work.getDescription()))
				{
					ps.setNull(37, Types.VARCHAR);
				}else{
					ps.setString(37, work.getDescription());
				}
				if(Api.isEmptyString(work.getPriority()))
				{
					ps.setNull(38, Types.INTEGER);
				}else{
					ps.setLong(38, Long.parseLong(work.getPriority()));
				}
				if(Api.isEmptyString(work.getCoutry()))
				{
					ps.setNull(39, Types.VARCHAR);
				}else{
					ps.setString(39, work.getCoutry());
				}
				if(Api.isEmptyString(work.getPincode()))
				{
					ps.setNull(40, Types.VARCHAR);
				}else{
					ps.setString(40, work.getPincode());
				}
				if(Api.isEmptyString(work.getState()))
				{
					ps.setNull(41, Types.VARCHAR);
				}else{
					ps.setString(41, work.getState());
				}
				if(Api.isEmptyString(work.getLandMark()))
				{
					ps.setNull(42, Types.VARCHAR);
				}else{
					ps.setString(42, work.getLandMark());
				}
				if(Api.isEmptyString(work.getCity()))
				{
					ps.setNull(43, Types.VARCHAR);
				}else{
					ps.setString(43, work.getCity());
				}
				if(Api.isEmptyString(work.getArea()))
				{
					ps.setNull(44, Types.VARCHAR);
				}else{
					ps.setString(44, work.getArea());
				}
				if(Api.isEmptyString(work.getStreet()))
				{
					ps.setNull(45, Types.VARCHAR);
				}else{
					ps.setString(45, work.getStreet());
				}
				if(work.getCustomerId() == null)
				{
					ps.setNull(46, Types.BIGINT);
				}else{
					ps.setLong(46, work.getCustomerId());
				}
				if(Api.isEmptyString(clientVersion))
				{
					ps.setNull(47, Types.VARCHAR);
				}else{
					ps.setString(47, clientVersion);
				}
				if(work.getWorkCreatedLocationId() ==  null) {
					ps.setNull(48, Types.BIGINT);
				}else {
					ps.setLong(48, work.getWorkCreatedLocationId());
				}
				if (work.getSourceOfAction() == null) {
					ps.setNull(49, Types.INTEGER);
				} else {
					ps.setInt(49, work.getSourceOfAction());
				}
				
				if(work.getCustomerCheckInId() == null) {
					ps.setNull(50, Types.BIGINT);
				} else {
					ps.setLong(50, work.getCustomerCheckInId());
				}
				
				if(work.getCustomEntityCheckInId() == null) {
					ps.setLong(51, Types.BIGINT);
				}else {
					ps.setLong(51, work.getCustomEntityCheckInId());
				}
				
				ps.setInt(52, work.getProcessSkeleton());
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		work.setWorkId(id);
		return id;

	}
	
	public long insertVisit(final Visit visit, final long by, final String code) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		/*
		 * final String rejectedOn = Api .getDateTimeInUTC(DatatypeConverter
		 * .parseDateTime(visit.getRejectedOn())) ;
		 */
		
		final String currentTime = Api.getDateTimeInUTC(new Date(System
				.currentTimeMillis()));
		
		visit.setCreateTime(currentTime);
		visit.setModifiedTime(currentTime);
		
		Log.info(getClass(), "rejectedOn " + visit.getRejectedOn()
				+ " rejected " + visit.isRejected());
		visit.setAddressLat(Api.makeNullIfEmpty(visit.getAddressLat()));
		visit.setAddressLng(Api.makeNullIfEmpty(visit.getAddressLng()));

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_VISIT, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, visit.getVisitTypeId());
				ps.setLong(2, visit.getVisitStateId());
				ps.setLong(3, visit.getEmpId());
				if (visit.getCustomerId() == -1 || visit.getCustomerId() == 0) {
					ps.setNull(4, Types.BIGINT);
				} else {
					ps.setLong(4, visit.getCustomerId());
				}
				ps.setString(5, visit.getEmpVisitTitle());
				ps.setString(6, visit.getEmpVisitDesc());
				ps.setString(
						7,
						visit.getVisitStartTime() == null ? null : Api
								.getDateTimeInUTC(DatatypeConverter
										.parseDateTime(visit
												.getVisitStartTime())));
				ps.setString(
						8,
						visit.getVisitEndTime() == null ? null
								: Api.getDateTimeInUTC(DatatypeConverter
										.parseDateTime(visit.getVisitEndTime())));
				ps.setBoolean(
						9,
						visit.isCompleted() == null ? false : visit
								.isCompleted());
				ps.setString(
						10,
						visit.getCompleteTime() == null ? null
								: Api.getDateTimeInUTC(DatatypeConverter
										.parseDateTime(visit.getCompleteTime())));
				ps.setBoolean(11, visit.isAddressSameAsCustomer());
				ps.setString(12, visit.getAddressStreet());
				ps.setString(13, visit.getAddressArea());
				ps.setString(14, visit.getAddressCity());
				ps.setString(15, visit.getAddressPincode());
				ps.setString(16, visit.getAddressLandMark());
				ps.setString(17, visit.getAddressState());
				ps.setString(18, visit.getAddressCountry());
				ps.setString(19, visit.getAddressLat());
				ps.setString(20, visit.getAddressLng());
				ps.setString(21, visit.getClientVisitId());
				ps.setString(22, code);
				ps.setString(23, visit.getExternalId());
				ps.setString(24, visit.getApiUserId());
				ps.setString(25, currentTime);
				ps.setString(26, currentTime);
				ps.setBoolean(27, visit.isApproved());
				ps.setLong(28, by);
				if (visit.getParentVisitId() == null) {
					ps.setNull(29, Types.BIGINT);
				} else {
					ps.setLong(29, visit.getParentVisitId());
				}
				ps.setString(30, visit.getPhoneNo());
				ps.setInt(31, visit.getPriority());

				if (visit.getEmpVisitFormId() == null) {
					ps.setNull(32, Types.BIGINT);
				} else {
					ps.setLong(32, visit.getEmpVisitFormId());
				}
				if (visit.getRecurringParentId() == null) {
					ps.setNull(33, Types.BIGINT);
				} else {
					ps.setLong(33, visit.getRecurringParentId());
				}
				ps.setBoolean(34, visit.isTemplate());
				ps.setBoolean(35, visit.isRejected());
				ps.setString(
						36,
						visit.getRejectedOn() == null ? null : Api
								.getDateTimeInUTC(DatatypeConverter
										.parseDateTime(visit.getRejectedOn())));
				ps.setLong(37, visit.getCompanyIdForVisit());
				if (visit.getCompletedCheckInId() == null) {
					ps.setNull(38, Types.BIGINT);
				} else {
					ps.setLong(38, visit.getCompletedCheckInId());
				}
				ps.setBoolean(39, visit.isForcePerformJob());
				return ps;
			}
		}, keyHolder);

		long visitId = keyHolder.getKey().longValue();
		visit.setVisitId(visitId);

		return visitId;
	}
	
	
	public String getFormFieldSpecId(String formSpecId,
			String skeletonFieldSpecId) {
		return jdbcTemplate.queryForObject(
				Sqls.SELECT_FELD_SPEC_ID_BY_SKELETONE_SPEC_ID, new Object[] {
						skeletonFieldSpecId, formSpecId }, String.class);
	}
	
	public void insertJobFormMappingForForm(final Long parentJobId,
			final long jobId, final long formId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_INTO_JOBFORMMAPPING_FOR_JOB,
						Statement.RETURN_GENERATED_KEYS);

				ps.setLong(1, jobId);
				ps.setLong(2, formId);
				if (parentJobId == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setLong(3, parentJobId);
				}
				return ps;
			}
		}, keyHolder);

	}

	

	public AddressBean getCustomerAddress(String customerId, long companyId) {
		AddressBean addressBean = jdbcTemplate.queryForObject(
				Sqls.SELECT_CUSTOMER_ADDRESS, new Object[] { customerId,
						companyId }, new BeanPropertyRowMapper<AddressBean>(
						AddressBean.class));
		return addressBean;
	}

	public List<VisitState> getVisitStatesForType(long typeId) {
		List<VisitState> visitStates = jdbcTemplate.query(
				Sqls.SELECT_VISIT_STATES_FOT_TYPE, new Object[] { typeId },
				new BeanPropertyRowMapper<VisitState>(VisitState.class));
		return visitStates;
	}
	public List<Entity> getEntitiesForEntityIn(String entityIds) {
		String sql = Sqls.SELECT_ENTITY_ENTITY_IN;
		entityIds = Api.trimComma(entityIds);

		if (!Api.isEmptyString(entityIds)) {
			sql = sql.replace(":ids", entityIds);
		} else {
			sql = sql.replace(":ids", "0");
		}

		List<Entity> entities = jdbcTemplate.query(sql, new Object[] {},
				new BeanPropertyRowMapper<Entity>(Entity.class));
		return entities;
	}
	
	public List<FormFieldSpecValidValue> getFormFieldSpecValidValueList(
			String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormFieldSpecValidValue> formFieldSpecValidValues = jdbcTemplate
					.query(Sqls.SELECT_FORM_FIELD_VALID_VALUES.replace(":ids",
							ids),

					new BeanPropertyRowMapper<FormFieldSpecValidValue>(
							FormFieldSpecValidValue.class));
			return formFieldSpecValidValues;
		}
		return new ArrayList<FormFieldSpecValidValue>();
	}
	
	public JobFormMapBean getJobFormDataMappingBasedOnFromSpec(int companyId,
			String formSpecUniqueId) {
		String sql = Sqls.FETCH_JOB_FORM_MAPPING_UNIQUE_SPEC;
		JobFormMapBean jobFormMapBean = jdbcTemplate
				.queryForObject(sql,
						new Object[] { companyId, formSpecUniqueId },
						new BeanPropertyRowMapper<JobFormMapBean>(
								JobFormMapBean.class));
		return jobFormMapBean;
	}

	public Work getWorkByFormId(long formId) {
		return jdbcTemplate.queryForObject(Sqls.SELECT_WORK_BY_FORM_ID,
				new Object[] { formId }, new BeanPropertyRowMapper<Work>(
						Work.class));
	}

	
	public WorkSpec getWorkSpecsForFromSpecUniqueIdAndCompany(
			String uniqueId, Integer companyId) {
			WorkSpec workSpec = null;
			try{
				workSpec = jdbcTemplate.queryForObject(Sqls.GET_WORKSPEC_BY_FORM_UNIQUEID,
						new Object[]{uniqueId, companyId}, new BeanPropertyRowMapper<WorkSpec>(WorkSpec.class));
			}catch(Exception e){
				return new WorkSpec();
			}
		return workSpec;

	}
	
	public int insertAssignAway(int type, long elementId, long empId) {
		return jdbcTemplate.update(
				Sqls.INSERT_ASSIGN_AWAY,
				new Object[] {
						type,
						elementId,
						empId,
						Api.getDateTimeInUTC(new Date(System
								.currentTimeMillis())) });
	}
	
	public List<Entity> getEntitiesIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			String sql = Sqls.SELECT_ENTITY_IN.replace(":ids", ids);
			List<Entity> entities = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Entity>(Entity.class));
			return entities;
		} else {
			return new ArrayList<Entity>();
		}
	}

	public List<FormFieldSpec> getFormFieldSpecForIn(List<FormSpec> formSpecs) {
		String ids = "";
		for (FormSpec formSpec : formSpecs) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}

			ids += "'" + formSpec.getFormSpecId() + "'";
		}

		return getFormFieldSpecForIn(ids);
	}
	
	public List<FormPageSpec> getFormPageSpecsForFormSpec(String formSpecIds) {
		if (!Api.isEmptyString(formSpecIds)) {
			String sql = Sqls.SELECT_FORM_PAGE_SPECS_FOR_FORM_SPEC_IN.replace(
					":ids", formSpecIds);
			List<FormPageSpec> formPageSpecs = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<FormPageSpec>(
							FormPageSpec.class));
			return formPageSpecs;
		} else {
			return new ArrayList<FormPageSpec>();
		}
	}
	public List<FormPageSpec> getFormPageSpecsForFormSpec(
			List<FormSpec> formSpecs) {
		String ids = "";
		if (formSpecs != null) {
			for (FormSpec formSpec : formSpecs) {
				if (!Api.isEmptyString(ids)) {
					ids += ",";
				}

				ids += formSpec.getFormSpecId() + "";
			}
		}

		return getFormPageSpecsForFormSpec(ids);
	}
	
	private ExtraSupportDao getExtraSupportDao(){
		
		ExtraSupportDao extraSupportDao = AppContext.getApplicationContext().getBean("extraSupportDao",ExtraSupportDao.class);
		
		return extraSupportDao;
	}
private ExtendedDao getExtendedDao(){
		
		ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
		
		return extendedDao;
	}
	
	private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
		
		ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
		
		return extraSupportAdditionalDao;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		
		return extraSupportAdditionalSupportDao;
	}
	
	
	public void insertFormSectionFields(final String sql, final List<FormSectionField> sectionFields)
	{
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < sectionFields.size(); i++)
				{
					FormSectionField formSectionField = sectionFields
							.get(i);
					ps.setLong(placeHolder++, formSectionField.getFormId());
					ps.setLong(placeHolder++, formSectionField.getFormSpecId());
					ps.setLong(placeHolder++, formSectionField.getSectionSpecId());
					ps.setLong(placeHolder++, formSectionField.getSectionFieldSpecId());
					ps.setInt(placeHolder++, formSectionField.getInstanceId());
					ps.setString(placeHolder++, formSectionField.getFieldValue());
					ps.setString(placeHolder++, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(placeHolder++, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(placeHolder++, formSectionField.getFieldLabel());
					ps.setInt(placeHolder++, formSectionField.getFieldType());
					ps.setInt(placeHolder++, formSectionField.getDisplayOrder());
					ps.setInt(placeHolder++, formSectionField.getIdentifier());
					ps.setString(placeHolder++, formSectionField.getDisplayValue());
					ps.setString(placeHolder++, formSectionField.getUniqueId());
					if(formSectionField.getInitialFormSectionFieldSpecId() == null) {
						ps.setNull(placeHolder++, Types.BIGINT);
					}else {
						ps.setLong(placeHolder++, formSectionField
								.getInitialFormSectionFieldSpecId());
					}
					if(formSectionField.getSkeletonFormSectionFieldSpecId() == null) {
						ps.setNull(placeHolder++, Types.BIGINT);
					}else {
						ps.setLong(placeHolder++, formSectionField
								.getSkeletonFormSectionFieldSpecId());
					}
					
				}
				return ps;
			}
		});
	}
	public void insertFormFields(final String sql, final List<FormField> fields)
	{
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < fields.size(); i++)
				{
					FormField formField = fields.get(i);
					ps.setLong(placeHolder++, formField.getFormId());
					ps.setLong(placeHolder++, formField.getFormSpecId());
					ps.setLong(placeHolder++, formField.getFieldSpecId());
					ps.setString(placeHolder++, formField.getFieldValue());
					ps.setString(placeHolder++, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(placeHolder++, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(placeHolder++, formField.getFieldLabel());
					ps.setInt(placeHolder++, formField.getFieldType());
					ps.setInt(placeHolder++, formField.getDisplayOrder());
					ps.setInt(placeHolder++, formField.getIdentifier());
					ps.setString(placeHolder++, formField.getDisplayValue());
					ps.setString(placeHolder++, formField.getUniqueId());
					if(formField.getInitialFormFieldSpecId() == null) {
						ps.setNull(placeHolder++, Types.BIGINT);
					}else {
						ps.setLong(placeHolder++, formField.getInitialFormFieldSpecId());
					}
					if(formField.getSkeletonFormFieldSpecId() == null) {
						ps.setNull(placeHolder++, Types.BIGINT);
					}else {
						ps.setLong(placeHolder++, formField.getSkeletonFormFieldSpecId());
					}
					
					ps.setInt(placeHolder++, formField.getIsVisible());
					if(formField.getGroupExpression() == null){
						ps.setNull(placeHolder++, Types.VARCHAR);
					}else{
						ps.setString(placeHolder++, formField.getGroupExpression());
					}
				}
				return ps;
			}
		});
	}
	
	public ReportField getReportFormSectionFields(String fieldValue,
			Long fieldSpecId) {
		ReportField reportField = null;

		if (fieldValue != null && fieldValue.contains("'")) {
			fieldValue = fieldValue.replaceAll("'", "''");
		}

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
			
			String sql = Sqls.SELECT_REPORT_FORM_SECTION_FIELDS.replace(
					":fieldValue", fieldValue).replace(":idsCsv", idsCsv);
			reportField = jdbcTemplate.queryForObject(sql,
					new Object[] { fieldSpecId },
					new BeanPropertyRowMapper<ReportField>(ReportField.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportField;
	}

	public void insertFormSectionFields(List<FormSectionField> sectionFields,boolean restrictDataFromMobile,boolean isSync,boolean isMasterForm) {
		Long currentInMillies = System.currentTimeMillis();
		
		
		Set<Long> formIds = new HashSet<Long>();
		List<FormCustomers> formCustomers = new ArrayList<FormCustomers>();
		List<FormCustomEntities> formCustomEntities = new ArrayList<FormCustomEntities>();
		Set<Long> formIdsForCustomEntities = new HashSet<Long>();
		List<FormSectionFieldSpec> formSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>();
		Map<Long,FormSectionFieldSpec> sectionFieldSpecIdAndFormSectionFieldSpecMap = new HashMap<Long,FormSectionFieldSpec>();
		if(sectionFields!=null && sectionFields.size()>0)
		{
			FormSectionField formField = sectionFields.get(0);
			formSectionFieldSpecs = getExtraSupportAdditionalSupportDao().getFormSectionFieldSpecsByFormSpec(formField.getFormSpecId());
			sectionFieldSpecIdAndFormSectionFieldSpecMap = (Map) Api.getMapFromList(formSectionFieldSpecs, "sectionFieldSpecId");
		}
		
		for (FormSectionField field : sectionFields) {
			ReportField reportField = null;
			if(sectionFieldSpecIdAndFormSectionFieldSpecMap!=null && !sectionFieldSpecIdAndFormSectionFieldSpecMap.isEmpty() && 
					sectionFieldSpecIdAndFormSectionFieldSpecMap.get(field.getSectionFieldSpecId()) != null){
				reportField = getExtendedDao().getReportFormSectionFields(field.getFieldValue(), field.getSectionFieldSpecId(),
						sectionFieldSpecIdAndFormSectionFieldSpecMap.get(field.getSectionFieldSpecId()).getFieldType());
			}else {
				reportField = getReportFormSectionFields(
						field.getFieldValue(), field.getSectionFieldSpecId());
			}
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
			}else if (field.getFieldType() == 39) {
				if (!Api.isEmptyString(field.getFieldValue())) {
					field.setDisplayValue(Api.getDateSpanDisplayValue(field.getFieldValue()));
				}
			} else if (field.getFieldType() == 40) {
				if (!Api.isEmptyString(field.getFieldValue())) {
					field.setDisplayValue(Api.getDateTimeSpanDisplayValue(field.getFieldValue()));
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
				insertFormSectionFields(sql + placeHolderString, fieldsSubList);
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

	public Map<String, String> getFormIdAndAuditMap(String formIds) {
		Map<String, String> formIdsAndAuditIdsMap = new HashMap<String, String>();

		if (!Api.isEmptyString(formIds)) {
			String query = Sqls.SELECT_FORMID_AND_AUDITID.replace(":formIds",
					formIds);
			List<Map<String, Object>> formIdsAndAuditIdsList = (List<Map<String, Object>>) jdbcTemplate
					.queryForList(query);

			for (Map<String, Object> map : formIdsAndAuditIdsList) {
				formIdsAndAuditIdsMap.put(map.get("formId").toString(), map
						.get("auditId").toString());
			}
		}

		return formIdsAndAuditIdsMap;
	}
	public Map<String, FormSpec> getFormSpecIdsAndFormSpecMap(
			List<Form>... added) {
		Set<String> formSpecIds = new HashSet<String>();
		Map<String, FormSpec> formSpecIdAndformSpecMap = new HashMap<String, FormSpec>();
		for (List<Form> list : added) {
			for (Form form : list) {
				formSpecIds.add("" + form.getFormSpecId());
			}
		}
		String ids = Api.toCSV(formSpecIds);
		if (!Api.isEmptyString(ids)) {
			List<FormSpec> formSpecs = getFormSpecsIn(ids);
			for (FormSpec formSpec : formSpecs) {
				formSpecIdAndformSpecMap.put("" + formSpec.getFormSpecId(),
						formSpec);
			}
		}

		return formSpecIdAndformSpecMap;

	}

	
	public Map<String, String> getFormSpecIdsAndTitleMap(List<Form>... added) {
		List<String> formSpecIds = new ArrayList<String>();
		Map<String, String> formSpecIdAndformTitleMap = new HashMap<String, String>();
		for (List<Form> list : added) {
			for (Form form : list) {
				formSpecIds.add("" + form.getFormSpecId());
			}
		}
		String ids = Api.toCSV(formSpecIds);
		if (!Api.isEmptyString(ids)) {
			List<FormSpec> formSpecs = getFormSpecsIn(ids);
			for (FormSpec formSpec : formSpecs) {
				formSpecIdAndformTitleMap.put("" + formSpec.getFormSpecId(),
						formSpec.getFormTitle());
			}
		}

		return formSpecIdAndformTitleMap;

	}
	
	public int deleteForm(long formId, long by, long companyId, boolean isMasterForm) {
		String sql = Sqls.DELETE_FORM;
		if(isMasterForm) {
			sql = Sqls.DELETE_MASTER_FORM;
		}
		return jdbcTemplate.update(
				sql,
				new Object[] {
						by,
						Api.getDateTimeInUTC(new Date(System
								.currentTimeMillis())), Api.getDateTimeInUTC(new Date(System
										.currentTimeMillis())), formId, companyId });
	}

	public int deleteFormSectionFields(long formId,
			String ignoreSectionFieldSpecIds, boolean isMasterForm) {
		String sql = null;
		if(isMasterForm) {
			sql = Sqls.DELETE_MASTER_FORM_SECTION_FIELD;
		}else if (!Api.isEmptyString(ignoreSectionFieldSpecIds)) {
			sql = Sqls.DELETE_FORM_SECTION_FIELD_IN_SYNC.replace(
					":sectionFieldSpecIds", ignoreSectionFieldSpecIds);
		} else {
			sql = Sqls.DELETE_FORM_SECTION_FIELD;
		}

		return jdbcTemplate.update(sql, new Object[] { formId });
	}
	
	public int deleteFormFields(long formId, String ignorefieldSpecIds, boolean isMasterForm) {
		String sql = null;
		if (!Api.isEmptyString(ignorefieldSpecIds)) {
			if(isMasterForm) {
				sql = Sqls.DELETE_MASTER_FORM_FIELDS_IN_SYNC.replace(":fieldSpecIds",ignorefieldSpecIds);
			}else {
				sql = Sqls.DELETE_FORM_FIELD_IN_SYNC.replace(":fieldSpecIds",ignorefieldSpecIds); 
			}
			
		} else {
			if(isMasterForm) {
				sql = Sqls.DELETE_MASTER_FORM_FIELD;
			}else
				sql = Sqls.DELETE_FORM_FIELD;
		}
		return jdbcTemplate.update(sql, new Object[] { formId });
	}
	public static final String DELETE_MASTER_FORM_FIELDS_IN_SYNC = "DELETE FROM `MasterFormFields` WHERE `formId` = ? AND fieldSpecId NOT IN(:fieldSpecIds) ;";

	public void deleteFormCustomersForForm(Long formId) 
	{
		String sql = Sqls.DELETE_FORM_CUSTOMERS_FOR_FORM_ID;
		jdbcTemplate.update(sql, new Object[]{formId});
		
	}
	public int updateForm(Form form, long by, boolean isMasterForm, String clientVersion) {
		String modifiedTime = null;
		String serverModifiedTime = null;
		int sourceOfModification = 0;

		if (form != null && form.isFromMigrationFields()) {
			by = form.getMigrationModifiedBy();
			modifiedTime = form.getMigrationModifiedTime();
		} else {
			if (!Api.isEmptyString(form.getModifiedTime()) && form.getModifiedTime().contains("T")
					&& form.getModifiedTime().contains("Z")) {
				modifiedTime = Api.getDateTimeFromXsd(form.getModifiedTime());

			} else {
				modifiedTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));

			}
		}

		if (form.getSourceOfModification() != null) {
			sourceOfModification = form.getSourceOfModification();
		}

		if (form != null && form.isFromMigrationFields()) {
			serverModifiedTime = form.getMigrationModifiedTime();
		} else {
			serverModifiedTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
		}

		String sql = Sqls.UPDATE_FORM;
		if (isMasterForm) {
			sql = Sqls.UPDATE_MASTER_FORM;
			return jdbcTemplate.update(sql,
					new Object[] { form.getFormSpecId(), form.getFormStatus(), form.getAssignTo(), by,
							form.getLocationId(), form.getExternalId(), form.getApiUserId(), modifiedTime,
							serverModifiedTime, form.isMediasCommitted(), form.getCheckInId(),
							form.isForcePerformActivity(), form.isFormProcessed(), form.getCustomEntityCheckInId(),
							form.getDraftForm(), clientVersion, sourceOfModification, form.getFormId() });
		} else {
			return jdbcTemplate.update(sql,
					new Object[] { form.getFormSpecId(), form.getFormStatus(), form.getAssignTo(), by,
							form.getLocationId(), form.getExternalId(), form.getApiUserId(), modifiedTime,
							serverModifiedTime, form.isMediasCommitted(), form.getCheckInId(),
							form.isForcePerformActivity(), form.isFormProcessed(), form.getCustomEntityCheckInId(),
							form.getDraftForm(), clientVersion, sourceOfModification, form.getFormFieldsUniqueKey(),
							form.getFormId() });

		}
	}

	
	public List<FormField> getFormFieldByForm(long formId) {
		List<FormField> formFields = jdbcTemplate.query(
				Sqls.SELECT_FORM_FIELD_BY_FORM_AND_FIELD,
				new Object[] { formId }, new BeanPropertyRowMapper<FormField>(
						FormField.class));
		return formFields;
	}

	public Form getForm(String id) {
		Form form = jdbcTemplate.queryForObject(Sqls.SELECT_FORM,
				new Object[] { id },
				new BeanPropertyRowMapper<Form>(Form.class));
		return form;
	}
	public long insertForm(final Form form, final long companyId,
			final Long by, final String code, final boolean isMasterForm, String clientVersion) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_FORM, Statement.RETURN_GENERATED_KEYS);
				if(isMasterForm) {
					 ps = connection.prepareStatement(Sqls.INSERT_MASTER_FORM, Statement.RETURN_GENERATED_KEYS);
				}
				ps.setLong(1, companyId);
				ps.setLong(2, form.getFormSpecId());
				ps.setInt(3, form.getFormStatus());
				if (form!= null && form.isFromMigrationFields()) {
					if (form.getMigrationFilledBy() == null) {
						ps.setNull(4, Types.BIGINT);
					} else {
						ps.setLong(4, form.getMigrationFilledBy());
					}
				} else {
					if (by == null) {
						ps.setNull(4, Types.BIGINT);
					} else {
						ps.setLong(4, by);
					}
				}
				if (form != null && form.isFromMigrationFields()) {
					if (form.getMigrationModifiedBy() == null) {
						ps.setNull(5, Types.BIGINT);
					} else {
						ps.setLong(5, form.getMigrationModifiedBy());
					}
				} else {
					if (by == null) {
						ps.setNull(5, Types.BIGINT);
					} else {
						ps.setLong(5, by);
					}
				}
				if (form.getAssignTo() == null) {
					ps.setNull(6, Types.BIGINT);
				} else {
					ps.setLong(6, form.getAssignTo());
				}
				if (form.getLocation() == null) {
					ps.setNull(7, Types.BIGINT);
				} else {
					ps.setLong(7, form.getLocation().getLocationId());
				}
				ps.setString(8, form.getClientFormId());
				ps.setString(9, code);
				ps.setString(10, form.getExternalId());
				ps.setString(11, form.getApiUserId());
				if (form != null && form.isFromMigrationFields()) {
					ps.setString(12, form.getMigrationCreatedTime());
				}else {
				if (!Api.isEmptyString(form.getCreatedTime())
						&& form.getCreatedTime().contains("T")
						&& form.getCreatedTime().contains("Z")) {
					String dateTime = Api.getDateTimeFromXsd(form
							.getCreatedTime());
					ps.setString(12, dateTime);

				} else {
					ps.setString(12, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
				}
				}
				if (form != null && form.isFromMigrationFields()) {
					ps.setString(13, form.getMigrationModifiedTime());
				}else {
				if (!Api.isEmptyString(form.getModifiedTime())
						&& form.getModifiedTime().contains("T")
						&& form.getModifiedTime().contains("Z")) {
					String dateTime = Api.getDateTimeFromXsd(form
							.getModifiedTime());
					ps.setString(13, dateTime);

				} else {
					ps.setString(13, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
				}
				}
				if (form != null && form.isFromMigrationFields()) {
					ps.setString(14, form.getMigrationCreatedTime());
				}else {
				ps.setString(14, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				}
				if (form != null && form.isFromMigrationFields()) {
					ps.setString(15, form.getMigrationModifiedTime());
				}else {
				ps.setString(15, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				}
				ps.setBoolean(16, form.isCommited());
				ps.setBoolean(17, form.isMediasCommitted());
				if(form.getCheckInId() == null)
				{
					ps.setNull(18, Types.BIGINT);
				}else{
					ps.setLong(18, form.getCheckInId());
				}
				
				ps.setInt(19, form.getStockStatus() == null ? -1 : form.getStockStatus());
				
				ps.setBoolean(20, form.isForcePerformActivity());
				
				ps.setBoolean(21, form.isPublicForm());
				
				if(!Api.isEmptyString(form.getIpAddress()))
					ps.setString(22, form.getIpAddress());
				else
					ps.setNull(22, Types.VARCHAR);
				if(form.getCustomEntityCheckInId() == null)
				{
					ps.setNull(23, Types.BIGINT);
				}else{
					ps.setLong(23, form.getCustomEntityCheckInId());
				}
				ps.setInt(24, form.getDraftForm());
				if(!Api.isEmptyString(clientVersion))
				{
					ps.setString(25, clientVersion);
				}else {
					ps.setNull(25, Types.VARCHAR);
				}
				if(!isMasterForm) {
					if (form.getTurnAroundTime() == null) {
						ps.setNull(26, Types.BIGINT);
					} else {
						ps.setLong(26, form.getTurnAroundTime());
					}
				}
				if(!isMasterForm) {
					if (form.getSourceOfAction() == null) {
						ps.setInt(27, Types.BIGINT);
					}else {
						ps.setInt(27, form.getSourceOfAction());
					}
					
					if(!Api.isEmptyString(form.getFormFieldsUniqueKey()))
					{
						ps.setString(28, form.getFormFieldsUniqueKey());
					}else {
						ps.setNull(28, Types.VARCHAR);
					}
				}else {
					if (form.getSourceOfAction() == null) {
						ps.setInt(26, Types.BIGINT);
					}else {
					ps.setInt(26, form.getSourceOfAction());
					}
				}
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		form.setFormId(id);

		return id;
	}
	
	public void insertFormLocationList(final List<Location> locations,
			final long formId, final int type) {

		jdbcTemplate.batchUpdate(Sqls.INSERT_INTO_FORM_LOCATIONS,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Location location = locations.get(i);
						ps.setLong(1, formId);
						ps.setLong(2, location.getLocationId());
						ps.setString(3, Api.getDateTimeInUTC(new Date(System
								.currentTimeMillis())));
						ps.setString(4, Api.getDateTimeInUTC(new Date(System
								.currentTimeMillis())));
						ps.setInt(5, type);
						ps.setBoolean(6, location.isDrafted());

					}

					@Override
					public int getBatchSize() {
						return locations.size();
					}

				});

	}
	
	
	public Map<String, Long> getFormClientSideIds(List<Form> forms, String code) {
		ClientIdCallBackHandler clientIdCallBackHandler = new ClientIdCallBackHandler();

		code = "'" + code + "'";

		if (!Api.isEmptyString(code)) {
			String clientSideIds = "";
			for (Form form : forms) {
				if (!Api.isEmptyString(form.getClientFormId())) {
					if (!Api.isEmptyString(clientSideIds)) {
						clientSideIds += ",";
					}

					clientSideIds += "'" + form.getClientFormId() + "'";
				}
			}

			if (!Api.isEmptyString(clientSideIds)) {
				jdbcTemplate.query(
						Sqls.SELECT_CLIENT_IDS_FORMS.replace(":clientSideIds",
								clientSideIds).replace(":clientCode", code),
						clientIdCallBackHandler);
			}
		}

		return clientIdCallBackHandler.getClientIdMap();
	}
	
	public Map<Long, FormSectionFieldSpec> getFormSectionFieldSpecForFormSpecMap(
			String formSpecIds) {
		Map<Long, FormSectionFieldSpec> map = new HashMap<Long, FormSectionFieldSpec>();

		if (!Api.isEmptyString(formSpecIds)) {
			List<FormSectionFieldSpec> formSectionFieldSpec = jdbcTemplate
					.query(Sqls.SELECT_FORM_SECTION_FIELD_SPECS_FOR_FORM_SPEC_IN
							.replace(":ids", formSpecIds), new Object[] {},
							new BeanPropertyRowMapper<FormSectionFieldSpec>(
									FormSectionFieldSpec.class));

			for (FormSectionFieldSpec formSectionFieldSpecTemp : formSectionFieldSpec) {
				map.put(formSectionFieldSpecTemp.getSectionFieldSpecId(),
						formSectionFieldSpecTemp);
			}

		}

		return map;
	}
	
	public List<FormFieldSpec> getFormFieldSpecForFieldSpecIdsIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormFieldSpec> formFieldSpecs = jdbcTemplate.query(
					Sqls.SELECT_FORM_FIELD_SPECS_FOR_FIELD_SPEC_ID.replace(":ids", ids),
					new Object[] {}, new BeanPropertyRowMapper<FormFieldSpec>(
							FormFieldSpec.class));
			return formFieldSpecs;
		}

		return new ArrayList<FormFieldSpec>();
	}
	
	public List<FormSpec> getFormSpecsIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormSpec> formSpecs = jdbcTemplate.query(
					Sqls.SELECT_FORM_SPECS_IN.replace(":ids", ids),
					new Object[] {}, new BeanPropertyRowMapper<FormSpec>(
							FormSpec.class));

			return formSpecs;
		}

		return new ArrayList<FormSpec>();
	}
	
	
	public List<Payment> getPaymentByClientId(long clietId) {

		List<Payment> payments=new ArrayList<Payment>();
		String sql = Sqls.SELECT_PAYMENT_BY_CLIENTID;
		
		payments=jdbcTemplate.query(sql, new Object[]{clietId },
				new BeanPropertyRowMapper<Payment>(Payment.class));

		return payments;
	}
	
	
	public Map<Long, FormFieldSpec> getFormFieldSpecMapForFields(String ids) {
		FormFieldSpecCallBackHandler formFieldSpecCallBackHandler = new FormFieldSpecCallBackHandler();

		Long currentTime = System.currentTimeMillis();
		Boolean debugLogEnable = constants.isDebugLogEnable();
		if (!Api.isEmptyString(ids)) {
			
			//Log.info(getClass(), "getFormFieldSpecMapForFields() // ids : "+ids, debugLogEnable, null);
			
			currentTime = System.currentTimeMillis();
			jdbcTemplate.query(Sqls.SELECT_FORM_FIELD_SPECS_FOR_FIELDS.replace(
					":ids", ids), formFieldSpecCallBackHandler);
			Log.info(getClass(), "getFormFieldSpecMapForFields() // time taken to query from DB : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);
		}

		currentTime = System.currentTimeMillis();
		try
		{
			return formFieldSpecCallBackHandler.getFormFieldSpecMap();
		}
		finally
		{
			Log.info(getClass(), "getFormFieldSpecMapForFields() // time taken to formFieldSpecCallBackHandler.getFormFieldSpecMap()  : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);
		}
	}
	
/*	public long insertPayment(final Payment payment) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_PAYMENT,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, payment.getClientId());
				ps.setLong(2, payment.getRemoteFormId());
				ps.setLong(3, payment.getFormFieldSpecId());
				ps.setString(4, payment.getReferenceId());
				ps.setString(5, payment.getBankTerminalId());
				ps.setString(6, payment.getCardHolderName());
				ps.setString(7, payment.getCardType());
				ps.setLong(8, payment.getPaymentId());
				ps.setString(9, payment.getTransactionId());
				ps.setString(10, payment.getResponseCode());
				ps.setString(11, payment.getStatus());
				ps.setString(12, payment.getRrn());
				ps.setString(13, payment.getActualAmount());
				ps.setString(14, payment.getProcessedAmount());
				ps.setString(15, payment.getPaymentDate());
				ps.setString(16, payment.getPaymentTime());
				ps.setString(17, payment.getCreatedTime());
				ps.setString(18, payment.getModifiedTime());
				ps.setString(19, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(20, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(21, payment.getCardNumber());
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		payment.setId(id);

		return id;
	}
	
	
	public int updatePaymentByClientId(final Payment payment) {
		return jdbcTemplate.update(
				Sqls.UPDATE_PAYMENT_BY_CLIENTID,
				new Object[] {payment.getRemoteFormId(),
						payment.getFormFieldSpecId(),
						payment.getReferenceId(),
						payment.getBankTerminalId(),
						payment.getCardHolderName(),
						payment.getCardType(),
						payment.getPaymentId(),
						payment.getTransactionId(),
						payment.getResponseCode(),
						payment.getStatus(),
						payment.getRrn(),
						payment.getActualAmount(),
						payment.getProcessedAmount(),
						payment.getPaymentDate(),
						payment.getPaymentTime(),
						payment.getCreatedTime(),
						payment.getModifiedTime(),
						Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())),
						payment.getCardNumber(),
						payment.getClientId()
						
	});
}
*/
	
	public List<PermissionSet> getPermissionSetsForEmployee(String empId) {
		List<PermissionSet> permissionSets = jdbcTemplate.query(
				Sqls.SELECT_PERMISSION_SETS_FOR_EMPLOYEE,
				new Object[] { empId },
				new BeanPropertyRowMapper<PermissionSet>(PermissionSet.class));
		return permissionSets;
	}
	public Map<Long, List<Long>> getPermissionSetEmployeeGroups(
			List<PermissionSet> permissionSets) {
		String ids = "";
		if (permissionSets != null) {
			for (PermissionSet permissionSet : permissionSets) {
				if (!Api.isEmptyString(ids)) {
					ids += ",";
				}
				ids += permissionSet.getPermissionSetId();
			}
		}

		return getPermissionSetEmployeeGroups(ids);
	}
	
	public Map<Long, List<Long>> getPermissionSetEmployeeGroups(String ids) {
		if (!Api.isEmptyString(ids)) {
			PermissionSetsEmployeeGroupCallBackHandler permissionSetsEmployeeGroupCallBackHandler = new PermissionSetsEmployeeGroupCallBackHandler();
			String sql = Sqls.SELECT_PERMISSION_SET_EMPLOYEE_GROUP.replace(
					":ids", ids);

			if (!Api.isEmptyString(ids)) {
				jdbcTemplate.query(sql,
						permissionSetsEmployeeGroupCallBackHandler);
			}

			return permissionSetsEmployeeGroupCallBackHandler
					.getPermissionSetsEmployeeGroupMap();
		} else {
			return new HashMap<Long, List<Long>>();
		}
	}
	
	public Map<Long, FormFieldSpec> getFormFieldSpecMapIn(String ids) {
		FormFieldSpecCallBackHandler formFieldSpecCallBackHandler = new FormFieldSpecCallBackHandler();

		if (!Api.isEmptyString(ids)) {
			jdbcTemplate.query(
					Sqls.SELECT_FORM_FIELD_SPECS_IN.replace(":ids", ids),
					formFieldSpecCallBackHandler);
		}

		return formFieldSpecCallBackHandler.getFormFieldSpecMap();
	}

	public void insertFormFields(List<FormField> fields, boolean isMasterForm,boolean restrictDataFromMobile,boolean isSync) {
		Long currentInMillies = System.currentTimeMillis();
		Set<Long> formIds = new HashSet<Long>();
		List<FormCustomers> formCustomers = new ArrayList<FormCustomers>();
		List<FormCustomEntities> formCustomEntities = new ArrayList<FormCustomEntities>();
		Set<Long> formIdsForCustomEntities = new HashSet<Long>();
		List<FormFieldSpec> formFieldSpecs = new ArrayList<FormFieldSpec>();
		Map<Long,FormFieldSpec> fieldSpecIdAndFieldSpecsMap = new HashMap<Long,FormFieldSpec>();
		if(fields!=null && fields.size()>0)
		{
			FormField formField = fields.get(0);
			formFieldSpecs = getFormFieldSpecForIn(formField.getFormSpecId()+"");
			fieldSpecIdAndFieldSpecsMap = (Map) Api.getMapFromList(formFieldSpecs, "fieldSpecId");
		}
		
		for (FormField field : fields) {
			ReportField reportField = null;
			if(fieldSpecIdAndFieldSpecsMap!=null && !fieldSpecIdAndFieldSpecsMap.isEmpty() &&
					fieldSpecIdAndFieldSpecsMap.get(field.getFieldSpecId()) != null){
				reportField = getExtendedDao().getReportFormFields(
						field.getFieldValue(), field.getFieldSpecId(),fieldSpecIdAndFieldSpecsMap.get(field.getFieldSpecId()).getFieldType());
			}else {
				reportField = getReportFormFields(
						field.getFieldValue(), field.getFieldSpecId());
			}
			
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
			} else if (field.getFieldType() == 39) {
				if (!Api.isEmptyString(field.getFieldValue())) {
					field.setDisplayValue(Api.getDateSpanDisplayValue(field.getFieldValue()));
				}
			} else if (field.getFieldType() == 40) {
				if (!Api.isEmptyString(field.getFieldValue())) {
					field.setDisplayValue(Api.getDateTimeSpanDisplayValue(field.getFieldValue()));
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
				insertFormFields(sql + placeHolderString, fieldsSubList);
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
			
			/*Long currentTime1 = System.currentTimeMillis();
			getExtraSupportAdditionalDao().insertCustomerLoginForms(formCustomers);
			Log.info(this.getClass(),"FormIds :"+formIds+" Time Taken to process insertCustomerLoginForms FormFileds :"+(System.currentTimeMillis() - currentTime1)+" ms");*/
			
			
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
	
	public ReportField getReportFormFields(String fieldValue, Long fieldSpecId) {
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
			String sql = Sqls.SELECT_REPORT_FORM_FIELDS.replace(":idsCsv",idsCsv);// .replaceAll(":fieldValue",
														// fieldValue);
			reportField = jdbcTemplate.queryForObject(sql, new Object[] {
					fieldValue, fieldValue, fieldValue, fieldValue, fieldValue,
					fieldValue, fieldValue,fieldValue,fieldValue,fieldValue,fieldSpecId },
					new BeanPropertyRowMapper<ReportField>(ReportField.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportField;
	}
	
	public FormSpec getLatestFormSpec(String uniqueId) {
		FormSpec formSpec = jdbcTemplate.queryForObject(
				Sqls.SELECT_LATEST_FORM_SPEC, new Object[] { uniqueId },
				new BeanPropertyRowMapper<FormSpec>(FormSpec.class));
		return formSpec;
	}
	
	
	public String getIdentifierForGivenForm(String formId) {
		// String identifier="";
		try {
			return jdbcTemplate.queryForObject(Sqls.SELECT_IDENTIFIER_FOR_FORM,
					new Object[] { formId }, String.class);
		} catch (Exception ex) {
			Log.ignore(getClass(), ex);
		}
		return "";

	}
	
	public Customer getCustomer(String id) {
		Customer customer = jdbcTemplate.queryForObject(Sqls.SELECT_CUSTOMER,
				new Object[] { id }, new BeanPropertyRowMapper<Customer>(
						Customer.class));
		return customer;
	}
	public Employee getAssignedEmployeeForGivenJob(String jobId) {
		try {
			return jdbcTemplate.queryForObject(
					Sqls.SELECT_ASSIGNED_EMPLOYEE_FOR_GIVEN_JOB,
					new Object[] { jobId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception ex) {
			Log.info(getClass(), "Exception while retriving employee", ex);
		}
		return null;
	}
	
	public Route getRouteById(long companyId, String routeId) {
		Route route = jdbcTemplate.queryForObject(Sqls.SELECT_ROUTE_BY_ID,
				new Object[] { companyId, routeId },
				new BeanPropertyRowMapper<Route>(Route.class));
		return route;
	}
	
	public List<EntityField> getEntityFieldByEntityIn(String entityIds) {
		if (!Api.isEmptyString(entityIds)) {
			String sql = Sqls.SELECT_ENTITY_FIELD_BY_ENTITY_IN.replace(":ids",
					entityIds);
			List<EntityField> entityFields = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<EntityField>(
							EntityField.class));
			return entityFields;
		} else {
			return new ArrayList<EntityField>();
		}
	}
	public List<EntityField> getEntityFieldByEntityIn(List<Entity> entities) {

		String ids = "";
		/*if (entities != null) {
			for (Entity entity : entities) {
				if (!Api.isEmptyString(ids)) {
					ids += ",";
				}

				ids += entity.getEntityId() + "";
			}
		}*/
		ids = Api.toCSV(entities, "entityId", CsvOptions.NONE);

		return getEntityFieldByEntityIn(ids);
	}
	public FormSpec getFormSpec(String id) {
		FormSpec formSpec = null;

		try {
		 formSpec = jdbcTemplate.queryForObject(Sqls.SELECT_FORM_SPEC,
				new Object[] { id }, new BeanPropertyRowMapper<FormSpec>(
						FormSpec.class));
		return formSpec;
		}catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getFormSpec method",e.toString(),stackTrace,0);
			return formSpec;
		}
	}
	
	public List<Long> getEntityIdsForEntity(String requiredEntityIds) {
		String sql = Sqls.SELECT_ENTITY_IDS_FOR_GIVEN_ENTITIES.replace(
				":requiredEntityIds", requiredEntityIds);
		return jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public Map<Long, EntityFieldSpec> getEntityFieldSpecMapIn(String ids) {
		Map<Long, EntityFieldSpec> map = new HashMap<Long, EntityFieldSpec>();

		if (!Api.isEmptyString(ids)) {
			String sql = Sqls.SELECT_ENTITY_FIELD_SPECS_IN.replace(":ids", ids);
			List<EntityFieldSpec> entityFieldSpecs = jdbcTemplate.query(sql,
					new Object[] {},
					new BeanPropertyRowMapper<EntityFieldSpec>(
							EntityFieldSpec.class));
			for (EntityFieldSpec entityFieldSpec : entityFieldSpecs) {
				map.put(entityFieldSpec.getEntityFieldSpecId(), entityFieldSpec);
			}
		}

		return map;

	}

	public Map<Long, FormFieldSpec> getFormFieldSpecMapForFields(
			List<FormField> formFields) {
		
		Boolean debugLogEnable = constants.isDebugLogEnable();
		Long currentTime = System.currentTimeMillis();
		/*StringBuilder ids = new StringBuilder("");
		for (FormField formField : formFields) {
			if (!Api.isEmptyString(ids.toString())) {
				ids.append(",");
			}

			ids.append("'").append(formField.getFieldSpecId()).append("'");
		}*/
		String ids = Api.toCSV(formFields, "fieldSpecId", CsvOptions.NONE);
		Log.info(getClass(), "getFormFieldSpecMapForFields() // time taken to Api.toCSV(formFields, 'fieldSpecId', CsvOptions.NONE) : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);

		return getFormFieldSpecMapForFields(ids);
	}

	public Map<Long, FormSectionFieldSpec> getFormSectionFieldSpecMapForFields(
			List<FormSectionField> formSectionFields) {
		if (formSectionFields != null && formSectionFields.size() > 0) {
			String ids = "";
			for (FormSectionField formSectionField : formSectionFields) {
				if (!Api.isEmptyString(ids)) {
					ids += ",";
				}

				ids += "'" + formSectionField.getSectionFieldSpecId() + "'";
			}

			return getFormSectionFieldSpecMapForFields(ids);
		} else {
			return new HashMap<Long, FormSectionFieldSpec>();
		}
	}
	public List<Customer> getCustomersInForSync(String ids) {
		List<Customer> customers = jdbcTemplate.query(
				Sqls.SELECT_CUSTOMER_IN_SYNC.replace(":ids", ids),
				new Object[] {}, new BeanPropertyRowMapper<Customer>(
						Customer.class));
		return customers;
	}

	public Map<Long, FormSectionFieldSpec> getFormSectionFieldSpecMapForFields(
			String sectionFieldSpecIds) {
		Map<Long, FormSectionFieldSpec> map = new HashMap<Long, FormSectionFieldSpec>();

		if (!Api.isEmptyString(sectionFieldSpecIds)) {
			List<FormSectionFieldSpec> formSectionFieldSpec = jdbcTemplate
					.query(Sqls.SELECT_FORM_SECTION_FIELD_SPECS_IN.replace(
							":ids", sectionFieldSpecIds), new Object[] {},
							new BeanPropertyRowMapper<FormSectionFieldSpec>(
									FormSectionFieldSpec.class));

			for (FormSectionFieldSpec formSectionFieldSpecTemp : formSectionFieldSpec) {
				map.put(formSectionFieldSpecTemp.getSectionFieldSpecId(),
						formSectionFieldSpecTemp);
			}

		}

		return map;
	}
	
	public List<FormFieldSpecValidValue> getFormFieldSpecValidValuesIn(
			List<FormFieldSpec> formFieldSpeccs) {
		StringBuilder ids = new StringBuilder("");
		for (FormFieldSpec formFieldSpec : formFieldSpeccs) {
			if (!Api.isEmptyString(ids.toString())) {
				ids.append(",");
			}

			ids.append("'").append(formFieldSpec.getFieldSpecId()).append("'");
		}

		return getFormFieldSpecValidValuesIn(ids.toString());
	}
	
	public List<FormFieldSpecValidValue> getFormFieldSpecValidValuesIn(
			String ids) {
		if (!Api.isEmptyString(ids)) {
			List<FormFieldSpecValidValue> fieldSpecValidValues = jdbcTemplate
					.query(Sqls.SELECT_FORM_FIELD_VALID_VALUES_IN.replace(
							":ids", ids), new Object[] {},
							new BeanPropertyRowMapper<FormFieldSpecValidValue>(
									FormFieldSpecValidValue.class));
			return fieldSpecValidValues;
		}

		return new ArrayList<FormFieldSpecValidValue>();
	}
	
	public List<EmployeeGroup> getEmployeeGroupIn(String ids) {
		List<EmployeeGroup> employeeGroups = jdbcTemplate.query(
				Sqls.SELECT_EMPLOYEE_GROUP_IN.replace(":ids", ids),
				new Object[] {}, new BeanPropertyRowMapper<EmployeeGroup>(
						EmployeeGroup.class));
		return employeeGroups;
	}
	
	public List<Long> getEmployeesFromEmployeeGroup(String employeeGroupIds) {
		try {
			String sql = Sqls.SELECT_EMPLOYEES_FROM_EMPLOYEEGROUP;
			sql = sql.replace(":employeeGroupIds", employeeGroupIds);
			return jdbcTemplate.queryForList(sql, new Object[] {}, Long.class);
		}catch(Exception e) {
			return new ArrayList<Long>();
		}
	}
	public List<CustomerTag> getTagsByCustomerIdsforSync(String customerIds) {

		List<CustomerTag> customerTags = new ArrayList<CustomerTag>();

		String sql = Sqls.SELECT_CUSTOMER_TAGS_BY_CUSTOMER_IDS;
		customerTags = jdbcTemplate.query(
				sql.replace(":customerIds", customerIds),
				new BeanPropertyRowMapper<CustomerTag>(CustomerTag.class));
		return customerTags;

	}
	
	public Plugin getPlugin(long companyId, String pluginNo) {
		Plugin plugin = jdbcTemplate.queryForObject(Sqls.SELECT_PLUGIN,
				new Object[] { companyId, pluginNo },
				new BeanPropertyRowMapper<Plugin>(Plugin.class));
		return plugin;
	}
	
	public List<EmployeeGroup> getEmployeeGroupOfEmployee(Long... empIds) {
		String ids = Api.toCSV(empIds);
		if (!Api.isEmptyString(ids)) {
			String sql = Sqls.SELECT_EMPLOYEE_GROUP_OF_EMPLOYEES.replace(
					":ids", ids);
			List<EmployeeGroup> employeeGroups = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<EmployeeGroup>(
							EmployeeGroup.class));
			return employeeGroups;
		} else {
			return new ArrayList<EmployeeGroup>();
		}
	}
	public Company getCompany(long id) {
		Company company = jdbcTemplate.queryForObject(Sqls.SELECT_COMPANY,
				new Object[] { id }, new BeanPropertyRowMapper<Company>(
						Company.class));
		return company;
	}

	public List<FormSectionSpec> getFormSectionSpecForIn(
			List<FormSpec> formSpecs) {
		if (formSpecs != null && formSpecs.size() > 0) {
			String ids = "";
			for (FormSpec formSpec : formSpecs) {
				if (!Api.isEmptyString(ids)) {
					ids += ",";
				}
				ids += formSpec.getFormSpecId();
			}

			List<FormSectionSpec> formSectionSpecs = jdbcTemplate.query(
					Sqls.SELECT_FORM_SECTION_SPECS_IN.replace(":ids", ids),
					new Object[] {},
					new BeanPropertyRowMapper<FormSectionSpec>(
							FormSectionSpec.class));

			return formSectionSpecs;
		}

		return new ArrayList<FormSectionSpec>();
	}
public List<FormField> getFormFieldsForSync(List<Form> forms) {
		
		Boolean debugLogEnable = constants.isDebugLogEnable();
		Long currentTime = System.currentTimeMillis();
		/*StringBuilder ids = new StringBuilder("");
		for (Form form : forms) {
			if (!Api.isEmptyString(ids.toString())) {
				ids.append(",");
			}

			ids.append("'").append(form.getFormId()).append("'");
		}*/
		
		String ids = Api.toCSV(forms, "formId", CsvOptions.NONE);
		Log.info(getClass(), "getFormFieldsForSync() // time taken to Api.toCSV(forms, 'formId', CsvOptions.NONE) : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);

		return getFormFieldsForSync(ids);
	}

public List<FormField> getFormFieldsForSync(String ids) {
	
	Boolean debugLogEnable = constants.isDebugLogEnable();
	Long currentTime = System.currentTimeMillis();
	try
	{
	//	Log.info(getClass(), "getFormFieldsForSync() // ids  : "+ids, debugLogEnable, null);
		if (!Api.isEmptyString(ids)) {
			List<FormField> formFields = jdbcTemplate.query(
					Sqls.SELECT_FORM_FIELD_SYNC.replace(":ids", ids),
					new Object[] {}, new BeanPropertyRowMapper<FormField>(
							FormField.class));
			return formFields;
		}

		return new ArrayList<FormField>();
	}
	finally
	{
		Log.info(getClass(), "getFormFieldsForSync() // time taken to get FormFields from DB : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);
	}
}

public List<FormSectionField> getFormSectionFieldsForFormIn(List<Form> forms) {
	/*String ids = "";
	if (forms != null) {
		for (Form form : forms) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}

			ids += form.getFormId();
		}
	}*/
	StringBuilder ids = new StringBuilder("");
	for (Form form : forms) {
		if (!Api.isEmptyString(ids.toString())) {
			ids.append(",");
		}

		ids.append(form.getFormId());
	}

	return getFormSectionFieldsForFormIn(ids.toString());
}

public List<FormSectionField> getFormSectionFieldsForFormIn(String formIds) {
	if (!Api.isEmptyString(formIds)) {
		String sql = Sqls.SELECT_FORM_SECTION_FIELD_FOR_FORM_IN.replace(
				":ids", formIds);
		List<FormSectionField> formSectionFields = jdbcTemplate.query(sql,
				new Object[] {},
				new BeanPropertyRowMapper<FormSectionField>(
						FormSectionField.class));
		return formSectionFields;
	}

	return new ArrayList<FormSectionField>();
}

public List<String> getCustomerTerritoryIdsFromCustomersInSync(
		String allCustomerIdsCsv) {
	List<String> customerTerritoryIds = new ArrayList<String>();

	String sql = Sqls.SELECT_CUSTOME_TERRITORY_IDS_FROM_CUSTOMERS_FOR_SYNC;
	customerTerritoryIds = jdbcTemplate.queryForList(
			sql.replace(":customerIds", allCustomerIdsCsv),
			 new Object[] {}, String.class);
	return customerTerritoryIds;
}
public List<EmployeeEntityMap> getMappedEntityIds(long empId,
		String entitySpecIds) {
	List<EmployeeEntityMap> mappedEntityList = new ArrayList<EmployeeEntityMap>();
	// List<EmployeeEntityMap> mappedEntityList =null;
	if (!Api.isEmptyString(entitySpecIds)) {
		String sql = Sqls.SELECT_MAPPED_ENTITIES_FOR_ENTITYSPEC_EMPLOYEE
				.replaceAll(":entitySpecIds", entitySpecIds);

		mappedEntityList = jdbcTemplate.query(sql, new Object[] { empId },
				new BeanPropertyRowMapper<EmployeeEntityMap>(
						EmployeeEntityMap.class));
	}
	return mappedEntityList;
}
public List<Customer> getCustomers(String ids) {
	if (!Api.isEmptyString(ids)) {
		List<Customer> customers = jdbcTemplate.query(Sqls.SELECT_CUSTOMERS
				.replace(":ids", ids), new Object[] {},
				new BeanPropertyRowMapper<Customer>(Customer.class));
		return customers;
	} else {
		return new ArrayList<Customer>();
	}
	
}
public List<Tag> getCompanyTagsForSync(Integer companyId, String tagIds) {

	List<Tag> companyTags = new ArrayList<Tag>();
	String sql = Sqls.SELECT_TAGS_BY_COMPANY_ID_AND_TAG_IDS_FOR_SYNC.replace(
			":tagIds", tagIds);
	companyTags = jdbcTemplate.query(sql, new Object[] { companyId },
			new BeanPropertyRowMapper<Tag>(Tag.class));

	return companyTags;
}

public List<FormSectionFieldSpec> getFormSectionFieldSpecForIn(
		List<FormSectionSpec> formSectionSpecs) {
	if (formSectionSpecs != null && formSectionSpecs.size() > 0) {
		String ids = "";
		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}
			ids += formSectionSpec.getSectionSpecId();
		}

		List<FormSectionFieldSpec> formSectionFieldSpec = jdbcTemplate
				.query(Sqls.SELECT_FORM_SECTION_FIELD_SPECS_BY_SECTION_IN
						.replace(":ids", ids), new Object[] {},
						new BeanPropertyRowMapper<FormSectionFieldSpec>(
								FormSectionFieldSpec.class));

		return formSectionFieldSpec;

	}

	return new ArrayList<FormSectionFieldSpec>();
}

public List<FormSectionFieldSpecValidValue> getFormSectionFieldSpecValidValuesIn(
		List<FormSectionFieldSpec> formSectionFieldSpecs) {
	String ids = "";
	for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
		if (!Api.isEmptyString(ids)) {
			ids += ",";
		}

		ids += "'" + formSectionFieldSpec.getSectionFieldSpecId() + "'";
	}

	return getFormSectionFieldSpecValidValuesIn(ids);
}
public List<FormSectionFieldSpecValidValue> getFormSectionFieldSpecValidValuesIn(
		String ids) {
	if (!Api.isEmptyString(ids)) {
		List<FormSectionFieldSpecValidValue> formSectionFieldSpecValidValues = jdbcTemplate
				.query(Sqls.SELECT_FORM_SECTION_FIELD_VALID_VALUES_IN
						.replace(":ids", ids),
						new Object[] {},
						new BeanPropertyRowMapper<FormSectionFieldSpecValidValue>(
								FormSectionFieldSpecValidValue.class));
		return formSectionFieldSpecValidValues;
	}

	return new ArrayList<FormSectionFieldSpecValidValue>();
}

public String getEmployeeGroupIdsForEmployee(String empId) {
	try {
		return jdbcTemplate.queryForObject(
				Sqls.SELECT_EMPLOYEE_GROUPS_FOR_EMPLOYEE,
				new Object[] { empId }, String.class);
		
	}catch(Exception e) {
		//StackTraceElement[] stackTrace = e.getStackTrace();
		//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmployeeGroupIdsForEmployee method",e.toString(),stackTrace,0);
		return null;
	}
}
public List<VisibilityDependencyCriteria> getVisibilityDependencyCriteriasForFormSpecs(
		String formSpecIds) {
	List<VisibilityDependencyCriteria> visibilityDependencyCriteria = new ArrayList<VisibilityDependencyCriteria>();
	if (!Api.isEmptyString(formSpecIds)) {
		String sql = Sqls.SELECT_VISIBILITY_DEPENDENCY_CRITIRIAS_FOR_FORMSPECS
				.replace(":formSpecIds", formSpecIds);
		visibilityDependencyCriteria = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<VisibilityDependencyCriteria>(
						VisibilityDependencyCriteria.class));

	}
	return visibilityDependencyCriteria;
}


public List<ListFilteringCritiria> getListFilteringCritiriasForFormSpecs(
		String formSpecIds) {
	List<ListFilteringCritiria> listFilteringCritiria = new ArrayList<ListFilteringCritiria>();
	if (!Api.isEmptyString(formSpecIds)) {
		String sql = Sqls.SELECT_LIST_FILTERING_CRITIRIA_FOR_FORMSPECS
				.replace(":formSpecIds", formSpecIds);
		listFilteringCritiria = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ListFilteringCritiria>(
						ListFilteringCritiria.class));
	}
	return listFilteringCritiria;
}

public List<CustomerFilteringCritiria> getListOfCustomerFilterCriterias(
		String formSpecIds) {
	List<CustomerFilteringCritiria> customerFilteringCritiria = new ArrayList<CustomerFilteringCritiria>();
	if(!Api.isEmptyString(formSpecIds))
	{
		String sql = Sqls.SELECT_CUSTOMER_FILTER_CRITERIA.replace(":formSpecIds", formSpecIds);
		customerFilteringCritiria = jdbcTemplate.query(
				sql, new Object[] {},
				new BeanPropertyRowMapper<CustomerFilteringCritiria>(
						CustomerFilteringCritiria.class));
	}
	return customerFilteringCritiria;
	
}
public List<FormSectionField> getFormSectionFieldsForSync(List<Form> forms) {
	
	Boolean debugLogEnable = constants.isDebugLogEnable();
	Long currentTime = System.currentTimeMillis();
	
	/*StringBuilder ids = new StringBuilder("");
	for (Form form : forms) {
		if (!Api.isEmptyString(ids.toString())) {
			ids.append(",");
		}

		ids.append("'").append(form.getFormId()).append("'");
	}*/
	
	String ids = Api.toCSV(forms, "formId", CsvOptions.NONE);
	Log.info(getClass(), "getFormSectionFieldsForSync() // time taken to Api.toCSV(forms, 'formId', CsvOptions.NONE) : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);

	return getFormSectionFieldsForSync(ids);
}
public List<FormSectionField> getFormSectionFieldsForSync(String ids) {
	
	Boolean debugLogEnable = constants.isDebugLogEnable();
	Long currentTime = System.currentTimeMillis();
	try
	{
	//	Log.info(getClass(), "getFormSectionFieldsForSync() // ids  : "+ids, debugLogEnable, null);
		if (!Api.isEmptyString(ids)) {
			List<FormSectionField> formSectionFields = jdbcTemplate.query(
					Sqls.SELECT_FORM_SECTION_FIELD_SYNC.replace(":ids", ids),
					new Object[] {},
					new BeanPropertyRowMapper<FormSectionField>(
							FormSectionField.class));
			return formSectionFields;
		}
	}
	finally
	{
		Log.info(getClass(), "getFormSectionFieldsForSync() // time taken to get FormSectionFields from DB  : "+(System.currentTimeMillis()- currentTime)+" ms", debugLogEnable, null);
	}

	return new ArrayList<FormSectionField>();
}
	public List<Form> getFormsIn(String ids) {
		if (!Api.isEmptyString(ids)) {
			String sql = Sqls.SELECT_FORMS_IN.replace(":ids", ids);
			List<Form> forms = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Form>(Form.class));
			return forms;
		} else {
			return new ArrayList<Form>();
		}
	}
	public Company getCompanyInfo(String id) {
		Company company = jdbcTemplate.queryForObject(Sqls.SELECT_COMPANY_INFO,
				new Object[] { id }, new BeanPropertyRowMapper<Company>(
						Company.class));
		return company;
	}
	public int[] saveNearByCellInfo(final long locationId,
			final List<CellInfo> cellInfos) {
		return jdbcTemplate.batchUpdate(Sqls.INSERT_NEARBY_CELL_INFO,
				new BatchPreparedStatementSetter() {

					// @Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						CellInfo cellInfo = cellInfos.get(i);
						ps.setLong(1, locationId);
						ps.setString(2, cellInfo.getCellId());
						ps.setString(3, cellInfo.getLac());
						ps.setString(4, cellInfo.getMcc());
						ps.setString(5, cellInfo.getMnc());
						ps.setString(6, cellInfo.getSignalStrength());
					}

					// @Override
					public int getBatchSize() {
						return cellInfos.size();
					}
				});
	}
	public List<Long> getEntitySpecIdsForFormSpec(String formSpecId) {
		List<Long> ids = jdbcTemplate.queryForList(
				Sqls.SELECT_ENTITY_SPEC_IDS_FOR_FORM_SPC, new Object[] {
						formSpecId, formSpecId }, Long.class);
		return ids;
	}
	public List<FormFieldSpec> getFormFieldSpecs(long formSpecId) {
		List<FormFieldSpec> formFieldSpecs = jdbcTemplate.query(
				Sqls.SELECT_FORM_FIELD_SPECS, new Object[] { formSpecId },
				new BeanPropertyRowMapper<FormFieldSpec>(FormFieldSpec.class));
		return formFieldSpecs;
	}
	public Long getFormSpecIdFromSkeleton(long skeletonFormSpecId, int companyId) {
		try {
			return jdbcTemplate.queryForObject(
					Sqls.GET_FORMSPEC_ID_FROM_SKELETON, new Object[] {
							skeletonFormSpecId, companyId }, Long.class);
		} catch (Exception ex) {
//			ex.printStackTrace();
		}

		return null;
	}
	public List<FormSpec> getLatestFormSpecsForUnquids(String uniqueIds) {

		if (!Api.isEmptyString(uniqueIds)) {
			   String formSpecUniqueId = Api.processStringValuesList1(Api.csvToList(uniqueIds));
			String sql = Sqls.SELECT_LATEST_FORM_SPECS.replace(":uniqueIds",
					formSpecUniqueId);
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FormSpec>(
					FormSpec.class));
		}
		return new ArrayList<FormSpec>();
	}

	public List<Long> getAllCustomerIdsForCompany(long companyId) {
		List<Long> customerIdsList = jdbcTemplate.queryForList(Sqls.SELECT_ALL_CUSTOMER_IDS_FOR_COMPANY,
				new Object[] { companyId },
				Long.class);
		return customerIdsList;
	}
public long insertActivityStream(final ActivityStream activityStream) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_ACTIVITY_STREAMS,
						Statement.RETURN_GENERATED_KEYS);

				ps.setLong(1, activityStream.getCompanyId());
				ps.setLong(2, activityStream.getCreatedBy());
				ps.setString(3, activityStream.getMessage());
				ps.setLong(4, activityStream.getTargetId());
				ps.setInt(5, activityStream.getTargetType());
				ps.setString(6, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		return id;
	}

public String getCustomerIdsForEmpsUnderMe(String empIds) {

	String logText = "getCustomerIdsForEmpsUnderMe()  = ";
	String sql = Sqls.SELECT_CUSTOMERIDS_FOR_EMP_UNDER_ME;
	if (!empIds.isEmpty() && empIds != null)
		sql = sql.replace(":ids", empIds);
	else
		sql = sql.replace(":ids", "-1");

	String customerIds = null;
	
	Log.info(getClass(), logText+" starts");
	List<String> customerIdsList = jdbcTemplate.queryForList(sql, String.class);
	
	if(customerIdsList != null && !customerIdsList.isEmpty())
		customerIds = Api.toCSV(customerIdsList);
		
	return customerIds;
}

public List<CustomerField> getCustomerFieldsForCompany(Integer companyId) {

	List<CustomerField> customerFields=new ArrayList<CustomerField>();
	String sql = Sqls.SELECT_CUSTOMER_FIELDS_FOR_COMPANY;
	
	customerFields=jdbcTemplate.query(sql, new Object[]{companyId},
			new BeanPropertyRowMapper<CustomerField>(CustomerField.class));

	return customerFields;
}
public List<CustomerField> getCustomerFields() {
	
	List<CustomerField> customerFields=new ArrayList<CustomerField>();
	String sql = Sqls.SELECT_CUSTOMER_FIELDS;
	
	customerFields=jdbcTemplate.query(sql, new Object[]{},
			new BeanPropertyRowMapper<CustomerField>(CustomerField.class));

	return customerFields;
}
public JobCreateOrModifiNotify getjobDetails(String visitId) {
	try {
		return jdbcTemplate.queryForObject(
				Sqls.SELECT_JOB_DETAILS_FOR_NOTIFY,
				new Object[] { visitId },
				new BeanPropertyRowMapper<JobCreateOrModifiNotify>(
						JobCreateOrModifiNotify.class));
	} catch (Exception ex) {
		Log.info(getClass(), "while retriving visit", ex);
	}
	return null;
}
public Long getFormFieldSpecIdFromSkeleton(long skeletonFormFieldSpecId,
		long formSpecId) {
	try {

		return jdbcTemplate.queryForObject(
				Sqls.GET_FORMFIELD_SPEC_ID_FROM_SKELETON, new Object[] {
						skeletonFormFieldSpecId, formSpecId }, Long.class);

	} catch (Exception ex) {
//		ex.printStackTrace();
	}

	return null;
}
public void updateCustomersModifiedTime(long customerId) {
	Log.info(getClass(), "updateCustomersModifiedTime() // customerId = "+customerId+"  logText : "+Api.getLogText());
	jdbcTemplate.update(Sqls.UPDATE_CUSTOMER_MODIFIEDTIME,
			new Object[] { customerId });
}

public String getFormFieldValue(Long fieldSpecId, long formId, boolean isMasterForm) {
	String sql = Sqls.GET_FORM_FIELD_VALUE_FORM_ID;
	if(isMasterForm) {
		sql = Sqls.SELECT_MASTER_FORM_FIELD_VALUE_FORM_ID;
	}
	String fieldValue = null;
	try {
		fieldValue = jdbcTemplate.queryForObject(sql, new Object[] {
				fieldSpecId, formId }, String.class);
	} catch (Exception ex) {

	}
	return fieldValue;
}public Long getFormSectionFieldSpecIdFromSkeleton(
		long skeletonFormSectionFieldSpecId, long formSpecId,
		long sectionSpecId) {
	try {

		return jdbcTemplate.queryForObject(
				Sqls.GET_FORMSECTIONFIELD_SPEC_ID_FROM_SKELETON,
				new Object[] { skeletonFormSectionFieldSpecId, formSpecId,
						sectionSpecId }, Long.class);

	} catch (Exception ex) {
//		ex.printStackTrace();
	}

	return null;
}

public Long getEntityFieldSpecIdFromSkeleton(
		long skeletonEntityFieldSpecId, long entitySpecId) {
	try {

		return jdbcTemplate.queryForObject(
				Sqls.GET_ENTITY_FIELD_SPEC_SPEC_ID_FROM_SKELETON,
				new Object[] { skeletonEntityFieldSpecId, entitySpecId },
				Long.class);

	} catch (Exception ex) {
		//ex.printStackTrace();
	}

	return null;
}

public Long getEntitySpecIdFromSkeleton(long skeletonEntitySpecId,
		int companyId) {
	try {

		return jdbcTemplate.queryForObject(
				Sqls.GET_ENTITY_SPEC_ID_FROM_SKELETON, new Object[] {
						skeletonEntitySpecId, companyId }, Long.class);

	} catch (Exception ex) {
	//	ex.printStackTrace();
	}

	return null;
}
public void updateFormFieldValue(long fieldSpecId, String value, long formId,boolean isMasterForm) {
	String sql = Sqls.UPDATE_FORM_FIELD_VALUE;
	if(isMasterForm) {
		sql = Sqls.UPDATE_MASTER_FORM_FIELD_VALUE;
	}
	jdbcTemplate.update(sql, new Object[] { value, fieldSpecId, formId });
}


public List<FizikemDashboardBean> getFizikemDashboardReport(long empId,int offset, String onDate,int companyId) {
	Long branchEntitySpecId = getEntitySpecIdFromSkeleton(constants.getEmp_branch_list_specId(), companyId);
	//String branch_name_entityFieldSpecId = constants.getFizikem_branch_entityFieldSpecId();
	Long branch_name_entityFieldSpecId = getEntityFieldSpecIdFromSkeleton(constants.getFizikem_branch_skeleton_entityFieldSpecId(), branchEntitySpecId);
	 List<FizikemDashboardBean> dashboardBeanList = jdbcTemplate
			.query(Sqls.SELECT_FIZIKEM_ROUTEPLAN_DETAILS,
					new Object[] {branch_name_entityFieldSpecId,empId,offset,onDate},
					new BeanPropertyRowMapper<FizikemDashboardBean>(
							FizikemDashboardBean.class));
	return dashboardBeanList;
}

public Long getVisitTypeIdBySkeletonVisitTypeId(long visitTypeSkeletonId,
		int companyId) {
	
	Long type = null;
	try {
		type =  jdbcTemplate.queryForObject(
				Sqls.SELECT_SERVICE_CALL_VISIT_TYPE_ID, new Object[] {
						visitTypeSkeletonId, companyId }, Long.class);

	} catch (EmptyResultDataAccessException ex) {
		Log.ignore(this.getClass(), ex);
	} 
	return type;
}

public long insertCustomer(final Customer customer, final String code) {
	KeyHolder keyHolder = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

		@Override
		public PreparedStatement createPreparedStatement(
				Connection connection) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(
					Sqls.INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, customer.getCompanyId());

			ps.setString(2, Api.makeNullIfEmpty(customer.getCustomerNo()));
			ps.setString(3, customer.getCustomerName());
			if (customer.getCustomerTypeId() != null) {
				ps.setLong(4, customer.getCustomerTypeId());
			} else {
				ps.setNull(4, Types.BIGINT);
			}
			
			if(!Api.isEmptyString(customer.getCustomerAddressStreet()))
				ps.setString(5, customer.getCustomerAddressStreet());
			else
				ps.setNull(5, Types.VARCHAR);
			
			if(!Api.isEmptyString(customer.getCustomerAddressArea()))
				ps.setString(6, customer.getCustomerAddressArea());
			else
				ps.setNull(6, Types.VARCHAR);
			
			if(!Api.isEmptyString(customer.getCustomerAddressCity()))
				ps.setString(7, customer.getCustomerAddressCity());
			else
				ps.setNull(7, Types.VARCHAR);
			
			if(!Api.isEmptyString(customer.getCustomerAddressPincode()))
				ps.setString(8, customer.getCustomerAddressPincode());
			else
				ps.setNull(8, Types.VARCHAR);
			if(!Api.isEmptyString(customer.getCustomerAddressLandMark()))
				ps.setString(9, customer.getCustomerAddressLandMark());
			else
				ps.setNull(9, Types.VARCHAR);
			if(!Api.isEmptyString(customer.getCustomerAddressState()))
				ps.setString(10, customer.getCustomerAddressState());
			else
				ps.setNull(10, Types.VARCHAR);
			if(!Api.isEmptyString(customer.getCustomerAddressCountry()))
				ps.setString(11, customer.getCustomerAddressCountry());
			else
				ps.setNull(11, Types.VARCHAR);
			
			/*ps.setString(5, customer.getCustomerAddressStreet());
			ps.setString(6, customer.getCustomerAddressArea());
			ps.setString(7, customer.getCustomerAddressCity());
			ps.setString(8, customer.getCustomerAddressPincode());
			ps.setString(9, customer.getCustomerAddressLandMark());
			ps.setString(10, customer.getCustomerAddressState());
			ps.setString(11, customer.getCustomerAddressCountry());*/
			ps.setString(12, customer.getCustomerPhone());
			ps.setString(13, customer.getCustomerLat());
			ps.setString(14, customer.getCustomerLong());
			ps.setString(15, customer.getPrimaryContactFirstName());
			ps.setString(16, customer.getPrimaryContactLastName());
			ps.setString(17, customer.getPrimaryContactPhone());
			ps.setString(18, customer.getPrimaryContactTitle());
			ps.setString(19, customer.getPrimaryContactEmail());
			ps.setString(20, customer.getPrimaryContactPhotoUrl());
			ps.setString(21, customer.getSecondaryContactFirstName());
			ps.setString(22, customer.getSecondaryContactLastName());
			ps.setString(23, customer.getSecondaryContactPhone());
			ps.setString(24, customer.getSecondaryContactTitle());
			ps.setString(25, customer.getSecondaryContactEmail());
			ps.setString(26, customer.getSecondaryContactPhotoUrl());
			ps.setString(27, customer.getLogoUrl());
			ps.setString(28, customer.getClientCustomerId());
			ps.setString(29, code);
			ps.setString(30, customer.getApiUserId());
			ps.setString(31, Api.getDateTimeInUTC(new Date(System
					.currentTimeMillis())));
			ps.setString(32, Api.getDateTimeInUTC(new Date(System
					.currentTimeMillis())));

			if (customer.getCustomerFormId() != null)
				ps.setLong(33, customer.getCustomerFormId());
			else
				ps.setNull(33, Types.BIGINT);

			if (customer.getParentFormId() != null) {
				ps.setLong(34, customer.getParentFormId());
			} else {
				ps.setNull(34, Types.BIGINT);
			}
			
			if (customer.getCustomerCreatedBy() != null) {
				ps.setLong(35, customer.getCustomerCreatedBy());
			} else {
				ps.setNull(35, Types.BIGINT);
			}
			
			if (customer.getCustomerModifiedBy() != null) {
				ps.setLong(36, customer.getCustomerModifiedBy());
			} else {
				ps.setNull(36, Types.BIGINT);
			}

			ps.setString(37, customer.getCustomerAddressDistrict());
			if(customer.getParentId() != null){
				ps.setLong(38, customer.getParentId());
			} else {
				ps.setLong(38, -1);
			}
           ps.setBoolean(39, customer.isParent());
           if(customer.getChecksum() != null){
        	   ps.setString(40, customer.getChecksum());
           }else{
        	   ps.setNull(40, Types.VARCHAR);
           }
           if(customer.getCustomerTerritoryId() != null){
        	   ps.setLong(41, customer.getCustomerTerritoryId());
           }else{
        	   ps.setNull(41, Types.BIGINT);
           }
           if(customer.getSourceOfAction() != null){
        	   ps.setInt(42, customer.getSourceOfAction());
           }else{
        	   ps.setNull(42, Types.TINYINT);
           }
			return ps;
		}
	}, keyHolder);

	long customerId = keyHolder.getKey().longValue();
	customer.setCustomerId(customerId);

	return customerId;
}


public List<FormSectionSpec> getFormSectionSpecs(long formSpecId) {
	List<FormSectionSpec> formSectionSpecs = jdbcTemplate.query(
			Sqls.SELECT_FORM_SECTION_SPECS, new Object[] { formSpecId },
			new BeanPropertyRowMapper<FormSectionSpec>(
					FormSectionSpec.class));

	return formSectionSpecs;
}

public Map<Long, List<FormSectionFieldSpec>> getFormSectionFieldSpecMap(
		List<FormSectionSpec> formSectionSpecs) {
	Map<Long, List<FormSectionFieldSpec>> map = new HashMap<Long, List<FormSectionFieldSpec>>();

	if (formSectionSpecs != null && formSectionSpecs.size() > 0) {
		String ids = "";
		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			if (!Api.isEmptyString(ids)) {
				ids += ",";
			}
			ids += formSectionSpec.getSectionSpecId();
		}

		List<FormSectionFieldSpec> formSectionFieldSpec = jdbcTemplate
				.query(Sqls.SELECT_FORM_SECTION_FIELD_SPECS_BY_SECTION_IN
						.replace(":ids", ids), new Object[] {},
						new BeanPropertyRowMapper<FormSectionFieldSpec>(
								FormSectionFieldSpec.class));

		for (FormSectionFieldSpec formSectionFieldSpecTemp : formSectionFieldSpec) {
			List<FormSectionFieldSpec> sectionFieldSpecs = map
					.get(formSectionFieldSpecTemp.getSectionSpecId());
			if (sectionFieldSpecs == null) {
				sectionFieldSpecs = new ArrayList<FormSectionFieldSpec>();
				map.put(formSectionFieldSpecTemp.getSectionSpecId(),
						sectionFieldSpecs);
			}
			sectionFieldSpecs.add(formSectionFieldSpecTemp);
		}

	}

	return map;
}

	public List<VisibilityDependencyCriteria> getVisibilityDependencyCriterias(
			String formSpecId) {
		String sql = Sqls.SELECT_VISIBILITY_DEPENDENCY_CRITIRIAS;
		List<VisibilityDependencyCriteria> visibilityDependencyCriteria = jdbcTemplate
				.query(sql,
						new Object[] { formSpecId },
						new BeanPropertyRowMapper<VisibilityDependencyCriteria>(
								VisibilityDependencyCriteria.class));
		return visibilityDependencyCriteria;
	}

		public List<FieldRestrictCritria> getFieldSpecRestrictedCritiria(
				String formSpecId, int type, String groupIds) {
			if (!Api.isEmptyString(groupIds)) {
				String sql = "";
				if (type == FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD) {
					sql = Sqls.SELECT_FIELDS_WITH_RESTRICTIONS.replace(":groupIds",
							groupIds);
				} else {
					sql = Sqls.SELECT_SECTION_FIELLD_RESTRICTIONS.replace(
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
		
		public FormFieldSpecValidValue getFormFieldSpecValidValue(String id) {
			FormFieldSpecValidValue formFieldSpecValidValue = jdbcTemplate
					.queryForObject(Sqls.SELECT_FORM_FIELD_VALID_VALUE,
							new Object[] { id },
							new BeanPropertyRowMapper<FormFieldSpecValidValue>(
									FormFieldSpecValidValue.class));
			return formFieldSpecValidValue;
		}
		
		public Customer getCustomer(String id, long empId) {
			Customer customer = jdbcTemplate.queryForObject(
					Sqls.SELECT_CUSTOMER_FOR_EMP, new Object[] { id, empId },
					new BeanPropertyRowMapper<Customer>(Customer.class));
			return customer;
		}
		
		public List<Long> getCustomerIdsForEmp(long empId) {
			List<Long> customerIds = jdbcTemplate.queryForList(
					Sqls.SELECT_CUSTOMERIDS_FOR_EMP, new Object[] { empId },
					Long.class);
			return customerIds;
		}
		
		
		public long insertMail(final Mail mail) {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_MAIL, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, mail.getMailFrom());
					ps.setString(2, mail.getMailTo());
					ps.setString(3, mail.getMailSubject());
					ps.setString(4, mail.getMailBody());
					ps.setString(5, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(6, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(7, "" + mail.getMailBodyType());
					ps.setString(8, "" + mail.getCompanyId());
					ps.setBoolean(9, mail.isVerificationRequired());
					ps.setInt(10, mail.getPriority());
					ps.setInt(11, mail.getMailSentType());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			mail.setId(id);

			return id;
		}

		public int updateMailState(long id, int state) {
			return jdbcTemplate.update(
					Sqls.UPDATE_MAIL_STATE,
					new Object[] {
							state,
							Api.getDateTimeInUTC(new Date(System
									.currentTimeMillis())), id });
		}

		public List<Mail> getUnprocessedReportMails() {
			List<Mail> mails = jdbcTemplate.query(
					Sqls.SELECT_UNPROCESSED_REPORT_MAIL, new Object[] {},
					new BeanPropertyRowMapper<Mail>(Mail.class));
			return mails;
		}
		
		public List<Entity> getEntitiesForEntitySpecs(String entitySpecIds,
				String mappedEntityIds, String mappedEntitySpecIds) {
			String sql = Sqls.SELECT_ENTITY_ENTITY_SPECS;
			if (!Api.isEmptyString(entitySpecIds)) {
				sql = sql.replace(":ids", entitySpecIds);
			} else {
				sql = sql.replace(":ids", "0");
			}
			if (!Api.isEmptyString(mappedEntityIds)) {
				sql = sql.replace(":extraCondition", "AND `entityId` IN ("
						+ mappedEntityIds + ")");
			} else if (!Api.isEmptyString(mappedEntitySpecIds)) {
				sql = sql.replace(":extraCondition", "AND `entitySpecId` NOT IN ("
						+ mappedEntitySpecIds + ")");
			} else {
				sql = sql.replace(":extraCondition", "");
			}
			List<Entity> entities = new ArrayList<Entity>();
			try {
				 entities = jdbcTemplate.query(sql, new Object[] {},
							new BeanPropertyRowMapper<Entity>(Entity.class));
			}catch(Exception e) {
				Log.info(this.getClass(), "getEntitiesForEntitySpecs()" +e);
				return entities;
			}
			return entities;
		}

		
		public long insertWorkFormMap(final WorkFormMap workFormMap,
				final String code) {
			// jdbcTemplate.update(Sqls.INSERT_INTO_WORK_FORM_MAP,new
			// Object[]{workFormMap.getWorkId(),workFormMap.getFormId(),workFormMap.getClientWorkFormMapId(),code});
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_INTO_WORK_FORM_MAP,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, workFormMap.getWorkId());
					ps.setLong(2, workFormMap.getFormId());
					ps.setString(3, workFormMap.getClientWorkFormMapId());
					ps.setString(4, code);
					return ps;
				}

			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			workFormMap.setWorkFormMapId(id);
			return id;
		}

		public int deleteFormSectionFields(long formId, boolean isMasterForm) {
			String sql = Sqls.DELETE_FORM_SECTION_FIELD;
			if(isMasterForm) {
				sql = Sqls.DELETE_MASTER_FORM_SECTION_FIELD;
			}
			return jdbcTemplate.update(sql,
					new Object[] { formId });
		}
		
		public int deleteFormFields(long formId, boolean isMasterForm) {
			String sql = Sqls.DELETE_FORM_FIELD;
			if(isMasterForm) {
				sql = Sqls.DELETE_MASTER_FORM_FIELD;
			}
			return jdbcTemplate.update(sql,
					new Object[] { formId });
		}
		public StartWorkStopWorkLocations getStartWorkStopWorkLocationsForEmpIdAndCustId(
				long empId) 
		{
			try {
				StartWorkStopWorkLocations startWorkStopWorkLocation = jdbcTemplate
						.queryForObject(
								Sqls.SELECT_STARTWORK_STOPWORK_ID_FOR_EMPID_IN,
								new Object[] {empId},
								new BeanPropertyRowMapper<StartWorkStopWorkLocations>(
										StartWorkStopWorkLocations.class));
				return startWorkStopWorkLocation;

			} catch (Exception ex) {
				Log.ignore(this.getClass(), ex);
			}
			return null;
		}
  
		
		public long insertReportMail(final Mail mail) {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_REPORT_MAIL,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, mail.getMailFrom());
					ps.setString(2, mail.getMailTo());
					ps.setString(3, mail.getMailSubject());
					ps.setString(4, mail.getMailBody());
					ps.setString(5, mail.getAttachmentPath());
					ps.setString(6, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(7, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(8, mail.getCompanyId());
					ps.setInt(9, mail.getSenderType());
					ps.setInt(10, mail.getMailSentType());
					ps.setInt(11, mail.getNotificationEmail());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			mail.setId(id);

			return id;
		}

		
		public long insertAttachmentMail(final Mail mail) {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							Sqls.INSERT_ATTACHMENT_MAIL,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, mail.getMailFrom());
					ps.setString(2, mail.getMailTo());
					ps.setString(3, mail.getMailSubject());
					ps.setString(4, mail.getMailBody());
					ps.setString(5, mail.getAttachmentPath());
					ps.setString(6, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setString(7, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setInt(8, 1);//here 1 represent other than schedule report
					ps.setString(9, mail.getCompanyId());
					ps.setInt(10, mail.getMailSentType());
					return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			mail.setId(id);

			return id;
		}
		
		public Subscripton getActiveSubscripton(long companyId) {
			Subscripton subscripton = jdbcTemplate.queryForObject(
					Sqls.SELECT_ACTIVE_SUBSCRIPTION, new Object[] { companyId },
					new BeanPropertyRowMapper<Subscripton>(Subscripton.class));
			return subscripton;
		}
}


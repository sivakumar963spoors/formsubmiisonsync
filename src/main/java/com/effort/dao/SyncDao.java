package com.effort.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.effort.entity.EmployeeEventSubmissions;
import com.effort.entity.EmployeeEventSubmissionsAuditLogs;
import com.effort.entity.EmployeeTargetsConfiguration;
import com.effort.entity.FormSectionField;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;

@Repository
public class SyncDao {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Constants constants;
	
	public long insertEmployeeEventSubmissions(final EmployeeEventSubmissions employeeEventSubmission) {
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
		  
		   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_EMPLOYEE_EVENT_SUBMISSIONS, 
					Statement.RETURN_GENERATED_KEYS);
		            
					ps.setLong(1, employeeEventSubmission.getCompanyId());
					ps.setLong(2, employeeEventSubmission.getEmpId());
					ps.setString(3, employeeEventSubmission.getEventId());
					ps.setInt(4, employeeEventSubmission.getEventType());
					ps.setLong(5, employeeEventSubmission.getTargetConfigurationId());
					ps.setString(6, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(7, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setInt(8, 0);
					return ps;
			}
		}, keyHolder);

		long id = 0;
		List<Map<String, Object>> keyMapList = keyHolder.getKeyList();
		if(keyMapList.size() > 0) {
			id = Long.parseLong(keyMapList.get(0).get("GENERATED_KEY").toString());
			employeeEventSubmission.setId(id);
		}
		return employeeEventSubmission.getId();
	}
	
	 public List<EmployeeTargetsConfiguration> getEmployeeTargetsConfigurationList(long companyId,String uniqueId,String workSpecId,int moduleTypeId) {
			List<EmployeeTargetsConfiguration> employeeTargetsConfiguration = new ArrayList<EmployeeTargetsConfiguration>();
			String condition = "";
			if(moduleTypeId == 1 && !Api.isEmptyString(uniqueId)) {
				condition = "AND formSpecUniqueId='"+uniqueId+"'";
			}else if(moduleTypeId == 2 && !Api.isEmptyString(workSpecId)) {
				condition = "AND workspecId="+workSpecId;
			}
			String sql = Sqls.SELECT_MODULE_WISE_EMPLOYEE_TARGET_ASSIGNMENT.replace(":condition", condition);
			try {
				employeeTargetsConfiguration = jdbcTemplate.query(sql, new Object[]{companyId,moduleTypeId},new BeanPropertyRowMapper<EmployeeTargetsConfiguration>(EmployeeTargetsConfiguration.class));
				
			} catch (Exception e) {
				Log.info(this.getClass(),"Exception caught while Fetching getEmployeeTargetsConfigurationList");				
			}
			
			return employeeTargetsConfiguration;
		}
	 
	 public long insertEmployeeEventSubmissionsAuditLogs(final EmployeeEventSubmissionsAuditLogs employeeEventSubmissionAuditLogs) {
			
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
		  
		   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_EMPLOYEE_EVENT_SUBMISSIONS_AUDIT_LOGS, 
					Statement.RETURN_GENERATED_KEYS);
		            
					ps.setLong(1, employeeEventSubmissionAuditLogs.getCompanyId());
					ps.setLong(2, employeeEventSubmissionAuditLogs.getEmpId());
					ps.setString(3, employeeEventSubmissionAuditLogs.getEventId());
					ps.setInt(4, employeeEventSubmissionAuditLogs.getEventType());
					ps.setLong(5, employeeEventSubmissionAuditLogs.getTargetConfigurationId());
					ps.setString(6, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setString(7, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					ps.setLong(8, employeeEventSubmissionAuditLogs.getEmpId());
					ps.setString(9, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					return ps;
			}
		}, keyHolder);

		long id = 0;
		List<Map<String, Object>> keyMapList = keyHolder.getKeyList();
		if(keyMapList.size() > 0) {
			id = Long.parseLong(keyMapList.get(0).get("GENERATED_KEY").toString());
			employeeEventSubmissionAuditLogs.setAuditId(id);
		}
		return employeeEventSubmissionAuditLogs.getAuditId();
	}
	 public List<FormSectionField> getMasterFormSectionFieldsForSync(String ids) {
			
			Boolean debugLogEnable = constants.isDebugLogEnable();
			Long currentTime = System.currentTimeMillis();
			try
			{
			//	Log.info(getClass(), "getFormSectionFieldsForSync() // ids  : "+ids, debugLogEnable, null);
				if (!Api.isEmptyString(ids)) {
					List<FormSectionField> formSectionFields = jdbcTemplate.query(
							Sqls.SELECT_FORM_MASTER_SECTION_FIELD_SYNC.replace(":ids", ids),
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

			
}
}
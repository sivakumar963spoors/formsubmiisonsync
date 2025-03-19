package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.effort.entity.AuditFormFields;
import com.effort.entity.AuditFormSectionFields;
import com.effort.settings.Constants;
import com.effort.sqls.Sqls;
import com.effort.util.Log;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AuditDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Constants constants;
	
	public void  auditRichTextFormFields(long formId,long auditBy,String time) {
		  jdbcTemplate.update(Sqls.INSERT_RICH_TEXT_FORMFIELDS_AUDIT_LOG,new Object[]{auditBy,time,formId});
	}
	
	public void  auditRichTextFormSectionFields(long formId,long auditBy,String time) {
		  jdbcTemplate.update(Sqls.INSERT_RICH_TEXT_FORM_SECTION_FIELDS_AUDIT_LOG,new Object[]{auditBy,time,formId});
	}
	public void insertFormListUpdateAuditLogs(Long formId)
	{
		String sql = Sqls.INSERT_FORMLIST_UPDATES_AUDIT_LOGS;
		int count = jdbcTemplate.update(sql,new Object[]{formId,formId});
		Log.info(getClass(), "insertFormListUpdateAuditLogs() // formId = "+formId+" count = "+count);
	
	}
	public void insertFormCustomEntityUpdateAuditLogs(Long formId)
	{
		String sql = Sqls.INSERT_CUSTOM_ENTITY_UPDATE_LOGS;
		int count = jdbcTemplate.update(sql,new Object[]{formId,formId});
		Log.info(getClass(), "insertFormCustomEntityUpdateAuditLogs() // formId = "+formId+" count = "+count);
	}
	public long auditForm(final long formId, final long by, final String time, final String ipAddress,
			final boolean isMasterForm, final String oppUserName){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.AUDIT_FORM, Statement.RETURN_GENERATED_KEYS);
				if(isMasterForm) {
					ps = connection.prepareStatement(Sqls.AUDIT_MASTER_FORM, Statement.RETURN_GENERATED_KEYS);
				}
				ps.setNull(1, Types.BIGINT);
				ps.setLong(2, by);
				ps.setString(3, time);
				if(ipAddress==null)
				{
					ps.setNull(4,Types.VARCHAR);
				}
				else
				{
					ps.setString(4, ipAddress);
				}
				ps.setString(5, oppUserName);
				ps.setLong(6, formId);
				return ps;
			}
		}, keyHolder);
		
		long id = keyHolder.getKey().longValue();
		
		return id;
	}
	
	public List<AuditFormFields> getFormFieldsForAudit(long formId,
			long auditParent, long by, String time, boolean isMasterForm ) {
		
		String sql = Sqls.SELECT_FORM_FIELDS_FOR_AUDIT;
		if(isMasterForm) {
			sql = Sqls.SELECT_MASTER_FORM_FIELDS_FOR_AUDIT;
		}
		return jdbcTemplate.query(sql,
				new Object[] { auditParent, by, time, formId }, new BeanPropertyRowMapper<AuditFormFields>(AuditFormFields.class));
	
	}
	
	
	
	public void auditFormFieldsByBatchUpdate(List<AuditFormFields> auditFormFields)
	{
		Long currentInMillies = System.currentTimeMillis();
		List<AuditFormFields> auditFormFieldsSubList = new ArrayList<AuditFormFields>();
		String sql = new String("INSERT INTO `FormFieldsAuditLog` (`auditParent`, `fieldId`, `formId`, `formSpecId`, `fieldSpecId`, `fieldValue`, `createdTime`, `modifiedTime`, `auditBy`, `auditAt`) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		
		for(int i =0; i < auditFormFields.size(); i++)
		{
			if(first)
			{
				placeHolderString.append(" (?,?,?,?,?,?,?,?,?,?)");
				first = false;
			}
			else
			{
				placeHolderString.append(", (?,?,?,?,?,?,?,?,?,?)");
			}
			auditFormFieldsSubList.add(auditFormFields.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (auditFormFields.size()-1))
			{
				auditFormFieldsByBatchUpdate(sql + placeHolderString, auditFormFieldsSubList);
				placeHolderString = new StringBuffer();
				first = true;
				auditFormFieldsSubList = new ArrayList<AuditFormFields>();
			}
		}
		
		Log.info(getClass(), "auditFormFieldsByBatchUpdate() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		
	}
	
	public void auditFormFieldsByBatchUpdate(final String sql,
			final List<AuditFormFields> auditFormFields) 
	{
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < auditFormFields.size(); i++)
				{
					AuditFormFields auditFormField = auditFormFields.get(i);
					ps.setLong(placeHolder++, auditFormField.getAuditParent());
					ps.setLong(placeHolder++, auditFormField.getFieldId());
					ps.setLong(placeHolder++, auditFormField.getFormId());
					ps.setLong(placeHolder++, auditFormField.getFormSpecId());
					ps.setLong(placeHolder++, auditFormField.getFieldSpecId());
					ps.setString(placeHolder++, auditFormField.getFieldValue());
					ps.setString(placeHolder++, auditFormField.getCreatedTime());
					ps.setString(placeHolder++, auditFormField.getModifiedTime());
					ps.setLong(placeHolder++, auditFormField.getAuditBy());
					ps.setString(placeHolder++, auditFormField.getAuditAt());
				}
				return ps;
			}
		});
	}
	
	
	public List<AuditFormSectionFields> getFormSectionFieldsForAudit(
			long formId, long auditParent, long by, String time) {
		List<AuditFormSectionFields> auditSectionFormFields = jdbcTemplate.query(
				Sqls.SELECT_FORM_SECTION_FIELDS_FOR_AUDIT,
				new Object[] { auditParent, by, time, formId }, new BeanPropertyRowMapper<AuditFormSectionFields>(AuditFormSectionFields.class));
		return auditSectionFormFields;
	}
	
	public void auditFormSectionFieldsByBatchUpdate(List<AuditFormSectionFields> auditFormSectionFields)
	{
		Long currentInMillies = System.currentTimeMillis();
		List<AuditFormSectionFields> auditFormSectionFieldsSubList = new ArrayList<AuditFormSectionFields>();
		String sql = new String("INSERT INTO `FormSectionFieldsAuditLog` (`auditParent`, `sectionFieldId`, `formId`, `formSpecId`, `sectionSpecId`, `sectionFieldSpecId`, `instanceId`, `fieldValue`, `createdTime`, `modifiedTime`, `auditBy`, `auditAt`) VALUES");
		
		Integer placeHoldersGroupLimit = constants.getPlaceHoldersGroupLimit();
		StringBuffer placeHolderString = new StringBuffer();
		boolean first = true;
		
		for(int i =0; i < auditFormSectionFields.size(); i++)
		{
			if(first)
			{
				placeHolderString.append(" (?,?,?,?,?,?,?,?,?,?,?,?)");
				first = false;
			}
			else
			{
				placeHolderString.append(", (?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			auditFormSectionFieldsSubList.add(auditFormSectionFields.get(i));
			if(i % placeHoldersGroupLimit == 0 || i == (auditFormSectionFields.size()-1))
			{
				auditFormSectionFieldsByBatchUpdate(sql + placeHolderString, auditFormSectionFieldsSubList);
				placeHolderString = new StringBuffer();
				first = true;
				auditFormSectionFieldsSubList = new ArrayList<AuditFormSectionFields>();
			}
		}
		
		Log.info(getClass(), "auditFormSectionFieldsByBatchUpdate() // time taken to new insert : "+(System.currentTimeMillis() - currentInMillies));
		
	}
	
	public void auditFormSectionFieldsByBatchUpdate(final String sql,
			final List<AuditFormSectionFields> auditFormSectionFields) 
	{
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(sql);
				int placeHolder = 1;
				for(int i =0; i < auditFormSectionFields.size(); i++)
				{
					AuditFormSectionFields auditFormSectionField = auditFormSectionFields.get(i);
					ps.setLong(placeHolder++, auditFormSectionField.getAuditParent());
					ps.setLong(placeHolder++, auditFormSectionField.getSectionFieldId());
					ps.setLong(placeHolder++, auditFormSectionField.getFormId());
					ps.setLong(placeHolder++, auditFormSectionField.getFormSpecId());
					ps.setLong(placeHolder++, auditFormSectionField.getSectionSpecId());
					ps.setLong(placeHolder++, auditFormSectionField.getSectionFieldSpecId());
					ps.setLong(placeHolder++, auditFormSectionField.getInstanceId());
					ps.setString(placeHolder++, auditFormSectionField.getFieldValue());
					ps.setString(placeHolder++, auditFormSectionField.getCreatedTime());
					ps.setString(placeHolder++, auditFormSectionField.getModifiedTime());
					ps.setLong(placeHolder++, auditFormSectionField.getAuditBy());
					ps.setString(placeHolder++, auditFormSectionField.getAuditAt());
				}
				return ps;
			}
		});
	}
}

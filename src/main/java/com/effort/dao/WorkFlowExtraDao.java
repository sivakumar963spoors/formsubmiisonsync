package com.effort.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.effort.entity.CompanyRole;
import com.effort.entity.FormSpec;
import com.effort.entity.SystemRejectedFormsLog;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.WorkFlowRealData;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.entity.WorkflowEntityVisibilityCondition;
import com.effort.entity.WorkflowStage;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
@Repository
public class WorkFlowExtraDao {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public Integer updateWorkFlowFormStatusForform(WorkFlowFormStatus formStatus) {
		try {
			return jdbcTemplate.update(
					Sqls.UPDATE_WORK_FLOW_FORM_STATUS_FOR_FORM,
					new Object[] {
							formStatus.getId(),
							formStatus.getFormSpecId(),
							formStatus.getFormId(),
							formStatus.getFormIdentifier(),
							formStatus.getSubmittedBy(),
							formStatus.getSubmittedTime(),
							formStatus.getApprovedBy(),
							formStatus.getApprovedTime(),
							formStatus.getWorkFlowId(),
							formStatus.getWorkFlowStageId(),
							formStatus.getStatusMessage(),
							formStatus.getStatus(),
							formStatus.getCreatedTime(),
							formStatus.getModifiedTime(),
							formStatus.getCurrentRank(),
							formStatus.getNextRank(),
							formStatus.getPreviousRank(),
							formStatus.getPreviousStage(),
							formStatus.getEmpgroupId(),
							formStatus.getRemarks(),
							formStatus.getClientSideId(),
							formStatus.getClientCode(),
							formStatus.getAuditId(),
							formStatus.getManagerRank(),
							formStatus.getManagerCsvRanks(),
							formStatus.getTypeOfApproval(),
							formStatus.getId()
							 });
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public WorkFlowFormStatus getWorkFlowFormStatus(Long formId) {
		WorkFlowFormStatus workFlowFormStatus = null;
		try {
			workFlowFormStatus = jdbcTemplate.queryForObject(
					Sqls.SELECT_WORK_FLOW_FORM_STATUS, new Object[] { formId },
					new BeanPropertyRowMapper<WorkFlowFormStatus>(
							WorkFlowFormStatus.class));
		} catch (Exception e) {
			Log.info(getClass(), "getWorkFlowFormStatus //formId = "+formId);
		}
		return workFlowFormStatus;
	}
	public Integer insertWorkflowStatus(final WorkFlowFormStatus workFlowFormStatus) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		Integer count = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_WORK_FLOW_FORM_STATUS,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, workFlowFormStatus.getFormSpecId());
				ps.setLong(2, workFlowFormStatus.getFormId());
				ps.setString(3, workFlowFormStatus.getFormIdentifier());
				ps.setLong(4, workFlowFormStatus.getSubmittedBy());
				ps.setString(5, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setLong(6, workFlowFormStatus.getWorkFlowId());
				ps.setLong(7, workFlowFormStatus.getWorkFlowStageId());
				ps.setString(8, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(9, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				if (workFlowFormStatus.getPreviousRank() != null)
					ps.setInt(10, workFlowFormStatus.getPreviousRank());
				else
					ps.setNull(10, Types.INTEGER);
				if (workFlowFormStatus.getCurrentRank() != null)
					ps.setInt(11, workFlowFormStatus.getCurrentRank());
				else
					ps.setNull(11, Types.INTEGER);
				if (workFlowFormStatus.getNextRank() != null)
					ps.setInt(12, workFlowFormStatus.getNextRank());
				else
					ps.setNull(12, Types.INTEGER);

				ps.setString(13, workFlowFormStatus.getStatusMessage());
				if (workFlowFormStatus.getEmpgroupId() != null)
					ps.setString(14, workFlowFormStatus.getEmpgroupId());
				else
					ps.setNull(14, Types.VARCHAR);
				   
				if(!Api.isEmptyString(workFlowFormStatus.getClientSideId())){
				    ps.setString(15, workFlowFormStatus.getClientSideId());
				 }else{
					 ps.setNull(15, Types.VARCHAR);
				 }
				
				if(!Api.isEmptyString( workFlowFormStatus.getClientCode())){
				    ps.setString(16, workFlowFormStatus.getClientCode());
				 }else{
					 ps.setNull(16, Types.VARCHAR);
				 }
				if(workFlowFormStatus.getManagerRank()!=null){
				    ps.setLong(17, workFlowFormStatus.getManagerRank());
				 }else{
					 ps.setNull(17, Types.BIGINT);
				 }
				if(!Api.isEmptyString( workFlowFormStatus.getManagerCsvRanks())){
				    ps.setString(18, workFlowFormStatus.getManagerCsvRanks());
				 }else{
					 ps.setNull(18, Types.VARCHAR);
				 }
				
				ps.setShort(19, workFlowFormStatus.getStatus());
				if(workFlowFormStatus.getApprovedBy() != null){
				    ps.setLong(20, workFlowFormStatus.getApprovedBy());
				 }else{
					 ps.setNull(20, Types.BIGINT);
				}
				
				if(!Api.isEmptyString( workFlowFormStatus.getApprovedTime())){
				    ps.setString(21, workFlowFormStatus.getApprovedTime());
				 }else{
					 ps.setNull(21, Types.VARCHAR);
				}
				
				if (!Api.isEmptyString(workFlowFormStatus.getClientModifiedTime())) {
					ps.setString(22, workFlowFormStatus.getClientModifiedTime());
				} else {
					ps.setNull(22, Types.VARCHAR);
				}
				
			  return ps;
			}
		}, keyHolder);
		long id=keyHolder.getKey().longValue();
		workFlowFormStatus.setId(id);
		return count;
	}
	
	
	
    public Map<Long ,WorkflowStage> getWorkflowIdAndInitialStagesMap(String workflowIds){
    	Map<Long ,WorkflowStage>  workflowIdAndInitialStagesMap=new HashMap<Long, WorkflowStage>();
    	List<WorkflowStage> WorkflowStage =getWorkflowStagesForSync(workflowIds);
    	for (WorkflowStage workflowStage : WorkflowStage) {
    		workflowIdAndInitialStagesMap.put(workflowStage.getWorkflowId(),workflowStage);
		}
    	return  workflowIdAndInitialStagesMap;
    	
    }
    
    public List<WorkflowStage> getWorkflowStagesForSync(String workflowIds){
    	if(!Api.isEmptyString(workflowIds)){
    		String sql=Sqls.SELECT_WORKFLOW_STAGES_FOR_SYNC.replace(":workflowIds", workflowIds);
    		List<WorkflowStage> WorkflowStage=jdbcTemplate.query(sql, new BeanPropertyRowMapper<WorkflowStage>(WorkflowStage.class));
    	   return WorkflowStage;
    	}
    	return new ArrayList<WorkflowStage>();
    }
    
    public Long getAuditIfForWorkFlowHistory(Long formId) {
		Long auditId = null;
		try {
			auditId = jdbcTemplate.queryForLong(
					Sqls.SELECT_AUDIT_ID_FOR_WORKFLOW, new Object[] { formId });

		} catch (Exception e) {
			e.printStackTrace();
		}

		return auditId;
	}
    public WorkflowStage getWorkFlowStage(Long currentStage, Long workflowId,int mode) {
		WorkflowStage workflowStage=null;
		String query=null;
		if(mode==WorkflowStage.NEXT_STAGE){
			query=Sqls.SELECT_FOR_NEXT_STAGE;
		}else{
			query=Sqls.SELECT_FOR_PREVIOUS_STAGE;
		}
		
		
		try {
			workflowStage = jdbcTemplate.queryForObject(query,new Object[] { workflowId, currentStage },new BeanPropertyRowMapper<WorkflowStage>(WorkflowStage.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return workflowStage;
	}
    
    public String getRoleName(int rank,int companyId) {
    	String roleName = null;
    	if(rank!=0){
    	String sql = Sqls.SELECT_ROLENAME;
    	 roleName = jdbcTemplate.queryForObject(sql,
    		new Object[] { rank,companyId},String.class);
    	}
        	return roleName;
        }
        
    public Workflow getWorkFlow(Long id) {
		Workflow workFlow = null;
		try {
			workFlow = jdbcTemplate.queryForObject(Sqls.SELECT_WORK_FLOW,
					new Object[] { id }, new BeanPropertyRowMapper<Workflow>(
							Workflow.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlow;
	}
    public long insertWorkflowFormStatusHistory(
			final WorkFlowFormStatusHistory flowFormStatusHistory) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_WORK_FLOW_FORM_STATUS_HISTORY,
						Statement.RETURN_GENERATED_KEYS);

				ps.setLong(1, flowFormStatusHistory.getFormSpecId());
				ps.setLong(2, flowFormStatusHistory.getFormId());
				if (flowFormStatusHistory.getFormIdentifier() != null)
					ps.setString(3, flowFormStatusHistory.getFormIdentifier());
				else
					ps.setNull(3, Types.VARCHAR);

				ps.setLong(4, flowFormStatusHistory.getSubmittedBy());
				ps.setString(5, flowFormStatusHistory.getSubmittedTime());
				if (flowFormStatusHistory.getApprovedBy() != null)
					ps.setLong(6, flowFormStatusHistory.getApprovedBy());
				else
					ps.setNull(6, Types.BIGINT);
				if (flowFormStatusHistory.getApprovedTime() != null
						&& !flowFormStatusHistory.getApprovedTime().isEmpty())
					ps.setString(7, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
				else
					ps.setNull(7, Types.VARCHAR);
				ps.setLong(8, flowFormStatusHistory.getWorkFlowId());
				ps.setLong(9, flowFormStatusHistory.getWorkFlowStageId());

				if (flowFormStatusHistory.getStatusMessage() != null)
					ps.setString(10, flowFormStatusHistory.getStatusMessage());
				else
					ps.setNull(10, Types.VARCHAR);

				ps.setShort(11, flowFormStatusHistory.getStatus());
				ps.setString(12, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(13, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));

				if (flowFormStatusHistory.getCurrentRank() != null)
					ps.setLong(14, flowFormStatusHistory.getCurrentRank());
				else
					ps.setNull(14, Types.BIGINT);
				if (flowFormStatusHistory.getNextRank() != null)
					ps.setInt(15, flowFormStatusHistory.getNextRank());
				else
					ps.setNull(15, Types.INTEGER);
				if (flowFormStatusHistory.getPreviousRank() != null)
					ps.setInt(16, flowFormStatusHistory.getPreviousRank());
				else
					ps.setNull(16, Types.INTEGER);
				if (flowFormStatusHistory.getPreviousStage() != null)
					ps.setInt(17, flowFormStatusHistory.getPreviousStage());
				else
					ps.setNull(17, Types.INTEGER);
				if (flowFormStatusHistory.getEmpgroupId() != null)
					ps.setString(18, flowFormStatusHistory.getEmpgroupId());
				else
					ps.setNull(18, Types.VARCHAR);
				if (flowFormStatusHistory.getFormAuditId() != null)
					ps.setLong(19, flowFormStatusHistory.getFormAuditId());
				else
					ps.setNull(19, Types.BIGINT);
				if (flowFormStatusHistory.getRemarks() != null)
					ps.setString(20, flowFormStatusHistory.getRemarks());
				else
					ps.setNull(20, Types.VARCHAR);
				
				if (flowFormStatusHistory.getAuditId() != null)
					ps.setLong(21, flowFormStatusHistory.getAuditId());
				else
					ps.setNull(21, Types.BIGINT);
				
				if(flowFormStatusHistory.getManagerRank()!=null)
					ps.setLong(22, flowFormStatusHistory.getManagerRank());
				else
					ps.setNull(22, Types.BIGINT);
				if(flowFormStatusHistory.getManagerCsvRanks()!=null)
					ps.setString(23, flowFormStatusHistory.getManagerCsvRanks());
				else
					ps.setNull(23, Types.VARCHAR);
				
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		flowFormStatusHistory.setId(id);
		return id;
	}
    
    public Integer getNextRank(Integer companyId, Integer rank) {
		Integer nextRank = 0;
		try {
			nextRank = jdbcTemplate.queryForInt(Sqls.SELECT_FOR_NEXT_RANK,
					new Object[] { companyId, rank });

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nextRank;
	}
    public List<WorkFlowFormStatusHistory> getWorkFlowFormStatusesHistoryForSync(String formIds, String startDateForWorkFlowHistory){
	    String	sql =Sqls.SELECT_WORKFLOW_STATUS_HISTORIES_IN_SYNC;
	   
	    String extraCondition = "";
	    if(!Api.isEmptyString(formIds)){
	    	String condition="where wfsh.formId in ("+formIds+")";
	    	sql=sql.replace(":formIdsInCondion", condition);
	    	 if(!Api.isEmptyString(startDateForWorkFlowHistory))
	 	    {
	 	    	extraCondition = " AND wfsh.modifiedTime > '"+startDateForWorkFlowHistory+"'";
	 	    }
	    }else{
	    	sql=sql.replace(":formIdsInCondion", "where false");
	    }
	    
	    sql=sql.replace(":extraCondition",extraCondition);
	    
	 	 return jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<WorkFlowFormStatusHistory>(WorkFlowFormStatusHistory.class));
		}
	

	public CompanyRole getRoleForRank(final int companyId, Integer rank) {
		String sql = Sqls.SELECT_ROLE_FOR_RANK;
		CompanyRole role = null;
		try {
			role = jdbcTemplate.queryForObject(sql, new Object[] { companyId,
					rank }, new BeanPropertyRowMapper<CompanyRole>(
					CompanyRole.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}
	
	
	public List<WorkflowStage> getWorkFlowStages(String workflowId) {

		String sq1 = Sqls.SELECT_WORK_FLOW_STAGES.replace(":workFlowIds",
				workflowId);
		List<WorkflowStage> workFlowStages = jdbcTemplate.query(sq1,
				new BeanPropertyRowMapper<WorkflowStage>(WorkflowStage.class));
		return workFlowStages;

	}
	 public List<WorkFlowFormStatusHistory> getWorkFlowFormStatusHistoriesByFormIdAndWorkFlowId(WorkFlowFormStatus workFlowFormStatus) {
			List<WorkFlowFormStatusHistory> flowFormStatusHistories = new ArrayList<WorkFlowFormStatusHistory>();
			try {
				flowFormStatusHistories = jdbcTemplate.query(
						Sqls.SELECT_WORK_FLOW_FORM_STATUS_HISTORY_FORMID_WORKFLOWID,
						new Object[] { workFlowFormStatus.getFormId(),workFlowFormStatus.getWorkFlowId()},
						new BeanPropertyRowMapper<WorkFlowFormStatusHistory>(
								WorkFlowFormStatusHistory.class));

			} catch (Exception e) {
				e.printStackTrace();
			}

			return flowFormStatusHistories;
		}
	
	public Integer getPreviousRank(Integer companyId, Integer rank) {
		Integer previousRank = 0;
		try {
			previousRank = jdbcTemplate.queryForInt(
					Sqls.SELECT_FOR_PREVIOUS_RANK, new Object[] { companyId,
							rank });

		} catch (Exception e) {
			e.printStackTrace();
		}

		return previousRank;
	}
    public WorkFlowFormStatus getFormWorkflowStatus(String workflowStatusId){
      WorkFlowFormStatus workFlowFormStatus=  jdbcTemplate.queryForObject(Sqls.SELECT_WORK_FLOW_STATUS_WITH_WORKFLOW_ID,new Object[]{workflowStatusId},new BeanPropertyRowMapper<WorkFlowFormStatus>(WorkFlowFormStatus.class));	
      return  workFlowFormStatus;
       }
	public WorkflowStage getWorkFlowStage(Long id) {
		WorkflowStage workflowStage = null;
		try {
			workflowStage = jdbcTemplate.queryForObject(
					Sqls.SELECT_WORK_FLOW_STAGE, new Object[] { id },
					new BeanPropertyRowMapper<WorkflowStage>(
							WorkflowStage.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workflowStage;
	}
	
	public FormSpec getFormSpec(String formSpecId){
		  String sql = Sqls.SELECT_FORM_SPEC;
		 FormSpec formSpec= jdbcTemplate.queryForObject(sql, new Object[]{formSpecId},new BeanPropertyRowMapper<FormSpec>(FormSpec.class));
		 return formSpec;
	}
	
	public void updateWorkFlowFormStatusReject(
			final WorkFlowFormStatus workFlowFormStatus) {
		
		try {

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							Sqls.UPDATE_WORKFLOW_STATUS_REJECTED,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, workFlowFormStatus.getStatus());
					ps.setString(2, workFlowFormStatus.getStatusMessage());
					ps.setString(3, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())));
					ps.setLong(4, workFlowFormStatus.getId());
					ps.setLong(5, workFlowFormStatus.getFormId());

					return ps;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public WorkFlowRealData getWorkFlowRealData(Long formId) {
		String sql = Sqls.SELECT_FOR_WORKFLOW_REAL_DATA;
		WorkFlowRealData workFlowRealData = null;
		try {
			workFlowRealData = jdbcTemplate.queryForObject(sql,
					new Object[] { formId },
					new BeanPropertyRowMapper<WorkFlowRealData>(
							WorkFlowRealData.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workFlowRealData;
	}
	
	 public WorkflowStage getFirstStageForGivenStage(long workflowId) {
			try{
	    	    String sql = Sqls.SELECT_FIRST_STAGE_FOR_GIVEN_STAGE;
				Object[] objArray = new Object[]{workflowId};
				Log.info(getClass(), "displayQuery = "+Api.displayQuery(sql,objArray));
				return jdbcTemplate.queryForObject(sql, objArray,new BeanPropertyRowMapper<WorkflowStage>(WorkflowStage.class));
			}catch(Exception ex){
				
			}
	    	return null;
		}
	 
	 public WorkflowStage getWorkFlowStageWithId(Long stageId) {
			try {
				WorkflowStage workflowStage = jdbcTemplate.queryForObject(
						Sqls.SELECT_FOR_STAGE, new Object[] { stageId },
						new BeanPropertyRowMapper<WorkflowStage>(
								WorkflowStage.class));
				return workflowStage;
			} catch (Exception ex) {

			}
			return null;
		}
	 public List<WorkFlowFormStatus> getWorkFlowFormApprovalsByFormIds(String formIds, Integer companyId,Integer viewType) {
			List<WorkFlowFormStatus> flowFormStatus = null;
			try {
				//String sql = Sqls.SELECT_WORK_FLOW_APPROVAL_STATUS.replace(
				String sql = Sqls.SELECT_WORK_FLOW_APPROVAL_STATUS_NEW_BY_FORMIDS;
				sql = sql.replace(":companyId", companyId+"");
				sql = sql.replace(":formIds", formIds);
				if(viewType == 2) {
					sql = sql.replace(":workFlowStatus", "AND `wflow`.`status` IN (0,2)");
				}else if(viewType == 4){
					sql = sql.replace(":workFlowStatus", "AND `wflow`.`status` IN (-1)");
				}else if(viewType == 3){
					sql = sql.replace(":workFlowStatus", "AND `wflow`.`status` IN (1)");
				}else if(viewType == 5){
					sql = sql.replace(":workFlowStatus", "AND `wflow`.`status` IN (0,1,2)");
				}else {
					sql = sql.replace(":workFlowStatus", "");	
				}
				Log.info(getClass(), "getWorkFlowFormApprovals() // sql = "+sql);
				flowFormStatus = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<WorkFlowFormStatus>( WorkFlowFormStatus.class));

			} catch (Exception e) {
				e.printStackTrace();
			}

			return flowFormStatus;
		}
	 public List<Workflow> getWorkflowForFormSpecs(List<Long> formSpecIds,String syncDate) {
			if(Api.isEmptyString(syncDate)){
				syncDate="1970-01-01T00:00:00Z";
			}
			syncDate=Api.getDateTimeFromXsd(syncDate);
			List<Workflow> workflows = new ArrayList<Workflow>();
			if(formSpecIds!=null&&!formSpecIds.isEmpty()){
			    String sql=Sqls.SELECT_WORK_FLOW_FOR_FORM_SPECS.replace(":formspecIds", Api.toCSV(formSpecIds));
			    workflows = jdbcTemplate.query(sql,new Object[]{syncDate},new BeanPropertyRowMapper<Workflow>(Workflow.class));
			}
			return workflows;

		}
	 public List<WorkflowEntityMap> getWorkflowEntityMap(List<Long> formSpecIds,String syncDate) {
			if(Api.isEmptyString(syncDate)){
				syncDate="1970-01-01T00:00:00Z";
			}
			syncDate=Api.getDateTimeFromXsd(syncDate);
			
			List<WorkflowEntityMap> workflowEntityMap=new ArrayList<WorkflowEntityMap>();
			if(formSpecIds!=null&&!formSpecIds.isEmpty()){
			    String sql=Sqls.SELECT_WORKFLOWENTITYMAP.replace(":formSpecIds", Api.toCSV(formSpecIds));
			    workflowEntityMap = jdbcTemplate.query(sql,new Object[]{syncDate}, new  BeanPropertyRowMapper<WorkflowEntityMap>(WorkflowEntityMap.class));
			}
			return workflowEntityMap;

		}
	 public  List<WorkFlowFormStatus> getWorkflowFormStatus(String csvFormIds){
			if(!Api.isEmptyString(csvFormIds)){
			String sql=Sqls.SELECT_WORKFLOW_FORM_STATUS.replace(":formIds", csvFormIds);
			List<WorkFlowFormStatus> workFlowFormStatus=jdbcTemplate.query(sql, new BeanPropertyRowMapper<WorkFlowFormStatus>(WorkFlowFormStatus.class));
			return workFlowFormStatus;
			}
			return new ArrayList<WorkFlowFormStatus>();
			
		}
	 
		public WorkflowEntityMap getWorkflowEntityMap(String entityId, int type) {
			try{
			//return jdbcTemplate.queryForObject(Sqls.SELECT_WORKFLOWID_FROM_WORKFLOWENTITYMAP, new Object[]{entityId,type},Long.class);
				return jdbcTemplate.queryForObject(Sqls.SELECT_WORKFLOWENTITYMAP_FOR_ENTITYID, new Object[]{entityId,type},
						new BeanPropertyRowMapper<WorkflowEntityMap>(WorkflowEntityMap.class));
			}catch(Exception ex){
				
			}
			return null;
		}

		public Long getWorkflowIdIfExistFromWorkflowEntityMap(String entityId, int type) {
			try{
			return jdbcTemplate.queryForObject(Sqls.SELECT_WORKFLOWID_FROM_WORKFLOWENTITYMAP, new Object[]{entityId,type},Long.class);
				/*return jdbcTemplate.queryForObject(Sqls.SELECT_WORKFLOWID_FROM_WORKFLOWENTITYMAP, new Object[]{entityId,type},
						new BeanPropertyRowMapper<WorkflowEntityMap>(WorkflowEntityMap.class));*/
			}catch(Exception ex){
				
			}
			return null;
		}
		
		public WorkFlowFormStatus getWorkFlowFormStatusByFormIdOnly(long formId) {

			try {
				WorkFlowFormStatus workFlowFormStatus = jdbcTemplate.queryForObject(
						Sqls.SELECT_WORK_FLOW_FORM_STATUS_BY_FORM_ID, new Object[] { formId },
						new BeanPropertyRowMapper<WorkFlowFormStatus>(WorkFlowFormStatus.class));

				return workFlowFormStatus;
			} catch (Exception e) {
				Log.info(getClass(), "getWorkFlowFormStatusByFormIdOnly() // Exception occured ", e);
			}

			return null;
		}
		
		public int deleteWorkflowFormStatus(Integer companyId,String formId) {
			
			String sql = Sqls.DELETE_WORK_FLOW_FORM_STATUS;
			sql = sql.replace(":companyId", companyId+"").replace(":formId", formId+"");
			
			int deleted = jdbcTemplate.update(sql);
			return deleted;
		}
		
		public WorkFlowFormStatus getWorkFlowFormStatusByFormId(long formId) {

			try {
				WorkFlowFormStatus workFlowFormStatus = jdbcTemplate.queryForObject(
						Sqls.SELECT_WORK_FLOW_FORM_STATUS_FOR_FORM_ID, new Object[] { formId },
						new BeanPropertyRowMapper<WorkFlowFormStatus>(WorkFlowFormStatus.class));

				return workFlowFormStatus;
			} catch (Exception e) {
				Log.info(getClass(), "getWorkFlowFormStatusByFormId() // Exception occured ", e);
			}

			return null;
		}
		
		public List<WorkflowEntityMap> getWorkflowEntityMapByEntityIdAndType(String entityId, int type) {
			
			List<WorkflowEntityMap> workflowEntityMaps = new ArrayList<WorkflowEntityMap>();
			try{
			
				String sql = Sqls.SELECT_WORKFLOWENTITYMAP_BY_ENTTIYID_AND_TYPE;
				Object[] objArray = new Object[] {entityId,type};
				Log.info(getClass(), "displayQuery = "+Api.displayQuery(sql,objArray));
				workflowEntityMaps = jdbcTemplate.query(sql, 
						objArray,new BeanPropertyRowMapper<WorkflowEntityMap>(WorkflowEntityMap.class));
			}catch(Exception ex){
				
			}
			return workflowEntityMaps;
		}
		
		 public List<WorkflowEntityVisibilityCondition> getWorkflowEntityVisibilityConditionsByMappingIds(String mappingIds) {
				
				List<WorkflowEntityVisibilityCondition> workflowEntityMaps = new ArrayList<WorkflowEntityVisibilityCondition>();
				if(Api.isEmptyString(mappingIds)) {
					return workflowEntityMaps;
				}
				try{
				
					String sql = Sqls.SELECT_WORKFLOW_VISIBILITY_CONDITIONS_BY_MAPPING_IDS;
					sql = sql.replace(":workflowEntityMapIds", mappingIds);
					Log.info(getClass(), "sql = "+sql);
					workflowEntityMaps = jdbcTemplate.query(sql, 
							new BeanPropertyRowMapper<WorkflowEntityVisibilityCondition>(WorkflowEntityVisibilityCondition.class));
				}catch(Exception ex){
					
				}
				return workflowEntityMaps;
			}
		 
		 public long insertSystemRejectedFormsLogs(SystemRejectedFormsLog systemRejectedFormsLog,WebUser webUser) {
				
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
			  
			   PreparedStatement ps = connection.prepareStatement( Sqls.INSERT_INTO_SYSTEM_REJECTED_FORMS_LOGS, 
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, systemRejectedFormsLog.getCompanyId());
				ps.setLong(2, systemRejectedFormsLog.getFormId());
				ps.setLong(3, webUser.getEmpId());
				ps.setLong(4, webUser.getEmpId());
				ps.setString(5, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(6, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(7, systemRejectedFormsLog.getRemarks());
				return ps;
				}
			}, keyHolder);

			long id = keyHolder.getKey().longValue();
			return id;
			
		  }
		 
		 public List<WorkFlowFormStatusHistory> getWorkFlowFormStatusHistories(
					Long formId) {
				
				return jdbcTemplate.query(
							Sqls.SELECT_WORK_FLOW_FORM_STATUS_HISTORY,
							new Object[] { formId },
							new BeanPropertyRowMapper<WorkFlowFormStatusHistory>(
									WorkFlowFormStatusHistory.class));

				
			}

}

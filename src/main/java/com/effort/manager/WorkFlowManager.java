package com.effort.manager;

import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.context.AppContext;
import com.effort.dao.AuditDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.CompanyRole;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormRejectionStagesConfiguration;
import com.effort.entity.FormSpec;
import com.effort.entity.Plugin;
import com.effort.entity.SystemRejectedFormsLog;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.WorkFlowRealData;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.entity.WorkflowEntityVisibilityCondition;
import com.effort.entity.WorkflowStage;
import com.effort.exception.EffortError;
import com.effort.plugin.api.EffortWorkflowPluginService;
import com.effort.plugin.api.EffortWorkflowPluginServiceImpl;
import com.effort.plugin.api.WorkFlowPlugin;
import com.effort.plugin.engine.GenericPluginLoader;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ProductConstants;
import com.effort.settings.ServiceCallConstants;
import com.effort.util.Api;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
@Service
public class WorkFlowManager {
	@Autowired
	private AuditDao auditDao;
	
	@Autowired
	private  ExtraSupportDao extraSupportDao;
	
	@Autowired
	private ProductConstants productConstants;
	
	@Autowired
	private Constants constants;
	
	@Autowired
	private FormValidator formValidator;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private WorkFlowExtraDao workFlowExtraDao;
	
	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private WebManager webManager;
	
	@Autowired
	private ExtraDao extraDao;

	
	@Autowired
	EffortWorkflowPluginServiceImpl effortWorkflowPluginService;
	
	 
	@Autowired
	GenericPluginLoader genericPluginLoader;
	
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	


	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private WebAdditionalSupportManager getWebAdditionalSupportManager(){
		WebAdditionalSupportManager webAdditionalSupportManager = AppContext.getApplicationContext().getBean("webAdditionalSupportManager",WebAdditionalSupportManager.class);
		return webAdditionalSupportManager;
	}
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}
	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	
	private ContextUtils getContextUtils() {
		ContextUtils contextUtils = AppContext.getApplicationContext().getBean(
				"contextUtils", ContextUtils.class);
		return contextUtils;
	}
	
	private ExtendedDao getExtendedDao(){
		ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
		return extendedDao;
	}
	
	
	public String getStatusMessage(int status) {
		switch (status) {
		case -1:
			return "Rejected";
		case 2:
			return "Resubmitted";
		case 3:
			return "Cancelled";
		default:
			return "Waiting";
		}

	}
	
	public String getWorkFlowStatusMessage(
			WorkFlowFormStatus workFlowFormStatus, Integer companyId,
			Integer currentRank,String ipAddress, String oppUserName) {

		List<String> rankList = webManager.getRankListCsv(""
				+ workFlowFormStatus.getSubmittedBy());
		Integer nextRank = workFlowExtraDao.getNextRank(companyId, currentRank);
		String nextRankString = webManager.validateAndGetRank(rankList,
				nextRank.longValue(), WorkFlowFormStatus.NEXT_MANAGER_ROLE);
		nextRank = Integer.parseInt(nextRankString);

		Form form= webManager.getForm(workFlowFormStatus.getFormId()+"");
		FormSpec formSpec = webManager.getFormSpec(form.getFormSpecId()+"");
		String message = null;
		int stageType = 0;

		if (nextRank != 0) {
			short status = workFlowFormStatus.getStatus();
			if (status == 0) {
				CompanyRole companyRole = workFlowExtraDao.getRoleForRank(
						companyId, nextRank);
				message = "Waiting for " + companyRole.getRoleName()
						+ " Approval";
				
			} else if (status == 1) {
				WorkflowStage workflowStageInitial = getWorkFlowStage(workFlowFormStatus
						.getWorkFlowStageId());

				/*
				 * List<WorkflowStage> workflowStageList = workFlowExtraDao
				 * .getWorkFlowStages("" + workFlowFormStatus.getWorkFlowId());
				 */
				Integer finalApproverRank = 0;
				Integer approvalMode = 0;
				if (workflowStageInitial != null) {
					approvalMode = workflowStageInitial.getApprovalMode();
					finalApproverRank = workflowStageInitial
							.getFinalApproverRank();
					String finalApproverRankString = webManager
							.validateAndGetRank(rankList,
									finalApproverRank.longValue(),
									WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					finalApproverRank = Integer
							.parseInt(finalApproverRankString);
					stageType = workflowStageInitial.getStageType();
				}
				if (stageType == 1) {
					if (approvalMode == 1) {
						if (finalApproverRank.intValue() == workFlowFormStatus
								.getCurrentRank().intValue()) {
							message = "Approved";
						} else {
							CompanyRole companyRole = workFlowExtraDao
									.getRoleForRank(companyId, nextRank);
							if (companyRole != null) {
								message = "Waiting for "
										+ companyRole.getRoleName()
										+ " Approval";
							}
						}
					} else if (approvalMode == 2) {
						if (finalApproverRank.intValue() == workFlowFormStatus
								.getCurrentRank().intValue()) {
							message = "Approved";
						} else {
							String roleName = workFlowExtraDao.getRoleName(
									finalApproverRank, companyId);
							message = "Waiting for " + roleName;
						}
					} else if (approvalMode == 3) {
						if (finalApproverRank.intValue() == workFlowFormStatus
								.getCurrentRank().intValue()) {
							message = "Approved";
						} else {
							nextRankString = webManager.validateAndGetRank(
									rankList, workFlowFormStatus.getNextRank(),
									WorkFlowFormStatus.NEXT_MANAGER_ROLE);
							nextRank = Integer.parseInt(nextRankString);
							CompanyRole companyRole = workFlowExtraDao
									.getRoleForRank(companyId, nextRank);
							message = "Waiting for "
									+ companyRole.getRoleName() + " Approval";
						}
					}
				} else if (stageType == 2) {
					message = "Approved";
				}

			} else if (status == 2)
				message = "Resubmitted";
			else if (status == -1)
				message = "Rejected";
			else if (status == 3)
				message = "Cancelled";

		} else {
			short status = workFlowFormStatus.getStatus();
			if (status == 0) {
				message = "Waiting";
			}
			else if (status == 1){
				message = "Approved";
			}
			else if (status == 2)
				message = "Resubmitted";
			else if (status == -1)
				message = "Rejected";
			else if (status == 3)
				message = "Cancelled";

		}
		
	  if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_WAITING) {
			
		    WorkflowStage workflowStage = getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
			Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(workFlowFormStatus.getSubmittedBy()+"");
			
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				if (workflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					Long managerRank = employee.getManagerId();
					if (managerRank != null) {
						Employee managerEmployee = webManager.getEmployee("" + managerRank);
						message = "Waiting for "+ managerEmployee.getEmpName();
					}
				} 
			}
		}
			
		if(!Api.isEmptyString(message) &&  message.trim().toLowerCase().equals("approved")){
			if(constants.getFormSpecUniqueId_Of_Approval_form_to_call_rest_call_api().contains(formSpec.getUniqueId())){
				webManager.logFormAudit(form.getFormId(), form.getFilledBy(), form.getCompanyId(), JmsMessage.CHANGE_TYPE_MODIFY, 
						false,ipAddress,false,oppUserName);
//				webManager.sendJmsMessage(JmsMessage.TYPE_FORM, (long)-1, companyId, form.getFilledBy(), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(),
//					null, false);
			}
		}
		
		return message;

	}

	
	public void populateWorkflowFormStatusBasedOnApproveOrResubmit(
			WorkFlowFormStatus workFlowFormStatus,
			WorkflowStage currentWorkflowStage, int submitterRank,
			int companyId, int approvedOrResubmitFrom, long loginEmpId,
			 boolean endRuleType, boolean isSchedular, boolean isService) {
		Employee managerEmployeeId = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
				+ loginEmpId);

		if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
			
			if(WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL == currentWorkflowStage.getApprovalMode()) {
				// 
				WorkflowStage workflowStage = workFlowExtraDao
						.getWorkFlowStage(
								workFlowFormStatus.getWorkFlowStageId(),
								workFlowFormStatus.getWorkFlowId(),
								WorkflowStage.NEXT_STAGE);
				
				if(workFlowFormStatus.isNextStageEvaluated()) {
                      workflowStage = workFlowExtraDao.getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
                      currentWorkflowStage.setParallelApprovalRoleIds(workflowStage.getParallelApprovalRoleIds());
				}
				
				
				List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
				List<Integer> parllelRoleIds = Api.csvToIntegerList(currentWorkflowStage.getParallelApprovalRoleIds());
				Map<Integer,List<Employee>> managersRankMap = new HashMap<Integer, List<Employee>>();
				Map<Long,Integer> empIdRankMap = new HashMap<Long,Integer>();
				if(managers!=null && managers.size()>0)
				{
					managersRankMap = (Map) Api.getResolvedMapFromList(managers, "rank");
					empIdRankMap = (Map) Api.getMapFromList(managers,"empId","rank");
				}
				List<Employee> managersToApprove = new ArrayList<Employee>();
				for(Integer rank : parllelRoleIds) {
					if(managersRankMap.get(rank) != null) {
						managersToApprove.addAll(managersRankMap.get(rank));
					}
				}
				List<Long> managerIds = Api.listToLongList(managersToApprove, "empId");
				
				if(managerIds != null && managerIds.size() > 0) {
					
				int status = 1;
				List<Long> employeesWhoHasAlreadyApprovedOrRejected = getWebExtensionManager()
						.getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(workFlowFormStatus.getFormId(),
								workFlowFormStatus.getWorkFlowStageId(), Api.toCSV(managerIds), status,workFlowFormStatus);
				// 
				if (employeesWhoHasAlreadyApprovedOrRejected.size() < managerIds.size()) {
					WorkFlowFormStatus previousWorkFlowStatus = getFormWorkflowStatus(workFlowFormStatus.getId() + "");
					List<String> waitingForRoles = new ArrayList<String>();
					List<Integer> ranks = new ArrayList<Integer>();
					// 
					for(Long managerId : managerIds) {
						if(!employeesWhoHasAlreadyApprovedOrRejected.contains(managerId)) {
							if(empIdRankMap.get(managerId) != null && !ranks.contains(empIdRankMap.get(managerId))){
								ranks.add(empIdRankMap.get(managerId));
							}
						}
					}
					
					for(Integer rank : ranks) {
						String roleNameTemp = workFlowExtraDao.getRoleName(
									rank, companyId);
							waitingForRoles.add(roleNameTemp);
					}
					String statusMessage = "Waiting for "+Api.toCSV(waitingForRoles);
					
					workFlowFormStatus.setStatusMessage(statusMessage);
					workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
					return;
				}
			}else {
				workflowStage = workFlowExtraDao
						.getWorkFlowStage(
								workFlowFormStatus.getWorkFlowStageId(),
								workFlowFormStatus.getWorkFlowId(),
								WorkflowStage.NEXT_STAGE);
			}
				if (workflowStage != null && (!endRuleType || isSchedular)) {
					
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					moveToNextStage(workFlowFormStatus, workflowStage,
							companyId, submitterRank);
					if(!workFlowFormStatus.isNextStageEvaluated()) {
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ workflowStage.getStageName() + " Stage");
					}
					
				} else {
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					workFlowFormStatus
							.setStatus(WorkFlowFormStatus.STATUS_TYPE_APPROVED);
					if(isService)
						workFlowFormStatus.setStatusMessage("Approved by " +managerEmployeeId.getEmpName()+ " from Mail");
					else
					workFlowFormStatus.setStatusMessage("Approved");
				}
				
			}else if (workFlowFormStatus.getCurrentRank() <= currentWorkflowStage
					.getFinalApproverRank()) {
				WorkflowStage workflowStage = workFlowExtraDao
						.getWorkFlowStage(
								workFlowFormStatus.getWorkFlowStageId(),
								workFlowFormStatus.getWorkFlowId(),
								WorkflowStage.NEXT_STAGE);
				
				if(workFlowFormStatus.isNextStageEvaluated()) {
                    workflowStage = workFlowExtraDao.getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
                    currentWorkflowStage.setParallelApprovalRoleIds(workflowStage.getParallelApprovalRoleIds());
				}
				if (workflowStage != null && (!endRuleType || isSchedular)) {
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					moveToNextStage(workFlowFormStatus, workflowStage,
							companyId, submitterRank);
					workFlowFormStatus.setStatusMessage("Waiting for "
							+ workflowStage.getStageName() + " Stage");
				} else {
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					workFlowFormStatus
							.setStatus(WorkFlowFormStatus.STATUS_TYPE_APPROVED);
					if(isService)
						workFlowFormStatus.setStatusMessage("Approved by " +managerEmployeeId.getEmpName()+ " from Mail");
					else
					workFlowFormStatus.setStatusMessage("Approved");
				}
			} else {
				workFlowFormStatus.setEmpgroupId("-1");
				List<String> rankList = webManager.getRankListCsv(""
						+ workFlowFormStatus.getSubmittedBy());
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
					workFlowFormStatus.setPreviousRank(workFlowFormStatus
							.getCurrentRank());

					String currentRank = webManager.validateAndGetRank(
							rankList, workFlowFormStatus.getNextRank(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));

					int nextRank = workFlowExtraDao.getNextRank(companyId,
							workFlowFormStatus.getCurrentRank());
					workFlowFormStatus.setNextRank(nextRank);

				} else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
					workFlowFormStatus.setPreviousRank(workFlowFormStatus
							.getCurrentRank());
					List<String> rankList1 = webManager.getRankListCsv("" + loginEmpId);
					String currentRank = webManager.validateAndGetRank(rankList1,
							currentWorkflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus
							.setCurrentRank(Integer.parseInt(currentRank));
					String roleName = workFlowExtraDao.getRoleName(
							Integer.parseInt(currentRank), companyId);
					
					if (roleName != null) {
						workFlowFormStatus.setStatusMessage("Waiting for " + roleName);
					} else {
						roleName = workFlowExtraDao.getRoleName(
								currentWorkflowStage.getFinalApproverRank(), companyId);
						workFlowFormStatus.setStatusMessage("Waiting for " + roleName);
					}

				} else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
					workFlowFormStatus.setPreviousRank(workFlowFormStatus
							.getCurrentRank());
					String currentRank = webManager.validateAndGetRank(
							rankList, workFlowFormStatus.getNextRank(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					workFlowFormStatus.setNextRank(currentWorkflowStage
							.getFinalApproverRank());
				}
			}
		} else if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {

			if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED) {
				workFlowFormStatus.setStatusMessage("Resubmitted");
				workFlowFormStatus.setEmpgroupId(currentWorkflowStage
						.getEmpGroupIds());

				if (workFlowFormStatus.getSubmittedBy() != loginEmpId) {
					WorkflowStage workflowStage = workFlowExtraDao
							.getWorkFlowStage(
									workFlowFormStatus.getWorkFlowStageId(),
									workFlowFormStatus.getWorkFlowId(),
									WorkflowStage.NEXT_STAGE);
					if (workflowStage != null && (!endRuleType || isSchedular)) {
						executeWorkflowCustomCodeWhenRejectOrApprove(
								workFlowFormStatus.getFormId(),
								WorkFlowFormStatus.STATUS_TYPE_APPROVED,
								companyId,
								currentWorkflowStage.isCanExecuteCustomCode());
						moveToNextStage(workFlowFormStatus, workflowStage,
								companyId, submitterRank);
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ workflowStage.getStageName());
					}
				}
			} else {
				executeWorkflowCustomCodeWhenRejectOrApprove(
						workFlowFormStatus.getFormId(),
						WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
						currentWorkflowStage.isCanExecuteCustomCode());
				if(isService)
					workFlowFormStatus.setStatusMessage("Approved by " +managerEmployeeId.getEmpName()+ " from Mail");
				else
				workFlowFormStatus.setStatusMessage("Approved");
				workFlowFormStatus
						.setStatus(WorkFlowFormStatus.STATUS_TYPE_APPROVED);

				WorkflowStage workflowStage = workFlowExtraDao
						.getWorkFlowStage(
								workFlowFormStatus.getWorkFlowStageId(),
								workFlowFormStatus.getWorkFlowId(),
								WorkflowStage.NEXT_STAGE);
				if (workflowStage != null && (!endRuleType || isSchedular)) {
					moveToNextStage(workFlowFormStatus, workflowStage,
							companyId, submitterRank);
					if(!workFlowFormStatus.isNextStageEvaluated()) {
						workFlowFormStatus.setStatusMessage("Waiting for "+ workflowStage.getStageName());
						populateWorkFlowStatusMessageForHierachy(workFlowFormStatus);
					}
				}else {
					WorkflowStage currentStageWhichIsApproved = workFlowExtraDao
							.getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
					if (currentStageWhichIsApproved.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && currentStageWhichIsApproved.getConsiderEveryoneInGroup()==1) {
						String groupIds = currentStageWhichIsApproved.getEmpGroupIds();

						List<Long> empIds = extraDao.getEmployeesFromEmployeeGroup(groupIds);
						
						if(currentStageWhichIsApproved.getOnlyWithHierarchy() == 1) {
							List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
							if(managers != null && managers.size() > 0) {
								List<Long> managerIds = Api.listToLongList(managers, "empId");
								empIds.retainAll(managerIds);
							}
						}
						int groupEmpsCount = empIds.size();
						if(empIds.contains(workFlowFormStatus.getSubmittedBy())) {
							groupEmpsCount = groupEmpsCount-1;
						}
						int status = 1;
						List<Long> employeesWhoHasAlreadyApprovedOrRejected = getWebExtensionManager()
								.getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(workFlowFormStatus.getFormId(),
										workFlowFormStatus.getWorkFlowStageId(), Api.toCSV(empIds), status,workFlowFormStatus);
						int approvedCount = employeesWhoHasAlreadyApprovedOrRejected.size();
						if (employeesWhoHasAlreadyApprovedOrRejected == null || approvedCount < groupEmpsCount) {
							WorkFlowFormStatus previousWorkFlowStatus = getFormWorkflowStatus(workFlowFormStatus.getId() + "");
							workFlowFormStatus.setStatusMessage(previousWorkFlowStatus.getStatusMessage());
							workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
						}
					}
				}
			}
		} else if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
			Long managerId = workFlowFormStatus.getManagerRank();
			// List<String> managerList =
			// Api.csvToList(workFlowFormStatus.getManagerCsvRanks());
			// List<String> managerList = webManager.getManagerList("" +
			// workFlowFormStatus.getSubmittedBy());
			List<String> managerList = new ArrayList<String>();

			
			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
				Employee emp = webManager.getEmployee(""
						+ workFlowFormStatus.getSubmittedBy());
				managerList.add("" + emp.getEmpId());
				if (emp.getManagerId() != null) {
					managerList.add("" + emp.getManagerId());
				}
                if(workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED) {
                	managerId =  workFlowFormStatus.getSubmittedBy();
                }
			}
			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
				
				Integer approvalModeLevel = currentWorkflowStage.getApprovalModeLevel();
                managerList = webManager.getManagerList(""+ workFlowFormStatus.getSubmittedBy());
                int requiredLevel =  approvalModeLevel+1;
                if(requiredLevel >= managerList.size()) {
                	requiredLevel = managerList.size() - 1;
                }
                List<String> customLevelList = new ArrayList<String>();
				customLevelList.add(managerList.get(0));
				customLevelList.add(managerList.get(requiredLevel));
				managerId = Long.parseLong(managerList.get(requiredLevel));
				managerList.clear();
				managerList.addAll(customLevelList);
				if(workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED &&
	                		loginEmpId ==  workFlowFormStatus.getSubmittedBy().longValue()) {
	                	managerId =  workFlowFormStatus.getSubmittedBy();
	            }
			}
			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
				managerList = webManager.getManagerList(""
						+ workFlowFormStatus.getSubmittedBy());
			}
			String managerRank = webManager.getEntryFromList(managerList,
					managerId, WorkFlowFormStatus.NEXT_MANAGER_ROLE);
			
			if (Api.isEmptyString(managerRank)) {
				WorkflowStage workflowStage = workFlowExtraDao
						.getWorkFlowStage(
								workFlowFormStatus.getWorkFlowStageId(),
								workFlowFormStatus.getWorkFlowId(),
								WorkflowStage.NEXT_STAGE);
				if (workflowStage != null && (!endRuleType || isSchedular)) {
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					moveToNextStage(workFlowFormStatus, workflowStage,
							companyId, submitterRank);
					if(Api.isEmptyString(workFlowFormStatus.getStatusMessage()) ||
							workFlowFormStatus.getStatus() == 0) {
						populateStatusMessage(workFlowFormStatus, workflowStage);				
					}
				} else {
					executeWorkflowCustomCodeWhenRejectOrApprove(
							workFlowFormStatus.getFormId(),
							WorkFlowFormStatus.STATUS_TYPE_APPROVED, companyId,
							currentWorkflowStage.isCanExecuteCustomCode());
					workFlowFormStatus
							.setStatus(WorkFlowFormStatus.STATUS_TYPE_APPROVED);
					if(isService)
						workFlowFormStatus.setStatusMessage("Approved by " +managerEmployeeId.getEmpName()+ " from Mail");
					else
					workFlowFormStatus.setStatusMessage("Approved");
				}
			  } 
			else {
				// workFlowFormStatus.setEmpgroupId("-1");
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					workFlowFormStatus.setManagerRank(Long.parseLong(managerRank));
				}
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
					workFlowFormStatus.setManagerRank(Long.parseLong(managerRank));
				}
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
					workFlowFormStatus.setManagerRank(Long.parseLong(managerRank));
				}
				if (managerRank != null) {
					Employee managerEmployee = webManager.getEmployee(""
							+ managerRank);
					workFlowFormStatus.setStatusMessage("Waiting for "
							+ managerEmployee.getEmpName());
				}
			}
		}
		
		}
	

	public void populateStatusMessage(WorkFlowFormStatus workFlowFormStatus,WorkflowStage nextWorkflowStage) {
		
		Employee employee = webManager.getEmployee(""+ workFlowFormStatus.getSubmittedBy());
		
		if(nextWorkflowStage != null) {
			
			if (nextWorkflowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
					String statusMessage = getWorkFlowStatusMessageForEmpGroup(workFlowFormStatus);
					workFlowFormStatus.setStatusMessage(statusMessage);
			}else if (nextWorkflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED){
				String statusMessage = getWorkFlowStatusMessage(workFlowFormStatus,employee.getCompanyId(),employee.getRank(),null,null);
				workFlowFormStatus.setStatusMessage(statusMessage);
			}
		
		}

	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void insertOrUpdateWorkFlowFormStatus(
			WorkFlowRealData workFlowRealData, WebUser webUser,
			Employee employee, WorkFlowFormStatus workFlowFormStatus,
			WorkFlowFormStatus workFlowFormStatusOld,boolean isSchedular,boolean isService) {
		try {

		    workFlowFormStatus.setApprovedBy(webUser.getEmpId());
			WorkflowStage workflowStageInitial = getWorkFlowStage(workFlowFormStatus
					.getWorkFlowStageId());
			
			Integer stageType = 0, approvalMode = 0, finalApproverRank = 0;
			String empIds = null;
			stageType = workflowStageInitial.getStageType();
			
			Employee employee2 = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
					+ workFlowFormStatus.getSubmittedBy().intValue());
			int submitterRank = employee2.getRank();
			
			List<WorkflowStage> workflowStageList = workFlowExtraDao
					.getWorkFlowStages("" + workFlowFormStatus.getWorkFlowId());
			
			WorkflowStage firstStage = workflowStageList.get(0);
			
			if(workFlowFormStatus.getStatus() == 1) {
				try {
					WorkflowStage workflowStage = getExtendedDao().getWorkFlowNextStage(workflowStageInitial.getWorkflowId()+"",workFlowFormStatus
							.getWorkFlowStageId());
					
					boolean gotoNextLevel = true;
					if (stageType == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
						String groupIds = workflowStageInitial.getEmpGroupIds();

						List<Long> groupEmpIds = extraDao.getEmployeesFromEmployeeGroup(groupIds);
						
						if(workflowStageInitial.getOnlyWithHierarchy() == 1) {
							List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
							if(managers != null && managers.size() > 0) {
								List<Long> managerIds = Api.listToLongList(managers, "empId");
								groupEmpIds.retainAll(managerIds);
							}
						}
						int groupEmpsCount = groupEmpIds.size();
						if(groupEmpIds.contains(workFlowFormStatus.getSubmittedBy())) {
							groupEmpsCount = groupEmpsCount-1;
						}
						int status = 1;
						List<Long> employeesWhoHasAlreadyApprovedOrRejected = getWebExtensionManager()
								.getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(workFlowFormStatus.getFormId(),
										workFlowFormStatus.getWorkFlowStageId(), Api.toCSV(groupEmpIds), status,workFlowFormStatus);
						if (employeesWhoHasAlreadyApprovedOrRejected.size() < groupEmpsCount) {
							gotoNextLevel = false;
						}
					}else if(workflowStageInitial.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED && WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL == workflowStageInitial.getApprovalMode()) {
						List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
						List<Integer> parllelRoleIds = Api.csvToIntegerList(workflowStageInitial.getParallelApprovalRoleIds());
						Map<Integer,List<Employee>> managersRankMap = new HashMap<Integer, List<Employee>>();
						if(managers!=null && managers.size()>0)
						{
							managersRankMap = (Map) Api.getResolvedMapFromList(managers, "rank");
						}
						List<Employee> managersToApprove = new ArrayList<Employee>();
						for(Integer rank : parllelRoleIds) {
							if(managersRankMap.get(rank) != null) {
								managersToApprove.addAll(managersRankMap.get(rank));
							}
						}
						List<Long> managerIds = Api.listToLongList(managersToApprove, "empId");
						
						int status = 1;
						List<Long> employeesWhoHasAlreadyApprovedOrRejected = getWebExtensionManager()
								.getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(workFlowFormStatus.getFormId(),
										workFlowFormStatus.getWorkFlowStageId(), Api.toCSV(managerIds), status,workFlowFormStatus);
						if (employeesWhoHasAlreadyApprovedOrRejected.size() < managerIds.size()) {
							gotoNextLevel = false;
						}
						
					}
					
					if(gotoNextLevel && workflowStage != null && workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED && stageType != workflowStage.getStageType()) {
						workFlowFormStatus.setWorkFlowStageId(workflowStage.getStageId());
						stageType = workflowStage.getStageType();
						workflowStage.setStageId(workflowStageInitial.getStageId());
						workflowStageInitial = workflowStage;
						workFlowFormStatus.setStageType(Long.parseLong(stageType+""));
						workFlowFormStatus.setNextStageEvaluated(true);
						
						if(workFlowFormStatus.getCurrentRank() == null) {
							workFlowFormStatus.setCurrentRank(submitterRank);
						}
						if(workFlowFormStatus.getNextRank() == null) {
							workFlowFormStatus.setNextRank(workflowStage
									.getFinalApproverRank());
						}
						
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			approvalMode = workflowStageInitial.getApprovalMode();
			finalApproverRank = workflowStageInitial.getFinalApproverRank();
			empIds = workflowStageInitial.getEmpGroupIds();

			
			Workflow workflow = getWorkFlow(workFlowFormStatus.getWorkFlowId());
			
			if(workFlowFormStatusOld != null && workFlowFormStatus.getWorkFlowId().longValue() != workFlowFormStatusOld.getWorkFlowId().longValue()) {
				boolean reinitiated = getWebAdditionalSupportManager().reinitateFormApprovals(workFlowFormStatus.getFormId()+"", null,true,workFlowFormStatus.getWorkFlowId(),false);
				Log.info(getClass(), "insertOrUpdateWorkFlowFormStatus() // as workFlow formId changed detected, reintiating for formId = "+workFlowFormStatus.getFormId()
				  +" reinitiation status = "+reinitiated+" old workflowId = "+workFlowFormStatusOld.getWorkFlowId()+" new workflowId = "+workFlowFormStatus.getWorkFlowId());
				workFlowFormStatus.setTriggerJms(false);
				return;
			}
			
			if(workFlowFormStatusOld != null && workFlowFormStatusOld.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_APPROVED ) {
				if(workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED && 
						workFlowFormStatus.getSubmittedBy() == employee.getEmpId() && workflow.isReintiateApprovedFormAfterModify()) {
					boolean reinitiated = getWebAdditionalSupportManager().reinitateFormApprovals(workFlowFormStatus.getFormId()+"", null,true,workFlowFormStatus.getWorkFlowId(),false);
					Log.info(getClass(), "insertOrUpdateWorkFlowFormStatus() // reintiating for formId = "+workFlowFormStatus.getFormId()+" reinitiation status = "+reinitiated);
					workFlowFormStatus.setTriggerJms(false);
					return;
				}
			}
			
			if (stageType == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
				if (workFlowFormStatus.getStatus() == 1
						|| (workFlowFormStatus.getStatus() == 2)) {
					if (workFlowFormStatus.getStatus() == 2) {

						workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
						workFlowFormStatus.setSubmittedBy(employee.getEmpId());
						workFlowFormStatus.setWorkFlowStageId(firstStage.getStageId());
						
						webManager.populateWorkflowFormStatus(workFlowFormStatus,
								"" + workFlowFormStatus.getFormId(), webUser.getCompanyId(),
								firstStage, employee, webUser.getEmpId());
						
						List<Long> stageIdsToIgnore = new ArrayList<Long>();
						for(WorkflowStage stage : workflowStageList) {
							if(stage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && stage.getApprovalType() == 1) {
								stageIdsToIgnore.add(stage.getStageId());
							}
						}
						getExtendedDao().updateWorkFlowFormStatusInvalidateHistory(workFlowFormStatus.getFormId(),Api.toCSV(stageIdsToIgnore));
					} else {
						workFlowFormStatus
								.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
						populateWorkflowFormStatusBasedOnApproveOrResubmit(
								workFlowFormStatus, workflowStageInitial,
								submitterRank, webUser.getCompanyId(),
								WorkflowStage.STAGE_TYPE_ROLE_BASED,
								webUser.getEmpId(),workFlowRealData.isEndRuleType(), isSchedular,isService);
					}

				} else if (workFlowFormStatus.getStatus() == -1) {
					populateWorkflowFormStatusBasedOnReject(workFlowFormStatus,
							submitterRank, webUser.getCompanyId(),
							WorkflowStage.STAGE_TYPE_ROLE_BASED,
							workflowStageInitial,isService);
				}
				else if (workFlowFormStatus.getStatus() == 0) {
					populateWorkflowFormStatusBasedOnApproveOrResubmit(workFlowFormStatus, workflowStageInitial,
						submitterRank, webUser.getCompanyId(),
						WorkflowStage.STAGE_TYPE_ROLE_BASED,
						webUser.getEmpId(),workFlowRealData.isEndRuleType(),isSchedular, isService);
				}
			} else if (stageType == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
				if (workFlowFormStatus.getStatus() == 1
						|| workFlowFormStatus.getStatus() == 2) {
					if (workFlowFormStatus.getStatus() == 2) {

						workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED);
						workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
						workFlowFormStatus.setSubmittedBy(employee.getEmpId());
						workFlowFormStatus.setWorkFlowStageId(firstStage.getStageId());

						webManager.populateWorkflowFormStatus(workFlowFormStatus,
								"" + workFlowFormStatus.getFormId(), webUser.getCompanyId(),
								firstStage, employee, webUser.getEmpId());

						List<Long> stageIdsToIgnore = new ArrayList<Long>();
						for(WorkflowStage stage : workflowStageList) {
							if(stage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && stage.getApprovalType() == 1) {
								stageIdsToIgnore.add(stage.getStageId());
							}
						}
						getExtendedDao().updateWorkFlowFormStatusInvalidateHistory(workFlowFormStatus.getFormId(),Api.toCSV(stageIdsToIgnore));
					} else {
						workFlowFormStatus
								.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
						populateWorkflowFormStatusBasedOnApproveOrResubmit(
								workFlowFormStatus, workflowStageInitial,
								submitterRank, webUser.getCompanyId(),
								WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED,
								webUser.getEmpId(),workFlowRealData.isEndRuleType(), isSchedular,isService);
					}
					
				} else if (workFlowFormStatus.getStatus() == -1) {
					populateWorkflowFormStatusBasedOnReject(workFlowFormStatus,
							submitterRank, webUser.getCompanyId(),
							WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED,
							workflowStageInitial,isService);
				}
				else if (workFlowFormStatus.getStatus() == 0) {
					populateWorkflowFormStatusBasedResubmissionWithModifyData(workFlowFormStatus, workflowStageInitial,
						submitterRank, webUser.getCompanyId(),
						WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED,
						webUser.getEmpId());
				}
			} else if (stageType == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				if (workFlowFormStatus.getStatus() == 1
						|| workFlowFormStatus.getStatus() == 2) {
					if (workFlowFormStatus.getStatus() == 2) {
						workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED);
						workFlowFormStatus.setApprovedBy(workFlowFormStatusOld.getApprovedBy());
					} else {
						workFlowFormStatus
								.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
					}
					populateWorkflowFormStatusBasedOnApproveOrResubmit(
							workFlowFormStatus, workflowStageInitial,
							submitterRank, webUser.getCompanyId(),
							WorkflowStage.STAGE_TYPE_HIERARCHY_BASED,
							webUser.getEmpId(),workFlowRealData.isEndRuleType(),isSchedular,isService);
				} else if (workFlowFormStatus.getStatus() == -1) {
					populateWorkflowFormStatusBasedOnReject(workFlowFormStatus,
							submitterRank, webUser.getCompanyId(),
							WorkflowStage.STAGE_TYPE_HIERARCHY_BASED,
							workflowStageInitial,isService);
				}
				else if (workFlowFormStatus.getStatus() == 0) {
					populateWorkflowFormStatusBasedResubmissionWithModifyData(workFlowFormStatus, workflowStageInitial,
						submitterRank, webUser.getCompanyId(),
						WorkflowStage.STAGE_TYPE_HIERARCHY_BASED,
						webUser.getEmpId());
				}
			}

			String currentTime = Api.getDateTimeInUTC(new Date(System
					.currentTimeMillis()));
			workFlowFormStatus.setModifiedTime(currentTime);
			workFlowFormStatus.setApprovedTime(currentTime);
			workFlowExtraDao.updateWorkFlowFormStatusForform(workFlowFormStatus);
			
			if(workFlowFormStatus.getStatus() == 2 || workFlowFormStatus.getStatus() == 3) {
				
				//invalidating.... previous history....
				List<Long> stageIdsToIgnore = new ArrayList<Long>();
				for(WorkflowStage stage : workflowStageList) {
					if(stage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && stage.getApprovalType() == 1) {
						stageIdsToIgnore.add(stage.getStageId());
					}
				}
				getExtendedDao().updateWorkFlowFormStatusInvalidateHistory(workFlowFormStatus.getFormId(),Api.toCSV(stageIdsToIgnore));
				
			}
			if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_APPROVED) {
				FormSpec formSpec = webManager.getFormSpec(workFlowFormStatus
						.getFormSpecId() + "");

				if (constants.getGspCropFormSpecUniqueId().equals(
						formSpec.getUniqueId())) {
					if(constants.isGspCropFollowingWorkflow()){
					Log.info(this.getClass(), "calling processGspCropForm for formId:"+workFlowFormStatus.getFormId());
					webManager.processGspCropForm(workFlowFormStatus.getFormId());
					}
				}
				
				if(productConstants.getIsService()){
					Form form = webManager.getForm(workFlowFormStatus.getFormId()+"");
					FormSpec formSpecForService = getFormSpec("" + form.getFormSpecId());
					WorkflowEntityMap workflowEntityMap = workFlowExtraDao.getWorkflowEntityMap(formSpecForService.getUniqueId(),
									WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);
					if(workflowEntityMap!=null && workflowEntityMap.getIsEnabled() == 1){
						ServiceCallConstants serviceCallConstants = webManager.getServiceCallConstants(form.getCompanyId());
						webManager.updateFormFieldValueOfCustomer(form, serviceCallConstants);
						
					}
				}
				
				if(formSpec.getSkeletonFormSpecId() != null && formSpec.getSkeletonFormSpecId()==constants.getSales_new_leads_form_spec_id().longValue()){
					webManager.processNewLeadFormToCreateCustomer(workFlowFormStatus.getFormId(), formSpec.getCompanyId(),webUser);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateWorkFlowFormStatusReject(
			WorkFlowFormStatus workFlowFormStatus) {
		workFlowExtraDao.updateWorkFlowFormStatusReject(workFlowFormStatus);
	}
	public WorkFlowRealData getWorkFlowRealData(Long formId) {
		return workFlowExtraDao.getWorkFlowRealData(formId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Long insertOrUpdateWorkFlowFormStatusHistory(Employee employee,
			WebUser webUser, WorkFlowFormStatus workFlowFormStatus, Long empId,boolean isService) {

		WorkFlowFormStatusHistory flowFormStatusHistory = new WorkFlowFormStatusHistory();
		Long workFlowStatusHistoryId = null;

		try {

			// flowFormStatusHistory.setId(workFlowFormStatus.getId());
			flowFormStatusHistory.setFormSpecId(workFlowFormStatus
					.getFormSpecId());
			flowFormStatusHistory.setFormId(workFlowFormStatus.getFormId());
			flowFormStatusHistory.setFormIdentifier(workFlowFormStatus
					.getFormIdentifier());
			flowFormStatusHistory.setSubmittedBy(workFlowFormStatus
					.getSubmittedBy());
			flowFormStatusHistory.setSubmittedTime(workFlowFormStatus
					.getSubmittedTime());
			if(isService) {
				flowFormStatusHistory.setApprovedBy(empId);

			}else {
			flowFormStatusHistory.setApprovedBy(employee.getEmpId());
			}
			flowFormStatusHistory.setApprovedTime(Api
					.getDateTimeInUTC(new Date(System.currentTimeMillis())));
			flowFormStatusHistory.setWorkFlowId(workFlowFormStatus
					.getWorkFlowId());
			flowFormStatusHistory.setWorkFlowStageId(workFlowFormStatus
					.getWorkFlowStageId().longValue());
			flowFormStatusHistory.setStatusMessage(workFlowFormStatus
					.getStatusMessage());
			flowFormStatusHistory.setStatus(workFlowFormStatus.getStatus());
			flowFormStatusHistory.setCurrentRank(workFlowFormStatus
					.getCurrentRank());
			flowFormStatusHistory.setNextRank(workFlowFormStatus.getNextRank());
			flowFormStatusHistory.setPreviousRank(workFlowFormStatus
					.getPreviousRank());
			/*
			 * flowFormStatusHistory.setNextRank(workFlowExtraDao.getNextRank(
			 * webUser.getCompanyId(), employee.getRank()));
			 * flowFormStatusHistory
			 * .setPreviousRank(workFlowExtraDao.getPreviousRank(
			 * webUser.getCompanyId(), employee.getRank()));
			 */
			flowFormStatusHistory.setPreviousStage(workFlowFormStatus
					.getWorkFlowStageId().intValue());
			flowFormStatusHistory.setEmpgroupId(workFlowFormStatus
					.getEmpgroupId());
			flowFormStatusHistory.setFormAuditId(workFlowExtraDao
					.getAuditIfForWorkFlowHistory(workFlowFormStatus
							.getFormId()));
			flowFormStatusHistory.setRemarks(workFlowFormStatus.getRemarks());
			flowFormStatusHistory.setAuditId(workFlowFormStatus.getAuditId());
			// if (flowFormStatusHistory.getId() != null) {
			// workFlowExtraDao.updateWorkFlowFormStatus(flowFormStatusHistory);
			workFlowStatusHistoryId = workFlowExtraDao
					.insertWorkflowFormStatusHistory(flowFormStatusHistory);
			// } else {
			//
			// }

			workFlowFormStatus.setAuditId(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlowStatusHistoryId;

	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public WorkflowStage getWorkFlowStage(Long workFlowStageId) {
		WorkflowStage workFlowStage = null;
		try {
			workFlowStage = workFlowExtraDao.getWorkFlowStage(workFlowStageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlowStage;
	}
	
	public void moveToNextStage(WorkFlowFormStatus workFlowFormStatus,
			WorkflowStage workflowStage, int companyId, int submitterRank) {
		// check the previous stage 
		WorkflowStage currentStageWhichIsApproved = workFlowExtraDao
				.getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
		if (currentStageWhichIsApproved.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && currentStageWhichIsApproved.getConsiderEveryoneInGroup()==1) {
			String groupIds = currentStageWhichIsApproved.getEmpGroupIds();

			List<Long> empIds = extraDao.getEmployeesFromEmployeeGroup(groupIds);
			
			if(currentStageWhichIsApproved.getOnlyWithHierarchy() == 1) {
				List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
				if(managers != null && managers.size() > 0) {
					List<Long> managerIds = Api.listToLongList(managers, "empId");
					empIds.retainAll(managerIds);
				}
			}
			int groupEmpsCount = empIds.size();
			if(empIds.contains(workFlowFormStatus.getSubmittedBy())) {
				groupEmpsCount = groupEmpsCount-1;
			}
			int status = 1;
			List<Long> employeesWhoHasAlreadyApprovedOrRejected = getWebExtensionManager()
					.getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(workFlowFormStatus.getFormId(),
							workFlowFormStatus.getWorkFlowStageId(), Api.toCSV(empIds), status,workFlowFormStatus);
			int approvedCount = employeesWhoHasAlreadyApprovedOrRejected.size();
			if (employeesWhoHasAlreadyApprovedOrRejected == null || approvedCount < groupEmpsCount) {
				WorkFlowFormStatus previousWorkFlowStatus = getFormWorkflowStatus(workFlowFormStatus.getId() + "");
				workFlowFormStatus.setStatusMessage(previousWorkFlowStatus.getStatusMessage());
				workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
				return;
			}
		}
		if (workflowStage != null) {
			
			List<String> rankList = webManager.getRankListCsv(""
					+ workFlowFormStatus.getSubmittedBy());
			workFlowFormStatus.setEmpgroupId("-1");
			workFlowFormStatus.setManagerRank(null);
			workFlowFormStatus.setNextRank(null);
			workFlowFormStatus.setWorkFlowStageId(workflowStage.getStageId());
			workFlowFormStatus
					.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);
			
			
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
				if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
					if (submitterRank <= workflowStage.getFinalApproverRank()) {
						workFlowFormStatus.setPreviousRank(submitterRank);
						workFlowFormStatus.setCurrentRank(submitterRank);
						workFlowFormStatus.setNextRank(null);

					} else {
						String currentRank = webManager.validateAndGetRank(
								rankList, workFlowExtraDao.getNextRank(
										companyId, submitterRank),
								WorkFlowFormStatus.NEXT_MANAGER_ROLE);
						workFlowFormStatus.setCurrentRank(Integer
								.parseInt(currentRank));
						workFlowFormStatus.setPreviousRank(submitterRank);
						int nextRank = workFlowExtraDao.getNextRank(companyId,
								Integer.parseInt(currentRank));
						workFlowFormStatus.setNextRank(nextRank);
					}
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
					workFlowFormStatus.setPreviousRank(submitterRank);
					String currentRank = webManager.validateAndGetRank(
							rankList, workflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
					if (submitterRank <= workflowStage.getFinalApproverRank()) {
						workFlowFormStatus.setPreviousRank(submitterRank);
						workFlowFormStatus.setCurrentRank(submitterRank);
						workFlowFormStatus.setNextRank(null);

					} else {
						workFlowFormStatus.setPreviousRank(submitterRank);
						// int currentRank =
						// workFlowExtraDao.getNextRank(companyId,submitterRank);
						String currentRank = webManager.validateAndGetRank(
								rankList, workFlowExtraDao.getNextRank(
										companyId, submitterRank),
								WorkFlowFormStatus.NEXT_MANAGER_ROLE);
						workFlowFormStatus.setCurrentRank(Integer
								.parseInt(currentRank));
						workFlowFormStatus.setNextRank(workflowStage
								.getFinalApproverRank());
					}
				}else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL) {
					// here need find the roles..
					workFlowFormStatus.setPreviousRank(submitterRank);
					workFlowFormStatus.setCurrentRank(submitterRank);
					int submittedEmpRank = submitterRank;
					String roleName = null;
					List<Integer> approverRoles = Api.csvToIntegerList(workflowStage.getParallelApprovalRoleIds());
					List<Integer> waitingForRoles = new ArrayList<Integer>();
					if(approverRoles != null && approverRoles.size() > 0) {
						for(Integer approverRole : approverRoles) {
							if(approverRole < submittedEmpRank) {
								waitingForRoles.add(approverRole);
							}
						}
					}
					for(Integer waitingForRole : waitingForRoles) {
						String roleNameTemp = workFlowExtraDao.getRoleName(
								waitingForRole, companyId);
						if(!Api.isEmptyString(roleName)) {
							roleName = roleName+","+roleNameTemp;
						}else {
							roleName = roleNameTemp;
						}
					}
					
					if (roleName != null) {
						workFlowFormStatus.setStatusMessage("Waiting for " + roleName);
						workFlowFormStatus.setNextStageEvaluated(true);
					}
					
				}
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
				workFlowFormStatus
						.setEmpgroupId(workflowStage.getEmpGroupIds());
				workFlowFormStatus.setPreviousRank(null);
				workFlowFormStatus.setCurrentRank(null);
				workFlowFormStatus.setNextRank(null);
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
						+ workFlowFormStatus.getSubmittedBy().intValue());
				if (workflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					Long managerRank = employee.getManagerId();
					workFlowFormStatus.setManagerRank(managerRank);
					List<String> managerList = new ArrayList<String>();
					managerList.add("" + employee.getEmpId());
					managerList.add("" + managerRank);
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = webManager.getEmployee(""
								+ managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ managerEmployee.getEmpName());
					}
				} else if (workflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
					List<String> managerList = webManager.getManagerList(""
							+ employee.getEmpId());
					String managerRank = webManager.getEntryFromList(
							managerList, employee.getEmpId(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setManagerRank(Long
							.parseLong(managerRank));
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = webManager.getEmployee(""
								+ managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ managerEmployee.getEmpName());
					}
				}else if (workflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
					
					Integer approvalModeLevel = workflowStage.getApprovalModeLevel();
	                List<String> managerList = webManager.getManagerList(""+ employee.getEmpId());
	                int requiredLevel =  approvalModeLevel+1;// we need to add +1 because managerList contain sumbitter also
	                if(requiredLevel >= managerList.size()) {
	                	requiredLevel = managerList.size() - 1;
	                }
	                List<String> customLevelList = new ArrayList<String>();
					customLevelList.add(managerList.get(0));
					customLevelList.add(managerList.get(requiredLevel));
					Long managerRank = Long.parseLong(managerList.get(requiredLevel));
					
					managerList.clear();
					managerList.addAll(customLevelList);
					
					workFlowFormStatus.setManagerRank(managerRank);
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = webManager.getEmployee(""+ managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "+ managerEmployee.getEmpName());
					}
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
				}
			}
		}
	}

	
	
	public String getWorkFlowStatusMessageForEmpGroup(WorkFlowFormStatus workflowFormStatus){
		
		String logText = "getWorkFlowStatusMessageForEmpGroup() // formId = "+workflowFormStatus.getFormId()+" ";
		String workFlowStatusMessage = workflowFormStatus.getStatusMessage();
	try{
		//1.Validatations =========================
	    WorkflowStage workflowStage = workFlowExtraDao.getWorkFlowStage(workflowFormStatus.getWorkFlowStageId());
	    if(workflowStage == null){
	    	
	    	Log.info(getClass(), logText+" No workflowStage found.");
	    	return workFlowStatusMessage;
	    }
	    
	    if (workflowStage.getStageType() != WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
	    	Log.info(getClass(), logText+" This is not STAGE_TYPE_EMPLOYEE_GROUP_BASED . ");
	    	return workFlowStatusMessage;
	    }
	    
	    if(workflowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_WAITING){
	    	Log.info(getClass(), logText+" workflowFormStatus Id "+workflowFormStatus.getId()+" formId = "
	    			+workflowFormStatus.getFormId()+" not in waiting status");
	    	return workFlowStatusMessage;
	    }
	    
	    workflowFormStatus.setConsiderEveryoneInGroup(workflowStage.getConsiderEveryoneInGroup());
	    //2. Getting work flow stage employee groups =======
		List<EmployeeGroup> workFlowStageEmpGroups = extraDao.getEmployeeGroupIn(workflowStage.getEmpGroupIds());
		
		//3. No hierarchy is followed ==============
		if(workflowStage.getOnlyWithHierarchy() == 0){
			String empGroupNames = Api.toCSV(workFlowStageEmpGroups,"empGroupName");
			workFlowStatusMessage = "Waiting for "+empGroupNames;
			return workFlowStatusMessage;
		}
		
		//4.Hierarchy is followed===============
		List<EmployeeGroup> managerGroups = null; 
		List<String> empNamesInHierarchyList = new ArrayList<String>();
		List<Long> empIdsInHierarchyList = new ArrayList<Long>();
		if(workflowStage.getOnlyWithHierarchy() == 1){
		 
			List<Employee> managers = getWebSupportManager().getManagersAboveMe(workflowFormStatus.getSubmittedBy()+"");
			
			for (Employee employee : managers) {
				
				managerGroups = extraDao.getEmployeeGroupOfEmployee(employee.getEmpId());
				for (EmployeeGroup managerGroup : managerGroups) {
					
					if(Api.islistContainsGivenValue(workFlowStageEmpGroups, "empGroupId", managerGroup.getEmpGroupId()+"")){
						
						if(empIdsInHierarchyList.contains(employee.getEmpId())){
							continue;
						}
						empNamesInHierarchyList.add(employee.getEmpName());
						empIdsInHierarchyList.add(employee.getEmpId());
					}
				}
			}
			String empNamesInHierarchy = Api.toCSV(empNamesInHierarchyList);
			workFlowStatusMessage = "Waiting for "+empNamesInHierarchy;
			if(Api.isEmptyString(empNamesInHierarchy)){
				String empGroupNames = Api.toCSV(workFlowStageEmpGroups,"empGroupName");
				workFlowStatusMessage = "Waiting for "+empGroupNames; 
			}
		}
		 
		}catch(Exception e){
			Log.info(getClass(), logText+" Exception occured ",e);
			return workFlowStatusMessage;
		}
		return workFlowStatusMessage;
	}
	

	public WorkFlowFormStatus getFormWorkflowStatus(String workflowStatusId) {
		return workFlowExtraDao.getFormWorkflowStatus(workflowStatusId);

	}
	
	public void executeWorkflowCustomCodeWhenRejectOrApprove(long formId,
			int actionType, int companyId, boolean canExecuteCustomCode) {
		try {
			if (canExecuteCustomCode) {
				Plugin plugin = extraDao.getPlugin(companyId, ""
						+ Plugin.WORKFKLOW_PLUGIN_NUMBER);
				WorkFlowPlugin workFlowPlugin = (WorkFlowPlugin) genericPluginLoader
						.getPlugin(plugin.getPluginClass());
				workFlowPlugin.init(effortWorkflowPluginService);
				if (WorkFlowFormStatus.STATUS_TYPE_REJECTED == actionType) {
					workFlowPlugin.workFlowReject(formId);
				} else if (WorkFlowFormStatus.STATUS_TYPE_APPROVED == actionType) {
					workFlowPlugin.workFlowApprove(formId);
				}
			}

		} catch (Exception ex) {

		}

	}
	public Workflow getWorkFlow(Long workFlowId) {
		Workflow workFlow = null;
		try {
			workFlow = workFlowExtraDao.getWorkFlow(workFlowId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlow;
	}
	
	private void populateWorkflowFormStatusBasedOnReject(
			WorkFlowFormStatus workFlowFormStatus, int submitterRank,
			int companyId, int rejectionFrom, WorkflowStage currentWorkflowStage, boolean isService) {
		
		Employee emp = null;
		WorkflowStage workflowStage = workFlowExtraDao.getWorkFlowStage(
				workFlowFormStatus.getWorkFlowStageId(),
				workFlowFormStatus.getWorkFlowId(),
				WorkflowStage.PREVIOUS_STAGE);
		List<String> rankList = webManager.getRankListCsv(""
				+ workFlowFormStatus.getSubmittedBy());
		if (rejectionFrom == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
			if(currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL ) {
				workFlowFormStatus.setStatus((short) -1);
				workFlowFormStatus.setEmpgroupId("-1");

			}else if (submitterRank == workFlowFormStatus.getPreviousRank()
					&& workflowStage != null) {
				executeWorkflowCustomCodeWhenRejectOrApprove(
						workFlowFormStatus.getFormId(),
						WorkFlowFormStatus.STATUS_TYPE_REJECTED, companyId,
						currentWorkflowStage.isCanExecuteCustomCode());
				workflowStage = moveToPreviousStage(workFlowFormStatus, workflowStage,companyId, submitterRank,currentWorkflowStage);
			} else {
				workFlowFormStatus.setStatus((short) -1);
				workFlowFormStatus.setEmpgroupId("-1");
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
					workFlowFormStatus.setNextRank(workFlowFormStatus
							.getCurrentRank());
					String currentRank = webManager.validateAndGetRank(
							rankList, workFlowFormStatus.getPreviousRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					int previousRank1 = workFlowExtraDao.getPreviousRank(
							companyId, workFlowFormStatus.getCurrentRank());
					workFlowFormStatus.setPreviousRank(previousRank1);
					
				} else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
					workFlowFormStatus.setNextRank(currentWorkflowStage
							.getFinalApproverRank());
					String currentRank = webManager.validateAndGetRank(
							rankList, workFlowFormStatus.getPreviousRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
				} else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
					workFlowFormStatus.setNextRank(workFlowFormStatus
							.getCurrentRank());
					String currentRank = webManager.validateAndGetRank(
							rankList, workFlowFormStatus.getPreviousRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					workFlowFormStatus.setPreviousRank(workFlowExtraDao
							.getPreviousRank(companyId,
									workFlowFormStatus.getCurrentRank()));
					// workFlowFormStatus.setNextRank(currentWorkflowStage.getFinalApproverRank());
				}

			}
		} else if (rejectionFrom == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
			executeWorkflowCustomCodeWhenRejectOrApprove(
					workFlowFormStatus.getFormId(),
					WorkFlowFormStatus.STATUS_TYPE_REJECTED, companyId,
					currentWorkflowStage.isCanExecuteCustomCode());
			boolean ltfsLogin = constantsExtra.isLtfsLogin();
			if(ltfsLogin) {
				workFlowFormStatus.setEmpgroupId("-1");
				workFlowFormStatus.setPreviousRank(null);
			}
			else {
				if (workflowStage != null) {
					workflowStage = moveToPreviousStage(workFlowFormStatus, workflowStage,companyId, submitterRank,currentWorkflowStage);
				} else {
					//Deva,2018-08-24,setting empGroupId to current stage empGroupId. so commenting below line
					workFlowFormStatus.setEmpgroupId("-1");
				}
			}
			

		} else if (rejectionFrom == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
			Long managerId = workFlowFormStatus.getManagerRank();
			// List<String> managerList =
			// Api.csvToList(workFlowFormStatus.getManagerCsvRanks());
			// List<String> managerList = webManager.getManagerList("" +
			// workFlowFormStatus.getSubmittedBy());
			List<String> managerList = new ArrayList<String>();

			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
				 emp = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
						+ workFlowFormStatus.getSubmittedBy());
				//Deva,2018-06-21,for only immediate manager submitted by not required in managerList
				managerList.add("" + emp.getEmpId());
				if (emp.getManagerId() != null) {
					managerList.add("" + emp.getManagerId());
				}
			}
			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
				managerList = webManager.getManagerList(""
						+ workFlowFormStatus.getSubmittedBy());
			}
			if (currentWorkflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
				
				Integer approvalModeLevel = currentWorkflowStage.getApprovalModeLevel();
				managerList = webManager.getManagerList(""+ workFlowFormStatus.getSubmittedBy());
				int requiredLevel =  approvalModeLevel+1; // we need to add +1 because managerList contain submitter also
                if(requiredLevel >= managerList.size()) {
                	requiredLevel = managerList.size() - 1;
                }
            	List<String> customLevelList = new ArrayList<String>();
 				customLevelList.add(managerList.get(0));
 				customLevelList.add(managerList.get(requiredLevel));
 				managerId = Long.parseLong(managerList.get(requiredLevel));
 				managerList.clear();
 				managerList.addAll(customLevelList);
               
			}

			String managerRank = webManager.getEntryFromList(managerList,
					managerId, WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
			if (workFlowFormStatus.getSubmittedBy() == Long
					.parseLong(managerRank) && workflowStage != null) {
				executeWorkflowCustomCodeWhenRejectOrApprove(
						workFlowFormStatus.getFormId(),
						WorkFlowFormStatus.STATUS_TYPE_REJECTED, companyId,
						currentWorkflowStage.isCanExecuteCustomCode());
				workflowStage = moveToPreviousStage(workFlowFormStatus, workflowStage,companyId, submitterRank,currentWorkflowStage);
			} else {
				workFlowFormStatus.setStatus((short) -1);
				workFlowFormStatus.setEmpgroupId("-1");
				workFlowFormStatus.setNextRank(null);
				workFlowFormStatus.setCurrentRank(null);
				workFlowFormStatus.setPreviousRank(null);
				if (currentWorkflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					
					String latest_client_production_version = constants.getLatest_client_production_version();
					 emp = webManager.getEmployee(""+ workFlowFormStatus.getSubmittedBy());
					Long immdiateManagerRank = emp.getManagerId();
					workFlowFormStatus.setManagerRank(immdiateManagerRank);
					if(latest_client_production_version.startsWith("5")) {
						 emp = webManager.getEmployee(""+ workFlowFormStatus.getSubmittedBy());
						 immdiateManagerRank = emp.getManagerId();
						 workFlowFormStatus.setManagerRank(immdiateManagerRank);
					}else {
						workFlowFormStatus.setManagerRank(Long.parseLong(managerList.get(0)));
					}
					
					
				} else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
					workFlowFormStatus.setManagerRank(Long
							.parseLong(managerRank));
				}else if (currentWorkflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
					
					Integer approvalModeLevel = currentWorkflowStage.getApprovalModeLevel();
	                managerList = webManager.getManagerList(""+ workFlowFormStatus.getSubmittedBy());
	                int requiredLevel =  approvalModeLevel+1;  // we need to add +1 because managerList contain submitter also
	                if(requiredLevel >= managerList.size()) {
	                	requiredLevel = managerList.size() - 1;
	                }
	                List<String> customLevelList = new ArrayList<String>();
					customLevelList.add(managerList.get(0));
					customLevelList.add(managerList.get(requiredLevel));
					managerList.clear();
					managerList.addAll(customLevelList);
					managerId = Long.parseLong(managerList.get(0));
					workFlowFormStatus.setManagerRank(managerId);
				}

			}
		}
		workFlowFormStatus.setStatus((short) -1);
		 emp = webManager.getEmployee(""+ workFlowFormStatus.getApprovedBy());
		if(isService)
			workFlowFormStatus.setStatusMessage("Rejected by " +emp.getEmpName()+ " from Mail");
		else
		workFlowFormStatus.setStatusMessage("Rejected");
		
		if(workflowStage != null && workflowStage.isGoToFormSubmisson()) {
			
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
				  
				  workFlowFormStatus.setCurrentRank(submitterRank);
				  WorkFlowFormStatus workFlowFormStatusColoned = null;
				  try {
					  workFlowFormStatusColoned = (WorkFlowFormStatus) workFlowFormStatus.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				  
				  emp = webManager.getEmployee(""+ workFlowFormStatus.getSubmittedBy());
				  webManager.getRanks(workFlowFormStatusColoned, workflowStage, emp,companyId);
				  
				  //int previousRank = workFlowExtraDao.getPreviousRank(companyId, workFlowFormStatus.getCurrentRank());
				  //int nextRank = workFlowExtraDao.getNextRank(companyId, workFlowFormStatus.getCurrentRank());
				  workFlowFormStatus.setPreviousRank(workFlowFormStatusColoned.getNextRank());
				  workFlowFormStatus.setNextRank(workFlowFormStatusColoned.getCurrentRank());
			}
			
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) 
			  workFlowFormStatus.setManagerRank(workFlowFormStatus.getSubmittedBy());
			
			//workFlowFormStatus.setManagerCsvRanks(null);
		}

	}

	
	public WorkflowStage moveToPreviousStage(WorkFlowFormStatus workFlowFormStatus,
			WorkflowStage workflowStage, int companyId, int submitterRank,WorkflowStage currentWorkflowStage) {
		if (workflowStage != null) {
			
			
			if(workflowStage.getStageId().longValue() != currentWorkflowStage.getStageId().longValue()) {
				 workflowStage = getWorkFlowStageOnRejection(workFlowFormStatus, submitterRank, companyId,currentWorkflowStage);
				//workflowStage.setGoToFormSubmisson(configuredWorkflowStage.isGoToFormSubmisson());
			 }
			
			List<String> rankList = webManager.getRankListCsv(""
					+ workFlowFormStatus.getSubmittedBy());
			workFlowFormStatus.setEmpgroupId("-1");
			workFlowFormStatus.setNextRank(null);
			workFlowFormStatus.setWorkFlowStageId(workflowStage.getStageId());
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
				if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
					String currentRank = webManager.validateAndGetRank(
							rankList, workflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					workFlowFormStatus.setPreviousRank(workFlowExtraDao
							.getPreviousRank(companyId,
									workFlowFormStatus.getCurrentRank()));
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
					String currentRank = webManager.validateAndGetRank(
							rankList, workflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					workFlowFormStatus.setPreviousRank(submitterRank);
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
					String currentRank = webManager.validateAndGetRank(
							rankList, workflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE);
					workFlowFormStatus.setCurrentRank(Integer
							.parseInt(currentRank));
					int nRank = workFlowExtraDao.getNextRank(companyId,
							submitterRank);
					String previousRank = webManager.validateAndGetRank(
							rankList, nRank,
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setPreviousRank(Integer
							.parseInt(previousRank));
				}
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
				workFlowFormStatus
						.setEmpgroupId(workflowStage.getEmpGroupIds());
				workFlowFormStatus.setPreviousRank(null);
				workFlowFormStatus.setCurrentRank(null);
				workFlowFormStatus.setNextRank(null);
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				Employee employee =getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
						+ workFlowFormStatus.getSubmittedBy().intValue());
				if (workflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					Long managerRank = employee.getManagerId();
					workFlowFormStatus.setManagerRank(managerRank);
					List<String> managerList = new ArrayList<String>();
					managerList.add("" + employee.getEmpId());
					managerList.add("" + managerRank);
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
				} else if (workflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
					List<String> managerList = webManager.getManagerList(""
							+ employee.getEmpId());
					// List<String> managerList = webManager.getManagerList("" +
					// workFlowFormStatus.getSubmittedBy());
					String id = managerList.get(managerList.size() - 1);
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					workFlowFormStatus.setManagerRank(Long.parseLong(id));
				} else if (workflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
					  
					   Integer approvalModeLevel = workflowStage.getApprovalModeLevel();
					   
					   List<String> managerList = webManager.getManagerList(""+ employee.getEmpId());
					   int requiredLevel =  approvalModeLevel+1;
		               if(requiredLevel >= managerList.size()) {
		                	requiredLevel = managerList.size() - 1; // we need to add +1 because managerList contain submitter also
		               }

		               List<String> customLevelList = new ArrayList<String>();
					   customLevelList.add(managerList.get(0));
					   customLevelList.add(managerList.get(requiredLevel));
					   managerList.clear();
					   managerList.addAll(customLevelList);
						
					   String id = managerList.get(managerList.size() - 1);
					   String managerCsvRanks = Api.toCSV1(managerList);
					   workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					   workFlowFormStatus.setManagerRank(Long.parseLong(id));
				   
					}
			}
		}
		return workflowStage;
	}


	public WorkflowStage getWorkFlowStageOnRejection(WorkFlowFormStatus workFlowFormStatus, int submitterRank,
			int companyId,WorkflowStage currentWorkflowStage) {
		
		String logText = "getWorkFlowStageOnRejection() // formId="+workFlowFormStatus.getFormId()+" stageId="+currentWorkflowStage.getStageId();
		FormRejectionStagesConfiguration formRejectionStagesConfiguration = extraSupportAdditionalDao.getFormRejectionStagesConfigurationByStageId(companyId,currentWorkflowStage.getWorkflowId(),currentWorkflowStage.getStageId());
		
		if(formRejectionStagesConfiguration == null) {
			
			Log.info(getClass(), logText+" formRejectionStagesConfiguration not found");
			WorkflowStage workflowStage = workFlowExtraDao.getWorkFlowStage(
					workFlowFormStatus.getWorkFlowStageId(),
					workFlowFormStatus.getWorkFlowId(),
					WorkflowStage.PREVIOUS_STAGE);
			
			if(workflowStage != null)
				Log.info(getClass(), logText+" formRejectionStagesConfiguration not found workflowStageId = "+workflowStage.getWorkflowId());
			
			return workflowStage;
		}
		Log.info(getClass(), logText+" formRejectionStagesConfiguration = "+formRejectionStagesConfiguration);
		WorkflowStage workflowStage = null;
		if(formRejectionStagesConfiguration.getMappedStageId() == FormRejectionStagesConfiguration.GO_TO_FORM_SUBMISSION_STAGEID) { 
			workflowStage = workFlowExtraDao.getFirstStageForGivenStage(currentWorkflowStage.getWorkflowId());
			workflowStage.setEmpGroupIds("-1");
			workflowStage.setGoToFormSubmisson(true);
		}
		else
		 workflowStage = workFlowExtraDao.getWorkFlowStageWithId(formRejectionStagesConfiguration.getMappedStageId());
		
		
		if(workflowStage != null) {
			workflowStage.setInitiatedFromRejectedConfiguration(true);
			Log.info(getClass(), logText+" formRejectionStagesConfiguration found workflowStageId = "+workflowStage.getStageId());
		}
		
		return workflowStage;
	}
	
	
	public void populateWorkFlowStatusMessageForHierachy(WorkFlowFormStatus workflowFormStatus){
		
		String logText = "getWorkFlowStatusMessageForHierachy() // formId = "+workflowFormStatus.getFormId()+" ";
		String workFlowStatusMessage = workflowFormStatus.getStatusMessage();
		//1.Validatations =========================
	    WorkflowStage workflowStage = workFlowExtraDao.getWorkFlowStage(workflowFormStatus.getWorkFlowStageId());
	    if(workflowStage == null){
	    	Log.info(getClass(), logText+" No workflowStage found.");
	    	return;
	    }
	    
	    if (workflowStage.getStageType() != WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
	    	Log.info(getClass(), logText+" This is not STAGE_TYPE_HIERARCHY_BASED . ");
	    	return;
	    }
	    
	    if(workflowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_WAITING){
	    	Log.info(getClass(), logText+" workflowFormStatus Id "+workflowFormStatus.getId()+" formId = "
	    			+workflowFormStatus.getFormId()+" not in waiting status");
	    	return;
	    }
	    Long managerRank = workflowFormStatus.getManagerRank();
	    Log.info(getClass(), logText+" managerRank = "+managerRank);
	    if (managerRank != null) {
			Employee managerEmployee = webManager.getEmployee(""
					+ managerRank);
			workflowFormStatus.setStatusMessage("Waiting for "+ managerEmployee.getEmpName());
		}
	}

	
	public void populateWorkflowFormStatusBasedResubmissionWithModifyData(
			WorkFlowFormStatus workFlowFormStatus,
			WorkflowStage currentWorkflowStage, int submitterRank,
			int companyId, int approvedOrResubmitFrom, long loginEmpId) {

		WorkFlowFormStatus workFlowFormStatusOld = workFlowExtraDao.getWorkFlowFormStatus(workFlowFormStatus.getFormId());
		if(workFlowFormStatusOld == null) {
			return;
		}
		if(workFlowFormStatusOld.getStatus() != WorkFlowFormStatus.STATUS_TYPE_REJECTED) {
			return;
		}
		if(workFlowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_WAITING) {
			return;
		}
		if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
			workFlowFormStatus.setEmpgroupId(currentWorkflowStage.getEmpGroupIds());
			workFlowFormStatus.setApprovedBy(workFlowFormStatusOld.getApprovedBy());
		}
		else if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
			
			if(isHierarchyBasedImmediateManager(approvedOrResubmitFrom, currentWorkflowStage)) {
				
				List<String> managerList = new ArrayList<String>();
				String managerRank = null;
				Employee emp = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""+ workFlowFormStatus.getSubmittedBy());
				managerList.add("" + emp.getEmpId());
				if (emp.getManagerId() != null) {
					managerList.add("" + emp.getManagerId());
					managerRank = webManager.getEntryFromList(managerList,
							workFlowFormStatus.getSubmittedBy(), WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setManagerRank(Long.parseLong(managerRank));
					workFlowFormStatus.setApprovedBy(workFlowFormStatusOld.getApprovedBy());
				}
			}
		}
	}
	
	
public boolean isHierarchyBasedImmediateManager(int approvedOrResubmitFrom,WorkflowStage currentWorkflowStage) {
		
		if (approvedOrResubmitFrom == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED && 
				currentWorkflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
			return true;
		}
		
		return false;
	}

public FormSpec getFormSpec(String formSpecId) {
	return workFlowExtraDao.getFormSpec(formSpecId);

}

public void populateWorkFlowFormStatusApproval(Long empId, List<WorkFlowFormStatus> workflowApproval)
{
	Employee employee=getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId(empId+"");
	Integer empRank=employee.getRank();
	String loginGroupId=webManager.getEmployeeGroupIdsForEmployee(empId+"");
	List<Long> longEmpGroupList = Api.csvToLongList(loginGroupId);
	
	for (WorkFlowFormStatus workflowFormStatus : workflowApproval) {
		workflowFormStatus.setTzo(employee.getTimezoneOffset()+"");
		String empGroupId = workflowFormStatus.getEmpgroupId();
		List<Long> empGroupIdsList = Api.csvToLongList(empGroupId);
		workflowFormStatus.setIsInGroup(0);
		workflowFormStatus.setCanApprove(0);
		// boolean isGroupExit=false;

		// Employee
		// submitterEmp=webManager.getEmployee(""+workflowFormStatus.getSubmittedBy());
		if (workflowFormStatus.getStageType().longValue() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
			if (workflowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_REJECTED
					&& "-1".equals(workflowFormStatus.getEmpgroupId())) {
				if (workflowFormStatus.getSubmittedBy().longValue() == empId
						.longValue()
						&& workflowFormStatus.getPreviousRank() == null) {
					workflowFormStatus.setIsInGroup(1);
					workflowFormStatus.setCanApprove(1);
				}
			}

			if (!Api.isEmptyString(empGroupId)
					&& !Api.isEmptyString(loginGroupId)
					&& !empGroupId.equals("-1")) {
				// isGroupExit=true;
				if (workflowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_REJECTED) {

					for (Long empGroupIdVal : empGroupIdsList) {
						if (longEmpGroupList.contains(empGroupIdVal)) {
							workflowFormStatus.setIsInGroup(1);
							workflowFormStatus.setCanApprove(1);
						}
					}

				} else if ((workflowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_APPROVED && workflowFormStatus
						.getStatus() != WorkFlowFormStatus.STATUS_TYPE_CANCELED)) {
					for (Long empGroupIdVal : empGroupIdsList) {
						if (longEmpGroupList.contains(empGroupIdVal)) {
							workflowFormStatus.setIsInGroup(1);
							workflowFormStatus.setCanApprove(1);
						}
					}

				}

			}


		} else if (workflowFormStatus.getStageType().longValue() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
			if (((workflowFormStatus.getStatus() == 0)
					|| (workflowFormStatus.getStatus() == 2) || (workflowFormStatus
					.getStatus() == -1))
					&& ((empRank != null)
							&& (workflowFormStatus.getCurrentRank() != null) && (empRank == workflowFormStatus
							.getCurrentRank()))) {
				workflowFormStatus.setIsInGroup(1);
				workflowFormStatus.setCanApprove(1);
			}
		} else if (workflowFormStatus.getStageType().longValue() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
			if (workflowFormStatus.getManagerRank() != null
					&& (empId.longValue() == workflowFormStatus
							.getManagerRank().intValue())
					&& (workflowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_WAITING
							|| workflowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_REJECTED || workflowFormStatus
							.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED)) {
				workflowFormStatus.setIsInGroup(1);
				workflowFormStatus.setCanApprove(1);
			}
		}

	}
}

public Long getWorkflowIdIfExist(String entityId, int type) {
	return workFlowExtraDao.getWorkflowIdIfExistFromWorkflowEntityMap(
			entityId, type);
}

public Long getWorkflowIdByEntityIdAndType(String formId,String entityId, int type) {
	
	 String logText = "getWorkflowIdByEntityIdAndType() // formId = "+formId;
	 Long workFlowId = null;
	 try{
		 Log.info(getClass(), logText+" starts ...");
		 List<WorkflowEntityMap> workflowEntityMapList = workFlowExtraDao.getWorkflowEntityMapByEntityIdAndType(entityId,type);
		 if(workflowEntityMapList.isEmpty()) {
			 Log.info(getClass(), logText+" workflowEntityMapList is empty");
			 return null;
		 }
		 
		 String mappingIds = Api.toCSV(workflowEntityMapList,"id");
		 List<WorkflowEntityVisibilityCondition> visibilityConditionList = workFlowExtraDao.getWorkflowEntityVisibilityConditionsByMappingIds(mappingIds);
		 if(visibilityConditionList.isEmpty()) {
			 
			 
			 for (WorkflowEntityMap workflowEntityMap : workflowEntityMapList) {
					
				 if(workflowEntityMap.getDefaultWorkFlow() == 1) {
					 Log.info(getClass(), logText+" default check for workFlowId = "+workflowEntityMap.getWorkflowId());
					 return workflowEntityMap.getWorkflowId();
				 }
		     }
			 
			 workFlowId = workflowEntityMapList.get(0).getWorkflowId();
			 Log.info(getClass(), logText+" workFlowId = "+workFlowId);
			 return workFlowId;
		 }
		 
		 if(Api.isEmptyString(formId)) {
			 workFlowId = workflowEntityMapList.get(0).getWorkflowId();
			 return workFlowId;
		 }
		 Form form = webManager.getForm(formId+"");
		 List<FormField> formFields=webManager.getFormFields(form.getFormId());
		 Map<Long, Boolean> mappingIdAndResultMap = new HashMap<Long, Boolean>();
		 
		 Map<Long,WorkflowEntityMap> mappingIdAndWorkflowEntityMap = (Map)Api.getMapFromList(workflowEntityMapList, "id");
		 WorkflowEntityMap workflowEntityMapFromMap = null; 
		 Map<Long,List<WorkflowEntityVisibilityCondition>>  mappingIdAndVisibilityConditionListMap = (Map)Api.getGroupedMapFromList(visibilityConditionList, "workflowEntityMapId", null);
		 evaluteWorkflowEntityVisibilityCritirias(formFields, form.getFormSpecId(), mappingIdAndVisibilityConditionListMap, mappingIdAndResultMap);
		 
		 for (Entry<Long, Boolean> entry : mappingIdAndResultMap.entrySet()) {
			
			 if(entry.getValue()) {
				  
				 workflowEntityMapFromMap = mappingIdAndWorkflowEntityMap.get(entry.getKey().longValue());
				 Log.info(getClass(), logText+" workFlowId = "+workflowEntityMapFromMap.getWorkflowId());
				 return workflowEntityMapFromMap.getWorkflowId();
			 }
		 }
		 
		 for (WorkflowEntityMap workflowEntityMap : workflowEntityMapList) {
				
			 if(workflowEntityMap.getDefaultWorkFlow() == 1 && workflowEntityMapList.size() > 1) {
				 Log.info(getClass(), logText+" default check for workFlowId = "+workflowEntityMap.getWorkflowId());
				 return workflowEntityMap.getWorkflowId();
			 }
	     }
		 
		}catch(Exception ex){
			Log.error(getClass(), logText+" exception occured ",ex);
		}
		return workFlowId;
	}

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public void evaluteWorkflowEntityVisibilityCritirias(
				List<FormField> formFields,
				long formSpecId,
				Map<Long, List<WorkflowEntityVisibilityCondition>> mappingIdAndWorkflowEntityVisibilityConditionMap,
				Map<Long, Boolean> workActionSpecIdAndResultMap) {
			
			
			String logText = "evaluteWorkflowEntityVisibilityCritirias() // ";
			if(formFields==null){
				return;
			}
			
			List<FormFieldSpec> formFieldSpecs=webManager.getFormFieldSpecs(formSpecId);
			Map<String,FormFieldSpec>expressionAndFormFieldSpecMap=(Map)Api.getMapFromList(formFieldSpecs, "expression");
			Map<Long,FormField> formFieldSpecIdAndFieldMap=(Map)Api.getMapFromList(formFields, "fieldSpecId");
			Map<Long,FormField> expAndWorkFormFieldMap=(Map)Api.getMapFromList(formFields, "expression");
			Map<Long,FormField> skeletonFieldSpecIdAndWorkFormFieldMap=(Map)Api.getMapFromList(formFields, "skeletonFormFieldSpecId");
			List<FormFieldSpecValidValue>latestFormValidValues=webManager.getFormFieldSpecValidValues(formFieldSpecs) ;
			Map<Long,List<FormFieldSpecValidValue>>fieldSpecIdAndValidValusMap=(Map)Api.getGroupedMapFromList(latestFormValidValues, "fieldSpecId", null);
			
			for (Entry<Long, List<WorkflowEntityVisibilityCondition>> entry : mappingIdAndWorkflowEntityVisibilityConditionMap.entrySet()) {
				
				long mappingId = entry.getKey(); //WorkflowEntityMap primary key
				List<WorkflowEntityVisibilityCondition>  mappingIdSpecificVisibilityConditionList = entry.getValue();
				int  conjunction=1;
				int visibilityType=1;
				List<String> result = new ArrayList<String>();
				
				Log.info(getClass(), logText+" mappingId = "+mappingId+" conditions size = "+mappingIdSpecificVisibilityConditionList.size()+" starts...");
				for (WorkflowEntityVisibilityCondition visibilityCondition : mappingIdSpecificVisibilityConditionList) {
					
					String value = visibilityCondition.getValue();
					conjunction = visibilityCondition.getConjunction();
					visibilityType = visibilityCondition.getVisibilityType();
					long formId= formFields.get(0).getFormId();
					//Work fields
					FormFieldSpec formFieldSpec = expressionAndFormFieldSpecMap.get(visibilityCondition.getTargetFieldExpression());
					if(formFieldSpec==null){
						continue;
					}
					FormField formField=formFieldSpecIdAndFieldMap.get(formFieldSpec.getFieldSpecId());
					if(formField!=null){
						evaluteWorkflowEntityCritiriasForAddCritiries(formField.getFieldSpecId(), 
								formField.getFieldType(), formField.getFieldValue(), fieldSpecIdAndValidValusMap,
								visibilityCondition, result);
					}else{
						if(visibilityCondition.getCondition() == 24)
						{
							getWebAdditionalSupportManager().addToResultList(result,false);
						}
						else
						{
						    getWebAdditionalSupportManager().addToResultList(result,Api.isEmptyString(value));
						}
					}
				}
				
				boolean conjunctionResult;
				if (conjunction == 1) {
					conjunctionResult = !result.contains("false");
				} else {
					conjunctionResult = result.contains("true");
				}
				
				if (conjunctionResult) {
					if(visibilityType==1)
					{
						conjunctionResult=false;
					}
					else
					{
						conjunctionResult=true;
					}
				} else {
					if(visibilityType==1)
					{
						conjunctionResult=true;
					}
					else
					{
						conjunctionResult=false;
					}
				}
				Log.info(getClass(), logText+" mappingId = "+mappingId+" conjunction = "+conjunction+" result = "+result+" conjunctionResult = "+conjunctionResult+" ends.");
				workActionSpecIdAndResultMap.put(entry.getKey(), conjunctionResult);
				/*if(conjunctionResult) {
					Log.info(getClass(), logText+" mappingId = "+mappingId+" result is true so not evaluating other mappings");
				}*/
			}
		}

public void evaluteWorkflowEntityCritiriasForAddCritiries(Long fieldSpecId,int fieldType,String fieldValue,
		Map<Long,List<FormFieldSpecValidValue>>fieldSpecIdAndValidValusMap,
		WorkflowEntityVisibilityCondition workflowEntityVisibilityCondition,List<String> result) {

	   String value=workflowEntityVisibilityCondition.getValue();
       if(fieldType == Constants.FORM_FIELD_TYPE_MULTI_SELECT_LIST || fieldType == Constants.FORM_FIELD_TYPE_SINGLE_SELECT_LIST){
    	   
    	   List<Long>latestValidValueIds=new ArrayList<Long>();
    	   List<FormFieldSpecValidValue> latestValidValues= fieldSpecIdAndValidValusMap.get(fieldSpecId);
    	   Map<String,FormFieldSpecValidValue>latestValidValuesMap=(Map)Api.getMapFromList(latestValidValues, "value");
    	   if(!Api.isEmptyString(workflowEntityVisibilityCondition.getValue()))
    	   {
    		   List<String> givenValues = Api.csvToList(workflowEntityVisibilityCondition.getValue());
    		   for (String givenValue : givenValues) {
	    		   FormFieldSpecValidValue formFieldSpecValidValueLatest=latestValidValuesMap.get(givenValue);
	    		   if(formFieldSpecValidValueLatest!=null){
	    			   latestValidValueIds.add(formFieldSpecValidValueLatest.getValueId());
	    		   }
				
		     	}
    	   }
    	   value=Api.toCSV(latestValidValueIds);
       }
	   
	   webManager.resolveCondition(result, fieldValue,
				value,workflowEntityVisibilityCondition.getCondition(),workflowEntityVisibilityCondition.getFieldDataType());
	
  }

public Long getWorkFlowIdByFormSpecUniqueIdAndFormId(String formSpecUniqueId,String formId,int type) {
	 
	 Long workflowId = null;
    if(formId == null) {
			workflowId = getWorkflowIdIfExist(formSpecUniqueId,type);
	  }else {
			workflowId = getWorkflowIdByEntityIdAndType(formId,formSpecUniqueId,type);
	  }
    return workflowId;
}


public SystemRejectedFormsLog validateWorkflowChange(WorkFlowFormStatus workFlowFormStatus,WebUser webUser) {
	
	Form form = webManager.getForm(workFlowFormStatus.getFormId()+"");
	FormSpec formSpec = webManager.getFormSpec("" + form.getFormSpecId());
	/*Long workflowId = getWorkFlowExtraDao()
			.getWorkflowIdIfExistFromWorkflowEntityMap(
					formSpec.getUniqueId(),
					WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);*/
	
	String remarks = "";
	SystemRejectedFormsLog systemRejectedFormsLog = new SystemRejectedFormsLog();
	Long workflowId = getWorkFlowIdByFormSpecUniqueIdAndFormId(formSpec.getUniqueId(),form.getFormId()+"",WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);
	if(workflowId == null) {
			remarks = "No matching workflow found";
			systemRejectedFormsLog = new SystemRejectedFormsLog(webUser.getCompanyId(), workFlowFormStatus.getFormId(), remarks);
			systemRejectedFormsLog.setReinitateFromBeginingOfTheStage(true);
			return systemRejectedFormsLog; 
	}
	if(workflowId.longValue() != workFlowFormStatus.getWorkFlowId().longValue()) {
		remarks = "New work flow mapped. Old workFlowId="+workFlowFormStatus.getWorkFlowId()+" new WorkflowId="+workflowId;
		systemRejectedFormsLog = new SystemRejectedFormsLog(webUser.getCompanyId(), workFlowFormStatus.getFormId(), remarks);
		systemRejectedFormsLog.setReinitateFromBeginingOfTheStage(true);
		systemRejectedFormsLog.setWorkflowId(workflowId);
		return systemRejectedFormsLog; 
	}
   WorkflowStage workflowStage = workFlowExtraDao.getWorkFlowStageWithId(workFlowFormStatus.getWorkFlowStageId());
   if(workflowStage == null) {
	    remarks = "workflowStageId = "+workFlowFormStatus.getWorkFlowStageId()+" is in delete state";
		systemRejectedFormsLog = new SystemRejectedFormsLog(webUser.getCompanyId(), workFlowFormStatus.getFormId(), remarks);
		systemRejectedFormsLog.setReinitateFromBeginingOfTheStage(true);
		systemRejectedFormsLog.setWorkflowId(workflowId);
		return systemRejectedFormsLog; 
   }
   if (workflowStage.getStageType() != WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
	   remarks = "workflowStageId = "+workFlowFormStatus.getWorkFlowStageId()+" is not hierarchy based";
	   systemRejectedFormsLog = new SystemRejectedFormsLog(webUser.getCompanyId(), workFlowFormStatus.getFormId(), remarks);
	   systemRejectedFormsLog.setReinitateFromBeginingOfTheStage(true);
	   systemRejectedFormsLog.setWorkflowId(workflowId);
	   return systemRejectedFormsLog; 
   }
   return systemRejectedFormsLog;
}

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public boolean reinitateSystemRejectionFormForHierarchyChanges(WebUser webUser,
		WorkFlowFormStatus workFlowFormStatus,
		Employee webEmployee,
		boolean ignoreHistory,Long newWorkFlowId,boolean test) throws JsonGenerationException, JsonMappingException, IllegalArgumentException, IllegalAccessException, ParseException, IOException,EffortError {
	
    
	String logText = "reinitateSystemRejectionFormForHierarchyChanges() // formId = "+workFlowFormStatus.getFormId();
	Log.info(getClass(), logText+" test = "+test+" starts...");
	Log.info(getClass(), logText+" workFlowFormStatus Id = "+workFlowFormStatus.getId());
	long submittedBy = workFlowFormStatus.getSubmittedBy();
	Log.info(getClass(), logText+" submittedBy = "+submittedBy);
	
	
	//WorkFlowRealData workFlowRealData = getWorkFlowRealData(workFlowFormStatus.getFormId());
	WorkFlowFormStatus workFlowFormStatusOld= getFormWorkflowStatus(""+workFlowFormStatus.getId());
	//2. Case 2 : when next manager changes
	List<Long> alreadyApprovedManagers = getAlreadyApprovedManagersList(workFlowFormStatus);
	List<String> submittedByManagerList = webManager.getManagerList("" + workFlowFormStatus.getSubmittedBy());
	Employee submittedByEmployee =  getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId(submittedBy+"");
	Log.info(getClass(), logText+" alreadyApprovedManagers = "+alreadyApprovedManagers+" submittedByManagerList = "+submittedByManagerList);    
	SystemRejectedFormsLog systemRejectedFormsLog = new SystemRejectedFormsLog();
	String remarks = null;
	boolean reinitateFromBeginingOfTheStage = false;
	Form form = webManager.getForm(workFlowFormStatus.getFormId()+"");
	
	systemRejectedFormsLog = validateWorkflowChange(workFlowFormStatusOld,webUser);
	if(systemRejectedFormsLog.isReinitateFromBeginingOfTheStage()) {
		Log.info(getClass(), logText+" isReinitateFromBeginingOfTheStage is true because workflow change detected workFlowFormStatus Id = "+workFlowFormStatus.getId());
		
		if(systemRejectedFormsLog.getWorkflowId() == null) {
			Log.info(getClass(), logText+" workflow mapping not found for the formId");
			return false;
		}
		submittedBy = workFlowFormStatus.getSubmittedBy();
		Log.info(getClass(), logText+" submittedBy = "+submittedBy);
		
		if(test) {
			return true;
		}
		int deleted = workFlowExtraDao.deleteWorkflowFormStatus(webUser.getCompanyId(), workFlowFormStatus.getFormId()+"");
		
		if(deleted  == 0) {
			Log.info(getClass(), logText+"deletion failed");
			return false;
		}
		//webManager.createOrUpdateWorkflowForForm(form, webUser);
		String ids = getWebAdditionalSupportManager().insertWorkFlowFormStatus(form, webUser, submittedBy,ignoreHistory,newWorkFlowId);
		Log.info(getClass(), logText+" workflowFormStatus Id AND historyId = "+ids);
		long id = workFlowExtraDao.insertSystemRejectedFormsLogs(systemRejectedFormsLog, webUser);
		Log.info(getClass(), logText+" insertSystemRejectedFormsLogs() // id = "+id);
	    return true;
	
	}
	
	WorkflowStage workflowStage = getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
	
	for (Long alreadyApprovedManagerId : alreadyApprovedManagers) {
		
    	if(!submittedByManagerList.contains(alreadyApprovedManagerId+"")) {
    		remarks = " alreadyApprovedManagerId = "+alreadyApprovedManagerId+" is not in submittedByManagerList = "+submittedByManagerList+" so reinitating from stage1 alreadyApprovedManagers = ";
    		Log.info(getClass(), logText+" alreadyApprovedManagerId = "+alreadyApprovedManagerId+" is not in submittedByManagerList");
    		reinitateFromBeginingOfTheStage = true;
    		break;
    		
    	}
	}
	Log.info(getClass(), logText+" reinitateFromBeginingOfTheStage = "+reinitateFromBeginingOfTheStage);
	populateManagerRank(workFlowFormStatus, reinitateFromBeginingOfTheStage, submittedByEmployee, workflowStage, submittedByManagerList, alreadyApprovedManagers);
	//populateStatusMessage(workFlowFormStatus, workflowStage);
	if (workFlowFormStatus.getManagerRank() != null) {
		Employee managerEmployee = webManager.getEmployee(workFlowFormStatus.getManagerRank()+"");
		workFlowFormStatus.setStatusMessage("Waiting for "+ managerEmployee.getEmpName());
	}
	
	String currentTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
	workFlowFormStatus.setModifiedTime(currentTime);
	
	remarks = "reinitateFromBeginingOfTheStage = "+reinitateFromBeginingOfTheStage+" remarks = "+remarks+" old approved by = "
			+workFlowFormStatusOld.getApprovedBy()+" old managerRank = "+workFlowFormStatusOld.getManagerRank()+" new managerRank = "+workFlowFormStatus.getManagerRank()+" alreadyApprovedManagers = "+alreadyApprovedManagers;
	
	Log.info(getClass(), logText+" remarks = "+remarks);
	if(test) {
		return true;
	}
	workFlowExtraDao.updateWorkFlowFormStatusForform(workFlowFormStatus);
	
	//webManager.populateWorkflowFormStatus(workFlowFormStatus,"" + workFlowFormStatus.getFormId(), webUser.getCompanyId(),
			//workflowStage, approvedByEmployee, webUser.getEmpId());
	//insertOrUpdateWorkFlowFormStatus(workFlowRealData,webUser,approvedByEmployee, workFlowFormStatus,workFlowFormStatusOld,false,false);
	//workFlowExtraDao.updateWorkFlowFormStatusForform(workFlowFormStatus);
	
	remarks = "reinitateFromBeginingOfTheStage = "+reinitateFromBeginingOfTheStage+" remarks = "+remarks+" old approved by = "
	+workFlowFormStatusOld.getApprovedBy()+" old managerRank = "+workFlowFormStatusOld.getManagerRank()+" new managerRank = "+workFlowFormStatus.getManagerRank()+" alreadyApprovedManagers = "+alreadyApprovedManagers;
	systemRejectedFormsLog = new SystemRejectedFormsLog(webUser.getCompanyId(), workFlowFormStatus.getFormId(), remarks);
	if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_WAITING) {
		
		workFlowFormStatus.setRemarks("Reinitiated");
		long historyId = webManager.insertOrUpdateWorkFlowFormStatusHistory(
				webEmployee, webUser, workFlowFormStatus, webUser.getEmpId());
		workFlowFormStatus.setHistoryId(historyId);
	}
	long id = workFlowExtraDao.insertSystemRejectedFormsLogs(systemRejectedFormsLog, webUser);
	Log.info(getClass(), logText+" insertSystemRejectedFormsLogs() // id = "+id);
	return true;
}

public List<Long> getAlreadyApprovedManagersList(WorkFlowFormStatus workFlowFormStatus) {
	
	String logText = "isFormAlreadyApprovedByEmpId() // formId = "+workFlowFormStatus.getFormId();
	List<Long> alreadyApprovedManagers = new ArrayList<Long>();
	List<WorkFlowFormStatusHistory> flowFormStatusHistories = workFlowExtraDao.getWorkFlowFormStatusHistoriesByFormIdAndWorkFlowId(workFlowFormStatus);
	for (WorkFlowFormStatusHistory workFlowFormStatusHistory : flowFormStatusHistories) {
		
		if(workFlowFormStatusHistory.getWorkFlowStageId().longValue() != workFlowFormStatus.getWorkFlowStageId().longValue()) {
			break; // if stage mismatches then we need to  break this
		}
		if(workFlowFormStatusHistory.getApprovedBy() == null) {
			continue;
		}
		if(workFlowFormStatusHistory.getStatus().shortValue() != WorkFlowFormStatus.STATUS_TYPE_APPROVED) {
			continue;
		}
		alreadyApprovedManagers.add(workFlowFormStatusHistory.getApprovedBy());
	}
	Collections.reverse(alreadyApprovedManagers);
	Log.info(getClass(), logText+" alreadyApprovedManagers = "+alreadyApprovedManagers);
	return alreadyApprovedManagers;
}


public void populateManagerRank(WorkFlowFormStatus workFlowFormStatus,boolean reinitateFromBeginingOfTheStage,
		Employee submittedByEmployee,WorkflowStage workflowStage,
		List<String> submittedByManagerList,List<Long> alreadyApprovedManagers) {
	
	String logText = "populateManagerRank() // formId = "+workFlowFormStatus.getFormId();
	Integer approvalMode =  workflowStage.getApprovalMode();
	Log.info(getClass(), logText+" approvalMode = "+approvalMode+" reinitateFromBeginingOfTheStage = "+reinitateFromBeginingOfTheStage);
	workFlowFormStatus.setStatus((short)0);
	int requiredLevel = 0;
	if(reinitateFromBeginingOfTheStage) {
		workFlowFormStatus.setStatus((short)0);
		workFlowFormStatus.setManagerRank(submittedByEmployee.getManagerId());
	}else {
		
		if (approvalMode == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
			 
			Integer approvalModeLevel = workflowStage.getApprovalModeLevel();
			requiredLevel =  approvalModeLevel+1;  // we need to add +1 because managerList contain submitter also
            if(requiredLevel >= submittedByManagerList.size()) {
                	requiredLevel = submittedByManagerList.size() - 1;
            }
			String newManagerRank = submittedByManagerList.get(requiredLevel);
			workFlowFormStatus.setManagerRank(Long.parseLong(newManagerRank));
			List<String> customLevelList = new ArrayList<String>();
		   customLevelList.add(submittedByManagerList.get(0));
		   customLevelList.add(submittedByManagerList.get(requiredLevel));
		   String managerCsvRanks = Api.toCSV1(customLevelList);
		   workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
		}else {
			
			requiredLevel = alreadyApprovedManagers.size()+1;
			if(requiredLevel >= submittedByManagerList.size() || requiredLevel < 0){
				workFlowFormStatus.setManagerRank(null);
			}else {
				String newManagerRank = submittedByManagerList.get(requiredLevel);
				workFlowFormStatus.setStatus((short)0);
				workFlowFormStatus.setManagerRank(Long.parseLong(newManagerRank));
				String managerCsvRanks = Api.toCSV(submittedByManagerList);
				workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
			}
		}
	}
	Log.info(getClass(), logText+" requiredLevel = "+requiredLevel+" submittedByManagerList.size() = "+submittedByManagerList.size());
	
}


}

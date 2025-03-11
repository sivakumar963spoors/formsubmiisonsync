package com.effort.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.effort.beans.http.response.extra.FormSubmissionData;
import com.effort.entity.Employee;
import com.effort.entity.Form;
import com.effort.entity.FormHistoryForForm;
import com.effort.entity.JmsMessage;
import com.effort.exception.EffortError;
import com.effort.manager.MainManager;
import com.effort.manager.SyncManager;
import com.effort.manager.SyncValidationManager;
import com.effort.manager.WebManager;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping(value = "/v21/api")
public class FormSubmissionController {
	
	Logger logger = LogManager.getLogger("synclog"); 


	
	@Autowired
	private MainManager mainManager;
	
	@Autowired
	private SyncValidationManager syncValidationManager;
	
	@Autowired
	private WebManager webManager;
		
	@Autowired
	private SyncManager syncManager;
	
	
	@PostMapping(value = "/formSubmission/sync/{empId}", consumes = "application/json")
	@ResponseBody
	public FormHistoryForForm formSubmissionSyncPost(@PathVariable("empId") long empId,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "apiLevel", required = true) String apiLevel,
			@RequestParam(value = "syncRequestId", required = true, defaultValue = "") String syncRequestId,
			@RequestParam(value = "syncTime", required = true) String syncTime,
			@RequestParam(value = "clientVersion", required = true) String clientVersion,
			@RequestParam(value = "signature", required = true, defaultValue = "") String signature,
			@RequestParam(value = "safeCheck", required = true, defaultValue = "true") boolean safeCheck,
			@RequestBody String jsonString, HttpServletRequest request)
			throws EffortError, JsonGenerationException, JsonMappingException, IOException {

		code = SecurityUtils.stripInvalidCharacters(code, SecurityUtils.INPUT_TYPE_TEXT);
		apiLevel = SecurityUtils.stripInvalidCharacters(apiLevel, SecurityUtils.INPUT_TYPE_NUMBER);
		syncRequestId = SecurityUtils.stripInvalidCharacters(syncRequestId, SecurityUtils.INPUT_TYPE_NUMBER);
		clientVersion = SecurityUtils.stripInvalidCharacters(clientVersion, SecurityUtils.INPUT_TYPE_TEXT);

		List<JmsMessage> jmsMessages = new ArrayList<JmsMessage>();
		syncValidationManager.validateSyncSignrature(empId, apiLevel, signature, jsonString, request);

		syncTime = syncTime == null ? null
				: (syncTime.equals("null") || syncTime.equals("Null") || syncTime.equals("NULL")
						|| Api.isEmptyString(syncTime)) ? null : syncTime;

		try {

			Log.info(this.getClass(), " formSubmissionSyncPost Req. EmpId: " + empId + " syncTime = " + syncTime
					+ " syncRequestId = " + syncRequestId + ", json:" + jsonString);
			logger.info("formSubmissionSyncPost Req. EmpId: " + empId + " syncTime = " + syncTime + " syncRequestId = "
					+ syncRequestId + ", json:" + jsonString);

			Long syncStartTime = System.currentTimeMillis();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			
			FormSubmissionData formSubmissionData = new FormSubmissionData();
			try {
				formSubmissionData = (FormSubmissionData) Api.fromJson(jsonString, FormSubmissionData.class);
			} catch (JsonParseException e) {
				Log.ignore(this.getClass(), e);
				throw new EffortError(4001, HttpStatus.PRECONDITION_FAILED);
			} catch (JsonMappingException e) {
				Log.ignore(this.getClass(), e);
				throw new EffortError(4001, HttpStatus.PRECONDITION_FAILED);
			} catch (IOException e) {
				Log.ignore(this.getClass(), e);
				throw new EffortError(4001, HttpStatus.PRECONDITION_FAILED);
			}
			
			Employee employee = webManager.getEmployee(empId+"");
			Map<String, Long> formOldClientKeys = new HashMap<String,Long>();
			Map<String, Long> formNewKeys =new HashMap<String,Long>();
			Map<Long, String> formClientKeys = new HashMap<Long, String>();
			Map<String, Long> customerNewKeys= new HashMap<String,Long>();
			Map<Long, String> formWorkflowStatusClientKeys= new HashMap<Long,String>();
			Map<String, Long> customEntityNewKeys = new HashMap<String,Long>();
			Map<String, Boolean> customerMandatoryFormFieldsMissingMap =  new HashMap<String,Boolean>();
			List<Form> formsNotInDb =  new ArrayList<Form>();
			
			List<Long> addedModifiedFormIds = syncManager.processFormSubmissionSync(formSubmissionData, empId + "", code,
					ipAddress,employee,formOldClientKeys,formNewKeys,formClientKeys,customerNewKeys,formWorkflowStatusClientKeys,customEntityNewKeys,customerMandatoryFormFieldsMissingMap,formsNotInDb,jmsMessages);

			boolean skipValidation = true;
			FormHistoryForForm formHistoryForForm = mainManager.getFromDetailsWithFormId(
					Api.toCSV(addedModifiedFormIds), empId + "", code, null, null, apiLevel, "true", skipValidation,false,null,0);

			String syncResponseStr = Api.toJson(formHistoryForForm);

			Log.info(this.getClass(), "formSubmissionSyncPost Res. EmpId: " + empId + " syncRequestId = "
					+ syncRequestId + ", json:" + syncResponseStr);
				logger.info("formSubmissionSyncPost Res. EmpId: " + empId + " syncRequestId = " + syncRequestId + ", json:"
					+ syncResponseStr);
			return formHistoryForForm;
		} catch (EffortError ee) {
			jmsMessages = new ArrayList<JmsMessage>();
			throw ee;
		} finally {
			syncManager.sendMessages(jmsMessages, empId);
		}
	}
	
}

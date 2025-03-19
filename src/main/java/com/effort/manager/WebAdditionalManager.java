package com.effort.manager;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import com.effort.context.AppContext;
import com.effort.dao.AuditDao;
import com.effort.dao.ConfiguratorDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.Customer;
import com.effort.entity.CustomerField;
import com.effort.entity.FieldRestrictCritria;
import com.effort.entity.FieldSpecRestrictionGroup;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldGroupSpec;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFilteringCritiria;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.VisibilityDependencyCriteria;
import com.effort.entity.WorkSpecSchedulingConfig;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.util.Api;
import com.effort.validators.FormValidator;

@Service
public class WebAdditionalManager {
	
	@Autowired
	private ConfiguratorDao configuratorDao;
	@Autowired
	private WebSupportManager webSupportManager;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private WebManager webManager;
	
	@Autowired
	private Constants constants;
	@Autowired
	private ExtraSupportDao extraSupportDao;
	
	@Autowired
	private MainManager mainManager;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	
	@Autowired
	private WorkFlowExtraDao workFlowExtraDao;
		
	@Autowired
	private MediaManager mediaManager;

	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private ReportConstants reportConstants;
	
	@Autowired
	private FormValidator formValidator;
	
	@Autowired
	private AuditDao auditDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
		
		ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
		
		return extraSupportAdditionalDao;
	}
private ExtendedDao getExtendedDao(){
		
		ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
		
		return extendedDao;
	}
	
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	public void insertActivityForWebNotification(long id, long empId) {

		List<String> empIdsList = webManager.getManagerList(empId + "");
		String empIdcsv = Api.toCSV(empIdsList);
		List<Long> empIds = Api.csvToLongList(empIdcsv);
		extraSupportDao.insertActivityStreamWebNotification(empIds, id);
	}
	public FormSectionFieldSpec getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(String formSpecId, String sectionFieldSpecId)
	{
		return extraSupportDao.getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(formSpecId,sectionFieldSpecId);
	}

	@SuppressWarnings("unchecked")
	public void resolveFieldRestrictCritriaOfNotMine(List<FieldRestrictCritria> fieldRestrictCritrias, String formSpeId,
			String groupIds, int type) {
		if (Api.isEmptyString(groupIds)) {
			groupIds = "-1";
		}
		List<FieldRestrictCritria> fieldRestrictCritriasOfNotMy = extraSupportDao
				.getFieldSpecRestrictedCritiriaOfNotMine(formSpeId, type, groupIds);

		if (fieldRestrictCritriasOfNotMy != null && fieldRestrictCritriasOfNotMy.size() > 0) {

			Iterator<FieldRestrictCritria> itr = fieldRestrictCritriasOfNotMy.iterator();
			while (itr.hasNext()) {
				FieldRestrictCritria criteria = itr.next();

				for (FieldRestrictCritria fieldRestrictCritria : fieldRestrictCritrias) {
					if ((criteria.getFieldSpecId() == fieldRestrictCritria.getFieldSpecId())
							&& (fieldRestrictCritria.getType() == criteria.getType())) {
						itr.remove();
						break;
					}
				}
			}

			Iterator<FieldRestrictCritria> fieldRestrictCritriasIterator = fieldRestrictCritrias.iterator();

			while (fieldRestrictCritriasIterator.hasNext()) {
				FieldRestrictCritria fieldRestrictCritria = fieldRestrictCritriasIterator.next();
				for (FieldRestrictCritria fieldRestrictCritriaOfNotMy : fieldRestrictCritriasOfNotMy) {
					if ((fieldRestrictCritria.getFieldSpecId() == fieldRestrictCritriaOfNotMy.getFieldSpecId())
							&& (fieldRestrictCritria.getType() == 0 || fieldRestrictCritria.getType() == 2)) {
						if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.MAKE_VISIBILITY) {
							fieldRestrictCritria.setType(FieldSpecRestrictionGroup.VISIBILITY_RESTRICTION);
							fieldRestrictCritria.setVisibility(0);
						}
						if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.MAKE_EDIT) {
							fieldRestrictCritria.setType(FieldSpecRestrictionGroup.EDIT_RESTRICTION);
							fieldRestrictCritria.setVisibility(0);
						}
					}

				}
			}

			if (fieldRestrictCritrias.isEmpty() && fieldRestrictCritrias.size() == 0) {
				for (FieldRestrictCritria fieldRestrictCritriaOfNotMy : fieldRestrictCritriasOfNotMy) {
					FieldRestrictCritria newCriteria = new FieldRestrictCritria();
					if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.MAKE_VISIBILITY) {
						newCriteria.setType(FieldSpecRestrictionGroup.VISIBILITY_RESTRICTION);
						newCriteria.setVisibility(0);
						newCriteria.setFieldSpecId(fieldRestrictCritriaOfNotMy.getFieldSpecId());
					}
					if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.MAKE_EDIT) {
						newCriteria.setType(FieldSpecRestrictionGroup.EDIT_RESTRICTION);
						newCriteria.setVisibility(0);
						newCriteria.setFieldSpecId(fieldRestrictCritriaOfNotMy.getFieldSpecId());
					}

					if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.VISIBILITY_RESTRICTION) {
						newCriteria.setType(FieldSpecRestrictionGroup.MAKE_VISIBILITY);
						newCriteria.setVisibility(1);
						newCriteria.setFieldSpecId(fieldRestrictCritriaOfNotMy.getFieldSpecId());
					}
					if (fieldRestrictCritriaOfNotMy.getType() == FieldSpecRestrictionGroup.EDIT_RESTRICTION) {
						newCriteria.setType(FieldSpecRestrictionGroup.MAKE_EDIT);
						newCriteria.setVisibility(1);
						newCriteria.setFieldSpecId(fieldRestrictCritriaOfNotMy.getFieldSpecId());
					}
					fieldRestrictCritrias.add(newCriteria);
				}
			}//Deva,2018-09-26, For emp who has visibile permission and not having edit permission 
			else if (!fieldRestrictCritrias.isEmpty() && !fieldRestrictCritriasOfNotMy.isEmpty()) {
				
				Map<Long,List<FieldRestrictCritria>> specIdAndCritriasOfNotMeListMap = 
						(Map)Api.getGroupedMapFromList(fieldRestrictCritriasOfNotMy, "fieldSpecId",null);
				List<FieldRestrictCritria> critriasOfNotMeListFromMap = null;
				for (FieldRestrictCritria restrictCritria : fieldRestrictCritrias) {
				
					
					if(restrictCritria.getType() != FieldSpecRestrictionGroup.MAKE_VISIBILITY) {
						continue;
					}
					critriasOfNotMeListFromMap = specIdAndCritriasOfNotMeListMap.get(restrictCritria.getFieldSpecId());
					if(critriasOfNotMeListFromMap == null) {
						continue;
					}
					if(critriasOfNotMeListFromMap.get(0).getType() == FieldSpecRestrictionGroup.MAKE_EDIT) {
						restrictCritria.setType(FieldSpecRestrictionGroup.EDIT_RESTRICTION);
						restrictCritria.setVisibility(0);
					}
				}
			}

		}
	}

	
	public boolean resolveVisibilityCriteriaNew(List<FormFieldSpec> fieldSpecs,
			Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForFormFields,
			boolean isConsiderEditRestiction, Map<String, Long> formFieldspecIdExpressionMap,
			Map<Long, FormField> formFieldsMap, Map<Long, FormFieldSpec> formFieldspecMap,
			Map<String, Long> formSectionFieldspecIdExpressionMap,
			Map<String, FormSectionField> formSectionFieldDataMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldspecMap, List<FormSectionSpec> formSectionSpecs,
			Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForSectionFields,
			Map<Long, List<FormSectionField>> formSectionFieldsMap, Map<Long, Integer> sectionSpecMaxInstanceMap,
			Map<String, Boolean> condtionMandatorySectionFieldSpecMap,
			Map<String, Integer> visibilityCondtionSectionFieldSpecMap,
			Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForSections,
			Set<Long> hiddenFields, Set<String> hiddenSectionFields,
			Map<String, CustomerField> customerFieldExpressionMap, Customer customer) {
		boolean hasNewHidden = false;
		for (FormFieldSpec formFieldSpec : fieldSpecs) {
			List<String> result = new ArrayList<String>();

			List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForField = visibilityDependencyCriteriaMapForFormFields
					.get(formFieldSpec.getFieldSpecId());

			if (visibilityDependencyCriteriasForField != null) {

				if (!isConsiderEditRestiction) {
					Iterator<VisibilityDependencyCriteria> visibilityDependencyCriteriaIterator = visibilityDependencyCriteriasForField
							.iterator();

					while (visibilityDependencyCriteriaIterator.hasNext()) {
						VisibilityDependencyCriteria visibilityDependencyCriteria = (VisibilityDependencyCriteria) visibilityDependencyCriteriaIterator
								.next();
						if (visibilityDependencyCriteria.getVisibilityType() == 2) {
							visibilityDependencyCriteriaIterator.remove();
						}
					}
				}
				for (VisibilityDependencyCriteria visibilityDependencyCriteria2 : visibilityDependencyCriteriasForField) {

					String fieldValue = "";
					String givenValue = "";
					Long fieldSpecId = formFieldspecIdExpressionMap
							.get(visibilityDependencyCriteria2.getTargetFieldExpression());

					CustomerField customerField = customerFieldExpressionMap
							.get(visibilityDependencyCriteria2.getTargetFieldExpression());
					
					Long destFieldSpecId = (long) 0;
					if(!Api.isEmptyString(visibilityDependencyCriteria2.getDestinationFieldExpression())) {
						destFieldSpecId = formFieldspecIdExpressionMap
								.get(visibilityDependencyCriteria2.getDestinationFieldExpression());
					}

					if (customerField != null && customer != null) {
						fieldValue = customer.getCustomerFieldValuById(customerField.getCustomerFieldId());

						if (customerField.getVisibilityCheck() == 0
								&& visibilityDependencyCriteriasForField.get(0).isIgnoreHiddenFields()) {
							int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
							if (visibilityDependencyCriteriasForField.size() > 1) {

								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							} else {
								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							}

							continue;
						}

					} else if (fieldSpecId != null) {
						// boolean isFiledDependecyExists=true;

						FormField formField = formFieldsMap.get(fieldSpecId);
						FormField destinationFormField = formFieldsMap.get(destFieldSpecId);
						// if (formField == null) {
						// break;
						// }
						// fieldValue = formField.getFieldValue();

						if (hiddenFields.contains(fieldSpecId)
								&& visibilityDependencyCriteriasForField.get(0).isIgnoreHiddenFields()) {
							int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
							if (visibilityDependencyCriteriasForField.size() > 1) {

								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							} else {
								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							}

							continue;
						}

						FormFieldSpec formFieldSpec1 = formFieldspecMap.get(fieldSpecId);
						if (formField == null && (formFieldSpec1 != null && formFieldSpec1.getFieldType() != 14
								&& formFieldSpec1.getFieldType() != 5)) {
							break;
						}
						if (formField != null) {
							fieldValue = formField.getFieldValue();
						}
						if(destinationFormField != null) {
							givenValue = destinationFormField.getFieldValue();
						}

					} else {

						fieldSpecId = formSectionFieldspecIdExpressionMap
								.get(visibilityDependencyCriteria2.getTargetFieldExpression());
						
						if(!Api.isEmptyString(visibilityDependencyCriteria2.getDestinationFieldExpression())) {
							destFieldSpecId = formSectionFieldspecIdExpressionMap
									.get(visibilityDependencyCriteria2.getDestinationFieldExpression());
						}
						
						if (fieldSpecId == null) {
							continue;
						}

						String key = fieldSpecId + "_1";// consider first
														// instace field only;
						String destinationKey = destFieldSpecId + "_1";

						if (hiddenSectionFields.contains(key)
								&& visibilityDependencyCriteriasForField.get(0).isIgnoreHiddenFields()) {
							int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
							if (visibilityDependencyCriteriasForField.size() > 1) {

								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							} else {
								if (conjunction == 1) {
									result.add("true");
								} else {
									result.add("false");
								}
							}
							// result.add("true");
							continue;
						}

						FormSectionField formSectionField = formSectionFieldDataMap.get(key);
						FormSectionField destinationFormSectionField = formSectionFieldDataMap.get(destinationKey);
						FormSectionFieldSpec formSectionFieldSpec = formSectionFieldspecMap.get(key);
						if (formSectionField == null && formSectionFieldSpec.getFieldType() != 14
								&& formSectionFieldSpec.getFieldType() != 5) {
							break;
						}
						fieldValue = formSectionField.getFieldValue();
						if(!Api.isEmptyString(visibilityDependencyCriteria2.getDestinationFieldExpression())) {
							givenValue = destinationFormSectionField.getFieldValue();
						}
					}

					if(!Api.isEmptyString(visibilityDependencyCriteria2.getDestinationFieldExpression())) {
						webManager.resolveCondition(result, fieldValue, givenValue,
								visibilityDependencyCriteria2.getCondition(),
								visibilityDependencyCriteria2.getFieldDataType());
					}else {
						webManager.resolveCondition(result, fieldValue, visibilityDependencyCriteria2.getValue(),
								visibilityDependencyCriteria2.getCondition(),
								visibilityDependencyCriteria2.getFieldDataType());
					}
				}

				boolean conjunctionResult = false;

				if (visibilityDependencyCriteriasForField != null && visibilityDependencyCriteriasForField.size() > 0) {
					int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();

					if (conjunction == 1) {
						conjunctionResult = !result.contains("false");
					} else {
						conjunctionResult = result.contains("true");
					}
				}

				if (conjunctionResult && visibilityDependencyCriteriasForField != null
						&& result.size() == visibilityDependencyCriteriasForField.size()
						&& visibilityDependencyCriteriasForField.size() != 0) {

					Integer visibilityType = visibilityDependencyCriteriasForField.get(0).getVisibilityType();

					if (visibilityType == 3) // conditional mandatory
					{
						formFieldSpec.setConditionalMandatory(true);
						formFieldSpec.setIsRequired(true);
					} else {
						formFieldSpec.setVisbleOnVisibilityCondition(0);
						if (hiddenFields.add(formFieldSpec.getFieldSpecId())) {
							hasNewHidden = true;
						}
					}
				}
			}
		}

		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			List<FormSectionFieldSpec> formSectionFieldSpecs = formSectionSpec.getFormSectionFieldSpecs();

			for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
				Map<Integer, List<String>> sectionFieldIdAndResult = new HashMap<Integer, List<String>>();

				List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForField = visibilityDependencyCriteriaMapForSectionFields
						.get(formSectionFieldSpec.getSectionFieldSpecId());

				if (visibilityDependencyCriteriasForField != null) {
					if (!isConsiderEditRestiction) {
						Iterator<VisibilityDependencyCriteria> visibilityDependencyCriteriaIterator = visibilityDependencyCriteriasForField
								.iterator();
						while (visibilityDependencyCriteriaIterator.hasNext()) {
							VisibilityDependencyCriteria visibilityDependencyCriteria = (VisibilityDependencyCriteria) visibilityDependencyCriteriaIterator
									.next();
							if (visibilityDependencyCriteria.getVisibilityType() == 2) {
								visibilityDependencyCriteriaIterator.remove();
							}
						}
					}
					Map<Integer, List<String>> formSpecInstanceAndResult = new HashMap<Integer, List<String>>();
					List<String> resultForFormField = new ArrayList<String>();
					boolean isFormFiledDependecyCritiriaExists = false;
					for (VisibilityDependencyCriteria visibilityDependencyCriteria2 : visibilityDependencyCriteriasForField) {
						String fieldValue = "";
						Long fieldSpecId = formFieldspecIdExpressionMap
								.get(visibilityDependencyCriteria2.getTargetFieldExpression());
						if (fieldSpecId != null) {
							FormField formField = formFieldsMap.get(fieldSpecId);
							FormFieldSpec formFieldSpec = formFieldspecMap.get(fieldSpecId);
							if (formField == null
									&& (formFieldSpec.getFieldType() != 14 && formFieldSpec.getFieldType() != 5)) {
								break;
							}
							if (formField != null) {
								fieldValue = formField.getFieldValue();
							}
							
							isFormFiledDependecyCritiriaExists = true;
							
							fieldSpecId = formSectionFieldspecIdExpressionMap
									.get(visibilityDependencyCriteria2.getTargetFieldExpression());
							List<FormSectionField> formSectionFields = formSectionFieldsMap.get(fieldSpecId);
							
							if (formSectionFields == null && visibilityDependencyCriteriasForField.get(0).isIgnoreHiddenFields()
									&& Api.isEmptyString(fieldValue) ) 
							{
								resultForFormField.add(""+true);
								//sectionFieldIdAndResult.put(1, resultForFormField);
								//changed this because of visibility dependency applying only for first instance
								Integer maxInstanceIdForSection = sectionSpecMaxInstanceMap
										.get(formSectionFieldSpec.getSectionSpecId());
								if (maxInstanceIdForSection != null) {
									for (int i = 1; i <= maxInstanceIdForSection; i++) {
										sectionFieldIdAndResult.put(i, resultForFormField);
									}
								}
								continue;
							}
							
							webManager.resolveCondition(resultForFormField, fieldValue,
									visibilityDependencyCriteria2.getValue(),
									visibilityDependencyCriteria2.getCondition(),
									visibilityDependencyCriteria2.getFieldDataType());
							
							if (formSectionFields == null) {
								//sectionFieldIdAndResult.put(1, resultForFormField);
								Integer maxInstanceIdForSection = sectionSpecMaxInstanceMap
										.get(formSectionFieldSpec.getSectionSpecId());
								if (maxInstanceIdForSection != null) {
									for (int i = 1; i <= maxInstanceIdForSection; i++) {
										sectionFieldIdAndResult.put(i, resultForFormField);
									}
								}	
								continue;
							}

							for (FormSectionField formSectionField : formSectionFields) {
								sectionFieldIdAndResult.put(formSectionField.getInstanceId(), resultForFormField);
							}

						} else {
							fieldSpecId = formSectionFieldspecIdExpressionMap
									.get(visibilityDependencyCriteria2.getTargetFieldExpression());
							List<FormSectionField> formSectionFields = formSectionFieldsMap.get(fieldSpecId);
							FormSectionFieldSpec formSectionFieldSpec2 = formSectionFieldspecMap.get(fieldSpecId);
							if (formSectionFields == null) {
								formSectionFields = new ArrayList<FormSectionField>();
								// break;
							}
							List<Integer> processedSectionFieldSpecInstances = new ArrayList<Integer>();

							for (FormSectionField formSectionField : formSectionFields) {

								// String
								// key=formSectionField.getSectionFieldSpecId()+"_"+formSectionField.getInstanceId();
								List<String> result = sectionFieldIdAndResult.get(formSectionField.getInstanceId());
								if (result == null) {
									result = new ArrayList<String>();
									sectionFieldIdAndResult.put(formSectionField.getInstanceId(), result);

								}

								String key = fieldSpecId + "_" + formSectionField.getInstanceId();

								if (hiddenSectionFields.contains(key)
										&& visibilityDependencyCriteriasForField.get(0).isIgnoreHiddenFields()) {
									int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
									if (visibilityDependencyCriteriasForField.size() > 1) {

										if (conjunction == 1) {
											result.add("true");
										} else {
											result.add("false");
										}
									} else {
										if (conjunction == 1) {
											result.add("true");
										} else {
											result.add("false");
										}
									}
									// result.add("true");
									continue;
								}

								processedSectionFieldSpecInstances.add(formSectionField.getInstanceId());
								webManager.resolveCondition(result, formSectionField.getFieldValue(),
										visibilityDependencyCriteria2.getValue(),
										visibilityDependencyCriteria2.getCondition(),
										visibilityDependencyCriteria2.getFieldDataType());
							}
							if (visibilityDependencyCriteria2.getFieldDataType() == 14
									|| visibilityDependencyCriteria2.getFieldDataType() == 5) {
								Integer maxInstanceIdForSection = sectionSpecMaxInstanceMap
										.get(formSectionFieldSpec2.getSectionSpecId());
								if (maxInstanceIdForSection != null) {
									for (int i = 1; i <= maxInstanceIdForSection; i++) {
										if (!processedSectionFieldSpecInstances.contains(i)) {
											// List<String> result =
											// formSpecInstanceAndResult.get(i);
											List<String> result = sectionFieldIdAndResult.get(i);
											if (result == null) {
												result = new ArrayList<String>();
												sectionFieldIdAndResult.put(i, result);

											}
											String key = fieldSpecId + "_" + i;

											if (hiddenSectionFields.contains(key)
													&& visibilityDependencyCriteriasForField.get(0)
															.isIgnoreHiddenFields()) {
												int conjunction = visibilityDependencyCriteriasForField.get(0)
														.getConjunction();
												if (visibilityDependencyCriteriasForField.size() > 1) {

													if (conjunction == 1) {
														result.add("true");
													} else {
														result.add("false");
													}
												} else {
													if (conjunction == 1) {
														result.add("true");
													} else {
														result.add("false");
													}

												}
												// result.add("true");
												continue;
											}
											webManager.resolveCondition(result, null,
													visibilityDependencyCriteria2.getValue(),
													visibilityDependencyCriteria2.getCondition(),
													visibilityDependencyCriteria2.getFieldDataType());
										}
									}
								}
							}

						}
					}

					if (isFormFiledDependecyCritiriaExists) {
						boolean conjunctionResult = false;

						if (visibilityDependencyCriteriasForField != null
								&& visibilityDependencyCriteriasForField.size() > 0) {
							int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();

							if (conjunction == 1) {
								conjunctionResult = !resultForFormField.contains("false");
							} else {
								conjunctionResult = resultForFormField.contains("true");
							}
						}
						if (conjunctionResult && visibilityDependencyCriteriasForField != null
								&& resultForFormField.size() == visibilityDependencyCriteriasForField.size()
								&& visibilityDependencyCriteriasForField.size() != 0) {
							for (Entry<Integer, List<String>> entrySet : sectionFieldIdAndResult.entrySet()) {
								Integer key = entrySet.getKey();
								List<String> result = entrySet.getValue();
								if (visibilityDependencyCriteriasForField != null
										&& visibilityDependencyCriteriasForField.size() > 0) {
									int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
									if (conjunction == 1) {
										conjunctionResult = !result.contains("false");
									} else {
										conjunctionResult = result.contains("true");
									}
								}
								if (conjunctionResult && result.size() == visibilityDependencyCriteriasForField.size()
										&& visibilityDependencyCriteriasForField.size() != 0) {
									String key1 = formSectionFieldSpec.getSectionFieldSpecId() + "_" + key;
									Integer visibilityType = visibilityDependencyCriteriasForField.get(0)
											.getVisibilityType();
									if (visibilityType == 3) // 3 : conditional
																// mandatory
									{
										condtionMandatorySectionFieldSpecMap.put(key1, true);
									} else {
										visibilityCondtionSectionFieldSpecMap.put(key1, 0);

										if (hiddenSectionFields.add(key1)) {
											hasNewHidden = true;
										}
									}

									// if(formSectionFieldDataMap.get(key1)!=null){
									// FormSectionField
									// formSectionField=formSectionFieldDataMap.get(key1);
									// formSectionField.setVisibleOnVisibilityDependencyCritiria(0);
									// formSectionFieldSpec.setVisibleOnVisibilityCondition(0);
									//
									// }
									// formSectionFieldSpec.setVisibleOnVisibilityCondition(0);
								}
							}

						}
					} else {
						boolean conjunctionResult = false;

						for (Entry<Integer, List<String>> entrySet : sectionFieldIdAndResult.entrySet()) {
							Integer key = entrySet.getKey();
							List<String> result = entrySet.getValue();
							if (visibilityDependencyCriteriasForField != null
									&& visibilityDependencyCriteriasForField.size() > 0) {
								int conjunction = visibilityDependencyCriteriasForField.get(0).getConjunction();
								if (conjunction == 1) {
									conjunctionResult = !result.contains("false");
								} else {
									conjunctionResult = result.contains("true");
								}
							}
							if (conjunctionResult && result.size() == visibilityDependencyCriteriasForField.size()
									&& visibilityDependencyCriteriasForField.size() != 0) {
								String key1 = formSectionFieldSpec.getSectionFieldSpecId() + "_" + key;

								Integer visibilityType = visibilityDependencyCriteriasForField.get(0)
										.getVisibilityType();
								if (visibilityType == 3) // 3 : conditional
															// mandatory
								{
									condtionMandatorySectionFieldSpecMap.put(key1, true);
								} else {
									visibilityCondtionSectionFieldSpecMap.put(key1, 0);
									if (hiddenSectionFields.add(key1)) {
										hasNewHidden = true;
									}
								}
								// formSectionFieldSpec.setVisibleOnVisibilityCondition(0);
								// formSectionFieldSpec.setVisibleOnVisibilityCondition(0);
							}
						}

					}

				}
			}

			List<VisibilityDependencyCriteria> visibilityCriteriasForSection = visibilityDependencyCriteriaMapForSections
					.get(formSectionSpec.getSectionSpecId());
			if (visibilityCriteriasForSection != null && !visibilityCriteriasForSection.isEmpty())
				hasNewHidden = resolveSectionVisibilityCriteriaforForm(visibilityCriteriasForSection, formSectionSpec,
						formFieldspecIdExpressionMap, formSectionFieldspecIdExpressionMap, formFieldsMap,
						formFieldspecMap, formSectionFieldsMap, formSectionFieldDataMap, formSectionFieldspecMap,
						visibilityCondtionSectionFieldSpecMap, sectionSpecMaxInstanceMap, hiddenFields,
						hiddenSectionFields);

		}

		return hasNewHidden;
	}
	
	
	public boolean resolveSectionVisibilityCriteriaforForm(
			List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForSections, FormSectionSpec sectionSpec,
			Map<String, Long> formFieldspecIdExpressionMap, Map<String, Long> formSectionFieldspecIdExpressionMap,
			Map<Long, FormField> formFieldsMap, Map<Long, FormFieldSpec> formFieldspecMap,
			Map<Long, List<FormSectionField>> formSectionFieldsMap,
			Map<String, FormSectionField> formSectionFieldDataMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldspecMap,
			Map<String, Integer> visibilityCondtionSectionFieldSpecMap, Map<Long, Integer> sectionSpecMaxInstanceMap,
			Set<Long> hiddenFields, Set<String> hiddenSectionFields) {

		boolean hasNewHiddenField = false;
		if (visibilityDependencyCriteriasForSections != null) {

			List<FormSectionFieldSpec> formSectionFieldSpecs = sectionSpec.getFormSectionFieldSpecs();
			List<String> resultForFormField = new ArrayList<String>();
			for (VisibilityDependencyCriteria visibilityDependencyCriteriasForSection : visibilityDependencyCriteriasForSections) {

				String fieldValue = "";
				Long fieldSpecId = formFieldspecIdExpressionMap
						.get(visibilityDependencyCriteriasForSection.getTargetFieldExpression());
				if (fieldSpecId != null) {
					FormField formField = formFieldsMap.get(fieldSpecId);
					FormFieldSpec formFieldSpec = formFieldspecMap.get(fieldSpecId);
					if (formField == null
							&& (formFieldSpec.getFieldType() != 14 && formFieldSpec.getFieldType() != 5)) {
						break;
					}

					/*
					 * if(hiddenFields.contains(fieldSpecId)) {
					 * if(visibilityDependencyCriteriasForSections.size()>1) {
					 * int conjunction =
					 * visibilityDependencyCriteriasForSections
					 * .get(0).getConjunction(); if(conjunction == 1) {
					 * resultForFormField.add("true"); } else {
					 * resultForFormField.add("false"); } } else {
					 * resultForFormField.add("true"); }
					 * 
					 * continue; }
					 */

					if (formField != null) {
						fieldValue = formField.getFieldValue();
					}

					webManager.resolveCondition(resultForFormField, fieldValue,
							visibilityDependencyCriteriasForSection.getValue(),
							visibilityDependencyCriteriasForSection.getCondition(),
							visibilityDependencyCriteriasForSection.getFieldDataType());
				}

			}

			boolean conjunctionResult = false;

			if (visibilityDependencyCriteriasForSections != null
					&& visibilityDependencyCriteriasForSections.size() > 0) {
				int conjunction = visibilityDependencyCriteriasForSections.get(0).getConjunction();

				if (conjunction == 1) {
					conjunctionResult = !resultForFormField.contains("false");
				} else {
					conjunctionResult = resultForFormField.contains("true");
				}
			}

			if (conjunctionResult && visibilityDependencyCriteriasForSections != null
					&& resultForFormField.size() == visibilityDependencyCriteriasForSections.size()
					&& visibilityDependencyCriteriasForSections.size() != 0) {

				Integer visibilityType = visibilityDependencyCriteriasForSections.get(0).getVisibilityType();

				if (visibilityType == 1) {
					for (FormSectionFieldSpec fsf : formSectionFieldSpecs) {
						fsf.setVisibleOnVisibilityCondition(0);
						if (sectionSpecMaxInstanceMap != null) {
							Integer sectoinInstanceCount = sectionSpecMaxInstanceMap.get(fsf.getSectionSpecId());
							if (sectoinInstanceCount != null && sectoinInstanceCount > 0) {
								for (int instanceIndex = 1; instanceIndex <= sectoinInstanceCount; instanceIndex++) {
									String key = fsf.getSectionFieldSpecId() + "_" + instanceIndex;
									visibilityCondtionSectionFieldSpecMap.put(key, 0);

									if (hiddenSectionFields.add(key)) {
										hasNewHiddenField = true;
									}

								}
							}

						}

					}
					sectionSpec.setMinEntrys(0);
				}

			}
		}

		return hasNewHiddenField;

	}
	
	
	

	public WorkSpecSchedulingConfig getWorkSpecSchedulingConfig(Long workSpecId) {

		return extraSupportDao.getWorkSpecSchedulingConfig(workSpecId);
	}
	public List<FormFieldGroupSpec> getFormFieldGroupSpecForIn(String formSpecIds) {
		return extraSupportDao.getFormFieldGroupSpecForIn(formSpecIds);
	}
	
	
	 public Map<Long, List<FormFilteringCritiria>> getFormFilteringCritiriasMap(String formSpeId,int type){
		   
		   List<FormFilteringCritiria> allFormFilteringCritirias = extraSupportDao
					.getFormFilteringCritirias(formSpeId);

			List<FormFilteringCritiria> formFilteringCritirias = new ArrayList<FormFilteringCritiria>();
			for(FormFilteringCritiria formFilteringCritiria : allFormFilteringCritirias){
				
				if(Constants.FORM_FIELD_TYPE_FORM == formFilteringCritiria.getFieldType())
				{
					formFilteringCritirias.add(formFilteringCritiria);
				}
				/*else if(Constants.FORM_FIELD_TYPE_CUSTOMER == formFilteringCritiria.getFieldType())
				{
					customerFormFilteringCritirias.add(formFilteringCritiria);
				}*/
			}
			
			Map<Long, List<FormFilteringCritiria>> formFilteringCritiriasMap = getFormFilteringCritiriaMap(
					formFilteringCritirias,type);
			
			return formFilteringCritiriasMap;
			
	   }
}

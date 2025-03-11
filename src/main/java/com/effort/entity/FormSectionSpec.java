package com.effort.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormSectionSpec extends FormFieldSpecMaster{
	private long sectionSpecId;
	private long formSpecId;
	private int companyId;
	private String sectionTitle;
	private int minEntrys;
	private int maxEntrys;
	private int displayOrder;
	private String expression;
	private Integer pageId;
	private boolean deleted;
	private boolean instanceNumbering;
	@JsonProperty(access = Access.READ_ONLY)
	private List<FormSectionFieldSpec> formSectionFieldSpecs;
	private String formSectionFieldSpecsJson;
	
	private String fieldLabelError;
	
	private int type;
	
	private boolean deleteSectionInstnace;
	private boolean addSectionInstance;
	private boolean customerPresent;
	private Long initialFormSectionSpecId;
	private Long skeletonFormSectionSpecId;
	private String autoCreateFieldTypeExtra;
	private boolean autoCreateSectionInstance;
	private String autoCreateFieldTypeExtraError;
	private String autoCreateEntityJson;
	private String groupByCategories;
	private boolean manualSelection; 
	
	private int Visible= 1;
	private String visibilityDependencyCriteria;

	@JsonProperty(access = Access.READ_ONLY)
	private String sectionVisbleRestrictedEmpGrps;
	
	private String modifiedTime;
	private boolean filterAutoCreateSectionInstances;
	
	private String externalLabel;
	private Integer selectionTypeInDevice = 1;
	private String sectionSpecUniqueId;
	
	private int displayType = 3;
	private int autoCreateFieldCategoryType = 1;
	private boolean defaultInstanceCollapse;
	private Integer enableMediaFormatRestriction = 0;
	private String allowRequiredFormat;
	private Integer enableMappedTerritoriesRestriction = 0;
	
	private Integer empGroupVisibleType = 1;

	
	public final static int AUTO_CREATE_FIELD_CATEGORY_TYPE_FOR_ENTITY_SPECID = 1; 
	public final static int AUTO_CREATE_FIELD_CATEGORY_TYPE_FOR_EXPRESSION = 2;
	
	
	private String customEntityAutoCreateFieldTypeExtra;
	private boolean customEntityAutoCreateSectionInstance;
	private String customEntityAutoCreateFieldTypeExtraError;
	private Integer autoCreateFieldType;
	private String customEntityAutoCreateFieldSelector;
	
	private Integer reminderConfigEnabled;
	private String remainderRemarksFields;
	private Integer showRemainderBefore;
	

	public Long getInitialFormSectionSpecId() {
		return initialFormSectionSpecId;
	}
	public void setInitialFormSectionSpecId(Long initialFormSectionSpecId) {
		this.initialFormSectionSpecId = initialFormSectionSpecId;
	}
	public Long getSkeletonFormSectionSpecId() {
		return skeletonFormSectionSpecId;
	}
	public void setSkeletonFormSectionSpecId(Long skeletonFormSectionSpecId) {
		this.skeletonFormSectionSpecId = skeletonFormSectionSpecId;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public long getSectionSpecId() {
		return sectionSpecId;
	}
	public void setSectionSpecId(long sectionSpecId) {
		this.sectionSpecId = sectionSpecId;
	}
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	@JsonProperty("minEntries")
	public int getMinEntrys() {
		return minEntrys;
	}
	public void setMinEntrys(int minEntrys) {
		this.minEntrys = minEntrys;
	}
	@JsonProperty("maxEntries")
	public int getMaxEntrys() {
		return maxEntrys;
	}
	public void setMaxEntrys(int maxEntrys) {
		this.maxEntrys = maxEntrys;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Integer getPageId() {
		return pageId == null ? 0 : pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
		
	public boolean isInstanceNumbering() {
		return instanceNumbering;
	}
	public void setInstanceNumbering(boolean instanceNumbering) {
		this.instanceNumbering = instanceNumbering;
	}
	
	@JsonIgnore
	public int getCompanyId() {
		return companyId;
	}
	@JsonIgnore
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
	public void setType(int type) {
		this.type = type;
	}
	@JsonIgnore
	public boolean isCustomerPresent() {
		return customerPresent;
	}
	@JsonIgnore
	public void setCustomerPresent(boolean customerPresent) {
		this.customerPresent = customerPresent;
	}
	
	public List<FormSectionFieldSpec> getFormSectionFieldSpecs() {
		return formSectionFieldSpecs;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public List<FormSectionFieldSpec> getFormSectionFieldSpecsByFieldDisplayOrder() {
		if(formSectionFieldSpecs!=null && !formSectionFieldSpecs.isEmpty()){
			Collections.sort(formSectionFieldSpecs, new Comparator<FormSectionFieldSpec>() {

				@Override
				public int compare(FormSectionFieldSpec o1, FormSectionFieldSpec o2) {
					return o1.getDisplayOrder()-o2.getDisplayOrder();
				}
			});
		}
		return formSectionFieldSpecs;
	}
	
	public void setFormSectionFieldSpecs(
			List<FormSectionFieldSpec> formSectionFieldSpecs) {
		this.formSectionFieldSpecs = formSectionFieldSpecs;
	}
	@JsonIgnore
	public String getFieldLabelError() {
		return fieldLabelError;
	}
	@JsonIgnore
	public void setFieldLabelError(String fieldLabelError) {
		this.fieldLabelError = fieldLabelError;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public int getItemCount(){
		return formSectionFieldSpecs == null ? 0 : formSectionFieldSpecs.size();
	}
	
	
	public String toCSV() {
		return "[sectionSpecId=" + sectionSpecId
				+ ", formSpecId=" + formSpecId + ", companyId=" + companyId
				+ ", sectionTitle=" + sectionTitle + ", minEntrys=" + minEntrys
				+ ", maxEntrys=" + maxEntrys + ", displayOrder=" + displayOrder
				+ ", deleted=" + deleted + ", formSectionFieldSpecs="
				+ formSectionFieldSpecs + ", type=" + type
				+ ", customerPresent=" + customerPresent + "]";
	}
	

	
	@Override
	public String toString() {
		return "FormSectionSpec [sectionSpecId=" + sectionSpecId
				+ ", formSpecId=" + formSpecId + ", companyId=" + companyId
				+ ", sectionTitle=" + sectionTitle + ", minEntrys=" + minEntrys
				+ ", maxEntrys=" + maxEntrys + ", displayOrder=" + displayOrder
				+ ", expression=" + expression + ", pageId=" + pageId
				+ ", deleted=" + deleted + ", instanceNumbering="
				+ instanceNumbering + ", formSectionFieldSpecs="
				+ formSectionFieldSpecs + ", formSectionFieldSpecsJson="
				+ formSectionFieldSpecsJson + ", fieldLabelError="
				+ fieldLabelError + ", type=" + type + ", customerPresent="
				+ customerPresent + ", initialFormSectionSpecId="
				+ initialFormSectionSpecId + ", skeletonFormSectionSpecId="
				+ skeletonFormSectionSpecId + ", autoCreateFieldTypeExtra="
				+ autoCreateFieldTypeExtra + ", autoCreateSectionInstance="
				+ autoCreateSectionInstance
				+ ", autoCreateFieldTypeExtraError="
				+ autoCreateFieldTypeExtraError + ", autoCreateEntityJson="
				+ autoCreateEntityJson + "]";
	}
	public String getAutoCreateFieldTypeExtra() {
		return autoCreateFieldTypeExtra;
	}
	public void setAutoCreateFieldTypeExtra(String autoCreateFieldTypeExtra) {
		this.autoCreateFieldTypeExtra = autoCreateFieldTypeExtra;
	}
	
	public boolean isAutoCreateSectionInstance() {
		return autoCreateSectionInstance;
	}
	public void setAutoCreateSectionInstance(boolean autoCreateSectionInstance) {
		this.autoCreateSectionInstance = autoCreateSectionInstance;
	}
	public String getGroupByCategories() {
		return groupByCategories;
	}
	public void setGroupByCategories(String groupByCategories) {
		this.groupByCategories = groupByCategories;
	}
	public boolean isManualSelection() {
		return manualSelection;
	}
	public void setManualSelection(boolean manualSelection) {
		this.manualSelection = manualSelection;
	}
	@JsonIgnore
	public String getAutoCreateFieldTypeExtraError() {
		return autoCreateFieldTypeExtraError;
	}
	@JsonIgnore
	public void setAutoCreateFieldTypeExtraError(
			String autoCreateFieldTypeExtraError) {
		this.autoCreateFieldTypeExtraError = autoCreateFieldTypeExtraError;
	}
	@JsonIgnore
	public String getFormSectionFieldSpecsJson() {
		return formSectionFieldSpecsJson;
	}
	@JsonIgnore
	public void setFormSectionFieldSpecsJson(String formSectionFieldSpecsJson) {
		this.formSectionFieldSpecsJson = formSectionFieldSpecsJson;
	}
	@JsonIgnore
	public String getAutoCreateEntityJson() {
		return autoCreateEntityJson;
	}
	@JsonIgnore
	public void setAutoCreateEntityJson(String autoCreateEntityJson) {
		this.autoCreateEntityJson = autoCreateEntityJson;
	}
	public String getVisibilityDependencyCriteria() {
		return visibilityDependencyCriteria;
	}
	public void setVisibilityDependencyCriteria(String visibilityDependencyCriteria) {
		this.visibilityDependencyCriteria = visibilityDependencyCriteria;
	}
	
	public String getSectionVisbleRestrictedEmpGrps() {
		return sectionVisbleRestrictedEmpGrps;
	}
	
	public void setSectionVisbleRestrictedEmpGrps(String sectionVisbleRestrictedEmpGrps) {
		this.sectionVisbleRestrictedEmpGrps = sectionVisbleRestrictedEmpGrps;
	}
	public int getVisible() {
		return Visible;
	}
	public void setVisible(int visible) {
		Visible = visible;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	//Deva,2017-10-04, 
	//Not to send client, for web use only,not a column in data base
	public boolean isFilterAutoCreateSectionInstances() {
		return filterAutoCreateSectionInstances;
	}
	public void setFilterAutoCreateSectionInstances(boolean filterAutoCreateSectionInstances) {
		this.filterAutoCreateSectionInstances = filterAutoCreateSectionInstances;
	}
	public String getExternalLabel() {
		return externalLabel;
	}
	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}
	public Integer getSelectionTypeInDevice() {
		return selectionTypeInDevice;
	}
	public void setSelectionTypeInDevice(Integer selectionTypeInDevice) {
		this.selectionTypeInDevice = selectionTypeInDevice;
	}
	public String getSectionSpecUniqueId() {
		return sectionSpecUniqueId;
	}
	public void setSectionSpecUniqueId(String sectionSpecUniqueId) {
		this.sectionSpecUniqueId = sectionSpecUniqueId;
	}
	
	public int getDisplayType() {
		return displayType;
	}
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}
	public int getAutoCreateFieldCategoryType() {
		return autoCreateFieldCategoryType;
	}
	public void setAutoCreateFieldCategoryType(int autoCreateFieldCategoryType) {
		this.autoCreateFieldCategoryType = autoCreateFieldCategoryType;
	}
	public boolean isDeleteSectionInstnace() {
		return deleteSectionInstnace;
	}
	public void setDeleteSectionInstnace(boolean deleteSectionInstnace) {
		this.deleteSectionInstnace = deleteSectionInstnace;
	}
	public boolean isAddSectionInstance() {
		return addSectionInstance;
	}
	public void setAddSectionInstance(boolean addSectionInstance) {
		this.addSectionInstance = addSectionInstance;
	}
	public boolean isDefaultInstanceCollapse() {
		return defaultInstanceCollapse;
	}
	public void setDefaultInstanceCollapse(boolean defaultInstanceCollapse) {
		this.defaultInstanceCollapse = defaultInstanceCollapse;
	}
	public Integer getEnableMediaFormatRestriction() {
		return enableMediaFormatRestriction;
	}
	public void setEnableMediaFormatRestriction(Integer enableMediaFormatRestriction) {
		this.enableMediaFormatRestriction = enableMediaFormatRestriction;
	}
	public String getAllowRequiredFormat() {
		return allowRequiredFormat;
	}
	public void setAllowRequiredFormat(String allowRequiredFormat) {
		this.allowRequiredFormat = allowRequiredFormat;
	}
	
	public Integer getEnableMappedTerritoriesRestriction() {
		return enableMappedTerritoriesRestriction;
	}

	public void setEnableMappedTerritoriesRestriction(Integer enableMappedTerritoriesRestriction) {
		this.enableMappedTerritoriesRestriction = enableMappedTerritoriesRestriction;
	}
	public Integer getEmpGroupVisibleType() {
		return empGroupVisibleType;
	}
	public void setEmpGroupVisibleType(Integer empGroupVisibleType) {
		this.empGroupVisibleType = empGroupVisibleType;
	}
	public String getCustomEntityAutoCreateFieldTypeExtra() {
		return customEntityAutoCreateFieldTypeExtra;
	}
	public void setCustomEntityAutoCreateFieldTypeExtra(String customEntityAutoCreateFieldTypeExtra) {
		this.customEntityAutoCreateFieldTypeExtra = customEntityAutoCreateFieldTypeExtra;
	}
	public boolean isCustomEntityAutoCreateSectionInstance() {
		return customEntityAutoCreateSectionInstance;
	}
	public void setCustomEntityAutoCreateSectionInstance(boolean customEntityAutoCreateSectionInstance) {
		this.customEntityAutoCreateSectionInstance = customEntityAutoCreateSectionInstance;
	}
	public String getCustomEntityAutoCreateFieldTypeExtraError() {
		return customEntityAutoCreateFieldTypeExtraError;
	}
	public void setCustomEntityAutoCreateFieldTypeExtraError(String customEntityAutoCreateFieldTypeExtraError) {
		this.customEntityAutoCreateFieldTypeExtraError = customEntityAutoCreateFieldTypeExtraError;
	}
	public Integer getAutoCreateFieldType() {
		return autoCreateFieldType;
	}
	public void setAutoCreateFieldType(Integer autoCreateFieldType) {
		this.autoCreateFieldType = autoCreateFieldType;
	}
	public String getCustomEntityAutoCreateFieldSelector() {
		return customEntityAutoCreateFieldSelector;
	}
	public void setCustomEntityAutoCreateFieldSelector(String customEntityAutoCreateFieldSelector) {
		this.customEntityAutoCreateFieldSelector = customEntityAutoCreateFieldSelector;
	}
	public Integer getReminderConfigEnabled() {
		return reminderConfigEnabled;
	}
	public void setReminderConfigEnabled(Integer reminderConfigEnabled) {
		this.reminderConfigEnabled = reminderConfigEnabled;
	}
	public String getRemainderRemarksFields() {
		return remainderRemarksFields;
	}
	public void setRemainderRemarksFields(String remainderRemarksFields) {
		this.remainderRemarksFields = remainderRemarksFields;
	}
	public Integer getShowRemainderBefore() {
		return showRemainderBefore;
	}
	public void setShowRemainderBefore(Integer showRemainderBefore) {
		this.showRemainderBefore = showRemainderBefore;
	}
	
	
	
	
}

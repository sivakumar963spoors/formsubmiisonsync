package com.effort.beans.http.response.extra;

import java.util.List;

import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.CustomerFilteringCritiria;
import com.effort.entity.EmployeeFilteringCritiria;
import com.effort.entity.FieldSpecFilter;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.FormCleanUpRule;
import com.effort.entity.FormFieldGroupSpec;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormFieldSpecsExtra;
import com.effort.entity.FormFieldsColorDependencyCriterias;
import com.effort.entity.FormFilteringCritiria;
import com.effort.entity.FormPageSpec;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionFieldSpecValidValue;
import com.effort.entity.FormSectionFieldSpecsExtra;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.FormSpecConfigSaveOnOtpVerify;
import com.effort.entity.ListFilteringCritiria;
import com.effort.entity.OfflineCustomEntityUpdateConfiguration;
import com.effort.entity.OfflineListUpdateConfiguration;
import com.effort.entity.RemainderFieldsMap;
import com.effort.entity.StockFormConfiguration;
import com.effort.entity.VisibilityDependencyCriteria;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormSpecContainer {
	private List<FormPageSpec> pageSpecs;
	private List<FormSectionFieldSpec> sectionFields;
	private List<FormFieldSpec> fields;
	private List<FormFieldSpecsExtra> fieldsExtra;
	private List<FormSpec> formSpecs;
	private List<FormFieldSpecValidValue> fieldValidValues;
	private List<FormSectionSpec> sections;
	private List<FormSectionFieldSpecsExtra> sectionFieldsExtra;
	private List<FormSectionFieldSpecValidValue> sectionFieldValidValues;
	private List<FormFieldGroupSpec> formFieldGroupSpecs;
	private List<ListFilteringCritiria>listFilteringCriterias;
	private List<VisibilityDependencyCriteria>visibilityDependencyCriterias;
	@JsonProperty("formFieldBackgroundColorDependency")
	private List<FormFieldsColorDependencyCriterias> formFieldsColorDependencyCriterias;
	private List<RemainderFieldsMap> remainderFieldsMap;
	@JsonProperty("formCleanUpRules")
	private List<FormCleanUpRule> formCleanUpRule;
	private List<EmployeeFilteringCritiria> employeeFilteringCriterias;
	private List<CustomerFilteringCritiria> customerFilteringCriterias;
	private List<FieldValidationCritiria> fieldValidationCritirias;
	private List<FormFilteringCritiria> formFilteringCriterias;
	private List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias;
	private List<CustomEntityFilteringCritiria> customEntityFilteringCritirias;
	private List<StockFormConfiguration> stockFormConfigurations;
	private List<OfflineListUpdateConfiguration> offlineListUpdateConfigurations;
	private List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfigurations;
	private List<FieldSpecFilter> formFieldSpecFilters;
	private List<FieldSpecFilter> formSectionFieldSpecFilters;
	private List<FormSpecConfigSaveOnOtpVerify> saveFormOnOtpVerify;
	public List<FormSpec> getFormSpecs() {
		return formSpecs;
	}
	public void setFieldsExtra(List<FormFieldSpecsExtra> fieldsExtra) {
		this.fieldsExtra = fieldsExtra;
	}
	
	public void setFields(List<FormFieldSpec> fields) {
		this.fields = fields;
	}

	public void setFieldValidValues(List<FormFieldSpecValidValue> fieldValidValues) {
		this.fieldValidValues = fieldValidValues;
	}
	public void setPageSpecs(List<FormPageSpec> pageSpecs) {
		this.pageSpecs = pageSpecs;
	}
	public void setSections(List<FormSectionSpec> sections) {
		this.sections = sections;
	}
	public void setSectionFields(List<FormSectionFieldSpec> sectionFields) {
		this.sectionFields = sectionFields;
	}
	public void setSectionFieldsExtra(
			List<FormSectionFieldSpecsExtra> sectionFieldsExtra) {
		this.sectionFieldsExtra = sectionFieldsExtra;
	}

	public void setSectionFieldValidValues(
			List<FormSectionFieldSpecValidValue> sectionFieldValidValues) {
		this.sectionFieldValidValues = sectionFieldValidValues;
	}
	public void setListFilteringCriterias(List<ListFilteringCritiria> listFilteringCriterias) {
		this.listFilteringCriterias = listFilteringCriterias;
	}


	public void setFormFieldGroupSpecs(List<FormFieldGroupSpec> formFieldGroupSpecs) {
		this.formFieldGroupSpecs = formFieldGroupSpecs;
	}
	public void setVisibilityDependencyCriterias(List<VisibilityDependencyCriteria> visibilityDependencyCriterias) {
		this.visibilityDependencyCriterias = visibilityDependencyCriterias;
	}

	public void setFormFieldsColorDependencyCriterias(
			List<FormFieldsColorDependencyCriterias> formFieldsColorDependencyCriterias) {
		this.formFieldsColorDependencyCriterias = formFieldsColorDependencyCriterias;
	}
	public void setFormCleanUpRule(List<FormCleanUpRule> formCleanUpRule) {
		this.formCleanUpRule = formCleanUpRule;
	}
	public void setRemainderFieldsMap(List<RemainderFieldsMap> remainderFieldsMap) {
		this.remainderFieldsMap = remainderFieldsMap;
	}
	public void setCustomerAutoFilteringCritirias(List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias) {
		this.customerAutoFilteringCritirias = customerAutoFilteringCritirias;
	}
	public void setCustomerFilteringCriterias(
			List<CustomerFilteringCritiria> customerFilteringCriterias) {
		this.customerFilteringCriterias = customerFilteringCriterias;
	}
	public void setEmployeeFilteringCriterias(List<EmployeeFilteringCritiria> employeeFilteringCriterias) {
		this.employeeFilteringCriterias = employeeFilteringCriterias;
	}
	public void setFieldValidationCritirias(List<FieldValidationCritiria> fieldValidationCritirias) {
		this.fieldValidationCritirias = fieldValidationCritirias;
	}
	public void setFormFilteringCriterias(
			List<FormFilteringCritiria> formFilteringCriterias) {
		this.formFilteringCriterias = formFilteringCriterias;

	}
	public void setCustomEntityFilteringCritirias(List<CustomEntityFilteringCritiria> customEntityFilteringCritirias) {
		this.customEntityFilteringCritirias = customEntityFilteringCritirias;
	}
	public void setStockFormConfigurations(List<StockFormConfiguration> stockFormConfigurations) {
		this.stockFormConfigurations = stockFormConfigurations;
	}
	public void setOfflineListUpdateConfigurations(
			List<OfflineListUpdateConfiguration> offlineListUpdateConfigurations) {
		this.offlineListUpdateConfigurations = offlineListUpdateConfigurations;
	}
	public void setOfflineCustomEntityUpdateConfigurations(
			List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfigurations) {
		this.offlineCustomEntityUpdateConfigurations = offlineCustomEntityUpdateConfigurations;
	}
	public void setFormFieldSpecFilters(List<FieldSpecFilter> formFieldSpecFilters) {
		this.formFieldSpecFilters = formFieldSpecFilters;
	}
	public void setFormSectionFieldSpecFilters(
			List<FieldSpecFilter> formSectionFieldSpecFilters) {
		this.formSectionFieldSpecFilters = formSectionFieldSpecFilters;
	}
	public void setSaveFormOnOtpVerify(List<FormSpecConfigSaveOnOtpVerify> saveFormOnOtpVerify) {
		this.saveFormOnOtpVerify = saveFormOnOtpVerify;
	}
}

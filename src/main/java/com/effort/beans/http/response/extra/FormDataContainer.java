package com.effort.beans.http.response.extra;





import java.util.ArrayList;
import java.util.List;

import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormSectionField;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDataContainer {
	private List<Form> added;
	private List<Form> modified;
	private List<Long> deleted;
	
	private List<FormField> fields;
	private List<FormSectionField> sectionFields;
	
	private List<RichTextFormField> richTextFormField;
	private List<RichTextFormSectionField> richTextFormSectionField;
	

	public FormDataContainer() {
		this.added = new ArrayList<Form>();
		this.modified = new ArrayList<Form>();
		this.deleted = new ArrayList<Long>();
		this.fields = new ArrayList<FormField>();
		this.sectionFields = new ArrayList<FormSectionField>();
		this.setRichTextFormField(new ArrayList<RichTextFormField>());
		this.setRichTextFormSectionField(new ArrayList<RichTextFormSectionField>());
	}

	
	public List<Form> getAdded() {
		return added;
	}

	public void setAdded(List<Form> added) {
		this.added = added;
	}

	public List<Form> getModified() {
		return modified;
	}

	public void setModified(List<Form> modified) {
		this.modified = modified;
	}

	public List<Long> getDeleted() {
		return deleted;
	}

	public void setDeleted(List<Long> deleted) {
		this.deleted = deleted;
	}

	public List<FormField> getFields() {
		return fields;
	}

	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}

	public List<FormSectionField> getSectionFields() {
		return sectionFields;
	}

	public void setSectionFields(List<FormSectionField> sectionFields) {
		this.sectionFields = sectionFields;
	}

	public List<RichTextFormField> getRichTextFormField() {
		return richTextFormField;
	}

	public void setRichTextFormField(List<RichTextFormField> richTextFormField) {
		this.richTextFormField = richTextFormField;
	}

	public List<RichTextFormSectionField> getRichTextFormSectionField() {
		return richTextFormSectionField;
	}

	public void setRichTextFormSectionField(List<RichTextFormSectionField> richTextFormSectionField) {
		this.richTextFormSectionField = richTextFormSectionField;
	}

	
}

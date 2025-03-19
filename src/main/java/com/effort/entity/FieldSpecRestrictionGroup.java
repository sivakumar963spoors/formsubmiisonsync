package com.effort.entity;


public class FieldSpecRestrictionGroup {
	public static final int VISIBILITY_RESTRICTION=1;// making it invisible
	public static final int EDIT_RESTRICTION=2; // making it uneditable
	
	public static final int MAKE_VISIBILITY=3;// making it visible
	public static final int MAKE_EDIT=4; // making it editable
	
	public static final int FIELD_IS_FORMFIELD=1;
	public static final int FIELD_IS_SECTIONFIELD=2;
	
	public static final int TYPE_SECTION = 3;
	
	long id;
	long formSpecId;
	long formSectionFieldSpecId;
	long formFieldSpecId;
	int type;//this will tell type of Restriction like visibility or edit
	long  groupId;
	int fieldType;//it will  tell wether field is sectin field or form field
	
	private String formfieldSpecgroupIds;

	public String getFormfieldSpecgroupIds() {
		return formfieldSpecgroupIds;
	}
	public void setFormfieldSpecgroupIds(String formfieldSpecgroupIds) {
		this.formfieldSpecgroupIds = formfieldSpecgroupIds;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public long getFormSectionFieldSpecId() {
		return formSectionFieldSpecId;
	}
	public void setFormSectionFieldSpecId(long formSectionFieldSpecId) {
		this.formSectionFieldSpecId = formSectionFieldSpecId;
	}
	public long getFormFieldSpecId() {
		return formFieldSpecId;
	}
	public void setFormFieldSpecId(long formFieldSpecId) {
		this.formFieldSpecId = formFieldSpecId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	
}

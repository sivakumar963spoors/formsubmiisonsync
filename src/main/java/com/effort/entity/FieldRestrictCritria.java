package com.effort.entity;

public class FieldRestrictCritria {
	
	private Long id;
	private int type;
	private int visibility;
	private long fieldSpecId;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}

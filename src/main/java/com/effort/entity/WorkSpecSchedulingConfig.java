package com.effort.entity;


import java.util.List;

public class WorkSpecSchedulingConfig {

	public static final short WORK_INVITATION_TYPE= 2;
	public static final short WORK_DIRECT_ASSIGNMENT_TYPE= 1;
	public static final short WORK_GROUP_BASED_ASSIGNMENT= 3;
	
	public static final int EMPS_HAVE_LESS_WORK_LOAD = 1;
	public static final int EMPS_HAVE_NO_INCOMPLETE_WORKS = 2;
	
	private long id;
	private long workSpecId;
	private boolean enableWorkScheduling = true;
	private Short assignmentType;
	private boolean pickFromLessWorkLoad;
	private Integer primaryRadius;
	//private Integer secondaryRadius;
	private Integer primaryLastKnownThreshold; //Employee Known Location time of Threshold
	//private Integer secondaryLastKnownThreshold; //Employee Known Location time of Threshold
	private Integer maxInvitationsCount;
	//private Integer bufferTimeAfterWorkingHours;
	private Integer waitAfterReachingLocation;
	private String createdTime;
	private String modifiedTime;
	private boolean areaGroupBasedWorkAssignment;
	private boolean considerLeaveStatus;
	private boolean considerWeeklyOffs;
	private boolean considerHolidays;
	private boolean listBasedAssignment;
	private Integer workLoadType;
	private boolean pickFromNoIncompleteWorks;
	private Long modifiedBy;
	private Integer employeeEtaInMins;
	private Integer groupBasedAssignmentType;
	
	private boolean enableWorkInvitationCriteriaCondition;

    private Integer conjunction;  
    
	private Boolean weightageBased;
	private Boolean considerLeaves;
	private Boolean considerSignIn;
	private Long groupId;
	private String workFieldSpecUniqueId;
    private String considerWorkFieldSpecUniqueId;
	
   
	
	public boolean isEnableWorkScheduling() {
		return enableWorkScheduling;
	}
	public void setEnableWorkScheduling(boolean enableWorkScheduling) {
		this.enableWorkScheduling = enableWorkScheduling;
	}
	public Short getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(Short assignmentType) {
		this.assignmentType = assignmentType;
	}
	public Integer getPrimaryRadius() {
		return primaryRadius;
	}
	public void setPrimaryRadius(Integer primaryRadius) {
		this.primaryRadius = primaryRadius;
	}
	/*public Integer getSecondaryRadius() {
		return secondaryRadius;
	}
	public void setSecondaryRadius(Integer secondaryRadius) {
		this.secondaryRadius = secondaryRadius;
	}*/
	public Integer getPrimaryLastKnownThreshold() {
		return primaryLastKnownThreshold;
	}
	public void setPrimaryLastKnownThreshold(Integer primaryLastKnownThreshold) {
		this.primaryLastKnownThreshold = primaryLastKnownThreshold;
	}
	public Integer getMaxInvitationsCount() {
		return maxInvitationsCount;
	}
	public void setMaxInvitationsCount(Integer maxInvitationsCount) {
		this.maxInvitationsCount = maxInvitationsCount;
	}
	/*public Integer getBufferTimeAfterWorkingHours() {
		return bufferTimeAfterWorkingHours;
	}
	public void setBufferTimeAfterWorkingHours(Integer bufferTimeAfterWorkingHours) {
		this.bufferTimeAfterWorkingHours = bufferTimeAfterWorkingHours;
	}
	public Integer getSecondaryLastKnownThreshold() {
		return secondaryLastKnownThreshold;
	}
	public void setSecondaryLastKnownThreshold(Integer secondaryLastKnownThreshold) {
		this.secondaryLastKnownThreshold = secondaryLastKnownThreshold;
	}*/
	
	public long getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(long workSpecId) {
		this.workSpecId = workSpecId;
	}
	public Integer getWaitAfterReachingLocation() {
		return waitAfterReachingLocation;
	}
	public void setWaitAfterReachingLocation(Integer waitAfterReachingLocation) {
		this.waitAfterReachingLocation = waitAfterReachingLocation;
	}
	public boolean isPickFromLessWorkLoad() {
		return pickFromLessWorkLoad;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public void setPickFromLessWorkLoad(boolean pickFromLessWorkLoad) {
		this.pickFromLessWorkLoad = pickFromLessWorkLoad;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public boolean isAreaGroupBasedWorkAssignment() {
		return areaGroupBasedWorkAssignment;
	}
	public void setAreaGroupBasedWorkAssignment(boolean areaGroupBasedWorkAssignment) {
		this.areaGroupBasedWorkAssignment = areaGroupBasedWorkAssignment;
	}
	public boolean isConsiderLeaveStatus() {
		return considerLeaveStatus;
	}
	public void setConsiderLeaveStatus(boolean considerLeaveStatus) {
		this.considerLeaveStatus = considerLeaveStatus;
	}
	public boolean isConsiderWeeklyOffs() {
		return considerWeeklyOffs;
	}
	public void setConsiderWeeklyOffs(boolean considerWeeklyOffs) {
		this.considerWeeklyOffs = considerWeeklyOffs;
	}
	public boolean isConsiderHolidays() {
		return considerHolidays;
	}
	public void setConsiderHolidays(boolean considerHolidays) {
		this.considerHolidays = considerHolidays;
	}
	public boolean isListBasedAssignment() {
		return listBasedAssignment;
	}
	public void setListBasedAssignment(boolean listBasedAssignment) {
		this.listBasedAssignment = listBasedAssignment;
	}
	public boolean isPickFromNoIncompleteWorks() {
		return pickFromNoIncompleteWorks;
	}
	public void setPickFromNoIncompleteWorks(boolean pickFromNoIncompleteWorks) {
		this.pickFromNoIncompleteWorks = pickFromNoIncompleteWorks;
	}
	public Integer getWorkLoadType() {
		return workLoadType;
	}
	public void setWorkLoadType(Integer workLoadType) {
		this.workLoadType = workLoadType;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public boolean isEnableWorkInvitationCriteriaCondition() {
		return enableWorkInvitationCriteriaCondition;
	}
	public void setEnableWorkInvitationCriteriaCondition(boolean enableWorkInvitationCriteriaCondition) {
		this.enableWorkInvitationCriteriaCondition = enableWorkInvitationCriteriaCondition;
	}
	public Integer getConjunction() {
		return conjunction;
	}
	public void setConjunction(Integer conjunction) {
		this.conjunction = conjunction;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getEmployeeEtaInMins() {
		return employeeEtaInMins;
	}
	public void setEmployeeEtaInMins(Integer employeeEtaInMins) {
		this.employeeEtaInMins = employeeEtaInMins;
	}
	public Integer getGroupBasedAssignmentType() {
		return groupBasedAssignmentType;
	}
	public void setGroupBasedAssignmentType(Integer groupBasedAssignmentType) {
		this.groupBasedAssignmentType = groupBasedAssignmentType;
	}

	public Boolean getWeightageBased() {
		return weightageBased;
	}
	public void setWeightageBased(Boolean weightageBased) {
		this.weightageBased = weightageBased;
	}
	public Boolean getConsiderLeaves() {
		return considerLeaves;
	}
	public void setConsiderLeaves(Boolean considerLeaves) {
		this.considerLeaves = considerLeaves;
	}
	public Boolean getConsiderSignIn() {
		return considerSignIn;
	}
	public void setConsiderSignIn(Boolean considerSignIn) {
		this.considerSignIn = considerSignIn;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getWorkFieldSpecUniqueId() {
		return workFieldSpecUniqueId;
	}
	public void setWorkFieldSpecUniqueId(String workFieldSpecUniqueId) {
		this.workFieldSpecUniqueId = workFieldSpecUniqueId;
	}
	public String getConsiderWorkFieldSpecUniqueId() {
		return considerWorkFieldSpecUniqueId;
	}
	public void setConsiderWorkFieldSpecUniqueId(String considerWorkFieldSpecUniqueId) {
		this.considerWorkFieldSpecUniqueId = considerWorkFieldSpecUniqueId;
	}

	
	
	
}

package com.effort.plugin.api;



public interface WorkFlowPlugin extends GenericEffortPlugin {
	
	public void workFlowApprove(Long formId);

	public void workFlowReject(Long formId);
	
	public void workFlowCancel(Long formId);


}

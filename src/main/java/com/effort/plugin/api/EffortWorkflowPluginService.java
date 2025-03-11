package com.effort.plugin.api;

import org.springframework.context.annotation.Configuration;

public interface EffortWorkflowPluginService  {
	
	public void workFlowApprove(Long formId);

	public void workFlowReject(Long formId);
	
	public void workFlowCancel(Long formId);

}

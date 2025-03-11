package com.effort.plugin.api;

import org.springframework.stereotype.Service;

@Service  
public class EffortWorkflowPluginServiceImpl implements EffortWorkflowPluginService {

    @Override
    public void workFlowApprove(Long formId) {
        System.out.println("Approving workflow for form ID: " + formId);
    }

    @Override
    public void workFlowReject(Long formId) {
        System.out.println("Rejecting workflow for form ID: " + formId);
    }

    @Override
    public void workFlowCancel(Long formId) {
        System.out.println("Cancelling workflow for form ID: " + formId);
    }
}

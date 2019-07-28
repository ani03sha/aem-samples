package org.redquark.aem.samples.core.workflows.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.samples.core.workflows.StartWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;

/**
 * This service programmatically starts a workflow
 * 
 * @author Anirudh Sharma
 *
 */
@Component(service = StartWorkflow.class)
public class StartWorkflowImpl implements StartWorkflow {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean trigger(ResourceResolver resourceResolver, String payload, String model) {

		try {
			// Get workflow session from the resource resolver
			final WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);

			// Get the workflow model object
			final WorkflowModel workflowModel = workflowSession.getModel(model);

			// Create a WorkflowData object which points to the resource via JCR_PATH
			final WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

			// Passing some data via Map
			final Map<String, Object> workflowMetadata = new HashMap<>();
			workflowMetadata.put("A", "Sample Data");
			workflowMetadata.put("Date", new Date());

			// Start the workflow
			workflowSession.startWorkflow(workflowModel, workflowData, workflowMetadata);

			return true;

		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

}

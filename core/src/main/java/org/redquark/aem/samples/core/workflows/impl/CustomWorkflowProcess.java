package org.redquark.aem.samples.core.workflows.impl;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * @author Anirudh Sharma
 *
 */
@Component(
		service = WorkflowProcess.class, 
		property = { 
				"process.label=Custom Workflow Process Step",
				Constants.SERVICE_DESCRIPTION + "=Sample Granite Workflow Process Step Implementation" 
		}
)
public class CustomWorkflowProcess implements WorkflowProcess {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * This method is called by the workflow engine to perform work described in the
	 * step
	 * 
	 * @param workItem        - This is the resource moving through the workflow
	 * @param workflowSession - Session of the workflow
	 * @param metaDataMap     - Arguments for this Workflow Process defined on the
	 *                        workflow model (PROCESS_ARGS, argSingle, argMulti)
	 * @throws WorkflowException - This will be thrown when the workflow step cannot
	 *                           be completed. This will cause the workflow to retry
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		// Get workflow data (the data that is being passed through this workflow)
		final WorkflowData workflowData = workItem.getWorkflowData();

		// Get workflow payload type
		String payloadType = workflowData.getPayloadType();

		// Check if the payload is a path in the JCR (JCR_PATH); the other (less common)
		// type is JCR_UUID
		if (!StringUtils.equals(payloadType, "JCR_PATH")) {
			return;
		}

		// Get the workflow payload path to the JCR
		String payloadPath = workflowData.getPayload().toString();
		log.info("Payload path: {}", payloadPath);

		// Get workflow Process Arguments (PROCESS_ARGS)
		String processArgs = metaDataMap.get("PROCESS_ARGS", "default");

		// Store process arguments values in an array
		String[] processArgsValues = StringUtils.split(processArgs, ",");
		log.info("Process argument values: {}", Arrays.toString(processArgsValues));

		// Single and Multi args values
		String singleValue = metaDataMap.get("argSingle", "empty");
		String[] multiValue = metaDataMap.get("argMulti", new String[] { "empty" });

		log.info("Single value: {}", singleValue);
		log.info("Multi value: {}", Arrays.toString(multiValue));

		// Get persisted data in the workflow step (in metaDataMap)
		String persistedData = workItem.getWorkflow().getWorkflowData().getMetaDataMap().get("KeyOne", String.class);
		log.info("Persisted data in the workflow step {}", persistedData);

		// Set data in the workflow step
		workItem.getWorkflow().getWorkflowData().getMetaDataMap().put("KeyTwo", "Sample Value");

		// Update data via workflow session
		workflowSession.updateWorkflowData(workItem.getWorkflow(), workflowData);

	}

}

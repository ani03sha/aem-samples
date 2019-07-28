package org.redquark.aem.samples.core.workflows.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * @author Anirudh Sharma
 *
 */
@Component(
		service = ParticipantStepChooser.class, 
		property = { 
				"chooser.label=Custom Dynamic Participant Step" 
		}
)
public class CustomDynamicParticipant implements ParticipantStepChooser {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		String participant = "admin";

		// Getting workflow instance
		Workflow workflow = workItem.getWorkflow();

		// Getting the workflow history items from the workflow session object
		List<HistoryItem> workflowHistory = workflowSession.getHistory(workflow);

		if (!workflowHistory.isEmpty()) {
			participant = "administrator";
		} else {
			participant = "admin";
		}

		log.info("Participant's principal id is: {}", participant);

		return participant;
	}

}

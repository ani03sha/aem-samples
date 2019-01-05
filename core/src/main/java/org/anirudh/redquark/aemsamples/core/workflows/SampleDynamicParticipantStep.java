package org.anirudh.redquark.aemsamples.core.workflows;

import java.util.List;

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
 * Sample implementation of Dynamic Participant Step
 */
public class SampleDynamicParticipantStep implements ParticipantStepChooser {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		log.info("Inside the getParticipant method of SampleDynamicParticipantStep");

		/**
		 * Participant
		 */
		String participant = "admin";

		/**
		 * Getting the workflow instance from the workItem
		 */
		Workflow workflow = workItem.getWorkflow();

		/**
		 * List of workflow items
		 */
		List<HistoryItem> workflowHistory = workflowSession.getHistory(workflow);

		if (!workflowHistory.isEmpty()) {
			participant = "administrators";
		} else {
			participant = "admin";
		}

		log.info("####### Participant : " + participant + " ##############");

		return participant;
	}

}

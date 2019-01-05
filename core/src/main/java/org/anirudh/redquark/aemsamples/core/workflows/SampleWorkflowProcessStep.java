package org.anirudh.redquark.aemsamples.core.workflows;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * Sample implementation of a custom workflow process step
 */
@Component(label = "Sample Workflow Process Step", metatype = true, immediate = true)
@Properties({ @Property(name = Constants.SERVICE_DESCRIPTION, value = "Custom Process Step Description"),
		@Property(name = Constants.SERVICE_VENDOR, value = "Anirudh Sharma"),
		@Property(name = "process.label", value = "Custom Process Step Label") })
@Service(value = WorkflowProcess.class)
public class SampleWorkflowProcessStep implements WorkflowProcess {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Overridden method to describe the business logic
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		/**
		 * Getting the current assignee from the workItem
		 */
		String currentAssignee = workItem.getCurrentAssignee();

		/**
		 * Print the current assignee to the log
		 */
		log.info("Current assignee is: {}", currentAssignee);

	}

}

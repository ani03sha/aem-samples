package org.redquark.aem.samples.core.workflows.impl;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * @author Anirudh Sharma
 *
 */
@Component(
		service = WorkflowProcess.class, 
		property = {
				"process.label=Dialog Process Step"
		}
)
public class DialogProcessStep implements WorkflowProcess {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
		
		String singleValue = metaDataMap.get("argSingle", "empty");
		String date = metaDataMap.get("startDate", "empty");
		String[] multiValue = metaDataMap.get("argMulti", new String[] {"empty"});
		
		log.info("Single value: {}", singleValue);
		log.info("Date: {}", date);
		log.info("Mutli value: {}", Arrays.toString(multiValue));
	}

}

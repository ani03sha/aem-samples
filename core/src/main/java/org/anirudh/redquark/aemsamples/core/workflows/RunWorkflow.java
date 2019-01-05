package org.anirudh.redquark.aemsamples.core.workflows;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;

/**
 * This class invokes a workflow programmatically
 */
@Component
@Service
@Properties({ @Property(name = "sling.servlet.paths", value = "/bin/aemsamples/runworkflow"),
		@Property(name = "sling.servlet.methods", value = "GET") })
public class RunWorkflow extends SlingSafeMethodsServlet {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -4049294023241824432L;

	/**
	 * Injecting WorkflowService dependency
	 */
	@Reference
	private WorkflowService workflowService;

	/**
	 * Overridden method to define the business logic
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse reponse) {

		/**
		 * Getting the resource resolver from the request object
		 */
		ResourceResolver resolver = request.getResourceResolver();

		/**
		 * Adapting the resource resolver to the Session object
		 */
		Session session = resolver.adaptTo(Session.class);

		/**
		 * Path on which the workflow will run
		 */
		String path = "/content/aemsamples";

		/**
		 * Workflow model which we want to run - typically present in
		 * /etc/workflow/models
		 */
		String model = "update_asset";

		/**
		 * Create a workflow session
		 */
		WorkflowSession wfSession = workflowService.getWorkflowSession(session);

		try {

			/**
			 * Get the workflow model
			 */
			WorkflowModel workflowModel = wfSession.getModel(model);

			/**
			 * Get the workflow data
			 */
			WorkflowData workflowData = wfSession.newWorkflowData("JCR_PATH", path);

			/**
			 * Run the workflow
			 */
			wfSession.startWorkflow(workflowModel, workflowData);
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
		}
	}
}

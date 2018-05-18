package org.anirudh.redquark.aemsamples.core.impl;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

/**
 * This class creates a custom workflow step
 */
@Component
@Service
@Properties({ @Property(name = Constants.SERVICE_DESCRIPTION, value = "Test Email Workflow"),
		@Property(name = "process.label", value = "Test Email Workflow Process") })
public class CustomWorkflowStep implements WorkflowProcess {

	/**
	 * Default logger
	 */
	private static final Logger log = LoggerFactory.getLogger(CustomWorkflowStep.class);

	/**
	 * Injecting the dependency of MessageGatewayService
	 */
	@Reference
	private MessageGatewayService messageService;

	/**
	 * Overridden method where the actual implementation is written
	 */
	@Override
	public void execute(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2) throws WorkflowException {

		try {
			log.info("Inside the execute method of CustomWorkflowStep");

			/**
			 * MessageGateway instance
			 */
			MessageGateway<Email> gateway;

			/**
			 * Setup the email message
			 */
			Email email = new SimpleEmail();

			/**
			 * Setting the email values
			 */
			String to = "abc@xyz.com";
			String cc = "lmn@pqr.com";

			email.addTo(to);
			email.addCc(cc);
			email.setSubject("Custom Workflow Step");
			email.setFrom("test@email.com");
			email.setMsg("This message is to inform you that the AEM custom workflow step is executed");

			/**
			 * Inject a MessageGateway Service and send the message
			 */
			gateway = messageService.getGateway(Email.class);

			if (gateway != null) {
				/**
				 * Sending the email
				 */
				gateway.send(email);
			} else {
				throw new NullPointerException("Message Gateway is null");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

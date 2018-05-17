package org.anirudh.redquark.aemsamples.core.schedulers;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(metatype = false)
@Service
public class SimpleDSComponent implements Runnable {

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(SimpleDSComponent.class);

	/**
	 * Reference of Scheduler API - injecting the same
	 */
	@Reference
	private Scheduler scheduler;

	public void run() {
		log.info("Running...");
	}

	@SuppressWarnings("deprecation")
	protected void activate(ComponentContext context) {
		context.getBundleContext();

		/**
		 * Scheduling expression
		 * 
		 * The following job is executed every minute
		 * 
		 */
		String schedulingExpression = "0 * * * * ?";

		/**
		 * Creating a Map
		 */
		Map<String, Serializable> config = new HashMap<String, Serializable>();

		final Runnable job = new Runnable() {

			@Override
			public void run() {
				int staleItems = checkStaleItems();

				if (staleItems > -1) {
					//EmailUtil.sendEmail(staleItems);
				}
			}
		};

		/**
		 * Adding the job
		 */
		try {
			this.scheduler.addJob("Simple DS Job", job, config, schedulingExpression, true);
		} catch (Exception e) {
			job.run();
			log.error(e.getMessage(), e);
		}
	}

	protected void deactivate(ComponentContext context) {
	}

	/**
	 * Use MBean Logic to check the number of stale Workflow Items
	 */
	private int checkStaleItems() {

		try {
			/**
			 * Create an MBean server class
			 */
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

			/**
			 * Getting Workflow MBean
			 */
			ObjectName workflowMBean = getWorkflowMBean(mBeanServer);

			/**
			 * Get the number of Stale Workflows from AEM
			 */
			Object staleWorkflowCount = mBeanServer.invoke(workflowMBean, "countStaleWorkflows", new Object[] { null },
					new String[] { String.class.getName() });

			/**
			 * Casting the stale workflow count into int
			 */
			int staleWorkflows = (Integer) staleWorkflowCount;

			return staleWorkflows;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return -1;
	}

	private ObjectName getWorkflowMBean(MBeanServerConnection connection) {

		try {

			/**
			 * Getting the names of workflows
			 */
			Set<ObjectName> names = connection
					.queryNames(new ObjectName("com.adobe.granite.workflow:type=Maintenance,*"), null);

			if (names.isEmpty()) {
				return null;
			}

			return names.iterator().next();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

}

package org.anirudh.redquark.aemsamples.core.schedulers;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class demonstrate the use of Apache Sling Scheduler using the Scheduler
 * API
 */
@Component
public class CustomScheduledService {

	/**
	 * Default logger
	 */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * The scheduler for rescheduling jobs
	 */
	@Reference
	private Scheduler scheduler;

	/**
	 * Activate method
	 */
	@SuppressWarnings("deprecation")
	protected void activate(ComponentContext componentContext) {

		/**
		 * CASE 1 - With addJob() method, executes the scheduled job every minute
		 */

		/**
		 * Scheduling expression
		 */
		String schedulingExpression = "0 * * * * ?";
		/**
		 * Job Name which needs to be run
		 */
		String jobName1 = "case1";

		/**
		 * Config map
		 */
		Map<String, Serializable> config1 = new HashMap<String, Serializable>();

		/**
		 * Boolean variable to check if this job can run concurrently with other jobs
		 */
		boolean canRunConcurrently = true;

		/**
		 * Implementation of job
		 */
		final Runnable job1 = new Runnable() {

			@Override
			public void run() {

				log.info("Executing job1");

			}
		};

		try {
			/**
			 * Registering the job
			 */
			this.scheduler.addJob(jobName1, job1, config1, schedulingExpression, canRunConcurrently);
		} catch (Exception e) {
			job1.run();
			log.error(e.getMessage(), e);
		}

		/**
		 * CASE 2 - With addPeriodicJob(); executes the job every three minutes
		 */

		/**
		 * Job name
		 */
		String jobName2 = "case2";

		/**
		 * Duration after which periodic job should be run
		 */
		long period = 180;

		/**
		 * Config map
		 */
		Map<String, Serializable> config2 = new HashMap<String, Serializable>();

		/**
		 * Implementation of job
		 */
		final Runnable job2 = new Runnable() {

			@Override
			public void run() {

				log.info("Executing job2");

			}
		};

		try {
			/**
			 * Registering the job
			 */
			this.scheduler.addPeriodicJob(jobName2, job2, config2, period, canRunConcurrently);
		} catch (Exception e) {
			job2.run();
			log.error(e.getMessage(), e);
		}

		/**
		 * CASE 3 - With fireJobAt(): executes the job at a specific date (date of
		 * deployment + delay of 30 seconds)
		 */

		/**
		 * Name of the job
		 */
		String jobName3 = "case3";

		/**
		 * Delay interval
		 */
		final long delay = 30 * 1000;

		/**
		 * Specific date
		 */
		final Date fireDate = new Date();

		/**
		 * Setting the time - current time + 30 seconds
		 */
		fireDate.setTime(System.currentTimeMillis() + delay);

		/**
		 * Config map
		 */
		Map<String, Serializable> config3 = new HashMap<String, Serializable>();

		/**
		 * Implementing the job
		 */
		final Runnable job3 = new Runnable() {

			@Override
			public void run() {

				log.info("Executing job3 at date: {} with a delay of: {} seconds", fireDate, delay / 1000);
			}
		};

		try {
			/**
			 * Registering the job
			 */
			this.scheduler.fireJobAt(jobName3, job3, config3, fireDate);
		} catch (Exception e) {
			job3.run();
			log.error(e.getMessage(), e);
		}
	}

	protected void deactivate(ComponentContext context) {
		log.info("Deactivated, goodbye!");
	}
}

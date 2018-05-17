package org.anirudh.redquark.aemsamples.core.schedulers;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.anirudh.redquark.aemsamples.core.http.HttpHandler;
import org.anirudh.redquark.aemsamples.core.models.Weather;
import org.anirudh.redquark.aemsamples.core.utils.EmailUtil;
import org.anirudh.redquark.aemsamples.core.utils.JsonParserUtil;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WeatherScheduler {

	/**
	 * Default logger
	 */
	protected Logger log = LoggerFactory.getLogger(WeatherScheduler.class);

	/**
	 * The scheduler for rescheduling jobs
	 */
	@Reference
	private Scheduler scheduler;

	/**
	 * The activate method
	 */
	protected void activate(ComponentContext context) {

		/**
		 * Name of job
		 */
		String jobName = "weatherScheduler";

		/**
		 * Delay
		 */
		final long delay = 30 * 1000;

		/**
		 * Scheduling expression
		 */
		String schedulingExpression = "0 * * * * ?";

		/**
		 * Date at which the scheduler will be fired
		 */
		final Date fireDate = new Date();

		/**
		 * Setting the fireDate time
		 */
		fireDate.setTime(System.currentTimeMillis() + delay);

		/**
		 * Config map
		 */
		Map<String, Serializable> config = new HashMap<String, Serializable>();

		/**
		 * Implementation of the job
		 */
		final Runnable job = new Runnable() {

			@Override
			public void run() {

				log.info("Started executing Weather Scheduler");

				String response = HttpHandler.getWeatherData("CITY CODE, COUNRTY CODE");

				Weather weather = JsonParserUtil.parseJson(response);

				double latitude = weather.getCoord().getLat();
				double longitude = weather.getCoord().getLon();

				String coordinates = "Latitude: " + latitude + " and Longitude: " + longitude;

				EmailUtil.sendEmail(coordinates);

			}
		};

		try {
			/**
			 * Registering the job
			 */
			/* this.scheduler.fireJobAt(jobName, job, config, fireDate); */
			this.scheduler.addJob(jobName, job, config, schedulingExpression, true);
		} catch (Exception e) {
			job.run();
			log.error(e.getMessage(), e);
		}
	}

	protected void deactivate(ComponentContext context) {
		log.info("Execution complete, goodbye!");
	}

}

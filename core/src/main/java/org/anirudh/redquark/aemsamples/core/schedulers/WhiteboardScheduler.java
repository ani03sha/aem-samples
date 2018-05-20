package org.anirudh.redquark.aemsamples.core.schedulers;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class demonstrates a sling scheduler using the whiteboard pattern
 */
@Component(label = "Whiteboard Sling Scheduler", description = "This scheduler is implementing using the whiteboarad pattern", immediate = true, metatype = true)
@Properties({
		@Property(label = "Enabled", description = "Enable/Disable the Scheduled Service", name = "service.enabled", boolValue = true),
		@Property(label = "Cron expression defining when this Scheduled Service will run", description = "[every minute = 0 * * * * ?], [12:01am daily = 0 1 0 ? * *]", name = "scheduler.expression", value = "0 1 0 ? * *"),
		@Property(label = "Allow concurrent executions", description = "Allow concurrent executions of this Scheduled Service", name = "scheduler.concurrent", boolValue = false),
		@Property(label = "Vendor", name = "service.vendor", value = "SampleVendor", propertyPrivate = true) })
public class WhiteboardScheduler implements Runnable {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Injecting ResourceResolverFactory dependency
	 */
	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Overridden run method where the business logic is written
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		ResourceResolver resolver = null;

		try {
			/**
			 * Getting the ResourceResolver
			 */
			resolver = resolverFactory.getAdministrativeResourceResolver(null);

			/**
			 * Business logic
			 */
			log.info("Inside the run method of the class: WhiteboardScheduler");
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		} finally {
			/**
			 * Always close the resource resolver we open
			 */
			if (resolver != null) {
				resolver.close();
			}
		}
	}

	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		@SuppressWarnings("unchecked")
		final Map<String, String> properties = (Map<String, String>) componentContext.getProperties();
		log.info("Properties map: {}", properties);
	}

	@Deactivate
	protected void deactivate(ComponentContext ctx) {

		log.info("Deactivated! Good bye!!");
	}
}

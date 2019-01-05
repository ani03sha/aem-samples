package org.anirudh.redquark.aemsamples.core.schedulers;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class registers the scheduler using the WhiteBoard pattern
 */
@Component(label = "Sample Sling Scheduler", description = "Sample Description", immediate = true, metatype = true)
@Properties({
		@Property(label = "Enabled", description = "Enable/Disable the Scheduled service", name = "service.enabled", boolValue = true),
		@Property(label = "Cron Expression defining when this scheduled service will run", description = "[every minute = 0 * * * * ?], [12:01AM daily = 0 1 0 ? * *]", name = "scheduler.expression", value = "0 1 0 ? * *"),
		@Property(label = "Allow concurrent executions", description = "Allow concurrent executions of this scheduled service", name = "scheduler.concurrent", boolValue = false),
		@Property(label = "Vendor", name = "service.vendor", value = "SampleVendor", propertyPrivate = true) })
@Service
public class SampleScheduler implements Runnable {

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(SampleScheduler.class);

	/**
	 * Injecting instance of ResourceResolverFactory
	 */
	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Overridden method where the actual application logic will be written
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		ResourceResolver resolver = null;

		try {
			/**
			 * Getting the instance of ResourceResolver from the ResourceResolverFactory
			 */
			resolver = resolverFactory.getAdministrativeResourceResolver(null);

			log.info("Inside the run method of SampleScheduler");
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		} finally {
			/**
			 * Always close the resolvers that you open
			 */
			if (resolver != null) {
				resolver.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Activate
	protected void activate(final ComponentContext componentContext) {

		final Map<String, String> properties = (Map<String, String>) componentContext.getProperties();

		log.info("Properties map: ", properties);
	}

	@Deactivate
	protected void deactivate(ComponentContext componentContext) {

		log.info("Deactivated! Good bye!!");
	}

}

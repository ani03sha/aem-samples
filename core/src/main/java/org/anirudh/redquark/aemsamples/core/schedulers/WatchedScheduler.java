package org.anirudh.redquark.aemsamples.core.schedulers;

import java.util.Map;

import org.anirudh.redquark.aemsamples.core.AEMWatchFolder;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This scheduler invokes the AEMWatchFolder service at specific time intervals
 */
@Component
@Service
@Property(name = "scheduler.expression", value = "0 * * * * ?")
public class WatchedScheduler implements Runnable {

	/**
	 * Default logger
	 */
	private Logger log = LoggerFactory.getLogger(WatchedScheduler.class);

	/**
	 * Injecting AEMWatchFolder service
	 */
	@Reference
	private AEMWatchFolder watchFolder;

	/**
	 * Overridden run method
	 */
	@Override
	public void run() {

		log.info("WatchedScheduler is now running");

		/**
		 * Invoke the watched folder functionality
		 */
		//watchFolder.getXMLFiles();
	}

	@Property(label = "A parameter", description = "Can be configured in /system/console/configMgr")
	public static final String MY_PARAMETER = "myParameter";
	private String myParameter;

	@Activate
	protected void activate(final Map<String, Object> config) {
		configure(config);

		/**
		 * Invoke the watched folder functionality
		 */
		watchFolder.getFiles();
	}

	private void configure(final Map<String, Object> config) {
		myParameter = PropertiesUtil.toString(config.get(MY_PARAMETER), null);
		
		log.info(myParameter);

	}

}

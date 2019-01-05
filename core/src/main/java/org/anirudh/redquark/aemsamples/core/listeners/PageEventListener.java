package org.anirudh.redquark.aemsamples.core.listeners;

import java.util.Iterator;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;

/**
 * This class listens to the page modification events
 */
@Component
@Service
@Property(name = "event.topics", value = PageEvent.EVENT_TOPIC)
public class PageEventListener implements EventHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * @see org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.Event)
	 *      This is the overridden method which handles the event that is fired
	 */
	@Override
	public void handleEvent(Event event) {

		/**
		 * Creating an instance of PageEventas we are trying to listen modifications in
		 * cq:Page
		 */
		PageEvent pageEvent = PageEvent.fromEvent(event);

		/**
		 * An iterator that iterates on the modifications that are performed/executed on
		 * any cq:Page
		 */
		Iterator<PageModification> modifications = pageEvent.getModifications();

		/**
		 * Check if there are some modifications in the page
		 */
		while (modifications.hasNext()) {

			logger.info("Page modifications are {} ", modifications.next().getType());
		}

	}

}

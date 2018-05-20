package org.anirudh.redquark.aemsamples.core.listeners;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is an example of a simple EventListener which listens some JCR
 * events and logs them
 */
@Component(immediate = true)
@Service(value = EventListener.class)
public class SimpleResourceListener implements EventListener {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Injecting the dependency of SlingRepository
	 */
	@Reference
	private SlingRepository repository;

	/**
	 * Session object
	 */
	private Session session;

	/**
	 * Activate method
	 * 
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Activate
	public void activate(ComponentContext context) throws Exception {

		log.info("Activating example observation");

		try {

			/**
			 * Never use loginAdministrative in the production instances. Always create a
			 * service user and then obtain the session via the created service user
			 */
			session = repository.loginAdministrative(null);

			/**
			 * Getting the event listener's instance in the session object
			 */
			session.getWorkspace().getObservationManager().addEventListener(this,
					Event.PROPERTY_ADDED | Event.NODE_ADDED, "/apps/aemsamples", true, null, null, false);

		} catch (RepositoryException e) {

			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Overridden method that does the session logout
	 */
	@Deactivate
	public void deactivate() {

		if (session != null) {
			session.logout();
		}
	}

	/**
	 * Overridden method that logs the event at the JCR level - business logic needs
	 * to be put here
	 */
	@Override
	public void onEvent(EventIterator events) {

		try {

			while (events.hasNext()) {

				log.info("Added: {}", events.nextEvent().getPath());
			}
		} catch (RepositoryException e) {

			log.error(e.getMessage(), e);
		}
	}

}

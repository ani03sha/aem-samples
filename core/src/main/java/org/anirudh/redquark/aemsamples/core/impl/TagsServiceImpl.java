package org.anirudh.redquark.aemsamples.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jcr.Repository;
import javax.jcr.Session;

import org.anirudh.redquark.aemsamples.core.TagsService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

/**
 * This implementation gets all the tags at a path and then sorts them
 * alphabetically
 */
@Component(metatype = false)
@Service
public class TagsServiceImpl implements TagsService {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Injecting SlingRepository
	 */
	private SlingRepository repository;

	/**
	 * Getting session
	 */
	private Session session;

	/**
	 * Injecting ResourceResolverFactory
	 */
	private ResourceResolverFactory resolverFactory;

	/**
	 * Injecting JcrTagManagerFactory
	 */
	private JcrTagManagerFactory jcrTagManagerFactory;

	/**
	 * Path of the tags
	 */
	private static final String NAMESPACE = "/etc/tags/we-retail";

	/**
	 * Finding out the name of the repository
	 * 
	 * @return {@link String}
	 */
	public String getRepositoryName() {
		return repository.getDescriptor(Repository.REP_NAME_DESC);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void getAllTags() {

		try {
			/**
			 * Getting the resource resolver
			 */
			ResourceResolver resolver = resolverFactory.getAdministrativeResourceResolver(null);

			/**
			 * Adapting the resource resolver to the session object
			 */
			session = resolver.adaptTo(Session.class);

			/**
			 * Getting the TagManager
			 */
			TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

			/**
			 * Getting the resource
			 */
			Resource resource = resolver.getResource(NAMESPACE);

			/**
			 * Getting all the tags
			 */
			Tag[] tags = tagManager.getTags(resource);

			/**
			 * Calling the method to sort tags
			 */
			List<String> tagsList = sortTags(tags);

			log.info("Sorted tags list is: {}", tagsList);

		} catch (LoginException e) {

			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This overridden method sorts all the tags alphabetically
	 */
	@Override
	public List<String> sortTags(Tag[] tags) {

		/**
		 * List of tags
		 */
		List<String> tagsList = new ArrayList<String>();

		for (int i = 0; i < tags.length; i++) {

			tagsList.add(tags[i].getTitle());
		}

		/**
		 * Sort the tags
		 */
		Collections.sort(tagsList);

		return tagsList;
	}

}

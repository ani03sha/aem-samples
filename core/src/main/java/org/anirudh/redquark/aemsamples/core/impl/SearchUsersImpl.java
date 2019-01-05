package org.anirudh.redquark.aemsamples.core.impl;

import java.util.Iterator;

import javax.jcr.Session;

import org.anirudh.redquark.aemsamples.core.SearchUsers;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
public class SearchUsersImpl implements SearchUsers {

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * JCR Session
	 */
	private Session session;

	/**
	 * Injecting ResourceResolverFactory
	 */
	@Reference
	private ResourceResolverFactory resolverFactory;

	@SuppressWarnings("deprecation")
	@Override
	public String getUsers() {

		try {
			/**
			 * Getting resource resolver
			 */
			ResourceResolver resolver = resolverFactory.getAdministrativeResourceResolver(null);

			/**
			 * Adapting the resource resolver to session object
			 */
			session = resolver.adaptTo(Session.class);

			/**
			 * Getting the instance of UserManager from the JCR Session
			 */
			UserManager userManager = ((JackrabbitSession) session).getUserManager();

			/**
			 * Iterator that contains all the users
			 */
			Iterator<Authorizable> users = userManager.findAuthorizables("jcr:primaryType", "rep:User");

			/**
			 * Iterating over the users
			 */
			while (users.hasNext()) {

				/**
				 * Getting only one user
				 */
				Authorizable user = users.next();

				/**
				 * Check if the Authorizable is not a group
				 */
				if (!user.isGroup()) {

					/**
					 * Extracting the user id of the user
					 */
					String userId = user.getID();

					log.info("User ID of the user is : {}", userId);
				}

				/**
				 * Logging out from the session
				 */
				session.logout();

				return "All AEM users are written into the logs";
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
		return null;
	}

}

package org.anirudh.redquark.aemsamples.core.servlets;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet demonstrates the usage of Permission Sensitive caching principle
 * in conjunction with the dispatcher module
 */
@Component(metatype = false)
@Service
public class AuthCheckerServlet extends SlingSafeMethodsServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -8435996079039995485L;

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Property(value = "/bin/aemsamples/permissioncheck")
	private static final String SERVLET_PATH = "sling.servlet.paths";

	/**
	 * Overridden method to implement the logic
	 */
	@Override
	public void doHead(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		try {

			/**
			 * Retrieving the URI
			 */
			String uri = request.getParameter("uri");

			/**
			 * Getting the resource resolver
			 */
			ResourceResolver resolver = request.getResourceResolver();

			/**
			 * Adapting the resource resolver to the session object
			 */
			Session session = resolver.adaptTo(Session.class);

			/**
			 * Perform the permission check
			 */
			session.checkPermission(uri, Session.ACTION_READ);

			log.info("authchecker says OK");

			/**
			 * Setting the response status
			 */
			response.setStatus(SlingHttpServletResponse.SC_OK);
		} catch (Exception e) {
			/**
			 * If the exception occurs
			 */
			log.error("authchecker says READ access DENIED");

			/**
			 * Setting the status as 403 i.e. the read access is not present for the
			 * resource
			 */
			response.setStatus(SlingHttpServletResponse.SC_FORBIDDEN);
			log.error(e.getMessage(), e);
		} finally {
			
			/**
			 * Example configuration at the dispatcher side
			 */
			
			/*
			
			/auth_checker
			  {
			  # request is sent to this URL with '?uri=<page>' appended
			  /url "/bin/permissioncheck"
			       
			  # only the requested pages matching the filter section below are checked,
			  # all other pages get delivered unchecked
			  /filter
			    {
			    /0000
			      {
			      /glob "*"
			      /type "deny"
			      }
			    /0001
			      {
			      /glob "/content/secure/*.html"
			      /type "allow"
			      }
			    }
			  # any header line returned from the auth_checker's HEAD request matching
			  # the section below will be returned as well
			  /headers
			    {
			    /0000
			      {
			      /glob "*"
			      /type "deny"
			      }
			    /0001
			      {
			      /glob "Set-Cookie:*"
			      /type "allow"
			      }
			    }
			  }
			
			*/
		}
	}

}

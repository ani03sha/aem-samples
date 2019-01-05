package org.anirudh.redquark.aemsamples.core.servlets;

import java.io.IOException;

import javax.jcr.Repository;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet demonstrates the usage of a servlet that is registered via the
 * resourceType
 */
@SlingServlet(resourceTypes = "weretail/components/structure/page", selectors = "aemsamples", extensions = "html", methods = "GET", metatype = false)
public class ResourceTypeServlet extends SlingAllMethodsServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 6551099529415028208L;

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Injecting javax.jcr.Repository dependency
	 */
	@Reference
	private Repository repository;

	@Override
	protected void doGet(final SlingHttpServletRequest requet, SlingHttpServletResponse response) {

		log.info("Inside the doGet method of ResourceTypeServlet");

		/**
		 * Setting the content type property of response
		 */
		response.setContentType("application/json");

		/**
		 * Declaring the array of keys
		 */
		String[] keys = repository.getDescriptorKeys();

		/**
		 * Declaring a JSONObject
		 */
		JSONObject jsonObject = new JSONObject();

		/**
		 * Iterating the array over the keys
		 */
		for (int i = 0; i < keys.length; i++) {

			try {
				/**
				 * Putting keys and their descriptors in JSONObject
				 */
				jsonObject.put(keys[i], repository.getDescriptor(keys[i]));

			} catch (JSONException e) {

				log.error(e.getMessage(), e);
			}
		}

		try {
			/**
			 * Printing the values on the web page
			 */
			response.getWriter().println(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

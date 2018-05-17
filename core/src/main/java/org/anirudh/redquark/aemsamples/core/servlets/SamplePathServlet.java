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
 * This class demonstrates the use of Sling Servlets which is registered using
 * paths property
 */
@SlingServlet(paths = "/bin/aemsamples/simplepathservlet")
public class SamplePathServlet extends SlingAllMethodsServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -7495640011881893396L;

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Injecting javax.jcr.Repository dependency
	 */
	@Reference
	private Repository repository;

	/**
	 * Overridden doGet() method
	 */
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {

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

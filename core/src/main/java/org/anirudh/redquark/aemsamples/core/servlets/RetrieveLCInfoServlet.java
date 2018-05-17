package org.anirudh.redquark.aemsamples.core.servlets;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.msm.api.LiveRelationship;
import com.day.cq.wcm.msm.api.LiveRelationshipManager;

/**
 * This servlet is used to retrieve the information about the live copy
 */
@SlingServlet(paths = "/bin/msmutils/retrievelcinfo")
public class RetrieveLCInfoServlet extends SlingAllMethodsServlet {

	/**
	 * Default Serial Version UUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(RetrieveLCInfoServlet.class);

	@Reference
	private LiveRelationshipManager liveRelationshipManager;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		int mapSize = -1;

		log.info("**** Inside before try LiveCopy Status MSM Example Impl class ****");

		try {
			log.info("Inside LiveCopy Status MSM Example Impl class");

			/**
			 * Getting the instance of the ResourceResolver
			 */
			ResourceResolver resolver = request.getResourceResolver();

			/**
			 * Getting the resource dynamically
			 */
			String resourcePath = request.getParameter("resourcePath");

			/**
			 * Getting the resource for which the live relationship data is to be determined
			 */
			Resource resource = resolver.getResource(resourcePath);

			/**
			 * Getting the LiveRelationship's instance from the resource resolver.
			 */
			LiveRelationship liveRelationship = liveRelationshipManager.getLiveRelationship(resource, true);

			/**
			 * Check if the resource has live relationship
			 */
			boolean isLC = liveRelationshipManager.hasLiveRelationship(resource);

			if (!isLC) {

				log.info("The content does not have a live relationship");
			} else {

				/**
				 * Getting the advanced status
				 */
				Map<String, Boolean> map = liveRelationship.getStatus().getAdvancedStatus();

				/**
				 * Setting map's size
				 */
				mapSize = map.size();

				/**
				 * Iterating the map on the Entry object
				 */
				for (Entry<String, Boolean> entry : map.entrySet()) {

					/**
					 * Printing the content of the map on the browser
					 */
					response.getWriter().println("The Key is: " + entry.getKey());
					response.getWriter().println("The Value is: " + entry.getValue());
				}
			}

			response.getWriter().println("MSM was invoked and map size is: " + mapSize);

		} catch (Exception e) {
			/**
			 * Printing the error message in the logs
			 */
			log.error(e.getMessage(), e);
		}

	}

}

package org.anirudh.redquark.aemsamples.core.servlets;

import java.util.UUID;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet handles the claim and uses POST request
 */
@SlingServlet(paths = "/bin/aemsamples/handleclaim", methods = "POST", metatype = false)
public class HandleClaimServlet extends SlingAllMethodsServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 7074952778698255264L;

	/**
	 * Default logger
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {

		try {

			log.info("Inside the doPost method of HandleClaimServlet");

			/**
			 * Getting the data from the form on the web page
			 */
			String id = UUID.randomUUID().toString();
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String cat = request.getParameter("cat");
			String state = request.getParameter("state");
			String details = request.getParameter("details");
			String date = request.getParameter("date");
			String city = request.getParameter("city");

			/**
			 * Encode the submitted form data to JSON
			 */
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("firstname", firstName);
			obj.put("lastname", lastName);
			obj.put("address", address);
			obj.put("cat", cat);
			obj.put("state", state);
			obj.put("details", details);
			obj.put("date", date);
			obj.put("city", city);

			/**
			 * Get the JSON formatted data
			 */
			String jsonData = obj.toString();

			/**
			 * Return the JSON formatted data
			 */
			response.getWriter().write(jsonData);

			log.info("The data submitted by the user is: ", jsonData);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
	}

}

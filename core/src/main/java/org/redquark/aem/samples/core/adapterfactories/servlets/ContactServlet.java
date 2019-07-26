package org.redquark.aem.samples.core.adapterfactories.servlets;

import static org.redquark.aem.samples.core.constants.ApplicationContants.SLING_SERVLET_EXTENSIONS;
import static org.redquark.aem.samples.core.constants.ApplicationContants.SLING_SERVLET_METHODS;
import static org.redquark.aem.samples.core.constants.ApplicationContants.SLING_SERVLET_RESOURCE_TYPES;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.samples.core.adapterfactories.pojo.ContactList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 */
@Component(
		service = Servlet.class, 
		property = {
				SLING_SERVLET_RESOURCE_TYPES + "=aem-samples/components/content/contactList",
				SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
				SLING_SERVLET_EXTENSIONS + "=html"
				}
		)
public class ContactServlet extends SlingSafeMethodsServlet {

	// Generate serial version UID
	private static final long serialVersionUID = 1420390614912256470L;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Executing contact servlet");

		try {
			// Adapting request object to the ContactList class
			ContactList contactList = request.adaptTo(ContactList.class);
			
			// Getting all the contacts in the list
			response.getWriter().println(contactList.getContacts().toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}

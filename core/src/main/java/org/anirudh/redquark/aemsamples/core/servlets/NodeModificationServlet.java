/**
 * 
 */
package org.anirudh.redquark.aemsamples.core.servlets;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet showcases how to modify a JCR node
 */
@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/aemsamples/nodemodification")
public class NodeModificationServlet extends SlingAllMethodsServlet {

	/**
	 * Default logger
	 */
	private final Logger logger = LoggerFactory.getLogger(NodeModificationServlet.class);

	/**
	 * Instance of resource resolver
	 */
	private ResourceResolver resourceResolver;

	/**
	 * Instance of session
	 */
	private Session session;

	/**
	 * Overridden doGet method
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		/**
		 * Getting resource resolver from the rquest object
		 */
		resourceResolver = request.getResourceResolver();

		/**
		 * Adapting resource resolver to the Session obejct
		 */
		session = resourceResolver.adaptTo(Session.class);

		try {

			/**
			 * Getting the reference of root node in crx/de
			 */
			Node rootNode = session.getRootNode();

			/**
			 * Getting the reference of the node where we wish to make some changes
			 */
			Node testNode = rootNode.getNode("content/we-retail/us/en/jcr:content");

			/**
			 * Setting the property
			 */
			testNode.setProperty("cq:distribute", true);

			logger.info("Added property", testNode.getProperty("cq:distribute"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/**
				 * Saving the changes made in the session
				 */
				session.save();
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			} catch (ItemExistsException e) {
				e.printStackTrace();
			} catch (ReferentialIntegrityException e) {
				e.printStackTrace();
			} catch (ConstraintViolationException e) {
				e.printStackTrace();
			} catch (InvalidItemStateException e) {
				e.printStackTrace();
			} catch (VersionException e) {
				e.printStackTrace();
			} catch (LockException e) {
				e.printStackTrace();
			} catch (NoSuchNodeTypeException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
			/**
			 * Logging out from the session
			 */
			session.logout();
		}
	}

}

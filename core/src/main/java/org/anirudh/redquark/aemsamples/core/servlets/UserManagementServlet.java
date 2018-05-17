package org.anirudh.redquark.aemsamples.core.servlets;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import javax.servlet.http.HttpServletResponse;

import org.anirudh.redquark.aemsamples.core.models.Users;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet creates a user in the crx/de with the desired property
 */
@SlingServlet(paths = "/bin/aemsamples/usermanagement")
public class UserManagementServlet extends SlingAllMethodsServlet {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -5029807991421290474L;

	/**
	 * Default logger
	 */
	private static final Logger log = LoggerFactory.getLogger(UserManagementServlet.class);

	/**
	 * Injecting the dependency of the SlingRepository
	 */
	@Reference
	private SlingRepository repository;

	/**
	 * Instance of resource resolver
	 */
	private ResourceResolver resolver;

	/**
	 * Instance of the user session
	 */
	private Session session;

	/**
	 * Instance of the UserManager API
	 */
	private UserManager userManager;

	/**
	 * An object representing a single user object
	 */
	private User user;

	/**
	 * This method initializes or binds the SlingRepository instance
	 * 
	 * @param repository
	 */
	public void bindRepository(SlingRepository repository) {
		this.repository = repository;
	}

	/**
	 * An overridden doGet method that does all the magic stuff
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("UserManagementServlet is invoked");

		/**
		 * Getting the resource resolver from the SlingHttpServletRequest object
		 */
		resolver = request.getResourceResolver();

		/**
		 * Adapting the resource resolver to the Session object
		 */
		session = resolver.adaptTo(Session.class);

		/**
		 * Adapting the resource resolver to the UserManager object
		 */
		userManager = resolver.adaptTo(UserManager.class);

		user = null;

		/**
		 * Getting the type of request
		 */
		String requestType = request.getParameter("requestType");

		/**
		 * If the request is to create a new user
		 */
		if (requestType.equals("newUser")) {

			/**
			 * Setting all the desired properties in the /home/users
			 */
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String securityQuestion = request.getParameter("securityQuestion");
			String securityAnswer = request.getParameter("securityAnswer");

			/**
			 * Creating an instance of the model class AEMUser
			 */
			Users aemUsers = new Users();

			/**
			 * Setting all the properties
			 */
			aemUsers.setUserName(userName);
			aemUsers.setPassword(password);
			aemUsers.setFirstName(firstName);
			aemUsers.setLastName(lastName);
			aemUsers.setEmail(email);
			aemUsers.setSecurityQuestion(securityQuestion);
			aemUsers.setSecurityAnswer(securityAnswer);

			try {
				/**
				 * Check if the user does not exist
				 */
				user = (User) userManager.getAuthorizable(userName);

				if (user == null) {

					log.info("Creating a new user");

					createNewUser(aemUsers);

				} else {

					response.setStatus(HttpServletResponse.SC_CONFLICT);

					log.error("The user - " + userName + " already exists");

				}
			} catch (RepositoryException e) {

				e.printStackTrace();
			}

		} else if (requestType.equals("resetPassword")) {

			/**
			 * Getting the user name
			 */
			String userName = request.getParameter("userName");

			/**
			 * Getting the security answer set by the user
			 */
			String securityAnswer = request.getParameter("securityAnswer");

			try {

				/**
				 * Check if the user exists
				 */
				user = (User) userManager.getAuthorizable(userName);

				if (user != null) {

					/**
					 * Password change process is initiated
					 */
					resetPassword(user, securityAnswer);
				}
			} catch (RepositoryException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Method that sets the user's password
	 * 
	 * @param user
	 * @param securityAnswer
	 */
	private void resetPassword(User user, String securityAnswer) {

		try {
			log.info("bakar");
			Node profileNode = session.getRootNode().getNode(user.getPath() + "/profile");
			Property securityAnswerProperty = profileNode.getProperty("securityAnswer");
			Value[] values = securityAnswerProperty.getValues();
			log.info(profileNode + "-----" + securityAnswerProperty + "---------- " + values);

			for (int i = 0; i < values.length; i++) {
				String x = values[i].getString();
				log.info(x);
			}

		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates a new user in AEM
	 * 
	 * @param aemUsers
	 */
	private void createNewUser(Users aemUsers) {

		try {
			user = userManager.createUser(aemUsers.getUserName(), aemUsers.getPassword());
			ValueFactory valueFactory = session.getValueFactory();
			Value emailValue = valueFactory.createValue(aemUsers.getEmail());
			Value firstNameValue = valueFactory.createValue(aemUsers.getFirstName());
			Value lastNameValue = valueFactory.createValue(aemUsers.getLastName());
			Value securityQuestionValue = valueFactory.createValue(aemUsers.getSecurityQuestion());
			Value securityAnswerValue = valueFactory.createValue(aemUsers.getSecurityAnswer());

			user.setProperty("profile/email", emailValue);
			user.setProperty("profile/firstname", firstNameValue);
			user.setProperty("profile/lastname", lastNameValue);
			user.setProperty("profile/securityQuestion", securityQuestionValue);
			user.setProperty("profile/securityAnswer", securityAnswerValue);

		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			try {
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
			session.logout();
		}
	}
}

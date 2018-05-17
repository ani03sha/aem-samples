/**
 * 
 */
package org.anirudh.redquark.aemsamples.core.servlets;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet runs the sample query and prints the results on the web page
 */
@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/aemsamples/query")
public class QueryUtilServlet extends SlingAllMethodsServlet {

	/**
	 * Default logger
	 */
	private Logger logger = LoggerFactory.getLogger(QueryUtilServlet.class);

	/**
	 * Instance of resource resolver
	 */
	private ResourceResolver resolver;

	/**
	 * Instance of session
	 */
	private Session session;

	/**
	 * Overridden method where we would write our implementation
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		/**
		 * Getting the resource resolver from the request object
		 */
		resolver = request.getResourceResolver();

		/**
		 * Adapting the resource resolver into the Session object
		 */
		session = resolver.adaptTo(Session.class);

		try {

			logger.info("Query Execution Starts ----------------");

			/**
			 * Getting the instance of query manager from the workspace from the current
			 * session
			 */
			QueryManager queryManager = session.getWorkspace().getQueryManager();

			/**
			 * Sample query we wish to run. This can be made dynamic as well by setting the
			 * desired query in the request parameters and get the value from the object.
			 * See the commented line below.
			 */
			String queryString = "SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE(s,'/content')";

			// String queryString = request.getParameter("query");

			/**
			 * Creating a query object and set the type of the query
			 */
			Query query = queryManager.createQuery(queryString, "JCR-SQL2");

			/**
			 * Executing the query and storing the result in the QueryResult object which is
			 * actually the result set
			 */
			QueryResult queryResult = query.execute();

			/**
			 * Printing the results on the web page
			 */
			response.getWriter().println("--------------Result-------------");

			/**
			 * Getting the instance of RowIterator. This basically behaves like a cursor to
			 * each row of the result set.
			 */
			RowIterator rowIterator = queryResult.getRows();

			/**
			 * Parsing the results in the form of a JSONObject
			 */
			StringBuilder builder = new StringBuilder();

			builder.append('{');

			JSONObject jsonObject = new JSONObject();

			int rowCount = 0;

			while (rowIterator.hasNext()) {

				rowCount++;

				Row row = rowIterator.nextRow();

				jsonObject.put(row.toString(), rowCount);
			}

			/**
			 * Printing the results
			 */
			response.getWriter().println(jsonObject.toString());

			logger.info("Query Execution Ends -----------------");

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

}

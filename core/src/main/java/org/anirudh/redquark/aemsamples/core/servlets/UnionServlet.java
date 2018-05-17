/**
 * 
 */
package org.anirudh.redquark.aemsamples.core.servlets;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.InvalidQueryException;
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
 * This servlet demonstrates the usage of tow queries on different node types
 * and their union. For this example we are using cq:Page and dam:Asset node
 * types
 */
@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/aemsamples/union")
public class UnionServlet extends SlingAllMethodsServlet {

	/**
	 * Default logger
	 */
	private Logger logger = LoggerFactory.getLogger(QueryUtilServlet.class);

	/**
	 * Instance of resource resolver
	 */
	private ResourceResolver resolver;

	/**
	 * Instance of the session
	 */
	private Session session;

	/**
	 * Overridden doGet method which showcases the union of two queries
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		/**
		 * Getting the resource resolver from the Request object
		 */
		resolver = request.getResourceResolver();

		/**
		 * Adapting the resource resolver to the Session object
		 */
		session = resolver.adaptTo(Session.class);

		try {

			/**
			 * Instance of query manager taken from the workspace of the current session
			 */
			QueryManager queryManager = session.getWorkspace().getQueryManager();

			/**
			 * Two queries on different node types - dam:Asset and cq:Page respectively
			 */
			String firstQuery = "SELECT * FROM [dam:Asset] WHERE NAME() ='" + request.getParameter("q1") + "'";
			String secondQuery = "SELECT * FROM [cq:Page] WHERE NAME() ='" + request.getParameter("q2") + "'";

			/**
			 * String containing all the assets that are coming in the result set
			 */
			String assets = assetResults(firstQuery, queryManager);

			/**
			 * String containing all the assets that are coming in the result set
			 */
			String pages = pageResults(secondQuery, queryManager);

			/**
			 * Printing the complete results on the web page
			 */
			response.getWriter().println(assets + "*******" + pages);

		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the pages that are coming in the result set
	 * 
	 * @param secondQuery
	 * @param queryManager
	 * @return {@link String}
	 */
	private String pageResults(String secondQuery, QueryManager queryManager) {

		logger.info("Page Query execution starts");
		try {
			/**
			 * Creating a query object from the QueryManager object
			 */
			Query query = queryManager.createQuery(secondQuery, "JCR-SQL2");

			/**
			 * Setting the limit - only the first 10 results will be shown
			 */
			query.setLimit(10);

			/**
			 * Query result set object that is populated after executing the query
			 */
			QueryResult queryResult = query.execute();

			/**
			 * Instance to iterate over the rows that are coming in the result set
			 */
			RowIterator rowIterator = queryResult.getRows();

			StringBuilder builder = new StringBuilder();

			builder.append('{');

			JSONObject jsonObject = new JSONObject();

			int rowCount = 0;

			while (rowIterator.hasNext()) {

				rowCount++;

				Row row = rowIterator.nextRow();

				jsonObject.put(row.toString(), rowCount);
			}

			return jsonObject.toString();

		} catch (InvalidQueryException e) {

			e.printStackTrace();

			return "An error occurred";

		} catch (RepositoryException e) {

			e.printStackTrace();

			return "An error occurred";

		} catch (JSONException e) {

			e.printStackTrace();

			return "An error occurred";
		}

	}

	/**
	 * This method returns the assets that are coming in the result set
	 * 
	 * @param firstQuery
	 * @param queryManager
	 * @return {@link String}
	 */
	private String assetResults(String firstQuery, QueryManager queryManager) {

		logger.info("Asset Query execution starts");

		try {

			/**
			 * Creating a query object from the QueryManager object
			 */
			Query query = queryManager.createQuery(firstQuery, "JCR-SQL2");

			/**
			 * Setting the limit - only the first 10 results will be shown
			 */
			query.setLimit(10);

			/**
			 * Query result set object that is populated after executing the query
			 */
			QueryResult queryResult = query.execute();

			/**
			 * Instance to iterate over the rows that are coming in the result set
			 */
			RowIterator rowIterator = queryResult.getRows();

			StringBuilder builder = new StringBuilder();

			builder.append('{');

			JSONObject jsonObject = new JSONObject();

			int rowCount = 0;

			while (rowIterator.hasNext()) {

				rowCount++;

				Row row = rowIterator.nextRow();

				jsonObject.put(row.toString(), rowCount);
			}

			return jsonObject.toString();

		} catch (InvalidQueryException e) {

			e.printStackTrace();

			return "An error occurred";

		} catch (RepositoryException e) {

			e.printStackTrace();

			return "An error occurred";

		} catch (JSONException e) {

			e.printStackTrace();

			return "An error occurred";
		}
	}

}

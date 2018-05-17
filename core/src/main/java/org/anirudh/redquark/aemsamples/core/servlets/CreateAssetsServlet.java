package org.anirudh.redquark.aemsamples.core.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
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

import com.adobe.granite.asset.api.AssetVersion;
import com.adobe.granite.asset.api.AssetVersionManager;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;

/**
 * This servlet creates an asset as well its version
 */
@SlingServlet(paths = "/bin/createAsset")
public class CreateAssetsServlet extends SlingAllMethodsServlet {

	/**
	 * Default serial version UUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(CreateAssetsServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		/**
		 * Getting the instance of Resource Resolver from the web request
		 */
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = null;

		try {

			/**
			 * Getting the binary file from the file system
			 */
			File initialFile = new File(
					"C:\\Users\\anirshar\\Documents\\Development\\aem-development\\aem-projects\\eventing\\bundle\\test.indd");

			/**
			 * Getting the input stream from the binary
			 */
			InputStream targetStream = new FileInputStream(initialFile);

			/**
			 * Path of the asset where we want to upload the asset
			 */
			String assetPath = "/content/dam/geometrixx/test.indd";

			/**
			 * Getting an instance of AssetManager interface
			 */
			AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);

			/**
			 * Getting the instance of Abstract Interface and passing the mime type of the
			 * asset. This code then creates an asset at the specified path
			 */
			Asset imageAsset = assetManager.createAsset(assetPath, targetStream, "application/x-indesign", true);

			/**
			 * Checking if the asset is uploaded successfully
			 */
			if (imageAsset != null) {
				response.getWriter().println("Asset is uploaded successfully");
			} else {
				response.getWriter().println("Asset uploading failed");
			}

			/**
			 * Create a new version of an asset
			 */
			AssetVersionManager versionManager = resourceResolver.adaptTo(AssetVersionManager.class);

			AssetVersion version = versionManager.createVersion(imageAsset.getPath(), "upload from s3");

			log.info(String.format("Asset Version %s Successfully created for Asset : %s", version.getId(),
					imageAsset.getPath()));

			/**
			 * Getting the session from the resource resolver
			 */
			session = resourceResolver.adaptTo(Session.class);

			/**
			 * Getting the instance of /jcr:content/metadata node
			 */
			Node n = session.getNode("/content/dam/geometrixx/test.indd/jcr:content/metadata");

			/**
			 * Adding some properties in the metadata node
			 */
			for (int i = 0; i < 4; i++) {
				response.getWriter().println(n.isCheckedOut());
				n.setProperty("test" + i, "dummy" + i);
			}

		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				/**
				 * Saving the session
				 */
				session.save();
			} catch (AccessDeniedException e) {
				log.error(e.getMessage(), e);
			} catch (ItemExistsException e) {
				log.error(e.getMessage(), e);
			} catch (ReferentialIntegrityException e) {
				log.error(e.getMessage(), e);
			} catch (ConstraintViolationException e) {
				log.error(e.getMessage(), e);
			} catch (InvalidItemStateException e) {
				log.error(e.getMessage(), e);
			} catch (VersionException e) {
				log.error(e.getMessage(), e);
			} catch (LockException e) {
				log.error(e.getMessage(), e);
			} catch (NoSuchNodeTypeException e) {
				log.error(e.getMessage(), e);
			} catch (RepositoryException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}

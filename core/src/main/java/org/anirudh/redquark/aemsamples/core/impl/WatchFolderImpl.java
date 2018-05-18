package org.anirudh.redquark.aemsamples.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Calendar;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.anirudh.redquark.aemsamples.core.WatchFolder;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation and has various methods that read various
 * types of file from the watched folder as soon as they are placed
 */
@Component
@Service
public class WatchFolderImpl implements WatchFolder {

	/**
	 * Default logger
	 */
	private Logger log = LoggerFactory.getLogger(WatchFolder.class);

	/**
	 * Session object
	 */
	private Session session;

	/**
	 * Injecting ResourceResolverFactory
	 */
	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Path of the watched folder
	 */
	private String watchedFolderPath = "C:\\Users\\anirshar\\Documents\\Development\\aem-development\\aem-workspace\\watchdog\\watchdog-folder";

	/**
	 * Path of XML files in JCR
	 */
	private String jcrPath = "/content/jcrWatchdog";

	@Override
	public void getFiles() {

		/**
		 * InputStream instance
		 */
		InputStream inputStream = null;

		/**
		 * Name of the created file
		 */
		String fileName = null;

		try {

			/**
			 * Path variable which stores the actual path of your watch dog folder
			 */
			Path path = Paths.get(watchedFolderPath);

			/**
			 * Creating a watch service
			 */
			WatchService watchService = FileSystems.getDefault().newWatchService();

			/**
			 * Registering the path on an event as soon as a file is created in the watch
			 * dog folder. This can also be extended to the events where the files are
			 * modified, deleted etc.
			 */
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

			/**
			 * Creating a boolean variable which will check the validity of our listener
			 */
			boolean valid = true;

			/**
			 * Do-While loop starts
			 */
			do {

				/**
				 * A watch key is created when a watchable object is registered with a watch
				 * service. The key remains valid until: 1. It is cancelled, explicitly, by
				 * invoking its cancel method, or 2. Cancelled implicitly, because the object is
				 * no longer accessible, or 3. By closing the watch service.
				 * 
				 */
				WatchKey watchKey = watchService.take();

				log.info("Watcher service is running...");

				/**
				 * An event or a repeated event for an object that is registered with a
				 * WatchService.
				 */
				for (WatchEvent<?> event : watchKey.pollEvents()) {

					/**
					 * Comparing if the event occurred in the watch dog folder is of type create
					 */
					if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {

						/**
						 * Getting the file name
						 */
						fileName = event.context().toString();

						log.info("File created: " + fileName);

					}
				}
				valid = watchKey.reset();

				log.info("Watcher reset");

				/**
				 * Read the XML file from the specified watched folder
				 */
				File folder = new File(watchedFolderPath);

				/**
				 * Creating an array of all the files in the folder
				 */
				File[] files = folder.listFiles();

				/**
				 * Iterating over the length of the array or check for each and every file that
				 * is present in the watch dog folder
				 */
				for (int i = 0; i < files.length; i++) {

					/**
					 * Creating a new instance of the file
					 */
					File file = files[i];

					/**
					 * Check if the last modified date of the file is less than 2 minutes
					 */
					if (file.lastModified() > (System.currentTimeMillis() - 120000)) {
						
						/**
						 * Creating the input steam object
						 */
						inputStream = new FileInputStream(file);

						/**
						 * Save to AEM JCR
						 */
						saveToJCR(inputStream, fileName);
					}

				}
			} while (valid);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method actually saves the file in the watched folder to the AEM JCR
	 * 
	 * @param inputStream
	 */
	@SuppressWarnings("deprecation")
	private void saveToJCR(InputStream inputStream, String fileName) {

		try {

			/**
			 * Invoke the adaptTo method to create a session
			 */
			ResourceResolver resolver = resolverFactory.getAdministrativeResourceResolver(null);

			/**
			 * Getting the session
			 */
			session = resolver.adaptTo(Session.class);

			/**
			 * Get the node reference of the node where we want to store the files from
			 * watched folder in JCR
			 */
			Node node = session.getNode(jcrPath);

			/**
			 * Getting the instance of ValueFactory
			 */
			ValueFactory valueFactory = session.getValueFactory();

			/**
			 * Getting the content value
			 */
			Binary contentValue = valueFactory.createBinary(inputStream);

			/**
			 * File node
			 */
			Node fileNode = node.addNode(fileName, "nt:file");

			/**
			 * Adding the mixin property
			 */
			fileNode.addMixin("mix:referenceable");

			/**
			 * Adding the jcr:content node for the node which represents the actual file in
			 * JCR
			 */
			Node resNode = fileNode.addNode("jcr:content", "nt:resource");

			/**
			 * Setting the jcr:data property
			 */
			resNode.setProperty("jcr:data", contentValue);

			/**
			 * Getting last modified value - from a singleton
			 */
			Calendar lastModified = Calendar.getInstance();

			/**
			 * Setting the actual last modified time stamp for this node
			 */
			lastModified.setTimeInMillis(lastModified.getTimeInMillis());

			/**
			 * Setting the property
			 */
			resNode.setProperty("jcr:lastModified", lastModified);

			/**
			 * Saving the session
			 */
			session.save();

			/**
			 * Logout from the session
			 */
			session.logout();

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
	}

}

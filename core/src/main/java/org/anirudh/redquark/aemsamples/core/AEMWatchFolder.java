package org.anirudh.redquark.aemsamples.core;

/**
 * This interface defines the contract by which different types of files can be
 * read from the watched folder
 */
public interface AEMWatchFolder {

	/**
	 * This method gets all the XML files from the watched folder to DAM in AEM as
	 * soon as they are placed in it
	 */
	public void getFiles();
}

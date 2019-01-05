package org.anirudh.redquark.aemsamples.core;

import java.util.List;

import com.day.cq.tagging.Tag;

public interface TagsService {

	/**
	 * This method gets the list of all the tags at a path
	 */
	public void getAllTags();
	
	/**
	 * This method sorts the tags alphabetically
	 */
	public List<String> sortTags(Tag[] tags);
}

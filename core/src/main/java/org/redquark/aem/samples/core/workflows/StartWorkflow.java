package org.redquark.aem.samples.core.workflows;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * This interface represents the service which will be used to start workflow
 * programmatically
 * 
 * @author Anirudh Sharma
 *
 */
public interface StartWorkflow {

	/**
	 * This method triggers the workflow based on the payload and workflow model
	 * 
	 * @param resourceResolver - Resource Resolver
	 * @param payload          - Payload on which workflow needs to run
	 * @param model            - Workflow Model
	 * @return {@link Boolean}
	 */
	boolean trigger(ResourceResolver resourceResolver, String payload, String model);
}

package org.anirudh.redquark.aemsamples.core.impl;

import java.util.Collections;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.ActionConfig;
import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveActionFactory;
import com.day.cq.wcm.msm.api.LiveRelationship;

/**
 * This component is used to create custom roll out actions
 */
@SuppressWarnings("deprecation")
@Component(metatype = false)
@Service
public class CustomRolloutConfigService implements LiveActionFactory<LiveAction> {

	/**
	 * Sets the action name
	 */
	@Property(value = "customLiveAction")
	private static final String actionName = LiveActionFactory.LIVE_ACTION_NAME;

	@Override
	public LiveAction createAction(Resource config) throws WCMException {

		/**
		 * Declaring the ValueMap object
		 */
		ValueMap configs;

		/**
		 * Adapt the config resource to a ValueMap
		 */
		if (config == null || config.adaptTo(ValueMap.class) == null) {

			configs = new ValueMapDecorator(Collections.<String, Object>emptyMap());
		} else {
			configs = config.adaptTo(ValueMap.class);
		}
		return new CustomLiveAction(actionName, configs);
	}

	@Override
	public String createsAction() {
		return actionName;
	}

	/**
	 * Inner class that represents the live action
	 */
	private static class CustomLiveAction implements LiveAction {

		/**
		 * Name of the roll out action
		 */
		private String name;

		/**
		 * Roll out config
		 */
		private ValueMap configs;

		/**
		 * Logger
		 */
		private static final Logger log = LoggerFactory.getLogger(CustomLiveAction.class);

		/**
		 * Parameterized constructor
		 * 
		 * @param actionname
		 * @param configs
		 */
		public CustomLiveAction(String actionname, ValueMap configs) {
			this.name = actionName;
			this.configs = configs;
		}

		/**
		 * Overridden execute method
		 */
		@Override
		public void execute(Resource source, Resource target, LiveRelationship liveRelationship, boolean autoSave,
				boolean isResetRollout) throws WCMException {

			/**
			 * Last modified
			 */
			String lastModified = null;

			log.info(" *** Executing CustomLiveAction *** ");

			/**
			 * Determine if the LiveAction is configured to copy the cq:lastModifiedBy
			 * property
			 */
			if ((Boolean) configs.get("repLastModBy")) {

				/**
				 * Get the source's cq:lastModifiedBy property
				 */
				if (source != null && source.adaptTo(Node.class) != null) {

					/**
					 * Creating the ValueMap object for the Source
					 */
					ValueMap sourceVM = source.adaptTo(ValueMap.class);

					/**
					 * Setting the last modified string
					 */
					lastModified = sourceVM.get(com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_MOD_BY, String.class);
				}

				/**
				 * Set the target Node's la-lastModifiedBy property
				 */

				/**
				 * Getting the session
				 */
				Session session = null;

				if (target != null && target.adaptTo(Node.class) != null) {

					/**
					 * Getting the resource resolver
					 */
					ResourceResolver resolver = target.getResourceResolver();

					/**
					 * Adapting the session
					 */
					session = resolver.adaptTo(Session.class);

					/**
					 * Declaring an instance of the target node
					 */
					Node targetNode;

					try {

						/**
						 * Adapting to Node.class
						 */
						targetNode = target.adaptTo(Node.class);

						/**
						 * Setting the property
						 */
						targetNode.setProperty("la-lastModifiedBy", lastModified);

						log.info("*** Target node lastModifiedBy property updated: {} ***", lastModified);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}

				if (autoSave) {

					try {

						/**
						 * Saving the session
						 */
						session.save();
					} catch (Exception e) {

						try {
							/**
							 * Refreshing the session
							 */
							session.refresh(true);
						} catch (RepositoryException re) {
							log.error(re.getMessage(), re);
						}

						log.error(e.getMessage(), e);
					}
				}
			}
		}

		@Deprecated
		public void execute(ResourceResolver arg0, LiveRelationship arg1, ActionConfig arg2, boolean arg3)
				throws WCMException {

		}

		@Deprecated
		public void execute(ResourceResolver arg0, LiveRelationship arg1, ActionConfig arg2, boolean arg3, boolean arg4)
				throws WCMException {

		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getParameterName() {
			return null;
		}

		@Override
		public String[] getPropertiesNames() {
			return null;
		}

		@Override
		public int getRank() {
			return 0;
		}

		@Override
		public String getTitle() {
			return null;
		}

		@Override
		public void write(JSONWriter arg0) throws JSONException {

		}

	}

}

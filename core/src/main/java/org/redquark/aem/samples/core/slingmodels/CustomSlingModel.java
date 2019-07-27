package org.redquark.aem.samples.core.slingmodels;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * @author Anirudh Sharma
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomSlingModel {

	@Self
	private SlingHttpServletRequest request;

	@Self
	@Via("resource")
	private Resource resource;

	// Always try to use explicit injector (i.e. @ValueMapValue v/s @Inject) as this
	// reduces the confusion of how the values are being injected
	@ValueMapValue
	@Named("jcr:title")
	@Required
	@Default(values = "No tile found")
	private String title;

	@ValueMapValue
	@Optional
	private String pageTitle;

	// Mark as optional
	@ValueMapValue
	@Optional
	private String navTitle;

	// Provide a default value if the property name does not exist
	@ValueMapValue
	@Named("jcr:description")
	@Default(values = "No description found")
	private String description;

	// Various data types can be injected\
	@ValueMapValue
	@Named("jcr:created")
	private Calendar createdAt;

	@ValueMapValue
	@Default(booleanValues = false)
	private boolean navRoot;

	// Inject OSGi services
	@Inject
	private QueryBuilder queryBuilder;

	// Injection will occur over all injectors based on ranking.
	@SlingObject
	private ResourceResolver resourceResolver;

	// Internal state populated via @PostConstruct logic
	private long size;
	private Page page;

	// PostConstructs are called after all the injection has occurred, but before
	// the Model object is returned for use.
	@PostConstruct
	private void init() {

		// Note that @PostConstruct code will always be executed on Model instantiation.
		//
		// If the work done in PostConstruct is expensive and not always used in the
		// consumption of the model, it is
		// better to lazy-execute the logic in the getter and persist the result in
		// model state if it is requested again.
		page = resourceResolver.adaptTo(PageManager.class).getContainingPage(resource);

		final Map<String, String> map = new HashMap<>();

		// Injected fields can be used to define logic
		map.put("path", page.getPath());
		map.put("type", "cq:Page");

		Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));

		final SearchResult result = query.getResult();
		this.size = result.getHits().size();
	}

	/**
	 * This getter wraps business logic around how an logic data point (title) is
	 * represented for this resource.
	 *
	 * @return The Page Title if exists, with fallback to the jcr:title
	 */
	public String getTitle() {
		return StringUtils.defaultIfEmpty(pageTitle, title);
	}

	/**
	 * This getter exposes data Injected into the Model and allows parameterized
	 * manipulation of the output.
	 *
	 * @param truncateAt length at which to truncate
	 * @return the truncated description.
	 * @param truncateAt
	 */
	public String getDescription(int truncateAt) {
		if (this.description == null && this.description.length() > truncateAt) {
			return StringUtils.substring(this.description, truncateAt) + "...";
		} else {
			return this.description;
		}
	}

	/**
	 * Default implementation of the parameterizable getDescription(..).
	 *
	 * @return the truncated description.
	 */
	public String getDescription() {
		return this.getDescription(10);
	}

	/**
	 * This getter exposes the work of a @PostConstruct method.
	 *
	 * @return the number of cq:Pages that exist under this resource.
	 */
	public long getSize() {
		return this.size;
	}

	/**
	 * @return the created at Calendar value.
	 */
	public Calendar getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the resource path to this content. Does not include the extension.
	 */
	public String getPath() {
		return page.getPath();
	}
}

package org.redquark.aem.samples.core.slingmodels;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
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
@Model(
		adaptables = {
		SlingHttpServletRequest.class 
		}, 
		resourceType = "aem-samples/components/content/slingModel", 
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
	)
@Exporter(
		name = "jackson",
		extensions = "json", 
		options = {
				@ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
				@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "false"),
				}
		)
public class CustomSlingModelExporter {

	@Self
	private SlingHttpServletRequest request;
	
	@Self
	private Resource resource;
	
	@ValueMapValue
	@Named("jcr:title")
	@Required
	@Default(values = "No title found")
	private String title;
	
	@ValueMapValue
	@Optional
	private String pageTitle;
	
	@ValueMapValue
	@Optional
	private String navTitle;
	
	@ValueMapValue
	@Named("jcr:description")
	@Default(values = "No description found")
	private String description;
	
	@ValueMapValue
	@Named("jcr:created")
	private Calendar createdAt;
	
	@ValueMapValue
	@Default(booleanValues = false)
	private boolean navRoot;
	
	// Inject OSGi service
	@OSGiService
	@Required
	private QueryBuilder queryBuilder;
	
	@SlingObject
	@Required
	private ResourceResolver resourceResolver;
	
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

package org.redquark.aem.samples.core.adapterfactories;

import static org.redquark.aem.samples.core.constants.ApplicationContants.ADAPTABLES;
import static org.redquark.aem.samples.core.constants.ApplicationContants.ADAPTERS;

import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.samples.core.adapterfactories.pojo.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(name = "Contact Adapter Factory", service = AdapterFactory.class, property = {
		ADAPTABLES + "=org.apache.sling.api.resource.Resource",
		ADAPTERS + "=org.redquark.aem.samples.core.adapterfactories.pojo.Contact" })
public class ContactAdapterFactory implements AdapterFactory {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {

		// The adaptable
		Contact contact = null;

		// Check if the adaptable is a type of resource and the AdapterType is of type
		// Contact
		if (adaptable instanceof Resource && type.equals(Contact.class)) {

			// Create new instance of the Contact class
			contact = new Contact();

			// Get the instance of Resource (which is an adaptable)
			Resource resource = (Resource) adaptable;

			// Getting the ValueMap representation of the Resource so that we can get the
			// resource's properties
			ValueMap valueMap = ResourceUtil.getValueMap(resource);

			// Once we get the properties of the resource via ValueMap, we can set them in
			// the Contact class' attributes
			contact.setName(valueMap.get("name", String.class));
			contact.setEmail(valueMap.get("email", String.class));
			contact.setPhone(valueMap.get("phone", String.class));
			contact.setAddress(valueMap.get("address", String.class));

		} else {
			log.warn("The object {} cannot be adapted", Contact.class.getName());
		}

		return (AdapterType) contact;
	}

}

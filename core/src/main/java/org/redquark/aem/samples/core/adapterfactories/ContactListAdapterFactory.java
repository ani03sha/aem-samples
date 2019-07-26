package org.redquark.aem.samples.core.adapterfactories;

import static org.redquark.aem.samples.core.constants.ApplicationContants.ADAPTABLES;
import static org.redquark.aem.samples.core.constants.ApplicationContants.ADAPTERS;

import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.samples.core.adapterfactories.pojo.Contact;
import org.redquark.aem.samples.core.adapterfactories.pojo.ContactList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(
		name = "Contact List Adapter Factory", 
		service = AdapterFactory.class, 
		property = {
				ADAPTABLES + "=org.apache.sling.api.SlingHttpServletRequest",
				ADAPTERS + "=org.redquark.aem.samples.core.adapterfactories.pojo.ContactList" 
				}
		)
public class ContactListAdapterFactory implements AdapterFactory {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String CONTACTS_NODE = "contacts";
	
	@SuppressWarnings("unchecked")
	@Override
	public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {

		ContactList contactList = null;

		if (adaptable instanceof SlingHttpServletRequest && type.equals(ContactList.class)) {

			// Creating new instance of ContactList
			contactList = new ContactList();

			// Getting adaptable's instance
			SlingHttpServletRequest request = (SlingHttpServletRequest) adaptable;

			// Getting resource from the request object
			Resource appResource = request.getResource();

			Resource contactsNode = appResource.getChild(CONTACTS_NODE);

			Iterable<Resource> contacts = contactsNode.getChildren();
			Iterator<Resource> contanctsIterator = contacts.iterator();

			while (contanctsIterator.hasNext()) {
				contactList.addContact(contanctsIterator.next().adaptTo(Contact.class));

			}
		} else {
			log.warn("Cannot be adapted");
		}

		return (AdapterType) contactList;
	}

}

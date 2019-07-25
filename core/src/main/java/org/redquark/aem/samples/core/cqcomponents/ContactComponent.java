package org.redquark.aem.samples.core.cqcomponents;

import org.apache.sling.api.resource.Resource;
import org.redquark.aem.samples.core.adapterfactories.pojo.Contact;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Anirudh Sharma
 *
 */
public class ContactComponent extends WCMUsePojo {

	private String name;
	private String email;
	private String phone;
	private String address;

	@Override
	public void activate() throws Exception {

		// Getting the current resource
		Resource currentResource = getResource();

		// Adapting the resource to the custom class using ContactAdapterFactory
		Contact contact = currentResource.adaptTo(Contact.class);

		// Getting values from the adapted object
		name = contact.getName();
		email = contact.getEmail();
		phone = contact.getPhone();
		address = contact.getAddress();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

}

package org.redquark.aem.samples.core.adapterfactories.pojo;

import java.util.LinkedList;
import java.util.List;

/**
 * Another POJO that will deal with the list of contacts
 * 
 * @author Anirudh Sharma
 *
 */
public class ContactList {

	// List of contacts
	private List<Contact> contacts = new LinkedList<>();

	/**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addContact(Contact contact) {
		this.contacts.add(contact);
	}

}

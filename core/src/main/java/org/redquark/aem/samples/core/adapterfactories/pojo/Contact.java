package org.redquark.aem.samples.core.adapterfactories.pojo;

/**
 * This is a simple POJO that will contain the attributes of our contact model.
 * 
 * This class will be treated as an "adapter" which will be adapted from another
 * class (defined in the "adaptables" property of AdapterFactory)
 * 
 * @author Anirudh Sharma
 *
 */
public class Contact {

	private String name;
	private String email;
	private String phone;
	private String address;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}

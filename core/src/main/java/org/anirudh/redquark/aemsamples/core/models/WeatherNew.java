package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WeatherNew {

	private long id;
	private String main;
	private String description;
	private String icon;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("main", main).append("description", description)
				.append("icon", icon).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(icon).append(description).append(main).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WeatherNew) == false) {
			return false;
		}
		WeatherNew rhs = ((WeatherNew) other);
		return new EqualsBuilder().append(id, rhs.id).append(icon, rhs.icon).append(description, rhs.description)
				.append(main, rhs.main).isEquals();
	}

}
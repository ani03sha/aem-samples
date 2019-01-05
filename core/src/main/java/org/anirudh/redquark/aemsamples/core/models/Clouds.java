package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Clouds {

	private long all;

	public long getAll() {
		return all;
	}

	public void setAll(long all) {
		this.all = all;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("all", all).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(all).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Clouds) == false) {
			return false;
		}
		Clouds rhs = ((Clouds) other);
		return new EqualsBuilder().append(all, rhs.all).isEquals();
	}

}
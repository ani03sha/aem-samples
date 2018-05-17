package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Sys {

	private double message;
	private String country;
	private long sunrise;
	private long sunset;

	public double getMessage() {
		return message;
	}

	public void setMessage(double message) {
		this.message = message;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getSunrise() {
		return sunrise;
	}

	public void setSunrise(long sunrise) {
		this.sunrise = sunrise;
	}

	public long getSunset() {
		return sunset;
	}

	public void setSunset(long sunset) {
		this.sunset = sunset;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("message", message).append("country", country)
				.append("sunrise", sunrise).append("sunset", sunset).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(message).append(sunset).append(sunrise).append(country).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Sys) == false) {
			return false;
		}
		Sys rhs = ((Sys) other);
		return new EqualsBuilder().append(message, rhs.message).append(sunset, rhs.sunset).append(sunrise, rhs.sunrise)
				.append(country, rhs.country).isEquals();
	}

}

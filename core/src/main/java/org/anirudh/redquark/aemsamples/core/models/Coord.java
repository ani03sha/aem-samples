package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Coord {

	private double lon;
	private double lat;

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("lon", lon).append("lat", lat).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(lon).append(lat).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Coord) == false) {
			return false;
		}
		Coord rhs = ((Coord) other);
		return new EqualsBuilder().append(lon, rhs.lon).append(lat, rhs.lat).isEquals();
	}

}
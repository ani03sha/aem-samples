package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Wind {

	private double speed;
	private double deg;

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDeg() {
		return deg;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("speed", speed).append("deg", deg).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(speed).append(deg).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Wind) == false) {
			return false;
		}
		Wind rhs = ((Wind) other);
		return new EqualsBuilder().append(speed, rhs.speed).append(deg, rhs.deg).isEquals();
	}

}
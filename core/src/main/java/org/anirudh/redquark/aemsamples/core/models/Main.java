package org.anirudh.redquark.aemsamples.core.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Main {

	private double temp;
	private double pressure;
	private long humidity;
	private double tempMin;
	private double tempMax;
	private double seaLevel;
	private double grndLevel;

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public long getHumidity() {
		return humidity;
	}

	public void setHumidity(long humidity) {
		this.humidity = humidity;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getSeaLevel() {
		return seaLevel;
	}

	public void setSeaLevel(double seaLevel) {
		this.seaLevel = seaLevel;
	}

	public double getGrndLevel() {
		return grndLevel;
	}

	public void setGrndLevel(double grndLevel) {
		this.grndLevel = grndLevel;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("temp", temp).append("pressure", pressure).append("humidity", humidity)
				.append("tempMin", tempMin).append("tempMax", tempMax).append("seaLevel", seaLevel)
				.append("grndLevel", grndLevel).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(seaLevel).append(humidity).append(pressure).append(grndLevel)
				.append(tempMax).append(temp).append(tempMin).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Main) == false) {
			return false;
		}
		Main rhs = ((Main) other);
		return new EqualsBuilder().append(seaLevel, rhs.seaLevel).append(humidity, rhs.humidity)
				.append(pressure, rhs.pressure).append(grndLevel, rhs.grndLevel).append(tempMax, rhs.tempMax)
				.append(temp, rhs.temp).append(tempMin, rhs.tempMin).isEquals();
	}

}
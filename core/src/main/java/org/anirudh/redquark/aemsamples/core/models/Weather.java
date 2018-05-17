package org.anirudh.redquark.aemsamples.core.models;

import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Weather {

	private Coord coord;
	private List<WeatherNew> weatherNew = null;
	private String base;
	private Main main;
	private Wind wind;
	private Clouds clouds;
	private long dt;
	private Sys sys;
	private long id;
	private String name;
	private long cod;

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public List<WeatherNew> getWeather() {
		return weatherNew;
	}

	public void setWeather(List<WeatherNew> weatherNew) {
		this.weatherNew = weatherNew;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCod() {
		return cod;
	}

	public void setCod(long cod) {
		this.cod = cod;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("coord", coord).append("weather", weatherNew).append("base", base)
				.append("main", main).append("wind", wind).append("clouds", clouds).append("dt", dt).append("sys", sys)
				.append("id", id).append("name", name).append("cod", cod).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(dt).append(clouds).append(coord).append(wind).append(cod)
				.append(sys).append(name).append(base).append(weatherNew).append(main).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Weather) == false) {
			return false;
		}
		Weather rhs = ((Weather) other);
		return new EqualsBuilder().append(id, rhs.id).append(dt, rhs.dt).append(clouds, rhs.clouds)
				.append(coord, rhs.coord).append(wind, rhs.wind).append(cod, rhs.cod).append(sys, rhs.sys)
				.append(name, rhs.name).append(base, rhs.base).append(weatherNew, rhs.weatherNew).append(main, rhs.main)
				.isEquals();
	}

}
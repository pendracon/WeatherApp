/*
 * Copyright 2022 VeeTech Information Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * --------------------------------
 * Based on example code from:
 *   Maven by Example (books.sonatype.com/mvnex-book)
 */
package com.veetechis.weather.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Atmosphere
{
//	public Atmosphere() {}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getHumidity()
	{
		return humidity;
	}

	public void setHumidity(final String newHumidity)
	{
		this.humidity = newHumidity;
	}

	public String getVisibility()
	{
		return visibility;
	}

	public void setVisibility(final String newVisibility)
	{
		this.visibility = newVisibility;
	}

	public String getPressure()
	{
		return pressure;
	}

	public void setPressure(final String newPressure)
	{
		this.pressure = newPressure;
	}

	public String getRising()
	{
		return rising;
	}

	public void setRising(final String newRising)
	{
		this.rising = newRising;
	}

	public Weather getWeather()
	{
		return weather;
	}

	public void setWeather(Weather weather)
	{
		this.weather = weather;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String humidity;
	private String visibility;
	private String pressure;
	private String rising;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weather_id", nullable = false)
	private Weather weather;
}

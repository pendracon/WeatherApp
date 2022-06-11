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
public class Condition
{
//	public Condition() {}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String newText)
	{
		this.text = newText;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String newCode)
	{
		this.code = newCode;
	}

	public String getTemp()
	{
		return temp;
	}

	public void setTemp(String newTemp)
	{
		this.temp = newTemp;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String newDate)
	{
		this.date = newDate;
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

	private String text;
	private String code;
	private String temp;
	private String date;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weather_id", nullable = false)
	private Weather weather;
}

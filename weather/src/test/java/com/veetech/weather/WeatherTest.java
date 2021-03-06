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
package com.veetech.weather;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class WeatherTest
{
	public Weather getWeatherFromResource( String resource )
			throws Exception
	{
		return new WeatherbitParser().parse( getResourceInputStream(resource) );
	}

	public JSONObject getJSONFromResource( String resource )
			throws Exception
	{
		return (JSONObject) (new JSONParser()).parse( new InputStreamReader(getResourceInputStream(resource)) );
	}
	
	public String getResourceAsString( String resource )
			throws Exception
	{
		return IOUtils.toString( getResourceInputStream(resource) );
	}
	
	private InputStream getResourceInputStream( String resource )
			throws Exception
	{
		return getClass().getClassLoader().getResourceAsStream( resource );
	}
}

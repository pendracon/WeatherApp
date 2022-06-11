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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherbitParser
{
	public Weather parse( InputStream is )
			throws IOException, ParseException
	{
		Weather weather = new Weather();
	
		if (LOG.isInfoEnabled()) {
			LOG.info( "Parsing JSON stream..." );
		}
		JSONObject doc = (JSONObject) PARSER.parse( new InputStreamReader(is) );
		
		if (LOG.isDebugEnabled()) {
			LOG.debug( String.format("Loading weather info:%n%s", doc) );
		}
		else if (LOG.isInfoEnabled()) {
			LOG.info( "Loading weather info..." );
		}
		JSONObject data = (JSONObject) ((JSONArray)doc.get("data")).get( 0 );
		weather.setCity( String.valueOf(data.get("city_name")) );
		weather.setRegion( String.valueOf(data.get("state_code")) );
		weather.setCountry( String.valueOf(data.get("country_code")) );
		weather.setCondition( String.valueOf(((JSONObject)data.get("weather")).get("description")) );
		weather.setHumidity( String.valueOf(data.get("rh")) );
		weather.setTemp( String.valueOf(data.get("temp")) );
		weather.setChill( String.valueOf(data.get("app_temp")) );
		
		return weather;
	}

	
	private static final JSONParser PARSER = new JSONParser();
	private static final Logger LOG = Logger.getLogger(WeatherbitParser.class);
}

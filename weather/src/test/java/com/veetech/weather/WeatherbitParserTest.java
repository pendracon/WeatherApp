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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author pendracon
 */
public class WeatherbitParserTest
		extends WeatherTest
{
	@Test
	public void parse()
			throws Exception
	{
		Weather weather = getWeatherFromResource( "weather-test.json" );
		JSONObject doc = getJSONFromResource( "weather-test.json" );
		JSONObject data = (JSONObject) ((JSONArray)doc.get("data")).get( 0 );
		
		Assert.assertEquals( String.valueOf(data.get("city_name")), weather.getCity() );
		Assert.assertEquals( String.valueOf(data.get("state_code")), weather.getRegion() );
		Assert.assertEquals( String.valueOf(data.get("country_code")), weather.getCountry() );
		Assert.assertEquals( String.valueOf(data.get("rh")), weather.getHumidity() );
		Assert.assertEquals( String.valueOf(data.get("temp")), weather.getTemp() );
		Assert.assertEquals( String.valueOf(data.get("app_temp")), weather.getChill() );
		Assert.assertEquals( String.valueOf(((JSONObject)data.get("weather")).get("description")), weather.getCondition() );
	}
}

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
 */
package com.veetech.weather;

import com.veetechis.lib.text.TemplateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.log4j.Logger;

public class WeatherbitIO
{
	public static enum Units {
		METRIC( "M" ),
		SCIENTIFIC( "S" ),
		IMPERIAL( "I" );
		
		private Units( String typeCode )
		{
			this.typeCode = typeCode;
		}
		
		@Override
		public String toString() {
			return typeCode;
		}
		
		private final String typeCode;
	}
	
	public static final String WEATHER_URL_TMPL = "http://api.weatherbit.io/v2.0/current?key=@apiKey@&units=@units@&postal_code=@postalCode@&country=@countryCode@";

	
	public WeatherbitIO( String apiKey )
	{
		apiData = new Properties();
		apiData.setProperty( API_KEY_PARM, apiKey );
		apiData.setProperty( UNITS_PARM, Units.METRIC.name() );
	}

	
	public String getAPIKey()
	{
		return apiData.getProperty( API_KEY_PARM );
	}
	
	public void setUnits( Units units )
	{
		apiData.setProperty( UNITS_PARM, units.name() );
	}
	
	public Units getUnits()
	{
		return Units.valueOf( apiData.getProperty(UNITS_PARM) );
	}
	
	public InputStream forPostalCode( String postalCode )
			throws IOException
	{
		return forPostalCode( postalCode, "US" );
	}
	
	public InputStream forPostalCode( String postalCode, String countryCode )
			throws IOException
	{
		if (log.isInfoEnabled()) {
			log.info(String.format("Retrieving weather data for %s postal code %s...", countryCode, postalCode) );
		}

		apiData.setProperty( POSTAL_CODE_PARM, postalCode );
		apiData.setProperty( COUNTRY_CODE_PARM, countryCode );
		
		String url = TemplateFormat.textFormat( WEATHER_URL_TMPL, apiData );
		URLConnection conn = new URL(url).openConnection();
		return conn.getInputStream();
	}

	
	protected static final String API_KEY_PARM = "@apiKey@";
	protected static final String UNITS_PARM = "@units@";
	protected static final String POSTAL_CODE_PARM = "@postalCode@";
	protected static final String COUNTRY_CODE_PARM = "@countryCode@";
	
	private final Properties apiData;

	private static final Logger log = Logger.getLogger(WeatherbitIO.class);
}

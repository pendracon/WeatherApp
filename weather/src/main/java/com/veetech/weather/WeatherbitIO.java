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

import com.veetechis.lib.text.TemplateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
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
	
	
	public WeatherbitIO( String apiKey )
	{
		this.apiKey = apiKey;
	}

	
	public String getAPIKey()
	{
		return apiKey;
	}
	
	public InputStream forPostalCode( String postalCode, Units units )
			throws IOException
	{
		return forPostalCode( postalCode, "US", units );
	}
	
	public InputStream forPostalCode( String postalCode, String countryCode, Units units )
			throws IOException
	{
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Retrieving weather data for %s postal code %s...", countryCode, postalCode) );
		}

		Properties apiData = new Properties();
		apiData.setProperty( API_KEY_PARM, getAPIKey() );
		apiData.setProperty( POSTAL_CODE_PARM, (postalCode == null ? "12345" : postalCode) );
		apiData.setProperty( COUNTRY_CODE_PARM, (countryCode == null ? "US" : countryCode.toUpperCase(Locale.getDefault())) );
		apiData.setProperty( UNITS_PARM, (units == null ? Units.METRIC.name() : units.name()) );
		
		String url = TemplateFormat.textFormat( WEATHER_URL_TMPL, apiData );
		URLConnection conn = new URL(url).openConnection();
		return conn.getInputStream();
	}

	
	protected static final String API_KEY_PARM = "@apiKey@";
	protected static final String UNITS_PARM = "@units@";
	protected static final String POSTAL_CODE_PARM = "@postalCode@";
	protected static final String COUNTRY_CODE_PARM = "@countryCode@";
	
	protected static final String WEATHER_URL_TMPL = "http://api.weatherbit.io/v2.0/current?key=@apiKey@&units=@units@&postal_code=@postalCode@&country=@countryCode@";

	private final String apiKey;

	private static final Logger LOG = Logger.getLogger( WeatherbitIO.class );
}

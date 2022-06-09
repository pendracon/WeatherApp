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

import com.veetechis.lib.preferences.ArgumentsParser;
import com.veetechis.lib.util.KeyValueList;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.ParseException;

import static com.veetech.weather.WeatherbitIO.Units;

/**
 *
 * @author pendracon
 */
public class Main
{
	public static void main(String[] args)
			throws Exception
	{
		String key = null;
		String zip = null;
		Units units = Units.IMPERIAL;
		
		if (args.length > 0) {
			KeyValueList parms = new ArgumentsParser( ArgumentsParser.DEFAULT_SWITCH, "=" ).parse( args );
			if (parms.get("--key") != null) {
				key = parms.get( "--key" ).getValue();
			}
			if (parms.get("--zip") != null) {
				zip = parms.get( "--zip" ).getValue();
			}
			if (parms.get("--units") != null) {
				try {
					units = Units.valueOf( parms.get("--units").getValue().toUpperCase() );
				}
				catch (Exception e) {
					usage();
				}
			}
		}
		if (key == null || zip == null) {
			usage();
		}

		// Configure log4j...
		PropertyConfigurator.configure( Main.class.getClassLoader().getResource("log4j.properties") );
		
		// Show the weather for the specified ZIP code...
		printWeatherInfo( key, zip, units );
	}

	private static void printWeatherInfo( String key, String zip, Units units )
			throws IOException, ParseException
	{
		if (log.isInfoEnabled()) {
			log.info( WeatherService.getForecast(key, zip, units) );
		}
	}

	private static void usage()
	{
		log.error( String.format("%nUsage:%n"
				+ "\t--key=<apiKey>*%n"
				+ "\t      specify Weatherbit.IO user API key%n"
				+ "\t--zip=<ZIP code>*%n"
				+ "\t      specify US postal code for current weather forecast%n"
				+ "\t--units=[METRIC|SCIENTIFIC|IMPERIAL**]%n"
				+ "\t      specify output type as:%n"
				+ "\t         - METRIC     = e.g. Celcius%n"
				+ "\t         - SCIENTIFIC = e.g. Kelvin%n"
				+ "\t         - IMPERIAL   = e.g. Fahrenheit%n"
				+ "%n"
				+ "\t*  = required input%n"
				+ "\t** = default value%n") );
		System.exit( 5 );
	}

	
	private static final Logger log = Logger.getLogger(Main.class);
}

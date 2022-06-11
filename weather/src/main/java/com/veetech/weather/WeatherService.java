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

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class WeatherService
{
	public static Weather getForecast( String key, String zip, WeatherbitIO.Units units )
			throws IOException, ParseException
	{
		WeatherbitIO weather = CONTEXT.getBean( WeatherbitIO.class, key );
		return CONTEXT.getBean( WeatherbitParser.class ).parse( weather.forPostalCode(zip, units) );
	}

	public static String format( Weather weather )
			throws IOException
	{
		return CONTEXT.getBean( WeatherFormatter.class ).format( weather );
	}
	
	
	private WeatherService() {}

	
	private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext( WeatherConfig.class );
	private static final Logger LOG = Logger.getLogger( WeatherService.class );
}

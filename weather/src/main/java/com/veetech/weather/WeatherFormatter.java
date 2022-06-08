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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class WeatherFormatter
{
	public String format( Weather weather )
			throws IOException
	{
		if (log.isInfoEnabled()) {
			log.info( "Formatting weather data..." );
		}
		
		Reader reader = new InputStreamReader( getClass().getClassLoader().getResourceAsStream("output.vm") );
		VelocityContext context = new VelocityContext();
		context.put( "weather", weather );
		StringWriter writer = new StringWriter();
		Velocity.evaluate( context, writer, "", reader );
		
		return writer.toString();		
	}

	
	private static final Logger log = Logger.getLogger(WeatherFormatter.class);
}

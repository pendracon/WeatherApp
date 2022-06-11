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

import java.util.WeakHashMap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.veetech.weather")
public class WeatherConfig
{
	/**
	 * Return a Weatherbit instance for the given API key.
	 * 
	 * @param apiKey
	 * @return
	 */
	@Bean
	@Scope("prototype")
	public WeatherbitIO getWeatherbitIO( String apiKey )
	{
		WeatherbitIO wio = retrievers.get( apiKey );
		
		if (wio == null) {
			wio = new WeatherbitIO( apiKey );
			retrievers.put( apiKey, wio );
		}
		
		return wio;
	}
	
	@Bean
	@Scope("singleton")
	public WeatherbitParser getWeatherbitParser()
	{
		return new WeatherbitParser();
	}
	
	@Bean
	@Scope("singleton")
	public WeatherFormatter getWeatherFormatter()
	{
		return new WeatherFormatter();
	}

	
	private final WeakHashMap<String, WeatherbitIO> retrievers = new WeakHashMap<>();
	
	private static final Logger LOG = Logger.getLogger( WeatherConfig.class );
}

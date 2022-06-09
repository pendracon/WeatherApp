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
package com.veetech.weather.web;

import com.veetech.weather.WeatherService;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import static com.veetech.weather.WeatherbitIO.Units;
import java.util.Locale;
import org.json.simple.parser.ParseException;


/**
 *
 * @author pendracon
 */
//@WebServlet(name = "WeatherServlet", urlPatterns = {"/WeatherServlet"})
public class WeatherServlet
		extends HttpServlet
{
	/**
	 * Initializes a new instance of WeatherServlet with the API key for the
	 * downstream query service.
	 * 
	 * @throws ServletException
	 */
	public void init()
			throws ServletException
	{
		apiKey = getInitParameter( "apiKey" );
		if( apiKey == null || apiKey.length() == 0 ) {
			throw new ServletException( "Downstream service API key not defined!" );
		}
	}
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");

		String postalCode = request.getParameter( "postalCode" );
		Units units = Units.IMPERIAL;
		
		String unitsType = request.getParameter( "unitsType" );
		if (unitsType != null) {
			try {
				units = Units.valueOf( unitsType.toUpperCase(Locale.getDefault()) );
			}
			catch (IllegalArgumentException e) {
				if (log.isDebugEnabled()) {
					log.debug( String.format("Invalid units type specified: '%s' - using %s.", unitsType, units.name()) );
				}
			}
		}
		
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Weather App Front-end</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>Weather App</h2>");
			out.println( String.format("<p>Current forecast for postal code %s in %s units:", postalCode, units.name().toLowerCase(Locale.getDefault())) );
			out.println( String.format("<p>%s", WeatherService.getForecast(apiKey, postalCode, units).replaceAll("\n", "<br>")) );
			out.println("</body>");
			out.println("</html>");
		}
		catch (ParseException e) {
			throw new ServletException( "Error parsing forecast results.", e );
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Weather App Front-end";
	}// </editor-fold>

	private String apiKey;
	
	private static final Logger log = Logger.getLogger(WeatherServlet.class);
}

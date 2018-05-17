package org.anirudh.redquark.aemsamples.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.anirudh.redquark.aemsamples.core.constants.SchedulingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the HttpHandler class which hits the third party API and gets the
 * data.
 */
public class HttpHandler {

	/**
	 * Default logger
	 */
	private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

	/**
	 * Method to get current weather data
	 * @param query
	 * @return {@value}
	 */
	public static String getWeatherData(String query) {

		String finalResponse = null;

		try {

			URL url = new URL(SchedulingConstants.URL + "?q=" + query + "&appid=" + SchedulingConstants.APIKEY);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			connection.setRequestProperty("User-Agent", SchedulingConstants.USER_AGENT);

			log.info("Response code is {}", connection.getResponseCode());

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String inputLine;

				StringBuffer response = new StringBuffer();

				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}

				br.close();

				finalResponse = response.toString();

				log.info(finalResponse);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return finalResponse.toString();
	}
}

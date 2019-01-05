package org.anirudh.redquark.aemsamples.core.utils;

import java.io.IOException;

import org.anirudh.redquark.aemsamples.core.models.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonParserUtil.class);

	public static Weather parseJson(String jsonString) {

		log.info("JSON Response is: " + jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Weather weather = null;
		try {
			
			weather = objectMapper.readValue(jsonString, Weather.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;

	}
}

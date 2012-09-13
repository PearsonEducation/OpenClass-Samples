package com.pearson.sample.sis.serialization;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strategicgains.util.date.DateAdapterConstants;

/**
 * @author toddf
 * @since Sep 6, 2012
 */
public class JsonSerializer
{
	private static final Gson GSON;
	
	static
	{
		GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.registerTypeAdapter(Date.class, new TimepointProcessor())
			.setDateFormat(DateAdapterConstants.TIMESTAMP_OUTPUT_FORMAT)
			.create();
	}

	private JsonSerializer()
	{
		super();
	}

	public static <T> T deserialize(String string, Class<T> type)
	{
		return GSON.fromJson((String) string, type);
	}

	public static <T> T deserialize(String string, Type type)
	{
		return GSON.fromJson((String) string, type);
	}

	public static String serialize(Object object)
	{
		if (object == null) return "";

		return GSON.toJson(object);
	}
}

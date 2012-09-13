package com.pearson.sample.sis.serialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.strategicgains.util.date.DateAdapter;
import com.strategicgains.util.date.Iso8601TimepointAdapter;

/**
 * @author toddf
 * @since Sep 6, 2012
 */
public class TimepointProcessor
implements JsonSerializer<Date>, JsonDeserializer<Date>
{
	private DateAdapter adapter;

	public TimepointProcessor()
	{
		super();
		this.adapter = new Iso8601TimepointAdapter();
	}

    @Override
    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
    throws JsonParseException
    {
	    try
        {
	        return adapter.parse(json.getAsJsonPrimitive().getAsString());
        }
        catch (ParseException e)
        {
        	throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext context)
    {
    	return new JsonPrimitive(adapter.format(date));
    }
}

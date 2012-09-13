package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.domain.Institution;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class InstitutionService
{
	private AsyncHttpClient client;

	public InstitutionService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public Institution read(String slug, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.INSTITUTION_URL, slug, apiKey, token);
		Future<Response> f = client.prepareGet(url).execute();
		String body = f.get().getResponseBody();
		Type dataType = new TypeToken<ResponseWrapper<Institution>>(){}.getType();
		ResponseWrapper<Institution> rw = JsonSerializer.deserialize(f.get().getResponseBody(), dataType);

		if (rw.isSuccessful())
		{
			return rw.getData();
		}

		throw new ServiceException("Error reading institution slug, " + slug + ": " + (rw.hasMessage() ? rw.getMessage() : body));
	}
}

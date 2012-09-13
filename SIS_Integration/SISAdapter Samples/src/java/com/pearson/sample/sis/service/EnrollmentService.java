package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.domain.Enrollment;
import com.pearson.sample.sis.domain.Institution;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class EnrollmentService
{
	private AsyncHttpClient client;

	public EnrollmentService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public Enrollment readBySisId(String sourceKey, Institution institution, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.ENROLLMENT_SIS_URL, institution.getId(), apiKey, token, sourceKey);
		Future<Response> f = client.prepareGet(url).execute();
		Type dataType = new TypeToken<ResponseWrapper<Enrollment>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<Enrollment> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error reading enrollment source key, " + sourceKey + ": " + body);
	}

	public Enrollment read(String id, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.ENROLLMENT_URL, id, apiKey, token);
		Future<Response> f = client.prepareGet(url).execute();
		Type dataType = new TypeToken<ResponseWrapper<Enrollment>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<Enrollment> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error reading enrollment id, " + id + ": " + body);
	}

	public Enrollment create(Enrollment enrollment, Institution institution, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.ENROLLMENT_CREATE_URL, institution.getSlug(), apiKey, token);
		Future<Response> f = client.preparePost(url)
			.setBody(JsonSerializer.serialize(enrollment))
			.setHeader("Content-Type", "application/json")
			.execute();
		Type dataType = new TypeToken<ResponseWrapper<Enrollment>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 201)
		{
			ResponseWrapper<Enrollment> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error creating enrollment, " + enrollment.getSourceKey() + ": " + body);
	}

	public boolean update(Enrollment enrollment, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.ENROLLMENT_URL, enrollment.getId(), apiKey, token);
		Future<Response> f = client.preparePut(url)
			.setBody(JsonSerializer.serialize(enrollment))
			.setHeader("Content-Type", "application/json")
			.execute();
		Type dataType = new TypeToken<ResponseWrapper<Enrollment>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<Enrollment> rw = JsonSerializer.deserialize(body, dataType);
			return rw.isSuccessful();
		}

		throw new ServiceException("Error creating enrollment, " + enrollment.getSourceKey() + ": " + body);		
	}
}

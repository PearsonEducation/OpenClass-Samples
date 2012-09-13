package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.domain.CourseSection;
import com.pearson.sample.sis.domain.Enrollment;
import com.pearson.sample.sis.domain.Registration;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class RegistrationService
{
	private AsyncHttpClient client;

	public RegistrationService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public Registration read(Enrollment enrollment, CourseSection course, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.REGISTRATION_READ_URL, course.getId(), enrollment.getId(), apiKey, token);
		Future<Response> f = client.prepareGet(url).execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<Registration>>(){}.getType();
			ResponseWrapper<Registration> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error reading registration for enrollment/course: " + body);
	}

	public Registration create(Registration enrollment, CourseSection courseSection, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.REGISTRATION_CREATE_URL, courseSection.getId(), apiKey, token);
		Future<Response> f = client.preparePost(url)
			.setBody(JsonSerializer.serialize(enrollment))
			.setHeader("Content-Type", "application/json")
			.execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 201)
		{
			Type dataType = new TypeToken<ResponseWrapper<Registration>>(){}.getType();
			ResponseWrapper<Registration> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error creating registration: " + body);
	}

	public boolean update(Registration registration, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.REGISTRATION_URL, registration.getId(), apiKey, token);
		Future<Response> f = client.preparePut(url)
			.setBody(JsonSerializer.serialize(registration))
			.setHeader("Content-Type", "application/json")
			.execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<Registration>>(){}.getType();
			ResponseWrapper<Registration> rw = JsonSerializer.deserialize(body, dataType);
			return rw.isSuccessful();
		}

		throw new ServiceException("Error updating registration, " + registration.getId() + ": " + body);		
	}

	public boolean drop(Registration registration, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.REGISTRATION_URL, registration.getId(), apiKey, token);
		Future<Response> f = client.prepareDelete(url)
			.execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<Registration>>(){}.getType();
			ResponseWrapper<Registration> rw = JsonSerializer.deserialize(body, dataType);
			return rw.isSuccessful();
		}

		throw new ServiceException("Error dropping registration, " + registration.getId() + ": " + body);		
	}
}

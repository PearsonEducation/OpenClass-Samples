package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.Utils;
import com.pearson.sample.sis.domain.CourseSection;
import com.pearson.sample.sis.domain.Institution;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class CourseSectionService
{
	private AsyncHttpClient client;

	public CourseSectionService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public CourseSection[] readBySisId(Institution institution, String apiKey, String token, String... courseCallNumbers)
	throws Exception
	{
		String ccns = Utils.join(',', courseCallNumbers);
		String url = String.format(Constants.COURSE_SECTION_SIS_URL, institution.getId(), apiKey, token, ccns);
		Future<Response> f = client.prepareGet(url).execute();
		Type dataType = new TypeToken<ResponseWrapper<CourseSection[]>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<CourseSection[]> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error reading course call numbers, " + ccns + ": " + body);
	}

	public CourseSection read(String id, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.COURSE_SECTION_URL, id, apiKey, token);
		Future<Response> f = client.prepareGet(url).execute();
		Type dataType = new TypeToken<ResponseWrapper<CourseSection>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<CourseSection> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error reading course section id, " + id + ": " + body);
	}

	public CourseSection create(CourseSection courseSection, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.COURSE_SECTION_CREATE_URL, apiKey, token);
		Future<Response> f = client.preparePost(url)
			.setBody(JsonSerializer.serialize(courseSection))
			.setHeader("Content-Type", "application/json")
			.execute();
		Type dataType = new TypeToken<ResponseWrapper<CourseSection>>(){}.getType();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 201)
		{
			ResponseWrapper<CourseSection> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				return rw.getData();
			}
		}

		throw new ServiceException("Error creating course section, " + courseSection.getId() + ": " + body);
	}

	public boolean update(CourseSection courseSection, String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.COURSE_SECTION_URL, courseSection.getId(), apiKey, token);
		Future<Response> f = client.preparePut(url)
			.setBody(JsonSerializer.serialize(courseSection))
			.setHeader("Content-Type", "application/json")
			.execute();
		Type dataType = new TypeToken<ResponseWrapper<CourseSection>>(){}.getType();
		String body = f.get().getResponseBody();

		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<CourseSection> rw = JsonSerializer.deserialize(body, dataType);
			return rw.isSuccessful();
		}

		throw new ServiceException("Error updating course section, " + courseSection.getId() + ": " + body);
	}

	/**
	 * @param courseSection
	 * @param destCourse
	 * @param apiKey
	 * @param token
	 */
    public boolean copy(CourseSection courseSection, CourseSection destCourse, String apiKey, String token)
    throws Exception
    {
		String url = String.format(Constants.COURSE_COPY_URL, destCourse.getId(), courseSection.getId(), apiKey, token);
		Future<Response> f = client.preparePut(url)
		// The following body content and header work around a proxy issue.
		// Ordinarily, this operation does not require a request body.
			.setBody("")
			.setHeader("Content-Type", "application/json")
			.execute();
		Type dataType = new TypeToken<ResponseWrapper<String>>(){}.getType();
		String body = f.get().getResponseBody();

		if (f.get().getStatusCode() == 200)
		{
			ResponseWrapper<String> rw = JsonSerializer.deserialize(body, dataType);
			return rw.isSuccessful();
		}

		throw new ServiceException("Error copying course section, " + courseSection.getId() + ": " + body);
    }
}

package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class TokenService
{
	private AsyncHttpClient client;
	private String refreshToken;

	public TokenService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public String acquire(String user, String password, String apiKey)
	throws Exception
	{
		String url = String.format(Constants.TOKEN_URL, apiKey);
		String postBody = String.format("email=%s&password=%s", user, password);
		Future<Response> f = client.preparePost(url)
			.setHeader("Content-Type", "application/x-www-form-urlencoded")
			.setBody(postBody)
			.execute();

		String responseBody = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<Map<String, String>>>(){}.getType();
			
			ResponseWrapper<Map<String, String>> rw = JsonSerializer.deserialize(responseBody, dataType);
	
			if (rw.isSuccessful())
			{
				Map<String, String> o = rw.getData();
				if ("true".equals(o.get("lacksTOS")) || "true".equals(o.get("invalidCredentials"))) return null;
	
				this.refreshToken = URLEncoder.encode(o.get("refreshToken"), Constants.ENCODING);
				return URLEncoder.encode(o.get("authToken"), Constants.ENCODING);
			}
		}

		throw new ServiceException("Error acquiring token: " + responseBody);
	}

	public String refresh(String apiKey)
	throws Exception
	{
		if (refreshToken == null)
		{
			throw new IllegalStateException("Refresh token is null. A token must be acquired first via acquire() call.");
		}

		String url = String.format(Constants.REFRESH_URL, apiKey, refreshToken);
		Future<Response> f = client.prepareGet(url).execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<Map<String, String>>>(){}.getType();
			ResponseWrapper<Map<String, String>> rw = JsonSerializer.deserialize(body, dataType);
	
			if (rw.isSuccessful())
			{
				Map<String, String> o = rw.getData();
				this.refreshToken = URLEncoder.encode(o.get("refreshToken"), Constants.ENCODING);
				return URLEncoder.encode(o.get("authnToken"), Constants.ENCODING);
			}
		}

		throw new ServiceException("Error acquiring token: " + body);
	}
}

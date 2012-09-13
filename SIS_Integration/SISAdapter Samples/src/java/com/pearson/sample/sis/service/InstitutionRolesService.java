package com.pearson.sample.sis.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.pearson.sample.sis.Constants;
import com.pearson.sample.sis.domain.InstitutionRole;
import com.pearson.sample.sis.domain.ResponseWrapper;
import com.pearson.sample.sis.serialization.JsonSerializer;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class InstitutionRolesService
{
	private AsyncHttpClient client;

	public InstitutionRolesService(AsyncHttpClient client)
	{
		super();
		this.client = client;
	}

	public Map<String, InstitutionRole> read(String apiKey, String token)
	throws Exception
	{
		String url = String.format(Constants.INSTITUTION_ROLES_URL, apiKey, token);
		Future<Response> f = client.prepareGet(url).execute();
		String body = f.get().getResponseBody();
		
		if (f.get().getStatusCode() == 200)
		{
			Type dataType = new TypeToken<ResponseWrapper<List<InstitutionRole>>>(){}.getType();
			ResponseWrapper<List<InstitutionRole>> rw = JsonSerializer.deserialize(f.get().getResponseBody(), dataType);
	
			Map<String, InstitutionRole> rolesBySlug = new HashMap<String, InstitutionRole>();
	
			if (rw.isSuccessful())
			{
				for (InstitutionRole role : rw.getData())
				{
					rolesBySlug.put(role.getSlug(), role);
				}
				
				return rolesBySlug;
			}
		}

		throw new ServiceException("Error reading institution roles: " + body);
	}
}

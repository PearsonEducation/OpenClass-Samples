package com.pearson.sample.sis.domain;


/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class ResponseWrapper<T>
{
	private static final String SUCCESS = "success";

	private int code;
	private String status;
	private String message;
	private T data;

	public T getData()
	{
		return data;
	}

	public boolean hasData()
	{
		return (getData() != null);
	}

	public boolean isSuccessful()
	{
		return SUCCESS.equalsIgnoreCase(status);
	}

	public int getCode()
	{
		return code;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public boolean hasMessage()
	{
		return (getMessage() != null && !getMessage().trim().isEmpty());
	}
}

package com.pearson.sample.sis.service;

/**
 * @author toddf
 * @since Sep 7, 2012
 */
public class ServiceException
extends Exception
{
    private static final long serialVersionUID = -6874856127047828422L;

	/**
	 * 
	 */
	public ServiceException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ServiceException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ServiceException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}

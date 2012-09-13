package com.pearson.sample.sis.domain;

import java.util.Date;

/**
 * @author toddf
 * @since Aug 29, 2012
 */
public abstract class BaseDomainObject
{

	private String id;
	private Date createdAt;
	private Date updatedAt;

	public String getId()
    {
    	return id;
    }

	public Date getCreatedAt()
    {
    	return createdAt;
    }

	public Date getUpdatedAt()
    {
    	return updatedAt;
    }
}

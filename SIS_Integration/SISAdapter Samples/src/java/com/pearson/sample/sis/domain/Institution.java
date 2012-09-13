package com.pearson.sample.sis.domain;


public class Institution
extends BaseDomainObject
{
	private String name;
	private String slug;
	private String description;
	private String sourceType;
	private String sourceKey;

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public String getSlug()
	{
		return slug;
	}

	public void setSlug(String slug)
	{
		this.slug = slug;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSourceType()
	{
		return sourceType;
	}

	public void setSourceType(String sourceType)
	{
		this.sourceType = sourceType;
	}

	public String getSourceKey()
	{
		return sourceKey;
	}

	public void setSourceKey(String sourceKey)
	{
		this.sourceKey = sourceKey;
	}
}

package com.pearson.sample.sis.domain;

import java.util.Date;

public class CourseSection
extends BaseDomainObject
{
	private String institutionId;
	private String courseTitle;
	private String description;
	private String courseCode;
	private String sectionCode;
	private String sectionDetails;
	private String[] callNumbers;
	private Date startDate;
	private Date endDate;
	private Double credits;
	private String termId;
	private String termName;
	private String[] productCodes;
	private String sourceId;
	
	// Returned on read...
	private String institutionName;
	private int studentCount;
	private String instructorNames;

	public int getStudentCount()
	{
		return studentCount;
	}

	public String getInstructorNames()
	{
		return instructorNames;
	}

	public String getInstitutionId()
	{
		return institutionId;
	}

	public void setInstitutionId(String institutionId)
	{
		this.institutionId = institutionId;
	}

	public void setCourseTitle(String courseTitle)
	{
		this.courseTitle = courseTitle;
	}

	public String getCourseTitle()
	{
		return courseTitle;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCourseCode()
	{
		return courseCode;
	}

	public void setCourseCode(String courseCode)
	{
		this.courseCode = courseCode;
	}

	public String getSectionCode()
	{
		return sectionCode;
	}

	public void setSectionCode(String sectionCode)
	{
		this.sectionCode = sectionCode;
	}

	public String getSectionDetails()
	{
		return sectionDetails;
	}

	public void setSectionDetails(String sectionDetails)
	{
		this.sectionDetails = sectionDetails;
	}

	public String[] getCallNumbers()
	{
		return callNumbers;
	}

	public void setCallNumbers(String[] callNumbers)
	{
		this.callNumbers = callNumbers;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Double getCredits()
	{
		return credits;
	}

	public void setCredits(Double credits)
	{
		this.credits = credits;
	}

	public String getTermId()
	{
		return termId;
	}

	public void setTermId(String termId)
	{
		this.termId = termId;
	}

	public String getTermName()
	{
		return termName;
	}

	public void setTermName(String termName)
	{
		this.termName = termName;
	}

	public String[] getProductCodes()
	{
		return productCodes;
	}

	public void setProductCodes(String[] productCodes)
	{
		this.productCodes = productCodes;
	}

	public String getSourceId()
	{
		return sourceId;
	}

	public void setSourceId(String sourceId)
	{
		this.sourceId = sourceId;
	}

	public String getName()
	{
		return courseTitle;
	}

	public String getInstitutionName()
	{
		return institutionName;
	}


	// SECTION: FACTORY

	/**
	 * @param institution
	 * @return
	 */
    public static CourseSection newCourse(Institution institution, String courseTitle, String... ccns)
    {
    	CourseSection c = new CourseSection();
    	c.setInstitutionId(institution.getId());
    	c.setCourseTitle(courseTitle);
    	String[] callNumbers = new String[ccns.length];
    	int i = 0;

    	for (String callNumber : ccns)
    	{
    		callNumbers[i++] = callNumber;
    	}
    	
    	c.setCallNumbers(callNumbers);
    	return c;
    }
}

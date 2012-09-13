package com.pearson.sample.sis.domain;

import java.util.Map;

public class Enrollment
extends BaseDomainObject
{
	private String firstName;
	private String lastName;
	private String institutionId;
	private String institutionRoleId;
	private String email;
	
	private String sourceKey;
	private String sourceType = "batch_upload";
	
	// Returned from save operation...
	private String personId;

	
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getInstitutionId()
	{
		return institutionId;
	}

	public void setInstitutionId(String institutionId)
	{
		this.institutionId = institutionId;
	}

	public String getInstitutionRoleId()
	{
		return institutionRoleId;
	}

	public void setInstitutionRoleId(String institutionRoleId)
	{
		this.institutionRoleId = institutionRoleId;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getSourceKey()
	{
		return sourceKey;
	}

	public void setSourceKey(String sourceKey)
	{
		this.sourceKey = sourceKey;
	}

	public String getSourceType()
	{
		return sourceType;
	}

	public void setSourceType(String sourceType)
	{
		this.sourceType = sourceType;
	}

	public String getPersonId()
	{
		return personId;
	}

	public void setPersonId(String personId)
	{
		this.personId = personId;
	}

	// SECTION: FACTORY METHODS

	/**
	 * @param instRoles
	 * @return
	 */
    public static Enrollment newStudentEnrollment(Institution inst, Map<String, InstitutionRole> instRoles,
    	String firstName, String lastName, String email, String sourceKey, String personId)
    {
    	return newEnrollment(inst, instRoles, firstName, lastName, "student", email, sourceKey, personId);
    }

	/**
	 * @param instRoles
	 * @return
	 */
    public static Enrollment newInstructorEnrollment(Institution inst, Map<String, InstitutionRole> instRoles,
    	String firstName, String lastName, String email, String sourceKey, String personId)
    {
    	return newEnrollment(inst, instRoles, firstName, lastName, "instructor", email, sourceKey, personId);
    }

	/**
	 * @param instRoles
	 * @return
	 */
    public static Enrollment newTeachersAssistantEnrollment(Institution inst, Map<String, InstitutionRole> instRoles,
    	String firstName, String lastName, String email, String sourceKey, String personId)
    {
    	return newEnrollment(inst, instRoles, firstName, lastName, "ta", email, sourceKey, personId);
    }

	/**
	 * @param instRoles
	 * @return
	 */
    public static Enrollment newEnrollment(Institution inst, Map<String, InstitutionRole> instRoles,
    	String firstName, String lastName, String roleName, String email, String sourceKey, String personId)
    {
    	Enrollment e = new Enrollment();
    	e.firstName = firstName;
    	e.lastName = lastName;
    	e.institutionId = inst.getId();
    	e.institutionRoleId = instRoles.get(roleName).getId();
    	e.email = email;
    	e.sourceKey = sourceKey;
    	e.personId = personId;
	    return e;
    }
}

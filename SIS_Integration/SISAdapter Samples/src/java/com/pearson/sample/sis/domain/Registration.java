package com.pearson.sample.sis.domain;

import java.util.Map;


public class Registration
extends BaseDomainObject
{
	private String enrollmentId;
	private String courseSectionId;
	private String roleId;

	public String getEnrollmentId()
	{
		return enrollmentId;
	}

	public void setEnrollmentId(String personId)
	{
		this.enrollmentId = personId;
	}

	public String getCourseSectionId()
	{
		return courseSectionId;
	}

	public void setCourseSectionId(String courseSectionId)
	{
		this.courseSectionId = courseSectionId;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}


	// SECTION: FACTORY METHODS

	/**
	 * @param student
	 * @param courseSection
	 * @param course
	 * @param courseRoles 
	 * @return
	 */
    public static Registration newStudentRegistration(Enrollment student, CourseSection course, Map<String, CourseRole> courseRoles)
    {
    	return newRegistration(student, course, "student", courseRoles);
    }

    public static Registration newInstructorRegistration(Enrollment instructor, CourseSection course, Map<String, CourseRole> courseRoles)
    {
    	return newRegistration(instructor, course, "instructor", courseRoles);
    }
    
    public static Registration newTaRegistration(Enrollment ta, CourseSection course, Map<String, CourseRole> courseRoles)
    {
    	return newRegistration(ta, course, "ta", courseRoles);
    }
    
    public static Registration newRegistration(Enrollment student, CourseSection cs, String roleName, Map<String, CourseRole> courseRoles)
    {
    	Registration r = new Registration();
    	r.setEnrollmentId(student.getId());
    	r.setCourseSectionId(cs.getId());
    	r.setRoleId(courseRoles.get(roleName).getId());
    	return r;
    }
}
package com.pearson.sample.sis;

import java.util.Map;

import com.ning.http.client.AsyncHttpClient;
import com.pearson.sample.sis.domain.CourseRole;
import com.pearson.sample.sis.domain.CourseSection;
import com.pearson.sample.sis.domain.Enrollment;
import com.pearson.sample.sis.domain.Institution;
import com.pearson.sample.sis.domain.InstitutionRole;
import com.pearson.sample.sis.domain.Registration;
import com.pearson.sample.sis.service.CourseRolesService;
import com.pearson.sample.sis.service.CourseSectionService;
import com.pearson.sample.sis.service.EnrollmentService;
import com.pearson.sample.sis.service.InstitutionRolesService;
import com.pearson.sample.sis.service.InstitutionService;
import com.pearson.sample.sis.service.RegistrationService;
import com.pearson.sample.sis.service.TokenService;

/**
 * @author toddf
 * @since Aug 29, 2012
 */
public class Main
{
	public static void main(String[] args)
	throws Exception
	{
		AsyncHttpClient client = new AsyncHttpClient();
		
		try
		{
			// TOKEN ACQUISITION/REFRESH
			TokenService tokens = new TokenService(client);
			String token = tokens.acquire(Constants.LOGIN, Constants.PASSWORD, Constants.API_KEY);
//			System.out.println(token);
			token = tokens.refresh(Constants.API_KEY);
//			System.out.println(token);
	
			// INSTITUTION
			InstitutionService insts = new InstitutionService(client);
			Institution institution = insts.read(Constants.INSTITUTION_SLUG, Constants.API_KEY, token);
			
			// COURSE ROLES CROSS-REFERENCE
			CourseRolesService courseRolesClient = new CourseRolesService(client);
			Map<String, CourseRole> courseRoles = courseRolesClient.read(Constants.API_KEY, token);

			// INSTITUTION ROLES CROSS-REFERENCE
			InstitutionRolesService instRolesClient = new InstitutionRolesService(client);
			Map<String, InstitutionRole> instRoles = instRolesClient.read(Constants.API_KEY, token);

			// CREATE A STUDENT
			EnrollmentService enrollments = new EnrollmentService(client);
			Enrollment s = Enrollment.newStudentEnrollment(institution, instRoles,
	    		"student42",
	    		"bar42",
	    		"student42@esomething.com",
	    		"student42bar42SISID",
	    		null);
			Enrollment newStudent = enrollments.create(s, institution, Constants.API_KEY, token);

			// READ AN ENROLLMENT BY SIS ID
			Enrollment student = enrollments.readBySisId("student1bar1SISID", institution, Constants.API_KEY, token);
			// Now we can update the student...
			// However, it may take [quite] a while for permissions to show up for the update case after a create.
			student.setFirstName(student.getFirstName() + " (updated)");
			enrollments.update(student, Constants.API_KEY, token);

			// CREATE INSTRUCTOR(S)
			Enrollment i = Enrollment.newInstructorEnrollment(institution, instRoles,
	    		"instructor42",
	    		"foo42",
	    		"instructor42@esomething.com",
	    		"instructor42foo42SISID",
	    		null);
			Enrollment newInstructor = enrollments.create(i, institution, Constants.API_KEY, token);

			// READ THE INSTRUCTOR BY SIS ID
			Enrollment instructor = enrollments.readBySisId("instructor1foo1SISID", institution, Constants.API_KEY, token);
	
			// CREATE A COURSE
			CourseSectionService courses = new CourseSectionService(client);
			CourseSection cs = CourseSection.newCourse(institution, "TF Test Course 1", "tftc1ccn1", "tftc1ccn2");
			CourseSection newCourse = courses.create(cs, Constants.API_KEY, token);

			// READ COURSE(s) BY COURSE CALL NUMBERs (SIS ID)
			CourseSection[] course = courses.readBySisId(institution, Constants.API_KEY, token, "tftc1ccn1");
			// Now we can update the course...
			// However, it may take [quite] a while for permissions to show up for the update case after a create.
			course[0].setCourseTitle(course[0].getCourseTitle() + " (updated)");
			courses.update(course[0], Constants.API_KEY, token);

			// REGISTER AN ENROLLEE IN A COURSE
			RegistrationService registrations = new RegistrationService(client);
			Registration r = Registration.newStudentRegistration(student, course[0], courseRoles);
			Registration newRegistration = registrations.create(r, course[0], Constants.API_KEY, token);

			// READ A REGISTRATION
			Registration registration = registrations.read(student, course[0], Constants.API_KEY, token);
			// Now we can update the registration...
			registrations.update(registration, Constants.API_KEY, token);
			
			// DROP STUDENT FROM COURSE (DELETE A REGISTRATION)
			registrations.drop(registration, Constants.API_KEY, token);

			// CREATE A NEW COURSE AND USE IT AS A DESTINATION FOR COPY
			CourseSection destCS = CourseSection.newCourse(institution, "TF Test Course 3", "tftc3ccn1", "tftc3ccn2");
			CourseSection destCourse = courses.create(destCS, Constants.API_KEY, token);
			courses.copy(course[0], destCourse, Constants.API_KEY, token);
//			System.out.println(destCourse.getId());
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		finally
		{
			client.close();
		}
	}
}

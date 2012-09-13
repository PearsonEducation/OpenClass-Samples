package com.pearson.sample.sis;

import java.nio.charset.Charset;

/**
 * @author toddf
 * @since Sep 6, 2012
 */
public final class Constants
{
	// Configuration (should be in a file).
	public static final String INSTITUTION_SLUG = "<institution slug here>";
//	public static final String HOST = "https://50.16.203.242";
	public static final String HOST = "https://api.openclasslabs.com";
	public static final String API_KEY = "<apigee key goes here>";
	public static final String LOGIN = "<institution admin email>";
	public static final String PASSWORD = "<institution admin password>";

	// API URLs
	private static final String API_SUFFIX = "?apiKey=%s";
	private static final String URL_SUFFIX =  API_SUFFIX + "&token=%s";
	public static final String TOKEN_URL = HOST + "/v1/identities/login/basic" + API_SUFFIX;
	public static final String REFRESH_URL = HOST + "/v1/identities/login/refresh" + API_SUFFIX + "&refreshToken=%s";
	public static final String INSTITUTION_URL = HOST + "/v1/campus/institutions/%s.json" + URL_SUFFIX;
	public static final String INSTITUTION_ROLES_URL = HOST + "/v1/campus/institutionroles.json" + URL_SUFFIX;
	public static final String COURSE_ROLES_URL = HOST + "/v1/campus/courseroles.json" + URL_SUFFIX;
	public static final String ENROLLMENT_URL = HOST + "/v1/campus/enrollments/%s.json" + URL_SUFFIX;
	public static final String ENROLLMENT_SIS_URL = HOST + "/v1/campus/institution/%s/enrollments/bySourceKey.json" + URL_SUFFIX + "&sourceKey=%s&sourceType=batch_upload";
	public static final String ENROLLMENT_CREATE_URL = HOST + "/v1/campus/institutions/%s/enrollments.json" + URL_SUFFIX;
	public static final String COURSE_SECTION_CREATE_URL = HOST + "/v1/campus/coursesections.json" + URL_SUFFIX;
	public static final String COURSE_SECTION_URL = HOST + "/v1/campus/coursesections/%s.json" + URL_SUFFIX;
	public static final String COURSE_SECTION_SIS_URL = HOST + "/v1/campus/institutions/%s/coursesections.json" + URL_SUFFIX + "&matchAllCallNumbers=false&callNumbers=%s";
	public static final String REGISTRATION_URL = HOST + "/v1/campus/registrations/%s.json" + URL_SUFFIX;
	public static final String REGISTRATION_CREATE_URL = HOST + "/v1/campus/coursesections/%s/registrations.json" + URL_SUFFIX;
	public static final String REGISTRATION_READ_URL = HOST + "/v1/campus/coursesections/%s/courseregistrations/enrollments/%s.json" + URL_SUFFIX;
	public static final String COURSE_COPY_URL = HOST + "/v1/campus/coursesections/%s/copycontentfrom/%s.json" + URL_SUFFIX;	

	// ENCODING
	public static final String ENCODING = "UTF-8";
	public static final Charset CHARSET = Charset.forName(ENCODING);

	private Constants()
	{
		// private constructor to prevent instantiation.
	}
}

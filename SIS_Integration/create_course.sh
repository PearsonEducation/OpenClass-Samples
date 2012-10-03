#! /bin/bash
#
# Create a course, which is called a CourseSection in OpenClass.
# The JSON payload is described on http://code.pearson.com and in the 
# Java example code domain package.
#
# The SIS ID for a course section is "callNumbers" which is an array
# of strings.
#
TOKEN=`cat token.txt`
APIGEE_KEY="<Apigee key goes here>"
HOST="https://api.openclasslabs.com"
INST_SLUG="<institution slug goes here>"
echo "***** CREATING COURSE SECTION *****"
curl -i -X POST -d "{<json data goes here>}" "${HOST}/v1/campus/coursesections.json?apiKey=${APIGEE_KEY}&${TOKEN}"

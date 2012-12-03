#! /bin/bash
#
# Finds the list of global course roles. The role IDs are utilized
# when registering a user (Enrollment) for a course.
#
TOKEN=`cat token.txt`
APIGEE_KEY="58deb02cf618fa9a1d04e983a13aca3b"
HOST="https://api.openclasslabs.com"
echo "***** COURSE-ROLES *****"
curl -i "${HOST}/v1/campus/courseroles.json?apiKey=${APIGEE_KEY}&${TOKEN}"

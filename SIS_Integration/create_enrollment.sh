#! /bin/bash
#
# Create a user within the context of an Institution.  In OpenClass,
# this is called an "enrollment" (as opposed to registering an enrollment
# in a course section (or "course").
#
# The SIS ID is "sourceKey" and "sourceType" MUST be "batch_upload"
#
TOKEN=`cat token.txt`
APIGEE_KEY=""
HOST="https://api.openclasslabs.com"
INST_SLUG="pearsonplayground"
echo "***** CREATING ENROLLMENT *****"
curl -i -X POST -d "{\"firstName\":\"Todd\",\"lastName\":\"Foo2\",\"sourceKey\":\"todd-foo2\",\"email\":\"todd.foo2@esomething.com\",\"institutionRoleId\":\"4e1f694e1c93e55ef1d4d5e9\",\"sourceType\":\"batch_upload\",\"institutionId\":\"4e20885deabfa3a2586b5fb1\"}" "${HOST}/v1/campus/institutions/${INST_SLUG}/enrollments.json?apiKey=${APIGEE_KEY}&${TOKEN}"

#! /bin/bash
#
# Finds the list of global institution roles. The role IDs are utilized
# when creating a user (e.g. an Enrollment).
#
TOKEN=`cat token.txt`
APIGEE_KEY="58deb02cf618fa9a1d04e983a13aca3b"
HOST="https://api.openclasslabs.com"
echo "***** INSTITUTION ROLES *****"
curl -i "${HOST}/v1/campus/institutionroles.json?apiKey=${APIGEE_KEY}&${TOKEN}"

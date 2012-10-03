#! /bin/bash
#
# Acquiring an access token is the first step...
#
APIGEE_KEY="<apigee key goes here>"
EMAIL="<login email goes here>"
PASSWORD="<password goes here>"
HOST="https://api.openclasslabs.com"
RESPONSE=`curl -i -X POST -d "email=${EMAIL}&password=${PASSWORD}" "${HOST}/v1/identities/login/basic?apiKey=${APIGEE_KEY}"`
echo $RESPONSE

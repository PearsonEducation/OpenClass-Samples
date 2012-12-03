#! /bin/bash
#
# Finds the institution data based on institution 'slug'
# Several subsequent calls require the institution ID.
#
TOKEN=`cat token.txt`
APIGEE_KEY="58deb02cf618fa9a1d04e983a13aca3b"
HOST="https://api.openclasslabs.com"
INST_SLUG="pearsonplayground"
echo "***** INSTITUTION *****"
INST=`curl "${HOST}/v1/campus/institutions/${INST_SLUG}.json?apiKey=${APIGEE_KEY}&${TOKEN}"`
echo ${INST}

#! /bin/bash
#
TOKEN=`cat token.txt`
APIGEE_KEY="<apigee key goes here>"
HOST="https://api.openclasslabs.com"
echo "1. Lookup an Enrollment by Source Key (SIS Identifier)"
curl -i "https://api.openclasslabs.com/v1/campus/institutions/4e20885deabfa3a2586b5fb1/enrollments/bysourcekey.json?apiKey=${APIGEE_KEY}&${TOKEN}&sourceKey=student1bar1SISID&sourceType=batch_upload"
#
echo "2. Look Up a Course Section by Course Call Number (SIS Identifier)"
curl -i "https://api.openclasslabs.com/v1/campus/institutions/4e20885deabfa3a2586b5fb1/coursesections.json?apiKey=${APIGEE_KEY}&${TOKEN}&matchAllCallNumbers=false&callNumbers=tftc1ccn1"
#
echo "3. Copy Course Content"
curl -i -X PUT -d "" "https://api.openclasslabs.com/v1/campus/coursesections/504f9020a2a331fcf9562c48/copycontentfrom/504e22d45ee5e0e76f5bc5e1.json?apiKey=${APIGEE_KEY}&${TOKEN}"
#
echo "4. Lookup a Registration"
curl -i "https://api.openclasslabs.com/v1/campus/coursesections/504e22d45ee5e0e76f5bc5e1/courseregistrations/enrollments/504e08eba2a34f8f271bac07.json?apiKey=${APIGEE_KEY}&${TOKEN}"

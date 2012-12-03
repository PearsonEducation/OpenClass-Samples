#! /bin/bash
#
REFRESH_TOKEN=`cat refresh_token.txt`
RESPONSE=`curl -i "http://50.16.205.242:8080/v1/identities/login/refresh?apiKey=58deb02cf618fa9a1d04e983a13aca3b&refreshToken=${REFRESH_TOKEN}"`
echo $RESPONSE
#TOKEN=/token=.*\&refresh/$RESPONSE/
#echo $TOKEN

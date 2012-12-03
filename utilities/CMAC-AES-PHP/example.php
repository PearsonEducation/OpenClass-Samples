<?php 

/** 
 * Using PHPCryptLib to Create CMAC-AES Hashes
 * 
 * The eCollege RESTful APIs leverage OAuth protocols to grant access to user and partner data.
 * Accessing user-specific resources (i.e. student grades) requires OAuth 2.0 protocol. More
 * Information on eCollege Authentication APIs is available on the Pearson Developer Network: 
 * 
 * http://code.pearson.com/pearson-learningstudio/apis/authentication/authentication-overview
 * 
 * eCollege has implemented two authorization grant types: Password Credentials and Assertion. 
 * 
 * In some cases, students and instructors enter LearningStudio via a Single Sign-On 
 * process from another portal. For this use case, the user often dose not know their 
 * LearningStudio credentials, rather their session is established by a third-party 
 * system acting as their proxy. OAuth 2 establishes support for this type of scenario 
 * via an assertion that the third-party system makes regarding their user, that 
 * LearningStudio can validate as coming from a trusted system. This is accomplished via 
 * the OAuth 2.0 Assertion grant type.
 * Full details of this grant type including assertion format is here: 
 * http://code.pearson.com/pearson-learningstudio/apis/authentication/oauth-20-assertion
 * 
 * In order to sign the assertion, eCollege requires a CMAC-AES hash be generated from 
 * the assertion string and then appended to the assertion. This library generates the  
 * necessary hash in PHP. 
 * 
 * This library is a streamlined version of PHPCryptLib, (c) 2011 Anthony Ferrara and 
 * available at https://github.com/ircmaxell/PHP-CryptLib and released under MIT License.
 * Only the code related to generating CMAC hashes has been included. 
 * 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

// Include the boostrap file from CryptLib.
require_once 'lib/CryptLib/bootstrap.php'; 

//Instantiate the CMAC-generating class
$eCMAC = new CryptLib\MAC\Implementation\ECollegeCMAC; 

//Define your partner-specific secret key somewhere in your code. 
$your_secret_key = 'keep.it.secret.keep.it.safe';

//Set up variables for the request
$api_url = "https://m-api.ecollege.com/token";
$grantType = "assertion";
$assertionType = "<type>"; //either urn:ecollege:names:moauth:1.0:assertion 
							//    or urn:ecollege:names:sourcedid:1.0:assertion

//Assemble the Assertion string following this pattern (see documentation for details)
$assertion = '{client_name}|{key_moniker}|{client_id}|{client_string}|{username}|{timestamp}';

try{

	//Pass Assertion and Key to the generateEcollegeCMAC method. 
	//This assumes the Assertion string has NOT been binary encoded. This method will pack() the 
	//string for you, hash it, and then bin2hex it back to a usable string. 
	$cmac = $eCMAC->generateECollegeCMAC($assertion,$your_secret_key); 

	//$cmac is now equal to something like 989d5631fc2a4cd831ba84e6c7d39478

} catch(Exception $e){ 
	exit($e->getMessage());  
} 
	
//Append the signature hash to the Assertion 
$assertion .= '|'.$cmac; 


// 
// You now have a signed assertion string. To get an access token, use 
// code similar to the following. 
// 

//Create a request to the API 
$request = new HttpRequest($api_url, HttpRequest::METH_POST);        
$request->setContentType("application/x-www-form-urlencoded");
$request->addPostFields(array("grant_type" => $grantType,
                              "assertion_type" => $assertionType,
                              "assertion" => $assertion)); 
try{
	
	// Send Request & Get Response
	$request->send();

	if ($request->getResponseCode() == 200) {

	    // Get the Json reponse containing the Access Token
	    $json = $request->getResponseBody();

	    // Parse the Json response and retrieve the Access Token
	    $ser = json_decode($json);
	    $accessToken = $ser->access_token;
	    echo ("Access Token = ".$accessToken);
	
	} else {
	    // The server returned an error; display the error message
	    echo ("The server returned '".$request->getResponseBody()."'".
	            " with the status code '".$request->getResponseCode()."'");
	}
} catch(Exception $e){ 
	exit($e->getMessage()); 
} 

?>
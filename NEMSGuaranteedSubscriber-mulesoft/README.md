
# Demo Project in Mule 4

## Tasks
- read messages off Solace to obtain event URL location
- login to FHIR server
- GET record from URL
- POST above response as payload to /$extract URI to retrieve the goals

Can be deployed to Cloudhub 


## Project Build
### Requirements:
Java8 JDK (eg. openjdk8)
Maven 3.8.7
Anypoint Studio 7 (Mule 4)

Build from directory of POM.xml
```
mvn clean package
```
Then run from Anypoint Studio

### Required properties in properties-dev.yaml
(see other NEMSGuaranteedSubscriber README.md for properties and replace **** with the values)

```
properties-dev.yaml
auth:
  server: "https://auth.integration-dev.covid19.health.nz/oauth2/token"
  clientId: "****"
  clientSecret: "****"
  scope: "scope/cinc"
  grantType: "client_credentials"
  
solace:
 clientUsername: "****"
 clientPassword: "****"
 messageVpn: "ring-of-fhir"
 brokerHost: "tcps://ring-of-fhir.messaging.solace.cloud:55443" 

fhir:
  xApiKey: "****" 
  requestContext: "****"
```

## Process

### 1. JSON notification received off Solace
{
    "fullUrl": "https://fhir.api-dev.digital.health.nz/R4/QuestionnaireResponse/19d56ad6-25d1-4299-b06d-58a24b361411"
}


### 2. Authenticate to FHIR Server via OAuth2, access token response:
{
	"access_token":"******","expires_in":3600,"token_type":"Bearer"
}


### 3. GET fullUrl endpoint from JSON notification

GET /R4/QuestionnaireResponse/19d56ad6-25d1-4299-b06d-58a24b361411 HTTP/1.1
x-api-key: *****
request-context: *****
x-correlation-id: *****
authorization: Bearer *****
Host: *****
User-Agent: AHC/1.0
Connection: keep-alive
Accept: */*


HTTP/1.1 200 OK
{"subject":{"type":"Patient","identifier":{"value":"ZZZ9999","use":"official","system":""},"display":"J WCDHB Pace"},"authored":"2024-03-21T21:16:11.712Z","meta":{"lastUpdated":"2024-03-21T21:17:01.527Z","versionId":"1"},"status":"completed","item":[{"answer":[{"valueCoding":{"code":"yes","display":"Yes"}}],"linkId":"2274198486440","text":"Do you want to smoke less or quit altogether?"},{"answer":[{"valueCoding":{"display":"Providing resources"}}],"linkId":"6423468528467","text":"How can we best support you in reaching that goal?"},{"answer":[{"valueString":"test"}],"linkId":"8196750247032","text":"Are there any challenges you foresee in achieving this?"},{"item":[{"answer":[{"valueString":"active"}],"linkId":"goalStatus"},{"answer":[{"valueString":"Stop smoking"}],"linkId":"goalDescription"},{"answer":[{"valueBoolean":true}],"linkId":"goalSubject"}],"linkId":"goalExtractContainer"}],"resourceType":"QuestionnaireResponse","questionnaire":"","id":"19d56ad6-25d1-4299-b06d-58a24b361411","author":{"type":"Practitioner","identifier":{"value":"92ZZVW","use":"official","system":""},"display":"Pharmacist-TestTwo ThreeQualification"}}


### 4. POST the above response as payload to the /R4/QuestionnaireResponse/$extract URL, to receive the Goal

POST /R4/QuestionnaireResponse/$extract HTTP/1.1
x-api-key: *****
request-context: *****
x-correlation-id: *****
authorization: Bearer *****
Host: 
User-Agent: AHC/1.0
Connection: keep-alive
Accept: */*
Content-Type: application/fhir+json; charset=UTF-8
Content-Length: 1336

{"subject":{"type":"Patient","identifier":{"value":"ZZZ9999","use":"official","system":""},"display":"J WCDHB Pace"},"authored":"2024-03-21T21:16:11.712Z","meta":{"lastUpdated":"2024-03-21T21:17:01.527Z","versionId":"1"},"status":"completed","item":[{"answer":[{"valueCoding":{"code":"yes","display":"Yes"}}],"linkId":"2274198486440","text":"Do you want to smoke less or quit altogether?"},{"answer":[{"valueCoding":{"display":"Providing resources"}}],"linkId":"6423468528467","text":"How can we best support you in reaching that goal?"},{"answer":[{"valueString":"test"}],"linkId":"8196750247032","text":"Are there any challenges you foresee in achieving this?"},{"item":[{"answer":[{"valueString":"active"}],"linkId":"goalStatus"},{"answer":[{"valueString":"Stop smoking"}],"linkId":"goalDescription"},{"answer":[{"valueBoolean":true}],"linkId":"goalSubject"}],"linkId":"goalExtractContainer"}],"resourceType":"QuestionnaireResponse","questionnaire":"","id":"19d56ad6-25d1-4299-b06d-58a24b361411","author":{"type":"Practitioner","identifier":{"value":"92ZZVW","use":"official","system":""},"display":"Pharmacist-TestTwo ThreeQualification"}}

HTTP/1.1 200 OK
{"resourceType":"Bundle","type":"transaction","entry":[{"resource":{"resourceType":"Goal","lifecycleStatus":"active","description":{"coding":"Stop smoking"},"subject":{"type":"Patient","identifier":{"value":"ZZZ9999","use":"official","system":""},"display":"J WCDHB Pace"}},"fullUrl":"urn:uuid:goal","request":{"method":"POST","url":"Goal"}}]}

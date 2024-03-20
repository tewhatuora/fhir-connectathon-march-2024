# NEMS Guaranteed Subscriber Java

This application can be used as a template to connect to NEMS. It supports the following use cases:

- Connects to the Pilot event - Death notice
- Connects to the FHIR NHI API to retrieve additional information not provided in the payload
- Writes the payload to a simple database procedure

This code can be used to build new events and leverage the logic. Guidelines has been provided below to describe how the program has been structured.

## Prerequisites

- Maven version 3.9.4
- Java 17
- Docker for desktop (Optional)

## How to Run

- Clone this repository to a directory of your choosing.

- To connect this application to the solace broker you will need the following connection details

  - HostName and Port
  - VPN Name
  - Queue Name
  - Username
  - Password

    These will be provided to you separately.

- Once you have received your credentials add them to the application.properties file found here `src-> main-> resources`
- In the directory you cloned the repository into run `mvn clean install`

## Run locally

- Run `java -jar target/GuaranteedSubscriber-1.0.0.jar`

## Run containerized using docker

- Ensure docker desktop is running
- Run `docker-compose up --build `

## Guidelines

The code provided has been designed to enable quick development for developers that has limited codeing experience. For repeatable tasks like connectivity, this has been provided for you. If you have limited FHIR helper code has been provided. Going forward these helper files will be extended as new events are added to the NEMS broker.

### Getting Started

To start there is a package `EventLoader.java` this is the package where you add your custom code. This package gets invoked once the application has successfully connected to the queue and waiting for messages.

The application expects the topic subscription to be in the following format

    [domain] / [resource] / [event]

It looks for the event name and perfoms a check. This allows the client application to support multiple event types. It is recommeded to create a new package that accepts `com.solace.messaging.receiver.InboundMessage`.

### Utilities

The Utilities folder contains a number of helper packages. These will be extended as new events as the NEMS platform evolves. If you modify these, you need to ensure that your repo synchronisation protects you from overwriting your code.

- DatabaseUtil: Provides connect string to a sample Oracle stored procedure. The definition is stored in the `DeathDatabase.java` package.
- EventUtil: Provides connection and helper functions to manage events easily
- NhiUtil: Gets data from the NHI FHIR API in a digestable format. Currently on Death data is supported
- RestUtil: REST helper package that helps call REST APIs and parse them

### Curl to POST

`curl --location 'https://ring-of-fhir.messaging.solace.cloud:9443/fhir/QuestionnaireResponse/SmokingCessation' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic cXVlc3Rpb25uYWlyZTpmamc2RU1SLnRtay5ybWE1ZnJm' \
--header 'Cookie: TSID=609e013db6d8eb00' \
--data '{
    "test": "manual"
}'`

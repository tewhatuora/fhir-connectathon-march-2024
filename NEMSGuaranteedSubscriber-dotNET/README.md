# NEMS Guaranteed Subscriber .NET

This is a simple NEMS queue consumer console app written using .NET 8. If an error occurs the stacktrace will be printed to the console. There are 2 installation types, Docker and local, choose the one most appropriate to your environment.

### Installation Instructions (Docker)

#### Requirements:

- Docker

#### Installation:

1. Clone this repo into a folder of your choice.
2. Modify the `properties.json` the details are mostly complete. Ask the NEMS team leader for a username and password and update the place holders `YOUR-USERNAME` and `YOUR_PASSWORD`
3. Run `docker-compose up --build` in the root folder.

The application should install itself and start up. Once started it will connect to the NEMS instance and start listening on the specified queue. Once a message is sent to the queue the consumer will pick it up and should display the message contents to the terminal.

### Installation Instructions (IDE)

If you don't have an IDE install Visual Studio Code. Its a lightweight IDE that supports most languages.

#### Requirements:

- .NET Version 8.0
- IDE that supports .NET

#### Installation:

1.  Clone this repo into a folder of your choice.
2.  Copy the `properties.json` into the source folder
3.  Select the `source/properties.json` file and under the Properties panel set `Copy to Output` to `Copy if newer`
4.  Open the `source/properties.json`, the details are mostly complete. Ask the NEMS team leader for a username and password and update the place holders `YOUR-USERNAME` and `YOUR_PASSWORD`

# Sparta Application

### Overview

**SpartaApplication** is a server application that ingests and persists registers sent by a provider and retrieves those
registers on a provider basis.

### Endpoints

The application exposes the following HTTP endpoints:

- `/load/{provider}`. Receives a request including a provider and a number of records. These are persisted in the
  database and the number of records persisted per call is returned.
- `/data/{provider}/total`. Receives a request indicating a provider and returns the total amount of records registered
  for that provider.

### Considerations

Several considerations and assumptions have been made during the coding of this application.

- All the models are immutable to avoid data modification.
- Debug logs have been placed in both endpoints. However, the default log level is now `INFO`, change it to `DEBUG` in
  the `application.properties` file in order to see the logs printed.

A [MultiValuedMap](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiValuedMap.html)
is used for persistence. This choice has been made in order to persist multiple records for the same provider since a
HashMap would overwrite the last value whenever the same key is placed in the map.

- Since the application receives the requests from a closed source there's no access to proper testing data, so a text
  file with valid data has been generated in order to properly test the application. The text file has been generated
  following these steps:
    1. Launch the application in debug mode.
    2. Print on console the content of the `byte[]` received in the `/load/{provider}` endpoint.
    3. Place a breakpoint at the beginning of said endpoint, so it stops just after the request arrives.
    4. Start the client. The content of the first request will be printed in the console.
    5. Save that content in a file and place it in the `test/resources` directory. Alternatively, the console can be
       wiped clean and the process can be allowed another request to change the data.
    6. To test it, parse the text file and convert it to `byte[]` (see `convertTestFileToByteArray()` in `Utils` class
       inside the `test`folder).

### Instructions

In order to use the application, the following steps must be taken:

1. Install Gradle (if not installed).
    - Instructions: https://gradle.org/install/
2. First launch option: build & run the application using the `jar`.
   1. Build it.
       - On Linux/MacOS: run `./gradlew clean build` in the project root folder.
       - On Windows: run `gradle clean build` in the project root folder.
   2. Run it:
      - Go to `sparta_feed_consumer\build\libs\` and run `java -jar sparta_server-0.0.1-SNAPSHOT.jar`.
3. Second launch option: run the spring boot command.
    - On Linux/MacOS: run `./gradlew bootRun` in the project root folder.
    - On Windows: run `gradle bootRun` in the project root folder.
4. Run the client.
    - Run `java -jar sparta-client.jar` in the project root folder.

### Future versions and upgrades

There are a few upgrades I would like to introduce in the application if I ever have the time:

- Use `WebFlux` to allow non-blocking operations.
- Check the integrity of the data using `crc32SensorData`.
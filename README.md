# CDC API
Hi, and welcome to the Charge & Drive Connect API project!

### Useful commands
To run the application use the following command:
```bash
$ ./gradlew bootRun -Dspring.profiles.active=$profile
``` 

To simply build it use:
```bash
$ ./gradlew build
``` 

To run the tests use:
```bash
$ ./gradlew test
``` 

To generate swagger documentation and typescript client:
```bash
$ ./gradlew test swagger
``` 

### Technology stack

#### Spring Boot
This application is a Spring Boot web service.

#### Docker

### Structure 

#### Package structure
The structure is by layer and not feature. The reason for choosing it by layer was because there were a lot of things common between the classes in each layer. Currently the project consists of 10 packages.

#### Tests
All classes should have test high test coverage using unit tests. All unit teste classes should have the post-fix `Test`. If an integration test is needed the class should have the post-fix `IT`.

### Available profiles

#### dev
Runs the application in development mode. 

#### test 
Runs the application using the **test** envionment configuration. Requires VPN for connecting to spring cloud configurations.


### Toggles (available profiles)

#### price-profiles-from-rest
forces price-profiles fetching from pricing-service apis (instead of Elasticsearch). This can be useful when Elasticsearch is being re-indexed

### Troubleshooting
**The builds are slow**

Try to run the command with the flag **--offline**. This can speed up the build a lot. You will however not get the latest packages.

**I'm getting random errors**

Try to make a clean build. You can do that with the following command:
```bash
$ ./gradlew clean build
``` 

### Useful links

**Jenkins**

http://jenkins.tingcore-infra.com/job/charge-and-drive/job/cdc-api

**SonarQube**



### Swagger
We are using Swagger in our services in order to document our rest endpoints. Most of the work will be done for you by Swagger. However you will need to annotate your controllers. More info can be fond here:

> [Swagger guide](http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)

If you run the the **bootRun** command a Swagger gui page will be generated for you. You can find the local version here:

> [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

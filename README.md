# Ad-Tech Microservice
AdTech Microservice that tracks the ads that are being delivered through their lifecycle and generates some simple statistics.

Table of contents
=================
<!--ts-->
   * [Prerequisites](#prerequisites)
   * [About Application](#about-application)
   * [Execution](#execution)
<!--te-->

## Prerequisites
 * Maven (3.6.3)
 * Java 11
 * Docker
 * Docker-Compose

## About Application
It is a Simple Ad-Tech Application.
 * Gets Advertisement Info
 * Stores the Records in Postgres DB
 * Generates Simple Statistics About Advertisement Records
 
## Execution
Go to the root of the project and run commands below. 
```Bash
mvn clean install -DskipTests
```

```Bash
docker-compose up
```



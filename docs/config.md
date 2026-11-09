Configuration Demo

## JVM system property

Command:
java '-Dpolar.greeting=Welcome from JVM' -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar

Response:
Welcome from JVM

## CLI arguement

java -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar --polar.greeting="Welcome from CLI"

Response:
Welcome from CLI

## Environment variable

Command:
$env:POLAR_GREETING = "Welcome from ENV"
java -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar

Response:
Welcome from ENV

## JVM Property and CLI arguement

Command: java '-Dpolar.greeting=Welcome from JVM' -jar build/libs/catalog-service-0.0.1-SNAPSHOT.jar --polar.greeting="Welcome from CLI"

Response:
Welcome from CLI
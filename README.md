# wildfly-swarm-modules-demo

WildFly Swarm Modules Demo

## Usage

### Run App

``` sh
$ ./mvnw clean package \
  && java -jar target/wildfly-swarm-modules-demo-swarm.jar
```

``` sh
$ curl localhost:8080 
{"conn": "org.jboss.jca.adapters.jdbc.jdk7.WrappedConnectionJDK7@..."}
```

### Run the test

``` sh
$ ./mvnw clean verify \
  -Dswarm.project.stage.file=file://`pwd`/src/main/resources/project-stages.yml
```

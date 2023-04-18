# Cacao
## _Messaging App_


Cacao is a messaging app inpired by the Korean chat app, Kakao.
 This is the backend of the application.
 It was made using various [Spring](https://spring.io/projects/spring-boot) projects,
 [H2 Database](http://h2database.com/html/main.html),
  and [Google Oauth](https://developers.google.com/identity/protocols/oauth2).
   The [front end] can be found [here][front end].
 

## Features

- Receive messages in real time
- Google Oauth authorization
- Add friends
- See unread messages count
- Send messages even when the friend is offline
- Receive messages sent to you weren't logged in


## Installation

Cacao requires [Java 11](https://www.oracle.com/java/technologies/javase-downloads.html) and [Maven](https://maven.apache.org/install.html) to build and run.


#### Build

You must create the `application.properties` file using the sample or include the necessary properties at runtime. For example, you need to include the google client id and secret.
You can also put a `data.sql` file to initialize the database with users and friends. 

Put the [front end] in the parent directory of the backend directory or change the location in the pom.xml.
You must first install the [front end] using the instructions.  The backend will build the front end and include it in the jar.


```sh
mvn install
```
If you want to deploy the [front end] separately you must modify the maven build.

#### Run

```sh
java -jar server.jar
```

Add any changed properties as runtime arguments like so:
```sh
java -jar server.jar --server.port=80 
```



[front end]: https://github.com/aquino-a/cacao-web

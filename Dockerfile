FROM openjdk:11
EXPOSE 8080
ADD ./target/recipe-api-0.0.1-SNAPSHOT.jar recipe-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","recipe-api-0.0.1-SNAPSHOT.jar"]
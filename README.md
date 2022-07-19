# ABN AMRO BE Assessment
****

# Getting Started

### API Endpoints
* GET /recipes/search : get a list of recipes by criteria:
  * serving: filter by number of people served
  * title: title of the recipe
  * ingredientIn: ingredient list that should be in the recipe
  * ingredientOut: ingredient list that should not be in the recipe
  * type (Non-Vegetarian / Vegetarian): filter by vegetarian or non-vegetarian recipes
  * instruction: filter recipes which containing the following word
* POST /recipes/ : create a recipe
* GET /recipes/{id} : returns a recipe by id
* PUT /recipes/{id} : update information and ingredients of a recipe
* DELETE /recipes/{id} : remove a recipe by id
* POST /ingredients/ : create an ingredient
* GET /ingredients/{id} : returns an ingredient by id
* PUT /ingredients/{id} : update an ingredient
* DELETE /ingredients/{id} : remove an ingredient by id

### Security
This service has a basic authentication configured by property (Application.yml)
* User: abn
* Password: 1234

### API Documentation
[Swagger - http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Stack
* Java 11
* Maven
* Spring Boot 2
* Postgres
* Flyway
* Docker-Compose
* Spring Data JPA

## Testing
* JUnit 5
* Testcontainer

### Run the Application

#### Docker-Compose
```bash
docker-compose build && docker-compose up
```
At the first run the database will be created and the server will start at <http://localhost:8080>.

### Running Tests
```bash
./mvnw clean test
```

****

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Testcontainers](https://www.testcontainers.org/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web.security)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)


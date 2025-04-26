# tracking-api
This Spring Boot 3.x application exposes a RESTful API end-point to generate unique tracking numbers. 
It uses a Snowflake-like distributed ID algorithm to ensure uniqueness and high concurrency.

# Setup and Running
**Prerequisites:** Java 17+, Maven 3+

1. **Build the project:**
mvn clean package

java -jar target/tracking-service-0.0.1-SNAPSHOT.jar
OR
mvn spring-boot:run

http://{host}:{port}/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=2.5&created_at=2018-11-20T19:29:32%2B08:00&customer_id=123&customer_name=JohnDoe&customer_slug=john-doe

### Sample Response
{
  "trackingNumber": "TPID5CJ6T0AUU96O",
  "createdAt": "2025-04-26T17:01:30.6868086+05:30"
}


TP - Fixed Constant
ID - Destination country code
Remaining varying unique data.

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
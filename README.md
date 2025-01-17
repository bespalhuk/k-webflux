# Read Me First
Due to its simplicity, concurrency issues may occur.

# Techs
- Kotlin
- Spring Webflux
- Spring Data MongoDB (Reactive)
- Spring Cloud Stream (RabbitMQ, Kafka)
- WebClient
- Wiremock
- Testcontainers (MongoDB, RabbitMQ, Kafka)

# Getting Started
1 - Docker folder: <b>docker-compose up -d</b><br />
2 - Run: <b>./gradlew bootRun --args='--spring.profiles.active=local'</b><br />
3 - Access [swagger](http://localhost:8080/webjars/swagger-ui/index.html)<br />
4 - Create an entry<br />
5 - Update an entry<br />
5.1 - If <b>starter is different</b>: update will be made after producing and consuming a message from <b>RabbitMQ</b>.<br />
5.2 - If <b>legendary is different</b>: update will be made after producing and consuming a message from <b>Kafka</b>.<br />

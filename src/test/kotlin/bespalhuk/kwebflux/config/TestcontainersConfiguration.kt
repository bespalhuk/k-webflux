package bespalhuk.kwebflux.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.kafka.ConfluentKafkaContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun mongoContainer(): MongoDBContainer =
        MongoDBContainer(DockerImageName.parse("mongo:latest"))

    @Bean
    @ServiceConnection
    fun rabbitMQContainer(): RabbitMQContainer =
        RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management"))

    @Bean
    @ServiceConnection
    fun kafkaContainer(): ConfluentKafkaContainer =
        ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
}

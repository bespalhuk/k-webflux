package bespalhuk.kwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@ConfigurationPropertiesScan
@EnableReactiveMongoAuditing
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

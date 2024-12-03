package bespalhuk.kwebflux.config.persistence

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["bespalhuk.kwebflux.app.adapter.output.persistence"])
class MongoReactiveConfig

package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDomain
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserPersistenceAdapter(
    private val userDocumentRepository: UserDocumentRepository
) : SaveUserPortOut {

    override fun save(user: User): Mono<User> =
        Mono.just(user)
            .map { it.toDocument() }
            .flatMap { userDocumentRepository.save(it) }
            .map { it.toDomain() }
}

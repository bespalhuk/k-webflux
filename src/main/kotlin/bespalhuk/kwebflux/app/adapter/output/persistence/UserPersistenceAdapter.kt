package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDomain
import bespalhuk.kwebflux.core.common.exception.UserNotFoundException
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.port.output.DeleteUserPortOut
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserPersistenceAdapter(
    private val userDocumentRepository: UserDocumentRepository,
) : SaveUserPortOut, FindUserPortOut, DeleteUserPortOut {

    override fun save(user: User): Mono<User> =
        Mono.just(user)
            .map { it.toDocument() }
            .flatMap { userDocumentRepository.save(it) }
            .map { it.toDomain() }

    override fun find(id: String): Mono<User> =
        Mono.just(id)
            .flatMap { userDocumentRepository.findById(it) }
            .switchIfEmpty(Mono.error(UserNotFoundException()))
            .map { it.toDomain() }

    override fun delete(id: String): Mono<Void> =
        Mono.just(id)
            .flatMap { userDocumentRepository.deleteById(it) }
}

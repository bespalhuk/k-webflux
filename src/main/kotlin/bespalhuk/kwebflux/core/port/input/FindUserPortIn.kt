package bespalhuk.kwebflux.core.port.input

import bespalhuk.kwebflux.core.domain.User
import reactor.core.publisher.Mono

fun interface FindUserPortIn {
    fun find(id: String): Mono<Result<User>>
}

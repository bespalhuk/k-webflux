package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.User
import reactor.core.publisher.Mono

fun interface FindUserPortOut {
    fun find(username: String): Mono<User>
}

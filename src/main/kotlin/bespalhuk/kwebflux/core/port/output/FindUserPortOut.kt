package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.User
import reactor.core.publisher.Mono

fun interface FindUserPortOut {
    fun find(id: String): Mono<User>
}

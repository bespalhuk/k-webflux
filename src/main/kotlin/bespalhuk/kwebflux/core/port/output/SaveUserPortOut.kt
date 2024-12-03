package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.User
import reactor.core.publisher.Mono

fun interface SaveUserPortOut {
    fun save(user: User): Mono<User>
}

package bespalhuk.kwebflux.core.port.output

import reactor.core.publisher.Mono

fun interface DeleteUserPortOut {
    fun delete(id: String): Mono<Void>
}

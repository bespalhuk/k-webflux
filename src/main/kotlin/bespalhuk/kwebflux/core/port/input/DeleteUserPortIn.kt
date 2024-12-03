package bespalhuk.kwebflux.core.port.input

import reactor.core.publisher.Mono

fun interface DeleteUserPortIn {
    fun delete(id: String): Mono<Result<Boolean>>
}

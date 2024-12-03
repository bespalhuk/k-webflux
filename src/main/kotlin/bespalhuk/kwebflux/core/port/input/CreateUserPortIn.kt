package bespalhuk.kwebflux.core.port.input

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UserInput
import reactor.core.publisher.Mono

fun interface CreateUserPortIn {
    fun create(input: UserInput): Mono<Result<User>>
}
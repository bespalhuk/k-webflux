package bespalhuk.kwebflux.core.port.input

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterInput
import reactor.core.publisher.Mono

fun interface UpdateStarterPortIn {
    fun update(input: UpdateStarterInput): Mono<Result<User>>
}

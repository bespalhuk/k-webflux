package bespalhuk.kwebflux.core.port.input

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryInput
import reactor.core.publisher.Mono

fun interface UpdateLegendaryPortIn {
    fun update(input: UpdateLegendaryInput): Mono<Result<User>>
}

package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.port.input.FindUserPortIn
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

private val log = KotlinLogging.logger {}

@Service
class FindUserUseCase(
    private val findUserPortOut: FindUserPortOut,
) : FindUserPortIn {

    override fun find(id: String): Mono<Result<User>> =
        Mono.just(id)
            .flatMap { findUserPortOut.find(it) }
            .map { Result.success(it) }
            .onErrorResume { throwable ->
                log.error { "Error finding user: $id" }
                Mono.just(Result.failure(throwable))
            }
            .log("FindUserUseCase.find", Level.INFO, SignalType.ON_NEXT)
}

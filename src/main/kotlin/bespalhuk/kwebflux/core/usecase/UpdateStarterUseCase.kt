package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.service.PokemonService
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterInput
import bespalhuk.kwebflux.core.port.input.UpdateStarterPortIn
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

private val log = KotlinLogging.logger {}

@Service
class UpdateStarterUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : UpdateStarterPortIn {

    override fun update(input: UpdateStarterInput): Mono<Result<User>> =
        Mono.just(input)
            .flatMap { findUserPortOut.find(it.id) }
            .flatMap { update(it, input) }
            .flatMap { saveUserPortOut.save(it) }
            .map { Result.success(it) }
            .onErrorResume { throwable ->
                log.error { "Error updating user: ${input.id} - starter: ${input.starter.number}" }
                Mono.just(Result.failure(throwable))
            }
            .log("UpdateStarterUseCase.update", Level.INFO, SignalType.ON_NEXT)

    private fun update(
        user: User,
        input: UpdateStarterInput,
    ): Mono<User> =
        Mono.just(user)
            .map {
                it.team.starter = input.starter
                it
            }
            .flatMap { applyMove(it) }

    private fun applyMove(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { pokemonService.getMove(it.team.starter) }
            .map {
                user.apply {
                    user.team.starterMove = it
                }
            }
}

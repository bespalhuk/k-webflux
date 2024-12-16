package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.service.PokemonService
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryInput
import bespalhuk.kwebflux.core.port.input.UpdateLegendaryPortIn
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

private val log = KotlinLogging.logger {}

@Service
class UpdateLegendaryUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : UpdateLegendaryPortIn {

    override fun update(input: UpdateLegendaryInput): Mono<Result<User>> =
        Mono.just(input)
            .flatMap { findUserPortOut.find(it.id) }
            .flatMap { update(it, input) }
            .flatMap { saveUserPortOut.save(it) }
            .map { Result.success(it) }
            .onErrorResume { throwable ->
                log.error { "Error updating user: ${input.id} - legendary: ${input.legendary.number}" }
                Mono.just(Result.failure(throwable))
            }
            .log("UpdateLegendaryUseCase.update", Level.INFO, SignalType.ON_NEXT)

    private fun update(
        user: User,
        input: UpdateLegendaryInput,
    ): Mono<User> =
        Mono.just(user)
            .map {
                it.team.legendary = input.legendary
                it
            }
            .flatMap { applyMove(it) }

    private fun applyMove(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { pokemonService.getMove(it.team.legendary) }
            .map {
                user.apply {
                    user.team.legendaryMove = it
                }
            }
}

package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryOutput
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterOutput
import bespalhuk.kwebflux.core.domain.vo.UpdateUserInput
import bespalhuk.kwebflux.core.port.input.UpdateUserPortIn
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import bespalhuk.kwebflux.core.port.output.UpdateLegendaryPortOut
import bespalhuk.kwebflux.core.port.output.UpdateStarterPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

private val log = KotlinLogging.logger {}

@Service
class UpdateUserUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val saveUserPortOut: SaveUserPortOut,
    private val updateStarterPortOut: UpdateStarterPortOut,
    private val updateLegendaryPortOut: UpdateLegendaryPortOut,
) : UpdateUserPortIn {

    override fun update(input: UpdateUserInput): Mono<Result<User>> =
        Mono.just(input)
            .flatMap { findUserPortOut.find(it.id) }
            .flatMap { update(it, input) }
            .flatMap { saveUserPortOut.save(it) }
            .map { Result.success(it) }
            .onErrorResume { throwable ->
                log.error { "Error updating user: ${input.id}" }
                Mono.just(Result.failure(throwable))
            }
            .log("UpdateUserUseCase.update", Level.INFO, SignalType.ON_NEXT)

    private fun update(
        user: User,
        input: UpdateUserInput,
    ): Mono<User> =
        Mono.just(user)
            .map {
                it.team.name = input.team
                updateMembers(it, input)
                it
            }

    private fun updateMembers(
        user: User,
        input: UpdateUserInput,
    ) {
        if (user.team.starter != input.starter) {
            updateStarter(input)
        }
        if (user.team.legendary != input.legendary) {
            updateLegendary(input)
        }
    }

    private fun updateStarter(input: UpdateUserInput) =
        updateStarterPortOut.publish(
            UpdateStarterOutput(
                input.id,
                input.starter,
            )
        )

    private fun updateLegendary(input: UpdateUserInput) =
        updateLegendaryPortOut.publish(
            UpdateLegendaryOutput(
                input.id,
                input.legendary,
            )
        )
}

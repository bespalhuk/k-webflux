package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.common.exception.UserAlreadyExistsException
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.mapper.toDomain
import bespalhuk.kwebflux.core.domain.service.PokemonService
import bespalhuk.kwebflux.core.domain.vo.CreateUserInput
import bespalhuk.kwebflux.core.port.input.CreateUserPortIn
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

private val log = KotlinLogging.logger {}

@Service
class CreateUserUseCase(
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : CreateUserPortIn {

    override fun create(input: CreateUserInput): Mono<Result<User>> =
        Mono.just(input)
            .map { it.toDomain() }
            .flatMap { applyMoves(it) }
            .flatMap { saveUserPortOut.save(it) }
            .map { Result.success(it) }
            .onErrorResume { throwable ->
                onErrorReturn(throwable, input)
            }
            .log("CreateUserUseCase.create", Level.INFO, SignalType.ON_NEXT)

    private fun applyMoves(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { pokemonService.getMoves(it.team) }
            .map {
                user.apply {
                    team.starterMove = it.first
                    team.legendaryMove = it.second
                }
            }

    private fun onErrorReturn(
        throwable: Throwable,
        input: CreateUserInput,
    ): Mono<out Result<User>> =
        if (throwable is DuplicateKeyException) {
            log.error { "User already exists: ${input.username}." }
            Mono.just(Result.failure(UserAlreadyExistsException()))
        } else {
            log.error { "Generic error." }
            Mono.error(throwable)
        }
}

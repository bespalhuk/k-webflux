package bespalhuk.kwebflux.app.adapter.output.web.pokemon

import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.exception.PokemonResponseException
import bespalhuk.kwebflux.config.client.ClientConstants.Constants.QUALIFIER_WEBCLIENT_POKEMON
import bespalhuk.kwebflux.config.client.ClientResponseErrorCompanions
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.port.output.RetrievePokemonPortOut
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.Exceptions
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import reactor.kotlin.core.publisher.onErrorResume
import java.util.logging.Level

@Component
class PokemonClientAdapter(
    @Qualifier(QUALIFIER_WEBCLIENT_POKEMON)
    private val pokemonWebClient: WebClient,
) : RetrievePokemonPortOut {

    companion object {
        const val RETRIEVE_POKEMON = "PokemonClientAdapter.retrieve"
    }

    override fun retrieveMoves(
        starter: StarterPokemonEnum,
        legendary: LegendaryPokemonEnum,
    ): Mono<Pair<String, String>> =
        Mono.zipDelayError(retrieve(starter.number), retrieve(legendary.number))
            .onErrorMap(RuntimeException::class.java) { it ->
                val message = Exceptions.unwrapMultiple(it)
                    .map { it.message }
                    .fold("") { empty, element -> empty.plus(" ").plus(element) }
                PokemonResponseException(message.trim())
            }
            .flatMap {
                Mono.just(
                    Pair(
                        it.t1.moves.random().move.name,
                        it.t2.moves.random().move.name,
                    )
                )
            }

    override fun retrieveMove(starter: StarterPokemonEnum): Mono<String> =
        Mono.just(starter)
            .flatMap { retrieveMove(it.number) }

    override fun retrieveMove(legendary: LegendaryPokemonEnum): Mono<String> =
        Mono.just(legendary)
            .flatMap { retrieveMove(it.number) }

    private fun retrieveMove(number: Int): Mono<String> =
        Mono.just(number)
            .flatMap { retrieve(it) }
            .onErrorMap(RuntimeException::class.java) {
                PokemonResponseException("Failed trying to retrieve move from pokemon $it")
            }
            .map { it.moves.random().move.name }

    private fun retrieve(number: Int): Mono<PokemonResponse> =
        pokemonWebClient.get()
            .uri("/pokemon/{number}", number)
            .retrieve()
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                ClientResponseErrorCompanions.notFound("Pokemon not found."),
            )
            .onStatus(
                { status -> status.is4xxClientError },
                ClientResponseErrorCompanions.toClientError(RETRIEVE_POKEMON),
            )
            .onStatus(
                { status -> status.is5xxServerError },
                ClientResponseErrorCompanions.toServerError(RETRIEVE_POKEMON),
            )
            .bodyToMono(PokemonResponse::class.java)
            .onErrorResume(RuntimeException::class) {
                Mono.error(it)
            }
            .log(RETRIEVE_POKEMON, Level.INFO, SignalType.ON_NEXT)
}

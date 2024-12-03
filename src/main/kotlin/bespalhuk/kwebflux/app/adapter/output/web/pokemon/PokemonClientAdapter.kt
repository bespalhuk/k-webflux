package bespalhuk.kwebflux.app.adapter.output.web.pokemon

import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonWebResponse
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.exception.PokemonResponseException
import bespalhuk.kwebflux.config.client.HttpClientErrorCompanions
import bespalhuk.kwebflux.config.client.WebClientConstants.Constants.BEAN_WEBCLIENT_POKEMON
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
    @Qualifier(BEAN_WEBCLIENT_POKEMON)
    private val webClientPokemon: WebClient,
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

    private fun retrieve(number: Int): Mono<PokemonWebResponse> =
        webClientPokemon.get()
            .uri("/pokemon/{number}", number)
            .retrieve()
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                HttpClientErrorCompanions.notFound("Pokemon not found."),
            )
            .onStatus(
                { status -> status.is4xxClientError },
                HttpClientErrorCompanions.toClientError(RETRIEVE_POKEMON)
            )
            .onStatus(
                { status -> status.is5xxServerError },
                HttpClientErrorCompanions.toServerError(RETRIEVE_POKEMON),
            )
            .bodyToMono(PokemonWebResponse::class.java)
            .onErrorResume(RuntimeException::class) {
                Mono.error(it)
            }
            .log(RETRIEVE_POKEMON, Level.INFO, SignalType.ON_NEXT)
}

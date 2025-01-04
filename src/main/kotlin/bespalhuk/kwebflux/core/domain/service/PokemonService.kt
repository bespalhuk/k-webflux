package bespalhuk.kwebflux.core.domain.service

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.port.output.RetrievePokemonPortOut
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PokemonService(
    private val retrievePokemonPortOut: RetrievePokemonPortOut,
) {

    fun getMoves(team: User.Team): Mono<Pair<String, String>> =
        Mono.just(team)
            .flatMap {
                retrievePokemonPortOut.retrieveMoves(it.starter, it.legendary)
            }

    fun getMove(starter: StarterPokemonEnum): Mono<String> =
        Mono.just(starter)
            .flatMap {
                retrievePokemonPortOut.retrieveMove(it)
            }

    fun getMove(legendary: LegendaryPokemonEnum): Mono<String> =
        Mono.just(legendary)
            .flatMap {
                retrievePokemonPortOut.retrieveMove(it)
            }
}

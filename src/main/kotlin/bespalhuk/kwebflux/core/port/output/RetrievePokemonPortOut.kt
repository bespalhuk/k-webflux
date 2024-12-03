package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import reactor.core.publisher.Mono

fun interface RetrievePokemonPortOut {
    fun retrieveMoves(starter: StarterPokemonEnum, legendary: LegendaryPokemonEnum): Mono<Pair<String, String>>
}

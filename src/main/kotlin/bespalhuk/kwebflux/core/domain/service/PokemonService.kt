package bespalhuk.kwebflux.core.domain.service

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.port.output.RetrievePokemonPortOut
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PokemonService(
    private val retrievePokemonPortOut: RetrievePokemonPortOut
) {

    fun getMoves(team: User.Team): Mono<Pair<String, String>> =
        Mono.just(team)
            .flatMap {
                retrievePokemonPortOut.retrieveMoves(it.starter, it.legendary)
            }
}

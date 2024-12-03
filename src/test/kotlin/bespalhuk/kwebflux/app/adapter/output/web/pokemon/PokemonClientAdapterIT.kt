package bespalhuk.kwebflux.app.adapter.output.web.pokemon

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonWebResponse
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.dataprovider.stub.PokemonStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import reactor.kotlin.test.test

class PokemonClientAdapterIT(
    @Autowired
    private val pokemonClientAdapter: PokemonClientAdapter,
) : IntegrationTest() {

    @Test
    fun `given starter and legendary, retrieve moves`() {
        val starter = StarterPokemonEnum.PIKACHU
        val starterResponse = response("shock")
        PokemonStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))

        pokemonClientAdapter.retrieveMoves(starter, legendary)
            .test().assertNext {
                assertThat(it.first).isNotNull()
                assertThat(it.second).isNotNull()
            }.verifyComplete()
    }

    private fun response(move: String): PokemonWebResponse {
        return PokemonWebResponse(
            listOf(
                MoveItem(
                    Move(move)
                )
            )
        )
    }
}

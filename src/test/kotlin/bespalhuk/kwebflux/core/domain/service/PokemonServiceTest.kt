package bespalhuk.kwebflux.core.domain.service

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.port.output.RetrievePokemonPortOut
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class PokemonServiceTest : UnitTest() {

    private lateinit var pokemonService: PokemonService

    private lateinit var retrievePokemonPortOut: RetrievePokemonPortOut

    @BeforeEach
    fun beforeEach() {
        retrievePokemonPortOut = mockk()
        pokemonService = PokemonService(
            retrievePokemonPortOut,
        )
    }

    @Test
    fun `verify calls when retrieving moves`() {
        val team = UserDataProvider().user().team

        val moves = Pair(
            "shock",
            "hadouken",
        )

        every {
            retrievePokemonPortOut.retrieveMoves(
                team.starter,
                team.legendary,
            )
        } returns Mono.just(moves)

        pokemonService.getMoves(team)
            .test().assertNext {
                assertThat(it)
                    .usingRecursiveComparison()
                    .isEqualTo(moves)
            }.verifyComplete()

        verify(exactly = 1) {
            retrievePokemonPortOut.retrieveMoves(
                team.starter,
                team.legendary,
            )
        }
    }

    @Test
    fun `verify calls when retrieving starter move`() {
        val starter = StarterPokemonEnum.PIKACHU
        val move = "shock"

        every {
            retrievePokemonPortOut.retrieveMove(starter)
        } returns Mono.just(move)

        pokemonService.getMove(starter)
            .test().assertNext {
                assertThat(it).isEqualTo(move)
            }.verifyComplete()

        verify(exactly = 1) {
            retrievePokemonPortOut.retrieveMove(starter)
        }
    }

    @Test
    fun `verify calls when retrieving legendary move`() {
        val legendary = LegendaryPokemonEnum.MEW
        val move = "hadouken"

        every {
            retrievePokemonPortOut.retrieveMove(legendary)
        } returns Mono.just(move)

        pokemonService.getMove(legendary)
            .test().assertNext {
                assertThat(it).isEqualTo(move)
            }.verifyComplete()

        verify(exactly = 1) {
            retrievePokemonPortOut.retrieveMove(legendary)
        }
    }
}

package bespalhuk.kwebflux.core.domain.service

import bespalhuk.kwebflux.abstraction.UnitTest
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
    fun init() {
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
        } returns (Mono.just(moves))

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
}

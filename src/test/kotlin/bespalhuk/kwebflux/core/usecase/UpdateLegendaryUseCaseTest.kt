package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.service.PokemonService
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryInput
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class UpdateLegendaryUseCaseTest : UnitTest() {

    private lateinit var updateLegendaryUseCase: UpdateLegendaryUseCase

    private lateinit var findUserPortOut: FindUserPortOut
    private lateinit var pokemonService: PokemonService
    private lateinit var saveUserPortOut: SaveUserPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        pokemonService = mockk()
        saveUserPortOut = mockk()
        updateLegendaryUseCase = UpdateLegendaryUseCase(
            findUserPortOut,
            pokemonService,
            saveUserPortOut,
        )
    }

    @Test
    fun `verify calls when legendary was changed`() {
        val user = UserDataProvider().user()
        val input = UpdateLegendaryInput(
            user.id!!,
            LegendaryPokemonEnum.MEWTWO,
        )
        val move = "gou hadouken"

        every {
            findUserPortOut.find(any())
        } returns Mono.just(user)

        every {
            pokemonService.getMove(any<LegendaryPokemonEnum>())
        } returns Mono.just(move)

        every {
            saveUserPortOut.save(any())
        } returns Mono.just(user)

        updateLegendaryUseCase.update(input)
            .test().assertNext {
                assertThat(it.isSuccess).isTrue
                assertThat(it.getOrThrow())
                    .usingRecursiveComparison()
                    .isEqualTo(user)
                assertThat(it.getOrNull()!!.team.legendaryMove)
                    .isEqualTo(move)
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
            pokemonService.getMove(any<LegendaryPokemonEnum>())
            saveUserPortOut.save(any())
        }
    }
}

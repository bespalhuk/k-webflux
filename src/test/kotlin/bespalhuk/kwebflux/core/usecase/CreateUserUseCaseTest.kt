package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.domain.service.PokemonService
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import bespalhuk.kwebflux.dataprovider.UserInputDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class CreateUserUseCaseTest : UnitTest() {

    private lateinit var createUserUseCase: CreateUserUseCase

    private lateinit var pokemonService: PokemonService
    private lateinit var saveUserPortOut: SaveUserPortOut

    @BeforeEach
    fun init() {
        pokemonService = mockk()
        saveUserPortOut = mockk()
        createUserUseCase = CreateUserUseCase(
            pokemonService,
            saveUserPortOut,
        )
    }

    @Test
    fun `verify calls when input return success`() {
        val input = UserInputDataProvider().input()
        val moves = Pair(
            "shock",
            "hadouken",
        )
        val savedUser = UserDataProvider().user()

        every {
            pokemonService.getMoves(any())
        } returns (Mono.just(moves))

        every {
            saveUserPortOut.save(any())
        } returns (Mono.just(savedUser))

        createUserUseCase.create(input)
            .test().assertNext {
                assertThat(it.isSuccess).isTrue
                assertThat(it.getOrNull())
                    .usingRecursiveComparison()
                    .isEqualTo(savedUser)
            }.verifyComplete()

        verify(exactly = 1) {
            pokemonService.getMoves(any())
            saveUserPortOut.save(any())
        }
    }
}
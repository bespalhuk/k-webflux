package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.common.exception.UserNotFoundException
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.core.port.output.SaveUserPortOut
import bespalhuk.kwebflux.core.port.output.UpdateLegendaryPortOut
import bespalhuk.kwebflux.core.port.output.UpdateStarterPortOut
import bespalhuk.kwebflux.dataprovider.UpdateUserInputDataProvider
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class UpdateUserUseCaseTest : UnitTest() {

    private lateinit var updateUserUseCase: UpdateUserUseCase

    private lateinit var findUserPortOut: FindUserPortOut
    private lateinit var saveUserPortOut: SaveUserPortOut
    private lateinit var updateStarterPortOut: UpdateStarterPortOut
    private lateinit var updateLegendaryPortOut: UpdateLegendaryPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        saveUserPortOut = mockk()
        updateStarterPortOut = mockk()
        updateLegendaryPortOut = mockk()
        updateUserUseCase = UpdateUserUseCase(
            findUserPortOut,
            saveUserPortOut,
            updateStarterPortOut,
            updateLegendaryPortOut,
        )
    }

    @Test
    fun `verify calls when input return success and user has the same team members`() {
        val input = UpdateUserInputDataProvider().input()
        val user = UserDataProvider().user()

        every {
            findUserPortOut.find(any())
        } returns Mono.just(user)

        every {
            saveUserPortOut.save(any())
        } returns Mono.just(user)

        updateUserUseCase.update(input)
            .test().assertNext {
                assertThat(it.isSuccess).isTrue
                assertThat(it.getOrNull())
                    .usingRecursiveComparison()
                    .isEqualTo(user)
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
            saveUserPortOut.save(any())
        }

        verify(exactly = 0) {
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }

    @Test
    fun `verify calls when input return success and user has different team members`() {
        val input = UpdateUserInputDataProvider().input()
        val user = UserDataProvider().user()
        user.team.starter = StarterPokemonEnum.BULBASAUR
        user.team.legendary = LegendaryPokemonEnum.ARTICUNO

        every {
            findUserPortOut.find(any())
        } returns Mono.just(user)

        every {
            updateStarterPortOut.publish(any())
        } just Runs

        every {
            updateLegendaryPortOut.publish(any())
        } just Runs

        every {
            saveUserPortOut.save(any())
        } returns Mono.just(user)

        updateUserUseCase.update(input)
            .test().assertNext {
                assertThat(it.isSuccess).isTrue
                assertThat(it.getOrNull())
                    .usingRecursiveComparison()
                    .isEqualTo(user)
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
            saveUserPortOut.save(any())
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }

    @Test
    fun `verify calls when user does not exists`() {
        val input = UpdateUserInputDataProvider().input()

        every {
            findUserPortOut.find(any())
        } returns Mono.error(UserNotFoundException())

        updateUserUseCase.update(input)
            .test().assertNext {
                assertThat(it.isFailure).isTrue
                assertThrows<UserNotFoundException> {
                    it.getOrThrow()
                }
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
        }

        verify(exactly = 0) {
            saveUserPortOut.save(any())
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }
}

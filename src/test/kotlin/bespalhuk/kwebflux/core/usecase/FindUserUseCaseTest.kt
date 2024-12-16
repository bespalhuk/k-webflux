package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.common.exception.UserNotFoundException
import bespalhuk.kwebflux.core.port.output.FindUserPortOut
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.util.UUID

class FindUserUseCaseTest : UnitTest() {

    private lateinit var findUserUseCase: FindUserUseCase

    private lateinit var findUserPortOut: FindUserPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        findUserUseCase = FindUserUseCase(
            findUserPortOut,
        )
    }

    @Test
    fun `verify calls when user is found`() {
        val user = UserDataProvider().user()

        every {
            findUserPortOut.find(any())
        } returns Mono.just(user)

        findUserUseCase.find(user.id!!)
            .test().assertNext {
                assertThat(it.isSuccess).isTrue
                assertThat(it.getOrNull())
                    .usingRecursiveComparison()
                    .isEqualTo(user)
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
        }
    }

    @Test
    fun `verify calls when user is not found`() {
        val id = UUID.randomUUID().toString()

        every {
            findUserPortOut.find(any())
        } returns Mono.error(UserNotFoundException())

        findUserUseCase.find(id)
            .test().assertNext {
                assertThat(it.isFailure).isTrue
                assertThrows<UserNotFoundException> {
                    it.getOrThrow()
                }
            }.verifyComplete()

        verify(exactly = 1) {
            findUserPortOut.find(any())
        }
    }
}

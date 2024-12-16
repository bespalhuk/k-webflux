package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.port.output.DeleteUserPortOut
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.util.UUID

class DeleteUserUseCaseTest : UnitTest() {

    private lateinit var deleteUserUseCase: DeleteUserUseCase

    private lateinit var deleteUserPortOut: DeleteUserPortOut

    @BeforeEach
    fun beforeEach() {
        deleteUserPortOut = mockk()
        deleteUserUseCase = DeleteUserUseCase(
            deleteUserPortOut,
        )
    }

    @Test
    fun `verify calls when user is deleted`() {
        val id = UUID.randomUUID().toString()

        every {
            deleteUserPortOut.delete(any())
        } returns Mono.empty()

        deleteUserUseCase.delete(id)
            .test()
            .verifyComplete()

        verify(exactly = 1) {
            deleteUserPortOut.delete(any())
        }
    }
}

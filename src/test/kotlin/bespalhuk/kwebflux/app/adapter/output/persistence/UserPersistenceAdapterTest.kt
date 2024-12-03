package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class UserPersistenceAdapterTest : UnitTest() {

    private lateinit var userPersistenceAdapter: UserPersistenceAdapter

    private lateinit var userDocumentRepository: UserDocumentRepository

    @BeforeEach
    fun init() {
        userDocumentRepository = mockk()
        userPersistenceAdapter = UserPersistenceAdapter(
            userDocumentRepository,
        )
    }

    @Test
    fun `verify calls when user is saved successfully`() {
        val user = UserDataProvider().user()
        val document = user.toDocument()

        every {
            userDocumentRepository.save(any())
        } returns Mono.just(document)

        userPersistenceAdapter.save(user)
            .test().assertNext {
                assertThat(it)
                    .usingRecursiveComparison()
                    .isEqualTo(user)
            }
            .verifyComplete()

        verify(exactly = 1) {
            userDocumentRepository.save(any())
        }
    }

    @Test
    fun `verify calls when trying to save user but failed`() {
        val user = UserDataProvider().user()

        every {
            userDocumentRepository.save(any())
        } throws IllegalArgumentException()

        userPersistenceAdapter.save(user)
            .test()
            .expectError()
    }
}

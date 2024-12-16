package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.core.common.exception.UserNotFoundException
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.util.UUID

class UserPersistenceAdapterTest : UnitTest() {

    private lateinit var userPersistenceAdapter: UserPersistenceAdapter

    private lateinit var userDocumentRepository: UserDocumentRepository

    @BeforeEach
    fun beforeEach() {
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
        } throws DuplicateKeyException("exception")

        userPersistenceAdapter.save(user)
            .test()
            .verifyError(DuplicateKeyException::class.java)

        verify(exactly = 1) {
            userDocumentRepository.save(any())
        }
    }

    @Test
    fun `verify calls when user is found successfully`() {
        val user = UserDataProvider().user()
        val document = user.toDocument()

        every {
            userDocumentRepository.findById(user.id!!)
        } returns Mono.just(document)

        userPersistenceAdapter.find(user.id!!)
            .test().assertNext {
                assertThat(it)
                    .usingRecursiveComparison()
                    .isEqualTo(user)
            }.verifyComplete()

        verify(exactly = 1) {
            userDocumentRepository.findById(any<String>())
        }
    }

    @Test
    fun `verify calls when user is not found`() {
        val id = UUID.randomUUID().toString()

        every {
            userDocumentRepository.findById(id)
        } returns Mono.empty()

        userPersistenceAdapter.find(id)
            .test()
            .verifyError(UserNotFoundException::class.java)

        verify(exactly = 1) {
            userDocumentRepository.findById(any<String>())
        }
    }

    @Test
    fun `verify calls when user is deleted`() {
        val id = UUID.randomUUID().toString()

        every {
            userDocumentRepository.deleteById(id)
        } returns Mono.empty()

        userPersistenceAdapter.delete(id)
            .test()
            .verifyComplete()

        verify(exactly = 1) {
            userDocumentRepository.deleteById(any<String>())
        }
    }
}

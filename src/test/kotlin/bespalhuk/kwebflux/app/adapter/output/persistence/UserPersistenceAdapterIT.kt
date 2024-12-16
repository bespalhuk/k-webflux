package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.core.common.exception.UserNotFoundException
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import bespalhuk.kwebflux.dataprovider.UserDocumentDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import reactor.kotlin.test.test
import java.util.UUID

class UserPersistenceAdapterIT(
    @Autowired
    private val userPersistenceAdapter: UserPersistenceAdapter,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() {
        userDocumentRepository.deleteAll().block()
    }

    @Test
    fun `given user, save successfully`() {
        val user = UserDataProvider().newUser()

        userPersistenceAdapter.save(user)
            .test().assertNext {
                assertThat(it.id).isNotNull()
                assertThat(it).usingRecursiveComparison()
                    .ignoringFields("id", "createdDate", "lastModified")
                    .isEqualTo(user)
            }.verifyComplete()
    }

    @Test
    fun `given user, fail to save`() {
        userDocumentRepository.save(UserDocumentDataProvider().document()).block()

        userPersistenceAdapter.save(UserDataProvider().newUser())
            .test()
            .verifyError(DuplicateKeyException::class.java)
    }

    @Test
    fun `given id, find successfully`() {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document).block()

        userPersistenceAdapter.find(document.id!!)
            .test().assertNext {
                assertThat(it).isNotNull()
            }
            .verifyComplete()
    }

    @Test
    fun `given id, fail to find`() {
        userPersistenceAdapter.find(UUID.randomUUID().toString())
            .test()
            .verifyError(UserNotFoundException::class.java)
    }

    @Test
    fun `given id, delete successfully when user exists`() {
        val document = UserDocumentDataProvider().document()
        val id = document.id!!
        userDocumentRepository.save(document).block()

        userDocumentRepository.existsById(id)
            .test().assertNext {
                assertThat(it).isTrue()
            }.verifyComplete()

        userPersistenceAdapter.delete(id)
            .test()
            .verifyComplete()

        userDocumentRepository.existsById(id)
            .test().assertNext {
                assertThat(it).isFalse()
            }.verifyComplete()
    }

    @Test
    fun `given id, delete successfully when user does not exists`() {
        val id = UUID.randomUUID().toString()

        userDocumentRepository.existsById(id)
            .test().assertNext {
                assertThat(it).isFalse()
            }.verifyComplete()

        userPersistenceAdapter.delete(id)
            .test()
            .verifyComplete()
    }
}

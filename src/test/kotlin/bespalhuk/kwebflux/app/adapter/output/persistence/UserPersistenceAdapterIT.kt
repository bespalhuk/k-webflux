package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import bespalhuk.kwebflux.dataprovider.UserDocumentDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import reactor.kotlin.test.test

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
    fun `given user, fail on save`() {
        userDocumentRepository.save(UserDocumentDataProvider().document()).block()

        userPersistenceAdapter.save(UserDataProvider().newUser())
            .test()
            .verifyError(DuplicateKeyException::class.java)
    }
}

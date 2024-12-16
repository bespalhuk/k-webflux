package bespalhuk.kwebflux.app.adapter.input.web.user.find

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kwebflux.dataprovider.UserDocumentDataProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.kotlin.test.test

class FindUserControllerIT(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    object Path {
        const val PATH: String = "/api/users/{id}"
    }

    @AfterEach
    fun afterEach() {
        userDocumentRepository.deleteAll().block()
    }

    @Test
    fun `given request, find successfully`() {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document).block()

        webTestClient.get()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.OK)
            .returnResult<UserResponse>()
            .responseBody
            .test()
            .expectNextMatches {
                it.id == document.id &&
                        it.username == document.username &&
                        it.team == document.team.name &&
                        it.starterMove == document.team.starterMove &&
                        it.legendaryMove == document.team.legendaryMove
            }
            .verifyComplete()
    }

    @Test
    fun `given request, fail finding`() {
        val document = UserDocumentDataProvider().document()

        webTestClient.get()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
    }
}

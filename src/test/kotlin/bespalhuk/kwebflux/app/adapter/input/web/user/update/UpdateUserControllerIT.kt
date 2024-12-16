package bespalhuk.kwebflux.app.adapter.input.web.user.update

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

class UpdateUserControllerIT(
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
    fun `given request, update successfully`() {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document).block()

        val request = UpdateUserRequest(
            "leaders",
            document.team.starter.number,
            document.team.legendary.number,
        )

        webTestClient.put()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.ACCEPTED)
            .returnResult<UserResponse>()
            .responseBody
            .test()
            .expectNextMatches {
                it.id == document.id &&
                        it.username == document.username &&
                        it.team == request.team &&
                        it.starterMove == document.team.starterMove &&
                        it.legendaryMove == document.team.legendaryMove
            }
            .verifyComplete()
    }

    @Test
    fun `given request, fail updating`() {
        val document = UserDocumentDataProvider().document()

        val request = UpdateUserRequest(
            "leaders",
            document.team.starter.number,
            document.team.legendary.number,
        )

        webTestClient.put()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
    }
}

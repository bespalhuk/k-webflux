package bespalhuk.kwebflux.app.adapter.input.web.user

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonWebResponse
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.dataprovider.UserDocumentDataProvider
import bespalhuk.kwebflux.dataprovider.stub.PokemonStub
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.kotlin.test.test

class CreateUserControllerIT(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    object Path {
        const val PATH: String = "/api/users"
    }

    @AfterEach
    fun afterEach() {
        userDocumentRepository.deleteAll().block()
    }

    @Test
    fun `given user request, create successfully`() {
        val document = UserDocumentDataProvider().document()

        val request = CreateUserRequest(
            document.username,
            document.team.starter.number,
            document.team.legendary.number,
        )

        stub()

        webTestClient.post()
            .uri {
                it.path(Path.PATH).build()
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<CreateUserResponse>()
            .responseBody
            .test()
            .expectNextMatches {
                it.id.isNotBlank() &&
                        it.username == document.username &&
                        it.starterMove.isNotBlank() &&
                        it.legendaryMove.isNotBlank()
            }
            .verifyComplete()
    }

    @Test
    fun `given user request, fail on creation`() {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document).block()

        val request = CreateUserRequest(
            document.username,
            document.team.starter.number,
            document.team.legendary.number,
        )

        stub()

        webTestClient.post()
            .uri {
                it.path(Path.PATH).build()
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)

    }

    private fun stub() {
        val starter = StarterPokemonEnum.PIKACHU
        val starterResponse = response("shock")
        PokemonStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))
    }

    private fun response(move: String): PokemonWebResponse {
        return PokemonWebResponse(
            listOf(
                MoveItem(
                    Move(move)
                )
            )
        )
    }
}

package bespalhuk.kwebflux.app.adapter.input.web.user.create

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.dataprovider.UserDocumentDataProvider
import bespalhuk.kwebflux.dataprovider.stub.PokemonApiStub
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
    fun `given request, create successfully`() {
        val document = UserDocumentDataProvider().document()

        val request = CreateUserRequest(
            document.username,
            document.team.name,
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
            .expectStatus().isEqualTo(HttpStatus.CREATED)
            .returnResult<UserResponse>()
            .responseBody
            .test()
            .expectNextMatches {
                it.id.isNotBlank() &&
                        it.username == document.username &&
                        it.team == document.team.name &&
                        it.starterMove == document.team.starterMove &&
                        it.legendaryMove == document.team.legendaryMove
            }
            .verifyComplete()
    }

    @Test
    fun `given request, fail on creation`() {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document).block()

        val request = CreateUserRequest(
            document.username,
            document.team.name,
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
        PokemonApiStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonApiStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))
    }

    private fun response(move: String): PokemonResponse =
        PokemonResponse(
            listOf(
                MoveItem(
                    Move(move)
                )
            )
        )
}

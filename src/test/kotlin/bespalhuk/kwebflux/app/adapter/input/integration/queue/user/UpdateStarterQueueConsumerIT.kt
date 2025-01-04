package bespalhuk.kwebflux.app.adapter.input.integration.queue.user

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.output.integration.queue.user.UpdateStarterQueueProducer
import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonWebResponse
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterOutput
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import bespalhuk.kwebflux.dataprovider.stub.PokemonStub
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import reactor.test.StepVerifier
import java.util.concurrent.TimeUnit

class UpdateStarterQueueConsumerIT(
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
    @Autowired
    private val updateStarterQueueProducer: UpdateStarterQueueProducer,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() {
        userDocumentRepository.deleteAll().block()
    }

    @Test
    fun `given update output, update user starter`() {
        val user = userDocumentRepository.save(
            UserDataProvider().newUser().toDocument()
        ).block()

        val starter = StarterPokemonEnum.BULBASAUR

        stub(starter)

        val output = UpdateStarterOutput(
            user?.id!!,
            starter,
        )
        updateStarterQueueProducer.publish(output)

        await()
            .atMost(2, TimeUnit.SECONDS)
            .until {
                userDocumentRepository.findById(output.id).block()?.team?.starter == starter
            }

        StepVerifier.create(userDocumentRepository.findById(output.id))
            .assertNext {
                assertThat(it.team.starter).isEqualTo(starter)
                assertThat(it.team.starterMove).isNotEqualTo(user.team.starterMove)
            }
            .verifyComplete()
    }

    private fun stub(starter: StarterPokemonEnum) {
        val response = response()
        PokemonStub.retrieve(starter.number, HttpStatus.OK, toJson(response))
    }

    private fun response(): PokemonWebResponse =
        PokemonWebResponse(
            listOf(
                MoveItem(
                    Move("vine whip")
                )
            )
        )
}

package bespalhuk.kwebflux.app.adapter.input.integration.topic.user

import bespalhuk.kwebflux.abstraction.IntegrationTest
import bespalhuk.kwebflux.app.adapter.output.integration.topic.user.UpdateLegendaryTopicProducer
import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kwebflux.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto.PokemonWebResponse
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryOutput
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

class UpdateLegendaryTopicConsumerIT(
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
    @Autowired
    private val updateLegendaryTopicProducer: UpdateLegendaryTopicProducer,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() {
        userDocumentRepository.deleteAll().block()
    }

    @Test
    fun `given update output, update user legendary`() {
        val user = userDocumentRepository.save(
            UserDataProvider().newUser().toDocument()
        ).block()

        val legendary = LegendaryPokemonEnum.MEWTWO

        stub(legendary)

        val output = UpdateLegendaryOutput(
            user?.id!!,
            legendary,
        )
        updateLegendaryTopicProducer.publish(output)

        await()
            .atMost(2, TimeUnit.SECONDS)
            .until {
                userDocumentRepository.findById(output.id).block()?.team?.legendary == legendary
            }

        StepVerifier.create(userDocumentRepository.findById(output.id))
            .assertNext {
                assertThat(it.team.legendary).isEqualTo(legendary)
                assertThat(it.team.legendaryMove).isNotEqualTo(user.team.legendaryMove)
            }
            .verifyComplete()
    }

    private fun stub(legendary: LegendaryPokemonEnum) {
        val response = response()
        PokemonStub.retrieve(legendary.number, HttpStatus.OK, toJson(response))
    }

    private fun response(): PokemonWebResponse =
        PokemonWebResponse(
            listOf(
                MoveItem(
                    Move("gou hadouken")
                )
            )
        )
}

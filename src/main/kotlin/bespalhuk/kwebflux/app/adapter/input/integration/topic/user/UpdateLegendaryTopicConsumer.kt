package bespalhuk.kwebflux.app.adapter.input.integration.topic.user

import bespalhuk.kwebflux.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kwebflux.app.adapter.common.mapper.toInput
import bespalhuk.kwebflux.core.port.input.UpdateLegendaryPortIn
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Component
class UpdateLegendaryTopicConsumer(
    private val updateLegendaryPortIn: UpdateLegendaryPortIn,
) {

    @Bean
    fun updateLegendaryConsumer(): Consumer<Flux<UpdateLegendaryMessage>> =
        Consumer { flux ->
            flux.doOnNext { message ->
                updateLegendaryPortIn.update(message.toInput())
            }.subscribe()
        }
}

package bespalhuk.kwebflux.app.adapter.input.integration.topic.user

import bespalhuk.kwebflux.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kwebflux.app.adapter.common.mapper.toInput
import bespalhuk.kwebflux.core.port.input.UpdateLegendaryPortIn
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.function.Consumer

@Component
class UpdateLegendaryTopicConsumer(
    private val updateLegendaryPortIn: UpdateLegendaryPortIn,
) {

    @Bean
    fun updateLegendaryConsumer(): Consumer<Flux<Message<UpdateLegendaryMessage>>> =
        Consumer { flux ->
            flux.delayElements(Duration.ofMillis(1000))
                .subscribe { message ->
                    updateLegendaryPortIn.update(message.payload.toInput()).subscribe()
                }
        }
}

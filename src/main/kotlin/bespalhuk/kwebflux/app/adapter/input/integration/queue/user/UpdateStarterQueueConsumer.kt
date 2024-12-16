package bespalhuk.kwebflux.app.adapter.input.integration.queue.user

import bespalhuk.kwebflux.app.adapter.common.UpdateStarterMessage
import bespalhuk.kwebflux.app.adapter.common.mapper.toInput
import bespalhuk.kwebflux.core.port.input.UpdateStarterPortIn
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Component
class UpdateStarterQueueConsumer(
    private val updateStarterPortIn: UpdateStarterPortIn,
) {

    @Bean
    fun updateStarterConsumer(): Consumer<Flux<UpdateStarterMessage>> =
        Consumer { flux ->
            flux.doOnNext { message ->
                updateStarterPortIn.update(message.toInput())
            }.subscribe()
        }
}
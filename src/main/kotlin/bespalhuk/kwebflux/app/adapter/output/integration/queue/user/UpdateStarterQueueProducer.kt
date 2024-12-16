package bespalhuk.kwebflux.app.adapter.output.integration.queue.user

import bespalhuk.kwebflux.app.adapter.common.mapper.toMessage
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterOutput
import bespalhuk.kwebflux.core.port.output.UpdateStarterPortOut
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class UpdateStarterQueueProducer(
    private val streamBridge: StreamBridge,
) : UpdateStarterPortOut {

    override fun publish(output: UpdateStarterOutput) {
        streamBridge.send("updateStarterProducer-out-0", output.toMessage())
    }
}

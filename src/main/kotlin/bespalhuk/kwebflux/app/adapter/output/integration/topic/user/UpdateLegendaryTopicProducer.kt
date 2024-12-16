package bespalhuk.kwebflux.app.adapter.output.integration.topic.user

import bespalhuk.kwebflux.app.adapter.common.mapper.toMessage
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryOutput
import bespalhuk.kwebflux.core.port.output.UpdateLegendaryPortOut
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class UpdateLegendaryTopicProducer(
    private val streamBridge: StreamBridge,
) : UpdateLegendaryPortOut {

    override fun publish(output: UpdateLegendaryOutput) {
        streamBridge.send("updateLegendaryProducer-out-0", output.toMessage())
    }
}

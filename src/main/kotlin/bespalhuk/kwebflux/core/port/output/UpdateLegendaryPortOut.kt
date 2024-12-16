package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryOutput

fun interface UpdateLegendaryPortOut {
    fun publish(output: UpdateLegendaryOutput)
}

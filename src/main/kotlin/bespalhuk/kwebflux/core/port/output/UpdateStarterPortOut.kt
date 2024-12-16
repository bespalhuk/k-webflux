package bespalhuk.kwebflux.core.port.output

import bespalhuk.kwebflux.core.domain.vo.UpdateStarterOutput

fun interface UpdateStarterPortOut {
    fun publish(output: UpdateStarterOutput)
}

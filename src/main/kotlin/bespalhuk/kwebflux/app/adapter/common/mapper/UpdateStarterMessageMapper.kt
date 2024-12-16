package bespalhuk.kwebflux.app.adapter.common.mapper

import bespalhuk.kwebflux.app.adapter.common.UpdateStarterMessage
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterInput
import bespalhuk.kwebflux.core.domain.vo.UpdateStarterOutput

fun UpdateStarterOutput.toMessage(
) = UpdateStarterMessage(
    id = id,
    starterNumber = starter.number,
)

fun UpdateStarterMessage.toInput(
) = UpdateStarterInput(
    id = id,
    starter = StarterPokemonEnum.map(starterNumber),
)

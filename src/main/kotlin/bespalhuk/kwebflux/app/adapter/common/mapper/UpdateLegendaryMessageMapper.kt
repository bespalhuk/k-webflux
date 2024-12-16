package bespalhuk.kwebflux.app.adapter.common.mapper

import bespalhuk.kwebflux.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryInput
import bespalhuk.kwebflux.core.domain.vo.UpdateLegendaryOutput

fun UpdateLegendaryOutput.toMessage(
) = UpdateLegendaryMessage(
    id = id,
    legendaryNumber = legendary.number,
)

fun UpdateLegendaryMessage.toInput(
) = UpdateLegendaryInput(
    id = id,
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)

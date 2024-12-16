package bespalhuk.kwebflux.app.adapter.input.web.user.update.mapper

import bespalhuk.kwebflux.app.adapter.input.web.user.update.UpdateUserRequest
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateUserInput

fun UpdateUserRequest.toInput(
    id: String,
) = UpdateUserInput(
    id = id,
    team = team,
    starter = StarterPokemonEnum.map(starterNumber),
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)

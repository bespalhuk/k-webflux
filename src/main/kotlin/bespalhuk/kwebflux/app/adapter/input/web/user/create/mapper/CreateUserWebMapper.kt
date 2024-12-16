package bespalhuk.kwebflux.app.adapter.input.web.user.create.mapper

import bespalhuk.kwebflux.app.adapter.input.web.user.create.CreateUserRequest
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.CreateUserInput

fun CreateUserRequest.toInput(
) = CreateUserInput(
    username = username,
    team = team,
    starter = StarterPokemonEnum.map(starterNumber),
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)

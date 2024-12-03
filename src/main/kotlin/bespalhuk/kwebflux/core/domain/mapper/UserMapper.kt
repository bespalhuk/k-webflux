package bespalhuk.kwebflux.core.domain.mapper

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UserInput

fun UserInput.toDomain(
) = User(
    id = null,
    createdDate = null,
    lastModified = null,
    username = username,
    team = User.Team(
        starter = StarterPokemonEnum.map(starterNumber),
        legendary = LegendaryPokemonEnum.map(legendaryNumber),
    ),
)

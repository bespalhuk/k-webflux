package bespalhuk.kwebflux.app.adapter.input.web.user

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum

data class CreateUserResponse(
    val id: String,
    val username: String,
    val starter: StarterPokemonEnum,
    val starterMove: String,
    val legendary: LegendaryPokemonEnum,
    val legendaryMove: String,
)

package bespalhuk.kwebflux.core.domain.vo

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum

data class UpdateUserInput(
    val id: String,
    val team: String,
    val starter: StarterPokemonEnum,
    val legendary: LegendaryPokemonEnum,
)

package bespalhuk.kwebflux.core.domain.vo

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum

data class UpdateLegendaryInput(
    val id: String,
    val legendary: LegendaryPokemonEnum,
)

package bespalhuk.kwebflux.core.domain.vo

import bespalhuk.kwebflux.core.domain.StarterPokemonEnum

data class UpdateStarterOutput(
    val id: String,
    val starter: StarterPokemonEnum,
)

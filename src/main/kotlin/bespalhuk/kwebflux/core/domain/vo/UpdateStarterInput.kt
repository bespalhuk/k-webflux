package bespalhuk.kwebflux.core.domain.vo

import bespalhuk.kwebflux.core.domain.StarterPokemonEnum

data class UpdateStarterInput(
    val id: String,
    val starter: StarterPokemonEnum,
)

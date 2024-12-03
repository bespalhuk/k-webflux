package bespalhuk.kwebflux.core.domain

import java.time.Instant

data class User(
    val id: String?,
    val createdDate: Instant?,
    val lastModified: Instant?,
    val username: String,
    val team: Team,
) {
    data class Team(
        val starter: StarterPokemonEnum,
        var starterMove: String? = null,
        val legendary: LegendaryPokemonEnum,
        var legendaryMove: String? = null,
    )
}

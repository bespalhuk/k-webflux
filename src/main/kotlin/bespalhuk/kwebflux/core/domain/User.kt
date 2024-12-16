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
        var name: String,
        var starter: StarterPokemonEnum,
        var starterMove: String? = null,
        var legendary: LegendaryPokemonEnum,
        var legendaryMove: String? = null,
    )
}

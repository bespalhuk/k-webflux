package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.User
import java.time.Instant
import java.util.UUID

class UserDataProvider {

    fun newUser(): User = User(
        null,
        null,
        null,
        "username",
        User.Team(
            "rocket",
            StarterPokemonEnum.PIKACHU,
            "shock",
            LegendaryPokemonEnum.MEW,
            "hadouken",
        )
    )

    fun user(): User = User(
        UUID.randomUUID().toString(),
        Instant.now(),
        Instant.now(),
        "username",
        User.Team(
            "rocket",
            StarterPokemonEnum.PIKACHU,
            "shock",
            LegendaryPokemonEnum.MEW,
            "hadouken",
        )
    )
}

package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocument
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.User
import java.time.Instant
import java.util.*

class UserDocumentDataProvider {

    fun document(): UserDocument = UserDocument(
        UUID.randomUUID().toString(),
        Instant.now(),
        Instant.now(),
        "username",
        User.Team(
            StarterPokemonEnum.PIKACHU,
            "shock",
            LegendaryPokemonEnum.MEW,
            "hadouken",
        )
    )
}

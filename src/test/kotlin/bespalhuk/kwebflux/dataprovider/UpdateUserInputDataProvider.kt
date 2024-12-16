package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.UpdateUserInput
import java.util.UUID

class UpdateUserInputDataProvider {

    fun input(): UpdateUserInput = UpdateUserInput(
        UUID.randomUUID().toString(),
        "rocket",
        StarterPokemonEnum.PIKACHU,
        LegendaryPokemonEnum.MEW,
    )
}

package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.core.domain.vo.CreateUserInput

class CreateUserInputDataProvider {

    fun input(): CreateUserInput = CreateUserInput(
        "username",
        "rocket",
        StarterPokemonEnum.PIKACHU,
        LegendaryPokemonEnum.MEW,
    )
}

package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.core.domain.vo.UserInput

class UserInputDataProvider {

    fun input(): UserInput = UserInput(
        "username",
        25,
        151,
    )
}

package bespalhuk.kwebflux.app.adapter.input.web.user.mapper

import bespalhuk.kwebflux.app.adapter.input.web.user.CreateUserRequest
import bespalhuk.kwebflux.app.adapter.input.web.user.CreateUserResponse
import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.UserInput

fun CreateUserRequest.toInput(
) = UserInput(
    username = username,
    starterNumber = starterNumber,
    legendaryNumber = legendaryNumber,
)

fun User.toResponse(
) = CreateUserResponse(
    id = id!!,
    username = username,
    starter = team.starter,
    starterMove = team.starterMove!!,
    legendary = team.legendary,
    legendaryMove = team.legendaryMove!!,
)

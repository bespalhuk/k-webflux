package bespalhuk.kwebflux.app.adapter.input.web.user.mapper

import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.core.domain.User

fun User.toResponse(
) = UserResponse(
    id = id!!,
    username = username,
    team = team.name,
    starter = team.starter,
    starterMove = team.starterMove!!,
    legendary = team.legendary,
    legendaryMove = team.legendaryMove!!,
)

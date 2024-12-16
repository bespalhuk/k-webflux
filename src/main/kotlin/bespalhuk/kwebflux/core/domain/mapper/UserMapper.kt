package bespalhuk.kwebflux.core.domain.mapper

import bespalhuk.kwebflux.core.domain.User
import bespalhuk.kwebflux.core.domain.vo.CreateUserInput

fun CreateUserInput.toDomain(
) = User(
    id = null,
    createdDate = null,
    lastModified = null,
    username = username,
    team = User.Team(
        name = team,
        starter = starter,
        legendary = legendary,
    ),
)
